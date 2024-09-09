package net.optifine.render;

import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.vertex.BufferBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;

public class RenderCache implements IBufferSourceListener {
   private long cacheTimeMs;
   private long updateTimeMs;
   private Map renderTypeBuffers = new LinkedHashMap();
   private Deque freeBuffers = new ArrayDeque();

   public RenderCache(long cacheTimeMs) {
      this.cacheTimeMs = cacheTimeMs;
   }

   public boolean drawCached(GuiGraphics graphicsIn) {
      Iterator var2;
      if (System.currentTimeMillis() > this.updateTimeMs) {
         graphicsIn.m_280262_();
         var2 = this.renderTypeBuffers.values().iterator();

         while(var2.hasNext()) {
            ByteBuffer bb = (ByteBuffer)var2.next();
            this.freeBuffers.add(bb);
         }

         this.renderTypeBuffers.clear();
         this.updateTimeMs = System.currentTimeMillis() + this.cacheTimeMs;
         return false;
      } else {
         var2 = this.renderTypeBuffers.keySet().iterator();

         while(var2.hasNext()) {
            RenderType rt = (RenderType)var2.next();
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

   public void finish(RenderType renderTypeIn, BufferBuilder bufferIn) {
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
