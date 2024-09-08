package net.optifine.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.Config;

public class GuiMessage extends GuiScreenOF {
   private C_3583_ parentScreen;
   private C_4996_ messageLine1;
   private C_4996_ messageLine2;
   private final List<C_178_> listLines2 = Lists.newArrayList();
   protected String confirmButtonText;
   private int ticksUntilEnable;

   public GuiMessage(C_3583_ parentScreen, String line1, String line2) {
      super(C_4996_.m_237115_("of.options.detailsTitle"));
      this.parentScreen = parentScreen;
      this.messageLine1 = C_4996_.m_237113_(line1);
      this.messageLine2 = C_4996_.m_237113_(line2);
      this.confirmButtonText = C_4513_.m_118938_("gui.done", new Object[0]);
   }

   public void m_7856_() {
      this.m_142416_(new GuiButtonOF(0, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 96, this.confirmButtonText));
      this.listLines2.clear();
      this.listLines2.addAll(this.f_96541_.f_91062_.m_92923_(this.messageLine2, this.f_96543_ - 50));
   }

   protected void actionPerformed(C_3449_ button) {
      Config.getMinecraft().m_91152_(this.parentScreen);
   }

   public void m_88315_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      drawCenteredString(graphicsIn, this.fontRenderer, this.messageLine1, this.f_96543_ / 2, 70, 16777215);
      int var4x = 90;

      for (C_178_ line : this.listLines2) {
         drawCenteredString(graphicsIn, this.fontRenderer, line, this.f_96543_ / 2, var4x, 16777215);
         var4x += 9;
      }
   }

   public void setButtonDelay(int ticksUntilEnable) {
      this.ticksUntilEnable = ticksUntilEnable;

      for (C_3451_ var3 : this.getButtonList()) {
         var3.j = false;
      }
   }

   public void m_86600_() {
      super.m_86600_();
      if (--this.ticksUntilEnable == 0) {
         for (C_3451_ var2 : this.getButtonList()) {
            var2.j = true;
         }
      }
   }
}
