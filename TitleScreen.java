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
import net.minecraft.src.C_211143_;
import net.minecraft.src.C_213160_;
import net.minecraft.src.C_243453_;
import net.minecraft.src.C_256714_;
import net.minecraft.src.C_263613_;
import net.minecraft.src.C_267340_;
import net.minecraft.src.C_276132_;
import net.minecraft.src.C_2785_;
import net.minecraft.src.C_279516_;
import net.minecraft.src.C_290288_;
import net.minecraft.src.C_3197_;
import net.minecraft.src.C_3304_;
import net.minecraft.src.C_336464_;
import net.minecraft.src.C_336537_;
import net.minecraft.src.C_336602_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_3495_;
import net.minecraft.src.C_3499_;
import net.minecraft.src.C_3541_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_3694_;
import net.minecraft.src.C_3695_;
import net.minecraft.src.C_3757_;
import net.minecraft.src.C_4144_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4995_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5285_;
import net.minecraft.src.C_2785_.C_2786_;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;

public class TitleScreen extends C_3583_ {
   private static final Logger f_96536_ = LogUtils.getLogger();
   private static final C_4996_ f_169367_ = C_4996_.m_237115_("narrator.screen.title");
   private static final C_4996_ c = C_4996_.m_237115_("title.credits");
   private static final String r = "Demo_World";
   private static final float s = 2000.0F;
   @Nullable
   private C_279516_ u;
   private C_3451_ v;
   @Nullable
   private C_3304_ w;
   private float x = 1.0F;
   private boolean y;
   private long f_169373_;
   private final C_263613_ A;
   private C_3583_ modUpdateNotification;

   public TitleScreen() {
      this(false);
   }

   public TitleScreen(boolean fadeIn) {
      this(fadeIn, null);
   }

   public TitleScreen(boolean fadeIn, @Nullable C_263613_ logoRendererIn) {
      super(f_169367_);
      this.y = fadeIn;
      this.A = (C_263613_)Objects.requireNonNullElseGet(logoRendererIn, () -> new C_263613_(false));
   }

   private boolean m() {
      return this.w != null;
   }

   public void m_86600_() {
      if (this.m()) {
         this.w.m_86600_();
      }
   }

   public static CompletableFuture<Void> a(TextureManager texMngr, Executor backgroundExecutor) {
      return CompletableFuture.allOf(
         texMngr.a(C_263613_.a, backgroundExecutor),
         texMngr.a(C_263613_.c, backgroundExecutor),
         texMngr.a(C_4144_.a, backgroundExecutor),
         f_314949_.a(texMngr, backgroundExecutor)
      );
   }

   public boolean m_7043_() {
      return false;
   }

   public boolean m_6913_() {
      return false;
   }

