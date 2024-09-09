import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.src.C_137_;
import net.minecraft.src.C_141783_;
import net.minecraft.src.C_1625_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_182855_;
import net.minecraft.src.C_182953_;
import net.minecraft.src.C_182959_;
import net.minecraft.src.C_183079_;
import net.minecraft.src.C_19_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_2116_;
import net.minecraft.src.C_2147_;
import net.minecraft.src.C_2663_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4695_;
import net.minecraft.src.C_182875_.C_196108_;
import net.minecraft.src.C_206962_.C_206964_;
import net.minecraft.src.C_2187_.C_2188_;
import net.minecraft.src.C_2190_.C_2191_;
import net.minecraft.src.C_4675_.C_4681_;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;

public class Blender {
   private static final Blender a = new Blender(new Long2ObjectOpenHashMap(), new Long2ObjectOpenHashMap()) {
      @Override
      public Blender.a a(int p_207242_1_, int p_207242_2_) {
         return new Blender.a(1.0, 0.0);
      }

      @Override
      public double a(C_206964_ p_207103_1_, double p_207103_2_) {
         return p_207103_2_;
      }

      @Override
      public C_182855_ a(C_182855_ p_183383_1_) {
         return p_183383_1_;
      }
   };
   private static final C_2663_ b = C_2663_.m_230511_(new C_182953_(42L), C_183079_.f_254655_);
   private static final int c = C_141783_.m_175404_(7) - 1;
   private static final int d = C_141783_.m_175406_(c + 3);
   private static final int e = 2;
   private static final int f = C_141783_.m_175406_(5);
   private static final double g = 8.0;
   private final Long2ObjectOpenHashMap<C_182959_> h;
   private final Long2ObjectOpenHashMap<C_182959_> i;

   public static Blender a() {
      return a;
   }

   public static Blender a(@Nullable C_19_ p_190202_0_) {
      if (p_190202_0_ == null) {
         return a;
      } else {
         ChunkPos chunkpos = p_190202_0_.a();
         if (!p_190202_0_.a(chunkpos, d)) {
            return a;
         } else {
            Long2ObjectOpenHashMap<C_182959_> long2objectopenhashmap = new Long2ObjectOpenHashMap();
            Long2ObjectOpenHashMap<C_182959_> long2objectopenhashmap1 = new Long2ObjectOpenHashMap();
            int i = Mth.h(d + 1);

            for (int j = -d; j <= d; j++) {
               for (int k = -d; k <= d; k++) {
                  if (j * j + k * k <= i) {
                     int l = chunkpos.e + j;
                     int i1 = chunkpos.f + k;
                     C_182959_ blendingdata = C_182959_.m_190304_(p_190202_0_, l, i1);
                     if (blendingdata != null) {
                        long2objectopenhashmap.put(ChunkPos.c(l, i1), blendingdata);
                        if (j >= -f && j <= f && k >= -f && k <= f) {
                           long2objectopenhashmap1.put(ChunkPos.c(l, i1), blendingdata);
                        }
                     }
                  }
               }
            }

            return long2objectopenhashmap.isEmpty() && long2objectopenhashmap1.isEmpty() ? a : new Blender(long2objectopenhashmap, long2objectopenhashmap1);
         }
      }
   }

   Blender(Long2ObjectOpenHashMap<C_182959_> p_i202196_1_, Long2ObjectOpenHashMap<C_182959_> p_i202196_2_) {
      this.h = p_i202196_1_;
      this.i = p_i202196_2_;
   }

