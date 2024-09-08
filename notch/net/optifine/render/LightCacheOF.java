package net.optifine.render;

import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_4134_;
import net.minecraft.src.C_4186_;
import net.minecraft.src.C_4675_;
import net.optifine.override.ChunkCacheOF;

public class LightCacheOF {
   public static final float getBrightness(C_2064_ blockStateIn, C_1557_ worldIn, C_4675_ blockPosIn) {
      float aoLight = getAoLightRaw(blockStateIn, worldIn, blockPosIn);
      return C_4186_.fixAoLightValue(aoLight);
   }

   private static float getAoLightRaw(C_2064_ blockStateIn, C_1557_ worldIn, C_4675_ blockPosIn) {
      if (blockStateIn.b() == C_1710_.f_50110_) {
         return 1.0F;
      } else {
         return blockStateIn.b() == C_1710_.f_50616_ ? 1.0F : blockStateIn.f(worldIn, blockPosIn);
      }
   }

   public static final int getPackedLight(C_2064_ blockStateIn, C_1557_ worldIn, C_4675_ blockPosIn) {
      return worldIn instanceof ChunkCacheOF cc ? cc.getCombinedLight(blockStateIn, worldIn, blockPosIn) : getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
   }

   public static int getPackedLightRaw(C_1557_ worldIn, C_2064_ blockStateIn, C_4675_ blockPosIn) {
      return C_4134_.m_109537_(worldIn, blockStateIn, blockPosIn);
   }
}
