package net.optifine.gui;

import java.awt.Rectangle;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.gui.GuiButtonDownloadShaders;
import net.optifine.shaders.gui.GuiButtonEnumShaderOption;

public class TooltipProviderEnumShaderOptions implements TooltipProvider {
   public Rectangle getTooltipBounds(C_3583_ guiScreen, int x, int y) {
      int x1 = guiScreen.f_96543_ - 450;
      int y1 = 35;
      if (x1 < 10) {
         x1 = 10;
      }

      if (y <= y1 + 94) {
         y1 += 100;
      }

      int x2 = x1 + 150 + 150;
      int y2 = y1 + 84 + 10;
      return new Rectangle(x1, y1, x2 - x1, y2 - y1);
   }

   @Override
   public boolean isRenderBorder() {
      return true;
   }

   public String[] getTooltipLines(C_3449_ btn, int width) {
      if (btn instanceof GuiButtonDownloadShaders) {
         return TooltipProviderOptions.getTooltipLines("of.options.shaders.DOWNLOAD");
      } else if (!(btn instanceof GuiButtonEnumShaderOption gbeso)) {
         return null;
      } else {
         EnumShaderOption option = gbeso.getEnumShaderOption();
         return this.getTooltipLines(option);
      }
   }

   private String[] getTooltipLines(EnumShaderOption option) {
      return TooltipProviderOptions.getTooltipLines(option.getResourceKey());
   }
}
