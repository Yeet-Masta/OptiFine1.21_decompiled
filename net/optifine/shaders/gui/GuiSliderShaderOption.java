package net.optifine.shaders.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.optifine.shaders.config.ShaderOption;

public class GuiSliderShaderOption extends GuiButtonShaderOption {
   private float sliderValue;
   public boolean dragging;
   private ShaderOption shaderOption = null;
   private static ResourceLocation SLIDER_SPRITE = new ResourceLocation("widget/slider");
   private static ResourceLocation HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_highlighted");
   private static ResourceLocation SLIDER_HANDLE_SPRITE = new ResourceLocation("widget/slider_handle");
   private static ResourceLocation SLIDER_HANDLE_HIGHLIGHTED_SPRITE = new ResourceLocation("widget/slider_handle_highlighted");

   public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
      super(buttonId, x, y, w, h, shaderOption, text);
      this.sliderValue = 1.0F;
      this.shaderOption = shaderOption;
      this.sliderValue = shaderOption.getIndexNormalized();
      this.setMessage(GuiShaderOptions.getButtonText(shaderOption, this.f_93618_));
   }

   public void m_87963_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.f_93624_) {
         if (this.dragging && !Screen.m_96638_()) {
            this.sliderValue = (float)(mouseX - (this.m_252754_() + 4)) / (float)(this.f_93618_ - 8);
            this.sliderValue = Mth.m_14036_(this.sliderValue, 0.0F, 1.0F);
            this.shaderOption.setIndexNormalized(this.sliderValue);
            this.sliderValue = this.shaderOption.getIndexNormalized();
            this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.f_93618_));
         }

         Minecraft mc = Minecraft.m_91087_();
         graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, this.f_93625_);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.enableDepthTest();
         graphicsIn.m_292816_(this.getSprite(), this.m_252754_(), this.m_252907_(), this.m_5711_(), this.m_93694_());
         graphicsIn.m_292816_(
            this.getHandleSprite(), this.m_252754_() + (int)(this.sliderValue * (float)(this.f_93618_ - 8)), this.m_252907_(), 8, this.m_93694_()
         );
         int col = this.f_93623_ ? 16777215 : 10526880;
         this.m_280465_(graphicsIn, mc.f_91062_, col | Mth.m_14167_(this.f_93625_ * 255.0F) << 24);
      }
   }

   private ResourceLocation getSprite() {
      return this.m_93696_() && !this.dragging ? HIGHLIGHTED_SPRITE : SLIDER_SPRITE;
   }

   private ResourceLocation getHandleSprite() {
      return !this.f_93622_ && !this.dragging ? SLIDER_HANDLE_SPRITE : SLIDER_HANDLE_HIGHLIGHTED_SPRITE;
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      if (super.m_6375_(mouseX, mouseY, button)) {
         this.sliderValue = (float)(mouseX - (double)(this.m_252754_() + 4)) / (float)(this.f_93618_ - 8);
         this.sliderValue = Mth.m_14036_(this.sliderValue, 0.0F, 1.0F);
         this.shaderOption.setIndexNormalized(this.sliderValue);
         this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.f_93618_));
         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean m_6348_(double mouseX, double mouseY, int button) {
      this.dragging = false;
      return true;
   }

   @Override
   public void valueChanged() {
      this.sliderValue = this.shaderOption.getIndexNormalized();
   }

   @Override
   public boolean isSwitchable() {
      return false;
   }
}
