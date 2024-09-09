package net.minecraft.src;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class C_4518_ {
   public static final C_4520_ f_119011_ = new C_4520_();
   public static final String f_174858_ = "animation";
   public static final int f_174859_ = 1;
   public static final int f_174860_ = -1;
   public static final C_4518_ f_119012_ = new C_4518_(Lists.newArrayList(), -1, -1, 1, false) {
      public C_243504_ m_245821_(int widthIn, int heightIn) {
         return new C_243504_(widthIn, heightIn);
      }
   };
   private final List f_119013_;
   private int f_119014_;
   private int f_119015_;
   private final int f_119016_;
   private final boolean f_119017_;

   public C_4518_(List animationFramesIn, int frameWidthIn, int frameHeightIn, int frameTimeIn, boolean interpolateIn) {
      this.f_119013_ = animationFramesIn;
      this.f_119014_ = frameWidthIn;
      this.f_119015_ = frameHeightIn;
      this.f_119016_ = frameTimeIn;
      this.f_119017_ = interpolateIn;
   }

   public C_243504_ m_245821_(int widthIn, int heightIn) {
      if (this.f_119014_ != -1) {
         return this.f_119015_ != -1 ? new C_243504_(this.f_119014_, this.f_119015_) : new C_243504_(this.f_119014_, heightIn);
      } else if (this.f_119015_ != -1) {
         return new C_243504_(widthIn, this.f_119015_);
      } else {
         int i = Math.min(widthIn, heightIn);
         return new C_243504_(i, i);
      }
   }

   public int m_119030_() {
      return this.f_119016_;
   }

   public boolean m_119036_() {
      return this.f_119017_;
   }

   public void m_174861_(C_141760_ frameOutputIn) {
      Iterator var2 = this.f_119013_.iterator();

      while(var2.hasNext()) {
         C_4517_ animationframe = (C_4517_)var2.next();
         frameOutputIn.m_174863_(animationframe.m_119010_(), animationframe.m_174856_(this.f_119016_));
      }

   }

   public List getAnimationFrames() {
      return this.f_119013_;
   }

   public int getFrameCount() {
      return this.f_119013_.size();
   }

   public void setFrameWidth(int frameWidth) {
      this.f_119014_ = frameWidth;
   }

   public void setFrameHeight(int frameHeight) {
      this.f_119015_ = frameHeight;
   }

   @FunctionalInterface
   public interface C_141760_ {
      void m_174863_(int var1, int var2);
   }
}
