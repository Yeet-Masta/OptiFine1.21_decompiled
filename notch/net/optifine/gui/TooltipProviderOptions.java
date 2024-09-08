package net.optifine.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.C_213334_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.optifine.Lang;

public class TooltipProviderOptions implements TooltipProvider {
   public Rectangle getTooltipBounds(C_3583_ guiScreen, int x, int y) {
      int x1 = guiScreen.f_96543_ / 2 - 150;
      int y1 = guiScreen.f_96544_ / 6 - 7;
      if (y <= y1 + 98) {
         y1 += 105;
      }

      int x2 = x1 + 150 + 150;
      int y2 = y1 + 84 + 10;
      return new Rectangle(x1, y1, x2 - x1, y2 - y1);
   }

   @Override
   public boolean isRenderBorder() {
      return false;
   }

   public String[] getTooltipLines(C_3449_ btn, int width) {
      if (!(btn instanceof IOptionControl ctl)) {
         return null;
      } else {
         C_213334_ option = ctl.getControlOption();
         return option == null ? null : getTooltipLines(option.getResourceKey());
      }
   }

   public static String[] getTooltipLines(String key) {
      List<String> list = new ArrayList();

      for (int i = 0; i < 10; i++) {
         String lineKey = key + ".tooltip." + (i + 1);
         String line = Lang.get(lineKey, null);
         if (line == null) {
            break;
         }

         list.add(line);
      }

      return list.size() <= 0 ? null : (String[])list.toArray(new String[list.size()]);
   }
}
