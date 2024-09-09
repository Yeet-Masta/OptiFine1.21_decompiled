package net.optifine.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBuffers;

public class RenderUtils {
   private static boolean flushRenderBuffers = true;
   // $FF: renamed from: mc net.minecraft.client.Minecraft
   private static Minecraft field_28 = Minecraft.m_91087_();

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
         RenderBuffers rtb = field_28.m_91269_();
         rtb.m_110104_().flushRenderBuffers();
         rtb.m_110108_().flushRenderBuffers();
      }
   }

   public static void finishRenderBuffers() {
      RenderBuffers rtb = field_28.m_91269_();
      rtb.m_110104_().m_109911_();
      rtb.m_110108_().m_109911_();
   }
}
