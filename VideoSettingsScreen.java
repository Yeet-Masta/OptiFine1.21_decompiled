import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.src.C_336564_;
import net.minecraft.src.C_3383_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4127_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_4996_;
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

public class VideoSettingsScreen extends GuiScreenOF {
   private C_3583_ parentGuiScreen;
   private Options guiGameSettings;
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

   public VideoSettingsScreen(C_3583_ par1GuiScreen, C_3391_ minecraftIn, Options par2GameSettings) {
      super(C_4996_.m_237115_("options.videoTitle"));
      this.parentGuiScreen = par1GuiScreen;
      this.guiGameSettings = par2GameSettings;
      this.gpuWarning = this.minecraft.m_91105_();
      this.gpuWarning.m_109252_();
      if (this.guiGameSettings.j().c() == C_3383_.FABULOUS) {
         this.gpuWarning.m_109248_();
      }

      this.minecraft = minecraftIn;
   }

   public void aT_() {
      this.buttonList.clear();
      OptionInstance[] videoOptions = new OptionInstance[]{
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
         OptionInstance opt = videoOptions[i];
         if (opt != null) {
            int x = this.m / 2 - 155 + i % 2 * 160;
            int y = this.n / 6 + 21 * (i / 2) - 12;
            C_3449_ guiElement = (C_3449_)this.c(opt.a(this.minecraft.m, x, y, 150));
            guiElement.m_257544_(null);
            if (opt == this.settings.GUI_SCALE) {
               this.buttonGuiScale = guiElement;
            }
         }
      }

      this.c(new GuiButtonOF(220, this.m / 2 - 155 + 160, this.n / 6 + 105 - 12, 150, 20, C_4513_.m_118938_("of.options.quickInfo", new Object[0])));
      int y = this.n / 6 + 21 * (videoOptions.length / 2) - 12;
      int x = 0;
      x = this.m / 2 - 155 + 0;
      this.c(new GuiScreenButtonOF(231, x, y, Lang.get("of.options.shaders")));
      x = this.m / 2 - 155 + 160;
      this.c(new GuiScreenButtonOF(202, x, y, Lang.get("of.options.quality")));
      y += 21;
      x = this.m / 2 - 155 + 0;
      this.c(new GuiScreenButtonOF(201, x, y, Lang.get("of.options.details")));
      x = this.m / 2 - 155 + 160;
      this.c(new GuiScreenButtonOF(212, x, y, Lang.get("of.options.performance")));
      y += 21;
      x = this.m / 2 - 155 + 0;
      this.c(new GuiScreenButtonOF(211, x, y, Lang.get("of.options.animations")));
      x = this.m / 2 - 155 + 160;
      this.c(new GuiScreenButtonOF(222, x, y, Lang.get("of.options.other")));
      y += 21;
      this.c(new GuiButtonOF(200, this.m / 2 - 100, this.n / 6 + 168 + 11, C_4513_.m_118938_("gui.done", new Object[0])));
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
            this.guiGameSettings.j().a(C_3383_.FABULOUS);
            C_3391_.m_91087_().f.f();
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
         int newScale = this.guiGameSettings.aq().c() - 1;
         if (newScale < 0) {
            newScale = C_3391_.m_91087_().aM().a(0, C_3391_.m_91087_().m_91390_());
         }

         this.settings.GUI_SCALE.a(newScale);
         this.updateGuiScale();
      }
   }

   private void updateGuiScale() {
      this.minecraft.m_5741_();
      Window mw = this.minecraft.aM();
      int btnWidth = GuiUtils.getWidth(this.buttonGuiScale);
      int btnHeight = GuiUtils.getHeight(this.buttonGuiScale);
      int x = this.buttonGuiScale.m_252754_() + (btnWidth - btnHeight);
      int y = this.buttonGuiScale.m_252907_() + btnHeight / 2;
      GLFW.glfwSetCursorPos(mw.j(), (double)x * mw.t(), (double)y * mw.t());
   }

   private void actionPerformed(GuiButtonOF button, int val) {
      if (button.j) {
         if (button.id == 200) {
            this.minecraft.m.aw();
            this.minecraft.m_91152_(this.parentGuiScreen);
         }

         if (button.id == 201) {
            this.minecraft.m.aw();
            GuiDetailSettingsOF scr = new GuiDetailSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 202) {
            this.minecraft.m.aw();
            GuiQualitySettingsOF scr = new GuiQualitySettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 211) {
            this.minecraft.m.aw();
            GuiAnimationSettingsOF scr = new GuiAnimationSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 212) {
            this.minecraft.m.aw();
            GuiPerformanceSettingsOF scr = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 220) {
            this.minecraft.m.aw();
            GuiQuickInfoOF scr = new GuiQuickInfoOF(this);
            this.minecraft.m_91152_(scr);
         }

         if (button.id == 222) {
            this.minecraft.m.aw();
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

            this.minecraft.m.aw();
            GuiShaders scr = new GuiShaders(this, this.guiGameSettings);
            this.minecraft.m_91152_(scr);
         }
      }
   }

   public void j() {
      this.minecraft.m.aw();
      super.j();
   }

   public void a(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.a(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.a(this.minecraft.h, this.k, this.m / 2, 15, 16777215);
      this.renderVersion(graphicsIn);
      this.tooltipManager.drawTooltips(graphicsIn, mouseX, mouseY, this.buttonList);
   }

   private void renderVersion(GuiGraphics graphicsIn) {
      graphicsIn.c().a();
      graphicsIn.c().a(0.0F, 0.0F, -10.0F);
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

      graphicsIn.b(this.minecraft.h, ver, 2, this.n - 10, 10526880);
      String verMc = "Minecraft 1.21";
      int lenMc = this.minecraft.h.b(verMc);
      graphicsIn.b(this.minecraft.h, verMc, this.m - lenMc - 2, this.n - 10, 10526880);
      graphicsIn.c().b();
   }

   public void b(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      graphicsIn.c().a();
      graphicsIn.c().a(0.0F, 0.0F, -20.0F);
      super.b(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.c().b();
   }
}
