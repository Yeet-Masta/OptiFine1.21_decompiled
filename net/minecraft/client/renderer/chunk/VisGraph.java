package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;

public class VisGraph {
   private static final int f_173723_ = 4;
   private static final int f_173724_ = 16;
   private static final int f_173725_ = 15;
   private static final int f_173726_ = 4096;
   private static final int f_173727_ = 0;
   private static final int f_173728_ = 4;
   private static final int f_173729_ = 8;
   private static final int f_112949_ = (int)Math.pow(16.0, 0.0);
   private static final int f_112950_ = (int)Math.pow(16.0, 1.0);
   private static final int f_112951_ = (int)Math.pow(16.0, 2.0);
   private static final int f_173730_ = -1;
   private static final net.minecraft.core.Direction[] f_112952_ = net.minecraft.core.Direction.values();
   private final BitSet f_112953_ = new BitSet(4096);
   private static final int[] f_112954_ = net.minecraft.Util.m_137469_(new int[1352], intArrayIn -> {
      int i = 0;
      int j = 15;
      int k = 0;

      for (int l = 0; l < 16; l++) {
         for (int i1 = 0; i1 < 16; i1++) {
            for (int j1 = 0; j1 < 16; j1++) {
               if (l == 0 || l == 15 || i1 == 0 || i1 == 15 || j1 == 0 || j1 == 15) {
                  intArrayIn[k++] = m_112961_(l, i1, j1);
               }
            }
         }
      }
   });
   private int f_112955_ = 4096;

   public void m_112971_(BlockPos pos) {
      this.f_112953_.set(m_112975_(pos), true);
      this.f_112955_--;
   }

   private static int m_112975_(BlockPos pos) {
      return m_112961_(pos.m_123341_() & 15, pos.m_123342_() & 15, pos.m_123343_() & 15);
   }

   private static int m_112961_(int x, int y, int z) {
      return x << 0 | y << 8 | z << 4;
   }

   public net.minecraft.client.renderer.chunk.VisibilitySet m_112958_() {
      net.minecraft.client.renderer.chunk.VisibilitySet visibilityset = new net.minecraft.client.renderer.chunk.VisibilitySet();
      if (4096 - this.f_112955_ < 256) {
         visibilityset.m_112992_(true);
      } else if (this.f_112955_ == 0) {
         visibilityset.m_112992_(false);
      } else {
         for (int i : f_112954_) {
            if (!this.f_112953_.get(i)) {
               visibilityset.m_112990_(this.m_112959_(i));
            }
         }
      }

      return visibilityset;
   }

   private Set<net.minecraft.core.Direction> m_112959_(int pos) {
      Set<net.minecraft.core.Direction> set = EnumSet.noneOf(net.minecraft.core.Direction.class);
      IntPriorityQueue intpriorityqueue = new IntArrayFIFOQueue(384);
      intpriorityqueue.enqueue(pos);
      this.f_112953_.set(pos, true);

      while (!intpriorityqueue.isEmpty()) {
         int i = intpriorityqueue.dequeueInt();
         this.m_112968_(i, set);

         for (net.minecraft.core.Direction direction : f_112952_) {
            int j = this.m_112965_(i, direction);
            if (j >= 0 && !this.f_112953_.get(j)) {
               this.f_112953_.set(j, true);
               intpriorityqueue.enqueue(j);
            }
         }
      }

      return set;
   }

   private void m_112968_(int pos, Set<net.minecraft.core.Direction> setFacings) {
      int i = pos >> 0 & 15;
      if (i == 0) {
         setFacings.add(net.minecraft.core.Direction.WEST);
      } else if (i == 15) {
         setFacings.add(net.minecraft.core.Direction.EAST);
      }

      int j = pos >> 8 & 15;
      if (j == 0) {
         setFacings.add(net.minecraft.core.Direction.DOWN);
      } else if (j == 15) {
         setFacings.add(net.minecraft.core.Direction.UP);
      }

      int k = pos >> 4 & 15;
      if (k == 0) {
         setFacings.add(net.minecraft.core.Direction.NORTH);
      } else if (k == 15) {
         setFacings.add(net.minecraft.core.Direction.SOUTH);
      }
   }

   private int m_112965_(int pos, net.minecraft.core.Direction facing) {
      switch (facing) {
         case DOWN:
            if ((pos >> 8 & 15) == 0) {
               return -1;
            }

            return pos - f_112951_;
         case UP:
            if ((pos >> 8 & 15) == 15) {
               return -1;
            }

            return pos + f_112951_;
         case NORTH:
            if ((pos >> 4 & 15) == 0) {
               return -1;
            }

            return pos - f_112950_;
         case SOUTH:
            if ((pos >> 4 & 15) == 15) {
               return -1;
            }

            return pos + f_112950_;
         case WEST:
            if ((pos >> 0 & 15) == 0) {
               return -1;
            }

            return pos - f_112949_;
         case EAST:
            if ((pos >> 0 & 15) == 15) {
               return -1;
            }

            return pos + f_112949_;
         default:
            return -1;
      }
   }
}
