import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.src.C_140989_;
import net.minecraft.src.C_140993_;
import net.minecraft.src.C_182790_;
import net.minecraft.src.C_182885_;
import net.minecraft.src.C_2134_;
import net.minecraft.src.C_2135_;
import net.minecraft.src.C_2141_;
import net.minecraft.src.C_2143_;
import net.minecraft.src.C_2144_;
import net.minecraft.src.C_238161_;
import net.minecraft.src.C_290082_;
import net.minecraft.src.C_4698_;
import net.minecraft.src.C_4983_;
import net.minecraft.src.C_2143_.C_182878_;
import net.minecraft.src.C_238161_.C_238162_;
import net.minecraft.src.C_238161_.C_238164_;

public class PalettedContainer<T> implements C_2144_<T>, C_238161_<T> {
   private static final int a = 0;
   private final C_2144_<T> b = (p_198182_0_, p_198182_1_) -> 0;
   private final C_4698_<T> c;
   private volatile PalettedContainer.c<T> d;
   private final PalettedContainer.d e;
   private final C_140993_ f = new C_140993_("PalettedContainer");

   public void a() {
      this.f.m_199416_();
   }

   public void b() {
      this.f.m_199422_();
   }

   public static <T> Codec<PalettedContainer<T>> a(C_4698_<T> p_238371_0_, Codec<T> p_238371_1_, PalettedContainer.d p_238371_2_, T p_238371_3_) {
      C_238164_<T, PalettedContainer<T>> unpacker = PalettedContainer::a;
      return a(p_238371_0_, p_238371_1_, p_238371_2_, p_238371_3_, unpacker);
   }

   public static <T> Codec<C_238161_<T>> b(C_4698_<T> p_238418_0_, Codec<T> p_238418_1_, PalettedContainer.d p_238418_2_, T p_238418_3_) {
      C_238164_<T, C_238161_<T>> unpacker = (p_318427_0_, p_318427_1_, p_318427_2_) -> a(p_318427_0_, p_318427_1_, p_318427_2_).map(p_200428_0_ -> p_200428_0_);
      return a(p_238418_0_, p_238418_1_, p_238418_2_, p_238418_3_, unpacker);
   }

   private static <T, C extends C_238161_<T>> Codec<C> a(
      C_4698_<T> p_238427_0_, Codec<T> p_238427_1_, PalettedContainer.d p_238427_2_, T p_238427_3_, C_238164_<T, C> p_238427_4_
   ) {
      return RecordCodecBuilder.create(
            p_318428_2_ -> p_318428_2_.group(
                     p_238427_1_.mapResult(C_140989_.m_184381_(p_238427_3_)).listOf().fieldOf("palette").forGetter(C_238162_::f_238184_),
                     Codec.LONG_STREAM.lenientOptionalFieldOf("data").forGetter(C_238162_::f_238179_)
                  )
                  .apply(p_318428_2_, C_238162_::new)
         )
         .comapFlatMap(p_188078_3_ -> p_238427_4_.read(p_238427_0_, p_238427_2_, p_188078_3_), p_188071_2_ -> p_188071_2_.a(p_238427_0_, p_238427_2_));
   }

   public PalettedContainer(
      C_4698_<T> p_i188034_1_, PalettedContainer.d p_i188034_2_, PalettedContainer.a<T> p_i188034_3_, BitStorage p_i188034_4_, List<T> p_i188034_5_
   ) {
      this.c = p_i188034_1_;
      this.e = p_i188034_2_;
      this.d = new PalettedContainer.c<>(p_i188034_3_, p_i188034_4_, p_i188034_3_.a().m_188026_(p_i188034_3_.b(), p_i188034_1_, this, p_i188034_5_));
   }

   private PalettedContainer(C_4698_<T> p_i199927_1_, PalettedContainer.d p_i199927_2_, PalettedContainer.c<T> p_i199927_3_) {
      this.c = p_i199927_1_;
      this.e = p_i199927_2_;
      this.d = p_i199927_3_;
   }

   public PalettedContainer(C_4698_<T> p_i188040_1_, T p_i188040_2_, PalettedContainer.d p_i188040_3_) {
      this.e = p_i188040_3_;
      this.c = p_i188040_1_;
      this.d = this.a(null, 0);
      this.d.c.m_6796_(p_i188040_2_);
   }

   private PalettedContainer.c<T> a(@Nullable PalettedContainer.c<T> p_188051_1_, int p_188051_2_) {
      PalettedContainer.a<T> configuration = this.e.a(this.c, p_188051_2_);
      return p_188051_1_ != null && configuration.equals(p_188051_1_.c()) ? p_188051_1_ : configuration.a(this.c, this, this.e.a());
   }

   public int m_7248_(int p_7248_1_, T p_7248_2_) {
      PalettedContainer.c<T> data = this.d;
      PalettedContainer.c<T> data1 = this.a(data, p_7248_1_);
      data1.a(data.c, data.b);
      this.d = data1;
      return data1.c.m_6796_(p_7248_2_);
   }

   public T a(int x, int y, int z, T state) {
      this.a();

      Object object;
      try {
         object = this.a(this.e.a(x, y, z), state);
      } finally {
         this.b();
      }

      return (T)object;
   }

