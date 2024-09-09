package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;

public class SaddleLayer extends RenderLayer {
   public ResourceLocation f_117387_;
   public EntityModel f_117388_;

   public SaddleLayer(RenderLayerParent p_i117389_1_, EntityModel p_i117389_2_, ResourceLocation p_i117389_3_) {
      super(p_i117389_1_);
      this.f_117388_ = p_i117389_2_;
      this.f_117387_ = p_i117389_3_;
   }

   public void m_6494_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Entity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (((Saddleable)entitylivingbaseIn).m_6254_()) {
         this.m_117386_().m_102624_(this.f_117388_);
         this.f_117388_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.f_117388_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_110458_(this.f_117387_));
         this.f_117388_.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
      }

   }
}
