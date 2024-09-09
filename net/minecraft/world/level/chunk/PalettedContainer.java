package net.minecraft.world.level.chunk;

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
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.ThreadingDetector;
import net.minecraft.util.ZeroBitStorage;
import net.minecraft.world.level.chunk.Palette.Factory;
import net.minecraft.world.level.chunk.PalettedContainerRO.PackedData;
import net.minecraft.world.level.chunk.PalettedContainerRO.Unpacker;

public class PalettedContainer<T> implements PaletteResize<T>, PalettedContainerRO<T> {
   private static final int f_188031_ = 0;
   private final PaletteResize<T> f_63070_ = (p_198182_0_, p_198182_1_) -> 0;
   private final IdMap<T> f_63071_;
   private volatile net.minecraft.world.level.chunk.PalettedContainer.Data<T> f_188032_;
   private final net.minecraft.world.level.chunk.PalettedContainer.Strategy f_188033_;
   private final ThreadingDetector f_199441_ = new ThreadingDetector("PalettedContainer");

   public void m_63084_() {
      this.f_199441_.m_199416_();
   }

   public void m_63120_() {
      this.f_199441_.m_199422_();
   }

   public static <T> Codec<net.minecraft.world.level.chunk.PalettedContainer<T>> m_238371_(
      IdMap<T> p_238371_0_, Codec<T> p_238371_1_, net.minecraft.world.level.chunk.PalettedContainer.Strategy p_238371_2_, T p_238371_3_
   ) {
      Unpacker<T, net.minecraft.world.level.chunk.PalettedContainer<T>> unpacker = net.minecraft.world.level.chunk.PalettedContainer::m_188067_;
      return m_238427_(p_238371_0_, p_238371_1_, p_238371_2_, p_238371_3_, unpacker);
   }

   public static <T> Codec<PalettedContainerRO<T>> m_238418_(
      IdMap<T> p_238418_0_, Codec<T> p_238418_1_, net.minecraft.world.level.chunk.PalettedContainer.Strategy p_238418_2_, T p_238418_3_
   ) {
      Unpacker<T, PalettedContainerRO<T>> unpacker = (p_318427_0_, p_318427_1_, p_318427_2_) -> m_188067_(p_318427_0_, p_318427_1_, p_318427_2_)
            .map(p_200428_0_ -> p_200428_0_);
      return m_238427_(p_238418_0_, p_238418_1_, p_238418_2_, p_238418_3_, unpacker);
   }

   private static <T, C extends PalettedContainerRO<T>> Codec<C> m_238427_(
      IdMap<T> p_238427_0_,
      Codec<T> p_238427_1_,
      net.minecraft.world.level.chunk.PalettedContainer.Strategy p_238427_2_,
      T p_238427_3_,
      Unpacker<T, C> p_238427_4_
   ) {
      return RecordCodecBuilder.create(
            p_318428_2_ -> p_318428_2_.group(
                     p_238427_1_.mapResult(ExtraCodecs.m_184381_(p_238427_3_)).listOf().fieldOf("palette").forGetter(PackedData::f_238184_),
                     Codec.LONG_STREAM.lenientOptionalFieldOf("data").forGetter(PackedData::f_238179_)
                  )
                  .apply(p_318428_2_, PackedData::new)
         )
         .comapFlatMap(
            p_188078_3_ -> p_238427_4_.m_238363_(p_238427_0_, p_238427_2_, p_188078_3_), p_188071_2_ -> p_188071_2_.m_188064_(p_238427_0_, p_238427_2_)
         );
   }

   public PalettedContainer(
      IdMap<T> p_i188034_1_,
      net.minecraft.world.level.chunk.PalettedContainer.Strategy p_i188034_2_,
      net.minecraft.world.level.chunk.PalettedContainer.Configuration<T> p_i188034_3_,
      net.minecraft.util.BitStorage p_i188034_4_,
      List<T> p_i188034_5_
   ) {
      this.f_63071_ = p_i188034_1_;
      this.f_188033_ = p_i188034_2_;
      this.f_188032_ = new net.minecraft.world.level.chunk.PalettedContainer.Data<>(
         p_i188034_3_, p_i188034_4_, p_i188034_3_.f_188085_().m_188026_(p_i188034_3_.f_188086_(), p_i188034_1_, this, p_i188034_5_)
      );
   }

