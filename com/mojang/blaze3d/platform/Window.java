package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.GpuFrameTimer;
import net.optifine.util.TextureUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;

public class Window implements AutoCloseable {
   private static Logger f_85345_ = LogUtils.getLogger();
   public static int f_337695_;
   public static int f_336756_;
   private GLFWErrorCallback f_85346_ = GLFWErrorCallback.m_294843_(this::m_85382_);
   private WindowEventHandler f_85347_;
   private ScreenManager f_85348_;
   private long f_85349_;
   private int f_85350_;
   private int f_85351_;
   private int f_85352_;
   private int f_85353_;
   private Optional<VideoMode> f_85354_;
   private boolean f_85355_;
   private boolean f_85356_;
   private int f_85357_;
   private int f_85358_;
   private int f_85359_;
   private int f_85360_;
   private int f_85361_;
   private int f_85362_;
   private int f_85363_;
   private int f_85364_;
   private double f_85365_;
   private String f_85366_ = "";
   private boolean f_85367_;
   private int f_85368_;
   private boolean f_85369_;
   private boolean closed;

   public Window(WindowEventHandler p_i85371_1_, ScreenManager p_i85371_2_, DisplayData p_i85371_3_, @Nullable String videoModeName, String titleIn) {
      this.f_85348_ = p_i85371_2_;
      this.m_85451_();
      this.m_85403_("Pre startup");
      this.f_85347_ = p_i85371_1_;
      Optional<VideoMode> optional = VideoMode.m_85333_(videoModeName);
      if (optional.isPresent()) {
         this.f_85354_ = optional;
      } else if (p_i85371_3_.f_84007_.isPresent() && p_i85371_3_.f_84008_.isPresent()) {
         this.f_85354_ = Optional.m_253057_(new VideoMode(p_i85371_3_.f_84007_.getAsInt(), p_i85371_3_.f_84008_.getAsInt(), 8, 8, 8, 60));
      } else {
         this.f_85354_ = Optional.m_274566_();
      }

      this.f_85356_ = this.f_85355_ = p_i85371_3_.f_84009_;
      Monitor monitor = p_i85371_2_.m_85271_(GLFW.glfwGetPrimaryMonitor());
      this.f_85352_ = this.f_85359_ = p_i85371_3_.f_84005_ > 0 ? p_i85371_3_.f_84005_ : 1;
      this.f_85353_ = this.f_85360_ = p_i85371_3_.f_84006_ > 0 ? p_i85371_3_.f_84006_ : 1;
      GLFW.glfwDefaultWindowHints();
      if (Config.isAntialiasing()) {
         GLFW.glfwWindowHint(135181, Config.getAntialiasingLevel());
      }

      GLFW.glfwWindowHint(139265, 196609);
      GLFW.glfwWindowHint(139275, 221185);
      GLFW.glfwWindowHint(139266, 3);
      GLFW.glfwWindowHint(139267, 2);
      GLFW.glfwWindowHint(139272, 204801);
      GLFW.glfwWindowHint(139270, 1);
      long handleForge = 0L;
      if (Reflector.ImmediateWindowHandler_setupMinecraftWindow.exists()) {
         handleForge = Reflector.ImmediateWindowHandler_setupMinecraftWindow
            .callLong(
               (IntSupplier)() -> this.f_85359_,
               (IntSupplier)() -> this.f_85360_,
               (Supplier)() -> titleIn,
               (LongSupplier)() -> this.f_85355_ && monitor != null ? monitor.m_84954_() : 0L
            );
         if (Config.isAntialiasing()) {
            GLFW.glfwDestroyWindow(handleForge);
            handleForge = 0L;
         }
      }

      if (handleForge != 0L) {
         this.f_85349_ = handleForge;
      } else {
         this.f_85349_ = GLFW.glfwCreateWindow(this.f_85359_, this.f_85360_, titleIn, this.f_85355_ && monitor != null ? monitor.m_84954_() : 0L, 0L);
      }

      if (handleForge == 0L
         || !Reflector.ImmediateWindowHandler_positionWindow
            .callBoolean(
               Optional.ofNullable(monitor),
               (IntConsumer)w -> this.f_85359_ = this.f_85352_ = w,
               (IntConsumer)h -> this.f_85360_ = this.f_85353_ = h,
               (IntConsumer)x -> this.f_85357_ = this.f_85350_ = x,
               (IntConsumer)y -> this.f_85358_ = this.f_85351_ = y
            )) {
         if (monitor != null) {
            VideoMode videomode = monitor.m_84948_(this.f_85355_ ? this.f_85354_ : Optional.m_274566_());
            this.f_85350_ = this.f_85357_ = monitor.m_84951_() + videomode.m_85332_() / 2 - this.f_85359_ / 2;
            this.f_85351_ = this.f_85358_ = monitor.m_84952_() + videomode.m_85335_() / 2 - this.f_85360_ / 2;
         } else {
            int[] aint1 = new int[1];
            int[] aint = new int[1];
            GLFW.glfwGetWindowPos(this.f_85349_, aint1, aint);
            this.f_85350_ = this.f_85357_ = aint1[0];
            this.f_85351_ = this.f_85358_ = aint[0];
         }
      }

      GLFW.glfwMakeContextCurrent(this.f_85349_);
      GL.createCapabilities();
      int i = RenderSystem.maxSupportedTextureSize();
      GLFW.glfwSetWindowSizeLimits(this.f_85349_, -1, -1, i, i);
      this.m_85453_();
      this.m_85452_();
      GLFW.glfwSetFramebufferSizeCallback(this.f_85349_, this::m_85415_);
      GLFW.glfwSetWindowPosCallback(this.f_85349_, this::m_85388_);
      GLFW.glfwSetWindowSizeCallback(this.f_85349_, this::m_85427_);
      GLFW.glfwSetWindowFocusCallback(this.f_85349_, this::m_85392_);
      GLFW.glfwSetCursorEnterCallback(this.f_85349_, this::m_85419_);
   }

