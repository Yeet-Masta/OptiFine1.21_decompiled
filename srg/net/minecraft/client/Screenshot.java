package net.minecraft.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ClickEvent.Action;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class Screenshot {
   private static final Logger f_92276_ = LogUtils.getLogger();
   public static final String f_260508_ = "screenshots";
   private int f_168594_;
   private final DataOutputStream f_168595_;
   private final byte[] f_168596_;
   private final int f_168597_;
   private final int f_168598_;
   private File f_168599_;

   public static void m_92289_(File gameDirectory, RenderTarget buffer, Consumer<Component> messageConsumer) {
      m_92295_(gameDirectory, null, buffer, messageConsumer);
   }

   public static void m_92295_(File gameDirectory, @Nullable String screenshotName, RenderTarget buffer, Consumer<Component> messageConsumer) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> m_92305_(gameDirectory, screenshotName, buffer, messageConsumer));
      } else {
         m_92305_(gameDirectory, screenshotName, buffer, messageConsumer);
      }
   }

   private static void m_92305_(File gameDirectory, @Nullable String screenshotName, RenderTarget buffer, Consumer<Component> messageConsumer) {
      Minecraft mc = Config.getMinecraft();
      Window mainWindow = mc.m_91268_();
      Options gameSettings = Config.getGameSettings();
      int fbWidth = mainWindow.m_85441_();
      int fbHeight = mainWindow.m_85442_();
      int guiScaleOld = gameSettings.m_231928_().m_231551_();
      int guiScale = mainWindow.m_85385_(mc.f_91066_.m_231928_().m_231551_(), mc.f_91066_.m_231819_().m_231551_());
      int mul = Config.getScreenshotSize();
      boolean resize = GLX.isUsingFBOs() && mul > 1;
      if (resize) {
         gameSettings.m_231928_().m_231514_(guiScale * mul);

         try {
            mainWindow.resizeFramebuffer(fbWidth * mul, fbHeight * mul);
         } catch (Exception var19) {
            f_92276_.warn("Couldn't save screenshot", var19);
            messageConsumer.accept(Component.m_237110_("screenshot.failure", new Object[]{var19.getMessage()}));
         }

         GlStateManager.clear(16640);
         mc.m_91385_().m_83947_(true);
         GlStateManager.enableTexture();
         RenderSystem.getModelViewStack().pushMatrix();
         mc.f_91063_.m_109093_(mc.m_338668_(), true);
         RenderSystem.getModelViewStack().popMatrix();
         RenderSystem.applyModelViewMatrix();
      }

      NativeImage nativeimage = m_92279_(buffer);
      if (resize) {
         mc.m_91385_().m_83970_();
         Config.getGameSettings().m_231928_().m_231514_(guiScaleOld);
         mainWindow.resizeFramebuffer(fbWidth, fbHeight);
      }

      File file1 = new File(gameDirectory, "screenshots");
      file1.mkdir();
      File file2;
      if (screenshotName == null) {
         file2 = m_92287_(file1);
      } else {
         file2 = new File(file1, screenshotName);
      }

      Object event = null;
      if (Reflector.ForgeHooksClient_onScreenshot.exists()) {
         event = Reflector.call(Reflector.ForgeHooksClient_onScreenshot, nativeimage, file2);
         if (Reflector.callBoolean(event, Reflector.Event_isCanceled)) {
            Component msg = (Component)Reflector.call(event, Reflector.ScreenshotEvent_getCancelMessage);
            messageConsumer.accept(msg);
            return;
         }

         file2 = (File)Reflector.call(event, Reflector.ScreenshotEvent_getScreenshotFile);
      }

      File target = file2;
      Object eventF = event;
      Util.m_183992_()
         .execute(
            () -> {
               try {
                  nativeimage.m_85056_(target);
                  Component component = Component.m_237113_(target.getName())
                     .m_130940_(ChatFormatting.UNDERLINE)
                     .m_130938_(styleIn -> styleIn.m_131142_(new ClickEvent(Action.OPEN_FILE, target.getAbsolutePath())));
                  if (eventF != null && Reflector.call(eventF, Reflector.ScreenshotEvent_getResultMessage) != null) {
                     messageConsumer.accept((Component)Reflector.call(eventF, Reflector.ScreenshotEvent_getResultMessage));
                  } else {
                     messageConsumer.accept(Component.m_237110_("screenshot.success", new Object[]{component}));
                  }
               } catch (Exception var8x) {
                  f_92276_.warn("Couldn't save screenshot", var8x);
                  messageConsumer.accept(Component.m_237110_("screenshot.failure", new Object[]{var8x.getMessage()}));
               } finally {
                  nativeimage.close();
               }
            }
         );
   }

   public static NativeImage m_92279_(RenderTarget framebufferIn) {
      if (!GLX.isUsingFBOs()) {
         NativeImage nativeimage = new NativeImage(framebufferIn.f_83915_, framebufferIn.f_83916_, false);
         nativeimage.downloadFromFramebuffer();
         nativeimage.m_85122_();
         return nativeimage;
      } else {
         int i = framebufferIn.f_83915_;
         int j = framebufferIn.f_83916_;
         NativeImage nativeimage = new NativeImage(i, j, false);
         RenderSystem.bindTexture(framebufferIn.m_83975_());
         nativeimage.m_85045_(0, true);
         nativeimage.m_85122_();
         return nativeimage;
      }
   }

   private static File m_92287_(File gameDirectory) {
      String s = Util.m_241986_();
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
      this.f_168597_ = widthIn;
      this.f_168598_ = heightIn;
      this.f_168594_ = rowHeightIn;
      File file1 = new File(fileIn, "screenshots");
      file1.mkdir();
      String s = "huge_" + Util.m_241986_();
      int i = 1;

      while ((this.f_168599_ = new File(file1, s + (i == 1 ? "" : "_" + i) + ".tga")).exists()) {
         i++;
      }

      byte[] abyte = new byte[18];
      abyte[2] = 2;
      abyte[12] = (byte)(widthIn % 256);
      abyte[13] = (byte)(widthIn / 256);
      abyte[14] = (byte)(heightIn % 256);
      abyte[15] = (byte)(heightIn / 256);
      abyte[16] = 24;
      this.f_168596_ = new byte[widthIn * rowHeightIn * 3];
      this.f_168595_ = new DataOutputStream(new FileOutputStream(this.f_168599_));
      this.f_168595_.write(abyte);
   }

   public void m_168609_(ByteBuffer bufferIn, int xIn, int yIn, int widthIn, int heightIn) {
      int i = widthIn;
      int j = heightIn;
      if (widthIn > this.f_168597_ - xIn) {
         i = this.f_168597_ - xIn;
      }

      if (heightIn > this.f_168598_ - yIn) {
         j = this.f_168598_ - yIn;
      }

      this.f_168594_ = j;

      for (int k = 0; k < j; k++) {
         bufferIn.position((heightIn - j) * widthIn * 3 + k * widthIn * 3);
         int l = (xIn + k * this.f_168597_) * 3;
         bufferIn.get(this.f_168596_, l, i * 3);
      }
   }

   public void m_168605_() throws IOException {
      this.f_168595_.write(this.f_168596_, 0, this.f_168597_ * 3 * this.f_168594_);
   }

   public File m_168615_() throws IOException {
      this.f_168595_.close();
      return this.f_168599_;
   }
}
