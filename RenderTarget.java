import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.src.C_3391_;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.GLConst;

public abstract class RenderTarget {
   private static final int a = 0;
   private static final int b = 1;
   private static final int l = 2;
   private static final int m = 3;
   public int c;
   public int d;
   public int e;
   public int f;
   public final boolean g;
   public int h;
   protected int i;
   protected int j;
   private final float[] n = Util.a((Supplier<float[]>)(() -> new float[]{1.0F, 1.0F, 1.0F, 0.0F}));
   public int k;
   private boolean stencilEnabled = false;

   public RenderTarget(boolean useDepthIn) {
      this.g = useDepthIn;
      this.h = -1;
      this.i = -1;
      this.j = -1;
   }

   public void a(int widthIn, int heightIn, boolean onMacIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.d(widthIn, heightIn, onMacIn));
      } else {
         this.d(widthIn, heightIn, onMacIn);
      }
   }

   private void d(int widthIn, int heightIn, boolean onMacIn) {
      if (!GLX.isUsingFBOs()) {
         this.e = widthIn;
         this.f = heightIn;
         this.c = widthIn;
         this.d = heightIn;
      } else {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._enableDepthTest();
         if (this.h >= 0) {
            this.a();
         }

         this.b(widthIn, heightIn, onMacIn);
         GlStateManager._glBindFramebuffer(36160, 0);
      }
   }

   public void a() {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         this.d();
         this.e();
         if (this.j > -1) {
            TextureUtil.releaseTextureId(this.j);
            this.j = -1;
         }

         if (this.i > -1) {
            TextureUtil.releaseTextureId(this.i);
            this.i = -1;
         }

         if (this.h > -1) {
            GlStateManager._glBindFramebuffer(36160, 0);
            GlStateManager._glDeleteFramebuffers(this.h);
            this.h = -1;
         }
      }
   }

   public void a(RenderTarget framebufferIn) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._glBindFramebuffer(36008, framebufferIn.h);
         GlStateManager._glBindFramebuffer(36009, this.h);
         GlStateManager._glBlitFrameBuffer(0, 0, framebufferIn.c, framebufferIn.d, 0, 0, this.c, this.d, 256, 9728);
         GlStateManager._glBindFramebuffer(36160, 0);
      }
   }

   public void b(int widthIn, int heightIn, boolean onMacIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      int i = RenderSystem.maxSupportedTextureSize();
      if (widthIn > 0 && widthIn <= i && heightIn > 0 && heightIn <= i) {
         this.e = widthIn;
         this.f = heightIn;
         this.c = widthIn;
         this.d = heightIn;
         if (!GLX.isUsingFBOs()) {
            this.b(onMacIn);
         } else {
            this.h = GlStateManager.glGenFramebuffers();
            this.i = TextureUtil.generateTextureId();
            if (this.g) {
               this.j = TextureUtil.generateTextureId();
               GlStateManager._bindTexture(this.j);
               GlStateManager._texParameter(3553, 10241, 9728);
               GlStateManager._texParameter(3553, 10240, 9728);
               GlStateManager._texParameter(3553, 34892, 0);
               GlStateManager._texParameter(3553, 10242, 33071);
               GlStateManager._texParameter(3553, 10243, 33071);
               if (this.stencilEnabled) {
                  GlStateManager._texImage2D(3553, 0, 36013, this.c, this.d, 0, 34041, 36269, null);
               } else {
                  GlStateManager._texImage2D(3553, 0, 6402, this.c, this.d, 0, 6402, 5126, null);
               }
            }

            this.a(9728, true);
            GlStateManager._bindTexture(this.i);
            GlStateManager._texParameter(3553, 10242, 33071);
            GlStateManager._texParameter(3553, 10243, 33071);
            GlStateManager._texImage2D(3553, 0, 32856, this.c, this.d, 0, 6408, 5121, null);
            GlStateManager._glBindFramebuffer(36160, this.h);
            GlStateManager._glFramebufferTexture2D(36160, 36064, 3553, this.i, 0);
            if (this.g) {
               if (this.stencilEnabled) {
                  if (ReflectorForge.getForgeUseCombinedDepthStencilAttachment()) {
                     GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, 33306, 3553, this.j, 0);
                  } else {
                     GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, 36096, 3553, this.j, 0);
                     GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, 36128, 3553, this.j, 0);
                  }
               } else {
                  GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.j, 0);
               }
            }

            this.b();
            this.b(onMacIn);
            this.d();
         }
      } else {
         throw new IllegalArgumentException("Window " + widthIn + "x" + heightIn + " size out of bounds (max. size: " + i + ")");
      }
   }

   public void a(int framebufferFilterIn) {
      this.a(framebufferFilterIn, false);
   }

   private void a(int framebufferFilterIn, boolean forcedIn) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         if (forcedIn || framebufferFilterIn != this.k) {
            this.k = framebufferFilterIn;
            GlStateManager._bindTexture(this.i);
            GlStateManager._texParameter(3553, 10241, framebufferFilterIn);
            GlStateManager._texParameter(3553, 10240, framebufferFilterIn);
            GlStateManager._bindTexture(0);
         }
      }
   }

   public void b() {
      RenderSystem.assertOnRenderThreadOrInit();
      int i = GlStateManager.glCheckFramebufferStatus(36160);
      if (i != 36053) {
         if (i == 36054) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
         } else if (i == 36055) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
         } else if (i == 36059) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
         } else if (i == 36060) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
         } else if (i == 36061) {
            throw new RuntimeException("GL_FRAMEBUFFER_UNSUPPORTED");
         } else if (i == 1285) {
            throw new RuntimeException("GL_OUT_OF_MEMORY");
         } else {
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + i);
         }
      }
   }

   public void c() {
      RenderSystem.assertOnRenderThread();
      GlStateManager._bindTexture(this.i);
   }

   public void d() {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._bindTexture(0);
      }
   }

   public void a(boolean setViewportIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.c(setViewportIn));
      } else {
         this.c(setViewportIn);
      }
   }

   private void c(boolean setViewportIn) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._glBindFramebuffer(36160, this.h);
         if (setViewportIn) {
            GlStateManager._viewport(0, 0, this.e, this.f);
         }
      }
   }

   public void e() {
      if (GLX.isUsingFBOs()) {
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> GlStateManager._glBindFramebuffer(36160, 0));
         } else {
            GlStateManager._glBindFramebuffer(36160, 0);
         }
      }
   }

   public void a(float red, float green, float blue, float alpha) {
      this.n[0] = red;
      this.n[1] = green;
      this.n[2] = blue;
      this.n[3] = alpha;
   }

   public void a(int width, int height) {
      this.c(width, height, true);
   }

   public void c(int width, int height, boolean noBlend) {
      this.e(width, height, noBlend);
   }

   private void e(int width, int height, boolean noBlend) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThread();
         GlStateManager._colorMask(true, true, true, false);
         GlStateManager._disableDepthTest();
         GlStateManager._depthMask(false);
         GlStateManager._viewport(0, 0, width, height);
         if (noBlend) {
            GlStateManager._disableBlend();
         }

         C_3391_ minecraft = C_3391_.m_91087_();
         ShaderInstance shaderinstance = (ShaderInstance)Objects.requireNonNull(minecraft.j.e, "Blit shader not loaded");
         shaderinstance.a("DiffuseSampler", this.i);
         shaderinstance.g();
         BufferBuilder bufferbuilder = RenderSystem.renderThreadTesselator().a(VertexFormat.c.h, DefaultVertexFormat.a);
         bufferbuilder.a(0.0F, 0.0F, 0.0F);
         bufferbuilder.a(1.0F, 0.0F, 0.0F);
         bufferbuilder.a(1.0F, 1.0F, 0.0F);
         bufferbuilder.a(0.0F, 1.0F, 0.0F);
         BufferUploader.b(bufferbuilder.b());
         shaderinstance.f();
         GlStateManager._depthMask(true);
         GlStateManager._colorMask(true, true, true, true);
      }
   }

   public void b(boolean onMac) {
      RenderSystem.assertOnRenderThreadOrInit();
      this.a(true);
      GlStateManager._clearColor(this.n[0], this.n[1], this.n[2], this.n[3]);
      int i = 16384;
      if (this.g) {
         GlStateManager._clearDepth(1.0);
         i |= 256;
      }

      GlStateManager._clear(i, onMac);
      this.e();
   }

   public int f() {
      return this.i;
   }

   public int g() {
      return this.j;
   }

   public void enableStencil() {
      if (!this.stencilEnabled) {
         this.stencilEnabled = true;
         this.a(this.e, this.f, C_3391_.f_91002_);
      }
   }

   public boolean isStencilEnabled() {
      return this.stencilEnabled;
   }
}
