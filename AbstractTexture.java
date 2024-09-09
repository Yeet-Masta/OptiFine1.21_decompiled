import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.concurrent.Executor;
import net.minecraft.src.C_77_;
import net.optifine.Config;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.ShadersTex;

public abstract class AbstractTexture implements AutoCloseable {
   public static final int a = -1;
   protected int b = -1;
   protected boolean c;
   protected boolean d;
   public MultiTexID multiTex;
   private boolean blurMipmapSet;
   private boolean lastBlur;
   private boolean lastMipmap;

   public void a(boolean blurIn, boolean mipmapIn) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (!this.blurMipmapSet || this.c != blurIn || this.d != mipmapIn) {
         this.blurMipmapSet = true;
         this.c = blurIn;
         this.d = mipmapIn;
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

         GlStateManager._bindTexture(this.a());
         this.c();
         GlStateManager._texParameter(3553, 10241, i);
         GlStateManager._texParameter(3553, 10240, j);
      }
   }

   public int a() {
      RenderSystem.assertOnRenderThreadOrInit();
      if (this.b == -1) {
         this.b = TextureUtil.generateTextureId();
      }

      return this.b;
   }

   public void b() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            ShadersTex.deleteTextures(this, this.b);
            this.blurMipmapSet = false;
            if (this.b != -1) {
               TextureUtil.releaseTextureId(this.b);
               this.b = -1;
            }
         });
      } else if (this.b != -1) {
         ShadersTex.deleteTextures(this, this.b);
         this.blurMipmapSet = false;
         TextureUtil.releaseTextureId(this.b);
         this.b = -1;
      }
   }

   public abstract void a(C_77_ var1) throws IOException;

   public void c() {
      if (!RenderSystem.isOnRenderThreadOrInit()) {
         RenderSystem.recordRenderCall(() -> GlStateManager._bindTexture(this.a()));
      } else {
         GlStateManager._bindTexture(this.a());
      }
   }

   public void a(TextureManager textureManagerIn, C_77_ resourceManagerIn, ResourceLocation resourceLocationIn, Executor executorIn) {
      textureManagerIn.a(resourceLocationIn, this);
   }

   public void close() {
   }

   public MultiTexID getMultiTexID() {
      return ShadersTex.getMultiTexID(this);
   }

   public void setBlurMipmap(boolean blur, boolean mipmap) {
      this.lastBlur = this.c;
      this.lastMipmap = this.d;
      this.a(blur, mipmap);
   }

   public void restoreLastBlurMipmap() {
      this.a(this.lastBlur, this.lastMipmap);
   }
}
