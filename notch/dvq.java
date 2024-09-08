package net.minecraft.src;

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
import net.minecraft.src.C_2143_.C_182878_;
import net.minecraft.src.C_238161_.C_238162_;
import net.minecraft.src.C_238161_.C_238164_;

public class C_2145_<T> implements C_2144_<T>, C_238161_<T> {
   private static final int f_188031_ = 0;
   private final C_2144_<T> f_63070_ = (p_198182_0_, p_198182_1_) -> 0;
   private final C_4698_<T> f_63071_;
   private volatile C_2145_.C_182880_<T> f_188032_;
   private final C_2145_.C_182882_ f_188033_;
   private final C_140993_ f_199441_ = new C_140993_("PalettedContainer");

   public void m_63084_() {
      this.f_199441_.m_199416_();
   }

   public void m_63120_() {
      this.f_199441_.m_199422_();
   }

   public static <T> Codec<C_2145_<T>> m_238371_(C_4698_<T> p_238371_0_, Codec<T> p_238371_1_, C_2145_.C_182882_ p_238371_2_, T p_238371_3_) {
      C_238164_<T, C_2145_<T>> unpacker = C_2145_::m_188067_;
      return m_238427_(p_238371_0_, p_238371_1_, p_238371_2_, p_238371_3_, unpacker);
   }

   public static <T> Codec<C_238161_<T>> m_238418_(C_4698_<T> p_238418_0_, Codec<T> p_238418_1_, C_2145_.C_182882_ p_238418_2_, T p_238418_3_) {
      C_238164_<T, C_238161_<T>> unpacker = (p_318427_0_, p_318427_1_, p_318427_2_) -> m_188067_(p_318427_0_, p_318427_1_, p_318427_2_)
            .map(p_200428_0_ -> p_200428_0_);
      return m_238427_(p_238418_0_, p_238418_1_, p_238418_2_, p_238418_3_, unpacker);
   }

   private static <T, C extends C_238161_<T>> Codec<C> m_238427_(
      C_4698_<T> p_238427_0_, Codec<T> p_238427_1_, C_2145_.C_182882_ p_238427_2_, T p_238427_3_, C_238164_<T, C> p_238427_4_
   ) {
      return RecordCodecBuilder.create(
            p_318428_2_ -> p_318428_2_.group(
                     p_238427_1_.mapResult(C_140989_.m_184381_(p_238427_3_)).listOf().fieldOf("palette").forGetter(C_238162_::f_238184_),
                     Codec.LONG_STREAM.lenientOptionalFieldOf("data").forGetter(C_238162_::f_238179_)
                  )
                  .apply(p_318428_2_, C_238162_::new)
         )
         .comapFlatMap(
            p_188078_3_ -> p_238427_4_.m_238363_(p_238427_0_, p_238427_2_, p_188078_3_), p_188071_2_ -> p_188071_2_.m_188064_(p_238427_0_, p_238427_2_)
         );
   }

   public C_2145_(C_4698_<T> p_i188034_1_, C_2145_.C_182882_ p_i188034_2_, C_2145_.C_182879_<T> p_i188034_3_, C_163_ p_i188034_4_, List<T> p_i188034_5_) {
      this.f_63071_ = p_i188034_1_;
      this.f_188033_ = p_i188034_2_;
      this.f_188032_ = new C_2145_.C_182880_<>(
         p_i188034_3_, p_i188034_4_, p_i188034_3_.f_188085_().m_188026_(p_i188034_3_.f_188086_(), p_i188034_1_, this, p_i188034_5_)
      );
   }

   private C_2145_(C_4698_<T> p_i199927_1_, C_2145_.C_182882_ p_i199927_2_, C_2145_.C_182880_<T> p_i199927_3_) {
      this.f_63071_ = p_i199927_1_;
      this.f_188033_ = p_i199927_2_;
      this.f_188032_ = p_i199927_3_;
   }

   public C_2145_(C_4698_<T> p_i188040_1_, T p_i188040_2_, C_2145_.C_182882_ p_i188040_3_) {
      this.f_188033_ = p_i188040_3_;
      this.f_63071_ = p_i188040_1_;
      this.f_188032_ = this.m_188051_(null, 0);
      this.f_188032_.f_188102_.m_6796_(p_i188040_2_);
   }

