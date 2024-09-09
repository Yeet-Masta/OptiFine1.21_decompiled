import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.src.C_276066_;
import net.minecraft.src.C_77_;
import net.optifine.Config;
import net.optifine.shaders.ShadersTex;
import org.slf4j.Logger;

public class DynamicTexture extends AbstractTexture implements C_276066_ {
   private static final Logger e = LogUtils.getLogger();
   @Nullable
   private NativeImage f;

   public DynamicTexture(NativeImage nativeImageIn) {
      this.f = nativeImageIn;
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            TextureUtil.prepareImage(this.a(), this.f.a(), this.f.b());
            this.d();
            if (Config.isShaders()) {
               ShadersTex.initDynamicTextureNS(this);
            }
         });
      } else {
         TextureUtil.prepareImage(this.a(), this.f.a(), this.f.b());
         this.d();
         if (Config.isShaders()) {
            ShadersTex.initDynamicTextureNS(this);
         }
      }
   }

   public DynamicTexture(int widthIn, int heightIn, boolean clearIn) {
      this.f = new NativeImage(widthIn, heightIn, clearIn);
      TextureUtil.prepareImage(this.a(), this.f.a(), this.f.b());
      if (Config.isShaders()) {
         ShadersTex.initDynamicTextureNS(this);
      }
   }

   @Override
   public void a(C_77_ manager) {
   }

   public void d() {
      if (this.f != null) {
         this.c();
         this.f.a(0, 0, 0, false);
      } else {
         e.warn("Trying to upload disposed texture {}", this.a());
      }
   }

   @Nullable
   public NativeImage e() {
      return this.f;
   }

   public void a(NativeImage nativeImageIn) {
      if (this.f != null) {
         this.f.close();
      }

      this.f = nativeImageIn;
   }

   @Override
   public void close() {
      if (this.f != null) {
         this.f.close();
         this.b();
         this.f = null;
      }
   }

   public void a(ResourceLocation locIn, Path pathIn) throws IOException {
      if (this.f != null) {
         String s = locIn.c() + ".png";
         Path path = pathIn.resolve(s);
         this.f.a(path);
      }
   }
}