   public Blender.a a(int p_207242_1_, int p_207242_2_) {
      int i = C_141783_.m_175400_(p_207242_1_);
      int j = C_141783_.m_175400_(p_207242_2_);
      double d0 = this.a(i, 0, j, C_182959_::m_190285_);
      if (d0 != Double.MAX_VALUE) {
         return new Blender.a(0.0, a(d0));
      } else {
         MutableDouble mutabledouble = new MutableDouble(0.0);
         MutableDouble mutabledouble1 = new MutableDouble(0.0);
         MutableDouble mutabledouble2 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.h
            .forEach(
               (p_202243_5_, p_202243_6_) -> p_202243_6_.m_190295_(
                     C_141783_.m_175404_(ChunkPos.a(p_202243_5_)), C_141783_.m_175404_(ChunkPos.b(p_202243_5_)), (p_190193_5_, p_190193_6_, p_190193_7_) -> {
                        double d3 = Mth.f((double)(i - p_190193_5_), (double)(j - p_190193_6_));
                        if (!(d3 > (double)c)) {
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
            return new Blender.a(1.0, 0.0);
         } else {
            double d1 = mutabledouble1.doubleValue() / mutabledouble.doubleValue();
            double d2 = Mth.a(mutabledouble2.doubleValue() / (double)(c + 1), 0.0, 1.0);
            d2 = 3.0 * d2 * d2 - 2.0 * d2 * d2 * d2;
            return new Blender.a(d2, a(d1));
         }
      }
   }

   private static double a(double p_190154_0_) {
      double d0 = 1.0;
      double d1 = p_190154_0_ + 0.5;
      double d2 = Mth.c(d1, 8.0);
      return 1.0 * (32.0 * (d1 - 128.0) - 3.0 * (d1 - 120.0) * d2 + 3.0 * d2 * d2) / (128.0 * (32.0 - 3.0 * d2));
   }

   public double a(C_206964_ p_207103_1_, double p_207103_2_) {
      int i = C_141783_.m_175400_(p_207103_1_.m_207115_());
      int j = p_207103_1_.m_207114_() / 8;
      int k = C_141783_.m_175400_(p_207103_1_.m_207113_());
      double d0 = this.a(i, j, k, C_182959_::m_190333_);
      if (d0 != Double.MAX_VALUE) {
         return d0;
      } else {
         MutableDouble mutabledouble = new MutableDouble(0.0);
         MutableDouble mutabledouble1 = new MutableDouble(0.0);
         MutableDouble mutabledouble2 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.i
            .forEach(
               (p_202234_6_, p_202234_7_) -> p_202234_7_.m_190289_(
                     C_141783_.m_175404_(ChunkPos.a(p_202234_6_)),
                     C_141783_.m_175404_(ChunkPos.b(p_202234_6_)),
                     j - 1,
                     j + 1,
                     (p_202223_6_, p_202223_7_, p_202223_8_, p_202223_9_) -> {
                        double d3 = Mth.g((double)(i - p_202223_6_), (double)((j - p_202223_7_) * 2), (double)(k - p_202223_8_));
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
            double d2 = Mth.a(mutabledouble2.doubleValue() / 3.0, 0.0, 1.0);
            return Mth.d(d2, d1, p_207103_2_);
         }
      }
   }

   private double a(int p_190174_1_, int p_190174_2_, int p_190174_3_, Blender.b p_190174_4_) {
      int i = C_141783_.m_175406_(p_190174_1_);
      int j = C_141783_.m_175406_(p_190174_3_);
      boolean flag = (p_190174_1_ & 3) == 0;
      boolean flag1 = (p_190174_3_ & 3) == 0;
      double d0 = this.a(p_190174_4_, i, j, p_190174_1_, p_190174_2_, p_190174_3_);
      if (d0 == Double.MAX_VALUE) {
         if (flag && flag1) {
            d0 = this.a(p_190174_4_, i - 1, j - 1, p_190174_1_, p_190174_2_, p_190174_3_);
         }

         if (d0 == Double.MAX_VALUE) {
            if (flag) {
               d0 = this.a(p_190174_4_, i - 1, j, p_190174_1_, p_190174_2_, p_190174_3_);
            }

            if (d0 == Double.MAX_VALUE && flag1) {
               d0 = this.a(p_190174_4_, i, j - 1, p_190174_1_, p_190174_2_, p_190174_3_);
            }
         }
      }

      return d0;
   }

   private double a(Blender.b p_190211_1_, int p_190211_2_, int p_190211_3_, int p_190211_4_, int p_190211_5_, int p_190211_6_) {
      C_182959_ blendingdata = (C_182959_)this.h.get(ChunkPos.c(p_190211_2_, p_190211_3_));
      return blendingdata != null
         ? p_190211_1_.get(blendingdata, p_190211_4_ - C_141783_.m_175404_(p_190211_2_), p_190211_5_, p_190211_6_ - C_141783_.m_175404_(p_190211_3_))
         : Double.MAX_VALUE;
   }

   public C_182855_ a(C_182855_ p_183383_1_) {
      return (p_204667_2_, p_204667_3_, p_204667_4_, p_204667_5_) -> {
         C_203228_<C_1629_> holder = this.a(p_204667_2_, p_204667_3_, p_204667_4_);
         return holder == null ? p_183383_1_.m_203407_(p_204667_2_, p_204667_3_, p_204667_4_, p_204667_5_) : holder;
      };
   }

   @Nullable
   private C_203228_<C_1629_> a(int p_224706_1_, int p_224706_2_, int p_224706_3_) {
      MutableDouble mutabledouble = new MutableDouble(Double.POSITIVE_INFINITY);
      MutableObject<C_203228_<C_1629_>> mutableobject = new MutableObject();
      this.h
         .forEach(
            (p_224710_5_, p_224710_6_) -> p_224710_6_.m_224748_(
                  C_141783_.m_175404_(ChunkPos.a(p_224710_5_)),
                  p_224706_2_,
                  C_141783_.m_175404_(ChunkPos.b(p_224710_5_)),
                  (p_224718_4_, p_224718_5_, p_224718_6_) -> {
                     double d2 = Mth.f((double)(p_224706_1_ - p_224718_4_), (double)(p_224706_3_ - p_224718_5_));
                     if (!(d2 > (double)c) && d2 < mutabledouble.doubleValue()) {
                        mutableobject.setValue(p_224718_6_);
                        mutabledouble.setValue(d2);
                     }
                  }
               )
         );
      if (mutabledouble.doubleValue() == Double.POSITIVE_INFINITY) {
         return null;
      } else {
         double d0 = b.m_75380_((double)p_224706_1_, 0.0, (double)p_224706_3_) * 12.0;
         double d1 = Mth.a((mutabledouble.doubleValue() + d0) / (double)(c + 1), 0.0, 1.0);
         return d1 > 0.5 ? null : (C_203228_)mutableobject.getValue();
      }
   }

   public static void a(C_19_ p_197031_0_, C_2116_ p_197031_1_) {
      ChunkPos chunkpos = p_197031_1_.f();
      boolean flag = p_197031_1_.m_187675_();
      C_4681_ blockpos$mutableblockpos = new C_4681_();
      C_4675_ blockpos = new C_4675_(chunkpos.d(), 0, chunkpos.e());
      C_182959_ blendingdata = p_197031_1_.m_183407_();
      if (blendingdata != null) {
         int i = blendingdata.m_224743_().m_141937_();
         int j = blendingdata.m_224743_().m_151558_() - 1;
         if (flag) {
            for (int k = 0; k < 16; k++) {
               for (int l = 0; l < 16; l++) {
                  a(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, i - 1, l));
                  a(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, i, l));
                  a(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, j, l));
                  a(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, k, j + 1, l));
               }
            }
         }

         for (Direction direction : Direction.c.a) {
            if (p_197031_0_.m_6325_(chunkpos.e + direction.j(), chunkpos.f + direction.l()).m_187675_() != flag) {
               int i1 = direction == Direction.f ? 15 : 0;
               int j1 = direction == Direction.e ? 0 : 15;
               int k1 = direction == Direction.d ? 15 : 0;
               int l1 = direction == Direction.c ? 0 : 15;

               for (int i2 = i1; i2 <= j1; i2++) {
                  for (int j2 = k1; j2 <= l1; j2++) {
                     int k2 = Math.min(j, p_197031_1_.m_5885_(C_2191_.MOTION_BLOCKING, i2, j2)) + 1;

                     for (int l2 = i; l2 < k2; l2++) {
                        a(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, i2, l2, j2));
                     }
                  }
               }
            }
         }
      }
   }

