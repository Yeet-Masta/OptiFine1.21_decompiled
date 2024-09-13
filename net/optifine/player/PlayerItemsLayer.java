package net.optifine.player;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.optifine.Config;

public class PlayerItemsLayer extends RenderLayer {
   private PlayerRenderer renderPlayer = null;

   public PlayerItemsLayer(PlayerRenderer renderPlayer) {
      super(renderPlayer);
      this.renderPlayer = renderPlayer;
   }

   @Override
   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Entity entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      this.renderEquippedItems(entitylivingbaseIn, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_);
   }

   protected void renderEquippedItems(Entity entityLiving, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, int packedOverlayIn) {
      if (Config.isShowCapes()) {
         if (!entityLiving.m_20145_()) {
            if (entityLiving instanceof AbstractClientPlayer player) {
               HumanoidModel modelBipedMain = (HumanoidModel)this.renderPlayer.m_7200_();
               PlayerConfigurations.renderPlayerItems(modelBipedMain, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
         }
      }
   }
}
