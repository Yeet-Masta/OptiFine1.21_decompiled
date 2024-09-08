package net.optifine;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;

public class BlockPosM extends C_4675_ {
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
      this(C_188_.m_14107_(xIn), C_188_.m_14107_(yIn), C_188_.m_14107_(zIn));
   }

   public BlockPosM(int x, int y, int z, int level) {
      super(0, 0, 0);
      this.mx = x;
      this.my = y;
      this.mz = z;
      this.level = level;
   }

   public int u() {
      return this.mx;
   }

   public int v() {
      return this.my;
   }

   public int w() {
      return this.mz;
   }

   public void setXyz(int x, int y, int z) {
      this.mx = x;
      this.my = y;
      this.mz = z;
      this.needsUpdate = true;
   }

   public BlockPosM setXyz(double xIn, double yIn, double zIn) {
      this.setXyz(C_188_.m_14107_(xIn), C_188_.m_14107_(yIn), C_188_.m_14107_(zIn));
      return this;
   }

   public C_4675_ m_121945_(C_4687_ facing) {
      if (this.level <= 0) {
         return super.m_5484_(facing, 1).m_7949_();
      } else {
         if (this.facings == null) {
            this.facings = new BlockPosM[C_4687_.f_122346_.length];
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

   public C_4675_ m_5484_(C_4687_ facing, int n) {
      return n == 1 ? this.m_121945_(facing) : super.m_5484_(facing, n).m_7949_();
   }

   public void setPosOffset(C_4675_ pos, C_4687_ facing) {
      this.mx = pos.u() + facing.m_122429_();
      this.my = pos.v() + facing.m_122430_();
      this.mz = pos.w() + facing.m_122431_();
   }

   public C_4675_ setPosOffset(C_4675_ pos, C_4687_ facing, C_4687_ facing2) {
      this.mx = pos.u() + facing.m_122429_() + facing2.m_122429_();
      this.my = pos.v() + facing.m_122430_() + facing2.m_122430_();
      this.mz = pos.w() + facing.m_122431_() + facing2.m_122431_();
      return this;
   }

   private void update() {
      for (int i = 0; i < 6; i++) {
         BlockPosM bpm = this.facings[i];
         if (bpm != null) {
            C_4687_ facing = C_4687_.f_122346_[i];
            int nx = this.mx + facing.m_122429_();
            int ny = this.my + facing.m_122430_();
            int nz = this.mz + facing.m_122431_();
            bpm.setXyz(nx, ny, nz);
         }
      }

      this.needsUpdate = false;
   }

   public C_4675_ m_7949_() {
      return new C_4675_(this.mx, this.my, this.mz);
   }

   public static Iterable<BlockPosM> getAllInBoxMutableM(C_4675_ from, C_4675_ to) {
      final C_4675_ posFrom = new C_4675_(Math.min(from.u(), to.u()), Math.min(from.v(), to.v()), Math.min(from.w(), to.w()));
      final C_4675_ posTo = new C_4675_(Math.max(from.u(), to.u()), Math.max(from.v(), to.v()), Math.max(from.w(), to.w()));
      return new Iterable<BlockPosM>() {
         public Iterator<BlockPosM> iterator() {
            return new AbstractIterator<BlockPosM>() {
               private BlockPosM posM = null;

               protected BlockPosM computeNext() {
                  if (this.posM == null) {
                     this.posM = new BlockPosM(posFrom.u(), posFrom.v(), posFrom.w(), 3);
                     return this.posM;
                  } else if (this.posM.equals(posTo)) {
                     return (BlockPosM)this.endOfData();
                  } else {
                     int bx = this.posM.u();
                     int by = this.posM.v();
                     int bz = this.posM.w();
                     if (bx < posTo.u()) {
                        bx++;
                     } else if (bz < posTo.w()) {
                        bx = posFrom.u();
                        bz++;
                     } else if (by < posTo.v()) {
                        bx = posFrom.u();
                        bz = posFrom.w();
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
