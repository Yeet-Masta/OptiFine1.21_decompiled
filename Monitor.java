import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.src.C_3160_;
import net.optifine.util.VideoModeComparator;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class Monitor {
   private final long a;
   private final List<C_3160_> b;
   private C_3160_ c;
   private int d;
   private int e;

   public Monitor(long pointerIn) {
      this.a = pointerIn;
      this.b = Lists.newArrayList();
      this.a();
   }

   public void a() {
      this.b.clear();
      Buffer buffer = GLFW.glfwGetVideoModes(this.a);
      GLFWVidMode glCurrentMode = GLFW.glfwGetVideoMode(this.a);
      C_3160_ currentMode = new C_3160_(glCurrentMode);
      List<C_3160_> removedModes = new ArrayList();

      for (int i = buffer.limit() - 1; i >= 0; i--) {
         buffer.position(i);
         C_3160_ videomode = new C_3160_(buffer);
         if (videomode.m_85336_() >= 8 && videomode.m_85337_() >= 8 && videomode.m_85338_() >= 8) {
            if (videomode.m_85341_() < currentMode.m_85341_()) {
               removedModes.add(videomode);
            } else {
               this.b.add(videomode);
            }
         }
      }

      removedModes.sort(new VideoModeComparator().reversed());

      for (C_3160_ vm : removedModes) {
         if (getVideoMode(this.b, vm.m_85332_(), vm.m_85335_()) == null) {
            this.b.add(vm);
         }
      }

      this.b.sort(new VideoModeComparator());
      int[] aint = new int[1];
      int[] aint1 = new int[1];
      GLFW.glfwGetMonitorPos(this.a, aint, aint1);
      this.d = aint[0];
      this.e = aint1[0];
      GLFWVidMode glfwvidmode = GLFW.glfwGetVideoMode(this.a);
      this.c = new C_3160_(glfwvidmode);
   }

   public C_3160_ a(Optional<C_3160_> optionalVideoMode) {
      if (optionalVideoMode.isPresent()) {
         C_3160_ videomode = (C_3160_)optionalVideoMode.get();

         for (C_3160_ videomode1 : this.b) {
            if (videomode1.equals(videomode)) {
               return videomode1;
            }
         }
      }

      return this.b();
   }

   public int a(C_3160_ modeIn) {
      return this.b.indexOf(modeIn);
   }

   public C_3160_ b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public int d() {
      return this.e;
   }

   public C_3160_ a(int index) {
      return (C_3160_)this.b.get(index);
   }

   public int e() {
      return this.b.size();
   }

   public long f() {
      return this.a;
   }

   public String toString() {
      return String.format(Locale.ROOT, "Monitor[%s %sx%s %s]", this.a, this.d, this.e, this.c);
   }

   public static C_3160_ getVideoMode(List<C_3160_> list, int width, int height) {
      for (C_3160_ vm : list) {
         if (vm.m_85332_() == width && vm.m_85335_() == height) {
            return vm;
         }
      }

      return null;
   }
}
