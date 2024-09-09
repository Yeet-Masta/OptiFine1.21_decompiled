package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
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
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.Config;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;
import net.optifine.player.CapeImageBuffer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class HttpTexture extends SimpleTexture {
   private static final Logger f_117993_ = LogUtils.getLogger();
   private static final int f_181889_ = 64;
   private static final int f_181890_ = 64;
   private static final int f_181891_ = 32;
   @Nullable
   private final File f_117994_;
   private final String f_117995_;
   private final boolean f_117996_;
   @Nullable
   private final Runnable f_117997_;
   @Nullable
   private CompletableFuture f_117998_;
   private boolean f_117999_;
   public Boolean imageFound = null;
   public boolean pipeline = false;
   private boolean uploadPending = false;

   public HttpTexture(@Nullable File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, boolean legacySkinIn, @Nullable Runnable processTaskIn) {
      super(textureResourceLocation);
      this.f_117994_ = cacheFileIn;
      this.f_117995_ = imageUrlIn;
      this.f_117996_ = legacySkinIn;
      this.f_117997_ = processTaskIn;
   }

   private void m_118010_(NativeImage nativeImageIn) {
      if (this.f_117997_ instanceof CapeImageBuffer) {
         CapeImageBuffer cib = (CapeImageBuffer)this.f_117997_;
         nativeImageIn = cib.parseUserSkin(nativeImageIn);
         cib.skinAvailable();
      }

      this.setImageImpl(nativeImageIn);
   }

   private void setImageImpl(NativeImage nativeImageIn) {
      if (this.f_117997_ != null) {
         this.f_117997_.run();
      }

      Minecraft.m_91087_().execute(() -> {
         this.f_117999_ = true;
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
               this.m_118020_(nativeImageIn);
            });
         } else {
            this.m_118020_(nativeImageIn);
         }

      });
   }

   private void m_118020_(NativeImage imageIn) {
      TextureUtil.prepareImage(this.m_117963_(), imageIn.m_84982_(), imageIn.m_85084_());
      imageIn.m_85040_(0, 0, 0, true);
      this.imageFound = imageIn != null;
      this.size = imageIn.getSize();
   }

   public void m_6704_(ResourceManager manager) throws IOException {
      Minecraft.m_91087_().execute(() -> {
         if (!this.f_117999_) {
            try {
               super.m_6704_(manager);
            } catch (IOException var3) {
               f_117993_.warn("Failed to load texture: {}", this.f_118129_, var3);
            }

            this.f_117999_ = true;
         }

      });
      if (this.f_117998_ == null) {
         NativeImage nativeimage;
         if (this.f_117994_ != null && this.f_117994_.isFile()) {
            f_117993_.debug("Loading http texture from local cache ({})", this.f_117994_);
            FileInputStream fileinputstream = new FileInputStream(this.f_117994_);
            nativeimage = this.m_118018_(fileinputstream);
         } else {
            nativeimage = null;
         }

         if (nativeimage != null) {
            this.m_118010_(nativeimage);
            this.loadingFinished();
         } else {
            this.f_117998_ = CompletableFuture.runAsync(() -> {
               HttpURLConnection httpurlconnection = null;
               f_117993_.debug("Downloading http texture from {} to {}", this.f_117995_, this.f_117994_);
               if (this.shouldPipeline()) {
                  this.loadPipelined();
               } else {
                  try {
                     httpurlconnection = (HttpURLConnection)(new URL(this.f_117995_)).openConnection(Minecraft.m_91087_().m_91096_());
                     httpurlconnection.setDoInput(true);
                     httpurlconnection.setDoOutput(false);
                     httpurlconnection.connect();
                     if (httpurlconnection.getResponseCode() / 100 == 2) {
                        Object inputstream;
                        if (this.f_117994_ != null) {
                           FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), this.f_117994_);
                           inputstream = new FileInputStream(this.f_117994_);
                        } else {
                           inputstream = httpurlconnection.getInputStream();
                        }

                        Minecraft.m_91087_().execute(() -> {
                           NativeImage nativeimage1 = this.m_118018_(inputstream);
                           if (nativeimage1 != null) {
                              this.m_118010_(nativeimage1);
                              this.loadingFinished();
                           }

                        });
                        this.uploadPending = true;
                        return;
                     }

                     if (httpurlconnection.getErrorStream() != null) {
                        Config.readAll(httpurlconnection.getErrorStream());
                     }

                     return;
                  } catch (Exception var6) {
                     f_117993_.error("Couldn't download http texture", var6);
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
   private NativeImage m_118018_(InputStream inputStreamIn) {
      NativeImage nativeimage = null;

      try {
         nativeimage = NativeImage.m_85058_(inputStreamIn);
         if (this.f_117996_) {
            nativeimage = this.m_118032_(nativeimage);
         }
      } catch (Exception var4) {
         f_117993_.warn("Error while loading the skin texture", var4);
      }

      return nativeimage;
   }

   private boolean shouldPipeline() {
      if (!this.pipeline) {
         return false;
      } else {
         Proxy proxy = Minecraft.m_91087_().m_91096_();
         if (proxy.type() != Type.DIRECT && proxy.type() != Type.SOCKS) {
            return false;
         } else {
            return this.f_117995_.startsWith("http://");
         }
      }
   }

   private void loadPipelined() {
      try {
         HttpRequest req = HttpPipeline.makeRequest(this.f_117995_, Minecraft.m_91087_().m_91096_());
         HttpResponse resp = HttpPipeline.executeRequest(req);
         if (resp.getStatus() / 100 != 2) {
            return;
         }

         byte[] body = resp.getBody();
         ByteArrayInputStream bais = new ByteArrayInputStream(body);
         NativeImage ni;
         if (this.f_117994_ != null) {
            FileUtils.copyInputStreamToFile(bais, this.f_117994_);
            ni = NativeImage.m_85058_(new FileInputStream(this.f_117994_));
         } else {
            ni = NativeImage.m_85058_(bais);
         }

         this.m_118010_(ni);
         this.uploadPending = true;
         return;
      } catch (Exception var9) {
         Logger var10000 = f_117993_;
         String var10001 = var9.getClass().getName();
         var10000.error("Couldn't download http texture: " + var10001 + ": " + var9.getMessage());
      } finally {
         this.loadingFinished();
      }

   }

   private void loadingFinished() {
      if (!this.uploadPending) {
         if (this.f_117997_ instanceof CapeImageBuffer) {
            CapeImageBuffer cib = (CapeImageBuffer)this.f_117997_;
            cib.cleanup();
         }

      }
   }

   public Runnable getProcessTask() {
      return this.f_117997_;
   }

   private Executor getExecutor() {
      return this.f_117995_.startsWith("http://s.optifine.net") ? Util.getCapeExecutor() : Util.m_183991_();
   }

   @Nullable
   private NativeImage m_118032_(NativeImage imageIn) {
      int i = imageIn.m_85084_();
      int j = imageIn.m_84982_();
      if (j != 64 || i != 32 && i != 64) {
         imageIn.close();
         f_117993_.warn("Discarding incorrectly sized ({}x{}) skin texture from {}", new Object[]{j, i, this.f_117995_});
         return null;
      } else {
         boolean flag = i == 32;
         if (flag) {
            NativeImage nativeimage = new NativeImage(64, 64, true);
            nativeimage.m_85054_(imageIn);
            imageIn.close();
            imageIn = nativeimage;
            nativeimage.m_84997_(0, 32, 64, 32, 0);
            nativeimage.m_85025_(4, 16, 16, 32, 4, 4, true, false);
            nativeimage.m_85025_(8, 16, 16, 32, 4, 4, true, false);
            nativeimage.m_85025_(0, 20, 24, 32, 4, 12, true, false);
            nativeimage.m_85025_(4, 20, 16, 32, 4, 12, true, false);
            nativeimage.m_85025_(8, 20, 8, 32, 4, 12, true, false);
            nativeimage.m_85025_(12, 20, 16, 32, 4, 12, true, false);
            nativeimage.m_85025_(44, 16, -8, 32, 4, 4, true, false);
            nativeimage.m_85025_(48, 16, -8, 32, 4, 4, true, false);
            nativeimage.m_85025_(40, 20, 0, 32, 4, 12, true, false);
            nativeimage.m_85025_(44, 20, -8, 32, 4, 12, true, false);
            nativeimage.m_85025_(48, 20, -16, 32, 4, 12, true, false);
            nativeimage.m_85025_(52, 20, -8, 32, 4, 12, true, false);
         }

         m_118022_(imageIn, 0, 0, 32, 16);
         if (flag) {
            m_118012_(imageIn, 32, 0, 64, 32);
         }

         m_118022_(imageIn, 0, 16, 64, 32);
         m_118022_(imageIn, 16, 48, 48, 64);
         return imageIn;
      }
   }

   private static void m_118012_(NativeImage image, int x, int y, int width, int height) {
      int l;
      int i1;
      for(l = x; l < width; ++l) {
         for(i1 = y; i1 < height; ++i1) {
            int k = image.m_84985_(l, i1);
            if ((k >> 24 & 255) < 128) {
               return;
            }
         }
      }

      for(l = x; l < width; ++l) {
         for(i1 = y; i1 < height; ++i1) {
            image.m_84988_(l, i1, image.m_84985_(l, i1) & 16777215);
         }
      }

   }

   private static void m_118022_(NativeImage image, int x, int y, int width, int height) {
      for(int i = x; i < width; ++i) {
         for(int j = y; j < height; ++j) {
            image.m_84988_(i, j, image.m_84985_(i, j) | -16777216);
         }
      }

   }
}
