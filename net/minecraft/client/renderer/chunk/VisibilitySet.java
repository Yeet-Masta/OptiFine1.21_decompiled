package net.minecraft.client.renderer.chunk;

import java.util.Set;
import net.minecraft.core.Direction;

public class VisibilitySet {
   private static int f_112979_ = Direction.values().length;
   private long bits;

   public void m_112990_(Set<Direction> facing) {
      for (Direction direction : facing) {
         for (Direction direction1 : facing) {
            this.m_112986_(direction, direction1, true);
         }
      }
   }

   public void m_112986_(Direction facing, Direction facing2, boolean value) {
      this.setBit(facing.ordinal() + facing2.ordinal() * f_112979_, value);
      this.setBit(facing2.ordinal() + facing.ordinal() * f_112979_, value);
   }

   public void m_112992_(boolean visible) {
      if (visible) {
         this.bits = -1L;
      } else {
         this.bits = 0L;
      }
   }

   public boolean m_112983_(Direction facing, Direction facing2) {
      return this.getBit(facing.ordinal() + facing2.ordinal() * f_112979_);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');

      for (Direction direction : Direction.values()) {
         stringbuilder.append(' ').append(direction.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');

      for (Direction direction2 : Direction.values()) {
         stringbuilder.append(direction2.toString().toUpperCase().charAt(0));

         for (Direction direction1 : Direction.values()) {
            if (direction2 == direction1) {
               stringbuilder.append("  ");
            } else {
               boolean flag = this.m_112983_(direction2, direction1);
               stringbuilder.append(' ').append((char)(flag ? 'Y' : 'n'));
            }
         }

         stringbuilder.append('\n');
      }

      return stringbuilder.toString();
   }

   private boolean getBit(int i) {
      return (this.bits & 1L << i) != 0L;
   }

   private void setBit(int i, boolean on) {
      if (on) {
         this.setBit(i);
      } else {
         this.clearBit(i);
      }
   }

   private void setBit(int i) {
      this.bits |= 1L << i;
   }

   private void clearBit(int i) {
      this.bits &= ~(1L << i);
   }
}
