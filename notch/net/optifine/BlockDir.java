package net.optifine;

import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;

public enum BlockDir {
   DOWN(C_4687_.DOWN),
   UP(C_4687_.UP),
   NORTH(C_4687_.NORTH),
   SOUTH(C_4687_.SOUTH),
   WEST(C_4687_.WEST),
   EAST(C_4687_.EAST),
   NORTH_WEST(C_4687_.NORTH, C_4687_.WEST),
   NORTH_EAST(C_4687_.NORTH, C_4687_.EAST),
   SOUTH_WEST(C_4687_.SOUTH, C_4687_.WEST),
   SOUTH_EAST(C_4687_.SOUTH, C_4687_.EAST),
   DOWN_NORTH(C_4687_.DOWN, C_4687_.NORTH),
   DOWN_SOUTH(C_4687_.DOWN, C_4687_.SOUTH),
   UP_NORTH(C_4687_.UP, C_4687_.NORTH),
   UP_SOUTH(C_4687_.UP, C_4687_.SOUTH),
   DOWN_WEST(C_4687_.DOWN, C_4687_.WEST),
   DOWN_EAST(C_4687_.DOWN, C_4687_.EAST),
   UP_WEST(C_4687_.UP, C_4687_.WEST),
   UP_EAST(C_4687_.UP, C_4687_.EAST);

   private C_4687_ facing1;
   private C_4687_ facing2;

   private BlockDir(C_4687_ facing1) {
      this.facing1 = facing1;
   }

   private BlockDir(C_4687_ facing1, C_4687_ facing2) {
      this.facing1 = facing1;
      this.facing2 = facing2;
   }

   public C_4687_ getFacing1() {
      return this.facing1;
   }

   public C_4687_ getFacing2() {
      return this.facing2;
   }

   C_4675_ offset(C_4675_ pos) {
      pos = pos.m_5484_(this.facing1, 1);
      if (this.facing2 != null) {
         pos = pos.m_5484_(this.facing2, 1);
      }

      return pos;
   }

   public int getOffsetX() {
      int offset = this.facing1.m_122429_();
      if (this.facing2 != null) {
         offset += this.facing2.m_122429_();
      }

      return offset;
   }

   public int getOffsetY() {
      int offset = this.facing1.m_122430_();
      if (this.facing2 != null) {
         offset += this.facing2.m_122430_();
      }

      return offset;
   }

   public int getOffsetZ() {
      int offset = this.facing1.m_122431_();
      if (this.facing2 != null) {
         offset += this.facing2.m_122431_();
      }

      return offset;
   }

   public boolean isDouble() {
      return this.facing2 != null;
   }
}
