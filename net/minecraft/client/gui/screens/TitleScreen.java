package net.minecraft.client.gui.screens;

import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CommonButtons;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;

public class TitleScreen extends Screen {
   private static Logger f_96717_ = LogUtils.getLogger();
   private static Component f_316071_ = Component.m_237115_("narrator.screen.title");
   private static Component f_169438_ = Component.m_237115_("title.credits");
   private static String f_169439_;
   private static float f_314630_;
   @Nullable
   private SplashRenderer f_96721_;
   private Button f_96722_;
   @Nullable
   private RealmsNotificationsScreen f_96726_;
   private float f_315047_ = 1.0F;
   private boolean f_96714_;
   private long f_96715_;
   private LogoRenderer f_263781_;
   private Screen modUpdateNotification;

   public TitleScreen() {
      this(false);
   }

   public TitleScreen(boolean fadeIn) {
      this(fadeIn, null);
   }

   public TitleScreen(boolean fadeIn, @Nullable LogoRenderer logoRendererIn) {
      super(f_316071_);
      this.f_96714_ = fadeIn;
      this.f_263781_ = (LogoRenderer)Objects.requireNonNullElseGet(logoRendererIn, () -> new LogoRenderer(false));
   }

   private boolean m_96789_() {
      return this.f_96726_ != null;
   }

   public void m_86600_() {
      if (this.m_96789_()) {
         this.f_96726_.m_86600_();
      }
   }

   public static CompletableFuture<Void> m_96754_(TextureManager texMngr, Executor backgroundExecutor) {
      return CompletableFuture.allOf(
         texMngr.m_118501_(LogoRenderer.f_263712_, backgroundExecutor),
         texMngr.m_118501_(LogoRenderer.f_263806_, backgroundExecutor),
         texMngr.m_118501_(PanoramaRenderer.f_314014_, backgroundExecutor),
         f_314949_.m_108854_(texMngr, backgroundExecutor)
      );
   }

   public boolean m_7043_() {
      return false;
   }

   public boolean m_6913_() {
      return false;
   }

   protected void m_7856_() {
      if (this.f_96721_ == null) {
         this.f_96721_ = this.f_96541_.m_91310_().m_280369_();
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(new Date());
         int day = calendar.get(5);
         int month = calendar.get(2) + 1;
         if (day == 8 && month == 4) {
            this.f_96721_ = new SplashRenderer("Happy birthday, OptiFine!");
         }

         if (day == 14 && month == 8) {
            this.f_96721_ = new SplashRenderer("Happy birthday, sp614x!");
         }
      }

      int i = this.f_96547_.m_92852_(f_169438_);
      int j = this.f_96543_ - i - 2;
      int k = 24;
      int l = this.f_96544_ / 4 + 48;
      Button modButton = null;
      if (this.f_96541_.m_91402_()) {
         this.m_96772_(l, 24);
      } else {
         this.m_96763_(l, 24);
         if (Reflector.ModListScreen_Constructor.exists()) {
            modButton = ReflectorForge.makeButtonMods(this, l, 24);
            this.m_142416_(modButton);
         }
      }

      SpriteIconButton spriteiconbutton = (SpriteIconButton)this.m_142416_(
         CommonButtons.m_292713_(20, btnIn -> this.f_96541_.m_91152_(new LanguageSelectScreen(this, this.f_96541_.f_91066_, this.f_96541_.m_91102_())), true)
      );
      spriteiconbutton.m_264152_(this.f_96543_ / 2 - 124, l + 72 + 12);
      this.m_142416_(
         Button.m_253074_(Component.m_237115_("menu.options"), btnIn -> this.f_96541_.m_91152_(new OptionsScreen(this, this.f_96541_.f_91066_)))
            .m_252987_(this.f_96543_ / 2 - 100, l + 72 + 12, 98, 20)
            .m_253136_()
      );
      this.m_142416_(
         Button.m_253074_(Component.m_237115_("menu.quit"), btnIn -> this.f_96541_.m_91395_())
            .m_252987_(this.f_96543_ / 2 + 2, l + 72 + 12, 98, 20)
            .m_253136_()
      );
      SpriteIconButton spriteiconbutton1 = (SpriteIconButton)this.m_142416_(
         CommonButtons.m_294306_(20, btnIn -> this.f_96541_.m_91152_(new AccessibilityOptionsScreen(this, this.f_96541_.f_91066_)), true)
      );
      spriteiconbutton1.m_264152_(this.f_96543_ / 2 + 104, l + 72 + 12);
      this.m_142416_(
         new PlainTextButton(j, this.f_96544_ - 10, i, 10, f_169438_, btnIn -> this.f_96541_.m_91152_(new CreditsAndAttributionScreen(this)), this.f_96547_)
      );
      if (this.f_96726_ == null) {
         this.f_96726_ = new RealmsNotificationsScreen();
      }

      if (this.m_96789_()) {
         this.f_96726_.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
      }

      if (Reflector.TitleScreenModUpdateIndicator_init.exists()) {
         this.modUpdateNotification = (Screen)Reflector.m_46374_(Reflector.TitleScreenModUpdateIndicator_init, this, modButton);
      }
   }

