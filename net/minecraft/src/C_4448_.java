package net.minecraft.src;

public class C_4448_ extends C_4447_ {
   public C_5265_ f_117387_;
   public C_3819_ f_117388_;

   public C_4448_(C_4382_ p_i117389_1_, C_3819_ p_i117389_2_, C_5265_ p_i117389_3_) {
      super(p_i117389_1_);
      this.f_117388_ = p_i117389_2_;
      this.f_117387_ = p_i117389_3_;
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_507_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (((C_540_)entitylivingbaseIn).m_6254_()) {
         this.m_117386_().m_102624_(this.f_117388_);
         this.f_117388_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.f_117388_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110458_(this.f_117387_));
         this.f_117388_.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
      }

   }
}
