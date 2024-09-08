package net.optifine.shaders.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5322_;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderEnumShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.config.EnumShaderOption;

public class GuiShaders extends GuiScreenOF {
   protected C_3583_ parentGui;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderEnumShaderOptions());
   private int updateTimer = -1;
   private GuiSlotShaders shaderList;
   private boolean saved = false;
   private static float[] QUALITY_MULTIPLIERS = new float[]{
      0.5F, 0.6F, 0.6666667F, 0.75F, 0.8333333F, 0.9F, 1.0F, 1.1666666F, 1.3333334F, 1.5F, 1.6666666F, 1.8F, 2.0F
   };
   private static String[] QUALITY_MULTIPLIER_NAMES = new String[]{
      "0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", "1.66x", "1.8x", "2x"
   };
   private static float QUALITY_MULTIPLIER_DEFAULT = 1.0F;
   private static float[] HAND_DEPTH_VALUES = new float[]{0.0625F, 0.125F, 0.25F};
   private static String[] HAND_DEPTH_NAMES = new String[]{"0.5x", "1x", "2x"};
   private static float HAND_DEPTH_DEFAULT = 0.125F;
   public static final int EnumOS_UNKNOWN = 0;
   public static final int EnumOS_WINDOWS = 1;
   public static final int EnumOS_OSX = 2;
   public static final int EnumOS_SOLARIS = 3;
   public static final int EnumOS_LINUX = 4;

   public GuiShaders(C_3583_ par1GuiScreen, C_3401_ par2GameSettings) {
      super(C_4996_.m_237113_(C_4513_.m_118938_("of.options.shadersTitle", new Object[0])));
      this.parentGui = par1GuiScreen;
   }

   public void aT_() {
      if (Shaders.shadersConfig == null) {
         Shaders.loadConfig();
      }

      int btnWidth = 120;
      int btnHeight = 20;
      int btnX = this.m - btnWidth - 10;
      int baseY = 30;
      int stepY = 20;
      int shaderListWidth = this.m - btnWidth - 20;
      this.shaderList = new GuiSlotShaders(this, shaderListWidth, this.n, baseY, this.n - 50, 16);
      this.d(this.shaderList);
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, btnX, 0 * stepY + baseY, btnWidth, btnHeight));
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, btnX, 1 * stepY + baseY, btnWidth, btnHeight));
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, btnX, 2 * stepY + baseY, btnWidth, btnHeight));
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, btnX, 3 * stepY + baseY, btnWidth, btnHeight));
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, btnX, 4 * stepY + baseY, btnWidth, btnHeight));
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, btnX, 5 * stepY + baseY, btnWidth, btnHeight));
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, btnX, 6 * stepY + baseY, btnWidth, btnHeight));
      this.c(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, btnX, 7 * stepY + baseY, btnWidth, btnHeight));
      int btnFolderWidth = Math.min(150, shaderListWidth / 2 - 10);
      int xFolder = shaderListWidth / 4 - btnFolderWidth / 2;
      int yFolder = this.n - 25;
      this.c(new GuiButtonOF(201, xFolder, yFolder, btnFolderWidth - 22 + 1, btnHeight, Lang.get("of.options.shaders.shadersFolder")));
      this.c(new GuiButtonDownloadShaders(210, xFolder + btnFolderWidth - 22 - 1, yFolder));
      this.c(
         new GuiButtonOF(
            202, shaderListWidth / 4 * 3 - btnFolderWidth / 2, this.n - 25, btnFolderWidth, btnHeight, C_4513_.m_118938_("gui.done", new Object[0])
         )
      );
      this.c(new GuiButtonOF(203, btnX, this.n - 25, btnWidth, btnHeight, Lang.get("of.options.shaders.shaderOptions")));
      this.a(this.shaderList);
      this.updateButtons();
   }

   public void updateButtons() {
      boolean shaderActive = Config.isShaders();

      for (C_3449_ guiElement : this.getButtonList()) {
         if (guiElement instanceof GuiButtonOF) {
            GuiButtonOF button = (GuiButtonOF)guiElement;
            if (button.id != 201 && button.id != 202 && button.id != 210 && button.id != EnumShaderOption.ANTIALIASING.ordinal()) {
               button.j = shaderActive;
            }
         }
      }
   }

   protected void actionPerformed(C_3449_ button) {
      this.actionPerformed(button, false);
   }

   protected void actionPerformedRightClick(C_3449_ button) {
      this.actionPerformed(button, true);
   }

   private void actionPerformed(C_3449_ guiElement, boolean rightClick) {
      if (guiElement.f_93623_) {
         if (guiElement instanceof GuiButtonEnumShaderOption gbeso) {
            switch (gbeso.getEnumShaderOption()) {
               case ANTIALIASING:
                  Shaders.nextAntialiasingLevel(!rightClick);
                  if (s()) {
                     Shaders.configAntialiasingLevel = 0;
                  }

                  Shaders.uninit();
                  break;
               case NORMAL_MAP:
                  Shaders.configNormalMap = !Shaders.configNormalMap;
                  if (s()) {
                     Shaders.configNormalMap = true;
                  }

                  Shaders.uninit();
                  this.l.m_91088_();
                  break;
               case SPECULAR_MAP:
                  Shaders.configSpecularMap = !Shaders.configSpecularMap;
                  if (s()) {
                     Shaders.configSpecularMap = true;
                  }

                  Shaders.uninit();
                  this.l.m_91088_();
                  break;
               case RENDER_RES_MUL:
                  Shaders.configRenderResMul = this.getNextValue(Shaders.configRenderResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !rightClick, s());
                  Shaders.uninit();
                  Shaders.scheduleResize();
                  break;
               case SHADOW_RES_MUL:
                  Shaders.configShadowResMul = this.getNextValue(Shaders.configShadowResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !rightClick, s());
                  Shaders.uninit();
                  Shaders.scheduleResizeShadow();
                  break;
               case HAND_DEPTH_MUL:
                  Shaders.configHandDepthMul = this.getNextValue(Shaders.configHandDepthMul, HAND_DEPTH_VALUES, HAND_DEPTH_DEFAULT, !rightClick, s());
                  Shaders.uninit();
                  break;
               case OLD_HAND_LIGHT:
                  Shaders.configOldHandLight.nextValue(!rightClick);
                  if (s()) {
                     Shaders.configOldHandLight.resetValue();
                  }

                  Shaders.uninit();
                  break;
               case OLD_LIGHTING:
                  Shaders.configOldLighting.nextValue(!rightClick);
                  if (s()) {
                     Shaders.configOldLighting.resetValue();
                  }

                  Shaders.updateBlockLightLevel();
                  Shaders.uninit();
                  this.l.m_91088_();
                  break;
               case TWEAK_BLOCK_DAMAGE:
                  Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                  break;
               case CLOUD_SHADOW:
                  Shaders.configCloudShadow = !Shaders.configCloudShadow;
                  break;
               case TEX_MIN_FIL_B:
                  Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                  Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
                  gbeso.setMessage("Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB]);
                  ShadersTex.updateTextureMinMagFilter();
                  break;
               case TEX_MAG_FIL_N:
                  Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                  gbeso.setMessage("Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN]);
                  ShadersTex.updateTextureMinMagFilter();
                  break;
               case TEX_MAG_FIL_S:
                  Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                  gbeso.setMessage("Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS]);
                  ShadersTex.updateTextureMinMagFilter();
                  break;
               case SHADOW_CLIP_FRUSTRUM:
                  Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                  gbeso.setMessage("ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum));
                  ShadersTex.updateTextureMinMagFilter();
            }

            gbeso.updateButtonText();
         } else if (!rightClick) {
            if (guiElement instanceof GuiButtonOF button) {
               switch (button.id) {
                  case 201:
                     switch (getOSType()) {
                        case 1:
                           String var2x = String.format("cmd.exe /C start \"Open file\" \"%s\"", Shaders.shaderPacksDir.getAbsolutePath());

                           try {
                              Runtime.getRuntime().exec(var2x);
                              return;
                           } catch (IOException var9) {
                              var9.printStackTrace();
                              break;
                           }
                        case 2:
                           try {
                              Runtime.getRuntime().exec(new String[]{"/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath()});
                              return;
                           } catch (IOException var10) {
                              var10.printStackTrace();
                           }
                     }

                     boolean var8 = false;

                     try {
                        URI uri = new File(this.l.f_91069_, "shaderpacks").toURI();
                        C_5322_.m_137581_().m_137648_(uri);
                     } catch (Throwable var8x) {
                        var8x.printStackTrace();
                        var8 = true;
                     }

                     if (var8) {
                        Config.dbg("Opening via system class!");
                        C_5322_.m_137581_().m_137646_("file://" + Shaders.shaderPacksDir.getAbsolutePath());
                     }
                     break;
                  case 202:
                     Shaders.storeConfig();
                     this.saved = true;
                     this.l.m_91152_(this.parentGui);
                     break;
                  case 203:
                     GuiShaderOptions gui = new GuiShaderOptions(this, Config.getGameSettings());
                     Config.getMinecraft().m_91152_(gui);
                  case 204:
                  case 205:
                  case 206:
                  case 207:
                  case 208:
                  case 209:
                  default:
                     break;
                  case 210:
                     try {
                        URI uri = new URI("http://optifine.net/shaderPacks");
                        C_5322_.m_137581_().m_137648_(uri);
                     } catch (Throwable var7) {
                        var7.printStackTrace();
                     }
               }
            }
         }
      }
   }

   public void j() {
      if (!this.saved) {
         Shaders.storeConfig();
         this.saved = true;
      }

      super.j();
   }

   public void a(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.b(graphicsIn, mouseX, mouseY, partialTicks);
      this.shaderList.render(graphicsIn, mouseX, mouseY, partialTicks);
      if (this.updateTimer <= 0) {
         this.shaderList.updateList();
         this.updateTimer += 20;
      }

      drawCenteredString(graphicsIn, this.fontRenderer, this.k, this.m / 2, 15, 16777215);
      String info = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
      int infoWidth = this.fontRenderer.m_92895_(info);
      if (infoWidth < this.m - 5) {
         drawCenteredString(graphicsIn, this.fontRenderer, info, this.m / 2, this.n - 40, 10526880);
      } else {
         drawString(graphicsIn, this.fontRenderer, info, 5, this.n - 40, 10526880);
      }

      super.a(graphicsIn, mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(graphicsIn, mouseX, mouseY, this.getButtonList());
   }

   public void b(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
   }

   public void e() {
      super.e();
      this.updateTimer--;
   }

   public C_3391_ getMc() {
      return this.l;
   }

   public void drawCenteredString(C_279497_ graphicsIn, String text, int x, int y, int color) {
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
            if (++index >= values.length) {
               index = 0;
            }
         } else if (--index < 0) {
            index = values.length - 1;
         }

         return values[index];
      }
   }

   public static int getValueIndex(float val, float[] values) {
      for (int i = 0; i < values.length; i++) {
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
