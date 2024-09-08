package net.minecraft.src;

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

public class C_3431_ {
   private static final C_5265_ f_291829_ = C_5265_.m_340282_("hud/crosshair");
   private static final C_5265_ f_291363_ = C_5265_.m_340282_("hud/crosshair_attack_indicator_full");
   private static final C_5265_ f_291360_ = C_5265_.m_340282_("hud/crosshair_attack_indicator_background");
   private static final C_5265_ f_290711_ = C_5265_.m_340282_("hud/crosshair_attack_indicator_progress");
   private static final C_5265_ f_291473_ = C_5265_.m_340282_("hud/effect_background_ambient");
   private static final C_5265_ f_290513_ = C_5265_.m_340282_("hud/effect_background");
   private static final C_5265_ f_291149_ = C_5265_.m_340282_("hud/hotbar");
   private static final C_5265_ f_290368_ = C_5265_.m_340282_("hud/hotbar_selection");
   private static final C_5265_ f_291322_ = C_5265_.m_340282_("hud/hotbar_offhand_left");
   private static final C_5265_ f_291864_ = C_5265_.m_340282_("hud/hotbar_offhand_right");
   private static final C_5265_ f_290833_ = C_5265_.m_340282_("hud/hotbar_attack_indicator_background");
   private static final C_5265_ f_291301_ = C_5265_.m_340282_("hud/hotbar_attack_indicator_progress");
   private static final C_5265_ f_291483_ = C_5265_.m_340282_("hud/jump_bar_background");
   private static final C_5265_ f_291104_ = C_5265_.m_340282_("hud/jump_bar_cooldown");
   private static final C_5265_ f_291820_ = C_5265_.m_340282_("hud/jump_bar_progress");
   private static final C_5265_ f_291741_ = C_5265_.m_340282_("hud/experience_bar_background");
   private static final C_5265_ f_291491_ = C_5265_.m_340282_("hud/experience_bar_progress");
   private static final C_5265_ f_291879_ = C_5265_.m_340282_("hud/armor_empty");
   private static final C_5265_ f_291785_ = C_5265_.m_340282_("hud/armor_half");
   private static final C_5265_ f_290436_ = C_5265_.m_340282_("hud/armor_full");
   private static final C_5265_ f_291486_ = C_5265_.m_340282_("hud/food_empty_hunger");
   private static final C_5265_ f_291577_ = C_5265_.m_340282_("hud/food_half_hunger");
   private static final C_5265_ f_290366_ = C_5265_.m_340282_("hud/food_full_hunger");
   private static final C_5265_ f_290694_ = C_5265_.m_340282_("hud/food_empty");
   private static final C_5265_ f_291002_ = C_5265_.m_340282_("hud/food_half");
   private static final C_5265_ f_291650_ = C_5265_.m_340282_("hud/food_full");
   private static final C_5265_ f_291833_ = C_5265_.m_340282_("hud/air");
   private static final C_5265_ f_291609_ = C_5265_.m_340282_("hud/air_bursting");
   private static final C_5265_ f_290939_ = C_5265_.m_340282_("hud/heart/vehicle_container");
   private static final C_5265_ f_291256_ = C_5265_.m_340282_("hud/heart/vehicle_full");
   private static final C_5265_ f_290334_ = C_5265_.m_340282_("hud/heart/vehicle_half");
   private static final C_5265_ f_92981_ = C_5265_.m_340282_("textures/misc/vignette.png");
   private static final C_5265_ f_92983_ = C_5265_.m_340282_("textures/misc/pumpkinblur.png");
   private static final C_5265_ f_168665_ = C_5265_.m_340282_("textures/misc/spyglass_scope.png");
   private static final C_5265_ f_168666_ = C_5265_.m_340282_("textures/misc/powder_snow_outline.png");
   private static final Comparator<C_302146_> f_302813_ = Comparator.comparing(C_302146_::f_303807_)
      .reversed()
      .thenComparing(C_302146_::f_302847_, String.CASE_INSENSITIVE_ORDER);
   private static final C_4996_ f_92984_ = C_4996_.m_237115_("demo.demoExpired");
   private static final C_4996_ f_193830_ = C_4996_.m_237115_("menu.savingLevel");
   private static final float f_168668_ = 5.0F;
   private static final int f_168669_ = 10;
   private static final int f_168670_ = 10;
   private static final String f_168671_ = ": ";
   private static final float f_168672_ = 0.2F;
   private static final int f_168673_ = 9;
   private static final int f_168674_ = 8;
   private static final float f_193831_ = 0.2F;
   private final C_212974_ f_92985_ = C_212974_.m_216327_();
   private final C_3391_ f_92986_;
   private final C_3454_ f_92988_;
   private int f_92989_;
   @Nullable
   private C_4996_ f_92990_;
   private int f_92991_;
   private boolean f_92992_;
   private boolean f_238167_;
   public float f_92980_ = 1.0F;
   private int f_92993_;
   private C_1391_ f_92994_ = C_1391_.f_41583_;
   protected C_3462_ f_291320_;
   private final C_3487_ f_92996_;
   private final C_3496_ f_92997_;
   private final C_3479_ f_92998_;
   private final C_3450_ f_92999_;
   private int f_93000_;
   @Nullable
   private C_4996_ f_93001_;
   @Nullable
   private C_4996_ f_93002_;
   private int f_92970_;
   private int f_92971_;
   private int f_92972_;
   private int f_92973_;
   private int f_92974_;
   private long f_92975_;
   private long f_92976_;
   private float f_193828_;
   private float f_193829_;
   private final C_313825_ f_316662_ = new C_313825_();
   private float f_168664_;

   public C_3431_(C_3391_ mcIn) {
      this.f_92986_ = mcIn;
      this.f_291320_ = new C_3462_(mcIn);
      this.f_92997_ = new C_3496_(mcIn);
      this.f_92988_ = new C_3454_(mcIn);
      this.f_92998_ = new C_3479_(mcIn, this);
      this.f_92999_ = new C_3450_(mcIn);
      this.f_92996_ = new C_3487_(mcIn);
      this.m_93006_();
      C_313825_ layereddraw = new C_313825_()
         .m_322513_(this::m_318727_)
         .m_322513_(this::m_280130_)
         .m_322513_(this::m_319215_)
         .m_322513_(this::m_324707_)
         .m_322513_(this::m_280523_)
         .m_322513_((graphicsIn, partialTicks) -> this.f_92999_.m_280652_(graphicsIn));
      C_313825_ layereddraw1 = new C_313825_()
         .m_322513_(this::m_280339_)
         .m_322513_((graphicsIn, partialTicks) -> {
            if (this.f_291320_.m_294516_()) {
               this.f_291320_.m_94056_(graphicsIn);
            }
         })
         .m_322513_(this::m_323286_)
         .m_322513_(this::m_324281_)
         .m_322513_(this::m_320668_)
         .m_322513_(this::m_322096_)
         .m_322513_(this::m_320053_)
         .m_322513_((graphicsIn, partialTicks) -> this.f_92996_.m_280227_(graphicsIn));
      this.f_316662_.m_323151_(layereddraw, () -> !mcIn.f_91066_.f_92062_).m_322513_(this::m_322680_).m_323151_(layereddraw1, () -> !mcIn.f_91066_.f_92062_);
   }

