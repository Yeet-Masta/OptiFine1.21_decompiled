package net.optifine.util;

public class GuiPoint {
   // $FF: renamed from: x int
   private int field_52;
   // $FF: renamed from: y int
   private int field_53;

   public GuiPoint(int x, int y) {
      this.field_52 = x;
      this.field_53 = y;
   }

   public int getX() {
      return this.field_52;
   }

   public int getY() {
      return this.field_53;
   }
}
