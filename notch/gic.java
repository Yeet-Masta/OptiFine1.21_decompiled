package net.minecraft.src;

import java.util.Set;

public class C_4272_ {
   private static final int f_112979_ = C_4687_.values().length;
   private long bits;

   public void m_112990_(Set<C_4687_> facing) {
      for (C_4687_ direction : facing) {
         for (C_4687_ direction1 : facing) {
            this.m_112986_(direction, direction1, true);
         }
      }
   }

   public void m_112986_(C_4687_ facing, C_4687_ facing2, boolean value) {
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

   public boolean m_112983_(C_4687_ facing, C_4687_ facing2) {
      return this.getBit(facing.ordinal() + facing2.ordinal() * f_112979_);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');

      for (C_4687_ direction : C_4687_.values()) {
         stringbuilder.append(' ').append(direction.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');

      for (C_4687_ direction2 : C_4687_.values()) {
         stringbuilder.append(direction2.toString().toUpperCase().charAt(0));

         for (C_4687_ direction1 : C_4687_.values()) {
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
