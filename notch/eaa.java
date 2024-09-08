package net.minecraft.src;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.src.C_182875_.C_196108_;
import net.minecraft.src.C_206962_.C_206964_;
import net.minecraft.src.C_2187_.C_2188_;
import net.minecraft.src.C_2190_.C_2191_;
import net.minecraft.src.C_4675_.C_4681_;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;

public class C_182955_ {
   private static final C_182955_ f_190137_ = new C_182955_(new Long2ObjectOpenHashMap(), new Long2ObjectOpenHashMap()) {
      @Override
      public C_182955_.C_207020_ m_207242_(int p_207242_1_, int p_207242_2_) {
         return new C_182955_.C_207020_(1.0, 0.0);
      }

      @Override
      public double m_207103_(C_206964_ p_207103_1_, double p_207103_2_) {
         return p_207103_2_;
      }

      @Override
      public C_182855_ m_183383_(C_182855_ p_183383_1_) {
         return p_183383_1_;
      }
   };
   private static final C_2663_ f_190138_ = C_2663_.m_230511_(new C_182953_(42L), C_183079_.f_254655_);
   private static final int f_190139_ = C_141783_.m_175404_(7) - 1;
   private static final int f_190140_ = C_141783_.m_175406_(f_190139_ + 3);
   private static final int f_190141_ = 2;
   private static final int f_190142_ = C_141783_.m_175406_(5);
   private static final double f_197017_ = 8.0;
   private final Long2ObjectOpenHashMap<C_182959_> f_224696_;
   private final Long2ObjectOpenHashMap<C_182959_> f_224697_;

   public static C_182955_ m_190153_() {
      return f_190137_;
   }

   public static C_182955_ m_190202_(@Nullable C_19_ p_190202_0_) {
      if (p_190202_0_ == null) {
         return f_190137_;
      } else {
         C_1560_ chunkpos = p_190202_0_.m_143488_();
         if (!p_190202_0_.m_215159_(chunkpos, f_190140_)) {
            return f_190137_;
         } else {
            Long2ObjectOpenHashMap<C_182959_> long2objectopenhashmap = new Long2ObjectOpenHashMap();
            Long2ObjectOpenHashMap<C_182959_> long2objectopenhashmap1 = new Long2ObjectOpenHashMap();
            int i = C_188_.m_144944_(f_190140_ + 1);

            for (int j = -f_190140_; j <= f_190140_; j++) {
               for (int k = -f_190140_; k <= f_190140_; k++) {
                  if (j * j + k * k <= i) {
                     int l = chunkpos.f_45578_ + j;
                     int i1 = chunkpos.f_45579_ + k;
                     C_182959_ blendingdata = C_182959_.m_190304_(p_190202_0_, l, i1);
                     if (blendingdata != null) {
                        long2objectopenhashmap.put(C_1560_.m_45589_(l, i1), blendingdata);
                        if (j >= -f_190142_ && j <= f_190142_ && k >= -f_190142_ && k <= f_190142_) {
                           long2objectopenhashmap1.put(C_1560_.m_45589_(l, i1), blendingdata);
                        }
                     }
                  }
               }
            }

            return long2objectopenhashmap.isEmpty() && long2objectopenhashmap1.isEmpty()
               ? f_190137_
               : new C_182955_(long2objectopenhashmap, long2objectopenhashmap1);
         }
      }
   }

   C_182955_(Long2ObjectOpenHashMap<C_182959_> p_i202196_1_, Long2ObjectOpenHashMap<C_182959_> p_i202196_2_) {
      this.f_224696_ = p_i202196_1_;
      this.f_224697_ = p_i202196_2_;
   }

   public C_182955_.C_207020_ m_207242_(int p_207242_1_, int p_207242_2_) {
      int i = C_141783_.m_175400_(p_207242_1_);
      int j = C_141783_.m_175400_(p_207242_2_);
      double d0 = this.m_190174_(i, 0, j, C_182959_::m_190285_);
      if (d0 != Double.MAX_VALUE) {
         return new C_182955_.C_207020_(0.0, m_190154_(d0));
      } else {
         MutableDouble mutabledouble = new MutableDouble(0.0);
         MutableDouble mutabledouble1 = new MutableDouble(0.0);
         MutableDouble mutabledouble2 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.f_224696_
            .forEach(
               (p_202243_5_, p_202243_6_) -> p_202243_6_.m_190295_(
                     C_141783_.m_175404_(C_1560_.m_45592_(p_202243_5_)),
                     C_141783_.m_175404_(C_1560_.m_45602_(p_202243_5_)),
                     (p_190193_5_, p_190193_6_, p_190193_7_) -> {
                        double d3 = C_188_.m_184645_((double)(i - p_190193_5_), (double)(j - p_190193_6_));
                        if (!(d3 > (double)f_190139_)) {
                           if (d3 < mutabledouble2.doubleValue()) {
                              mutabledouble2.setValue(d3);
                           }

                           double d4 = 1.0 / (d3 * d3 * d3 * d3);
                           mutabledouble1.add(p_190193_7_ * d4);
                           mutabledouble.add(d4);
                        }
                     }
                  )
            );
         if (mutabledouble2.doubleValue() == Double.POSITIVE_INFINITY) {
            return new C_182955_.C_207020_(1.0, 0.0);
         } else {
            double d1 = mutabledouble1.doubleValue() / mutabledouble.doubleValue();
            double d2 = C_188_.m_14008_(mutabledouble2.doubleValue() / (double)(f_190139_ + 1), 0.0, 1.0);
            d2 = 3.0 * d2 * d2 - 2.0 * d2 * d2 * d2;
            return new C_182955_.C_207020_(d2, m_190154_(d1));
         }
      }
   }

