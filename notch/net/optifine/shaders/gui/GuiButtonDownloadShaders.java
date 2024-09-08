package net.optifine.shaders.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_5265_;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonDownloadShaders extends GuiButtonOF {
   public GuiButtonDownloadShaders(int buttonID, int xPos, int yPos) {
      super(buttonID, xPos, yPos, 22, 20, "");
   }

   public void b(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.k) {
         super.b(graphicsIn, mouseX, mouseY, partialTicks);
         C_5265_ locTexture = new C_5265_("optifine/textures/icons.png");
         RenderSystem.setShaderTexture(0, locTexture);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         blit(graphicsIn, locTexture, this.D() + 3, this.E() + 2, 0, 0, 16, 16);
      }
   }
}
