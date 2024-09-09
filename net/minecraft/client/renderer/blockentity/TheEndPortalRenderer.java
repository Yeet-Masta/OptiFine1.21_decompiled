package net.minecraft.client.renderer.blockentity;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.optifine.Config;
import net.optifine.shaders.ShadersRender;
import org.joml.Matrix4f;

public class TheEndPortalRenderer<T extends TheEndPortalBlockEntity> implements net.minecraft.client.renderer.blockentity.BlockEntityRenderer<T> {
   public static final net.minecraft.resources.ResourceLocation f_112626_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "textures/environment/end_sky.png"
   );
   public static final net.minecraft.resources.ResourceLocation f_112627_ = net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/end_portal.png");

   public TheEndPortalRenderer(Context contextIn) {
   }

   public void m_6922_(
      T tileEntityIn,
      float partialTicks,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      if (!Config.isShaders()
         || !ShadersRender.renderEndPortal(tileEntityIn, partialTicks, this.m_142491_(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn)) {
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         this.m_173690_(tileEntityIn, matrix4f, bufferIn.m_6299_(this.m_142330_()));
      }
   }

   private void m_173690_(T blockEntityIn, Matrix4f matrixIn, com.mojang.blaze3d.vertex.VertexConsumer vertexConsumerIn) {
      float f = this.m_142489_();
      float f1 = this.m_142491_();
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, net.minecraft.core.Direction.SOUTH);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, net.minecraft.core.Direction.NORTH);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, net.minecraft.core.Direction.EAST);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, net.minecraft.core.Direction.WEST);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, net.minecraft.core.Direction.DOWN);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, f1, f1, 1.0F, 1.0F, 0.0F, 0.0F, net.minecraft.core.Direction.UP);
   }

   private void m_252771_(
      T blockEntityIn,
      Matrix4f matrixIn,
      com.mojang.blaze3d.vertex.VertexConsumer vertexConsumerIn,
      float x1,
      float x2,
      float y1,
      float y2,
      float z1,
      float z2,
      float z3,
      float z4,
      net.minecraft.core.Direction p_252771_12_
   ) {
      if (blockEntityIn.m_6665_(p_252771_12_)) {
         vertexConsumerIn.m_339083_(matrixIn, x1, y1, z1);
         vertexConsumerIn.m_339083_(matrixIn, x2, y1, z2);
         vertexConsumerIn.m_339083_(matrixIn, x2, y2, z3);
         vertexConsumerIn.m_339083_(matrixIn, x1, y2, z4);
      }
   }

   protected float m_142491_() {
      return 0.75F;
   }

   protected float m_142489_() {
      return 0.375F;
   }

   protected net.minecraft.client.renderer.RenderType m_142330_() {
      return net.minecraft.client.renderer.RenderType.m_173239_();
   }
}
