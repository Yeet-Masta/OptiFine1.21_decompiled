package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

class GlStateManager$C_3116_ {
   private final int f_84585_;
   private boolean f_84586_;

   public GlStateManager$C_3116_(int capabilityIn) {
      this.f_84585_ = capabilityIn;
   }

   public void m_84589_() {
      this.m_84590_(false);
   }

   public void m_84592_() {
      this.m_84590_(true);
   }

   public void m_84590_(boolean enabled) {
      RenderSystem.assertOnRenderThreadOrInit();
      if (enabled != this.f_84586_) {
         this.f_84586_ = enabled;
         if (enabled) {
            GL11.glEnable(this.f_84585_);
         } else {
            GL11.glDisable(this.f_84585_);
         }
      }
   }
}
