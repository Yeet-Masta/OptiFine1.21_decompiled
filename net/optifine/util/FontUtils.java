package net.optifine.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.util.profiling.InactiveProfiler;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class FontUtils {
   public static Properties readFontProperties(net.minecraft.resources.ResourceLocation locationFontTexture) {
      String fontFileName = locationFontTexture.m_135815_();
      Properties props = new PropertiesOrdered();
      String suffix = ".png";
      if (!fontFileName.endsWith(suffix)) {
         return props;
      } else {
         String fileName = fontFileName.substring(0, fontFileName.length() - suffix.length()) + ".properties";

         try {
            net.minecraft.resources.ResourceLocation locProp = new net.minecraft.resources.ResourceLocation(locationFontTexture.m_135827_(), fileName);
            InputStream in = Config.getResourceStream(Config.getResourceManager(), locProp);
            if (in == null) {
               return props;
            }

            Config.log("Loading " + fileName);
            props.load(in);
            in.close();
         } catch (FileNotFoundException var7) {
         } catch (IOException var8) {
            var8.printStackTrace();
         }

         return props;
      }
   }

   public static Int2ObjectMap<Float> readCustomCharWidths(Properties props) {
      Int2ObjectMap<Float> map = new Int2ObjectOpenHashMap();

      for (String key : props.keySet()) {
         String prefix = "width.";
         if (key.startsWith(prefix)) {
            String numStr = key.substring(prefix.length());
            int num = Config.parseInt(numStr, -1);
            if (num >= 0) {
               String value = props.getProperty(key);
               float width = Config.parseFloat(value, -1.0F);
               if (width >= 0.0F) {
                  char ch = (char)num;
                  map.put(ch, new Float(width));
               }
            }
         }
      }

      return map;
   }

   public static float readFloat(Properties props, String key, float defOffset) {
      String str = props.getProperty(key);
      if (str == null) {
         return defOffset;
      } else {
         float offset = Config.parseFloat(str, Float.MIN_VALUE);
         if (offset == Float.MIN_VALUE) {
            Config.warn("Invalid value for " + key + ": " + str);
            return defOffset;
         } else {
            return offset;
         }
      }
   }

   public static boolean readBoolean(Properties props, String key, boolean defVal) {
      String str = props.getProperty(key);
      if (str == null) {
         return defVal;
      } else {
         String strLow = str.toLowerCase().trim();
         if (strLow.equals("true") || strLow.equals("on")) {
            return true;
         } else if (!strLow.equals("false") && !strLow.equals("off")) {
            Config.warn("Invalid value for " + key + ": " + str);
            return defVal;
         } else {
            return false;
         }
      }
   }

   public static net.minecraft.resources.ResourceLocation getHdFontLocation(net.minecraft.resources.ResourceLocation fontLoc) {
      if (!Config.isCustomFonts()) {
         return fontLoc;
      } else if (fontLoc == null) {
         return fontLoc;
      } else if (!Config.isMinecraftThread()) {
         return fontLoc;
      } else {
         String fontName = fontLoc.m_135815_();
         String texturesStr = "textures/";
         String optifineStr = "optifine/";
         if (!fontName.startsWith(texturesStr)) {
            return fontLoc;
         } else {
            fontName = fontName.substring(texturesStr.length());
            fontName = optifineStr + fontName;
            net.minecraft.resources.ResourceLocation fontLocHD = new net.minecraft.resources.ResourceLocation(fontLoc.m_135827_(), fontName);
            return Config.hasResource(Config.getResourceManager(), fontLocHD) ? fontLocHD : fontLoc;
         }
      }
   }

   public static void reloadFonts() {
      PreparationBarrier stage = new PreparationBarrier() {
         public <T> CompletableFuture<T> m_6769_(T x) {
            return CompletableFuture.completedFuture(x);
         }
      };
      Executor ex = net.minecraft.Util.m_183991_();
      Minecraft mc = Minecraft.m_91087_();
      FontManager frm = (FontManager)Reflector.getFieldValue(mc, Reflector.Minecraft_fontResourceManager);
      if (frm != null) {
         frm.m_5540_(stage, Config.getResourceManager(), InactiveProfiler.f_18554_, InactiveProfiler.f_18554_, ex, mc);
      }
   }
}
