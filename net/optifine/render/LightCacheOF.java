package net.optifine.render;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.optifine.override.ChunkCacheOF;

public class LightCacheOF {
   public static final float getBrightness(net.minecraft.world.level.block.state.BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
      float aoLight = getAoLightRaw(blockStateIn, worldIn, blockPosIn);
      return net.minecraft.client.renderer.block.ModelBlockRenderer.fixAoLightValue(aoLight);
   }

   private static float getAoLightRaw(net.minecraft.world.level.block.state.BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
      if (blockStateIn.m_60734_() == Blocks.f_50110_) {
         return 1.0F;
      } else {
         return blockStateIn.m_60734_() == Blocks.f_50616_ ? 1.0F : blockStateIn.m_60792_(worldIn, blockPosIn);
      }
   }

   public static final int getPackedLight(net.minecraft.world.level.block.state.BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
      return worldIn instanceof ChunkCacheOF cc ? cc.getCombinedLight(blockStateIn, worldIn, blockPosIn) : getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
   }

   public static int getPackedLightRaw(BlockAndTintGetter worldIn, net.minecraft.world.level.block.state.BlockState blockStateIn, BlockPos blockPosIn) {
      return net.minecraft.client.renderer.LevelRenderer.m_109537_(worldIn, blockStateIn, blockPosIn);
   }
}
