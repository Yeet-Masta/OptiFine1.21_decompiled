package net.optifine.shaders.gui;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderEnumShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.EnumShaderOption;

public class GuiShaders extends GuiScreenOF {
   protected Screen parentGui;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderEnumShaderOptions());
   private int updateTimer = -1;
   private GuiSlotShaders shaderList;
   private boolean saved = false;
   private static float[] QUALITY_MULTIPLIERS = new float[]{0.5F, 0.6F, 0.6666667F, 0.75F, 0.8333333F, 0.9F, 1.0F, 1.1666666F, 1.3333334F, 1.5F, 1.6666666F, 1.8F, 2.0F};
   private static String[] QUALITY_MULTIPLIER_NAMES = new String[]{"0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", "1.66x", "1.8x", "2x"};
   private static float QUALITY_MULTIPLIER_DEFAULT = 1.0F;
   private static float[] HAND_DEPTH_VALUES = new float[]{0.0625F, 0.125F, 0.25F};
   private static String[] HAND_DEPTH_NAMES = new String[]{"0.5x", "1x", "2x"};
   private static float HAND_DEPTH_DEFAULT = 0.125F;
   public static final int EnumOS_UNKNOWN = 0;
   public static final int EnumOS_WINDOWS = 1;
   public static final int EnumOS_OSX = 2;
   public static final int EnumOS_SOLARIS = 3;
   public static final int EnumOS_LINUX = 4;

   public GuiShaders(Screen par1GuiScreen, Options par2GameSettings) {
      super(Component.m_237113_(I18n.m_118938_("of.options.shadersTitle", new Object[0])));
      this.parentGui = par1GuiScreen;
   }

   public void m_7856_() {
      if (Shaders.shadersConfig == null) {
         Shaders.loadConfig();
      }

      int btnWidth = 120;
      int btnHeight = 20;
      int btnX = this.f_96543_ - btnWidth - 10;
      int baseY = 30;
      int stepY = 20;
      int shaderListWidth = this.f_96543_ - btnWidth - 20;
      this.shaderList = new GuiSlotShaders(this, shaderListWidth, this.f_96544_, baseY, this.f_96544_ - 50, 16);
      this.m_7787_(this.shaderList);
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, btnX, 0 * stepY + baseY, btnWidth, btnHeight));
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, btnX, 1 * stepY + baseY, btnWidth, btnHeight));
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, btnX, 2 * stepY + baseY, btnWidth, btnHeight));
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, btnX, 3 * stepY + baseY, btnWidth, btnHeight));
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, btnX, 4 * stepY + baseY, btnWidth, btnHeight));
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, btnX, 5 * stepY + baseY, btnWidth, btnHeight));
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, btnX, 6 * stepY + baseY, btnWidth, btnHeight));
      this.m_142416_(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, btnX, 7 * stepY + baseY, btnWidth, btnHeight));
      int btnFolderWidth = Math.min(150, shaderListWidth / 2 - 10);
      int xFolder = shaderListWidth / 4 - btnFolderWidth / 2;
      int yFolder = this.f_96544_ - 25;
      this.m_142416_(new GuiButtonOF(201, xFolder, yFolder, btnFolderWidth - 22 + 1, btnHeight, Lang.get("of.options.shaders.shadersFolder")));
      this.m_142416_(new GuiButtonDownloadShaders(210, xFolder + btnFolderWidth - 22 - 1, yFolder));
      this.m_142416_(new GuiButtonOF(202, shaderListWidth / 4 * 3 - btnFolderWidth / 2, this.f_96544_ - 25, btnFolderWidth, btnHeight, I18n.m_118938_("gui.done", new Object[0])));
      this.m_142416_(new GuiButtonOF(203, btnX, this.f_96544_ - 25, btnWidth, btnHeight, Lang.get("of.options.shaders.shaderOptions")));
      this.m_7522_(this.shaderList);
      this.updateButtons();
   }

   public void updateButtons() {
      boolean shaderActive = Config.isShaders();
      Iterator it = this.getButtonList().iterator();

      while(it.hasNext()) {
         AbstractWidget guiElement = (AbstractWidget)it.next();
         if (guiElement instanceof GuiButtonOF button) {
            if (button.field_45 != 201 && button.field_45 != 202 && button.field_45 != 210 && button.field_45 != EnumShaderOption.ANTIALIASING.ordinal()) {
               button.f_93623_ = shaderActive;
            }
         }
      }

   }

   protected void actionPerformed(AbstractWidget button) {
      this.actionPerformed(button, false);
   }

   protected void actionPerformedRightClick(AbstractWidget button) {
      this.actionPerformed(button, true);
   }

   private void actionPerformed(AbstractWidget var1, boolean var2) {
      // $FF: Couldn't be decompiled
   }

   public void m_7861_() {
      if (!this.saved) {
         Shaders.storeConfig();
         this.saved = true;
      }

      super.m_7861_();
   }

   public void m_88315_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_280273_(graphicsIn, mouseX, mouseY, partialTicks);
      this.shaderList.render(graphicsIn, mouseX, mouseY, partialTicks);
      if (this.updateTimer <= 0) {
         this.shaderList.updateList();
         this.updateTimer += 20;
      }

      drawCenteredString(graphicsIn, this.fontRenderer, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      String info = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
      int infoWidth = this.fontRenderer.m_92895_(info);
      if (infoWidth < this.f_96543_ - 5) {
         drawCenteredString(graphicsIn, this.fontRenderer, info, this.f_96543_ / 2, this.f_96544_ - 40, 10526880);
      } else {
         drawString(graphicsIn, this.fontRenderer, info, 5, this.f_96544_ - 40, 10526880);
      }

      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(graphicsIn, mouseX, mouseY, this.getButtonList());
   }

   public void m_280273_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
   }

   public void m_86600_() {
      super.m_86600_();
      --this.updateTimer;
   }

   public Minecraft getMc() {
      return this.f_96541_;
   }

   public void drawCenteredString(GuiGraphics graphicsIn, String text, int x, int y, int color) {
      drawCenteredString(graphicsIn, this.fontRenderer, text, x, y, color);
   }

   public static String toStringOnOff(boolean value) {
      String on = Lang.getOn();
      String off = Lang.getOff();
      return value ? on : off;
   }

   public static String toStringAa(int value) {
      if (value == 2) {
         return "FXAA 2x";
      } else {
         return value == 4 ? "FXAA 4x" : Lang.getOff();
      }
   }

   public static String toStringValue(float val, float[] values, String[] names) {
      int index = getValueIndex(val, values);
      return names[index];
   }

   private float getNextValue(float val, float[] values, float valDef, boolean forward, boolean reset) {
      if (reset) {
         return valDef;
      } else {
         int index = getValueIndex(val, values);
         if (forward) {
            ++index;
            if (index >= values.length) {
               index = 0;
            }
         } else {
            --index;
            if (index < 0) {
               index = values.length - 1;
            }
         }

         return values[index];
      }
   }

   public static int getValueIndex(float val, float[] values) {
      for(int i = 0; i < values.length; ++i) {
         float value = values[i];
         if (value >= val) {
            return i;
         }
      }

      return values.length - 1;
   }

   public static String toStringQuality(float val) {
      return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
   }

   public static String toStringHandDepth(float val) {
      return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
   }

   public static int getOSType() {
      String osName = System.getProperty("os.name").toLowerCase();
      if (osName.contains("win")) {
         return 1;
      } else if (osName.contains("mac")) {
         return 2;
      } else if (osName.contains("solaris")) {
         return 3;
      } else if (osName.contains("sunos")) {
         return 3;
      } else if (osName.contains("linux")) {
         return 4;
      } else {
         return osName.contains("unix") ? 4 : 0;
      }
   }
}