   private static double m_190154_(double p_190154_0_) {
      double d0 = 1.0;
      double d1 = p_190154_0_ + 0.5;
      double d2 = C_188_.m_14109_(d1, 8.0);
      return 1.0 * (32.0 * (d1 - 128.0) - 3.0 * (d1 - 120.0) * d2 + 3.0 * d2 * d2) / (128.0 * (32.0 - 3.0 * d2));
   }

   public double m_207103_(C_206964_ p_207103_1_, double p_207103_2_) {
      int i = C_141783_.m_175400_(p_207103_1_.m_207115_());
      int j = p_207103_1_.m_207114_() / 8;
      int k = C_141783_.m_175400_(p_207103_1_.m_207113_());
      double d0 = this.m_190174_(i, j, k, C_182959_::m_190333_);
      if (d0 != Double.MAX_VALUE) {
         return d0;
      } else {
         MutableDouble mutabledouble = new MutableDouble(0.0);
         MutableDouble mutabledouble1 = new MutableDouble(0.0);
         MutableDouble mutabledouble2 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.f_224697_
            .forEach(
               (p_202234_6_, p_202234_7_) -> p_202234_7_.m_190289_(
                     C_141783_.m_175404_(C_1560_.m_45592_(p_202234_6_)),
                     C_141783_.m_175404_(C_1560_.m_45602_(p_202234_6_)),
                     j - 1,
                     j + 1,
                     (p_202223_6_, p_202223_7_, p_202223_8_, p_202223_9_) -> {
                        double d3 = C_188_.m_184648_((double)(i - p_202223_6_), (double)((j - p_202223_7_) * 2), (double)(k - p_202223_8_));
                        if (!(d3 > 2.0)) {
                           if (d3 < mutabledouble2.doubleValue()) {
                              mutabledouble2.setValue(d3);
                           }

                           double d4 = 1.0 / (d3 * d3 * d3 * d3);
                           mutabledouble1.add(p_202223_9_ * d4);
                           mutabledouble.add(d4);
                        }
                     }
                  )
            );
         if (mutabledouble2.doubleValue() == Double.POSITIVE_INFINITY) {
            return p_207103_2_;
         } else {
            double d1 = mutabledouble1.doubleValue() / mutabledouble.doubleValue();
            double d2 = C_188_.m_14008_(mutabledouble2.doubleValue() / 3.0, 0.0, 1.0);
            return C_188_.m_14139_(d2, d1, p_207103_2_);
         }
      }
   }

   private double m_190174_(int p_190174_1_, int p_190174_2_, int p_190174_3_, C_182955_.C_182957_ p_190174_4_) {
      int i = C_141783_.m_175406_(p_190174_1_);
      int j = C_141783_.m_175406_(p_190174_3_);
      boolean flag = (p_190174_1_ & 3) == 0;
      boolean flag1 = (p_190174_3_ & 3) == 0;
      double d0 = this.m_190211_(p_190174_4_, i, j, p_190174_1_, p_190174_2_, p_190174_3_);
      if (d0 == Double.MAX_VALUE) {
         if (flag && flag1) {
            d0 = this.m_190211_(p_190174_4_, i - 1, j - 1, p_190174_1_, p_190174_2_, p_190174_3_);
         }

         if (d0 == Double.MAX_VALUE) {
            if (flag) {
               d0 = this.m_190211_(p_190174_4_, i - 1, j, p_190174_1_, p_190174_2_, p_190174_3_);
            }

            if (d0 == Double.MAX_VALUE && flag1) {
               d0 = this.m_190211_(p_190174_4_, i, j - 1, p_190174_1_, p_190174_2_, p_190174_3_);
            }
         }
      }

      return d0;
   }

