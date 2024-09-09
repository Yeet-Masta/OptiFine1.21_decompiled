package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.providers.FreeTypeUtil;
import net.minecraft.util.PngInfo;
import net.minecraft.util.FastColor.ABGR32;
import net.minecraft.util.FastColor.ARGB32;
import net.optifine.Config;
import net.optifine.util.NativeMemory;
import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.STBIWriteCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FT_Bitmap;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FT_GlyphSlot;
import org.lwjgl.util.freetype.FreeType;
import org.slf4j.Logger;

public final class NativeImage implements AutoCloseable {
   private static final Logger f_84958_ = LogUtils.getLogger();
   private static final Set<StandardOpenOption> f_84959_ = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
   private final com.mojang.blaze3d.platform.NativeImage.Format f_84960_;
   private final int f_84961_;
   private final int f_84962_;
   private final boolean f_84963_;
   private long f_84964_;
   private final long f_84965_;
   private static boolean updateBlurMipmap = true;

   public NativeImage(int widthIn, int heightIn, boolean clear) {
      this(com.mojang.blaze3d.platform.NativeImage.Format.RGBA, widthIn, heightIn, clear);
   }

   public NativeImage(com.mojang.blaze3d.platform.NativeImage.Format pixelFormatIn, int widthIn, int heightIn, boolean initialize) {
      if (widthIn > 0 && heightIn > 0) {
         this.f_84960_ = pixelFormatIn;
         this.f_84961_ = widthIn;
         this.f_84962_ = heightIn;
         this.f_84965_ = (long)widthIn * (long)heightIn * (long)pixelFormatIn.m_85161_();
         this.f_84963_ = false;
         if (initialize) {
            this.f_84964_ = MemoryUtil.nmemCalloc(1L, this.f_84965_);
         } else {
            this.f_84964_ = MemoryUtil.nmemAlloc(this.f_84965_);
         }

         if (this.f_84964_ == 0L) {
            throw new IllegalStateException("Unable to allocate texture of size " + widthIn + "x" + heightIn + " (" + pixelFormatIn.m_85161_() + " channels)");
         } else {
            this.m_85124_();
            NativeMemory.imageAllocated(this);
         }
      } else {
         throw new IllegalArgumentException("Invalid texture size: " + widthIn + "x" + heightIn);
      }
   }

   private NativeImage(com.mojang.blaze3d.platform.NativeImage.Format pixelFormatIn, int widthIn, int heightIn, boolean stbiPointerIn, long pointer) {
      if (widthIn > 0 && heightIn > 0) {
         this.f_84960_ = pixelFormatIn;
         this.f_84961_ = widthIn;
         this.f_84962_ = heightIn;
         this.f_84963_ = stbiPointerIn;
         this.f_84964_ = pointer;
         this.f_84965_ = (long)widthIn * (long)heightIn * (long)pixelFormatIn.m_85161_();
      } else {
         throw new IllegalArgumentException("Invalid texture size: " + widthIn + "x" + heightIn);
      }
   }

   public String toString() {
      return "NativeImage[" + this.f_84960_ + " " + this.f_84961_ + "x" + this.f_84962_ + "@" + this.f_84964_ + (this.f_84963_ ? "S" : "N") + "]";
   }

   private boolean m_166422_(int xIn, int yIn) {
      return xIn < 0 || xIn >= this.f_84961_ || yIn < 0 || yIn >= this.f_84962_;
   }

   public static com.mojang.blaze3d.platform.NativeImage m_85058_(InputStream inputStreamIn) throws IOException {
      return m_85048_(com.mojang.blaze3d.platform.NativeImage.Format.RGBA, inputStreamIn);
   }

