package net.optifine.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.minecraft.resources.ResourceLocation;
import net.optifine.Config;
import net.optifine.util.PropertiesOrdered;

public class ConfigUtils {
   public static String readString(String fileName, String property) {
      Properties props = readProperties(fileName);
      if (props == null) {
         return null;
      } else {
         String val = props.getProperty(property);
         if (val != null) {
            val = val.trim();
         }

         return val;
      }
   }

   public static Properties readProperties(String fileName) {
      try {
         ResourceLocation loc = new ResourceLocation(fileName);
         InputStream in = Config.getResourceStream(loc);
         if (in == null) {
            return null;
         } else {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            return props;
         }
      } catch (FileNotFoundException var4) {
         return null;
      } catch (IOException var5) {
         Config.warn("Error parsing: " + fileName);
         String var10000 = var5.getClass().getName();
         Config.warn(var10000 + ": " + var5.getMessage());
         return null;
      }
   }
}
