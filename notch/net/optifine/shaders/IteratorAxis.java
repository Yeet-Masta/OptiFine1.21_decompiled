package net.optifine.shaders;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.src.C_4675_;
import net.optifine.BlockPosM;

public class IteratorAxis implements Iterator<C_4675_> {
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

   public IteratorAxis(C_4675_ posStart, C_4675_ posEnd, double yDelta, double zDelta) {
      this.yDelta = yDelta;
      this.zDelta = zDelta;
      this.xStart = posStart.u();
      this.xEnd = posEnd.u();
      this.yStart = (double)posStart.v();
      this.yEnd = (double)posEnd.v() - 0.5;
      this.zStart = (double)posStart.w();
      this.zEnd = (double)posEnd.w() - 0.5;
      this.xNext = this.xStart;
      this.yNext = this.yStart;
      this.zNext = this.zStart;
      this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
   }

   public boolean hasNext() {
      return this.hasNext;
   }

   public C_4675_ next() {
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
      this.zNext++;
      if (!(this.zNext < this.zEnd)) {
         this.zNext = this.zStart;
         this.yNext++;
         if (!(this.yNext < this.yEnd)) {
            this.yNext = this.yStart;
            this.yStart = this.yStart + this.yDelta;
            this.yEnd = this.yEnd + this.yDelta;
            this.yNext = this.yStart;
            this.zStart = this.zStart + this.zDelta;
            this.zEnd = this.zEnd + this.zDelta;
            this.zNext = this.zStart;
            this.xNext++;
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
      C_4675_ posStart = new C_4675_(-2, 10, 20);
      C_4675_ posEnd = new C_4675_(2, 12, 22);
      double yDelta = -0.5;
      double zDelta = 0.5;
      IteratorAxis it = new IteratorAxis(posStart, posEnd, yDelta, zDelta);
      System.out.println("Start: " + posStart + ", end: " + posEnd + ", yDelta: " + yDelta + ", zDelta: " + zDelta);

      while (it.hasNext()) {
         C_4675_ blockPos = it.next();
         System.out.println(blockPos + "");
      }
   }
}