   private void m_96763_(int yIn, int rowHeightIn) {
      this.m_142416_(
         Button.m_253074_(Component.m_237115_("menu.singleplayer"), btnIn -> this.f_96541_.m_91152_(new SelectWorldScreen(this)))
            .m_252987_(this.f_96543_ / 2 - 100, yIn, 200, 20)
            .m_253136_()
      );
      Component component = this.m_240255_();
      boolean flag = component == null;
      Tooltip tooltip = component != null ? Tooltip.m_257550_(component) : null;
      ((Button)this.m_142416_(Button.m_253074_(Component.m_237115_("menu.multiplayer"), btnIn -> {
         Screen screen = (Screen)(this.f_96541_.f_91066_.f_92083_ ? new JoinMultiplayerScreen(this) : new SafetyScreen(this));
         this.f_96541_.m_91152_(screen);
      }).m_252987_(this.f_96543_ / 2 - 100, yIn + rowHeightIn * 1, 200, 20).m_257505_(tooltip).m_253136_())).f_93623_ = flag;
      boolean forge = Reflector.ModListScreen_Constructor.exists();
      int realmsX = forge ? this.f_96543_ / 2 + 2 : this.f_96543_ / 2 - 100;
      int realmsWidth = forge ? 98 : 200;
      ((Button)this.m_142416_(
            Button.m_253074_(Component.m_237115_("menu.online"), btnIn -> this.f_96541_.m_91152_(new RealmsMainScreen(this)))
               .m_252987_(realmsX, yIn + rowHeightIn * 2, realmsWidth, 20)
               .m_257505_(tooltip)
               .m_253136_()
         ))
         .f_93623_ = flag;
   }

   @Nullable
   private Component m_240255_() {
      if (this.f_96541_.m_91400_()) {
         return null;
      } else if (this.f_96541_.m_294837_()) {
         return Component.m_237115_("title.multiplayer.disabled.banned.name");
      } else {
         BanDetails bandetails = this.f_96541_.m_239210_();
         if (bandetails != null) {
            return bandetails.expires() != null
               ? Component.m_237115_("title.multiplayer.disabled.banned.temporary")
               : Component.m_237115_("title.multiplayer.disabled.banned.permanent");
         } else {
            return Component.m_237115_("title.multiplayer.disabled");
         }
      }
   }

