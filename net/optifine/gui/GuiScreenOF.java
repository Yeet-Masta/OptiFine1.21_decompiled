package net.optifine.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.optifine.util.GuiUtils;

public class GuiScreenOF extends Screen {
   protected Font fontRenderer = Minecraft.m_91087_().f_91062_;
   protected boolean mousePressed = false;
   protected Options settings = Minecraft.m_91087_().f_91066_;

   public GuiScreenOF(Component title) {
      super(title);
   }

   public List<AbstractWidget> getButtonList() {
      List<AbstractWidget> buttons = new ArrayList();

      for (GuiEventListener gel : this.m_6702_()) {
         if (gel instanceof AbstractWidget) {
            buttons.add((AbstractWidget)gel);
         }
      }

      return buttons;
   }

   protected void actionPerformed(AbstractWidget button) {
   }

   protected void actionPerformedRightClick(AbstractWidget button) {
   }

   public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
      boolean ret = super.m_6375_(mouseX, mouseY, mouseButton);
      this.mousePressed = true;
      AbstractWidget btn = getSelectedButton((int)mouseX, (int)mouseY, this.getButtonList());
      if (btn != null && btn.f_93623_) {
         if (mouseButton == 1 && btn instanceof IOptionControl ioc && ioc.getControlOption() == this.settings.GUI_SCALE) {
            btn.m_7435_(super.f_96541_.m_91106_());
         }

         if (mouseButton == 0) {
            this.actionPerformed(btn);
         } else if (mouseButton == 1) {
            this.actionPerformedRightClick(btn);
         }

         return true;
      } else {
         return ret;
      }
   }

   public boolean m_6050_(double mouseX, double mouseY, double deltaX, double deltaY) {
      boolean ret = super.m_6050_(mouseX, mouseY, deltaX, deltaY);
      AbstractWidget btn = getSelectedButton((int)mouseX, (int)mouseY, this.getButtonList());
      if (btn != null && btn.f_93623_ && btn instanceof IOptionControl) {
         this.actionPerformed(btn);
         return true;
      } else {
         return ret;
      }
   }

   public boolean m_6348_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      if (!this.mousePressed) {
         return false;
      } else {
         this.mousePressed = false;
         this.m_7897_(false);
         return this.m_7222_() != null && this.m_7222_().m_6348_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_)
            ? true
            : super.m_6348_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
      }
   }

   public boolean m_7979_(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
      return !this.mousePressed ? false : super.m_7979_(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
   }

   public static AbstractWidget getSelectedButton(int x, int y, List<AbstractWidget> listButtons) {
      for (int i = 0; i < listButtons.size(); i++) {
         AbstractWidget btn = (AbstractWidget)listButtons.get(i);
         if (btn.f_93624_) {
            int btnWidth = GuiUtils.m_92515_(btn);
            int btnHeight = GuiUtils.getHeight(btn);
            if (x >= btn.m_252754_() && y >= btn.m_252907_() && x < btn.m_252754_() + btnWidth && y < btn.m_252907_() + btnHeight) {
               return btn;
            }
         }
      }

      return null;
   }

   public static void drawString(GuiGraphics graphicsIn, Font fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280137_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }

   public static void drawCenteredString(GuiGraphics graphicsIn, Font fontRendererIn, FormattedCharSequence textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280364_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }

   public static void drawCenteredString(GuiGraphics graphicsIn, Font fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280137_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }

   public static void drawCenteredString(GuiGraphics graphicsIn, Font fontRendererIn, Component textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280653_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }
}
