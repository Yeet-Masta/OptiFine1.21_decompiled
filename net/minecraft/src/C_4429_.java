package net.minecraft.src;

public class C_4429_ extends C_4447_ {
   private static final C_5265_ f_116907_ = C_5265_.m_340282_("textures/entity/zombie/drowned_outer_layer.png");
   public C_3814_ f_116908_;
   public C_5265_ customTextureLocation;

   public C_4429_(C_4382_ p_i174489_1_, C_141653_ p_i174489_2_) {
      super(p_i174489_1_);
      this.f_116908_ = new C_3814_(p_i174489_2_.m_171103_(C_141656_.f_171139_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_990_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      C_5265_ textureLocation = this.customTextureLocation != null ? this.customTextureLocation : f_116907_;
      m_117359_(this.m_117386_(), this.f_116908_, textureLocation, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, -1);
   }
}