   private static void a(C_2116_ p_197040_0_, C_4675_ p_197040_1_) {
      BlockState blockstate = p_197040_0_.a_(p_197040_1_);
      if (blockstate.m_204336_(C_137_.f_13035_)) {
         p_197040_0_.m_8113_(p_197040_1_);
      }

      C_2691_ fluidstate = p_197040_0_.m_6425_(p_197040_1_);
      if (!fluidstate.m_76178_()) {
         p_197040_0_.m_8113_(p_197040_1_);
      }
   }

   public static void a(C_1625_ p_197034_0_, C_2147_ p_197034_1_) {
      ChunkPos chunkpos = p_197034_1_.f();
      Builder<C_4695_, C_182959_> builder = ImmutableMap.builder();

      for (C_4695_ direction8 : C_4695_.values()) {
         int i = chunkpos.e + direction8.m_235697_();
         int j = chunkpos.f + direction8.m_235698_();
         C_182959_ blendingdata = p_197034_0_.m_6325_(i, j).m_183407_();
         if (blendingdata != null) {
            builder.put(direction8, blendingdata);
         }
      }

      ImmutableMap<C_4695_, C_182959_> immutablemap = builder.build();
      if (p_197034_1_.m_187675_() || !immutablemap.isEmpty()) {
         Blender.c blender$distancegetter = a(p_197034_1_.m_183407_(), immutablemap);
         C_196108_ carvingmask$mask = (p_202260_1_, p_202260_2_, p_202260_3_) -> {
            double d0 = (double)p_202260_1_ + 0.5 + b.m_75380_((double)p_202260_1_, (double)p_202260_2_, (double)p_202260_3_) * 4.0;
            double d1 = (double)p_202260_2_ + 0.5 + b.m_75380_((double)p_202260_2_, (double)p_202260_3_, (double)p_202260_1_) * 4.0;
            double d2 = (double)p_202260_3_ + 0.5 + b.m_75380_((double)p_202260_3_, (double)p_202260_1_, (double)p_202260_2_) * 4.0;
            return blender$distancegetter.getDistance(d0, d1, d2) < 4.0;
         };
         Stream.of(C_2188_.values()).map(p_197034_1_::m_183613_).forEach(p_202257_1_ -> p_202257_1_.m_196710_(carvingmask$mask));
      }
   }

