package net.minecraft.src;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import javax.annotation.Nullable;

public class C_2145_ implements C_2144_, C_238161_ {
   private static final int f_188031_ = 0;
   private final C_2144_ f_63070_ = (p_198182_0_, p_198182_1_) -> {
      return 0;
   };
   private final C_4698_ f_63071_;
   private volatile C_182880_ f_188032_;
   private final C_182882_ f_188033_;
   private final C_140993_ f_199441_ = new C_140993_("PalettedContainer");

   public void m_63084_() {
      this.f_199441_.m_199416_();
   }

   public void m_63120_() {
      this.f_199441_.m_199422_();
   }

   public static Codec m_238371_(C_4698_ p_238371_0_, Codec p_238371_1_, C_182882_ p_238371_2_, Object p_238371_3_) {
      C_238161_.C_238164_ unpacker = C_2145_::m_188067_;
      return m_238427_(p_238371_0_, p_238371_1_, p_238371_2_, p_238371_3_, unpacker);
   }

   public static Codec m_238418_(C_4698_ p_238418_0_, Codec p_238418_1_, C_182882_ p_238418_2_, Object p_238418_3_) {
      C_238161_.C_238164_ unpacker = (p_318427_0_, p_318427_1_, p_318427_2_) -> {
         return m_188067_(p_318427_0_, p_318427_1_, p_318427_2_).map((p_200428_0_) -> {
            return p_200428_0_;
         });
      };
      return m_238427_(p_238418_0_, p_238418_1_, p_238418_2_, p_238418_3_, unpacker);
   }

   private static Codec m_238427_(C_4698_ p_238427_0_, Codec p_238427_1_, C_182882_ p_238427_2_, Object p_238427_3_, C_238161_.C_238164_ p_238427_4_) {
      return RecordCodecBuilder.create((p_318428_2_) -> {
         return p_318428_2_.group(p_238427_1_.mapResult(C_140989_.m_184381_(p_238427_3_)).listOf().fieldOf("palette").forGetter(C_238161_.C_238162_::f_238184_), Codec.LONG_STREAM.lenientOptionalFieldOf("data").forGetter(C_238161_.C_238162_::f_238179_)).apply(p_318428_2_, C_238161_.C_238162_::new);
      }).comapFlatMap((p_188078_3_) -> {
         return p_238427_4_.m_238363_(p_238427_0_, p_238427_2_, p_188078_3_);
      }, (p_188071_2_) -> {
         return p_188071_2_.m_188064_(p_238427_0_, p_238427_2_);
      });
   }

   public C_2145_(C_4698_ p_i188034_1_, C_182882_ p_i188034_2_, C_182879_ p_i188034_3_, C_163_ p_i188034_4_, List p_i188034_5_) {
      this.f_63071_ = p_i188034_1_;
      this.f_188033_ = p_i188034_2_;
      this.f_188032_ = new C_182880_(p_i188034_3_, p_i188034_4_, p_i188034_3_.f_188085_().m_188026_(p_i188034_3_.f_188086_(), p_i188034_1_, this, p_i188034_5_));
   }

   private C_2145_(C_4698_ p_i199927_1_, C_182882_ p_i199927_2_, C_182880_ p_i199927_3_) {
      this.f_63071_ = p_i199927_1_;
      this.f_188033_ = p_i199927_2_;
      this.f_188032_ = p_i199927_3_;
   }

   public C_2145_(C_4698_ p_i188040_1_, Object p_i188040_2_, C_182882_ p_i188040_3_) {
      this.f_188033_ = p_i188040_3_;
      this.f_63071_ = p_i188040_1_;
      this.f_188032_ = this.m_188051_((C_182880_)null, 0);
      this.f_188032_.f_188102_.m_6796_(p_i188040_2_);
   }

