package net.optifine.render;

import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.vertex.BufferBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;

public class RenderCache implements IBufferSourceListener {
   private long cacheTimeMs;
   private long updateTimeMs;
   private Map<RenderType, ByteBuffer> renderTypeBuffers = new LinkedHashMap();
   private Deque<ByteBuffer> freeBuffers = new ArrayDeque();

   public RenderCache(long cacheTimeMs) {
      this.cacheTimeMs = cacheTimeMs;
   }

   public boolean drawCached(GuiGraphics graphicsIn) {
      if (System.currentTimeMillis() > this.updateTimeMs) {
         graphicsIn.m_280262_();

         for (ByteBuffer bb : this.renderTypeBuffers.values()) {
            this.freeBuffers.add(bb);
         }

         this.renderTypeBuffers.clear();
         this.updateTimeMs = System.currentTimeMillis() + this.cacheTimeMs;
         return false;
      } else {
         for (RenderType rt : this.renderTypeBuffers.keySet()) {
            ByteBuffer bb = (ByteBuffer)this.renderTypeBuffers.get(rt);
            graphicsIn.putBulkData(rt, bb);
            bb.rewind();
         }

         graphicsIn.m_280262_();
         return true;
      }
   }

   public void startRender(GuiGraphics graphicsIn) {
      graphicsIn.m_280091_().addListener(this);
   }

   public void stopRender(GuiGraphics graphicsIn) {
      graphicsIn.m_280262_();
      graphicsIn.m_280091_().removeListener(this);
   }

   @Override
   public void m_185413_(RenderType renderTypeIn, BufferBuilder bufferIn) {
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
         bb = GlUtil.m_166247_(size);
      }

      bb.position(0);
      bb.limit(0);
      return bb;
   }
}
