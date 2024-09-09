package net.optifine.entity.model;

public class ArrowModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart body;

   public ArrowModel(net.minecraft.client.model.geom.ModelPart body) {
      super(net.minecraft.client.renderer.RenderType::m_110458_);
      this.body = body;
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
      this.body.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
   }
}
