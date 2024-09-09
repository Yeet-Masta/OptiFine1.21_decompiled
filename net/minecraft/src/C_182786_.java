package net.minecraft.src;

import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.optifine.util.ArrayCaches;
import org.apache.commons.lang3.Validate;

public class C_182786_ implements C_163_ {
   private static final int[] f_184706_ = new int[]{-1, -1, 0, Integer.MIN_VALUE, 0, 0, 1431655765, 1431655765, 0, Integer.MIN_VALUE, 0, 1, 858993459, 858993459, 0, 715827882, 715827882, 0, 613566756, 613566756, 0, Integer.MIN_VALUE, 0, 2, 477218588, 477218588, 0, 429496729, 429496729, 0, 390451572, 390451572, 0, 357913941, 357913941, 0, 330382099, 330382099, 0, 306783378, 306783378, 0, 286331153, 286331153, 0, Integer.MIN_VALUE, 0, 3, 252645135, 252645135, 0, 238609294, 238609294, 0, 226050910, 226050910, 0, 214748364, 214748364, 0, 204522252, 204522252, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 178956970, 178956970, 0, 171798691, 171798691, 0, 165191049, 165191049, 0, 159072862, 159072862, 0, 153391689, 153391689, 0, 148102320, 148102320, 0, 143165576, 143165576, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 126322567, 126322567, 0, 122713351, 122713351, 0, 119304647, 119304647, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 110127366, 110127366, 0, 107374182, 107374182, 0, 104755299, 104755299, 0, 102261126, 102261126, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 95443717, 95443717, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 89478485, 89478485, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 84215045, 84215045, 0, 82595524, 82595524, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 76695844, 76695844, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 71582788, 71582788, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 68174084, 68174084, 0, Integer.MIN_VALUE, 0, 5};
   private long[] f_184707_;
   private final int f_184708_;
   private final long f_184709_;
   private final int f_184710_;
   private final int f_184711_;
   private final int f_184712_;
   private final int f_184713_;
   private final int f_184714_;
   private boolean dataFromCache;
   private static ArrayCaches dataCaches;

   public C_182786_(int p_i198163_1_, int p_i198163_2_, int[] p_i198163_3_) {
      this(p_i198163_1_, p_i198163_2_);
      int j = 0;

      int i;
      for(i = 0; i <= p_i198163_2_ - this.f_184711_; i += this.f_184711_) {
         long k = 0L;

         for(int i1 = this.f_184711_ - 1; i1 >= 0; --i1) {
            k <<= p_i198163_1_;
            k |= (long)p_i198163_3_[i + i1] & this.f_184709_;
         }

         this.f_184707_[j++] = k;
      }

      int k1 = p_i198163_2_ - i;
      if (k1 > 0) {
         long l = 0L;

         for(int j1 = k1 - 1; j1 >= 0; --j1) {
            l <<= p_i198163_1_;
            l |= (long)p_i198163_3_[i + j1] & this.f_184709_;
         }

         this.f_184707_[j] = l;
      }

   }

   public C_182786_(int p_i184716_1_, int p_i184716_2_) {
      this(p_i184716_1_, p_i184716_2_, (long[])null);
   }

   public C_182786_(int p_i184723_1_, int p_i184723_2_, @Nullable long[] p_i184723_3_) {
      this.dataFromCache = false;
      Validate.inclusiveBetween(1L, 32L, (long)p_i184723_1_);
      this.f_184710_ = p_i184723_2_;
      this.f_184708_ = p_i184723_1_;
      this.f_184709_ = (1L << p_i184723_1_) - 1L;
      this.f_184711_ = (char)(64 / p_i184723_1_);
      int i = 3 * (this.f_184711_ - 1);
      this.f_184712_ = f_184706_[i + 0];
      this.f_184713_ = f_184706_[i + 1];
      this.f_184714_ = f_184706_[i + 2];
      int j = (p_i184723_2_ + this.f_184711_ - 1) / this.f_184711_;
      if (p_i184723_3_ != null) {
         if (p_i184723_3_.length != j) {
            throw new C_182787_("Invalid length given for storage, got: " + p_i184723_3_.length + " but expected: " + j);
         }

         this.f_184707_ = p_i184723_3_;
      } else {
         this.f_184707_ = new long[j];
      }

   }

