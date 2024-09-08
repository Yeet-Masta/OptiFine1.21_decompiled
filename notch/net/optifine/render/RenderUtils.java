package net.optifine.render;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4148_;

public class RenderUtils {
   private static boolean flushRenderBuffers = true;
   private static C_3391_ mc = C_3391_.m_91087_();

   public static boolean setFlushRenderBuffers(boolean flushRenderBuffers) {
      boolean prev = RenderUtils.flushRenderBuffers;
      RenderUtils.flushRenderBuffers = flushRenderBuffers;
      return prev;
   }

   public static boolean isFlushRenderBuffers() {
      return flushRenderBuffers;
   }

   public static void flushRenderBuffers() {
      if (flushRenderBuffers) {
         C_4148_ rtb = mc.m_91269_();
         rtb.m_110104_().flushRenderBuffers();
         rtb.m_110108_().flushRenderBuffers();
      }
   }

   public static void finishRenderBuffers() {
      C_4148_ rtb = mc.m_91269_();
      rtb.m_110104_().m_109911_();
      rtb.m_110108_().m_109911_();
   }
}
