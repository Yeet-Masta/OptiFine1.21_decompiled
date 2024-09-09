package net.minecraft.client.renderer.chunk;

import java.util.Set;

public class VisibilitySet {
   private static final int f_112979_ = net.minecraft.core.Direction.values().length;
   private long bits;

   public void m_112990_(Set<net.minecraft.core.Direction> facing) {
      for (net.minecraft.core.Direction direction : facing) {
         for (net.minecraft.core.Direction direction1 : facing) {
            this.m_112986_(direction, direction1, true);
         }
      }
   }

   public void m_112986_(net.minecraft.core.Direction facing, net.minecraft.core.Direction facing2, boolean value) {
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

   public boolean m_112983_(net.minecraft.core.Direction facing, net.minecraft.core.Direction facing2) {
      return this.getBit(facing.ordinal() + facing2.ordinal() * f_112979_);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');

      for (net.minecraft.core.Direction direction : net.minecraft.core.Direction.values()) {
         stringbuilder.append(' ').append(direction.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');

      for (net.minecraft.core.Direction direction2 : net.minecraft.core.Direction.values()) {
         stringbuilder.append(direction2.toString().toUpperCase().charAt(0));

         for (net.minecraft.core.Direction direction1 : net.minecraft.core.Direction.values()) {
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