   private C_182880_ m_188051_(@Nullable C_182880_ p_188051_1_, int p_188051_2_) {
      C_182879_ configuration = this.f_188033_.m_183248_(this.f_63071_, p_188051_2_);
      return p_188051_1_ != null && configuration.equals(p_188051_1_.f_188100_()) ? p_188051_1_ : configuration.m_188091_(this.f_63071_, this, this.f_188033_.m_188144_());
   }

   public int m_7248_(int p_7248_1_, Object p_7248_2_) {
      C_182880_ data = this.f_188032_;
      C_182880_ data1 = this.m_188051_(data, p_7248_1_);
      data1.m_188111_(data.f_188102_, data.f_188101_);
      this.f_188032_ = data1;
      return data1.f_188102_.m_6796_(p_7248_2_);
   }

   public Object m_63091_(int x, int y, int z, Object state) {
      this.m_63084_();

      Object object;
      try {
         object = this.m_63096_(this.f_188033_.m_188145_(x, y, z), state);
      } finally {
         this.m_63120_();
      }

      return object;
   }

   public Object m_63127_(int x, int y, int z, Object state) {
      return this.m_63096_(this.f_188033_.m_188145_(x, y, z), state);
   }

   private Object m_63096_(int index, Object state) {
      int i = this.f_188032_.f_188102_.m_6796_(state);
      int j = this.f_188032_.f_188101_.m_13516_(index, i);
      return this.f_188032_.f_188102_.m_5795_(j);
   }

   public void m_156470_(int p_156470_1_, int p_156470_2_, int p_156470_3_, Object p_156470_4_) {
      this.m_63084_();

      try {
         this.m_63132_(this.f_188033_.m_188145_(p_156470_1_, p_156470_2_, p_156470_3_), p_156470_4_);
      } finally {
         this.m_63120_();
      }

   }

   private void m_63132_(int index, Object state) {
      int i = this.f_188032_.f_188102_.m_6796_(state);
      this.f_188032_.f_188101_.m_13524_(index, i);
   }

   public Object m_63087_(int x, int y, int z) {
      return this.m_63085_(this.f_188033_.m_188145_(x, y, z));
   }

   protected Object m_63085_(int index) {
      C_182880_ data = this.f_188032_;
      return data.f_188102_.m_5795_(data.f_188101_.m_13514_(index));
   }

   public void m_196879_(Consumer p_196879_1_) {
      C_2143_ palette = this.f_188032_.f_188102_();
      IntSet intset = new IntArraySet();
      C_163_ var10000 = this.f_188032_.f_188101_;
      Objects.requireNonNull(intset);
      var10000.m_13519_(intset::add);
      intset.forEach((p_196885_2_) -> {
         p_196879_1_.accept(palette.m_5795_(p_196885_2_));
      });
   }

