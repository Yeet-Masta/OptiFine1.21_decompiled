import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_77_;
import net.optifine.Config;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;
import net.optifine.player.CapeImageBuffer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class HttpTexture extends SimpleTexture {
   private static final Logger f = LogUtils.getLogger();
   private static final int g = 64;
   private static final int h = 64;
   private static final int i = 32;
   @Nullable
   private final File j;
   private final String k;
   private final boolean l;
   @Nullable
   private final Runnable m;
   @Nullable
   private CompletableFuture<?> n;
   private boolean o;
   public Boolean imageFound = null;
   public boolean pipeline = false;
   private boolean uploadPending = false;

   public HttpTexture(
      @Nullable File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, boolean legacySkinIn, @Nullable Runnable processTaskIn
   ) {
      super(textureResourceLocation);
      this.j = cacheFileIn;
      this.k = imageUrlIn;
      this.l = legacySkinIn;
      this.m = processTaskIn;
   }

   private void a(NativeImage nativeImageIn) {
      if (this.m instanceof CapeImageBuffer cib) {
         nativeImageIn = cib.parseUserSkin(nativeImageIn);
         cib.skinAvailable();
      }

      this.setImageImpl(nativeImageIn);
   }

   private void setImageImpl(NativeImage nativeImageIn) {
      if (this.m != null) {
         this.m.run();
      }

      C_3391_.m_91087_().execute(() -> {
         this.o = true;
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.b(nativeImageIn));
         } else {
            this.b(nativeImageIn);
         }
      });
   }

   private void b(NativeImage imageIn) {
      TextureUtil.prepareImage(this.a(), imageIn.a(), imageIn.b());
      imageIn.a(0, 0, 0, true);
      this.imageFound = imageIn != null;
      this.size = imageIn.getSize();
   }

   @Override
   public void a(C_77_ manager) throws IOException {
      C_3391_.m_91087_().execute(() -> {
         if (!this.o) {
            try {
               super.a(manager);
            } catch (IOException var3x) {
               f.warn("Failed to load texture: {}", this.e, var3x);
            }

            this.o = true;
         }
      });
      if (this.n == null) {
         NativeImage nativeimage;
         if (this.j != null && this.j.isFile()) {
            f.debug("Loading http texture from local cache ({})", this.j);
            FileInputStream fileinputstream = new FileInputStream(this.j);
            nativeimage = this.a(fileinputstream);
         } else {
            nativeimage = null;
         }

         if (nativeimage != null) {
            this.a(nativeimage);
            this.loadingFinished();
         } else {
            this.n = CompletableFuture.runAsync(() -> {
               HttpURLConnection httpurlconnection = null;
               f.debug("Downloading http texture from {} to {}", this.k, this.j);
               if (this.shouldPipeline()) {
                  this.loadPipelined();
               } else {
                  try {
                     httpurlconnection = (HttpURLConnection)new URL(this.k).openConnection(C_3391_.m_91087_().m_91096_());
                     httpurlconnection.setDoInput(true);
                     httpurlconnection.setDoOutput(false);
                     httpurlconnection.connect();
                     if (httpurlconnection.getResponseCode() / 100 != 2) {
                        if (httpurlconnection.getErrorStream() != null) {
                           Config.readAll(httpurlconnection.getErrorStream());
                        }

                        return;
                     }

                     InputStream inputstream;
                     if (this.j != null) {
                        FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), this.j);
                        inputstream = new FileInputStream(this.j);
                     } else {
                        inputstream = httpurlconnection.getInputStream();
                     }

                     C_3391_.m_91087_().execute(() -> {
                        NativeImage nativeimage1 = this.a(inputstream);
                        if (nativeimage1 != null) {
                           this.a(nativeimage1);
                           this.loadingFinished();
                        }
                     });
                     this.uploadPending = true;
                  } catch (Exception var6) {
                     f.error("Couldn't download http texture", var6);
                     return;
                  } finally {
                     if (httpurlconnection != null) {
                        httpurlconnection.disconnect();
                     }

                     this.loadingFinished();
                  }
               }
            }, this.getExecutor());
         }
      }
   }

   @Nullable
   private NativeImage a(InputStream inputStreamIn) {
      NativeImage nativeimage = null;

      try {
         nativeimage = NativeImage.a(inputStreamIn);
         if (this.l) {
            nativeimage = this.c(nativeimage);
         }
      } catch (Exception var4) {
         f.warn("Error while loading the skin texture", var4);
      }

      return nativeimage;
   }

   private boolean shouldPipeline() {
      if (!this.pipeline) {
         return false;
      } else {
         Proxy proxy = C_3391_.m_91087_().m_91096_();
         return proxy.type() != Type.DIRECT && proxy.type() != Type.SOCKS ? false : this.k.startsWith("http://");
      }
   }

   private void loadPipelined() {
      try {
         HttpRequest req = HttpPipeline.makeRequest(this.k, C_3391_.m_91087_().m_91096_());
         HttpResponse resp = HttpPipeline.executeRequest(req);
         if (resp.getStatus() / 100 == 2) {
            byte[] body = resp.getBody();
            ByteArrayInputStream bais = new ByteArrayInputStream(body);
            NativeImage ni;
            if (this.j != null) {
               FileUtils.copyInputStreamToFile(bais, this.j);
               ni = NativeImage.a(new FileInputStream(this.j));
            } else {
               ni = NativeImage.a(bais);
            }

            this.a(ni);
            this.uploadPending = true;
            return;
         }
      } catch (Exception var9) {
         f.error("Couldn't download http texture: " + var9.getClass().getName() + ": " + var9.getMessage());
         return;
      } finally {
         this.loadingFinished();
      }
   }

   private void loadingFinished() {
      if (!this.uploadPending) {
         if (this.m instanceof CapeImageBuffer cib) {
            cib.cleanup();
         }
      }
   }

   public Runnable getProcessTask() {
      return this.m;
   }

   private Executor getExecutor() {
      return this.k.startsWith("http://s.optifine.net") ? Util.getCapeExecutor() : Util.g();
   }

   @Nullable
   private NativeImage c(NativeImage imageIn) {
      int i = imageIn.b();
      int j = imageIn.a();
      if (j == 64 && (i == 32 || i == 64)) {
         boolean flag = i == 32;
         if (flag) {
            NativeImage nativeimage = new NativeImage(64, 64, true);
            nativeimage.a(imageIn);
            imageIn.close();
            imageIn = nativeimage;
            nativeimage.a(0, 32, 64, 32, 0);
            nativeimage.a(4, 16, 16, 32, 4, 4, true, false);
            nativeimage.a(8, 16, 16, 32, 4, 4, true, false);
            nativeimage.a(0, 20, 24, 32, 4, 12, true, false);
            nativeimage.a(4, 20, 16, 32, 4, 12, true, false);
            nativeimage.a(8, 20, 8, 32, 4, 12, true, false);
            nativeimage.a(12, 20, 16, 32, 4, 12, true, false);
            nativeimage.a(44, 16, -8, 32, 4, 4, true, false);
            nativeimage.a(48, 16, -8, 32, 4, 4, true, false);
            nativeimage.a(40, 20, 0, 32, 4, 12, true, false);
            nativeimage.a(44, 20, -8, 32, 4, 12, true, false);
            nativeimage.a(48, 20, -16, 32, 4, 12, true, false);
            nativeimage.a(52, 20, -8, 32, 4, 12, true, false);
         }

         b(imageIn, 0, 0, 32, 16);
         if (flag) {
            a(imageIn, 32, 0, 64, 32);
         }

         b(imageIn, 0, 16, 64, 32);
         b(imageIn, 16, 48, 48, 64);
         return imageIn;
      } else {
         imageIn.close();
         f.warn("Discarding incorrectly sized ({}x{}) skin texture from {}", new Object[]{j, i, this.k});
         return null;
      }
   }

   private static void a(NativeImage image, int x, int y, int width, int height) {
      for (int i = x; i < width; i++) {
         for (int j = y; j < height; j++) {
            int k = image.a(i, j);
            if ((k >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for (int l = x; l < width; l++) {
         for (int i1 = y; i1 < height; i1++) {
            image.a(l, i1, image.a(l, i1) & 16777215);
         }
      }
   }

   private static void b(NativeImage image, int x, int y, int width, int height) {
      for (int i = x; i < width; i++) {
         for (int j = y; j < height; j++) {
            image.a(i, j, image.a(i, j) | 0xFF000000);
         }
      }
   }
}
