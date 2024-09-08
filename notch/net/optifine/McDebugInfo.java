package net.optifine;

import net.minecraft.src.C_3391_;

public class McDebugInfo {
   private C_3391_ minecraft = C_3391_.m_91087_();
   private String mcDebug = this.minecraft.f_90977_;

   public boolean isChanged() {
      if (this.mcDebug == this.minecraft.f_90977_) {
         return false;
      } else {
         this.mcDebug = this.minecraft.f_90977_;
         return true;
      }
   }
}
