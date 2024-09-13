package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.RenderType;
import net.optifine.Config;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.LinkedList;
import org.lwjgl.PointerBuffer;

public class VboRegion {
   private RenderType layer = null;
   private int glArrayObjectId = GlStateManager._glGenVertexArrays();
   private int glBufferId = GlStateManager._glGenBuffers();
   private int capacity = 4096;
   private int positionTop = 0;
   private int sizeUsed;
   private LinkedList<VboRange> rangeList = new LinkedList<>();
   private VboRange compactRangeLast = null;
   private PointerBuffer bufferIndexVertex = Config.createDirectPointerBuffer(this.capacity);
   private IntBuffer bufferCountVertex = Config.createDirectIntBuffer(this.capacity);
   private int vertexBytes = DefaultVertexFormat.f_85811_.m_86020_();
   private VertexFormat.Mode drawMode = VertexFormat.Mode.QUADS;
   private boolean isShaders = Config.isShaders();

   public VboRegion(RenderType layer) {
      this.layer = layer;
      this.bindBuffer();
      long capacityBytes = this.toBytes(this.capacity);
      GlStateManager._glBufferData(GlStateManager.GL_ARRAY_BUFFER, capacityBytes, GlStateManager.GL_STATIC_DRAW);
      this.unbindBuffer();
   }

   public void bufferData(ByteBuffer data, VboRange range) {
      if (this.glBufferId >= 0) {
         int posOld = range.getPosition();
         int sizeOld = range.getSize();
         int sizeNew = this.toVertex((long)data.limit());
         if (sizeNew <= 0) {
            if (posOld >= 0) {
               range.setPosition(-1);
               range.setSize(0);
               this.rangeList.remove(range.getNode());
               this.sizeUsed -= sizeOld;
            }
         } else {
            if (sizeNew > sizeOld) {
               range.setPosition(this.positionTop);
               range.setSize(sizeNew);
               this.positionTop += sizeNew;
               if (posOld >= 0) {
                  this.rangeList.remove(range.getNode());
               }

               this.rangeList.addLast(range.getNode());
            }

            range.setSize(sizeNew);
            this.sizeUsed += sizeNew - sizeOld;
            this.checkVboSize(range.getPositionNext());
            long positionBytes = this.toBytes(range.getPosition());
            this.bindVertexArray();
            this.bindBuffer();
            GlStateManager.bufferSubData(GlStateManager.GL_ARRAY_BUFFER, positionBytes, data);
            this.unbindBuffer();
            unbindVertexArray();
            if (this.positionTop > this.sizeUsed * 11 / 10) {
               this.compactRanges(1);
            }
         }
      }
   }

   private void compactRanges(int countMax) {
      if (!this.rangeList.isEmpty()) {
         VboRange range = this.compactRangeLast;
         if (range == null || !this.rangeList.m_274455_(range.getNode())) {
            range = this.rangeList.getFirst().getItem();
         }

         int posCompact = range.getPosition();
         VboRange rangePrev = range.getPrev();
         if (rangePrev == null) {
            posCompact = 0;
         } else {
            posCompact = rangePrev.getPositionNext();
         }

         int count = 0;

         while (range != null && count < countMax) {
            count++;
            if (range.getPosition() == posCompact) {
               posCompact += range.getSize();
               range = range.getNext();
            } else {
               int sizeFree = range.getPosition() - posCompact;
               if (range.getSize() <= sizeFree) {
                  this.copyVboData(range.getPosition(), posCompact, range.getSize());
                  range.setPosition(posCompact);
                  posCompact += range.getSize();
                  range = range.getNext();
               } else {
                  this.checkVboSize(this.positionTop + range.getSize());
                  this.copyVboData(range.getPosition(), this.positionTop, range.getSize());
                  range.setPosition(this.positionTop);
                  this.positionTop = this.positionTop + range.getSize();
                  VboRange rangeNext = range.getNext();
                  this.rangeList.remove(range.getNode());
                  this.rangeList.addLast(range.getNode());
                  range = rangeNext;
               }
            }
         }

         if (range == null) {
            this.positionTop = this.rangeList.getLast().getItem().getPositionNext();
         }

         this.compactRangeLast = range;
      }
   }

   private void checkRanges() {
      int count = 0;
      int size = 0;

      for (VboRange range = this.rangeList.getFirst().getItem(); range != null; range = range.getNext()) {
         count++;
         size += range.getSize();
         if (range.getPosition() < 0 || range.getSize() <= 0 || range.getPositionNext() > this.positionTop) {
            throw new RuntimeException("Invalid range: " + range);
         }

         VboRange rangePrev = range.getPrev();
         if (rangePrev != null && range.getPosition() < rangePrev.getPositionNext()) {
            throw new RuntimeException("Invalid range: " + range);
         }

         VboRange rangeNext = range.getNext();
         if (rangeNext != null && range.getPositionNext() > rangeNext.getPosition()) {
            throw new RuntimeException("Invalid range: " + range);
         }
      }

      if (count != this.rangeList.getSize()) {
         throw new RuntimeException("Invalid count: " + count + " <> " + this.rangeList.getSize());
      } else if (size != this.sizeUsed) {
         throw new RuntimeException("Invalid size: " + size + " <> " + this.sizeUsed);
      }
   }

