package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.optifine.BlockPosM;

public class Iterator3d implements Iterator {
   private IteratorAxis iteratorAxis;
   private BlockPosM blockPos = new BlockPosM(0, 0, 0);
   private int axis = 0;
   // $FF: renamed from: kX int
   private int field_56;
   // $FF: renamed from: kY int
   private int field_57;
   // $FF: renamed from: kZ int
   private int field_58;
   private static final int AXIS_X = 0;
   private static final int AXIS_Y = 1;
   private static final int AXIS_Z = 2;

   public Iterator3d(BlockPos posStart, BlockPos posEnd, int width, int height) {
      boolean revX = posStart.m_123341_() > posEnd.m_123341_();
      boolean revY = posStart.m_123342_() > posEnd.m_123342_();
      boolean revZ = posStart.m_123343_() > posEnd.m_123343_();
      posStart = this.reverseCoord(posStart, revX, revY, revZ);
      posEnd = this.reverseCoord(posEnd, revX, revY, revZ);
      this.field_56 = revX ? -1 : 1;
      this.field_57 = revY ? -1 : 1;
      this.field_58 = revZ ? -1 : 1;
      Vec3 vec = new Vec3((double)(posEnd.m_123341_() - posStart.m_123341_()), (double)(posEnd.m_123342_() - posStart.m_123342_()), (double)(posEnd.m_123343_() - posStart.m_123343_()));
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
      BlockPos pos1;
      BlockPos pos2;
      int countX;
      double deltaY;
      double deltaZ;
      if (dotZabs >= dotYabs && dotZabs >= dotXabs) {
         this.axis = 2;
         pos1 = new BlockPos(posStart.m_123343_(), posStart.m_123342_() - width, posStart.m_123341_() - height);
         pos2 = new BlockPos(posEnd.m_123343_(), posStart.m_123342_() + width + 1, posStart.m_123341_() + height + 1);
         countX = posEnd.m_123343_() - posStart.m_123343_();
         deltaY = (double)(posEnd.m_123342_() - posStart.m_123342_()) / (1.0 * (double)countX);
         deltaZ = (double)(posEnd.m_123341_() - posStart.m_123341_()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else if (dotYabs >= dotXabs && dotYabs >= dotZabs) {
         this.axis = 1;
         pos1 = new BlockPos(posStart.m_123342_(), posStart.m_123341_() - width, posStart.m_123343_() - height);
         pos2 = new BlockPos(posEnd.m_123342_(), posStart.m_123341_() + width + 1, posStart.m_123343_() + height + 1);
         countX = posEnd.m_123342_() - posStart.m_123342_();
         deltaY = (double)(posEnd.m_123341_() - posStart.m_123341_()) / (1.0 * (double)countX);
         deltaZ = (double)(posEnd.m_123343_() - posStart.m_123343_()) / (1.0 * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else {
         this.axis = 0;
         pos1 = new BlockPos(posStart.m_123341_(), posStart.m_123342_() - width, posStart.m_123343_() - height);
         pos2 = new BlockPos(posEnd.m_123341_(), posStart.m_123342_() + width + 1, posStart.m_123343_() + height + 1);
         countX = posEnd.m_123341_() - posStart.m_123341_();
         deltaY = (double)(posEnd.m_123342_() - posStart.m_123342_()) / (1.0 * (double)countX);
         deltaZ = (double)(posEnd.m_123343_() - posStart.m_123343_()) / (1.0 * (double)countX);
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
            this.blockPos.setXyz(pos.m_123341_() * this.field_56, pos.m_123342_() * this.field_57, pos.m_123343_() * this.field_58);
            return this.blockPos;
         case 1:
            this.blockPos.setXyz(pos.m_123342_() * this.field_56, pos.m_123341_() * this.field_57, pos.m_123343_() * this.field_58);
            return this.blockPos;
         case 2:
            this.blockPos.setXyz(pos.m_123343_() * this.field_56, pos.m_123342_() * this.field_57, pos.m_123341_() * this.field_58);
            return this.blockPos;
         default:
            this.blockPos.setXyz(pos.m_123341_() * this.field_56, pos.m_123342_() * this.field_57, pos.m_123343_() * this.field_58);
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

      while(it.hasNext()) {
         BlockPos blockPos = it.next();
         System.out.println("" + String.valueOf(blockPos));
      }

   }
}
