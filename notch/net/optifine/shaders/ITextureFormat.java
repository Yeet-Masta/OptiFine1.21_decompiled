package net.optifine.shaders;

import net.optifine.Config;
import net.optifine.config.ConfigUtils;
import net.optifine.texture.IColorBlender;

public interface ITextureFormat {
   IColorBlender getColorBlender(ShadersTextureType var1);

   boolean isTextureBlend(ShadersTextureType var1);

   String getMacroName();

   String getMacroVersion();

   static ITextureFormat readConfiguration() {
      if (!Config.isShaders()) {
         return null;
      } else {
         String formatStr = ConfigUtils.readString("optifine/texture.properties", "format");
         if (formatStr != null) {
            String[] parts = Config.tokenize(formatStr, "/");
            String name = parts[0];
            String ver = parts.length > 1 ? parts[1] : null;
            if (name.equals("lab-pbr")) {
               return new TextureFormatLabPbr(ver);
            } else {
               Config.warn("Unknown texture format: " + formatStr);
               return null;
            }
         } else {
            return null;
         }
      }
   }
}
