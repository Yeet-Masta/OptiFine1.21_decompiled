package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Objects;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.GLConst;

public abstract class RenderTarget {
   private static int f_166194_;
   private static int f_166195_;
   private static int f_166196_;
   private static int f_166197_;
   public int f_83915_;
   public int f_83916_;
   public int f_83917_;
   public int f_83918_;
   public boolean f_83919_;
   public int f_83920_;
   protected int f_83923_;
   protected int f_83924_;
   private float[] f_83921_ = Util.m_137537_(() -> new float[]{1.0F, 1.0F, 1.0F, 0.0F});
   public int f_83922_;
   private boolean stencilEnabled = false;

   public RenderTarget(boolean useDepthIn) {
      this.f_83919_ = useDepthIn;
      this.f_83920_ = -1;
      this.f_83923_ = -1;
      this.f_83924_ = -1;
   }

   public void m_83941_(int widthIn, int heightIn, boolean onMacIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.m_83964_(widthIn, heightIn, onMacIn));
      } else {
         this.m_83964_(widthIn, heightIn, onMacIn);
      }
   }

   private void m_83964_(int widthIn, int heightIn, boolean onMacIn) {
      if (!GLX.isUsingFBOs()) {
         this.f_83917_ = widthIn;
         this.f_83918_ = heightIn;
         this.f_83915_ = widthIn;
         this.f_83916_ = heightIn;
      } else {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._enableDepthTest();
         if (this.f_83920_ >= 0) {
            this.m_83930_();
         }

         this.m_83950_(widthIn, heightIn, onMacIn);
         GlStateManager._glBindFramebuffer(36160, 0);
      }
   }

   public void m_83930_() {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         this.m_83963_();
         this.m_83970_();
         if (this.f_83924_ > -1) {
            TextureUtil.releaseTextureId(this.f_83924_);
            this.f_83924_ = -1;
         }

         if (this.f_83923_ > -1) {
            TextureUtil.releaseTextureId(this.f_83923_);
            this.f_83923_ = -1;
         }

         if (this.f_83920_ > -1) {
            GlStateManager._glBindFramebuffer(36160, 0);
            GlStateManager._glDeleteFramebuffers(this.f_83920_);
            this.f_83920_ = -1;
         }
      }
   }

   public void m_83945_(RenderTarget framebufferIn) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._glBindFramebuffer(36008, framebufferIn.f_83920_);
         GlStateManager._glBindFramebuffer(36009, this.f_83920_);
         GlStateManager._glBlitFrameBuffer(0, 0, framebufferIn.f_83915_, framebufferIn.f_83916_, 0, 0, this.f_83915_, this.f_83916_, 256, 9728);
         GlStateManager._glBindFramebuffer(36160, 0);
      }
   }

   public void m_83950_(int widthIn, int heightIn, boolean onMacIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      int i = RenderSystem.maxSupportedTextureSize();
      if (widthIn > 0 && widthIn <= i && heightIn > 0 && heightIn <= i) {
         this.f_83917_ = widthIn;
         this.f_83918_ = heightIn;
         this.f_83915_ = widthIn;
         this.f_83916_ = heightIn;
         if (!GLX.isUsingFBOs()) {
            this.m_83954_(onMacIn);
         } else {
            this.f_83920_ = GlStateManager.glGenFramebuffers();
            this.f_83923_ = TextureUtil.generateTextureId();
            if (this.f_83919_) {
               this.f_83924_ = TextureUtil.generateTextureId();
               GlStateManager._bindTexture(this.f_83924_);
               GlStateManager._texParameter(3553, 10241, 9728);
               GlStateManager._texParameter(3553, 10240, 9728);
               GlStateManager._texParameter(3553, 34892, 0);
               GlStateManager._texParameter(3553, 10242, 33071);
               GlStateManager._texParameter(3553, 10243, 33071);
               if (this.stencilEnabled) {
                  GlStateManager._texImage2D(3553, 0, 36013, this.f_83915_, this.f_83916_, 0, 34041, 36269, null);
               } else {
                  GlStateManager._texImage2D(3553, 0, 6402, this.f_83915_, this.f_83916_, 0, 6402, 5126, null);
               }
            }

            this.m_321006_(9728, true);
            GlStateManager._bindTexture(this.f_83923_);
            GlStateManager._texParameter(3553, 10242, 33071);
            GlStateManager._texParameter(3553, 10243, 33071);
            GlStateManager._texImage2D(3553, 0, 32856, this.f_83915_, this.f_83916_, 0, 6408, 5121, null);
            GlStateManager._glBindFramebuffer(36160, this.f_83920_);
            GlStateManager._glFramebufferTexture2D(36160, 36064, 3553, this.f_83923_, 0);
            if (this.f_83919_) {
               if (this.stencilEnabled) {
                  if (ReflectorForge.getForgeUseCombinedDepthStencilAttachment()) {
                     GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, 33306, 3553, this.f_83924_, 0);
                  } else {
                     GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, 36096, 3553, this.f_83924_, 0);
                     GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, 36128, 3553, this.f_83924_, 0);
                  }
               } else {
                  GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.f_83924_, 0);
               }
            }

            this.m_83949_();
            this.m_83954_(onMacIn);
            this.m_83963_();
         }
      } else {
         throw new IllegalArgumentException("Window " + widthIn + "x" + heightIn + " size out of bounds (max. size: " + i + ")");
      }
   }

   public void m_83936_(int framebufferFilterIn) {
      this.m_321006_(framebufferFilterIn, false);
   }

   private void m_321006_(int framebufferFilterIn, boolean forcedIn) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         if (forcedIn || framebufferFilterIn != this.f_83922_) {
            this.f_83922_ = framebufferFilterIn;
            GlStateManager._bindTexture(this.f_83923_);
            GlStateManager._texParameter(3553, 10241, framebufferFilterIn);
            GlStateManager._texParameter(3553, 10240, framebufferFilterIn);
            GlStateManager._bindTexture(0);
         }
      }
   }

   public void m_83949_() {
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

   public void m_83956_() {
      RenderSystem.assertOnRenderThread();
      GlStateManager._bindTexture(this.f_83923_);
   }

   public void m_83963_() {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._bindTexture(0);
      }
   }

   public void m_83947_(boolean setViewportIn) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.m_83961_(setViewportIn));
      } else {
         this.m_83961_(setViewportIn);
      }
   }

   private void m_83961_(boolean setViewportIn) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThreadOrInit();
         GlStateManager._glBindFramebuffer(36160, this.f_83920_);
         if (setViewportIn) {
            GlStateManager._viewport(0, 0, this.f_83917_, this.f_83918_);
         }
      }
   }

   public void m_83970_() {
      if (GLX.isUsingFBOs()) {
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> GlStateManager._glBindFramebuffer(36160, 0));
         } else {
            GlStateManager._glBindFramebuffer(36160, 0);
         }
      }
   }

   public void m_83931_(float red, float green, float blue, float alpha) {
      this.f_83921_[0] = red;
      this.f_83921_[1] = green;
      this.f_83921_[2] = blue;
      this.f_83921_[3] = alpha;
   }

   public void m_83938_(int width, int height) {
      this.m_83957_(width, height, true);
   }

   public void m_83957_(int width, int height, boolean noBlend) {
      this.m_83971_(width, height, noBlend);
   }

   private void m_83971_(int width, int height, boolean noBlend) {
      if (GLX.isUsingFBOs()) {
         RenderSystem.assertOnRenderThread();
         GlStateManager._colorMask(true, true, true, false);
         GlStateManager._disableDepthTest();
         GlStateManager._depthMask(false);
         GlStateManager._viewport(0, 0, width, height);
         if (noBlend) {
            GlStateManager._disableBlend();
         }

         Minecraft minecraft = Minecraft.m_91087_();
         ShaderInstance shaderinstance = (ShaderInstance)Objects.requireNonNull(minecraft.f_91063_.f_172635_, "Blit shader not loaded");
         shaderinstance.m_173350_("DiffuseSampler", this.f_83923_);
         shaderinstance.m_173363_();
         BufferBuilder bufferbuilder = RenderSystem.renderThreadTesselator().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_166850_);
         bufferbuilder.m_167146_(0.0F, 0.0F, 0.0F);
         bufferbuilder.m_167146_(1.0F, 0.0F, 0.0F);
         bufferbuilder.m_167146_(1.0F, 1.0F, 0.0F);
         bufferbuilder.m_167146_(0.0F, 1.0F, 0.0F);
         BufferUploader.m_231209_(bufferbuilder.m_339905_());
         shaderinstance.m_173362_();
         GlStateManager._depthMask(true);
         GlStateManager._colorMask(true, true, true, true);
      }
   }

   public void m_83954_(boolean onMac) {
      RenderSystem.assertOnRenderThreadOrInit();
      this.m_83947_(true);
      GlStateManager._clearColor(this.f_83921_[0], this.f_83921_[1], this.f_83921_[2], this.f_83921_[3]);
      int i = 16384;
      if (this.f_83919_) {
         GlStateManager._clearDepth(1.0);
         i |= 256;
      }

      GlStateManager._clear(i, onMac);
      this.m_83970_();
   }

   public int m_83975_() {
      return this.f_83923_;
   }

   public int m_83980_() {
      return this.f_83924_;
   }

   public void enableStencil() {
      if (!this.stencilEnabled) {
         this.stencilEnabled = true;
         this.m_83941_(this.f_83917_, this.f_83918_, Minecraft.f_91002_);
      }
   }

   public boolean isStencilEnabled() {
      return this.stencilEnabled;
   }
}
