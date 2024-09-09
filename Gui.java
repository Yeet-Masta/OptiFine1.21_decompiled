import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1219_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_139_;
import net.minecraft.src.C_1407_;
import net.minecraft.src.C_1593_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_200_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_2110_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_290072_;
import net.minecraft.src.C_301995_;
import net.minecraft.src.C_302080_;
import net.minecraft.src.C_302146_;
import net.minecraft.src.C_3041_;
import net.minecraft.src.C_3042_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_3073_;
import net.minecraft.src.C_3074_;
import net.minecraft.src.C_3076_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_313825_;
import net.minecraft.src.C_336468_;
import net.minecraft.src.C_3371_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3479_;
import net.minecraft.src.C_3487_;
import net.minecraft.src.C_3496_;
import net.minecraft.src.C_3657_;
import net.minecraft.src.C_4503_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_474_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_496_;
import net.minecraft.src.C_498_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_500_;
import net.minecraft.src.C_5012_;
import net.minecraft.src.C_5020_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_520_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_534_;
import net.minecraft.src.C_559_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_3043_.C_3044_;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomItems;
import net.optifine.TextureAnimations;
import net.optifine.reflect.Reflector;
import org.joml.Matrix4fStack;

public class Gui {
   private static final ResourceLocation b = ResourceLocation.b("hud/crosshair");
   private static final ResourceLocation c = ResourceLocation.b("hud/crosshair_attack_indicator_full");
   private static final ResourceLocation d = ResourceLocation.b("hud/crosshair_attack_indicator_background");
   private static final ResourceLocation e = ResourceLocation.b("hud/crosshair_attack_indicator_progress");
   private static final ResourceLocation f = ResourceLocation.b("hud/effect_background_ambient");
   private static final ResourceLocation g = ResourceLocation.b("hud/effect_background");
   private static final ResourceLocation h = ResourceLocation.b("hud/hotbar");
   private static final ResourceLocation i = ResourceLocation.b("hud/hotbar_selection");
   private static final ResourceLocation j = ResourceLocation.b("hud/hotbar_offhand_left");
   private static final ResourceLocation k = ResourceLocation.b("hud/hotbar_offhand_right");
   private static final ResourceLocation l = ResourceLocation.b("hud/hotbar_attack_indicator_background");
   private static final ResourceLocation m = ResourceLocation.b("hud/hotbar_attack_indicator_progress");
   private static final ResourceLocation n = ResourceLocation.b("hud/jump_bar_background");
   private static final ResourceLocation o = ResourceLocation.b("hud/jump_bar_cooldown");
   private static final ResourceLocation p = ResourceLocation.b("hud/jump_bar_progress");
   private static final ResourceLocation q = ResourceLocation.b("hud/experience_bar_background");
   private static final ResourceLocation r = ResourceLocation.b("hud/experience_bar_progress");
   private static final ResourceLocation s = ResourceLocation.b("hud/armor_empty");
   private static final ResourceLocation t = ResourceLocation.b("hud/armor_half");
   private static final ResourceLocation u = ResourceLocation.b("hud/armor_full");
   private static final ResourceLocation v = ResourceLocation.b("hud/food_empty_hunger");
   private static final ResourceLocation w = ResourceLocation.b("hud/food_half_hunger");
   private static final ResourceLocation x = ResourceLocation.b("hud/food_full_hunger");
   private static final ResourceLocation y = ResourceLocation.b("hud/food_empty");
   private static final ResourceLocation z = ResourceLocation.b("hud/food_half");
   private static final ResourceLocation A = ResourceLocation.b("hud/food_full");
   private static final ResourceLocation B = ResourceLocation.b("hud/air");
   private static final ResourceLocation C = ResourceLocation.b("hud/air_bursting");
   private static final ResourceLocation D = ResourceLocation.b("hud/heart/vehicle_container");
   private static final ResourceLocation E = ResourceLocation.b("hud/heart/vehicle_full");
   private static final ResourceLocation F = ResourceLocation.b("hud/heart/vehicle_half");
   private static final ResourceLocation G = ResourceLocation.b("textures/misc/vignette.png");
   private static final ResourceLocation H = ResourceLocation.b("textures/misc/pumpkinblur.png");
   private static final ResourceLocation I = ResourceLocation.b("textures/misc/spyglass_scope.png");
   private static final ResourceLocation J = ResourceLocation.b("textures/misc/powder_snow_outline.png");
   private static final Comparator<C_302146_> K = Comparator.comparing(C_302146_::f_303807_)
      .reversed()
      .thenComparing(C_302146_::f_302847_, String.CASE_INSENSITIVE_ORDER);
   private static final C_4996_ L = C_4996_.m_237115_("demo.demoExpired");
   private static final C_4996_ M = C_4996_.m_237115_("menu.savingLevel");
   private static final float N = 5.0F;
   private static final int O = 10;
   private static final int P = 10;
   private static final String Q = ": ";
   private static final float R = 0.2F;
   private static final int S = 9;
   private static final int T = 8;
   private static final float U = 0.2F;
   private final C_212974_ V = C_212974_.m_216327_();
   private final C_3391_ W;
   private final ChatComponent X;
   private int Y;
   @Nullable
   private C_4996_ Z;
   private int aa;
   private boolean ab;
   private boolean ac;
   public float a = 1.0F;
   private int ad;
   private C_1391_ ae = C_1391_.f_41583_;
   protected DebugScreenOverlay af;
   private final C_3487_ ag;
   private final C_3496_ ah;
   private final C_3479_ ai;
   private final BossHealthOverlay aj;
   private int ak;
   @Nullable
   private C_4996_ al;
   @Nullable
   private C_4996_ am;
   private int an;
   private int ao;
   private int ap;
   private int aq;
   private int ar;
   private long as;
   private long at;
   private float au;
   private float av;
   private final C_313825_ aw = new C_313825_();
   private float ax;

   public Gui(C_3391_ mcIn) {
      this.W = mcIn;
      this.af = new DebugScreenOverlay(mcIn);
      this.ah = new C_3496_(mcIn);
      this.X = new ChatComponent(mcIn);
      this.ai = new C_3479_(mcIn, this);
      this.aj = new BossHealthOverlay(mcIn);
      this.ag = new C_3487_(mcIn);
      this.a();
      C_313825_ layereddraw = new C_313825_()
         .m_322513_(this::c)
         .m_322513_(this::j)
         .m_322513_(this::l)
         .m_322513_(this::n)
         .m_322513_(this::k)
         .m_322513_((graphicsIn, partialTicks) -> this.aj.a(graphicsIn));
      C_313825_ layereddraw1 = new C_313825_()
         .m_322513_(this::o)
         .m_322513_((graphicsIn, partialTicks) -> {
            if (this.af.d()) {
               this.af.a(graphicsIn);
            }
         })
         .m_322513_(this::h)
         .m_322513_(this::e)
         .m_322513_(this::f)
         .m_322513_(this::g)
         .m_322513_(this::i)
         .m_322513_((graphicsIn, partialTicks) -> this.ag.a(graphicsIn));
      this.aw.m_323151_(layereddraw, () -> !mcIn.m.Y).m_322513_(this::d).m_323151_(layereddraw1, () -> !mcIn.m.Y);
   }

