package net.optifine;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.core.BlockPos;

public class BlockPosM extends BlockPos {
   private int mx;
   private int my;
   private int mz;
   private int level;
   private BlockPosM[] facings;
   private boolean needsUpdate;

   public BlockPosM() {
      this(0, 0, 0, 0);
   }

   public BlockPosM(int x, int y, int z) {
      this(x, y, z, 0);
   }

   public BlockPosM(double xIn, double yIn, double zIn) {
      this(net.minecraft.util.Mth.m_14107_(xIn), net.minecraft.util.Mth.m_14107_(yIn), net.minecraft.util.Mth.m_14107_(zIn));
   }

   public BlockPosM(int x, int y, int z, int level) {
      super(0, 0, 0);
      this.mx = x;
      this.my = y;
      this.mz = z;
      this.level = level;
   }

   public int m_123341_() {
      return this.mx;
   }

   public int m_123342_() {
      return this.my;
   }

   public int m_123343_() {
      return this.mz;
   }

   public void setXyz(int x, int y, int z) {
      this.mx = x;
      this.my = y;
      this.mz = z;
      this.needsUpdate = true;
   }

   public BlockPosM setXyz(double xIn, double yIn, double zIn) {
      this.setXyz(net.minecraft.util.Mth.m_14107_(xIn), net.minecraft.util.Mth.m_14107_(yIn), net.minecraft.util.Mth.m_14107_(zIn));
      return this;
   }

   public BlockPos m_121945_(net.minecraft.core.Direction facing) {
      if (this.level <= 0) {
         return super.m_5484_(facing, 1).m_7949_();
      } else {
         if (this.facings == null) {
            this.facings = new BlockPosM[net.minecraft.core.Direction.f_122346_.length];
         }

         if (this.needsUpdate) {
            this.update();
         }

         int index = facing.m_122411_();
         BlockPosM bpm = this.facings[index];
         if (bpm == null) {
            int nx = this.mx + facing.m_122429_();
            int ny = this.my + facing.m_122430_();
            int nz = this.mz + facing.m_122431_();
            bpm = new BlockPosM(nx, ny, nz, this.level - 1);
            this.facings[index] = bpm;
         }

         return bpm;
      }
   }

   public BlockPos m_5484_(net.minecraft.core.Direction facing, int n) {
      return n == 1 ? this.m_121945_(facing) : super.m_5484_(facing, n).m_7949_();
   }

   public void setPosOffset(BlockPos pos, net.minecraft.core.Direction facing) {
      this.mx = pos.m_123341_() + facing.m_122429_();
      this.my = pos.m_123342_() + facing.m_122430_();
      this.mz = pos.m_123343_() + facing.m_122431_();
   }

   public BlockPos setPosOffset(BlockPos pos, net.minecraft.core.Direction facing, net.minecraft.core.Direction facing2) {
      this.mx = pos.m_123341_() + facing.m_122429_() + facing2.m_122429_();
      this.my = pos.m_123342_() + facing.m_122430_() + facing2.m_122430_();
      this.mz = pos.m_123343_() + facing.m_122431_() + facing2.m_122431_();
      return this;
   }

   private void update() {
      for (int i = 0; i < 6; i++) {
         BlockPosM bpm = this.facings[i];
         if (bpm != null) {
            net.minecraft.core.Direction facing = net.minecraft.core.Direction.f_122346_[i];
            int nx = this.mx + facing.m_122429_();
            int ny = this.my + facing.m_122430_();
            int nz = this.mz + facing.m_122431_();
            bpm.setXyz(nx, ny, nz);
         }
      }

      this.needsUpdate = false;
   }

   public BlockPos m_7949_() {
      return new BlockPos(this.mx, this.my, this.mz);
   }

   public static Iterable<BlockPosM> getAllInBoxMutableM(BlockPos from, BlockPos to) {
      final BlockPos posFrom = new BlockPos(
         Math.min(from.m_123341_(), to.m_123341_()), Math.min(from.m_123342_(), to.m_123342_()), Math.min(from.m_123343_(), to.m_123343_())
      );
      final BlockPos posTo = new BlockPos(
         Math.max(from.m_123341_(), to.m_123341_()), Math.max(from.m_123342_(), to.m_123342_()), Math.max(from.m_123343_(), to.m_123343_())
      );
      return new Iterable<BlockPosM>() {
         public Iterator<BlockPosM> iterator() {
            return new AbstractIterator<BlockPosM>() {
               private BlockPosM posM = null;

               protected BlockPosM computeNext() {
                  if (this.posM == null) {
                     this.posM = new BlockPosM(posFrom.m_123341_(), posFrom.m_123342_(), posFrom.m_123343_(), 3);
                     return this.posM;
                  } else if (this.posM.equals(posTo)) {
                     return (BlockPosM)this.endOfData();
                  } else {
                     int bx = this.posM.m_123341_();
                     int by = this.posM.m_123342_();
                     int bz = this.posM.m_123343_();
                     if (bx < posTo.m_123341_()) {
                        bx++;
                     } else if (bz < posTo.m_123343_()) {
                        bx = posFrom.m_123341_();
                        bz++;
                     } else if (by < posTo.m_123342_()) {
                        bx = posFrom.m_123341_();
                        bz = posFrom.m_123343_();
                        by++;
                     }

                     this.posM.setXyz(bx, by, bz);
                     return this.posM;
                  }
               }
            };
         }
      };
   }
}
