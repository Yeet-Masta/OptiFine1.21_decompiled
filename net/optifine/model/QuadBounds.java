package net.optifine.model;

import net.minecraft.core.Direction;

public class QuadBounds {
   private float minX = Float.MAX_VALUE;
   private float minY = Float.MAX_VALUE;
   private float minZ = Float.MAX_VALUE;
   private float maxX = -Float.MAX_VALUE;
   private float maxY = -Float.MAX_VALUE;
   private float maxZ = -Float.MAX_VALUE;

   public QuadBounds(int[] vertexData) {
      int step = vertexData.length / 4;

      for (int i = 0; i < 4; i++) {
         int pos = i * step;
         float x = Float.intBitsToFloat(vertexData[pos + 0]);
         float y = Float.intBitsToFloat(vertexData[pos + 1]);
         float z = Float.intBitsToFloat(vertexData[pos + 2]);
         if (this.minX > x) {
            this.minX = x;
         }

         if (this.minY > y) {
            this.minY = y;
         }

         if (this.minZ > z) {
            this.minZ = z;
         }

         if (this.maxX < x) {
            this.maxX = x;
         }

         if (this.maxY < y) {
            this.maxY = y;
         }

         if (this.maxZ < z) {
            this.maxZ = z;
         }
      }
   }

   public float getMinX() {
      return this.minX;
   }

   public float getMinY() {
      return this.minY;
   }

   public float getMinZ() {
      return this.minZ;
   }

   public float getMaxX() {
      return this.maxX;
   }

   public float getMaxY() {
      return this.maxY;
   }

   public float getMaxZ() {
      return this.maxZ;
   }

   public boolean isFaceQuad(Direction face) {
      float min;
      float max;
      float val;
      switch (<unrepresentable>.$SwitchMap$net$minecraft$core$Direction[face.ordinal()]) {
         case 1:
            min = this.getMinY();
            max = this.getMaxY();
            val = 0.0F;
            break;
         case 2:
            min = this.getMinY();
            max = this.getMaxY();
            val = 1.0F;
            break;
         case 3:
            min = this.getMinZ();
            max = this.getMaxZ();
            val = 0.0F;
            break;
         case 4:
            min = this.getMinZ();
            max = this.getMaxZ();
            val = 1.0F;
            break;
         case 5:
            min = this.getMinX();
            max = this.getMaxX();
            val = 0.0F;
            break;
         case 6:
            min = this.getMinX();
            max = this.getMaxX();
            val = 1.0F;
            break;
         default:
            return false;
      }

      return min == val && max == val;
   }

   public boolean isFullQuad(Direction face) {
      float min1;
      float max1;
      float min2;
      float max2;
      switch (<unrepresentable>.$SwitchMap$net$minecraft$core$Direction[face.ordinal()]) {
         case 1:
         case 2:
            min1 = this.getMinX();
            max1 = this.getMaxX();
            min2 = this.getMinZ();
            max2 = this.getMaxZ();
            break;
         case 3:
         case 4:
            min1 = this.getMinX();
            max1 = this.getMaxX();
            min2 = this.getMinY();
            max2 = this.getMaxY();
            break;
         case 5:
         case 6:
            min1 = this.getMinY();
            max1 = this.getMaxY();
            min2 = this.getMinZ();
            max2 = this.getMaxZ();
            break;
         default:
            return false;
      }

      return min1 == 0.0F && max1 == 1.0F && min2 == 0.0F && max2 == 1.0F;
   }
}
