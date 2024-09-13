package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.optifine.BlockPosM;

public class Iterator3d implements Iterator<BlockPos> {
   private IteratorAxis iteratorAxis;
   private BlockPosM blockPos = new BlockPosM(0, 0, 0);
   private int axis = 0;
   private int f_144146_;
   private int f_144147_;
   private int f_144148_;
   private static int AXIS_X;
   private static int AXIS_Y;
   private static int AXIS_Z;

   public Iterator3d(BlockPos posStart, BlockPos posEnd, int width, int height) {
      boolean revX = posStart.m_123341_() > posEnd.m_123341_();
      boolean revY = posStart.m_123342_() > posEnd.m_123342_();
      boolean revZ = posStart.m_123343_() > posEnd.m_123343_();
      posStart = this.reverseCoord(posStart, revX, revY, revZ);
      posEnd = this.reverseCoord(posEnd, revX, revY, revZ);
      this.f_144146_ = revX ? -1 : 1;
      this.f_144147_ = revY ? -1 : 1;
      this.f_144148_ = revZ ? -1 : 1;
      Vec3 vec = new Vec3(
         (double)(posEnd.m_123341_() - posStart.m_123341_()),
         (double)(posEnd.m_123342_() - posStart.m_123342_()),
         (double)(posEnd.m_123343_() - posStart.m_123343_())
      );
      Vec3 vecN = vec.m_82541_();
      Vec3 vecX = new Vec3(1.0, 0.0, 0.0);
      double dotX = vecN.m_82526_(vecX);
      double dotXabs = Math.abs(dotX);
      Vec3 vecY = new Vec3(0.0, 1.0, 0.0);
      double dotY = vecN.m_82526_(vecY);
      double dotYabs = Math.abs(dotY);
      Vec3 vecZ = new Vec3(0.0, 0.0, 1.0);
      double dotZ = vecN.m_82526_(vecZ);
      double dotZabs = Math.abs(dotZ);
      if (dotZabs >= dotYabs && dotZabs >= dotXabs) {
         this.axis = 2;
         BlockPos pos1 = new BlockPos(posStart.m_123343_(), posStart.m_123342_() - width, posStart.m_123341_() - height);
         BlockPos pos2 = new BlockPos(posEnd.m_123343_(), posStart.m_123342_() + width + 1, posStart.m_123341_() + height + 1);
         int countX = posEnd.m_123343_() - posStart.m_123343_();
         double deltaY = (double)(posEnd.m_123342_() - posStart.m_123342_()) / (1.0 * (double)countX);
         double deltaZ = (double)(posEnd.m_123341_() - posStart.m_123341_()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else if (dotYabs >= dotXabs && dotYabs >= dotZabs) {
         this.axis = 1;
         BlockPos pos1 = new BlockPos(posStart.m_123342_(), posStart.m_123341_() - width, posStart.m_123343_() - height);
         BlockPos pos2 = new BlockPos(posEnd.m_123342_(), posStart.m_123341_() + width + 1, posStart.m_123343_() + height + 1);
         int countX = posEnd.m_123342_() - posStart.m_123342_();
         double deltaY = (double)(posEnd.m_123341_() - posStart.m_123341_()) / (1.0 * (double)countX);
         double deltaZ = (double)(posEnd.m_123343_() - posStart.m_123343_()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else {
         this.axis = 0;
         BlockPos pos1 = new BlockPos(posStart.m_123341_(), posStart.m_123342_() - width, posStart.m_123343_() - height);
         BlockPos pos2 = new BlockPos(posEnd.m_123341_(), posStart.m_123342_() + width + 1, posStart.m_123343_() + height + 1);
         int countX = posEnd.m_123341_() - posStart.m_123341_();
         double deltaY = (double)(posEnd.m_123342_() - posStart.m_123342_()) / (1.0 * (double)countX);
         double deltaZ = (double)(posEnd.m_123343_() - posStart.m_123343_()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      }
   }

   private BlockPos reverseCoord(BlockPos pos, boolean revX, boolean revY, boolean revZ) {
      if (revX) {
         pos = new BlockPos(-pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
      }

      if (revY) {
         pos = new BlockPos(pos.m_123341_(), -pos.m_123342_(), pos.m_123343_());
      }

      if (revZ) {
         pos = new BlockPos(pos.m_123341_(), pos.m_123342_(), -pos.m_123343_());
      }

      return pos;
   }

   public boolean hasNext() {
      return this.iteratorAxis.hasNext();
   }

   public BlockPos next() {
      BlockPos pos = this.iteratorAxis.next();
      switch (this.axis) {
         case 0:
            this.blockPos.setXyz(pos.m_123341_() * this.f_144146_, pos.m_123342_() * this.f_144147_, pos.m_123343_() * this.f_144148_);
            return this.blockPos;
         case 1:
            this.blockPos.setXyz(pos.m_123342_() * this.f_144146_, pos.m_123341_() * this.f_144147_, pos.m_123343_() * this.f_144148_);
            return this.blockPos;
         case 2:
            this.blockPos.setXyz(pos.m_123343_() * this.f_144146_, pos.m_123342_() * this.f_144147_, pos.m_123341_() * this.f_144148_);
            return this.blockPos;
         default:
            this.blockPos.setXyz(pos.m_123341_() * this.f_144146_, pos.m_123342_() * this.f_144147_, pos.m_123343_() * this.f_144148_);
            return this.blockPos;
      }
   }

   public void remove() {
      throw new RuntimeException("Not supported");
   }

   public static void main(String[] args) {
      BlockPos posStart = new BlockPos(10, 20, 30);
      BlockPos posEnd = new BlockPos(30, 40, 20);
      Iterator3d it = new Iterator3d(posStart, posEnd, 1, 1);

      while (it.hasNext()) {
         BlockPos blockPos = it.next();
         System.out.println(blockPos + "");
      }
   }
}
