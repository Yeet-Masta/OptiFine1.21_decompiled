package net.optifine.render;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3139_;
import net.minecraft.src.C_3173_;
import net.minecraft.src.C_4168_;

public class RenderCache implements IBufferSourceListener {
   private long cacheTimeMs;
   private long updateTimeMs;
   private Map<C_4168_, ByteBuffer> renderTypeBuffers = new LinkedHashMap();
   private Deque<ByteBuffer> freeBuffers = new ArrayDeque();

   public RenderCache(long cacheTimeMs) {
      this.cacheTimeMs = cacheTimeMs;
   }

   public boolean drawCached(C_279497_ graphicsIn) {
      if (System.currentTimeMillis() > this.updateTimeMs) {
         graphicsIn.m_280262_();

         for (ByteBuffer bb : this.renderTypeBuffers.values()) {
            this.freeBuffers.add(bb);
         }

         this.renderTypeBuffers.clear();
         this.updateTimeMs = System.currentTimeMillis() + this.cacheTimeMs;
         return false;
      } else {
         for (C_4168_ rt : this.renderTypeBuffers.keySet()) {
            ByteBuffer bb = (ByteBuffer)this.renderTypeBuffers.get(rt);
            graphicsIn.putBulkData(rt, bb);
            bb.rewind();
         }

         graphicsIn.m_280262_();
         return true;
      }
   }

   public void startRender(C_279497_ graphicsIn) {
      graphicsIn.m_280091_().addListener(this);
   }

   public void stopRender(C_279497_ graphicsIn) {
      graphicsIn.m_280262_();
      graphicsIn.m_280091_().removeListener(this);
   }

   public void finish(C_4168_ renderTypeIn, C_3173_ bufferIn) {
      ByteBuffer bb = (ByteBuffer)this.renderTypeBuffers.get(renderTypeIn);
      if (bb == null) {
         bb = this.allocateByteBuffer(524288);
         this.renderTypeBuffers.put(renderTypeIn, bb);
      }

      bb.position(bb.limit());
      bb.limit(bb.capacity());
      bufferIn.getBulkData(bb);
      bb.flip();
   }

   private ByteBuffer allocateByteBuffer(int size) {
      ByteBuffer bb = (ByteBuffer)this.freeBuffers.pollLast();
      if (bb == null) {
         bb = C_3139_.m_166247_(size);
      }

      bb.position(0);
      bb.limit(0);
      return bb;
   }
}
