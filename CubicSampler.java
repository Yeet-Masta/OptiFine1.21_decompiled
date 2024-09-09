import net.optifine.Vec3M;

public class CubicSampler {
   private static final int a = 2;
   private static final int b = 6;
   private static final double[] c = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

   private CubicSampler() {
   }

   public static Vec3 a(Vec3 vectorIn, CubicSampler.a fetcherIn) {
      if (vectorIn instanceof Vec3M vec3m) {
         return sampleM(vec3m, fetcherIn);
      } else {
         int i = Mth.a(vectorIn.m_7096_());
         int j = Mth.a(vectorIn.m_7098_());
         int k = Mth.a(vectorIn.m_7094_());
         double d0 = vectorIn.m_7096_() - (double)i;
         double d1 = vectorIn.m_7098_() - (double)j;
         double d2 = vectorIn.m_7094_() - (double)k;
         double d3 = 0.0;
         Vec3 vec3 = Vec3.b;

         for (int l = 0; l < 6; l++) {
            double d4 = Mth.d(d0, c[l + 1], c[l]);
            int i1 = i - 2 + l;

            for (int j1 = 0; j1 < 6; j1++) {
               double d5 = Mth.d(d1, c[j1 + 1], c[j1]);
               int k1 = j - 2 + j1;

               for (int l1 = 0; l1 < 6; l1++) {
                  double d6 = Mth.d(d2, c[l1 + 1], c[l1]);
                  int i2 = k - 2 + l1;
                  double d7 = d4 * d5 * d6;
                  d3 += d7;
                  vec3 = vec3.e(fetcherIn.fetch(i1, k1, i2).a(d7));
               }
            }
         }

         return vec3.a(1.0 / d3);
      }
   }

   public static Vec3M sampleM(Vec3 vectorIn, CubicSampler.a fetcherIn) {
      int x0 = Mth.a(vectorIn.m_7096_());
      int y0 = Mth.a(vectorIn.m_7098_());
      int z0 = Mth.a(vectorIn.m_7094_());
      double dx = vectorIn.m_7096_() - (double)x0;
      double dy = vectorIn.m_7098_() - (double)y0;
      double dz = vectorIn.m_7094_() - (double)z0;
      double stSum = 0.0;
      Vec3M vecSum = new Vec3M(0.0, 0.0, 0.0);

      for (int kx = 0; kx < 6; kx++) {
         double sx = Mth.d(dx, c[kx + 1], c[kx]);
         int x = x0 - 2 + kx;

         for (int ky = 0; ky < 6; ky++) {
            double sy = Mth.d(dy, c[ky + 1], c[ky]);
            int y = y0 - 2 + ky;

            for (int kz = 0; kz < 6; kz++) {
               double sz = Mth.d(dz, c[kz + 1], c[kz]);
               int z = z0 - 2 + kz;
               double st = sx * sy * sz;
               stSum += st;
               Vec3 vecCol = fetcherIn.fetch(x, y, z);
               vecCol = vecCol.a(st);
               vecSum = vecSum.add(vecCol);
            }
         }
      }

      return vecSum.scale(1.0 / stSum);
   }

   @FunctionalInterface
   public interface a {
      Vec3 fetch(int var1, int var2, int var3);
   }
}
