package net.optifine.player;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3829_;
import net.minecraft.src.C_4102_;
import net.minecraft.src.C_4139_;
import net.optifine.Config;

public class PlayerConfiguration {
   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
   private boolean initialized = false;

   public void renderPlayerItems(C_3829_ modelBiped, C_4102_ player, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, int packedOverlayIn) {
      if (this.initialized) {
         for (int i = 0; i < this.playerItemModels.length; i++) {
            PlayerItemModel model = this.playerItemModels[i];
            model.render(modelBiped, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
         }
      }
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public void setInitialized(boolean initialized) {
      this.initialized = initialized;
   }

   public PlayerItemModel[] getPlayerItemModels() {
      return this.playerItemModels;
   }

   public void addPlayerItemModel(PlayerItemModel playerItemModel) {
      this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray(this.playerItemModels, playerItemModel);
   }
}
