package net.minecraft.world.level.levelgen.blending;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.CarvingMask.Mask;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.FluidState;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;

public class Blender {
   private static final Blender f_190137_ = new Blender(new Long2ObjectOpenHashMap(), new Long2ObjectOpenHashMap()) {
      @Override
      public Blender.BlendingOutput m_207242_(int p_207242_1_, int p_207242_2_) {
         return new Blender.BlendingOutput(1.0, 0.0);
      }

      @Override
      public double m_207103_(FunctionContext p_207103_1_, double p_207103_2_) {
         return p_207103_2_;
      }

      @Override
      public BiomeResolver m_183383_(BiomeResolver p_183383_1_) {
         return p_183383_1_;
      }
   };
   private static final NormalNoise f_190138_ = NormalNoise.m_230511_(new XoroshiroRandomSource(42L), NoiseData.f_254655_);
   private static final int f_190139_ = QuartPos.m_175404_(7) - 1;
   private static final int f_190140_ = QuartPos.m_175406_(f_190139_ + 3);
   private static final int f_190141_ = 2;
   private static final int f_190142_ = QuartPos.m_175406_(5);
   private static final double f_197017_ = 8.0;
   private final Long2ObjectOpenHashMap<BlendingData> f_224696_;
   private final Long2ObjectOpenHashMap<BlendingData> f_224697_;

   public static Blender m_190153_() {
      return f_190137_;
   }

   public static Blender m_190202_(@Nullable WorldGenRegion p_190202_0_) {
      if (p_190202_0_ == null) {
         return f_190137_;
      } else {
         ChunkPos chunkpos = p_190202_0_.m_143488_();
         if (!p_190202_0_.m_215159_(chunkpos, f_190140_)) {
            return f_190137_;
         } else {
            Long2ObjectOpenHashMap<BlendingData> long2objectopenhashmap = new Long2ObjectOpenHashMap();
            Long2ObjectOpenHashMap<BlendingData> long2objectopenhashmap1 = new Long2ObjectOpenHashMap();
            int i = Mth.m_144944_(f_190140_ + 1);

            for (int j = -f_190140_; j <= f_190140_; j++) {
               for (int k = -f_190140_; k <= f_190140_; k++) {
                  if (j * j + k * k <= i) {
                     int l = chunkpos.f_45578_ + j;
                     int i1 = chunkpos.f_45579_ + k;
                     BlendingData blendingdata = BlendingData.m_190304_(p_190202_0_, l, i1);
                     if (blendingdata != null) {
                        long2objectopenhashmap.put(ChunkPos.m_45589_(l, i1), blendingdata);
                        if (j >= -f_190142_ && j <= f_190142_ && k >= -f_190142_ && k <= f_190142_) {
                           long2objectopenhashmap1.put(ChunkPos.m_45589_(l, i1), blendingdata);
                        }
                     }
                  }
               }
            }

            return long2objectopenhashmap.isEmpty() && long2objectopenhashmap1.isEmpty()
               ? f_190137_
               : new Blender(long2objectopenhashmap, long2objectopenhashmap1);
         }
      }
   }

   Blender(Long2ObjectOpenHashMap<BlendingData> p_i202196_1_, Long2ObjectOpenHashMap<BlendingData> p_i202196_2_) {
      this.f_224696_ = p_i202196_1_;
      this.f_224697_ = p_i202196_2_;
   }

