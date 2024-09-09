package net.minecraft.src;

import net.optifine.Config;
import net.optifine.shaders.ShadersRender;
import org.joml.Matrix4f;

public class C_4260_ implements C_4244_ {
   public static final C_5265_ f_112626_ = C_5265_.m_340282_("textures/environment/end_sky.png");
   public static final C_5265_ f_112627_ = C_5265_.m_340282_("textures/entity/end_portal.png");

   public C_4260_(C_141731_.C_141732_ contextIn) {
   }

   public void m_6922_(C_2033_ tileEntityIn, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn) {
      if (!Config.isShaders() || !ShadersRender.renderEndPortal(tileEntityIn, partialTicks, this.m_142491_(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn)) {
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         this.m_173690_(tileEntityIn, matrix4f, bufferIn.m_6299_(this.m_142330_()));
      }
   }

   private void m_173690_(C_2033_ blockEntityIn, Matrix4f matrixIn, C_3187_ vertexConsumerIn) {
      float f = this.m_142489_();
      float f1 = this.m_142491_();
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, C_4687_.SOUTH);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, C_4687_.NORTH);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, C_4687_.EAST);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, C_4687_.WEST);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, C_4687_.DOWN);
      this.m_252771_(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, f1, f1, 1.0F, 1.0F, 0.0F, 0.0F, C_4687_.field_50);
   }

   private void m_252771_(C_2033_ blockEntityIn, Matrix4f matrixIn, C_3187_ vertexConsumerIn, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, C_4687_ p_252771_12_) {
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

   protected C_4168_ m_142330_() {
      return C_4168_.m_173239_();
   }
}