   public void m_93006_() {
      this.f_92970_ = 10;
      this.f_92971_ = 70;
      this.f_92972_ = 20;
   }

   public void m_280421_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      RenderSystem.enableDepthTest();
      this.f_316662_.m_322951_(graphicsIn, partialTicks);
      RenderSystem.disableDepthTest();
   }

   private void m_318727_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      if (Config.isVignetteEnabled()) {
         this.m_280154_(graphicsIn, this.f_92986_.m_91288_());
      }

      float f = partialTicks.m_339005_();
      this.f_168664_ = C_188_.m_14179_(0.5F * f, this.f_168664_, 1.125F);
      if (this.f_92986_.f_91066_.m_92176_().m_90612_()) {
         if (this.f_92986_.f_91074_.gw()) {
            this.m_280278_(graphicsIn, this.f_168664_);
         } else {
            this.f_168664_ = 0.5F;
            C_1391_ itemstack = this.f_92986_.f_91074_.fY().m_36052_(3);
            if (itemstack.m_150930_(C_1710_.f_50143_.m_5456_())) {
               this.m_280155_(graphicsIn, f_92983_, 1.0F);
            }
         }
      }

      if (this.f_92986_.f_91074_.cn() > 0) {
         this.m_280155_(graphicsIn, f_168666_, this.f_92986_.f_91074_.co());
      }

      float f1 = C_188_.m_14179_(partialTicks.m_338527_(false), this.f_92986_.f_91074_.f_108590_, this.f_92986_.f_91074_.f_108589_);
      if (f1 > 0.0F && !this.f_92986_.f_91074_.b(C_500_.f_19604_)) {
         this.m_280379_(graphicsIn, f1);
      }
   }

   private void m_322680_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      if (this.f_92986_.f_91074_.gc() > 0) {
         this.f_92986_.m_91307_().m_6180_("sleep");
         float f = (float)this.f_92986_.f_91074_.gc();
         float f1 = f / 100.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F - (f - 100.0F) / 10.0F;
         }

         int i = (int)(220.0F * f1) << 24 | 1052704;
         graphicsIn.m_285944_(C_4168_.m_286086_(), 0, 0, graphicsIn.m_280182_(), graphicsIn.m_280206_(), i);
         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private void m_324281_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      C_3429_ font = this.m_93082_();
      if (this.f_92990_ != null && this.f_92991_ > 0) {
         this.f_92986_.m_91307_().m_6180_("overlayMessage");
         float f = (float)this.f_92991_ - partialTicks.m_338527_(false);
         int i = (int)(f * 255.0F / 20.0F);
         if (i > 255) {
            i = 255;
         }

         if (i > 8) {
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_252880_((float)(graphicsIn.m_280182_() / 2), (float)(graphicsIn.m_280206_() - 68), 0.0F);
            int j;
            if (this.f_92992_) {
               j = C_188_.m_339996_(f / 50.0F, 0.7F, 0.6F, i);
            } else {
               j = C_175_.m_320289_(i, -1);
            }

            int k = font.m_92852_(this.f_92990_);
            graphicsIn.m_339210_(font, this.f_92990_, -k / 2, -4, k, j);
            graphicsIn.m_280168_().m_85849_();
         }

         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private void m_320668_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      if (this.f_93001_ != null && this.f_93000_ > 0) {
         C_3429_ font = this.m_93082_();
         this.f_92986_.m_91307_().m_6180_("titleAndSubtitle");
         float f = (float)this.f_93000_ - partialTicks.m_338527_(false);
         int i = 255;
         if (this.f_93000_ > this.f_92972_ + this.f_92971_) {
            float f1 = (float)(this.f_92970_ + this.f_92971_ + this.f_92972_) - f;
            i = (int)(f1 * 255.0F / (float)this.f_92970_);
         }

         if (this.f_93000_ <= this.f_92972_) {
            i = (int)(f * 255.0F / (float)this.f_92972_);
         }

         i = C_188_.m_14045_(i, 0, 255);
         if (i > 8) {
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_252880_((float)(graphicsIn.m_280182_() / 2), (float)(graphicsIn.m_280206_() / 2), 0.0F);
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_85841_(4.0F, 4.0F, 4.0F);
            int l = font.m_92852_(this.f_93001_);
            int j = C_175_.m_320289_(i, -1);
            graphicsIn.m_339210_(font, this.f_93001_, -l / 2, -10, l, j);
            graphicsIn.m_280168_().m_85849_();
            if (this.f_93002_ != null) {
               graphicsIn.m_280168_().m_85836_();
               graphicsIn.m_280168_().m_85841_(2.0F, 2.0F, 2.0F);
               int k = font.m_92852_(this.f_93002_);
               graphicsIn.m_339210_(font, this.f_93002_, -k / 2, 5, k, j);
               graphicsIn.m_280168_().m_85849_();
            }

            graphicsIn.m_280168_().m_85849_();
         }

         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private void m_322096_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      if (!this.f_92988_.m_93818_()) {
         C_3161_ window = this.f_92986_.m_91268_();
         int i = C_188_.m_14107_(this.f_92986_.f_91067_.m_91589_() * (double)window.m_85445_() / (double)window.m_85443_());
         int j = C_188_.m_14107_(this.f_92986_.f_91067_.m_91594_() * (double)window.m_85446_() / (double)window.m_85444_());
         this.f_92988_.m_280165_(graphicsIn, this.f_92989_, i, j, false);
      }
   }

   private void m_323286_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      C_3076_ scoreboard = this.f_92986_.f_91073_.m_6188_();
      C_3073_ objective = null;
      C_3074_ playerteam = scoreboard.m_83500_(this.f_92986_.f_91074_.cB());
      if (playerteam != null) {
         C_290072_ displayslot = C_290072_.m_293761_(playerteam.m_7414_());
         if (displayslot != null) {
            objective = scoreboard.m_83416_(displayslot);
         }
      }

      C_3073_ objective1 = objective != null ? objective : scoreboard.m_83416_(C_290072_.SIDEBAR);
      if (objective1 != null) {
         this.m_280030_(graphicsIn, objective1);
      }
   }

   private void m_320053_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      C_3076_ scoreboard = this.f_92986_.f_91073_.m_6188_();
      C_3073_ objective = scoreboard.m_83416_(C_290072_.LIST);
      if (this.f_92986_.f_91066_.f_92099_.m_90857_()
         && (!this.f_92986_.m_91090_() || this.f_92986_.f_91074_.f_108617_.m_246170_().size() > 1 || objective != null)) {
         this.f_92998_.m_94556_(true);
         this.f_92998_.m_280406_(graphicsIn, graphicsIn.m_280182_(), scoreboard, objective);
      } else {
         this.f_92998_.m_94556_(false);
      }
   }

   private void m_280130_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      C_3401_ options = this.f_92986_.f_91066_;
      if (options.m_92176_().m_90612_() && (this.f_92986_.f_91072_.m_105295_() != C_1593_.SPECTATOR || this.m_93024_(this.f_92986_.f_91077_))) {
         RenderSystem.enableBlend();
         if (this.f_291320_.m_294516_() && !this.f_92986_.f_91074_.go() && !options.m_231824_().m_231551_()) {
            C_3373_ camera = this.f_92986_.f_91063_.m_109153_();
            Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
            matrix4fstack.pushMatrix();
            matrix4fstack.mul(graphicsIn.m_280168_().m_85850_().m_252922_());
            matrix4fstack.translate((float)(graphicsIn.m_280182_() / 2), (float)(graphicsIn.m_280206_() / 2), 0.0F);
            matrix4fstack.rotateX(-camera.m_90589_() * (float) (Math.PI / 180.0));
            matrix4fstack.rotateY(camera.m_90590_() * (float) (Math.PI / 180.0));
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
            graphicsIn.m_292816_(f_291829_, (graphicsIn.m_280182_() - 15) / 2, (graphicsIn.m_280206_() - 15) / 2, 15, 15);
            if (this.f_92986_.f_91066_.m_232120_().m_231551_() == C_3371_.CROSSHAIR) {
               float f = this.f_92986_.f_91074_.F(0.0F);
               boolean flag = false;
               if (this.f_92986_.f_91076_ != null && this.f_92986_.f_91076_ instanceof C_524_ && f >= 1.0F) {
                  flag = this.f_92986_.f_91074_.gr() > 5.0F;
                  flag &= this.f_92986_.f_91076_.m_6084_();
               }

               int j = graphicsIn.m_280206_() / 2 - 7 + 16;
               int k = graphicsIn.m_280182_() / 2 - 8;
               if (flag) {
                  graphicsIn.m_292816_(f_291363_, k, j, 16, 16);
               } else if (f < 1.0F) {
                  int l = (int)(f * 17.0F);
                  graphicsIn.m_292816_(f_291360_, k, j, 16, 4);
                  graphicsIn.m_294122_(f_290711_, 16, 4, 0, 0, k, j, l, 4);
               }
            }

            RenderSystem.defaultBlendFunc();
         }

         RenderSystem.disableBlend();
      }
   }

   private boolean m_93024_(@Nullable C_3043_ rayTraceIn) {
      if (rayTraceIn == null) {
         return false;
      } else if (rayTraceIn.m_6662_() == C_3044_.ENTITY) {
         return ((C_3042_)rayTraceIn).m_82443_() instanceof C_474_;
      } else if (rayTraceIn.m_6662_() == C_3044_.BLOCK) {
         C_4675_ blockpos = ((C_3041_)rayTraceIn).m_82425_();
         C_1596_ level = this.f_92986_.f_91073_;
         return level.m_8055_(blockpos).m_60750_(level, blockpos) != null;
      } else {
         return false;
      }
   }

   private void m_280523_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      Collection<C_498_> collection = this.f_92986_.f_91074_.et();
      if (!collection.isEmpty()) {
         if (this.f_92986_.f_91080_ instanceof C_3657_ effectrenderinginventoryscreen && effectrenderinginventoryscreen.m_194018_()) {
            return;
         }

         RenderSystem.enableBlend();
         int j1 = 0;
         int k1 = 0;
         C_4503_ mobeffecttexturemanager = this.f_92986_.m_91306_();
         List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());

         for (C_498_ mobeffectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
            C_203228_<C_496_> holder = mobeffectinstance.m_19544_();
            IClientMobEffectExtensions cmee = IClientMobEffectExtensions.of(mobeffectinstance);
            if ((cmee == null || cmee.isVisibleInGui(mobeffectinstance)) && mobeffectinstance.m_19575_()) {
               int i = graphicsIn.m_280182_();
               int j = 1;
               if (this.f_92986_.m_91402_()) {
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
                  graphicsIn.m_292816_(f_291473_, i, j, 24, 24);
               } else {
                  graphicsIn.m_292816_(f_290513_, i, j, 24, 24);
                  if (mobeffectinstance.m_267633_(200)) {
                     int k = mobeffectinstance.m_19557_();
                     int l = 10 - k / 20;
                     f = C_188_.m_14036_((float)k / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + C_188_.m_14089_((float)k * (float) Math.PI / 5.0F) * C_188_.m_14036_((float)l / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               if (cmee == null || !cmee.renderGuiIcon(mobeffectinstance, this, graphicsIn, i, j, 0.0F, f)) {
                  C_4486_ textureatlassprite = mobeffecttexturemanager.m_118732_(holder);
                  int l1 = i;
                  int i1 = j;
                  float f1 = f;
                  list.add((Runnable)() -> {
                     graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, f1);
                     graphicsIn.m_280159_(l1 + 3, i1 + 3, 0, 18, 18, textureatlassprite);
                     graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
                  });
               }
            }
         }

         list.forEach(Runnable::run);
         RenderSystem.disableBlend();
      }
   }

   private void m_319215_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      if (this.f_92986_.f_91072_.m_105295_() == C_1593_.SPECTATOR) {
         this.f_92997_.m_280623_(graphicsIn);
      } else {
         this.m_324469_(graphicsIn, partialTicks);
      }

      int i = graphicsIn.m_280182_() / 2 - 91;
      C_534_ playerrideablejumping = this.f_92986_.f_91074_.m_245714_();
      if (playerrideablejumping != null) {
         this.m_280069_(playerrideablejumping, graphicsIn, i);
      } else if (this.m_322177_()) {
         this.m_280276_(graphicsIn, i);
      }

      if (this.f_92986_.f_91072_.m_105205_()) {
         this.m_280173_(graphicsIn);
      }

      this.m_280250_(graphicsIn);
      if (this.f_92986_.f_91072_.m_105295_() != C_1593_.SPECTATOR) {
         this.m_280295_(graphicsIn);
      } else if (this.f_92986_.f_91074_.R_()) {
         this.f_92997_.m_280365_(graphicsIn);
      }
   }

   private void m_324469_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      C_1141_ player = this.m_93092_();
      if (player != null) {
         C_1391_ itemstack = player.eU();
         C_520_ humanoidarm = player.m_5737_().m_20828_();
         int i = graphicsIn.m_280182_() / 2;
         int j = 182;
         int k = 91;
         RenderSystem.enableBlend();
         graphicsIn.m_280168_().m_85836_();
         graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, -90.0F);
         graphicsIn.m_292816_(f_291149_, i - 91, graphicsIn.m_280206_() - 22, 182, 22);
         graphicsIn.m_292816_(f_290368_, i - 91 - 1 + player.m_150109_().f_35977_ * 20, graphicsIn.m_280206_() - 22 - 1, 24, 23);
         if (!itemstack.m_41619_()) {
            if (humanoidarm == C_520_.LEFT) {
               graphicsIn.m_292816_(f_291322_, i - 91 - 29, graphicsIn.m_280206_() - 23, 29, 24);
            } else {
               graphicsIn.m_292816_(f_291864_, i + 91, graphicsIn.m_280206_() - 23, 29, 24);
            }
         }

         graphicsIn.m_280168_().m_85849_();
         RenderSystem.disableBlend();
         int l = 1;
         CustomItems.setRenderOffHand(false);

         for (int i1 = 0; i1 < 9; i1++) {
            int j1 = i - 90 + i1 * 20 + 2;
            int k1 = graphicsIn.m_280206_() - 16 - 3;
            this.m_280585_(graphicsIn, j1, k1, partialTicks, player, (C_1391_)player.m_150109_().f_35974_.get(i1), l++);
         }

         if (!itemstack.m_41619_()) {
            CustomItems.setRenderOffHand(true);
            int i2 = graphicsIn.m_280206_() - 16 - 3;
            if (humanoidarm == C_520_.LEFT) {
               this.m_280585_(graphicsIn, i - 91 - 26, i2, partialTicks, player, itemstack, l++);
            } else {
               this.m_280585_(graphicsIn, i + 91 + 10, i2, partialTicks, player, itemstack, l++);
            }

            CustomItems.setRenderOffHand(false);
         }

         if (this.f_92986_.f_91066_.m_232120_().m_231551_() == C_3371_.HOTBAR) {
            RenderSystem.enableBlend();
            float f = this.f_92986_.f_91074_.F(0.0F);
            if (f < 1.0F) {
               int j2 = graphicsIn.m_280206_() - 20;
               int k2 = i + 91 + 6;
               if (humanoidarm == C_520_.RIGHT) {
                  k2 = i - 91 - 22;
               }

               int l1 = (int)(f * 19.0F);
               graphicsIn.m_292816_(f_290833_, k2, j2, 18, 18);
               graphicsIn.m_294122_(f_291301_, 18, 18, 0, 18 - l1, k2, j2 + 18 - l1, 18, l1);
            }

            RenderSystem.disableBlend();
         }
      }
   }

   private void m_280069_(C_534_ jumpingIn, C_279497_ graphicsIn, int x) {
      this.f_92986_.m_91307_().m_6180_("jumpBar");
      float f = this.f_92986_.f_91074_.m_108634_();
      int i = 182;
      int j = (int)(f * 183.0F);
      int k = graphicsIn.m_280206_() - 32 + 3;
      RenderSystem.enableBlend();
      graphicsIn.m_292816_(f_291483_, x, k, 182, 5);
      if (jumpingIn.m_245614_() > 0) {
         graphicsIn.m_292816_(f_291104_, x, k, 182, 5);
      } else if (j > 0) {
         graphicsIn.m_294122_(f_291820_, 182, 5, 0, 0, x, k, j, 5);
      }

      RenderSystem.disableBlend();
      this.f_92986_.m_91307_().m_7238_();
   }

   private void m_280276_(C_279497_ graphicsIn, int x) {
      this.f_92986_.m_91307_().m_6180_("expBar");
      int i = this.f_92986_.f_91074_.gh();
      if (i > 0) {
         int j = 182;
         int k = (int)(this.f_92986_.f_91074_.cs * 183.0F);
         int l = graphicsIn.m_280206_() - 32 + 3;
         RenderSystem.enableBlend();
         graphicsIn.m_292816_(f_291741_, x, l, 182, 5);
         if (k > 0) {
            graphicsIn.m_294122_(f_291491_, 182, 5, 0, 0, x, l, k, 5);
         }

         RenderSystem.disableBlend();
      }

      this.f_92986_.m_91307_().m_7238_();
   }

   private void m_324707_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      int i = this.f_92986_.f_91074_.cq;
      if (this.m_322177_() && i > 0) {
         this.f_92986_.m_91307_().m_6180_("expLevel");
         int col = 8453920;
         if (Config.isCustomColors()) {
            col = CustomColors.getExpBarTextColor(col);
         }

         String s = i + "";
         int j = (graphicsIn.m_280182_() - this.m_93082_().m_92895_(s)) / 2;
         int k = graphicsIn.m_280206_() - 31 - 4;
         graphicsIn.m_280056_(this.m_93082_(), s, j + 1, k, 0, false);
         graphicsIn.m_280056_(this.m_93082_(), s, j - 1, k, 0, false);
         graphicsIn.m_280056_(this.m_93082_(), s, j, k + 1, 0, false);
         graphicsIn.m_280056_(this.m_93082_(), s, j, k - 1, 0, false);
         graphicsIn.m_280056_(this.m_93082_(), s, j, k, col, false);
         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private boolean m_322177_() {
      return this.f_92986_.f_91074_.m_245714_() == null && this.f_92986_.f_91072_.m_105288_();
   }

   private void m_280295_(C_279497_ graphicsIn) {
      this.renderSelectedItemName(graphicsIn, 0);
   }

   public void renderSelectedItemName(C_279497_ graphicsIn, int yShift) {
      this.f_92986_.m_91307_().m_6180_("selectedItemName");
      if (this.f_92993_ > 0 && !this.f_92994_.m_41619_()) {
         C_5012_ mutablecomponent = C_4996_.m_237119_().m_7220_(this.f_92994_.m_41786_()).m_130940_(this.f_92994_.m_41791_().m_321696_());
         if (Reflector.ForgeRarity_getStyleModifier.exists()) {
            C_1407_ rarityForge = this.f_92994_.m_41791_();
            UnaryOperator<C_5020_> styleModifier = (UnaryOperator<C_5020_>)Reflector.call(rarityForge, Reflector.ForgeRarity_getStyleModifier);
            mutablecomponent = C_4996_.m_237119_().m_7220_(this.f_92994_.m_41786_()).m_130938_(styleModifier);
         }

         if (this.f_92994_.b(C_313616_.f_316016_)) {
            mutablecomponent.m_130940_(C_4856_.ITALIC);
         }

         C_4996_ highlightTip = mutablecomponent;
         if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
            highlightTip = (C_4996_)Reflector.call(this.f_92994_, Reflector.IForgeItemStack_getHighlightTip, mutablecomponent);
         }

         int i = this.m_93082_().m_92852_(highlightTip);
         int j = (graphicsIn.m_280182_() - i) / 2;
         int k = graphicsIn.m_280206_() - Math.max(yShift, 59);
         if (!this.f_92986_.f_91072_.m_105205_()) {
            k += 14;
         }

         int l = (int)((float)this.f_92993_ * 256.0F / 10.0F);
         if (l > 255) {
            l = 255;
         }

         if (l > 0) {
            C_3429_ font = null;
            IClientItemExtensions cmee = IClientItemExtensions.of(this.f_92994_);
            if (cmee != null) {
               font = cmee.getFont(this.f_92994_, IClientItemExtensions.FontContext.SELECTED_ITEM_NAME);
            }

            if (font != null) {
               i = (graphicsIn.m_280182_() - font.m_92852_(highlightTip)) / 2;
               graphicsIn.m_280648_(this.m_93082_(), highlightTip.m_7532_(), j, k, 16777215 + (l << 24));
            } else {
               graphicsIn.m_339210_(this.m_93082_(), mutablecomponent, j, k, i, C_175_.m_320289_(l, -1));
            }
         }
      }

      this.f_92986_.m_91307_().m_7238_();
   }

   private void m_280339_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      if (this.f_92986_.m_91402_()) {
         this.f_92986_.m_91307_().m_6180_("demo");
         C_4996_ component;
         if (this.f_92986_.f_91073_.m_46467_() >= 120500L) {
            component = f_92984_;
         } else {
            component = C_4996_.m_237110_(
               "demo.remainingTime",
               new Object[]{C_200_.m_14404_((int)(120500L - this.f_92986_.f_91073_.m_46467_()), this.f_92986_.f_91073_.m_304826_().m_306179_())}
            );
         }

         int i = this.m_93082_().m_92852_(component);
         int j = graphicsIn.m_280182_() - i - 10;
         int k = 5;
         graphicsIn.m_339210_(this.m_93082_(), component, j, 5, i, -1);
         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private void m_280030_(C_279497_ graphicsIn, C_3073_ objective) {
      C_3076_ scoreboard = objective.m_83313_();
      C_302080_ numberformat = objective.m_305063_(C_301995_.f_303088_);
      C_3431_.C_301998_[] agui$displayentry = (C_3431_.C_301998_[])scoreboard.m_306706_(objective)
         .stream()
         .filter(entryIn -> !entryIn.m_307477_())
         .sorted(f_302813_)
         .limit(15L)
         .map(entry2In -> {
            C_3074_ playerteam = scoreboard.m_83500_(entry2In.f_302847_());
            C_4996_ component1 = entry2In.m_305530_();
            C_4996_ component2 = C_3074_.m_83348_(playerteam, component1);
            C_4996_ component3 = entry2In.m_304640_(numberformat);
            int i1 = this.m_93082_().m_92852_(component3);
            return new C_3431_.C_301998_(component2, component3, i1);
         })
         .toArray(C_3431_.C_301998_[]::new);
      C_4996_ component = objective.m_83322_();
      int i = this.m_93082_().m_92852_(component);
      int j = i;
      int k = this.m_93082_().m_92895_(": ");

      for (C_3431_.C_301998_ gui$displayentry : agui$displayentry) {
         j = Math.max(j, this.m_93082_().m_92852_(gui$displayentry.f_302553_) + (gui$displayentry.f_302793_ > 0 ? k + gui$displayentry.f_302793_ : 0));
      }

      int l = j;
      graphicsIn.m_286007_(() -> {
         int i1 = agui$displayentry.length;
         int j1 = i1 * 9;
         int k1 = graphicsIn.m_280206_() / 2 + j1 / 3;
         int l1 = 3;
         int i2 = graphicsIn.m_280182_() - l - 3;
         int j2 = graphicsIn.m_280182_() - 3 + 2;
         int k2 = this.f_92986_.f_91066_.m_92170_(0.3F);
         int l2 = this.f_92986_.f_91066_.m_92170_(0.4F);
         int i3 = k1 - i1 * 9;
         graphicsIn.m_280509_(i2 - 2, i3 - 9 - 1, j2, i3 - 1, l2);
         graphicsIn.m_280509_(i2 - 2, i3 - 1, j2, k1, k2);
         graphicsIn.m_280614_(this.m_93082_(), component, i2 + l / 2 - i / 2, i3 - 9, -1, false);

         for (int j3 = 0; j3 < i1; j3++) {
            C_3431_.C_301998_ gui$displayentry1 = agui$displayentry[j3];
            int k3 = k1 - (i1 - j3) * 9;
            graphicsIn.m_280614_(this.m_93082_(), gui$displayentry1.f_302553_, i2, k3, -1, false);
            graphicsIn.m_280614_(this.m_93082_(), gui$displayentry1.f_303810_, j2 - gui$displayentry1.f_302793_, k3, -1, false);
         }
      });
   }

   @Nullable
   private C_1141_ m_93092_() {
      return this.f_92986_.m_91288_() instanceof C_1141_ player ? player : null;
   }

   @Nullable
   private C_524_ m_93093_() {
      C_1141_ player = this.m_93092_();
      if (player != null) {
         C_507_ entity = player.dc();
         if (entity == null) {
            return null;
         }

         if (entity instanceof C_524_) {
            return (C_524_)entity;
         }
      }

      return null;
   }

   private int m_93022_(@Nullable C_524_ livingEntityIn) {
      if (livingEntityIn != null && livingEntityIn.bH()) {
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

   private int m_93012_(int healthIn) {
      return (int)Math.ceil((double)healthIn / 10.0);
   }

   private void m_280173_(C_279497_ graphicsIn) {
      C_1141_ player = this.m_93092_();
      if (player != null) {
         int i = C_188_.m_14167_(player.ew());
         boolean flag = this.f_92976_ > (long)this.f_92989_ && (this.f_92976_ - (long)this.f_92989_) / 3L % 2L == 1L;
         long j = C_5322_.m_137550_();
         if (i < this.f_92973_ && player.am > 0) {
            this.f_92975_ = j;
            this.f_92976_ = (long)(this.f_92989_ + 20);
         } else if (i > this.f_92973_ && player.am > 0) {
            this.f_92975_ = j;
            this.f_92976_ = (long)(this.f_92989_ + 10);
         }

         if (j - this.f_92975_ > 1000L) {
            this.f_92973_ = i;
            this.f_92974_ = i;
            this.f_92975_ = j;
         }

         this.f_92973_ = i;
         int k = this.f_92974_;
         this.f_92985_.m_188584_((long)(this.f_92989_ * 312871));
         int l = graphicsIn.m_280182_() / 2 - 91;
         int i1 = graphicsIn.m_280182_() / 2 + 91;
         int j1 = graphicsIn.m_280206_() - 39;
         float f = Math.max((float)player.g(C_559_.f_22276_), (float)Math.max(k, i));
         int k1 = C_188_.m_14167_(player.m_6103_());
         int l1 = C_188_.m_14167_((f + (float)k1) / 2.0F / 10.0F);
         int i2 = Math.max(10 - (l1 - 2), 3);
         int j2 = j1 - 10;
         int k2 = -1;
         if (player.b(C_500_.f_19605_)) {
            k2 = this.f_92989_ % C_188_.m_14167_(f + 5.0F);
         }

         this.f_92986_.m_91307_().m_6180_("armor");
         m_232354_(graphicsIn, player, j1, l1, i2, l);
         this.f_92986_.m_91307_().m_6182_("health");
         this.m_168688_(graphicsIn, player, l, j1, i2, k2, f, i, k, k1, flag);
         C_524_ livingentity = this.m_93093_();
         int l2 = this.m_93022_(livingentity);
         if (l2 == 0) {
            this.f_92986_.m_91307_().m_6182_("food");
            this.m_320133_(graphicsIn, player, j1, i1);
            j2 -= 10;
         }

         this.f_92986_.m_91307_().m_6182_("air");
         int i3 = player.cl();
         int j3 = Math.min(player.cm(), i3);
         if (player.a(C_139_.f_13131_) || j3 < i3) {
            int k3 = this.m_93012_(l2) - 1;
            j2 -= k3 * 10;
            int l3 = C_188_.m_14165_((double)(j3 - 2) * 10.0 / (double)i3);
            int i4 = C_188_.m_14165_((double)j3 * 10.0 / (double)i3) - l3;
            RenderSystem.enableBlend();

            for (int j4 = 0; j4 < l3 + i4; j4++) {
               if (j4 < l3) {
                  graphicsIn.m_292816_(f_291833_, i1 - j4 * 8 - 9, j2, 9, 9);
               } else {
                  graphicsIn.m_292816_(f_291609_, i1 - j4 * 8 - 9, j2, 9, 9);
               }
            }

            RenderSystem.disableBlend();
         }

         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private static void m_232354_(C_279497_ graphicsIn, C_1141_ playerIn, int p_232354_2_, int p_232354_3_, int p_232354_4_, int p_232354_5_) {
      int i = playerIn.eK();
      if (i > 0) {
         RenderSystem.enableBlend();
         int j = p_232354_2_ - (p_232354_3_ - 1) * p_232354_4_ - 10;

         for (int k = 0; k < 10; k++) {
            int l = p_232354_5_ + k * 8;
            if (k * 2 + 1 < i) {
               graphicsIn.m_292816_(f_290436_, l, j, 9, 9);
            }

            if (k * 2 + 1 == i) {
               graphicsIn.m_292816_(f_291785_, l, j, 9, 9);
            }

            if (k * 2 + 1 > i) {
               graphicsIn.m_292816_(f_291879_, l, j, 9, 9);
            }
         }

         RenderSystem.disableBlend();
      }
   }

   private void m_168688_(
      C_279497_ graphicsIn,
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
      C_3431_.C_141588_ gui$hearttype = C_3431_.C_141588_.m_168732_(playerIn);
      boolean flag = playerIn.dO().m_6106_().m_5466_();
      int i = C_188_.m_14165_((double)p_168688_7_ / 2.0);
      int j = C_188_.m_14165_((double)p_168688_10_ / 2.0);
      int k = i * 2;

      for (int l = i + j - 1; l >= 0; l--) {
         int i1 = l / 10;
         int j1 = l % 10;
         int k1 = p_168688_3_ + j1 * 8;
         int l1 = p_168688_4_ - i1 * p_168688_5_;
         if (p_168688_8_ + p_168688_10_ <= 4) {
            l1 += this.f_92985_.m_188503_(2);
         }

         if (l < i && l == p_168688_6_) {
            l1 -= 2;
         }

         this.m_280593_(graphicsIn, C_3431_.C_141588_.CONTAINER, k1, l1, flag, halfIn, false);
         int i2 = l * 2;
         boolean flag1 = l >= i;
         if (flag1) {
            int j2 = i2 - k;
            if (j2 < p_168688_10_) {
               boolean flag2 = j2 + 1 == p_168688_10_;
               this.m_280593_(graphicsIn, gui$hearttype == C_3431_.C_141588_.WITHERED ? gui$hearttype : C_3431_.C_141588_.ABSORBING, k1, l1, flag, false, flag2);
            }
         }

         if (halfIn && i2 < p_168688_9_) {
            boolean flag3 = i2 + 1 == p_168688_9_;
            this.m_280593_(graphicsIn, gui$hearttype, k1, l1, flag, true, flag3);
         }

         if (i2 < p_168688_8_) {
            boolean flag4 = i2 + 1 == p_168688_8_;
            this.m_280593_(graphicsIn, gui$hearttype, k1, l1, flag, false, flag4);
         }
      }
   }

   private void m_280593_(C_279497_ graphicsIn, C_3431_.C_141588_ typeIn, int xIn, int yIn, boolean hardcoreIn, boolean halfIn, boolean blinkingIn) {
      RenderSystem.enableBlend();
      graphicsIn.m_292816_(typeIn.m_295491_(hardcoreIn, blinkingIn, halfIn), xIn, yIn, 9, 9);
      RenderSystem.disableBlend();
   }

   private void m_320133_(C_279497_ graphicsIn, C_1141_ playerIn, int p_320133_3_, int p_320133_4_) {
      C_1219_ fooddata = playerIn.m_36324_();
      int i = fooddata.m_38702_();
      RenderSystem.enableBlend();

      for (int j = 0; j < 10; j++) {
         int k = p_320133_3_;
         C_5265_ resourcelocation;
         C_5265_ resourcelocation1;
         C_5265_ resourcelocation2;
         if (playerIn.b(C_500_.f_19612_)) {
            resourcelocation = f_291486_;
            resourcelocation1 = f_291577_;
            resourcelocation2 = f_290366_;
         } else {
            resourcelocation = f_290694_;
            resourcelocation1 = f_291002_;
            resourcelocation2 = f_291650_;
         }

         if (playerIn.m_36324_().m_38722_() <= 0.0F && this.f_92989_ % (i * 3 + 1) == 0) {
            k = p_320133_3_ + (this.f_92985_.m_188503_(3) - 1);
         }

         int l = p_320133_4_ - j * 8 - 9;
         graphicsIn.m_292816_(resourcelocation, l, k, 9, 9);
         if (j * 2 + 1 < i) {
            graphicsIn.m_292816_(resourcelocation2, l, k, 9, 9);
         }

         if (j * 2 + 1 == i) {
            graphicsIn.m_292816_(resourcelocation1, l, k, 9, 9);
         }
      }

      RenderSystem.disableBlend();
   }

   private void m_280250_(C_279497_ graphicsIn) {
      C_524_ livingentity = this.m_93093_();
      if (livingentity != null) {
         int i = this.m_93022_(livingentity);
         if (i != 0) {
            int j = (int)Math.ceil((double)livingentity.m_21223_());
            this.f_92986_.m_91307_().m_6182_("mountHealth");
            int k = graphicsIn.m_280206_() - 39;
            int l = graphicsIn.m_280182_() / 2 + 91;
            int i1 = k;
            int j1 = 0;
            RenderSystem.enableBlend();

            while (i > 0) {
               int k1 = Math.min(i, 10);
               i -= k1;

               for (int l1 = 0; l1 < k1; l1++) {
                  int i2 = l - l1 * 8 - 9;
                  graphicsIn.m_292816_(f_290939_, i2, i1, 9, 9);
                  if (l1 * 2 + 1 + j1 < j) {
                     graphicsIn.m_292816_(f_291256_, i2, i1, 9, 9);
                  }

                  if (l1 * 2 + 1 + j1 == j) {
                     graphicsIn.m_292816_(f_290334_, i2, i1, 9, 9);
                  }
               }

               i1 -= 10;
               j1 += 20;
            }

            RenderSystem.disableBlend();
         }
      }
   }

   private void m_280155_(C_279497_ graphicsIn, C_5265_ locationIn, float alphaIn) {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, alphaIn);
      graphicsIn.m_280398_(locationIn, 0, 0, -90, 0.0F, 0.0F, graphicsIn.m_280182_(), graphicsIn.m_280206_(), graphicsIn.m_280182_(), graphicsIn.m_280206_());
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void m_280278_(C_279497_ graphicsIn, float scopeScaleIn) {
      float f = (float)Math.min(graphicsIn.m_280182_(), graphicsIn.m_280206_());
      float f1 = Math.min((float)graphicsIn.m_280182_() / f, (float)graphicsIn.m_280206_() / f) * scopeScaleIn;
      int i = C_188_.m_14143_(f * f1);
      int j = C_188_.m_14143_(f * f1);
      int k = (graphicsIn.m_280182_() - i) / 2;
      int l = (graphicsIn.m_280206_() - j) / 2;
      int i1 = k + i;
      int j1 = l + j;
      RenderSystem.enableBlend();
      graphicsIn.m_280398_(f_168665_, k, l, -90, 0.0F, 0.0F, i, j, i, j);
      RenderSystem.disableBlend();
      graphicsIn.m_285795_(C_4168_.m_286086_(), 0, j1, graphicsIn.m_280182_(), graphicsIn.m_280206_(), -90, -16777216);
      graphicsIn.m_285795_(C_4168_.m_286086_(), 0, 0, graphicsIn.m_280182_(), l, -90, -16777216);
      graphicsIn.m_285795_(C_4168_.m_286086_(), 0, l, k, j1, -90, -16777216);
      graphicsIn.m_285795_(C_4168_.m_286086_(), i1, l, graphicsIn.m_280182_(), j1, -90, -16777216);
   }

   private void m_93020_(C_507_ entityIn) {
      C_4675_ blockpos = C_4675_.m_274561_(entityIn.m_20185_(), entityIn.m_20188_(), entityIn.m_20189_());
      float f = C_4138_.m_234316_(entityIn.m_9236_().m_6042_(), entityIn.m_9236_().A(blockpos));
      float f1 = C_188_.m_14036_(1.0F - f, 0.0F, 1.0F);
      this.f_92980_ = this.f_92980_ + (f1 - this.f_92980_) * 0.01F;
   }

   private void m_280154_(C_279497_ graphicsIn, @Nullable C_507_ entityIn) {
      if (!Config.isVignetteEnabled()) {
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
      } else {
         C_2110_ worldborder = this.f_92986_.f_91073_.m_6857_();
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
            f = C_188_.m_14036_(f, 0.0F, 1.0F);
            graphicsIn.m_280246_(0.0F, f, f, 1.0F);
         } else {
            float f2 = this.f_92980_;
            f2 = C_188_.m_14036_(f2, 0.0F, 1.0F);
            graphicsIn.m_280246_(f2, f2, f2, 1.0F);
         }

         graphicsIn.m_280398_(f_92981_, 0, 0, -90, 0.0F, 0.0F, graphicsIn.m_280182_(), graphicsIn.m_280206_(), graphicsIn.m_280182_(), graphicsIn.m_280206_());
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private void m_280379_(C_279497_ graphicsIn, float timeInPortal) {
      if (timeInPortal < 1.0F) {
         timeInPortal *= timeInPortal;
         timeInPortal *= timeInPortal;
         timeInPortal = timeInPortal * 0.8F + 0.2F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, timeInPortal);
      C_4486_ textureatlassprite = this.f_92986_.m_91289_().m_110907_().m_110882_(C_1710_.f_50142_.m_49966_());
      graphicsIn.m_280159_(0, 0, -90, graphicsIn.m_280182_(), graphicsIn.m_280206_(), textureatlassprite);
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void m_280585_(C_279497_ graphicsIn, int x, int y, C_336468_ partialTicks, C_1141_ player, C_1391_ stack, int seedIn) {
      if (!stack.m_41619_()) {
         float f = (float)stack.m_41612_() - partialTicks.m_338527_(false);
         if (f > 0.0F) {
            float f1 = 1.0F + f / 5.0F;
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_252880_((float)(x + 8), (float)(y + 12), 0.0F);
            graphicsIn.m_280168_().m_85841_(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            graphicsIn.m_280168_().m_252880_((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
         }

         graphicsIn.m_280638_(player, stack, x, y, seedIn);
         if (f > 0.0F) {
            graphicsIn.m_280168_().m_85849_();
         }

         graphicsIn.m_280370_(this.f_92986_.f_91062_, stack, x, y);
      }
   }

   public void m_193832_(boolean skipTickIn) {
      this.m_193836_();
      if (!skipTickIn) {
         this.m_93066_();
      }
   }

   private void m_93066_() {
      if (this.f_92986_.f_91073_ == null) {
         TextureAnimations.updateAnimations();
      }

      if (this.f_92991_ > 0) {
         this.f_92991_--;
      }

      if (this.f_93000_ > 0) {
         this.f_93000_--;
         if (this.f_93000_ <= 0) {
            this.f_93001_ = null;
            this.f_93002_ = null;
         }
      }

      this.f_92989_++;
      C_507_ entity = this.f_92986_.m_91288_();
      if (entity != null) {
         this.m_93020_(entity);
      }

      if (this.f_92986_.f_91074_ != null) {
         C_1391_ itemstack = this.f_92986_.f_91074_.fY().m_36056_();
         boolean forgeEquals = true;
         if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
            C_4996_ stackTip = (C_4996_)Reflector.call(itemstack, Reflector.IForgeItemStack_getHighlightTip, itemstack.m_41786_());
            C_4996_ highlightTip = (C_4996_)Reflector.call(this.f_92994_, Reflector.IForgeItemStack_getHighlightTip, this.f_92994_.m_41786_());
            forgeEquals = Config.equals(stackTip, highlightTip);
         }

         if (itemstack.m_41619_()) {
            this.f_92993_ = 0;
         } else if (this.f_92994_.m_41619_()
            || !itemstack.m_150930_(this.f_92994_.m_41720_())
            || !itemstack.m_41786_().equals(this.f_92994_.m_41786_())
            || !forgeEquals) {
            this.f_92993_ = (int)(40.0 * this.f_92986_.f_91066_.m_264038_().m_231551_());
         } else if (this.f_92993_ > 0) {
            this.f_92993_--;
         }

         this.f_92994_ = itemstack;
      }

      this.f_92988_.m_246602_();
   }

   private void m_193836_() {
      MinecraftServer minecraftserver = this.f_92986_.m_91092_();
      boolean flag = minecraftserver != null && minecraftserver.m_195518_();
      this.f_193829_ = this.f_193828_;
      this.f_193828_ = C_188_.m_14179_(0.2F, this.f_193828_, flag ? 1.0F : 0.0F);
   }

   public void m_93055_(C_4996_ recordName) {
      C_4996_ component = C_4996_.m_237110_("record.nowPlaying", new Object[]{recordName});
      this.m_93063_(component, true);
      this.f_92986_.m_240477_().m_168785_(component);
   }

   public void m_93063_(C_4996_ component, boolean animateColor) {
      this.m_238397_(false);
      this.f_92990_ = component;
      this.f_92991_ = 60;
      this.f_92992_ = animateColor;
   }

   public void m_238397_(boolean disabledIn) {
      this.f_238167_ = disabledIn;
   }

   public boolean m_238351_() {
      return this.f_238167_ && this.f_92991_ > 0;
   }

   public void m_168684_(int timeFadeIn, int timeDisplay, int timeFadeOut) {
      if (timeFadeIn >= 0) {
         this.f_92970_ = timeFadeIn;
      }

      if (timeDisplay >= 0) {
         this.f_92971_ = timeDisplay;
      }

      if (timeFadeOut >= 0) {
         this.f_92972_ = timeFadeOut;
      }

      if (this.f_93000_ > 0) {
         this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
      }
   }

   public void m_168711_(C_4996_ componentIn) {
      this.f_93002_ = componentIn;
   }

   public void m_168714_(C_4996_ componentIn) {
      this.f_93001_ = componentIn;
      this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
   }

   public void m_168713_() {
      this.f_93001_ = null;
      this.f_93002_ = null;
      this.f_93000_ = 0;
   }

   public C_3454_ m_93076_() {
      return this.f_92988_;
   }

   public int m_93079_() {
      return this.f_92989_;
   }

   public C_3429_ m_93082_() {
      return this.f_92986_.f_91062_;
   }

   public C_3496_ m_93085_() {
      return this.f_92997_;
   }

   public C_3479_ m_93088_() {
      return this.f_92998_;
   }

   public void m_93089_() {
      this.f_92998_.m_94529_();
      this.f_92999_.m_93703_();
      this.f_92986_.m_91300_().m_94919_();
      this.f_291320_.m_294940_();
      this.f_92988_.m_93795_(true);
   }

   public C_3450_ m_93090_() {
      return this.f_92999_;
   }

   public C_3462_ m_295051_() {
      return this.f_291320_;
   }

   public void m_93091_() {
      this.f_291320_.m_94040_();
   }

   public void m_280266_(C_279497_ graphicsIn, C_336468_ partialTicks) {
      if (this.f_92986_.f_91066_.m_231834_().m_231551_() && (this.f_193828_ > 0.0F || this.f_193829_ > 0.0F)) {
         int i = C_188_.m_14143_(255.0F * C_188_.m_14036_(C_188_.m_14179_(partialTicks.m_338557_(), this.f_193829_, this.f_193828_), 0.0F, 1.0F));
         if (i > 8) {
            C_3429_ font = this.m_93082_();
            int j = font.m_92852_(f_193830_);
            int k = C_175_.m_320289_(i, -1);
            int l = graphicsIn.m_280182_() - j - 2;
            int i1 = graphicsIn.m_280206_() - 35;
            graphicsIn.m_339210_(font, f_193830_, l, i1, j, k);
         }
      }
   }

   static enum C_141588_ {
      CONTAINER(
         C_5265_.m_340282_("hud/heart/container"),
         C_5265_.m_340282_("hud/heart/container_blinking"),
         C_5265_.m_340282_("hud/heart/container"),
         C_5265_.m_340282_("hud/heart/container_blinking"),
         C_5265_.m_340282_("hud/heart/container_hardcore"),
         C_5265_.m_340282_("hud/heart/container_hardcore_blinking"),
         C_5265_.m_340282_("hud/heart/container_hardcore"),
         C_5265_.m_340282_("hud/heart/container_hardcore_blinking")
      ),
      NORMAL(
         C_5265_.m_340282_("hud/heart/full"),
         C_5265_.m_340282_("hud/heart/full_blinking"),
         C_5265_.m_340282_("hud/heart/half"),
         C_5265_.m_340282_("hud/heart/half_blinking"),
         C_5265_.m_340282_("hud/heart/hardcore_full"),
         C_5265_.m_340282_("hud/heart/hardcore_full_blinking"),
         C_5265_.m_340282_("hud/heart/hardcore_half"),
         C_5265_.m_340282_("hud/heart/hardcore_half_blinking")
      ),
      POISIONED(
         C_5265_.m_340282_("hud/heart/poisoned_full"),
         C_5265_.m_340282_("hud/heart/poisoned_full_blinking"),
         C_5265_.m_340282_("hud/heart/poisoned_half"),
         C_5265_.m_340282_("hud/heart/poisoned_half_blinking"),
         C_5265_.m_340282_("hud/heart/poisoned_hardcore_full"),
         C_5265_.m_340282_("hud/heart/poisoned_hardcore_full_blinking"),
         C_5265_.m_340282_("hud/heart/poisoned_hardcore_half"),
         C_5265_.m_340282_("hud/heart/poisoned_hardcore_half_blinking")
      ),
      WITHERED(
         C_5265_.m_340282_("hud/heart/withered_full"),
         C_5265_.m_340282_("hud/heart/withered_full_blinking"),
         C_5265_.m_340282_("hud/heart/withered_half"),
         C_5265_.m_340282_("hud/heart/withered_half_blinking"),
         C_5265_.m_340282_("hud/heart/withered_hardcore_full"),
         C_5265_.m_340282_("hud/heart/withered_hardcore_full_blinking"),
         C_5265_.m_340282_("hud/heart/withered_hardcore_half"),
         C_5265_.m_340282_("hud/heart/withered_hardcore_half_blinking")
      ),
      ABSORBING(
         C_5265_.m_340282_("hud/heart/absorbing_full"),
         C_5265_.m_340282_("hud/heart/absorbing_full_blinking"),
         C_5265_.m_340282_("hud/heart/absorbing_half"),
         C_5265_.m_340282_("hud/heart/absorbing_half_blinking"),
         C_5265_.m_340282_("hud/heart/absorbing_hardcore_full"),
         C_5265_.m_340282_("hud/heart/absorbing_hardcore_full_blinking"),
         C_5265_.m_340282_("hud/heart/absorbing_hardcore_half"),
         C_5265_.m_340282_("hud/heart/absorbing_hardcore_half_blinking")
      ),
      FROZEN(
         C_5265_.m_340282_("hud/heart/frozen_full"),
         C_5265_.m_340282_("hud/heart/frozen_full_blinking"),
         C_5265_.m_340282_("hud/heart/frozen_half"),
         C_5265_.m_340282_("hud/heart/frozen_half_blinking"),
         C_5265_.m_340282_("hud/heart/frozen_hardcore_full"),
         C_5265_.m_340282_("hud/heart/frozen_hardcore_full_blinking"),
         C_5265_.m_340282_("hud/heart/frozen_hardcore_half"),
         C_5265_.m_340282_("hud/heart/frozen_hardcore_half_blinking")
      );

      private final C_5265_ f_291247_;
      private final C_5265_ f_290790_;
      private final C_5265_ f_291803_;
      private final C_5265_ f_291226_;
      private final C_5265_ f_291645_;
      private final C_5265_ f_291413_;
      private final C_5265_ f_291153_;
      private final C_5265_ f_291264_;

      private C_141588_(
         final C_5265_ fullIn,
         final C_5265_ fullBlinkingIn,
         final C_5265_ halfIn,
         final C_5265_ halfBlinkingIn,
         final C_5265_ hardcoreFullIn,
         final C_5265_ hardcoreFullBlinkingIn,
         final C_5265_ hardcoreHalfIn,
         final C_5265_ hardcoreHalfBlinkingIn
      ) {
         this.f_291247_ = fullIn;
         this.f_290790_ = fullBlinkingIn;
         this.f_291803_ = halfIn;
         this.f_291226_ = halfBlinkingIn;
         this.f_291645_ = hardcoreFullIn;
         this.f_291413_ = hardcoreFullBlinkingIn;
         this.f_291153_ = hardcoreHalfIn;
         this.f_291264_ = hardcoreHalfBlinkingIn;
      }

      public C_5265_ m_295491_(boolean hardcoreIn, boolean halfIn, boolean blinkingIn) {
         if (!hardcoreIn) {
            if (halfIn) {
               return blinkingIn ? this.f_291226_ : this.f_291803_;
            } else {
               return blinkingIn ? this.f_290790_ : this.f_291247_;
            }
         } else if (halfIn) {
            return blinkingIn ? this.f_291264_ : this.f_291153_;
         } else {
            return blinkingIn ? this.f_291413_ : this.f_291645_;
         }
      }

      static C_3431_.C_141588_ m_168732_(C_1141_ playerIn) {
         C_3431_.C_141588_ gui$hearttype;
         if (playerIn.b(C_500_.f_19614_)) {
            gui$hearttype = POISIONED;
         } else if (playerIn.b(C_500_.f_19615_)) {
            gui$hearttype = WITHERED;
         } else if (playerIn.cp()) {
            gui$hearttype = FROZEN;
         } else {
            gui$hearttype = NORMAL;
         }

         return gui$hearttype;
      }
   }

   static record C_301998_(C_4996_ f_302553_, C_4996_ f_303810_, int f_302793_) {
   }
}