   private void m_96772_(int yIn, int rowHeightIn) {
      boolean flag = this.m_96792_();
      this.m_142416_(Button.m_253074_(Component.m_237115_("menu.playdemo"), btnIn -> {
         if (flag) {
            this.f_96541_.m_231466_().m_320872_("Demo_World", () -> this.f_96541_.m_91152_(this));
         } else {
            this.f_96541_.m_231466_().m_233157_("Demo_World", MinecraftServer.f_129743_, WorldOptions.f_244225_, WorldPresets::m_246552_, this);
         }
      }).m_252987_(this.f_96543_ / 2 - 100, yIn, 200, 20).m_253136_());
      this.f_96722_ = (Button)this.m_142416_(
         Button.m_253074_(
               Component.m_237115_("menu.resetdemo"),
               btnIn -> {
                  LevelStorageSource levelstoragesource = this.f_96541_.m_91392_();

                  try {
                     LevelStorageAccess levelstoragesource$levelstorageaccess = levelstoragesource.m_78260_("Demo_World");

                     try {
                        if (levelstoragesource$levelstorageaccess.m_306456_()) {
                           this.f_96541_
                              .m_91152_(
                                 new ConfirmScreen(
                                    this::m_96777_,
                                    Component.m_237115_("selectWorld.deleteQuestion"),
                                    Component.m_237110_("selectWorld.deleteWarning", new Object[]{MinecraftServer.f_129743_.m_46917_()}),
                                    Component.m_237115_("selectWorld.deleteButton"),
                                    CommonComponents.f_130656_
                                 )
                              );
                        }
                     } catch (Throwable var7) {
                        if (levelstoragesource$levelstorageaccess != null) {
                           try {
                              levelstoragesource$levelstorageaccess.close();
                           } catch (Throwable var6) {
                              var7.addSuppressed(var6);
                           }
                        }

                        throw var7;
                     }

                     if (levelstoragesource$levelstorageaccess != null) {
                        levelstoragesource$levelstorageaccess.close();
                     }
                  } catch (IOException var8) {
                     SystemToast.m_94852_(this.f_96541_, "Demo_World");
                     f_96717_.warn("Failed to access demo world", var8);
                  }
               }
            )
            .m_252987_(this.f_96543_ / 2 - 100, yIn + rowHeightIn * 1, 200, 20)
            .m_253136_()
      );
      this.f_96722_.f_93623_ = flag;
   }