   private void checkVboSize(int sizeMin) {
      if (this.capacity < sizeMin) {
         this.expandVbo(sizeMin);
      }
   }

   private void copyVboData(int posFrom, int posTo, int size) {
      long posFromBytes = this.toBytes(posFrom);
      long posToBytes = this.toBytes(posTo);
      long sizeBytes = this.toBytes(size);
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, this.glBufferId);
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, this.glBufferId);
      GlStateManager.copyBufferSubData(GlStateManager.GL_COPY_READ_BUFFER, GlStateManager.GL_COPY_WRITE_BUFFER, posFromBytes, posToBytes, sizeBytes);
      Config.checkGlError("Copy VBO range");
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, 0);
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, 0);
   }

   private void expandVbo(int sizeMin) {
      int capacityNew = this.capacity * 6 / 4;

      while (capacityNew < sizeMin) {
         capacityNew = capacityNew * 6 / 4;
      }

      long capacityBytes = this.toBytes(this.capacity);
      long capacityNewBytes = this.toBytes(capacityNew);
      int glBufferIdNew = GlStateManager._glGenBuffers();
      GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, glBufferIdNew);
      GlStateManager._glBufferData(GlStateManager.GL_ARRAY_BUFFER, capacityNewBytes, GlStateManager.GL_STATIC_DRAW);
      Config.checkGlError("Expand VBO");
      GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, 0);
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, this.glBufferId);
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, glBufferIdNew);
      GlStateManager.copyBufferSubData(GlStateManager.GL_COPY_READ_BUFFER, GlStateManager.GL_COPY_WRITE_BUFFER, 0L, 0L, capacityBytes);
      Config.checkGlError("Copy VBO: " + capacityNewBytes);
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_READ_BUFFER, 0);
      GlStateManager._glBindBuffer(GlStateManager.GL_COPY_WRITE_BUFFER, 0);
      GlStateManager._glDeleteBuffers(this.glBufferId);
      this.bufferIndexVertex = Config.createDirectPointerBuffer(capacityNew);
      this.bufferCountVertex = Config.createDirectIntBuffer(capacityNew);
      this.glBufferId = glBufferIdNew;
      this.capacity = capacityNew;
   }

   public void bindVertexArray() {
      GlStateManager._glBindVertexArray(this.glArrayObjectId);
   }

   public void bindBuffer() {
      GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, this.glBufferId);
   }

   public void drawArrays(VertexFormat.Mode drawMode, VboRange range) {
      if (this.drawMode != drawMode) {
         if (this.bufferIndexVertex.position() > 0) {
            throw new IllegalArgumentException("Mixed region draw modes: " + this.drawMode + " != " + drawMode);
         }

         this.drawMode = drawMode;
      }

      int indexSize = 4;
      int pos = drawMode.m_166958_(range.getPosition()) * indexSize;
      this.bufferIndexVertex.put((long)pos);
      int count = drawMode.m_166958_(range.getSize());
      this.bufferCountVertex.put(count);
   }

   public void finishDraw() {
      this.bindVertexArray();
      this.bindBuffer();
      this.layer.m_110508_().m_166912_();
      if (this.isShaders) {
         ShadersRender.setupArrayPointersVbo();
      }

      int indexCount = this.drawMode.m_166958_(this.positionTop);
      RenderSystem.AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = RenderSystem.getSequentialBuffer(this.drawMode);
      VertexFormat.IndexType indexType = rendersystem$autostorageindexbuffer.m_157483_();
      rendersystem$autostorageindexbuffer.m_221946_(indexCount);
      this.bufferIndexVertex.flip();
      this.bufferCountVertex.flip();
      GlStateManager.glMultiDrawElements(this.drawMode.f_166946_, this.bufferCountVertex, indexType.f_166923_, this.bufferIndexVertex);
      this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
      this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
      if (this.positionTop > this.sizeUsed * 11 / 10) {
         this.compactRanges(1);
      }
   }

   public void unbindBuffer() {
      GlStateManager._glBindBuffer(GlStateManager.GL_ARRAY_BUFFER, 0);
   }

   public static void unbindVertexArray() {
      GlStateManager._glBindVertexArray(0);
   }

   public void deleteGlBuffers() {
      if (this.glArrayObjectId >= 0) {
         GlStateManager._glDeleteVertexArrays(this.glArrayObjectId);
         this.glArrayObjectId = -1;
      }

      if (this.glBufferId >= 0) {
         GlStateManager._glDeleteBuffers(this.glBufferId);
         this.glBufferId = -1;
      }
   }

   private long toBytes(int vertex) {
      return (long)vertex * (long)this.vertexBytes;
   }

   private int toVertex(long bytes) {
      return (int)(bytes / (long)this.vertexBytes);
   }

   public int getPositionTop() {
      return this.positionTop;
   }
}
