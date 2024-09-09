package net.optifine.util;

public class FastArrayList {
   private Object[] array;
   private int size;

   public FastArrayList(int capacity) {
      this.array = new Object[capacity];
   }

   public void add(Object element) {
      this.array[this.size] = element;
      ++this.size;
   }

   public Object get(int index) {
      return this.array[index];
   }

   public int size() {
      return this.size;
   }

   public void clear() {
      this.size = 0;
   }
}
