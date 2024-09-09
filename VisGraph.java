import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.src.C_4675_;

public class VisGraph {
   private static final int a = 4;
   private static final int b = 16;
   private static final int c = 15;
   private static final int d = 4096;
   private static final int e = 0;
   private static final int f = 4;
   private static final int g = 8;
   private static final int h = (int)Math.pow(16.0, 0.0);
   private static final int i = (int)Math.pow(16.0, 1.0);
   private static final int j = (int)Math.pow(16.0, 2.0);
   private static final int k = -1;
   private static final Direction[] l = Direction.values();
   private final BitSet m = new BitSet(4096);
   private static final int[] n = Util.a(new int[1352], intArrayIn -> {
      int i = 0;
      int j = 15;
      int k = 0;

      for (int l = 0; l < 16; l++) {
         for (int i1 = 0; i1 < 16; i1++) {
            for (int j1 = 0; j1 < 16; j1++) {
               if (l == 0 || l == 15 || i1 == 0 || i1 == 15 || j1 == 0 || j1 == 15) {
                  intArrayIn[k++] = a(l, i1, j1);
               }
            }
         }
      }
   });
   private int o = 4096;

   public void a(C_4675_ pos) {
      this.m.set(b(pos), true);
      this.o--;
   }

   private static int b(C_4675_ pos) {
      return a(pos.m_123341_() & 15, pos.m_123342_() & 15, pos.m_123343_() & 15);
   }

   private static int a(int x, int y, int z) {
      return x << 0 | y << 8 | z << 4;
   }

   public VisibilitySet a() {
      VisibilitySet visibilityset = new VisibilitySet();
      if (4096 - this.o < 256) {
         visibilityset.a(true);
      } else if (this.o == 0) {
         visibilityset.a(false);
      } else {
         for (int i : n) {
            if (!this.m.get(i)) {
               visibilityset.a(this.a(i));
            }
         }
      }

      return visibilityset;
   }

   private Set<Direction> a(int pos) {
      Set<Direction> set = EnumSet.noneOf(Direction.class);
      IntPriorityQueue intpriorityqueue = new IntArrayFIFOQueue(384);
      intpriorityqueue.enqueue(pos);
      this.m.set(pos, true);

      while (!intpriorityqueue.isEmpty()) {
         int i = intpriorityqueue.dequeueInt();
         this.a(i, set);

         for (Direction direction : l) {
            int j = this.a(i, direction);
            if (j >= 0 && !this.m.get(j)) {
               this.m.set(j, true);
               intpriorityqueue.enqueue(j);
            }
         }
      }

      return set;
   }

   private void a(int pos, Set<Direction> setFacings) {
      int i = pos >> 0 & 15;
      if (i == 0) {
         setFacings.add(Direction.e);
      } else if (i == 15) {
         setFacings.add(Direction.f);
      }

      int j = pos >> 8 & 15;
      if (j == 0) {
         setFacings.add(Direction.a);
      } else if (j == 15) {
         setFacings.add(Direction.b);
      }

      int k = pos >> 4 & 15;
      if (k == 0) {
         setFacings.add(Direction.c);
      } else if (k == 15) {
         setFacings.add(Direction.d);
      }
   }

   private int a(int pos, Direction facing) {
      switch (facing) {
         case a:
            if ((pos >> 8 & 15) == 0) {
               return -1;
            }

            return pos - j;
         case b:
            if ((pos >> 8 & 15) == 15) {
               return -1;
            }

            return pos + j;
         case c:
            if ((pos >> 4 & 15) == 0) {
               return -1;
            }

            return pos - i;
         case d:
            if ((pos >> 4 & 15) == 15) {
               return -1;
            }

            return pos + i;
         case e:
            if ((pos >> 0 & 15) == 0) {
               return -1;
            }

            return pos - h;
         case f:
            if ((pos >> 0 & 15) == 15) {
               return -1;
            }

            return pos + h;
         default:
            return -1;
      }
   }
}
