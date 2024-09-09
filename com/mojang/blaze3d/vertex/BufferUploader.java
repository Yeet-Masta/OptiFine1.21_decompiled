package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

public class BufferUploader {
   @Nullable
   private static com.mojang.blaze3d.vertex.VertexBuffer f_231201_;

   public static void m_166835_() {
      if (f_231201_ != null) {
         m_231208_();
         com.mojang.blaze3d.vertex.VertexBuffer.m_85931_();
      }
   }

   public static void m_231208_() {
      f_231201_ = null;
   }

   public static void m_231202_(com.mojang.blaze3d.vertex.MeshData bufferIn) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> m_231211_(bufferIn));
      } else {
         m_231211_(bufferIn);
      }
   }

   private static void m_231211_(com.mojang.blaze3d.vertex.MeshData bufferIn) {
      com.mojang.blaze3d.vertex.VertexBuffer vertexbuffer = m_231213_(bufferIn);
      vertexbuffer.m_253207_(RenderSystem.getModelViewMatrix(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
   }

   public static void m_231209_(com.mojang.blaze3d.vertex.MeshData bufferIn) {
      com.mojang.blaze3d.vertex.VertexBuffer vertexbuffer = m_231213_(bufferIn);
      vertexbuffer.m_166882_();
   }

   private static com.mojang.blaze3d.vertex.VertexBuffer m_231213_(com.mojang.blaze3d.vertex.MeshData bufferIn) {
      RenderSystem.assertOnRenderThread();
      com.mojang.blaze3d.vertex.VertexBuffer vertexbuffer = m_231206_(bufferIn.m_339246_().f_336748_());
      vertexbuffer.m_231221_(bufferIn);
      return vertexbuffer;
   }

   private static com.mojang.blaze3d.vertex.VertexBuffer m_231206_(com.mojang.blaze3d.vertex.VertexFormat formatIn) {
      com.mojang.blaze3d.vertex.VertexBuffer vertexbuffer = formatIn.m_231233_();
      m_231204_(vertexbuffer);
      return vertexbuffer;
   }

   private static void m_231204_(com.mojang.blaze3d.vertex.VertexBuffer bufferIn) {
      if (bufferIn != f_231201_) {
         bufferIn.m_85921_();
         f_231201_ = bufferIn;
      }
   }
}
