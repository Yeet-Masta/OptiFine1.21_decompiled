package net.minecraft.src;

public class C_4451_ extends C_4447_ {
   public C_3819_ f_117455_;
   public C_5265_ customTextureLocation;

   public C_4451_(C_4382_ p_i174535_1_, C_141653_ p_i174535_2_) {
      super(p_i174535_1_);
      this.f_117455_ = new C_3870_(p_i174535_2_.m_171103_(C_141656_.f_171242_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_524_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      C_3391_ minecraft = C_3391_.m_91087_();
      boolean flag = minecraft.m_91314_(entitylivingbaseIn) && entitylivingbaseIn.ci();
      if (!entitylivingbaseIn.ci() || flag) {
         C_5265_ textureLocation = this.customTextureLocation != null ? this.customTextureLocation : this.m_117347_(entitylivingbaseIn);
         C_3187_ vertexconsumer;
         if (flag) {
            vertexconsumer = bufferIn.m_6299_(C_4168_.m_110491_(textureLocation));
         } else {
            vertexconsumer = bufferIn.m_6299_(C_4168_.m_110473_(textureLocation));
         }

         ((C_3870_)this.m_117386_()).a(this.f_117455_);
         this.f_117455_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.f_117455_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.f_117455_.a(matrixStackIn, vertexconsumer, packedLightIn, C_4357_.m_115338_(entitylivingbaseIn, 0.0F));
      }

   }
}
