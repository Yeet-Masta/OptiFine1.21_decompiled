package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import net.minecraft.src.C_2064_;
import net.optifine.Config;

public class CacheObjectArray {
   private static ArrayDeque<int[]> arrays = new ArrayDeque();
   private static int maxCacheSize = 10;

   private static synchronized int[] allocateArray(int size) {
      int[] ints = (int[])arrays.pollLast();
      if (ints == null || ints.length < size) {
         ints = new int[size];
      }

      return ints;
   }

   public static synchronized void freeArray(int[] ints) {
      if (arrays.size() < maxCacheSize) {
         arrays.add(ints);
      }
   }

   public static void main(String[] args) throws Exception {
      int size = 4096;
      int count = 500000;
      testNew(size, count);
      testClone(size, count);
      testNewObj(size, count);
      testCloneObj(size, count);
      testNewObjDyn(C_2064_.class, size, count);
      long timeNew = testNew(size, count);
      long timeClone = testClone(size, count);
      long timeNewObj = testNewObj(size, count);
      long timeCloneObj = testCloneObj(size, count);
      long timeNewObjDyn = testNewObjDyn(C_2064_.class, size, count);
      Config.dbg("New: " + timeNew);
      Config.dbg("Clone: " + timeClone);
      Config.dbg("NewObj: " + timeNewObj);
      Config.dbg("CloneObj: " + timeCloneObj);
      Config.dbg("NewObjDyn: " + timeNewObjDyn);
   }

   private static long testClone(int size, int count) {
      long timeStart = System.currentTimeMillis();
      int[] template = new int[size];

      for (int i = 0; i < count; i++) {
         int[] var6 = (int[])template.clone();
      }

      long timeEnd = System.currentTimeMillis();
      return timeEnd - timeStart;
   }

   private static long testNew(int size, int count) {
      long timeStart = System.currentTimeMillis();

      for (int i = 0; i < count; i++) {
         int[] var5 = (int[])Array.newInstance(int.class, size);
      }

      long timeEnd = System.currentTimeMillis();
      return timeEnd - timeStart;
   }

   private static long testCloneObj(int size, int count) {
      long timeStart = System.currentTimeMillis();
      C_2064_[] template = new C_2064_[size];

      for (int i = 0; i < count; i++) {
         C_2064_[] var6 = (C_2064_[])template.clone();
      }

      long timeEnd = System.currentTimeMillis();
      return timeEnd - timeStart;
   }

   private static long testNewObj(int size, int count) {
      long timeStart = System.currentTimeMillis();

      for (int i = 0; i < count; i++) {
         C_2064_[] var5 = new C_2064_[size];
      }

      long timeEnd = System.currentTimeMillis();
      return timeEnd - timeStart;
   }

   private static long testNewObjDyn(Class cls, int size, int count) {
      long timeStart = System.currentTimeMillis();

      for (int i = 0; i < count; i++) {
         Object[] var6 = (Object[])Array.newInstance(cls, size);
      }

      long timeEnd = System.currentTimeMillis();
      return timeEnd - timeStart;
   }
}
