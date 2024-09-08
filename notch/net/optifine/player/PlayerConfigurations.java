package net.optifine.player;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3829_;
import net.minecraft.src.C_4102_;
import net.minecraft.src.C_4139_;
import net.optifine.http.FileDownloadThread;
import net.optifine.http.HttpUtils;

public class PlayerConfigurations {
   private static Map mapConfigurations = null;
   private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();

   public static void renderPlayerItems(C_3829_ modelBiped, C_4102_ player, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, int packedOverlayIn) {
      PlayerConfiguration cfg = getPlayerConfiguration(player);
      if (cfg != null) {
         cfg.renderPlayerItems(modelBiped, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
      }
   }

   public static synchronized PlayerConfiguration getPlayerConfiguration(C_4102_ player) {
      if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
         C_4102_ currentPlayer = C_3391_.m_91087_().f_91074_;
         if (currentPlayer != null) {
            setPlayerConfiguration(currentPlayer.getNameClear(), null);
            timeReloadPlayerItemsMs = System.currentTimeMillis();
         }
      }

      String name = player.getNameClear();
      if (name == null) {
         return null;
      } else {
         PlayerConfiguration pc = (PlayerConfiguration)getMapConfigurations().get(name);
         if (pc == null) {
            pc = new PlayerConfiguration();
            getMapConfigurations().put(name, pc);
            PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
            String url = HttpUtils.getPlayerItemsUrl() + "/users/" + name + ".cfg";
            FileDownloadThread fdt = new FileDownloadThread(url, pcl);
            fdt.start();
         }

         return pc;
      }
   }

   public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc) {
      getMapConfigurations().put(player, pc);
   }

   private static Map getMapConfigurations() {
      if (mapConfigurations == null) {
         mapConfigurations = new HashMap();
      }

      return mapConfigurations;
   }
}
