package net.optifine.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class IteratorCache {
   private static Deque dequeIterators = new ArrayDeque();

   public static Iterator getReadOnly(List list) {
      synchronized(dequeIterators) {
         IteratorReusable it = (IteratorReusable)dequeIterators.pollFirst();
         if (it == null) {
            it = new IteratorReadOnly();
         }

         ((IteratorReusable)it).setList(list);
         return (Iterator)it;
      }
   }

   private static void finished(IteratorReusable iterator) {
      synchronized(dequeIterators) {
         if (dequeIterators.size() <= 1000) {
            iterator.setList((List)null);
            dequeIterators.addLast(iterator);
         }
      }
   }

   static {
      for(int i = 0; i < 1000; ++i) {
         IteratorReadOnly it = new IteratorReadOnly();
         dequeIterators.add(it);
      }

   }

   public interface IteratorReusable extends Iterator {
      void setList(List var1);
   }

   public static class IteratorReadOnly implements IteratorReusable {
      private List list;
      private int index;
      private boolean hasNext;

      public void setList(List list) {
         if (this.hasNext) {
            String var10002 = String.valueOf(this.list);
            throw new RuntimeException("Iterator still used, oldList: " + var10002 + ", newList: " + String.valueOf(list));
         } else {
            this.list = list;
            this.index = 0;
            this.hasNext = list != null && this.index < list.size();
         }
      }

      public Object next() {
         if (!this.hasNext) {
            return null;
         } else {
            Object obj = this.list.get(this.index);
            ++this.index;
            this.hasNext = this.index < this.list.size();
            return obj;
         }
      }

      public boolean hasNext() {
         if (!this.hasNext) {
            IteratorCache.finished(this);
            return false;
         } else {
            return this.hasNext;
         }
      }
   }
}
