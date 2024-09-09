package net.optifine.player;

import net.minecraft.client.model.HumanoidModel;
import net.optifine.Config;

public class PlayerConfiguration {
   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
   private boolean initialized = false;

   public void renderPlayerItems(
      HumanoidModel modelBiped,
      net.minecraft.client.player.AbstractClientPlayer player,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      int packedOverlayIn
   ) {
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
