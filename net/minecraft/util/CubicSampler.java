package net.minecraft.util;

import net.minecraft.world.phys.Vec3;
import net.optifine.Vec3M;

public class CubicSampler {
   private static int f_177979_;
   private static int f_177980_;
   private static double[] f_130036_ = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

   private CubicSampler() {
   }

   public static Vec3 m_130038_(Vec3 vectorIn, CubicSampler.Vec3Fetcher fetcherIn) {
      if (vectorIn instanceof Vec3M vec3m) {
         return sampleM(vec3m, fetcherIn);
      } else {
         int i = Mth.m_14107_(vectorIn.m_7096_());
         int j = Mth.m_14107_(vectorIn.m_7098_());
         int k = Mth.m_14107_(vectorIn.m_7094_());
         double d0 = vectorIn.m_7096_() - (double)i;
         double d1 = vectorIn.m_7098_() - (double)j;
         double d2 = vectorIn.m_7094_() - (double)k;
         double d3 = 0.0;
         Vec3 vec3 = Vec3.f_82478_;

         for (int l = 0; l < 6; l++) {
            double d4 = Mth.m_14139_(d0, f_130036_[l + 1], f_130036_[l]);
            int i1 = i - 2 + l;

            for (int j1 = 0; j1 < 6; j1++) {
               double d5 = Mth.m_14139_(d1, f_130036_[j1 + 1], f_130036_[j1]);
               int k1 = j - 2 + j1;

               for (int l1 = 0; l1 < 6; l1++) {
                  double d6 = Mth.m_14139_(d2, f_130036_[l1 + 1], f_130036_[l1]);
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

   public static Vec3M sampleM(Vec3 vectorIn, CubicSampler.Vec3Fetcher fetcherIn) {
      int x0 = Mth.m_14107_(vectorIn.m_7096_());
      int y0 = Mth.m_14107_(vectorIn.m_7098_());
      int z0 = Mth.m_14107_(vectorIn.m_7094_());
      double dx = vectorIn.m_7096_() - (double)x0;
      double dy = vectorIn.m_7098_() - (double)y0;
      double dz = vectorIn.m_7094_() - (double)z0;
      double stSum = 0.0;
      Vec3M vecSum = new Vec3M(0.0, 0.0, 0.0);

      for (int kx = 0; kx < 6; kx++) {
         double sx = Mth.m_14139_(dx, f_130036_[kx + 1], f_130036_[kx]);
         int x = x0 - 2 + kx;

         for (int ky = 0; ky < 6; ky++) {
            double sy = Mth.m_14139_(dy, f_130036_[ky + 1], f_130036_[ky]);
            int y = y0 - 2 + ky;

            for (int kz = 0; kz < 6; kz++) {
               double sz = Mth.m_14139_(dz, f_130036_[kz + 1], f_130036_[kz]);
               int z = z0 - 2 + kz;
               double st = sx * sy * sz;
               stSum += st;
               Vec3 vecCol = fetcherIn.m_130041_(x, y, z);
               vecCol = vecCol.m_82490_(st);
               vecSum = vecSum.add(vecCol);
            }
         }
      }

      return vecSum.scale(1.0 / stSum);
   }

   @FunctionalInterface
   public interface Vec3Fetcher {
      Vec3 m_130041_(int var1, int var2, int var3);
   }
}
