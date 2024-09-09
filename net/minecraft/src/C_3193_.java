package net.minecraft.src;

import java.util.function.Consumer;
import net.optifine.render.VertexBuilderWrapper;

public class C_3193_ {
   public static C_3187_ m_167060_() {
      throw new IllegalArgumentException();
   }

   public static C_3187_ m_167061_(C_3187_ vertexConsumerIn) {
      return vertexConsumerIn;
   }

   public static C_3187_ m_86168_(C_3187_ consumer1, C_3187_ consumer2) {
      return new C_3194_(consumer1, consumer2);
   }

   public static C_3187_ m_167063_(C_3187_... vertexConsumersIn) {
      return new C_141551_(vertexConsumersIn);
   }

   static class C_3194_ extends VertexBuilderWrapper implements C_3187_ {
      private final C_3187_ f_86171_;
      private final C_3187_ f_86172_;
      private boolean fixMultitextureUV;

      public C_3194_(C_3187_ firstIn, C_3187_ secondIn) {
         super(secondIn);
         if (firstIn == secondIn) {
            throw new IllegalArgumentException("Duplicate delegates");
         } else {
            this.f_86171_ = firstIn;
            this.f_86172_ = secondIn;
            this.updateFixMultitextureUv();
         }
      }

      public C_3187_ m_167146_(float x, float y, float z) {
         this.f_86171_.m_167146_(x, y, z);
         this.f_86172_.m_167146_(x, y, z);
         return this;
      }

      public C_3187_ m_167129_(int red, int green, int blue, int alpha) {
         this.f_86171_.m_167129_(red, green, blue, alpha);
         this.f_86172_.m_167129_(red, green, blue, alpha);
         return this;
      }

      public C_3187_ m_167083_(float u, float v) {
         this.f_86171_.m_167083_(u, v);
         this.f_86172_.m_167083_(u, v);
         return this;
      }

      public C_3187_ m_338369_(int u, int v) {
         this.f_86171_.m_338369_(u, v);
         this.f_86172_.m_338369_(u, v);
         return this;
      }

      public C_3187_ m_338813_(int u, int v) {
         this.f_86171_.m_338813_(u, v);
         this.f_86172_.m_338813_(u, v);
         return this;
      }

      public C_3187_ m_338525_(float x, float y, float z) {
         this.f_86171_.m_338525_(x, y, z);
         this.f_86172_.m_338525_(x, y, z);
         return this;
      }

      public void m_338367_(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
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

      public C_3187_ getSecondaryBuilder() {
         return this.f_86171_;
      }
   }

   static class C_141551_ extends VertexBuilderWrapper implements C_3187_ {
      private C_3187_[] f_167071_;

      C_141551_(C_3187_[] delegates) {
         super(delegates.length > 0 ? delegates[0] : null);
         this.f_167071_ = delegates;

         for(int i = 0; i < delegates.length; ++i) {
            for(int j = i + 1; j < delegates.length; ++j) {
               if (delegates[i] == delegates[j]) {
                  throw new IllegalArgumentException("Duplicate delegates");
               }
            }
         }

         this.f_167071_ = delegates;
      }

      private void m_167144_(Consumer consumerIn) {
         C_3187_[] var2 = this.f_167071_;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            C_3187_ vertexconsumer = var2[var4];
            consumerIn.accept(vertexconsumer);
         }

      }

      public C_3187_ m_167146_(float x, float y, float z) {
         this.m_167144_((consumerIn) -> {
            consumerIn.m_167146_(x, y, z);
         });
         return this;
      }

      public C_3187_ m_167129_(int red, int green, int blue, int alpha) {
         this.m_167144_((consumerIn) -> {
            consumerIn.m_167129_(red, green, blue, alpha);
         });
         return this;
      }

      public C_3187_ m_167083_(float u, float v) {
         this.m_167144_((consumerIn) -> {
            consumerIn.m_167083_(u, v);
         });
         return this;
      }

      public C_3187_ m_338369_(int u, int v) {
         this.m_167144_((consumerIn) -> {
            consumerIn.m_338369_(u, v);
         });
         return this;
      }

      public C_3187_ m_338813_(int u, int v) {
         this.m_167144_((consumerIn) -> {
            consumerIn.m_338813_(u, v);
         });
         return this;
      }

      public C_3187_ m_338525_(float x, float y, float z) {
         this.m_167144_((consumerIn) -> {
            consumerIn.m_338525_(x, y, z);
         });
         return this;
      }

      public void m_338367_(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
         this.m_167144_((consumerIn) -> {
            consumerIn.m_338367_(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
         });
      }
   }
}
