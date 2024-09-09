package net.optifine.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.optifine.Config;

public class StaticMap {
   private static final Map MAP = Collections.synchronizedMap(new HashMap());

   public static boolean contains(String key) {
      return MAP.containsKey(key);
   }

   public static boolean contains(String key, Object value) {
      if (!MAP.containsKey(key)) {
         return false;
      } else {
         Object val = MAP.get(key);
         return Config.equals(val, value);
      }
   }

   public static Object get(String key) {
      return MAP.get(key);
   }

   public static void put(String key, Object val) {
      MAP.put(key, val);
   }

   public static void remove(String key) {
      MAP.remove(key);
   }

   public static int getInt(String key, int def) {
      Object val = MAP.get(key);
      if (!(val instanceof Integer valInt)) {
         return def;
      } else {
         return valInt;
      }
   }

   public static int putInt(String key, int val) {
      int valPrev = getInt(key, 0);
      Integer valObj = val;
      MAP.put(key, valObj);
      return valPrev;
   }

   public static long getLong(String key, long def) {
      Object val = MAP.get(key);
      if (!(val instanceof Long valLong)) {
         return def;
      } else {
         return valLong;
      }
   }

   public static void putLong(String key, long val) {
      Long valObj = val;
      MAP.put(key, valObj);
   }

   public static long putLong(String key, long val, long def) {
      long valPrev = getLong(key, def);
      Long valObj = val;
      MAP.put(key, valObj);
      return valPrev;
   }

   public static long addLong(String key, long val, long def) {
      long valMap = getLong(key, def);
      valMap += val;
      putLong(key, valMap);
      return valMap;
   }
}
