package net.minecraft.client.resources.model;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.optifine.render.RenderUtils;
import net.optifine.util.TextureUtils;

public class Material {
   public static final Comparator<net.minecraft.client.resources.model.Material> f_244523_ = Comparator.comparing(
         net.minecraft.client.resources.model.Material::m_119193_
      )
      .thenComparing(net.minecraft.client.resources.model.Material::m_119203_);
   private final net.minecraft.resources.ResourceLocation f_119187_;
   private final net.minecraft.resources.ResourceLocation f_119188_;
   @Nullable
   private net.minecraft.client.renderer.RenderType f_119189_;

   public Material(net.minecraft.resources.ResourceLocation atlasLocationIn, net.minecraft.resources.ResourceLocation textureLocationIn) {
      this.f_119187_ = atlasLocationIn;
      this.f_119188_ = textureLocationIn;
   }

   public net.minecraft.resources.ResourceLocation m_119193_() {
      return this.f_119187_;
   }

   public net.minecraft.resources.ResourceLocation m_119203_() {
      return this.f_119188_;
   }

   public net.minecraft.client.renderer.texture.TextureAtlasSprite m_119204_() {
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = (net.minecraft.client.renderer.texture.TextureAtlasSprite)Minecraft.m_91087_()
         .m_91258_(this.m_119193_())
         .apply(this.m_119203_());
      return TextureUtils.getCustomSprite(sprite);
   }

   public net.minecraft.client.renderer.RenderType m_119201_(
      Function<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.RenderType> renderTypeGetter
   ) {
      if (this.f_119189_ == null) {
         this.f_119189_ = (net.minecraft.client.renderer.RenderType)renderTypeGetter.apply(this.f_119187_);
      }

      return this.f_119189_;
   }

   public com.mojang.blaze3d.vertex.VertexConsumer m_119194_(
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      Function<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.RenderType> renderTypeGetter
   ) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = this.m_119204_();
      net.minecraft.client.renderer.RenderType renderType = this.m_119201_(renderTypeGetter);
      if (sprite.isSpriteEmissive && renderType.isEntitySolid()) {
         RenderUtils.flushRenderBuffers();
         renderType = net.minecraft.client.renderer.RenderType.m_110452_(this.f_119187_);
      }

      return sprite.m_118381_(bufferIn.m_6299_(renderType));
   }

   public com.mojang.blaze3d.vertex.VertexConsumer m_119197_(
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      Function<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.RenderType> renderTypeGetter,
      boolean hasEffectIn
   ) {
      return this.m_119204_()
         .m_118381_(net.minecraft.client.renderer.entity.ItemRenderer.m_115222_(bufferIn, this.m_119201_(renderTypeGetter), true, hasEffectIn));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         net.minecraft.client.resources.model.Material material = (net.minecraft.client.resources.model.Material)p_equals_1_;
         return this.f_119187_.equals(material.f_119187_) && this.f_119188_.equals(material.f_119188_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.f_119187_, this.f_119188_});
   }

   public String toString() {
      return "Material{atlasLocation=" + this.f_119187_ + ", texture=" + this.f_119188_ + "}";
   }
}
