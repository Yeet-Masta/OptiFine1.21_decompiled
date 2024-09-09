import java.util.Locale;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_4713_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.util.MathUtils;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.math.NumberUtils;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Mth {
   private static final long k = 61440L;
   private static final long l = 16384L;
   private static final long m = -4611686018427387904L;
   private static final long n = Long.MIN_VALUE;
   public static final float a = (float) Math.PI;
   public static final float b = (float) (Math.PI / 2);
   public static final float c = (float) (Math.PI * 2);
   public static final float d = (float) (Math.PI / 180.0);
   public static final float e = 180.0F / (float)Math.PI;
   public static final float f = 1.0E-5F;
   public static final float g = c(2.0F);
   private static final float o = 10430.378F;
   public static final Vector3f h = new Vector3f(0.0F, 1.0F, 0.0F);
   public static final Vector3f i = new Vector3f(1.0F, 0.0F, 0.0F);
   public static final Vector3f j = new Vector3f(0.0F, 0.0F, 1.0F);
   private static final float[] p = Util.a(new float[65536], p_14076_0_ -> {
      for (int i = 0; i < p_14076_0_.length; i++) {
         p_14076_0_[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
      }
   });
   private static final C_212974_ q = C_212974_.m_216337_();
   private static final int[] r = new int[]{
      0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
   };
   private static final double s = 0.16666666666666666;
   private static final int t = 8;
   private static final int u = 257;
   private static final double v = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] w = new double[257];
   private static final double[] x = new double[257];
   private static final int SIN_BITS = 12;
   private static final int SIN_MASK = 4095;
   private static final int SIN_COUNT = 4096;
   private static final int SIN_COUNT_D4 = 1024;
   public static final float PI2 = MathUtils.roundToFloat(Math.PI * 2);
   public static final float PId2 = MathUtils.roundToFloat(Math.PI / 2);
   private static final float radToIndex = MathUtils.roundToFloat(651.8986469044033);
   public static final float deg2Rad = MathUtils.roundToFloat(Math.PI / 180.0);
   private static final float[] SIN_TABLE_FAST = new float[4096];
   public static boolean fastMath = false;

   public static float a(float value) {
      return fastMath ? SIN_TABLE_FAST[(int)(value * radToIndex) & 4095] : p[(int)(value * 10430.378F) & 65535];
   }

   public static float b(float value) {
      return fastMath ? SIN_TABLE_FAST[(int)(value * radToIndex + 1024.0F) & 4095] : p[(int)(value * 10430.378F + 16384.0F) & 65535];
   }

   public static float c(float value) {
      return (float)Math.sqrt((double)value);
   }

   public static int d(float value) {
      int i = (int)value;
      return value < (float)i ? i - 1 : i;
   }

   public static int a(double value) {
      int i = (int)value;
      return value < (double)i ? i - 1 : i;
   }

   public static long b(double value) {
      long i = (long)value;
      return value < (double)i ? i - 1L : i;
   }

   public static float e(float value) {
      return Math.abs(value);
   }

   public static int a(int value) {
      return Math.abs(value);
   }

   public static int f(float value) {
      int i = (int)value;
      return value > (float)i ? i + 1 : i;
   }

   public static int c(double value) {
      int i = (int)value;
      return value > (double)i ? i + 1 : i;
   }

   public static int a(int num, int min, int max) {
      return Math.min(Math.max(num, min), max);
   }

   public static long a(long num, long min, long max) {
      return Math.min(Math.max(num, min), max);
   }

   public static float a(float num, float min, float max) {
      return num < min ? min : Math.min(num, max);
   }

   public static double a(double num, double min, double max) {
      return num < min ? min : Math.min(num, max);
   }

   public static double b(double lowerBnd, double upperBnd, double slide) {
      if (slide < 0.0) {
         return lowerBnd;
      } else {
         return slide > 1.0 ? upperBnd : d(slide, lowerBnd, upperBnd);
      }
   }

   public static float b(float p_144920_0_, float p_144920_1_, float p_144920_2_) {
      if (p_144920_2_ < 0.0F) {
         return p_144920_0_;
      } else {
         return p_144920_2_ > 1.0F ? p_144920_1_ : i(p_144920_2_, p_144920_0_, p_144920_1_);
      }
   }

   public static double a(double x, double y) {
      if (x < 0.0) {
         x = -x;
      }

      if (y < 0.0) {
         y = -y;
      }

      return Math.max(x, y);
   }

   public static int a(int x, int y) {
      return Math.floorDiv(x, y);
   }

   public static int a(C_212974_ random, int minimum, int maximum) {
      return minimum >= maximum ? minimum : random.m_188503_(maximum - minimum + 1) + minimum;
   }

   public static float a(C_212974_ random, float minimum, float maximum) {
      return minimum >= maximum ? minimum : random.m_188501_() * (maximum - minimum) + minimum;
   }

   public static double a(C_212974_ random, double minimum, double maximum) {
      return minimum >= maximum ? minimum : random.m_188500_() * (maximum - minimum) + minimum;
   }

   public static boolean a(float x, float y) {
      return Math.abs(y - x) < 1.0E-5F;
   }

   public static boolean b(double x, double y) {
      return Math.abs(y - x) < 1.0E-5F;
   }

   public static int b(int x, int y) {
      return Math.floorMod(x, y);
   }

   public static float b(float numerator, float denominator) {
      return (numerator % denominator + denominator) % denominator;
   }

   public static double c(double numerator, double denominator) {
      return (numerator % denominator + denominator) % denominator;
   }

   public static boolean c(int p_264612_0_, int p_264612_1_) {
      return p_264612_0_ % p_264612_1_ == 0;
   }

   public static int b(int angle) {
      int i = angle % 360;
      if (i >= 180) {
         i -= 360;
      }

      if (i < -180) {
         i += 360;
      }

      return i;
   }

   public static float g(float value) {
      float f = value % 360.0F;
      if (f >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   public static double d(double value) {
      double d0 = value % 360.0;
      if (d0 >= 180.0) {
         d0 -= 360.0;
      }

      if (d0 < -180.0) {
         d0 += 360.0;
      }

      return d0;
   }

   public static float c(float p_14118_0_, float p_14118_1_) {
      return g(p_14118_1_ - p_14118_0_);
   }

   public static float d(float p_14145_0_, float p_14145_1_) {
      return e(c(p_14145_0_, p_14145_1_));
   }

   public static float c(float p_14094_0_, float p_14094_1_, float p_14094_2_) {
      float f = c(p_14094_0_, p_14094_1_);
      float f1 = a(f, -p_14094_2_, p_14094_2_);
      return p_14094_1_ - f1;
   }

   public static float d(float p_14121_0_, float p_14121_1_, float p_14121_2_) {
      p_14121_2_ = e(p_14121_2_);
      return p_14121_0_ < p_14121_1_ ? a(p_14121_0_ + p_14121_2_, p_14121_0_, p_14121_1_) : a(p_14121_0_ - p_14121_2_, p_14121_1_, p_14121_0_);
   }

   public static float e(float p_14148_0_, float p_14148_1_, float p_14148_2_) {
      float f = c(p_14148_0_, p_14148_1_);
      return d(p_14148_0_, p_14148_0_ + f, p_14148_2_);
   }

   public static int a(String value, int defaultValue) {
      return NumberUtils.toInt(value, defaultValue);
   }

   public static int c(int value) {
      int i = value - 1;
      i |= i >> 1;
      i |= i >> 2;
      i |= i >> 4;
      i |= i >> 8;
      i |= i >> 16;
      return i + 1;
   }

   public static boolean d(int value) {
      return value != 0 && (value & value - 1) == 0;
   }

   public static int e(int value) {
      value = d(value) ? value : c(value);
      return r[(int)((long)value * 125613361L >> 27) & 31];
   }

   public static int f(int value) {
      return e(value) - (d(value) ? 0 : 1);
   }

   public static int f(float rIn, float gIn, float bIn) {
      return C_175_.m_13660_(0, d(rIn * 255.0F), d(gIn * 255.0F), d(bIn * 255.0F));
   }

   public static float h(float number) {
      return number - (float)d(number);
   }

   public static double e(double number) {
      return number - (double)b(number);
   }

   @Deprecated
   public static long a(C_4713_ pos) {
      return b(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
   }

   @Deprecated
   public static long b(int x, int y, int z) {
      long i = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
      i = i * i * 42317861L + i * 11L;
      return i >> 16;
   }

   public static UUID a(C_212974_ random) {
      long i = random.m_188505_() & -61441L | 16384L;
      long j = random.m_188505_() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(i, j);
   }

   public static UUID a() {
      return a(q);
   }

   public static double c(double p_14112_0_, double p_14112_2_, double p_14112_4_) {
      return (p_14112_0_ - p_14112_2_) / (p_14112_4_ - p_14112_2_);
   }

   public static float g(float p_184655_0_, float p_184655_1_, float p_184655_2_) {
      return (p_184655_0_ - p_184655_1_) / (p_184655_2_ - p_184655_1_);
   }

   public static boolean a(Vec3 p_144888_0_, Vec3 p_144888_1_, C_3040_ p_144888_2_) {
      double d0 = (p_144888_2_.f_82288_ + p_144888_2_.f_82291_) * 0.5;
      double d1 = (p_144888_2_.f_82291_ - p_144888_2_.f_82288_) * 0.5;
      double d2 = p_144888_0_.c - d0;
      if (Math.abs(d2) > d1 && d2 * p_144888_1_.c >= 0.0) {
         return false;
      } else {
         double d3 = (p_144888_2_.f_82289_ + p_144888_2_.f_82292_) * 0.5;
         double d4 = (p_144888_2_.f_82292_ - p_144888_2_.f_82289_) * 0.5;
         double d5 = p_144888_0_.d - d3;
         if (Math.abs(d5) > d4 && d5 * p_144888_1_.d >= 0.0) {
            return false;
         } else {
            double d6 = (p_144888_2_.f_82290_ + p_144888_2_.f_82293_) * 0.5;
            double d7 = (p_144888_2_.f_82293_ - p_144888_2_.f_82290_) * 0.5;
            double d8 = p_144888_0_.e - d6;
            if (Math.abs(d8) > d7 && d8 * p_144888_1_.e >= 0.0) {
               return false;
            } else {
               double d9 = Math.abs(p_144888_1_.c);
               double d10 = Math.abs(p_144888_1_.d);
               double d11 = Math.abs(p_144888_1_.e);
               double d12 = p_144888_1_.d * d8 - p_144888_1_.e * d5;
               if (Math.abs(d12) > d4 * d11 + d7 * d10) {
                  return false;
               } else {
                  d12 = p_144888_1_.e * d2 - p_144888_1_.c * d8;
                  if (Math.abs(d12) > d1 * d11 + d7 * d9) {
                     return false;
                  } else {
                     d12 = p_144888_1_.c * d5 - p_144888_1_.d * d2;
                     return Math.abs(d12) < d1 * d10 + d4 * d9;
                  }
               }
            }
         }
      }
   }

   public static double d(double p_14136_0_, double p_14136_2_) {
      double d0 = p_14136_2_ * p_14136_2_ + p_14136_0_ * p_14136_0_;
      if (Double.isNaN(d0)) {
         return Double.NaN;
      } else {
         boolean flag = p_14136_0_ < 0.0;
         if (flag) {
            p_14136_0_ = -p_14136_0_;
         }

         boolean flag1 = p_14136_2_ < 0.0;
         if (flag1) {
            p_14136_2_ = -p_14136_2_;
         }

         boolean flag2 = p_14136_0_ > p_14136_2_;
         if (flag2) {
            double d1 = p_14136_2_;
            p_14136_2_ = p_14136_0_;
            p_14136_0_ = d1;
         }

         double d9 = g(d0);
         p_14136_2_ *= d9;
         p_14136_0_ *= d9;
         double d2 = v + p_14136_0_;
         int i = (int)Double.doubleToRawLongBits(d2);
         double d3 = w[i];
         double d4 = x[i];
         double d5 = d2 - v;
         double d6 = p_14136_0_ * d4 - p_14136_2_ * d5;
         double d7 = (6.0 + d6 * d6) * d6 * 0.16666666666666666;
         double d8 = d3 + d7;
         if (flag2) {
            d8 = (Math.PI / 2) - d8;
         }

         if (flag1) {
            d8 = Math.PI - d8;
         }

         if (flag) {
            d8 = -d8;
         }

         return d8;
      }
   }

   public static float i(float p_264536_0_) {
      return org.joml.Math.invsqrt(p_264536_0_);
   }

   public static double f(double p_264555_0_) {
      return org.joml.Math.invsqrt(p_264555_0_);
   }

   @Deprecated
   public static double g(double number) {
      double d0 = 0.5 * number;
      long i = Double.doubleToRawLongBits(number);
      i = 6910469410427058090L - (i >> 1);
      number = Double.longBitsToDouble(i);
      return number * (1.5 - d0 * number * number);
   }

   public static float j(float number) {
      int i = Float.floatToIntBits(number);
      i = 1419967116 - i / 3;
      float f = Float.intBitsToFloat(i);
      f = 0.6666667F * f + 1.0F / (3.0F * f * f * number);
      return 0.6666667F * f + 1.0F / (3.0F * f * f * number);
   }

   public static int h(float hue, float saturation, float value) {
      return a(hue, saturation, value, 0);
   }

   public static int a(float hue, float saturation, float value, int alpha) {
      int i = (int)(hue * 6.0F) % 6;
      float f = hue * 6.0F - (float)i;
      float f1 = value * (1.0F - saturation);
      float f2 = value * (1.0F - f * saturation);
      float f3 = value * (1.0F - (1.0F - f) * saturation);
      float f4;
      float f5;
      float f6;
      switch (i) {
         case 0:
            f4 = value;
            f5 = f3;
            f6 = f1;
            break;
         case 1:
            f4 = f2;
            f5 = value;
            f6 = f1;
            break;
         case 2:
            f4 = f1;
            f5 = value;
            f6 = f3;
            break;
         case 3:
            f4 = f1;
            f5 = f2;
            f6 = value;
            break;
         case 4:
            f4 = f3;
            f5 = f1;
            f6 = value;
            break;
         case 5:
            f4 = value;
            f5 = f1;
            f6 = f2;
            break;
         default:
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
      }

      return C_175_.m_13660_(alpha, a((int)(f4 * 255.0F), 0, 255), a((int)(f5 * 255.0F), 0, 255), a((int)(f6 * 255.0F), 0, 255));
   }

   public static int g(int val) {
      val ^= val >>> 16;
      val *= -2048144789;
      val ^= val >>> 13;
      val *= -1028477387;
      return val ^ val >>> 16;
   }

   public static int a(int min, int max, IntPredicate isTargetBeforeOrAt) {
      int i = max - min;

      while (i > 0) {
         int j = i / 2;
         int k = min + j;
         if (isTargetBeforeOrAt.test(k)) {
            i = j;
         } else {
            min = k + 1;
            i -= j + 1;
         }
      }

      return min;
   }

   public static int a(float pct, int start, int end) {
      return start + d(pct * (float)(end - start));
   }

   public static int b(float pct, int start, int end) {
      int i = end - start;
      return start + d(pct * (float)(i - 1)) + (pct > 0.0F ? 1 : 0);
   }

   public static float i(float pct, float start, float end) {
      return start + pct * (end - start);
   }

   public static double d(double pct, double start, double end) {
      return start + pct * (end - start);
   }

   public static double a(double p_14012_0_, double p_14012_2_, double p_14012_4_, double p_14012_6_, double p_14012_8_, double p_14012_10_) {
      return d(p_14012_2_, d(p_14012_0_, p_14012_4_, p_14012_6_), d(p_14012_0_, p_14012_8_, p_14012_10_));
   }

   public static double a(
      double p_14019_0_,
      double p_14019_2_,
      double p_14019_4_,
      double p_14019_6_,
      double p_14019_8_,
      double p_14019_10_,
      double p_14019_12_,
      double p_14019_14_,
      double p_14019_16_,
      double p_14019_18_,
      double p_14019_20_
   ) {
      return d(
         p_14019_4_,
         a(p_14019_0_, p_14019_2_, p_14019_6_, p_14019_8_, p_14019_10_, p_14019_12_),
         a(p_14019_0_, p_14019_2_, p_14019_14_, p_14019_16_, p_14019_18_, p_14019_20_)
      );
   }

   public static float a(float p_216244_0_, float p_216244_1_, float p_216244_2_, float p_216244_3_, float p_216244_4_) {
      return 0.5F
         * (
            2.0F * p_216244_2_
               + (p_216244_3_ - p_216244_1_) * p_216244_0_
               + (2.0F * p_216244_1_ - 5.0F * p_216244_2_ + 4.0F * p_216244_3_ - p_216244_4_) * p_216244_0_ * p_216244_0_
               + (3.0F * p_216244_2_ - p_216244_1_ - 3.0F * p_216244_3_ + p_216244_4_) * p_216244_0_ * p_216244_0_ * p_216244_0_
         );
   }

   public static double h(double p_14197_0_) {
      return p_14197_0_ * p_14197_0_ * p_14197_0_ * (p_14197_0_ * (p_14197_0_ * 6.0 - 15.0) + 10.0);
   }

   public static double i(double p_144946_0_) {
      return 30.0 * p_144946_0_ * p_144946_0_ * (p_144946_0_ - 1.0) * (p_144946_0_ - 1.0);
   }

   public static int j(double x) {
      if (x == 0.0) {
         return 0;
      } else {
         return x > 0.0 ? 1 : -1;
      }
   }

   public static float j(float p_14189_0_, float p_14189_1_, float p_14189_2_) {
      return p_14189_1_ + p_14189_0_ * g(p_14189_2_ - p_14189_1_);
   }

   public static double e(double p_293415_0_, double p_293415_2_, double p_293415_4_) {
      return p_293415_2_ + p_293415_0_ * d(p_293415_4_ - p_293415_2_);
   }

   public static float e(float p_14156_0_, float p_14156_1_) {
      return (Math.abs(p_14156_0_ % p_14156_1_ - p_14156_1_ * 0.5F) - p_14156_1_ * 0.25F) / (p_14156_1_ * 0.25F);
   }

   public static float k(float valueIn) {
      return valueIn * valueIn;
   }

   public static double k(double p_144952_0_) {
      return p_144952_0_ * p_144952_0_;
   }

   public static int h(int p_144944_0_) {
      return p_144944_0_ * p_144944_0_;
   }

   public static long a(long p_184643_0_) {
      return p_184643_0_ * p_184643_0_;
   }

   public static double a(double p_144851_0_, double p_144851_2_, double p_144851_4_, double p_144851_6_, double p_144851_8_) {
      return b(p_144851_6_, p_144851_8_, c(p_144851_0_, p_144851_2_, p_144851_4_));
   }

   public static float b(float p_184631_0_, float p_184631_1_, float p_184631_2_, float p_184631_3_, float p_184631_4_) {
      return b(p_184631_3_, p_184631_4_, g(p_184631_0_, p_184631_1_, p_184631_2_));
   }

   public static double b(double p_144914_0_, double p_144914_2_, double p_144914_4_, double p_144914_6_, double p_144914_8_) {
      return d(c(p_144914_0_, p_144914_2_, p_144914_4_), p_144914_6_, p_144914_8_);
   }

   public static float c(float p_184637_0_, float p_184637_1_, float p_184637_2_, float p_184637_3_, float p_184637_4_) {
      return i(g(p_184637_0_, p_184637_1_, p_184637_2_), p_184637_3_, p_184637_4_);
   }

   public static double l(double p_144954_0_) {
      return p_144954_0_ + (2.0 * C_212974_.m_216335_((long)a(p_144954_0_ * 3000.0)).m_188500_() - 1.0) * 1.0E-7 / 2.0;
   }

   public static int d(int p_144941_0_, int p_144941_1_) {
      return e(p_144941_0_, p_144941_1_) * p_144941_1_;
   }

   public static int e(int p_184652_0_, int p_184652_1_) {
      return -Math.floorDiv(-p_184652_0_, p_184652_1_);
   }

   public static int b(C_212974_ p_216287_0_, int p_216287_1_, int p_216287_2_) {
      return p_216287_0_.m_188503_(p_216287_2_ - p_216287_1_ + 1) + p_216287_1_;
   }

   public static float b(C_212974_ p_216283_0_, float p_216283_1_, float p_216283_2_) {
      return p_216283_0_.m_188501_() * (p_216283_2_ - p_216283_1_) + p_216283_1_;
   }

   public static float c(C_212974_ p_216291_0_, float p_216291_1_, float p_216291_2_) {
      return p_216291_1_ + (float)p_216291_0_.m_188583_() * p_216291_2_;
   }

   public static double e(double p_211589_0_, double p_211589_2_) {
      return p_211589_0_ * p_211589_0_ + p_211589_2_ * p_211589_2_;
   }

   public static double f(double p_184645_0_, double p_184645_2_) {
      return Math.sqrt(e(p_184645_0_, p_184645_2_));
   }

   public static double f(double p_211592_0_, double p_211592_2_, double p_211592_4_) {
      return p_211592_0_ * p_211592_0_ + p_211592_2_ * p_211592_2_ + p_211592_4_ * p_211592_4_;
   }

   public static double g(double p_184648_0_, double p_184648_2_, double p_184648_4_) {
      return Math.sqrt(f(p_184648_0_, p_184648_2_, p_184648_4_));
   }

   public static float k(float p_338503_0_, float p_338503_1_, float p_338503_2_) {
      return p_338503_0_ * p_338503_0_ + p_338503_1_ * p_338503_1_ + p_338503_2_ * p_338503_2_;
   }

   public static int a(double p_184628_0_, int p_184628_2_) {
      return a(p_184628_0_ / (double)p_184628_2_) * p_184628_2_;
   }

   public static IntStream c(int p_216295_0_, int p_216295_1_, int p_216295_2_) {
      return a(p_216295_0_, p_216295_1_, p_216295_2_, 1);
   }

   public static IntStream a(int p_216250_0_, int p_216250_1_, int p_216250_2_, int p_216250_3_) {
      if (p_216250_1_ > p_216250_2_) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "upperbound %d expected to be > lowerBound %d", p_216250_2_, p_216250_1_));
      } else if (p_216250_3_ < 1) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "steps expected to be >= 1, was %d", p_216250_3_));
      } else {
         return p_216250_0_ >= p_216250_1_ && p_216250_0_ <= p_216250_2_ ? IntStream.iterate(p_216250_0_, p_216278_3_ -> {
            int i = Math.abs(p_216250_0_ - p_216278_3_);
            return p_216250_0_ - i >= p_216250_1_ || p_216250_0_ + i <= p_216250_2_;
         }, p_216255_4_ -> {
            boolean flag = p_216255_4_ <= p_216250_0_;
            int i = Math.abs(p_216250_0_ - p_216255_4_);
            boolean flag1 = p_216250_0_ + i + p_216250_3_ <= p_216250_2_;
            if (!flag || !flag1) {
               int j = p_216250_0_ - i - (flag ? p_216250_3_ : 0);
               if (j >= p_216250_1_) {
                  return j;
               }
            }

            return p_216250_0_ + i + p_216250_3_;
         }) : IntStream.empty();
      }
   }

   public static Quaternionf a(Vector3f p_305706_0_, Quaternionf p_305706_1_, Quaternionf p_305706_2_) {
      float f = p_305706_0_.dot(p_305706_1_.x, p_305706_1_.y, p_305706_1_.z);
      return p_305706_2_.set(p_305706_0_.x * f, p_305706_0_.y * f, p_305706_0_.z * f, p_305706_1_.w).normalize();
   }

   public static int a(Fraction p_320106_0_, int p_320106_1_) {
      return p_320106_0_.getNumerator() * p_320106_1_ / p_320106_0_.getDenominator();
   }

   static {
      for (int i = 0; i < 257; i++) {
         double d0 = (double)i / 256.0;
         double d1 = Math.asin(d0);
         x[i] = Math.cos(d1);
         w[i] = d1;
      }

      for (int s = 0; s < SIN_TABLE_FAST.length; s++) {
         SIN_TABLE_FAST[s] = MathUtils.roundToFloat(Math.sin((double)s * Math.PI * 2.0 / 4096.0));
      }
   }
}
