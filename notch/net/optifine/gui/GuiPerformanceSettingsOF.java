package net.optifine.gui;

import net.minecraft.src.C_213334_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.config.Option;

public class GuiPerformanceSettingsOF extends GuiScreenOF {
   private C_3583_ prevScreen;
   private C_3401_ settings;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiPerformanceSettingsOF(C_3583_ guiscreen, C_3401_ gamesettings) {
      super(C_4996_.m_237113_(C_4513_.m_118938_("of.options.performanceTitle", new Object[0])));
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   public void aT_() {
      this.p();
      C_213334_[] options = new C_213334_[]{
         Option.RENDER_REGIONS,
         Option.FAST_RENDER,
         Option.SMART_ANIMATIONS,
         Option.FAST_MATH,
         Option.SMOOTH_FPS,
         Option.SMOOTH_WORLD,
         Option.CHUNK_UPDATES,
         Option.CHUNK_UPDATES_DYNAMIC,
         Option.LAZY_CHUNK_LOADING,
         this.settings.PRIORITIZE_CHUNK_UPDATES
      };

      for (int i = 0; i < options.length; i++) {
         C_213334_ option = options[i];
         int x = this.m / 2 - 155 + i % 2 * 160;
         int y = this.n / 6 + 21 * (i / 2) - 12;
         C_3449_ guielement = (C_3449_)this.c(option.m_231507_(this.l.f_91066_, x, y, 150));
         guielement.m_257544_(null);
      }

      this.c(new GuiButtonOF(200, this.m / 2 - 100, this.n / 6 + 168 + 11, C_4513_.m_118938_("gui.done", new Object[0])));
   }

   protected void actionPerformed(C_3449_ guiElement) {
      if (guiElement instanceof GuiButtonOF guibutton) {
         if (guibutton.j) {
            if (guibutton.id == 200) {
               this.l.f_91066_.m_92169_();
               this.l.m_91152_(this.prevScreen);
            }
         }
      }
   }

   public void j() {
      this.l.f_91066_.m_92169_();
      super.j();
   }

   public void a(C_279497_ graphicsIn, int x, int y, float partialTicks) {
      super.a(graphicsIn, x, y, partialTicks);
      drawCenteredString(graphicsIn, this.fontRenderer, this.k, this.m / 2, 15, 16777215);
      this.tooltipManager.drawTooltips(graphicsIn, x, y, this.getButtonList());
   }
}
