package net.optifine.gui;

import net.minecraft.src.C_213334_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.Lang;
import net.optifine.config.Option;

public class GuiAnimationSettingsOF extends GuiScreenOF {
   private C_3583_ prevScreen;
   private C_3401_ settings;

   public GuiAnimationSettingsOF(C_3583_ guiscreen, C_3401_ gamesettings) {
      super(C_4996_.m_237115_("of.options.animationsTitle"));
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   public void aT_() {
      this.p();
      C_213334_[] options = new C_213334_[]{
         Option.ANIMATED_WATER,
         Option.ANIMATED_LAVA,
         Option.ANIMATED_FIRE,
         Option.ANIMATED_PORTAL,
         Option.ANIMATED_REDSTONE,
         Option.ANIMATED_EXPLOSION,
         Option.ANIMATED_FLAME,
         Option.ANIMATED_SMOKE,
         Option.VOID_PARTICLES,
         Option.WATER_PARTICLES,
         Option.RAIN_SPLASH,
         Option.PORTAL_PARTICLES,
         Option.POTION_PARTICLES,
         Option.DRIPPING_WATER_LAVA,
         Option.ANIMATED_TERRAIN,
         Option.ANIMATED_TEXTURES,
         Option.FIREWORK_PARTICLES,
         this.settings.PARTICLES
      };

      for (int i = 0; i < options.length; i++) {
         C_213334_ opt = options[i];
         int x = this.m / 2 - 155 + i % 2 * 160;
         int y = this.n / 6 + 21 * (i / 2) - 12;
         C_3449_ guielement = (C_3449_)this.c(opt.m_231507_(this.l.f_91066_, x, y, 150));
         guielement.m_257544_(null);
      }

      this.c(new GuiButtonOF(210, this.m / 2 - 155, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
      this.c(new GuiButtonOF(211, this.m / 2 - 155 + 80, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
      this.c(new GuiScreenButtonOF(200, this.m / 2 + 5, this.n / 6 + 168 + 11, C_4513_.m_118938_("gui.done", new Object[0])));
   }

   protected void actionPerformed(C_3449_ guiElement) {
      if (guiElement instanceof GuiButtonOF guibutton) {
         if (guibutton.j) {
            if (guibutton.id == 200) {
               this.l.f_91066_.m_92169_();
               this.l.m_91152_(this.prevScreen);
            }

            if (guibutton.id == 210) {
               this.l.f_91066_.setAllAnimations(true);
            }

            if (guibutton.id == 211) {
               this.l.f_91066_.setAllAnimations(false);
            }

            this.l.m_5741_();
         }
      }
   }

   public void j() {
      this.l.f_91066_.m_92169_();
      super.j();
   }

   public void a(C_279497_ graphicsIn, int x, int y, float partialTicks) {
      super.a(graphicsIn, x, y, partialTicks);
      drawCenteredString(graphicsIn, this.l.f_91062_, this.k, this.m / 2, 15, 16777215);
   }
}
