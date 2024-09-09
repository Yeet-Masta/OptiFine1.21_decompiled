package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringUtil;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomItems;
import net.optifine.TextureAnimations;
import net.optifine.reflect.Reflector;
import org.joml.Matrix4fStack;

public class Gui {
   private static final net.minecraft.resources.ResourceLocation f_291829_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/crosshair");
   private static final net.minecraft.resources.ResourceLocation f_291363_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "hud/crosshair_attack_indicator_full"
   );
   private static final net.minecraft.resources.ResourceLocation f_291360_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "hud/crosshair_attack_indicator_background"
   );
   private static final net.minecraft.resources.ResourceLocation f_290711_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "hud/crosshair_attack_indicator_progress"
   );
   private static final net.minecraft.resources.ResourceLocation f_291473_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/effect_background_ambient");
   private static final net.minecraft.resources.ResourceLocation f_290513_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/effect_background");
   private static final net.minecraft.resources.ResourceLocation f_291149_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/hotbar");
   private static final net.minecraft.resources.ResourceLocation f_290368_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/hotbar_selection");
   private static final net.minecraft.resources.ResourceLocation f_291322_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/hotbar_offhand_left");
   private static final net.minecraft.resources.ResourceLocation f_291864_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/hotbar_offhand_right");
   private static final net.minecraft.resources.ResourceLocation f_290833_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "hud/hotbar_attack_indicator_background"
   );
   private static final net.minecraft.resources.ResourceLocation f_291301_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "hud/hotbar_attack_indicator_progress"
   );
   private static final net.minecraft.resources.ResourceLocation f_291483_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/jump_bar_background");
   private static final net.minecraft.resources.ResourceLocation f_291104_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/jump_bar_cooldown");
   private static final net.minecraft.resources.ResourceLocation f_291820_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/jump_bar_progress");
   private static final net.minecraft.resources.ResourceLocation f_291741_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/experience_bar_background");
   private static final net.minecraft.resources.ResourceLocation f_291491_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/experience_bar_progress");
   private static final net.minecraft.resources.ResourceLocation f_291879_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/armor_empty");
   private static final net.minecraft.resources.ResourceLocation f_291785_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/armor_half");
   private static final net.minecraft.resources.ResourceLocation f_290436_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/armor_full");
   private static final net.minecraft.resources.ResourceLocation f_291486_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/food_empty_hunger");
   private static final net.minecraft.resources.ResourceLocation f_291577_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/food_half_hunger");
   private static final net.minecraft.resources.ResourceLocation f_290366_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/food_full_hunger");
   private static final net.minecraft.resources.ResourceLocation f_290694_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/food_empty");
   private static final net.minecraft.resources.ResourceLocation f_291002_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/food_half");
   private static final net.minecraft.resources.ResourceLocation f_291650_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/food_full");
   private static final net.minecraft.resources.ResourceLocation f_291833_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/air");
   private static final net.minecraft.resources.ResourceLocation f_291609_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/air_bursting");
   private static final net.minecraft.resources.ResourceLocation f_290939_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/vehicle_container");
   private static final net.minecraft.resources.ResourceLocation f_291256_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/vehicle_full");
   private static final net.minecraft.resources.ResourceLocation f_290334_ = net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/vehicle_half");
   private static final net.minecraft.resources.ResourceLocation f_92981_ = net.minecraft.resources.ResourceLocation.m_340282_("textures/misc/vignette.png");
   private static final net.minecraft.resources.ResourceLocation f_92983_ = net.minecraft.resources.ResourceLocation.m_340282_("textures/misc/pumpkinblur.png");
   private static final net.minecraft.resources.ResourceLocation f_168665_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "textures/misc/spyglass_scope.png"
   );
   private static final net.minecraft.resources.ResourceLocation f_168666_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "textures/misc/powder_snow_outline.png"
   );
   private static final Comparator<PlayerScoreEntry> f_302813_ = Comparator.comparing(PlayerScoreEntry::f_303807_)
      .reversed()
      .thenComparing(PlayerScoreEntry::f_302847_, String.CASE_INSENSITIVE_ORDER);
   private static final Component f_92984_ = Component.m_237115_("demo.demoExpired");
   private static final Component f_193830_ = Component.m_237115_("menu.savingLevel");
   private static final float f_168668_ = 5.0F;
   private static final int f_168669_ = 10;
   private static final int f_168670_ = 10;
   private static final String f_168671_ = ": ";
   private static final float f_168672_ = 0.2F;
   private static final int f_168673_ = 9;
   private static final int f_168674_ = 8;
   private static final float f_193831_ = 0.2F;
   private final RandomSource f_92985_ = RandomSource.m_216327_();
   private final Minecraft f_92986_;
   private final net.minecraft.client.gui.components.ChatComponent f_92988_;
   private int f_92989_;
   @Nullable
   private Component f_92990_;
   private int f_92991_;
   private boolean f_92992_;
   private boolean f_238167_;
   public float f_92980_ = 1.0F;
   private int f_92993_;
   private ItemStack f_92994_ = ItemStack.f_41583_;
   protected net.minecraft.client.gui.components.DebugScreenOverlay f_291320_;
   private final SubtitleOverlay f_92996_;
   private final SpectatorGui f_92997_;
   private final PlayerTabOverlay f_92998_;
   private final net.minecraft.client.gui.components.BossHealthOverlay f_92999_;
   private int f_93000_;
   @Nullable
   private Component f_93001_;
   @Nullable
   private Component f_93002_;
   private int f_92970_;
   private int f_92971_;
   private int f_92972_;
   private int f_92973_;
   private int f_92974_;
   private long f_92975_;
   private long f_92976_;
   private float f_193828_;
   private float f_193829_;
   private final LayeredDraw f_316662_ = new LayeredDraw();
   private float f_168664_;

   public Gui(Minecraft mcIn) {
      this.f_92986_ = mcIn;
      this.f_291320_ = new net.minecraft.client.gui.components.DebugScreenOverlay(mcIn);
      this.f_92997_ = new SpectatorGui(mcIn);
      this.f_92988_ = new net.minecraft.client.gui.components.ChatComponent(mcIn);
      this.f_92998_ = new PlayerTabOverlay(mcIn, this);
      this.f_92999_ = new net.minecraft.client.gui.components.BossHealthOverlay(mcIn);
      this.f_92996_ = new SubtitleOverlay(mcIn);
      this.m_93006_();
      LayeredDraw layereddraw = new LayeredDraw()
         .m_322513_(this::m_318727_)
         .m_322513_(this::m_280130_)
         .m_322513_(this::m_319215_)
         .m_322513_(this::m_324707_)
         .m_322513_(this::m_280523_)
         .m_322513_((graphicsIn, partialTicks) -> this.f_92999_.m_280652_(graphicsIn));
      LayeredDraw layereddraw1 = new LayeredDraw()
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

   public void m_280421_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      RenderSystem.enableDepthTest();
      this.f_316662_.m_322951_(graphicsIn, partialTicks);
      RenderSystem.disableDepthTest();
   }

   private void m_318727_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      if (Config.isVignetteEnabled()) {
         this.m_280154_(graphicsIn, this.f_92986_.m_91288_());
      }

      float f = partialTicks.m_339005_();
      this.f_168664_ = net.minecraft.util.Mth.m_14179_(0.5F * f, this.f_168664_, 1.125F);
      if (this.f_92986_.f_91066_.m_92176_().m_90612_()) {
         if (this.f_92986_.f_91074_.m_150108_()) {
            this.m_280278_(graphicsIn, this.f_168664_);
         } else {
            this.f_168664_ = 0.5F;
            ItemStack itemstack = this.f_92986_.f_91074_.m_150109_().m_36052_(3);
            if (itemstack.m_150930_(Blocks.f_50143_.m_5456_())) {
               this.m_280155_(graphicsIn, f_92983_, 1.0F);
            }
         }
      }

      if (this.f_92986_.f_91074_.m_146888_() > 0) {
         this.m_280155_(graphicsIn, f_168666_, this.f_92986_.f_91074_.m_146889_());
      }

      float f1 = net.minecraft.util.Mth.m_14179_(partialTicks.m_338527_(false), this.f_92986_.f_91074_.f_108590_, this.f_92986_.f_91074_.f_108589_);
      if (f1 > 0.0F && !this.f_92986_.f_91074_.m_21023_(MobEffects.f_19604_)) {
         this.m_280379_(graphicsIn, f1);
      }
   }

   private void m_322680_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      if (this.f_92986_.f_91074_.m_36318_() > 0) {
         this.f_92986_.m_91307_().m_6180_("sleep");
         float f = (float)this.f_92986_.f_91074_.m_36318_();
         float f1 = f / 100.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F - (f - 100.0F) / 10.0F;
         }

         int i = (int)(220.0F * f1) << 24 | 1052704;
         graphicsIn.m_285944_(net.minecraft.client.renderer.RenderType.m_286086_(), 0, 0, graphicsIn.m_280182_(), graphicsIn.m_280206_(), i);
         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private void m_324281_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      net.minecraft.client.gui.Font font = this.m_93082_();
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
               j = net.minecraft.util.Mth.m_339996_(f / 50.0F, 0.7F, 0.6F, i);
            } else {
               j = ARGB32.m_320289_(i, -1);
            }

            int k = font.m_92852_(this.f_92990_);
            graphicsIn.m_339210_(font, this.f_92990_, -k / 2, -4, k, j);
            graphicsIn.m_280168_().m_85849_();
         }

         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private void m_320668_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      if (this.f_93001_ != null && this.f_93000_ > 0) {
         net.minecraft.client.gui.Font font = this.m_93082_();
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

         i = net.minecraft.util.Mth.m_14045_(i, 0, 255);
         if (i > 8) {
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_252880_((float)(graphicsIn.m_280182_() / 2), (float)(graphicsIn.m_280206_() / 2), 0.0F);
            graphicsIn.m_280168_().m_85836_();
            graphicsIn.m_280168_().m_85841_(4.0F, 4.0F, 4.0F);
            int l = font.m_92852_(this.f_93001_);
            int j = ARGB32.m_320289_(i, -1);
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

   private void m_322096_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      if (!this.f_92988_.m_93818_()) {
         com.mojang.blaze3d.platform.Window window = this.f_92986_.m_91268_();
         int i = net.minecraft.util.Mth.m_14107_(this.f_92986_.f_91067_.m_91589_() * (double)window.m_85445_() / (double)window.m_85443_());
         int j = net.minecraft.util.Mth.m_14107_(this.f_92986_.f_91067_.m_91594_() * (double)window.m_85446_() / (double)window.m_85444_());
         this.f_92988_.m_280165_(graphicsIn, this.f_92989_, i, j, false);
      }
   }

   private void m_323286_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      Scoreboard scoreboard = this.f_92986_.f_91073_.m_6188_();
      Objective objective = null;
      PlayerTeam playerteam = scoreboard.m_83500_(this.f_92986_.f_91074_.m_6302_());
      if (playerteam != null) {
         DisplaySlot displayslot = DisplaySlot.m_293761_(playerteam.m_7414_());
         if (displayslot != null) {
            objective = scoreboard.m_83416_(displayslot);
         }
      }

      Objective objective1 = objective != null ? objective : scoreboard.m_83416_(DisplaySlot.SIDEBAR);
      if (objective1 != null) {
         this.m_280030_(graphicsIn, objective1);
      }
   }

   private void m_320053_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      Scoreboard scoreboard = this.f_92986_.f_91073_.m_6188_();
      Objective objective = scoreboard.m_83416_(DisplaySlot.LIST);
      if (this.f_92986_.f_91066_.f_92099_.m_90857_()
         && (!this.f_92986_.m_91090_() || this.f_92986_.f_91074_.f_108617_.m_246170_().size() > 1 || objective != null)) {
         this.f_92998_.m_94556_(true);
         this.f_92998_.m_280406_(graphicsIn, graphicsIn.m_280182_(), scoreboard, objective);
      } else {
         this.f_92998_.m_94556_(false);
      }
   }

   private void m_280130_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      net.minecraft.client.Options options = this.f_92986_.f_91066_;
      if (options.m_92176_().m_90612_() && (this.f_92986_.f_91072_.m_105295_() != GameType.SPECTATOR || this.m_93024_(this.f_92986_.f_91077_))) {
         RenderSystem.enableBlend();
         if (this.f_291320_.m_294516_() && !this.f_92986_.f_91074_.m_36330_() && !options.m_231824_().m_231551_()) {
            net.minecraft.client.Camera camera = this.f_92986_.f_91063_.m_109153_();
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
            if (this.f_92986_.f_91066_.m_232120_().m_231551_() == AttackIndicatorStatus.CROSSHAIR) {
               float f = this.f_92986_.f_91074_.m_36403_(0.0F);
               boolean flag = false;
               if (this.f_92986_.f_91076_ != null && this.f_92986_.f_91076_ instanceof LivingEntity && f >= 1.0F) {
                  flag = this.f_92986_.f_91074_.m_36333_() > 5.0F;
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

   private boolean m_93024_(@Nullable HitResult rayTraceIn) {
      if (rayTraceIn == null) {
         return false;
      } else if (rayTraceIn.m_6662_() == Type.ENTITY) {
         return ((EntityHitResult)rayTraceIn).m_82443_() instanceof MenuProvider;
      } else if (rayTraceIn.m_6662_() == Type.BLOCK) {
         BlockPos blockpos = ((BlockHitResult)rayTraceIn).m_82425_();
         Level level = this.f_92986_.f_91073_;
         return level.m_8055_(blockpos).m_60750_(level, blockpos) != null;
      } else {
         return false;
      }
   }

   private void m_280523_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      Collection<MobEffectInstance> collection = this.f_92986_.f_91074_.m_21220_();
      if (!collection.isEmpty()) {
         if (this.f_92986_.f_91080_ instanceof EffectRenderingInventoryScreen effectrenderinginventoryscreen && effectrenderinginventoryscreen.m_194018_()) {
            return;
         }

         RenderSystem.enableBlend();
         int j1 = 0;
         int k1 = 0;
         MobEffectTextureManager mobeffecttexturemanager = this.f_92986_.m_91306_();
         List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());

         for (MobEffectInstance mobeffectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
            Holder<MobEffect> holder = mobeffectinstance.m_19544_();
            IClientMobEffectExtensions cmee = IClientMobEffectExtensions.of(mobeffectinstance);
            if ((cmee == null || cmee.isVisibleInGui(mobeffectinstance)) && mobeffectinstance.m_19575_()) {
               int i = graphicsIn.m_280182_();
               int j = 1;
               if (this.f_92986_.m_91402_()) {
                  j += 15;
               }

               if (((MobEffect)holder.m_203334_()).m_19486_()) {
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
                     f = net.minecraft.util.Mth.m_14036_((float)k / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + net.minecraft.util.Mth.m_14089_((float)k * (float) Math.PI / 5.0F)
                           * net.minecraft.util.Mth.m_14036_((float)l / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               if (cmee == null || !cmee.renderGuiIcon(mobeffectinstance, this, graphicsIn, i, j, 0.0F, f)) {
                  net.minecraft.client.renderer.texture.TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.m_118732_(holder);
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

   private void m_319215_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      if (this.f_92986_.f_91072_.m_105295_() == GameType.SPECTATOR) {
         this.f_92997_.m_280623_(graphicsIn);
      } else {
         this.m_324469_(graphicsIn, partialTicks);
      }

      int i = graphicsIn.m_280182_() / 2 - 91;
      PlayerRideableJumping playerrideablejumping = this.f_92986_.f_91074_.m_245714_();
      if (playerrideablejumping != null) {
         this.m_280069_(playerrideablejumping, graphicsIn, i);
      } else if (this.m_322177_()) {
         this.m_280276_(graphicsIn, i);
      }

      if (this.f_92986_.f_91072_.m_105205_()) {
         this.m_280173_(graphicsIn);
      }

      this.m_280250_(graphicsIn);
      if (this.f_92986_.f_91072_.m_105295_() != GameType.SPECTATOR) {
         this.m_280295_(graphicsIn);
      } else if (this.f_92986_.f_91074_.m_5833_()) {
         this.f_92997_.m_280365_(graphicsIn);
      }
   }

   private void m_324469_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      Player player = this.m_93092_();
      if (player != null) {
         ItemStack itemstack = player.m_21206_();
         HumanoidArm humanoidarm = player.m_5737_().m_20828_();
         int i = graphicsIn.m_280182_() / 2;
         int j = 182;
         int k = 91;
         RenderSystem.enableBlend();
         graphicsIn.m_280168_().m_85836_();
         graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, -90.0F);
         graphicsIn.m_292816_(f_291149_, i - 91, graphicsIn.m_280206_() - 22, 182, 22);
         graphicsIn.m_292816_(f_290368_, i - 91 - 1 + player.m_150109_().f_35977_ * 20, graphicsIn.m_280206_() - 22 - 1, 24, 23);
         if (!itemstack.m_41619_()) {
            if (humanoidarm == HumanoidArm.LEFT) {
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
            this.m_280585_(graphicsIn, j1, k1, partialTicks, player, (ItemStack)player.m_150109_().f_35974_.get(i1), l++);
         }

         if (!itemstack.m_41619_()) {
            CustomItems.setRenderOffHand(true);
            int i2 = graphicsIn.m_280206_() - 16 - 3;
            if (humanoidarm == HumanoidArm.LEFT) {
               this.m_280585_(graphicsIn, i - 91 - 26, i2, partialTicks, player, itemstack, l++);
            } else {
               this.m_280585_(graphicsIn, i + 91 + 10, i2, partialTicks, player, itemstack, l++);
            }

            CustomItems.setRenderOffHand(false);
         }

         if (this.f_92986_.f_91066_.m_232120_().m_231551_() == AttackIndicatorStatus.HOTBAR) {
            RenderSystem.enableBlend();
            float f = this.f_92986_.f_91074_.m_36403_(0.0F);
            if (f < 1.0F) {
               int j2 = graphicsIn.m_280206_() - 20;
               int k2 = i + 91 + 6;
               if (humanoidarm == HumanoidArm.RIGHT) {
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

   private void m_280069_(PlayerRideableJumping jumpingIn, net.minecraft.client.gui.GuiGraphics graphicsIn, int x) {
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

   private void m_280276_(net.minecraft.client.gui.GuiGraphics graphicsIn, int x) {
      this.f_92986_.m_91307_().m_6180_("expBar");
      int i = this.f_92986_.f_91074_.m_36323_();
      if (i > 0) {
         int j = 182;
         int k = (int)(this.f_92986_.f_91074_.f_36080_ * 183.0F);
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

   private void m_324707_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      int i = this.f_92986_.f_91074_.f_36078_;
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

   private void m_280295_(net.minecraft.client.gui.GuiGraphics graphicsIn) {
      this.renderSelectedItemName(graphicsIn, 0);
   }

   public void renderSelectedItemName(net.minecraft.client.gui.GuiGraphics graphicsIn, int yShift) {
      this.f_92986_.m_91307_().m_6180_("selectedItemName");
      if (this.f_92993_ > 0 && !this.f_92994_.m_41619_()) {
         MutableComponent mutablecomponent = Component.m_237119_().m_7220_(this.f_92994_.m_41786_()).m_130940_(this.f_92994_.m_41791_().m_321696_());
         if (Reflector.ForgeRarity_getStyleModifier.exists()) {
            Rarity rarityForge = this.f_92994_.m_41791_();
            UnaryOperator<Style> styleModifier = (UnaryOperator<Style>)Reflector.call(rarityForge, Reflector.ForgeRarity_getStyleModifier);
            mutablecomponent = Component.m_237119_().m_7220_(this.f_92994_.m_41786_()).m_130938_(styleModifier);
         }

         if (this.f_92994_.m_319951_(DataComponents.f_316016_)) {
            mutablecomponent.m_130940_(ChatFormatting.ITALIC);
         }

         Component highlightTip = mutablecomponent;
         if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
            highlightTip = (Component)Reflector.call(this.f_92994_, Reflector.IForgeItemStack_getHighlightTip, mutablecomponent);
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
            net.minecraft.client.gui.Font font = null;
            IClientItemExtensions cmee = IClientItemExtensions.of(this.f_92994_);
            if (cmee != null) {
               font = cmee.getFont(this.f_92994_, IClientItemExtensions.FontContext.SELECTED_ITEM_NAME);
            }

            if (font != null) {
               i = (graphicsIn.m_280182_() - font.m_92852_(highlightTip)) / 2;
               graphicsIn.m_280648_(this.m_93082_(), highlightTip.m_7532_(), j, k, 16777215 + (l << 24));
            } else {
               graphicsIn.m_339210_(this.m_93082_(), mutablecomponent, j, k, i, ARGB32.m_320289_(l, -1));
            }
         }
      }

      this.f_92986_.m_91307_().m_7238_();
   }

   private void m_280339_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      if (this.f_92986_.m_91402_()) {
         this.f_92986_.m_91307_().m_6180_("demo");
         Component component;
         if (this.f_92986_.f_91073_.m_46467_() >= 120500L) {
            component = f_92984_;
         } else {
            component = Component.m_237110_(
               "demo.remainingTime",
               new Object[]{StringUtil.m_14404_((int)(120500L - this.f_92986_.f_91073_.m_46467_()), this.f_92986_.f_91073_.m_304826_().m_306179_())}
            );
         }

         int i = this.m_93082_().m_92852_(component);
         int j = graphicsIn.m_280182_() - i - 10;
         int k = 5;
         graphicsIn.m_339210_(this.m_93082_(), component, j, 5, i, -1);
         this.f_92986_.m_91307_().m_7238_();
      }
   }

   private void m_280030_(net.minecraft.client.gui.GuiGraphics graphicsIn, Objective objective) {
      Scoreboard scoreboard = objective.m_83313_();
      NumberFormat numberformat = objective.m_305063_(StyledFormat.f_303088_);
      net.minecraft.client.gui.Gui.DisplayEntry[] agui$displayentry = (net.minecraft.client.gui.Gui.DisplayEntry[])scoreboard.m_306706_(objective)
         .stream()
         .filter(entryIn -> !entryIn.m_307477_())
         .sorted(f_302813_)
         .limit(15L)
         .map(entry2In -> {
            PlayerTeam playerteam = scoreboard.m_83500_(entry2In.f_302847_());
            Component component1 = entry2In.m_305530_();
            Component component2 = PlayerTeam.m_83348_(playerteam, component1);
            Component component3 = entry2In.m_304640_(numberformat);
            int i1 = this.m_93082_().m_92852_(component3);
            return new net.minecraft.client.gui.Gui.DisplayEntry(component2, component3, i1);
         })
         .toArray(net.minecraft.client.gui.Gui.DisplayEntry[]::new);
      Component component = objective.m_83322_();
      int i = this.m_93082_().m_92852_(component);
      int j = i;
      int k = this.m_93082_().m_92895_(": ");

      for (net.minecraft.client.gui.Gui.DisplayEntry gui$displayentry : agui$displayentry) {
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
            net.minecraft.client.gui.Gui.DisplayEntry gui$displayentry1 = agui$displayentry[j3];
            int k3 = k1 - (i1 - j3) * 9;
            graphicsIn.m_280614_(this.m_93082_(), gui$displayentry1.f_302553_, i2, k3, -1, false);
            graphicsIn.m_280614_(this.m_93082_(), gui$displayentry1.f_303810_, j2 - gui$displayentry1.f_302793_, k3, -1, false);
         }
      });
   }

   @Nullable
   private Player m_93092_() {
      return this.f_92986_.m_91288_() instanceof Player player ? player : null;
   }

   @Nullable
   private LivingEntity m_93093_() {
      Player player = this.m_93092_();
      if (player != null) {
         Entity entity = player.m_20202_();
         if (entity == null) {
            return null;
         }

         if (entity instanceof LivingEntity) {
            return (LivingEntity)entity;
         }
      }

      return null;
   }

   private int m_93022_(@Nullable LivingEntity livingEntityIn) {
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

   private int m_93012_(int healthIn) {
      return (int)Math.ceil((double)healthIn / 10.0);
   }

   private void m_280173_(net.minecraft.client.gui.GuiGraphics graphicsIn) {
      Player player = this.m_93092_();
      if (player != null) {
         int i = net.minecraft.util.Mth.m_14167_(player.m_21223_());
         boolean flag = this.f_92976_ > (long)this.f_92989_ && (this.f_92976_ - (long)this.f_92989_) / 3L % 2L == 1L;
         long j = net.minecraft.Util.m_137550_();
         if (i < this.f_92973_ && player.f_19802_ > 0) {
            this.f_92975_ = j;
            this.f_92976_ = (long)(this.f_92989_ + 20);
         } else if (i > this.f_92973_ && player.f_19802_ > 0) {
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
         float f = Math.max((float)player.m_246858_(Attributes.f_22276_), (float)Math.max(k, i));
         int k1 = net.minecraft.util.Mth.m_14167_(player.m_6103_());
         int l1 = net.minecraft.util.Mth.m_14167_((f + (float)k1) / 2.0F / 10.0F);
         int i2 = Math.max(10 - (l1 - 2), 3);
         int j2 = j1 - 10;
         int k2 = -1;
         if (player.m_21023_(MobEffects.f_19605_)) {
            k2 = this.f_92989_ % net.minecraft.util.Mth.m_14167_(f + 5.0F);
         }

         this.f_92986_.m_91307_().m_6180_("armor");
         m_232354_(graphicsIn, player, j1, l1, i2, l);
         this.f_92986_.m_91307_().m_6182_("health");
         this.m_168688_(graphicsIn, player, l, j1, i2, k2, f, i, k, k1, flag);
         LivingEntity livingentity = this.m_93093_();
         int l2 = this.m_93022_(livingentity);
         if (l2 == 0) {
            this.f_92986_.m_91307_().m_6182_("food");
            this.m_320133_(graphicsIn, player, j1, i1);
            j2 -= 10;
         }

         this.f_92986_.m_91307_().m_6182_("air");
         int i3 = player.m_6062_();
         int j3 = Math.min(player.m_20146_(), i3);
         if (player.m_204029_(FluidTags.f_13131_) || j3 < i3) {
            int k3 = this.m_93012_(l2) - 1;
            j2 -= k3 * 10;
            int l3 = net.minecraft.util.Mth.m_14165_((double)(j3 - 2) * 10.0 / (double)i3);
            int i4 = net.minecraft.util.Mth.m_14165_((double)j3 * 10.0 / (double)i3) - l3;
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

   private static void m_232354_(
      net.minecraft.client.gui.GuiGraphics graphicsIn, Player playerIn, int p_232354_2_, int p_232354_3_, int p_232354_4_, int p_232354_5_
   ) {
      int i = playerIn.m_21230_();
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
      net.minecraft.client.gui.GuiGraphics graphicsIn,
      Player playerIn,
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
      net.minecraft.client.gui.Gui.HeartType gui$hearttype = net.minecraft.client.gui.Gui.HeartType.m_168732_(playerIn);
      boolean flag = playerIn.m_9236_().m_6106_().m_5466_();
      int i = net.minecraft.util.Mth.m_14165_((double)p_168688_7_ / 2.0);
      int j = net.minecraft.util.Mth.m_14165_((double)p_168688_10_ / 2.0);
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

         this.m_280593_(graphicsIn, net.minecraft.client.gui.Gui.HeartType.CONTAINER, k1, l1, flag, halfIn, false);
         int i2 = l * 2;
         boolean flag1 = l >= i;
         if (flag1) {
            int j2 = i2 - k;
            if (j2 < p_168688_10_) {
               boolean flag2 = j2 + 1 == p_168688_10_;
               this.m_280593_(
                  graphicsIn,
                  gui$hearttype == net.minecraft.client.gui.Gui.HeartType.WITHERED ? gui$hearttype : net.minecraft.client.gui.Gui.HeartType.ABSORBING,
                  k1,
                  l1,
                  flag,
                  false,
                  flag2
               );
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

   private void m_280593_(
      net.minecraft.client.gui.GuiGraphics graphicsIn,
      net.minecraft.client.gui.Gui.HeartType typeIn,
      int xIn,
      int yIn,
      boolean hardcoreIn,
      boolean halfIn,
      boolean blinkingIn
   ) {
      RenderSystem.enableBlend();
      graphicsIn.m_292816_(typeIn.m_295491_(hardcoreIn, blinkingIn, halfIn), xIn, yIn, 9, 9);
      RenderSystem.disableBlend();
   }

   private void m_320133_(net.minecraft.client.gui.GuiGraphics graphicsIn, Player playerIn, int p_320133_3_, int p_320133_4_) {
      FoodData fooddata = playerIn.m_36324_();
      int i = fooddata.m_38702_();
      RenderSystem.enableBlend();

      for (int j = 0; j < 10; j++) {
         int k = p_320133_3_;
         net.minecraft.resources.ResourceLocation resourcelocation;
         net.minecraft.resources.ResourceLocation resourcelocation1;
         net.minecraft.resources.ResourceLocation resourcelocation2;
         if (playerIn.m_21023_(MobEffects.f_19612_)) {
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

   private void m_280250_(net.minecraft.client.gui.GuiGraphics graphicsIn) {
      LivingEntity livingentity = this.m_93093_();
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

   private void m_280155_(net.minecraft.client.gui.GuiGraphics graphicsIn, net.minecraft.resources.ResourceLocation locationIn, float alphaIn) {
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

   private void m_280278_(net.minecraft.client.gui.GuiGraphics graphicsIn, float scopeScaleIn) {
      float f = (float)Math.min(graphicsIn.m_280182_(), graphicsIn.m_280206_());
      float f1 = Math.min((float)graphicsIn.m_280182_() / f, (float)graphicsIn.m_280206_() / f) * scopeScaleIn;
      int i = net.minecraft.util.Mth.m_14143_(f * f1);
      int j = net.minecraft.util.Mth.m_14143_(f * f1);
      int k = (graphicsIn.m_280182_() - i) / 2;
      int l = (graphicsIn.m_280206_() - j) / 2;
      int i1 = k + i;
      int j1 = l + j;
      RenderSystem.enableBlend();
      graphicsIn.m_280398_(f_168665_, k, l, -90, 0.0F, 0.0F, i, j, i, j);
      RenderSystem.disableBlend();
      graphicsIn.m_285795_(net.minecraft.client.renderer.RenderType.m_286086_(), 0, j1, graphicsIn.m_280182_(), graphicsIn.m_280206_(), -90, -16777216);
      graphicsIn.m_285795_(net.minecraft.client.renderer.RenderType.m_286086_(), 0, 0, graphicsIn.m_280182_(), l, -90, -16777216);
      graphicsIn.m_285795_(net.minecraft.client.renderer.RenderType.m_286086_(), 0, l, k, j1, -90, -16777216);
      graphicsIn.m_285795_(net.minecraft.client.renderer.RenderType.m_286086_(), i1, l, graphicsIn.m_280182_(), j1, -90, -16777216);
   }

   private void m_93020_(Entity entityIn) {
      BlockPos blockpos = BlockPos.m_274561_(entityIn.m_20185_(), entityIn.m_20188_(), entityIn.m_20189_());
      float f = net.minecraft.client.renderer.LightTexture.m_234316_(entityIn.m_9236_().m_6042_(), entityIn.m_9236_().m_46803_(blockpos));
      float f1 = net.minecraft.util.Mth.m_14036_(1.0F - f, 0.0F, 1.0F);
      this.f_92980_ = this.f_92980_ + (f1 - this.f_92980_) * 0.01F;
   }

   private void m_280154_(net.minecraft.client.gui.GuiGraphics graphicsIn, @Nullable Entity entityIn) {
      if (!Config.isVignetteEnabled()) {
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
      } else {
         WorldBorder worldborder = this.f_92986_.f_91073_.m_6857_();
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
            f = net.minecraft.util.Mth.m_14036_(f, 0.0F, 1.0F);
            graphicsIn.m_280246_(0.0F, f, f, 1.0F);
         } else {
            float f2 = this.f_92980_;
            f2 = net.minecraft.util.Mth.m_14036_(f2, 0.0F, 1.0F);
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

   private void m_280379_(net.minecraft.client.gui.GuiGraphics graphicsIn, float timeInPortal) {
      if (timeInPortal < 1.0F) {
         timeInPortal *= timeInPortal;
         timeInPortal *= timeInPortal;
         timeInPortal = timeInPortal * 0.8F + 0.2F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, timeInPortal);
      net.minecraft.client.renderer.texture.TextureAtlasSprite textureatlassprite = this.f_92986_.m_91289_().m_110907_().m_110882_(Blocks.f_50142_.m_49966_());
      graphicsIn.m_280159_(0, 0, -90, graphicsIn.m_280182_(), graphicsIn.m_280206_(), textureatlassprite);
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void m_280585_(net.minecraft.client.gui.GuiGraphics graphicsIn, int x, int y, DeltaTracker partialTicks, Player player, ItemStack stack, int seedIn) {
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
      Entity entity = this.f_92986_.m_91288_();
      if (entity != null) {
         this.m_93020_(entity);
      }

      if (this.f_92986_.f_91074_ != null) {
         ItemStack itemstack = this.f_92986_.f_91074_.m_150109_().m_36056_();
         boolean forgeEquals = true;
         if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
            Component stackTip = (Component)Reflector.call(itemstack, Reflector.IForgeItemStack_getHighlightTip, itemstack.m_41786_());
            Component highlightTip = (Component)Reflector.call(this.f_92994_, Reflector.IForgeItemStack_getHighlightTip, this.f_92994_.m_41786_());
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
      this.f_193828_ = net.minecraft.util.Mth.m_14179_(0.2F, this.f_193828_, flag ? 1.0F : 0.0F);
   }

   public void m_93055_(Component recordName) {
      Component component = Component.m_237110_("record.nowPlaying", new Object[]{recordName});
      this.m_93063_(component, true);
      this.f_92986_.m_240477_().m_168785_(component);
   }

   public void m_93063_(Component component, boolean animateColor) {
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

   public void m_168711_(Component componentIn) {
      this.f_93002_ = componentIn;
   }

   public void m_168714_(Component componentIn) {
      this.f_93001_ = componentIn;
      this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
   }

   public void m_168713_() {
      this.f_93001_ = null;
      this.f_93002_ = null;
      this.f_93000_ = 0;
   }

   public net.minecraft.client.gui.components.ChatComponent m_93076_() {
      return this.f_92988_;
   }

   public int m_93079_() {
      return this.f_92989_;
   }

   public net.minecraft.client.gui.Font m_93082_() {
      return this.f_92986_.f_91062_;
   }

   public SpectatorGui m_93085_() {
      return this.f_92997_;
   }

   public PlayerTabOverlay m_93088_() {
      return this.f_92998_;
   }

   public void m_93089_() {
      this.f_92998_.m_94529_();
      this.f_92999_.m_93703_();
      this.f_92986_.m_91300_().m_94919_();
      this.f_291320_.m_294940_();
      this.f_92988_.m_93795_(true);
   }

   public net.minecraft.client.gui.components.BossHealthOverlay m_93090_() {
      return this.f_92999_;
   }

   public net.minecraft.client.gui.components.DebugScreenOverlay m_295051_() {
      return this.f_291320_;
   }

   public void m_93091_() {
      this.f_291320_.m_94040_();
   }

   public void m_280266_(net.minecraft.client.gui.GuiGraphics graphicsIn, DeltaTracker partialTicks) {
      if (this.f_92986_.f_91066_.m_231834_().m_231551_() && (this.f_193828_ > 0.0F || this.f_193829_ > 0.0F)) {
         int i = net.minecraft.util.Mth.m_14143_(
            255.0F * net.minecraft.util.Mth.m_14036_(net.minecraft.util.Mth.m_14179_(partialTicks.m_338557_(), this.f_193829_, this.f_193828_), 0.0F, 1.0F)
         );
         if (i > 8) {
            net.minecraft.client.gui.Font font = this.m_93082_();
            int j = font.m_92852_(f_193830_);
            int k = ARGB32.m_320289_(i, -1);
            int l = graphicsIn.m_280182_() - j - 2;
            int i1 = graphicsIn.m_280206_() - 35;
            graphicsIn.m_339210_(font, f_193830_, l, i1, j, k);
         }
      }
   }

   static record DisplayEntry(Component f_302553_, Component f_303810_, int f_302793_) {
   }

   static enum HeartType {
      CONTAINER(
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container_hardcore"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container_hardcore_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container_hardcore"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/container_hardcore_blinking")
      ),
      NORMAL(
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/half_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/hardcore_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/hardcore_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/hardcore_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/hardcore_half_blinking")
      ),
      POISIONED(
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_half_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_hardcore_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_hardcore_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_hardcore_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/poisoned_hardcore_half_blinking")
      ),
      WITHERED(
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_half_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_hardcore_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_hardcore_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_hardcore_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/withered_hardcore_half_blinking")
      ),
      ABSORBING(
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_half_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_hardcore_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_hardcore_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_hardcore_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/absorbing_hardcore_half_blinking")
      ),
      FROZEN(
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_half_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_hardcore_full"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_hardcore_full_blinking"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_hardcore_half"),
         net.minecraft.resources.ResourceLocation.m_340282_("hud/heart/frozen_hardcore_half_blinking")
      );

      private final net.minecraft.resources.ResourceLocation f_291247_;
      private final net.minecraft.resources.ResourceLocation f_290790_;
      private final net.minecraft.resources.ResourceLocation f_291803_;
      private final net.minecraft.resources.ResourceLocation f_291226_;
      private final net.minecraft.resources.ResourceLocation f_291645_;
      private final net.minecraft.resources.ResourceLocation f_291413_;
      private final net.minecraft.resources.ResourceLocation f_291153_;
      private final net.minecraft.resources.ResourceLocation f_291264_;

      private HeartType(
         final net.minecraft.resources.ResourceLocation fullIn,
         final net.minecraft.resources.ResourceLocation fullBlinkingIn,
         final net.minecraft.resources.ResourceLocation halfIn,
         final net.minecraft.resources.ResourceLocation halfBlinkingIn,
         final net.minecraft.resources.ResourceLocation hardcoreFullIn,
         final net.minecraft.resources.ResourceLocation hardcoreFullBlinkingIn,
         final net.minecraft.resources.ResourceLocation hardcoreHalfIn,
         final net.minecraft.resources.ResourceLocation hardcoreHalfBlinkingIn
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

      public net.minecraft.resources.ResourceLocation m_295491_(boolean hardcoreIn, boolean halfIn, boolean blinkingIn) {
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

      static net.minecraft.client.gui.Gui.HeartType m_168732_(Player playerIn) {
         net.minecraft.client.gui.Gui.HeartType gui$hearttype;
         if (playerIn.m_21023_(MobEffects.f_19614_)) {
            gui$hearttype = POISIONED;
         } else if (playerIn.m_21023_(MobEffects.f_19615_)) {
            gui$hearttype = WITHERED;
         } else if (playerIn.m_146890_()) {
            gui$hearttype = FROZEN;
         } else {
            gui$hearttype = NORMAL;
         }

         return gui$hearttype;
      }
   }
}