   public void a() {
      this.an = 10;
      this.ao = 70;
      this.ap = 20;
   }

   public void a(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      RenderSystem.enableDepthTest();
      this.aw.a(graphicsIn, partialTicks);
      RenderSystem.disableDepthTest();
   }

   private void c(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      if (Config.isVignetteEnabled()) {
         this.a(graphicsIn, this.W.m_91288_());
      }

      float f = partialTicks.m_339005_();
      this.ax = Mth.i(0.5F * f, this.ax, 1.125F);
      if (this.W.m.aB().m_90612_()) {
         if (this.W.f_91074_.gw()) {
            this.a(graphicsIn, this.ax);
         } else {
            this.ax = 0.5F;
            C_1391_ itemstack = this.W.f_91074_.fY().m_36052_(3);
            if (itemstack.m_150930_(C_1710_.f_50143_.m_5456_())) {
               this.a(graphicsIn, H, 1.0F);
            }
         }
      }

      if (this.W.f_91074_.cn() > 0) {
         this.a(graphicsIn, J, this.W.f_91074_.co());
      }

      float f1 = Mth.i(partialTicks.m_338527_(false), this.W.f_91074_.f_108590_, this.W.f_91074_.f_108589_);
      if (f1 > 0.0F && !this.W.f_91074_.b(C_500_.f_19604_)) {
         this.b(graphicsIn, f1);
      }
   }

