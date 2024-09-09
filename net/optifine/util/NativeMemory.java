package net.optifine.util;

import com.mojang.blaze3d.platform.NativeImage;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.LongSupplier;
import net.optifine.Config;

public class NativeMemory {
   private static long imageAllocated = 0L;
   private static LongSupplier bufferAllocatedSupplier = makeLongSupplier(new String[][]{{"sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"}, {"jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"}}, makeDefaultAllocatedSupplier());
   private static LongSupplier bufferMaximumSupplier = makeLongSupplier(new String[][]{{"sun.misc.VM", "maxDirectMemory"}, {"jdk.internal.misc.VM", "maxDirectMemory"}}, makeDefaultMaximumSupplier());

   public static long getBufferAllocated() {
      return bufferAllocatedSupplier == null ? -1L : bufferAllocatedSupplier.getAsLong();
   }

   public static long getBufferMaximum() {
      return bufferMaximumSupplier == null ? -1L : bufferMaximumSupplier.getAsLong();
   }

   public static synchronized void imageAllocated(NativeImage nativeImage) {
      imageAllocated += nativeImage.getSize();
   }

   public static synchronized void imageFreed(NativeImage nativeImage) {
      imageAllocated -= nativeImage.getSize();
   }

   public static long getImageAllocated() {
      return imageAllocated;
   }

   private static BufferPoolMXBean getDirectBufferPoolMXBean() {
      List mxBeans = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
      Iterator var1 = mxBeans.iterator();

      BufferPoolMXBean mxBean;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         mxBean = (BufferPoolMXBean)var1.next();
      } while(!Config.equals(mxBean.getName(), "direct"));

      return mxBean;
   }

   private static LongSupplier makeDefaultAllocatedSupplier() {
      final BufferPoolMXBean mxBean = getDirectBufferPoolMXBean();
      if (mxBean == null) {
         return null;
      } else {
         LongSupplier ls = new LongSupplier() {
            public long getAsLong() {
               return mxBean.getMemoryUsed();
            }
         };
         return ls;
      }
   }

   private static LongSupplier makeDefaultMaximumSupplier() {
      LongSupplier ls = new LongSupplier() {
         public long getAsLong() {
            return Runtime.getRuntime().maxMemory();
         }
      };
      return ls;
   }

   private static LongSupplier makeLongSupplier(String[][] paths, LongSupplier defaultSupplier) {
      List exceptions = new ArrayList();

      for(int i = 0; i < paths.length; ++i) {
         String[] path = paths[i];

         try {
            LongSupplier supplier = makeLongSupplier(path);
            if (supplier != null) {
               return supplier;
            }
         } catch (Throwable var6) {
            exceptions.add(var6);
         }
      }

      Iterator it = exceptions.iterator();

      while(it.hasNext()) {
         Throwable t = (Throwable)it.next();
         String var10000 = t.getClass().getName();
         Config.warn("(Reflector) " + var10000 + ": " + t.getMessage());
      }

      return defaultSupplier;
   }

   private static LongSupplier makeLongSupplier(String[] path) throws Exception {
      if (path.length < 2) {
         return null;
      } else {
         Class cls = Class.forName(path[0]);
         final Method method = cls.getMethod(path[1]);
         method.setAccessible(true);
         final Object object = null;

         for(int i = 2; i < path.length; ++i) {
            String name = path[i];
            object = method.invoke(object);
            method = object.getClass().getMethod(name);
            method.setAccessible(true);
         }

         LongSupplier ls = new LongSupplier() {
            private boolean disabled = false;

            public long getAsLong() {
               if (this.disabled) {
                  return -1L;
               } else {
                  try {
                     return (Long)method.invoke(object);
                  } catch (Throwable var2) {
                     String var10000 = var2.getClass().getName();
                     Config.warn("(Reflector) " + var10000 + ": " + var2.getMessage());
                     this.disabled = true;
                     return -1L;
                  }
               }
            }
         };
         return ls;
      }
   }
}
