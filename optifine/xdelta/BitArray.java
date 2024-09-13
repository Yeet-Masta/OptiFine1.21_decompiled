package optifine.xdelta;

public class BitArray {
   int[] implArray;
   int size;
   static int INT_SIZE;

   public BitArray(int size) {
      int implSize = size / 32 + 1;
      this.implArray = new int[implSize];
   }

   public void set(int pos, boolean value) {
      int implPos = pos / 32;
      int bitMask = 1 << (pos & 31);
      if (value) {
         this.implArray[implPos] = this.implArray[implPos] | bitMask;
      } else {
         this.implArray[implPos] = this.implArray[implPos] & ~bitMask;
      }
   }

   public boolean get(int pos) {
      int implPos = pos / 32;
      int bitMask = 1 << (pos & 31);
      return (this.implArray[implPos] & bitMask) != 0;
   }
}
