package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class LevelChunkSection {
   public static int f_156455_;
   public static int f_156456_;
   public static int f_156457_;
   public static int f_187994_;
   private short f_62969_;
   private short f_62970_;
   private short f_62971_;
   private PalettedContainer<BlockState> f_62972_;
   private PalettedContainerRO<Holder<Biome>> f_187995_;

   public LevelChunkSection(PalettedContainer<BlockState> dataIn, PalettedContainerRO<Holder<Biome>> biomesIn) {
      this.f_62972_ = dataIn;
      this.f_187995_ = biomesIn;
      this.m_63018_();
   }

   public LevelChunkSection(Registry<Biome> registryIn) {
      this.f_62972_ = new PalettedContainer<>(Block.f_49791_, Blocks.f_50016_.m_49966_(), PalettedContainer.Strategy.f_188137_);
      this.f_187995_ = new PalettedContainer<>(registryIn.m_206115_(), registryIn.m_246971_(Biomes.f_48202_), PalettedContainer.Strategy.f_188138_);
   }

   public BlockState m_62982_(int x, int y, int z) {
      return this.f_62972_.m_63087_(x, y, z);
   }

   public FluidState m_63007_(int x, int y, int z) {
      return this.f_62972_.m_63087_(x, y, z).m_60819_();
   }

   public void m_62981_() {
      this.f_62972_.m_63084_();
   }

   public void m_63006_() {
      this.f_62972_.m_63120_();
   }

   public BlockState m_62986_(int x, int y, int z, BlockState blockStateIn) {
      return this.m_62991_(x, y, z, blockStateIn, true);
   }

   public BlockState m_62991_(int x, int y, int z, BlockState state, boolean useLocks) {
      BlockState blockstate;
      if (useLocks) {
         blockstate = this.f_62972_.m_63091_(x, y, z, state);
      } else {
         blockstate = this.f_62972_.m_63127_(x, y, z, state);
      }

      FluidState fluidstate = blockstate.m_60819_();
      FluidState fluidstate1 = state.m_60819_();
      if (!blockstate.m_60795_()) {
         this.f_62969_--;
         if (blockstate.m_60823_()) {
            this.f_62970_--;
         }
      }

      if (!fluidstate.m_76178_()) {
         this.f_62971_--;
      }

      if (!state.m_60795_()) {
         this.f_62969_++;
         if (state.m_60823_()) {
            this.f_62970_++;
         }
      }

      if (!fluidstate1.m_76178_()) {
         this.f_62971_++;
      }

      return blockstate;
   }

   public boolean m_188008_() {
      return this.f_62969_ == 0;
   }

   public boolean m_63014_() {
      return this.m_63015_() || this.m_63016_();
   }

   public boolean m_63015_() {
      return this.f_62970_ > 0;
   }

   public boolean m_63016_() {
      return this.f_62971_ > 0;
   }

   public void m_63018_() {
      LevelChunkSection.BlockCounter levelchunksection$blockcounter = new LevelChunkSection.BlockCounter();
      this.f_62972_.m_63099_(levelchunksection$blockcounter);
      this.f_62969_ = (short)levelchunksection$blockcounter.f_204437_;
      this.f_62970_ = (short)levelchunksection$blockcounter.f_204438_;
      this.f_62971_ = (short)levelchunksection$blockcounter.f_204439_;
   }

   public PalettedContainer<BlockState> m_63019_() {
      return this.f_62972_;
   }

   public PalettedContainerRO<Holder<Biome>> m_187996_() {
      return this.f_187995_;
   }

   public void m_63004_(FriendlyByteBuf packetBufferIn) {
      this.f_62969_ = packetBufferIn.readShort();
      this.f_62972_.m_63118_(packetBufferIn);
      PalettedContainer<Holder<Biome>> palettedcontainer = this.f_187995_.m_238334_();
      palettedcontainer.m_63118_(packetBufferIn);
      this.f_187995_ = palettedcontainer;
   }

   public void m_274599_(FriendlyByteBuf packetBufferIn) {
      PalettedContainer<Holder<Biome>> palettedcontainer = this.f_187995_.m_238334_();
      palettedcontainer.m_63118_(packetBufferIn);
      this.f_187995_ = palettedcontainer;
   }

   public void m_63011_(FriendlyByteBuf packetBufferIn) {
      packetBufferIn.writeShort(this.f_62969_);
      this.f_62972_.m_63135_(packetBufferIn);
      this.f_187995_.m_63135_(packetBufferIn);
   }

   public int m_63020_() {
      return 2 + this.f_62972_.m_63137_() + this.f_187995_.m_63137_();
   }

   public boolean m_63002_(Predicate<BlockState> state) {
      return this.f_62972_.m_63109_(state);
   }

   public Holder<Biome> m_204433_(int x, int y, int z) {
      return (Holder<Biome>)this.f_187995_.m_63087_(x, y, z);
   }

   public void m_280631_(BiomeResolver resolverIn, Sampler samplerIn, int x, int y, int z) {
      PalettedContainer<Holder<Biome>> palettedcontainer = this.f_187995_.m_238334_();
      int i = 4;

      for (int j = 0; j < 4; j++) {
         for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
               palettedcontainer.m_63127_(j, k, l, resolverIn.m_203407_(x + j, y + k, z + l, samplerIn));
            }
         }
      }

      this.f_187995_ = palettedcontainer;
   }

   public short getBlockRefCount() {
      return this.f_62969_;
   }

   class BlockCounter implements PalettedContainer.CountConsumer<BlockState> {
      public int f_204437_;
      public int f_204438_;
      public int f_204439_;

      public void m_63144_(BlockState blockStateIn, int countIn) {
         FluidState fluidstate = blockStateIn.m_60819_();
         if (!blockStateIn.m_60795_()) {
            this.f_204437_ += countIn;
            if (blockStateIn.m_60823_()) {
               this.f_204438_ += countIn;
            }
         }

         if (!fluidstate.m_76178_()) {
            this.f_204437_ += countIn;
            if (fluidstate.m_76187_()) {
               this.f_204439_ += countIn;
            }
         }
      }
   }
}
