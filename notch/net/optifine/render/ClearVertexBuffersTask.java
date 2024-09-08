package net.optifine.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.src.C_290152_;
import net.minecraft.src.C_3186_;
import net.minecraft.src.C_4168_;

public class ClearVertexBuffersTask implements Runnable {
   List<C_3186_> listBuffers;

   public ClearVertexBuffersTask(List<C_3186_> listBuffers) {
      this.listBuffers = listBuffers;
   }

   public void run() {
      for (int i = 0; i < this.listBuffers.size(); i++) {
         C_3186_ vb = (C_3186_)this.listBuffers.get(i);
         vb.clearData();
      }
   }

   public String toString() {
      return this.listBuffers + "";
   }

   public static ClearVertexBuffersTask make(Set<C_4168_> renderedLayers, C_290152_.C_290138_ renderChunk) {
      List<C_3186_> listBuffers = null;

      for (C_4168_ rt : C_290152_.BLOCK_RENDER_LAYERS) {
         C_3186_ vb = renderChunk.m_294581_(rt);
         if (vb != null && !vb.isEmpty() && (renderedLayers == null || !renderedLayers.contains(rt))) {
            if (listBuffers == null) {
               listBuffers = new ArrayList();
            }

            listBuffers.add(vb);
         }
      }

      return listBuffers == null ? null : new ClearVertexBuffersTask(listBuffers);
   }

   public static CompletableFuture<Void> makeFuture(Set<C_4168_> renderedLayers, C_290152_.C_290138_ renderChunk, Executor executor) {
      ClearVertexBuffersTask task = make(renderedLayers, renderChunk);
      return task == null ? null : CompletableFuture.runAsync(() -> task.run(), executor);
   }
}