   protected void m_7856_() {
      if (this.u == null) {
         this.u = this.f_96541_.m_91310_().m_280369_();
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(new Date());
         int day = calendar.get(5);
         int month = calendar.get(2) + 1;
         if (day == 8 && month == 4) {
            this.u = new C_279516_("Happy birthday, OptiFine!");
         }

         if (day == 14 && month == 8) {
            this.u = new C_279516_("Happy birthday, sp614x!");
         }
      }

      int i = this.o.a(c);
      int j = this.f_96543_ - i - 2;
      int k = 24;
      int l = this.f_96544_ / 4 + 48;
      C_3451_ modButton = null;
      if (this.f_96541_.m_91402_()) {
         this.b(l, 24);
      } else {
         this.a(l, 24);
         if (Reflector.ModListScreen_Constructor.exists()) {
            modButton = ReflectorForge.makeButtonMods(this, l, 24);
            this.m_142416_(modButton);
         }
      }

      C_290288_ spriteiconbutton = (C_290288_)this.m_142416_(
         C_267340_.m_292713_(20, btnIn -> this.f_96541_.m_91152_(new C_336602_(this, this.f_96541_.m, this.f_96541_.m_91102_())), true)
      );
      spriteiconbutton.m_264152_(this.f_96543_ / 2 - 124, l + 72 + 12);
      this.m_142416_(
         C_3451_.m_253074_(C_4996_.m_237115_("menu.options"), btnIn -> this.f_96541_.m_91152_(new C_336537_(this, this.f_96541_.m)))
            .m_252987_(this.f_96543_ / 2 - 100, l + 72 + 12, 98, 20)
            .m_253136_()
      );
      this.m_142416_(
         C_3451_.m_253074_(C_4996_.m_237115_("menu.quit"), btnIn -> this.f_96541_.m_91395_()).m_252987_(this.f_96543_ / 2 + 2, l + 72 + 12, 98, 20).m_253136_()
      );
      C_290288_ spriteiconbutton1 = (C_290288_)this.m_142416_(
         C_267340_.m_294306_(20, btnIn -> this.f_96541_.m_91152_(new C_336464_(this, this.f_96541_.m)), true)
      );
      spriteiconbutton1.m_264152_(this.f_96543_ / 2 + 104, l + 72 + 12);
      this.m_142416_(new C_211143_(j, this.f_96544_ - 10, i, 10, c, btnIn -> this.f_96541_.m_91152_(new C_276132_(this)), this.o));
      if (this.w == null) {
         this.w = new C_3304_();
      }

      if (this.m()) {
         this.w.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
      }

      if (Reflector.TitleScreenModUpdateIndicator_init.exists()) {
         this.modUpdateNotification = (C_3583_)Reflector.call(Reflector.TitleScreenModUpdateIndicator_init, this, modButton);
      }
   }

   private void a(int yIn, int rowHeightIn) {
      this.m_142416_(
         C_3451_.m_253074_(C_4996_.m_237115_("menu.singleplayer"), btnIn -> this.f_96541_.m_91152_(new C_3757_(this)))
            .m_252987_(this.f_96543_ / 2 - 100, yIn, 200, 20)
            .m_253136_()
      );
      C_4996_ component = this.C();
      boolean flag = component == null;
      C_256714_ tooltip = component != null ? C_256714_.m_257550_(component) : null;
      ((C_3451_)this.m_142416_(C_3451_.m_253074_(C_4996_.m_237115_("menu.multiplayer"), btnIn -> {
         C_3583_ screen = (C_3583_)(this.f_96541_.m.v ? new C_3694_(this) : new C_3695_(this));
         this.f_96541_.m_91152_(screen);
      }).m_252987_(this.f_96543_ / 2 - 100, yIn + rowHeightIn * 1, 200, 20).m_257505_(tooltip).m_253136_())).f_93623_ = flag;
      boolean forge = Reflector.ModListScreen_Constructor.exists();
      int realmsX = forge ? this.f_96543_ / 2 + 2 : this.f_96543_ / 2 - 100;
      int realmsWidth = forge ? 98 : 200;
      ((C_3451_)this.m_142416_(
            C_3451_.m_253074_(C_4996_.m_237115_("menu.online"), btnIn -> this.f_96541_.m_91152_(new C_3197_(this)))
               .m_252987_(realmsX, yIn + rowHeightIn * 2, realmsWidth, 20)
               .m_257505_(tooltip)
               .m_253136_()
         ))
         .f_93623_ = flag;
   }