   private PalettedContainer(
      IdMap<T> p_i199927_1_,
      net.minecraft.world.level.chunk.PalettedContainer.Strategy p_i199927_2_,
      net.minecraft.world.level.chunk.PalettedContainer.Data<T> p_i199927_3_
   ) {
      this.f_63071_ = p_i199927_1_;
      this.f_188033_ = p_i199927_2_;
      this.f_188032_ = p_i199927_3_;
   }

   public PalettedContainer(IdMap<T> p_i188040_1_, T p_i188040_2_, net.minecraft.world.level.chunk.PalettedContainer.Strategy p_i188040_3_) {
      this.f_188033_ = p_i188040_3_;
      this.f_63071_ = p_i188040_1_;
      this.f_188032_ = this.m_188051_(null, 0);
      this.f_188032_.f_188102_.m_6796_(p_i188040_2_);
   }

   private net.minecraft.world.level.chunk.PalettedContainer.Data<T> m_188051_(
      @Nullable net.minecraft.world.level.chunk.PalettedContainer.Data<T> p_188051_1_, int p_188051_2_
   ) {
      net.minecraft.world.level.chunk.PalettedContainer.Configuration<T> configuration = this.f_188033_.m_183248_(this.f_63071_, p_188051_2_);
      return p_188051_1_ != null && configuration.equals(p_188051_1_.f_188100_())
         ? p_188051_1_
         : configuration.m_188091_(this.f_63071_, this, this.f_188033_.m_188144_());
   }

   public int m_7248_(int p_7248_1_, T p_7248_2_) {
      net.minecraft.world.level.chunk.PalettedContainer.Data<T> data = this.f_188032_;
      net.minecraft.world.level.chunk.PalettedContainer.Data<T> data1 = this.m_188051_(data, p_7248_1_);
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
      net.minecraft.world.level.chunk.PalettedContainer.Data<T> data = this.f_188032_;
      return (T)data.f_188102_.m_5795_(data.f_188101_.m_13514_(index));
   }

   public void m_196879_(Consumer<T> p_196879_1_) {
      Palette<T> palette = this.f_188032_.f_188102_();
      IntSet intset = new IntArraySet();
      this.f_188032_.f_188101_.m_13519_(intset::add);
      intset.forEach(p_196885_2_ -> p_196879_1_.accept(palette.m_5795_(p_196885_2_)));
   }

   public void m_63118_(FriendlyByteBuf buf) {
      this.m_63084_();

      try {
         int i = buf.readByte();
         net.minecraft.world.level.chunk.PalettedContainer.Data<T> data = this.m_188051_(this.f_188032_, i);
         data.f_188102_.m_5680_(buf);
         buf.m_130105_(data.f_188101_.m_13513_());
         this.f_188032_ = data;
      } finally {
         this.m_63120_();
      }
   }

   public void m_63135_(FriendlyByteBuf buf) {
      this.m_63084_();

      try {
         this.f_188032_.m_188114_(buf);
      } finally {
         this.m_63120_();
      }
   }

   private static <T> DataResult<net.minecraft.world.level.chunk.PalettedContainer<T>> m_188067_(
      IdMap<T> p_188067_0_, net.minecraft.world.level.chunk.PalettedContainer.Strategy p_188067_1_, PackedData<T> p_188067_2_
   ) {
      List<T> list = p_188067_2_.f_238184_();
      int i = p_188067_1_.m_188144_();
      int j = p_188067_1_.m_188151_(p_188067_0_, list.size());
      net.minecraft.world.level.chunk.PalettedContainer.Configuration<T> configuration = p_188067_1_.m_183248_(p_188067_0_, j);
      net.minecraft.util.BitStorage bitstorage;
      if (j == 0) {
         bitstorage = new ZeroBitStorage(i);
      } else {
         Optional<LongStream> optional = p_188067_2_.f_238179_();
         if (optional.isEmpty()) {
            return DataResult.error(() -> "Missing values for non-zero storage");
         }

         long[] along = ((LongStream)optional.get()).toArray();

         try {
            if (configuration.f_188085_() == net.minecraft.world.level.chunk.PalettedContainer.Strategy.f_188139_) {
               Palette<T> palette = new HashMapPalette(p_188067_0_, j, (p_238277_0_, p_238277_1_) -> 0, list);
               net.minecraft.util.SimpleBitStorage simplebitstorage = new net.minecraft.util.SimpleBitStorage(j, i, along);
               int[] aint = new int[i];
               simplebitstorage.m_197970_(aint);
               m_198189_(aint, p_238280_2_ -> p_188067_0_.m_7447_(palette.m_5795_(p_238280_2_)));
               bitstorage = new net.minecraft.util.SimpleBitStorage(configuration.f_188086_(), i, aint);
            } else {
               bitstorage = new net.minecraft.util.SimpleBitStorage(configuration.f_188086_(), i, along);
            }
         } catch (net.minecraft.util.SimpleBitStorage.InitializationException var13) {
            return DataResult.error(() -> "Failed to read PalettedContainer: " + var13.getMessage());
         }
      }

      return DataResult.success(new net.minecraft.world.level.chunk.PalettedContainer<>(p_188067_0_, p_188067_1_, configuration, bitstorage, list));
   }

