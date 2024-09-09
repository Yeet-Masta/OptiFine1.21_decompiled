package net.optifine.render;

public class VertexBuilderDummy implements com.mojang.blaze3d.vertex.VertexConsumer {
   private net.minecraft.client.renderer.MultiBufferSource.BufferSource renderTypeBuffer = null;

   public VertexBuilderDummy(net.minecraft.client.renderer.MultiBufferSource.BufferSource renderTypeBuffer) {
      this.renderTypeBuffer = renderTypeBuffer;
   }

   @Override
   public net.minecraft.client.renderer.MultiBufferSource.BufferSource getRenderTypeBuffer() {
      return this.renderTypeBuffer;
   }

   @Override
   public com.mojang.blaze3d.vertex.VertexConsumer m_167146_(float x, float y, float z) {
      return this;
   }

   @Override
   public com.mojang.blaze3d.vertex.VertexConsumer m_167129_(int red, int green, int blue, int alpha) {
      return this;
   }

   @Override
   public com.mojang.blaze3d.vertex.VertexConsumer m_167083_(float u, float v) {
      return this;
   }

   @Override
   public com.mojang.blaze3d.vertex.VertexConsumer m_338369_(int u, int v) {
      return this;
   }

   @Override
   public com.mojang.blaze3d.vertex.VertexConsumer m_338813_(int u, int v) {
      return this;
   }

   @Override
   public com.mojang.blaze3d.vertex.VertexConsumer m_338525_(float x, float y, float z) {
      return this;
   }
}
