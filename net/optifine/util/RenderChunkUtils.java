package net.optifine.util;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class RenderChunkUtils {
   public static int getCountBlocks(SectionRenderDispatcher.RenderSection renderChunk) {
      LevelChunkSection[] ebss = renderChunk.getChunk().m_7103_();
      if (ebss == null) {
         return 0;
      } else {
         int indexEbs = renderChunk.m_295500_().m_123342_() - renderChunk.getWorld().m_141937_() >> 4;
         LevelChunkSection ebs = ebss[indexEbs];
         return ebs == null ? 0 : ebs.getBlockRefCount();
      }
   }

   public static double getRelativeBufferSize(SectionRenderDispatcher.RenderSection renderChunk) {
      int blockCount = getCountBlocks(renderChunk);
      double vertexCountRel = getRelativeBufferSize(blockCount);
      return vertexCountRel;
   }

   public static double getRelativeBufferSize(int blockCount) {
      double countRel = (double)blockCount / 4096.0;
      countRel *= 0.995;
      double weight = countRel * 2.0 - 1.0;
      weight = Mth.m_14008_(weight, -1.0, 1.0);
      return Math.sqrt(1.0 - weight * weight);
   }
}
