package net.minecraft.client.model;

import java.util.function.Function;
import net.optifine.EmissiveTextures;

public abstract class Model {
   protected final Function<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.RenderType> f_103106_;
   public int textureWidth = 64;
   public int textureHeight = 32;
   public net.minecraft.resources.ResourceLocation locationTextureCustom;

   public Model(Function<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.RenderType> renderTypeIn) {
      this.f_103106_ = renderTypeIn;
   }

   public final net.minecraft.client.renderer.RenderType m_103119_(net.minecraft.resources.ResourceLocation locationIn) {
      net.minecraft.client.renderer.RenderType type = (net.minecraft.client.renderer.RenderType)this.f_103106_.apply(locationIn);
      if (EmissiveTextures.isRenderEmissive() && type.isEntitySolid()) {
         type = net.minecraft.client.renderer.RenderType.m_110452_(locationIn);
      }

      return type;
   }

   public abstract void m_7695_(com.mojang.blaze3d.vertex.PoseStack var1, com.mojang.blaze3d.vertex.VertexConsumer var2, int var3, int var4, int var5);

   public final void m_340227_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn
   ) {
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, -1);
   }
}
