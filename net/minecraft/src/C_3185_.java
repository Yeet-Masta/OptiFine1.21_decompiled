package net.minecraft.src;

import javax.annotation.Nullable;

public class C_3185_ {
   private static final int f_302531_ = 786432;
   private final C_336589_ f_337014_;
   @Nullable
   private static C_3185_ f_302797_;

   public static void m_306948_() {
      if (f_302797_ != null) {
         throw new IllegalStateException("Tesselator has already been initialized");
      } else {
         f_302797_ = new C_3185_();
      }
   }

   public static C_3185_ m_85913_() {
      if (f_302797_ == null) {
         throw new IllegalStateException("Tesselator has not been initialized");
      } else {
         return f_302797_;
      }
   }

   public C_3185_(int bufferSize) {
      this.f_337014_ = new C_336589_(bufferSize);
   }

   public C_3185_() {
      this(786432);
   }

   public C_3173_ m_339075_(C_3188_.C_141549_ modeIn, C_3188_ formatIn) {
      return new C_3173_(this.f_337014_, modeIn, formatIn);
   }

   public void m_339098_() {
      this.f_337014_.m_340278_();
   }

   public void draw(C_3173_ bufferIn) {
      C_3177_.m_231202_(bufferIn.m_339905_());
   }
}