   public T b(int x, int y, int z, T state) {
      return this.a(this.e.a(x, y, z), state);
   }

   private T a(int index, T state) {
      int i = this.d.c.m_6796_(state);
      int j = this.d.b.a(index, i);
      return (T)this.d.c.m_5795_(j);
   }

   public void c(int p_156470_1_, int p_156470_2_, int p_156470_3_, T p_156470_4_) {
      this.a();

      try {
         this.b(this.e.a(p_156470_1_, p_156470_2_, p_156470_3_), p_156470_4_);
      } finally {
         this.b();
      }
   }

   private void b(int index, T state) {
      int i = this.d.c.m_6796_(state);
      this.d.b.b(index, i);
   }

   public T m_63087_(int x, int y, int z) {
      return this.a(this.e.a(x, y, z));
   }

   protected T a(int index) {
      PalettedContainer.c<T> data = this.d;
      return (T)data.c.m_5795_(data.b.a(index));
   }

   public void m_196879_(Consumer<T> p_196879_1_) {
      C_2143_<T> palette = this.d.e();
      IntSet intset = new IntArraySet();
      this.d.b.a(intset::add);
      intset.forEach(p_196885_2_ -> p_196879_1_.accept(palette.m_5795_(p_196885_2_)));
   }

   public void a(C_4983_ buf) {
      this.a();

      try {
         int i = buf.readByte();
         PalettedContainer.c<T> data = this.a(this.d, i);
         data.c.m_5680_(buf);
         buf.m_130105_(data.b.a());
         this.d = data;
      } finally {
         this.b();
      }
   }

   public void m_63135_(C_4983_ buf) {
      this.a();

      try {
         this.d.a(buf);
      } finally {
         this.b();
      }
   }

   private static <T> DataResult<PalettedContainer<T>> a(C_4698_<T> p_188067_0_, PalettedContainer.d p_188067_1_, C_238162_<T> p_188067_2_) {
      List<T> list = p_188067_2_.f_238184_();
      int i = p_188067_1_.a();
      int j = p_188067_1_.b(p_188067_0_, list.size());
      PalettedContainer.a<T> configuration = p_188067_1_.a(p_188067_0_, j);
      BitStorage bitstorage;
      if (j == 0) {
         bitstorage = new C_182790_(i);
      } else {
         Optional<LongStream> optional = p_188067_2_.f_238179_();
         if (optional.isEmpty()) {
            return DataResult.error(() -> "Missing values for non-zero storage");
         }

         long[] along = ((LongStream)optional.get()).toArray();

         try {
            if (configuration.a() == PalettedContainer.d.f) {
               C_2143_<T> palette = new C_2135_(p_188067_0_, j, (p_238277_0_, p_238277_1_) -> 0, list);
               SimpleBitStorage simplebitstorage = new SimpleBitStorage(j, i, along);
               int[] aint = new int[i];
               simplebitstorage.a(aint);
               a(aint, p_238280_2_ -> p_188067_0_.m_7447_(palette.m_5795_(p_238280_2_)));
               bitstorage = new SimpleBitStorage(configuration.b(), i, aint);
            } else {
               bitstorage = new SimpleBitStorage(configuration.b(), i, along);
            }
         } catch (SimpleBitStorage.a var13) {
            return DataResult.error(() -> "Failed to read PalettedContainer: " + var13.getMessage());
         }
      }

      return DataResult.success(new PalettedContainer<>(p_188067_0_, p_188067_1_, configuration, bitstorage, list));
   }

   public C_238162_<T> a(C_4698_<T> p_188064_1_, PalettedContainer.d p_188064_2_) {
      this.a();

      C_238162_ palettedcontainerro$packeddata;
      try {
         C_2135_<T> hashmappalette = new C_2135_(p_188064_1_, this.d.b.c(), this.b);
         int i = p_188064_2_.a();
         int[] aint = new int[i];
         this.d.b.a(aint);
         a(aint, p_198176_2_ -> hashmappalette.m_6796_(this.d.c.m_5795_(p_198176_2_)));
         int j = p_188064_2_.b(p_188064_1_, hashmappalette.m_62680_());
         Optional<LongStream> optional;
         if (j != 0) {
            SimpleBitStorage simplebitstorage = new SimpleBitStorage(j, i, aint);
            optional = Optional.of(Arrays.stream(simplebitstorage.a()));
         } else {
            optional = Optional.empty();
         }

         palettedcontainerro$packeddata = new C_238162_(hashmappalette.m_187917_(), optional);
      } finally {
         this.b();
      }

      return palettedcontainerro$packeddata;
   }

   private static <T> void a(int[] p_198189_0_, IntUnaryOperator p_198189_1_) {
      int i = -1;
      int j = -1;

      for (int k = 0; k < p_198189_0_.length; k++) {
         int l = p_198189_0_[k];
         if (l != i) {
            i = l;
            j = p_198189_1_.applyAsInt(l);
         }

         p_198189_0_[k] = j;
      }
   }

   public int m_63137_() {
      return this.d.a();
   }

