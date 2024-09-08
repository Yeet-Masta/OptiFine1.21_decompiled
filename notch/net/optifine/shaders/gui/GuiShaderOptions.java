package net.optifine.shaders.gui;

import net.minecraft.src.C_188_;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3429_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.shaders.config.ShaderOptionScreen;

public class GuiShaderOptions extends GuiScreenOF {
   private C_3583_ prevScreen;
   private C_3401_ settings;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderShaderOptions());
   private String screenName = null;
   private String screenText = null;
   private boolean changed = false;
   public static final String OPTION_PROFILE = "<profile>";
   public static final String OPTION_EMPTY = "<empty>";
   public static final String OPTION_REST = "*";

   public GuiShaderOptions(C_3583_ guiscreen, C_3401_ gamesettings) {
      super(C_4996_.m_237113_(C_4513_.m_118938_("of.options.shaderOptionsTitle", new Object[0])));
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   public GuiShaderOptions(C_3583_ guiscreen, C_3401_ gamesettings, String screenName) {
      this(guiscreen, gamesettings);
      this.screenName = screenName;
      if (screenName != null) {
         this.screenText = Shaders.translate("screen." + screenName, screenName);
      }
   }

   public void m_7856_() {
      int baseId = 100;
      int baseX = 0;
      int baseY = 30;
      int stepY = 20;
      int btnWidth = 120;
      int btnHeight = 20;
      int columns = Shaders.getShaderPackColumns(this.screenName, 2);
      ShaderOption[] ops = Shaders.getShaderPackOptions(this.screenName);
      if (ops != null) {
         int colsMin = C_188_.m_14165_((double)ops.length / 9.0);
         if (columns < colsMin) {
            columns = colsMin;
         }

         for (int i = 0; i < ops.length; i++) {
            ShaderOption so = ops[i];
            if (so != null && so.isVisible()) {
               int col = i % columns;
               int row = i / columns;
               int colWidth = Math.min(this.f_96543_ / columns, 200);
               baseX = (this.f_96543_ - colWidth * columns) / 2;
               int x = col * colWidth + 5 + baseX;
               int y = baseY + row * stepY;
               int w = colWidth - 10;
               String text = getButtonText(so, w);
               GuiButtonShaderOption btn;
               if (Shaders.isShaderPackOptionSlider(so.getName())) {
                  btn = new GuiSliderShaderOption(baseId + i, x, y, w, btnHeight, so, text);
               } else {
                  btn = new GuiButtonShaderOption(baseId + i, x, y, w, btnHeight, so, text);
               }

               btn.j = so.isEnabled();
               this.m_142416_(btn);
            }
         }
      }

      this.m_142416_(
         new GuiButtonOF(
            201, this.f_96543_ / 2 - btnWidth - 20, this.f_96544_ / 6 + 168 + 11, btnWidth, btnHeight, C_4513_.m_118938_("controls.reset", new Object[0])
         )
      );
      this.m_142416_(
         new GuiButtonOF(200, this.f_96543_ / 2 + 20, this.f_96544_ / 6 + 168 + 11, btnWidth, btnHeight, C_4513_.m_118938_("gui.done", new Object[0]))
      );
   }

   public static String getButtonText(ShaderOption so, int btnWidth) {
      String labelName = so.getNameText();
      if (so instanceof ShaderOptionScreen soScr) {
         return labelName + "...";
      } else {
         C_3429_ fr = Config.getMinecraft().f_91062_;
         int lenSuffix = fr.m_92895_(": " + Lang.getOff()) + 5;

         while (fr.m_92895_(labelName) + lenSuffix >= btnWidth && labelName.length() > 0) {
            labelName = labelName.substring(0, labelName.length() - 1);
         }

         String col = so.isChanged() ? so.getValueColor(so.getValue()) : "";
         String labelValue = so.getValueText(so.getValue());
         return labelName + ": " + col + labelValue;
      }
   }

   protected void actionPerformed(C_3449_ guiElement) {
      if (guiElement instanceof GuiButtonOF guibutton) {
         if (guibutton.j) {
            if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption btnSo) {
               ShaderOption so = btnSo.getShaderOption();
               if (so instanceof ShaderOptionScreen) {
                  String screenName = so.getName();
                  GuiShaderOptions scr = new GuiShaderOptions(this, this.settings, screenName);
                  this.f_96541_.m_91152_(scr);
                  return;
               }

               if (m_96638_()) {
                  so.resetValue();
               } else if (btnSo.isSwitchable()) {
                  so.nextValue();
               }

               this.updateAllButtons();
               this.changed = true;
            }

            if (guibutton.id == 201) {
               ShaderOption[] opts = Shaders.getChangedOptions(Shaders.getShaderPackOptions());

               for (int i = 0; i < opts.length; i++) {
                  ShaderOption opt = opts[i];
                  opt.resetValue();
                  this.changed = true;
               }

               this.updateAllButtons();
            }

            if (guibutton.id == 200) {
               if (this.changed) {
                  Shaders.saveShaderPackOptions();
                  this.changed = false;
                  Shaders.uninit();
               }

               this.f_96541_.m_91152_(this.prevScreen);
            }
         }
      }
   }

   public void m_7861_() {
      if (this.changed) {
         Shaders.saveShaderPackOptions();
         this.changed = false;
         Shaders.uninit();
      }

      super.m_7861_();
   }

   protected void actionPerformedRightClick(C_3449_ guiElement) {
      if (guiElement instanceof GuiButtonShaderOption btnSo) {
         ShaderOption so = btnSo.getShaderOption();
         if (m_96638_()) {
            so.resetValue();
         } else if (btnSo.isSwitchable()) {
            so.prevValue();
         }

         this.updateAllButtons();
         this.changed = true;
      }
   }

   private void updateAllButtons() {
      for (C_3451_ btn : this.getButtonList()) {
         if (btn instanceof GuiButtonShaderOption) {
            GuiButtonShaderOption gbso = (GuiButtonShaderOption)btn;
            ShaderOption opt = gbso.getShaderOption();
            if (opt instanceof ShaderOptionProfile optProf) {
               optProf.updateProfile();
            }

            gbso.setMessage(getButtonText(opt, gbso.y()));
            gbso.valueChanged();
         }
      }
   }

   public void m_88315_(C_279497_ graphicsIn, int x, int y, float partialTicks) {
      super.m_88315_(graphicsIn, x, y, partialTicks);
      if (this.screenText != null) {
         drawCenteredString(graphicsIn, this.fontRenderer, this.screenText, this.f_96543_ / 2, 15, 16777215);
      } else {
         drawCenteredString(graphicsIn, this.fontRenderer, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      }

      this.tooltipManager.drawTooltips(graphicsIn, x, y, this.getButtonList());
   }
}
