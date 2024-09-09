package net.optifine.util;

import java.util.Optional;

public class Either {
   private Optional left;
   private Optional right;

   private Either(Optional leftIn, Optional rightIn) {
      this.left = leftIn;
      this.right = rightIn;
      if (!this.left.isPresent() && !this.right.isPresent()) {
         throw new IllegalArgumentException("Both left and right are not present");
      } else if (this.left.isPresent() && this.right.isPresent()) {
         throw new IllegalArgumentException("Both left and right are present");
      }
   }

   public Optional getLeft() {
      return this.left;
   }

   public Optional getRight() {
      return this.right;
   }

   public static Either makeLeft(Object value) {
      return new Either(Optional.of(value), Optional.empty());
   }

   public static Either makeRight(Object value) {
      return new Either(Optional.empty(), Optional.of(value));
   }
}
