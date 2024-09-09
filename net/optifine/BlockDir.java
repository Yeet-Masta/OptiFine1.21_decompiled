package net.optifine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public enum BlockDir {
   DOWN(Direction.DOWN),
   // $FF: renamed from: UP net.optifine.BlockDir
   field_36(Direction.field_61),
   NORTH(Direction.NORTH),
   SOUTH(Direction.SOUTH),
   WEST(Direction.WEST),
   EAST(Direction.EAST),
   NORTH_WEST(Direction.NORTH, Direction.WEST),
   NORTH_EAST(Direction.NORTH, Direction.EAST),
   SOUTH_WEST(Direction.SOUTH, Direction.WEST),
   SOUTH_EAST(Direction.SOUTH, Direction.EAST),
   DOWN_NORTH(Direction.DOWN, Direction.NORTH),
   DOWN_SOUTH(Direction.DOWN, Direction.SOUTH),
   UP_NORTH(Direction.field_61, Direction.NORTH),
   UP_SOUTH(Direction.field_61, Direction.SOUTH),
   DOWN_WEST(Direction.DOWN, Direction.WEST),
   DOWN_EAST(Direction.DOWN, Direction.EAST),
   UP_WEST(Direction.field_61, Direction.WEST),
   UP_EAST(Direction.field_61, Direction.EAST);

   private Direction facing1;
   private Direction facing2;

   private BlockDir(Direction facing1) {
      this.facing1 = facing1;
   }

   private BlockDir(Direction facing1, Direction facing2) {
      this.facing1 = facing1;
      this.facing2 = facing2;
   }

   public Direction getFacing1() {
      return this.facing1;
   }

   public Direction getFacing2() {
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

   // $FF: synthetic method
   private static BlockDir[] $values() {
      return new BlockDir[]{DOWN, field_36, NORTH, SOUTH, WEST, EAST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST, DOWN_NORTH, DOWN_SOUTH, UP_NORTH, UP_SOUTH, DOWN_WEST, DOWN_EAST, UP_WEST, UP_EAST};
   }
}
