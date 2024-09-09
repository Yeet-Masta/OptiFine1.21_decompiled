package net.minecraft.src;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public class C_141048_ extends C_141050_ {
   public static final C_141048_ f_146451_ = new C_141048_(0.0F);
   public static final MapCodec f_146452_;
   private final float f_146453_;

   public static C_141048_ m_146458_(float p_146458_0_) {
      return p_146458_0_ == 0.0F ? f_146451_ : new C_141048_(p_146458_0_);
   }

   private C_141048_(float p_i146455_1_) {
      this.f_146453_ = p_i146455_1_;
   }

   public float m_146474_() {
      return this.f_146453_;
   }

   public float m_214084_(C_212974_ p_214084_1_) {
      return this.f_146453_;
   }

   public float m_142735_() {
      return this.f_146453_;
   }

   public float m_142734_() {
      return this.f_146453_;
   }

   public C_141051_ m_141961_() {
      return C_141051_.f_146519_;
   }

   public String toString() {
      return Float.toString(this.f_146453_);
   }

   static {
      f_146452_ = Codec.FLOAT.fieldOf("value").xmap(C_141048_::m_146458_, C_141048_::m_146474_);
   }
}
