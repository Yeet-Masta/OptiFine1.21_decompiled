package net.optifine.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemoryMonitor {
   private static long startTimeMs = System.currentTimeMillis();
   private static long startMemory = getMemoryUsed();
   private static long lastTimeMs;
   private static long lastMemory;
   private static boolean gcEvent;
   private static long memBytesSec;
   private static long memBytesSecAvg;
   private static List listMemBytesSec;
   private static long gcBytesSec;
   // $FF: renamed from: MB long
   private static long field_24;

   public static void update() {
      long timeMs = System.currentTimeMillis();
      long memory = getMemoryUsed();
      gcEvent = memory < lastMemory;
      if (gcEvent) {
         gcBytesSec = memBytesSec;
         startTimeMs = timeMs;
         startMemory = memory;
      }

      long timeDiffMs = timeMs - startTimeMs;
      long memoryDiff = memory - startMemory;
      double timeDiffSec = (double)timeDiffMs / 1000.0;
      if (memoryDiff >= 0L && timeDiffSec > 0.0) {
         memBytesSec = (long)((double)memoryDiff / timeDiffSec);
         listMemBytesSec.add(memBytesSec);
         if (timeMs / 1000L != lastTimeMs / 1000L) {
            long sumBytes = 0L;

            Long bytes;
            for(Iterator var12 = listMemBytesSec.iterator(); var12.hasNext(); sumBytes += bytes) {
               bytes = (Long)var12.next();
            }

            memBytesSecAvg = sumBytes / (long)listMemBytesSec.size();
            listMemBytesSec.clear();
         }
      }

      lastTimeMs = timeMs;
      lastMemory = memory;
   }

   private static long getMemoryUsed() {
      Runtime r = Runtime.getRuntime();
      return r.totalMemory() - r.freeMemory();
   }

   public static long getStartTimeMs() {
      return startTimeMs;
   }

   public static long getStartMemoryMb() {
      return startMemory / field_24;
   }

   public static boolean isGcEvent() {
      return gcEvent;
   }

   public static long getAllocationRateMb() {
      return memBytesSec / field_24;
   }

   public static long getAllocationRateAvgMb() {
      return memBytesSecAvg / field_24;
   }

   public static long getGcRateMb() {
      return gcBytesSec / field_24;
   }

   static {
      lastTimeMs = startTimeMs;
      lastMemory = startMemory;
      gcEvent = false;
      memBytesSec = 0L;
      memBytesSecAvg = 0L;
      listMemBytesSec = new ArrayList();
      gcBytesSec = 0L;
      field_24 = 1048576L;
   }
}
