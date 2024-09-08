package com.mojang.blaze3d.platform;

public enum GlStateManager$LogicOp {
   AND(5377),
   AND_INVERTED(5380),
   AND_REVERSE(5378),
   CLEAR(5376),
   COPY(5379),
   COPY_INVERTED(5388),
   EQUIV(5385),
   INVERT(5386),
   NAND(5390),
   NOOP(5381),
   NOR(5384),
   OR(5383),
   OR_INVERTED(5389),
   OR_REVERSE(5387),
   SET(5391),
   XOR(5382);

   public final int f_84715_;

   private GlStateManager$LogicOp(final int opcodeIn) {
      this.f_84715_ = opcodeIn;
   }
}
