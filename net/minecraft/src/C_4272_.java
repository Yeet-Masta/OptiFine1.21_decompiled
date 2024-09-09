package net.minecraft.src;

import java.util.Iterator;
import java.util.Set;

public class C_4272_ {
   private static final int f_112979_ = C_4687_.values().length;
   private long bits;

   public void m_112990_(Set facing) {
      Iterator var2 = facing.iterator();

      while(var2.hasNext()) {
         C_4687_ direction = (C_4687_)var2.next();
         Iterator var4 = facing.iterator();

         while(var4.hasNext()) {
            C_4687_ direction1 = (C_4687_)var4.next();
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
      C_4687_[] var2 = C_4687_.values();
      int var3 = var2.length;

      int var4;
      C_4687_ direction2;
      for(var4 = 0; var4 < var3; ++var4) {
         direction2 = var2[var4];
         stringbuilder.append(' ').append(direction2.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');
      var2 = C_4687_.values();
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         direction2 = var2[var4];
         stringbuilder.append(direction2.toString().toUpperCase().charAt(0));
         C_4687_[] var6 = C_4687_.values();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            C_4687_ direction1 = var6[var8];
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
