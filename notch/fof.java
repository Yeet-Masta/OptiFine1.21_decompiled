package net.minecraft.src;

import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.C_2785_.C_2786_;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;

public class C_3588_ extends C_3583_ {
   private static final Logger f_96717_ = LogUtils.getLogger();
   private static final C_4996_ f_316071_ = C_4996_.m_237115_("narrator.screen.title");
   private static final C_4996_ f_169438_ = C_4996_.m_237115_("title.credits");
   private static final String f_169439_ = "Demo_World";
   private static final float f_314630_ = 2000.0F;
   @Nullable
   private C_279516_ f_96721_;
   private C_3451_ f_96722_;
   @Nullable
   private C_3304_ f_96726_;
   private float f_315047_ = 1.0F;
   private boolean f_96714_;
   private long f_96715_;
   private final C_263613_ f_263781_;
   private C_3583_ modUpdateNotification;

   public C_3588_() {
      this(false);
   }

   public C_3588_(boolean fadeIn) {
      this(fadeIn, null);
   }

   public C_3588_(boolean fadeIn, @Nullable C_263613_ logoRendererIn) {
      super(f_316071_);
      this.f_96714_ = fadeIn;
      this.f_263781_ = (C_263613_)Objects.requireNonNullElseGet(logoRendererIn, () -> new C_263613_(false));
   }

   private boolean m_96789_() {
      return this.f_96726_ != null;
   }

   public void m_86600_() {
      if (this.m_96789_()) {
         this.f_96726_.m_86600_();
      }
   }

   public static CompletableFuture<Void> m_96754_(C_4490_ texMngr, Executor backgroundExecutor) {
      return CompletableFuture.allOf(
         texMngr.m_118501_(C_263613_.f_263712_, backgroundExecutor),
         texMngr.m_118501_(C_263613_.f_263806_, backgroundExecutor),
         texMngr.m_118501_(C_4144_.f_314014_, backgroundExecutor),
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
            this.f_96721_ = new C_279516_("Happy birthday, OptiFine!");
         }

         if (day == 14 && month == 8) {
            this.f_96721_ = new C_279516_("Happy birthday, sp614x!");
         }
      }

      int i = this.f_96547_.m_92852_(f_169438_);
      int j = this.f_96543_ - i - 2;
      int k = 24;
      int l = this.f_96544_ / 4 + 48;
      C_3451_ modButton = null;
      if (this.f_96541_.m_91402_()) {
         this.m_96772_(l, 24);
      } else {
         this.m_96763_(l, 24);
         if (Reflector.ModListScreen_Constructor.exists()) {
            modButton = ReflectorForge.makeButtonMods(this, l, 24);
            this.m_142416_(modButton);
         }
      }

      C_290288_ spriteiconbutton = (C_290288_)this.m_142416_(
         C_267340_.m_292713_(20, btnIn -> this.f_96541_.m_91152_(new C_336602_(this, this.f_96541_.f_91066_, this.f_96541_.m_91102_())), true)
      );
      spriteiconbutton.c(this.f_96543_ / 2 - 124, l + 72 + 12);
      this.m_142416_(
         C_3451_.m_253074_(C_4996_.m_237115_("menu.options"), btnIn -> this.f_96541_.m_91152_(new C_336537_(this, this.f_96541_.f_91066_)))
            .m_252987_(this.f_96543_ / 2 - 100, l + 72 + 12, 98, 20)
            .m_253136_()
      );
      this.m_142416_(
         C_3451_.m_253074_(C_4996_.m_237115_("menu.quit"), btnIn -> this.f_96541_.m_91395_()).m_252987_(this.f_96543_ / 2 + 2, l + 72 + 12, 98, 20).m_253136_()
      );
      C_290288_ spriteiconbutton1 = (C_290288_)this.m_142416_(
         C_267340_.m_294306_(20, btnIn -> this.f_96541_.m_91152_(new C_336464_(this, this.f_96541_.f_91066_)), true)
      );
      spriteiconbutton1.c(this.f_96543_ / 2 + 104, l + 72 + 12);
      this.m_142416_(new C_211143_(j, this.f_96544_ - 10, i, 10, f_169438_, btnIn -> this.f_96541_.m_91152_(new C_276132_(this)), this.f_96547_));
      if (this.f_96726_ == null) {
         this.f_96726_ = new C_3304_();
      }

      if (this.m_96789_()) {
         this.f_96726_.b(this.f_96541_, this.f_96543_, this.f_96544_);
      }

