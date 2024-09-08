package net.optifine.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3429_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3495_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4996_;
import net.optifine.util.GuiUtils;

public class GuiScreenOF extends C_3583_ {
   protected C_3429_ fontRenderer = C_3391_.m_91087_().f_91062_;
   protected boolean mousePressed = false;
   protected C_3401_ settings = C_3391_.m_91087_().f_91066_;

   public GuiScreenOF(C_4996_ title) {
      super(title);
   }

   public List<C_3449_> getButtonList() {
      List<C_3449_> buttons = new ArrayList();

      for (C_3495_ gel : this.m_6702_()) {
         if (gel instanceof C_3449_) {
            buttons.add((C_3449_)gel);
         }
      }

      return buttons;
   }

   protected void actionPerformed(C_3449_ button) {
   }

   protected void actionPerformedRightClick(C_3449_ button) {
   }

   public boolean a(double mouseX, double mouseY, int mouseButton) {
      boolean ret = super.a(mouseX, mouseY, mouseButton);
      this.mousePressed = true;
      C_3449_ btn = getSelectedButton((int)mouseX, (int)mouseY, this.getButtonList());
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

   public boolean a(double mouseX, double mouseY, double deltaX, double deltaY) {
      boolean ret = super.a(mouseX, mouseY, deltaX, deltaY);
      C_3449_ btn = getSelectedButton((int)mouseX, (int)mouseY, this.getButtonList());
      if (btn != null && btn.f_93623_ && btn instanceof IOptionControl) {
         this.actionPerformed(btn);
         return true;
      } else {
         return ret;
      }
   }

   public boolean b(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      if (!this.mousePressed) {
         return false;
      } else {
         this.mousePressed = false;
         this.b_(false);
         return this.aN_() != null && this.aN_().m_6348_(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_)
            ? true
            : super.b(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
      }
   }

   public boolean a(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
      return !this.mousePressed ? false : super.a(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
   }

   public static C_3449_ getSelectedButton(int x, int y, List<C_3449_> listButtons) {
      for (int i = 0; i < listButtons.size(); i++) {
         C_3449_ btn = (C_3449_)listButtons.get(i);
         if (btn.f_93624_) {
            int btnWidth = GuiUtils.getWidth(btn);
            int btnHeight = GuiUtils.getHeight(btn);
            if (x >= btn.m_252754_() && y >= btn.m_252907_() && x < btn.m_252754_() + btnWidth && y < btn.m_252907_() + btnHeight) {
               return btn;
            }
         }
      }

      return null;
   }

   public static void drawString(C_279497_ graphicsIn, C_3429_ fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280137_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }

   public static void drawCenteredString(C_279497_ graphicsIn, C_3429_ fontRendererIn, C_178_ textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280364_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }

   public static void drawCenteredString(C_279497_ graphicsIn, C_3429_ fontRendererIn, String textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280137_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }

   public static void drawCenteredString(C_279497_ graphicsIn, C_3429_ fontRendererIn, C_4996_ textIn, int xIn, int yIn, int colorIn) {
      graphicsIn.m_280653_(fontRendererIn, textIn, xIn, yIn, colorIn);
   }
}
