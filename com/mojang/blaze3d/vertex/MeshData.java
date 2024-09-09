package com.mojang.blaze3d.vertex;

import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.annotation.Nullable;
import net.optifine.render.MultiTextureBuilder;
import net.optifine.render.MultiTextureData;
import org.apache.commons.lang3.mutable.MutableLong;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class MeshData implements AutoCloseable {
   private final com.mojang.blaze3d.vertex.ByteBufferBuilder.Result f_337070_;
   @Nullable
   private com.mojang.blaze3d.vertex.ByteBufferBuilder.Result f_337083_;
   private final com.mojang.blaze3d.vertex.MeshData.DrawState f_337640_;
   private MultiTextureData multiTextureData;

   public MeshData(com.mojang.blaze3d.vertex.ByteBufferBuilder.Result vertexBufferIn, com.mojang.blaze3d.vertex.MeshData.DrawState drawStateIn) {
      this(vertexBufferIn, drawStateIn, null);
   }

   public MeshData(
      com.mojang.blaze3d.vertex.ByteBufferBuilder.Result vertexBufferIn,
      com.mojang.blaze3d.vertex.MeshData.DrawState drawStateIn,
      MultiTextureData multiTextureDataIn
   ) {
      this.f_337070_ = vertexBufferIn;
      this.f_337640_ = drawStateIn;
      this.multiTextureData = multiTextureDataIn;
   }

   private static Vector3f[] m_340126_(ByteBuffer bufferIn, int vertexCountIn, com.mojang.blaze3d.vertex.VertexFormat vertexFormatIn) {
      int i = vertexFormatIn.m_338798_(com.mojang.blaze3d.vertex.VertexFormatElement.f_336661_);
      if (i == -1) {
         throw new IllegalArgumentException("Cannot identify quad centers with no position element");
      } else {
         FloatBuffer floatbuffer = bufferIn.asFloatBuffer();
         int j = vertexFormatIn.m_86020_() / 4;
         int k = j * 4;
         int l = vertexCountIn / 4;
         Vector3f[] avector3f = new Vector3f[l];

         for (int i1 = 0; i1 < l; i1++) {
            int j1 = i1 * k + i;
            int k1 = j1 + j * 2;
            float f = floatbuffer.get(j1 + 0);
            float f1 = floatbuffer.get(j1 + 1);
            float f2 = floatbuffer.get(j1 + 2);
            float f3 = floatbuffer.get(k1 + 0);
            float f4 = floatbuffer.get(k1 + 1);
            float f5 = floatbuffer.get(k1 + 2);
            avector3f[i1] = new Vector3f((f + f3) / 2.0F, (f1 + f4) / 2.0F, (f2 + f5) / 2.0F);
         }

         return avector3f;
      }
   }

   public ByteBuffer m_340620_() {
      return this.f_337070_.m_338393_();
   }

   @Nullable
   public ByteBuffer m_339370_() {
      return this.f_337083_ != null ? this.f_337083_.m_338393_() : null;
   }

   public com.mojang.blaze3d.vertex.MeshData.DrawState m_339246_() {
      return this.f_337640_;
   }

   @Nullable
   public com.mojang.blaze3d.vertex.MeshData.SortState m_338666_(com.mojang.blaze3d.vertex.ByteBufferBuilder builderIn, VertexSorting sortingIn) {
      if (this.f_337640_.f_336934_() != com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS) {
         return null;
      } else {
         Vector3f[] avector3f = m_340126_(this.f_337070_.m_338393_(), this.f_337640_.f_336624_(), this.f_337640_.f_336748_());
         com.mojang.blaze3d.vertex.MeshData.SortState meshdata$sortstate = new com.mojang.blaze3d.vertex.MeshData.SortState(
            avector3f, this.f_337640_.f_337180_(), this.multiTextureData
         );
         this.f_337083_ = meshdata$sortstate.m_340180_(builderIn, sortingIn);
         return meshdata$sortstate;
      }
   }

   public void close() {
      this.f_337070_.close();
      if (this.f_337083_ != null) {
         this.f_337083_.close();
      }
   }

   public MultiTextureData getMultiTextureData() {
      return this.multiTextureData;
   }

   public String toString() {
      return "vertexBuffer: (" + this.f_337070_ + "), indexBuffer: (" + this.f_337083_ + "), drawState: (" + this.f_337640_ + ")";
   }

   public static record DrawState(
      com.mojang.blaze3d.vertex.VertexFormat f_336748_,
      int f_336624_,
      int f_337456_,
      com.mojang.blaze3d.vertex.VertexFormat.Mode f_336934_,
      com.mojang.blaze3d.vertex.VertexFormat.IndexType f_337180_
   ) {
      public int getVertexBufferSize() {
         return this.f_336624_ * this.f_336748_.m_86020_();
      }
   }

   public static record SortState(Vector3f[] f_336944_, com.mojang.blaze3d.vertex.VertexFormat.IndexType f_337501_, MultiTextureData multiTextureData) {
      public SortState(Vector3f[] centroids, com.mojang.blaze3d.vertex.VertexFormat.IndexType indexType) {
         this(centroids, indexType, null);
      }

      @Nullable
      public com.mojang.blaze3d.vertex.ByteBufferBuilder.Result m_340180_(com.mojang.blaze3d.vertex.ByteBufferBuilder builderIn, VertexSorting sortingIn) {
         int[] aint = sortingIn.m_277065_(this.f_336944_);
         long i = builderIn.m_338881_(aint.length * 6 * this.f_337501_.f_166924_);
         IntConsumer intconsumer = this.m_338675_(i, this.f_337501_);

         for (int j : aint) {
            intconsumer.accept(j * 4 + 0);
            intconsumer.accept(j * 4 + 1);
            intconsumer.accept(j * 4 + 2);
            intconsumer.accept(j * 4 + 2);
            intconsumer.accept(j * 4 + 3);
            intconsumer.accept(j * 4 + 0);
         }

         if (this.multiTextureData != null) {
            MultiTextureBuilder mtb = builderIn.getBufferBuilderCache().getMultiTextureBuilder();
            this.multiTextureData.prepareSort(mtb, aint);
         }

         return builderIn.m_339207_();
      }

      private IntConsumer m_338675_(long ptrIn, com.mojang.blaze3d.vertex.VertexFormat.IndexType typeIn) {
         MutableLong mutablelong = new MutableLong(ptrIn);

         return switch (typeIn) {
            case SHORT -> valIn -> MemoryUtil.memPutShort(mutablelong.getAndAdd(2L), (short)valIn);
            case INT -> valIn -> MemoryUtil.memPutInt(mutablelong.getAndAdd(4L), valIn);
         };
      }
   }
}
