package net.minecraft.client.resources.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.optifine.render.RenderUtils;
import net.optifine.util.TextureUtils;

public class Material {
   public static final Comparator<Material> f_244523_ = Comparator.comparing(Material::m_119193_).thenComparing(Material::m_119203_);
   private final ResourceLocation f_119187_;
   private final ResourceLocation f_119188_;
   @Nullable
   private RenderType f_119189_;

   public Material(ResourceLocation atlasLocationIn, ResourceLocation textureLocationIn) {
      this.f_119187_ = atlasLocationIn;
      this.f_119188_ = textureLocationIn;
   }

   public ResourceLocation m_119193_() {
      return this.f_119187_;
   }

   public ResourceLocation m_119203_() {
      return this.f_119188_;
   }

   public TextureAtlasSprite m_119204_() {
      TextureAtlasSprite sprite = (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(this.m_119193_()).apply(this.m_119203_());
      return TextureUtils.getCustomSprite(sprite);
   }

   public RenderType m_119201_(Function<ResourceLocation, RenderType> renderTypeGetter) {
      if (this.f_119189_ == null) {
         this.f_119189_ = (RenderType)renderTypeGetter.apply(this.f_119187_);
      }

      return this.f_119189_;
   }

   public VertexConsumer m_119194_(MultiBufferSource bufferIn, Function<ResourceLocation, RenderType> renderTypeGetter) {
      TextureAtlasSprite sprite = this.m_119204_();
      RenderType renderType = this.m_119201_(renderTypeGetter);
      if (sprite.isSpriteEmissive && renderType.isEntitySolid()) {
         RenderUtils.flushRenderBuffers();
         renderType = RenderType.m_110452_(this.f_119187_);
      }

      return sprite.m_118381_(bufferIn.m_6299_(renderType));
   }

   public VertexConsumer m_119197_(MultiBufferSource bufferIn, Function<ResourceLocation, RenderType> renderTypeGetter, boolean hasEffectIn) {
      return this.m_119204_().m_118381_(ItemRenderer.m_115222_(bufferIn, this.m_119201_(renderTypeGetter), true, hasEffectIn));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         Material material = (Material)p_equals_1_;
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
