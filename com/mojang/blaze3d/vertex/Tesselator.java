package com.mojang.blaze3d.vertex;

import javax.annotation.Nullable;

public class Tesselator {
   private static final int f_302531_ = 786432;
   private final com.mojang.blaze3d.vertex.ByteBufferBuilder f_337014_;
   @Nullable
   private static com.mojang.blaze3d.vertex.Tesselator f_302797_;

   public static void m_306948_() {
      if (f_302797_ != null) {
         throw new IllegalStateException("Tesselator has already been initialized");
      } else {
         f_302797_ = new com.mojang.blaze3d.vertex.Tesselator();
      }
   }

   public static com.mojang.blaze3d.vertex.Tesselator m_85913_() {
      if (f_302797_ == null) {
         throw new IllegalStateException("Tesselator has not been initialized");
      } else {
         return f_302797_;
      }
   }

   public Tesselator(int bufferSize) {
      this.f_337014_ = new com.mojang.blaze3d.vertex.ByteBufferBuilder(bufferSize);
   }

   public Tesselator() {
      this(786432);
   }

   public com.mojang.blaze3d.vertex.BufferBuilder m_339075_(com.mojang.blaze3d.vertex.VertexFormat.Mode modeIn, com.mojang.blaze3d.vertex.VertexFormat formatIn) {
      return new com.mojang.blaze3d.vertex.BufferBuilder(this.f_337014_, modeIn, formatIn);
   }

   public void m_339098_() {
      this.f_337014_.m_340278_();
   }

   public void draw(com.mojang.blaze3d.vertex.BufferBuilder bufferIn) {
      com.mojang.blaze3d.vertex.BufferUploader.m_231202_(bufferIn.m_339905_());
   }
}
