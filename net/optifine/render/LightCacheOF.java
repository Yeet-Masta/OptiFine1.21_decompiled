package net.optifine.render;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.override.ChunkCacheOF;

public class LightCacheOF {
   public static final float getBrightness(BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
      float aoLight = getAoLightRaw(blockStateIn, worldIn, blockPosIn);
      aoLight = ModelBlockRenderer.fixAoLightValue(aoLight);
      return aoLight;
   }

   private static float getAoLightRaw(BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
      if (blockStateIn.m_60734_() == Blocks.f_50110_) {
         return 1.0F;
      } else {
         return blockStateIn.m_60734_() == Blocks.f_50616_ ? 1.0F : blockStateIn.m_60792_(worldIn, blockPosIn);
      }
   }

   public static final int getPackedLight(BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
      if (worldIn instanceof ChunkCacheOF cc) {
         return cc.getCombinedLight(blockStateIn, worldIn, blockPosIn);
      } else {
         return getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
      }
   }

   public static int getPackedLightRaw(BlockAndTintGetter worldIn, BlockState blockStateIn, BlockPos blockPosIn) {
      return LevelRenderer.m_109537_(worldIn, blockStateIn, blockPosIn);
   }
}
