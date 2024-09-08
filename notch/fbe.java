package net.minecraft.src;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

public class C_3177_ {
   @Nullable
   private static C_3186_ f_231201_;

   public static void m_166835_() {
      if (f_231201_ != null) {
         m_231208_();
         C_3186_.m_85931_();
      }
   }

   public static void m_231208_() {
      f_231201_ = null;
   }

   public static void m_231202_(C_336471_ bufferIn) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> m_231211_(bufferIn));
      } else {
         m_231211_(bufferIn);
      }
   }

   private static void m_231211_(C_336471_ bufferIn) {
      C_3186_ vertexbuffer = m_231213_(bufferIn);
      vertexbuffer.m_253207_(RenderSystem.getModelViewMatrix(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
   }

   public static void m_231209_(C_336471_ bufferIn) {
      C_3186_ vertexbuffer = m_231213_(bufferIn);
      vertexbuffer.m_166882_();
   }

   private static C_3186_ m_231213_(C_336471_ bufferIn) {
      RenderSystem.assertOnRenderThread();
      C_3186_ vertexbuffer = m_231206_(bufferIn.m_339246_().f_336748_());
      vertexbuffer.m_231221_(bufferIn);
      return vertexbuffer;
   }

   private static C_3186_ m_231206_(C_3188_ formatIn) {
      C_3186_ vertexbuffer = formatIn.m_231233_();
      m_231204_(vertexbuffer);
      return vertexbuffer;
   }

   private static void m_231204_(C_3186_ bufferIn) {
      if (bufferIn != f_231201_) {
         bufferIn.m_85921_();
         f_231201_ = bufferIn;
      }
   }
}
