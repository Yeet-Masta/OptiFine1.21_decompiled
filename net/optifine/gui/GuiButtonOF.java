package net.optifine.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.CreateNarration;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class GuiButtonOF extends Button {
   public final int id;

   public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, OnPress pressable, CreateNarration narrationIn) {
      super(x, y, widthIn, heightIn, Component.m_237113_(buttonText), pressable, narrationIn);
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
      super.m_93666_(Component.m_237113_(messageIn));
   }

   public static void blit(
      net.minecraft.client.gui.GuiGraphics graphicsIn,
      net.minecraft.resources.ResourceLocation locationIn,
      int x,
      int y,
      int rectX,
      int rectY,
      int width,
      int height
   ) {
      graphicsIn.m_280218_(locationIn, x, y, rectX, rectY, width, height);
   }

   public void blit(
      net.minecraft.client.gui.GuiGraphics graphicsIn,
      net.minecraft.resources.ResourceLocation locationIn,
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
