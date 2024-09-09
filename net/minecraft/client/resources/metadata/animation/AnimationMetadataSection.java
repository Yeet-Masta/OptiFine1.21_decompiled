package net.minecraft.client.resources.metadata.animation;

import com.google.common.collect.Lists;
import java.util.List;

public class AnimationMetadataSection {
   public static final AnimationMetadataSectionSerializer f_119011_ = new AnimationMetadataSectionSerializer();
   public static final String f_174858_ = "animation";
   public static final int f_174859_ = 1;
   public static final int f_174860_ = -1;
   public static final net.minecraft.client.resources.metadata.animation.AnimationMetadataSection f_119012_ = new net.minecraft.client.resources.metadata.animation.AnimationMetadataSection(
      Lists.newArrayList(), -1, -1, 1, false
   ) {
      @Override
      public FrameSize m_245821_(int widthIn, int heightIn) {
         return new FrameSize(widthIn, heightIn);
      }
   };
   private final List<AnimationFrame> f_119013_;
   private int f_119014_;
   private int f_119015_;
   private final int f_119016_;
   private final boolean f_119017_;

   public AnimationMetadataSection(List<AnimationFrame> animationFramesIn, int frameWidthIn, int frameHeightIn, int frameTimeIn, boolean interpolateIn) {
      this.f_119013_ = animationFramesIn;
      this.f_119014_ = frameWidthIn;
      this.f_119015_ = frameHeightIn;
      this.f_119016_ = frameTimeIn;
      this.f_119017_ = interpolateIn;
   }

   public FrameSize m_245821_(int widthIn, int heightIn) {
      if (this.f_119014_ != -1) {
         return this.f_119015_ != -1 ? new FrameSize(this.f_119014_, this.f_119015_) : new FrameSize(this.f_119014_, heightIn);
      } else if (this.f_119015_ != -1) {
         return new FrameSize(widthIn, this.f_119015_);
      } else {
         int i = Math.min(widthIn, heightIn);
         return new FrameSize(i, i);
      }
   }

   public int m_119030_() {
      return this.f_119016_;
   }

   public boolean m_119036_() {
      return this.f_119017_;
   }

   public void m_174861_(net.minecraft.client.resources.metadata.animation.AnimationMetadataSection.FrameOutput frameOutputIn) {
      for (AnimationFrame animationframe : this.f_119013_) {
         frameOutputIn.m_174863_(animationframe.m_119010_(), animationframe.m_174856_(this.f_119016_));
      }
   }

   public List<AnimationFrame> getAnimationFrames() {
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
   public interface FrameOutput {
      void m_174863_(int var1, int var2);
   }
}