   public static com.mojang.blaze3d.platform.NativeImage m_85048_(
      @Nullable com.mojang.blaze3d.platform.NativeImage.Format pixelFormatIn, InputStream inputStreamIn
   ) throws IOException {
      ByteBuffer bytebuffer = null;

      com.mojang.blaze3d.platform.NativeImage nativeimage;
      try {
         bytebuffer = TextureUtil.readResource(inputStreamIn);
         bytebuffer.rewind();
         nativeimage = m_85051_(pixelFormatIn, bytebuffer);
      } finally {
         MemoryUtil.memFree(bytebuffer);
         IOUtils.closeQuietly(inputStreamIn);
      }

      return nativeimage;
   }

   public static com.mojang.blaze3d.platform.NativeImage m_85062_(ByteBuffer byteBufferIn) throws IOException {
      return m_85051_(com.mojang.blaze3d.platform.NativeImage.Format.RGBA, byteBufferIn);
   }

   public static com.mojang.blaze3d.platform.NativeImage m_271751_(byte[] bytesIn) throws IOException {
      MemoryStack memorystack = MemoryStack.stackPush();

      com.mojang.blaze3d.platform.NativeImage nativeimage;
      try {
         ByteBuffer bytebuffer = memorystack.malloc(bytesIn.length);
         bytebuffer.put(bytesIn);
         bytebuffer.rewind();
         nativeimage = m_85062_(bytebuffer);
      } catch (Throwable var6) {
         if (memorystack != null) {
            try {
               memorystack.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }
         }

         throw var6;
      }

      if (memorystack != null) {
         memorystack.close();
      }

      return nativeimage;
   }

   public static com.mojang.blaze3d.platform.NativeImage m_85051_(
      @Nullable com.mojang.blaze3d.platform.NativeImage.Format pixelFormatIn, ByteBuffer byteBufferIn
   ) throws IOException {
      if (pixelFormatIn != null && !pixelFormatIn.m_85175_()) {
         throw new UnsupportedOperationException("Don't know how to read format " + pixelFormatIn);
      } else if (MemoryUtil.memAddress(byteBufferIn) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         PngInfo.m_304649_(byteBufferIn);
         MemoryStack memorystack = MemoryStack.stackPush();

         com.mojang.blaze3d.platform.NativeImage nativeimage;
         try {
            IntBuffer intbuffer = memorystack.mallocInt(1);
            IntBuffer intbuffer1 = memorystack.mallocInt(1);
            IntBuffer intbuffer2 = memorystack.mallocInt(1);
            ByteBuffer bytebuffer = STBImage.stbi_load_from_memory(
               byteBufferIn, intbuffer, intbuffer1, intbuffer2, pixelFormatIn == null ? 0 : pixelFormatIn.f_85130_
            );
            if (bytebuffer == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            nativeimage = new com.mojang.blaze3d.platform.NativeImage(
               pixelFormatIn == null ? com.mojang.blaze3d.platform.NativeImage.Format.m_85167_(intbuffer2.get(0)) : pixelFormatIn,
               intbuffer.get(0),
               intbuffer1.get(0),
               true,
               MemoryUtil.memAddress(bytebuffer)
            );
            NativeMemory.imageAllocated(nativeimage);
         } catch (Throwable var9) {
            if (memorystack != null) {
               try {
                  memorystack.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }
            }

            throw var9;
         }

         if (memorystack != null) {
            memorystack.close();
         }

         return nativeimage;
      }
   }

   public static void m_85081_(boolean linear, boolean mipmap) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (updateBlurMipmap) {
         if (linear) {
            GlStateManager._texParameter(3553, 10241, mipmap ? 9987 : 9729);
            GlStateManager._texParameter(3553, 10240, 9729);
         } else {
            int mipmapType = Config.getMipmapType();
            GlStateManager._texParameter(3553, 10241, mipmap ? mipmapType : 9728);
            GlStateManager._texParameter(3553, 10240, 9728);
         }
      }
   }