   private double m_190211_(C_182955_.C_182957_ p_190211_1_, int p_190211_2_, int p_190211_3_, int p_190211_4_, int p_190211_5_, int p_190211_6_) {
      C_182959_ blendingdata = (C_182959_)this.f_224696_.get(C_1560_.m_45589_(p_190211_2_, p_190211_3_));
      return blendingdata != null
         ? p_190211_1_.m_190233_(blendingdata, p_190211_4_ - C_141783_.m_175404_(p_190211_2_), p_190211_5_, p_190211_6_ - C_141783_.m_175404_(p_190211_3_))
         : Double.MAX_VALUE;
   }

   public C_182855_ m_183383_(C_182855_ p_183383_1_) {
      return (p_204667_2_, p_204667_3_, p_204667_4_, p_204667_5_) -> {
         C_203228_<C_1629_> holder = this.m_224706_(p_204667_2_, p_204667_3_, p_204667_4_);
         return holder == null ? p_183383_1_.m_203407_(p_204667_2_, p_204667_3_, p_204667_4_, p_204667_5_) : holder;
      };
   }

   @Nullable
   private C_203228_<C_1629_> m_224706_(int p_224706_1_, int p_224706_2_, int p_224706_3_) {
      MutableDouble mutabledouble = new MutableDouble(Double.POSITIVE_INFINITY);
      MutableObject<C_203228_<C_1629_>> mutableobject = new MutableObject();
      this.f_224696_
         .forEach(
            (p_224710_5_, p_224710_6_) -> p_224710_6_.m_224748_(
                  C_141783_.m_175404_(C_1560_.m_45592_(p_224710_5_)),
                  p_224706_2_,
                  C_141783_.m_175404_(C_1560_.m_45602_(p_224710_5_)),
                  (p_224718_4_, p_224718_5_, p_224718_6_) -> {
                     double d2 = C_188_.m_184645_((double)(p_224706_1_ - p_224718_4_), (double)(p_224706_3_ - p_224718_5_));
                     if (!(d2 > (double)f_190139_) && d2 < mutabledouble.doubleValue()) {
                        mutableobject.setValue(p_224718_6_);
                        mutabledouble.setValue(d2);
                     }
                  }
               )
         );
      if (mutabledouble.doubleValue() == Double.POSITIVE_INFINITY) {
         return null;
      } else {
         double d0 = f_190138_.m_75380_((double)p_224706_1_, 0.0, (double)p_224706_3_) * 12.0;
         double d1 = C_188_.m_14008_((mutabledouble.doubleValue() + d0) / (double)(f_190139_ + 1), 0.0, 1.0);
         return d1 > 0.5 ? null : (C_203228_)mutableobject.getValue();
      }
   }

