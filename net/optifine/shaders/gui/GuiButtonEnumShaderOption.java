package net.optifine.shaders.gui;

import net.minecraft.client.resources.language.I18n;
import net.optifine.gui.GuiButtonOF;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.EnumShaderOption;

public class GuiButtonEnumShaderOption extends GuiButtonOF {
   private EnumShaderOption enumShaderOption = null;

   public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int x, int y, int widthIn, int heightIn) {
      super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
      this.enumShaderOption = enumShaderOption;
   }

   public EnumShaderOption getEnumShaderOption() {
      return this.enumShaderOption;
   }

   private static String getButtonText(EnumShaderOption eso) {
      String var10000 = eso.getResourceKey();
      String nameText = I18n.m_118938_(var10000, new Object[0]) + ": ";
      switch (eso) {
         case ANTIALIASING:
            return nameText + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
         case NORMAL_MAP:
            return nameText + GuiShaders.toStringOnOff(Shaders.configNormalMap);
         case SPECULAR_MAP:
            return nameText + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
         case RENDER_RES_MUL:
            return nameText + GuiShaders.toStringQuality(Shaders.configRenderResMul);
         case SHADOW_RES_MUL:
            return nameText + GuiShaders.toStringQuality(Shaders.configShadowResMul);
         case HAND_DEPTH_MUL:
            return nameText + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
         case CLOUD_SHADOW:
            return nameText + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
         case OLD_HAND_LIGHT:
            return nameText + Shaders.configOldHandLight.getUserValue();
         case OLD_LIGHTING:
            return nameText + Shaders.configOldLighting.getUserValue();
         case SHADOW_CLIP_FRUSTRUM:
            return nameText + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
         case TWEAK_BLOCK_DAMAGE:
            return nameText + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
         default:
            return nameText + Shaders.getEnumShaderOption(eso);
      }
   }

   public void updateButtonText() {
      this.setMessage(getButtonText(this.enumShaderOption));
   }

   protected boolean m_7972_(int button) {
      return true;
   }
}