   public PackedData<T> m_188064_(IdMap<T> p_188064_1_, net.minecraft.world.level.chunk.PalettedContainer.Strategy p_188064_2_) {
      this.m_63084_();

      PackedData palettedcontainerro$packeddata;
      try {
         HashMapPalette<T> hashmappalette = new HashMapPalette(p_188064_1_, this.f_188032_.f_188101_.m_144604_(), this.f_63070_);
         int i = p_188064_2_.m_188144_();
         int[] aint = new int[i];
         this.f_188032_.f_188101_.m_197970_(aint);
         m_198189_(aint, p_198176_2_ -> hashmappalette.m_6796_(this.f_188032_.f_188102_.m_5795_(p_198176_2_)));
         int j = p_188064_2_.m_188151_(p_188064_1_, hashmappalette.m_62680_());
         Optional<LongStream> optional;
         if (j != 0) {
            net.minecraft.util.SimpleBitStorage simplebitstorage = new net.minecraft.util.SimpleBitStorage(j, i, aint);
            optional = Optional.of(Arrays.stream(simplebitstorage.m_13513_()));
         } else {
            optional = Optional.empty();
         }

         palettedcontainerro$packeddata = new PackedData(hashmappalette.m_187917_(), optional);
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

   public net.minecraft.world.level.chunk.PalettedContainer<T> m_199931_() {
      return new net.minecraft.world.level.chunk.PalettedContainer<>(this.f_63071_, this.f_188033_, this.f_188032_.m_238361_());
   }

   public net.minecraft.world.level.chunk.PalettedContainer<T> m_238334_() {
      return new net.minecraft.world.level.chunk.PalettedContainer<>(this.f_63071_, (T)this.f_188032_.f_188102_.m_5795_(0), this.f_188033_);
   }

   public void m_63099_(net.minecraft.world.level.chunk.PalettedContainer.CountConsumer<T> countConsumerIn) {
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

   static record Configuration<T>(Factory f_188085_, int f_188086_) {
      public net.minecraft.world.level.chunk.PalettedContainer.Data<T> m_188091_(IdMap<T> p_188091_1_, PaletteResize<T> p_188091_2_, int p_188091_3_) {
         net.minecraft.util.BitStorage bitstorage = (net.minecraft.util.BitStorage)(this.f_188086_ == 0
            ? new ZeroBitStorage(p_188091_3_)
            : new net.minecraft.util.SimpleBitStorage(this.f_188086_, p_188091_3_));
         Palette<T> palette = this.f_188085_.m_188026_(this.f_188086_, p_188091_1_, p_188091_2_, List.of());
         return new net.minecraft.world.level.chunk.PalettedContainer.Data<>(this, bitstorage, palette);
      }
   }

   @FunctionalInterface
   public interface CountConsumer<T> {
      void m_63144_(T var1, int var2);
   }

   static record Data<T>(
      net.minecraft.world.level.chunk.PalettedContainer.Configuration<T> f_188100_, net.minecraft.util.BitStorage f_188101_, Palette<T> f_188102_
   ) {
      public void m_188111_(Palette<T> p_188111_1_, net.minecraft.util.BitStorage p_188111_2_) {
         for (int i = 0; i < p_188111_2_.m_13521_(); i++) {
            T t = (T)p_188111_1_.m_5795_(p_188111_2_.m_13514_(i));
            this.f_188101_.m_13524_(i, this.f_188102_.m_6796_(t));
         }
      }

      public int m_188107_() {
         return 1 + this.f_188102_.m_6429_() + VarInt.m_294521_(this.f_188101_.m_13513_().length) + this.f_188101_.m_13513_().length * 8;
      }

      public void m_188114_(FriendlyByteBuf p_188114_1_) {
         p_188114_1_.writeByte(this.f_188101_.m_144604_());
         this.f_188102_.m_5678_(p_188114_1_);
         p_188114_1_.m_130091_(this.f_188101_.m_13513_());
      }

      public net.minecraft.world.level.chunk.PalettedContainer.Data<T> m_238361_() {
         return new net.minecraft.world.level.chunk.PalettedContainer.Data<>(this.f_188100_, this.f_188101_.m_199833_(), this.f_188102_.m_199814_());
      }
   }

   public abstract static class Strategy {
      public static final Factory f_188134_ = SingleValuePalette::m_188213_;
      public static final Factory f_188135_ = LinearPalette::m_188019_;
      public static final Factory f_188136_ = HashMapPalette::m_187912_;
      static final Factory f_188139_ = GlobalPalette::m_187898_;
      public static final net.minecraft.world.level.chunk.PalettedContainer.Strategy f_188137_ = new net.minecraft.world.level.chunk.PalettedContainer.Strategy(
         4
      ) {
         @Override
         public <A> net.minecraft.world.level.chunk.PalettedContainer.Configuration<A> m_183248_(IdMap<A> p_183248_1_, int p_183248_2_) {
            return switch (p_183248_2_) {
               case 0 -> new net.minecraft.world.level.chunk.PalettedContainer.Configuration(f_188134_, p_183248_2_);
               case 1, 2, 3, 4 -> new net.minecraft.world.level.chunk.PalettedContainer.Configuration(f_188135_, 4);
               case 5, 6, 7, 8 -> new net.minecraft.world.level.chunk.PalettedContainer.Configuration(f_188136_, p_183248_2_);
               default -> new net.minecraft.world.level.chunk.PalettedContainer.Configuration(
               net.minecraft.world.level.chunk.PalettedContainer.Strategy.f_188139_, net.minecraft.util.Mth.m_14163_(p_183248_1_.m_13562_())
            );
            };
         }
      };
      public static final net.minecraft.world.level.chunk.PalettedContainer.Strategy f_188138_ = new net.minecraft.world.level.chunk.PalettedContainer.Strategy(
         2
      ) {
         @Override
         public <A> net.minecraft.world.level.chunk.PalettedContainer.Configuration<A> m_183248_(IdMap<A> p_183248_1_, int p_183248_2_) {
            return switch (p_183248_2_) {
               case 0 -> new net.minecraft.world.level.chunk.PalettedContainer.Configuration(f_188134_, p_183248_2_);
               case 1, 2, 3 -> new net.minecraft.world.level.chunk.PalettedContainer.Configuration(f_188135_, p_183248_2_);
               default -> new net.minecraft.world.level.chunk.PalettedContainer.Configuration(
               net.minecraft.world.level.chunk.PalettedContainer.Strategy.f_188139_, net.minecraft.util.Mth.m_14163_(p_183248_1_.m_13562_())
            );
            };
         }
      };
      private final int f_188140_;

      Strategy(int p_i188142_1_) {
         this.f_188140_ = p_i188142_1_;
      }

      public int m_188144_() {
         return 1 << this.f_188140_ * 3;
      }

      public int m_188145_(int p_188145_1_, int p_188145_2_, int p_188145_3_) {
         return (p_188145_2_ << this.f_188140_ | p_188145_3_) << this.f_188140_ | p_188145_1_;
      }

      public abstract <A> net.minecraft.world.level.chunk.PalettedContainer.Configuration<A> m_183248_(IdMap<A> var1, int var2);

      <A> int m_188151_(IdMap<A> p_188151_1_, int p_188151_2_) {
         int i = net.minecraft.util.Mth.m_14163_(p_188151_2_);
         net.minecraft.world.level.chunk.PalettedContainer.Configuration<A> configuration = this.m_183248_(p_188151_1_, i);
         return configuration.f_188085_() == f_188139_ ? i : configuration.f_188086_();
      }
   }
}
