package net.minecraft.src;

public class C_313407_<T extends C_526_ & C_1042_, M extends C_3819_<T>> extends C_4447_<T, M> {
   public C_3868_<T> f_314940_;
   public C_5265_ f_316006_;

   public C_313407_(C_4382_<T, M> p_i323834_1_, C_141653_ p_i323834_2_, C_141655_ p_i323834_3_, C_5265_ p_i323834_4_) {
      super(p_i323834_1_);
      this.f_316006_ = p_i323834_4_;
      this.f_314940_ = new C_3868_(p_i323834_2_.m_171103_(p_i323834_3_));
   }

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
      m_117359_(
         this.m_117386_(),
         this.f_314940_,
         this.f_316006_,
         matrixStackIn,
         bufferIn,
         packedLightIn,
         entitylivingbaseIn,
         limbSwing,
         limbSwingAmount,
         ageInTicks,
         netHeadYaw,
         headPitch,
         partialTicks,
         -1
      );
   }
}
