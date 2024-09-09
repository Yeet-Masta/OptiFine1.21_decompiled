package net.optifine.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ClearVertexBuffersTask implements Runnable {
   List<com.mojang.blaze3d.vertex.VertexBuffer> listBuffers;

   public ClearVertexBuffersTask(List<com.mojang.blaze3d.vertex.VertexBuffer> listBuffers) {
      this.listBuffers = listBuffers;
   }

   public void run() {
      for (int i = 0; i < this.listBuffers.size(); i++) {
         com.mojang.blaze3d.vertex.VertexBuffer vb = (com.mojang.blaze3d.vertex.VertexBuffer)this.listBuffers.get(i);
         vb.clearData();
      }
   }

   public String toString() {
      return this.listBuffers + "";
   }

   public static ClearVertexBuffersTask make(
      Set<net.minecraft.client.renderer.RenderType> renderedLayers, net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection renderChunk
   ) {
      List<com.mojang.blaze3d.vertex.VertexBuffer> listBuffers = null;

      for (net.minecraft.client.renderer.RenderType rt : net.minecraft.client.renderer.chunk.SectionRenderDispatcher.BLOCK_RENDER_LAYERS) {
         com.mojang.blaze3d.vertex.VertexBuffer vb = renderChunk.m_294581_(rt);
         if (vb != null && !vb.isEmpty() && (renderedLayers == null || !renderedLayers.contains(rt))) {
            if (listBuffers == null) {
               listBuffers = new ArrayList();
            }

            listBuffers.add(vb);
         }
      }

      return listBuffers == null ? null : new ClearVertexBuffersTask(listBuffers);
   }

   public static CompletableFuture<Void> makeFuture(
      Set<net.minecraft.client.renderer.RenderType> renderedLayers,
      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection renderChunk,
      Executor executor
   ) {
      ClearVertexBuffersTask task = make(renderedLayers, renderChunk);
      return task == null ? null : CompletableFuture.runAsync(() -> task.run(), executor);
   }
}
