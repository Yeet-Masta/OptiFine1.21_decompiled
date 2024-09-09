package net.optifine.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.FontTexture;
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
      net.minecraft.client.renderer.texture.TextureManager textureManager = Minecraft.m_91087_().m_91097_();
      long sum = 0L;

      for (net.minecraft.client.renderer.texture.AbstractTexture texture : textureManager.getTextures()) {
         long size = getTextureSize(texture);
         if (Config.isShaders()) {
            size *= 3L;
         }

         sum += size;
      }

      return sum;
   }

   public static long getTextureSize(net.minecraft.client.renderer.texture.AbstractTexture texture) {
      if (texture instanceof net.minecraft.client.renderer.texture.DynamicTexture dt) {
         com.mojang.blaze3d.platform.NativeImage img = dt.m_117991_();
         if (img != null) {
            return img.getSize();
         }
      }

      if (texture instanceof FontTexture ft) {
         return 262144L;
      } else if (texture instanceof SimpleShaderTexture sst) {
         return sst.getSize();
      } else if (texture instanceof net.minecraft.client.renderer.texture.SimpleTexture st) {
         return st.size;
      } else {
         return texture instanceof net.minecraft.client.renderer.texture.TextureAtlas ta ? (long)(ta.m_276092_() * ta.m_276095_() * 4) : 0L;
      }
   }
}