   private boolean m_96792_() {
      try {
         LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_96541_.m_91392_().m_78260_("Demo_World");

         boolean flag;
         try {
            flag = levelstoragesource$levelstorageaccess.m_306456_();
         } catch (Throwable var6) {
            if (levelstoragesource$levelstorageaccess != null) {
               try {
                  levelstoragesource$levelstorageaccess.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (levelstoragesource$levelstorageaccess != null) {
            levelstoragesource$levelstorageaccess.close();
         }

         return flag;
      } catch (IOException var7) {
         SystemToast.m_94852_(this.f_96541_, "Demo_World");
         f_96717_.warn("Failed to read demo world data", var7);
         return false;
      }
   }

   public void m_88315_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.f_96715_ == 0L && this.f_96714_) {
         this.f_96715_ = Util.m_137550_();
      }

      float f = 1.0F;
      GlStateManager._disableDepthTest();
      if (this.f_96714_) {
         float f1 = (float)(Util.m_137550_() - this.f_96715_) / 2000.0F;
         if (f1 > 1.0F) {
            this.f_96714_ = false;
            this.f_315047_ = 1.0F;
         } else {
            f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
            f = Mth.m_184631_(f1, 0.5F, 1.0F, 0.0F, 1.0F);
            this.f_315047_ = Mth.m_184631_(f1, 0.0F, 0.5F, 0.0F, 1.0F);
         }

         this.m_320273_(f);
      }

      this.m_318720_(graphicsIn, partialTicks);
      int i = Mth.m_14167_(f * 255.0F) << 24;
      if ((i & -67108864) != 0) {
         super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
         this.f_263781_.m_280037_(graphicsIn, this.f_96543_, f);
         if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_renderMainMenu, this, graphicsIn, this.f_96547_, this.f_96543_, this.f_96544_, i);
         }

         if (this.f_96721_ != null && !this.f_96541_.f_91066_.m_307023_().m_231551_()) {
            this.f_96721_.m_280672_(graphicsIn, this.f_96543_, this.f_96547_, i);
         }

         String s = "Minecraft " + SharedConstants.m_183709_().m_132493_();
         if (this.f_96541_.m_91402_()) {
            s = s + " Demo";
         } else {
            s = s + ("release".equalsIgnoreCase(this.f_96541_.m_91389_()) ? "" : "/" + this.f_96541_.m_91389_());
         }

         if (Minecraft.m_193589_().m_184597_()) {
            s = s + I18n.m_118938_("menu.modded", new Object[0]);
         }

         if (Reflector.BrandingControl.exists()) {
            if (Reflector.BrandingControl_forEachLine.exists()) {
               BiConsumer<Integer, String> lineConsumer = (brdline, brd) -> graphicsIn.m_280488_(
                     this.f_96547_, brd, 2, this.f_96544_ - (10 + brdline * (9 + 1)), 16777215 | i
                  );
               Reflector.m_46374_(Reflector.BrandingControl_forEachLine, true, true, lineConsumer);
            }

            if (Reflector.BrandingControl_forEachAboveCopyrightLine.exists()) {
               BiConsumer<Integer, String> lineConsumer = (brdline, brd) -> graphicsIn.m_280488_(
                     this.f_96547_, brd, this.f_96543_ - this.f_96547_.m_92895_(brd), this.f_96544_ - (10 + (brdline + 1) * (9 + 1)), 16777215 | i
                  );
               Reflector.m_46374_(Reflector.BrandingControl_forEachAboveCopyrightLine, lineConsumer);
            }
         } else {
            graphicsIn.m_280488_(this.f_96547_, s, 2, this.f_96544_ - 10, 16777215 | i);
         }

         if (this.m_96789_() && f >= 1.0F) {
            RenderSystem.enableDepthTest();
            this.f_96726_.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
         }
      }

      if (this.modUpdateNotification != null && f >= 1.0F) {
         this.modUpdateNotification.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      }
   }

   private void m_320273_(float alphaIn) {
      for (GuiEventListener guieventlistener : this.m_6702_()) {
         if (guieventlistener instanceof AbstractWidget abstractwidget) {
            abstractwidget.m_93650_(alphaIn);
         }
      }
   }

   public void m_280273_(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
   }

   protected void m_318720_(GuiGraphics graphicsIn, float partialTicks) {
      f_317031_.m_110003_(graphicsIn, this.f_96543_, this.f_96544_, this.f_315047_, partialTicks);
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      return super.m_6375_(mouseX, mouseY, button) ? true : this.m_96789_() && this.f_96726_.m_6375_(mouseX, mouseY, button);
   }

   public void m_7861_() {
      if (this.f_96726_ != null) {
         this.f_96726_.m_7861_();
      }
   }

   public void m_274333_() {
      super.m_274333_();
      if (this.f_96726_ != null) {
         this.f_96726_.m_274333_();
      }
   }

   private void m_96777_(boolean confirmIn) {
      if (confirmIn) {
         try {
            LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_96541_.m_91392_().m_78260_("Demo_World");

            try {
               levelstoragesource$levelstorageaccess.m_78311_();
            } catch (Throwable var6) {
               if (levelstoragesource$levelstorageaccess != null) {
                  try {
                     levelstoragesource$levelstorageaccess.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (levelstoragesource$levelstorageaccess != null) {
               levelstoragesource$levelstorageaccess.close();
            }
         } catch (IOException var7) {
            SystemToast.m_94866_(this.f_96541_, "Demo_World");
            f_96717_.warn("Failed to delete demo world", var7);
         }
      }

      this.f_96541_.m_91152_(this);
   }
}
