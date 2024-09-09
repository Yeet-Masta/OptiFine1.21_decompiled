package net.minecraft.client.gui.screens.options;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Window;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.config.Option;
import net.optifine.gui.GuiAnimationSettingsOF;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiDetailSettingsOF;
import net.optifine.gui.GuiOtherSettingsOF;
import net.optifine.gui.GuiPerformanceSettingsOF;
import net.optifine.gui.GuiQualitySettingsOF;
import net.optifine.gui.GuiQuickInfoOF;
import net.optifine.gui.GuiScreenButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderOptions;
import net.optifine.shaders.gui.GuiShaders;
import net.optifine.util.GuiUtils;
import org.lwjgl.glfw.GLFW;

public class VideoSettingsScreen extends GuiScreenOF {
   private Screen parentGuiScreen;
   private Options guiGameSettings;
   private GpuWarnlistManager gpuWarning;
   private static final Component TEXT_FABULOUS;
   private static final Component TEXT_WARNING_MESSAGE;
   private static final Component TEXT_WARNING_TITLE;
   private static final Component TEXT_WARNING_ACCEPT;
   private static final Component TEXT_WARNING_CANCEL;
   private static final Component NEW_LINE;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
   private List buttonList = this.getButtonList();
   private AbstractWidget buttonGuiScale;
   private Minecraft minecraft = Minecraft.m_91087_();

   public VideoSettingsScreen(Screen par1GuiScreen, Minecraft minecraftIn, Options par2GameSettings) {
      super(Component.m_237115_("options.videoTitle"));
      this.parentGuiScreen = par1GuiScreen;
      this.guiGameSettings = par2GameSettings;
      this.gpuWarning = this.minecraft.m_91105_();
      this.gpuWarning.m_109252_();
      if (this.guiGameSettings.m_232060_().m_231551_() == GraphicsStatus.FABULOUS) {
         this.gpuWarning.m_109248_();
      }

      this.minecraft = minecraftIn;
   }

   public void m_7856_() {
      this.buttonList.clear();
      OptionInstance[] videoOptions = new OptionInstance[]{this.settings.GRAPHICS, this.settings.RENDER_DISTANCE, this.settings.field_19, this.settings.SIMULATION_DISTANCE, Option.AO_LEVEL, this.settings.FRAMERATE_LIMIT, this.settings.GUI_SCALE, this.settings.ENTITY_SHADOWS, this.settings.GAMMA, Option.DYNAMIC_FOV, Option.DYNAMIC_LIGHTS, null};

      int i;
      for(i = 0; i < videoOptions.length; ++i) {
         OptionInstance opt = videoOptions[i];
         if (opt != null) {
            int x = this.f_96543_ / 2 - 155 + i % 2 * 160;
            int y = this.f_96544_ / 6 + 21 * (i / 2) - 12;
            AbstractWidget guiElement = (AbstractWidget)this.m_142416_(opt.m_231507_(this.minecraft.f_91066_, x, y, 150));
            guiElement.m_257544_((Tooltip)null);
            if (opt == this.settings.GUI_SCALE) {
               this.buttonGuiScale = guiElement;
            }
         }
      }

      this.m_142416_(new GuiButtonOF(220, this.f_96543_ / 2 - 155 + 160, this.f_96544_ / 6 + 105 - 12, 150, 20, I18n.m_118938_("of.options.quickInfo", new Object[0])));
      i = this.f_96544_ / 6 + 21 * (videoOptions.length / 2) - 12;
      int x = false;
      int x = this.f_96543_ / 2 - 155 + 0;
      this.m_142416_(new GuiScreenButtonOF(231, x, i, Lang.get("of.options.shaders")));
      x = this.f_96543_ / 2 - 155 + 160;
      this.m_142416_(new GuiScreenButtonOF(202, x, i, Lang.get("of.options.quality")));
      i += 21;
      x = this.f_96543_ / 2 - 155 + 0;
      this.m_142416_(new GuiScreenButtonOF(201, x, i, Lang.get("of.options.details")));
      x = this.f_96543_ / 2 - 155 + 160;
      this.m_142416_(new GuiScreenButtonOF(212, x, i, Lang.get("of.options.performance")));
      i += 21;
      x = this.f_96543_ / 2 - 155 + 0;
      this.m_142416_(new GuiScreenButtonOF(211, x, i, Lang.get("of.options.animations")));
      x = this.f_96543_ / 2 - 155 + 160;
      this.m_142416_(new GuiScreenButtonOF(222, x, i, Lang.get("of.options.other")));
      i += 21;
      this.m_142416_(new GuiButtonOF(200, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168 + 11, I18n.m_118938_("gui.done", new Object[0])));
      this.buttonList = this.getButtonList();
   }

