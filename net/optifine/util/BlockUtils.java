package net.optifine.util;

import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.optifine.Config;
import net.optifine.render.RenderEnv;

public class BlockUtils {
   private static final ThreadLocal<BlockUtils.RenderSideCacheKey> threadLocalKey = ThreadLocal.withInitial(
      () -> new BlockUtils.RenderSideCacheKey(null, null, null)
   );
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<BlockUtils.RenderSideCacheKey>> threadLocalMap = ThreadLocal.withInitial(
      () -> {
         Object2ByteLinkedOpenHashMap<BlockUtils.RenderSideCacheKey> object2bytelinkedopenhashmap = new Object2ByteLinkedOpenHashMap<BlockUtils.RenderSideCacheKey>(
            200
         ) {
            protected void rehash(int p_rehash_1_) {
            }
         };
         object2bytelinkedopenhashmap.defaultReturnValue((byte)127);
         return object2bytelinkedopenhashmap;
      }
   );

   public static boolean shouldSideBeRendered(
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      BlockGetter blockReaderIn,
      BlockPos blockPosIn,
      net.minecraft.core.Direction facingIn,
      RenderEnv renderEnv
   ) {
      BlockPos posNeighbour = blockPosIn.m_121945_(facingIn);
      net.minecraft.world.level.block.state.BlockState stateNeighbour = blockReaderIn.m_8055_(posNeighbour);
      if (stateNeighbour.isCacheOpaqueCube() && !(blockStateIn.m_60734_() instanceof PowderSnowBlock)) {
         return false;
      } else if (blockStateIn.m_60719_(stateNeighbour, facingIn)) {
         return false;
      } else {
         return stateNeighbour.m_60815_()
            ? shouldSideBeRenderedCached(blockStateIn, blockReaderIn, blockPosIn, facingIn, renderEnv, stateNeighbour, posNeighbour)
            : true;
      }
   }

   public static boolean shouldSideBeRenderedCached(
      net.minecraft.world.level.block.state.BlockState blockStateIn,
      BlockGetter blockReaderIn,
      BlockPos blockPosIn,
      net.minecraft.core.Direction facingIn,
      RenderEnv renderEnv,
      net.minecraft.world.level.block.state.BlockState stateNeighbourIn,
      BlockPos posNeighbourIn
   ) {
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

   public static Block getBlock(net.minecraft.resources.ResourceLocation loc) {
      return !BuiltInRegistries.f_256975_.m_7804_(loc) ? null : (Block)BuiltInRegistries.f_256975_.m_7745_(loc);
   }

   public static int getMetadata(net.minecraft.world.level.block.state.BlockState blockState) {
      Block block = blockState.m_60734_();
      StateDefinition<Block, net.minecraft.world.level.block.state.BlockState> stateContainer = block.m_49965_();
      List<net.minecraft.world.level.block.state.BlockState> validStates = stateContainer.m_61056_();
      return validStates.indexOf(blockState);
   }

   public static int getMetadataCount(Block block) {
      StateDefinition<Block, net.minecraft.world.level.block.state.BlockState> stateContainer = block.m_49965_();
      List<net.minecraft.world.level.block.state.BlockState> validStates = stateContainer.m_61056_();
      return validStates.size();
   }

   public static net.minecraft.world.level.block.state.BlockState getBlockState(Block block, int metadata) {
      StateDefinition<Block, net.minecraft.world.level.block.state.BlockState> stateContainer = block.m_49965_();
      List<net.minecraft.world.level.block.state.BlockState> validStates = stateContainer.m_61056_();
      return metadata >= 0 && metadata < validStates.size() ? (net.minecraft.world.level.block.state.BlockState)validStates.get(metadata) : null;
   }

   public static List<net.minecraft.world.level.block.state.BlockState> getBlockStates(Block block) {
      StateDefinition<Block, net.minecraft.world.level.block.state.BlockState> stateContainer = block.m_49965_();
      List<net.minecraft.world.level.block.state.BlockState> validStates = stateContainer.m_61056_();
      return validStates;
   }

   public static boolean isFullCube(net.minecraft.world.level.block.state.BlockState stateIn, BlockGetter blockReaderIn, BlockPos posIn) {
      return stateIn.isCacheOpaqueCollisionShape();
   }

   public static Collection<net.minecraft.world.level.block.state.properties.Property> getProperties(
      net.minecraft.world.level.block.state.BlockState blockState
   ) {
      return blockState.m_61147_();
   }

   public static boolean isPropertyTrue(net.minecraft.world.level.block.state.BlockState blockState, BooleanProperty prop) {
      Boolean value = (Boolean)blockState.m_61148_().get(prop);
      return Config.isTrue(value);
   }

   public static boolean isPropertyFalse(net.minecraft.world.level.block.state.BlockState blockState, BooleanProperty prop) {
      Boolean value = (Boolean)blockState.m_61148_().get(prop);
      return Config.isFalse(value);
   }

   public static final class RenderSideCacheKey {
      private net.minecraft.world.level.block.state.BlockState blockState1;
      private net.minecraft.world.level.block.state.BlockState blockState2;
      private net.minecraft.core.Direction facing;
      private int hashCode;

      private RenderSideCacheKey(
         net.minecraft.world.level.block.state.BlockState blockState1In,
         net.minecraft.world.level.block.state.BlockState blockState2In,
         net.minecraft.core.Direction facingIn
      ) {
         this.blockState1 = blockState1In;
         this.blockState2 = blockState2In;
         this.facing = facingIn;
      }

      private void init(
         net.minecraft.world.level.block.state.BlockState blockState1In,
         net.minecraft.world.level.block.state.BlockState blockState2In,
         net.minecraft.core.Direction facingIn
      ) {
         this.blockState1 = blockState1In;
         this.blockState2 = blockState2In;
         this.facing = facingIn;
         this.hashCode = 0;
      }

      public BlockUtils.RenderSideCacheKey duplicate() {
         return new BlockUtils.RenderSideCacheKey(this.blockState1, this.blockState2, this.facing);
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else {
            return !(p_equals_1_ instanceof BlockUtils.RenderSideCacheKey block$rendersidecachekey)
               ? false
               : this.blockState1 == block$rendersidecachekey.blockState1
                  && this.blockState2 == block$rendersidecachekey.blockState2
                  && this.facing == block$rendersidecachekey.facing;
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
