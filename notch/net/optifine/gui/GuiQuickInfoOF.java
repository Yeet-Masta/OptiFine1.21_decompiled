package net.optifine.gui;

import net.minecraft.src.C_213334_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.Lang;
import net.optifine.config.Option;

public class GuiQuickInfoOF extends GuiScreenOF {
   private C_3583_ prevScreen;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiQuickInfoOF(C_3583_ guiscreen) {
      super(C_4996_.m_237115_("of.options.quickInfoTitle"));
      this.prevScreen = guiscreen;
   }

   public void aT_() {
      this.p();
      C_213334_[] options = new C_213334_[]{
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
         C_213334_ opt = options[i];
         if (opt != null) {
            int x = this.m / 2 - 155 + i % 2 * 160;
            int y = this.n / 6 + 21 * (i / 2) - 12;
            C_3449_ guielement = (C_3449_)this.c(opt.m_231507_(this.l.f_91066_, x, y, 150));
            guielement.m_257544_(null);
         }
      }

      this.c(new GuiButtonOF(210, this.m / 2 - 155, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
      this.c(new GuiButtonOF(211, this.m / 2 - 155 + 80, this.n / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
      this.c(new GuiScreenButtonOF(200, this.m / 2 + 5, this.n / 6 + 168 + 11, C_4513_.m_118938_("gui.done", new Object[0])));
      this.updateSubOptions();
   }

   protected void actionPerformed(C_3449_ guiElement) {
      this.updateSubOptions();
      if (guiElement instanceof GuiButtonOF guibutton) {
         if (guibutton.j) {
            if (guibutton.id == 200) {
               this.l.f_91066_.m_92169_();
               this.l.m_91152_(this.prevScreen);
            }

            if (guibutton.id == 210) {
               this.l.f_91066_.setAllQuickInfos(true);
            }

            if (guibutton.id == 211) {
               this.l.f_91066_.setAllQuickInfos(false);
            }

            this.l.m_5741_();
         }
      }
   }

   private void updateSubOptions() {
      boolean enabled = this.settings.ofQuickInfo;

      for (C_3449_ aw : this.getButtonList()) {
         if (aw instanceof IOptionControl) {
            IOptionControl oc = (IOptionControl)aw;
            if (oc.getControlOption() != Option.QUICK_INFO) {
               aw.f_93623_ = enabled;
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