   @Nullable
   private C_4996_ C() {
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

   private void b(int yIn, int rowHeightIn) {
      boolean flag = this.D();
      this.m_142416_(C_3451_.m_253074_(C_4996_.m_237115_("menu.playdemo"), btnIn -> {
         if (flag) {
            this.f_96541_.m_231466_().m_320872_("Demo_World", () -> this.f_96541_.m_91152_(this));
         } else {
            this.f_96541_.m_231466_().m_233157_("Demo_World", MinecraftServer.f_129743_, C_243453_.f_244225_, C_213160_::m_246552_, this);
         }
      }).m_252987_(this.f_96543_ / 2 - 100, yIn, 200, 20).m_253136_());
      this.v = (C_3451_)this.m_142416_(
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
                                    this::m_169409_,
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
                     f_96536_.warn("Failed to access demo world", var8);
                  }
               }
            )
            .m_252987_(this.f_96543_ / 2 - 100, yIn + rowHeightIn * 1, 200, 20)
            .m_253136_()
      );
      this.v.f_93623_ = flag;
   }

   private boolean D() {
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
         f_96536_.warn("Failed to read demo world data", var7);
         return false;
      }
   }

   public void a(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.f_169373_ == 0L && this.y) {
         this.f_169373_ = Util.c();
      }

      float f = 1.0F;
      GlStateManager._disableDepthTest();
      if (this.y) {
         float f1 = (float)(Util.c() - this.f_169373_) / 2000.0F;
         if (f1 > 1.0F) {
            this.y = false;
            this.x = 1.0F;
         } else {
            f1 = Mth.a(f1, 0.0F, 1.0F);
            f = Mth.b(f1, 0.5F, 1.0F, 0.0F, 1.0F);
            this.x = Mth.b(f1, 0.0F, 0.5F, 0.0F, 1.0F);
         }

         this.b(f);
      }

      this.a(graphicsIn, partialTicks);
      int i = Mth.f(f * 255.0F) << 24;
      if ((i & -67108864) != 0) {
         super.a(graphicsIn, mouseX, mouseY, partialTicks);
         this.A.a(graphicsIn, this.f_96543_, f);
         if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_renderMainMenu, this, graphicsIn, this.o, this.f_96543_, this.f_96544_, i);
         }

         if (this.u != null && !this.f_96541_.m.c().c()) {
            this.u.a(graphicsIn, this.f_96543_, this.o, i);
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
               BiConsumer<Integer, String> lineConsumer = (brdline, brd) -> graphicsIn.b(this.o, brd, 2, this.f_96544_ - (10 + brdline * (9 + 1)), 16777215 | i);
               Reflector.call(Reflector.BrandingControl_forEachLine, true, true, lineConsumer);
            }

            if (Reflector.BrandingControl_forEachAboveCopyrightLine.exists()) {
               BiConsumer<Integer, String> lineConsumer = (brdline, brd) -> graphicsIn.b(
                     this.o, brd, this.f_96543_ - this.o.b(brd), this.f_96544_ - (10 + (brdline + 1) * (9 + 1)), 16777215 | i
                  );
               Reflector.call(Reflector.BrandingControl_forEachAboveCopyrightLine, lineConsumer);
            }
         } else {
            graphicsIn.b(this.o, s, 2, this.f_96544_ - 10, 16777215 | i);
         }

         if (this.m() && f >= 1.0F) {
            RenderSystem.enableDepthTest();
            this.w.a(graphicsIn, mouseX, mouseY, partialTicks);
         }
      }

      if (this.modUpdateNotification != null && f >= 1.0F) {
         this.modUpdateNotification.a(graphicsIn, mouseX, mouseY, partialTicks);
      }
   }

   private void b(float alphaIn) {
      for (C_3495_ guieventlistener : this.m_6702_()) {
         if (guieventlistener instanceof C_3449_ abstractwidget) {
            abstractwidget.m_93650_(alphaIn);
         }
      }
   }

   public void b(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
   }

   protected void a(GuiGraphics graphicsIn, float partialTicks) {
      f_317031_.a(graphicsIn, this.f_96543_, this.f_96544_, this.x, partialTicks);
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      return super.m_6375_(mouseX, mouseY, button) ? true : this.m() && this.w.m_6375_(mouseX, mouseY, button);
   }

   public void m_7861_() {
      if (this.w != null) {
         this.w.m_7861_();
      }
   }

   public void m_274333_() {
      super.m_274333_();
      if (this.w != null) {
         this.w.m_274333_();
      }
   }

   private void m_169409_(boolean confirmIn) {
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
            f_96536_.warn("Failed to delete demo world", var7);
         }
      }

      this.f_96541_.m_91152_(this);
   }
}
