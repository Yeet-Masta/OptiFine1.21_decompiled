package net.optifine.shaders;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.core.BlockPos;
import net.optifine.BlockPosM;

public class IteratorAxis implements Iterator {
   private double yDelta;
   private double zDelta;
   private int xStart;
   private int xEnd;
   private double yStart;
   private double yEnd;
   private double zStart;
   private double zEnd;
   private int xNext;
   private double yNext;
   private double zNext;
   private BlockPosM pos = new BlockPosM(0, 0, 0);
   private boolean hasNext = false;

   public IteratorAxis(BlockPos posStart, BlockPos posEnd, double yDelta, double zDelta) {
      this.yDelta = yDelta;
      this.zDelta = zDelta;
      this.xStart = posStart.m_123341_();
      this.xEnd = posEnd.m_123341_();
      this.yStart = (double)posStart.m_123342_();
      this.yEnd = (double)posEnd.m_123342_() - 0.5;
      this.zStart = (double)posStart.m_123343_();
      this.zEnd = (double)posEnd.m_123343_() - 0.5;
      this.xNext = this.xStart;
      this.yNext = this.yStart;
      this.zNext = this.zStart;
      this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
   }

   public boolean hasNext() {
      return this.hasNext;
   }

   public BlockPos next() {
      if (!this.hasNext) {
         throw new NoSuchElementException();
      } else {
         this.pos.setXyz((double)this.xNext, this.yNext, this.zNext);
         this.nextPos();
         this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
         return this.pos;
      }
   }

   private void nextPos() {
      ++this.zNext;
      if (!(this.zNext < this.zEnd)) {
         this.zNext = this.zStart;
         ++this.yNext;
         if (!(this.yNext < this.yEnd)) {
            this.yNext = this.yStart;
            this.yStart += this.yDelta;
            this.yEnd += this.yDelta;
            this.yNext = this.yStart;
            this.zStart += this.zDelta;
            this.zEnd += this.zDelta;
            this.zNext = this.zStart;
            ++this.xNext;
            if (this.xNext >= this.xEnd) {
               ;
            }
         }
      }
   }

   public void remove() {
      throw new RuntimeException("Not implemented");
   }

   public static void main(String[] args) throws Exception {
      BlockPos posStart = new BlockPos(-2, 10, 20);
      BlockPos posEnd = new BlockPos(2, 12, 22);
      double yDelta = -0.5;
      double zDelta = 0.5;
      IteratorAxis it = new IteratorAxis(posStart, posEnd, yDelta, zDelta);
      PrintStream var10000 = System.out;
      String var10001 = String.valueOf(posStart);
      var10000.println("Start: " + var10001 + ", end: " + String.valueOf(posEnd) + ", yDelta: " + yDelta + ", zDelta: " + zDelta);

      while(it.hasNext()) {
         BlockPos blockPos = it.next();
         System.out.println("" + String.valueOf(blockPos));
      }

   }
}