   private C_2145_.C_182880_<T> m_188051_(@Nullable C_2145_.C_182880_<T> p_188051_1_, int p_188051_2_) {
      C_2145_.C_182879_<T> configuration = this.f_188033_.m_183248_(this.f_63071_, p_188051_2_);
      return p_188051_1_ != null && configuration.equals(p_188051_1_.f_188100_())
         ? p_188051_1_
         : configuration.m_188091_(this.f_63071_, this, this.f_188033_.m_188144_());
   }

   public int m_7248_(int p_7248_1_, T p_7248_2_) {
      C_2145_.C_182880_<T> data = this.f_188032_;
      C_2145_.C_182880_<T> data1 = this.m_188051_(data, p_7248_1_);
      data1.m_188111_(data.f_188102_, data.f_188101_);
      this.f_188032_ = data1;
      return data1.f_188102_.m_6796_(p_7248_2_);
   }

   public T m_63091_(int x, int y, int z, T state) {
      this.m_63084_();

      Object object;
      try {
         object = this.m_63096_(this.f_188033_.m_188145_(x, y, z), state);
      } finally {
         this.m_63120_();
      }

      return (T)object;
   }

   public T m_63127_(int x, int y, int z, T state) {
      return this.m_63096_(this.f_188033_.m_188145_(x, y, z), state);
   }

   private T m_63096_(int index, T state) {
      int i = this.f_188032_.f_188102_.m_6796_(state);
      int j = this.f_188032_.f_188101_.m_13516_(index, i);
      return (T)this.f_188032_.f_188102_.m_5795_(j);
   }

   public void m_156470_(int p_156470_1_, int p_156470_2_, int p_156470_3_, T p_156470_4_) {
      this.m_63084_();

      try {
         this.m_63132_(this.f_188033_.m_188145_(p_156470_1_, p_156470_2_, p_156470_3_), p_156470_4_);
      } finally {
         this.m_63120_();
      }
   }

   private void m_63132_(int index, T state) {
      int i = this.f_188032_.f_188102_.m_6796_(state);
      this.f_188032_.f_188101_.m_13524_(index, i);
   }

   public T m_63087_(int x, int y, int z) {
      return this.m_63085_(this.f_188033_.m_188145_(x, y, z));
   }

   protected T m_63085_(int index) {
      C_2145_.C_182880_<T> data = this.f_188032_;
      return (T)data.f_188102_.m_5795_(data.f_188101_.m_13514_(index));
   }

   public void m_196879_(Consumer<T> p_196879_1_) {
      C_2143_<T> palette = this.f_188032_.f_188102_();
      IntSet intset = new IntArraySet();
      this.f_188032_.f_188101_.m_13519_(intset::add);
      intset.forEach(p_196885_2_ -> p_196879_1_.accept(palette.m_5795_(p_196885_2_)));
   }

   public void m_63118_(C_4983_ buf) {
      this.m_63084_();

      try {
         int i = buf.readByte();
         C_2145_.C_182880_<T> data = this.m_188051_(this.f_188032_, i);
         data.f_188102_.m_5680_(buf);
         buf.m_130105_(data.f_188101_.m_13513_());
         this.f_188032_ = data;
      } finally {
         this.m_63120_();
      }
   }

   public void m_63135_(C_4983_ buf) {
      this.m_63084_();

      try {
         this.f_188032_.m_188114_(buf);
      } finally {
         this.m_63120_();
      }
   }

