package net.optifine.player;

import net.minecraft.client.model.HumanoidModel;

public class PlayerItemRenderer {
   private int attachTo = 0;
   private net.minecraft.client.model.geom.ModelPart modelRenderer = null;

   public PlayerItemRenderer(int attachTo, net.minecraft.client.model.geom.ModelPart modelRenderer) {
      this.attachTo = attachTo;
      this.modelRenderer = modelRenderer;
   }

   public net.minecraft.client.model.geom.ModelPart getModelRenderer() {
      return this.modelRenderer;
   }

   public void render(
      HumanoidModel modelBiped,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      com.mojang.blaze3d.vertex.VertexConsumer bufferIn,
      int packedLightIn,
      int packedOverlayIn
   ) {
      net.minecraft.client.model.geom.ModelPart attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
      if (attachModel != null) {
         attachModel.m_104299_(matrixStackIn);
      }

      this.modelRenderer.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
   }
}