   public static void m_197031_(C_19_ p_197031_0_, C_2116_ p_197031_1_) {
      C_1560_ chunkpos = p_197031_1_.m_7697_();
      boolean flag = p_197031_1_.m_187675_();
      C_4681_ blockpos$mutableblockpos = new C_4681_();
      C_4675_ blockpos = new C_4675_(chunkpos.m_45604_(), 0, chunkpos.m_45605_());
      C_182959_ blendingdata = p_197031_1_.m_183407_();
      if (blendingdata != null) {
         int i = blendingdata.m_224743_().m_141937_();
         int j = blendingdata.m_224743_().m_151558_() - 1;
         if (flag) {
            for (int k = 0; k < 16; k++) {
               for (int l = 0; l < 16; l++) {
                  m_197040_(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, i - 1, l));
                  m_197040_(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, i, l));
                  m_197040_(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, j, l));
                  m_197040_(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, j + 1, l));
               }
            }
         }

         for (C_4687_ direction : C_4687_.C_4694_.HORIZONTAL) {
            if (p_197031_0_.m_6325_(chunkpos.f_45578_ + direction.m_122429_(), chunkpos.f_45579_ + direction.m_122431_()).m_187675_() != flag) {
               int i1 = direction == C_4687_.EAST ? 15 : 0;
               int j1 = direction == C_4687_.WEST ? 0 : 15;
               int k1 = direction == C_4687_.SOUTH ? 15 : 0;
               int l1 = direction == C_4687_.NORTH ? 0 : 15;

               for (int i2 = i1; i2 <= j1; i2++) {
                  for (int j2 = k1; j2 <= l1; j2++) {
                     int k2 = Math.min(j, p_197031_1_.m_5885_(C_2191_.MOTION_BLOCKING, i2, j2)) + 1;

                     for (int l2 = i; l2 < k2; l2++) {
                        m_197040_(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, i2, l2, j2));
                     }
                  }
               }
            }
         }
      }
   }

   private static void m_197040_(C_2116_ p_197040_0_, C_4675_ p_197040_1_) {
      C_2064_ blockstate = p_197040_0_.a_(p_197040_1_);
      if (blockstate.m_204336_(C_137_.f_13035_)) {
         p_197040_0_.m_8113_(p_197040_1_);
      }

      C_2691_ fluidstate = p_197040_0_.b_(p_197040_1_);
      if (!fluidstate.m_76178_()) {
         p_197040_0_.m_8113_(p_197040_1_);
      }
   }

   public static void m_197034_(C_1625_ p_197034_0_, C_2147_ p_197034_1_) {
      C_1560_ chunkpos = p_197034_1_.f();
      Builder<C_4695_, C_182959_> builder = ImmutableMap.builder();

      for (C_4695_ direction8 : C_4695_.values()) {
         int i = chunkpos.f_45578_ + direction8.m_235697_();
         int j = chunkpos.f_45579_ + direction8.m_235698_();
         C_182959_ blendingdata = p_197034_0_.a(i, j).m_183407_();
         if (blendingdata != null) {
            builder.put(direction8, blendingdata);
         }
      }

      ImmutableMap<C_4695_, C_182959_> immutablemap = builder.build();
      if (p_197034_1_.s() || !immutablemap.isEmpty()) {
         C_182955_.C_196111_ blender$distancegetter = m_224726_(p_197034_1_.t(), immutablemap);
         C_196108_ carvingmask$mask = (p_202260_1_, p_202260_2_, p_202260_3_) -> {
            double d0 = (double)p_202260_1_ + 0.5 + f_190138_.m_75380_((double)p_202260_1_, (double)p_202260_2_, (double)p_202260_3_) * 4.0;
            double d1 = (double)p_202260_2_ + 0.5 + f_190138_.m_75380_((double)p_202260_2_, (double)p_202260_3_, (double)p_202260_1_) * 4.0;
            double d2 = (double)p_202260_3_ + 0.5 + f_190138_.m_75380_((double)p_202260_3_, (double)p_202260_1_, (double)p_202260_2_) * 4.0;
            return blender$distancegetter.m_197061_(d0, d1, d2) < 4.0;
         };
         Stream.of(C_2188_.values()).map(p_197034_1_::m_183613_).forEach(p_202257_1_ -> p_202257_1_.m_196710_(carvingmask$mask));
      }
   }

   public static C_182955_.C_196111_ m_224726_(@Nullable C_182959_ p_224726_0_, Map<C_4695_, C_182959_> p_224726_1_) {
      List<C_182955_.C_196111_> list = Lists.newArrayList();
      if (p_224726_0_ != null) {
         list.add(m_224729_(null, p_224726_0_));
      }

      p_224726_1_.forEach((p_224732_1_, p_224732_2_) -> list.add(m_224729_(p_224732_1_, p_224732_2_)));
      return (p_202265_1_, p_202265_3_, p_202265_5_) -> {
         double d0 = Double.POSITIVE_INFINITY;

         for (C_182955_.C_196111_ blender$distancegetter : list) {
            double d1 = blender$distancegetter.m_197061_(p_202265_1_, p_202265_3_, p_202265_5_);
            if (d1 < d0) {
               d0 = d1;
            }
         }

         return d0;
      };
   }

   private static C_182955_.C_196111_ m_224729_(@Nullable C_4695_ p_224729_0_, C_182959_ p_224729_1_) {
      double d0 = 0.0;
      double d1 = 0.0;
      if (p_224729_0_ != null) {
         for (C_4687_ direction : p_224729_0_.m_122593_()) {
            d0 += (double)(direction.m_122429_() * 16);
            d1 += (double)(direction.m_122431_() * 16);
         }
      }

      double d5 = d0;
      double d2 = d1;
      double d3 = (double)p_224729_1_.m_224743_().m_141928_() / 2.0;
      double d4 = (double)p_224729_1_.m_224743_().m_141937_() + d3;
      return (p_224698_8_, p_224698_10_, p_224698_12_) -> m_197024_(p_224698_8_ - 8.0 - d5, p_224698_10_ - d4, p_224698_12_ - 8.0 - d2, 8.0, d3, 8.0);
   }

   private static double m_197024_(double p_197024_0_, double p_197024_2_, double p_197024_4_, double p_197024_6_, double p_197024_8_, double p_197024_10_) {
      double d0 = Math.abs(p_197024_0_) - p_197024_6_;
      double d1 = Math.abs(p_197024_2_) - p_197024_8_;
      double d2 = Math.abs(p_197024_4_) - p_197024_10_;
      return C_188_.m_184648_(Math.max(0.0, d0), Math.max(0.0, d1), Math.max(0.0, d2));
   }

   interface C_182957_ {
      double m_190233_(C_182959_ var1, int var2, int var3, int var4);
   }

   public interface C_196111_ {
      double m_197061_(double var1, double var3, double var5);
   }

   public static record C_207020_(double f_209729_, double f_209730_) {
   }
}
