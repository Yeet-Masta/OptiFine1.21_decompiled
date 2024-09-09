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
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Lang {
   private static final Splitter splitter = Splitter.on('=').limit(2);
   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

   public static void resourcesReloaded() {
      Map localeProperties = new HashMap();
      List listFiles = new ArrayList();
      String PREFIX = "optifine/lang/";
      String EN_US = "en_us";
      String SUFFIX = ".lang";
      listFiles.add(PREFIX + EN_US + SUFFIX);
      if (!Config.getGameSettings().f_92075_.equals(EN_US)) {
         listFiles.add(PREFIX + Config.getGameSettings().f_92075_ + SUFFIX);
      }

      String[] files = (String[])listFiles.toArray(new String[listFiles.size()]);
      loadResources((PackResources)Config.getDefaultResourcePack(), (String[])files, localeProperties);
      PackResources[] resourcePacks = Config.getResourcePacks();

      for(int i = 0; i < resourcePacks.length; ++i) {
         PackResources rp = resourcePacks[i];
         loadResources((PackResources)rp, (String[])files, localeProperties);
      }

   }

   private static void loadResources(PackResources rp, String[] files, Map localeProperties) {
      try {
         for(int i = 0; i < files.length; ++i) {
            String file = files[i];
            ResourceLocation loc = new ResourceLocation(file);
            IoSupplier supplier = rp.m_214146_(PackType.CLIENT_RESOURCES, loc);
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

      while(it.hasNext()) {
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

   public static void loadResources(ResourceManager resourceManager, String langCode, Map map) {
      try {
         String pathLang = "optifine/lang/" + langCode + ".lang";
         ResourceLocation locLang = new ResourceLocation(pathLang);
         Resource res = resourceManager.m_215593_(locLang);
         InputStream is = res.m_215507_();
         loadLocaleData(is, map);
      } catch (IOException var7) {
      }

   }

   public static String get(String key) {
      return I18n.m_118938_(key, new Object[0]);
   }

   public static MutableComponent getComponent(String key) {
      return Component.m_237115_(key);
   }

   public static String get(String key, String def) {
      String str = I18n.m_118938_(key, new Object[0]);
      return str != null && !str.equals(key) ? str : def;
   }

   public static String getOn() {
      return I18n.m_118938_("options.on", new Object[0]);
   }

   public static String getOff() {
      return I18n.m_118938_("options.off", new Object[0]);
   }

   public static String getFast() {
      return I18n.m_118938_("options.graphics.fast", new Object[0]);
   }

   public static String getFancy() {
      return I18n.m_118938_("options.graphics.fancy", new Object[0]);
   }

   public static String getDefault() {
      return I18n.m_118938_("generator.minecraft.normal", new Object[0]);
   }
}