   public static Blender.c a(@Nullable C_182959_ p_224726_0_, Map<C_4695_, C_182959_> p_224726_1_) {
      List<Blender.c> list = Lists.newArrayList();
      if (p_224726_0_ != null) {
         list.add(a(null, p_224726_0_));
      }

      p_224726_1_.forEach((p_224732_1_, p_224732_2_) -> list.add(a(p_224732_1_, p_224732_2_)));
      return (p_202265_1_, p_202265_3_, p_202265_5_) -> {
         double d0 = Double.POSITIVE_INFINITY;

         for (Blender.c blender$distancegetter : list) {
            double d1 = blender$distancegetter.getDistance(p_202265_1_, p_202265_3_, p_202265_5_);
            if (d1 < d0) {
               d0 = d1;
            }
         }

         return d0;
      };
   }

   private static Blender.c a(@Nullable C_4695_ p_224729_0_, C_182959_ p_224729_1_) {
      double d0 = 0.0;
      double d1 = 0.0;
      if (p_224729_0_ != null) {
         for (Direction direction : p_224729_0_.m_122593_()) {
            d0 += (double)(direction.j() * 16);
            d1 += (double)(direction.l() * 16);
         }
      }

      double d5 = d0;
      double d2 = d1;
      double d3 = (double)p_224729_1_.m_224743_().m_141928_() / 2.0;
      double d4 = (double)p_224729_1_.m_224743_().m_141937_() + d3;
      return (p_224698_8_, p_224698_10_, p_224698_12_) -> a(p_224698_8_ - 8.0 - d5, p_224698_10_ - d4, p_224698_12_ - 8.0 - d2, 8.0, d3, 8.0);
   }

   private static double a(double p_197024_0_, double p_197024_2_, double p_197024_4_, double p_197024_6_, double p_197024_8_, double p_197024_10_) {
      double d0 = Math.abs(p_197024_0_) - p_197024_6_;
      double d1 = Math.abs(p_197024_2_) - p_197024_8_;
      double d2 = Math.abs(p_197024_4_) - p_197024_10_;
      return Mth.g(Math.max(0.0, d0), Math.max(0.0, d1), Math.max(0.0, d2));
   }

   public static record a(double a, double b) {
   }

   interface b {
      double get(C_182959_ var1, int var2, int var3, int var4);
   }

   public interface c {
      double getDistance(double var1, double var3, double var5);
   }
}
