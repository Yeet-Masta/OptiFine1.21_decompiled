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
   List listBuffers;

   public ClearVertexBuffersTask(List listBuffers) {
      this.listBuffers = listBuffers;
   }

   public void run() {
      for(int i = 0; i < this.listBuffers.size(); ++i) {
         VertexBuffer vb = (VertexBuffer)this.listBuffers.get(i);
         vb.clearData();
      }

   }

   public String toString() {
      return "" + String.valueOf(this.listBuffers);
   }

   public static ClearVertexBuffersTask make(Set renderedLayers, SectionRenderDispatcher.RenderSection renderChunk) {
      List listBuffers = null;
      RenderType[] var3 = SectionRenderDispatcher.BLOCK_RENDER_LAYERS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         RenderType rt = var3[var5];
         VertexBuffer vb = renderChunk.m_294581_(rt);
         if (vb != null && !vb.isEmpty() && (renderedLayers == null || !renderedLayers.contains(rt))) {
            if (listBuffers == null) {
               listBuffers = new ArrayList();
            }

            listBuffers.add(vb);
         }
      }

      if (listBuffers == null) {
         return null;
      } else {
         return new ClearVertexBuffersTask(listBuffers);
      }
   }

   public static CompletableFuture makeFuture(Set renderedLayers, SectionRenderDispatcher.RenderSection renderChunk, Executor executor) {
      ClearVertexBuffersTask task = make(renderedLayers, renderChunk);
      return task == null ? null : CompletableFuture.runAsync(() -> {
         task.run();
      }, executor);
   }
}