   protected void actionPerformed(AbstractWidget button) {
      if (button == this.buttonGuiScale) {
         this.updateGuiScale();
      }

      this.checkFabulousWarning();
      if (button instanceof GuiButtonOF gbo) {
         this.actionPerformed(gbo, 1);
      }
   }

   private void checkFabulousWarning() {
      if (this.gpuWarning.m_109250_()) {
         List list = Lists.newArrayList(new Component[]{TEXT_WARNING_MESSAGE, NEW_LINE});
         String s = this.gpuWarning.m_109253_();
         if (s != null) {
            list.add(NEW_LINE);
            list.add(Component.m_237110_("options.graphics.warning.renderer", new Object[]{s}).m_130940_(ChatFormatting.GRAY));
         }

         String s1 = this.gpuWarning.m_109255_();
         if (s1 != null) {
            list.add(NEW_LINE);
            list.add(Component.m_237110_("options.graphics.warning.vendor", new Object[]{s1}).m_130940_(ChatFormatting.GRAY));
         }

         String s2 = this.gpuWarning.m_109254_();
         if (s2 != null) {
            list.add(NEW_LINE);
            list.add(Component.m_237110_("options.graphics.warning.version", new Object[]{s2}).m_130940_(ChatFormatting.GRAY));
         }

         this.minecraft.m_91152_(new UnsupportedGraphicsWarningScreen(TEXT_WARNING_TITLE, list, ImmutableList.of(new UnsupportedGraphicsWarningScreen.ButtonOption(TEXT_WARNING_ACCEPT, (btn) -> {
            this.guiGameSettings.m_232060_().m_231514_(GraphicsStatus.FABULOUS);
            Minecraft.m_91087_().f_91060_.m_109818_();
            this.gpuWarning.m_109248_();
            this.minecraft.m_91152_(this);
         }), new UnsupportedGraphicsWarningScreen.ButtonOption(TEXT_WARNING_CANCEL, (btn) -> {
            this.gpuWarning.m_109249_();
            this.minecraft.m_91152_(this);
         }))));
      }

   }

   protected void actionPerformedRightClick(AbstractWidget button) {
      if (button == this.buttonGuiScale) {
         int newScale = (Integer)this.guiGameSettings.m_231928_().m_231551_() - 1;
         if (newScale < 0) {
            newScale = Minecraft.m_91087_().m_91268_().m_85385_(0, Minecraft.m_91087_().m_91390_());
         }

         this.settings.GUI_SCALE.m_231514_(newScale);
         this.updateGuiScale();
      }

   }

   private void updateGuiScale() {
      this.minecraft.m_5741_();
      Window mw = this.minecraft.m_91268_();
      int btnWidth = GuiUtils.getWidth(this.buttonGuiScale);
      int btnHeight = GuiUtils.getHeight(this.buttonGuiScale);
      int x = this.buttonGuiScale.m_252754_() + (btnWidth - btnHeight);
      int y = this.buttonGuiScale.m_252907_() + btnHeight / 2;
      GLFW.glfwSetCursorPos(mw.m_85439_(), (double)x * mw.m_85449_(), (double)y * mw.m_85449_());
   }

