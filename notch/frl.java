package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.src.C_336564_.C_336590_;
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

public class C_336491_ extends GuiScreenOF {
   private C_3583_ parentGuiScreen;
   private C_3401_ guiGameSettings;
   private C_4127_ gpuWarning;
   private static final C_4996_ TEXT_FABULOUS = C_4996_.m_237115_("options.graphics.fabulous").m_130940_(C_4856_.ITALIC);
   private static final C_4996_ TEXT_WARNING_MESSAGE = C_4996_.m_237110_("options.graphics.warning.message", new Object[]{TEXT_FABULOUS, TEXT_FABULOUS});
   private static final C_4996_ TEXT_WARNING_TITLE = C_4996_.m_237115_("options.graphics.warning.title").m_130940_(C_4856_.RED);
   private static final C_4996_ TEXT_WARNING_ACCEPT = C_4996_.m_237115_("options.graphics.warning.accept");
   private static final C_4996_ TEXT_WARNING_CANCEL = C_4996_.m_237115_("options.graphics.warning.cancel");
   private static final C_4996_ NEW_LINE = C_4996_.m_237113_("\n");
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
   private List<C_3449_> buttonList = this.getButtonList();
   private C_3449_ buttonGuiScale;
   private C_3391_ minecraft = C_3391_.m_91087_();

   public C_336491_(C_3583_ par1GuiScreen, C_3391_ minecraftIn, C_3401_ par2GameSettings) {
      super(C_4996_.m_237115_("options.videoTitle"));
      this.parentGuiScreen = par1GuiScreen;
      this.guiGameSettings = par2GameSettings;
      this.gpuWarning = this.minecraft.m_91105_();
      this.gpuWarning.m_109252_();
      if (this.guiGameSettings.m_232060_().m_231551_() == C_3383_.FABULOUS) {
         this.gpuWarning.m_109248_();
      }

      this.minecraft = minecraftIn;
   }

   public void m_7856_() {
      this.buttonList.clear();
      C_213334_[] videoOptions = new C_213334_[]{
         this.settings.GRAPHICS,
         this.settings.RENDER_DISTANCE,
         this.settings.AO,
         this.settings.SIMULATION_DISTANCE,
         Option.AO_LEVEL,
         this.settings.FRAMERATE_LIMIT,
         this.settings.GUI_SCALE,
         this.settings.ENTITY_SHADOWS,
         this.settings.GAMMA,
         Option.DYNAMIC_FOV,
         Option.DYNAMIC_LIGHTS,
         null
      };

      for (int i = 0; i < videoOptions.length; i++) {
         C_213334_ opt = videoOptions[i];
         if (opt != null) {
            int x = this.f_96543_ / 2 - 155 + i % 2 * 160;
            int y = this.f_96544_ / 6 + 21 * (i / 2) - 12;
            C_3449_ guiElement = (C_3449_)this.m_142416_(opt.m_231507_(this.minecraft.f_91066_, x, y, 150));
            guiElement.m_257544_(null);
            if (opt == this.settings.GUI_SCALE) {
               this.buttonGuiScale = guiElement;
            }
         }
      }

      this.m_142416_(
         new GuiButtonOF(220, this.f_96543_ / 2 - 155 + 160, this.f_96544_ / 6 + 105 - 12, 150, 20, C_4513_.m_118938_("of.options.quickInfo", new Object[0]))
      );
      int y = this.f_96544_ / 6 + 21 * (videoOptions.length / 2) - 12;
      int x = 0;
      x = this.f_96543_ / 2 - 155 + 0;
      this.m_142416_(new GuiScreenButtonOF(231, x, y, Lang.get("of.options.shaders")));
      x = this.f_96543_ / 2 - 155 + 160;
      this.m_142416_(new GuiScreenButtonOF(202, x, y, Lang.get("of.options.quality")));
      y += 21;
      x = this.f_96543_ / 2 - 155 + 0;
      this.m_142416_(new GuiScreenButtonOF(201, x, y, Lang.get("of.options.details")));
      x = this.f_96543_ / 2 - 155 + 160;
      this.m_142416_(new GuiScreenButtonOF(212, x, y, Lang.get("of.options.performance")));
      y += 21;
      x = this.f_96543_ / 2 - 155 + 0;
      this.m_142416_(new GuiScreenButtonOF(211, x, y, Lang.get("of.options.animations")));
      x = this.f_96543_ / 2 - 155 + 160;
      this.m_142416_(new GuiScreenButtonOF(222, x, y, Lang.get("of.options.other")));
      y += 21;
      this.m_142416_(new GuiButtonOF(200, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168 + 11, C_4513_.m_118938_("gui.done", new Object[0])));
      this.buttonList = this.getButtonList();
   }

