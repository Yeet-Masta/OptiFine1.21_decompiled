package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.src.C_3046_;
import net.minecraft.src.C_4675_;
import net.optifine.BlockPosM;

public class Iterator3d implements Iterator<C_4675_> {
   private IteratorAxis iteratorAxis;
   private BlockPosM blockPos = new BlockPosM(0, 0, 0);
   private int axis = 0;
   private int kX;
   private int kY;
   private int kZ;
   private static final int AXIS_X = 0;
   private static final int AXIS_Y = 1;
   private static final int AXIS_Z = 2;

   public Iterator3d(C_4675_ posStart, C_4675_ posEnd, int width, int height) {
      boolean revX = posStart.u() > posEnd.u();
      boolean revY = posStart.v() > posEnd.v();
      boolean revZ = posStart.w() > posEnd.w();
      posStart = this.reverseCoord(posStart, revX, revY, revZ);
      posEnd = this.reverseCoord(posEnd, revX, revY, revZ);
      this.kX = revX ? -1 : 1;
      this.kY = revY ? -1 : 1;
      this.kZ = revZ ? -1 : 1;
      C_3046_ vec = new C_3046_((double)(posEnd.u() - posStart.u()), (double)(posEnd.v() - posStart.v()), (double)(posEnd.w() - posStart.w()));
      C_3046_ vecN = vec.m_82541_();
      C_3046_ vecX = new C_3046_(1.0, 0.0, 0.0);
      double dotX = vecN.m_82526_(vecX);
      double dotXabs = Math.abs(dotX);
      C_3046_ vecY = new C_3046_(0.0, 1.0, 0.0);
      double dotY = vecN.m_82526_(vecY);
      double dotYabs = Math.abs(dotY);
      C_3046_ vecZ = new C_3046_(0.0, 0.0, 1.0);
      double dotZ = vecN.m_82526_(vecZ);
      double dotZabs = Math.abs(dotZ);
      if (dotZabs >= dotYabs && dotZabs >= dotXabs) {
         this.axis = 2;
         C_4675_ pos1 = new C_4675_(posStart.w(), posStart.v() - width, posStart.u() - height);
         C_4675_ pos2 = new C_4675_(posEnd.w(), posStart.v() + width + 1, posStart.u() + height + 1);
         int countX = posEnd.w() - posStart.w();
         double deltaY = (double)(posEnd.v() - posStart.v()) / (1.0 * (double)countX);
         double deltaZ = (double)(posEnd.u() - posStart.u()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else if (dotYabs >= dotXabs && dotYabs >= dotZabs) {
         this.axis = 1;
         C_4675_ pos1 = new C_4675_(posStart.v(), posStart.u() - width, posStart.w() - height);
         C_4675_ pos2 = new C_4675_(posEnd.v(), posStart.u() + width + 1, posStart.w() + height + 1);
         int countX = posEnd.v() - posStart.v();
         double deltaY = (double)(posEnd.u() - posStart.u()) / (1.0 * (double)countX);
         double deltaZ = (double)(posEnd.w() - posStart.w()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else {
         this.axis = 0;
         C_4675_ pos1 = new C_4675_(posStart.u(), posStart.v() - width, posStart.w() - height);
         C_4675_ pos2 = new C_4675_(posEnd.u(), posStart.v() + width + 1, posStart.w() + height + 1);
         int countX = posEnd.u() - posStart.u();
         double deltaY = (double)(posEnd.v() - posStart.v()) / (1.0 * (double)countX);
         double deltaZ = (double)(posEnd.w() - posStart.w()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      }
   }

   private C_4675_ reverseCoord(C_4675_ pos, boolean revX, boolean revY, boolean revZ) {
      if (revX) {
         pos = new C_4675_(-pos.u(), pos.v(), pos.w());
      }

      if (revY) {
         pos = new C_4675_(pos.u(), -pos.v(), pos.w());
      }

      if (revZ) {
         pos = new C_4675_(pos.u(), pos.v(), -pos.w());
      }

      return pos;
   }

   public boolean hasNext() {
      return this.iteratorAxis.hasNext();
   }

   public C_4675_ next() {
      C_4675_ pos = this.iteratorAxis.next();
      switch (this.axis) {
         case 0:
            this.blockPos.setXyz(pos.u() * this.kX, pos.v() * this.kY, pos.w() * this.kZ);
            return this.blockPos;
         case 1:
            this.blockPos.setXyz(pos.v() * this.kX, pos.u() * this.kY, pos.w() * this.kZ);
            return this.blockPos;
         case 2:
            this.blockPos.setXyz(pos.w() * this.kX, pos.v() * this.kY, pos.u() * this.kZ);
            return this.blockPos;
         default:
            this.blockPos.setXyz(pos.u() * this.kX, pos.v() * this.kY, pos.w() * this.kZ);
            return this.blockPos;
      }
   }

   public void remove() {
      throw new RuntimeException("Not supported");
   }

   public static void main(String[] args) {
      C_4675_ posStart = new C_4675_(10, 20, 30);
      C_4675_ posEnd = new C_4675_(30, 40, 20);
      Iterator3d it = new Iterator3d(posStart, posEnd, 1, 1);

      while (it.hasNext()) {
         C_4675_ blockPos = it.next();
         System.out.println(blockPos + "");
      }
   }
}
