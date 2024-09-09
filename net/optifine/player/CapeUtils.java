package net.optifine.player;

import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.optifine.Config;
import net.optifine.util.TextureUtils;

public class CapeUtils {
   private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");

   public static void downloadCape(net.minecraft.client.player.AbstractClientPlayer player) {
      String username = player.getNameClear();
      if (username != null && !username.isEmpty() && !username.contains("\u0000") && PATTERN_USERNAME.matcher(username).matches()) {
         String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
         net.minecraft.resources.ResourceLocation rl = new net.minecraft.resources.ResourceLocation("capeof/" + username);
         net.minecraft.client.renderer.texture.TextureManager textureManager = Minecraft.m_91087_().m_91097_();
         net.minecraft.client.renderer.texture.AbstractTexture tex = textureManager.m_174786_(rl, null);
         if (tex != null && tex instanceof net.minecraft.client.renderer.texture.HttpTexture tdid && tdid.imageFound != null) {
            if (tdid.imageFound) {
               player.setLocationOfCape(rl);
               if (tdid.getProcessTask() instanceof CapeImageBuffer) {
                  CapeImageBuffer cib = (CapeImageBuffer)tdid.getProcessTask();
                  player.setElytraOfCape(cib.isElytraOfCape());
               }
            }

            return;
         }

         CapeImageBuffer cib = new CapeImageBuffer(player, rl);
         net.minecraft.resources.ResourceLocation locEmpty = TextureUtils.LOCATION_TEXTURE_EMPTY;
         net.minecraft.client.renderer.texture.HttpTexture textureCape = new net.minecraft.client.renderer.texture.HttpTexture(
            null, ofCapeUrl, locEmpty, false, cib
         );
         textureCape.pipeline = true;
         textureManager.m_118495_(rl, textureCape);
      }
   }

   public static com.mojang.blaze3d.platform.NativeImage parseCape(com.mojang.blaze3d.platform.NativeImage img) {
      int imageWidth = 64;
      int imageHeight = 32;
      int srcWidth = img.m_84982_();

      for (int srcHeight = img.m_85084_(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2) {
         imageWidth *= 2;
      }

      com.mojang.blaze3d.platform.NativeImage imgNew = new com.mojang.blaze3d.platform.NativeImage(imageWidth, imageHeight, true);
      imgNew.m_85054_(img);
      img.close();
      return imgNew;
   }

   public static boolean isElytraCape(com.mojang.blaze3d.platform.NativeImage imageRaw, com.mojang.blaze3d.platform.NativeImage imageFixed) {
      return imageRaw.m_84982_() > imageFixed.m_85084_();
   }

   public static void reloadCape(net.minecraft.client.player.AbstractClientPlayer player) {
      String nameClear = player.getNameClear();
      net.minecraft.resources.ResourceLocation rl = new net.minecraft.resources.ResourceLocation("capeof/" + nameClear);
      net.minecraft.client.renderer.texture.TextureManager textureManager = Config.getTextureManager();
      if (textureManager.m_118506_(rl) instanceof net.minecraft.client.renderer.texture.SimpleTexture simpleTex) {
         simpleTex.m_117964_();
         textureManager.m_118513_(rl);
      }

      player.setLocationOfCape(null);
      player.setElytraOfCape(false);
      downloadCape(player);
   }
}
