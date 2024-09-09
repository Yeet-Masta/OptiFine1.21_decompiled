package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.Config;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class DynamicTexture extends net.minecraft.client.renderer.texture.AbstractTexture implements Dumpable {
   private static final Logger f_117976_ = LogUtils.getLogger();
   @Nullable
   private com.mojang.blaze3d.platform.NativeImage f_117977_;

   public DynamicTexture(com.mojang.blaze3d.platform.NativeImage nativeImageIn) {
      this.f_117977_ = nativeImageIn;
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            TextureUtil.prepareImage(this.m_117963_(), this.f_117977_.m_84982_(), this.f_117977_.m_85084_());
            this.m_117985_();
            if (Config.isShaders()) {
               ShadersTex.initDynamicTextureNS(this);
            }
         });
      } else {
         TextureUtil.prepareImage(this.m_117963_(), this.f_117977_.m_84982_(), this.f_117977_.m_85084_());
         this.m_117985_();
         if (Config.isShaders()) {
            ShadersTex.initDynamicTextureNS(this);
         }
      }
   }

   public DynamicTexture(int widthIn, int heightIn, boolean clearIn) {
      this.f_117977_ = new com.mojang.blaze3d.platform.NativeImage(widthIn, heightIn, clearIn);
      TextureUtil.prepareImage(this.m_117963_(), this.f_117977_.m_84982_(), this.f_117977_.m_85084_());
      if (Config.isShaders()) {
         ShadersTex.initDynamicTextureNS(this);
      }
   }

   @Override
   public void m_6704_(ResourceManager manager) {
   }

   public void m_117985_() {
      if (this.f_117977_ != null) {
         this.m_117966_();
         this.f_117977_.m_85040_(0, 0, 0, false);
      } else {
         f_117976_.warn("Trying to upload disposed texture {}", this.m_117963_());
      }
   }

   @Nullable
   public com.mojang.blaze3d.platform.NativeImage m_117991_() {
      return this.f_117977_;
   }

   public void m_117988_(com.mojang.blaze3d.platform.NativeImage nativeImageIn) {
      if (this.f_117977_ != null) {
         this.f_117977_.close();
      }

      this.f_117977_ = nativeImageIn;
   }

   @Override
   public void close() {
      if (this.f_117977_ != null) {
         this.f_117977_.close();
         this.m_117964_();
         this.f_117977_ = null;
      }
   }

   public void m_276079_(net.minecraft.resources.ResourceLocation locIn, Path pathIn) throws IOException {
      if (this.f_117977_ != null) {
         String s = locIn.m_179910_() + ".png";
         Path path = pathIn.resolve(s);
         this.f_117977_.m_85066_(path);
      }
   }
}
