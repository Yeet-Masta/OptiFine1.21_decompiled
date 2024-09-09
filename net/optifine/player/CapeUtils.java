package net.optifine.player;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.File;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.optifine.Config;
import net.optifine.util.TextureUtils;

public class CapeUtils {
   private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");

   public static void downloadCape(AbstractClientPlayer player) {
      String username = player.getNameClear();
      if (username != null && !username.isEmpty() && !username.contains("\u0000") && PATTERN_USERNAME.matcher(username).matches()) {
         String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
         ResourceLocation rl = new ResourceLocation("capeof/" + username);
         TextureManager textureManager = Minecraft.m_91087_().m_91097_();
         AbstractTexture tex = textureManager.m_174786_(rl, (AbstractTexture)null);
         if (tex != null && tex instanceof HttpTexture) {
            HttpTexture tdid = (HttpTexture)tex;
            if (tdid.imageFound != null) {
               if (tdid.imageFound) {
                  player.setLocationOfCape(rl);
                  if (tdid.getProcessTask() instanceof CapeImageBuffer) {
                     CapeImageBuffer cib = (CapeImageBuffer)tdid.getProcessTask();
                     player.setElytraOfCape(cib.isElytraOfCape());
                  }
               }

               return;
            }
         }

         CapeImageBuffer cib = new CapeImageBuffer(player, rl);
         ResourceLocation locEmpty = TextureUtils.LOCATION_TEXTURE_EMPTY;
         HttpTexture textureCape = new HttpTexture((File)null, ofCapeUrl, locEmpty, false, cib);
         textureCape.pipeline = true;
         textureManager.m_118495_(rl, textureCape);
      }

   }

   public static NativeImage parseCape(NativeImage img) {
      int imageWidth = 64;
      int imageHeight = 32;
      int srcWidth = img.m_84982_();

      for(int srcHeight = img.m_85084_(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2) {
         imageWidth *= 2;
      }

      NativeImage imgNew = new NativeImage(imageWidth, imageHeight, true);
      imgNew.m_85054_(img);
      img.close();
      return imgNew;
   }

   public static boolean isElytraCape(NativeImage imageRaw, NativeImage imageFixed) {
      return imageRaw.m_84982_() > imageFixed.m_85084_();
   }

   public static void reloadCape(AbstractClientPlayer player) {
      String nameClear = player.getNameClear();
      ResourceLocation rl = new ResourceLocation("capeof/" + nameClear);
      TextureManager textureManager = Config.getTextureManager();
      AbstractTexture tex = textureManager.m_118506_(rl);
      if (tex instanceof SimpleTexture simpleTex) {
         simpleTex.m_117964_();
         textureManager.m_118513_(rl);
      }

      player.setLocationOfCape((ResourceLocation)null);
      player.setElytraOfCape(false);
      downloadCape(player);
   }
}
