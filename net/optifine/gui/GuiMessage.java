package net.optifine.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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
   private final List listLines2 = Lists.newArrayList();
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

   protected void actionPerformed(AbstractWidget button) {
      Config.getMinecraft().m_91152_(this.parentScreen);
   }

   public void m_88315_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      drawCenteredString(graphicsIn, this.fontRenderer, this.messageLine1, this.f_96543_ / 2, 70, 16777215);
      int var4 = 90;

      for(Iterator var5 = this.listLines2.iterator(); var5.hasNext(); var4 += 9) {
         FormattedCharSequence line = (FormattedCharSequence)var5.next();
         drawCenteredString(graphicsIn, this.fontRenderer, line, this.f_96543_ / 2, var4, 16777215);
         Objects.requireNonNull(this.fontRenderer);
      }

   }

   public void setButtonDelay(int ticksUntilEnable) {
      this.ticksUntilEnable = ticksUntilEnable;

      Button var3;
      for(Iterator var2 = this.getButtonList().iterator(); var2.hasNext(); var3.f_93623_ = false) {
         var3 = (Button)var2.next();
      }

   }

   public void m_86600_() {
      super.m_86600_();
      Button var2;
      if (--this.ticksUntilEnable == 0) {
         for(Iterator var1 = this.getButtonList().iterator(); var1.hasNext(); var2.f_93623_ = true) {
            var2 = (Button)var1.next();
         }
      }

   }
}
