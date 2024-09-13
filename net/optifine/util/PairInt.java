package net.optifine.util;

public class PairInt {
   private int left;
   private int right;
   private int hashCode;

   public PairInt(int left, int right) {
      this.left = left;
      this.right = right;
      this.hashCode = left + 37 * right;
   }

   public static PairInt m_253057_(int left, int right) {
      return new PairInt(left, right);
   }

   public int getLeft() {
      return this.left;
   }

   public int getRight() {
      return this.right;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return !(obj instanceof PairInt pi) ? false : this.left == pi.left && this.right == pi.right;
      }
   }

   public String toString() {
      return "(" + this.left + ", " + this.right + ")";
   }
}
