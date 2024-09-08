package net.minecraft.src;

public class C_4448_<T extends C_507_ & C_540_, M extends C_3819_<T>> extends C_4447_<T, M> {
   public C_5265_ f_117387_;
   public M f_117388_;

   public C_4448_(C_4382_<T, M> p_i117389_1_, M p_i117389_2_, C_5265_ p_i117389_3_) {
      super(p_i117389_1_);
      this.f_117388_ = p_i117389_2_;
      this.f_117387_ = p_i117389_3_;
   }

   @Override
   public void m_6494_(
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
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
         C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110458_(this.f_117387_));
         this.f_117388_.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
      }
   }
}
