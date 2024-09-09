package net.minecraft.src;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.optifine.Config;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class C_4470_ extends C_4468_ implements C_276066_ {
   private static final Logger f_117976_ = LogUtils.getLogger();
   @Nullable
   private C_3148_ f_117977_;

   public C_4470_(C_3148_ nativeImageIn) {
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

   public C_4470_(int widthIn, int heightIn, boolean clearIn) {
      this.f_117977_ = new C_3148_(widthIn, heightIn, clearIn);
      TextureUtil.prepareImage(this.m_117963_(), this.f_117977_.m_84982_(), this.f_117977_.m_85084_());
      if (Config.isShaders()) {
         ShadersTex.initDynamicTextureNS(this);
      }

   }

   public void m_6704_(C_77_ manager) {
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
   public C_3148_ m_117991_() {
      return this.f_117977_;
   }

   public void m_117988_(C_3148_ nativeImageIn) {
      if (this.f_117977_ != null) {
         this.f_117977_.close();
      }

      this.f_117977_ = nativeImageIn;
   }

   public void close() {
      if (this.f_117977_ != null) {
         this.f_117977_.close();
         this.m_117964_();
         this.f_117977_ = null;
      }

   }

   public void m_276079_(C_5265_ locIn, Path pathIn) throws IOException {
      if (this.f_117977_ != null) {
         String s = locIn.m_179910_() + ".png";
         Path path = pathIn.resolve(s);
         this.f_117977_.m_85066_(path);
      }

   }
}
