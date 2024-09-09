package net.minecraft.client.gui.screens;

import javax.annotation.Nullable;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ProgressListener;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class ProgressScreen extends Screen implements ProgressListener {
   @Nullable
   private Component f_96506_;
   @Nullable
   private Component f_96507_;
   private int f_96508_;
   private boolean f_96509_;
   private final boolean f_169362_;
   private CustomLoadingScreen customLoadingScreen;

   public ProgressScreen(boolean clearIn) {
      super(GameNarrator.f_93310_);
      this.f_169362_ = clearIn;
      this.customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();
   }

   public boolean m_6913_() {
      return false;
   }

   protected boolean m_264396_() {
      return false;
   }

   public void m_6309_(Component component) {
      this.m_6308_(component);
   }

   public void m_6308_(Component component) {
      this.f_96506_ = component;
      this.m_6307_(Component.m_237115_("menu.working"));
   }

   public void m_6307_(Component component) {
      this.f_96507_ = component;
      this.m_6952_(0);
   }

   public void m_6952_(int progress) {
      this.f_96508_ = progress;
   }

   public void m_7730_() {
      this.f_96509_ = true;
   }

   public void m_88315_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.f_96509_) {
         if (this.f_169362_) {
            this.f_96541_.m_91152_((Screen)null);
         }
      } else {
         super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
         if (this.f_96508_ > 0) {
            if (this.f_96506_ != null) {
               graphicsIn.m_280653_(this.f_96547_, this.f_96506_, this.f_96543_ / 2, 70, 16777215);
            }

            if (this.f_96507_ != null && this.f_96508_ != 0) {
               graphicsIn.m_280653_(this.f_96547_, Component.m_237119_().m_7220_(this.f_96507_).m_130946_(" " + this.f_96508_ + "%"), this.f_96543_ / 2, 90, 16777215);
            }
         }
      }

   }

   public void m_280273_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.customLoadingScreen != null && this.f_96541_.f_91073_ == null) {
         this.customLoadingScreen.drawBackground(this.f_96543_, this.f_96544_);
      } else {
         super.m_280273_(graphicsIn, mouseX, mouseY, partialTicks);
      }

   }
}
