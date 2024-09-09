package net.minecraft.src;

import net.optifine.Config;
import net.optifine.CustomColors;

public class C_4335_ extends C_4331_ {
   private static final C_5265_ f_114579_ = C_5265_.m_340282_("textures/entity/experience_orb.png");
   private static final C_4168_ f_114580_;

   public C_4335_(C_141742_.C_141743_ contextIn) {
      super(contextIn);
      this.f_114477_ = 0.15F;
      this.f_114478_ = 0.75F;
   }

   protected int m_6086_(C_518_ entityIn, C_4675_ partialTicks) {
      return C_188_.m_14045_(super.m_6086_(entityIn, partialTicks) + 7, 0, 15);
   }

   public void m_7392_(C_518_ entityIn, float entityYaw, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      int i = entityIn.m_20802_();
      float f = (float)(i % 4 * 16 + 0) / 64.0F;
      float f1 = (float)(i % 4 * 16 + 16) / 64.0F;
      float f2 = (float)(i / 4 * 16 + 0) / 64.0F;
      float f3 = (float)(i / 4 * 16 + 16) / 64.0F;
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      float f7 = 255.0F;
      float f8 = ((float)entityIn.ai + partialTicks) / 2.0F;
      if (Config.isCustomColors()) {
         f8 = CustomColors.getXpOrbTimer(f8);
      }

      int j = (int)((C_188_.m_14031_(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int k = true;
      int l = (int)((C_188_.m_14031_(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
      matrixStackIn.m_252880_(0.0F, 0.1F, 0.0F);
      matrixStackIn.m_252781_(this.f_114476_.m_253208_());
      float f9 = 0.3F;
      matrixStackIn.m_85841_(0.3F, 0.3F, 0.3F);
      C_3187_ vertexconsumer = bufferIn.m_6299_(f_114580_);
      C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_();
      int red = j;
      int green = 255;
      int blue = l;
      if (Config.isCustomColors()) {
         int col = CustomColors.getXpOrbColor(f8);
         if (col >= 0) {
            red = col >> 16 & 255;
            green = col >> 8 & 255;
            blue = col >> 0 & 255;
         }
      }

      m_252863_(vertexconsumer, posestack$pose, -0.5F, -0.25F, red, green, blue, f, f3, packedLightIn);
      m_252863_(vertexconsumer, posestack$pose, 0.5F, -0.25F, red, green, blue, f1, f3, packedLightIn);
      m_252863_(vertexconsumer, posestack$pose, 0.5F, 0.75F, red, green, blue, f1, f2, packedLightIn);
      m_252863_(vertexconsumer, posestack$pose, -0.5F, 0.75F, red, green, blue, f, f2, packedLightIn);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private static void m_252863_(C_3187_ bufferIn, C_3181_.C_3183_ matrixStackIn, float x, float y, int red, int green, int blue, float texU, float texV, int packedLight) {
      bufferIn.m_338370_(matrixStackIn, x, y, 0.0F).m_167129_(red, green, blue, 128).m_167083_(texU, texV).m_338943_(C_4474_.f_118083_).m_338973_(packedLight).m_339200_(matrixStackIn, 0.0F, 1.0F, 0.0F);
   }

   public C_5265_ m_5478_(C_518_ entity) {
      return f_114579_;
   }

   static {
      f_114580_ = C_4168_.m_110467_(f_114579_);
   }
}
