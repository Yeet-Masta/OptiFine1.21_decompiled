import java.util.Set;

public class VisibilitySet {
   private static final int a = Direction.values().length;
   private long bits;

   public void a(Set<Direction> facing) {
      for (Direction direction : facing) {
         for (Direction direction1 : facing) {
            this.a(direction, direction1, true);
         }
      }
   }

   public void a(Direction facing, Direction facing2, boolean value) {
      this.setBit(facing.ordinal() + facing2.ordinal() * a, value);
      this.setBit(facing2.ordinal() + facing.ordinal() * a, value);
   }

   public void a(boolean visible) {
      if (visible) {
         this.bits = -1L;
      } else {
         this.bits = 0L;
      }
   }

   public boolean a(Direction facing, Direction facing2) {
      return this.getBit(facing.ordinal() + facing2.ordinal() * a);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');

      for (Direction direction : Direction.values()) {
         stringbuilder.append(' ').append(direction.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');

      for (Direction direction2 : Direction.values()) {
         stringbuilder.append(direction2.toString().toUpperCase().charAt(0));

         for (Direction direction1 : Direction.values()) {
            if (direction2 == direction1) {
               stringbuilder.append("  ");
            } else {
               boolean flag = this.a(direction2, direction1);
               stringbuilder.append(' ').append((char)(flag ? 'Y' : 'n'));
            }
         }

         stringbuilder.append('\n');
      }

      return stringbuilder.toString();
   }

   private boolean getBit(int i) {
      return (this.bits & 1L << i) != 0L;
   }

   private void setBit(int i, boolean on) {
      if (on) {
         this.setBit(i);
      } else {
         this.clearBit(i);
      }
   }

   private void setBit(int i) {
      this.bits |= 1L << i;
   }

   private void clearBit(int i) {
      this.bits &= ~(1L << i);
   }
}
