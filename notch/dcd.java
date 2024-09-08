package net.minecraft.src;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public class C_1560_ {
   private static final int f_199440_ = 1056;
   public static final long f_45577_ = m_45589_(1875066, 1875066);
   public static final C_1560_ f_186419_ = new C_1560_(0, 0);
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

   public C_1560_(int x, int z) {
      this.f_45578_ = x;
      this.f_45579_ = z;
   }

   public C_1560_(C_4675_ pos) {
      this.f_45578_ = C_4710_.m_123171_(pos.u());
      this.f_45579_ = C_4710_.m_123171_(pos.w());
   }

   public C_1560_(long longIn) {
      this.f_45578_ = (int)longIn;
      this.f_45579_ = (int)(longIn >> 32);
   }

   public static C_1560_ m_220337_(int regionX, int regionZ) {
      return new C_1560_(regionX << 5, regionZ << 5);
   }

   public static C_1560_ m_220340_(int regionX, int regionZ) {
      return new C_1560_((regionX << 5) + 31, (regionZ << 5) + 31);
   }

   public long m_45588_() {
      return m_45589_(this.f_45578_, this.f_45579_);
   }

   public static long m_45589_(int x, int z) {
      return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
   }

   public static long m_151388_(C_4675_ posIn) {
      return m_45589_(C_4710_.m_123171_(posIn.u()), C_4710_.m_123171_(posIn.w()));
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
         return p_equals_1_ instanceof C_1560_ chunkpos ? this.f_45578_ == chunkpos.f_45578_ && this.f_45579_ == chunkpos.f_45579_ : false;
      }
   }

   public int m_151390_() {
      return this.m_151382_(8);
   }

   public int m_151393_() {
      return this.m_151391_(8);
   }

   public int m_45604_() {
      return C_4710_.m_123223_(this.f_45578_);
   }

   public int m_45605_() {
      return C_4710_.m_123223_(this.f_45579_);
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

   public C_4675_ m_151384_(int xIn, int yIn, int zIn) {
      return new C_4675_(this.m_151382_(xIn), yIn, this.m_151391_(zIn));
   }

   public int m_151382_(int xIn) {
      return C_4710_.m_175554_(this.f_45578_, xIn);
   }

   public int m_151391_(int zIn) {
      return C_4710_.m_175554_(this.f_45579_, zIn);
   }

   public C_4675_ m_151394_(int yIn) {
      return new C_4675_(this.m_151390_(), yIn, this.m_151393_());
   }

   public String toString() {
      return "[" + this.f_45578_ + ", " + this.f_45579_ + "]";
   }

   public C_4675_ m_45615_() {
      return new C_4675_(this.m_45604_(), 0, this.m_45605_());
   }

   public int m_45594_(C_1560_ chunkPosIn) {
      return this.m_340425_(chunkPosIn.f_45578_, chunkPosIn.f_45579_);
   }

   public int m_340425_(int x, int z) {
      return Math.max(Math.abs(this.f_45578_ - x), Math.abs(this.f_45579_ - z));
   }

   public int m_293627_(C_1560_ chunkPosIn) {
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

   public static Stream<C_1560_> m_45596_(C_1560_ center, int radius) {
      return m_45599_(new C_1560_(center.f_45578_ - radius, center.f_45579_ - radius), new C_1560_(center.f_45578_ + radius, center.f_45579_ + radius));
   }

   public static Stream<C_1560_> m_45599_(final C_1560_ start, final C_1560_ end) {
      int i = Math.abs(start.f_45578_ - end.f_45578_) + 1;
      int j = Math.abs(start.f_45579_ - end.f_45579_) + 1;
      final int k = start.f_45578_ < end.f_45578_ ? 1 : -1;
      final int l = start.f_45579_ < end.f_45579_ ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<C_1560_>((long)(i * j), 64) {
         @Nullable
         private C_1560_ f_45621_;

         public boolean tryAdvance(Consumer<? super C_1560_> p_tryAdvance_1_) {
            if (this.f_45621_ == null) {
               this.f_45621_ = start;
            } else {
               int i1 = this.f_45621_.f_45578_;
               int j1 = this.f_45621_.f_45579_;
               if (i1 == end.f_45578_) {
                  if (j1 == end.f_45579_) {
                     return false;
                  }

                  this.f_45621_ = new C_1560_(start.f_45578_, j1 + l);
               } else {
                  this.f_45621_ = new C_1560_(i1 + k, j1);
               }
            }

            p_tryAdvance_1_.accept(this.f_45621_);
            return true;
         }
      }, false);
   }
}
