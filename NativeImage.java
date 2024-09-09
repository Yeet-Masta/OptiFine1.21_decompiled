import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
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
import net.minecraft.src.C_301616_;
import net.minecraft.src.C_3108_;
import net.minecraft.src.C_313596_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_174_.C_265822_;
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
   private static final Logger a = LogUtils.getLogger();
   private static final Set<StandardOpenOption> b = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
   private final NativeImage.a c;
   private final int d;
   private final int e;
   private final boolean f;
   private long g;
   private final long h;
   private static boolean updateBlurMipmap = true;

   public NativeImage(int widthIn, int heightIn, boolean clear) {
      this(NativeImage.a.a, widthIn, heightIn, clear);
   }

   public NativeImage(NativeImage.a pixelFormatIn, int widthIn, int heightIn, boolean initialize) {
      if (widthIn > 0 && heightIn > 0) {
         this.c = pixelFormatIn;
         this.d = widthIn;
         this.e = heightIn;
         this.h = (long)widthIn * (long)heightIn * (long)pixelFormatIn.a();
         this.f = false;
         if (initialize) {
            this.g = MemoryUtil.nmemCalloc(1L, this.h);
         } else {
            this.g = MemoryUtil.nmemAlloc(this.h);
         }

         if (this.g == 0L) {
            throw new IllegalStateException("Unable to allocate texture of size " + widthIn + "x" + heightIn + " (" + pixelFormatIn.a() + " channels)");
         } else {
            this.j();
            NativeMemory.imageAllocated(this);
         }
      } else {
         throw new IllegalArgumentException("Invalid texture size: " + widthIn + "x" + heightIn);
      }
   }

   private NativeImage(NativeImage.a pixelFormatIn, int widthIn, int heightIn, boolean stbiPointerIn, long pointer) {
      if (widthIn > 0 && heightIn > 0) {
         this.c = pixelFormatIn;
         this.d = widthIn;
         this.e = heightIn;
         this.f = stbiPointerIn;
         this.g = pointer;
         this.h = (long)widthIn * (long)heightIn * (long)pixelFormatIn.a();
      } else {
         throw new IllegalArgumentException("Invalid texture size: " + widthIn + "x" + heightIn);
      }
   }

   public String toString() {
      return "NativeImage[" + this.c + " " + this.d + "x" + this.e + "@" + this.g + (this.f ? "S" : "N") + "]";
   }

   private boolean f(int xIn, int yIn) {
      return xIn < 0 || xIn >= this.d || yIn < 0 || yIn >= this.e;
   }

   public static NativeImage a(InputStream inputStreamIn) throws IOException {
      return a(NativeImage.a.a, inputStreamIn);
   }

   public static NativeImage a(@Nullable NativeImage.a pixelFormatIn, InputStream inputStreamIn) throws IOException {
      ByteBuffer bytebuffer = null;

      NativeImage nativeimage;
      try {
         bytebuffer = TextureUtil.readResource(inputStreamIn);
         bytebuffer.rewind();
         nativeimage = a(pixelFormatIn, bytebuffer);
      } finally {
         MemoryUtil.memFree(bytebuffer);
         IOUtils.closeQuietly(inputStreamIn);
      }

      return nativeimage;
   }

   public static NativeImage a(ByteBuffer byteBufferIn) throws IOException {
      return a(NativeImage.a.a, byteBufferIn);
   }

   public static NativeImage a(byte[] bytesIn) throws IOException {
      MemoryStack memorystack = MemoryStack.stackPush();

      NativeImage nativeimage;
      try {
         ByteBuffer bytebuffer = memorystack.malloc(bytesIn.length);
         bytebuffer.put(bytesIn);
         bytebuffer.rewind();
         nativeimage = a(bytebuffer);
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

   public static NativeImage a(@Nullable NativeImage.a pixelFormatIn, ByteBuffer byteBufferIn) throws IOException {
      if (pixelFormatIn != null && !pixelFormatIn.w()) {
         throw new UnsupportedOperationException("Don't know how to read format " + pixelFormatIn);
      } else if (MemoryUtil.memAddress(byteBufferIn) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         C_301616_.m_304649_(byteBufferIn);
         MemoryStack memorystack = MemoryStack.stackPush();

         NativeImage nativeimage;
         try {
            IntBuffer intbuffer = memorystack.mallocInt(1);
            IntBuffer intbuffer1 = memorystack.mallocInt(1);
            IntBuffer intbuffer2 = memorystack.mallocInt(1);
            ByteBuffer bytebuffer = STBImage.stbi_load_from_memory(byteBufferIn, intbuffer, intbuffer1, intbuffer2, pixelFormatIn == null ? 0 : pixelFormatIn.e);
            if (bytebuffer == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            nativeimage = new NativeImage(
               pixelFormatIn == null ? NativeImage.a.a(intbuffer2.get(0)) : pixelFormatIn,
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

   public static void a(boolean linear, boolean mipmap) {
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

   private void j() {
      if (this.g == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   public void close() {
      if (this.g != 0L) {
         if (this.f) {
            STBImage.nstbi_image_free(this.g);
         } else {
            MemoryUtil.nmemFree(this.g);
         }

         NativeMemory.imageFreed(this);
      }

      this.g = 0L;
   }

   public int a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   public NativeImage.a c() {
      return this.c;
   }

   public int a(int x, int y) {
      if (this.c != NativeImage.a.a) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelRGBA only works on RGBA images; have %s", this.c));
      } else if (this.f(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.d, this.e));
      } else {
         this.j();
         long i = ((long)x + (long)y * (long)this.d) * 4L;
         return MemoryUtil.memGetInt(this.g + i);
      }
   }

   public void a(int x, int y, int value) {
      if (this.c != NativeImage.a.a) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "setPixelRGBA only works on RGBA images; have %s", this.c));
      } else if (this.f(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.d, this.e));
      } else {
         this.j();
         long i = ((long)x + (long)y * (long)this.d) * 4L;
         MemoryUtil.memPutInt(this.g + i, value);
      }
   }

   public NativeImage a(IntUnaryOperator opIn) {
      if (this.c != NativeImage.a.a) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "function application only works on RGBA images; have %s", this.c));
      } else {
         this.j();
         NativeImage nativeimage = new NativeImage(this.d, this.e, false);
         int i = this.d * this.e;
         IntBuffer intbuffer = MemoryUtil.memIntBuffer(this.g, i);
         IntBuffer intbuffer1 = MemoryUtil.memIntBuffer(nativeimage.g, i);

         for (int j = 0; j < i; j++) {
            intbuffer1.put(j, opIn.applyAsInt(intbuffer.get(j)));
         }

         return nativeimage;
      }
   }

   public void b(IntUnaryOperator operatorIn) {
      if (this.c != NativeImage.a.a) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "function application only works on RGBA images; have %s", this.c));
      } else {
         this.j();
         int i = this.d * this.e;
         IntBuffer intbuffer = MemoryUtil.memIntBuffer(this.g, i);

         for (int j = 0; j < i; j++) {
            intbuffer.put(j, operatorIn.applyAsInt(intbuffer.get(j)));
         }
      }
   }

   public int[] d() {
      if (this.c != NativeImage.a.a) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelsRGBA only works on RGBA images; have %s", this.c));
      } else {
         this.j();
         int[] aint = new int[this.d * this.e];
         MemoryUtil.memIntBuffer(this.g, this.d * this.e).get(aint);
         return aint;
      }
   }

   public void a(int x, int y, byte lum) {
      RenderSystem.assertOnRenderThread();
      if (!this.c.h()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "setPixelLuminance only works on image with luminance; have %s", this.c));
      } else if (this.f(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.d, this.e));
      } else {
         this.j();
         long i = ((long)x + (long)y * (long)this.d) * (long)this.c.a() + (long)(this.c.m() / 8);
         MemoryUtil.memPutByte(this.g + i, lum);
      }
   }

   public byte b(int x, int y) {
      RenderSystem.assertOnRenderThread();
      if (!this.c.o()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no red or luminance in %s", this.c));
      } else if (this.f(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.d, this.e));
      } else {
         int i = (x + y * this.d) * this.c.a() + this.c.s() / 8;
         return MemoryUtil.memGetByte(this.g + (long)i);
      }
   }

   public byte c(int x, int y) {
      RenderSystem.assertOnRenderThread();
      if (!this.c.p()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no green or luminance in %s", this.c));
      } else if (this.f(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.d, this.e));
      } else {
         int i = (x + y * this.d) * this.c.a() + this.c.t() / 8;
         return MemoryUtil.memGetByte(this.g + (long)i);
      }
   }

   public byte d(int x, int y) {
      RenderSystem.assertOnRenderThread();
      if (!this.c.q()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no blue or luminance in %s", this.c));
      } else if (this.f(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.d, this.e));
      } else {
         int i = (x + y * this.d) * this.c.a() + this.c.u() / 8;
         return MemoryUtil.memGetByte(this.g + (long)i);
      }
   }

   public byte e(int x, int y) {
      if (!this.c.r()) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "no luminance or alpha in %s", this.c));
      } else if (this.f(x, y)) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", x, y, this.d, this.e));
      } else {
         int i = (x + y * this.d) * this.c.a() + this.c.v() / 8;
         return MemoryUtil.memGetByte(this.g + (long)i);
      }
   }

   public void b(int x, int y, int col) {
      if (this.c != NativeImage.a.a) {
         throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
      } else {
         int i = this.a(x, y);
         float f = (float)C_265822_.m_266503_(col) / 255.0F;
         float f1 = (float)C_265822_.m_266247_(col) / 255.0F;
         float f2 = (float)C_265822_.m_266446_(col) / 255.0F;
         float f3 = (float)C_265822_.m_266313_(col) / 255.0F;
         float f4 = (float)C_265822_.m_266503_(i) / 255.0F;
         float f5 = (float)C_265822_.m_266247_(i) / 255.0F;
         float f6 = (float)C_265822_.m_266446_(i) / 255.0F;
         float f7 = (float)C_265822_.m_266313_(i) / 255.0F;
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
         this.a(x, y, C_265822_.m_266248_(j, k, l, i1));
      }
   }

   @Deprecated
   public int[] e() {
      if (this.c != NativeImage.a.a) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.j();
         int[] aint = new int[this.a() * this.b()];

         for (int i = 0; i < this.b(); i++) {
            for (int j = 0; j < this.a(); j++) {
               int k = this.a(j, i);
               aint[j + i * this.a()] = C_175_.m_13660_(C_265822_.m_266503_(k), C_265822_.m_266313_(k), C_265822_.m_266446_(k), C_265822_.m_266247_(k));
            }
         }

         return aint;
      }
   }

   public void a(int level, int xOffset, int yOffset, boolean autoClose) {
      this.a(level, xOffset, yOffset, 0, 0, this.d, this.e, false, autoClose);
   }

   public void a(int level, int xOffset, int yOffset, int unpackSkipPixels, int unpackSkipRows, int widthIn, int heightIn, boolean mipmap, boolean autoClose) {
      this.a(level, xOffset, yOffset, unpackSkipPixels, unpackSkipRows, widthIn, heightIn, false, false, mipmap, autoClose);
   }

   public void a(
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
            () -> this.b(level, xOffset, yOffset, unpackSkipPixels, unpackSkipRows, widthIn, heightIn, blur, clamp, mipmap, autoClose)
         );
      } else {
         this.b(level, xOffset, yOffset, unpackSkipPixels, unpackSkipRows, widthIn, heightIn, blur, clamp, mipmap, autoClose);
      }
   }

   private void b(
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
         this.j();
         a(blur, mipmap);
         if (widthIn == this.a()) {
            GlStateManager._pixelStore(3314, 0);
         } else {
            GlStateManager._pixelStore(3314, this.a());
         }

         GlStateManager._pixelStore(3316, unpackSkipPixels);
         GlStateManager._pixelStore(3315, unpackSkipRows);
         this.c.c();
         GlStateManager._texSubImage2D(3553, level, xOffset, yOffset, widthIn, heightIn, this.c.d(), 5121, this.g);
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

   public void a(int level, boolean opaque) {
      RenderSystem.assertOnRenderThread();
      this.j();
      this.c.b();
      GlStateManager._getTexImage(3553, level, this.c.d(), 5121, this.g);
      if (opaque && this.c.i()) {
         for (int i = 0; i < this.b(); i++) {
            for (int j = 0; j < this.a(); j++) {
               this.a(j, i, this.a(j, i) | 255 << this.c.n());
            }
         }
      }
   }

   public void a(float val) {
      RenderSystem.assertOnRenderThread();
      if (this.c.a() != 1) {
         throw new IllegalStateException("Depth buffer must be stored in NativeImage with 1 component.");
      } else {
         this.j();
         this.c.b();
         GlStateManager._readPixels(0, 0, this.d, this.e, 6402, 5121, this.g);
      }
   }

   public void f() {
      RenderSystem.assertOnRenderThread();
      this.c.c();
      GlStateManager._glDrawPixels(this.d, this.e, this.c.d(), 5121, this.g);
   }

   public void a(File fileIn) throws IOException {
      this.a(fileIn.toPath());
   }

   public boolean a(FT_Face info, int glyphIndex) {
      if (this.c.a() != 1) {
         throw new IllegalArgumentException("Can only write fonts into 1-component images.");
      } else if (C_313596_.m_321328_(FreeType.FT_Load_Glyph(info, glyphIndex, 4), "Loading glyph")) {
         return false;
      } else {
         FT_GlyphSlot ft_glyphslot = (FT_GlyphSlot)Objects.requireNonNull(info.glyph(), "Glyph not initialized");
         FT_Bitmap ft_bitmap = ft_glyphslot.bitmap();
         if (ft_bitmap.pixel_mode() != 2) {
            throw new IllegalStateException("Rendered glyph was not 8-bit grayscale");
         } else if (ft_bitmap.width() == this.a() && ft_bitmap.rows() == this.b()) {
            int i = ft_bitmap.width() * ft_bitmap.rows();
            ByteBuffer bytebuffer = (ByteBuffer)Objects.requireNonNull(ft_bitmap.buffer(i), "Glyph has no bitmap");
            MemoryUtil.memCopy(MemoryUtil.memAddress(bytebuffer), this.g, (long)i);
            return true;
         } else {
            throw new IllegalArgumentException(
               String.format(
                  Locale.ROOT, "Glyph bitmap of size %sx%s does not match image of size: %sx%s", ft_bitmap.width(), ft_bitmap.rows(), this.a(), this.b()
               )
            );
         }
      }
   }

   public void a(Path pathIn) throws IOException {
      if (!this.c.w()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.c);
      } else {
         this.j();
         WritableByteChannel writablebytechannel = Files.newByteChannel(pathIn, b);

         try {
            if (!this.a(writablebytechannel)) {
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

   public byte[] g() throws IOException {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

      byte[] abyte;
      try {
         WritableByteChannel writablebytechannel = Channels.newChannel(bytearrayoutputstream);

         try {
            if (!this.a(writablebytechannel)) {
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

   private boolean a(WritableByteChannel channelIn) throws IOException {
      NativeImage.c nativeimage$writecallback = new NativeImage.c(channelIn);

      boolean var5;
      try {
         int i = Math.min(this.b(), Integer.MAX_VALUE / this.a() / this.c.a());
         if (i < this.b()) {
            a.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", this.b(), i);
         }

         if (STBImageWrite.nstbi_write_png_to_func(nativeimage$writecallback.address(), 0L, this.a(), i, this.c.a(), this.g, 0) == 0) {
            return false;
         }

         nativeimage$writecallback.a();
         var5 = true;
      } finally {
         nativeimage$writecallback.free();
      }

      return var5;
   }

   public void a(NativeImage from) {
      if (from.c() != this.c) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int i = this.c.a();
         this.j();
         from.j();
         if (this.d == from.d) {
            MemoryUtil.memCopy(from.g, this.g, Math.min(this.h, from.h));
         } else {
            int j = Math.min(this.a(), from.a());
            int k = Math.min(this.b(), from.b());

            for (int l = 0; l < k; l++) {
               int i1 = l * from.a() * i;
               int j1 = l * this.a() * i;
               MemoryUtil.memCopy(from.g + (long)i1, this.g + (long)j1, (long)j * (long)i);
            }
         }
      }
   }

   public void a(int x, int y, int widthIn, int heightIn, int value) {
      for (int i = y; i < y + heightIn; i++) {
         for (int j = x; j < x + widthIn; j++) {
            this.a(j, i, value);
         }
      }
   }

   public void a(int xFrom, int yFrom, int xToDelta, int yToDelta, int widthIn, int heightIn, boolean mirrorX, boolean mirrorY) {
      this.a(this, xFrom, yFrom, xFrom + xToDelta, yFrom + yToDelta, widthIn, heightIn, mirrorX, mirrorY);
   }

   public void a(NativeImage imageIn, int xFrom, int yFrom, int xTo, int yTo, int widthIn, int heightIn, boolean mirrorX, boolean mirrorY) {
      for (int i = 0; i < heightIn; i++) {
         for (int j = 0; j < widthIn; j++) {
            int k = mirrorX ? widthIn - 1 - j : j;
            int l = mirrorY ? heightIn - 1 - i : i;
            int i1 = this.a(xFrom + j, yFrom + i);
            imageIn.a(xTo + k, yTo + l, i1);
         }
      }
   }

   public void h() {
      this.j();
      int i = this.c.a();
      int j = this.a() * i;
      long k = MemoryUtil.nmemAlloc((long)j);

      try {
         for (int l = 0; l < this.b() / 2; l++) {
            int i1 = l * this.a() * i;
            int j1 = (this.b() - 1 - l) * this.a() * i;
            MemoryUtil.memCopy(this.g + (long)i1, k, (long)j);
            MemoryUtil.memCopy(this.g + (long)j1, this.g + (long)i1, (long)j);
            MemoryUtil.memCopy(k, this.g + (long)j1, (long)j);
         }
      } finally {
         MemoryUtil.nmemFree(k);
      }
   }

   public void a(int xIn, int yIn, int widthIn, int heightIn, NativeImage imageIn) {
      this.j();
      if (imageIn.c() != this.c) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int i = this.c.a();
         STBImageResize.nstbir_resize_uint8(
            this.g + (long)((xIn + yIn * this.a()) * i), widthIn, heightIn, this.a() * i, imageIn.g, imageIn.a(), imageIn.b(), 0, i
         );
      }
   }

   public void i() {
      C_3108_.m_84001_(this.g);
   }

   public IntBuffer getBufferRGBA() {
      if (this.c != NativeImage.a.a) {
         throw new IllegalArgumentException(String.format("getBuffer only works on RGBA images; have %s", this.c));
      } else {
         this.j();
         return MemoryUtil.memIntBuffer(this.g, (int)this.h);
      }
   }

   public long getSize() {
      return this.h;
   }

   public void downloadFromFramebuffer() {
      this.j();
      this.c.b();
      GlStateManager.readPixels(0, 0, this.d, this.e, this.c.d(), 5121, this.g);
   }

   public static void setUpdateBlurMipmap(boolean updateBlurMipmap) {
      NativeImage.updateBlurMipmap = updateBlurMipmap;
   }

   public static boolean isUpdateBlurMipmap() {
      return updateBlurMipmap;
   }

   public static enum a {
      a(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      b(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      c(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      d(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      final int e;
      private final int f;
      private final boolean g;
      private final boolean h;
      private final boolean i;
      private final boolean j;
      private final boolean k;
      private final int l;
      private final int m;
      private final int n;
      private final int o;
      private final int p;
      private final boolean q;

      private a(
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
         this.e = channelsIn;
         this.f = glFormatIn;
         this.g = redIn;
         this.h = greenIn;
         this.i = blueIn;
         this.j = luminanceIn;
         this.k = alphaIn;
         this.l = offsetRedIn;
         this.m = offsetGreenIn;
         this.n = offsetBlueIn;
         this.o = offsetLuminanceIn;
         this.p = offsetAlphaIn;
         this.q = standardIn;
      }

      public int a() {
         return this.e;
      }

      public void b() {
         RenderSystem.assertOnRenderThread();
         GlStateManager._pixelStore(3333, this.a());
      }

      public void c() {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._pixelStore(3317, this.a());
      }

      public int d() {
         return this.f;
      }

      public boolean e() {
         return this.g;
      }

      public boolean f() {
         return this.h;
      }

      public boolean g() {
         return this.i;
      }

      public boolean h() {
         return this.j;
      }

      public boolean i() {
         return this.k;
      }

      public int j() {
         return this.l;
      }

      public int k() {
         return this.m;
      }

      public int l() {
         return this.n;
      }

      public int m() {
         return this.o;
      }

      public int n() {
         return this.p;
      }

      public boolean o() {
         return this.j || this.g;
      }

      public boolean p() {
         return this.j || this.h;
      }

      public boolean q() {
         return this.j || this.i;
      }

      public boolean r() {
         return this.j || this.k;
      }

      public int s() {
         return this.j ? this.o : this.l;
      }

      public int t() {
         return this.j ? this.o : this.m;
      }

      public int u() {
         return this.j ? this.o : this.n;
      }

      public int v() {
         return this.j ? this.o : this.p;
      }

      public boolean w() {
         return this.q;
      }

      static NativeImage.a a(int channelsIn) {
         switch (channelsIn) {
            case 1:
               return d;
            case 2:
               return c;
            case 3:
               return b;
            case 4:
            default:
               return a;
         }
      }
   }

   public static enum b {
      a(6408),
      b(6407),
      c(33319),
      d(6403);

      private final int e;

      private b(final int glFormatIn) {
         this.e = glFormatIn;
      }

      public int a() {
         return this.e;
      }
   }

   static class c extends STBIWriteCallback {
      private final WritableByteChannel a;
      @Nullable
      private IOException b;

      c(WritableByteChannel byteChannelIn) {
         this.a = byteChannelIn;
      }

      public void invoke(long p_invoke_1_, long p_invoke_3_, int p_invoke_5_) {
         ByteBuffer bytebuffer = getData(p_invoke_3_, p_invoke_5_);

         try {
            this.a.write(bytebuffer);
         } catch (IOException var8) {
            this.b = var8;
         }
      }

      public void a() throws IOException {
         if (this.b != null) {
            throw this.b;
         }
      }
   }
}
