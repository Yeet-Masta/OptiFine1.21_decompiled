package net.minecraft.client.model;

public class ModelUtils {
   public static float m_103125_(float p_103125_0_, float p_103125_1_, float p_103125_2_) {
      float f = p_103125_1_ - p_103125_0_;

      while (f < (float) -Math.PI) {
         f += (float) (Math.PI * 2);
      }

      while (f >= (float) Math.PI) {
         f -= (float) (Math.PI * 2);
      }

      return p_103125_0_ + p_103125_2_ * f;
   }
}
