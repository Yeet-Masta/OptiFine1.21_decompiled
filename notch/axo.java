package net.minecraft.src;

import net.optifine.Vec3M;

public class C_4980_ {
   private static final int f_177979_ = 2;
   private static final int f_177980_ = 6;
   private static final double[] f_130036_ = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

   private C_4980_() {
   }

   public static C_3046_ m_130038_(C_3046_ vectorIn, C_4980_.C_4981_ fetcherIn) {
      if (vectorIn instanceof Vec3M vec3m) {
         return sampleM(vec3m, fetcherIn);
      } else {
         int i = C_188_.m_14107_(vectorIn.m_7096_());
         int j = C_188_.m_14107_(vectorIn.m_7098_());
         int k = C_188_.m_14107_(vectorIn.m_7094_());
         double d0 = vectorIn.m_7096_() - (double)i;
         double d1 = vectorIn.m_7098_() - (double)j;
         double d2 = vectorIn.m_7094_() - (double)k;
         double d3 = 0.0;
         C_3046_ vec3 = C_3046_.f_82478_;

         for (int l = 0; l < 6; l++) {
            double d4 = C_188_.m_14139_(d0, f_130036_[l + 1], f_130036_[l]);
            int i1 = i - 2 + l;

            for (int j1 = 0; j1 < 6; j1++) {
               double d5 = C_188_.m_14139_(d1, f_130036_[j1 + 1], f_130036_[j1]);
               int k1 = j - 2 + j1;

               for (int l1 = 0; l1 < 6; l1++) {
                  double d6 = C_188_.m_14139_(d2, f_130036_[l1 + 1], f_130036_[l1]);
                  int i2 = k - 2 + l1;
                  double d7 = d4 * d5 * d6;
                  d3 += d7;
                  vec3 = vec3.m_82549_(fetcherIn.m_130041_(i1, k1, i2).m_82490_(d7));
               }
            }
         }

         return vec3.m_82490_(1.0 / d3);
      }
   }

   public static Vec3M sampleM(C_3046_ vectorIn, C_4980_.C_4981_ fetcherIn) {
      int x0 = C_188_.m_14107_(vectorIn.m_7096_());
      int y0 = C_188_.m_14107_(vectorIn.m_7098_());
      int z0 = C_188_.m_14107_(vectorIn.m_7094_());
      double dx = vectorIn.m_7096_() - (double)x0;
      double dy = vectorIn.m_7098_() - (double)y0;
      double dz = vectorIn.m_7094_() - (double)z0;
      double stSum = 0.0;
      Vec3M vecSum = new Vec3M(0.0, 0.0, 0.0);

      for (int kx = 0; kx < 6; kx++) {
         double sx = C_188_.m_14139_(dx, f_130036_[kx + 1], f_130036_[kx]);
         int x = x0 - 2 + kx;

         for (int ky = 0; ky < 6; ky++) {
            double sy = C_188_.m_14139_(dy, f_130036_[ky + 1], f_130036_[ky]);
            int y = y0 - 2 + ky;

            for (int kz = 0; kz < 6; kz++) {
               double sz = C_188_.m_14139_(dz, f_130036_[kz + 1], f_130036_[kz]);
               int z = z0 - 2 + kz;
               double st = sx * sy * sz;
               stSum += st;
               C_3046_ vecCol = fetcherIn.m_130041_(x, y, z);
               vecCol = vecCol.m_82490_(st);
               vecSum = vecSum.add(vecCol);
            }
         }
      }

      return vecSum.scale(1.0 / stSum);
   }

   @FunctionalInterface
   public interface C_4981_ {
      C_3046_ m_130041_(int var1, int var2, int var3);
   }
}
