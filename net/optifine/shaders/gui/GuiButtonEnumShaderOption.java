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
      String nameText = I18n.m_118938_(eso.getResourceKey(), new Object[0]) + ": ";
      switch (<unrepresentable>.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[eso.ordinal()]) {
         case 1:
            return nameText + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
         case 2:
            return nameText + GuiShaders.toStringOnOff(Shaders.configNormalMap);
         case 3:
            return nameText + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
         case 4:
            return nameText + GuiShaders.toStringQuality(Shaders.configRenderResMul);
         case 5:
            return nameText + GuiShaders.toStringQuality(Shaders.configShadowResMul);
         case 6:
            return nameText + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
         case 7:
            return nameText + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
         case 8:
            return nameText + Shaders.configOldHandLight.getUserValue();
         case 9:
            return nameText + Shaders.configOldLighting.getUserValue();
         case 10:
            return nameText + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
         case 11:
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
