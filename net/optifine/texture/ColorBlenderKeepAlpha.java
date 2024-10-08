package net.optifine.texture;

public class ColorBlenderKeepAlpha implements IColorBlender {
   public int blend(int col1, int col2, int col3, int col4) {
      int cx1 = this.alphaBlend(col1, col2);
      int cx2 = this.alphaBlend(col3, col4);
      int cx = this.alphaBlend(cx1, cx2);
      return cx;
   }

   private int alphaBlend(int c1, int c2) {
      int a1 = (c1 & -16777216) >> 24 & 255;
      int a2 = (c2 & -16777216) >> 24 & 255;
      int ax = (a1 + a2) / 2;
      if (a1 == 0 && a2 == 0) {
         a1 = 1;
         a2 = 1;
      } else {
         if (a1 == 0) {
            c1 = c2;
            ax = a2;
         }

         if (a2 == 0) {
            c2 = c1;
            ax = a1;
         }
      }

      int r1 = (c1 >> 16 & 255) * a1;
      int g1 = (c1 >> 8 & 255) * a1;
      int b1 = (c1 & 255) * a1;
      int r2 = (c2 >> 16 & 255) * a2;
      int g2 = (c2 >> 8 & 255) * a2;
      int b2 = (c2 & 255) * a2;
      int rx = (r1 + r2) / (a1 + a2);
      int gx = (g1 + g2) / (a1 + a2);
      int bx = (b1 + b2) / (a1 + a2);
      return ax << 24 | rx << 16 | gx << 8 | bx;
   }
}
