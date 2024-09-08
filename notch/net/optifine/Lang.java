package net.optifine;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5012_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Lang {
   private static final Splitter splitter = Splitter.on('=').limit(2);
   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

   public static void resourcesReloaded() {
      Map localeProperties = new HashMap();
      List<String> listFiles = new ArrayList();
      String PREFIX = "optifine/lang/";
      String EN_US = "en_us";
      String SUFFIX = ".lang";
      listFiles.add(PREFIX + EN_US + SUFFIX);
      if (!Config.getGameSettings().f_92075_.equals(EN_US)) {
         listFiles.add(PREFIX + Config.getGameSettings().f_92075_ + SUFFIX);
      }

      String[] files = (String[])listFiles.toArray(new String[listFiles.size()]);
      loadResources(Config.getDefaultResourcePack(), files, localeProperties);
      C_50_[] resourcePacks = Config.getResourcePacks();

      for (int i = 0; i < resourcePacks.length; i++) {
         C_50_ rp = resourcePacks[i];
         loadResources(rp, files, localeProperties);
      }
   }

   private static void loadResources(C_50_ rp, String[] files, Map localeProperties) {
      try {
         for (int i = 0; i < files.length; i++) {
            String file = files[i];
            C_5265_ loc = new C_5265_(file);
            C_243587_<InputStream> supplier = rp.m_214146_(C_51_.CLIENT_RESOURCES, loc);
            if (supplier != null) {
               InputStream in = (InputStream)supplier.m_247737_();
               if (in != null) {
                  loadLocaleData(in, localeProperties);
               }
            }
         }
      } catch (IOException var8) {
         var8.printStackTrace();
      }
   }

   public static void loadLocaleData(InputStream is, Map localeProperties) throws IOException {
      Iterator it = IOUtils.readLines(is, Charsets.UTF_8).iterator();
      is.close();

      while (it.hasNext()) {
         String line = (String)it.next();
         if (!line.isEmpty() && line.charAt(0) != '#') {
            String[] parts = (String[])Iterables.toArray(splitter.split(line), String.class);
            if (parts != null && parts.length == 2) {
               String key = parts[0];
               String value = pattern.matcher(parts[1]).replaceAll("%$1s");
               localeProperties.put(key, value);
            }
         }
      }
   }

   public static void loadResources(C_77_ resourceManager, String langCode, Map<String, String> map) {
      try {
         String pathLang = "optifine/lang/" + langCode + ".lang";
         C_5265_ locLang = new C_5265_(pathLang);
         C_76_ res = resourceManager.getResourceOrThrow(locLang);
         InputStream is = res.m_215507_();
         loadLocaleData(is, map);
      } catch (IOException var7) {
      }
   }

   public static String get(String key) {
      return C_4513_.m_118938_(key, new Object[0]);
   }

   public static C_5012_ getComponent(String key) {
      return C_4996_.m_237115_(key);
   }

   public static String get(String key, String def) {
      String str = C_4513_.m_118938_(key, new Object[0]);
      return str != null && !str.equals(key) ? str : def;
   }

   public static String getOn() {
      return C_4513_.m_118938_("options.on", new Object[0]);
   }

   public static String getOff() {
      return C_4513_.m_118938_("options.off", new Object[0]);
   }

   public static String getFast() {
      return C_4513_.m_118938_("options.graphics.fast", new Object[0]);
   }

   public static String getFancy() {
      return C_4513_.m_118938_("options.graphics.fancy", new Object[0]);
   }

   public static String getDefault() {
      return C_4513_.m_118938_("generator.minecraft.normal", new Object[0]);
   }
}
