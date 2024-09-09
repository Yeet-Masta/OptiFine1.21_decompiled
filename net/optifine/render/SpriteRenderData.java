package net.optifine.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SpriteRenderData {
   private TextureAtlasSprite sprite;
   private int[] positions;
   private int[] counts;

   public SpriteRenderData(TextureAtlasSprite sprite, int[] positions, int[] counts) {
      this.sprite = sprite;
      this.positions = positions;
      this.counts = counts;
      if (positions.length != counts.length) {
         throw new IllegalArgumentException(positions.length + " != " + counts.length);
      }
   }

   public TextureAtlasSprite getSprite() {
      return this.sprite;
   }

   public int[] getPositions() {
      return this.positions;
   }

   public int[] getCounts() {
      return this.counts;
   }

   public String toString() {
      String var10000 = String.valueOf(this.sprite.getName());
      return var10000 + ", " + this.positions.length;
   }
}