   public boolean m_63109_(Predicate<T> p_63109_1_) {
      return this.d.c.m_6419_(p_63109_1_);
   }

   public PalettedContainer<T> d() {
      return new PalettedContainer<>(this.c, this.e, this.d.b());
   }

   public PalettedContainer<T> e() {
      return new PalettedContainer<>(this.c, (T)this.d.c.m_5795_(0), this.e);
   }

   public void a(PalettedContainer.b<T> countConsumerIn) {
      if (this.d.c.m_62680_() == 1) {
         countConsumerIn.accept((T)this.d.c.m_5795_(0), this.d.b.b());
      } else {
         Int2IntOpenHashMap int2intopenhashmap = new Int2IntOpenHashMap();
         this.d.b.a(p_198179_1_ -> int2intopenhashmap.addTo(p_198179_1_, 1));
         int2intopenhashmap.int2IntEntrySet()
            .forEach(p_63138_2_ -> countConsumerIn.accept((T)this.d.c.m_5795_(p_63138_2_.getIntKey()), p_63138_2_.getIntValue()));
      }
   }

   public void finish() {
      this.d.d().finish();
   }

   static record a<T>(C_182878_ a, int b) {
      public PalettedContainer.c<T> a(C_4698_<T> p_188091_1_, C_2144_<T> p_188091_2_, int p_188091_3_) {
         BitStorage bitstorage = (BitStorage)(this.b == 0 ? new C_182790_(p_188091_3_) : new SimpleBitStorage(this.b, p_188091_3_));
         C_2143_<T> palette = this.a.m_188026_(this.b, p_188091_1_, p_188091_2_, List.of());
         return new PalettedContainer.c<>(this, bitstorage, palette);
      }
   }

   @FunctionalInterface
   public interface b<T> {
      void accept(T var1, int var2);
   }

   static record c<T>(PalettedContainer.a<T> a, BitStorage b, C_2143_<T> c) {
      public void a(C_2143_<T> p_188111_1_, BitStorage p_188111_2_) {
         for (int i = 0; i < p_188111_2_.b(); i++) {
            T t = (T)p_188111_1_.m_5795_(p_188111_2_.a(i));
            this.b.b(i, this.c.m_6796_(t));
         }
      }

      public int a() {
         return 1 + this.c.m_6429_() + C_290082_.m_294521_(this.b.a().length) + this.b.a().length * 8;
      }

      public void a(C_4983_ p_188114_1_) {
         p_188114_1_.writeByte(this.b.c());
         this.c.m_5678_(p_188114_1_);
         p_188114_1_.m_130091_(this.b.a());
      }

      public PalettedContainer.c<T> b() {
         return new PalettedContainer.c<>(this.a, this.b.d(), this.c.m_199814_());
      }

      public PalettedContainer.a<T> c() {
         return this.a;
      }

      public BitStorage d() {
         return this.b;
      }

      public C_2143_<T> e() {
         return this.c;
      }
   }

   public abstract static class d {
      public static final C_182878_ a = C_182885_::m_188213_;
      public static final C_182878_ b = C_2141_::m_188019_;
      public static final C_182878_ c = C_2135_::m_187912_;
      static final C_182878_ f = C_2134_::m_187898_;
      public static final PalettedContainer.d d = new PalettedContainer.d(4) {
         @Override
         public <A> PalettedContainer.a<A> a(C_4698_<A> p_183248_1_, int p_183248_2_) {
            return switch (p_183248_2_) {
               case 0 -> new PalettedContainer.a(a, p_183248_2_);
               case 1, 2, 3, 4 -> new PalettedContainer.a(b, 4);
               case 5, 6, 7, 8 -> new PalettedContainer.a(c, p_183248_2_);
               default -> new PalettedContainer.a(PalettedContainer.d.f, Mth.e(p_183248_1_.m_13562_()));
            };
         }
      };
      public static final PalettedContainer.d e = new PalettedContainer.d(2) {
         @Override
         public <A> PalettedContainer.a<A> a(C_4698_<A> p_183248_1_, int p_183248_2_) {
            return switch (p_183248_2_) {
               case 0 -> new PalettedContainer.a(a, p_183248_2_);
               case 1, 2, 3 -> new PalettedContainer.a(b, p_183248_2_);
               default -> new PalettedContainer.a(PalettedContainer.d.f, Mth.e(p_183248_1_.m_13562_()));
            };
         }
      };
      private final int g;

      d(int p_i188142_1_) {
         this.g = p_i188142_1_;
      }

      public int a() {
         return 1 << this.g * 3;
      }

      public int a(int p_188145_1_, int p_188145_2_, int p_188145_3_) {
         return (p_188145_2_ << this.g | p_188145_3_) << this.g | p_188145_1_;
      }

      public abstract <A> PalettedContainer.a<A> a(C_4698_<A> var1, int var2);

      <A> int b(C_4698_<A> p_188151_1_, int p_188151_2_) {
         int i = Mth.e(p_188151_2_);
         PalettedContainer.a<A> configuration = this.a(p_188151_1_, i);
         return configuration.a() == f ? i : configuration.b();
      }
   }
}
