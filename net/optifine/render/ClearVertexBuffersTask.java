package net.optifine.render;

import com.mojang.blaze3d.vertex.VertexBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;

public class ClearVertexBuffersTask implements Runnable {
   List<VertexBuffer> listBuffers;

   public ClearVertexBuffersTask(List<VertexBuffer> listBuffers) {
      this.listBuffers = listBuffers;
   }

   public void run() {
      for (int i = 0; i < this.listBuffers.size(); i++) {
         VertexBuffer vb = (VertexBuffer)this.listBuffers.get(i);
         vb.clearData();
      }
   }

   public String toString() {
      return this.listBuffers + "";
   }

   public static ClearVertexBuffersTask make(Set<RenderType> renderedLayers, SectionRenderDispatcher.RenderSection renderChunk) {
      List<VertexBuffer> listBuffers = null;

      for (RenderType rt : SectionRenderDispatcher.BLOCK_RENDER_LAYERS) {
         VertexBuffer vb = renderChunk.m_294581_(rt);
         if (vb != null && !vb.isEmpty() && (renderedLayers == null || !renderedLayers.m_274455_(rt))) {
            if (listBuffers == null) {
               listBuffers = new ArrayList();
            }

            listBuffers.add(vb);
         }
      }

      return listBuffers == null ? null : new ClearVertexBuffersTask(listBuffers);
   }

   public static CompletableFuture<Void> makeFuture(Set<RenderType> renderedLayers, SectionRenderDispatcher.RenderSection renderChunk, Executor executor) {
      ClearVertexBuffersTask task = make(renderedLayers, renderChunk);
      return task == null ? null : CompletableFuture.runAsync(() -> task.run(), executor);
   }
}
