package com.mojang.blaze3d.vertex;

import java.util.function.Consumer;
import net.optifine.render.VertexBuilderWrapper;

public class VertexMultiConsumer {
   public static VertexConsumer m_167060_() {
      throw new IllegalArgumentException();
   }

   public static VertexConsumer m_167061_(VertexConsumer vertexConsumerIn) {
      return vertexConsumerIn;
   }

   public static VertexConsumer m_86168_(VertexConsumer consumer1, VertexConsumer consumer2) {
      return new VertexMultiConsumer.Double(consumer1, consumer2);
   }

   public static VertexConsumer m_167063_(VertexConsumer... vertexConsumersIn) {
      return new VertexMultiConsumer.Multiple(vertexConsumersIn);
   }

   static class Double extends VertexBuilderWrapper implements VertexConsumer {
      private VertexConsumer f_86171_;
      private VertexConsumer f_86172_;
      private boolean fixMultitextureUV;

      public Double(VertexConsumer firstIn, VertexConsumer secondIn) {
         super(secondIn);
         if (firstIn == secondIn) {
            throw new IllegalArgumentException("Duplicate delegates");
         } else {
            this.f_86171_ = firstIn;
            this.f_86172_ = secondIn;
            this.updateFixMultitextureUv();
         }
      }

      @Override
      public VertexConsumer m_167146_(float x, float y, float z) {
         this.f_86171_.m_167146_(x, y, z);
         this.f_86172_.m_167146_(x, y, z);
         return this;
      }

      @Override
      public VertexConsumer m_167129_(int red, int green, int blue, int alpha) {
         this.f_86171_.m_167129_(red, green, blue, alpha);
         this.f_86172_.m_167129_(red, green, blue, alpha);
         return this;
      }

      @Override
      public VertexConsumer m_167083_(float u, float v) {
         this.f_86171_.m_167083_(u, v);
         this.f_86172_.m_167083_(u, v);
         return this;
      }

      @Override
      public VertexConsumer m_338369_(int u, int v) {
         this.f_86171_.m_338369_(u, v);
         this.f_86172_.m_338369_(u, v);
         return this;
      }

      @Override
      public VertexConsumer m_338813_(int u, int v) {
         this.f_86171_.m_338813_(u, v);
         this.f_86172_.m_338813_(u, v);
         return this;
      }

      @Override
      public VertexConsumer m_338525_(float x, float y, float z) {
         this.f_86171_.m_338525_(x, y, z);
         this.f_86172_.m_338525_(x, y, z);
         return this;
      }

      @Override
      public void m_338367_(
         float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ
      ) {
         if (this.fixMultitextureUV) {
            this.f_86171_.m_338367_(x, y, z, argb, texU / 32.0F, texV / 32.0F, overlayUV, lightmapUV, normalX, normalY, normalZ);
         } else {
            this.f_86171_.m_338367_(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
         }

         this.f_86172_.m_338367_(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
      }

      private void updateFixMultitextureUv() {
         this.fixMultitextureUV = !this.f_86171_.isMultiTexture() && this.f_86172_.isMultiTexture();
      }

      @Override
      public VertexConsumer getSecondaryBuilder() {
         return this.f_86171_;
      }
   }

   static class Multiple extends VertexBuilderWrapper implements VertexConsumer {
      private VertexConsumer[] f_167071_;

      Multiple(VertexConsumer[] delegates) {
         super(delegates.length > 0 ? delegates[0] : null);
         this.f_167071_ = delegates;

         for (int i = 0; i < delegates.length; i++) {
            for (int j = i + 1; j < delegates.length; j++) {
               if (delegates[i] == delegates[j]) {
                  throw new IllegalArgumentException("Duplicate delegates");
               }
            }
         }

         this.f_167071_ = delegates;
      }

      private void m_167144_(Consumer<VertexConsumer> consumerIn) {
         for (VertexConsumer vertexconsumer : this.f_167071_) {
            consumerIn.m_340568_(vertexconsumer);
         }
      }

      @Override
      public VertexConsumer m_167146_(float x, float y, float z) {
         this.m_167144_(consumerIn -> consumerIn.m_167146_(x, y, z));
         return this;
      }

      @Override
      public VertexConsumer m_167129_(int red, int green, int blue, int alpha) {
         this.m_167144_(consumerIn -> consumerIn.m_167129_(red, green, blue, alpha));
         return this;
      }

      @Override
      public VertexConsumer m_167083_(float u, float v) {
         this.m_167144_(consumerIn -> consumerIn.m_167083_(u, v));
         return this;
      }

      @Override
      public VertexConsumer m_338369_(int u, int v) {
         this.m_167144_(consumerIn -> consumerIn.m_338369_(u, v));
         return this;
      }

      @Override
      public VertexConsumer m_338813_(int u, int v) {
         this.m_167144_(consumerIn -> consumerIn.m_338813_(u, v));
         return this;
      }

      @Override
      public VertexConsumer m_338525_(float x, float y, float z) {
         this.m_167144_(consumerIn -> consumerIn.m_338525_(x, y, z));
         return this;
      }

      @Override
      public void m_338367_(
         float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ
      ) {
         this.m_167144_(consumerIn -> consumerIn.m_338367_(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ));
      }
   }
}
