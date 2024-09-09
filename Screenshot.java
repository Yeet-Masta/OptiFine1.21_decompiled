import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_4993_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_4993_.C_4994_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class Screenshot {
   private static final Logger b = LogUtils.getLogger();
   public static final String a = "screenshots";
   private int c;
   private final DataOutputStream d;
   private final byte[] e;
   private final int f;
   private final int g;
   private File h;

   public static void a(File gameDirectory, RenderTarget buffer, Consumer<C_4996_> messageConsumer) {
      a(gameDirectory, null, buffer, messageConsumer);
   }

   public static void a(File gameDirectory, @Nullable String screenshotName, RenderTarget buffer, Consumer<C_4996_> messageConsumer) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> b(gameDirectory, screenshotName, buffer, messageConsumer));
      } else {
         b(gameDirectory, screenshotName, buffer, messageConsumer);
      }
   }

   private static void b(File gameDirectory, @Nullable String screenshotName, RenderTarget buffer, Consumer<C_4996_> messageConsumer) {
      C_3391_ mc = Config.getMinecraft();
      Window mainWindow = mc.aM();
      Options gameSettings = Config.getGameSettings();
      int fbWidth = mainWindow.l();
      int fbHeight = mainWindow.m();
      int guiScaleOld = gameSettings.aq().c();
      int guiScale = mainWindow.a(mc.m.aq().c(), mc.m.P().c());
      int mul = Config.getScreenshotSize();
      boolean resize = GLX.isUsingFBOs() && mul > 1;
      if (resize) {
         gameSettings.aq().a(guiScale * mul);

         try {
            mainWindow.resizeFramebuffer(fbWidth * mul, fbHeight * mul);
         } catch (Exception var19) {
            b.warn("Couldn't save screenshot", var19);
            messageConsumer.accept(C_4996_.m_237110_("screenshot.failure", new Object[]{var19.getMessage()}));
         }

         GlStateManager.clear(16640);
         mc.h().a(true);
         GlStateManager.enableTexture();
         RenderSystem.getModelViewStack().pushMatrix();
         mc.j.a(mc.m_338668_(), true);
         RenderSystem.getModelViewStack().popMatrix();
         RenderSystem.applyModelViewMatrix();
      }

      NativeImage nativeimage = a(buffer);
      if (resize) {
         mc.h().e();
         Config.getGameSettings().aq().a(guiScaleOld);
         mainWindow.resizeFramebuffer(fbWidth, fbHeight);
      }

      File file1 = new File(gameDirectory, "screenshots");
      file1.mkdir();
      File file2;
      if (screenshotName == null) {
         file2 = a(file1);
      } else {
         file2 = new File(file1, screenshotName);
      }

      Object event = null;
      if (Reflector.ForgeHooksClient_onScreenshot.exists()) {
         event = Reflector.call(Reflector.ForgeHooksClient_onScreenshot, nativeimage, file2);
         if (Reflector.callBoolean(event, Reflector.Event_isCanceled)) {
            C_4996_ msg = (C_4996_)Reflector.call(event, Reflector.ScreenshotEvent_getCancelMessage);
            messageConsumer.accept(msg);
            return;
         }

         file2 = (File)Reflector.call(event, Reflector.ScreenshotEvent_getScreenshotFile);
      }

      File target = file2;
      Object eventF = event;
      Util.h()
         .execute(
            () -> {
               try {
                  nativeimage.a(target);
                  C_4996_ component = C_4996_.m_237113_(target.getName())
                     .m_130940_(C_4856_.UNDERLINE)
                     .m_130938_(styleIn -> styleIn.m_131142_(new C_4993_(C_4994_.OPEN_FILE, target.getAbsolutePath())));
                  if (eventF != null && Reflector.call(eventF, Reflector.ScreenshotEvent_getResultMessage) != null) {
                     messageConsumer.accept((C_4996_)Reflector.call(eventF, Reflector.ScreenshotEvent_getResultMessage));
                  } else {
                     messageConsumer.accept(C_4996_.m_237110_("screenshot.success", new Object[]{component}));
                  }
               } catch (Exception var8x) {
                  b.warn("Couldn't save screenshot", var8x);
                  messageConsumer.accept(C_4996_.m_237110_("screenshot.failure", new Object[]{var8x.getMessage()}));
               } finally {
                  nativeimage.close();
               }
            }
         );
   }

   public static NativeImage a(RenderTarget framebufferIn) {
      if (!GLX.isUsingFBOs()) {
         NativeImage nativeimage = new NativeImage(framebufferIn.c, framebufferIn.d, false);
         nativeimage.downloadFromFramebuffer();
         nativeimage.h();
         return nativeimage;
      } else {
         int i = framebufferIn.c;
         int j = framebufferIn.d;
         NativeImage nativeimage = new NativeImage(i, j, false);
         RenderSystem.bindTexture(framebufferIn.f());
         nativeimage.a(0, true);
         nativeimage.h();
         return nativeimage;
      }
   }

   private static File a(File gameDirectory) {
      String s = Util.f();
      int i = 1;

      while (true) {
         File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");
         if (!file1.exists()) {
            return file1;
         }

         i++;
      }
   }

   public Screenshot(File fileIn, int widthIn, int heightIn, int rowHeightIn) throws IOException {
      this.f = widthIn;
      this.g = heightIn;
      this.c = rowHeightIn;
      File file1 = new File(fileIn, "screenshots");
      file1.mkdir();
      String s = "huge_" + Util.f();
      int i = 1;

      while ((this.h = new File(file1, s + (i == 1 ? "" : "_" + i) + ".tga")).exists()) {
         i++;
      }

      byte[] abyte = new byte[18];
      abyte[2] = 2;
      abyte[12] = (byte)(widthIn % 256);
      abyte[13] = (byte)(widthIn / 256);
      abyte[14] = (byte)(heightIn % 256);
      abyte[15] = (byte)(heightIn / 256);
      abyte[16] = 24;
      this.e = new byte[widthIn * rowHeightIn * 3];
      this.d = new DataOutputStream(new FileOutputStream(this.h));
      this.d.write(abyte);
   }

   public void a(ByteBuffer bufferIn, int xIn, int yIn, int widthIn, int heightIn) {
      int i = widthIn;
      int j = heightIn;
      if (widthIn > this.f - xIn) {
         i = this.f - xIn;
      }

      if (heightIn > this.g - yIn) {
         j = this.g - yIn;
      }

      this.c = j;

      for (int k = 0; k < j; k++) {
         bufferIn.position((heightIn - j) * widthIn * 3 + k * widthIn * 3);
         int l = (xIn + k * this.f) * 3;
         bufferIn.get(this.e, l, i * 3);
      }
   }

   public void a() throws IOException {
      this.d.write(this.e, 0, this.f * 3 * this.c);
   }

   public File b() throws IOException {
      this.d.close();
      return this.h;
   }
}
