package net.optifine.util;

import net.minecraft.src.C_188_;
import net.minecraft.src.C_2139_;
import net.minecraft.src.C_290152_.C_290138_;

public class RenderChunkUtils {
   public static int getCountBlocks(C_290138_ renderChunk) {
      C_2139_[] ebss = renderChunk.getChunk().d();
      if (ebss == null) {
         return 0;
      } else {
         int indexEbs = renderChunk.m_295500_().v() - renderChunk.getWorld().I_() >> 4;
         C_2139_ ebs = ebss[indexEbs];
         return ebs == null ? 0 : ebs.getBlockRefCount();
      }
   }

   public static double getRelativeBufferSize(C_290138_ renderChunk) {
      int blockCount = getCountBlocks(renderChunk);
      return getRelativeBufferSize(blockCount);
   }

   public static double getRelativeBufferSize(int blockCount) {
      double countRel = (double)blockCount / 4096.0;
      countRel *= 0.995;
      double weight = countRel * 2.0 - 1.0;
      weight = C_188_.m_14008_(weight, -1.0, 1.0);
      return Math.sqrt(1.0 - weight * weight);
   }
}
