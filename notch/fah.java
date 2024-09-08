package net.minecraft.src;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.optifine.util.VideoModeComparator;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class C_3146_ {
   private final long f_84936_;
   private final List<C_3160_> f_84937_;
   private C_3160_ f_84938_;
   private int f_84939_;
   private int f_84940_;

   public C_3146_(long pointerIn) {
      this.f_84936_ = pointerIn;
      this.f_84937_ = Lists.newArrayList();
      this.m_84943_();
   }

   public void m_84943_() {
      this.f_84937_.clear();
      Buffer buffer = GLFW.glfwGetVideoModes(this.f_84936_);
      GLFWVidMode glCurrentMode = GLFW.glfwGetVideoMode(this.f_84936_);
      C_3160_ currentMode = new C_3160_(glCurrentMode);
      List<C_3160_> removedModes = new ArrayList();

      for (int i = buffer.limit() - 1; i >= 0; i--) {
         buffer.position(i);
         C_3160_ videomode = new C_3160_(buffer);
         if (videomode.m_85336_() >= 8 && videomode.m_85337_() >= 8 && videomode.m_85338_() >= 8) {
            if (videomode.m_85341_() < currentMode.m_85341_()) {
               removedModes.add(videomode);
            } else {
               this.f_84937_.add(videomode);
            }
         }
      }

      removedModes.sort(new VideoModeComparator().reversed());

      for (C_3160_ vm : removedModes) {
         if (getVideoMode(this.f_84937_, vm.m_85332_(), vm.m_85335_()) == null) {
            this.f_84937_.add(vm);
         }
      }

      this.f_84937_.sort(new VideoModeComparator());
      int[] aint = new int[1];
      int[] aint1 = new int[1];
      GLFW.glfwGetMonitorPos(this.f_84936_, aint, aint1);
      this.f_84939_ = aint[0];
      this.f_84940_ = aint1[0];
      GLFWVidMode glfwvidmode = GLFW.glfwGetVideoMode(this.f_84936_);
      this.f_84938_ = new C_3160_(glfwvidmode);
   }

   public C_3160_ m_84948_(Optional<C_3160_> optionalVideoMode) {
      if (optionalVideoMode.isPresent()) {
         C_3160_ videomode = (C_3160_)optionalVideoMode.get();

         for (C_3160_ videomode1 : this.f_84937_) {
            if (videomode1.equals(videomode)) {
               return videomode1;
            }
         }
      }

      return this.m_84950_();
   }

   public int m_84946_(C_3160_ modeIn) {
      return this.f_84937_.indexOf(modeIn);
   }

   public C_3160_ m_84950_() {
      return this.f_84938_;
   }

   public int m_84951_() {
      return this.f_84939_;
   }

   public int m_84952_() {
      return this.f_84940_;
   }

   public C_3160_ m_84944_(int index) {
      return (C_3160_)this.f_84937_.get(index);
   }

   public int m_84953_() {
      return this.f_84937_.size();
   }

   public long m_84954_() {
      return this.f_84936_;
   }

   public String toString() {
      return String.format(Locale.ROOT, "Monitor[%s %sx%s %s]", this.f_84936_, this.f_84939_, this.f_84940_, this.f_84938_);
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
