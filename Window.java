import com.mojang.blaze3d.platform.GLX;
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
import net.minecraft.src.C_181894_;
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_279509_;
import net.minecraft.src.C_3109_;
import net.minecraft.src.C_3140_;
import net.minecraft.src.C_3158_;
import net.minecraft.src.C_3160_;
import net.minecraft.src.C_3165_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3793_;
import net.minecraft.src.C_50_;
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

public final class Window implements AutoCloseable {
   private static final Logger c = LogUtils.getLogger();
   public static final int a = 320;
   public static final int b = 240;
   private final GLFWErrorCallback d = GLFWErrorCallback.create(this::a);
   private final C_3165_ e;
   private final C_3158_ f;
   private final long g;
   private int h;
   private int i;
   private int j;
   private int k;
   private Optional<C_3160_> l;
   private boolean m;
   private boolean n;
   private int o;
   private int p;
   private int q;
   private int r;
   private int s;
   private int t;
   private int u;
   private int v;
   private double w;
   private String x = "";
   private boolean y;
   private int z;
   private boolean A;
   private boolean closed;

   public Window(C_3165_ p_i85371_1_, C_3158_ p_i85371_2_, C_3109_ p_i85371_3_, @Nullable String videoModeName, String titleIn) {
      this.f = p_i85371_2_;
      this.v();
      this.a("Pre startup");
      this.e = p_i85371_1_;
      Optional<C_3160_> optional = C_3160_.m_85333_(videoModeName);
      if (optional.isPresent()) {
         this.l = optional;
      } else if (p_i85371_3_.f_84007_.isPresent() && p_i85371_3_.f_84008_.isPresent()) {
         this.l = Optional.of(new C_3160_(p_i85371_3_.f_84007_.getAsInt(), p_i85371_3_.f_84008_.getAsInt(), 8, 8, 8, 60));
      } else {
         this.l = Optional.empty();
      }

      this.n = this.m = p_i85371_3_.f_84009_;
      Monitor monitor = p_i85371_2_.a(GLFW.glfwGetPrimaryMonitor());
      this.j = this.q = p_i85371_3_.f_84005_ > 0 ? p_i85371_3_.f_84005_ : 1;
      this.k = this.r = p_i85371_3_.f_84006_ > 0 ? p_i85371_3_.f_84006_ : 1;
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
               (IntSupplier)() -> this.q, (IntSupplier)() -> this.r, (Supplier)() -> titleIn, (LongSupplier)() -> this.m && monitor != null ? monitor.f() : 0L
            );
         if (Config.isAntialiasing()) {
            GLFW.glfwDestroyWindow(handleForge);
            handleForge = 0L;
         }
      }

      if (handleForge != 0L) {
         this.g = handleForge;
      } else {
         this.g = GLFW.glfwCreateWindow(this.q, this.r, titleIn, this.m && monitor != null ? monitor.f() : 0L, 0L);
      }

      if (handleForge == 0L
         || !Reflector.ImmediateWindowHandler_positionWindow
            .callBoolean(
               Optional.ofNullable(monitor),
               (IntConsumer)w -> this.q = this.j = w,
               (IntConsumer)h -> this.r = this.k = h,
               (IntConsumer)x -> this.o = this.h = x,
               (IntConsumer)y -> this.p = this.i = y
            )) {
         if (monitor != null) {
            C_3160_ videomode = monitor.a(this.m ? this.l : Optional.empty());
            this.h = this.o = monitor.c() + videomode.m_85332_() / 2 - this.q / 2;
            this.i = this.p = monitor.d() + videomode.m_85335_() / 2 - this.r / 2;
         } else {
            int[] aint1 = new int[1];
            int[] aint = new int[1];
            GLFW.glfwGetWindowPos(this.g, aint1, aint);
            this.h = this.o = aint1[0];
            this.i = this.p = aint[0];
         }
      }

      GLFW.glfwMakeContextCurrent(this.g);
      GL.createCapabilities();
      int i = RenderSystem.maxSupportedTextureSize();
      GLFW.glfwSetWindowSizeLimits(this.g, -1, -1, i, i);
      this.x();
      this.w();
      GLFW.glfwSetFramebufferSizeCallback(this.g, this::b);
      GLFW.glfwSetWindowPosCallback(this.g, this::a);
      GLFW.glfwSetWindowSizeCallback(this.g, this::c);
      GLFW.glfwSetWindowFocusCallback(this.g, this::a);
      GLFW.glfwSetCursorEnterCallback(this.g, this::b);
   }

   public static String a() {
      int i = GLFW.glfwGetPlatform();

      return switch (i) {
         case 0 -> "<error>";
         case 393217 -> "win32";
         case 393218 -> "cocoa";
         case 393219 -> "wayland";
         case 393220 -> "x11";
         case 393221 -> "null";
         default -> String.format(Locale.ROOT, "unknown (%08X)", i);
      };
   }

   public int b() {
      RenderSystem.assertOnRenderThread();
      return GLX._getRefreshRate(this);
   }

   public boolean c() {
      return GLX._shouldClose(this);
   }

   public static void a(BiConsumer<Integer, String> errorHandlerIn) {
      MemoryStack memorystack = MemoryStack.stackPush();

      try {
         PointerBuffer pointerbuffer = memorystack.mallocPointer(1);
         int i = GLFW.glfwGetError(pointerbuffer);
         if (i != 0) {
            long j = pointerbuffer.get();
            String s = j == 0L ? "" : MemoryUtil.memUTF8(j);
            errorHandlerIn.accept(i, s);
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

   public void a(C_50_ resourcesIn, C_279509_ iconSetIn) throws IOException {
      int i = GLFW.glfwGetPlatform();
      switch (i) {
         case 393217:
         case 393220:
            List<C_243587_<InputStream>> list = iconSetIn.m_280284_(resourcesIn);
            List<ByteBuffer> list1 = new ArrayList(list.size());

            try {
               MemoryStack memorystack = MemoryStack.stackPush();

               try {
                  Buffer buffer = GLFWImage.malloc(list.size(), memorystack);

                  for (int j = 0; j < list.size(); j++) {
                     try (NativeImage nativeimage = NativeImage.a((InputStream)((C_243587_)list.get(j)).m_247737_())) {
                        ByteBuffer bytebuffer = MemoryUtil.memAlloc(nativeimage.a() * nativeimage.b() * 4);
                        list1.add(bytebuffer);
                        bytebuffer.asIntBuffer().put(nativeimage.d());
                        buffer.position(j);
                        buffer.width(nativeimage.a());
                        buffer.height(nativeimage.b());
                        buffer.pixels(bytebuffer);
                     }
                  }

                  GLFW.glfwSetWindowIcon(this.g, (Buffer)buffer.position(0));
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
            C_181894_.m_247671_(iconSetIn.m_280095_(resourcesIn));
         case 393219:
         case 393221:
            break;
         default:
            c.warn("Not setting icon for unrecognized platform: {}", i);
      }
   }

   public void a(String renderPhaseIn) {
      this.x = renderPhaseIn;
      if (renderPhaseIn.equals("Startup")) {
         TextureUtils.registerTickableTextures();
      }

      if (renderPhaseIn.equals("Render")) {
         GpuFrameTimer.startRender();
      }
   }

   private void v() {
      GLFW.glfwSetErrorCallback(Window::b);
   }

   private static void b(int error, long description) {
      String s = "GLFW error " + error + ": " + MemoryUtil.memUTF8(description);
      TinyFileDialogs.tinyfd_messageBox(
         "Minecraft", s + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false
      );
      throw new Window.a(s);
   }

   public void a(int error, long description) {
      RenderSystem.assertOnRenderThread();
      String s = MemoryUtil.memUTF8(description);
      c.error("########## GL ERROR ##########");
      c.error("@ {}", this.x);
      c.error("{}: {}", error, s);
   }

   public void d() {
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback(this.d);
      if (glfwerrorcallback != null) {
         glfwerrorcallback.free();
      }

      TextureUtils.registerResourceListener();
   }

   public void a(boolean vsyncEnabled) {
      RenderSystem.assertOnRenderThreadOrInit();
      this.A = vsyncEnabled;
      GLFW.glfwSwapInterval(vsyncEnabled ? 1 : 0);
   }

   public void close() {
      RenderSystem.assertOnRenderThread();
      this.closed = true;
      Callbacks.glfwFreeCallbacks(this.g);
      this.d.close();
      GLFW.glfwDestroyWindow(this.g);
      GLFW.glfwTerminate();
   }

   private void a(long windowPointer, int windowXIn, int windowYIn) {
      this.o = windowXIn;
      this.p = windowYIn;
   }

   private void b(long windowPointer, int framebufferWidth, int framebufferHeight) {
      if (windowPointer == this.g) {
         int i = this.l();
         int j = this.m();
         if (framebufferWidth != 0 && framebufferHeight != 0) {
            this.s = framebufferWidth;
            this.t = framebufferHeight;
            if (this.l() != i || this.m() != j) {
               this.e.m_5741_();
            }
         }
      }
   }

   private void w() {
      int[] aint = new int[1];
      int[] aint1 = new int[1];
      GLFW.glfwGetFramebufferSize(this.g, aint, aint1);
      this.s = aint[0] > 0 ? aint[0] : 1;
      this.t = aint1[0] > 0 ? aint1[0] : 1;
      if (this.t == 0 || this.s == 0) {
         Reflector.ImmediateWindowHandler_updateFBSize.call((IntConsumer)w -> this.s = w, (IntConsumer)h -> this.t = h);
      }
   }

   private void c(long windowPointer, int windowWidthIn, int windowHeightIn) {
      this.q = windowWidthIn;
      this.r = windowHeightIn;
   }

   private void a(long windowPointer, boolean hasFocus) {
      if (windowPointer == this.g) {
         this.e.m_7440_(hasFocus);
      }
   }

   private void b(long windowPointer, boolean enterIn) {
      if (enterIn) {
         this.e.m_5740_();
      }
   }

   public void a(int limitIn) {
      this.z = limitIn;
   }

   public int e() {
      if (C_3391_.m_91087_().m.N().c()) {
         return 260;
      } else {
         return this.z <= 0 ? 260 : this.z;
      }
   }

   public void f() {
      GpuFrameTimer.finishRender();
      RenderSystem.flipFrame(this.g);
      if (this.m != this.n) {
         this.n = this.m;
         this.c(this.A);
      }
   }

   public Optional<C_3160_> g() {
      return this.l;
   }

   public void a(Optional<C_3160_> fullscreenModeIn) {
      boolean flag = !fullscreenModeIn.equals(this.l);
      this.l = fullscreenModeIn;
      if (flag) {
         this.y = true;
      }
   }

   public void h() {
      if (this.m && this.y) {
         this.y = false;
         this.x();
         this.e.m_5741_();
      }
   }

   private void x() {
      boolean flag = GLFW.glfwGetWindowMonitor(this.g) != 0L;
      if (this.m) {
         Monitor monitor = this.f.a(this);
         if (monitor == null) {
            c.warn("Failed to find suitable monitor for fullscreen mode");
            this.m = false;
         } else {
            if (C_3391_.f_91002_) {
               C_181894_.m_182517_(this.g);
            }

            C_3160_ videomode = monitor.a(this.l);
            if (!flag) {
               this.h = this.o;
               this.i = this.p;
               this.j = this.q;
               this.k = this.r;
            }

            this.o = 0;
            this.p = 0;
            this.q = videomode.m_85332_();
            this.r = videomode.m_85335_();
            GLFW.glfwSetWindowMonitor(this.g, monitor.f(), this.o, this.p, this.q, this.r, videomode.m_85341_());
            if (C_3391_.f_91002_) {
               C_181894_.m_305469_(this.g);
            }
         }
      } else {
         this.o = this.h;
         this.p = this.i;
         this.q = this.j;
         this.r = this.k;
         GLFW.glfwSetWindowMonitor(this.g, 0L, this.o, this.p, this.q, this.r, -1);
      }
   }

   public void i() {
      this.m = !this.m;
   }

   public void a(int widthIn, int heightIn) {
      this.j = widthIn;
      this.k = heightIn;
      this.m = false;
      this.x();
   }

   private void c(boolean vsyncEnabled) {
      RenderSystem.assertOnRenderThread();

      try {
         this.x();
         this.e.m_5741_();
         this.a(vsyncEnabled);
         this.f();
      } catch (Exception var3) {
         c.error("Couldn't toggle fullscreen", var3);
      }
   }

   public int a(int guiScaleIn, boolean forceUnicode) {
      int i = 1;

      while (i != guiScaleIn && i < this.s && i < this.t && this.s / (i + 1) >= 320 && this.t / (i + 1) >= 240) {
         i++;
      }

      if (forceUnicode && i % 2 != 0) {
         i++;
      }

      return i;
   }

   public void a(double scaleFactor) {
      this.w = scaleFactor;
      int i = (int)((double)this.s / scaleFactor);
      this.u = (double)this.s / scaleFactor > (double)i ? i + 1 : i;
      int j = (int)((double)this.t / scaleFactor);
      this.v = (double)this.t / scaleFactor > (double)j ? j + 1 : j;
   }

   public void b(String titleIn) {
      GLFW.glfwSetWindowTitle(this.g, titleIn);
   }

   public long j() {
      return this.g;
   }

   public boolean k() {
      return this.m;
   }

   public int l() {
      return this.s;
   }

   public int m() {
      return this.t;
   }

   public void b(int widthIn) {
      this.s = widthIn;
   }

   public void c(int heightIn) {
      this.t = heightIn;
   }

   public int n() {
      return this.q;
   }

   public int o() {
      return this.r;
   }

   public int p() {
      return this.u;
   }

   public int q() {
      return this.v;
   }

   public int r() {
      return this.o;
   }

   public int s() {
      return this.p;
   }

   public double t() {
      return this.w;
   }

   @Nullable
   public Monitor u() {
      return this.f.a(this);
   }

   public void b(boolean valueIn) {
      C_3140_.m_84848_(this.g, valueIn);
   }

   public void resizeFramebuffer(int width, int height) {
      this.b(this.g, width, height);
   }

   public boolean isClosed() {
      return this.closed;
   }

   public static class a extends C_3793_ {
      a(String messageIn) {
         super(messageIn);
      }
   }
}
