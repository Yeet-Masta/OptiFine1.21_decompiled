package net.optifine.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3509_;
import net.minecraft.src.C_439_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_5322_;
import net.minecraft.src.C_69_.C_70_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class FontUtils {
   public static Properties readFontProperties(C_5265_ locationFontTexture) {
      String fontFileName = locationFontTexture.m_135815_();
      Properties props = new PropertiesOrdered();
      String suffix = ".png";
      if (!fontFileName.endsWith(suffix)) {
         return props;
      } else {
         String fileName = fontFileName.substring(0, fontFileName.length() - suffix.length()) + ".properties";

         try {
            C_5265_ locProp = new C_5265_(locationFontTexture.m_135827_(), fileName);
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

   public static C_5265_ getHdFontLocation(C_5265_ fontLoc) {
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
            C_5265_ fontLocHD = new C_5265_(fontLoc.m_135827_(), fontName);
            return Config.hasResource(Config.getResourceManager(), fontLocHD) ? fontLocHD : fontLoc;
         }
      }
   }

   public static void reloadFonts() {
      C_70_ stage = new C_70_() {
         public <T> CompletableFuture<T> m_6769_(T x) {
            return CompletableFuture.completedFuture(x);
         }
      };
      Executor ex = C_5322_.m_183991_();
      C_3391_ mc = C_3391_.m_91087_();
      C_3509_ frm = (C_3509_)Reflector.getFieldValue(mc, Reflector.Minecraft_fontResourceManager);
      if (frm != null) {
         frm.m_5540_(stage, Config.getResourceManager(), C_439_.f_18554_, C_439_.f_18554_, ex, mc);
      }
   }
}
