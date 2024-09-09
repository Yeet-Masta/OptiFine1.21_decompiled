package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.optifine.BlockPosM;

public class IteratorRenderChunks implements Iterator<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> {
   private net.minecraft.client.renderer.ViewArea viewFrustum;
   private Iterator3d Iterator3d;
   private BlockPosM posBlock = new BlockPosM(0, 0, 0);

   public IteratorRenderChunks(net.minecraft.client.renderer.ViewArea viewFrustum, BlockPos posStart, BlockPos posEnd, int width, int height) {
      this.viewFrustum = viewFrustum;
      this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
   }

   public boolean hasNext() {
      return this.Iterator3d.hasNext();
   }

   public net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection next() {
      BlockPos pos = this.Iterator3d.next();
      this.posBlock.setXyz(pos.m_123341_() << 4, pos.m_123342_() << 4, pos.m_123343_() << 4);
      return this.viewFrustum.m_292642_(this.posBlock);
   }

   public void remove() {
      throw new RuntimeException("Not implemented");
   }
}
