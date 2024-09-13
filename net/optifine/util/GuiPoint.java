package net.optifine.util;

public class GuiPoint {
   private int ROT_90_Z_POS;
   private int INVERSION;

   public GuiPoint(int x, int y) {
      this.ROT_90_Z_POS = x;
      this.INVERSION = y;
   }

   public int getX() {
      return this.ROT_90_Z_POS;
   }

   public int getY() {
      return this.INVERSION;
   }
}