   private static <T> DataResult<C_2145_<T>> m_188067_(C_4698_<T> p_188067_0_, C_2145_.C_182882_ p_188067_1_, C_238162_<T> p_188067_2_) {
      List<T> list = p_188067_2_.f_238184_();
      int i = p_188067_1_.m_188144_();
      int j = p_188067_1_.m_188151_(p_188067_0_, list.size());
      C_2145_.C_182879_<T> configuration = p_188067_1_.m_183248_(p_188067_0_, j);
      C_163_ bitstorage;
      if (j == 0) {
         bitstorage = new C_182790_(i);
      } else {
         Optional<LongStream> optional = p_188067_2_.f_238179_();
         if (optional.isEmpty()) {
            return DataResult.error(() -> "Missing values for non-zero storage");
         }

         long[] along = ((LongStream)optional.get()).toArray();

         try {
            if (configuration.f_188085_() == C_2145_.C_182882_.f_188139_) {
               C_2143_<T> palette = new C_2135_(p_188067_0_, j, (p_238277_0_, p_238277_1_) -> 0, list);
               C_182786_ simplebitstorage = new C_182786_(j, i, along);
               int[] aint = new int[i];
               simplebitstorage.m_197970_(aint);
               m_198189_(aint, p_238280_2_ -> p_188067_0_.m_7447_(palette.m_5795_(p_238280_2_)));
               bitstorage = new C_182786_(configuration.f_188086_(), i, aint);
            } else {
               bitstorage = new C_182786_(configuration.f_188086_(), i, along);
            }
         } catch (C_182786_.C_182787_ var13) {
            return DataResult.error(() -> "Failed to read PalettedContainer: " + var13.getMessage());
         }
      }

      return DataResult.success(new C_2145_<>(p_188067_0_, p_188067_1_, configuration, bitstorage, list));
   }

   public C_238162_<T> m_188064_(C_4698_<T> p_188064_1_, C_2145_.C_182882_ p_188064_2_) {
      this.m_63084_();

      C_238162_ palettedcontainerro$packeddata;
      try {
         C_2135_<T> hashmappalette = new C_2135_(p_188064_1_, this.f_188032_.f_188101_.m_144604_(), this.f_63070_);
         int i = p_188064_2_.m_188144_();
         int[] aint = new int[i];
         this.f_188032_.f_188101_.m_197970_(aint);
         m_198189_(aint, p_198176_2_ -> hashmappalette.m_6796_(this.f_188032_.f_188102_.m_5795_(p_198176_2_)));
         int j = p_188064_2_.m_188151_(p_188064_1_, hashmappalette.m_62680_());
         Optional<LongStream> optional;
         if (j != 0) {
            C_182786_ simplebitstorage = new C_182786_(j, i, aint);
            optional = Optional.of(Arrays.stream(simplebitstorage.m_13513_()));
         } else {
            optional = Optional.empty();
         }

         palettedcontainerro$packeddata = new C_238162_(hashmappalette.m_187917_(), optional);
      } finally {
         this.m_63120_();
      }

      return palettedcontainerro$packeddata;
   }

   private static <T> void m_198189_(int[] p_198189_0_, IntUnaryOperator p_198189_1_) {
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
      return this.f_188032_.m_188107_();
   }

   public boolean m_63109_(Predicate<T> p_63109_1_) {
      return this.f_188032_.f_188102_.m_6419_(p_63109_1_);
   }

   public C_2145_<T> m_199931_() {
      return new C_2145_<>(this.f_63071_, this.f_188033_, this.f_188032_.m_238361_());
   }

   public C_2145_<T> m_238334_() {
      return new C_2145_<>(this.f_63071_, (T)this.f_188032_.f_188102_.m_5795_(0), this.f_188033_);
   }

   public void m_63099_(C_2145_.C_2146_<T> countConsumerIn) {
      if (this.f_188032_.f_188102_.m_62680_() == 1) {
         countConsumerIn.m_63144_((T)this.f_188032_.f_188102_.m_5795_(0), this.f_188032_.f_188101_.m_13521_());
      } else {
         Int2IntOpenHashMap int2intopenhashmap = new Int2IntOpenHashMap();
         this.f_188032_.f_188101_.m_13519_(p_198179_1_ -> int2intopenhashmap.addTo(p_198179_1_, 1));
         int2intopenhashmap.int2IntEntrySet()
            .forEach(p_63138_2_ -> countConsumerIn.m_63144_((T)this.f_188032_.f_188102_.m_5795_(p_63138_2_.getIntKey()), p_63138_2_.getIntValue()));
      }
   }

   public void finish() {
      this.f_188032_.f_188101_().finish();
   }

   static record C_182879_<T>(C_182878_ f_188085_, int f_188086_) {
      public C_2145_.C_182880_<T> m_188091_(C_4698_<T> p_188091_1_, C_2144_<T> p_188091_2_, int p_188091_3_) {
         C_163_ bitstorage = (C_163_)(this.f_188086_ == 0 ? new C_182790_(p_188091_3_) : new C_182786_(this.f_188086_, p_188091_3_));
         C_2143_<T> palette = this.f_188085_.m_188026_(this.f_188086_, p_188091_1_, p_188091_2_, List.of());
         return new C_2145_.C_182880_<>(this, bitstorage, palette);
      }
   }

