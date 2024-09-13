package net.optifine.gui;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.Lang;
import net.optifine.config.Option;

public class GuiQuickInfoOF extends GuiScreenOF {
   private Screen prevScreen;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiQuickInfoOF(Screen guiscreen) {
      super(Component.m_237115_("of.options.quickInfoTitle"));
      this.prevScreen = guiscreen;
   }

   public void m_7856_() {
      this.m_169413_();
      OptionInstance[] options = new OptionInstance[]{
         Option.QUICK_INFO,
         Option.QUICK_INFO_FPS,
         Option.QUICK_INFO_CHUNKS,
         Option.QUICK_INFO_ENTITIES,
         Option.QUICK_INFO_PARTICLES,
         Option.QUICK_INFO_UPDATES,
         Option.QUICK_INFO_GPU,
         Option.QUICK_INFO_POS,
         Option.QUICK_INFO_BIOME,
         Option.QUICK_INFO_FACING,
         Option.QUICK_INFO_LIGHT,
         Option.QUICK_INFO_MEMORY,
         Option.QUICK_INFO_NATIVE_MEMORY,
         Option.QUICK_INFO_TARGET_BLOCK,
         Option.QUICK_INFO_TARGET_FLUID,
         Option.QUICK_INFO_TARGET_ENTITY,
         Option.QUICK_INFO_LABELS,
         Option.QUICK_INFO_BACKGROUND
      };

      for (int i = 0; i < options.length; i++) {
         OptionInstance opt = options[i];
         if (opt != null) {
            int x = this.f_96543_ / 2 - 155 + i % 2 * 160;
            int y = this.f_96544_ / 6 + 21 * (i / 2) - 12;
            AbstractWidget guielement = (AbstractWidget)this.m_142416_(opt.m_231507_(this.f_96541_.f_91066_, x, y, 150));
            guielement.m_257544_(null);
         }
      }

      this.m_142416_(new GuiButtonOF(210, this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
      this.m_142416_(new GuiButtonOF(211, this.f_96543_ / 2 - 155 + 80, this.f_96544_ / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
      this.m_142416_(new GuiScreenButtonOF(200, this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 168 + 11, I18n.m_118938_("gui.done", new Object[0])));
      this.updateSubOptions();
   }

   @Override
   protected void actionPerformed(AbstractWidget guiElement) {
      this.updateSubOptions();
      if (guiElement instanceof GuiButtonOF guibutton) {
         if (guibutton.f_93623_) {
            if (guibutton.f_11893_ == 200) {
               this.f_96541_.f_91066_.m_92169_();
               this.f_96541_.m_91152_(this.prevScreen);
            }

            if (guibutton.f_11893_ == 210) {
               this.f_96541_.f_91066_.setAllQuickInfos(true);
            }

            if (guibutton.f_11893_ == 211) {
               this.f_96541_.f_91066_.setAllQuickInfos(false);
            }

            this.f_96541_.m_5741_();
         }
      }
   }

   private void updateSubOptions() {
      boolean enabled = this.settings.ofQuickInfo;

      for (AbstractWidget aw : this.getButtonList()) {
         if (aw instanceof IOptionControl) {
            IOptionControl oc = (IOptionControl)aw;
            if (oc.getControlOption() != Option.QUICK_INFO) {
               aw.f_93623_ = enabled;
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
      drawCenteredString(graphicsIn, this.f_96541_.f_91062_, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      this.tooltipManager.drawTooltips(graphicsIn, x, y, this.getButtonList());
   }
}
