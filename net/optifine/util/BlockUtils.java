package net.optifine.util;

import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.optifine.Config;
import net.optifine.render.RenderEnv;

public class BlockUtils {
   private static final ThreadLocal threadLocalKey = ThreadLocal.withInitial(() -> {
      return new RenderSideCacheKey((BlockState)null, (BlockState)null, (Direction)null);
   });
   private static final ThreadLocal threadLocalMap = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap object2bytelinkedopenhashmap = new Object2ByteLinkedOpenHashMap(200) {
         protected void rehash(int p_rehash_1_) {
         }
      };
      object2bytelinkedopenhashmap.defaultReturnValue((byte)127);
      return object2bytelinkedopenhashmap;
   });

   public static boolean shouldSideBeRendered(BlockState blockStateIn, BlockGetter blockReaderIn, BlockPos blockPosIn, Direction facingIn, RenderEnv renderEnv) {
      BlockPos posNeighbour = blockPosIn.m_121945_(facingIn);
      BlockState stateNeighbour = blockReaderIn.m_8055_(posNeighbour);
      if (stateNeighbour.isCacheOpaqueCube() && !(blockStateIn.m_60734_() instanceof PowderSnowBlock)) {
         return false;
      } else if (blockStateIn.m_60719_(stateNeighbour, facingIn)) {
         return false;
      } else {
         return stateNeighbour.m_60815_() ? shouldSideBeRenderedCached(blockStateIn, blockReaderIn, blockPosIn, facingIn, renderEnv, stateNeighbour, posNeighbour) : true;
      }
   }

   public static boolean shouldSideBeRenderedCached(BlockState blockStateIn, BlockGetter blockReaderIn, BlockPos blockPosIn, Direction facingIn, RenderEnv renderEnv, BlockState stateNeighbourIn, BlockPos posNeighbourIn) {
      long key = (long)blockStateIn.getBlockStateId() << 36 | (long)stateNeighbourIn.getBlockStateId() << 4 | (long)facingIn.ordinal();
      Long2ByteLinkedOpenHashMap map = renderEnv.getRenderSideMap();
      byte b0 = map.getAndMoveToFirst(key);
      if (b0 != 0) {
         return b0 > 0;
      } else {
         VoxelShape voxelshape = blockStateIn.m_60655_(blockReaderIn, blockPosIn, facingIn);
         if (voxelshape.m_83281_()) {
            return true;
         } else {
            VoxelShape voxelshape1 = stateNeighbourIn.m_60655_(blockReaderIn, posNeighbourIn, facingIn.m_122424_());
            boolean flag = Shapes.m_83157_(voxelshape, voxelshape1, BooleanOp.f_82685_);
            if (map.size() > 400) {
               map.removeLastByte();
            }

            map.putAndMoveToFirst(key, (byte)(flag ? 1 : -1));
            return flag;
         }
      }
   }

   public static int getBlockId(Block block) {
      return BuiltInRegistries.f_256975_.m_7447_(block);
   }

   public static Block getBlock(ResourceLocation loc) {
      return !BuiltInRegistries.f_256975_.m_7804_(loc) ? null : (Block)BuiltInRegistries.f_256975_.m_7745_(loc);
   }

   public static int getMetadata(BlockState blockState) {
      Block block = blockState.m_60734_();
      StateDefinition stateContainer = block.m_49965_();
      List validStates = stateContainer.m_61056_();
      int metadata = validStates.indexOf(blockState);
      return metadata;
   }

   public static int getMetadataCount(Block block) {
      StateDefinition stateContainer = block.m_49965_();
      List validStates = stateContainer.m_61056_();
      return validStates.size();
   }

   public static BlockState getBlockState(Block block, int metadata) {
      StateDefinition stateContainer = block.m_49965_();
      List validStates = stateContainer.m_61056_();
      if (metadata >= 0 && metadata < validStates.size()) {
         BlockState blockState = (BlockState)validStates.get(metadata);
         return blockState;
      } else {
         return null;
      }
   }

   public static List getBlockStates(Block block) {
      StateDefinition stateContainer = block.m_49965_();
      List validStates = stateContainer.m_61056_();
      return validStates;
   }

   public static boolean isFullCube(BlockState stateIn, BlockGetter blockReaderIn, BlockPos posIn) {
      return stateIn.isCacheOpaqueCollisionShape();
   }

   public static Collection getProperties(BlockState blockState) {
      return blockState.m_61147_();
   }

   public static boolean isPropertyTrue(BlockState blockState, BooleanProperty prop) {
      Boolean value = (Boolean)blockState.m_61148_().get(prop);
      return Config.isTrue(value);
   }

   public static boolean isPropertyFalse(BlockState blockState, BooleanProperty prop) {
      Boolean value = (Boolean)blockState.m_61148_().get(prop);
      return Config.isFalse(value);
   }

   public static final class RenderSideCacheKey {
      private BlockState blockState1;
      private BlockState blockState2;
      private Direction facing;
      private int hashCode;

      private RenderSideCacheKey(BlockState blockState1In, BlockState blockState2In, Direction facingIn) {
         this.blockState1 = blockState1In;
         this.blockState2 = blockState2In;
         this.facing = facingIn;
      }

      private void init(BlockState blockState1In, BlockState blockState2In, Direction facingIn) {
         this.blockState1 = blockState1In;
         this.blockState2 = blockState2In;
         this.facing = facingIn;
         this.hashCode = 0;
      }

      public RenderSideCacheKey duplicate() {
         return new RenderSideCacheKey(this.blockState1, this.blockState2, this.facing);
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (!(p_equals_1_ instanceof RenderSideCacheKey)) {
            return false;
         } else {
            RenderSideCacheKey block$rendersidecachekey = (RenderSideCacheKey)p_equals_1_;
            return this.blockState1 == block$rendersidecachekey.blockState1 && this.blockState2 == block$rendersidecachekey.blockState2 && this.facing == block$rendersidecachekey.facing;
         }
      }

      public int hashCode() {
         if (this.hashCode == 0) {
            this.hashCode = 31 * this.hashCode + this.blockState1.hashCode();
            this.hashCode = 31 * this.hashCode + this.blockState2.hashCode();
            this.hashCode = 31 * this.hashCode + this.facing.hashCode();
         }

         return this.hashCode;
      }
   }
}
