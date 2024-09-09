import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.optifine.util.ArrayCaches;
import org.apache.commons.lang3.Validate;

public class SimpleBitStorage implements BitStorage {
   private static final int[] a = new int[]{
      -1,
      -1,
      0,
      Integer.MIN_VALUE,
      0,
      0,
      1431655765,
      1431655765,
      0,
      Integer.MIN_VALUE,
      0,
      1,
      858993459,
      858993459,
      0,
      715827882,
      715827882,
      0,
      613566756,
      613566756,
      0,
      Integer.MIN_VALUE,
      0,
      2,
      477218588,
      477218588,
      0,
      429496729,
      429496729,
      0,
      390451572,
      390451572,
      0,
      357913941,
      357913941,
      0,
      330382099,
      330382099,
      0,
      306783378,
      306783378,
      0,
      286331153,
      286331153,
      0,
      Integer.MIN_VALUE,
      0,
      3,
      252645135,
      252645135,
      0,
      238609294,
      238609294,
      0,
      226050910,
      226050910,
      0,
      214748364,
      214748364,
      0,
      204522252,
      204522252,
      0,
      195225786,
      195225786,
      0,
      186737708,
      186737708,
      0,
      178956970,
      178956970,
      0,
      171798691,
      171798691,
      0,
      165191049,
      165191049,
      0,
      159072862,
      159072862,
      0,
      153391689,
      153391689,
      0,
      148102320,
      148102320,
      0,
      143165576,
      143165576,
      0,
      138547332,
      138547332,
      0,
      Integer.MIN_VALUE,
      0,
      4,
      130150524,
      130150524,
      0,
      126322567,
      126322567,
      0,
      122713351,
      122713351,
      0,
      119304647,
      119304647,
      0,
      116080197,
      116080197,
      0,
      113025455,
      113025455,
      0,
      110127366,
      110127366,
      0,
      107374182,
      107374182,
      0,
      104755299,
      104755299,
      0,
      102261126,
      102261126,
      0,
      99882960,
      99882960,
      0,
      97612893,
      97612893,
      0,
      95443717,
      95443717,
      0,
      93368854,
      93368854,
      0,
      91382282,
      91382282,
      0,
      89478485,
      89478485,
      0,
      87652393,
      87652393,
      0,
      85899345,
      85899345,
      0,
      84215045,
      84215045,
      0,
      82595524,
      82595524,
      0,
      81037118,
      81037118,
      0,
      79536431,
      79536431,
      0,
      78090314,
      78090314,
      0,
      76695844,
      76695844,
      0,
      75350303,
      75350303,
      0,
      74051160,
      74051160,
      0,
      72796055,
      72796055,
      0,
      71582788,
      71582788,
      0,
      70409299,
      70409299,
      0,
      69273666,
      69273666,
      0,
      68174084,
      68174084,
      0,
      Integer.MIN_VALUE,
      0,
      5
   };
   private long[] b;
   private final int c;
   private final long d;
   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final int i;
   private boolean dataFromCache = false;
   private static ArrayCaches dataCaches = new ArrayCaches(new int[]{256, 342, 410, 456}, long.class, 256);

   public SimpleBitStorage(int p_i198163_1_, int p_i198163_2_, int[] p_i198163_3_) {
      this(p_i198163_1_, p_i198163_2_);
      int j = 0;

      int i;
      for (i = 0; i <= p_i198163_2_ - this.f; i += this.f) {
         long k = 0L;

         for (int i1 = this.f - 1; i1 >= 0; i1--) {
            k <<= p_i198163_1_;
            k |= (long)p_i198163_3_[i + i1] & this.d;
         }

         this.b[j++] = k;
      }

      int k1 = p_i198163_2_ - i;
      if (k1 > 0) {
         long l = 0L;

         for (int j1 = k1 - 1; j1 >= 0; j1--) {
            l <<= p_i198163_1_;
            l |= (long)p_i198163_3_[i + j1] & this.d;
         }

         this.b[j] = l;
      }
   }

   public SimpleBitStorage(int p_i184716_1_, int p_i184716_2_) {
      this(p_i184716_1_, p_i184716_2_, (long[])null);
   }

   public SimpleBitStorage(int p_i184723_1_, int p_i184723_2_, @Nullable long[] p_i184723_3_) {
      Validate.inclusiveBetween(1L, 32L, (long)p_i184723_1_);
      this.e = p_i184723_2_;
      this.c = p_i184723_1_;
      this.d = (1L << p_i184723_1_) - 1L;
      this.f = (char)(64 / p_i184723_1_);
      int i = 3 * (this.f - 1);
      this.g = a[i + 0];
      this.h = a[i + 1];
      this.i = a[i + 2];
      int j = (p_i184723_2_ + this.f - 1) / this.f;
      if (p_i184723_3_ != null) {
         if (p_i184723_3_.length != j) {
            throw new SimpleBitStorage.a("Invalid length given for storage, got: " + p_i184723_3_.length + " but expected: " + j);
         }

         this.b = p_i184723_3_;
      } else {
         this.b = new long[j];
      }
   }

   private int b(int index) {
      long i = Integer.toUnsignedLong(this.g);
      long j = Integer.toUnsignedLong(this.h);
      return (int)((long)index * i + j >> 32 >> this.i);
   }

   @Override
   public int a(int index, int value) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)index);
      Validate.inclusiveBetween(0L, this.d, (long)value);
      int i = this.b(index);
      long j = this.b[i];
      int k = (index - i * this.f) * this.c;
      int l = (int)(j >> k & this.d);
      this.b[i] = j & ~(this.d << k) | ((long)value & this.d) << k;
      return l;
   }

   @Override
   public void b(int index, int value) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)index);
      Validate.inclusiveBetween(0L, this.d, (long)value);
      int i = this.b(index);
      long j = this.b[i];
      int k = (index - i * this.f) * this.c;
      this.b[i] = j & ~(this.d << k) | ((long)value & this.d) << k;
   }

   @Override
   public int a(int index) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)index);
      int i = this.b(index);
      long j = this.b[i];
      int k = (index - i * this.f) * this.c;
      return (int)(j >> k & this.d);
   }

   @Override
   public long[] a() {
      return this.b;
   }

   @Override
   public int b() {
      return this.e;
   }

   @Override
   public int c() {
      return this.c;
   }

   @Override
   public void a(IntConsumer consumer) {
      int i = 0;

      for (long j : this.b) {
         for (int k = 0; k < this.f; k++) {
            consumer.accept((int)(j & this.d));
            j >>= this.c;
            if (++i >= this.e) {
               return;
            }
         }
      }
   }

   @Override
   public void a(int[] arrayIn) {
      int i = this.b.length;
      int j = 0;

      for (int k = 0; k < i - 1; k++) {
         long l = this.b[k];

         for (int i1 = 0; i1 < this.f; i1++) {
            arrayIn[j + i1] = (int)(l & this.d);
            l >>= this.c;
         }

         j += this.f;
      }

      int j1 = this.e - j;
      if (j1 > 0) {
         long k1 = this.b[i - 1];

         for (int l1 = 0; l1 < j1; l1++) {
            arrayIn[j + l1] = (int)(k1 & this.d);
            k1 >>= this.c;
         }
      }
   }

   @Override
   public BitStorage d() {
      return new SimpleBitStorage(this.c, this.e, (long[])this.b.clone());
   }

   @Override
   public void finish() {
      if (this.dataFromCache) {
         dataCaches.free(this.b);
         this.b = null;
         this.dataFromCache = false;
      }
   }

   public static class a extends RuntimeException {
      a(String textIn) {
         super(textIn);
      }
   }
}
