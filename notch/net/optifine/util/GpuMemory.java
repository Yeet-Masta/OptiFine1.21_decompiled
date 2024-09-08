package net.optifine.util;

import net.minecraft.src.C_3148_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3512_;
import net.minecraft.src.C_4468_;
import net.minecraft.src.C_4470_;
import net.minecraft.src.C_4476_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4490_;
import net.optifine.Config;
import net.optifine.shaders.SimpleShaderTexture;

public class GpuMemory {
   private static long bufferAllocated = 0L;
   private static long textureAllocated = 0L;
   private static long textureAllocatedUpdateTime = 0L;

   public static synchronized void bufferAllocated(long size) {
      bufferAllocated += size;
   }

   public static synchronized void bufferFreed(long size) {
      bufferAllocated -= size;
   }

   public static long getBufferAllocated() {
      return bufferAllocated;
   }

   public static long getTextureAllocated() {
      if (System.currentTimeMillis() > textureAllocatedUpdateTime) {
         textureAllocated = calculateTextureAllocated();
         textureAllocatedUpdateTime = System.currentTimeMillis() + 1000L;
      }

      return textureAllocated;
   }

   private static long calculateTextureAllocated() {
      C_4490_ textureManager = C_3391_.m_91087_().m_91097_();
      long sum = 0L;

      for (C_4468_ texture : textureManager.getTextures()) {
         long size = getTextureSize(texture);
         if (Config.isShaders()) {
            size *= 3L;
         }

         sum += size;
      }

      return sum;
   }

   public static long getTextureSize(C_4468_ texture) {
      if (texture instanceof C_4470_ dt) {
         C_3148_ img = dt.m_117991_();
         if (img != null) {
            return img.getSize();
         }
      }

      if (texture instanceof C_3512_ ft) {
         return 262144L;
      } else if (texture instanceof SimpleShaderTexture sst) {
         return sst.getSize();
      } else if (texture instanceof C_4476_ st) {
         return st.size;
      } else {
         return texture instanceof C_4484_ ta ? (long)(ta.m_276092_() * ta.m_276095_() * 4) : 0L;
      }
   }
}
