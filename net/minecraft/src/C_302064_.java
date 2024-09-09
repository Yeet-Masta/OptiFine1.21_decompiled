package net.minecraft.src;

public class C_302064_ extends C_4447_ {
   private C_5265_ f_315155_ = C_5265_.m_340282_("textures/entity/breeze/breeze_wind.png");
   private C_302185_ f_336859_;

   public C_302064_(C_141742_.C_141743_ p_i307292_1_, C_4382_ p_i307292_2_) {
      super(p_i307292_2_);
      this.f_336859_ = new C_302185_(p_i307292_1_.m_174023_(C_141656_.f_337505_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_301969_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      float f = (float)entitylivingbaseIn.ai + partialTicks;
      C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_305520_(this.f_315155_, this.m_306824_(f) % 1.0F, 0.0F));
      this.f_336859_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      C_302054_.m_323838_(this.f_336859_, new C_3889_[]{this.f_336859_.m_321100_()}).a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }

   private float m_306824_(float p_306824_1_) {
      return p_306824_1_ * 0.02F;
   }

   public void setModel(C_302185_ model) {
      this.f_336859_ = model;
   }

   public void setTextureLocation(C_5265_ textureLocation) {
      this.f_315155_ = textureLocation;
   }
}