   private int m_184739_(int index) {
      long i = Integer.toUnsignedLong(this.f_184712_);
      long j = Integer.toUnsignedLong(this.f_184713_);
      return (int)((long)index * i + j >> 32 >> this.f_184714_);
   }

   public int m_13516_(int index, int value) {
      Validate.inclusiveBetween(0L, (long)(this.f_184710_ - 1), (long)index);
      Validate.inclusiveBetween(0L, this.f_184709_, (long)value);
      int i = this.m_184739_(index);
      long j = this.f_184707_[i];
      int k = (index - i * this.f_184711_) * this.f_184708_;
      int l = (int)(j >> k & this.f_184709_);
      this.f_184707_[i] = j & ~(this.f_184709_ << k) | ((long)value & this.f_184709_) << k;
      return l;
   }

   public void m_13524_(int index, int value) {
      Validate.inclusiveBetween(0L, (long)(this.f_184710_ - 1), (long)index);
      Validate.inclusiveBetween(0L, this.f_184709_, (long)value);
      int i = this.m_184739_(index);
      long j = this.f_184707_[i];
      int k = (index - i * this.f_184711_) * this.f_184708_;
      this.f_184707_[i] = j & ~(this.f_184709_ << k) | ((long)value & this.f_184709_) << k;
   }

   public int m_13514_(int index) {
      Validate.inclusiveBetween(0L, (long)(this.f_184710_ - 1), (long)index);
      int i = this.m_184739_(index);
      long j = this.f_184707_[i];
      int k = (index - i * this.f_184711_) * this.f_184708_;
      return (int)(j >> k & this.f_184709_);
   }

   public long[] m_13513_() {
      return this.f_184707_;
   }

   public int m_13521_() {
      return this.f_184710_;
   }

   public int m_144604_() {
      return this.f_184708_;
   }

   public void m_13519_(IntConsumer consumer) {
      int i = 0;
      long[] var3 = this.f_184707_;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         long j = var3[var5];

         for(int k = 0; k < this.f_184711_; ++k) {
            consumer.accept((int)(j & this.f_184709_));
            j >>= this.f_184708_;
            ++i;
            if (i >= this.f_184710_) {
               return;
            }
         }
      }

   }

   public void m_197970_(int[] arrayIn) {
      int i = this.f_184707_.length;
      int j = 0;

      int j1;
      long k1;
      int l1;
      for(j1 = 0; j1 < i - 1; ++j1) {
         k1 = this.f_184707_[j1];

         for(l1 = 0; l1 < this.f_184711_; ++l1) {
            arrayIn[j + l1] = (int)(k1 & this.f_184709_);
            k1 >>= this.f_184708_;
         }

         j += this.f_184711_;
      }

      j1 = this.f_184710_ - j;
      if (j1 > 0) {
         k1 = this.f_184707_[i - 1];

         for(l1 = 0; l1 < j1; ++l1) {
            arrayIn[j + l1] = (int)(k1 & this.f_184709_);
            k1 >>= this.f_184708_;
         }
      }

   }

   public C_163_ m_199833_() {
      return new C_182786_(this.f_184708_, this.f_184710_, (long[])this.f_184707_.clone());
   }

   public void finish() {
      if (this.dataFromCache) {
         dataCaches.free(this.f_184707_);
         this.f_184707_ = null;
         this.dataFromCache = false;
      }

   }

   static {
      dataCaches = new ArrayCaches(new int[]{256, 342, 410, 456}, Long.TYPE, 256);
   }

   public static class C_182787_ extends RuntimeException {
      C_182787_(String textIn) {
         super(textIn);
      }
   }
}
