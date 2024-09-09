package net.minecraft.src;

public class C_4442_ extends C_4447_ {
   private static final C_5265_[] f_117214_ = new C_5265_[]{C_5265_.m_340282_("textures/entity/llama/decor/white.png"), C_5265_.m_340282_("textures/entity/llama/decor/orange.png"), C_5265_.m_340282_("textures/entity/llama/decor/magenta.png"), C_5265_.m_340282_("textures/entity/llama/decor/light_blue.png"), C_5265_.m_340282_("textures/entity/llama/decor/yellow.png"), C_5265_.m_340282_("textures/entity/llama/decor/lime.png"), C_5265_.m_340282_("textures/entity/llama/decor/pink.png"), C_5265_.m_340282_("textures/entity/llama/decor/gray.png"), C_5265_.m_340282_("textures/entity/llama/decor/light_gray.png"), C_5265_.m_340282_("textures/entity/llama/decor/cyan.png"), C_5265_.m_340282_("textures/entity/llama/decor/purple.png"), C_5265_.m_340282_("textures/entity/llama/decor/blue.png"), C_5265_.m_340282_("textures/entity/llama/decor/brown.png"), C_5265_.m_340282_("textures/entity/llama/decor/green.png"), C_5265_.m_340282_("textures/entity/llama/decor/red.png"), C_5265_.m_340282_("textures/entity/llama/decor/black.png")};
   private static final C_5265_ f_117215_ = C_5265_.m_340282_("textures/entity/llama/decor/trader_llama.png");
   private final C_3837_ f_117216_;

   public C_4442_(C_4382_ p_i174498_1_, C_141653_ p_i174498_2_) {
      super(p_i174498_1_);
      this.f_117216_ = new C_3837_(p_i174498_2_.m_171103_(C_141656_.f_171195_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_930_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      C_1353_ dyecolor = entitylivingbaseIn.m_30826_();
      C_5265_ resourcelocation;
      if (dyecolor != null) {
         resourcelocation = f_117214_[dyecolor.m_41060_()];
      } else {
         if (!entitylivingbaseIn.m_7565_()) {
            return;
         }

         resourcelocation = f_117215_;
      }

      if (this.f_117216_.locationTextureCustom != null) {
         resourcelocation = this.f_117216_.locationTextureCustom;
      }

      ((C_3837_)this.m_117386_()).a(this.f_117216_);
      this.f_117216_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110458_(resourcelocation));
      this.f_117216_.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }
}
