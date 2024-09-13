package net.optifine.gui;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.config.Option;

public class GuiQualitySettingsOF extends GuiScreenOF {
   private Screen prevScreen;
   private Options settings;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiQualitySettingsOF(Screen guiscreen, Options gamesettings) {
      super(Component.m_237113_(I18n.m_118938_("of.options.qualityTitle", new Object[0])));
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   public void m_7856_() {
      this.m_169413_();
      OptionInstance[] options = new OptionInstance[]{
         this.settings.MIPMAP_LEVELS,
         Option.MIPMAP_TYPE,
         Option.AF_LEVEL,
         Option.AA_LEVEL,
         Option.EMISSIVE_TEXTURES,
         Option.RANDOM_ENTITIES,
         Option.BETTER_GRASS,
         Option.BETTER_SNOW,
         Option.CUSTOM_FONTS,
         Option.CUSTOM_COLORS,
         Option.CONNECTED_TEXTURES,
         Option.NATURAL_TEXTURES,
         Option.CUSTOM_SKY,
         Option.CUSTOM_ITEMS,
         Option.CUSTOM_ENTITY_MODELS,
         Option.CUSTOM_GUIS,
         this.settings.SCREEN_EFFECT_SCALE,
         this.settings.FOV_EFFECT_SCALE
      };

      for (int i = 0; i < options.length; i++) {
         OptionInstance opt = options[i];
         int x = this.f_96543_ / 2 - 155 + i % 2 * 160;
         int y = this.f_96544_ / 6 + 21 * (i / 2) - 12;
         AbstractWidget guielement = (AbstractWidget)this.m_142416_(opt.m_231507_(this.f_96541_.f_91066_, x, y, 150));
         guielement.m_257544_(null);
      }

      this.m_142416_(new GuiButtonOF(200, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168 + 11, I18n.m_118938_("gui.done", new Object[0])));
   }

   @Override
   protected void actionPerformed(AbstractWidget guiElement) {
      if (guiElement instanceof GuiButtonOF guibutton) {
         if (guibutton.f_93623_) {
            if (guibutton.f_11893_ == 200) {
               this.f_96541_.f_91066_.m_92169_();
               this.f_96541_.m_91152_(this.prevScreen);
            }
         }
      }
   }

   public void m_7861_() {
      this.f_96541_.f_91066_.m_92169_();
      super.m_7861_();
   }

   public void m_88315_(GuiGraphics graphicsIn, int x, int y, float partialTicks) {
      super.m_88315_(graphicsIn, x, y, partialTicks);
      drawCenteredString(graphicsIn, this.fontRenderer, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      this.tooltipManager.drawTooltips(graphicsIn, x, y, this.getButtonList());
   }
}
