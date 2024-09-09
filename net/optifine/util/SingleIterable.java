package net.optifine.util;

import java.util.Iterator;

public class SingleIterable implements Iterable, Iterator {
   private Object value;

   public SingleIterable() {
   }

   public SingleIterable(Object value) {
      this.value = value;
   }

   public Iterator iterator() {
      return this;
   }

   public boolean hasNext() {
      return this.value != null;
   }

   public Object next() {
      Object ret = this.value;
      this.value = null;
      return ret;
   }

   public void setValue(Object value) {
      this.value = value;
   }
}