   private void actionPerformed(GuiButtonOF button, int val) {
      if (button.f_93623_) {
         if (button.field_45 == 200) {
            this.minecraft.f_91066_.m_92169_();
            this.minecraft.m_91152_(this.parentGuiScreen);
         }

         if (button.field_45 == 201) {
            this.minecraft.f_91066_.m_92169_();
            GuiDetailSettingsOF scr = new GuiDetailSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.field_45 == 202) {
            this.minecraft.f_91066_.m_92169_();
            GuiQualitySettingsOF scr = new GuiQualitySettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.field_45 == 211) {
            this.minecraft.f_91066_.m_92169_();
            GuiAnimationSettingsOF scr = new GuiAnimationSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.field_45 == 212) {
            this.minecraft.f_91066_.m_92169_();
            GuiPerformanceSettingsOF scr = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.field_45 == 220) {
            this.minecraft.f_91066_.m_92169_();
            GuiQuickInfoOF scr = new GuiQuickInfoOF(this);
            this.minecraft.m_91152_(scr);
         }

         if (button.field_45 == 222) {
            this.minecraft.f_91066_.m_92169_();
            GuiOtherSettingsOF scr = new GuiOtherSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.field_45 == 231) {
            if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
               Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
               return;
            }

            if (Config.isGraphicsFabulous()) {
               Config.showGuiMessage(Lang.get("of.message.shaders.gf1"), Lang.get("of.message.shaders.gf2"));
               return;
            }

            this.minecraft.f_91066_.m_92169_();
            GuiShaders scr = new GuiShaders(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

      }
   }

   public void m_7861_() {
      this.minecraft.f_91066_.m_92169_();
      super.m_7861_();
   }

   public void m_88315_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.m_280653_(this.minecraft.f_91062_, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      this.renderVersion(graphicsIn);
      this.tooltipManager.drawTooltips(graphicsIn, mouseX, mouseY, this.buttonList);
   }

   private void renderVersion(GuiGraphics graphicsIn) {
      graphicsIn.m_280168_().m_85836_();
      graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, -10.0F);
      String ver = Config.getVersion();
      String ed = "HD_U";
      if (ed.equals("HD")) {
         ver = "OptiFine HD J1_pre9";
      }

      if (ed.equals("HD_U")) {
         ver = "OptiFine HD J1_pre9 Ultra";
      }

      if (ed.equals("L")) {
         ver = "OptiFine J1_pre9 Light";
      }

      graphicsIn.m_280488_(this.minecraft.f_91062_, ver, 2, this.f_96544_ - 10, 10526880);
      String verMc = "Minecraft 1.21";
      int lenMc = this.minecraft.f_91062_.m_92895_(verMc);
      graphicsIn.m_280488_(this.minecraft.f_91062_, verMc, this.f_96543_ - lenMc - 2, this.f_96544_ - 10, 10526880);
      graphicsIn.m_280168_().m_85849_();
   }

   public void m_280273_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      graphicsIn.m_280168_().m_85836_();
      graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, -20.0F);
      super.m_280273_(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.m_280168_().m_85849_();
   }

   static {
      TEXT_FABULOUS = Component.m_237115_("options.graphics.fabulous").m_130940_(ChatFormatting.ITALIC);
      TEXT_WARNING_MESSAGE = Component.m_237110_("options.graphics.warning.message", new Object[]{TEXT_FABULOUS, TEXT_FABULOUS});
      TEXT_WARNING_TITLE = Component.m_237115_("options.graphics.warning.title").m_130940_(ChatFormatting.RED);
      TEXT_WARNING_ACCEPT = Component.m_237115_("options.graphics.warning.accept");
      TEXT_WARNING_CANCEL = Component.m_237115_("options.graphics.warning.cancel");
      NEW_LINE = Component.m_237113_("\n");
   }
}
