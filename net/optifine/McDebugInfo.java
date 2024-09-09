package net.optifine;

import net.minecraft.client.Minecraft;

public class McDebugInfo {
   private Minecraft minecraft = Minecraft.m_91087_();
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
