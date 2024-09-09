package net.optifine.player;

import java.util.function.Function;

public class ModelPlayerItem extends net.minecraft.client.model.Model {
   public ModelPlayerItem(Function<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.RenderType> renderTypeIn) {
      super(renderTypeIn);
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