   private void m_85124_() {
      if (this.f_84964_ == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   public void close() {
      if (this.f_84964_ != 0L) {
         if (this.f_84963_) {
            STBImage.nstbi_image_free(this.f_84964_);
         } else {
            MemoryUtil.nmemFree(this.f_84964_);
         }

         NativeMemory.imageFreed(this);
      }

      this.f_84964_ = 0L;
   }

   public int m_84982_() {
      return this.f_84961_;
   }

   public int m_85084_() {
      return this.f_84962_;
   }

   public com.mojang.blaze3d.platform.NativeImage.Format m_85102_() {
      return this.f_84960_;
   }

   public int m_84985_(int x, int y) {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelRGBA only works on RGBA images; have %s", this.f_84960_));
      } else if (this.m_166422_(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.f_84961_, this.f_84962_));
      } else {
         this.m_85124_();
         long i = ((long)x + (long)y * (long)this.f_84961_) * 4L;
         return MemoryUtil.memGetInt(this.f_84964_ + i);
      }
   }

   public void m_84988_(int x, int y, int value) {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "setPixelRGBA only works on RGBA images; have %s", this.f_84960_));
      } else if (this.m_166422_(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.f_84961_, this.f_84962_));
      } else {
         this.m_85124_();
         long i = ((long)x + (long)y * (long)this.f_84961_) * 4L;
         MemoryUtil.memPutInt(this.f_84964_ + i, value);
      }
   }

   public com.mojang.blaze3d.platform.NativeImage m_266528_(IntUnaryOperator opIn) {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "function application only works on RGBA images; have %s", this.f_84960_));
      } else {
         this.m_85124_();
         com.mojang.blaze3d.platform.NativeImage nativeimage = new com.mojang.blaze3d.platform.NativeImage(this.f_84961_, this.f_84962_, false);
         int i = this.f_84961_ * this.f_84962_;
         IntBuffer intbuffer = MemoryUtil.memIntBuffer(this.f_84964_, i);
         IntBuffer intbuffer1 = MemoryUtil.memIntBuffer(nativeimage.f_84964_, i);

         for (int j = 0; j < i; j++) {
            intbuffer1.put(j, opIn.applyAsInt(intbuffer.get(j)));
         }

         return nativeimage;
      }
   }

   public void m_284481_(IntUnaryOperator operatorIn) {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "function application only works on RGBA images; have %s", this.f_84960_));
      } else {
         this.m_85124_();
         int i = this.f_84961_ * this.f_84962_;
         IntBuffer intbuffer = MemoryUtil.memIntBuffer(this.f_84964_, i);

         for (int j = 0; j < i; j++) {
            intbuffer.put(j, operatorIn.applyAsInt(intbuffer.get(j)));
         }
      }
   }

   public int[] m_266370_() {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelsRGBA only works on RGBA images; have %s", this.f_84960_));
      } else {
         this.m_85124_();
         int[] aint = new int[this.f_84961_ * this.f_84962_];
         MemoryUtil.memIntBuffer(this.f_84964_, this.f_84961_ * this.f_84962_).get(aint);
         return aint;
      }
   }

   public void m_166402_(int x, int y, byte lum) {
      RenderSystem.assertOnRenderThread();
      if (!this.f_84960_.m_166428_()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "setPixelLuminance only works on image with luminance; have %s", this.f_84960_));
      } else if (this.m_166422_(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.f_84961_, this.f_84962_));
      } else {
         this.m_85124_();
         long i = ((long)x + (long)y * (long)this.f_84961_) * (long)this.f_84960_.m_85161_() + (long)(this.f_84960_.m_166432_() / 8);
         MemoryUtil.memPutByte(this.f_84964_ + i, lum);
      }
   }

   public byte m_166408_(int x, int y) {
      RenderSystem.assertOnRenderThread();
      if (!this.f_84960_.m_166433_()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no red or luminance in %s", this.f_84960_));
      } else if (this.m_166422_(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.f_84961_, this.f_84962_));
      } else {
         int i = (x + y * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166436_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public byte m_166415_(int x, int y) {
      RenderSystem.assertOnRenderThread();
      if (!this.f_84960_.m_166434_()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no green or luminance in %s", this.f_84960_));
      } else if (this.m_166422_(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.f_84961_, this.f_84962_));
      } else {
         int i = (x + y * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166437_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public byte m_166418_(int x, int y) {
      RenderSystem.assertOnRenderThread();
      if (!this.f_84960_.m_166435_()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no blue or luminance in %s", this.f_84960_));
      } else if (this.m_166422_(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.f_84961_, this.f_84962_));
      } else {
         int i = (x + y * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166438_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public byte m_85087_(int x, int y) {
      if (!this.f_84960_.m_85173_()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no luminance or alpha in %s", this.f_84960_));
      } else if (this.m_166422_(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.f_84961_, this.f_84962_));
      } else {
         int i = (x + y * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_85174_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public void m_166411_(int x, int y, int col) {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
      } else {
         int i = this.m_84985_(x, y);
         float f = (float)ABGR32.m_266503_(col) / 255.0F;
         float f1 = (float)ABGR32.m_266247_(col) / 255.0F;
         float f2 = (float)ABGR32.m_266446_(col) / 255.0F;
         float f3 = (float)ABGR32.m_266313_(col) / 255.0F;
         float f4 = (float)ABGR32.m_266503_(i) / 255.0F;
         float f5 = (float)ABGR32.m_266247_(i) / 255.0F;
         float f6 = (float)ABGR32.m_266446_(i) / 255.0F;
         float f7 = (float)ABGR32.m_266313_(i) / 255.0F;
         float f8 = 1.0F - f;
         float f9 = f * f + f4 * f8;
         float f10 = f1 * f + f5 * f8;
         float f11 = f2 * f + f6 * f8;
         float f12 = f3 * f + f7 * f8;
         if (f9 > 1.0F) {
            f9 = 1.0F;
         }

         if (f10 > 1.0F) {
            f10 = 1.0F;
         }

         if (f11 > 1.0F) {
            f11 = 1.0F;
         }

         if (f12 > 1.0F) {
            f12 = 1.0F;
         }

         int j = (int)(f9 * 255.0F);
         int k = (int)(f10 * 255.0F);
         int l = (int)(f11 * 255.0F);
         int i1 = (int)(f12 * 255.0F);
         this.m_84988_(x, y, ABGR32.m_266248_(j, k, l, i1));
      }
   }

   @Deprecated
   public int[] m_85118_() {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.m_85124_();
         int[] aint = new int[this.m_84982_() * this.m_85084_()];

         for (int i = 0; i < this.m_85084_(); i++) {
            for (int j = 0; j < this.m_84982_(); j++) {
               int k = this.m_84985_(j, i);
               aint[j + i * this.m_84982_()] = ARGB32.m_13660_(ABGR32.m_266503_(k), ABGR32.m_266313_(k), ABGR32.m_266446_(k), ABGR32.m_266247_(k));
            }
         }

         return aint;
      }
   }

   public void m_85040_(int level, int xOffset, int yOffset, boolean autoClose) {
      this.m_85003_(level, xOffset, yOffset, 0, 0, this.f_84961_, this.f_84962_, false, autoClose);
   }

   public void m_85003_(
      int level, int xOffset, int yOffset, int unpackSkipPixels, int unpackSkipRows, int widthIn, int heightIn, boolean mipmap, boolean autoClose
   ) {
      this.m_85013_(level, xOffset, yOffset, unpackSkipPixels, unpackSkipRows, widthIn, heightIn, false, false, mipmap, autoClose);
   }

   public void m_85013_(
      int level,
      int xOffset,
      int yOffset,
      int unpackSkipPixels,
      int unpackSkipRows,
      int widthIn,
      int heightIn,
      boolean blur,
      boolean clamp,
      boolean mipmap,
      boolean autoClose
   ) {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(
            () -> this.m_85090_(level, xOffset, yOffset, unpackSkipPixels, unpackSkipRows, widthIn, heightIn, blur, clamp, mipmap, autoClose)
         );
      } else {
         this.m_85090_(level, xOffset, yOffset, unpackSkipPixels, unpackSkipRows, widthIn, heightIn, blur, clamp, mipmap, autoClose);
      }
   }

   private void m_85090_(
      int level,
      int xOffset,
      int yOffset,
      int unpackSkipPixels,
      int unpackSkipRows,
      int widthIn,
      int heightIn,
      boolean blur,
      boolean clamp,
      boolean mipmap,
      boolean autoClose
   ) {
      try {
         RenderSystem.assertOnRenderThreadOrInit();
         this.m_85124_();
         m_85081_(blur, mipmap);
         if (widthIn == this.m_84982_()) {
            GlStateManager._pixelStore(3314, 0);
         } else {
            GlStateManager._pixelStore(3314, this.m_84982_());
         }

         GlStateManager._pixelStore(3316, unpackSkipPixels);
         GlStateManager._pixelStore(3315, unpackSkipRows);
         this.f_84960_.m_85169_();
         GlStateManager._texSubImage2D(3553, level, xOffset, yOffset, widthIn, heightIn, this.f_84960_.m_85170_(), 5121, this.f_84964_);
         if (clamp) {
            GlStateManager._texParameter(3553, 10242, 33071);
            GlStateManager._texParameter(3553, 10243, 33071);
         }
      } finally {
         if (autoClose) {
            this.close();
         }
      }
   }

   public void m_85045_(int level, boolean opaque) {
      RenderSystem.assertOnRenderThread();
      this.m_85124_();
      this.f_84960_.m_85166_();
      GlStateManager._getTexImage(3553, level, this.f_84960_.m_85170_(), 5121, this.f_84964_);
      if (opaque && this.f_84960_.m_85171_()) {
         for (int i = 0; i < this.m_85084_(); i++) {
            for (int j = 0; j < this.m_84982_(); j++) {
               this.m_84988_(j, i, this.m_84985_(j, i) | 255 << this.f_84960_.m_85172_());
            }
         }
      }
   }

   public void m_166400_(float val) {
      RenderSystem.assertOnRenderThread();
      if (this.f_84960_.m_85161_() != 1) {
         throw new IllegalStateException("Depth buffer must be stored in NativeImage with 1 component.");
      } else {
         this.m_85124_();
         this.f_84960_.m_85166_();
         GlStateManager._readPixels(0, 0, this.f_84961_, this.f_84962_, 6402, 5121, this.f_84964_);
      }
   }

   public void m_166421_() {
      RenderSystem.assertOnRenderThread();
      this.f_84960_.m_85169_();
      GlStateManager._glDrawPixels(this.f_84961_, this.f_84962_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
   }

   public void m_85056_(File fileIn) throws IOException {
      this.m_85066_(fileIn.toPath());
   }

   public boolean m_85068_(FT_Face info, int glyphIndex) {
      if (this.f_84960_.m_85161_() != 1) {
         throw new IllegalArgumentException("Can only write fonts into 1-component images.");
      } else if (FreeTypeUtil.m_321328_(FreeType.FT_Load_Glyph(info, glyphIndex, 4), "Loading glyph")) {
         return false;
      } else {
         FT_GlyphSlot ft_glyphslot = (FT_GlyphSlot)Objects.requireNonNull(info.glyph(), "Glyph not initialized");
         FT_Bitmap ft_bitmap = ft_glyphslot.bitmap();
         if (ft_bitmap.pixel_mode() != 2) {
            throw new IllegalStateException("Rendered glyph was not 8-bit grayscale");
         } else if (ft_bitmap.width() == this.m_84982_() && ft_bitmap.rows() == this.m_85084_()) {
            int i = ft_bitmap.width() * ft_bitmap.rows();
            ByteBuffer bytebuffer = (ByteBuffer)Objects.requireNonNull(ft_bitmap.buffer(i), "Glyph has no bitmap");
            MemoryUtil.memCopy(MemoryUtil.memAddress(bytebuffer), this.f_84964_, (long)i);
            return true;
         } else {
            throw new IllegalArgumentException(
               String.format(
                  Locale.ROOT,
                  "Glyph bitmap of size %sx%s does not match image of size: %sx%s",
                  ft_bitmap.width(),
                  ft_bitmap.rows(),
                  this.m_84982_(),
                  this.m_85084_()
               )
            );
         }
      }
   }

   public void m_85066_(Path pathIn) throws IOException {
      if (!this.f_84960_.m_85175_()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.f_84960_);
      } else {
         this.m_85124_();
         WritableByteChannel writablebytechannel = Files.newByteChannel(pathIn, f_84959_);

         try {
            if (!this.m_85064_(writablebytechannel)) {
               throw new IOException("Could not write image to the PNG file \"" + pathIn.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
            }
         } catch (Throwable var6) {
            if (writablebytechannel != null) {
               try {
                  writablebytechannel.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (writablebytechannel != null) {
            writablebytechannel.close();
         }
      }
   }

   public byte[] m_85121_() throws IOException {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

      byte[] abyte;
      try {
         WritableByteChannel writablebytechannel = Channels.newChannel(bytearrayoutputstream);

         try {
            if (!this.m_85064_(writablebytechannel)) {
               throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
            }

            abyte = bytearrayoutputstream.toByteArray();
         } catch (Throwable var8) {
            if (writablebytechannel != null) {
               try {
                  writablebytechannel.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (writablebytechannel != null) {
            writablebytechannel.close();
         }
      } catch (Throwable var9) {
         try {
            bytearrayoutputstream.close();
         } catch (Throwable var6) {
            var9.addSuppressed(var6);
         }

         throw var9;
      }

      bytearrayoutputstream.close();
      return abyte;
   }

   private boolean m_85064_(WritableByteChannel channelIn) throws IOException {
      com.mojang.blaze3d.platform.NativeImage.WriteCallback nativeimage$writecallback = new com.mojang.blaze3d.platform.NativeImage.WriteCallback(channelIn);

      boolean var5;
      try {
         int i = Math.min(this.m_85084_(), Integer.MAX_VALUE / this.m_84982_() / this.f_84960_.m_85161_());
         if (i < this.m_85084_()) {
            f_84958_.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", this.m_85084_(), i);
         }

         if (STBImageWrite.nstbi_write_png_to_func(nativeimage$writecallback.address(), 0L, this.m_84982_(), i, this.f_84960_.m_85161_(), this.f_84964_, 0)
            == 0) {
            return false;
         }

         nativeimage$writecallback.m_85202_();
         var5 = true;
      } finally {
         nativeimage$writecallback.free();
      }

      return var5;
   }

   public void m_85054_(com.mojang.blaze3d.platform.NativeImage from) {
      if (from.m_85102_() != this.f_84960_) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int i = this.f_84960_.m_85161_();
         this.m_85124_();
         from.m_85124_();
         if (this.f_84961_ == from.f_84961_) {
            MemoryUtil.memCopy(from.f_84964_, this.f_84964_, Math.min(this.f_84965_, from.f_84965_));
         } else {
            int j = Math.min(this.m_84982_(), from.m_84982_());
            int k = Math.min(this.m_85084_(), from.m_85084_());

            for (int l = 0; l < k; l++) {
               int i1 = l * from.m_84982_() * i;
               int j1 = l * this.m_84982_() * i;
               MemoryUtil.memCopy(from.f_84964_ + (long)i1, this.f_84964_ + (long)j1, (long)j * (long)i);
            }
         }
      }
   }

   public void m_84997_(int x, int y, int widthIn, int heightIn, int value) {
      for (int i = y; i < y + heightIn; i++) {
         for (int j = x; j < x + widthIn; j++) {
            this.m_84988_(j, i, value);
         }
      }
   }

   public void m_85025_(int xFrom, int yFrom, int xToDelta, int yToDelta, int widthIn, int heightIn, boolean mirrorX, boolean mirrorY) {
      this.m_260930_(this, xFrom, yFrom, xFrom + xToDelta, yFrom + yToDelta, widthIn, heightIn, mirrorX, mirrorY);
   }

   public void m_260930_(
      com.mojang.blaze3d.platform.NativeImage imageIn, int xFrom, int yFrom, int xTo, int yTo, int widthIn, int heightIn, boolean mirrorX, boolean mirrorY
   ) {
      for (int i = 0; i < heightIn; i++) {
         for (int j = 0; j < widthIn; j++) {
            int k = mirrorX ? widthIn - 1 - j : j;
            int l = mirrorY ? heightIn - 1 - i : i;
            int i1 = this.m_84985_(xFrom + j, yFrom + i);
            imageIn.m_84988_(xTo + k, yTo + l, i1);
         }
      }
   }

   public void m_85122_() {
      this.m_85124_();
      int i = this.f_84960_.m_85161_();
      int j = this.m_84982_() * i;
      long k = MemoryUtil.nmemAlloc((long)j);

      try {
         for (int l = 0; l < this.m_85084_() / 2; l++) {
            int i1 = l * this.m_84982_() * i;
            int j1 = (this.m_85084_() - 1 - l) * this.m_84982_() * i;
            MemoryUtil.memCopy(this.f_84964_ + (long)i1, k, (long)j);
            MemoryUtil.memCopy(this.f_84964_ + (long)j1, this.f_84964_ + (long)i1, (long)j);
            MemoryUtil.memCopy(k, this.f_84964_ + (long)j1, (long)j);
         }
      } finally {
         MemoryUtil.nmemFree(k);
      }
   }

   public void m_85034_(int xIn, int yIn, int widthIn, int heightIn, com.mojang.blaze3d.platform.NativeImage imageIn) {
      this.m_85124_();
      if (imageIn.m_85102_() != this.f_84960_) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int i = this.f_84960_.m_85161_();
         STBImageResize.nstbir_resize_uint8(
            this.f_84964_ + (long)((xIn + yIn * this.m_84982_()) * i),
            widthIn,
            heightIn,
            this.m_84982_() * i,
            imageIn.f_84964_,
            imageIn.m_84982_(),
            imageIn.m_85084_(),
            0,
            i
         );
      }
   }

   public void m_85123_() {
      DebugMemoryUntracker.m_84001_(this.f_84964_);
   }

   public IntBuffer getBufferRGBA() {
      if (this.f_84960_ != com.mojang.blaze3d.platform.NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format("getBuffer only works on RGBA images; have %s", this.f_84960_));
      } else {
         this.m_85124_();
         return MemoryUtil.memIntBuffer(this.f_84964_, (int)this.f_84965_);
      }
   }

   public long getSize() {
      return this.f_84965_;
   }

   public void downloadFromFramebuffer() {
      this.m_85124_();
      this.f_84960_.m_85166_();
      GlStateManager.readPixels(0, 0, this.f_84961_, this.f_84962_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
   }

   public static void setUpdateBlurMipmap(boolean updateBlurMipmap) {
      com.mojang.blaze3d.platform.NativeImage.updateBlurMipmap = updateBlurMipmap;
   }

   public static boolean isUpdateBlurMipmap() {
      return updateBlurMipmap;
   }

   public static enum Format {
      RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      LUMINANCE_ALPHA(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      LUMINANCE(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      final int f_85130_;
      private final int f_85131_;
      private final boolean f_85132_;
      private final boolean f_85133_;
      private final boolean f_85134_;
      private final boolean f_85135_;
      private final boolean f_85136_;
      private final int f_85137_;
      private final int f_85138_;
      private final int f_85139_;
      private final int f_85140_;
      private final int f_85141_;
      private final boolean f_85142_;

      private Format(
         final int channelsIn,
         final int glFormatIn,
         final boolean redIn,
         final boolean greenIn,
         final boolean blueIn,
         final boolean luminanceIn,
         final boolean alphaIn,
         final int offsetRedIn,
         final int offsetGreenIn,
         final int offsetBlueIn,
         final int offsetLuminanceIn,
         final int offsetAlphaIn,
         final boolean standardIn
      ) {
         this.f_85130_ = channelsIn;
         this.f_85131_ = glFormatIn;
         this.f_85132_ = redIn;
         this.f_85133_ = greenIn;
         this.f_85134_ = blueIn;
         this.f_85135_ = luminanceIn;
         this.f_85136_ = alphaIn;
         this.f_85137_ = offsetRedIn;
         this.f_85138_ = offsetGreenIn;
         this.f_85139_ = offsetBlueIn;
         this.f_85140_ = offsetLuminanceIn;
         this.f_85141_ = offsetAlphaIn;
         this.f_85142_ = standardIn;
      }

      public int m_85161_() {
         return this.f_85130_;
      }

      public void m_85166_() {
         RenderSystem.assertOnRenderThread();
         GlStateManager._pixelStore(3333, this.m_85161_());
      }

      public void m_85169_() {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._pixelStore(3317, this.m_85161_());
      }

      public int m_85170_() {
         return this.f_85131_;
      }

      public boolean m_166425_() {
         return this.f_85132_;
      }

      public boolean m_166426_() {
         return this.f_85133_;
      }

      public boolean m_166427_() {
         return this.f_85134_;
      }

      public boolean m_166428_() {
         return this.f_85135_;
      }

      public boolean m_85171_() {
         return this.f_85136_;
      }

      public int m_166429_() {
         return this.f_85137_;
      }

      public int m_166430_() {
         return this.f_85138_;
      }

      public int m_166431_() {
         return this.f_85139_;
      }

      public int m_166432_() {
         return this.f_85140_;
      }

      public int m_85172_() {
         return this.f_85141_;
      }

      public boolean m_166433_() {
         return this.f_85135_ || this.f_85132_;
      }

      public boolean m_166434_() {
         return this.f_85135_ || this.f_85133_;
      }

      public boolean m_166435_() {
         return this.f_85135_ || this.f_85134_;
      }

      public boolean m_85173_() {
         return this.f_85135_ || this.f_85136_;
      }

      public int m_166436_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85137_;
      }

      public int m_166437_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85138_;
      }

      public int m_166438_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85139_;
      }

      public int m_85174_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85141_;
      }

      public boolean m_85175_() {
         return this.f_85142_;
      }

      static com.mojang.blaze3d.platform.NativeImage.Format m_85167_(int channelsIn) {
         switch (channelsIn) {
            case 1:
               return LUMINANCE;
            case 2:
               return LUMINANCE_ALPHA;
            case 3:
               return RGB;
            case 4:
            default:
               return RGBA;
         }
      }
   }

   public static enum InternalGlFormat {
      RGBA(6408),
      RGB(6407),
      RG(33319),
      RED(6403);

      private final int f_85184_;

      private InternalGlFormat(final int glFormatIn) {
         this.f_85184_ = glFormatIn;
      }

      public int m_85191_() {
         return this.f_85184_;
      }
   }

   static class WriteCallback extends STBIWriteCallback {
      private final WritableByteChannel f_85195_;
      @Nullable
      private IOException f_85196_;

      WriteCallback(WritableByteChannel byteChannelIn) {
         this.f_85195_ = byteChannelIn;
      }

      public void invoke(long p_invoke_1_, long p_invoke_3_, int p_invoke_5_) {
         ByteBuffer bytebuffer = getData(p_invoke_3_, p_invoke_5_);

         try {
            this.f_85195_.write(bytebuffer);
         } catch (IOException var8) {
            this.f_85196_ = var8;
         }
      }

      public void m_85202_() throws IOException {
         if (this.f_85196_ != null) {
            throw this.f_85196_;
         }
      }
   }
}
