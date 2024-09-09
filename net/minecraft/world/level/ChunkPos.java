package net.minecraft.world.level;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public class ChunkPos {
   private static final int f_199440_ = 1056;
   public static final long f_45577_ = m_45589_(1875066, 1875066);
   public static final ChunkPos f_186419_ = new ChunkPos(0, 0);
   private static final long f_151375_ = 32L;
   private static final long f_151376_ = 4294967295L;
   private static final int f_151377_ = 5;
   public static final int f_220335_ = 32;
   private static final int f_151378_ = 31;
   public static final int f_220336_ = 31;
   public final int f_45578_;
   public final int f_45579_;
   private static final int f_151379_ = 1664525;
   private static final int f_151380_ = 1013904223;
   private static final int f_151381_ = -559038737;
   private int cachedHashCode = 0;

   public ChunkPos(int x, int z) {
      this.f_45578_ = x;
      this.f_45579_ = z;
   }

   public ChunkPos(BlockPos pos) {
      this.f_45578_ = SectionPos.m_123171_(pos.m_123341_());
      this.f_45579_ = SectionPos.m_123171_(pos.m_123343_());
   }

   public ChunkPos(long longIn) {
      this.f_45578_ = (int)longIn;
      this.f_45579_ = (int)(longIn >> 32);
   }

   public static ChunkPos m_220337_(int regionX, int regionZ) {
      return new ChunkPos(regionX << 5, regionZ << 5);
   }

   public static ChunkPos m_220340_(int regionX, int regionZ) {
      return new ChunkPos((regionX << 5) + 31, (regionZ << 5) + 31);
   }

   public long m_45588_() {
      return m_45589_(this.f_45578_, this.f_45579_);
   }

   public static long m_45589_(int x, int z) {
      return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
   }

   public static long m_151388_(BlockPos posIn) {
      return m_45589_(SectionPos.m_123171_(posIn.m_123341_()), SectionPos.m_123171_(posIn.m_123343_()));
   }

   public static int m_45592_(long chunkAsLong) {
      return (int)(chunkAsLong & 4294967295L);
   }

   public static int m_45602_(long chunkAsLong) {
      return (int)(chunkAsLong >>> 32 & 4294967295L);
   }

   public int hashCode() {
      if (this.cachedHashCode != 0) {
         return this.cachedHashCode;
      } else {
         this.cachedHashCode = m_220343_(this.f_45578_, this.f_45579_);
         return this.cachedHashCode;
      }
   }

   public static int m_220343_(int x, int z) {
      int i = 1664525 * x + 1013904223;
      int j = 1664525 * (z ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         boolean var10000;
         if (p_equals_1_ instanceof ChunkPos) {
            ChunkPos chunkpos = (ChunkPos)p_equals_1_;
            var10000 = this.f_45578_ == chunkpos.f_45578_ && this.f_45579_ == chunkpos.f_45579_;
         } else {
            var10000 = false;
         }

         return var10000;
      }
   }

   public int m_151390_() {
      return this.m_151382_(8);
   }

   public int m_151393_() {
      return this.m_151391_(8);
   }

   public int m_45604_() {
      return SectionPos.m_123223_(this.f_45578_);
   }

   public int m_45605_() {
      return SectionPos.m_123223_(this.f_45579_);
   }

   public int m_45608_() {
      return this.m_151382_(15);
   }

   public int m_45609_() {
      return this.m_151391_(15);
   }

   public int m_45610_() {
      return this.f_45578_ >> 5;
   }

   public int m_45612_() {
      return this.f_45579_ >> 5;
   }

   public int m_45613_() {
      return this.f_45578_ & 31;
   }

   public int m_45614_() {
      return this.f_45579_ & 31;
   }

   public BlockPos m_151384_(int xIn, int yIn, int zIn) {
      return new BlockPos(this.m_151382_(xIn), yIn, this.m_151391_(zIn));
   }

   public int m_151382_(int xIn) {
      return SectionPos.m_175554_(this.f_45578_, xIn);
   }

   public int m_151391_(int zIn) {
      return SectionPos.m_175554_(this.f_45579_, zIn);
   }

   public BlockPos m_151394_(int yIn) {
      return new BlockPos(this.m_151390_(), yIn, this.m_151393_());
   }

   public String toString() {
      return "[" + this.f_45578_ + ", " + this.f_45579_ + "]";
   }

   public BlockPos m_45615_() {
      return new BlockPos(this.m_45604_(), 0, this.m_45605_());
   }

   public int m_45594_(ChunkPos chunkPosIn) {
      return this.m_340425_(chunkPosIn.f_45578_, chunkPosIn.f_45579_);
   }

   public int m_340425_(int x, int z) {
      return Math.max(Math.abs(this.f_45578_ - x), Math.abs(this.f_45579_ - z));
   }

   public int m_293627_(ChunkPos chunkPosIn) {
      return this.m_292874_(chunkPosIn.f_45578_, chunkPosIn.f_45579_);
   }

   public int m_294557_(long longPosIn) {
      return this.m_292874_(m_45592_(longPosIn), m_45602_(longPosIn));
   }

   private int m_292874_(int xIn, int yIn) {
      int i = xIn - this.f_45578_;
      int j = yIn - this.f_45579_;
      return i * i + j * j;
   }

   public static Stream m_45596_(ChunkPos center, int radius) {
      return m_45599_(new ChunkPos(center.f_45578_ - radius, center.f_45579_ - radius), new ChunkPos(center.f_45578_ + radius, center.f_45579_ + radius));
   }

   public static Stream m_45599_(final ChunkPos start, final ChunkPos end) {
      int i = Math.abs(start.f_45578_ - end.f_45578_) + 1;
      int j = Math.abs(start.f_45579_ - end.f_45579_) + 1;
      final int k = start.f_45578_ < end.f_45578_ ? 1 : -1;
      final int l = start.f_45579_ < end.f_45579_ ? 1 : -1;
      return StreamSupport.stream(new Spliterators.AbstractSpliterator((long)(i * j), 64) {
         @Nullable
         private ChunkPos f_45621_;

         public boolean tryAdvance(Consumer p_tryAdvance_1_) {
            if (this.f_45621_ == null) {
               this.f_45621_ = start;
            } else {
               int i1 = this.f_45621_.f_45578_;
               int j1 = this.f_45621_.f_45579_;
               if (i1 == end.f_45578_) {
                  if (j1 == end.f_45579_) {
                     return false;
                  }

                  this.f_45621_ = new ChunkPos(start.f_45578_, j1 + l);
               } else {
                  this.f_45621_ = new ChunkPos(i1 + k, j1);
               }
            }

            p_tryAdvance_1_.accept(this.f_45621_);
            return true;
         }
      }, false);
   }
}
