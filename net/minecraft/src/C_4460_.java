package net.minecraft.src;

public class C_4460_ extends C_4432_ {
   private static final C_5265_ f_117695_ = C_5265_.m_340282_("textures/entity/wither/wither_armor.png");
   public C_3884_ f_117696_;
   public C_5265_ customTextureLocation;

   public C_4460_(C_4382_ p_i174553_1_, C_141653_ p_i174553_2_) {
      super(p_i174553_1_);
      this.f_117696_ = new C_3884_(p_i174553_2_.m_171103_(C_141656_.f_171215_));
   }

   protected float m_7631_(float ticksIn) {
      return C_188_.m_14089_(ticksIn * 0.02F) * 3.0F;
   }

   protected C_5265_ m_7029_() {
      return this.customTextureLocation != null ? this.customTextureLocation : f_117695_;
   }

   protected C_3819_ m_7193_() {
      return this.f_117696_;
   }
}
