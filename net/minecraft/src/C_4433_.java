package net.minecraft.src;

import net.optifine.Config;
import net.optifine.shaders.Shaders;

public abstract class C_4433_ extends C_4447_ {
   public C_4433_(C_4382_ p_i116980_1_) {
      super(p_i116980_1_);
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_507_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      C_3187_ vertexconsumer = bufferIn.m_6299_(this.m_5708_());
      if (Config.isShaders()) {
         Shaders.beginSpiderEyes();
      }

      Config.getRenderGlobal().renderOverlayEyes = true;
      this.m_117386_().a(matrixStackIn, vertexconsumer, 15728640, C_4474_.f_118083_);
      Config.getRenderGlobal().renderOverlayEyes = false;
      if (Config.isShaders()) {
         Shaders.endSpiderEyes();
      }

   }

   public abstract C_4168_ m_5708_();
}
