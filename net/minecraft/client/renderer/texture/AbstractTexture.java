package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.Config;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.ShadersTex;

public abstract class AbstractTexture implements AutoCloseable {
   public static final int f_174680_ = -1;
   protected int f_117950_ = -1;
   protected boolean f_117951_;
   protected boolean f_117952_;
   public MultiTexID multiTex;
   private boolean blurMipmapSet;
   private boolean lastBlur;
   private boolean lastMipmap;

   public void m_117960_(boolean blurIn, boolean mipmapIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (!this.blurMipmapSet || this.f_117951_ != blurIn || this.f_117952_ != mipmapIn) {
         this.blurMipmapSet = true;
         this.f_117951_ = blurIn;
         this.f_117952_ = mipmapIn;
         int i;
         int j;
         if (blurIn) {
            i = mipmapIn ? 9987 : 9729;
            j = 9729;
         } else {
            int mipmapType = Config.getMipmapType();
            i = mipmapIn ? mipmapType : 9728;
            j = 9728;
         }

         GlStateManager._bindTexture(this.m_117963_());
         this.m_117966_();
         GlStateManager._texParameter(3553, 10241, i);
         GlStateManager._texParameter(3553, 10240, j);
      }
   }

   public int m_117963_() {
      RenderSystem.assertOnRenderThreadOrInit();
      if (this.f_117950_ == -1) {
         this.f_117950_ = TextureUtil.generateTextureId();
      }

      return this.f_117950_;
   }

   public void m_117964_() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            ShadersTex.deleteTextures(this, this.f_117950_);
            this.blurMipmapSet = false;
            if (this.f_117950_ != -1) {
               TextureUtil.releaseTextureId(this.f_117950_);
               this.f_117950_ = -1;
            }
         });
      } else if (this.f_117950_ != -1) {
         ShadersTex.deleteTextures(this, this.f_117950_);
         this.blurMipmapSet = false;
         TextureUtil.releaseTextureId(this.f_117950_);
         this.f_117950_ = -1;
      }
   }

   public abstract void m_6704_(ResourceManager var1) throws IOException;

   public void m_117966_() {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> GlStateManager._bindTexture(this.m_117963_()));
      } else {
         GlStateManager._bindTexture(this.m_117963_());
      }
   }

   public void m_6479_(
      net.minecraft.client.renderer.texture.TextureManager textureManagerIn,
      ResourceManager resourceManagerIn,
      net.minecraft.resources.ResourceLocation resourceLocationIn,
      Executor executorIn
   ) {
      textureManagerIn.m_118495_(resourceLocationIn, this);
   }

   public void close() {
   }

   public MultiTexID getMultiTexID() {
      return ShadersTex.getMultiTexID(this);
   }

   public void setBlurMipmap(boolean blur, boolean mipmap) {
      this.lastBlur = this.f_117951_;
      this.lastMipmap = this.f_117952_;
      this.m_117960_(blur, mipmap);
   }

   public void restoreLastBlurMipmap() {
      this.m_117960_(this.lastBlur, this.lastMipmap);
   }
}
