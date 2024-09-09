package net.optifine.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import sun.misc.Unsafe;

public class UnsafeUtils {
   private static Unsafe unsafe;
   private static boolean checked;

   private static Unsafe getUnsafe() {
      if (checked) {
         return unsafe;
      } else {
         try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe)unsafeField.get((Object)null);
         } catch (Exception var1) {
            var1.printStackTrace();
         }

         return unsafe;
      }
   }

   public static void setStaticInt(Field field, int value) {
      if (field != null) {
         if (field.getType() == Integer.TYPE) {
            if (Modifier.isStatic(field.getModifiers())) {
               Unsafe us = getUnsafe();
               if (us != null) {
                  Object fieldBase = unsafe.staticFieldBase(field);
                  long fieldOffset = unsafe.staticFieldOffset(field);
                  if (fieldBase != null) {
                     unsafe.putInt(fieldBase, fieldOffset, value);
                  }
               }
            }
         }
      }
   }
}
