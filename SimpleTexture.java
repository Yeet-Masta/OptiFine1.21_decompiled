import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.minecraft.src.C_4526_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class SimpleTexture extends AbstractTexture {
   static final Logger f = LogUtils.getLogger();
   protected final ResourceLocation e;
   private C_77_ resourceManager;
   public ResourceLocation locationEmissive;
   public boolean isEmissive;
   public long size;

   public SimpleTexture(ResourceLocation textureResourceLocation) {
      this.e = textureResourceLocation;
   }

   @Override
   public void a(C_77_ manager) throws IOException {
      this.resourceManager = manager;
      SimpleTexture.a simpletexture$textureimage = this.b(manager);
      simpletexture$textureimage.c();
      C_4526_ texturemetadatasection = simpletexture$textureimage.a();
      boolean flag;
      boolean flag1;
      if (texturemetadatasection != null) {
         flag = texturemetadatasection.m_119115_();
         flag1 = texturemetadatasection.m_119116_();
      } else {
         flag = false;
         flag1 = false;
      }

      NativeImage nativeimage = simpletexture$textureimage.b();
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> this.a(nativeimage, flag, flag1));
      } else {
         this.a(nativeimage, flag, flag1);
      }
   }

   private void a(NativeImage imageIn, boolean blurIn, boolean clampIn) {
      TextureUtil.prepareImage(this.a(), 0, imageIn.a(), imageIn.b());
      imageIn.a(0, 0, 0, 0, 0, imageIn.a(), imageIn.b(), blurIn, clampIn, false, true);
      if (Config.isShaders()) {
         ShadersTex.loadSimpleTextureNS(this.a(), imageIn, blurIn, clampIn, this.resourceManager, this.e, this.getMultiTexID());
      }

      if (EmissiveTextures.isActive()) {
         EmissiveTextures.loadTexture(this.e, this);
      }

      this.size = imageIn.getSize();
   }

   protected SimpleTexture.a b(C_77_ resourceManager) {
      return SimpleTexture.a.a(resourceManager, this.e);
   }

   protected static class a implements Closeable {
      @Nullable
      private final C_4526_ a;
      @Nullable
      private final NativeImage b;
      @Nullable
      private final IOException c;

      public a(IOException exceptionIn) {
         this.c = exceptionIn;
         this.a = null;
         this.b = null;
      }

      public a(@Nullable C_4526_ metadataIn, NativeImage imageIn) {
         this.c = null;
         this.a = metadataIn;
         this.b = imageIn;
      }

      public static SimpleTexture.a a(C_77_ resourceManagerIn, ResourceLocation locationIn) {
         try {
            C_76_ resource = resourceManagerIn.getResourceOrThrow(locationIn);
            InputStream inputstream = resource.m_215507_();

            NativeImage nativeimage;
            try {
               nativeimage = NativeImage.a(inputstream);
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
               texturemetadatasection = (C_4526_)resource.m_215509_().m_214059_(C_4526_.f_119108_).orElse(null);
            } catch (RuntimeException var8) {
               SimpleTexture.f.warn("Failed reading metadata of: {}", locationIn, var8);
            }

            return new SimpleTexture.a(texturemetadatasection, nativeimage);
         } catch (IOException var10) {
            return new SimpleTexture.a(var10);
         }
      }

      @Nullable
      public C_4526_ a() {
         return this.a;
      }

      public NativeImage b() throws IOException {
         if (this.c != null) {
            throw this.c;
         } else {
            return this.b;
         }
      }

      public void close() {
         if (this.b != null) {
            this.b.close();
         }
      }

      public void c() throws IOException {
         if (this.c != null) {
            throw this.c;
         }
      }
   }
}
