import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.src.C_243504_;
import net.minecraft.src.C_4517_;
import net.minecraft.src.C_4520_;

public class AnimationMetadataSection {
   public static final C_4520_ a = new C_4520_();
   public static final String b = "animation";
   public static final int c = 1;
   public static final int d = -1;
   public static final AnimationMetadataSection e = new AnimationMetadataSection(Lists.newArrayList(), -1, -1, 1, false) {
      @Override
      public C_243504_ a(int widthIn, int heightIn) {
         return new C_243504_(widthIn, heightIn);
      }
   };
   private final List<C_4517_> f;
   private int g;
   private int h;
   private final int i;
   private final boolean j;

   public AnimationMetadataSection(List<C_4517_> animationFramesIn, int frameWidthIn, int frameHeightIn, int frameTimeIn, boolean interpolateIn) {
      this.f = animationFramesIn;
      this.g = frameWidthIn;
      this.h = frameHeightIn;
      this.i = frameTimeIn;
      this.j = interpolateIn;
   }

   public C_243504_ a(int widthIn, int heightIn) {
      if (this.g != -1) {
         return this.h != -1 ? new C_243504_(this.g, this.h) : new C_243504_(this.g, heightIn);
      } else if (this.h != -1) {
         return new C_243504_(widthIn, this.h);
      } else {
         int i = Math.min(widthIn, heightIn);
         return new C_243504_(i, i);
      }
   }

   public int a() {
      return this.i;
   }

   public boolean b() {
      return this.j;
   }

   public void a(AnimationMetadataSection.a frameOutputIn) {
      for (C_4517_ animationframe : this.f) {
         frameOutputIn.accept(animationframe.m_119010_(), animationframe.m_174856_(this.i));
      }
   }

   public List<C_4517_> getAnimationFrames() {
      return this.f;
   }

   public int getFrameCount() {
      return this.f.size();
   }

   public void setFrameWidth(int frameWidth) {
      this.g = frameWidth;
   }

   public void setFrameHeight(int frameHeight) {
      this.h = frameHeight;
   }

   @FunctionalInterface
   public interface a {
      void accept(int var1, int var2);
   }
}
