package net.optifine.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;

public class VertexBuilderDummy implements VertexConsumer {
   private MultiBufferSource.BufferSource renderTypeBuffer = null;

   public VertexBuilderDummy(MultiBufferSource.BufferSource renderTypeBuffer) {
      this.renderTypeBuffer = renderTypeBuffer;
   }

   public MultiBufferSource.BufferSource getRenderTypeBuffer() {
      return this.renderTypeBuffer;
   }

   public VertexConsumer m_167146_(float x, float y, float z) {
      return this;
   }

   public VertexConsumer m_167129_(int red, int green, int blue, int alpha) {
      return this;
   }

   public VertexConsumer m_167083_(float u, float v) {
      return this;
   }

   public VertexConsumer m_338369_(int u, int v) {
      return this;
   }

   public VertexConsumer m_338813_(int u, int v) {
      return this;
   }

   public VertexConsumer m_338525_(float x, float y, float z) {
      return this;
   }
}
