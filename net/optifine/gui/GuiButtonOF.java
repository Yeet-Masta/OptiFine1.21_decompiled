package net.optifine.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiButtonOF extends Button {
   // $FF: renamed from: id int
   public final int field_45;

   public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Button.OnPress pressable, Button.CreateNarration narrationIn) {
      super(x, y, widthIn, heightIn, Component.m_237113_(buttonText), pressable, narrationIn);
      this.field_45 = buttonId;
   }

   public GuiButtonOF(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
      this(buttonId, x, y, widthIn, heightIn, buttonText, (btn) -> {
      }, f_252438_);
   }

   public GuiButtonOF(int buttonId, int x, int y, String buttonText) {
      this(buttonId, x, y, 200, 20, buttonText, (btn) -> {
      }, f_252438_);
   }

   public void setMessage(String messageIn) {
      super.m_93666_(Component.m_237113_(messageIn));
   }

   public static void blit(GuiGraphics graphicsIn, ResourceLocation locationIn, int x, int y, int rectX, int rectY, int width, int height) {
      graphicsIn.m_280218_(locationIn, x, y, rectX, rectY, width, height);
   }

   public void blit(GuiGraphics graphicsIn, ResourceLocation locationIn, int x, int y, int width, int height, float rectX, float rectY, int rectWidth, int rectHeight, int texWidth, int texHeight) {
      graphicsIn.m_280411_(locationIn, x, y, width, height, rectX, rectY, rectWidth, rectHeight, texWidth, texHeight);
   }
}
