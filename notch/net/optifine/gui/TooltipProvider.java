package net.optifine.gui;

import java.awt.Rectangle;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;

public interface TooltipProvider {
   Rectangle getTooltipBounds(C_3583_ var1, int var2, int var3);

   String[] getTooltipLines(C_3449_ var1, int var2);

   boolean isRenderBorder();
}