   public void m_63118_(C_4983_ buf) {
      this.m_63084_();

      try {
         int i = buf.readByte();
         C_182880_ data = this.m_188051_(this.f_188032_, i);
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

   private static DataResult m_188067_(C_4698_ p_188067_0_, C_182882_ p_188067_1_, C_238161_.C_238162_ p_188067_2_) {
      List list = p_188067_2_.f_238184_();
      int i = p_188067_1_.m_188144_();
      int j = p_188067_1_.m_188151_(p_188067_0_, list.size());
      C_182879_ configuration = p_188067_1_.m_183248_(p_188067_0_, j);
      Object bitstorage;
      if (j == 0) {
         bitstorage = new C_182790_(i);
      } else {
         Optional optional = p_188067_2_.f_238179_();
         if (optional.isEmpty()) {
            return DataResult.error(() -> {
               return "Missing values for non-zero storage";
            });
         }

         long[] along = ((LongStream)optional.get()).toArray();

         try {
            if (configuration.f_188085_() == C_2145_.C_182882_.f_188139_) {
               C_2143_ palette = new C_2135_(p_188067_0_, j, (p_238277_0_, p_238277_1_) -> {
                  return 0;
               }, list);
               C_182786_ simplebitstorage = new C_182786_(j, i, along);
               int[] aint = new int[i];
               simplebitstorage.m_197970_(aint);
               m_198189_(aint, (p_238280_2_) -> {
                  return p_188067_0_.m_7447_(palette.m_5795_(p_238280_2_));
               });
               bitstorage = new C_182786_(configuration.f_188086_(), i, aint);
            } else {
               bitstorage = new C_182786_(configuration.f_188086_(), i, along);
            }
         } catch (C_182786_.C_182787_ var13) {
            return DataResult.error(() -> {
               return "Failed to read PalettedContainer: " + var13.getMessage();
            });
         }
      }

      return DataResult.success(new C_2145_(p_188067_0_, p_188067_1_, configuration, (C_163_)bitstorage, list));
   }

   public C_238161_.C_238162_ m_188064_(C_4698_ p_188064_1_, C_182882_ p_188064_2_) {
      this.m_63084_();

      C_238161_.C_238162_ palettedcontainerro$packeddata;
      try {
         C_2135_ hashmappalette = new C_2135_(p_188064_1_, this.f_188032_.f_188101_.m_144604_(), this.f_63070_);
         int i = p_188064_2_.m_188144_();
         int[] aint = new int[i];
         this.f_188032_.f_188101_.m_197970_(aint);
         m_198189_(aint, (p_198176_2_) -> {
            return hashmappalette.m_6796_(this.f_188032_.f_188102_.m_5795_(p_198176_2_));
         });
         int j = p_188064_2_.m_188151_(p_188064_1_, hashmappalette.m_62680_());
         Optional optional;
         if (j != 0) {
            C_182786_ simplebitstorage = new C_182786_(j, i, aint);
            optional = Optional.of(Arrays.stream(simplebitstorage.m_13513_()));
         } else {
            optional = Optional.empty();
         }

         palettedcontainerro$packeddata = new C_238161_.C_238162_(hashmappalette.m_187917_(), optional);
      } finally {
         this.m_63120_();
      }

      return palettedcontainerro$packeddata;
   }

   private static void m_198189_(int[] p_198189_0_, IntUnaryOperator p_198189_1_) {
      int i = -1;
      int j = -1;

      for(int k = 0; k < p_198189_0_.length; ++k) {
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

   public boolean m_63109_(Predicate p_63109_1_) {
      return this.f_188032_.f_188102_.m_6419_(p_63109_1_);
   }

   public C_2145_ m_199931_() {
      return new C_2145_(this.f_63071_, this.f_188033_, this.f_188032_.m_238361_());
   }

   public C_2145_ m_238334_() {
      return new C_2145_(this.f_63071_, this.f_188032_.f_188102_.m_5795_(0), this.f_188033_);
   }

   public void m_63099_(C_2146_ countConsumerIn) {
      if (this.f_188032_.f_188102_.m_62680_() == 1) {
         countConsumerIn.m_63144_(this.f_188032_.f_188102_.m_5795_(0), this.f_188032_.f_188101_.m_13521_());
      } else {
         Int2IntOpenHashMap int2intopenhashmap = new Int2IntOpenHashMap();
         this.f_188032_.f_188101_.m_13519_((p_198179_1_) -> {
            int2intopenhashmap.addTo(p_198179_1_, 1);
         });
         int2intopenhashmap.int2IntEntrySet().forEach((p_63138_2_) -> {
            countConsumerIn.m_63144_(this.f_188032_.f_188102_.m_5795_(p_63138_2_.getIntKey()), p_63138_2_.getIntValue());
         });
      }

   }

   public void finish() {
      this.f_188032_.f_188101_().finish();
   }

   public abstract static class C_182882_ {
      public static final C_2143_.C_182878_ f_188134_ = C_182885_::m_188213_;
      public static final C_2143_.C_182878_ f_188135_ = C_2141_::m_188019_;
      public static final C_2143_.C_182878_ f_188136_ = C_2135_::m_187912_;
      static final C_2143_.C_182878_ f_188139_ = C_2134_::m_187898_;
      public static final C_182882_ f_188137_ = new C_182882_(4) {
         public C_182879_ m_183248_(C_4698_ p_183248_1_, int p_183248_2_) {
            C_182879_ var10000;
            switch (p_183248_2_) {
               case 0:
                  var10000 = new C_182879_(f_188134_, p_183248_2_);
                  break;
               case 1:
               case 2:
               case 3:
               case 4:
                  var10000 = new C_182879_(f_188135_, 4);
                  break;
               case 5:
               case 6:
               case 7:
               case 8:
                  var10000 = new C_182879_(f_188136_, p_183248_2_);
                  break;
               default:
                  var10000 = new C_182879_(C_2145_.C_182882_.f_188139_, C_188_.m_14163_(p_183248_1_.m_13562_()));
            }

            return var10000;
         }
      };
      public static final C_182882_ f_188138_ = new C_182882_(2) {
         public C_182879_ m_183248_(C_4698_ p_183248_1_, int p_183248_2_) {
            C_182879_ var10000;
            switch (p_183248_2_) {
               case 0:
                  var10000 = new C_182879_(f_188134_, p_183248_2_);
                  break;
               case 1:
               case 2:
               case 3:
                  var10000 = new C_182879_(f_188135_, p_183248_2_);
                  break;
               default:
                  var10000 = new C_182879_(C_2145_.C_182882_.f_188139_, C_188_.m_14163_(p_183248_1_.m_13562_()));
            }

            return var10000;
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

      public abstract C_182879_ m_183248_(C_4698_ var1, int var2);

      int m_188151_(C_4698_ p_188151_1_, int p_188151_2_) {
         int i = C_188_.m_14163_(p_188151_2_);
         C_182879_ configuration = this.m_183248_(p_188151_1_, i);
         return configuration.f_188085_() == f_188139_ ? i : configuration.f_188086_();
      }
   }

   static record C_182880_(C_182879_ f_188100_, C_163_ f_188101_, C_2143_ f_188102_) {
      C_182880_(C_182879_ configuration, C_163_ storage, C_2143_ palette) {
         this.f_188100_ = configuration;
         this.f_188101_ = storage;
         this.f_188102_ = palette;
      }

      public void m_188111_(C_2143_ p_188111_1_, C_163_ p_188111_2_) {
         for(int i = 0; i < p_188111_2_.m_13521_(); ++i) {
            Object t = p_188111_1_.m_5795_(p_188111_2_.m_13514_(i));
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

      public C_182880_ m_238361_() {
         return new C_182880_(this.f_188100_, this.f_188101_.m_199833_(), this.f_188102_.m_199814_());
      }

      public C_182879_ f_188100_() {
         return this.f_188100_;
      }

      public C_163_ f_188101_() {
         return this.f_188101_;
      }

      public C_2143_ f_188102_() {
         return this.f_188102_;
      }
   }

   static record C_182879_(C_2143_.C_182878_ f_188085_, int f_188086_) {
      C_182879_(C_2143_.C_182878_ factory, int bits) {
         this.f_188085_ = factory;
         this.f_188086_ = bits;
      }

      public C_182880_ m_188091_(C_4698_ p_188091_1_, C_2144_ p_188091_2_, int p_188091_3_) {
         C_163_ bitstorage = this.f_188086_ == 0 ? new C_182790_(p_188091_3_) : new C_182786_(this.f_188086_, p_188091_3_);
         C_2143_ palette = this.f_188085_.m_188026_(this.f_188086_, p_188091_1_, p_188091_2_, List.of());
         return new C_182880_(this, (C_163_)bitstorage, palette);
      }

      public C_2143_.C_182878_ f_188085_() {
         return this.f_188085_;
      }

      public int f_188086_() {
         return this.f_188086_;
      }
   }

   @FunctionalInterface
   public interface C_2146_ {
      void m_63144_(Object var1, int var2);
   }
}
