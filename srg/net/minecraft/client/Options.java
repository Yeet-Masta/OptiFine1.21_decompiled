package net.minecraft.client;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.config.FloatOptions;
import net.optifine.config.IPersitentOption;
import net.optifine.config.Option;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import net.optifine.util.FontUtils;
import net.optifine.util.KeyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class Options {
   static final Logger f_92077_ = LogUtils.getLogger();
   static final Gson f_92078_ = new Gson();
   private static final TypeToken<List<String>> f_290931_ = new TypeToken<List<String>>() {
   };
   public static final int f_168406_ = 2;
   public static final int f_168407_ = 4;
   public static final int f_168409_ = 8;
   public static final int f_168410_ = 12;
   public static final int f_168411_ = 16;
   public static final int f_168412_ = 32;
   private static final Splitter f_92107_ = Splitter.on(':').limit(2);
   public static final String f_193766_ = "";
   private static final Component f_231789_ = Component.m_237115_("options.darkMojangStudiosBackgroundColor.tooltip");
   private final OptionInstance<Boolean> f_168413_ = OptionInstance.m_257536_(
      "options.darkMojangStudiosBackgroundColor", OptionInstance.m_231535_(f_231789_), false
   );
   private static final Component f_231790_ = Component.m_237115_("options.hideLightningFlashes.tooltip");
   private final OptionInstance<Boolean> f_231791_ = OptionInstance.m_257536_("options.hideLightningFlashes", OptionInstance.m_231535_(f_231790_), false);
   private static final Component f_302626_ = Component.m_237115_("options.hideSplashTexts.tooltip");
   private final OptionInstance<Boolean> f_302346_ = OptionInstance.m_257536_("options.hideSplashTexts", OptionInstance.m_231535_(f_302626_), false);
   private final OptionInstance<Double> f_92053_ = new OptionInstance<>("options.sensitivity", OptionInstance.m_231498_(), (p_232095_0_, p_232095_1_) -> {
      if (p_232095_1_ == 0.0) {
         return m_231921_(p_232095_0_, Component.m_237115_("options.sensitivity.min"));
      } else {
         return p_232095_1_ == 1.0 ? m_231921_(p_232095_0_, Component.m_237115_("options.sensitivity.max")) : m_231897_(p_232095_0_, 2.0 * p_232095_1_);
      }
   }, OptionInstance.UnitDouble.INSTANCE, 0.5, p_232114_0_ -> {
   });
   private final OptionInstance<Integer> f_92106_;
   private final OptionInstance<Integer> f_193768_;
   private int f_193765_ = 0;
   private final OptionInstance<Double> f_92112_ = new OptionInstance<>(
      "options.entityDistanceScaling",
      OptionInstance.m_231498_(),
      Options::m_231897_,
      new OptionInstance.IntRange(2, 20).m_231657_(p_232019_0_ -> (double)p_232019_0_ / 4.0, p_232111_0_ -> (int)(p_232111_0_ * 4.0)),
      Codec.doubleRange(0.5, 5.0),
      1.0,
      p_232040_0_ -> {
      }
   );
   public static final int f_231811_ = 260;
   private final OptionInstance<Integer> f_92113_ = new OptionInstance<>(
      "options.framerateLimit",
      OptionInstance.m_231498_(),
      (p_232047_0_, p_232047_1_) -> {
         if (this.m_231817_().m_231551_()) {
            return m_231921_(p_232047_0_, Component.m_237115_("of.options.framerateLimit.vsync"));
         } else {
            return p_232047_1_ == 260
               ? m_231921_(p_232047_0_, Component.m_237115_("options.framerateLimit.max"))
               : m_231921_(p_232047_0_, Component.m_237110_("options.framerate", new Object[]{p_232047_1_}));
         }
      },
      new OptionInstance.IntRange(0, 52).m_231657_(p_232002_0_ -> p_232002_0_ * 5, p_232093_0_ -> p_232093_0_ / 5),
      Codec.intRange(0, 260),
      120,
      p_232085_0_ -> {
         this.m_231817_().m_231514_(p_232085_0_ == 0);
         Minecraft.m_91087_().m_91268_().m_85380_(p_232085_0_);
      }
   );
   private final OptionInstance<CloudStatus> f_231792_ = new OptionInstance<>(
      "options.renderClouds",
      OptionInstance.m_231498_(),
      OptionInstance.m_231546_(),
      new OptionInstance.Enum<>(
         Arrays.asList(CloudStatus.values()),
         Codec.withAlternative(CloudStatus.f_291249_, Codec.BOOL, p_232081_0_ -> p_232081_0_ ? CloudStatus.FANCY : CloudStatus.OFF)
      ),
      CloudStatus.FANCY,
      p_231853_0_ -> {
         if (Minecraft.m_91085_()) {
            RenderTarget rendertarget = Minecraft.m_91087_().f_91060_.m_109832_();
            if (rendertarget != null) {
               rendertarget.m_83954_(Minecraft.f_91002_);
            }
         }
      }
   );
   private static final Component f_231793_ = Component.m_237115_("options.graphics.fast.tooltip");
   private static final Component f_231794_ = Component.m_237110_(
      "options.graphics.fabulous.tooltip", new Object[]{Component.m_237115_("options.graphics.fabulous").m_130940_(ChatFormatting.ITALIC)}
   );
   private static final Component f_231785_ = Component.m_237115_("options.graphics.fancy.tooltip");
   private final OptionInstance<GraphicsStatus> f_92115_ = new OptionInstance<>(
      "options.graphics",
      p_317296_0_ -> {
         if (Boolean.TRUE) {
            return null;
         } else {
            return switch (p_317296_0_) {
               case FANCY -> Tooltip.m_257550_(f_231785_);
               case FAST -> Tooltip.m_257550_(f_231793_);
               case FABULOUS -> Tooltip.m_257550_(f_231794_);
               default -> throw new MatchException(null, null);
            };
         }
      },
      (p_231903_0_, p_231903_1_) -> {
         MutableComponent mutablecomponent = Component.m_237115_(p_231903_1_.m_35968_());
         return p_231903_1_ == GraphicsStatus.FABULOUS ? mutablecomponent.m_130940_(ChatFormatting.ITALIC) : mutablecomponent;
      },
      new OptionInstance.AltEnum<>(
         Arrays.asList(GraphicsStatus.values()),
         (List<GraphicsStatus>)Stream.of(GraphicsStatus.values()).filter(p_231942_0_ -> p_231942_0_ != GraphicsStatus.FABULOUS).collect(Collectors.toList()),
         () -> !Config.isShaders() && GLX.isUsingFBOs() ? Minecraft.m_91087_().m_91396_() && Minecraft.m_91087_().m_91105_().m_109251_() : true,
         (p_231861_0_, p_231861_1_) -> {
            Minecraft minecraft = Minecraft.m_91087_();
            GpuWarnlistManager gpuwarnlistmanager = minecraft.m_91105_();
            if (p_231861_1_ == GraphicsStatus.FABULOUS && gpuwarnlistmanager.m_109240_()) {
               gpuwarnlistmanager.m_109247_();
            } else {
               p_231861_0_.m_231514_(p_231861_1_);
               this.updateRenderClouds();
               minecraft.f_91060_.m_109818_();
            }
         },
         Codec.INT.xmap(GraphicsStatus::m_90774_, GraphicsStatus::m_35965_)
      ),
      GraphicsStatus.FANCY,
      p_231855_0_ -> {
      }
   );
   private final OptionInstance<Boolean> f_92116_ = OptionInstance.m_231528_("options.ao", true, p_231849_0_ -> Minecraft.m_91087_().f_91060_.m_109818_());
   private static final Component f_231786_ = Component.m_237115_("options.prioritizeChunkUpdates.none.tooltip");
   private static final Component f_231787_ = Component.m_237115_("options.prioritizeChunkUpdates.byPlayer.tooltip");
   private static final Component f_231788_ = Component.m_237115_("options.prioritizeChunkUpdates.nearby.tooltip");
   private final OptionInstance<PrioritizeChunkUpdates> f_193769_ = new OptionInstance<>(
      "options.prioritizeChunkUpdates",
      p_317297_0_ -> {
         if (Boolean.TRUE) {
            return null;
         } else {
            return switch (p_317297_0_) {
               case NONE -> Tooltip.m_257550_(f_231786_);
               case PLAYER_AFFECTED -> Tooltip.m_257550_(f_231787_);
               case NEARBY -> Tooltip.m_257550_(f_231788_);
               default -> throw new MatchException(null, null);
            };
         }
      },
      OptionInstance.m_231546_(),
      new OptionInstance.Enum<>(
         Arrays.asList(PrioritizeChunkUpdates.values()), Codec.INT.xmap(PrioritizeChunkUpdates::m_193787_, PrioritizeChunkUpdates::m_35965_)
      ),
      PrioritizeChunkUpdates.NONE,
      p_231870_0_ -> {
      }
   );
   public List<String> f_92117_ = Lists.newArrayList();
   public List<String> f_92118_ = Lists.newArrayList();
   private final OptionInstance<ChatVisiblity> f_92119_ = new OptionInstance<>(
      "options.chat.visibility",
      OptionInstance.m_231498_(),
      OptionInstance.m_231546_(),
      new OptionInstance.Enum<>(Arrays.asList(ChatVisiblity.values()), Codec.INT.xmap(ChatVisiblity::m_35966_, ChatVisiblity::m_35965_)),
      ChatVisiblity.FULL,
      p_231843_0_ -> {
      }
   );
   private final OptionInstance<Double> f_92120_ = new OptionInstance<>(
      "options.chat.opacity",
      OptionInstance.m_231498_(),
      (p_232087_0_, p_232087_1_) -> m_231897_(p_232087_0_, p_232087_1_ * 0.9 + 0.1),
      OptionInstance.UnitDouble.INSTANCE,
      1.0,
      p_232105_0_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_()
   );
   private final OptionInstance<Double> f_92121_ = new OptionInstance<>(
      "options.chat.line_spacing", OptionInstance.m_231498_(), Options::m_231897_, OptionInstance.UnitDouble.INSTANCE, 0.0, p_232102_0_ -> {
      }
   );
   private static final Component f_315005_ = Component.m_237115_("options.accessibility.menu_background_blurriness.tooltip");
   private static final int f_315767_ = 5;
   private final OptionInstance<Integer> f_317010_ = new OptionInstance<>(
      "options.accessibility.menu_background_blurriness",
      OptionInstance.m_231535_(f_315005_),
      Options::m_338389_,
      new OptionInstance.IntRange(0, 10),
      5,
      p_232108_0_ -> {
      }
   );
   private final OptionInstance<Double> f_92122_ = new OptionInstance<>(
      "options.accessibility.text_background_opacity",
      OptionInstance.m_231498_(),
      Options::m_231897_,
      OptionInstance.UnitDouble.INSTANCE,
      0.5,
      p_232099_0_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_()
   );
   private final OptionInstance<Double> f_244402_ = new OptionInstance<>(
      "options.accessibility.panorama_speed", OptionInstance.m_231498_(), Options::m_231897_, OptionInstance.UnitDouble.INSTANCE, 1.0, p_232038_0_ -> {
      }
   );
   private static final Component f_273812_ = Component.m_237115_("options.accessibility.high_contrast.tooltip");
   private final OptionInstance<Boolean> f_273910_ = OptionInstance.m_257874_(
      "options.accessibility.high_contrast", OptionInstance.m_231535_(f_273812_), false, p_275764_1_ -> {
         PackRepository packrepository = Minecraft.m_91087_().m_91099_();
         boolean flag1 = packrepository.m_10523_().contains("high_contrast");
         if (!flag1 && p_275764_1_) {
            if (packrepository.m_275855_("high_contrast")) {
               this.m_274546_(packrepository);
            }
         } else if (flag1 && !p_275764_1_ && packrepository.m_275853_("high_contrast")) {
            this.m_274546_(packrepository);
         }
      }
   );
   private final OptionInstance<Boolean> f_290977_ = OptionInstance.m_257536_(
      "options.accessibility.narrator_hotkey",
      OptionInstance.m_231535_(
         Minecraft.f_91002_
            ? Component.m_237115_("options.accessibility.narrator_hotkey.mac.tooltip")
            : Component.m_237115_("options.accessibility.narrator_hotkey.tooltip")
      ),
      true
   );
   @Nullable
   public String f_92123_;
   public boolean f_92124_;
   public boolean f_92125_;
   public boolean f_92126_ = true;
   private final Set<PlayerModelPart> f_92108_ = EnumSet.allOf(PlayerModelPart.class);
   private final OptionInstance<HumanoidArm> f_92127_ = new OptionInstance<>(
      "options.mainHand",
      OptionInstance.m_231498_(),
      OptionInstance.m_231546_(),
      new OptionInstance.Enum<>(Arrays.asList(HumanoidArm.values()), HumanoidArm.f_291347_),
      HumanoidArm.RIGHT,
      p_231841_1_ -> this.m_92172_()
   );
   public int f_92128_;
   public int f_92129_;
   private final OptionInstance<Double> f_92131_ = new OptionInstance<>(
      "options.chat.scale",
      OptionInstance.m_231498_(),
      (p_232077_0_, p_232077_1_) -> (Component)(p_232077_1_ == 0.0 ? CommonComponents.m_130663_(p_232077_0_, false) : m_231897_(p_232077_0_, p_232077_1_)),
      OptionInstance.UnitDouble.INSTANCE,
      1.0,
      p_232091_0_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_()
   );
   private final OptionInstance<Double> f_92132_ = new OptionInstance<>(
      "options.chat.width",
      OptionInstance.m_231498_(),
      (p_232067_0_, p_232067_1_) -> m_231952_(p_232067_0_, (int)((double)ChatComponent.m_93798_(p_232067_1_) / 4.0571431)),
      OptionInstance.UnitDouble.INSTANCE,
      1.0,
      p_232083_0_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_()
   );
   private final OptionInstance<Double> f_92133_ = new OptionInstance<>(
      "options.chat.height.unfocused",
      OptionInstance.m_231498_(),
      (p_232057_0_, p_232057_1_) -> m_231952_(p_232057_0_, ChatComponent.m_93811_(p_232057_1_)),
      OptionInstance.UnitDouble.INSTANCE,
      ChatComponent.m_232477_(),
      p_232073_0_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_()
   );
   private final OptionInstance<Double> f_92134_ = new OptionInstance<>(
      "options.chat.height.focused",
      OptionInstance.m_231498_(),
      (p_232044_0_, p_232044_1_) -> m_231952_(p_232044_0_, ChatComponent.m_93811_(p_232044_1_)),
      OptionInstance.UnitDouble.INSTANCE,
      1.0,
      p_232063_0_ -> Minecraft.m_91087_().f_91065_.m_93076_().m_93769_()
   );
   private final OptionInstance<Double> f_92135_ = new OptionInstance<>(
      "options.chat.delay_instant",
      OptionInstance.m_231498_(),
      (p_241715_0_, p_241715_1_) -> p_241715_1_ <= 0.0
            ? Component.m_237115_("options.chat.delay_none")
            : Component.m_237110_("options.chat.delay", new Object[]{String.format(Locale.ROOT, "%.1f", p_241715_1_)}),
      new OptionInstance.IntRange(0, 60).m_231657_(p_231985_0_ -> (double)p_231985_0_ / 10.0, p_232053_0_ -> (int)(p_232053_0_ * 10.0)),
      Codec.doubleRange(0.0, 6.0),
      0.0,
      p_240679_0_ -> Minecraft.m_91087_().m_240442_().m_240692_(p_240679_0_)
   );
   private static final Component f_263815_ = Component.m_237115_("options.notifications.display_time.tooltip");
   private final OptionInstance<Double> f_263718_ = new OptionInstance<>(
      "options.notifications.display_time",
      OptionInstance.m_231535_(f_263815_),
      (p_231961_0_, p_231961_1_) -> m_231921_(p_231961_0_, Component.m_237110_("options.multiplier", new Object[]{p_231961_1_})),
      new OptionInstance.IntRange(5, 100).m_231657_(p_263860_0_ -> (double)p_263860_0_ / 10.0, p_263861_0_ -> (int)(p_263861_0_ * 10.0)),
      Codec.doubleRange(0.5, 10.0),
      1.0,
      p_231851_0_ -> {
      }
   );
   private final OptionInstance<Integer> f_92027_ = new OptionInstance<>("options.mipmapLevels", OptionInstance.m_231498_(), (p_232032_0_, p_232032_1_) -> {
      if ((double)p_232032_1_.intValue() >= 4.0) {
         return m_231921_(p_232032_0_, Component.m_237115_("of.general.max"));
      } else {
         return (Component)(p_232032_1_ == 0 ? CommonComponents.m_130663_(p_232032_0_, false) : m_231900_(p_232032_0_, p_232032_1_));
      }
   }, new OptionInstance.IntRange(0, 4), 4, p_232023_0_ -> this.updateMipmaps());
   public boolean f_92028_ = true;
   private final OptionInstance<AttackIndicatorStatus> f_92029_ = new OptionInstance<>(
      "options.attackIndicator",
      OptionInstance.m_231498_(),
      OptionInstance.m_231546_(),
      new OptionInstance.Enum<>(Arrays.asList(AttackIndicatorStatus.values()), Codec.INT.xmap(AttackIndicatorStatus::m_90509_, AttackIndicatorStatus::m_35965_)),
      AttackIndicatorStatus.CROSSHAIR,
      p_231987_0_ -> {
      }
   );
   public TutorialSteps f_92030_ = TutorialSteps.MOVEMENT;
   public boolean f_92031_ = false;
   public boolean f_168405_ = false;
   private final OptionInstance<Integer> f_92032_ = new OptionInstance<>(
      "options.biomeBlendRadius", OptionInstance.m_231498_(), (p_232015_0_, p_232015_1_) -> {
         int i = p_232015_1_ * 2 + 1;
         return m_231921_(p_232015_0_, Component.m_237115_("options.biomeBlendRadius." + i));
      }, new OptionInstance.IntRange(0, 7, false), 2, p_232025_0_ -> Minecraft.m_91087_().f_91060_.m_109818_()
   );
   private final OptionInstance<Double> f_92033_ = new OptionInstance<>(
      "options.mouseWheelSensitivity",
      OptionInstance.m_231498_(),
      (p_241716_0_, p_241716_1_) -> m_231921_(p_241716_0_, Component.m_237113_(String.format(Locale.ROOT, "%.2f", p_241716_1_))),
      new OptionInstance.IntRange(-200, 100).m_231657_(Options::m_231965_, Options::m_231839_),
      Codec.doubleRange(m_231965_(-200), m_231965_(100)),
      m_231965_(0),
      p_231946_0_ -> {
      }
   );
   private final OptionInstance<Boolean> f_92034_ = OptionInstance.m_231528_("options.rawMouseInput", true, p_232061_0_ -> {
      Window window = Minecraft.m_91087_().m_91268_();
      if (window != null) {
         window.m_85424_(p_232061_0_);
      }
   });
   public int f_92035_ = 1;
   private final OptionInstance<Boolean> f_92036_ = OptionInstance.m_231525_("options.autoJump", false);
   private final OptionInstance<Boolean> f_256834_ = OptionInstance.m_231525_("options.operatorItemsTab", false);
   private final OptionInstance<Boolean> f_92037_ = OptionInstance.m_231525_("options.autoSuggestCommands", true);
   private final OptionInstance<Boolean> f_92038_ = OptionInstance.m_231525_("options.chat.color", true);
   private final OptionInstance<Boolean> f_92039_ = OptionInstance.m_231525_("options.chat.links", true);
   private final OptionInstance<Boolean> f_92040_ = OptionInstance.m_231525_("options.chat.links.prompt", true);
   private final OptionInstance<Boolean> f_92041_ = OptionInstance.m_231528_("options.vsync", true, p_232051_0_ -> {
      if (Minecraft.m_91087_().m_91268_() != null) {
         Minecraft.m_91087_().m_91268_().m_85409_(p_232051_0_);
      }
   });
   private final OptionInstance<Boolean> f_92042_ = OptionInstance.m_231525_("options.entityShadows", true);
   private final OptionInstance<Boolean> f_92043_ = OptionInstance.m_231528_("options.forceUnicodeFont", false, p_317299_0_ -> m_320153_());
   private final OptionInstance<Boolean> f_314642_ = OptionInstance.m_257874_(
      "options.japaneseGlyphVariants",
      OptionInstance.m_231535_(Component.m_237115_("options.japaneseGlyphVariants.tooltip")),
      m_324081_(),
      p_317300_0_ -> m_320153_()
   );
   private final OptionInstance<Boolean> f_92044_ = OptionInstance.m_231525_("options.invertMouse", false);
   private final OptionInstance<Boolean> f_92045_ = OptionInstance.m_231525_("options.discrete_mouse_scroll", false);
   private static final Component f_337252_ = Component.m_237115_("options.realmsNotifications.tooltip");
   private final OptionInstance<Boolean> f_92046_ = OptionInstance.m_257536_("options.realmsNotifications", OptionInstance.m_231535_(f_337252_), true);
   private static final Component f_231804_ = Component.m_237115_("options.allowServerListing.tooltip");
   private final OptionInstance<Boolean> f_193762_ = OptionInstance.m_257874_(
      "options.allowServerListing", OptionInstance.m_231535_(f_231804_), true, p_232021_1_ -> this.m_92172_()
   );
   private final OptionInstance<Boolean> f_92047_ = OptionInstance.m_231525_("options.reducedDebugInfo", false);
   private final Map<SoundSource, OptionInstance<Double>> f_244498_ = Util.m_137469_(new EnumMap(SoundSource.class), p_244656_1_ -> {
      for (SoundSource soundsource : SoundSource.values()) {
         p_244656_1_.put(soundsource, this.m_247249_("soundCategory." + soundsource.m_12676_(), soundsource));
      }
   });
   private final OptionInstance<Boolean> f_92049_ = OptionInstance.m_231525_("options.showSubtitles", false);
   private static final Component f_231805_ = Component.m_237115_("options.directionalAudio.on.tooltip");
   private static final Component f_231806_ = Component.m_237115_("options.directionalAudio.off.tooltip");
   private final OptionInstance<Boolean> f_231807_ = OptionInstance.m_257874_(
      "options.directionalAudio", p_257068_0_ -> p_257068_0_ ? Tooltip.m_257550_(f_231805_) : Tooltip.m_257550_(f_231806_), false, p_263137_0_ -> {
         SoundManager soundmanager = Minecraft.m_91087_().m_91106_();
         soundmanager.m_194526_();
         soundmanager.m_120367_(SimpleSoundInstance.m_263171_(SoundEvents.f_12490_, 1.0F));
      }
   );
   private final OptionInstance<Boolean> f_92050_ = new OptionInstance<>(
      "options.accessibility.text_background",
      OptionInstance.m_231498_(),
      (p_231975_0_, p_231975_1_) -> p_231975_1_
            ? Component.m_237115_("options.accessibility.text_background.chat")
            : Component.m_237115_("options.accessibility.text_background.everywhere"),
      OptionInstance.f_231471_,
      true,
      p_231874_0_ -> {
      }
   );
   private final OptionInstance<Boolean> f_92051_ = OptionInstance.m_231525_("options.touchscreen", false);
   private final OptionInstance<Boolean> f_92052_ = OptionInstance.m_231528_("options.fullscreen", false, p_231969_1_ -> {
      Minecraft minecraft = Minecraft.m_91087_();
      if (minecraft.m_91268_() != null && minecraft.m_91268_().m_85440_() != p_231969_1_) {
         minecraft.m_91268_().m_85438_();
         this.m_231829_().m_231514_(minecraft.m_91268_().m_85440_());
      }
   });
   private final OptionInstance<Boolean> f_92080_ = OptionInstance.m_231525_("options.viewBobbing", true);
   private static final Component f_231808_ = Component.m_237115_("options.key.toggle");
   private static final Component f_231809_ = Component.m_237115_("options.key.hold");
   private final OptionInstance<Boolean> f_92081_ = new OptionInstance<>(
      "key.sneak",
      OptionInstance.m_231498_(),
      (p_231955_0_, p_231955_1_) -> p_231955_1_ ? f_231808_ : f_231809_,
      OptionInstance.f_231471_,
      false,
      p_231989_0_ -> {
      }
   );
   private final OptionInstance<Boolean> f_92082_ = new OptionInstance<>(
      "key.sprint",
      OptionInstance.m_231498_(),
      (p_231909_0_, p_231909_1_) -> p_231909_1_ ? f_231808_ : f_231809_,
      OptionInstance.f_231471_,
      false,
      p_231971_0_ -> {
      }
   );
   public boolean f_92083_;
   private static final Component f_231810_ = Component.m_237115_("options.hideMatchedNames.tooltip");
   private final OptionInstance<Boolean> f_92084_ = OptionInstance.m_257536_("options.hideMatchedNames", OptionInstance.m_231535_(f_231810_), true);
   private final OptionInstance<Boolean> f_193763_ = OptionInstance.m_231525_("options.autosaveIndicator", true);
   private static final Component f_231797_ = Component.m_237115_("options.onlyShowSecureChat.tooltip");
   private final OptionInstance<Boolean> f_231798_ = OptionInstance.m_257536_("options.onlyShowSecureChat", OptionInstance.m_231535_(f_231797_), false);
   public final KeyMapping f_92085_ = new KeyMapping("key.forward", 87, "key.categories.movement");
   public final KeyMapping f_92086_ = new KeyMapping("key.left", 65, "key.categories.movement");
   public final KeyMapping f_92087_ = new KeyMapping("key.back", 83, "key.categories.movement");
   public final KeyMapping f_92088_ = new KeyMapping("key.right", 68, "key.categories.movement");
   public final KeyMapping f_92089_ = new KeyMapping("key.jump", 32, "key.categories.movement");
   public final KeyMapping f_92090_ = new ToggleKeyMapping("key.sneak", 340, "key.categories.movement", this.f_92081_::m_231551_);
   public final KeyMapping f_92091_ = new ToggleKeyMapping("key.sprint", 341, "key.categories.movement", this.f_92082_::m_231551_);
   public final KeyMapping f_92092_ = new KeyMapping("key.inventory", 69, "key.categories.inventory");
   public final KeyMapping f_92093_ = new KeyMapping("key.swapOffhand", 70, "key.categories.inventory");
   public final KeyMapping f_92094_ = new KeyMapping("key.drop", 81, "key.categories.inventory");
   public final KeyMapping f_92095_ = new KeyMapping("key.use", Type.MOUSE, 1, "key.categories.gameplay");
   public final KeyMapping f_92096_ = new KeyMapping("key.attack", Type.MOUSE, 0, "key.categories.gameplay");
   public final KeyMapping f_92097_ = new KeyMapping("key.pickItem", Type.MOUSE, 2, "key.categories.gameplay");
   public final KeyMapping f_92098_ = new KeyMapping("key.chat", 84, "key.categories.multiplayer");
   public final KeyMapping f_92099_ = new KeyMapping("key.playerlist", 258, "key.categories.multiplayer");
   public final KeyMapping f_92100_ = new KeyMapping("key.command", 47, "key.categories.multiplayer");
   public final KeyMapping f_92101_ = new KeyMapping("key.socialInteractions", 80, "key.categories.multiplayer");
   public final KeyMapping f_92102_ = new KeyMapping("key.screenshot", 291, "key.categories.misc");
   public final KeyMapping f_92103_ = new KeyMapping("key.togglePerspective", 294, "key.categories.misc");
   public final KeyMapping f_92104_ = new KeyMapping("key.smoothCamera", InputConstants.f_84822_.m_84873_(), "key.categories.misc");
   public final KeyMapping f_92105_ = new KeyMapping("key.fullscreen", 300, "key.categories.misc");
   public final KeyMapping f_92054_ = new KeyMapping("key.spectatorOutlines", InputConstants.f_84822_.m_84873_(), "key.categories.misc");
   public final KeyMapping f_92055_ = new KeyMapping("key.advancements", 76, "key.categories.misc");
   public final KeyMapping[] f_92056_ = new KeyMapping[]{
      new KeyMapping("key.hotbar.1", 49, "key.categories.inventory"),
      new KeyMapping("key.hotbar.2", 50, "key.categories.inventory"),
      new KeyMapping("key.hotbar.3", 51, "key.categories.inventory"),
      new KeyMapping("key.hotbar.4", 52, "key.categories.inventory"),
      new KeyMapping("key.hotbar.5", 53, "key.categories.inventory"),
      new KeyMapping("key.hotbar.6", 54, "key.categories.inventory"),
      new KeyMapping("key.hotbar.7", 55, "key.categories.inventory"),
      new KeyMapping("key.hotbar.8", 56, "key.categories.inventory"),
      new KeyMapping("key.hotbar.9", 57, "key.categories.inventory")
   };
   public final KeyMapping f_92057_ = new KeyMapping("key.saveToolbarActivator", 67, "key.categories.creative");
   public final KeyMapping f_92058_ = new KeyMapping("key.loadToolbarActivator", 88, "key.categories.creative");
   public KeyMapping[] f_92059_ = (KeyMapping[])ArrayUtils.addAll(
      new KeyMapping[]{
         this.f_92096_,
         this.f_92095_,
         this.f_92085_,
         this.f_92086_,
         this.f_92087_,
         this.f_92088_,
         this.f_92089_,
         this.f_92090_,
         this.f_92091_,
         this.f_92094_,
         this.f_92092_,
         this.f_92098_,
         this.f_92099_,
         this.f_92097_,
         this.f_92100_,
         this.f_92101_,
         this.f_92102_,
         this.f_92103_,
         this.f_92104_,
         this.f_92105_,
         this.f_92054_,
         this.f_92093_,
         this.f_92057_,
         this.f_92058_,
         this.f_92055_
      },
      this.f_92056_
   );
   protected Minecraft f_92060_;
   private final File f_92110_;
   public boolean f_92062_;
   private CameraType f_92111_ = CameraType.FIRST_PERSON;
   public String f_92066_ = "";
   public boolean f_92067_;
   private final OptionInstance<Integer> f_92068_ = new OptionInstance<>(
      "options.fov",
      OptionInstance.m_231498_(),
      (p_231998_0_, p_231998_1_) -> {
         return switch (p_231998_1_) {
            case 70 -> m_231921_(p_231998_0_, Component.m_237115_("options.fov.min"));
            case 110 -> m_231921_(p_231998_0_, Component.m_237115_("options.fov.max"));
            default -> m_231900_(p_231998_0_, p_231998_1_);
         };
      },
      new OptionInstance.IntRange(30, 110),
      Codec.DOUBLE.xmap(p_232006_0_ -> (int)(p_232006_0_ * 40.0 + 70.0), p_232008_0_ -> ((double)p_232008_0_.intValue() - 70.0) / 40.0),
      70,
      p_231991_0_ -> Minecraft.m_91087_().f_91060_.m_109826_()
   );
   private static final Component f_260656_ = Component.m_237110_(
      "options.telemetry.button.tooltip",
      new Object[]{Component.m_237115_("options.telemetry.state.minimal"), Component.m_237115_("options.telemetry.state.all")}
   );
   private final OptionInstance<Boolean> f_260461_ = OptionInstance.m_260965_(
      "options.telemetry.button",
      OptionInstance.m_231535_(f_260656_),
      (p_260741_0_, p_260741_1_) -> {
         Minecraft minecraft = Minecraft.m_91087_();
         if (!minecraft.m_261210_()) {
            return Component.m_237115_("options.telemetry.state.none");
         } else {
            return p_260741_1_ && minecraft.m_261227_()
               ? Component.m_237115_("options.telemetry.state.all")
               : Component.m_237115_("options.telemetry.state.minimal");
         }
      },
      false,
      p_231948_0_ -> {
      }
   );
   private static final Component f_231799_ = Component.m_237115_("options.screenEffectScale.tooltip");
   private final OptionInstance<Double> f_92069_ = new OptionInstance<>(
      "options.screenEffectScale", OptionInstance.m_231535_(f_231799_), Options::m_324758_, OptionInstance.UnitDouble.INSTANCE, 1.0, p_231876_0_ -> {
      }
   );
   private static final Component f_231800_ = Component.m_237115_("options.fovEffectScale.tooltip");
   private final OptionInstance<Double> f_92070_ = new OptionInstance<>(
      "options.fovEffectScale",
      OptionInstance.m_231535_(f_231800_),
      Options::m_324758_,
      OptionInstance.UnitDouble.INSTANCE.m_231750_(Mth::m_144952_, Math::sqrt),
      Codec.doubleRange(0.0, 1.0),
      1.0,
      p_231973_0_ -> {
      }
   );
   private static final Component f_231801_ = Component.m_237115_("options.darknessEffectScale.tooltip");
   private final OptionInstance<Double> f_231802_ = new OptionInstance<>(
      "options.darknessEffectScale",
      OptionInstance.m_231535_(f_231801_),
      Options::m_324758_,
      OptionInstance.UnitDouble.INSTANCE.m_231750_(Mth::m_144952_, Math::sqrt),
      1.0,
      p_231868_0_ -> {
      }
   );
   private static final Component f_267409_ = Component.m_237115_("options.glintSpeed.tooltip");
   private final OptionInstance<Double> f_267458_ = new OptionInstance<>(
      "options.glintSpeed", OptionInstance.m_231535_(f_267409_), Options::m_324758_, OptionInstance.UnitDouble.INSTANCE, 0.5, p_241717_0_ -> {
      }
   );
   private static final Component f_267450_ = Component.m_237115_("options.glintStrength.tooltip");
   private final OptionInstance<Double> f_267462_ = new OptionInstance<>(
      "options.glintStrength",
      OptionInstance.m_231535_(f_267450_),
      Options::m_324758_,
      OptionInstance.UnitDouble.INSTANCE,
      0.75,
      RenderSystem::setShaderGlintAlpha
   );
   private static final Component f_268597_ = Component.m_237115_("options.damageTiltStrength.tooltip");
   private final OptionInstance<Double> f_268427_ = new OptionInstance<>(
      "options.damageTiltStrength", OptionInstance.m_231535_(f_268597_), Options::m_324758_, OptionInstance.UnitDouble.INSTANCE, 1.0, p_260742_0_ -> {
      }
   );
   private final OptionInstance<Double> f_92071_ = new OptionInstance<>("options.gamma", OptionInstance.m_231498_(), (p_231912_0_, p_231912_1_) -> {
      int i = (int)(p_231912_1_ * 100.0);
      if (i == 0) {
         return m_231921_(p_231912_0_, Component.m_237115_("options.gamma.min"));
      } else if (i == 50) {
         return m_231921_(p_231912_0_, Component.m_237115_("options.gamma.default"));
      } else {
         return i == 100 ? m_231921_(p_231912_0_, Component.m_237115_("options.gamma.max")) : m_231900_(p_231912_0_, i);
      }
   }, OptionInstance.UnitDouble.INSTANCE, 0.5, p_263858_0_ -> {
   });
   public static final int f_278127_ = 0;
   private static final int f_276073_ = 2147483646;
   private final OptionInstance<Integer> f_92072_ = new OptionInstance<>(
      "options.guiScale",
      OptionInstance.m_231498_(),
      (p_231981_0_, p_231981_1_) -> p_231981_1_ == 0 ? Component.m_237115_("options.guiScale.auto") : Component.m_237113_(Integer.toString(p_231981_1_)),
      new OptionInstance.ClampingLazyMaxIntRange(0, () -> {
         Minecraft minecraft = Minecraft.m_91087_();
         return !minecraft.m_91396_() ? 2147483646 : minecraft.m_91268_().m_85385_(0, minecraft.m_91390_());
      }, 2147483646),
      0,
      p_317301_1_ -> this.f_92060_.m_5741_()
   );
   private final OptionInstance<ParticleStatus> f_92073_ = new OptionInstance<>(
      "options.particles",
      OptionInstance.m_231498_(),
      OptionInstance.m_231546_(),
      new OptionInstance.Enum<>(Arrays.asList(ParticleStatus.values()), Codec.INT.xmap(ParticleStatus::m_92196_, ParticleStatus::m_35965_)),
      ParticleStatus.ALL,
      p_267500_0_ -> {
      }
   );
   private final OptionInstance<NarratorStatus> f_231803_ = new OptionInstance<>(
      "options.narrator",
      OptionInstance.m_231498_(),
      (p_240390_1_, p_240390_2_) -> (Component)(this.f_92060_.m_240477_().m_93316_()
            ? p_240390_2_.m_91621_()
            : Component.m_237115_("options.narrator.notavailable")),
      new OptionInstance.Enum<>(Arrays.asList(NarratorStatus.values()), Codec.INT.xmap(NarratorStatus::m_91619_, NarratorStatus::m_91618_)),
      NarratorStatus.OFF,
      p_240389_1_ -> this.f_92060_.m_240477_().m_93317_(p_240389_1_)
   );
   public String f_92075_ = "en_us";
   private final OptionInstance<String> f_193764_ = new OptionInstance<>(
      "options.audioDevice",
      OptionInstance.m_231498_(),
      (p_231918_0_, p_231918_1_) -> {
         if ("".equals(p_231918_1_)) {
            return Component.m_237115_("options.audioDevice.default");
         } else {
            return p_231918_1_.startsWith("OpenAL Soft on ")
               ? Component.m_237113_(p_231918_1_.substring(SoundEngine.f_194470_))
               : Component.m_237113_(p_231918_1_);
         }
      },
      new OptionInstance.LazyEnum<>(
         () -> Stream.concat(Stream.of(""), Minecraft.m_91087_().m_91106_().m_194525_().stream()).toList(),
         p_232010_0_ -> Minecraft.m_91087_().m_91396_()
                  && (p_232010_0_ == null || p_232010_0_.isEmpty())
                  && !Minecraft.m_91087_().m_91106_().m_194525_().contains(p_232010_0_)
               ? Optional.empty()
               : Optional.of(p_232010_0_),
         Codec.STRING
      ),
      "",
      p_263138_0_ -> {
         SoundManager soundmanager = Minecraft.m_91087_().m_91106_();
         soundmanager.m_194526_();
         soundmanager.m_120367_(SimpleSoundInstance.m_263171_(SoundEvents.f_12490_, 1.0F));
      }
   );
   public boolean f_263744_ = true;
   public boolean f_92076_;
   public int ofFogType = 1;
   public float ofFogStart = 0.8F;
   public int ofMipmapType = 0;
   public boolean ofOcclusionFancy = false;
   public boolean ofSmoothFps = false;
   public boolean ofSmoothWorld = Config.isSingleProcessor();
   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
   public boolean ofRenderRegions = false;
   public boolean ofSmartAnimations = false;
   public double ofAoLevel = 1.0;
   public int ofAaLevel = 0;
   public int ofAfLevel = 1;
   public int ofClouds = 0;
   public double ofCloudsHeight = 0.0;
   public int ofTrees = 0;
   public int ofRain = 0;
   public int ofBetterGrass = 3;
   public int ofAutoSaveTicks = 4000;
   public boolean ofLagometer = false;
   public boolean ofProfiler = false;
   public boolean ofWeather = true;
   public boolean ofSky = true;
   public boolean ofStars = true;
   public boolean ofSunMoon = true;
   public int ofVignette = 0;
   public int ofChunkUpdates = 1;
   public boolean ofChunkUpdatesDynamic = false;
   public int ofTime = 0;
   public boolean ofBetterSnow = false;
   public boolean ofSwampColors = true;
   public boolean ofRandomEntities = true;
   public boolean ofCustomFonts = true;
   public boolean ofCustomColors = true;
   public boolean ofCustomSky = true;
   public boolean ofShowCapes = true;
   public int ofConnectedTextures = 2;
   public boolean ofCustomItems = true;
   public boolean ofNaturalTextures = false;
   public boolean ofEmissiveTextures = true;
   public boolean ofFastMath = false;
   public boolean ofFastRender = false;
   public boolean ofDynamicFov = true;
   public boolean ofAlternateBlocks = true;
   public int ofDynamicLights = 3;
   public boolean ofCustomEntityModels = true;
   public boolean ofCustomGuis = true;
   public boolean ofShowGlErrors = true;
   public int ofScreenshotSize = 1;
   public int ofChatBackground = 0;
   public boolean ofChatShadow = true;
   public int ofTelemetry = 0;
   public boolean ofHeldItemTooltips = true;
   public int ofAnimatedWater = 0;
   public int ofAnimatedLava = 0;
   public boolean ofAnimatedFire = true;
   public boolean ofAnimatedPortal = true;
   public boolean ofAnimatedRedstone = true;
   public boolean ofAnimatedExplosion = true;
   public boolean ofAnimatedFlame = true;
   public boolean ofAnimatedSmoke = true;
   public boolean ofVoidParticles = true;
   public boolean ofWaterParticles = true;
   public boolean ofRainSplash = true;
   public boolean ofPortalParticles = true;
   public boolean ofPotionParticles = true;
   public boolean ofFireworkParticles = true;
   public boolean ofDrippingWaterLava = true;
   public boolean ofAnimatedTerrain = true;
   public boolean ofAnimatedTextures = true;
   public boolean ofQuickInfo = false;
   public int ofQuickInfoFps = Option.FULL.getValue();
   public boolean ofQuickInfoChunks = true;
   public boolean ofQuickInfoEntities = true;
   public boolean ofQuickInfoParticles = false;
   public boolean ofQuickInfoUpdates = true;
   public boolean ofQuickInfoGpu = false;
   public int ofQuickInfoPos = Option.COMPACT.getValue();
   public int ofQuickInfoFacing = Option.OFF.getValue();
   public boolean ofQuickInfoBiome = false;
   public boolean ofQuickInfoLight = false;
   public int ofQuickInfoMemory = Option.OFF.getValue();
   public int ofQuickInfoNativeMemory = Option.OFF.getValue();
   public int ofQuickInfoTargetBlock = Option.OFF.getValue();
   public int ofQuickInfoTargetFluid = Option.OFF.getValue();
   public int ofQuickInfoTargetEntity = Option.OFF.getValue();
   public int ofQuickInfoLabels = Option.COMPACT.getValue();
   public boolean ofQuickInfoBackground = false;
   public static final int DEFAULT = 0;
   public static final int FAST = 1;
   public static final int FANCY = 2;
   public static final int OFF = 3;
   public static final int SMART = 4;
   public static final int COMPACT = 5;
   public static final int FULL = 6;
   public static final int DETAILED = 7;
   public static final int ANIM_ON = 0;
   public static final int ANIM_GENERATED = 1;
   public static final int ANIM_OFF = 2;
   public static final String DEFAULT_STR = "Default";
   public static final double CHAT_WIDTH_SCALE = 4.0571431;
   public static final int[] VALS_FAST_FANCY_OFF = new int[]{1, 2, 3};
   private static final int[] OF_TREES_VALUES = new int[]{0, 1, 4, 2};
   private static final int[] OF_DYNAMIC_LIGHTS = new int[]{3, 1, 2};
   private static final String[] KEYS_DYNAMIC_LIGHTS = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
   public static final int TELEM_ON = 0;
   public static final int TELEM_ANON = 1;
   public static final int TELEM_OFF = 2;
   private static final int[] OF_TELEMETRY = new int[]{0, 1, 2};
   private static final String[] KEYS_TELEMETRY = new String[]{"options.on", "of.general.anonymous", "options.off"};
   public KeyMapping ofKeyBindZoom;
   private File optionsFileOF;
   private boolean loadOptions;
   private boolean saveOptions;
   public final OptionInstance GRAPHICS = this.f_92115_;
   public final OptionInstance RENDER_DISTANCE;
   public final OptionInstance SIMULATION_DISTANCE;
   public final OptionInstance AO = this.f_92116_;
   public final OptionInstance FRAMERATE_LIMIT = this.f_92113_;
   public final OptionInstance GUI_SCALE = this.f_92072_;
   public final OptionInstance ENTITY_SHADOWS = this.f_92042_;
   public final OptionInstance GAMMA = this.f_92071_;
   public final OptionInstance ATTACK_INDICATOR = this.f_92029_;
   public final OptionInstance PARTICLES = this.f_92073_;
   public final OptionInstance VIEW_BOBBING = this.f_92080_;
   public final OptionInstance AUTOSAVE_INDICATOR = this.f_193763_;
   public final OptionInstance ENTITY_DISTANCE_SCALING = this.f_92112_;
   public final OptionInstance BIOME_BLEND_RADIUS = this.f_92032_;
   public final OptionInstance FULLSCREEN = this.f_92052_;
   public final OptionInstance PRIORITIZE_CHUNK_UPDATES = this.f_193769_;
   public final OptionInstance MIPMAP_LEVELS = this.f_92027_;
   public final OptionInstance SCREEN_EFFECT_SCALE = this.f_92069_;
   public final OptionInstance FOV_EFFECT_SCALE = this.f_92070_;

   public OptionInstance<Boolean> m_231838_() {
      return this.f_168413_;
   }

   public OptionInstance<Boolean> m_231935_() {
      return this.f_231791_;
   }

   public OptionInstance<Boolean> m_307023_() {
      return this.f_302346_;
   }

   public OptionInstance<Double> m_231964_() {
      return this.f_92053_;
   }

   public OptionInstance<Integer> m_231984_() {
      return this.f_92106_;
   }

   public OptionInstance<Integer> m_232001_() {
      return this.f_193768_;
   }

   public OptionInstance<Double> m_232018_() {
      return this.f_92112_;
   }

   public OptionInstance<Integer> m_232035_() {
      return this.f_92113_;
   }

   public OptionInstance<CloudStatus> m_232050_() {
      return this.f_231792_;
   }

   public OptionInstance<GraphicsStatus> m_232060_() {
      return this.f_92115_;
   }

   public OptionInstance<Boolean> m_232070_() {
      return this.f_92116_;
   }

   public OptionInstance<PrioritizeChunkUpdates> m_232080_() {
      return this.f_193769_;
   }

   public void m_274546_(PackRepository repoIn) {
      List<String> list = ImmutableList.copyOf(this.f_92117_);
      this.f_92117_.clear();
      this.f_92118_.clear();

      for (Pack pack : repoIn.m_10524_()) {
         if (!pack.m_10450_()) {
            this.f_92117_.add(pack.m_10446_());
            if (!pack.m_10443_().m_10489_()) {
               this.f_92118_.add(pack.m_10446_());
            }
         }
      }

      this.m_92169_();
      List<String> list1 = ImmutableList.copyOf(this.f_92117_);
      if (!list1.equals(list)) {
         this.f_92060_.m_91391_();
      }
   }

   public OptionInstance<ChatVisiblity> m_232090_() {
      return this.f_92119_;
   }

   public OptionInstance<Double> m_232098_() {
      return this.f_92120_;
   }

   public OptionInstance<Double> m_232101_() {
      return this.f_92121_;
   }

   public OptionInstance<Integer> m_323040_() {
      return this.f_317010_;
   }

   public int m_321110_() {
      return this.m_323040_().m_231551_();
   }

   public OptionInstance<Double> m_232104_() {
      return this.f_92122_;
   }

   public OptionInstance<Double> m_245201_() {
      return this.f_244402_;
   }

   public OptionInstance<Boolean> m_274330_() {
      return this.f_273910_;
   }

   public OptionInstance<Boolean> m_292959_() {
      return this.f_290977_;
   }

   public OptionInstance<HumanoidArm> m_232107_() {
      return this.f_92127_;
   }

   public OptionInstance<Double> m_232110_() {
      return this.f_92131_;
   }

   public OptionInstance<Double> m_232113_() {
      return this.f_92132_;
   }

   public OptionInstance<Double> m_232116_() {
      return this.f_92133_;
   }

   public OptionInstance<Double> m_232117_() {
      return this.f_92134_;
   }

   public OptionInstance<Double> m_232118_() {
      return this.f_92135_;
   }

   public OptionInstance<Double> m_264038_() {
      return this.f_263718_;
   }

   public OptionInstance<Integer> m_232119_() {
      return this.f_92027_;
   }

   public OptionInstance<AttackIndicatorStatus> m_232120_() {
      return this.f_92029_;
   }

   public OptionInstance<Integer> m_232121_() {
      return this.f_92032_;
   }

   private static double m_231965_(int valueIn) {
      return Math.pow(10.0, (double)valueIn / 100.0);
   }

   private static int m_231839_(double valueIn) {
      return Mth.m_14107_(Math.log10(valueIn) * 100.0);
   }

   public OptionInstance<Double> m_232122_() {
      return this.f_92033_;
   }

   public OptionInstance<Boolean> m_232123_() {
      return this.f_92034_;
   }

   public OptionInstance<Boolean> m_231812_() {
      return this.f_92036_;
   }

   public OptionInstance<Boolean> m_257871_() {
      return this.f_256834_;
   }

   public OptionInstance<Boolean> m_231813_() {
      return this.f_92037_;
   }

   public OptionInstance<Boolean> m_231814_() {
      return this.f_92038_;
   }

   public OptionInstance<Boolean> m_231815_() {
      return this.f_92039_;
   }

   public OptionInstance<Boolean> m_231816_() {
      return this.f_92040_;
   }

   public OptionInstance<Boolean> m_231817_() {
      return this.f_92041_;
   }

   public OptionInstance<Boolean> m_231818_() {
      return this.f_92042_;
   }

   private static void m_320153_() {
      Minecraft minecraft = Minecraft.m_91087_();
      if (minecraft.m_91268_() != null) {
         minecraft.m_323618_();
         minecraft.m_5741_();
      }
   }

   public OptionInstance<Boolean> m_231819_() {
      return this.f_92043_;
   }

   private static boolean m_324081_() {
      return Locale.getDefault().getLanguage().equalsIgnoreCase("ja");
   }

   public OptionInstance<Boolean> m_321442_() {
      return this.f_314642_;
   }

   public OptionInstance<Boolean> m_231820_() {
      return this.f_92044_;
   }

   public OptionInstance<Boolean> m_231821_() {
      return this.f_92045_;
   }

   public OptionInstance<Boolean> m_231822_() {
      return this.f_92046_;
   }

   public OptionInstance<Boolean> m_231823_() {
      return this.f_193762_;
   }

   public OptionInstance<Boolean> m_231824_() {
      return this.f_92047_;
   }

   public final float m_92147_(SoundSource category) {
      return this.m_246669_(category).m_231551_().floatValue();
   }

   public final OptionInstance<Double> m_246669_(SoundSource p_246669_1_) {
      return (OptionInstance<Double>)Objects.requireNonNull((OptionInstance)this.f_244498_.get(p_246669_1_));
   }

   private OptionInstance<Double> m_247249_(String p_247249_1_, SoundSource p_247249_2_) {
      return new OptionInstance<>(
         p_247249_1_,
         OptionInstance.m_231498_(),
         Options::m_324758_,
         OptionInstance.UnitDouble.INSTANCE,
         1.0,
         p_244657_1_ -> Minecraft.m_91087_().m_91106_().m_120358_(p_247249_2_, p_244657_1_.floatValue())
      );
   }

   public OptionInstance<Boolean> m_231825_() {
      return this.f_92049_;
   }

   public OptionInstance<Boolean> m_231826_() {
      return this.f_231807_;
   }

   public OptionInstance<Boolean> m_231827_() {
      return this.f_92050_;
   }

   public OptionInstance<Boolean> m_231828_() {
      return this.f_92051_;
   }

   public OptionInstance<Boolean> m_231829_() {
      return this.f_92052_;
   }

   public OptionInstance<Boolean> m_231830_() {
      return this.f_92080_;
   }

   public OptionInstance<Boolean> m_231831_() {
      return this.f_92081_;
   }

   public OptionInstance<Boolean> m_231832_() {
      return this.f_92082_;
   }

   public OptionInstance<Boolean> m_231833_() {
      return this.f_92084_;
   }

   public OptionInstance<Boolean> m_231834_() {
      return this.f_193763_;
   }

   public OptionInstance<Boolean> m_231836_() {
      return this.f_231798_;
   }

   public OptionInstance<Integer> m_231837_() {
      return this.f_92068_;
   }

   public OptionInstance<Boolean> m_261324_() {
      return this.f_260461_;
   }

   public OptionInstance<Double> m_231924_() {
      return this.f_92069_;
   }

   public OptionInstance<Double> m_231925_() {
      return this.f_92070_;
   }

   public OptionInstance<Double> m_231926_() {
      return this.f_231802_;
   }

   public OptionInstance<Double> m_267805_() {
      return this.f_267458_;
   }

   public OptionInstance<Double> m_267782_() {
      return this.f_267462_;
   }

   public OptionInstance<Double> m_269326_() {
      return this.f_268427_;
   }

   public OptionInstance<Double> m_231927_() {
      return this.f_92071_;
   }

   public OptionInstance<Integer> m_231928_() {
      return this.f_92072_;
   }

   public OptionInstance<ParticleStatus> m_231929_() {
      return this.f_92073_;
   }

   public OptionInstance<NarratorStatus> m_231930_() {
      return this.f_231803_;
   }

   public OptionInstance<String> m_231931_() {
      return this.f_193764_;
   }

   public void m_338485_() {
      this.f_263744_ = false;
      this.m_92169_();
   }

   public Options(Minecraft mcIn, File mcDataDir) {
      this.setForgeKeybindProperties();
      long MB = 1000000L;
      int maxRenderDist = 32;
      if (Runtime.getRuntime().maxMemory() >= 1500L * MB) {
         maxRenderDist = 48;
      }

      if (Runtime.getRuntime().maxMemory() >= 2500L * MB) {
         maxRenderDist = 64;
      }

      this.f_92060_ = mcIn;
      this.f_92110_ = new File(mcDataDir, "options.txt");
      boolean flag = Runtime.getRuntime().maxMemory() >= 1000000000L;
      this.f_92106_ = new OptionInstance<>(
         "options.renderDistance",
         OptionInstance.m_231498_(),
         (p_231915_0_, p_231915_1_) -> m_231921_(p_231915_0_, Component.m_237110_("options.chunks", new Object[]{p_231915_1_})),
         new OptionInstance.IntRange(2, flag ? maxRenderDist : 16, false),
         12,
         p_231950_0_ -> Minecraft.m_91087_().f_91060_.m_109826_()
      );
      this.f_193768_ = new OptionInstance<>(
         "options.simulationDistance",
         OptionInstance.m_231498_(),
         (p_263859_0_, p_263859_1_) -> m_231921_(p_263859_0_, Component.m_237110_("options.chunks", new Object[]{p_263859_1_})),
         new OptionInstance.IntRange(5, flag ? 32 : 16, false),
         12,
         p_268764_0_ -> {
         }
      );
      this.f_92076_ = Util.m_137581_() == Util.OS.WINDOWS;
      this.RENDER_DISTANCE = this.f_92106_;
      this.SIMULATION_DISTANCE = this.f_193768_;
      this.optionsFileOF = new File(mcDataDir, "optionsof.txt");
      this.m_232035_().m_231514_(this.m_232035_().getMaxValue());
      this.ofKeyBindZoom = new KeyMapping("of.key.zoom", 67, "key.categories.misc");
      this.f_92059_ = (KeyMapping[])ArrayUtils.add(this.f_92059_, this.ofKeyBindZoom);
      KeyUtils.fixKeyConflicts(this.f_92059_, new KeyMapping[]{this.ofKeyBindZoom});
      this.f_92106_.m_231514_(8);
      this.m_92140_();
      Config.initGameSettings(this);
   }

   public float m_92141_(float opacityIn) {
      return this.f_92050_.m_231551_() ? opacityIn : this.m_232104_().m_231551_().floatValue();
   }

   public int m_92170_(float opacityIn) {
      return (int)(this.m_92141_(opacityIn) * 255.0F) << 24 & 0xFF000000;
   }

   public int m_92143_(int colorIn) {
      return this.f_92050_.m_231551_() ? colorIn : (int)(this.f_92122_.m_231551_() * 255.0) << 24 & 0xFF000000;
   }

   public void m_92159_(KeyMapping keyBindingIn, Key inputIn) {
      keyBindingIn.m_90848_(inputIn);
      this.m_92169_();
   }

   private void m_323232_(Options.OptionAccess optionAccessIn) {
      optionAccessIn.m_232124_("ao", this.f_92116_);
      optionAccessIn.m_232124_("biomeBlendRadius", this.f_92032_);
      optionAccessIn.m_232124_("enableVsync", this.f_92041_);
      if (this.loadOptions) {
         if (this.m_231817_().m_231551_()) {
            this.f_92113_.m_231514_(this.f_92113_.getMinValue());
         }

         this.updateVSync();
      }

      optionAccessIn.m_232124_("entityDistanceScaling", this.f_92112_);
      optionAccessIn.m_232124_("entityShadows", this.f_92042_);
      optionAccessIn.m_232124_("forceUnicodeFont", this.f_92043_);
      optionAccessIn.m_232124_("japaneseGlyphVariants", this.f_314642_);
      optionAccessIn.m_232124_("fov", this.f_92068_);
      optionAccessIn.m_232124_("fovEffectScale", this.f_92070_);
      optionAccessIn.m_232124_("darknessEffectScale", this.f_231802_);
      optionAccessIn.m_232124_("glintSpeed", this.f_267458_);
      optionAccessIn.m_232124_("glintStrength", this.f_267462_);
      optionAccessIn.m_232124_("prioritizeChunkUpdates", this.f_193769_);
      optionAccessIn.m_232124_("fullscreen", this.f_92052_);
      optionAccessIn.m_232124_("gamma", this.f_92071_);
      optionAccessIn.m_232124_("graphicsMode", this.f_92115_);
      if (this.loadOptions) {
         this.updateRenderClouds();
      }

      optionAccessIn.m_232124_("guiScale", this.f_92072_);
      optionAccessIn.m_232124_("maxFps", this.f_92113_);
      if (this.loadOptions && this.m_231817_().m_231551_()) {
         this.m_232035_().m_231514_(this.m_232035_().getMinValue());
      }

      optionAccessIn.m_232124_("mipmapLevels", this.f_92027_);
      optionAccessIn.m_232124_("narrator", this.f_231803_);
      optionAccessIn.m_232124_("particles", this.f_92073_);
      optionAccessIn.m_232124_("reducedDebugInfo", this.f_92047_);
      optionAccessIn.m_232124_("renderClouds", this.f_231792_);
      optionAccessIn.m_232124_("renderDistance", this.f_92106_);
      optionAccessIn.m_232124_("simulationDistance", this.f_193768_);
      optionAccessIn.m_232124_("screenEffectScale", this.f_92069_);
      optionAccessIn.m_232124_("soundDevice", this.f_193764_);
   }

   private void m_168427_(Options.FieldAccess fieldAccessIn) {
      this.m_323232_(fieldAccessIn);
      fieldAccessIn.m_232124_("autoJump", this.f_92036_);
      fieldAccessIn.m_232124_("operatorItemsTab", this.f_256834_);
      fieldAccessIn.m_232124_("autoSuggestions", this.f_92037_);
      fieldAccessIn.m_232124_("chatColors", this.f_92038_);
      fieldAccessIn.m_232124_("chatLinks", this.f_92039_);
      fieldAccessIn.m_232124_("chatLinksPrompt", this.f_92040_);
      fieldAccessIn.m_232124_("discrete_mouse_scroll", this.f_92045_);
      fieldAccessIn.m_232124_("invertYMouse", this.f_92044_);
      fieldAccessIn.m_232124_("realmsNotifications", this.f_92046_);
      fieldAccessIn.m_232124_("showSubtitles", this.f_92049_);
      fieldAccessIn.m_232124_("directionalAudio", this.f_231807_);
      fieldAccessIn.m_232124_("touchscreen", this.f_92051_);
      fieldAccessIn.m_232124_("bobView", this.f_92080_);
      fieldAccessIn.m_232124_("toggleCrouch", this.f_92081_);
      fieldAccessIn.m_232124_("toggleSprint", this.f_92082_);
      fieldAccessIn.m_232124_("darkMojangStudiosBackground", this.f_168413_);
      fieldAccessIn.m_232124_("hideLightningFlashes", this.f_231791_);
      fieldAccessIn.m_232124_("hideSplashTexts", this.f_302346_);
      fieldAccessIn.m_232124_("mouseSensitivity", this.f_92053_);
      fieldAccessIn.m_232124_("damageTiltStrength", this.f_268427_);
      fieldAccessIn.m_232124_("highContrast", this.f_273910_);
      fieldAccessIn.m_232124_("narratorHotkey", this.f_290977_);
      this.f_92117_ = fieldAccessIn.m_142634_("resourcePacks", this.f_92117_, Options::m_292893_, f_92078_::toJson);
      this.f_92118_ = fieldAccessIn.m_142634_("incompatibleResourcePacks", this.f_92118_, Options::m_292893_, f_92078_::toJson);
      this.f_92066_ = fieldAccessIn.m_141943_("lastServer", this.f_92066_);
      this.f_92075_ = fieldAccessIn.m_141943_("lang", this.f_92075_);
      fieldAccessIn.m_232124_("chatVisibility", this.f_92119_);
      fieldAccessIn.m_232124_("chatOpacity", this.f_92120_);
      fieldAccessIn.m_232124_("chatLineSpacing", this.f_92121_);
      fieldAccessIn.m_232124_("textBackgroundOpacity", this.f_92122_);
      fieldAccessIn.m_232124_("backgroundForChatOnly", this.f_92050_);
      this.f_92124_ = fieldAccessIn.m_142682_("hideServerAddress", this.f_92124_);
      this.f_92125_ = fieldAccessIn.m_142682_("advancedItemTooltips", this.f_92125_);
      this.f_92126_ = fieldAccessIn.m_142682_("pauseOnLostFocus", this.f_92126_);
      this.f_92128_ = fieldAccessIn.m_142708_("overrideWidth", this.f_92128_);
      this.f_92129_ = fieldAccessIn.m_142708_("overrideHeight", this.f_92129_);
      fieldAccessIn.m_232124_("chatHeightFocused", this.f_92134_);
      fieldAccessIn.m_232124_("chatDelay", this.f_92135_);
      fieldAccessIn.m_232124_("chatHeightUnfocused", this.f_92133_);
      fieldAccessIn.m_232124_("chatScale", this.f_92131_);
      fieldAccessIn.m_232124_("chatWidth", this.f_92132_);
      fieldAccessIn.m_232124_("notificationDisplayTime", this.f_263718_);
      this.f_92028_ = fieldAccessIn.m_142682_("useNativeTransport", this.f_92028_);
      fieldAccessIn.m_232124_("mainHand", this.f_92127_);
      fieldAccessIn.m_232124_("attackIndicator", this.f_92029_);
      this.f_92030_ = fieldAccessIn.m_142634_("tutorialStep", this.f_92030_, TutorialSteps::m_120642_, TutorialSteps::m_120639_);
      fieldAccessIn.m_232124_("mouseWheelSensitivity", this.f_92033_);
      fieldAccessIn.m_232124_("rawMouseInput", this.f_92034_);
      this.f_92035_ = fieldAccessIn.m_142708_("glDebugVerbosity", this.f_92035_);
      this.f_92083_ = fieldAccessIn.m_142682_("skipMultiplayerWarning", this.f_92083_);
      fieldAccessIn.m_232124_("hideMatchedNames", this.f_92084_);
      this.f_92031_ = fieldAccessIn.m_142682_("joinedFirstServer", this.f_92031_);
      this.f_168405_ = fieldAccessIn.m_142682_("hideBundleTutorial", this.f_168405_);
      this.f_92076_ = fieldAccessIn.m_142682_("syncChunkWrites", this.f_92076_);
      fieldAccessIn.m_232124_("showAutosaveIndicator", this.f_193763_);
      fieldAccessIn.m_232124_("allowServerListing", this.f_193762_);
      fieldAccessIn.m_232124_("onlyShowSecureChat", this.f_231798_);
      fieldAccessIn.m_232124_("panoramaScrollSpeed", this.f_244402_);
      fieldAccessIn.m_232124_("telemetryOptInExtra", this.f_260461_);
      this.f_263744_ = fieldAccessIn.m_142682_("onboardAccessibility", this.f_263744_);
      fieldAccessIn.m_232124_("menuBackgroundBlurriness", this.f_317010_);
      this.processOptionsForge(fieldAccessIn);
   }

   private void processOptionsForge(Options.FieldAccess fieldAccessIn) {
      for (KeyMapping keymapping : this.f_92059_) {
         String s = keymapping.m_90865_();
         if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
            Object keyModifier = Reflector.call(keymapping, Reflector.ForgeKeyBinding_getKeyModifier);
            Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
            s = keymapping.m_90865_() + (keyModifier != keyModifierNone ? ":" + keyModifier : "");
         }

         String s1 = fieldAccessIn.m_141943_("key_" + keymapping.m_90860_(), s);
         if (!s.equals(s1)) {
            if (Reflector.KeyModifier_valueFromString.exists()) {
               if (s1.indexOf(58) != -1) {
                  String[] pts = s1.split(":");
                  Object keyModifier = Reflector.call(Reflector.KeyModifier_valueFromString, pts[1]);
                  Reflector.call(keymapping, Reflector.ForgeKeyBinding_setKeyModifierAndCode, keyModifier, InputConstants.m_84851_(pts[0]));
               } else {
                  Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
                  Reflector.call(keymapping, Reflector.ForgeKeyBinding_setKeyModifierAndCode, keyModifierNone, InputConstants.m_84851_(s1));
               }
            } else {
               keymapping.m_90848_(InputConstants.m_84851_(s1));
            }
         }
      }

      for (SoundSource soundsource : SoundSource.values()) {
         fieldAccessIn.m_232124_("soundCategory_" + soundsource.m_12676_(), (OptionInstance)this.f_244498_.get(soundsource));
      }

      for (PlayerModelPart playermodelpart : PlayerModelPart.values()) {
         boolean flag = this.f_92108_.contains(playermodelpart);
         boolean flag1 = fieldAccessIn.m_142682_("modelPart_" + playermodelpart.m_36446_(), flag);
         if (flag1 != flag) {
            this.m_92154_(playermodelpart, flag1);
         }
      }
   }

   public void m_92140_() {
      this.load(false);
   }

   public void load(boolean limited) {
      this.loadOptions = true;

      try {
         if (!this.f_92110_.exists()) {
            return;
         }

         CompoundTag compoundtag = new CompoundTag();
         BufferedReader bufferedreader = Files.newReader(this.f_92110_, Charsets.UTF_8);

         try {
            bufferedreader.lines().forEach(lineIn -> {
               try {
                  Iterator<String> iterator = f_92107_.split(lineIn).iterator();
                  compoundtag.m_128359_((String)iterator.next(), (String)iterator.next());
               } catch (Exception var3x) {
                  f_92077_.warn("Skipping bad option: {}", lineIn);
               }
            });
         } catch (Throwable var7) {
            if (bufferedreader != null) {
               try {
                  bufferedreader.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (bufferedreader != null) {
            bufferedreader.close();
         }

         final CompoundTag compoundtag1 = this.m_92164_(compoundtag);
         if (!compoundtag1.m_128441_("graphicsMode") && compoundtag1.m_128441_("fancyGraphics")) {
            if (m_168435_(compoundtag1.m_128461_("fancyGraphics"))) {
               this.f_92115_.m_231514_(GraphicsStatus.FANCY);
            } else {
               this.f_92115_.m_231514_(GraphicsStatus.FAST);
            }
         }

         Consumer<Options.FieldAccess> processor = limited ? this::processOptionsForge : this::m_168427_;
         processor.accept(
            new Options.FieldAccess() {
               @Nullable
               private String m_168458_(String nameIn) {
                  return compoundtag1.m_128441_(nameIn) ? compoundtag1.m_128423_(nameIn).m_7916_() : null;
               }

               @Override
               public <T> void m_232124_(String keyIn, OptionInstance<T> optionIn) {
                  String s = this.m_168458_(keyIn);
                  if (s != null) {
                     JsonReader jsonreader = new JsonReader(new StringReader(s.isEmpty() ? "\"\"" : s));
                     JsonElement jsonelement = JsonParser.parseReader(jsonreader);
                     DataResult<T> dataresult = optionIn.m_231554_().parse(JsonOps.INSTANCE, jsonelement);
                     dataresult.error()
                        .ifPresent(errorIn -> Options.f_92077_.error("Error parsing option value " + s + " for option " + optionIn + ": " + errorIn.message()));
                     dataresult.ifSuccess(optionIn::m_231514_);
                  }
               }

               @Override
               public int m_142708_(String keyIn, int valueIn) {
                  String s = this.m_168458_(keyIn);
                  if (s != null) {
                     try {
                        return Integer.parseInt(s);
                     } catch (NumberFormatException var5) {
                        Options.f_92077_.warn("Invalid integer value for option {} = {}", new Object[]{keyIn, s, var5});
                     }
                  }

                  return valueIn;
               }

               @Override
               public boolean m_142682_(String keyIn, boolean valueIn) {
                  String s = this.m_168458_(keyIn);
                  return s != null ? Options.m_168435_(s) : valueIn;
               }

               @Override
               public String m_141943_(String keyIn, String valueIn) {
                  return (String)MoreObjects.firstNonNull(this.m_168458_(keyIn), valueIn);
               }

               @Override
               public float m_142432_(String keyIn, float valueIn) {
                  String s = this.m_168458_(keyIn);
                  if (s == null) {
                     return valueIn;
                  } else if (Options.m_168435_(s)) {
                     return 1.0F;
                  } else if (Options.m_168440_(s)) {
                     return 0.0F;
                  } else {
                     try {
                        return Float.parseFloat(s);
                     } catch (NumberFormatException var5) {
                        Options.f_92077_.warn("Invalid floating point value for option {} = {}", new Object[]{keyIn, s, var5});
                        return valueIn;
                     }
                  }
               }

               @Override
               public <T> T m_142634_(String keyIn, T valueIn, Function<String, T> readerIn, Function<T, String> writerIn) {
                  String s = this.m_168458_(keyIn);
                  return (T)(s == null ? valueIn : readerIn.apply(s));
               }
            }
         );
         if (compoundtag1.m_128441_("fullscreenResolution")) {
            this.f_92123_ = compoundtag1.m_128461_("fullscreenResolution");
         }

         if (this.f_92060_.m_91268_() != null) {
            this.f_92060_.m_91268_().m_85380_(this.f_92113_.m_231551_());
         }

         KeyMapping.m_90854_();
      } catch (Exception var8) {
         f_92077_.error("Failed to load options", var8);
      }

      this.loadOptions = false;
      this.loadOfOptions();
   }

   static boolean m_168435_(String textIn) {
      return "true".equals(textIn);
   }

   static boolean m_168440_(String textIn) {
      return "false".equals(textIn);
   }

   private CompoundTag m_92164_(CompoundTag nbt) {
      int i = 0;

      try {
         i = Integer.parseInt(nbt.m_128461_("version"));
      } catch (RuntimeException var4) {
      }

      return DataFixTypes.OPTIONS.m_264218_(this.f_92060_.m_91295_(), nbt, i);
   }

   public void m_92169_() {
      this.saveOptions = true;
      if (!Reflector.ClientModLoader_isLoading.exists() || !Reflector.callBoolean(Reflector.ClientModLoader_isLoading)) {
         try {
            final PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.f_92110_), StandardCharsets.UTF_8));

            try {
               printwriter.println("version:" + SharedConstants.m_183709_().m_183476_().m_193006_());
               this.m_168427_(
                  new Options.FieldAccess() {
                     public void m_168490_(String prefixIn) {
                        printwriter.print(prefixIn);
                        printwriter.print(':');
                     }

                     @Override
                     public <T> void m_232124_(String keyIn, OptionInstance<T> optionIn) {
                        optionIn.m_231554_()
                           .encodeStart(JsonOps.INSTANCE, optionIn.m_231551_())
                           .ifError(errorIn -> Options.f_92077_.error("Error saving option " + optionIn + ": " + errorIn))
                           .ifSuccess(jsonElemIn -> {
                              this.m_168490_(keyIn);
                              printwriter.println(Options.f_92078_.toJson(jsonElemIn));
                           });
                     }

                     @Override
                     public int m_142708_(String keyIn, int valueIn) {
                        this.m_168490_(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public boolean m_142682_(String keyIn, boolean valueIn) {
                        this.m_168490_(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public String m_141943_(String keyIn, String valueIn) {
                        this.m_168490_(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public float m_142432_(String keyIn, float valueIn) {
                        this.m_168490_(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public <T> T m_142634_(String keyIn, T valueIn, Function<String, T> readerIn, Function<T, String> writerIn) {
                        this.m_168490_(keyIn);
                        printwriter.println((String)writerIn.apply(valueIn));
                        return valueIn;
                     }
                  }
               );
               if (this.f_92060_.m_91268_().m_85436_().isPresent()) {
                  printwriter.println("fullscreenResolution:" + ((VideoMode)this.f_92060_.m_91268_().m_85436_().get()).m_85342_());
               }
            } catch (Throwable var5) {
               try {
                  printwriter.close();
               } catch (Throwable var4) {
                  var5.addSuppressed(var4);
               }

               throw var5;
            }

            printwriter.close();
         } catch (Exception var6) {
            f_92077_.error("Failed to save options", var6);
         }

         this.saveOptions = false;
         this.saveOfOptions();
         this.m_92172_();
      }
   }

   public ClientInformation m_294596_() {
      int i = 0;

      for (PlayerModelPart playermodelpart : this.f_92108_) {
         i |= playermodelpart.m_36445_();
      }

      return new ClientInformation(
         this.f_92075_,
         this.f_92106_.m_231551_(),
         this.f_92119_.m_231551_(),
         this.f_92038_.m_231551_(),
         i,
         this.f_92127_.m_231551_(),
         this.f_92060_.m_167974_(),
         this.f_193762_.m_231551_()
      );
   }

   public void m_92172_() {
      if (this.f_92060_.f_91074_ != null) {
         this.f_92060_.f_91074_.f_108617_.m_295327_(new ServerboundClientInformationPacket(this.m_294596_()));
      }
   }

   private void m_92154_(PlayerModelPart modelPart, boolean enable) {
      if (enable) {
         this.f_92108_.add(modelPart);
      } else {
         this.f_92108_.remove(modelPart);
      }
   }

   public boolean m_168416_(PlayerModelPart partIn) {
      return this.f_92108_.contains(partIn);
   }

   public void m_168418_(PlayerModelPart partIn, boolean enabledIn) {
      this.m_92154_(partIn, enabledIn);
      this.m_92172_();
   }

   public CloudStatus m_92174_() {
      return this.m_193772_() >= 4 ? this.f_231792_.m_231551_() : CloudStatus.OFF;
   }

   public boolean m_92175_() {
      return this.f_92028_;
   }

   public void setOptionFloatValueOF(OptionInstance option, double val) {
      if (option == Option.CLOUD_HEIGHT) {
         this.ofCloudsHeight = val;
      }

      if (option == Option.AO_LEVEL) {
         this.ofAoLevel = val;
         this.f_92060_.f_91060_.m_109818_();
      }

      if (option == Option.AA_LEVEL) {
         int valInt = (int)val;
         if (valInt > 0 && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
            return;
         }

         if (valInt > 0 && Config.isGraphicsFabulous()) {
            Config.showGuiMessage(Lang.get("of.message.aa.gf1"), Lang.get("of.message.aa.gf2"));
            return;
         }

         this.ofAaLevel = valInt;
         this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
      }

      if (option == Option.AF_LEVEL) {
         int valIntx = (int)val;
         this.ofAfLevel = valIntx;
         this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
         this.f_92060_.m_91088_();
         Shaders.uninit();
      }

      if (option == Option.MIPMAP_TYPE) {
         int valIntx = (int)val;
         this.ofMipmapType = Config.limit(valIntx, 0, 3);
         this.updateMipmaps();
      }
   }

   public double getOptionFloatValueOF(OptionInstance settingOption) {
      if (settingOption == Option.CLOUD_HEIGHT) {
         return this.ofCloudsHeight;
      } else if (settingOption == Option.AO_LEVEL) {
         return this.ofAoLevel;
      } else if (settingOption == Option.AA_LEVEL) {
         return (double)this.ofAaLevel;
      } else if (settingOption == Option.AF_LEVEL) {
         return (double)this.ofAfLevel;
      } else {
         return settingOption == Option.MIPMAP_TYPE ? (double)this.ofMipmapType : Float.MAX_VALUE;
      }
   }

   public void setOptionValueOF(OptionInstance par1EnumOptions, int par2) {
      if (par1EnumOptions == Option.FOG_FANCY) {
         switch (this.ofFogType) {
            case 2:
               this.ofFogType = 3;
               break;
            default:
               this.ofFogType = 2;
         }
      }

      if (par1EnumOptions == Option.FOG_START) {
         this.ofFogStart += 0.2F;
         if (this.ofFogStart > 0.81F) {
            this.ofFogStart = 0.2F;
         }
      }

      if (par1EnumOptions == Option.SMOOTH_FPS) {
         this.ofSmoothFps = !this.ofSmoothFps;
      }

      if (par1EnumOptions == Option.SMOOTH_WORLD) {
         this.ofSmoothWorld = !this.ofSmoothWorld;
         Config.updateThreadPriorities();
      }

      if (par1EnumOptions == Option.CLOUDS) {
         this.ofClouds++;
         if (this.ofClouds > 3) {
            this.ofClouds = 0;
         }

         this.updateRenderClouds();
      }

      if (par1EnumOptions == Option.TREES) {
         this.ofTrees = nextValue(this.ofTrees, OF_TREES_VALUES);
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.RAIN) {
         this.ofRain++;
         if (this.ofRain > 3) {
            this.ofRain = 0;
         }
      }

      if (par1EnumOptions == Option.ANIMATED_WATER) {
         this.ofAnimatedWater++;
         if (this.ofAnimatedWater == 1) {
            this.ofAnimatedWater++;
         }

         if (this.ofAnimatedWater > 2) {
            this.ofAnimatedWater = 0;
         }
      }

      if (par1EnumOptions == Option.ANIMATED_LAVA) {
         this.ofAnimatedLava++;
         if (this.ofAnimatedLava == 1) {
            this.ofAnimatedLava++;
         }

         if (this.ofAnimatedLava > 2) {
            this.ofAnimatedLava = 0;
         }
      }

      if (par1EnumOptions == Option.ANIMATED_FIRE) {
         this.ofAnimatedFire = !this.ofAnimatedFire;
      }

      if (par1EnumOptions == Option.ANIMATED_PORTAL) {
         this.ofAnimatedPortal = !this.ofAnimatedPortal;
      }

      if (par1EnumOptions == Option.ANIMATED_REDSTONE) {
         this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
      }

      if (par1EnumOptions == Option.ANIMATED_EXPLOSION) {
         this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
      }

      if (par1EnumOptions == Option.ANIMATED_FLAME) {
         this.ofAnimatedFlame = !this.ofAnimatedFlame;
      }

      if (par1EnumOptions == Option.ANIMATED_SMOKE) {
         this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
      }

      if (par1EnumOptions == Option.VOID_PARTICLES) {
         this.ofVoidParticles = !this.ofVoidParticles;
      }

      if (par1EnumOptions == Option.WATER_PARTICLES) {
         this.ofWaterParticles = !this.ofWaterParticles;
      }

      if (par1EnumOptions == Option.PORTAL_PARTICLES) {
         this.ofPortalParticles = !this.ofPortalParticles;
      }

      if (par1EnumOptions == Option.POTION_PARTICLES) {
         this.ofPotionParticles = !this.ofPotionParticles;
      }

      if (par1EnumOptions == Option.FIREWORK_PARTICLES) {
         this.ofFireworkParticles = !this.ofFireworkParticles;
      }

      if (par1EnumOptions == Option.DRIPPING_WATER_LAVA) {
         this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
      }

      if (par1EnumOptions == Option.ANIMATED_TERRAIN) {
         this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
      }

      if (par1EnumOptions == Option.ANIMATED_TEXTURES) {
         this.ofAnimatedTextures = !this.ofAnimatedTextures;
      }

      if (par1EnumOptions == Option.RAIN_SPLASH) {
         this.ofRainSplash = !this.ofRainSplash;
      }

      if (par1EnumOptions == Option.LAGOMETER) {
         this.ofLagometer = !this.ofLagometer;
         if (this.f_92060_.m_293199_().f_291101_ && this.f_92060_.m_293199_().f_290551_ != this.ofLagometer) {
            this.f_92060_.m_293199_().m_294611_();
         }
      }

      if (par1EnumOptions == Option.AUTOSAVE_TICKS) {
         int step = 900;
         this.ofAutoSaveTicks = Math.max(this.ofAutoSaveTicks / step * step, step);
         this.ofAutoSaveTicks *= 2;
         if (this.ofAutoSaveTicks > 32 * step) {
            this.ofAutoSaveTicks = step;
         }
      }

      if (par1EnumOptions == Option.BETTER_GRASS) {
         this.ofBetterGrass++;
         if (this.ofBetterGrass > 3) {
            this.ofBetterGrass = 1;
         }

         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.CONNECTED_TEXTURES) {
         this.ofConnectedTextures++;
         if (this.ofConnectedTextures > 3) {
            this.ofConnectedTextures = 1;
         }

         if (this.ofConnectedTextures == 2) {
            this.f_92060_.f_91060_.m_109818_();
         } else {
            this.f_92060_.m_91088_();
         }
      }

      if (par1EnumOptions == Option.WEATHER) {
         this.ofWeather = !this.ofWeather;
      }

      if (par1EnumOptions == Option.SKY) {
         this.ofSky = !this.ofSky;
      }

      if (par1EnumOptions == Option.STARS) {
         this.ofStars = !this.ofStars;
      }

      if (par1EnumOptions == Option.SUN_MOON) {
         this.ofSunMoon = !this.ofSunMoon;
      }

      if (par1EnumOptions == Option.VIGNETTE) {
         this.ofVignette++;
         if (this.ofVignette > 2) {
            this.ofVignette = 0;
         }
      }

      if (par1EnumOptions == Option.CHUNK_UPDATES) {
         this.ofChunkUpdates++;
         if (this.ofChunkUpdates > 5) {
            this.ofChunkUpdates = 1;
         }
      }

      if (par1EnumOptions == Option.CHUNK_UPDATES_DYNAMIC) {
         this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
      }

      if (par1EnumOptions == Option.TIME) {
         this.ofTime++;
         if (this.ofTime > 2) {
            this.ofTime = 0;
         }
      }

      if (par1EnumOptions == Option.PROFILER) {
         this.ofProfiler = !this.ofProfiler;
         if (this.f_92060_.m_293199_().f_291101_ && this.f_92060_.m_293199_().f_291871_ != this.ofProfiler) {
            this.f_92060_.m_293199_().m_293481_();
         }
      }

      if (par1EnumOptions == Option.BETTER_SNOW) {
         this.ofBetterSnow = !this.ofBetterSnow;
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.SWAMP_COLORS) {
         this.ofSwampColors = !this.ofSwampColors;
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.RANDOM_ENTITIES) {
         this.ofRandomEntities = !this.ofRandomEntities;
         this.f_92060_.m_91088_();
      }

      if (par1EnumOptions == Option.CUSTOM_FONTS) {
         this.ofCustomFonts = !this.ofCustomFonts;
         FontUtils.reloadFonts();
      }

      if (par1EnumOptions == Option.CUSTOM_COLORS) {
         this.ofCustomColors = !this.ofCustomColors;
         CustomColors.update();
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.CUSTOM_ITEMS) {
         this.ofCustomItems = !this.ofCustomItems;
         this.f_92060_.m_91088_();
      }

      if (par1EnumOptions == Option.CUSTOM_SKY) {
         this.ofCustomSky = !this.ofCustomSky;
         CustomSky.update();
      }

      if (par1EnumOptions == Option.SHOW_CAPES) {
         this.ofShowCapes = !this.ofShowCapes;
      }

      if (par1EnumOptions == Option.NATURAL_TEXTURES) {
         this.ofNaturalTextures = !this.ofNaturalTextures;
         NaturalTextures.update();
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.EMISSIVE_TEXTURES) {
         this.ofEmissiveTextures = !this.ofEmissiveTextures;
         this.f_92060_.m_91088_();
      }

      if (par1EnumOptions == Option.FAST_MATH) {
         this.ofFastMath = !this.ofFastMath;
         Mth.fastMath = this.ofFastMath;
      }

      if (par1EnumOptions == Option.FAST_RENDER) {
         this.ofFastRender = !this.ofFastRender;
      }

      if (par1EnumOptions == Option.LAZY_CHUNK_LOADING) {
         this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
      }

      if (par1EnumOptions == Option.RENDER_REGIONS) {
         this.ofRenderRegions = !this.ofRenderRegions;
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.SMART_ANIMATIONS) {
         this.ofSmartAnimations = !this.ofSmartAnimations;
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.DYNAMIC_FOV) {
         this.ofDynamicFov = !this.ofDynamicFov;
      }

      if (par1EnumOptions == Option.ALTERNATE_BLOCKS) {
         this.ofAlternateBlocks = !this.ofAlternateBlocks;
         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.DYNAMIC_LIGHTS) {
         this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         DynamicLights.removeLights(this.f_92060_.f_91060_);
      }

      if (par1EnumOptions == Option.SCREENSHOT_SIZE) {
         this.ofScreenshotSize++;
         if (this.ofScreenshotSize > 4) {
            this.ofScreenshotSize = 1;
         }
      }

      if (par1EnumOptions == Option.CUSTOM_ENTITY_MODELS) {
         this.ofCustomEntityModels = !this.ofCustomEntityModels;
         this.f_92060_.m_91088_();
      }

      if (par1EnumOptions == Option.CUSTOM_GUIS) {
         this.ofCustomGuis = !this.ofCustomGuis;
         CustomGuis.update();
      }

      if (par1EnumOptions == Option.SHOW_GL_ERRORS) {
         this.ofShowGlErrors = !this.ofShowGlErrors;
      }

      if (par1EnumOptions == Option.HELD_ITEM_TOOLTIPS) {
         this.ofHeldItemTooltips = !this.ofHeldItemTooltips;
      }

      if (par1EnumOptions == Option.ADVANCED_TOOLTIPS) {
         this.f_92125_ = !this.f_92125_;
      }

      if (par1EnumOptions == Option.CHAT_BACKGROUND) {
         if (this.ofChatBackground == 0) {
            this.ofChatBackground = 5;
         } else if (this.ofChatBackground == 5) {
            this.ofChatBackground = 3;
         } else {
            this.ofChatBackground = 0;
         }
      }

      if (par1EnumOptions == Option.CHAT_SHADOW) {
         this.ofChatShadow = !this.ofChatShadow;
      }

      if (par1EnumOptions == Option.TELEMETRY) {
         this.ofTelemetry = nextValue(this.ofTelemetry, OF_TELEMETRY);
      }
   }

   public Component getKeyComponentOF(OptionInstance option) {
      String str = this.getKeyBindingOF(option);
      if (str == null) {
         return null;
      } else {
         Component comp = Component.m_237113_(str);
         return comp;
      }
   }

   public String getKeyBindingOF(OptionInstance par1EnumOptions) {
      String var2 = I18n.m_118938_(par1EnumOptions.getResourceKey(), new Object[0]) + ": ";
      if (var2 == null) {
         var2 = par1EnumOptions.getResourceKey();
      }

      if (par1EnumOptions == this.RENDER_DISTANCE) {
         int distChunks = this.m_231984_().m_231551_();
         String str = I18n.m_118938_("of.options.renderDistance.tiny", new Object[0]);
         int baseDist = 2;
         if (distChunks >= 4) {
            str = I18n.m_118938_("of.options.renderDistance.short", new Object[0]);
            baseDist = 4;
         }

         if (distChunks >= 8) {
            str = I18n.m_118938_("of.options.renderDistance.normal", new Object[0]);
            baseDist = 8;
         }

         if (distChunks >= 16) {
            str = I18n.m_118938_("of.options.renderDistance.far", new Object[0]);
            baseDist = 16;
         }

         if (distChunks >= 32) {
            str = Lang.get("of.options.renderDistance.extreme");
            baseDist = 32;
         }

         if (distChunks >= 48) {
            str = Lang.get("of.options.renderDistance.insane");
            baseDist = 48;
         }

         if (distChunks >= 64) {
            str = Lang.get("of.options.renderDistance.ludicrous");
            baseDist = 64;
         }

         int diff = this.m_231984_().m_231551_() - baseDist;
         String descr = str;
         if (diff > 0) {
            descr = str + "+";
         }

         return var2 + distChunks + " " + descr;
      } else if (par1EnumOptions == Option.FOG_FANCY) {
         switch (this.ofFogType) {
            case 1:
               return var2 + Lang.getFast();
            case 2:
               return var2 + Lang.getFancy();
            case 3:
               return var2 + Lang.getOff();
            default:
               return var2 + Lang.getOff();
         }
      } else if (par1EnumOptions == Option.FOG_START) {
         return var2 + this.ofFogStart;
      } else if (par1EnumOptions == Option.MIPMAP_TYPE) {
         return FloatOptions.getText(par1EnumOptions, (double)this.ofMipmapType);
      } else if (par1EnumOptions == Option.SMOOTH_FPS) {
         return this.ofSmoothFps ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.SMOOTH_WORLD) {
         return this.ofSmoothWorld ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.CLOUDS) {
         switch (this.ofClouds) {
            case 1:
               return var2 + Lang.getFast();
            case 2:
               return var2 + Lang.getFancy();
            case 3:
               return var2 + Lang.getOff();
            default:
               return var2 + Lang.getDefault();
         }
      } else if (par1EnumOptions == Option.TREES) {
         switch (this.ofTrees) {
            case 1:
               return var2 + Lang.getFast();
            case 2:
               return var2 + Lang.getFancy();
            case 3:
            default:
               return var2 + Lang.getDefault();
            case 4:
               return var2 + Lang.get("of.general.smart");
         }
      } else if (par1EnumOptions == Option.RAIN) {
         switch (this.ofRain) {
            case 1:
               return var2 + Lang.getFast();
            case 2:
               return var2 + Lang.getFancy();
            case 3:
               return var2 + Lang.getOff();
            default:
               return var2 + Lang.getDefault();
         }
      } else if (par1EnumOptions == Option.ANIMATED_WATER) {
         switch (this.ofAnimatedWater) {
            case 1:
               return var2 + Lang.get("of.options.animation.dynamic");
            case 2:
               return var2 + Lang.getOff();
            default:
               return var2 + Lang.getOn();
         }
      } else if (par1EnumOptions == Option.ANIMATED_LAVA) {
         switch (this.ofAnimatedLava) {
            case 1:
               return var2 + Lang.get("of.options.animation.dynamic");
            case 2:
               return var2 + Lang.getOff();
            default:
               return var2 + Lang.getOn();
         }
      } else if (par1EnumOptions == Option.ANIMATED_FIRE) {
         return this.ofAnimatedFire ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ANIMATED_PORTAL) {
         return this.ofAnimatedPortal ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ANIMATED_REDSTONE) {
         return this.ofAnimatedRedstone ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ANIMATED_EXPLOSION) {
         return this.ofAnimatedExplosion ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ANIMATED_FLAME) {
         return this.ofAnimatedFlame ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ANIMATED_SMOKE) {
         return this.ofAnimatedSmoke ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.VOID_PARTICLES) {
         return this.ofVoidParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.WATER_PARTICLES) {
         return this.ofWaterParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.PORTAL_PARTICLES) {
         return this.ofPortalParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.POTION_PARTICLES) {
         return this.ofPotionParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.FIREWORK_PARTICLES) {
         return this.ofFireworkParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.DRIPPING_WATER_LAVA) {
         return this.ofDrippingWaterLava ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ANIMATED_TERRAIN) {
         return this.ofAnimatedTerrain ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ANIMATED_TEXTURES) {
         return this.ofAnimatedTextures ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.RAIN_SPLASH) {
         return this.ofRainSplash ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.LAGOMETER) {
         return this.ofLagometer ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.AUTOSAVE_TICKS) {
         int step = 900;
         if (this.ofAutoSaveTicks <= step) {
            return var2 + Lang.get("of.options.save.45s");
         } else if (this.ofAutoSaveTicks <= 2 * step) {
            return var2 + Lang.get("of.options.save.90s");
         } else if (this.ofAutoSaveTicks <= 4 * step) {
            return var2 + Lang.get("of.options.save.3min");
         } else if (this.ofAutoSaveTicks <= 8 * step) {
            return var2 + Lang.get("of.options.save.6min");
         } else {
            return this.ofAutoSaveTicks <= 16 * step ? var2 + Lang.get("of.options.save.12min") : var2 + Lang.get("of.options.save.24min");
         }
      } else if (par1EnumOptions == Option.BETTER_GRASS) {
         switch (this.ofBetterGrass) {
            case 1:
               return var2 + Lang.getFast();
            case 2:
               return var2 + Lang.getFancy();
            default:
               return var2 + Lang.getOff();
         }
      } else if (par1EnumOptions == Option.CONNECTED_TEXTURES) {
         switch (this.ofConnectedTextures) {
            case 1:
               return var2 + Lang.getFast();
            case 2:
               return var2 + Lang.getFancy();
            default:
               return var2 + Lang.getOff();
         }
      } else if (par1EnumOptions == Option.WEATHER) {
         return this.ofWeather ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.SKY) {
         return this.ofSky ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.STARS) {
         return this.ofStars ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.SUN_MOON) {
         return this.ofSunMoon ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.VIGNETTE) {
         switch (this.ofVignette) {
            case 1:
               return var2 + Lang.getFast();
            case 2:
               return var2 + Lang.getFancy();
            default:
               return var2 + Lang.getDefault();
         }
      } else if (par1EnumOptions == Option.CHUNK_UPDATES) {
         return var2 + this.ofChunkUpdates;
      } else if (par1EnumOptions == Option.CHUNK_UPDATES_DYNAMIC) {
         return this.ofChunkUpdatesDynamic ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.TIME) {
         if (this.ofTime == 1) {
            return var2 + Lang.get("of.options.time.dayOnly");
         } else {
            return this.ofTime == 2 ? var2 + Lang.get("of.options.time.nightOnly") : var2 + Lang.getDefault();
         }
      } else if (par1EnumOptions == Option.AA_LEVEL) {
         return FloatOptions.getText(par1EnumOptions, (double)this.ofAaLevel);
      } else if (par1EnumOptions == Option.AF_LEVEL) {
         return FloatOptions.getText(par1EnumOptions, (double)this.ofAfLevel);
      } else if (par1EnumOptions == Option.PROFILER) {
         return this.ofProfiler ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.BETTER_SNOW) {
         return this.ofBetterSnow ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.SWAMP_COLORS) {
         return this.ofSwampColors ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.RANDOM_ENTITIES) {
         return this.ofRandomEntities ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.CUSTOM_FONTS) {
         return this.ofCustomFonts ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.CUSTOM_COLORS) {
         return this.ofCustomColors ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.CUSTOM_SKY) {
         return this.ofCustomSky ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.SHOW_CAPES) {
         return this.ofShowCapes ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.CUSTOM_ITEMS) {
         return this.ofCustomItems ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.NATURAL_TEXTURES) {
         return this.ofNaturalTextures ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.EMISSIVE_TEXTURES) {
         return this.ofEmissiveTextures ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.FAST_MATH) {
         return this.ofFastMath ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.FAST_RENDER) {
         return this.ofFastRender ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.LAZY_CHUNK_LOADING) {
         return this.ofLazyChunkLoading ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.RENDER_REGIONS) {
         return this.ofRenderRegions ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.SMART_ANIMATIONS) {
         return this.ofSmartAnimations ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.DYNAMIC_FOV) {
         return this.ofDynamicFov ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ALTERNATE_BLOCKS) {
         return this.ofAlternateBlocks ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.DYNAMIC_LIGHTS) {
         int index = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         return var2 + getTranslation(KEYS_DYNAMIC_LIGHTS, index);
      } else if (par1EnumOptions == Option.SCREENSHOT_SIZE) {
         return this.ofScreenshotSize <= 1 ? var2 + Lang.getDefault() : var2 + this.ofScreenshotSize + "x";
      } else if (par1EnumOptions == Option.CUSTOM_ENTITY_MODELS) {
         return this.ofCustomEntityModels ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.CUSTOM_GUIS) {
         return this.ofCustomGuis ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.SHOW_GL_ERRORS) {
         return this.ofShowGlErrors ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.HELD_ITEM_TOOLTIPS) {
         return this.ofHeldItemTooltips ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.ADVANCED_TOOLTIPS) {
         return this.f_92125_ ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.CHAT_BACKGROUND) {
         if (this.ofChatBackground == 3) {
            return var2 + Lang.getOff();
         } else {
            return this.ofChatBackground == 5 ? var2 + Lang.get("of.general.compact") : var2 + Lang.getDefault();
         }
      } else if (par1EnumOptions == Option.CHAT_SHADOW) {
         return this.ofChatShadow ? var2 + Lang.getOn() : var2 + Lang.getOff();
      } else if (par1EnumOptions == Option.TELEMETRY) {
         int index = indexOf(this.ofTelemetry, OF_TELEMETRY);
         return var2 + getTranslation(KEYS_TELEMETRY, index);
      } else if (par1EnumOptions.isProgressOption()) {
         double d0 = (Double)par1EnumOptions.m_231551_();
         return d0 == 0.0 ? var2 + I18n.m_118938_("options.off", new Object[0]) : var2 + (int)(d0 * 100.0) + "%";
      } else {
         return null;
      }
   }

   public void loadOfOptions() {
      try {
         File ofReadFile = this.optionsFileOF;
         if (!ofReadFile.exists()) {
            ofReadFile = this.f_92110_;
         }

         if (!ofReadFile.exists()) {
            return;
         }

         List<IPersitentOption> persistentOptions = this.getPersistentOptions();
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(ofReadFile), StandardCharsets.UTF_8));
         String s = "";

         while ((s = bufferedreader.readLine()) != null) {
            try {
               String[] as = s.split(":");
               if (as[0].equals("ofRenderDistanceChunks") && as.length >= 2) {
                  this.f_92106_.m_231514_(Integer.valueOf(as[1]));
                  this.f_92106_.m_231514_(Config.limit(this.f_92106_.m_231551_(), 2, 1024));
               }

               if (as[0].equals("ofFogType") && as.length >= 2) {
                  this.ofFogType = Integer.valueOf(as[1]);
                  this.ofFogType = Config.limit(this.ofFogType, 2, 3);
               }

               if (as[0].equals("ofFogStart") && as.length >= 2) {
                  this.ofFogStart = Float.valueOf(as[1]);
                  if (this.ofFogStart < 0.2F) {
                     this.ofFogStart = 0.2F;
                  }

                  if (this.ofFogStart > 0.81F) {
                     this.ofFogStart = 0.8F;
                  }
               }

               if (as[0].equals("ofMipmapType") && as.length >= 2) {
                  this.ofMipmapType = Integer.valueOf(as[1]);
                  this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
               }

               if (as[0].equals("ofOcclusionFancy") && as.length >= 2) {
                  this.ofOcclusionFancy = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofSmoothFps") && as.length >= 2) {
                  this.ofSmoothFps = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofSmoothWorld") && as.length >= 2) {
                  this.ofSmoothWorld = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAoLevel") && as.length >= 2) {
                  this.ofAoLevel = (double)Float.valueOf(as[1]).floatValue();
                  this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0, 1.0);
               }

               if (as[0].equals("ofClouds") && as.length >= 2) {
                  this.ofClouds = Integer.valueOf(as[1]);
                  this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                  this.updateRenderClouds();
               }

               if (as[0].equals("ofCloudsHeight") && as.length >= 2) {
                  this.ofCloudsHeight = (double)Float.valueOf(as[1]).floatValue();
                  this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0, 1.0);
               }

               if (as[0].equals("ofTrees") && as.length >= 2) {
                  this.ofTrees = Integer.valueOf(as[1]);
                  this.ofTrees = limit(this.ofTrees, OF_TREES_VALUES);
               }

               if (as[0].equals("ofRain") && as.length >= 2) {
                  this.ofRain = Integer.valueOf(as[1]);
                  this.ofRain = Config.limit(this.ofRain, 0, 3);
               }

               if (as[0].equals("ofAnimatedWater") && as.length >= 2) {
                  this.ofAnimatedWater = Integer.valueOf(as[1]);
                  this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
               }

               if (as[0].equals("ofAnimatedLava") && as.length >= 2) {
                  this.ofAnimatedLava = Integer.valueOf(as[1]);
                  this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
               }

               if (as[0].equals("ofAnimatedFire") && as.length >= 2) {
                  this.ofAnimatedFire = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAnimatedPortal") && as.length >= 2) {
                  this.ofAnimatedPortal = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAnimatedRedstone") && as.length >= 2) {
                  this.ofAnimatedRedstone = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAnimatedExplosion") && as.length >= 2) {
                  this.ofAnimatedExplosion = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAnimatedFlame") && as.length >= 2) {
                  this.ofAnimatedFlame = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAnimatedSmoke") && as.length >= 2) {
                  this.ofAnimatedSmoke = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofVoidParticles") && as.length >= 2) {
                  this.ofVoidParticles = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofWaterParticles") && as.length >= 2) {
                  this.ofWaterParticles = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofPortalParticles") && as.length >= 2) {
                  this.ofPortalParticles = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofPotionParticles") && as.length >= 2) {
                  this.ofPotionParticles = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofFireworkParticles") && as.length >= 2) {
                  this.ofFireworkParticles = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofDrippingWaterLava") && as.length >= 2) {
                  this.ofDrippingWaterLava = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAnimatedTerrain") && as.length >= 2) {
                  this.ofAnimatedTerrain = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAnimatedTextures") && as.length >= 2) {
                  this.ofAnimatedTextures = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofRainSplash") && as.length >= 2) {
                  this.ofRainSplash = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofLagometer") && as.length >= 2) {
                  this.ofLagometer = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAutoSaveTicks") && as.length >= 2) {
                  this.ofAutoSaveTicks = Integer.valueOf(as[1]);
                  this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
               }

               if (as[0].equals("ofBetterGrass") && as.length >= 2) {
                  this.ofBetterGrass = Integer.valueOf(as[1]);
                  this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
               }

               if (as[0].equals("ofConnectedTextures") && as.length >= 2) {
                  this.ofConnectedTextures = Integer.valueOf(as[1]);
                  this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
               }

               if (as[0].equals("ofWeather") && as.length >= 2) {
                  this.ofWeather = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofSky") && as.length >= 2) {
                  this.ofSky = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofStars") && as.length >= 2) {
                  this.ofStars = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofSunMoon") && as.length >= 2) {
                  this.ofSunMoon = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofVignette") && as.length >= 2) {
                  this.ofVignette = Integer.valueOf(as[1]);
                  this.ofVignette = Config.limit(this.ofVignette, 0, 2);
               }

               if (as[0].equals("ofChunkUpdates") && as.length >= 2) {
                  this.ofChunkUpdates = Integer.valueOf(as[1]);
                  this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
               }

               if (as[0].equals("ofChunkUpdatesDynamic") && as.length >= 2) {
                  this.ofChunkUpdatesDynamic = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofTime") && as.length >= 2) {
                  this.ofTime = Integer.valueOf(as[1]);
                  this.ofTime = Config.limit(this.ofTime, 0, 2);
               }

               if (as[0].equals("ofAaLevel") && as.length >= 2) {
                  this.ofAaLevel = Integer.valueOf(as[1]);
                  this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
               }

               if (as[0].equals("ofAfLevel") && as.length >= 2) {
                  this.ofAfLevel = Integer.valueOf(as[1]);
                  this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
               }

               if (as[0].equals("ofProfiler") && as.length >= 2) {
                  this.ofProfiler = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofBetterSnow") && as.length >= 2) {
                  this.ofBetterSnow = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofSwampColors") && as.length >= 2) {
                  this.ofSwampColors = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofRandomEntities") && as.length >= 2) {
                  this.ofRandomEntities = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofCustomFonts") && as.length >= 2) {
                  this.ofCustomFonts = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofCustomColors") && as.length >= 2) {
                  this.ofCustomColors = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofCustomItems") && as.length >= 2) {
                  this.ofCustomItems = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofCustomSky") && as.length >= 2) {
                  this.ofCustomSky = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofShowCapes") && as.length >= 2) {
                  this.ofShowCapes = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofNaturalTextures") && as.length >= 2) {
                  this.ofNaturalTextures = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofEmissiveTextures") && as.length >= 2) {
                  this.ofEmissiveTextures = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofLazyChunkLoading") && as.length >= 2) {
                  this.ofLazyChunkLoading = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofRenderRegions") && as.length >= 2) {
                  this.ofRenderRegions = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofSmartAnimations") && as.length >= 2) {
                  this.ofSmartAnimations = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofDynamicFov") && as.length >= 2) {
                  this.ofDynamicFov = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofAlternateBlocks") && as.length >= 2) {
                  this.ofAlternateBlocks = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofDynamicLights") && as.length >= 2) {
                  this.ofDynamicLights = Integer.valueOf(as[1]);
                  this.ofDynamicLights = limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
               }

               if (as[0].equals("ofScreenshotSize") && as.length >= 2) {
                  this.ofScreenshotSize = Integer.valueOf(as[1]);
                  this.ofScreenshotSize = Config.limit(this.ofScreenshotSize, 1, 4);
               }

               if (as[0].equals("ofCustomEntityModels") && as.length >= 2) {
                  this.ofCustomEntityModels = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofCustomGuis") && as.length >= 2) {
                  this.ofCustomGuis = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofShowGlErrors") && as.length >= 2) {
                  this.ofShowGlErrors = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofFastMath") && as.length >= 2) {
                  this.ofFastMath = Boolean.valueOf(as[1]);
                  Mth.fastMath = this.ofFastMath;
               }

               if (as[0].equals("ofFastRender") && as.length >= 2) {
                  this.ofFastRender = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofChatBackground") && as.length >= 2) {
                  this.ofChatBackground = Integer.valueOf(as[1]);
               }

               if (as[0].equals("ofChatShadow") && as.length >= 2) {
                  this.ofChatShadow = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("ofTelemetry") && as.length >= 2) {
                  this.ofTelemetry = Integer.valueOf(as[1]);
                  this.ofTelemetry = limit(this.ofTelemetry, OF_TELEMETRY);
               }

               if (as[0].equals("ofHeldItemTooltips") && as.length >= 2) {
                  this.ofHeldItemTooltips = Boolean.valueOf(as[1]);
               }

               if (as[0].equals("key_" + this.ofKeyBindZoom.m_90860_())) {
                  this.ofKeyBindZoom.m_90848_(InputConstants.m_84851_(as[1]));
               }

               String key = as[0];
               String val = as[1];

               for (IPersitentOption ipo : persistentOptions) {
                  if (Config.equals(key, ipo.getSaveKey())) {
                     ipo.loadValue(this, val);
                  }
               }
            } catch (Exception var10) {
               Config.dbg("Skipping bad option: " + s);
               var10.printStackTrace();
            }
         }

         KeyUtils.fixKeyConflicts(this.f_92059_, new KeyMapping[]{this.ofKeyBindZoom});
         KeyMapping.m_90854_();
         bufferedreader.close();
      } catch (Exception var11) {
         Config.warn("Failed to load options");
         var11.printStackTrace();
      }
   }

   private List<IPersitentOption> getPersistentOptions() {
      List<IPersitentOption> list = new ArrayList();

      for (OptionInstance opt : OptionInstance.OPTIONS_BY_KEY.values()) {
         if (opt instanceof IPersitentOption po) {
            list.add(po);
         }
      }

      return list;
   }

   public void saveOfOptions() {
      try {
         PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFileOF), StandardCharsets.UTF_8));
         printwriter.println("ofFogType:" + this.ofFogType);
         printwriter.println("ofFogStart:" + this.ofFogStart);
         printwriter.println("ofMipmapType:" + this.ofMipmapType);
         printwriter.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
         printwriter.println("ofSmoothFps:" + this.ofSmoothFps);
         printwriter.println("ofSmoothWorld:" + this.ofSmoothWorld);
         printwriter.println("ofAoLevel:" + this.ofAoLevel);
         printwriter.println("ofClouds:" + this.ofClouds);
         printwriter.println("ofCloudsHeight:" + this.ofCloudsHeight);
         printwriter.println("ofTrees:" + this.ofTrees);
         printwriter.println("ofRain:" + this.ofRain);
         printwriter.println("ofAnimatedWater:" + this.ofAnimatedWater);
         printwriter.println("ofAnimatedLava:" + this.ofAnimatedLava);
         printwriter.println("ofAnimatedFire:" + this.ofAnimatedFire);
         printwriter.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
         printwriter.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
         printwriter.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
         printwriter.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
         printwriter.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
         printwriter.println("ofVoidParticles:" + this.ofVoidParticles);
         printwriter.println("ofWaterParticles:" + this.ofWaterParticles);
         printwriter.println("ofPortalParticles:" + this.ofPortalParticles);
         printwriter.println("ofPotionParticles:" + this.ofPotionParticles);
         printwriter.println("ofFireworkParticles:" + this.ofFireworkParticles);
         printwriter.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
         printwriter.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
         printwriter.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
         printwriter.println("ofRainSplash:" + this.ofRainSplash);
         printwriter.println("ofLagometer:" + this.ofLagometer);
         printwriter.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
         printwriter.println("ofBetterGrass:" + this.ofBetterGrass);
         printwriter.println("ofConnectedTextures:" + this.ofConnectedTextures);
         printwriter.println("ofWeather:" + this.ofWeather);
         printwriter.println("ofSky:" + this.ofSky);
         printwriter.println("ofStars:" + this.ofStars);
         printwriter.println("ofSunMoon:" + this.ofSunMoon);
         printwriter.println("ofVignette:" + this.ofVignette);
         printwriter.println("ofChunkUpdates:" + this.ofChunkUpdates);
         printwriter.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
         printwriter.println("ofTime:" + this.ofTime);
         printwriter.println("ofAaLevel:" + this.ofAaLevel);
         printwriter.println("ofAfLevel:" + this.ofAfLevel);
         printwriter.println("ofProfiler:" + this.ofProfiler);
         printwriter.println("ofBetterSnow:" + this.ofBetterSnow);
         printwriter.println("ofSwampColors:" + this.ofSwampColors);
         printwriter.println("ofRandomEntities:" + this.ofRandomEntities);
         printwriter.println("ofCustomFonts:" + this.ofCustomFonts);
         printwriter.println("ofCustomColors:" + this.ofCustomColors);
         printwriter.println("ofCustomItems:" + this.ofCustomItems);
         printwriter.println("ofCustomSky:" + this.ofCustomSky);
         printwriter.println("ofShowCapes:" + this.ofShowCapes);
         printwriter.println("ofNaturalTextures:" + this.ofNaturalTextures);
         printwriter.println("ofEmissiveTextures:" + this.ofEmissiveTextures);
         printwriter.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
         printwriter.println("ofRenderRegions:" + this.ofRenderRegions);
         printwriter.println("ofSmartAnimations:" + this.ofSmartAnimations);
         printwriter.println("ofDynamicFov:" + this.ofDynamicFov);
         printwriter.println("ofAlternateBlocks:" + this.ofAlternateBlocks);
         printwriter.println("ofDynamicLights:" + this.ofDynamicLights);
         printwriter.println("ofScreenshotSize:" + this.ofScreenshotSize);
         printwriter.println("ofCustomEntityModels:" + this.ofCustomEntityModels);
         printwriter.println("ofCustomGuis:" + this.ofCustomGuis);
         printwriter.println("ofShowGlErrors:" + this.ofShowGlErrors);
         printwriter.println("ofFastMath:" + this.ofFastMath);
         printwriter.println("ofFastRender:" + this.ofFastRender);
         printwriter.println("ofChatBackground:" + this.ofChatBackground);
         printwriter.println("ofChatShadow:" + this.ofChatShadow);
         printwriter.println("ofTelemetry:" + this.ofTelemetry);
         printwriter.println("ofHeldItemTooltips:" + this.ofHeldItemTooltips);
         printwriter.println("key_" + this.ofKeyBindZoom.m_90860_() + ":" + this.ofKeyBindZoom.m_90865_());

         for (IPersitentOption ipo : this.getPersistentOptions()) {
            printwriter.println(ipo.getSaveKey() + ":" + ipo.getSaveText(this));
         }

         printwriter.close();
      } catch (Exception var5) {
         Config.warn("Failed to save options");
         var5.printStackTrace();
      }
   }

   public void updateRenderClouds() {
      switch (this.ofClouds) {
         case 1:
            this.f_231792_.m_231514_(CloudStatus.FAST);
            break;
         case 2:
            this.f_231792_.m_231514_(CloudStatus.FANCY);
            break;
         case 3:
            this.f_231792_.m_231514_(CloudStatus.OFF);
            break;
         default:
            if (this.m_232060_().m_231551_() != GraphicsStatus.FAST) {
               this.f_231792_.m_231514_(CloudStatus.FANCY);
            } else {
               this.f_231792_.m_231514_(CloudStatus.FAST);
            }
      }

      if (this.m_232060_().m_231551_() == GraphicsStatus.FABULOUS) {
         LevelRenderer wr = Minecraft.m_91087_().f_91060_;
         if (wr != null) {
            RenderTarget framebuffer = wr.m_109832_();
            if (framebuffer != null) {
               framebuffer.m_83954_(Minecraft.f_91002_);
            }
         }
      }
   }

   public void resetSettings() {
      this.f_92106_.m_231514_(8);
      this.f_193768_.m_231514_(8);
      this.f_92112_.m_231514_(1.0);
      this.f_92080_.m_231514_(true);
      this.f_92113_.m_231514_(this.f_92113_.getMaxValue());
      this.f_92041_.m_231514_(false);
      this.updateVSync();
      this.f_92027_.m_231514_(4);
      this.f_92115_.m_231514_(GraphicsStatus.FANCY);
      this.f_92116_.m_231514_(true);
      this.f_231792_.m_231514_(CloudStatus.FANCY);
      this.f_92068_.m_231514_(70);
      this.f_92071_.m_231514_(0.0);
      this.f_92072_.m_231514_(0);
      this.f_92073_.m_231514_(ParticleStatus.ALL);
      this.ofHeldItemTooltips = true;
      this.f_92043_.m_231514_(false);
      this.f_193769_.m_231514_(PrioritizeChunkUpdates.NONE);
      this.ofFogType = 2;
      this.ofFogStart = 0.8F;
      this.ofMipmapType = 0;
      this.ofOcclusionFancy = false;
      this.ofSmartAnimations = false;
      this.ofSmoothFps = false;
      Config.updateAvailableProcessors();
      this.ofSmoothWorld = Config.isSingleProcessor();
      this.ofLazyChunkLoading = false;
      this.ofRenderRegions = false;
      this.ofFastMath = false;
      this.ofFastRender = false;
      this.ofDynamicFov = true;
      this.ofAlternateBlocks = true;
      this.ofDynamicLights = 3;
      this.ofScreenshotSize = 1;
      this.ofCustomEntityModels = true;
      this.ofCustomGuis = true;
      this.ofShowGlErrors = true;
      this.ofChatBackground = 0;
      this.ofChatShadow = true;
      this.ofTelemetry = 0;
      this.ofAoLevel = 1.0;
      this.ofAaLevel = 0;
      this.ofAfLevel = 1;
      this.ofClouds = 0;
      this.ofCloudsHeight = 0.0;
      this.ofTrees = 0;
      this.ofRain = 0;
      this.ofBetterGrass = 3;
      this.ofAutoSaveTicks = 4000;
      this.ofLagometer = false;
      this.ofProfiler = false;
      this.ofWeather = true;
      this.ofSky = true;
      this.ofStars = true;
      this.ofSunMoon = true;
      this.ofVignette = 0;
      this.ofChunkUpdates = 1;
      this.ofChunkUpdatesDynamic = false;
      this.ofTime = 0;
      this.ofBetterSnow = false;
      this.ofSwampColors = true;
      this.ofRandomEntities = true;
      this.f_92032_.m_231514_(2);
      this.ofCustomFonts = true;
      this.ofCustomColors = true;
      this.ofCustomItems = true;
      this.ofCustomSky = true;
      this.ofShowCapes = true;
      this.ofConnectedTextures = 2;
      this.ofNaturalTextures = false;
      this.ofEmissiveTextures = true;
      this.ofAnimatedWater = 0;
      this.ofAnimatedLava = 0;
      this.ofAnimatedFire = true;
      this.ofAnimatedPortal = true;
      this.ofAnimatedRedstone = true;
      this.ofAnimatedExplosion = true;
      this.ofAnimatedFlame = true;
      this.ofAnimatedSmoke = true;
      this.ofVoidParticles = true;
      this.ofWaterParticles = true;
      this.ofRainSplash = true;
      this.ofPortalParticles = true;
      this.ofPotionParticles = true;
      this.ofFireworkParticles = true;
      this.ofDrippingWaterLava = true;
      this.ofAnimatedTerrain = true;
      this.ofAnimatedTextures = true;
      this.ofQuickInfo = false;
      this.ofQuickInfoFps = Option.FULL.getValue();
      this.ofQuickInfoChunks = true;
      this.ofQuickInfoEntities = true;
      this.ofQuickInfoParticles = false;
      this.ofQuickInfoUpdates = true;
      this.ofQuickInfoGpu = false;
      this.ofQuickInfoPos = Option.COMPACT.getValue();
      this.ofQuickInfoFacing = Option.OFF.getValue();
      this.ofQuickInfoBiome = false;
      this.ofQuickInfoLight = false;
      this.ofQuickInfoMemory = Option.OFF.getValue();
      this.ofQuickInfoNativeMemory = Option.OFF.getValue();
      this.ofQuickInfoTargetBlock = Option.OFF.getValue();
      this.ofQuickInfoTargetFluid = Option.OFF.getValue();
      this.ofQuickInfoTargetEntity = Option.OFF.getValue();
      this.ofQuickInfoLabels = Option.COMPACT.getValue();
      this.ofQuickInfoBackground = false;
      Shaders.setShaderPack("OFF");
      Shaders.configAntialiasingLevel = 0;
      Shaders.uninit();
      Shaders.storeConfig();
      this.f_92060_.m_91088_();
      this.m_92169_();
   }

   public void updateVSync() {
      if (this.f_92060_.m_91268_() != null) {
         this.f_92060_.m_91268_().m_85409_(this.f_92041_.m_231551_());
      }
   }

   public void updateMipmaps() {
      this.f_92060_.m_91312_(this.f_92027_.m_231551_());
      this.f_92060_.m_91088_();
   }

   public void setAllAnimations(boolean flag) {
      int animVal = flag ? 0 : 2;
      this.ofAnimatedWater = animVal;
      this.ofAnimatedLava = animVal;
      this.ofAnimatedFire = flag;
      this.ofAnimatedPortal = flag;
      this.ofAnimatedRedstone = flag;
      this.ofAnimatedExplosion = flag;
      this.ofAnimatedFlame = flag;
      this.ofAnimatedSmoke = flag;
      this.ofVoidParticles = flag;
      this.ofWaterParticles = flag;
      this.ofRainSplash = flag;
      this.ofPortalParticles = flag;
      this.ofPotionParticles = flag;
      this.ofFireworkParticles = flag;
      this.f_92073_.m_231514_(flag ? ParticleStatus.ALL : ParticleStatus.MINIMAL);
      this.ofDrippingWaterLava = flag;
      this.ofAnimatedTerrain = flag;
      this.ofAnimatedTextures = flag;
   }

   public void setAllQuickInfos(boolean flag) {
      if (flag) {
         this.ofQuickInfoFps = Option.FULL.getValue();
         this.ofQuickInfoChunks = true;
         this.ofQuickInfoEntities = true;
         this.ofQuickInfoParticles = true;
         this.ofQuickInfoUpdates = true;
         this.ofQuickInfoGpu = true;
         this.ofQuickInfoPos = Option.FULL.getValue();
         this.ofQuickInfoFacing = Option.FULL.getValue();
         this.ofQuickInfoBiome = true;
         this.ofQuickInfoLight = true;
         this.ofQuickInfoMemory = Option.FULL.getValue();
         this.ofQuickInfoNativeMemory = Option.FULL.getValue();
         this.ofQuickInfoTargetBlock = Option.FULL.getValue();
         this.ofQuickInfoTargetFluid = Option.FULL.getValue();
         this.ofQuickInfoTargetEntity = Option.FULL.getValue();
      } else {
         this.ofQuickInfoFps = Option.OFF.getValue();
         this.ofQuickInfoChunks = false;
         this.ofQuickInfoEntities = false;
         this.ofQuickInfoParticles = false;
         this.ofQuickInfoUpdates = false;
         this.ofQuickInfoGpu = false;
         this.ofQuickInfoPos = Option.OFF.getValue();
         this.ofQuickInfoFacing = Option.OFF.getValue();
         this.ofQuickInfoBiome = false;
         this.ofQuickInfoLight = false;
         this.ofQuickInfoMemory = Option.OFF.getValue();
         this.ofQuickInfoNativeMemory = Option.OFF.getValue();
         this.ofQuickInfoTargetBlock = Option.OFF.getValue();
         this.ofQuickInfoTargetFluid = Option.OFF.getValue();
         this.ofQuickInfoTargetEntity = Option.OFF.getValue();
      }
   }

   private static int nextValue(int val, int[] vals) {
      int index = indexOf(val, vals);
      if (index < 0) {
         return vals[0];
      } else {
         if (++index >= vals.length) {
            index = 0;
         }

         return vals[index];
      }
   }

   private static int limit(int val, int[] vals) {
      int index = indexOf(val, vals);
      return index < 0 ? vals[0] : val;
   }

   public static int indexOf(int val, int[] vals) {
      for (int i = 0; i < vals.length; i++) {
         if (vals[i] == val) {
            return i;
         }
      }

      return -1;
   }

   public static int indexOf(double val, double[] vals) {
      for (int i = 0; i < vals.length; i++) {
         if (vals[i] == val) {
            return i;
         }
      }

      return -1;
   }

   private static String getTranslation(String[] strArray, int index) {
      if (index < 0 || index >= strArray.length) {
         index = 0;
      }

      return I18n.m_118938_(strArray[index], new Object[0]);
   }

   public static Component genericValueLabel(String keyIn, int valueIn) {
      return m_231921_(Component.m_237115_(keyIn), Component.m_237113_(Integer.toString(valueIn)));
   }

   public static Component genericValueLabel(String keyIn, String valueKeyIn) {
      return m_231921_(Component.m_237115_(keyIn), Component.m_237115_(valueKeyIn));
   }

   public static Component genericValueLabel(String keyIn, String valueKeyIn, int valueIn) {
      return m_231921_(Component.m_237115_(keyIn), Component.m_237110_(valueKeyIn, new Object[]{Integer.toString(valueIn)}));
   }

   private void setForgeKeybindProperties() {
      if (Reflector.KeyConflictContext_IN_GAME.exists()) {
         if (Reflector.ForgeKeyBinding_setKeyConflictContext.exists()) {
            Object inGame = Reflector.getFieldValue(Reflector.KeyConflictContext_IN_GAME);
            Reflector.call(this.f_92085_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92086_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92087_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92088_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92089_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92090_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92091_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92096_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92098_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92099_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92100_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92103_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.f_92104_, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
         }
      }
   }

   public void m_92145_(PackRepository resourcePackListIn) {
      Set<String> set = Sets.newLinkedHashSet();
      Iterator<String> iterator = this.f_92117_.iterator();

      while (iterator.hasNext()) {
         String s = (String)iterator.next();
         Pack pack = resourcePackListIn.m_10507_(s);
         if (pack == null && !s.startsWith("file/")) {
            pack = resourcePackListIn.m_10507_("file/" + s);
         }

         if (pack == null) {
            f_92077_.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", s);
            iterator.remove();
         } else if (!pack.m_10443_().m_10489_() && !this.f_92118_.contains(s)) {
            f_92077_.warn("Removed resource pack {} from options because it is no longer compatible", s);
            iterator.remove();
         } else if (pack.m_10443_().m_10489_() && this.f_92118_.contains(s)) {
            f_92077_.info("Removed resource pack {} from incompatibility list because it's now compatible", s);
            this.f_92118_.remove(s);
         } else {
            set.add(pack.m_10446_());
         }
      }

      resourcePackListIn.m_10509_(set);
   }

   public CameraType m_92176_() {
      return this.f_92111_;
   }

   public void m_92157_(CameraType pointOfViewIn) {
      this.f_92111_ = pointOfViewIn;
   }

   private static List<String> m_292893_(String stringIn) {
      List<String> list = (List<String>)GsonHelper.m_13785_(f_92078_, stringIn, f_290931_);
      return (List<String>)(list != null ? list : Lists.newArrayList());
   }

   public File m_168450_() {
      return this.f_92110_;
   }

   public String m_168451_() {
      final List<Pair<String, Object>> list = new ArrayList();
      this.m_323232_(new Options.OptionAccess() {
         @Override
         public <T> void m_232124_(String keyIn, OptionInstance<T> optionIn) {
            list.add(Pair.of(keyIn, optionIn.m_231551_()));
         }
      });
      list.add(Pair.of("fullscreenResolution", String.valueOf(this.f_92123_)));
      list.add(Pair.of("glDebugVerbosity", this.f_92035_));
      list.add(Pair.of("overrideHeight", this.f_92129_));
      list.add(Pair.of("overrideWidth", this.f_92128_));
      list.add(Pair.of("syncChunkWrites", this.f_92076_));
      list.add(Pair.of("useNativeTransport", this.f_92028_));
      list.add(Pair.of("resourcePacks", this.f_92117_));
      return (String)list.stream()
         .sorted(Comparator.comparing(Pair::getFirst))
         .map(pairIn -> (String)pairIn.getFirst() + ": " + pairIn.getSecond())
         .collect(Collectors.joining(System.lineSeparator()));
   }

   public void m_193770_(int valueIn) {
      this.f_193765_ = valueIn;
   }

   public int m_193772_() {
      return this.f_193765_ > 0 ? Math.min(this.f_92106_.m_231551_(), this.f_193765_) : this.f_92106_.m_231551_();
   }

   private static Component m_231952_(Component componentIn, int valueIn) {
      return Component.m_237110_("options.pixel_value", new Object[]{componentIn, valueIn});
   }

   private static Component m_231897_(Component componentIn, double valueIn) {
      return Component.m_237110_("options.percent_value", new Object[]{componentIn, (int)(valueIn * 100.0)});
   }

   public static Component m_231921_(Component componentIn, Component valueIn) {
      return Component.m_237110_("options.generic_value", new Object[]{componentIn, valueIn});
   }

   public static Component m_231900_(Component componentIn, int valueIn) {
      return m_231921_(componentIn, Component.m_237113_(Integer.toString(valueIn)));
   }

   public static Component m_338389_(Component componentIn, int valueIn) {
      return valueIn == 0 ? m_231921_(componentIn, CommonComponents.f_130654_) : m_231900_(componentIn, valueIn);
   }

   private static Component m_324758_(Component componentIn, double valueIn) {
      return valueIn == 0.0 ? m_231921_(componentIn, CommonComponents.f_130654_) : m_231897_(componentIn, valueIn);
   }

   interface FieldAccess extends Options.OptionAccess {
      int m_142708_(String var1, int var2);

      boolean m_142682_(String var1, boolean var2);

      String m_141943_(String var1, String var2);

      float m_142432_(String var1, float var2);

      <T> T m_142634_(String var1, T var2, Function<String, T> var3, Function<T, String> var4);
   }

   interface OptionAccess {
      <T> void m_232124_(String var1, OptionInstance<T> var2);
   }
}
