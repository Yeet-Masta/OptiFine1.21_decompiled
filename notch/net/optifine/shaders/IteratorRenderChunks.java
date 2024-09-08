package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.src.C_4180_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_290152_.C_290138_;
import net.optifine.BlockPosM;

public class IteratorRenderChunks implements Iterator<C_290138_> {
   private C_4180_ viewFrustum;
   private Iterator3d Iterator3d;
   private BlockPosM posBlock = new BlockPosM(0, 0, 0);

   public IteratorRenderChunks(C_4180_ viewFrustum, C_4675_ posStart, C_4675_ posEnd, int width, int height) {
      this.viewFrustum = viewFrustum;
      this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
   }

   public boolean hasNext() {
      return this.Iterator3d.hasNext();
   }

   public C_290138_ next() {
      C_4675_ pos = this.Iterator3d.next();
      this.posBlock.setXyz(pos.u() << 4, pos.v() << 4, pos.w() << 4);
      return this.viewFrustum.m_292642_(this.posBlock);
   }

   public void remove() {
      throw new RuntimeException("Not implemented");
   }
}
