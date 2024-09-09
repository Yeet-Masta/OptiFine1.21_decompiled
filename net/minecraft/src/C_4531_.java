package net.minecraft.src;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.optifine.render.RenderUtils;
import net.optifine.util.TextureUtils;

public class C_4531_ {
   public static final Comparator f_244523_ = Comparator.comparing(C_4531_::m_119193_).thenComparing(C_4531_::m_119203_);
   private final C_5265_ f_119187_;
   private final C_5265_ f_119188_;
   @Nullable
   private C_4168_ f_119189_;

   public C_4531_(C_5265_ atlasLocationIn, C_5265_ textureLocationIn) {
      this.f_119187_ = atlasLocationIn;
      this.f_119188_ = textureLocationIn;
   }

   public C_5265_ m_119193_() {
      return this.f_119187_;
   }

   public C_5265_ m_119203_() {
      return this.f_119188_;
   }

   public C_4486_ m_119204_() {
      C_4486_ sprite = (C_4486_)C_3391_.m_91087_().m_91258_(this.m_119193_()).apply(this.m_119203_());
      sprite = TextureUtils.getCustomSprite(sprite);
      return sprite;
   }

   public C_4168_ m_119201_(Function renderTypeGetter) {
      if (this.f_119189_ == null) {
         this.f_119189_ = (C_4168_)renderTypeGetter.apply(this.f_119187_);
      }

      return this.f_119189_;
   }

   public C_3187_ m_119194_(C_4139_ bufferIn, Function renderTypeGetter) {
      C_4486_ sprite = this.m_119204_();
      C_4168_ renderType = this.m_119201_(renderTypeGetter);
      if (sprite.isSpriteEmissive && renderType.isEntitySolid()) {
         RenderUtils.flushRenderBuffers();
         renderType = C_4168_.m_110452_(this.f_119187_);
      }

      return sprite.m_118381_(bufferIn.m_6299_(renderType));
   }

   public C_3187_ m_119197_(C_4139_ bufferIn, Function renderTypeGetter, boolean hasEffectIn) {
      return this.m_119204_().m_118381_(C_4354_.m_115222_(bufferIn, this.m_119201_(renderTypeGetter), true, hasEffectIn));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         C_4531_ material = (C_4531_)p_equals_1_;
         return this.f_119187_.equals(material.f_119187_) && this.f_119188_.equals(material.f_119188_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.f_119187_, this.f_119188_});
   }

   public String toString() {
      String var10000 = String.valueOf(this.f_119187_);
      return "Material{atlasLocation=" + var10000 + ", texture=" + String.valueOf(this.f_119188_) + "}";
   }
}
