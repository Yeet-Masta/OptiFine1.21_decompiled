package com.mojang.blaze3d.vertex;

import javax.annotation.Nullable;

public class Tesselator {
   private static int f_302531_;
   private ByteBufferBuilder f_337014_;
   @Nullable
   private static Tesselator f_302797_;

   public static void m_306948_() {
      if (f_302797_ != null) {
         throw new IllegalStateException("Tesselator has already been initialized");
      } else {
         f_302797_ = new Tesselator();
      }
   }

   public static Tesselator m_85913_() {
      if (f_302797_ == null) {
         throw new IllegalStateException("Tesselator has not been initialized");
      } else {
         return f_302797_;
      }
   }

   public Tesselator(int bufferSize) {
      this.f_337014_ = new ByteBufferBuilder(bufferSize);
   }

   public Tesselator() {
      this(786432);
   }

   public BufferBuilder m_339075_(VertexFormat.Mode modeIn, VertexFormat formatIn) {
      return new BufferBuilder(this.f_337014_, modeIn, formatIn);
   }

   public void m_339098_() {
      this.f_337014_.m_340278_();
   }

   public void draw(BufferBuilder bufferIn) {
      BufferUploader.m_231202_(bufferIn.m_339905_());
   }
}
