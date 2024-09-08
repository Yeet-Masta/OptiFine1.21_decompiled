package net.optifine.render;

import net.minecraft.src.C_4486_;

public class SpriteRenderData {
   private C_4486_ sprite;
   private int[] positions;
   private int[] counts;

   public SpriteRenderData(C_4486_ sprite, int[] positions, int[] counts) {
      this.sprite = sprite;
      this.positions = positions;
      this.counts = counts;
      if (positions.length != counts.length) {
         throw new IllegalArgumentException(positions.length + " != " + counts.length);
      }
   }

   public C_4486_ getSprite() {
      return this.sprite;
   }

   public int[] getPositions() {
      return this.positions;
   }

   public int[] getCounts() {
      return this.counts;
   }

   public String toString() {
      return this.sprite.getName() + ", " + this.positions.length;
   }
}
