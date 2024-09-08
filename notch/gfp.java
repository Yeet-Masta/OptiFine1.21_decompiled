package net.minecraft.src;

import net.optifine.SmartAnimations;
import net.optifine.render.VertexBuilderWrapper;

public class C_4179_ extends VertexBuilderWrapper implements C_3187_ {
   private final C_3187_ f_110795_;
   private final C_4486_ f_110796_;

   public C_4179_(C_3187_ bufferIn, C_4486_ spriteIn) {
      super(bufferIn);
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(spriteIn);
      }

      this.f_110795_ = bufferIn;
      this.f_110796_ = spriteIn;
   }

   @Override
   public C_3187_ m_167146_(float x, float y, float z) {
      this.f_110795_.m_167146_(x, y, z);
      return this;
   }

   @Override
   public C_3187_ m_167129_(int red, int green, int blue, int alpha) {
      this.f_110795_.m_167129_(red, green, blue, alpha);
      return this;
   }

   @Override
   public C_3187_ m_167083_(float u, float v) {
      this.f_110795_.m_167083_(this.f_110796_.m_118367_(u), this.f_110796_.m_118393_(v));
      return this;
   }

   @Override
   public C_3187_ m_338369_(int u, int v) {
      this.f_110795_.m_338369_(u, v);
      return this;
   }

   @Override
   public C_3187_ m_338813_(int u, int v) {
      this.f_110795_.m_338813_(u, v);
      return this;
   }

   @Override
   public C_3187_ m_338525_(float x, float y, float z) {
      this.f_110795_.m_338525_(x, y, z);
      return this;
   }

   @Override
   public void m_338367_(
      float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ
   ) {
      if (this.f_110795_.isMultiTexture()) {
         this.f_110795_.putSprite(this.f_110796_);
         this.f_110795_.m_338367_(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
      } else {
         this.f_110795_
            .m_338367_(x, y, z, argb, this.f_110796_.m_118367_(texU), this.f_110796_.m_118393_(texV), overlayUV, lightmapUV, normalX, normalY, normalZ);
      }
   }

   @Override
   public boolean canAddVertexFast() {
      return this.f_110795_.canAddVertexFast();
   }

   @Override
   public void addVertexFast(float x, float y, float z, int color, float texU, float texV, int overlayUV, int lightmapUV, int normals) {
      if (this.f_110795_.isMultiTexture()) {
         this.f_110795_.putSprite(this.f_110796_);
         this.f_110795_.addVertexFast(x, y, z, color, texU, texV, overlayUV, lightmapUV, normals);
      } else {
         float tu = this.f_110796_.m_118367_(texU);
         float tv = this.f_110796_.m_118393_(texV);
         this.f_110795_.addVertexFast(x, y, z, color, tu, tv, overlayUV, lightmapUV, normals);
      }
   }
}
