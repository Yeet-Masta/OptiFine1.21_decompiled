package net.optifine;

import net.minecraft.core.BlockPos;

public enum BlockDir {
   DOWN(net.minecraft.core.Direction.DOWN),
   UP(net.minecraft.core.Direction.UP),
   NORTH(net.minecraft.core.Direction.NORTH),
   SOUTH(net.minecraft.core.Direction.SOUTH),
   WEST(net.minecraft.core.Direction.WEST),
   EAST(net.minecraft.core.Direction.EAST),
   NORTH_WEST(net.minecraft.core.Direction.NORTH, net.minecraft.core.Direction.WEST),
   NORTH_EAST(net.minecraft.core.Direction.NORTH, net.minecraft.core.Direction.EAST),
   SOUTH_WEST(net.minecraft.core.Direction.SOUTH, net.minecraft.core.Direction.WEST),
   SOUTH_EAST(net.minecraft.core.Direction.SOUTH, net.minecraft.core.Direction.EAST),
   DOWN_NORTH(net.minecraft.core.Direction.DOWN, net.minecraft.core.Direction.NORTH),
   DOWN_SOUTH(net.minecraft.core.Direction.DOWN, net.minecraft.core.Direction.SOUTH),
   UP_NORTH(net.minecraft.core.Direction.UP, net.minecraft.core.Direction.NORTH),
   UP_SOUTH(net.minecraft.core.Direction.UP, net.minecraft.core.Direction.SOUTH),
   DOWN_WEST(net.minecraft.core.Direction.DOWN, net.minecraft.core.Direction.WEST),
   DOWN_EAST(net.minecraft.core.Direction.DOWN, net.minecraft.core.Direction.EAST),
   UP_WEST(net.minecraft.core.Direction.UP, net.minecraft.core.Direction.WEST),
   UP_EAST(net.minecraft.core.Direction.UP, net.minecraft.core.Direction.EAST);

   private net.minecraft.core.Direction facing1;
   private net.minecraft.core.Direction facing2;

   private BlockDir(net.minecraft.core.Direction facing1) {
      this.facing1 = facing1;
   }

   private BlockDir(net.minecraft.core.Direction facing1, net.minecraft.core.Direction facing2) {
      this.facing1 = facing1;
      this.facing2 = facing2;
   }

   public net.minecraft.core.Direction getFacing1() {
      return this.facing1;
   }

   public net.minecraft.core.Direction getFacing2() {
      return this.facing2;
   }

   BlockPos offset(BlockPos pos) {
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