   public Blender.BlendingOutput m_207242_(int p_207242_1_, int p_207242_2_) {
      int i = QuartPos.m_175400_(p_207242_1_);
      int j = QuartPos.m_175400_(p_207242_2_);
      double d0 = this.m_190174_(i, 0, j, BlendingData::m_190285_);
      if (d0 != Double.MAX_VALUE) {
         return new Blender.BlendingOutput(0.0, m_190154_(d0));
      } else {
         MutableDouble mutabledouble = new MutableDouble(0.0);
         MutableDouble mutabledouble1 = new MutableDouble(0.0);
         MutableDouble mutabledouble2 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.f_224696_
            .forEach(
               (p_202243_5_, p_202243_6_) -> p_202243_6_.m_190295_(
                     QuartPos.m_175404_(ChunkPos.m_45592_(p_202243_5_)),
                     QuartPos.m_175404_(ChunkPos.m_45602_(p_202243_5_)),
                     (p_190193_5_, p_190193_6_, p_190193_7_) -> {
                        double d3 = Mth.m_184645_((double)(i - p_190193_5_), (double)(j - p_190193_6_));
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
            return new Blender.BlendingOutput(1.0, 0.0);
         } else {
            double d1 = mutabledouble1.doubleValue() / mutabledouble.doubleValue();
            double d2 = Mth.m_14008_(mutabledouble2.doubleValue() / (double)(f_190139_ + 1), 0.0, 1.0);
            d2 = 3.0 * d2 * d2 - 2.0 * d2 * d2 * d2;
            return new Blender.BlendingOutput(d2, m_190154_(d1));
         }
      }
   }

   private static double m_190154_(double p_190154_0_) {
      double d0 = 1.0;
      double d1 = p_190154_0_ + 0.5;
      double d2 = Mth.m_14109_(d1, 8.0);
      return 1.0 * (32.0 * (d1 - 128.0) - 3.0 * (d1 - 120.0) * d2 + 3.0 * d2 * d2) / (128.0 * (32.0 - 3.0 * d2));
   }

   public double m_207103_(FunctionContext p_207103_1_, double p_207103_2_) {
      int i = QuartPos.m_175400_(p_207103_1_.m_207115_());
      int j = p_207103_1_.m_207114_() / 8;
      int k = QuartPos.m_175400_(p_207103_1_.m_207113_());
      double d0 = this.m_190174_(i, j, k, BlendingData::m_190333_);
      if (d0 != Double.MAX_VALUE) {
         return d0;
      } else {
         MutableDouble mutabledouble = new MutableDouble(0.0);
         MutableDouble mutabledouble1 = new MutableDouble(0.0);
         MutableDouble mutabledouble2 = new MutableDouble(Double.POSITIVE_INFINITY);
         this.f_224697_
            .forEach(
               (p_202234_6_, p_202234_7_) -> p_202234_7_.m_190289_(
                     QuartPos.m_175404_(ChunkPos.m_45592_(p_202234_6_)),
                     QuartPos.m_175404_(ChunkPos.m_45602_(p_202234_6_)),
                     j - 1,
                     j + 1,
                     (p_202223_6_, p_202223_7_, p_202223_8_, p_202223_9_) -> {
                        double d3 = Mth.m_184648_((double)(i - p_202223_6_), (double)((j - p_202223_7_) * 2), (double)(k - p_202223_8_));
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
            double d2 = Mth.m_14008_(mutabledouble2.doubleValue() / 3.0, 0.0, 1.0);
            return Mth.m_14139_(d2, d1, p_207103_2_);
         }
      }
   }

   private double m_190174_(int p_190174_1_, int p_190174_2_, int p_190174_3_, Blender.CellValueGetter p_190174_4_) {
      int i = QuartPos.m_175406_(p_190174_1_);
      int j = QuartPos.m_175406_(p_190174_3_);
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

   private double m_190211_(Blender.CellValueGetter p_190211_1_, int p_190211_2_, int p_190211_3_, int p_190211_4_, int p_190211_5_, int p_190211_6_) {
      BlendingData blendingdata = (BlendingData)this.f_224696_.get(ChunkPos.m_45589_(p_190211_2_, p_190211_3_));
      return blendingdata != null
         ? p_190211_1_.m_190233_(blendingdata, p_190211_4_ - QuartPos.m_175404_(p_190211_2_), p_190211_5_, p_190211_6_ - QuartPos.m_175404_(p_190211_3_))
         : Double.MAX_VALUE;
   }

   public BiomeResolver m_183383_(BiomeResolver p_183383_1_) {
      return (p_204667_2_, p_204667_3_, p_204667_4_, p_204667_5_) -> {
         Holder<Biome> holder = this.m_224706_(p_204667_2_, p_204667_3_, p_204667_4_);
         return holder == null ? p_183383_1_.m_203407_(p_204667_2_, p_204667_3_, p_204667_4_, p_204667_5_) : holder;
      };
   }

   @Nullable
   private Holder<Biome> m_224706_(int p_224706_1_, int p_224706_2_, int p_224706_3_) {
      MutableDouble mutabledouble = new MutableDouble(Double.POSITIVE_INFINITY);
      MutableObject<Holder<Biome>> mutableobject = new MutableObject();
      this.f_224696_
         .forEach(
            (p_224710_5_, p_224710_6_) -> p_224710_6_.m_224748_(
                  QuartPos.m_175404_(ChunkPos.m_45592_(p_224710_5_)),
                  p_224706_2_,
                  QuartPos.m_175404_(ChunkPos.m_45602_(p_224710_5_)),
                  (p_224718_4_, p_224718_5_, p_224718_6_) -> {
                     double d2 = Mth.m_184645_((double)(p_224706_1_ - p_224718_4_), (double)(p_224706_3_ - p_224718_5_));
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
         double d1 = Mth.m_14008_((mutabledouble.doubleValue() + d0) / (double)(f_190139_ + 1), 0.0, 1.0);
         return d1 > 0.5 ? null : (Holder)mutableobject.getValue();
      }
   }

   public static void m_197031_(WorldGenRegion p_197031_0_, ChunkAccess p_197031_1_) {
      ChunkPos chunkpos = p_197031_1_.m_7697_();
      boolean flag = p_197031_1_.m_187675_();
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();
      BlockPos blockpos = new BlockPos(chunkpos.m_45604_(), 0, chunkpos.m_45605_());
      BlendingData blendingdata = p_197031_1_.m_183407_();
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

         for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (p_197031_0_.m_6325_(chunkpos.f_45578_ + direction.m_122429_(), chunkpos.f_45579_ + direction.m_122431_()).m_187675_() != flag) {
               int i1 = direction == Direction.EAST ? 15 : 0;
               int j1 = direction == Direction.WEST ? 0 : 15;
               int k1 = direction == Direction.SOUTH ? 15 : 0;
               int l1 = direction == Direction.NORTH ? 0 : 15;

               for (int i2 = i1; i2 <= j1; i2++) {
                  for (int j2 = k1; j2 <= l1; j2++) {
                     int k2 = Math.min(j, p_197031_1_.m_5885_(Types.MOTION_BLOCKING, i2, j2)) + 1;

                     for (int l2 = i; l2 < k2; l2++) {
                        m_197040_(p_197031_1_, blockpos$mutableblockpos.m_122154_(blockpos, i2, l2, j2));
                     }
                  }
               }
            }
         }
      }
   }

   private static void m_197040_(ChunkAccess p_197040_0_, BlockPos p_197040_1_) {
      BlockState blockstate = p_197040_0_.m_8055_(p_197040_1_);
      if (blockstate.m_204336_(BlockTags.f_13035_)) {
         p_197040_0_.m_8113_(p_197040_1_);
      }

      FluidState fluidstate = p_197040_0_.m_6425_(p_197040_1_);
      if (!fluidstate.m_76178_()) {
         p_197040_0_.m_8113_(p_197040_1_);
      }
   }

   public static void m_197034_(WorldGenLevel p_197034_0_, ProtoChunk p_197034_1_) {
      ChunkPos chunkpos = p_197034_1_.m_7697_();
      Builder<Direction8, BlendingData> builder = ImmutableMap.builder();

      for (Direction8 direction8 : Direction8.values()) {
         int i = chunkpos.f_45578_ + direction8.m_235697_();
         int j = chunkpos.f_45579_ + direction8.m_235698_();
         BlendingData blendingdata = p_197034_0_.m_6325_(i, j).m_183407_();
         if (blendingdata != null) {
            builder.put(direction8, blendingdata);
         }
      }

      ImmutableMap<Direction8, BlendingData> immutablemap = builder.build();
      if (p_197034_1_.m_187675_() || !immutablemap.isEmpty()) {
         Blender.DistanceGetter blender$distancegetter = m_224726_(p_197034_1_.m_183407_(), immutablemap);
         Mask carvingmask$mask = (p_202260_1_, p_202260_2_, p_202260_3_) -> {
            double d0 = (double)p_202260_1_ + 0.5 + f_190138_.m_75380_((double)p_202260_1_, (double)p_202260_2_, (double)p_202260_3_) * 4.0;
            double d1 = (double)p_202260_2_ + 0.5 + f_190138_.m_75380_((double)p_202260_2_, (double)p_202260_3_, (double)p_202260_1_) * 4.0;
            double d2 = (double)p_202260_3_ + 0.5 + f_190138_.m_75380_((double)p_202260_3_, (double)p_202260_1_, (double)p_202260_2_) * 4.0;
            return blender$distancegetter.m_197061_(d0, d1, d2) < 4.0;
         };
         Stream.of(Carving.values()).map(p_197034_1_::m_183613_).forEach(p_202257_1_ -> p_202257_1_.m_196710_(carvingmask$mask));
      }
   }

   public static Blender.DistanceGetter m_224726_(@Nullable BlendingData p_224726_0_, Map<Direction8, BlendingData> p_224726_1_) {
      List<Blender.DistanceGetter> list = Lists.newArrayList();
      if (p_224726_0_ != null) {
         list.add(m_224729_(null, p_224726_0_));
      }

      p_224726_1_.forEach((p_224732_1_, p_224732_2_) -> list.add(m_224729_(p_224732_1_, p_224732_2_)));
      return (p_202265_1_, p_202265_3_, p_202265_5_) -> {
         double d0 = Double.POSITIVE_INFINITY;

         for (Blender.DistanceGetter blender$distancegetter : list) {
            double d1 = blender$distancegetter.m_197061_(p_202265_1_, p_202265_3_, p_202265_5_);
            if (d1 < d0) {
               d0 = d1;
            }
         }

         return d0;
      };
   }

   private static Blender.DistanceGetter m_224729_(@Nullable Direction8 p_224729_0_, BlendingData p_224729_1_) {
      double d0 = 0.0;
      double d1 = 0.0;
      if (p_224729_0_ != null) {
         for (Direction direction : p_224729_0_.m_122593_()) {
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
      return Mth.m_184648_(Math.max(0.0, d0), Math.max(0.0, d1), Math.max(0.0, d2));
   }

   public static record BlendingOutput(double f_209729_, double f_209730_) {
   }

   interface CellValueGetter {
      double m_190233_(BlendingData var1, int var2, int var3, int var4);
   }

   public interface DistanceGetter {
      double m_197061_(double var1, double var3, double var5);
   }
}
