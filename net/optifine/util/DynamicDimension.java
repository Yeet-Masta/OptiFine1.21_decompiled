package net.optifine.util;

import java.awt.Dimension;

public class DynamicDimension {
   private boolean relative = false;
   private float width;
   private float height;

   public DynamicDimension(boolean relative, float width, float height) {
      this.relative = relative;
      this.width = width;
      this.height = height;
   }

   public boolean isRelative() {
      return this.relative;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public Dimension getDimension(int baseWidth, int baseHeight) {
      return this.relative ? new Dimension((int)(this.width * (float)baseWidth), (int)(this.height * (float)baseHeight)) : new Dimension((int)this.width, (int)this.height);
   }

   public int hashCode() {
      int hc = this.relative ? 1 : 0;
      hc = hc * 37 + (int)this.width;
      hc = hc * 37 + (int)this.height;
      return hc;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof DynamicDimension dim)) {
         return false;
      } else if (this.relative != dim.relative) {
         return false;
      } else if (this.width != dim.width) {
         return false;
      } else {
         return this.height == dim.height;
      }
   }
}
