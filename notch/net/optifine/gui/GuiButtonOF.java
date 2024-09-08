package net.optifine.gui;

import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_3451_.C_252357_;
import net.minecraft.src.C_3451_.C_3452_;

public class GuiButtonOF extends C_3451_ {
   public final int id;

   public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, C_3452_ pressable, C_252357_ narrationIn) {
      super(x, y, widthIn, heightIn, C_4996_.m_237113_(buttonText), pressable, narrationIn);
      this.id = buttonId;
   }

   public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
      this(buttonId, x, y, widthIn, heightIn, buttonText, btn -> {
      }, f_252438_);
   }

   public GuiButtonOF(int buttonId, int x, int y, String buttonText) {
      this(buttonId, x, y, 200, 20, buttonText, btn -> {
      }, f_252438_);
   }

   public void setMessage(String messageIn) {
      super.b(C_4996_.m_237113_(messageIn));
   }

   public static void blit(C_279497_ graphicsIn, C_5265_ locationIn, int x, int y, int rectX, int rectY, int width, int height) {
      graphicsIn.m_280218_(locationIn, x, y, rectX, rectY, width, height);
   }

   public void blit(
      C_279497_ graphicsIn,
      C_5265_ locationIn,
      int x,
      int y,
      int width,
      int height,
      float rectX,
      float rectY,
      int rectWidth,
      int rectHeight,
      int texWidth,
      int texHeight
   ) {
      graphicsIn.m_280411_(locationIn, x, y, width, height, rectX, rectY, rectWidth, rectHeight, texWidth, texHeight);
   }
}
