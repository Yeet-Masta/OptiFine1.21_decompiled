package net.optifine.shaders.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonDownloadShaders extends GuiButtonOF {
   public GuiButtonDownloadShaders(int buttonID, int xPos, int yPos) {
      super(buttonID, xPos, yPos, 22, 20, "");
   }

   public void m_87963_(net.minecraft.client.gui.GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.f_93624_) {
         super.m_87963_(graphicsIn, mouseX, mouseY, partialTicks);
         net.minecraft.resources.ResourceLocation locTexture = new net.minecraft.resources.ResourceLocation("optifine/textures/icons.png");
         RenderSystem.setShaderTexture(0, locTexture);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         blit(graphicsIn, locTexture, this.m_252754_() + 3, this.m_252907_() + 2, 0, 0, 16, 16);
      }
   }
}
