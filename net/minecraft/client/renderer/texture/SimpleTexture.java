package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class SimpleTexture extends net.minecraft.client.renderer.texture.AbstractTexture {
   static final Logger f_118130_ = LogUtils.getLogger();
   protected final net.minecraft.resources.ResourceLocation f_118129_;
   private ResourceManager resourceManager;
   public net.minecraft.resources.ResourceLocation locationEmissive;
   public boolean isEmissive;
   public long size;

   public SimpleTexture(net.minecraft.resources.ResourceLocation textureResourceLocation) {
      this.f_118129_ = textureResourceLocation;
   }

   @Override
   public void m_6704_(ResourceManager manager) throws IOException {
      this.resourceManager = manager;
      net.minecraft.client.renderer.texture.SimpleTexture.TextureImage simpletexture$textureimage = this.m_6335_(manager);
      simpletexture$textureimage.m_118159_();
      TextureMetadataSection texturemetadatasection = simpletexture$textureimage.m_118154_();
      boolean flag;
      boolean flag1;
      if (texturemetadatasection != null) {
         flag = texturemetadatasection.m_119115_();
         flag1 = texturemetadatasection.m_119116_();
      } else {
         flag = false;
         flag1 = false;
      }

      com.mojang.blaze3d.platform.NativeImage nativeimage = simpletexture$textureimage.m_118158_();
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> this.m_118136_(nativeimage, flag, flag1));
      } else {
         this.m_118136_(nativeimage, flag, flag1);
      }
   }

   private void m_118136_(com.mojang.blaze3d.platform.NativeImage imageIn, boolean blurIn, boolean clampIn) {
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

   protected net.minecraft.client.renderer.texture.SimpleTexture.TextureImage m_6335_(ResourceManager resourceManager) {
      return net.minecraft.client.renderer.texture.SimpleTexture.TextureImage.m_118155_(resourceManager, this.f_118129_);
   }

   protected static class TextureImage implements Closeable {
      @Nullable
      private final TextureMetadataSection f_118146_;
      @Nullable
      private final com.mojang.blaze3d.platform.NativeImage f_118147_;
      @Nullable
      private final IOException f_118148_;

      public TextureImage(IOException exceptionIn) {
         this.f_118148_ = exceptionIn;
         this.f_118146_ = null;
         this.f_118147_ = null;
      }

      public TextureImage(@Nullable TextureMetadataSection metadataIn, com.mojang.blaze3d.platform.NativeImage imageIn) {
         this.f_118148_ = null;
         this.f_118146_ = metadataIn;
         this.f_118147_ = imageIn;
      }

      public static net.minecraft.client.renderer.texture.SimpleTexture.TextureImage m_118155_(
         ResourceManager resourceManagerIn, net.minecraft.resources.ResourceLocation locationIn
      ) {
         try {
            Resource resource = resourceManagerIn.m_215593_(locationIn);
            InputStream inputstream = resource.m_215507_();

            com.mojang.blaze3d.platform.NativeImage nativeimage;
            try {
               nativeimage = com.mojang.blaze3d.platform.NativeImage.m_85058_(inputstream);
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

            TextureMetadataSection texturemetadatasection = null;

            try {
               texturemetadatasection = (TextureMetadataSection)resource.m_215509_().m_214059_(TextureMetadataSection.f_119108_).orElse(null);
            } catch (RuntimeException var8) {
               net.minecraft.client.renderer.texture.SimpleTexture.f_118130_.warn("Failed reading metadata of: {}", locationIn, var8);
            }

            return new net.minecraft.client.renderer.texture.SimpleTexture.TextureImage(texturemetadatasection, nativeimage);
         } catch (IOException var10) {
            return new net.minecraft.client.renderer.texture.SimpleTexture.TextureImage(var10);
         }
      }

      @Nullable
      public TextureMetadataSection m_118154_() {
         return this.f_118146_;
      }

      public com.mojang.blaze3d.platform.NativeImage m_118158_() throws IOException {
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
