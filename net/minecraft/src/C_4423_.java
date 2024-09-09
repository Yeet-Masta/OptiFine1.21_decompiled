package net.minecraft.src;

public class C_4423_ extends C_4432_ {
   private static final C_5265_ f_116676_ = C_5265_.m_340282_("textures/entity/creeper/creeper_armor.png");
   public C_3812_ f_116677_;
   public C_5265_ customTextureLocation;

   public C_4423_(C_4382_ p_i174470_1_, C_141653_ p_i174470_2_) {
      super(p_i174470_1_);
      this.f_116677_ = new C_3812_(p_i174470_2_.m_171103_(C_141656_.f_171129_));
   }

   protected float m_7631_(float ticksIn) {
      return ticksIn * 0.01F;
   }

   protected C_5265_ m_7029_() {
      return this.customTextureLocation != null ? this.customTextureLocation : f_116676_;
   }

   protected C_3819_ m_7193_() {
      return this.f_116677_;
   }
}