   protected void actionPerformed(C_3449_ button) {
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
         List<C_4996_> list = Lists.newArrayList(new C_4996_[]{TEXT_WARNING_MESSAGE, NEW_LINE});
         String s = this.gpuWarning.m_109253_();
         if (s != null) {
            list.add(NEW_LINE);
            list.add(C_4996_.m_237110_("options.graphics.warning.renderer", new Object[]{s}).m_130940_(C_4856_.GRAY));
         }

         String s1 = this.gpuWarning.m_109255_();
         if (s1 != null) {
            list.add(NEW_LINE);
            list.add(C_4996_.m_237110_("options.graphics.warning.vendor", new Object[]{s1}).m_130940_(C_4856_.GRAY));
         }

         String s2 = this.gpuWarning.m_109254_();
         if (s2 != null) {
            list.add(NEW_LINE);
            list.add(C_4996_.m_237110_("options.graphics.warning.version", new Object[]{s2}).m_130940_(C_4856_.GRAY));
         }

         this.minecraft.m_91152_(new C_336564_(TEXT_WARNING_TITLE, list, ImmutableList.of(new C_336590_(TEXT_WARNING_ACCEPT, btn -> {
            this.guiGameSettings.m_232060_().m_231514_(C_3383_.FABULOUS);
            C_3391_.m_91087_().f_91060_.m_109818_();
            this.gpuWarning.m_109248_();
            this.minecraft.m_91152_(this);
         }), new C_336590_(TEXT_WARNING_CANCEL, btn -> {
            this.gpuWarning.m_109249_();
            this.minecraft.m_91152_(this);
         }))));
      }
   }

   protected void actionPerformedRightClick(C_3449_ button) {
      if (button == this.buttonGuiScale) {
         int newScale = this.guiGameSettings.m_231928_().m_231551_() - 1;
         if (newScale < 0) {
            newScale = C_3391_.m_91087_().m_91268_().m_85385_(0, C_3391_.m_91087_().m_91390_());
         }

         this.settings.GUI_SCALE.m_231514_(newScale);
         this.updateGuiScale();
      }
   }

   private void updateGuiScale() {
      this.minecraft.m_5741_();
      C_3161_ mw = this.minecraft.m_91268_();
      int btnWidth = GuiUtils.getWidth(this.buttonGuiScale);
      int btnHeight = GuiUtils.getHeight(this.buttonGuiScale);
      int x = this.buttonGuiScale.m_252754_() + (btnWidth - btnHeight);
      int y = this.buttonGuiScale.m_252907_() + btnHeight / 2;
      GLFW.glfwSetCursorPos(mw.m_85439_(), (double)x * mw.m_85449_(), (double)y * mw.m_85449_());
   }

   private void actionPerformed(GuiButtonOF button, int val) {
      if (button.j) {
         if (button.id == 200) {
            this.minecraft.f_91066_.m_92169_();
            this.minecraft.m_91152_(this.parentGuiScreen);
         }

         if (button.id == 201) {
            this.minecraft.f_91066_.m_92169_();
            GuiDetailSettingsOF scr = new GuiDetailSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 202) {
            this.minecraft.f_91066_.m_92169_();
            GuiQualitySettingsOF scr = new GuiQualitySettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 211) {
            this.minecraft.f_91066_.m_92169_();
            GuiAnimationSettingsOF scr = new GuiAnimationSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 212) {
            this.minecraft.f_91066_.m_92169_();
            GuiPerformanceSettingsOF scr = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 220) {
            this.minecraft.f_91066_.m_92169_();
            GuiQuickInfoOF scr = new GuiQuickInfoOF(this);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 222) {
            this.minecraft.f_91066_.m_92169_();
            GuiOtherSettingsOF scr = new GuiOtherSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 231) {
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

   public void m_88315_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.m_280653_(this.minecraft.f_91062_, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      this.renderVersion(graphicsIn);
      this.tooltipManager.drawTooltips(graphicsIn, mouseX, mouseY, this.buttonList);
   }

   private void renderVersion(C_279497_ graphicsIn) {
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

   public void m_280273_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      graphicsIn.m_280168_().m_85836_();
      graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, -20.0F);
      super.m_280273_(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.m_280168_().m_85849_();
   }
}
