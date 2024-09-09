package net.minecraft.client.renderer.entity;

import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.optifine.entity.model.ArrowModel;

public abstract class ArrowRenderer<T extends AbstractArrow> extends net.minecraft.client.renderer.entity.EntityRenderer<T> {
   public ArrowModel model;

   public ArrowRenderer(Context p_i173916_1_) {
      super(p_i173916_1_);
   }

   public void m_7392_(
      T entityIn,
      float entityYaw,
      float partialTicks,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(net.minecraft.util.Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(net.minecraft.util.Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      int i = 0;
      float f = 0.0F;
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = 0.15625F;
      float f4 = 0.0F;
      float f5 = 0.15625F;
      float f6 = 0.15625F;
      float f7 = 0.3125F;
      float f8 = 0.05625F;
      float f9 = (float)entityIn.f_36706_ - partialTicks;
      if (f9 > 0.0F) {
         float f10 = -net.minecraft.util.Mth.m_14031_(f9 * 3.0F) * f9;
         matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(f10));
      }

      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(45.0F));
      matrixStackIn.m_85841_(0.05625F, 0.05625F, 0.05625F);
      matrixStackIn.m_252880_(-4.0F, 0.0F, 0.0F);
      net.minecraft.client.renderer.RenderType renderType = net.minecraft.client.renderer.RenderType.m_110452_(this.m_5478_(entityIn));
      if (this.model != null) {
         renderType = this.model.m_103119_(this.m_5478_(entityIn));
      }

      com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(renderType);
      com.mojang.blaze3d.vertex.PoseStack.Pose posestack$pose = matrixStackIn.m_85850_();
      if (this.model != null) {
         matrixStackIn.m_85841_(16.0F, 16.0F, 16.0F);
         this.model.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, -1);
      } else {
         this.m_253099_(posestack$pose, vertexconsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
         this.m_253099_(posestack$pose, vertexconsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
         this.m_253099_(posestack$pose, vertexconsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
         this.m_253099_(posestack$pose, vertexconsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
         this.m_253099_(posestack$pose, vertexconsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
         this.m_253099_(posestack$pose, vertexconsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
         this.m_253099_(posestack$pose, vertexconsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
         this.m_253099_(posestack$pose, vertexconsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

         for (int j = 0; j < 4; j++) {
            matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(90.0F));
            this.m_253099_(posestack$pose, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
            this.m_253099_(posestack$pose, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
            this.m_253099_(posestack$pose, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
            this.m_253099_(posestack$pose, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
         }
      }

      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public void m_253099_(
      com.mojang.blaze3d.vertex.PoseStack.Pose matrixStack,
      com.mojang.blaze3d.vertex.VertexConsumer vertexBuilder,
      int offsetX,
      int offsetY,
      int offsetZ,
      float textureX,
      float textureY,
      int normalX,
      int normalZ,
      int normalY,
      int packedLightIn
   ) {
      vertexBuilder.m_338370_(matrixStack, (float)offsetX, (float)offsetY, (float)offsetZ)
         .m_338399_(-1)
         .m_167083_(textureX, textureY)
         .m_338943_(OverlayTexture.f_118083_)
         .m_338973_(packedLightIn)
         .m_339200_(matrixStack, (float)normalX, (float)normalY, (float)normalZ);
   }
}
