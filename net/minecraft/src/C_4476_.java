package net.minecraft.src;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class C_4476_ extends C_4468_ {
   static final Logger f_118130_ = LogUtils.getLogger();
   protected final C_5265_ f_118129_;
   private C_77_ resourceManager;
   public C_5265_ locationEmissive;
   public boolean isEmissive;
   public long size;

   public C_4476_(C_5265_ textureResourceLocation) {
      this.f_118129_ = textureResourceLocation;
   }

   public void m_6704_(C_77_ manager) throws IOException {
      this.resourceManager = manager;
      C_4477_ simpletexture$textureimage = this.m_6335_(manager);
      simpletexture$textureimage.m_118159_();
      C_4526_ texturemetadatasection = simpletexture$textureimage.m_118154_();
      boolean flag;
      boolean flag1;
      if (texturemetadatasection != null) {
         flag = texturemetadatasection.m_119115_();
         flag1 = texturemetadatasection.m_119116_();
      } else {
         flag = false;
         flag1 = false;
      }

      C_3148_ nativeimage = simpletexture$textureimage.m_118158_();
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> {
            this.m_118136_(nativeimage, flag, flag1);
         });
      } else {
         this.m_118136_(nativeimage, flag, flag1);
      }

   }

   private void m_118136_(C_3148_ imageIn, boolean blurIn, boolean clampIn) {
      TextureUtil.prepareImage(this.m_117963_(), 0, imageIn.m_84982_(), imageIn.m_85084_());
      imageIn.m_85013_(0, 0, 0, 0, 0, imageIn.m_84982_(), imageIn.m_85084_(), blurIn, clampIn, false, true);
      if (Config.isShaders()) {
         ShadersTex.loadSimpleTextureNS(this.m_117963_(), imageIn, blurIn, clampIn, this.resourceManager, this.f_118129_, this.getMultiTexID());
      }

      if (EmissiveTextures.isActive()) {
         EmissiveTextures.loadTexture(this.f_118129_, this);
      }

      this.size = imageIn.getSize();
   }

   protected C_4477_ m_6335_(C_77_ resourceManager) {
      return C_4476_.C_4477_.m_118155_(resourceManager, this.f_118129_);
   }

   protected static class C_4477_ implements Closeable {
      @Nullable
      private final C_4526_ f_118146_;
      @Nullable
      private final C_3148_ f_118147_;
      @Nullable
      private final IOException f_118148_;

      public C_4477_(IOException exceptionIn) {
         this.f_118148_ = exceptionIn;
         this.f_118146_ = null;
         this.f_118147_ = null;
      }

      public C_4477_(@Nullable C_4526_ metadataIn, C_3148_ imageIn) {
         this.f_118148_ = null;
         this.f_118146_ = metadataIn;
         this.f_118147_ = imageIn;
      }

      public static C_4477_ m_118155_(C_77_ resourceManagerIn, C_5265_ locationIn) {
         try {
            C_76_ resource = resourceManagerIn.getResourceOrThrow(locationIn);
            InputStream inputstream = resource.m_215507_();

            C_3148_ nativeimage;
            try {
               nativeimage = C_3148_.m_85058_(inputstream);
            } catch (Throwable var9) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable var7) {
                     var9.addSuppressed(var7);
                  }
               }

               throw var9;
            }

            if (inputstream != null) {
               inputstream.close();
            }

            C_4526_ texturemetadatasection = null;

            try {
               texturemetadatasection = (C_4526_)resource.m_215509_().m_214059_(C_4526_.f_119108_).orElse((Object)null);
            } catch (RuntimeException var8) {
               C_4476_.f_118130_.warn("Failed reading metadata of: {}", locationIn, var8);
            }

            return new C_4477_(texturemetadatasection, nativeimage);
         } catch (IOException var10) {
            return new C_4477_(var10);
         }
      }

      @Nullable
      public C_4526_ m_118154_() {
         return this.f_118146_;
      }

      public C_3148_ m_118158_() throws IOException {
         if (this.f_118148_ != null) {
            throw this.f_118148_;
         } else {
            return this.f_118147_;
         }
      }

      public void close() {
         if (this.f_118147_ != null) {
            this.f_118147_.close();
         }

      }

      public void m_118159_() throws IOException {
         if (this.f_118148_ != null) {
            throw this.f_118148_;
         }
      }
   }
}
