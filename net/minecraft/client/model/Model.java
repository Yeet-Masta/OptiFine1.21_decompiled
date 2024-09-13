package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.optifine.EmissiveTextures;

public abstract class Model {
   protected Function<ResourceLocation, RenderType> f_103106_;
   public int textureWidth = 64;
   public int textureHeight = 32;
   public ResourceLocation locationTextureCustom;

   public Model(Function<ResourceLocation, RenderType> renderTypeIn) {
      this.f_103106_ = renderTypeIn;
   }

   public RenderType m_103119_(ResourceLocation locationIn) {
      RenderType type = (RenderType)this.f_103106_.apply(locationIn);
      if (EmissiveTextures.isRenderEmissive() && type.isEntitySolid()) {
         type = RenderType.m_110452_(locationIn);
      }

      return type;
   }

   public abstract void m_7695_(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5);

   public void m_340227_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, -1);
   }
}
