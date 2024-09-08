package net.optifine.shaders.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_5265_;
import net.optifine.shaders.config.ShaderOption;

public class GuiSliderShaderOption extends GuiButtonShaderOption {
   private float sliderValue;
   public boolean dragging;
   private ShaderOption shaderOption = null;
   private static final C_5265_ SLIDER_SPRITE = new C_5265_("widget/slider");
   private static final C_5265_ HIGHLIGHTED_SPRITE = new C_5265_("widget/slider_highlighted");
   private static final C_5265_ SLIDER_HANDLE_SPRITE = new C_5265_("widget/slider_handle");
   private static final C_5265_ SLIDER_HANDLE_HIGHLIGHTED_SPRITE = new C_5265_("widget/slider_handle_highlighted");

   public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
      super(buttonId, x, y, w, h, shaderOption, text);
      this.sliderValue = 1.0F;
      this.shaderOption = shaderOption;
      this.sliderValue = shaderOption.getIndexNormalized();
      this.setMessage(GuiShaderOptions.getButtonText(shaderOption, this.g));
   }

   public void b(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.k) {
         if (this.dragging && !C_3583_.m_96638_()) {
            this.sliderValue = (float)(mouseX - (this.D() + 4)) / (float)(this.g - 8);
            this.sliderValue = C_188_.m_14036_(this.sliderValue, 0.0F, 1.0F);
            this.shaderOption.setIndexNormalized(this.sliderValue);
            this.sliderValue = this.shaderOption.getIndexNormalized();
            this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.g));
         }

         C_3391_ mc = C_3391_.m_91087_();
         graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, this.l);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.enableDepthTest();
         graphicsIn.m_292816_(this.getSprite(), this.D(), this.E(), this.y(), this.w());
         graphicsIn.m_292816_(this.getHandleSprite(), this.D() + (int)(this.sliderValue * (float)(this.g - 8)), this.E(), 8, this.w());
         int col = this.j ? 16777215 : 10526880;
         this.a(graphicsIn, mc.f_91062_, col | C_188_.m_14167_(this.l * 255.0F) << 24);
      }
   }

   private C_5265_ getSprite() {
      return this.aO_() && !this.dragging ? HIGHLIGHTED_SPRITE : SLIDER_SPRITE;
   }

   private C_5265_ getHandleSprite() {
      return !this.i && !this.dragging ? SLIDER_HANDLE_SPRITE : SLIDER_HANDLE_HIGHLIGHTED_SPRITE;
   }

   public boolean a(double mouseX, double mouseY, int button) {
      if (super.a(mouseX, mouseY, button)) {
         this.sliderValue = (float)(mouseX - (double)(this.D() + 4)) / (float)(this.g - 8);
         this.sliderValue = C_188_.m_14036_(this.sliderValue, 0.0F, 1.0F);
         this.shaderOption.setIndexNormalized(this.sliderValue);
         this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.g));
         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean b(double mouseX, double mouseY, int button) {
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
