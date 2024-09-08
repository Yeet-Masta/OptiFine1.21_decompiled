package net.optifine.player;

import java.util.regex.Pattern;
import net.minecraft.src.C_3148_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4102_;
import net.minecraft.src.C_4468_;
import net.minecraft.src.C_4471_;
import net.minecraft.src.C_4476_;
import net.minecraft.src.C_4490_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;
import net.optifine.util.TextureUtils;

public class CapeUtils {
   private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");

   public static void downloadCape(C_4102_ player) {
      String username = player.getNameClear();
      if (username != null && !username.isEmpty() && !username.contains("\u0000") && PATTERN_USERNAME.matcher(username).matches()) {
         String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
         C_5265_ rl = new C_5265_("capeof/" + username);
         C_4490_ textureManager = C_3391_.m_91087_().m_91097_();
         C_4468_ tex = textureManager.m_174786_(rl, null);
         if (tex != null && tex instanceof C_4471_ tdid && tdid.imageFound != null) {
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
         C_5265_ locEmpty = TextureUtils.LOCATION_TEXTURE_EMPTY;
         C_4471_ textureCape = new C_4471_(null, ofCapeUrl, locEmpty, false, cib);
         textureCape.pipeline = true;
         textureManager.m_118495_(rl, textureCape);
      }
   }

   public static C_3148_ parseCape(C_3148_ img) {
      int imageWidth = 64;
      int imageHeight = 32;
      int srcWidth = img.m_84982_();

      for (int srcHeight = img.m_85084_(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2) {
         imageWidth *= 2;
      }

      C_3148_ imgNew = new C_3148_(imageWidth, imageHeight, true);
      imgNew.m_85054_(img);
      img.close();
      return imgNew;
   }

   public static boolean isElytraCape(C_3148_ imageRaw, C_3148_ imageFixed) {
      return imageRaw.m_84982_() > imageFixed.m_85084_();
   }

   public static void reloadCape(C_4102_ player) {
      String nameClear = player.getNameClear();
      C_5265_ rl = new C_5265_("capeof/" + nameClear);
      C_4490_ textureManager = Config.getTextureManager();
      if (textureManager.m_118506_(rl) instanceof C_4476_ simpleTex) {
         simpleTex.b();
         textureManager.m_118513_(rl);
      }

      player.setLocationOfCape(null);
      player.setElytraOfCape(false);
      downloadCape(player);
   }
}