      if (Reflector.TitleScreenModUpdateIndicator_init.exists()) {
         this.modUpdateNotification = (C_3583_)Reflector.call(Reflector.TitleScreenModUpdateIndicator_init, this, modButton);
      }
   }

   private void m_96763_(int yIn, int rowHeightIn) {
      this.m_142416_(
         C_3451_.m_253074_(C_4996_.m_237115_("menu.singleplayer"), btnIn -> this.f_96541_.m_91152_(new C_3757_(this)))
            .m_252987_(this.f_96543_ / 2 - 100, yIn, 200, 20)
            .m_253136_()
      );
      C_4996_ component = this.m_240255_();
      boolean flag = component == null;
      C_256714_ tooltip = component != null ? C_256714_.m_257550_(component) : null;
      ((C_3451_)this.m_142416_(C_3451_.m_253074_(C_4996_.m_237115_("menu.multiplayer"), btnIn -> {
         C_3583_ screen = (C_3583_)(this.f_96541_.f_91066_.f_92083_ ? new C_3694_(this) : new C_3695_(this));
         this.f_96541_.m_91152_(screen);
      }).m_252987_(this.f_96543_ / 2 - 100, yIn + rowHeightIn * 1, 200, 20).m_257505_(tooltip).m_253136_())).j = flag;
      boolean forge = Reflector.ModListScreen_Constructor.exists();
      int realmsX = forge ? this.f_96543_ / 2 + 2 : this.f_96543_ / 2 - 100;
      int realmsWidth = forge ? 98 : 200;
      ((C_3451_)this.m_142416_(
            C_3451_.m_253074_(C_4996_.m_237115_("menu.online"), btnIn -> this.f_96541_.m_91152_(new C_3197_(this)))
               .m_252987_(realmsX, yIn + rowHeightIn * 2, realmsWidth, 20)
               .m_257505_(tooltip)
               .m_253136_()
         ))
         .j = flag;
   }

   @Nullable
   private C_4996_ m_240255_() {
      if (this.f_96541_.m_91400_()) {
         return null;
      } else if (this.f_96541_.m_294837_()) {
         return C_4996_.m_237115_("title.multiplayer.disabled.banned.name");
      } else {
         BanDetails bandetails = this.f_96541_.m_239210_();
         if (bandetails != null) {
            return bandetails.expires() != null
               ? C_4996_.m_237115_("title.multiplayer.disabled.banned.temporary")
               : C_4996_.m_237115_("title.multiplayer.disabled.banned.permanent");
         } else {
            return C_4996_.m_237115_("title.multiplayer.disabled");
         }
      }
   }

   private void m_96772_(int yIn, int rowHeightIn) {
      boolean flag = this.m_96792_();
      this.m_142416_(C_3451_.m_253074_(C_4996_.m_237115_("menu.playdemo"), btnIn -> {
         if (flag) {
            this.f_96541_.m_231466_().m_320872_("Demo_World", () -> this.f_96541_.m_91152_(this));
         } else {
            this.f_96541_.m_231466_().m_233157_("Demo_World", MinecraftServer.f_129743_, C_243453_.f_244225_, C_213160_::m_246552_, this);
         }
      }).m_252987_(this.f_96543_ / 2 - 100, yIn, 200, 20).m_253136_());
      this.f_96722_ = (C_3451_)this.m_142416_(
         C_3451_.m_253074_(
               C_4996_.m_237115_("menu.resetdemo"),
               btnIn -> {
                  C_2785_ levelstoragesource = this.f_96541_.m_91392_();

                  try {
                     C_2786_ levelstoragesource$levelstorageaccess = levelstoragesource.m_78260_("Demo_World");

                     try {
                        if (levelstoragesource$levelstorageaccess.m_306456_()) {
                           this.f_96541_
                              .m_91152_(
                                 new C_3541_(
                                    this::m_96777_,
                                    C_4996_.m_237115_("selectWorld.deleteQuestion"),
                                    C_4996_.m_237110_("selectWorld.deleteWarning", new Object[]{MinecraftServer.f_129743_.m_46917_()}),
                                    C_4996_.m_237115_("selectWorld.deleteButton"),
                                    C_4995_.f_130656_
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
                     C_3499_.m_94852_(this.f_96541_, "Demo_World");
                     f_96717_.warn("Failed to access demo world", var8);
                  }
               }
            )
            .m_252987_(this.f_96543_ / 2 - 100, yIn + rowHeightIn * 1, 200, 20)
            .m_253136_()
      );
      this.f_96722_.j = flag;
   }

   private boolean m_96792_() {
      try {
         C_2786_ levelstoragesource$levelstorageaccess = this.f_96541_.m_91392_().m_78260_("Demo_World");

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
         C_3499_.m_94852_(this.f_96541_, "Demo_World");
         f_96717_.warn("Failed to read demo world data", var7);
         return false;
      }
   }

   public void m_88315_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.f_96715_ == 0L && this.f_96714_) {
         this.f_96715_ = C_5322_.m_137550_();
      }

      float f = 1.0F;
      GlStateManager._disableDepthTest();
      if (this.f_96714_) {
         float f1 = (float)(C_5322_.m_137550_() - this.f_96715_) / 2000.0F;
         if (f1 > 1.0F) {
            this.f_96714_ = false;
            this.f_315047_ = 1.0F;
         } else {
            f1 = C_188_.m_14036_(f1, 0.0F, 1.0F);
            f = C_188_.m_184631_(f1, 0.5F, 1.0F, 0.0F, 1.0F);
            this.f_315047_ = C_188_.m_184631_(f1, 0.0F, 0.5F, 0.0F, 1.0F);
         }

         this.m_320273_(f);
      }

      this.m_318720_(graphicsIn, partialTicks);
      int i = C_188_.m_14167_(f * 255.0F) << 24;
      if ((i & -67108864) != 0) {
         super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
         this.f_263781_.m_280037_(graphicsIn, this.f_96543_, f);
         if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_renderMainMenu, this, graphicsIn, this.f_96547_, this.f_96543_, this.f_96544_, i);
         }

         if (this.f_96721_ != null && !this.f_96541_.f_91066_.m_307023_().m_231551_()) {
            this.f_96721_.m_280672_(graphicsIn, this.f_96543_, this.f_96547_, i);
         }

         String s = "Minecraft " + C_5285_.m_183709_().m_132493_();
         if (this.f_96541_.m_91402_()) {
            s = s + " Demo";
         } else {
            s = s + ("release".equalsIgnoreCase(this.f_96541_.m_91389_()) ? "" : "/" + this.f_96541_.m_91389_());
         }

         if (C_3391_.m_193589_().m_184597_()) {
            s = s + C_4513_.m_118938_("menu.modded", new Object[0]);
         }

         if (Reflector.BrandingControl.exists()) {
            if (Reflector.BrandingControl_forEachLine.exists()) {
               BiConsumer<Integer, String> lineConsumer = (brdline, brd) -> graphicsIn.m_280488_(
                     this.f_96547_, brd, 2, this.f_96544_ - (10 + brdline * (9 + 1)), 16777215 | i
                  );
               Reflector.call(Reflector.BrandingControl_forEachLine, true, true, lineConsumer);
            }

            if (Reflector.BrandingControl_forEachAboveCopyrightLine.exists()) {
               BiConsumer<Integer, String> lineConsumer = (brdline, brd) -> graphicsIn.m_280488_(
                     this.f_96547_, brd, this.f_96543_ - this.f_96547_.m_92895_(brd), this.f_96544_ - (10 + (brdline + 1) * (9 + 1)), 16777215 | i
                  );
               Reflector.call(Reflector.BrandingControl_forEachAboveCopyrightLine, lineConsumer);
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
      for (C_3495_ guieventlistener : this.m_6702_()) {
         if (guieventlistener instanceof C_3449_ abstractwidget) {
            abstractwidget.m_93650_(alphaIn);
         }
      }
   }

   public void m_280273_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
   }

   protected void m_318720_(C_279497_ graphicsIn, float partialTicks) {
      f_317031_.m_110003_(graphicsIn, this.f_96543_, this.f_96544_, this.f_315047_, partialTicks);
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      return super.a(mouseX, mouseY, button) ? true : this.m_96789_() && this.f_96726_.a(mouseX, mouseY, button);
   }

   public void m_7861_() {
      if (this.f_96726_ != null) {
         this.f_96726_.j();
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
            C_2786_ levelstoragesource$levelstorageaccess = this.f_96541_.m_91392_().m_78260_("Demo_World");

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
            C_3499_.m_94866_(this.f_96541_, "Demo_World");
            f_96717_.warn("Failed to delete demo world", var7);
         }
      }

      this.f_96541_.m_91152_(this);
   }
}
