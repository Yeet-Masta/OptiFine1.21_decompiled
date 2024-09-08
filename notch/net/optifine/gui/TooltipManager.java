package net.optifine.gui;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3429_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;

public class TooltipManager {
   private C_3583_ guiScreen;
   private TooltipProvider tooltipProvider;
   private int lastMouseX = 0;
   private int lastMouseY = 0;
   private long mouseStillTime = 0L;

   public TooltipManager(C_3583_ guiScreen, TooltipProvider tooltipProvider) {
      this.guiScreen = guiScreen;
      this.tooltipProvider = tooltipProvider;
   }

   public void drawTooltips(C_279497_ graphicsIn, int x, int y, List<C_3449_> buttonList) {
      if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
         int activateDelay = 700;
         if (System.currentTimeMillis() >= this.mouseStillTime + (long)activateDelay) {
            C_3449_ btn = GuiScreenOF.getSelectedButton(x, y, buttonList);
            if (btn != null) {
               Rectangle rect = this.tooltipProvider.getTooltipBounds(this.guiScreen, x, y);
               String[] lines = this.tooltipProvider.getTooltipLines(btn, rect.width);
               if (lines != null) {
                  if (lines.length > 8) {
                     lines = (String[])Arrays.copyOf(lines, 8);
                     lines[lines.length - 1] = lines[lines.length - 1] + " ...";
                  }

                  graphicsIn.m_280168_().m_85836_();
                  graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, 400.0F);
                  if (this.tooltipProvider.isRenderBorder()) {
                     int colBorder = -528449408;
                     this.drawRectBorder(graphicsIn, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, colBorder);
                  }

                  graphicsIn.m_280509_(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, -536870912);

                  for (int i = 0; i < lines.length; i++) {
                     String line = lines[i];
                     int col = 14540253;
                     if (line.endsWith("!")) {
                        col = 16719904;
                     }

                     C_3429_ fontRenderer = C_3391_.m_91087_().f_91062_;
                     graphicsIn.m_280056_(fontRenderer, line, rect.x + 5, rect.y + 5 + i * 11, col, true);
                  }

                  graphicsIn.m_280168_().m_85849_();
               }
            }
         }
      } else {
         this.lastMouseX = x;
         this.lastMouseY = y;
         this.mouseStillTime = System.currentTimeMillis();
      }
   }

   private void drawRectBorder(C_279497_ graphicsIn, int x1, int y1, int x2, int y2, int col) {
      graphicsIn.m_280509_(x1, y1 - 1, x2, y1, col);
      graphicsIn.m_280509_(x1, y2, x2, y2 + 1, col);
      graphicsIn.m_280509_(x1 - 1, y1, x1, y2, col);
      graphicsIn.m_280509_(x2, y1, x2 + 1, y2, col);
   }
}
