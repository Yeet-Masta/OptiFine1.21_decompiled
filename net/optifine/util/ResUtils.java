package net.optifine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.VanillaPackResources;
import net.optifine.Config;

public class ResUtils {
   public static String[] collectFiles(String prefix, String suffix) {
      return collectFiles(new String[]{prefix}, new String[]{suffix});
   }

   public static String[] collectFiles(String[] prefixes, String[] suffixes) {
      Set setPaths = new LinkedHashSet();
      PackResources[] rps = Config.getResourcePacks();

      for(int i = 0; i < rps.length; ++i) {
         PackResources rp = rps[i];
         String[] ps = collectFiles(rp, (String[])prefixes, (String[])suffixes, (String[])null);
         setPaths.addAll(Arrays.asList(ps));
      }

      String[] paths = (String[])setPaths.toArray(new String[setPaths.size()]);
      return paths;
   }

   public static String[] collectFiles(PackResources rp, String prefix, String suffix, String[] defaultPaths) {
      return collectFiles(rp, new String[]{prefix}, new String[]{suffix}, defaultPaths);
   }

   public static String[] collectFiles(PackResources rp, String[] prefixes, String[] suffixes) {
      return collectFiles(rp, (String[])prefixes, (String[])suffixes, (String[])null);
   }

   public static String[] collectFiles(PackResources rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
      if (!(rp instanceof CompositePackResources tpFile)) {
         if (rp instanceof VanillaPackResources) {
            return collectFilesFixed(rp, defaultPaths);
         } else {
            tpFile = null;
            File tpFile;
            if (rp instanceof FilePackResources) {
               FilePackResources fpr = (FilePackResources)rp;
               tpFile = fpr.getFile();
            } else {
               if (!(rp instanceof PathPackResources)) {
                  Config.warn("Unknown resource pack type: " + String.valueOf(rp));
                  return new String[0];
               }

               PathPackResources ppr = (PathPackResources)rp;
               tpFile = ppr.f_243919_.toFile();
            }

            if (tpFile == null) {
               return new String[0];
            } else if (tpFile.isDirectory()) {
               return collectFilesFolder(tpFile, "", prefixes, suffixes);
            } else if (tpFile.isFile()) {
               return collectFilesZIP(tpFile, prefixes, suffixes);
            } else {
               Config.warn("Unknown resource pack file: " + String.valueOf(tpFile));
               return new String[0];
            }
         }
      } else {
         List pathsList = new ArrayList();
         Iterator var6 = tpFile.f_291498_.iterator();

         while(var6.hasNext()) {
            PackResources subRp = (PackResources)var6.next();
            String[] subPaths = collectFiles(subRp, prefixes, suffixes, defaultPaths);
            pathsList.addAll(Arrays.asList(subPaths));
         }

         String[] paths = (String[])pathsList.toArray(new String[pathsList.size()]);
         return paths;
      }
   }

   private static String[] collectFilesFixed(PackResources rp, String[] paths) {
      if (paths == null) {
         return new String[0];
      } else {
         List list = new ArrayList();

         for(int i = 0; i < paths.length; ++i) {
            String path = paths[i];
            if (!isLowercase(path)) {
               Config.warn("Skipping non-lowercase path: " + path);
            } else {
               ResourceLocation loc = new ResourceLocation(path);
               if (Config.hasResource(rp, loc)) {
                  list.add(path);
               }
            }
         }

         String[] pathArr = (String[])list.toArray(new String[list.size()]);
         return pathArr;
      }
   }

   private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
      List list = new ArrayList();
      String prefixAssets = "assets/minecraft/";
      File[] files = tpFile.listFiles();
      if (files == null) {
         return new String[0];
      } else {
         for(int i = 0; i < files.length; ++i) {
            File file = files[i];
            String name;
            if (file.isFile()) {
               name = basePath + file.getName();
               if (name.startsWith(prefixAssets)) {
                  name = name.substring(prefixAssets.length());
                  if (StrUtils.startsWith(name, prefixes) && StrUtils.endsWith(name, suffixes)) {
                     if (!isLowercase(name)) {
                        Config.warn("Skipping non-lowercase path: " + name);
                     } else {
                        list.add(name);
                     }
                  }
               }
            } else if (file.isDirectory()) {
               name = basePath + file.getName() + "/";
               String[] names = collectFilesFolder(file, name, prefixes, suffixes);

               for(int n = 0; n < names.length; ++n) {
                  String name = names[n];
                  list.add(name);
               }
            }
         }

         String[] names = (String[])list.toArray(new String[list.size()]);
         return names;
      }
   }

   private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
      List list = new ArrayList();
      String prefixAssets = "assets/minecraft/";

      try {
         ZipFile zf = new ZipFile(tpFile);
         Enumeration en = zf.entries();

         while(en.hasMoreElements()) {
            ZipEntry ze = (ZipEntry)en.nextElement();
            String name = ze.getName();
            if (name.startsWith(prefixAssets)) {
               name = name.substring(prefixAssets.length());
               if (StrUtils.startsWith(name, prefixes) && StrUtils.endsWith(name, suffixes)) {
                  if (!isLowercase(name)) {
                     Config.warn("Skipping non-lowercase path: " + name);
                  } else {
                     list.add(name);
                  }
               }
            }
         }

         zf.close();
         String[] names = (String[])list.toArray(new String[list.size()]);
         return names;
      } catch (IOException var9) {
         var9.printStackTrace();
         return new String[0];
      }
   }

   private static boolean isLowercase(String str) {
      return str.equals(str.toLowerCase(Locale.ROOT));
   }

   public static Properties readProperties(String path, String module) {
      ResourceLocation loc = new ResourceLocation(path);

      try {
         InputStream in = Config.getResourceStream(loc);
         if (in == null) {
            return null;
         } else {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg(module + ": Loading " + path);
            return props;
         }
      } catch (FileNotFoundException var5) {
         return null;
      } catch (IOException var6) {
         Config.warn(module + ": Error reading " + path);
         return null;
      }
   }

   public static Properties readProperties(InputStream in, String module) {
      if (in == null) {
         return null;
      } else {
         try {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            return props;
         } catch (FileNotFoundException var3) {
            return null;
         } catch (IOException var4) {
            return null;
         }
      }
   }
}
