package net.optifine.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;

public abstract class IntExpiringCache {
   private final int intervalMs;
   private long timeCheckMs;
   private Int2ObjectOpenHashMap map = new Int2ObjectOpenHashMap();

   public IntExpiringCache(int intervalMs) {
      this.intervalMs = intervalMs;
   }

   public Object get(int key) {
      long timeNowMs = System.currentTimeMillis();
      if (!this.map.isEmpty() && timeNowMs >= this.timeCheckMs) {
         this.timeCheckMs = timeNowMs + (long)this.intervalMs;
         long timeMinMs = timeNowMs - (long)this.intervalMs;
         IntSet keys = this.map.keySet();
         IntIterator it = keys.iterator();

         while(it.hasNext()) {
            int k = it.nextInt();
            if (k != key) {
               Wrapper w = (Wrapper)this.map.get(k);
               if (w.getAccessTimeMs() <= timeMinMs) {
                  it.remove();
               }
            }
         }
      }

      Wrapper w = (Wrapper)this.map.get(key);
      if (w == null) {
         Object obj = this.make();
         w = new Wrapper(obj);
         this.map.put(key, w);
      }

      w.setAccessTimeMs(timeNowMs);
      return w.getValue();
   }

   protected abstract Object make();

   public static class Wrapper {
      private final Object value;
      private long accessTimeMs;

      public Wrapper(Object value) {
         this.value = value;
      }

      public Object getValue() {
         return this.value;
      }

      public long getAccessTimeMs() {
         return this.accessTimeMs;
      }

      public void setAccessTimeMs(long accessTimeMs) {
         this.accessTimeMs = accessTimeMs;
      }
   }
}
