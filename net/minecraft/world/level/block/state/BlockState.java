package net.minecraft.world.level.block.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.optifine.Config;
import net.optifine.util.BlockUtils;

public class BlockState extends BlockStateBase implements IForgeBlockState {
   public static Codec<BlockState> f_61039_ = m_61127_(BuiltInRegistries.f_256975_.m_194605_(), Block::m_49966_).stable();
   private int blockId = -1;
   private int metadata = -1;
   private ResourceLocation blockLocation;
   private int blockStateId = -1;
   private static AtomicInteger blockStateIdCounter = new AtomicInteger(0);

   public int getBlockId() {
      if (this.blockId < 0) {
         this.blockId = BuiltInRegistries.f_256975_.m_7447_(this.m_60734_());
      }

      return this.blockId;
   }

   public int getMetadata() {
      if (this.metadata < 0) {
         this.metadata = BlockUtils.getMetadata(this);
         if (this.metadata < 0) {
            Config.warn("Metadata not found, block: " + this.getBlockLocation());
            this.metadata = 0;
         }
      }

      return this.metadata;
   }

   public ResourceLocation getBlockLocation() {
      if (this.blockLocation == null) {
         this.blockLocation = BuiltInRegistries.f_256975_.m_7981_(this.m_60734_());
      }

      return this.blockLocation;
   }

   public int getBlockStateId() {
      if (this.blockStateId < 0) {
         this.blockStateId = blockStateIdCounter.incrementAndGet();
      }

      return this.blockStateId;
   }

   public int getLightValue(BlockGetter world, BlockPos pos) {
      return this.m_60791_();
   }

   public boolean isCacheOpaqueCube() {
      return this.f_60593_ != null && this.f_60593_.f_60841_;
   }

   public boolean isCacheOpaqueCollisionShape() {
      return this.f_60593_ != null && this.f_60593_.f_60844_;
   }

   public BlockState(Block blockIn, Reference2ObjectArrayMap<Property<?>, Comparable<?>> propertiesIn, MapCodec<BlockState> codecIn) {
      super(blockIn, propertiesIn, codecIn);
   }

   protected BlockState m_7160_() {
      return this;
   }
}
