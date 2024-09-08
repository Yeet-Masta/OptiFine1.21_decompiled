package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;

public class ConstantFloat extends FloatProvider {
   public static final ConstantFloat f_146451_ = new ConstantFloat(0.0F);
   public static final MapCodec<ConstantFloat> f_146452_ = Codec.FLOAT.fieldOf("value").xmap(ConstantFloat::m_146458_, ConstantFloat::m_146474_);
   private final float f_146453_;

   public static ConstantFloat m_146458_(float p_146458_0_) {
      return p_146458_0_ == 0.0F ? f_146451_ : new ConstantFloat(p_146458_0_);
   }

   private ConstantFloat(float p_i146455_1_) {
      this.f_146453_ = p_i146455_1_;
   }

   public float m_146474_() {
      return this.f_146453_;
   }

   public float m_214084_(RandomSource p_214084_1_) {
      return this.f_146453_;
   }

   public float m_142735_() {
      return this.f_146453_;
   }

   public float m_142734_() {
      return this.f_146453_;
   }

   public FloatProviderType<?> m_141961_() {
      return FloatProviderType.f_146519_;
   }

   public String toString() {
      return Float.toString(this.f_146453_);
   }
}
