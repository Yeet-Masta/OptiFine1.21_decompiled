package net.optifine.gui;

import net.minecraft.src.C_213334_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.config.Option;

public class GuiDetailSettingsOF extends GuiScreenOF {
   private C_3583_ prevScreen;
   private C_3401_ settings;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiDetailSettingsOF(C_3583_ guiscreen, C_3401_ gamesettings) {
      super(C_4996_.m_237113_(C_4513_.m_118938_("of.options.detailsTitle", new Object[0])));
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   public void aT_() {
      this.p();
      C_213334_[] options = new C_213334_[]{
         Option.CLOUDS,
         Option.CLOUD_HEIGHT,
         Option.TREES,
         Option.RAIN,
         Option.SKY,
         Option.STARS,
         Option.SUN_MOON,
         Option.SHOW_CAPES,
         Option.FOG_FANCY,
         Option.FOG_START,
         this.settings.VIEW_BOBBING,
         Option.HELD_ITEM_TOOLTIPS,
         this.settings.AUTOSAVE_INDICATOR,
         Option.SWAMP_COLORS,
         Option.VIGNETTE,
         Option.ALTERNATE_BLOCKS,
         this.settings.ENTITY_DISTANCE_SCALING,
         this.settings.BIOME_BLEND_RADIUS
      };

      for (int i = 0; i < options.length; i++) {
         C_213334_ opt = options[i];
         int x = this.m / 2 - 155 + i % 2 * 160;
         int y = this.n / 6 + 21 * (i / 2) - 12;
         C_3449_ guielement = (C_3449_)this.c(opt.m_231507_(this.l.f_91066_, x, y, 150));
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
      drawCenteredString(graphicsIn, this.l.f_91062_, this.k, this.m / 2, 15, 16777215);
      this.tooltipManager.drawTooltips(graphicsIn, x, y, this.getButtonList());
   }
}
