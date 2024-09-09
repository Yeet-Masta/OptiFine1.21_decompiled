package net.optifine.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.config.Option;

public class GuiOtherSettingsOF extends GuiScreenOF {
   private Screen prevScreen;
   private net.minecraft.client.Options settings;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiOtherSettingsOF(Screen guiscreen, net.minecraft.client.Options gamesettings) {
      super(Component.m_237113_(I18n.m_118938_("of.options.otherTitle", new Object[0])));
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   public void m_7856_() {
      this.m_169413_();
      net.minecraft.client.OptionInstance fullscreenResolution = OptionFullscreenResolution.make();
      net.minecraft.client.OptionInstance[] options = new net.minecraft.client.OptionInstance[]{
         Option.LAGOMETER,
         Option.PROFILER,
         this.settings.ATTACK_INDICATOR,
         Option.ADVANCED_TOOLTIPS,
         Option.WEATHER,
         Option.TIME,
         this.settings.FULLSCREEN,
         Option.AUTOSAVE_TICKS,
         Option.SCREENSHOT_SIZE,
         Option.SHOW_GL_ERRORS,
         Option.TELEMETRY,
         null,
         fullscreenResolution,
         null
      };

      for (int i = 0; i < options.length; i++) {
         net.minecraft.client.OptionInstance option = options[i];
         if (option != null) {
            int x = this.f_96543_ / 2 - 155 + i % 2 * 160;
            int y = this.f_96544_ / 6 + 21 * (i / 2) - 12;
            AbstractWidget guielement = (AbstractWidget)this.m_142416_(option.m_231507_(this.f_96541_.f_91066_, x, y, 150));
            guielement.m_257544_(null);
            if (option == fullscreenResolution) {
               guielement.m_93674_(310);
            }
         }
      }

      this.m_142416_(new GuiButtonOF(210, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168 + 11 - 44, I18n.m_118938_("of.options.other.reset", new Object[0])));
      this.m_142416_(new GuiButtonOF(200, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168 + 11, I18n.m_118938_("gui.done", new Object[0])));
   }

   @Override
   protected void actionPerformed(AbstractWidget guiElement) {
      if (guiElement instanceof GuiButtonOF guibutton) {
         if (guibutton.f_93623_) {
            if (guibutton.id == 200) {
               this.f_96541_.f_91066_.m_92169_();
               this.f_96541_.m_91268_().m_85437_();
               this.f_96541_.m_91152_(this.prevScreen);
            }

            if (guibutton.id == 210) {
               this.f_96541_.f_91066_.m_92169_();
               String msg = I18n.m_118938_("of.message.other.reset", new Object[0]);
               ConfirmScreen guiyesno = new ConfirmScreen(this::confirmResult, Component.m_237113_(msg), Component.m_237113_(""));
               this.f_96541_.m_91152_(guiyesno);
            }
         }
      }
   }

   public void m_7861_() {
      this.f_96541_.f_91066_.m_92169_();
      this.f_96541_.m_91268_().m_85437_();
      super.m_7861_();
   }

   public void confirmResult(boolean flag) {
      if (flag) {
         this.f_96541_.f_91066_.resetSettings();
      }

      this.f_96541_.m_91152_(this);
   }

   public void m_88315_(net.minecraft.client.gui.GuiGraphics graphicsIn, int x, int y, float partialTicks) {
      super.m_88315_(graphicsIn, x, y, partialTicks);
      drawCenteredString(graphicsIn, this.fontRenderer, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      this.tooltipManager.drawTooltips(graphicsIn, x, y, this.getButtonList());
   }
}
