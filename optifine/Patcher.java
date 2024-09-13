package optifine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import optifine.xdelta.GDiffPatcher;
import optifine.xdelta.PatchException;

public class Patcher {
   public static String CONFIG_FILE;
   public static String CONFIG_FILE2;
   public static String CONFIG_FILE3;
   public static String PREFIX_PATCH;
   public static String SUFFIX_DELTA;
   public static String SUFFIX_MD5;

   public static void main(String[] args) throws Exception {
      if (args.length < 3) {
         Utils.dbg("Usage: Patcher <base.jar> <diff.jar> <mod.jar>");
      } else {
         File baseFile = new File(args[0]);
         File diffFile = new File(args[1]);
         File modFile = new File(args[2]);
         if (baseFile.getName().equals("AUTO")) {
            baseFile = Differ.detectBaseFile(diffFile);
         }

         if (!baseFile.exists() || !baseFile.isFile()) {
            throw new IOException("Base file not found: " + baseFile);
         } else if (diffFile.exists() && diffFile.isFile()) {
            m_288227_(baseFile, diffFile, modFile);
         } else {
            throw new IOException("Diff file not found: " + modFile);
         }
      }
   }

   public static void m_288227_(File baseFile, File diffFile, File modFile) throws Exception {
      ZipFile diffZip = new ZipFile(diffFile);
      Map<String, String> cfgMap = getConfigurationMap(diffZip);
      Pattern[] patterns = getConfigurationPatterns(cfgMap);
      ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(modFile));
      ZipFile baseZip = new ZipFile(baseFile);
      ZipResourceProvider zrp = new ZipResourceProvider(baseZip);
      Enumeration diffZipEntries = diffZip.entries();

      while (diffZipEntries.hasMoreElements()) {
         ZipEntry diffZipEntry = (ZipEntry)diffZipEntries.nextElement();
         InputStream in = diffZip.getInputStream(diffZipEntry);
         byte[] bytes = Utils.readAll(in);
         String name = diffZipEntry.getName();
         if (name.startsWith("patch/") && name.endsWith(".xdelta")) {
            name = name.substring("patch/".length());
            name = name.substring(0, name.length() - ".xdelta".length());
            byte[] bytesMod = applyPatch(name, bytes, patterns, cfgMap, zrp);
            String nameMd5 = "patch/" + name + ".md5";
            ZipEntry diffZipEntryMd5 = diffZip.getEntry(nameMd5);
            if (diffZipEntryMd5 != null) {
               byte[] md5 = Utils.readAll(diffZip.getInputStream(diffZipEntryMd5));
               String md5Str = new String(md5, "ASCII");
               byte[] md5Mod = HashUtils.getHashMd5(bytesMod);
               String md5ModStr = HashUtils.toHexString(md5Mod);
               if (!md5Str.equals(md5ModStr)) {
                  throw new Exception("MD5 not matching, name: " + name + ", saved: " + md5Str + ", patched: " + md5ModStr);
               }
            }

            ZipEntry zipEntryMod = new ZipEntry(name);
            zipOut.putNextEntry(zipEntryMod);
            zipOut.write(bytesMod);
            zipOut.closeEntry();
            Utils.dbg("Mod: " + name);
         } else if (!name.startsWith("patch/") || !name.endsWith(".md5")) {
            ZipEntry zipEntrySame = new ZipEntry(name);
            zipOut.putNextEntry(zipEntrySame);
            zipOut.write(bytes);
            zipOut.closeEntry();
            Utils.dbg("Same: " + zipEntrySame.getName());
         }
      }

      zipOut.close();
   }

   public static byte[] applyPatch(String name, byte[] bytesDiff, Pattern[] patterns, Map<String, String> cfgMap, IResourceProvider resourceProvider) throws IOException, PatchException {
      name = Utils.removePrefix(name, "/");
      String baseName = getPatchBase(name, patterns, cfgMap);
      if (baseName == null) {
         throw new IOException("No patch base, name: " + name + ", patterns: " + Utils.arrayToCommaSeparatedString(patterns));
      } else {
         InputStream baseIn = resourceProvider.getResourceStream(baseName);
         if (baseIn == null) {
            throw new IOException("Base resource not found: " + baseName);
         } else {
            byte[] baseBytes = Utils.readAll(baseIn);
            InputStream patchStream = new ByteArrayInputStream(bytesDiff);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            new GDiffPatcher(baseBytes, patchStream, outputStream);
            outputStream.close();
            return outputStream.toByteArray();
         }
      }
   }

   public static Pattern[] getConfigurationPatterns(Map<String, String> cfgMap) {
      String[] cfgKeys = (String[])cfgMap.keySet().toArray(new String[0]);
      Pattern[] patterns = new Pattern[cfgKeys.length];

      for (int i = 0; i < cfgKeys.length; i++) {
         String key = cfgKeys[i];
         patterns[i] = Pattern.m_289905_(key);
      }

      return patterns;
   }

   public static Map<String, String> getConfigurationMap(ZipFile modZip) throws IOException {
      Map<String, String> cfgMap = getConfigurationMap(modZip, "patch.cfg");
      Map<String, String> cfgMap2 = getConfigurationMap(modZip, "patch2.cfg");
      Map<String, String> cfgMap3 = getConfigurationMap(modZip, "patch3.cfg");
      cfgMap.putAll(cfgMap2);
      cfgMap.putAll(cfgMap3);
      return cfgMap;
   }

   public static Map<String, String> getConfigurationMap(ZipFile modZip, String pathConfig) throws IOException {
      Map<String, String> cfgMap = new LinkedHashMap();
      if (modZip == null) {
         return cfgMap;
      } else {
         ZipEntry entryPatch = modZip.getEntry(pathConfig);
         if (entryPatch == null) {
            return cfgMap;
         } else {
            InputStream inPatch = modZip.getInputStream(entryPatch);
            String[] lines = Utils.readLines(inPatch, "ASCII");
            inPatch.close();

            for (int i = 0; i < lines.length; i++) {
               String line = lines[i].trim();
               if (!line.startsWith("#") && line.length() > 0) {
                  String[] parts = Utils.tokenize(line, "=");
                  if (parts.length != 2) {
                     throw new IOException("Invalid patch configuration: " + line);
                  }

                  String key = parts[0].trim();
                  String val = parts[1].trim();
                  cfgMap.put(key, val);
               }
            }

            return cfgMap;
         }
      }
   }

   public static String getPatchBase(String name, Pattern[] patterns, Map<String, String> cfgMap) {
      name = Utils.removePrefix(name, "/");

      for (int i = 0; i < patterns.length; i++) {
         Pattern pattern = patterns[i];
         Matcher matcher = pattern.matcher(name);
         if (matcher.matches()) {
            String base = (String)cfgMap.get(pattern.pattern());
            if (base != null && base.trim().equals("*")) {
               return name;
            }

            for (int g = 1; g <= matcher.groupCount(); g++) {
               base = base.replace("$" + g, matcher.group(g));
            }

            return base;
         }
      }

      return null;
   }
}
