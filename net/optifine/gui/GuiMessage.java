package net.optifine.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.optifine.Config;

public class GuiMessage extends GuiScreenOF {
   private Screen parentScreen;
   private Component messageLine1;
   private Component messageLine2;
   private List<FormattedCharSequence> listLines2 = Lists.newArrayList();
   protected String confirmButtonText;
   private int ticksUntilEnable;

   public GuiMessage(Screen parentScreen, String line1, String line2) {
      super(Component.m_237115_("of.options.detailsTitle"));
      this.parentScreen = parentScreen;
      this.messageLine1 = Component.m_237113_(line1);
      this.messageLine2 = Component.m_237113_(line2);
      this.confirmButtonText = I18n.m_118938_("gui.done", new Object[0]);
   }

   public void m_7856_() {
      this.m_142416_(new GuiButtonOF(0, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 96, this.confirmButtonText));
      this.listLines2.clear();
      this.listLines2.addAll(this.f_96541_.f_91062_.m_92923_(this.messageLine2, this.f_96543_ - 50));
   }

   @Override
   protected void actionPerformed(AbstractWidget button) {
      Config.getMinecraft().m_91152_(this.parentScreen);
   }

   public void m_88315_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      drawCenteredString(graphicsIn, this.fontRenderer, this.messageLine1, this.f_96543_ / 2, 70, 16777215);
      int var4x = 90;

      for (FormattedCharSequence line : this.listLines2) {
         drawCenteredString(graphicsIn, this.fontRenderer, line, this.f_96543_ / 2, var4x, 16777215);
         var4x += 9;
      }
   }

   public void setButtonDelay(int ticksUntilEnable) {
      this.ticksUntilEnable = ticksUntilEnable;

      for (Button var3 : this.getButtonList()) {
         var3.f_93623_ = false;
      }
   }

   public void m_86600_() {
      super.m_86600_();
      if (--this.ticksUntilEnable == 0) {
         for (Button var2 : this.getButtonList()) {
            var2.f_93623_ = true;
         }
      }
   }
}
