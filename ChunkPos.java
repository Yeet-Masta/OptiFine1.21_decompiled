import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4710_;

public class ChunkPos {
   private static final int g = 1056;
   public static final long a = c(1875066, 1875066);
   public static final ChunkPos b = new ChunkPos(0, 0);
   private static final long h = 32L;
   private static final long i = 4294967295L;
   private static final int j = 5;
   public static final int c = 32;
   private static final int k = 31;
   public static final int d = 31;
   public final int e;
   public final int f;
   private static final int l = 1664525;
   private static final int m = 1013904223;
   private static final int n = -559038737;
   private int cachedHashCode = 0;

   public ChunkPos(int x, int z) {
      this.e = x;
      this.f = z;
   }

   public ChunkPos(C_4675_ pos) {
      this.e = C_4710_.m_123171_(pos.m_123341_());
      this.f = C_4710_.m_123171_(pos.m_123343_());
   }

   public ChunkPos(long longIn) {
      this.e = (int)longIn;
      this.f = (int)(longIn >> 32);
   }

   public static ChunkPos a(int regionX, int regionZ) {
      return new ChunkPos(regionX << 5, regionZ << 5);
   }

   public static ChunkPos b(int regionX, int regionZ) {
      return new ChunkPos((regionX << 5) + 31, (regionZ << 5) + 31);
   }

   public long a() {
      return c(this.e, this.f);
   }

   public static long c(int x, int z) {
      return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
   }

   public static long a(C_4675_ posIn) {
      return c(C_4710_.m_123171_(posIn.m_123341_()), C_4710_.m_123171_(posIn.m_123343_()));
   }

   public static int a(long chunkAsLong) {
      return (int)(chunkAsLong & 4294967295L);
   }

   public static int b(long chunkAsLong) {
      return (int)(chunkAsLong >>> 32 & 4294967295L);
   }

   public int hashCode() {
      if (this.cachedHashCode != 0) {
         return this.cachedHashCode;
      } else {
         this.cachedHashCode = d(this.e, this.f);
         return this.cachedHashCode;
      }
   }

   public static int d(int x, int z) {
      int i = 1664525 * x + 1013904223;
      int j = 1664525 * (z ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ instanceof ChunkPos chunkpos ? this.e == chunkpos.e && this.f == chunkpos.f : false;
      }
   }

   public int b() {
      return this.a(8);
   }

   public int c() {
      return this.b(8);
   }

   public int d() {
      return C_4710_.m_123223_(this.e);
   }

   public int e() {
      return C_4710_.m_123223_(this.f);
   }

   public int f() {
      return this.a(15);
   }

   public int g() {
      return this.b(15);
   }

   public int h() {
      return this.e >> 5;
   }

   public int i() {
      return this.f >> 5;
   }

   public int j() {
      return this.e & 31;
   }

   public int k() {
      return this.f & 31;
   }

   public C_4675_ a(int xIn, int yIn, int zIn) {
      return new C_4675_(this.a(xIn), yIn, this.b(zIn));
   }

   public int a(int xIn) {
      return C_4710_.m_175554_(this.e, xIn);
   }

   public int b(int zIn) {
      return C_4710_.m_175554_(this.f, zIn);
   }

   public C_4675_ c(int yIn) {
      return new C_4675_(this.b(), yIn, this.c());
   }

   public String toString() {
      return "[" + this.e + ", " + this.f + "]";
   }

   public C_4675_ l() {
      return new C_4675_(this.d(), 0, this.e());
   }

   public int a(ChunkPos chunkPosIn) {
      return this.e(chunkPosIn.e, chunkPosIn.f);
   }

   public int e(int x, int z) {
      return Math.max(Math.abs(this.e - x), Math.abs(this.f - z));
   }

   public int b(ChunkPos chunkPosIn) {
      return this.f(chunkPosIn.e, chunkPosIn.f);
   }

   public int c(long longPosIn) {
      return this.f(a(longPosIn), b(longPosIn));
   }

   private int f(int xIn, int yIn) {
      int i = xIn - this.e;
      int j = yIn - this.f;
      return i * i + j * j;
   }

   public static Stream<ChunkPos> a(ChunkPos center, int radius) {
      return a(new ChunkPos(center.e - radius, center.f - radius), new ChunkPos(center.e + radius, center.f + radius));
   }

   public static Stream<ChunkPos> a(final ChunkPos start, final ChunkPos end) {
      int i = Math.abs(start.e - end.e) + 1;
      int j = Math.abs(start.f - end.f) + 1;
      final int k = start.e < end.e ? 1 : -1;
      final int l = start.f < end.f ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<ChunkPos>((long)(i * j), 64) {
         @Nullable
         private ChunkPos e;

         public boolean tryAdvance(Consumer<? super ChunkPos> p_tryAdvance_1_) {
            if (this.e == null) {
               this.e = start;
            } else {
               int i1 = this.e.e;
               int j1 = this.e.f;
               if (i1 == end.e) {
                  if (j1 == end.f) {
                     return false;
                  }

                  this.e = new ChunkPos(start.e, j1 + l);
               } else {
                  this.e = new ChunkPos(i1 + k, j1);
               }
            }

            p_tryAdvance_1_.accept(this.e);
            return true;
         }
      }, false);
   }
}