   private void d(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      if (this.W.f_91074_.gc() > 0) {
         this.W.m_91307_().m_6180_("sleep");
         float f = (float)this.W.f_91074_.gc();
         float f1 = f / 100.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F - (f - 100.0F) / 10.0F;
         }

         int i = (int)(220.0F * f1) << 24 | 1052704;
         graphicsIn.a(RenderType.F(), 0, 0, graphicsIn.a(), graphicsIn.b(), i);
         this.W.m_91307_().m_7238_();
      }
   }

   private void e(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      Font font = this.f();
      if (this.Z != null && this.aa > 0) {
         this.W.m_91307_().m_6180_("overlayMessage");
         float f = (float)this.aa - partialTicks.m_338527_(false);
         int i = (int)(f * 255.0F / 20.0F);
         if (i > 255) {
            i = 255;
         }

         if (i > 8) {
            graphicsIn.c().a();
            graphicsIn.c().a((float)(graphicsIn.a() / 2), (float)(graphicsIn.b() - 68), 0.0F);
            int j;
            if (this.ab) {
               j = Mth.a(f / 50.0F, 0.7F, 0.6F, i);
            } else {
               j = C_175_.m_320289_(i, -1);
            }

            int k = font.a(this.Z);
            graphicsIn.a(font, this.Z, -k / 2, -4, k, j);
            graphicsIn.c().b();
         }

         this.W.m_91307_().m_7238_();
      }
   }

   private void f(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      if (this.al != null && this.ak > 0) {
         Font font = this.f();
         this.W.m_91307_().m_6180_("titleAndSubtitle");
         float f = (float)this.ak - partialTicks.m_338527_(false);
         int i = 255;
         if (this.ak > this.ap + this.ao) {
            float f1 = (float)(this.an + this.ao + this.ap) - f;
            i = (int)(f1 * 255.0F / (float)this.an);
         }

         if (this.ak <= this.ap) {
            i = (int)(f * 255.0F / (float)this.ap);
         }

         i = Mth.a(i, 0, 255);
         if (i > 8) {
            graphicsIn.c().a();
            graphicsIn.c().a((float)(graphicsIn.a() / 2), (float)(graphicsIn.b() / 2), 0.0F);
            graphicsIn.c().a();
            graphicsIn.c().b(4.0F, 4.0F, 4.0F);
            int l = font.a(this.al);
            int j = C_175_.m_320289_(i, -1);
            graphicsIn.a(font, this.al, -l / 2, -10, l, j);
            graphicsIn.c().b();
            if (this.am != null) {
               graphicsIn.c().a();
               graphicsIn.c().b(2.0F, 2.0F, 2.0F);
               int k = font.a(this.am);
               graphicsIn.a(font, this.am, -k / 2, 5, k, j);
               graphicsIn.c().b();
            }

            graphicsIn.c().b();
         }

         this.W.m_91307_().m_7238_();
      }
   }

   private void g(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      if (!this.X.e()) {
         Window window = this.W.aM();
         int i = Mth.a(this.W.f_91067_.m_91589_() * (double)window.p() / (double)window.n());
         int j = Mth.a(this.W.f_91067_.m_91594_() * (double)window.q() / (double)window.o());
         this.X.a(graphicsIn, this.Y, i, j, false);
      }
   }

   private void h(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      C_3076_ scoreboard = this.W.r.m_6188_();
      C_3073_ objective = null;
      C_3074_ playerteam = scoreboard.m_83500_(this.W.f_91074_.cB());
      if (playerteam != null) {
         C_290072_ displayslot = C_290072_.m_293761_(playerteam.m_7414_());
         if (displayslot != null) {
            objective = scoreboard.m_83416_(displayslot);
         }
      }

      C_3073_ objective1 = objective != null ? objective : scoreboard.m_83416_(C_290072_.SIDEBAR);
      if (objective1 != null) {
         this.a(graphicsIn, objective1);
      }
   }

   private void i(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      C_3076_ scoreboard = this.W.r.m_6188_();
      C_3073_ objective = scoreboard.m_83416_(C_290072_.LIST);
      if (this.W.m.K.m_90857_() && (!this.W.m_91090_() || this.W.f_91074_.f_108617_.m_246170_().size() > 1 || objective != null)) {
         this.ai.m_94556_(true);
         this.ai.a(graphicsIn, graphicsIn.a(), scoreboard, objective);
      } else {
         this.ai.m_94556_(false);
      }
   }

   private void j(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      Options options = this.W.m;
      if (options.aB().m_90612_() && (this.W.f_91072_.m_105295_() != C_1593_.SPECTATOR || this.a(this.W.f_91077_))) {
         RenderSystem.enableBlend();
         if (this.af.d() && !this.W.f_91074_.go() && !options.V().c()) {
            Camera camera = this.W.j.l();
            Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
            matrix4fstack.pushMatrix();
            matrix4fstack.mul(graphicsIn.c().c().a());
            matrix4fstack.translate((float)(graphicsIn.a() / 2), (float)(graphicsIn.b() / 2), 0.0F);
            matrix4fstack.rotateX(-camera.d() * (float) (Math.PI / 180.0));
            matrix4fstack.rotateY(camera.e() * (float) (Math.PI / 180.0));
            matrix4fstack.scale(-1.0F, -1.0F, -1.0F);
            RenderSystem.applyModelViewMatrix();
            RenderSystem.renderCrosshair(10);
            matrix4fstack.popMatrix();
            RenderSystem.applyModelViewMatrix();
         } else {
            RenderSystem.blendFuncSeparate(
               GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
               GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
               GlStateManager.SourceFactor.ONE,
               GlStateManager.DestFactor.ZERO
            );
            int i = 15;
            graphicsIn.a(b, (graphicsIn.a() - 15) / 2, (graphicsIn.b() - 15) / 2, 15, 15);
            if (this.W.m.D().c() == C_3371_.CROSSHAIR) {
               float f = this.W.f_91074_.F(0.0F);
               boolean flag = false;
               if (this.W.f_91076_ != null && this.W.f_91076_ instanceof C_524_ && f >= 1.0F) {
                  flag = this.W.f_91074_.gr() > 5.0F;
                  flag &= this.W.f_91076_.m_6084_();
               }

               int j = graphicsIn.b() / 2 - 7 + 16;
               int k = graphicsIn.a() / 2 - 8;
               if (flag) {
                  graphicsIn.a(c, k, j, 16, 16);
               } else if (f < 1.0F) {
                  int l = (int)(f * 17.0F);
                  graphicsIn.a(d, k, j, 16, 4);
                  graphicsIn.a(e, 16, 4, 0, 0, k, j, l, 4);
               }
            }

            RenderSystem.defaultBlendFunc();
         }

         RenderSystem.disableBlend();
      }
   }

   private boolean a(@Nullable C_3043_ rayTraceIn) {
      if (rayTraceIn == null) {
         return false;
      } else if (rayTraceIn.m_6662_() == C_3044_.ENTITY) {
         return ((C_3042_)rayTraceIn).m_82443_() instanceof C_474_;
      } else if (rayTraceIn.m_6662_() == C_3044_.BLOCK) {
         C_4675_ blockpos = ((C_3041_)rayTraceIn).m_82425_();
         C_1596_ level = this.W.r;
         return level.a_(blockpos).m_60750_(level, blockpos) != null;
      } else {
         return false;
      }
   }

   private void k(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      Collection<C_498_> collection = this.W.f_91074_.et();
      if (!collection.isEmpty()) {
         if (this.W.f_91080_ instanceof C_3657_ effectrenderinginventoryscreen && effectrenderinginventoryscreen.m_194018_()) {
            return;
         }

         RenderSystem.enableBlend();
         int j1 = 0;
         int k1 = 0;
         C_4503_ mobeffecttexturemanager = this.W.m_91306_();
         List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());

         for (C_498_ mobeffectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
            C_203228_<C_496_> holder = mobeffectinstance.m_19544_();
            IClientMobEffectExtensions cmee = IClientMobEffectExtensions.of(mobeffectinstance);
            if ((cmee == null || cmee.isVisibleInGui(mobeffectinstance)) && mobeffectinstance.m_19575_()) {
               int i = graphicsIn.a();
               int j = 1;
               if (this.W.m_91402_()) {
                  j += 15;
               }

               if (((C_496_)holder.m_203334_()).m_19486_()) {
                  j1++;
                  i -= 25 * j1;
               } else {
                  k1++;
                  i -= 25 * k1;
                  j += 26;
               }

               float f = 1.0F;
               if (mobeffectinstance.m_19571_()) {
                  graphicsIn.a(Gui.f, i, j, 24, 24);
               } else {
                  graphicsIn.a(g, i, j, 24, 24);
                  if (mobeffectinstance.m_267633_(200)) {
                     int k = mobeffectinstance.m_19557_();
                     int l = 10 - k / 20;
                     f = Mth.a((float)k / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + Mth.b((float)k * (float) Math.PI / 5.0F) * Mth.a((float)l / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               if (cmee == null || !cmee.renderGuiIcon(mobeffectinstance, this, graphicsIn, i, j, 0.0F, f)) {
                  TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.a(holder);
                  int l1 = i;
                  int i1 = j;
                  float f1 = f;
                  list.add((Runnable)() -> {
                     graphicsIn.a(1.0F, 1.0F, 1.0F, f1);
                     graphicsIn.a(l1 + 3, i1 + 3, 0, 18, 18, textureatlassprite);
                     graphicsIn.a(1.0F, 1.0F, 1.0F, 1.0F);
                  });
               }
            }
         }

         list.forEach(Runnable::run);
         RenderSystem.disableBlend();
      }
   }

   private void l(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      if (this.W.f_91072_.m_105295_() == C_1593_.SPECTATOR) {
         this.ah.a(graphicsIn);
      } else {
         this.m(graphicsIn, partialTicks);
      }

      int i = graphicsIn.a() / 2 - 91;
      C_534_ playerrideablejumping = this.W.f_91074_.m_245714_();
      if (playerrideablejumping != null) {
         this.a(playerrideablejumping, graphicsIn, i);
      } else if (this.m()) {
         this.a(graphicsIn, i);
      }

      if (this.W.f_91072_.m_105205_()) {
         this.b(graphicsIn);
      }

      this.c(graphicsIn);
      if (this.W.f_91072_.m_105295_() != C_1593_.SPECTATOR) {
         this.a(graphicsIn);
      } else if (this.W.f_91074_.R_()) {
         this.ah.b(graphicsIn);
      }
   }

   private void m(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      C_1141_ player = this.n();
      if (player != null) {
         C_1391_ itemstack = player.m_21206_();
         C_520_ humanoidarm = player.m_5737_().m_20828_();
         int i = graphicsIn.a() / 2;
         int j = 182;
         int k = 91;
         RenderSystem.enableBlend();
         graphicsIn.c().a();
         graphicsIn.c().a(0.0F, 0.0F, -90.0F);
         graphicsIn.a(h, i - 91, graphicsIn.b() - 22, 182, 22);
         graphicsIn.a(Gui.i, i - 91 - 1 + player.m_150109_().f_35977_ * 20, graphicsIn.b() - 22 - 1, 24, 23);
         if (!itemstack.m_41619_()) {
            if (humanoidarm == C_520_.LEFT) {
               graphicsIn.a(Gui.j, i - 91 - 29, graphicsIn.b() - 23, 29, 24);
            } else {
               graphicsIn.a(Gui.k, i + 91, graphicsIn.b() - 23, 29, 24);
            }
         }

         graphicsIn.c().b();
         RenderSystem.disableBlend();
         int l = 1;
         CustomItems.setRenderOffHand(false);

         for (int i1 = 0; i1 < 9; i1++) {
            int j1 = i - 90 + i1 * 20 + 2;
            int k1 = graphicsIn.b() - 16 - 3;
            this.a(graphicsIn, j1, k1, partialTicks, player, (C_1391_)player.m_150109_().f_35974_.get(i1), l++);
         }

         if (!itemstack.m_41619_()) {
            CustomItems.setRenderOffHand(true);
            int i2 = graphicsIn.b() - 16 - 3;
            if (humanoidarm == C_520_.LEFT) {
               this.a(graphicsIn, i - 91 - 26, i2, partialTicks, player, itemstack, l++);
            } else {
               this.a(graphicsIn, i + 91 + 10, i2, partialTicks, player, itemstack, l++);
            }

            CustomItems.setRenderOffHand(false);
         }

         if (this.W.m.D().c() == C_3371_.HOTBAR) {
            RenderSystem.enableBlend();
            float f = this.W.f_91074_.F(0.0F);
            if (f < 1.0F) {
               int j2 = graphicsIn.b() - 20;
               int k2 = i + 91 + 6;
               if (humanoidarm == C_520_.RIGHT) {
                  k2 = i - 91 - 22;
               }

               int l1 = (int)(f * 19.0F);
               graphicsIn.a(Gui.l, k2, j2, 18, 18);
               graphicsIn.a(m, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
            }

            RenderSystem.disableBlend();
         }
      }
   }

   private void a(C_534_ jumpingIn, GuiGraphics graphicsIn, int x) {
      this.W.m_91307_().m_6180_("jumpBar");
      float f = this.W.f_91074_.m_108634_();
      int i = 182;
      int j = (int)(f * 183.0F);
      int k = graphicsIn.b() - 32 + 3;
      RenderSystem.enableBlend();
      graphicsIn.a(n, x, k, 182, 5);
      if (jumpingIn.m_245614_() > 0) {
         graphicsIn.a(o, x, k, 182, 5);
      } else if (j > 0) {
         graphicsIn.a(p, 182, 5, 0, 0, x, k, j, 5);
      }

      RenderSystem.disableBlend();
      this.W.m_91307_().m_7238_();
   }

   private void a(GuiGraphics graphicsIn, int x) {
      this.W.m_91307_().m_6180_("expBar");
      int i = this.W.f_91074_.gh();
      if (i > 0) {
         int j = 182;
         int k = (int)(this.W.f_91074_.cs * 183.0F);
         int l = graphicsIn.b() - 32 + 3;
         RenderSystem.enableBlend();
         graphicsIn.a(q, x, l, 182, 5);
         if (k > 0) {
            graphicsIn.a(r, 182, 5, 0, 0, x, l, k, 5);
         }

         RenderSystem.disableBlend();
      }

      this.W.m_91307_().m_7238_();
   }

   private void n(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      int i = this.W.f_91074_.cq;
      if (this.m() && i > 0) {
         this.W.m_91307_().m_6180_("expLevel");
         int col = 8453920;
         if (Config.isCustomColors()) {
            col = CustomColors.getExpBarTextColor(col);
         }

         String s = i + "";
         int j = (graphicsIn.a() - this.f().b(s)) / 2;
         int k = graphicsIn.b() - 31 - 4;
         graphicsIn.a(this.f(), s, j + 1, k, 0, false);
         graphicsIn.a(this.f(), s, j - 1, k, 0, false);
         graphicsIn.a(this.f(), s, j, k + 1, 0, false);
         graphicsIn.a(this.f(), s, j, k - 1, 0, false);
         graphicsIn.a(this.f(), s, j, k, col, false);
         this.W.m_91307_().m_7238_();
      }
   }

   private boolean m() {
      return this.W.f_91074_.m_245714_() == null && this.W.f_91072_.m_105288_();
   }

   private void a(GuiGraphics graphicsIn) {
      this.renderSelectedItemName(graphicsIn, 0);
   }

   public void renderSelectedItemName(GuiGraphics graphicsIn, int yShift) {
      this.W.m_91307_().m_6180_("selectedItemName");
      if (this.ad > 0 && !this.ae.m_41619_()) {
         C_5012_ mutablecomponent = C_4996_.m_237119_().m_7220_(this.ae.m_41786_()).m_130940_(this.ae.m_41791_().m_321696_());
         if (Reflector.ForgeRarity_getStyleModifier.exists()) {
            C_1407_ rarityForge = this.ae.m_41791_();
            UnaryOperator<C_5020_> styleModifier = (UnaryOperator<C_5020_>)Reflector.call(rarityForge, Reflector.ForgeRarity_getStyleModifier);
            mutablecomponent = C_4996_.m_237119_().m_7220_(this.ae.m_41786_()).m_130938_(styleModifier);
         }

         if (this.ae.m_319951_(C_313616_.f_316016_)) {
            mutablecomponent.m_130940_(C_4856_.ITALIC);
         }

         C_4996_ highlightTip = mutablecomponent;
         if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
            highlightTip = (C_4996_)Reflector.call(this.ae, Reflector.IForgeItemStack_getHighlightTip, mutablecomponent);
         }

         int i = this.f().a(highlightTip);
         int j = (graphicsIn.a() - i) / 2;
         int k = graphicsIn.b() - Math.max(yShift, 59);
         if (!this.W.f_91072_.m_105205_()) {
            k += 14;
         }

         int l = (int)((float)this.ad * 256.0F / 10.0F);
         if (l > 255) {
            l = 255;
         }

         if (l > 0) {
            Font font = null;
            IClientItemExtensions cmee = IClientItemExtensions.of(this.ae);
            if (cmee != null) {
               font = cmee.getFont(this.ae, IClientItemExtensions.FontContext.SELECTED_ITEM_NAME);
            }

            if (font != null) {
               i = (graphicsIn.a() - font.a(highlightTip)) / 2;
               graphicsIn.b(this.f(), highlightTip.m_7532_(), j, k, 16777215 + (l << 24));
            } else {
               graphicsIn.a(this.f(), mutablecomponent, j, k, i, C_175_.m_320289_(l, -1));
            }
         }
      }

      this.W.m_91307_().m_7238_();
   }

   private void o(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      if (this.W.m_91402_()) {
         this.W.m_91307_().m_6180_("demo");
         C_4996_ component;
         if (this.W.r.m_46467_() >= 120500L) {
            component = L;
         } else {
            component = C_4996_.m_237110_("demo.remainingTime", new Object[]{C_200_.m_14404_((int)(120500L - this.W.r.m_46467_()), this.W.r.s().f())});
         }

         int i = this.f().a(component);
         int j = graphicsIn.a() - i - 10;
         int k = 5;
         graphicsIn.a(this.f(), component, j, 5, i, -1);
         this.W.m_91307_().m_7238_();
      }
   }

   private void a(GuiGraphics graphicsIn, C_3073_ objective) {
      C_3076_ scoreboard = objective.m_83313_();
      C_302080_ numberformat = objective.m_305063_(C_301995_.f_303088_);
      Gui.a[] agui$displayentry = (Gui.a[])scoreboard.m_306706_(objective)
         .stream()
         .filter(entryIn -> !entryIn.m_307477_())
         .sorted(K)
         .limit(15L)
         .map(entry2In -> {
            C_3074_ playerteam = scoreboard.m_83500_(entry2In.f_302847_());
            C_4996_ component1 = entry2In.m_305530_();
            C_4996_ component2 = C_3074_.m_83348_(playerteam, component1);
            C_4996_ component3 = entry2In.m_304640_(numberformat);
            int i1 = this.f().a(component3);
            return new Gui.a(component2, component3, i1);
         })
         .toArray(Gui.a[]::new);
      C_4996_ component = objective.m_83322_();
      int i = this.f().a(component);
      int j = i;
      int k = this.f().b(": ");

      for (Gui.a gui$displayentry : agui$displayentry) {
         j = Math.max(j, this.f().a(gui$displayentry.a) + (gui$displayentry.c > 0 ? k + gui$displayentry.c : 0));
      }

      int l = j;
      graphicsIn.a(() -> {
         int i1 = agui$displayentry.length;
         int j1 = i1 * 9;
         int k1 = graphicsIn.b() / 2 + j1 / 3;
         int l1 = 3;
         int i2 = graphicsIn.a() - l - 3;
         int j2 = graphicsIn.a() - 3 + 2;
         int k2 = this.W.m.b(0.3F);
         int l2 = this.W.m.b(0.4F);
         int i3 = k1 - i1 * 9;
         graphicsIn.a(i2 - 2, i3 - 9 - 1, j2, i3 - 1, l2);
         graphicsIn.a(i2 - 2, i3 - 1, j2, k1, k2);
         graphicsIn.a(this.f(), component, i2 + l / 2 - i / 2, i3 - 9, -1, false);

         for (int j3 = 0; j3 < i1; j3++) {
            Gui.a gui$displayentry1 = agui$displayentry[j3];
            int k3 = k1 - (i1 - j3) * 9;
            graphicsIn.a(this.f(), gui$displayentry1.a, i2, k3, -1, false);
            graphicsIn.a(this.f(), gui$displayentry1.b, j2 - gui$displayentry1.c, k3, -1, false);
         }
      });
   }

   @Nullable
   private C_1141_ n() {
      return this.W.m_91288_() instanceof C_1141_ player ? player : null;
   }

   @Nullable
   private C_524_ o() {
      C_1141_ player = this.n();
      if (player != null) {
         C_507_ entity = player.m_20202_();
         if (entity == null) {
            return null;
         }

         if (entity instanceof C_524_) {
            return (C_524_)entity;
         }
      }

      return null;
   }

   private int a(@Nullable C_524_ livingEntityIn) {
      if (livingEntityIn != null && livingEntityIn.m_20152_()) {
         float f = livingEntityIn.m_21233_();
         int i = (int)(f + 0.5F) / 2;
         if (i > 30) {
            i = 30;
         }

         return i;
      } else {
         return 0;
      }
   }

   private int a(int healthIn) {
      return (int)Math.ceil((double)healthIn / 10.0);
   }

   private void b(GuiGraphics graphicsIn) {
      C_1141_ player = this.n();
      if (player != null) {
         int i = Mth.f(player.m_21223_());
         boolean flag = this.at > (long)this.Y && (this.at - (long)this.Y) / 3L % 2L == 1L;
         long j = Util.c();
         if (i < this.aq && player.f_19802_ > 0) {
            this.as = j;
            this.at = (long)(this.Y + 20);
         } else if (i > this.aq && player.f_19802_ > 0) {
            this.as = j;
            this.at = (long)(this.Y + 10);
         }

         if (j - this.as > 1000L) {
            this.aq = i;
            this.ar = i;
            this.as = j;
         }

         this.aq = i;
         int k = this.ar;
         this.V.m_188584_((long)(this.Y * 312871));
         int l = graphicsIn.a() / 2 - 91;
         int i1 = graphicsIn.a() / 2 + 91;
         int j1 = graphicsIn.b() - 39;
         float f = Math.max((float)player.m_246858_(C_559_.f_22276_), (float)Math.max(k, i));
         int k1 = Mth.f(player.m_6103_());
         int l1 = Mth.f((f + (float)k1) / 2.0F / 10.0F);
         int i2 = Math.max(10 - (l1 - 2), 3);
         int j2 = j1 - 10;
         int k2 = -1;
         if (player.m_21023_(C_500_.f_19605_)) {
            k2 = this.Y % Mth.f(f + 5.0F);
         }

         this.W.m_91307_().m_6180_("armor");
         a(graphicsIn, player, j1, l1, i2, l);
         this.W.m_91307_().m_6182_("health");
         this.a(graphicsIn, player, l, j1, i2, k2, f, i, k, k1, flag);
         C_524_ livingentity = this.o();
         int l2 = this.a(livingentity);
         if (l2 == 0) {
            this.W.m_91307_().m_6182_("food");
            this.a(graphicsIn, player, j1, i1);
            j2 -= 10;
         }

         this.W.m_91307_().m_6182_("air");
         int i3 = player.m_6062_();
         int j3 = Math.min(player.m_20146_(), i3);
         if (player.m_204029_(C_139_.f_13131_) || j3 < i3) {
            int k3 = this.a(l2) - 1;
            j2 -= k3 * 10;
            int l3 = Mth.c((double)(j3 - 2) * 10.0 / (double)i3);
            int i4 = Mth.c((double)j3 * 10.0 / (double)i3) - l3;
            RenderSystem.enableBlend();

            for (int j4 = 0; j4 < l3 + i4; j4++) {
               if (j4 < l3) {
                  graphicsIn.a(B, i1 - j4 * 8 - 9, j2, 9, 9);
               } else {
                  graphicsIn.a(C, i1 - j4 * 8 - 9, j2, 9, 9);
               }
            }

            RenderSystem.disableBlend();
         }

         this.W.m_91307_().m_7238_();
      }
   }

   private static void a(GuiGraphics graphicsIn, C_1141_ playerIn, int p_232354_2_, int p_232354_3_, int p_232354_4_, int p_232354_5_) {
      int i = playerIn.m_21230_();
      if (i > 0) {
         RenderSystem.enableBlend();
         int j = p_232354_2_ - (p_232354_3_ - 1) * p_232354_4_ - 10;

         for (int k = 0; k < 10; k++) {
            int l = p_232354_5_ + k * 8;
            if (k * 2 + 1 < i) {
               graphicsIn.a(u, l, j, 9, 9);
            }

            if (k * 2 + 1 == i) {
               graphicsIn.a(t, l, j, 9, 9);
            }

            if (k * 2 + 1 > i) {
               graphicsIn.a(s, l, j, 9, 9);
            }
         }

         RenderSystem.disableBlend();
      }
   }

   private void a(
      GuiGraphics graphicsIn,
      C_1141_ playerIn,
      int p_168688_3_,
      int p_168688_4_,
      int p_168688_5_,
      int p_168688_6_,
      float p_168688_7_,
      int p_168688_8_,
      int p_168688_9_,
      int p_168688_10_,
      boolean halfIn
   ) {
      Gui.b gui$hearttype = Gui.b.a(playerIn);
      boolean flag = playerIn.m_9236_().m_6106_().m_5466_();
      int i = Mth.c((double)p_168688_7_ / 2.0);
      int j = Mth.c((double)p_168688_10_ / 2.0);
      int k = i * 2;

      for (int l = i + j - 1; l >= 0; l--) {
         int i1 = l / 10;
         int j1 = l % 10;
         int k1 = p_168688_3_ + j1 * 8;
         int l1 = p_168688_4_ - i1 * p_168688_5_;
         if (p_168688_8_ + p_168688_10_ <= 4) {
            l1 += this.V.m_188503_(2);
         }

         if (l < i && l == p_168688_6_) {
            l1 -= 2;
         }

         this.a(graphicsIn, Gui.b.a, k1, l1, flag, halfIn, false);
         int i2 = l * 2;
         boolean flag1 = l >= i;
         if (flag1) {
            int j2 = i2 - k;
            if (j2 < p_168688_10_) {
               boolean flag2 = j2 + 1 == p_168688_10_;
               this.a(graphicsIn, gui$hearttype == Gui.b.d ? gui$hearttype : Gui.b.e, k1, l1, flag, false, flag2);
            }
         }

         if (halfIn && i2 < p_168688_9_) {
            boolean flag3 = i2 + 1 == p_168688_9_;
            this.a(graphicsIn, gui$hearttype, k1, l1, flag, true, flag3);
         }

         if (i2 < p_168688_8_) {
            boolean flag4 = i2 + 1 == p_168688_8_;
            this.a(graphicsIn, gui$hearttype, k1, l1, flag, false, flag4);
         }
      }
   }

   private void a(GuiGraphics graphicsIn, Gui.b typeIn, int xIn, int yIn, boolean hardcoreIn, boolean halfIn, boolean blinkingIn) {
      RenderSystem.enableBlend();
      graphicsIn.a(typeIn.a(hardcoreIn, blinkingIn, halfIn), xIn, yIn, 9, 9);
      RenderSystem.disableBlend();
   }

   private void a(GuiGraphics graphicsIn, C_1141_ playerIn, int p_320133_3_, int p_320133_4_) {
      C_1219_ fooddata = playerIn.m_36324_();
      int i = fooddata.m_38702_();
      RenderSystem.enableBlend();

      for (int j = 0; j < 10; j++) {
         int k = p_320133_3_;
         ResourceLocation resourcelocation;
         ResourceLocation resourcelocation1;
         ResourceLocation resourcelocation2;
         if (playerIn.m_21023_(C_500_.f_19612_)) {
            resourcelocation = v;
            resourcelocation1 = w;
            resourcelocation2 = x;
         } else {
            resourcelocation = y;
            resourcelocation1 = z;
            resourcelocation2 = A;
         }

         if (playerIn.m_36324_().m_38722_() <= 0.0F && this.Y % (i * 3 + 1) == 0) {
            k = p_320133_3_ + (this.V.m_188503_(3) - 1);
         }

         int l = p_320133_4_ - j * 8 - 9;
         graphicsIn.a(resourcelocation, l, k, 9, 9);
         if (j * 2 + 1 < i) {
            graphicsIn.a(resourcelocation2, l, k, 9, 9);
         }

         if (j * 2 + 1 == i) {
            graphicsIn.a(resourcelocation1, l, k, 9, 9);
         }
      }

      RenderSystem.disableBlend();
   }

   private void c(GuiGraphics graphicsIn) {
      C_524_ livingentity = this.o();
      if (livingentity != null) {
         int i = this.a(livingentity);
         if (i != 0) {
            int j = (int)Math.ceil((double)livingentity.m_21223_());
            this.W.m_91307_().m_6182_("mountHealth");
            int k = graphicsIn.b() - 39;
            int l = graphicsIn.a() / 2 + 91;
            int i1 = k;
            int j1 = 0;
            RenderSystem.enableBlend();

            while (i > 0) {
               int k1 = Math.min(i, 10);
               i -= k1;

               for (int l1 = 0; l1 < k1; l1++) {
                  int i2 = l - l1 * 8 - 9;
                  graphicsIn.a(D, i2, i1, 9, 9);
                  if (l1 * 2 + 1 + j1 < j) {
                     graphicsIn.a(E, i2, i1, 9, 9);
                  }

                  if (l1 * 2 + 1 + j1 == j) {
                     graphicsIn.a(F, i2, i1, 9, 9);
                  }
               }

               i1 -= 10;
               j1 += 20;
            }

            RenderSystem.disableBlend();
         }
      }
   }

   private void a(GuiGraphics graphicsIn, ResourceLocation locationIn, float alphaIn) {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      graphicsIn.a(1.0F, 1.0F, 1.0F, alphaIn);
      graphicsIn.a(locationIn, 0, 0, -90, 0.0F, 0.0F, graphicsIn.a(), graphicsIn.b(), graphicsIn.a(), graphicsIn.b());
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      graphicsIn.a(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(GuiGraphics graphicsIn, float scopeScaleIn) {
      float f = (float)Math.min(graphicsIn.a(), graphicsIn.b());
      float f1 = Math.min((float)graphicsIn.a() / f, (float)graphicsIn.b() / f) * scopeScaleIn;
      int i = Mth.d(f * f1);
      int j = Mth.d(f * f1);
      int k = (graphicsIn.a() - i) / 2;
      int l = (graphicsIn.b() - j) / 2;
      int i1 = k + i;
      int j1 = l + j;
      RenderSystem.enableBlend();
      graphicsIn.a(I, k, l, -90, 0.0F, 0.0F, i, j, i, j);
      RenderSystem.disableBlend();
      graphicsIn.a(RenderType.F(), 0, j1, graphicsIn.a(), graphicsIn.b(), -90, -16777216);
      graphicsIn.a(RenderType.F(), 0, 0, graphicsIn.a(), l, -90, -16777216);
      graphicsIn.a(RenderType.F(), 0, l, k, j1, -90, -16777216);
      graphicsIn.a(RenderType.F(), i1, l, graphicsIn.a(), j1, -90, -16777216);
   }

   private void a(C_507_ entityIn) {
      C_4675_ blockpos = C_4675_.m_274561_(entityIn.m_20185_(), entityIn.m_20188_(), entityIn.m_20189_());
      float f = LightTexture.a(entityIn.m_9236_().m_6042_(), entityIn.m_9236_().m_46803_(blockpos));
      float f1 = Mth.a(1.0F - f, 0.0F, 1.0F);
      this.a = this.a + (f1 - this.a) * 0.01F;
   }

   private void a(GuiGraphics graphicsIn, @Nullable C_507_ entityIn) {
      if (!Config.isVignetteEnabled()) {
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
      } else {
         C_2110_ worldborder = this.W.r.m_6857_();
         float f = 0.0F;
         if (entityIn != null) {
            float f1 = (float)worldborder.m_61925_(entityIn);
            double d0 = Math.min(worldborder.m_61966_() * (double)worldborder.m_61967_() * 1000.0, Math.abs(worldborder.m_61961_() - worldborder.m_61959_()));
            double d1 = Math.max((double)worldborder.m_61968_(), d0);
            if ((double)f1 < d1) {
               f = 1.0F - (float)((double)f1 / d1);
            }
         }

         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
         );
         if (f > 0.0F) {
            f = Mth.a(f, 0.0F, 1.0F);
            graphicsIn.a(0.0F, f, f, 1.0F);
         } else {
            float f2 = this.a;
            f2 = Mth.a(f2, 0.0F, 1.0F);
            graphicsIn.a(f2, f2, f2, 1.0F);
         }

         graphicsIn.a(G, 0, 0, -90, 0.0F, 0.0F, graphicsIn.a(), graphicsIn.b(), graphicsIn.a(), graphicsIn.b());
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         graphicsIn.a(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private void b(GuiGraphics graphicsIn, float timeInPortal) {
      if (timeInPortal < 1.0F) {
         timeInPortal *= timeInPortal;
         timeInPortal *= timeInPortal;
         timeInPortal = timeInPortal * 0.8F + 0.2F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      graphicsIn.a(1.0F, 1.0F, 1.0F, timeInPortal);
      TextureAtlasSprite textureatlassprite = this.W.ao().a().a(C_1710_.f_50142_.o());
      graphicsIn.a(0, 0, -90, graphicsIn.a(), graphicsIn.b(), textureatlassprite);
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      graphicsIn.a(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void a(GuiGraphics graphicsIn, int x, int y, C_336468_ partialTicks, C_1141_ player, C_1391_ stack, int seedIn) {
      if (!stack.m_41619_()) {
         float f = (float)stack.m_41612_() - partialTicks.m_338527_(false);
         if (f > 0.0F) {
            float f1 = 1.0F + f / 5.0F;
            graphicsIn.c().a();
            graphicsIn.c().a((float)(x + 8), (float)(y + 12), 0.0F);
            graphicsIn.c().b(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            graphicsIn.c().a((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
         }

         graphicsIn.a(player, stack, x, y, seedIn);
         if (f > 0.0F) {
            graphicsIn.c().b();
         }

         graphicsIn.a(this.W.h, stack, x, y);
      }
   }

   public void a(boolean skipTickIn) {
      this.q();
      if (!skipTickIn) {
         this.p();
      }
   }

   private void p() {
      if (this.W.r == null) {
         TextureAnimations.updateAnimations();
      }

      if (this.aa > 0) {
         this.aa--;
      }

      if (this.ak > 0) {
         this.ak--;
         if (this.ak <= 0) {
            this.al = null;
            this.am = null;
         }
      }

      this.Y++;
      C_507_ entity = this.W.m_91288_();
      if (entity != null) {
         this.a(entity);
      }

      if (this.W.f_91074_ != null) {
         C_1391_ itemstack = this.W.f_91074_.fY().m_36056_();
         boolean forgeEquals = true;
         if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
            C_4996_ stackTip = (C_4996_)Reflector.call(itemstack, Reflector.IForgeItemStack_getHighlightTip, itemstack.m_41786_());
            C_4996_ highlightTip = (C_4996_)Reflector.call(this.ae, Reflector.IForgeItemStack_getHighlightTip, this.ae.m_41786_());
            forgeEquals = Config.equals(stackTip, highlightTip);
         }

         if (itemstack.m_41619_()) {
            this.ad = 0;
         } else if (this.ae.m_41619_() || !itemstack.m_150930_(this.ae.m_41720_()) || !itemstack.m_41786_().equals(this.ae.m_41786_()) || !forgeEquals) {
            this.ad = (int)(40.0 * this.W.m.B().c());
         } else if (this.ad > 0) {
            this.ad--;
         }

         this.ae = itemstack;
      }

      this.X.a();
   }

   private void q() {
      MinecraftServer minecraftserver = this.W.V();
      boolean flag = minecraftserver != null && minecraftserver.m_195518_();
      this.av = this.au;
      this.au = Mth.i(0.2F, this.au, flag ? 1.0F : 0.0F);
   }

   public void a(C_4996_ recordName) {
      C_4996_ component = C_4996_.m_237110_("record.nowPlaying", new Object[]{recordName});
      this.a(component, true);
      this.W.m_240477_().m_168785_(component);
   }

   public void a(C_4996_ component, boolean animateColor) {
      this.b(false);
      this.Z = component;
      this.aa = 60;
      this.ab = animateColor;
   }

   public void b(boolean disabledIn) {
      this.ac = disabledIn;
   }

   public boolean b() {
      return this.ac && this.aa > 0;
   }

   public void a(int timeFadeIn, int timeDisplay, int timeFadeOut) {
      if (timeFadeIn >= 0) {
         this.an = timeFadeIn;
      }

      if (timeDisplay >= 0) {
         this.ao = timeDisplay;
      }

      if (timeFadeOut >= 0) {
         this.ap = timeFadeOut;
      }

      if (this.ak > 0) {
         this.ak = this.an + this.ao + this.ap;
      }
   }

   public void b(C_4996_ componentIn) {
      this.am = componentIn;
   }

   public void c(C_4996_ componentIn) {
      this.al = componentIn;
      this.ak = this.an + this.ao + this.ap;
   }

   public void c() {
      this.al = null;
      this.am = null;
      this.ak = 0;
   }

   public ChatComponent d() {
      return this.X;
   }

   public int e() {
      return this.Y;
   }

   public Font f() {
      return this.W.h;
   }

   public C_3496_ g() {
      return this.ah;
   }

   public C_3479_ h() {
      return this.ai;
   }

   public void i() {
      this.ai.m_94529_();
      this.aj.a();
      this.W.m_91300_().m_94919_();
      this.af.o();
      this.X.a(true);
   }

   public BossHealthOverlay j() {
      return this.aj;
   }

   public DebugScreenOverlay k() {
      return this.af;
   }

   public void l() {
      this.af.a();
   }

   public void b(GuiGraphics graphicsIn, C_336468_ partialTicks) {
      if (this.W.m.af().c() && (this.au > 0.0F || this.av > 0.0F)) {
         int i = Mth.d(255.0F * Mth.a(Mth.i(partialTicks.m_338557_(), this.av, this.au), 0.0F, 1.0F));
         if (i > 8) {
            Font font = this.f();
            int j = font.a(M);
            int k = C_175_.m_320289_(i, -1);
            int l = graphicsIn.a() - j - 2;
            int i1 = graphicsIn.b() - 35;
            graphicsIn.a(font, M, l, i1, j, k);
         }
      }
   }

   static record a(C_4996_ a, C_4996_ b, int c) {
   }

   static enum b {
      a(
         ResourceLocation.b("hud/heart/container"),
         ResourceLocation.b("hud/heart/container_blinking"),
         ResourceLocation.b("hud/heart/container"),
         ResourceLocation.b("hud/heart/container_blinking"),
         ResourceLocation.b("hud/heart/container_hardcore"),
         ResourceLocation.b("hud/heart/container_hardcore_blinking"),
         ResourceLocation.b("hud/heart/container_hardcore"),
         ResourceLocation.b("hud/heart/container_hardcore_blinking")
      ),
      b(
         ResourceLocation.b("hud/heart/full"),
         ResourceLocation.b("hud/heart/full_blinking"),
         ResourceLocation.b("hud/heart/half"),
         ResourceLocation.b("hud/heart/half_blinking"),
         ResourceLocation.b("hud/heart/hardcore_full"),
         ResourceLocation.b("hud/heart/hardcore_full_blinking"),
         ResourceLocation.b("hud/heart/hardcore_half"),
         ResourceLocation.b("hud/heart/hardcore_half_blinking")
      ),
      c(
         ResourceLocation.b("hud/heart/poisoned_full"),
         ResourceLocation.b("hud/heart/poisoned_full_blinking"),
         ResourceLocation.b("hud/heart/poisoned_half"),
         ResourceLocation.b("hud/heart/poisoned_half_blinking"),
         ResourceLocation.b("hud/heart/poisoned_hardcore_full"),
         ResourceLocation.b("hud/heart/poisoned_hardcore_full_blinking"),
         ResourceLocation.b("hud/heart/poisoned_hardcore_half"),
         ResourceLocation.b("hud/heart/poisoned_hardcore_half_blinking")
      ),
      d(
         ResourceLocation.b("hud/heart/withered_full"),
         ResourceLocation.b("hud/heart/withered_full_blinking"),
         ResourceLocation.b("hud/heart/withered_half"),
         ResourceLocation.b("hud/heart/withered_half_blinking"),
         ResourceLocation.b("hud/heart/withered_hardcore_full"),
         ResourceLocation.b("hud/heart/withered_hardcore_full_blinking"),
         ResourceLocation.b("hud/heart/withered_hardcore_half"),
         ResourceLocation.b("hud/heart/withered_hardcore_half_blinking")
      ),
      e(
         ResourceLocation.b("hud/heart/absorbing_full"),
         ResourceLocation.b("hud/heart/absorbing_full_blinking"),
         ResourceLocation.b("hud/heart/absorbing_half"),
         ResourceLocation.b("hud/heart/absorbing_half_blinking"),
         ResourceLocation.b("hud/heart/absorbing_hardcore_full"),
         ResourceLocation.b("hud/heart/absorbing_hardcore_full_blinking"),
         ResourceLocation.b("hud/heart/absorbing_hardcore_half"),
         ResourceLocation.b("hud/heart/absorbing_hardcore_half_blinking")
      ),
      f(
         ResourceLocation.b("hud/heart/frozen_full"),
         ResourceLocation.b("hud/heart/frozen_full_blinking"),
         ResourceLocation.b("hud/heart/frozen_half"),
         ResourceLocation.b("hud/heart/frozen_half_blinking"),
         ResourceLocation.b("hud/heart/frozen_hardcore_full"),
         ResourceLocation.b("hud/heart/frozen_hardcore_full_blinking"),
         ResourceLocation.b("hud/heart/frozen_hardcore_half"),
         ResourceLocation.b("hud/heart/frozen_hardcore_half_blinking")
      );

      private final ResourceLocation g;
      private final ResourceLocation h;
      private final ResourceLocation i;
      private final ResourceLocation j;
      private final ResourceLocation k;
      private final ResourceLocation l;
      private final ResourceLocation m;
      private final ResourceLocation n;

      private b(
         final ResourceLocation fullIn,
         final ResourceLocation fullBlinkingIn,
         final ResourceLocation halfIn,
         final ResourceLocation halfBlinkingIn,
         final ResourceLocation hardcoreFullIn,
         final ResourceLocation hardcoreFullBlinkingIn,
         final ResourceLocation hardcoreHalfIn,
         final ResourceLocation hardcoreHalfBlinkingIn
      ) {
         this.g = fullIn;
         this.h = fullBlinkingIn;
         this.i = halfIn;
         this.j = halfBlinkingIn;
         this.k = hardcoreFullIn;
         this.l = hardcoreFullBlinkingIn;
         this.m = hardcoreHalfIn;
         this.n = hardcoreHalfBlinkingIn;
      }

      public ResourceLocation a(boolean hardcoreIn, boolean halfIn, boolean blinkingIn) {
         if (!hardcoreIn) {
            if (halfIn) {
               return blinkingIn ? this.j : this.i;
            } else {
               return blinkingIn ? this.h : this.g;
            }
         } else if (halfIn) {
            return blinkingIn ? this.n : this.m;
         } else {
            return blinkingIn ? this.l : this.k;
         }
      }

      static Gui.b a(C_1141_ playerIn) {
         Gui.b gui$hearttype;
         if (playerIn.m_21023_(C_500_.f_19614_)) {
            gui$hearttype = c;
         } else if (playerIn.m_21023_(C_500_.f_19615_)) {
            gui$hearttype = d;
         } else if (playerIn.m_146890_()) {
            gui$hearttype = f;
         } else {
            gui$hearttype = b;
         }

         return gui$hearttype;
      }
   }
}
