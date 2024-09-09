package net.minecraft.src;

import net.optifine.util.MathUtils;
import org.joml.Matrix4f;

public class C_3516_ {
   private final C_283726_ f_283799_;
   private final float f_95201_;
   private final float f_95202_;
   private final float f_95203_;
   private final float f_95204_;
   private final float f_95205_;
   private final float f_95206_;
   private final float f_95207_;
   private final float f_95208_;
   public static final Matrix4f MATRIX_IDENTITY = MathUtils.makeMatrixIdentity();

   public C_3516_(C_283726_ typesIn, float u0In, float u1In, float v0In, float v1In, float minXIn, float maxXIn, float minYIn, float maxYIn) {
      this.f_283799_ = typesIn;
      this.f_95201_ = u0In;
      this.f_95202_ = u1In;
      this.f_95203_ = v0In;
      this.f_95204_ = v1In;
      this.f_95205_ = minXIn;
      this.f_95206_ = maxXIn;
      this.f_95207_ = minYIn;
      this.f_95208_ = maxYIn;
   }

   public void m_5626_(boolean italicIn, float xIn, float yIn, Matrix4f matrixIn, C_3187_ bufferIn, float redIn, float greenIn, float blueIn, float alphaIn, int packedLight) {
      float f = xIn + this.f_95205_;
      float f1 = xIn + this.f_95206_;
      float f2 = yIn + this.f_95207_;
      float f3 = yIn + this.f_95208_;
      float f4 = italicIn ? 1.0F - 0.25F * this.f_95207_ : 0.0F;
      float f5 = italicIn ? 1.0F - 0.25F * this.f_95208_ : 0.0F;
      if (bufferIn instanceof C_3173_ bb && ((C_3173_)bufferIn).canAddVertexText()) {
         int r = (int)(redIn * 255.0F);
         int g = (int)(greenIn * 255.0F);
         int b = (int)(blueIn * 255.0F);
         int a = (int)(alphaIn * 255.0F);
         int col = a << 24 | b << 16 | g << 8 | r;
         Matrix4f mat4 = matrixIn == MATRIX_IDENTITY ? null : matrixIn;
         bb.addVertexText(mat4, f + f4, f2, 0.0F, col, this.f_95201_, this.f_95203_, packedLight);
         bb.addVertexText(mat4, f + f5, f3, 0.0F, col, this.f_95201_, this.f_95204_, packedLight);
         bb.addVertexText(mat4, f1 + f5, f3, 0.0F, col, this.f_95202_, this.f_95204_, packedLight);
         bb.addVertexText(mat4, f1 + f4, f2, 0.0F, col, this.f_95202_, this.f_95203_, packedLight);
      } else {
         bufferIn.m_339083_(matrixIn, f + f4, f2, 0.0F).m_340057_(redIn, greenIn, blueIn, alphaIn).m_167083_(this.f_95201_, this.f_95203_).m_338973_(packedLight);
         bufferIn.m_339083_(matrixIn, f + f5, f3, 0.0F).m_340057_(redIn, greenIn, blueIn, alphaIn).m_167083_(this.f_95201_, this.f_95204_).m_338973_(packedLight);
         bufferIn.m_339083_(matrixIn, f1 + f5, f3, 0.0F).m_340057_(redIn, greenIn, blueIn, alphaIn).m_167083_(this.f_95202_, this.f_95204_).m_338973_(packedLight);
         bufferIn.m_339083_(matrixIn, f1 + f4, f2, 0.0F).m_340057_(redIn, greenIn, blueIn, alphaIn).m_167083_(this.f_95202_, this.f_95203_).m_338973_(packedLight);
      }

   }

   public void m_95220_(C_3517_ effectIn, Matrix4f matrixIn, C_3187_ bufferIn, int packedLightIn) {
      if (bufferIn instanceof C_3173_ bb && ((C_3173_)bufferIn).canAddVertexText()) {
         int r = (int)(effectIn.f_95242_ * 255.0F);
         int g = (int)(effectIn.f_95243_ * 255.0F);
         int b = (int)(effectIn.f_95244_ * 255.0F);
         int a = (int)(effectIn.f_95245_ * 255.0F);
         int col = a << 24 | b << 16 | g << 8 | r;
         Matrix4f mat4 = matrixIn == MATRIX_IDENTITY ? null : matrixIn;
         bb.addVertexText(mat4, effectIn.f_95237_, effectIn.f_95238_, effectIn.f_95241_, col, this.f_95201_, this.f_95203_, packedLightIn);
         bb.addVertexText(mat4, effectIn.f_95239_, effectIn.f_95238_, effectIn.f_95241_, col, this.f_95201_, this.f_95204_, packedLightIn);
         bb.addVertexText(mat4, effectIn.f_95239_, effectIn.f_95240_, effectIn.f_95241_, col, this.f_95202_, this.f_95204_, packedLightIn);
         bb.addVertexText(mat4, effectIn.f_95237_, effectIn.f_95240_, effectIn.f_95241_, col, this.f_95202_, this.f_95203_, packedLightIn);
      } else {
         bufferIn.m_339083_(matrixIn, effectIn.f_95237_, effectIn.f_95238_, effectIn.f_95241_).m_340057_(effectIn.f_95242_, effectIn.f_95243_, effectIn.f_95244_, effectIn.f_95245_).m_167083_(this.f_95201_, this.f_95203_).m_338973_(packedLightIn);
         bufferIn.m_339083_(matrixIn, effectIn.f_95239_, effectIn.f_95238_, effectIn.f_95241_).m_340057_(effectIn.f_95242_, effectIn.f_95243_, effectIn.f_95244_, effectIn.f_95245_).m_167083_(this.f_95201_, this.f_95204_).m_338973_(packedLightIn);
         bufferIn.m_339083_(matrixIn, effectIn.f_95239_, effectIn.f_95240_, effectIn.f_95241_).m_340057_(effectIn.f_95242_, effectIn.f_95243_, effectIn.f_95244_, effectIn.f_95245_).m_167083_(this.f_95202_, this.f_95204_).m_338973_(packedLightIn);
         bufferIn.m_339083_(matrixIn, effectIn.f_95237_, effectIn.f_95240_, effectIn.f_95241_).m_340057_(effectIn.f_95242_, effectIn.f_95243_, effectIn.f_95244_, effectIn.f_95245_).m_167083_(this.f_95202_, this.f_95203_).m_338973_(packedLightIn);
      }

   }

   public C_4168_ m_181387_(C_3429_.C_180532_ modeIn) {
      return this.f_283799_.m_284370_(modeIn);
   }

   public static class C_3517_ {
      protected final float f_95237_;
      protected final float f_95238_;
      protected final float f_95239_;
      protected final float f_95240_;
      protected final float f_95241_;
      protected final float f_95242_;
      protected final float f_95243_;
      protected final float f_95244_;
      protected final float f_95245_;

      public C_3517_(float x0In, float y0In, float x1In, float y1In, float depthIn, float rIn, float gIn, float bIn, float aIn) {
         this.f_95237_ = x0In;
         this.f_95238_ = y0In;
         this.f_95239_ = x1In;
         this.f_95240_ = y1In;
         this.f_95241_ = depthIn;
         this.f_95242_ = rIn;
         this.f_95243_ = gIn;
         this.f_95244_ = bIn;
         this.f_95245_ = aIn;
      }
   }
}
