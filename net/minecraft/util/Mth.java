package net.minecraft.util;

import java.util.Locale;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import net.minecraft.Util;
import net.minecraft.core.Vec3i;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.optifine.util.MathUtils;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.lang3.math.NumberUtils;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Mth {
   private static long f_144838_;
   private static long f_144839_;
   private static long f_144840_;
   private static long f_144841_;
   public static float f_144830_;
   public static float f_144831_;
   public static float f_144832_;
   public static float f_144833_;
   public static float f_144834_;
   public static float f_144835_;
   public static float f_13994_ = m_14116_(2.0F);
   private static float f_144842_;
   public static Vector3f f_303648_ = new Vector3f(0.0F, 1.0F, 0.0F);
   public static Vector3f f_302939_ = new Vector3f(1.0F, 0.0F, 0.0F);
   public static Vector3f f_302844_ = new Vector3f(0.0F, 0.0F, 1.0F);
   private static float[] f_13995_ = Util.m_137469_(new float[65536], p_14076_0_ -> {
      for (int i = 0; i < p_14076_0_.length; i++) {
         p_14076_0_[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
      }
   });
   private static RandomSource f_13996_ = RandomSource.m_216337_();
   private static int[] f_13997_ = new int[]{
      0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
   };
   private static double f_144843_;
   private static int f_144844_;
   private static int f_144845_;
   private static double f_13998_ = Double.longBitsToDouble(4805340802404319232L);
   private static double[] f_13999_ = new double[257];
   private static double[] f_14000_ = new double[257];
   private static int SIN_BITS;
   private static int SIN_MASK;
   private static int SIN_COUNT;
   private static int SIN_COUNT_D4;
   public static float PI2 = MathUtils.roundToFloat(Math.PI * 2);
   public static float PId2 = MathUtils.roundToFloat(Math.PI / 2);
   private static float radToIndex = MathUtils.roundToFloat(651.8986469044033);
   public static float deg2Rad = MathUtils.roundToFloat(Math.PI / 180.0);
   private static float[] SIN_TABLE_FAST = new float[4096];
   public static boolean fastMath = false;

   public static float m_14031_(float value) {
      return fastMath ? SIN_TABLE_FAST[(int)(value * radToIndex) & 4095] : f_13995_[(int)(value * 10430.378F) & 65535];
   }

   public static float m_14089_(float value) {
      return fastMath ? SIN_TABLE_FAST[(int)(value * radToIndex + 1024.0F) & 4095] : f_13995_[(int)(value * 10430.378F + 16384.0F) & 65535];
   }

   public static float m_14116_(float value) {
      return (float)Math.sqrt((double)value);
   }

   public static int m_14143_(float value) {
      int i = (int)value;
      return value < (float)i ? i - 1 : i;
   }

   public static int m_14107_(double value) {
      int i = (int)value;
      return value < (double)i ? i - 1 : i;
   }

   public static long m_14134_(double value) {
      long i = (long)value;
      return value < (double)i ? i - 1L : i;
   }

   public static float m_14154_(float value) {
      return Math.abs(value);
   }

   public static int m_14040_(int value) {
      return Math.abs(value);
   }

   public static int m_14167_(float value) {
      int i = (int)value;
      return value > (float)i ? i + 1 : i;
   }

   public static int m_14165_(double value) {
      int i = (int)value;
      return value > (double)i ? i + 1 : i;
   }

   public static int m_14045_(int num, int min, int max) {
      return Math.min(Math.max(num, min), max);
   }

   public static long m_295574_(long num, long min, long max) {
      return Math.min(Math.max(num, min), max);
   }

   public static float m_14036_(float num, float min, float max) {
      return num < min ? min : Math.min(num, max);
   }

   public static double m_14008_(double num, double min, double max) {
      return num < min ? min : Math.min(num, max);
   }

   public static double m_14085_(double lowerBnd, double upperBnd, double slide) {
      if (slide < 0.0) {
         return lowerBnd;
      } else {
         return slide > 1.0 ? upperBnd : m_14139_(slide, lowerBnd, upperBnd);
      }
   }

   public static float m_144920_(float p_144920_0_, float p_144920_1_, float p_144920_2_) {
      if (p_144920_2_ < 0.0F) {
         return p_144920_0_;
      } else {
         return p_144920_2_ > 1.0F ? p_144920_1_ : m_14179_(p_144920_2_, p_144920_0_, p_144920_1_);
      }
   }

   public static double m_14005_(double x, double y) {
      if (x < 0.0) {
         x = -x;
      }

      if (y < 0.0) {
         y = -y;
      }

      return Math.max(x, y);
   }

   public static int m_14042_(int x, int y) {
      return Math.floorDiv(x, y);
   }

   public static int m_216271_(RandomSource random, int minimum, int maximum) {
      return minimum >= maximum ? minimum : random.m_188503_(maximum - minimum + 1) + minimum;
   }

   public static float m_216267_(RandomSource random, float minimum, float maximum) {
      return minimum >= maximum ? minimum : random.m_188501_() * (maximum - minimum) + minimum;
   }

   public static double m_216263_(RandomSource random, double minimum, double maximum) {
      return minimum >= maximum ? minimum : random.m_188500_() * (maximum - minimum) + minimum;
   }

   public static boolean m_14033_(float x, float y) {
      return Math.abs(y - x) < 1.0E-5F;
   }

   public static boolean m_14082_(double x, double y) {
      return Math.abs(y - x) < 1.0E-5F;
   }

   public static int m_14100_(int x, int y) {
      return Math.floorMod(x, y);
   }

   public static float m_14091_(float numerator, float denominator) {
      return (numerator % denominator + denominator) % denominator;
   }

   public static double m_14109_(double numerator, double denominator) {
      return (numerator % denominator + denominator) % denominator;
   }

   public static boolean m_264612_(int p_264612_0_, int p_264612_1_) {
      return p_264612_0_ % p_264612_1_ == 0;
   }

   public static int m_14098_(int angle) {
      int i = angle % 360;
      if (i >= 180) {
         i -= 360;
      }

      if (i < -180) {
         i += 360;
      }

      return i;
   }

   public static float m_14177_(float value) {
      float f = value % 360.0F;
      if (f >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   public static double m_14175_(double value) {
      double d0 = value % 360.0;
      if (d0 >= 180.0) {
         d0 -= 360.0;
      }

      if (d0 < -180.0) {
         d0 += 360.0;
      }

      return d0;
   }

   public static float m_14118_(float p_14118_0_, float p_14118_1_) {
      return m_14177_(p_14118_1_ - p_14118_0_);
   }

   public static float m_14145_(float p_14145_0_, float p_14145_1_) {
      return m_14154_(m_14118_(p_14145_0_, p_14145_1_));
   }

   public static float m_14094_(float p_14094_0_, float p_14094_1_, float p_14094_2_) {
      float f = m_14118_(p_14094_0_, p_14094_1_);
      float f1 = m_14036_(f, -p_14094_2_, p_14094_2_);
      return p_14094_1_ - f1;
   }

   public static float m_14121_(float p_14121_0_, float p_14121_1_, float p_14121_2_) {
      p_14121_2_ = m_14154_(p_14121_2_);
      return p_14121_0_ < p_14121_1_ ? m_14036_(p_14121_0_ + p_14121_2_, p_14121_0_, p_14121_1_) : m_14036_(p_14121_0_ - p_14121_2_, p_14121_1_, p_14121_0_);
   }

   public static float m_14148_(float p_14148_0_, float p_14148_1_, float p_14148_2_) {
      float f = m_14118_(p_14148_0_, p_14148_1_);
      return m_14121_(p_14148_0_, p_14148_0_ + f, p_14148_2_);
   }

   public static int m_14059_(String value, int defaultValue) {
      return NumberUtils.toInt(value, defaultValue);
   }

   public static int m_14125_(int value) {
      int i = value - 1;
      i |= i >> 1;
      i |= i >> 2;
      i |= i >> 4;
      i |= i >> 8;
      i |= i >> 16;
      return i + 1;
   }

   public static boolean m_14152_(int value) {
      return value != 0 && (value & value - 1) == 0;
   }

   public static int m_14163_(int value) {
      value = m_14152_(value) ? value : m_14125_(value);
      return f_13997_[(int)((long)value * 125613361L >> 27) & 31];
   }

   public static int m_14173_(int value) {
      return m_14163_(value) - (m_14152_(value) ? 0 : 1);
   }

   public static int m_14159_(float rIn, float gIn, float bIn) {
      return ARGB32.m_13660_(0, m_14143_(rIn * 255.0F), m_14143_(gIn * 255.0F), m_14143_(bIn * 255.0F));
   }

   public static float m_14187_(float number) {
      return number - (float)m_14143_(number);
   }

   public static double m_14185_(double number) {
      return number - (double)m_14134_(number);
   }

   @Deprecated
   public static long m_14057_(Vec3i pos) {
      return m_14130_(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
   }

   @Deprecated
   public static long m_14130_(int x, int y, int z) {
      long i = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
      i = i * i * 42317861L + i * 11L;
      return i >> 16;
   }

   public static UUID m_216261_(RandomSource random) {
      long i = random.m_188505_() & -61441L | 16384L;
      long j = random.m_188505_() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(i, j);
   }

   public static UUID m_14002_() {
      return m_216261_(f_13996_);
   }

   public static double m_14112_(double p_14112_0_, double p_14112_2_, double p_14112_4_) {
      return (p_14112_0_ - p_14112_2_) / (p_14112_4_ - p_14112_2_);
   }

   public static float m_184655_(float p_184655_0_, float p_184655_1_, float p_184655_2_) {
      return (p_184655_0_ - p_184655_1_) / (p_184655_2_ - p_184655_1_);
   }

   public static boolean m_144888_(Vec3 p_144888_0_, Vec3 p_144888_1_, AABB p_144888_2_) {
      double d0 = (p_144888_2_.f_82288_ + p_144888_2_.f_82291_) * 0.5;
      double d1 = (p_144888_2_.f_82291_ - p_144888_2_.f_82288_) * 0.5;
      double d2 = p_144888_0_.f_82479_ - d0;
      if (Math.abs(d2) > d1 && d2 * p_144888_1_.f_82479_ >= 0.0) {
         return false;
      } else {
         double d3 = (p_144888_2_.f_82289_ + p_144888_2_.f_82292_) * 0.5;
         double d4 = (p_144888_2_.f_82292_ - p_144888_2_.f_82289_) * 0.5;
         double d5 = p_144888_0_.f_82480_ - d3;
         if (Math.abs(d5) > d4 && d5 * p_144888_1_.f_82480_ >= 0.0) {
            return false;
         } else {
            double d6 = (p_144888_2_.f_82290_ + p_144888_2_.f_82293_) * 0.5;
            double d7 = (p_144888_2_.f_82293_ - p_144888_2_.f_82290_) * 0.5;
            double d8 = p_144888_0_.f_82481_ - d6;
            if (Math.abs(d8) > d7 && d8 * p_144888_1_.f_82481_ >= 0.0) {
               return false;
            } else {
               double d9 = Math.abs(p_144888_1_.f_82479_);
               double d10 = Math.abs(p_144888_1_.f_82480_);
               double d11 = Math.abs(p_144888_1_.f_82481_);
               double d12 = p_144888_1_.f_82480_ * d8 - p_144888_1_.f_82481_ * d5;
               if (Math.abs(d12) > d4 * d11 + d7 * d10) {
                  return false;
               } else {
                  d12 = p_144888_1_.f_82481_ * d2 - p_144888_1_.f_82479_ * d8;
                  if (Math.abs(d12) > d1 * d11 + d7 * d9) {
                     return false;
                  } else {
                     d12 = p_144888_1_.f_82479_ * d5 - p_144888_1_.f_82480_ * d2;
                     return Math.abs(d12) < d1 * d10 + d4 * d9;
                  }
               }
            }
         }
      }
   }

   public static double m_14136_(double p_14136_0_, double p_14136_2_) {
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

         double d9 = m_14193_(d0);
         p_14136_2_ *= d9;
         p_14136_0_ *= d9;
         double d2 = f_13998_ + p_14136_0_;
         int i = (int)Double.doubleToRawLongBits(d2);
         double d3 = f_13999_[i];
         double d4 = f_14000_[i];
         double d5 = d2 - f_13998_;
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

   public static float m_264536_(float p_264536_0_) {
      return org.joml.Math.invsqrt(p_264536_0_);
   }

   public static double m_264555_(double p_264555_0_) {
      return org.joml.Math.invsqrt(p_264555_0_);
   }

   @Deprecated
   public static double m_14193_(double number) {
      double d0 = 0.5 * number;
      long i = Double.doubleToRawLongBits(number);
      i = 6910469410427058090L - (i >> 1);
      number = Double.longBitsToDouble(i);
      return number * (1.5 - d0 * number * number);
   }

   public static float m_14199_(float number) {
      int i = Float.floatToIntBits(number);
      i = 1419967116 - i / 3;
      float f = Float.intBitsToFloat(i);
      f = 0.6666667F * f + 1.0F / (3.0F * f * f * number);
      return 0.6666667F * f + 1.0F / (3.0F * f * f * number);
   }

   public static int m_14169_(float hue, float saturation, float value) {
      return m_339996_(hue, saturation, value, 0);
   }

   public static int m_339996_(float hue, float saturation, float value, int alpha) {
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

      return ARGB32.m_13660_(alpha, m_14045_((int)(f4 * 255.0F), 0, 255), m_14045_((int)(f5 * 255.0F), 0, 255), m_14045_((int)(f6 * 255.0F), 0, 255));
   }

   public static int m_14183_(int val) {
      val ^= val >>> 16;
      val *= -2048144789;
      val ^= val >>> 13;
      val *= -1028477387;
      return val ^ val >>> 16;
   }

   public static int m_14049_(int min, int max, IntPredicate isTargetBeforeOrAt) {
      int i = max - min;

      while (i > 0) {
         int j = i / 2;
         int k = min + j;
         if (isTargetBeforeOrAt.m_125854_(k)) {
            i = j;
         } else {
            min = k + 1;
            i -= j + 1;
         }
      }

      return min;
   }

   public static int m_269140_(float pct, int start, int end) {
      return start + m_14143_(pct * (float)(end - start));
   }

   public static int m_295919_(float pct, int start, int end) {
      int i = end - start;
      return start + m_14143_(pct * (float)(i - 1)) + (pct > 0.0F ? 1 : 0);
   }

   public static float m_14179_(float pct, float start, float end) {
      return start + pct * (end - start);
   }

   public static double m_14139_(double pct, double start, double end) {
      return start + pct * (end - start);
   }

   public static double m_14012_(double p_14012_0_, double p_14012_2_, double p_14012_4_, double p_14012_6_, double p_14012_8_, double p_14012_10_) {
      return m_14139_(p_14012_2_, m_14139_(p_14012_0_, p_14012_4_, p_14012_6_), m_14139_(p_14012_0_, p_14012_8_, p_14012_10_));
   }

   public static double m_14019_(
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
      return m_14139_(
         p_14019_4_,
         m_14012_(p_14019_0_, p_14019_2_, p_14019_6_, p_14019_8_, p_14019_10_, p_14019_12_),
         m_14012_(p_14019_0_, p_14019_2_, p_14019_14_, p_14019_16_, p_14019_18_, p_14019_20_)
      );
   }

   public static float m_216244_(float p_216244_0_, float p_216244_1_, float p_216244_2_, float p_216244_3_, float p_216244_4_) {
      return 0.5F
         * (
            2.0F * p_216244_2_
               + (p_216244_3_ - p_216244_1_) * p_216244_0_
               + (2.0F * p_216244_1_ - 5.0F * p_216244_2_ + 4.0F * p_216244_3_ - p_216244_4_) * p_216244_0_ * p_216244_0_
               + (3.0F * p_216244_2_ - p_216244_1_ - 3.0F * p_216244_3_ + p_216244_4_) * p_216244_0_ * p_216244_0_ * p_216244_0_
         );
   }

   public static double m_14197_(double p_14197_0_) {
      return p_14197_0_ * p_14197_0_ * p_14197_0_ * (p_14197_0_ * (p_14197_0_ * 6.0 - 15.0) + 10.0);
   }

   public static double m_144946_(double p_144946_0_) {
      return 30.0 * p_144946_0_ * p_144946_0_ * (p_144946_0_ - 1.0) * (p_144946_0_ - 1.0);
   }

   public static int m_14205_(double x) {
      if (x == 0.0) {
         return 0;
      } else {
         return x > 0.0 ? 1 : -1;
      }
   }

   public static float m_14189_(float p_14189_0_, float p_14189_1_, float p_14189_2_) {
      return p_14189_1_ + p_14189_0_ * m_14177_(p_14189_2_ - p_14189_1_);
   }

   public static double m_293415_(double p_293415_0_, double p_293415_2_, double p_293415_4_) {
      return p_293415_2_ + p_293415_0_ * m_14175_(p_293415_4_ - p_293415_2_);
   }

   public static float m_14156_(float p_14156_0_, float p_14156_1_) {
      return (Math.abs(p_14156_0_ % p_14156_1_ - p_14156_1_ * 0.5F) - p_14156_1_ * 0.25F) / (p_14156_1_ * 0.25F);
   }

   public static float m_14207_(float valueIn) {
      return valueIn * valueIn;
   }

   public static double m_144952_(double p_144952_0_) {
      return p_144952_0_ * p_144952_0_;
   }

   public static int m_144944_(int p_144944_0_) {
      return p_144944_0_ * p_144944_0_;
   }

   public static long m_184643_(long p_184643_0_) {
      return p_184643_0_ * p_184643_0_;
   }

   public static double m_144851_(double p_144851_0_, double p_144851_2_, double p_144851_4_, double p_144851_6_, double p_144851_8_) {
      return m_14085_(p_144851_6_, p_144851_8_, m_14112_(p_144851_0_, p_144851_2_, p_144851_4_));
   }

   public static float m_184631_(float p_184631_0_, float p_184631_1_, float p_184631_2_, float p_184631_3_, float p_184631_4_) {
      return m_144920_(p_184631_3_, p_184631_4_, m_184655_(p_184631_0_, p_184631_1_, p_184631_2_));
   }

   public static double m_144914_(double p_144914_0_, double p_144914_2_, double p_144914_4_, double p_144914_6_, double p_144914_8_) {
      return m_14139_(m_14112_(p_144914_0_, p_144914_2_, p_144914_4_), p_144914_6_, p_144914_8_);
   }

   public static float m_184637_(float p_184637_0_, float p_184637_1_, float p_184637_2_, float p_184637_3_, float p_184637_4_) {
      return m_14179_(m_184655_(p_184637_0_, p_184637_1_, p_184637_2_), p_184637_3_, p_184637_4_);
   }

   public static double m_144954_(double p_144954_0_) {
      return p_144954_0_ + (2.0 * RandomSource.m_216335_((long)m_14107_(p_144954_0_ * 3000.0)).m_188500_() - 1.0) * 1.0E-7 / 2.0;
   }

   public static int m_144941_(int p_144941_0_, int p_144941_1_) {
      return m_184652_(p_144941_0_, p_144941_1_) * p_144941_1_;
   }

   public static int m_184652_(int p_184652_0_, int p_184652_1_) {
      return -Math.floorDiv(-p_184652_0_, p_184652_1_);
   }

   public static int m_216287_(RandomSource p_216287_0_, int p_216287_1_, int p_216287_2_) {
      return p_216287_0_.m_188503_(p_216287_2_ - p_216287_1_ + 1) + p_216287_1_;
   }

   public static float m_216283_(RandomSource p_216283_0_, float p_216283_1_, float p_216283_2_) {
      return p_216283_0_.m_188501_() * (p_216283_2_ - p_216283_1_) + p_216283_1_;
   }

   public static float m_216291_(RandomSource p_216291_0_, float p_216291_1_, float p_216291_2_) {
      return p_216291_1_ + (float)p_216291_0_.m_188583_() * p_216291_2_;
   }

   public static double m_211589_(double p_211589_0_, double p_211589_2_) {
      return p_211589_0_ * p_211589_0_ + p_211589_2_ * p_211589_2_;
   }

   public static double m_184645_(double p_184645_0_, double p_184645_2_) {
      return Math.sqrt(m_211589_(p_184645_0_, p_184645_2_));
   }

   public static double m_211592_(double p_211592_0_, double p_211592_2_, double p_211592_4_) {
      return p_211592_0_ * p_211592_0_ + p_211592_2_ * p_211592_2_ + p_211592_4_ * p_211592_4_;
   }

   public static double m_184648_(double p_184648_0_, double p_184648_2_, double p_184648_4_) {
      return Math.sqrt(m_211592_(p_184648_0_, p_184648_2_, p_184648_4_));
   }

   public static float m_338503_(float p_338503_0_, float p_338503_1_, float p_338503_2_) {
      return p_338503_0_ * p_338503_0_ + p_338503_1_ * p_338503_1_ + p_338503_2_ * p_338503_2_;
   }

   public static int m_184628_(double p_184628_0_, int p_184628_2_) {
      return m_14107_(p_184628_0_ / (double)p_184628_2_) * p_184628_2_;
   }

   public static IntStream m_216295_(int p_216295_0_, int p_216295_1_, int p_216295_2_) {
      return m_216250_(p_216295_0_, p_216295_1_, p_216295_2_, 1);
   }

   public static IntStream m_216250_(int p_216250_0_, int p_216250_1_, int p_216250_2_, int p_216250_3_) {
      if (p_216250_1_ > p_216250_2_) {
         throw new IllegalArgumentException(
            String.m_12886_(Locale.ROOT, "upperbound %d expected to be > lowerBound %d", new Object[]{p_216250_2_, p_216250_1_})
         );
      } else if (p_216250_3_ < 1) {
         throw new IllegalArgumentException(String.m_12886_(Locale.ROOT, "steps expected to be >= 1, was %d", new Object[]{p_216250_3_}));
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
         }) : IntStream.m_274566_();
      }
   }

   public static Quaternionf m_305706_(Vector3f p_305706_0_, Quaternionf p_305706_1_, Quaternionf p_305706_2_) {
      float f = p_305706_0_.dot(p_305706_1_.ROT_90_Z_POS, p_305706_1_.INVERSION, p_305706_1_.INVERT_X);
      return p_305706_2_.set(p_305706_0_.ROT_90_Z_POS * f, p_305706_0_.INVERSION * f, p_305706_0_.INVERT_X * f, p_305706_1_.ROT_90_Z_NEG).normalize();
   }

   public static int m_320106_(Fraction p_320106_0_, int p_320106_1_) {
      return p_320106_0_.getNumerator() * p_320106_1_ / p_320106_0_.getDenominator();
   }

   static {
      for (int i = 0; i < 257; i++) {
         double d0 = (double)i / 256.0;
         double d1 = Math.asin(d0);
         f_14000_[i] = Math.cos(d1);
         f_13999_[i] = d1;
      }

      for (int s = 0; s < SIN_TABLE_FAST.length; s++) {
         SIN_TABLE_FAST[s] = MathUtils.roundToFloat(Math.sin((double)s * Math.PI * 2.0 / 4096.0));
      }
   }
}
