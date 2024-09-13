package net.optifine;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;

public class BetterSnow {
   private static BakedModel modelSnowLayer = null;

   public static void m_252999_() {
      modelSnowLayer = Config.getMinecraft().m_91289_().m_110907_().m_110893_(Blocks.f_50125_.m_49966_());
   }

   public static BakedModel getModelSnowLayer() {
      return modelSnowLayer;
   }

   public static BlockState getStateSnowLayer() {
      return Blocks.f_50125_.m_49966_();
   }

   public static boolean shouldRender(BlockAndTintGetter lightReader, BlockState blockState, BlockPos blockPos) {
      if (!(lightReader instanceof BlockGetter)) {
         return false;
      } else {
         return !checkBlock(lightReader, blockState, blockPos) ? false : hasSnowNeighbours(lightReader, blockPos);
      }
   }

   private static boolean hasSnowNeighbours(BlockGetter blockAccess, BlockPos pos) {
      Block blockSnow = Blocks.f_50125_;
      if (blockAccess.m_8055_(pos.m_122012_()).m_60734_() == blockSnow
         || blockAccess.m_8055_(pos.m_122019_()).m_60734_() == blockSnow
         || blockAccess.m_8055_(pos.m_122024_()).m_60734_() == blockSnow
         || blockAccess.m_8055_(pos.m_122029_()).m_60734_() == blockSnow) {
         BlockState bsDown = blockAccess.m_8055_(pos.m_7495_());
         if (bsDown.m_60804_(blockAccess, pos)) {
            return true;
         }

         Block blockDown = bsDown.m_60734_();
         if (blockDown instanceof StairBlock) {
            return bsDown.m_61143_(StairBlock.f_56842_) == Half.TOP;
         }

         if (blockDown instanceof SlabBlock) {
            return bsDown.m_61143_(SlabBlock.f_56353_) == SlabType.TOP;
         }
      }

      return false;
   }

   private static boolean checkBlock(BlockGetter blockAccess, BlockState blockState, BlockPos blockPos) {
      if (blockState.m_60804_(blockAccess, blockPos)) {
         return false;
      } else {
         Block block = blockState.m_60734_();
         if (block == Blocks.f_50127_) {
            return false;
         } else if (!(block instanceof BushBlock)
            || !(block instanceof DoublePlantBlock)
               && !(block instanceof FlowerBlock)
               && !(block instanceof MushroomBlock)
               && !(block instanceof SaplingBlock)
               && !(block instanceof TallGrassBlock)) {
            if (block instanceof FenceBlock
               || block instanceof FenceGateBlock
               || block instanceof FlowerPotBlock
               || block instanceof CrossCollisionBlock
               || block instanceof SugarCaneBlock
               || block instanceof WallBlock) {
               return true;
            } else if (block instanceof RedstoneTorchBlock) {
               return true;
            } else if (block instanceof StairBlock) {
               return blockState.m_61143_(StairBlock.f_56842_) == Half.TOP;
            } else if (block instanceof SlabBlock) {
               return blockState.m_61143_(SlabBlock.f_56353_) == SlabType.TOP;
            } else if (block instanceof ButtonBlock) {
               return blockState.m_61143_(ButtonBlock.f_53179_) != AttachFace.FLOOR;
            } else if (block instanceof HopperBlock) {
               return true;
            } else if (block instanceof LadderBlock) {
               return true;
            } else if (block instanceof LeverBlock) {
               return true;
            } else {
               return block instanceof TurtleEggBlock ? true : block instanceof VineBlock;
            }
         } else {
            return true;
         }
      }
   }
}