   static record C_182880_<T>(C_2145_.C_182879_<T> f_188100_, C_163_ f_188101_, C_2143_<T> f_188102_) {
      public void m_188111_(C_2143_<T> p_188111_1_, C_163_ p_188111_2_) {
         for (int i = 0; i < p_188111_2_.m_13521_(); i++) {
            T t = (T)p_188111_1_.m_5795_(p_188111_2_.m_13514_(i));
            this.f_188101_.m_13524_(i, this.f_188102_.m_6796_(t));
         }
      }

      public int m_188107_() {
         return 1 + this.f_188102_.m_6429_() + C_290082_.m_294521_(this.f_188101_.m_13513_().length) + this.f_188101_.m_13513_().length * 8;
      }

      public void m_188114_(C_4983_ p_188114_1_) {
         p_188114_1_.writeByte(this.f_188101_.m_144604_());
         this.f_188102_.m_5678_(p_188114_1_);
         p_188114_1_.m_130091_(this.f_188101_.m_13513_());
      }

      public C_2145_.C_182880_<T> m_238361_() {
         return new C_2145_.C_182880_<>(this.f_188100_, this.f_188101_.m_199833_(), this.f_188102_.m_199814_());
      }
   }

   public abstract static class C_182882_ {
      public static final C_182878_ f_188134_ = C_182885_::m_188213_;
      public static final C_182878_ f_188135_ = C_2141_::m_188019_;
      public static final C_182878_ f_188136_ = C_2135_::m_187912_;
      static final C_182878_ f_188139_ = C_2134_::m_187898_;
      public static final C_2145_.C_182882_ f_188137_ = new C_2145_.C_182882_(4) {
         @Override
         public <A> C_2145_.C_182879_<A> m_183248_(C_4698_<A> p_183248_1_, int p_183248_2_) {
            return switch (p_183248_2_) {
               case 0 -> new C_2145_.C_182879_(f_188134_, p_183248_2_);
               case 1, 2, 3, 4 -> new C_2145_.C_182879_(f_188135_, 4);
               case 5, 6, 7, 8 -> new C_2145_.C_182879_(f_188136_, p_183248_2_);
               default -> new C_2145_.C_182879_(C_2145_.C_182882_.f_188139_, C_188_.m_14163_(p_183248_1_.m_13562_()));
            };
         }
      };
      public static final C_2145_.C_182882_ f_188138_ = new C_2145_.C_182882_(2) {
         @Override
         public <A> C_2145_.C_182879_<A> m_183248_(C_4698_<A> p_183248_1_, int p_183248_2_) {
            return switch (p_183248_2_) {
               case 0 -> new C_2145_.C_182879_(f_188134_, p_183248_2_);
               case 1, 2, 3 -> new C_2145_.C_182879_(f_188135_, p_183248_2_);
               default -> new C_2145_.C_182879_(C_2145_.C_182882_.f_188139_, C_188_.m_14163_(p_183248_1_.m_13562_()));
            };
         }
      };
      private final int f_188140_;

      C_182882_(int p_i188142_1_) {
         this.f_188140_ = p_i188142_1_;
      }

      public int m_188144_() {
         return 1 << this.f_188140_ * 3;
      }

      public int m_188145_(int p_188145_1_, int p_188145_2_, int p_188145_3_) {
         return (p_188145_2_ << this.f_188140_ | p_188145_3_) << this.f_188140_ | p_188145_1_;
      }

      public abstract <A> C_2145_.C_182879_<A> m_183248_(C_4698_<A> var1, int var2);

      <A> int m_188151_(C_4698_<A> p_188151_1_, int p_188151_2_) {
         int i = C_188_.m_14163_(p_188151_2_);
         C_2145_.C_182879_<A> configuration = this.m_183248_(p_188151_1_, i);
         return configuration.f_188085_() == f_188139_ ? i : configuration.f_188086_();
      }
   }

   @FunctionalInterface
   public interface C_2146_<T> {
      void m_63144_(T var1, int var2);
   }
}
