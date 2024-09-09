package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.optifine.SmartAnimations;
import net.optifine.render.VertexBuilderWrapper;

public class SpriteCoordinateExpander extends VertexBuilderWrapper implements VertexConsumer {
   private final VertexConsumer f_110795_;
   private final TextureAtlasSprite f_110796_;

   public SpriteCoordinateExpander(VertexConsumer bufferIn, TextureAtlasSprite spriteIn) {
      super(bufferIn);
      if (SmartAnimations.isActive()) {
         SmartAnimations.spriteRendered(spriteIn);
      }

      this.f_110795_ = bufferIn;
      this.f_110796_ = spriteIn;
   }

   public VertexConsumer m_167146_(float x, float y, float z) {
      this.f_110795_.m_167146_(x, y, z);
      return this;
   }

   public VertexConsumer m_167129_(int red, int green, int blue, int alpha) {
      this.f_110795_.m_167129_(red, green, blue, alpha);
      return this;
   }

   public VertexConsumer m_167083_(float u, float v) {
      this.f_110795_.m_167083_(this.f_110796_.m_118367_(u), this.f_110796_.m_118393_(v));
      return this;
   }

   public VertexConsumer m_338369_(int u, int v) {
      this.f_110795_.m_338369_(u, v);
      return this;
   }

   public VertexConsumer m_338813_(int u, int v) {
      this.f_110795_.m_338813_(u, v);
      return this;
   }

   public VertexConsumer m_338525_(float x, float y, float z) {
      this.f_110795_.m_338525_(x, y, z);
      return this;
   }

   public void m_338367_(float x, float y, float z, int argb, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
      if (this.f_110795_.isMultiTexture()) {
         this.f_110795_.putSprite(this.f_110796_);
         this.f_110795_.m_338367_(x, y, z, argb, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
      } else {
         this.f_110795_.m_338367_(x, y, z, argb, this.f_110796_.m_118367_(texU), this.f_110796_.m_118393_(texV), overlayUV, lightmapUV, normalX, normalY, normalZ);
      }
   }

   public boolean canAddVertexFast() {
      return this.f_110795_.canAddVertexFast();
   }

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