   public static String m_340634_() {
      int i = GLFW.glfwGetPlatform();

      return switch (i) {
         case 0 -> "<error>";
         case 393217 -> "win32";
         case 393218 -> "cocoa";
         case 393219 -> "wayland";
         case 393220 -> "x11";
         case 393221 -> "null";
         default -> String.m_12886_(Locale.ROOT, "unknown (%08X)", new Object[]{i});
      };
   }

   public int m_85377_() {
      RenderSystem.assertOnRenderThread();
      return GLX._getRefreshRate(this);
   }

   public boolean m_85411_() {
      return GLX._shouldClose(this);
   }

   public static void m_85407_(BiConsumer<Integer, String> errorHandlerIn) {
      MemoryStack memorystack = MemoryStack.stackPush();

      try {
         PointerBuffer pointerbuffer = memorystack.mallocPointer(1);
         int i = GLFW.glfwGetError(pointerbuffer);
         if (i != 0) {
            long j = pointerbuffer.get();
            String s = j == 0L ? "" : MemoryUtil.memUTF8(j);
            errorHandlerIn.m_340568_(i, s);
         }
      } catch (Throwable var8) {
         if (memorystack != null) {
            try {
               memorystack.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (memorystack != null) {
         memorystack.close();
      }
   }

   public void m_280655_(PackResources resourcesIn, IconSet iconSetIn) throws IOException {
      int i = GLFW.glfwGetPlatform();
      switch (i) {
         case 393217:
         case 393220:
            List<IoSupplier<InputStream>> list = iconSetIn.m_280284_(resourcesIn);
            List<ByteBuffer> list1 = new ArrayList(list.size());

            try {
               MemoryStack memorystack = MemoryStack.stackPush();

               try {
                  Buffer buffer = GLFWImage.malloc(list.size(), memorystack);

                  for (int j = 0; j < list.size(); j++) {
                     try (NativeImage nativeimage = NativeImage.m_85058_((InputStream)((IoSupplier)list.get(j)).m_247737_())) {
                        ByteBuffer bytebuffer = MemoryUtil.memAlloc(nativeimage.m_84982_() * nativeimage.m_85084_() * 4);
                        list1.add(bytebuffer);
                        bytebuffer.asIntBuffer().put(nativeimage.m_266370_());
                        buffer.position(j);
                        buffer.width(nativeimage.m_84982_());
                        buffer.height(nativeimage.m_85084_());
                        buffer.pixels(bytebuffer);
                     }
                  }

                  GLFW.glfwSetWindowIcon(this.f_85349_, (Buffer)buffer.position(0));
               } catch (Throwable var21) {
                  if (memorystack != null) {
                     try {
                        memorystack.close();
                     } catch (Throwable var18) {
                        var21.addSuppressed(var18);
                     }
                  }

                  throw var21;
               }

               if (memorystack != null) {
                  memorystack.close();
               }
               break;
            } finally {
               list1.forEach(MemoryUtil::memFree);
            }
         case 393218:
            MacosUtil.m_247671_(iconSetIn.m_280095_(resourcesIn));
         case 393219:
         case 393221:
            break;
         default:
            f_85345_.warn("Not setting icon for unrecognized platform: {}", i);
      }
   }

   public void m_85403_(String renderPhaseIn) {
      this.f_85366_ = renderPhaseIn;
      if (renderPhaseIn.equals("Startup")) {
         TextureUtils.registerTickableTextures();
      }

      if (renderPhaseIn.equals("Render")) {
         GpuFrameTimer.startRender();
      }
   }

   private void m_85451_() {
      GLFW.glfwSetErrorCallback(Window::m_85412_);
   }

   private static void m_85412_(int error, long description) {
      String s = "GLFW error " + error + ": " + MemoryUtil.memUTF8(description);
      TinyFileDialogs.tinyfd_messageBox(
         "Minecraft", s + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false
      );
      throw new Window.WindowInitFailed(s);
   }

   public void m_85382_(int error, long description) {
      RenderSystem.assertOnRenderThread();
      String s = MemoryUtil.memUTF8(description);
      f_85345_.error("########## GL ERROR ##########");
      f_85345_.error("@ {}", this.f_85366_);
      f_85345_.error("{}: {}", error, s);
   }

   public void m_85426_() {
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback(this.f_85346_);
      if (glfwerrorcallback != null) {
         glfwerrorcallback.free();
      }

      TextureUtils.registerResourceListener();
   }

   public void m_85409_(boolean vsyncEnabled) {
      RenderSystem.assertOnRenderThreadOrInit();
      this.f_85369_ = vsyncEnabled;
      GLFW.glfwSwapInterval(vsyncEnabled ? 1 : 0);
   }

   public void close() {
      RenderSystem.assertOnRenderThread();
      this.closed = true;
      Callbacks.glfwFreeCallbacks(this.f_85349_);
      this.f_85346_.close();
      GLFW.glfwDestroyWindow(this.f_85349_);
      GLFW.glfwTerminate();
   }

   private void m_85388_(long windowPointer, int windowXIn, int windowYIn) {
      this.f_85357_ = windowXIn;
      this.f_85358_ = windowYIn;
   }

   private void m_85415_(long windowPointer, int framebufferWidth, int framebufferHeight) {
      if (windowPointer == this.f_85349_) {
         int i = this.m_85441_();
         int j = this.m_85442_();
         if (framebufferWidth != 0 && framebufferHeight != 0) {
            this.f_85361_ = framebufferWidth;
            this.f_85362_ = framebufferHeight;
            if (this.m_85441_() != i || this.m_85442_() != j) {
               this.f_85347_.m_5741_();
            }
         }
      }
   }

   private void m_85452_() {
      int[] aint = new int[1];
      int[] aint1 = new int[1];
      GLFW.glfwGetFramebufferSize(this.f_85349_, aint, aint1);
      this.f_85361_ = aint[0] > 0 ? aint[0] : 1;
      this.f_85362_ = aint1[0] > 0 ? aint1[0] : 1;
      if (this.f_85362_ == 0 || this.f_85361_ == 0) {
         Reflector.ImmediateWindowHandler_updateFBSize.m_46374_((IntConsumer)w -> this.f_85361_ = w, (IntConsumer)h -> this.f_85362_ = h);
      }
   }

   private void m_85427_(long windowPointer, int windowWidthIn, int windowHeightIn) {
      this.f_85359_ = windowWidthIn;
      this.f_85360_ = windowHeightIn;
   }

   private void m_85392_(long windowPointer, boolean hasFocus) {
      if (windowPointer == this.f_85349_) {
         this.f_85347_.m_7440_(hasFocus);
      }
   }

   private void m_85419_(long windowPointer, boolean enterIn) {
      if (enterIn) {
         this.f_85347_.m_5740_();
      }
   }

   public void m_85380_(int limitIn) {
      this.f_85368_ = limitIn;
   }

   public int m_85434_() {
      if (Minecraft.m_91087_().f_91066_.m_231817_().m_231551_()) {
         return 260;
      } else {
         return this.f_85368_ <= 0 ? 260 : this.f_85368_;
      }
   }

   public void m_85435_() {
      GpuFrameTimer.finishRender();
      RenderSystem.flipFrame(this.f_85349_);
      if (this.f_85355_ != this.f_85356_) {
         this.f_85356_ = this.f_85355_;
         this.m_85431_(this.f_85369_);
      }
   }

   public Optional<VideoMode> m_85436_() {
      return this.f_85354_;
   }

   public void m_85405_(Optional<VideoMode> fullscreenModeIn) {
      boolean flag = !fullscreenModeIn.equals(this.f_85354_);
      this.f_85354_ = fullscreenModeIn;
      if (flag) {
         this.f_85367_ = true;
      }
   }

   public void m_85437_() {
      if (this.f_85355_ && this.f_85367_) {
         this.f_85367_ = false;
         this.m_85453_();
         this.f_85347_.m_5741_();
      }
   }

   private void m_85453_() {
      boolean flag = GLFW.glfwGetWindowMonitor(this.f_85349_) != 0L;
      if (this.f_85355_) {
         Monitor monitor = this.f_85348_.m_85276_(this);
         if (monitor == null) {
            f_85345_.warn("Failed to find suitable monitor for fullscreen mode");
            this.f_85355_ = false;
         } else {
            if (Minecraft.f_91002_) {
               MacosUtil.m_182517_(this.f_85349_);
            }

            VideoMode videomode = monitor.m_84948_(this.f_85354_);
            if (!flag) {
               this.f_85350_ = this.f_85357_;
               this.f_85351_ = this.f_85358_;
               this.f_85352_ = this.f_85359_;
               this.f_85353_ = this.f_85360_;
            }

            this.f_85357_ = 0;
            this.f_85358_ = 0;
            this.f_85359_ = videomode.m_85332_();
            this.f_85360_ = videomode.m_85335_();
            GLFW.glfwSetWindowMonitor(this.f_85349_, monitor.m_84954_(), this.f_85357_, this.f_85358_, this.f_85359_, this.f_85360_, videomode.m_85341_());
            if (Minecraft.f_91002_) {
               MacosUtil.m_305469_(this.f_85349_);
            }
         }
      } else {
         this.f_85357_ = this.f_85350_;
         this.f_85358_ = this.f_85351_;
         this.f_85359_ = this.f_85352_;
         this.f_85360_ = this.f_85353_;
         GLFW.glfwSetWindowMonitor(this.f_85349_, 0L, this.f_85357_, this.f_85358_, this.f_85359_, this.f_85360_, -1);
      }
   }

   public void m_85438_() {
      this.f_85355_ = !this.f_85355_;
   }

   public void m_166447_(int widthIn, int heightIn) {
      this.f_85352_ = widthIn;
      this.f_85353_ = heightIn;
      this.f_85355_ = false;
      this.m_85453_();
   }

   private void m_85431_(boolean vsyncEnabled) {
      RenderSystem.assertOnRenderThread();

      try {
         this.m_85453_();
         this.f_85347_.m_5741_();
         this.m_85409_(vsyncEnabled);
         this.m_85435_();
      } catch (Exception var3) {
         f_85345_.error("Couldn't toggle fullscreen", var3);
      }
   }

   public int m_85385_(int guiScaleIn, boolean forceUnicode) {
      int i = 1;

      while (i != guiScaleIn && i < this.f_85361_ && i < this.f_85362_ && this.f_85361_ / (i + 1) >= 320 && this.f_85362_ / (i + 1) >= 240) {
         i++;
      }

      if (forceUnicode && i % 2 != 0) {
         i++;
      }

      return i;
   }

   public void m_85378_(double scaleFactor) {
      this.f_85365_ = scaleFactor;
      int i = (int)((double)this.f_85361_ / scaleFactor);
      this.f_85363_ = (double)this.f_85361_ / scaleFactor > (double)i ? i + 1 : i;
      int j = (int)((double)this.f_85362_ / scaleFactor);
      this.f_85364_ = (double)this.f_85362_ / scaleFactor > (double)j ? j + 1 : j;
   }

   public void m_85422_(String titleIn) {
      GLFW.glfwSetWindowTitle(this.f_85349_, titleIn);
   }

   public long m_85439_() {
      return this.f_85349_;
   }

   public boolean m_85440_() {
      return this.f_85355_;
   }

   public int m_85441_() {
      return this.f_85361_;
   }

   public int m_85442_() {
      return this.f_85362_;
   }

   public void m_166450_(int widthIn) {
      this.f_85361_ = widthIn;
   }

   public void m_166452_(int heightIn) {
      this.f_85362_ = heightIn;
   }

   public int m_85443_() {
      return this.f_85359_;
   }

   public int m_85444_() {
      return this.f_85360_;
   }

   public int m_85445_() {
      return this.f_85363_;
   }

   public int m_85446_() {
      return this.f_85364_;
   }

   public int m_85447_() {
      return this.f_85357_;
   }

   public int m_85448_() {
      return this.f_85358_;
   }

   public double m_85449_() {
      return this.f_85365_;
   }

   @Nullable
   public Monitor m_85450_() {
      return this.f_85348_.m_85276_(this);
   }

   public void m_85424_(boolean valueIn) {
      InputConstants.m_84848_(this.f_85349_, valueIn);
   }

   public void resizeFramebuffer(int width, int height) {
      this.m_85415_(this.f_85349_, width, height);
   }

   public boolean isClosed() {
      return this.closed;
   }

   public static class WindowInitFailed extends SilentInitException {
      WindowInitFailed(String messageIn) {
         super(messageIn);
      }
   }
}
