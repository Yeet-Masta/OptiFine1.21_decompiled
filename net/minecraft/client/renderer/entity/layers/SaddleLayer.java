package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;

public class SaddleLayer<T extends Entity & Saddleable, M extends EntityModel<T>> extends net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> {
   public net.minecraft.resources.ResourceLocation f_117387_;
   public M f_117388_;

   public SaddleLayer(RenderLayerParent<T, M> p_i117389_1_, M p_i117389_2_, net.minecraft.resources.ResourceLocation p_i117389_3_) {
      super(p_i117389_1_);
      this.f_117388_ = p_i117389_2_;
      this.f_117387_ = p_i117389_3_;
   }

   @Override
   public void m_6494_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entitylivingbaseIn.m_6254_()) {
         this.m_117386_().m_102624_(this.f_117388_);
         this.f_117388_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.f_117388_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_110458_(this.f_117387_));
         this.f_117388_.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
      }
   }
}
