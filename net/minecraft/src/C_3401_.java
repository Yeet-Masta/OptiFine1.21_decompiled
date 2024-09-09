package net.minecraft.src;

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
import com.mojang.blaze3d.platform.GLX;
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
import net.minecraft.src.C_3140_.C_3143_;
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

public class C_3401_ {
   static final Logger f_92077_ = LogUtils.getLogger();
   static final Gson f_92078_ = new Gson();
   private static final TypeToken f_290931_ = new TypeToken() {
   };
   public static final int f_168406_ = 2;
   public static final int f_168407_ = 4;
   public static final int f_168409_ = 8;
   public static final int f_168410_ = 12;
   public static final int f_168411_ = 16;
   public static final int f_168412_ = 32;
   private static final Splitter f_92107_ = Splitter.on(':').limit(2);
   public static final String f_193766_ = "";
   private static final C_4996_ f_231789_ = C_4996_.m_237115_("options.darkMojangStudiosBackgroundColor.tooltip");
   private final C_213334_ f_168413_;
   private static final C_4996_ f_231790_ = C_4996_.m_237115_("options.hideLightningFlashes.tooltip");
   private final C_213334_ f_231791_;
   private static final C_4996_ f_302626_ = C_4996_.m_237115_("options.hideSplashTexts.tooltip");
   private final C_213334_ f_302346_;
   private final C_213334_ f_92053_;
   private final C_213334_ f_92106_;
   private final C_213334_ f_193768_;
   private int f_193765_;
   private final C_213334_ f_92112_;
   public static final int f_231811_ = 260;
   private final C_213334_ f_92113_;
   private final C_213334_ f_231792_;
   private static final C_4996_ f_231793_ = C_4996_.m_237115_("options.graphics.fast.tooltip");
   private static final C_4996_ f_231794_;
   private static final C_4996_ f_231785_;
   private final C_213334_ f_92115_;
   private final C_213334_ f_92116_;
   private static final C_4996_ f_231786_;
   private static final C_4996_ f_231787_;
   private static final C_4996_ f_231788_;
   private final C_213334_ f_193769_;
   public List f_92117_;
   public List f_92118_;
   private final C_213334_ f_92119_;
   private final C_213334_ f_92120_;
   private final C_213334_ f_92121_;
   private static final C_4996_ f_315005_;
   private static final int f_315767_ = 5;
   private final C_213334_ f_317010_;
   private final C_213334_ f_92122_;
   private final C_213334_ f_244402_;
   private static final C_4996_ f_273812_;
   private final C_213334_ f_273910_;
   private final C_213334_ f_290977_;
   @Nullable
   public String f_92123_;
   public boolean f_92124_;
   public boolean f_92125_;
   public boolean f_92126_;
   private final Set f_92108_;
   private final C_213334_ f_92127_;
   public int f_92128_;
   public int f_92129_;
   private final C_213334_ f_92131_;
   private final C_213334_ f_92132_;
   private final C_213334_ f_92133_;
   private final C_213334_ f_92134_;
   private final C_213334_ f_92135_;
   private static final C_4996_ f_263815_;
   private final C_213334_ f_263718_;
   private final C_213334_ f_92027_;
   public boolean f_92028_;
   private final C_213334_ f_92029_;
   public C_4621_ f_92030_;
   public boolean f_92031_;
   public boolean f_168405_;
   private final C_213334_ f_92032_;
   private final C_213334_ f_92033_;
   private final C_213334_ f_92034_;
   public int f_92035_;
   private final C_213334_ f_92036_;
   private final C_213334_ f_256834_;
   private final C_213334_ f_92037_;
   private final C_213334_ f_92038_;
   private final C_213334_ f_92039_;
   private final C_213334_ f_92040_;
   private final C_213334_ f_92041_;
   private final C_213334_ f_92042_;
   private final C_213334_ f_92043_;
   private final C_213334_ f_314642_;
   private final C_213334_ f_92044_;
   private final C_213334_ f_92045_;
   private static final C_4996_ f_337252_;
   private final C_213334_ f_92046_;
   private static final C_4996_ f_231804_;
   private final C_213334_ f_193762_;
   private final C_213334_ f_92047_;
   private final Map f_244498_;
   private final C_213334_ f_92049_;
   private static final C_4996_ f_231805_;
   private static final C_4996_ f_231806_;
   private final C_213334_ f_231807_;
   private final C_213334_ f_92050_;
   private final C_213334_ f_92051_;
   private final C_213334_ f_92052_;
   private final C_213334_ f_92080_;
   private static final C_4996_ f_231808_;
   private static final C_4996_ f_231809_;
   private final C_213334_ f_92081_;
   private final C_213334_ f_92082_;
   public boolean f_92083_;
   private static final C_4996_ f_231810_;
   private final C_213334_ f_92084_;
   private final C_213334_ f_193763_;
   private static final C_4996_ f_231797_;
   private final C_213334_ f_231798_;
   public final C_3387_ f_92085_;
   public final C_3387_ f_92086_;
   public final C_3387_ f_92087_;
   public final C_3387_ f_92088_;
   public final C_3387_ f_92089_;
   public final C_3387_ f_92090_;
   public final C_3387_ f_92091_;
   public final C_3387_ f_92092_;
   public final C_3387_ f_92093_;
   public final C_3387_ f_92094_;
   public final C_3387_ f_92095_;
   public final C_3387_ f_92096_;
   public final C_3387_ f_92097_;
   public final C_3387_ f_92098_;
   public final C_3387_ f_92099_;
   public final C_3387_ f_92100_;
   public final C_3387_ f_92101_;
   public final C_3387_ f_92102_;
   public final C_3387_ f_92103_;
   public final C_3387_ f_92104_;
   public final C_3387_ f_92105_;
   public final C_3387_ f_92054_;
   public final C_3387_ f_92055_;
   public final C_3387_[] f_92056_;
   public final C_3387_ f_92057_;
   public final C_3387_ f_92058_;
   public C_3387_[] f_92059_;
   protected C_3391_ f_92060_;
   private final File f_92110_;
   public boolean f_92062_;
   private C_3374_ f_92111_;
   public String f_92066_;
   public boolean f_92067_;
   private final C_213334_ f_92068_;
   private static final C_4996_ f_260656_;
   private final C_213334_ f_260461_;
   private static final C_4996_ f_231799_;
   private final C_213334_ f_92069_;
   private static final C_4996_ f_231800_;
   private final C_213334_ f_92070_;
   private static final C_4996_ f_231801_;
   private final C_213334_ f_231802_;
   private static final C_4996_ f_267409_;
   private final C_213334_ f_267458_;
   private static final C_4996_ f_267450_;
   private final C_213334_ f_267462_;
   private static final C_4996_ f_268597_;
   private final C_213334_ f_268427_;
   private final C_213334_ f_92071_;
   public static final int f_278127_ = 0;
   private static final int f_276073_ = 2147483646;
   private final C_213334_ f_92072_;
   private final C_213334_ f_92073_;
   private final C_213334_ f_231803_;
   public String f_92075_;
   private final C_213334_ f_193764_;
   public boolean f_263744_;
   public boolean f_92076_;
   public int ofFogType;
   public float ofFogStart;
   public int ofMipmapType;
   public boolean ofOcclusionFancy;
   public boolean ofSmoothFps;
   public boolean ofSmoothWorld;
   public boolean ofLazyChunkLoading;
   public boolean ofRenderRegions;
   public boolean ofSmartAnimations;
   public double ofAoLevel;
   public int ofAaLevel;
   public int ofAfLevel;
   public int ofClouds;
   public double ofCloudsHeight;
   public int ofTrees;
   public int ofRain;
   public int ofBetterGrass;
   public int ofAutoSaveTicks;
   public boolean ofLagometer;
   public boolean ofProfiler;
   public boolean ofWeather;
   public boolean ofSky;
   public boolean ofStars;
   public boolean ofSunMoon;
   public int ofVignette;
   public int ofChunkUpdates;
   public boolean ofChunkUpdatesDynamic;
   public int ofTime;
   public boolean ofBetterSnow;
   public boolean ofSwampColors;
   public boolean ofRandomEntities;
   public boolean ofCustomFonts;
   public boolean ofCustomColors;
   public boolean ofCustomSky;
   public boolean ofShowCapes;
   public int ofConnectedTextures;
   public boolean ofCustomItems;
   public boolean ofNaturalTextures;
   public boolean ofEmissiveTextures;
   public boolean ofFastMath;
   public boolean ofFastRender;
   public boolean ofDynamicFov;
   public boolean ofAlternateBlocks;
   public int ofDynamicLights;
   public boolean ofCustomEntityModels;
   public boolean ofCustomGuis;
   public boolean ofShowGlErrors;
   public int ofScreenshotSize;
   public int ofChatBackground;
   public boolean ofChatShadow;
   public int ofTelemetry;
   public boolean ofHeldItemTooltips;
   public int ofAnimatedWater;
   public int ofAnimatedLava;
   public boolean ofAnimatedFire;
   public boolean ofAnimatedPortal;
   public boolean ofAnimatedRedstone;
   public boolean ofAnimatedExplosion;
   public boolean ofAnimatedFlame;
   public boolean ofAnimatedSmoke;
   public boolean ofVoidParticles;
   public boolean ofWaterParticles;
   public boolean ofRainSplash;
   public boolean ofPortalParticles;
   public boolean ofPotionParticles;
   public boolean ofFireworkParticles;
   public boolean ofDrippingWaterLava;
   public boolean ofAnimatedTerrain;
   public boolean ofAnimatedTextures;
   public boolean ofQuickInfo;
   public int ofQuickInfoFps;
   public boolean ofQuickInfoChunks;
   public boolean ofQuickInfoEntities;
   public boolean ofQuickInfoParticles;
   public boolean ofQuickInfoUpdates;
   public boolean ofQuickInfoGpu;
   public int ofQuickInfoPos;
   public int ofQuickInfoFacing;
   public boolean ofQuickInfoBiome;
   public boolean ofQuickInfoLight;
   public int ofQuickInfoMemory;
   public int ofQuickInfoNativeMemory;
   public int ofQuickInfoTargetBlock;
   public int ofQuickInfoTargetFluid;
   public int ofQuickInfoTargetEntity;
   public int ofQuickInfoLabels;
   public boolean ofQuickInfoBackground;
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
   public static final int[] VALS_FAST_FANCY_OFF;
   private static final int[] OF_TREES_VALUES;
   private static final int[] OF_DYNAMIC_LIGHTS;
   private static final String[] KEYS_DYNAMIC_LIGHTS;
   public static final int TELEM_ON = 0;
   public static final int TELEM_ANON = 1;
   public static final int TELEM_OFF = 2;
   private static final int[] OF_TELEMETRY;
   private static final String[] KEYS_TELEMETRY;
   public C_3387_ ofKeyBindZoom;
   private File optionsFileOF;
   private boolean loadOptions;
   private boolean saveOptions;
   public final C_213334_ GRAPHICS;
   public final C_213334_ RENDER_DISTANCE;
   public final C_213334_ SIMULATION_DISTANCE;
   // $FF: renamed from: AO net.minecraft.src.C_213334_
   public final C_213334_ field_72;
   public final C_213334_ FRAMERATE_LIMIT;
   public final C_213334_ GUI_SCALE;
   public final C_213334_ ENTITY_SHADOWS;
   public final C_213334_ GAMMA;
   public final C_213334_ ATTACK_INDICATOR;
   public final C_213334_ PARTICLES;
   public final C_213334_ VIEW_BOBBING;
   public final C_213334_ AUTOSAVE_INDICATOR;
   public final C_213334_ ENTITY_DISTANCE_SCALING;
   public final C_213334_ BIOME_BLEND_RADIUS;
   public final C_213334_ FULLSCREEN;
   public final C_213334_ PRIORITIZE_CHUNK_UPDATES;
   public final C_213334_ MIPMAP_LEVELS;
   public final C_213334_ SCREEN_EFFECT_SCALE;
   public final C_213334_ FOV_EFFECT_SCALE;

   public C_213334_ m_231838_() {
      return this.f_168413_;
   }

   public C_213334_ m_231935_() {
      return this.f_231791_;
   }

   public C_213334_ m_307023_() {
      return this.f_302346_;
   }

   public C_213334_ m_231964_() {
      return this.f_92053_;
   }

   public C_213334_ m_231984_() {
      return this.f_92106_;
   }

   public C_213334_ m_232001_() {
      return this.f_193768_;
   }

   public C_213334_ m_232018_() {
      return this.f_92112_;
   }

   public C_213334_ m_232035_() {
      return this.f_92113_;
   }

   public C_213334_ m_232050_() {
      return this.f_231792_;
   }

   public C_213334_ m_232060_() {
      return this.f_92115_;
   }

   public C_213334_ m_232070_() {
      return this.f_92116_;
   }

   public C_213334_ m_232080_() {
      return this.f_193769_;
   }

   public void m_274546_(C_62_ repoIn) {
      List list = ImmutableList.copyOf(this.f_92117_);
      this.f_92117_.clear();
      this.f_92118_.clear();
      Iterator var3 = repoIn.m_10524_().iterator();

      while(var3.hasNext()) {
         C_58_ pack = (C_58_)var3.next();
         if (!pack.m_10450_()) {
            this.f_92117_.add(pack.m_10446_());
            if (!pack.m_10443_().m_10489_()) {
               this.f_92118_.add(pack.m_10446_());
            }
         }
      }

      this.m_92169_();
      List list1 = ImmutableList.copyOf(this.f_92117_);
      if (!list1.equals(list)) {
         this.f_92060_.m_91391_();
      }

   }

   public C_213334_ m_232090_() {
      return this.f_92119_;
   }

   public C_213334_ m_232098_() {
      return this.f_92120_;
   }

   public C_213334_ m_232101_() {
      return this.f_92121_;
   }

   public C_213334_ m_323040_() {
      return this.f_317010_;
   }

   public int m_321110_() {
      return (Integer)this.m_323040_().m_231551_();
   }

   public C_213334_ m_232104_() {
      return this.f_92122_;
   }

   public C_213334_ m_245201_() {
      return this.f_244402_;
   }

   public C_213334_ m_274330_() {
      return this.f_273910_;
   }

   public C_213334_ m_292959_() {
      return this.f_290977_;
   }

   public C_213334_ m_232107_() {
      return this.f_92127_;
   }

   public C_213334_ m_232110_() {
      return this.f_92131_;
   }

   public C_213334_ m_232113_() {
      return this.f_92132_;
   }

   public C_213334_ m_232116_() {
      return this.f_92133_;
   }

   public C_213334_ m_232117_() {
      return this.f_92134_;
   }

   public C_213334_ m_232118_() {
      return this.f_92135_;
   }

   public C_213334_ m_264038_() {
      return this.f_263718_;
   }

   public C_213334_ m_232119_() {
      return this.f_92027_;
   }

   public C_213334_ m_232120_() {
      return this.f_92029_;
   }

   public C_213334_ m_232121_() {
      return this.f_92032_;
   }

   private static double m_231965_(int valueIn) {
      return Math.pow(10.0, (double)valueIn / 100.0);
   }

   private static int m_231839_(double valueIn) {
      return C_188_.m_14107_(Math.log10(valueIn) * 100.0);
   }

   public C_213334_ m_232122_() {
      return this.f_92033_;
   }

   public C_213334_ m_232123_() {
      return this.f_92034_;
   }

   public C_213334_ m_231812_() {
      return this.f_92036_;
   }

   public C_213334_ m_257871_() {
      return this.f_256834_;
   }

   public C_213334_ m_231813_() {
      return this.f_92037_;
   }

   public C_213334_ m_231814_() {
      return this.f_92038_;
   }

   public C_213334_ m_231815_() {
      return this.f_92039_;
   }

   public C_213334_ m_231816_() {
      return this.f_92040_;
   }

   public C_213334_ m_231817_() {
      return this.f_92041_;
   }

   public C_213334_ m_231818_() {
      return this.f_92042_;
   }

   private static void m_320153_() {
      C_3391_ minecraft = C_3391_.m_91087_();
      if (minecraft.m_91268_() != null) {
         minecraft.m_323618_();
         minecraft.m_5741_();
      }

   }

   public C_213334_ m_231819_() {
      return this.f_92043_;
   }

   private static boolean m_324081_() {
      return Locale.getDefault().getLanguage().equalsIgnoreCase("ja");
   }

   public C_213334_ m_321442_() {
      return this.f_314642_;
   }

   public C_213334_ m_231820_() {
      return this.f_92044_;
   }

   public C_213334_ m_231821_() {
      return this.f_92045_;
   }

   public C_213334_ m_231822_() {
      return this.f_92046_;
   }

   public C_213334_ m_231823_() {
      return this.f_193762_;
   }

   public C_213334_ m_231824_() {
      return this.f_92047_;
   }

   public final float m_92147_(C_125_ category) {
      return ((Double)this.m_246669_(category).m_231551_()).floatValue();
   }

   public final C_213334_ m_246669_(C_125_ p_246669_1_) {
      return (C_213334_)Objects.requireNonNull((C_213334_)this.f_244498_.get(p_246669_1_));
   }

   private C_213334_ m_247249_(String p_247249_1_, C_125_ p_247249_2_) {
      return new C_213334_(p_247249_1_, C_213334_.m_231498_(), C_3401_::m_324758_, C_213334_.C_213350_.INSTANCE, 1.0, (p_244657_1_) -> {
         C_3391_.m_91087_().m_91106_().m_120358_(p_247249_2_, p_244657_1_.floatValue());
      });
   }

   public C_213334_ m_231825_() {
      return this.f_92049_;
   }

   public C_213334_ m_231826_() {
      return this.f_231807_;
   }

   public C_213334_ m_231827_() {
      return this.f_92050_;
   }

   public C_213334_ m_231828_() {
      return this.f_92051_;
   }

   public C_213334_ m_231829_() {
      return this.f_92052_;
   }

   public C_213334_ m_231830_() {
      return this.f_92080_;
   }

   public C_213334_ m_231831_() {
      return this.f_92081_;
   }

   public C_213334_ m_231832_() {
      return this.f_92082_;
   }

   public C_213334_ m_231833_() {
      return this.f_92084_;
   }

   public C_213334_ m_231834_() {
      return this.f_193763_;
   }

   public C_213334_ m_231836_() {
      return this.f_231798_;
   }

   public C_213334_ m_231837_() {
      return this.f_92068_;
   }

   public C_213334_ m_261324_() {
      return this.f_260461_;
   }

   public C_213334_ m_231924_() {
      return this.f_92069_;
   }

   public C_213334_ m_231925_() {
      return this.f_92070_;
   }

   public C_213334_ m_231926_() {
      return this.f_231802_;
   }

   public C_213334_ m_267805_() {
      return this.f_267458_;
   }

   public C_213334_ m_267782_() {
      return this.f_267462_;
   }

   public C_213334_ m_269326_() {
      return this.f_268427_;
   }

   public C_213334_ m_231927_() {
      return this.f_92071_;
   }

   public C_213334_ m_231928_() {
      return this.f_92072_;
   }

   public C_213334_ m_231929_() {
      return this.f_92073_;
   }

   public C_213334_ m_231930_() {
      return this.f_231803_;
   }

   public C_213334_ m_231931_() {
      return this.f_193764_;
   }

   public void m_338485_() {
      this.f_263744_ = false;
      this.m_92169_();
   }

   public C_3401_(C_3391_ mcIn, File mcDataDir) {
      this.f_168413_ = C_213334_.m_257536_("options.darkMojangStudiosBackgroundColor", C_213334_.m_231535_(f_231789_), false);
      this.f_231791_ = C_213334_.m_257536_("options.hideLightningFlashes", C_213334_.m_231535_(f_231790_), false);
      this.f_302346_ = C_213334_.m_257536_("options.hideSplashTexts", C_213334_.m_231535_(f_302626_), false);
      this.f_92053_ = new C_213334_("options.sensitivity", C_213334_.m_231498_(), (p_232095_0_, p_232095_1_) -> {
         if (p_232095_1_ == 0.0) {
            return m_231921_(p_232095_0_, C_4996_.m_237115_("options.sensitivity.min"));
         } else {
            return p_232095_1_ == 1.0 ? m_231921_(p_232095_0_, C_4996_.m_237115_("options.sensitivity.max")) : m_231897_(p_232095_0_, 2.0 * p_232095_1_);
         }
      }, C_213334_.C_213350_.INSTANCE, 0.5, (p_232114_0_) -> {
      });
      this.f_193765_ = 0;
      this.f_92112_ = new C_213334_("options.entityDistanceScaling", C_213334_.m_231498_(), C_3401_::m_231897_, (new C_213334_.C_213341_(2, 20)).m_231657_((p_232019_0_) -> {
         return (double)p_232019_0_ / 4.0;
      }, (p_232111_0_) -> {
         return (int)(p_232111_0_ * 4.0);
      }), Codec.doubleRange(0.5, 5.0), 1.0, (p_232040_0_) -> {
      });
      this.f_92113_ = new C_213334_("options.framerateLimit", C_213334_.m_231498_(), (p_232047_0_, p_232047_1_) -> {
         if ((Boolean)this.m_231817_().m_231551_()) {
            return m_231921_(p_232047_0_, C_4996_.m_237115_("of.options.framerateLimit.vsync"));
         } else {
            return p_232047_1_ == 260 ? m_231921_(p_232047_0_, C_4996_.m_237115_("options.framerateLimit.max")) : m_231921_(p_232047_0_, C_4996_.m_237110_("options.framerate", new Object[]{p_232047_1_}));
         }
      }, (new C_213334_.C_213341_(0, 52)).m_231657_((p_232002_0_) -> {
         return p_232002_0_ * 5;
      }, (p_232093_0_) -> {
         return p_232093_0_ / 5;
      }), Codec.intRange(0, 260), 120, (p_232085_0_) -> {
         this.m_231817_().m_231514_(p_232085_0_ == 0);
         C_3391_.m_91087_().m_91268_().m_85380_(p_232085_0_);
      });
      this.f_231792_ = new C_213334_("options.renderClouds", C_213334_.m_231498_(), C_213334_.m_231546_(), new C_213334_.C_213340_(Arrays.asList(C_3376_.values()), Codec.withAlternative(C_3376_.f_291249_, Codec.BOOL, (p_232081_0_) -> {
         return p_232081_0_ ? C_3376_.FANCY : C_3376_.OFF;
      })), C_3376_.FANCY, (p_231853_0_) -> {
         if (C_3391_.m_91085_()) {
            C_3106_ rendertarget = C_3391_.m_91087_().f_91060_.m_109832_();
            if (rendertarget != null) {
               rendertarget.m_83954_(C_3391_.f_91002_);
            }
         }

      });
      this.f_92115_ = new C_213334_("options.graphics", (p_317296_0_) -> {
         if (Boolean.TRUE) {
            return null;
         } else {
            C_256714_ var10000;
            switch (p_317296_0_) {
               case FANCY:
                  var10000 = C_256714_.m_257550_(f_231785_);
                  break;
               case FAST:
                  var10000 = C_256714_.m_257550_(f_231793_);
                  break;
               case FABULOUS:
                  var10000 = C_256714_.m_257550_(f_231794_);
                  break;
               default:
                  throw new MatchException((String)null, (Throwable)null);
            }

            return var10000;
         }
      }, (p_231903_0_, p_231903_1_) -> {
         C_5012_ mutablecomponent = C_4996_.m_237115_(p_231903_1_.m_35968_());
         return p_231903_1_ == C_3383_.FABULOUS ? mutablecomponent.m_130940_(C_4856_.ITALIC) : mutablecomponent;
      }, new C_213334_.C_213335_(Arrays.asList(C_3383_.values()), (List)Stream.of(C_3383_.values()).filter((p_231942_0_) -> {
         return p_231942_0_ != C_3383_.FABULOUS;
      }).collect(Collectors.toList()), () -> {
         if (!Config.isShaders() && GLX.isUsingFBOs()) {
            return C_3391_.m_91087_().m_91396_() && C_3391_.m_91087_().m_91105_().m_109251_();
         } else {
            return true;
         }
      }, (p_231861_0_, p_231861_1_) -> {
         C_3391_ minecraft = C_3391_.m_91087_();
         C_4127_ gpuwarnlistmanager = minecraft.m_91105_();
         if (p_231861_1_ == C_3383_.FABULOUS && gpuwarnlistmanager.m_109240_()) {
            gpuwarnlistmanager.m_109247_();
         } else {
            p_231861_0_.m_231514_(p_231861_1_);
            this.updateRenderClouds();
            minecraft.f_91060_.m_109818_();
         }

      }, Codec.INT.xmap(C_3383_::m_90774_, C_3383_::m_35965_)), C_3383_.FANCY, (p_231855_0_) -> {
      });
      this.f_92116_ = C_213334_.m_231528_("options.ao", true, (p_231849_0_) -> {
         C_3391_.m_91087_().f_91060_.m_109818_();
      });
      this.f_193769_ = new C_213334_("options.prioritizeChunkUpdates", (p_317297_0_) -> {
         if (Boolean.TRUE) {
            return null;
         } else {
            C_256714_ var10000;
            switch (p_317297_0_) {
               case NONE:
                  var10000 = C_256714_.m_257550_(f_231786_);
                  break;
               case PLAYER_AFFECTED:
                  var10000 = C_256714_.m_257550_(f_231787_);
                  break;
               case NEARBY:
                  var10000 = C_256714_.m_257550_(f_231788_);
                  break;
               default:
                  throw new MatchException((String)null, (Throwable)null);
            }

            return var10000;
         }
      }, C_213334_.m_231546_(), new C_213334_.C_213340_(Arrays.asList(C_183059_.values()), Codec.INT.xmap(C_183059_::m_193787_, C_183059_::m_35965_)), C_183059_.NONE, (p_231870_0_) -> {
      });
      this.f_92117_ = Lists.newArrayList();
      this.f_92118_ = Lists.newArrayList();
      this.f_92119_ = new C_213334_("options.chat.visibility", C_213334_.m_231498_(), C_213334_.m_231546_(), new C_213334_.C_213340_(Arrays.asList(C_1139_.values()), Codec.INT.xmap(C_1139_::m_35966_, C_1139_::m_35965_)), C_1139_.FULL, (p_231843_0_) -> {
      });
      this.f_92120_ = new C_213334_("options.chat.opacity", C_213334_.m_231498_(), (p_232087_0_, p_232087_1_) -> {
         return m_231897_(p_232087_0_, p_232087_1_ * 0.9 + 0.1);
      }, C_213334_.C_213350_.INSTANCE, 1.0, (p_232105_0_) -> {
         C_3391_.m_91087_().f_91065_.m_93076_().m_93769_();
      });
      this.f_92121_ = new C_213334_("options.chat.line_spacing", C_213334_.m_231498_(), C_3401_::m_231897_, C_213334_.C_213350_.INSTANCE, 0.0, (p_232102_0_) -> {
      });
      this.f_317010_ = new C_213334_("options.accessibility.menu_background_blurriness", C_213334_.m_231535_(f_315005_), C_3401_::m_338389_, new C_213334_.C_213341_(0, 10), 5, (p_232108_0_) -> {
      });
      this.f_92122_ = new C_213334_("options.accessibility.text_background_opacity", C_213334_.m_231498_(), C_3401_::m_231897_, C_213334_.C_213350_.INSTANCE, 0.5, (p_232099_0_) -> {
         C_3391_.m_91087_().f_91065_.m_93076_().m_93769_();
      });
      this.f_244402_ = new C_213334_("options.accessibility.panorama_speed", C_213334_.m_231498_(), C_3401_::m_231897_, C_213334_.C_213350_.INSTANCE, 1.0, (p_232038_0_) -> {
      });
      this.f_273910_ = C_213334_.m_257874_("options.accessibility.high_contrast", C_213334_.m_231535_(f_273812_), false, (p_275764_1_) -> {
         C_62_ packrepository = C_3391_.m_91087_().m_91099_();
         boolean flag1 = packrepository.m_10523_().contains("high_contrast");
         if (!flag1 && p_275764_1_) {
            if (packrepository.m_275855_("high_contrast")) {
               this.m_274546_(packrepository);
            }
         } else if (flag1 && !p_275764_1_ && packrepository.m_275853_("high_contrast")) {
            this.m_274546_(packrepository);
         }

      });
      this.f_290977_ = C_213334_.m_257536_("options.accessibility.narrator_hotkey", C_213334_.m_231535_(C_3391_.f_91002_ ? C_4996_.m_237115_("options.accessibility.narrator_hotkey.mac.tooltip") : C_4996_.m_237115_("options.accessibility.narrator_hotkey.tooltip")), true);
      this.f_92126_ = true;
      this.f_92108_ = EnumSet.allOf(C_1144_.class);
      this.f_92127_ = new C_213334_("options.mainHand", C_213334_.m_231498_(), C_213334_.m_231546_(), new C_213334_.C_213340_(Arrays.asList(C_520_.values()), C_520_.f_291347_), C_520_.RIGHT, (p_231841_1_) -> {
         this.m_92172_();
      });
      this.f_92131_ = new C_213334_("options.chat.scale", C_213334_.m_231498_(), (p_232077_0_, p_232077_1_) -> {
         return (C_4996_)(p_232077_1_ == 0.0 ? C_4995_.m_130663_(p_232077_0_, false) : m_231897_(p_232077_0_, p_232077_1_));
      }, C_213334_.C_213350_.INSTANCE, 1.0, (p_232091_0_) -> {
         C_3391_.m_91087_().f_91065_.m_93076_().m_93769_();
      });
      this.f_92132_ = new C_213334_("options.chat.width", C_213334_.m_231498_(), (p_232067_0_, p_232067_1_) -> {
         return m_231952_(p_232067_0_, (int)((double)C_3454_.m_93798_(p_232067_1_) / 4.0571431));
      }, C_213334_.C_213350_.INSTANCE, 1.0, (p_232083_0_) -> {
         C_3391_.m_91087_().f_91065_.m_93076_().m_93769_();
      });
      this.f_92133_ = new C_213334_("options.chat.height.unfocused", C_213334_.m_231498_(), (p_232057_0_, p_232057_1_) -> {
         return m_231952_(p_232057_0_, C_3454_.m_93811_(p_232057_1_));
      }, C_213334_.C_213350_.INSTANCE, C_3454_.m_232477_(), (p_232073_0_) -> {
         C_3391_.m_91087_().f_91065_.m_93076_().m_93769_();
      });
      this.f_92134_ = new C_213334_("options.chat.height.focused", C_213334_.m_231498_(), (p_232044_0_, p_232044_1_) -> {
         return m_231952_(p_232044_0_, C_3454_.m_93811_(p_232044_1_));
      }, C_213334_.C_213350_.INSTANCE, 1.0, (p_232063_0_) -> {
         C_3391_.m_91087_().f_91065_.m_93076_().m_93769_();
      });
      this.f_92135_ = new C_213334_("options.chat.delay_instant", C_213334_.m_231498_(), (p_241715_0_, p_241715_1_) -> {
         return p_241715_1_ <= 0.0 ? C_4996_.m_237115_("options.chat.delay_none") : C_4996_.m_237110_("options.chat.delay", new Object[]{String.format(Locale.ROOT, "%.1f", p_241715_1_)});
      }, (new C_213334_.C_213341_(0, 60)).m_231657_((p_231985_0_) -> {
         return (double)p_231985_0_ / 10.0;
      }, (p_232053_0_) -> {
         return (int)(p_232053_0_ * 10.0);
      }), Codec.doubleRange(0.0, 6.0), 0.0, (p_240679_0_) -> {
         C_3391_.m_91087_().m_240442_().m_240692_(p_240679_0_);
      });
      this.f_263718_ = new C_213334_("options.notifications.display_time", C_213334_.m_231535_(f_263815_), (p_231961_0_, p_231961_1_) -> {
         return m_231921_(p_231961_0_, C_4996_.m_237110_("options.multiplier", new Object[]{p_231961_1_}));
      }, (new C_213334_.C_213341_(5, 100)).m_231657_((p_263860_0_) -> {
         return (double)p_263860_0_ / 10.0;
      }, (p_263861_0_) -> {
         return (int)(p_263861_0_ * 10.0);
      }), Codec.doubleRange(0.5, 10.0), 1.0, (p_231851_0_) -> {
      });
      this.f_92027_ = new C_213334_("options.mipmapLevels", C_213334_.m_231498_(), (p_232032_0_, p_232032_1_) -> {
         if ((double)p_232032_1_ >= 4.0) {
            return m_231921_(p_232032_0_, C_4996_.m_237115_("of.general.max"));
         } else {
            return (C_4996_)(p_232032_1_ == 0 ? C_4995_.m_130663_(p_232032_0_, false) : m_231900_(p_232032_0_, p_232032_1_));
         }
      }, new C_213334_.C_213341_(0, 4), 4, (p_232023_0_) -> {
         this.updateMipmaps();
      });
      this.f_92028_ = true;
      this.f_92029_ = new C_213334_("options.attackIndicator", C_213334_.m_231498_(), C_213334_.m_231546_(), new C_213334_.C_213340_(Arrays.asList(C_3371_.values()), Codec.INT.xmap(C_3371_::m_90509_, C_3371_::m_35965_)), C_3371_.CROSSHAIR, (p_231987_0_) -> {
      });
      this.f_92030_ = C_4621_.MOVEMENT;
      this.f_92031_ = false;
      this.f_168405_ = false;
      this.f_92032_ = new C_213334_("options.biomeBlendRadius", C_213334_.m_231498_(), (p_232015_0_, p_232015_1_) -> {
         int i = p_232015_1_ * 2 + 1;
         return m_231921_(p_232015_0_, C_4996_.m_237115_("options.biomeBlendRadius." + i));
      }, new C_213334_.C_213341_(0, 7, false), 2, (p_232025_0_) -> {
         C_3391_.m_91087_().f_91060_.m_109818_();
      });
      this.f_92033_ = new C_213334_("options.mouseWheelSensitivity", C_213334_.m_231498_(), (p_241716_0_, p_241716_1_) -> {
         return m_231921_(p_241716_0_, C_4996_.m_237113_(String.format(Locale.ROOT, "%.2f", p_241716_1_)));
      }, (new C_213334_.C_213341_(-200, 100)).m_231657_(C_3401_::m_231965_, C_3401_::m_231839_), Codec.doubleRange(m_231965_(-200), m_231965_(100)), m_231965_(0), (p_231946_0_) -> {
      });
      this.f_92034_ = C_213334_.m_231528_("options.rawMouseInput", true, (p_232061_0_) -> {
         C_3161_ window = C_3391_.m_91087_().m_91268_();
         if (window != null) {
            window.m_85424_(p_232061_0_);
         }

      });
      this.f_92035_ = 1;
      this.f_92036_ = C_213334_.m_231525_("options.autoJump", false);
      this.f_256834_ = C_213334_.m_231525_("options.operatorItemsTab", false);
      this.f_92037_ = C_213334_.m_231525_("options.autoSuggestCommands", true);
      this.f_92038_ = C_213334_.m_231525_("options.chat.color", true);
      this.f_92039_ = C_213334_.m_231525_("options.chat.links", true);
      this.f_92040_ = C_213334_.m_231525_("options.chat.links.prompt", true);
      this.f_92041_ = C_213334_.m_231528_("options.vsync", true, (p_232051_0_) -> {
         if (C_3391_.m_91087_().m_91268_() != null) {
            C_3391_.m_91087_().m_91268_().m_85409_(p_232051_0_);
         }

      });
      this.f_92042_ = C_213334_.m_231525_("options.entityShadows", true);
      this.f_92043_ = C_213334_.m_231528_("options.forceUnicodeFont", false, (p_317299_0_) -> {
         m_320153_();
      });
      this.f_314642_ = C_213334_.m_257874_("options.japaneseGlyphVariants", C_213334_.m_231535_(C_4996_.m_237115_("options.japaneseGlyphVariants.tooltip")), m_324081_(), (p_317300_0_) -> {
         m_320153_();
      });
      this.f_92044_ = C_213334_.m_231525_("options.invertMouse", false);
      this.f_92045_ = C_213334_.m_231525_("options.discrete_mouse_scroll", false);
      this.f_92046_ = C_213334_.m_257536_("options.realmsNotifications", C_213334_.m_231535_(f_337252_), true);
      this.f_193762_ = C_213334_.m_257874_("options.allowServerListing", C_213334_.m_231535_(f_231804_), true, (p_232021_1_) -> {
         this.m_92172_();
      });
      this.f_92047_ = C_213334_.m_231525_("options.reducedDebugInfo", false);
      this.f_244498_ = (Map)C_5322_.m_137469_(new EnumMap(C_125_.class), (p_244656_1_) -> {
         C_125_[] var2 = C_125_.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            C_125_ soundsource = var2[var4];
            p_244656_1_.put(soundsource, this.m_247249_("soundCategory." + soundsource.m_12676_(), soundsource));
         }

      });
      this.f_92049_ = C_213334_.m_231525_("options.showSubtitles", false);
      this.f_231807_ = C_213334_.m_257874_("options.directionalAudio", (p_257068_0_) -> {
         return p_257068_0_ ? C_256714_.m_257550_(f_231805_) : C_256714_.m_257550_(f_231806_);
      }, false, (p_263137_0_) -> {
         C_4603_ soundmanager = C_3391_.m_91087_().m_91106_();
         soundmanager.m_194526_();
         soundmanager.m_120367_(C_4561_.m_263171_(C_124_.f_12490_, 1.0F));
      });
      this.f_92050_ = new C_213334_("options.accessibility.text_background", C_213334_.m_231498_(), (p_231975_0_, p_231975_1_) -> {
         return p_231975_1_ ? C_4996_.m_237115_("options.accessibility.text_background.chat") : C_4996_.m_237115_("options.accessibility.text_background.everywhere");
      }, C_213334_.f_231471_, true, (p_231874_0_) -> {
      });
      this.f_92051_ = C_213334_.m_231525_("options.touchscreen", false);
      this.f_92052_ = C_213334_.m_231528_("options.fullscreen", false, (p_231969_1_) -> {
         C_3391_ minecraft = C_3391_.m_91087_();
         if (minecraft.m_91268_() != null && minecraft.m_91268_().m_85440_() != p_231969_1_) {
            minecraft.m_91268_().m_85438_();
            this.m_231829_().m_231514_(minecraft.m_91268_().m_85440_());
         }

      });
      this.f_92080_ = C_213334_.m_231525_("options.viewBobbing", true);
      this.f_92081_ = new C_213334_("key.sneak", C_213334_.m_231498_(), (p_231955_0_, p_231955_1_) -> {
         return p_231955_1_ ? f_231808_ : f_231809_;
      }, C_213334_.f_231471_, false, (p_231989_0_) -> {
      });
      this.f_92082_ = new C_213334_("key.sprint", C_213334_.m_231498_(), (p_231909_0_, p_231909_1_) -> {
         return p_231909_1_ ? f_231808_ : f_231809_;
      }, C_213334_.f_231471_, false, (p_231971_0_) -> {
      });
      this.f_92084_ = C_213334_.m_257536_("options.hideMatchedNames", C_213334_.m_231535_(f_231810_), true);
      this.f_193763_ = C_213334_.m_231525_("options.autosaveIndicator", true);
      this.f_231798_ = C_213334_.m_257536_("options.onlyShowSecureChat", C_213334_.m_231535_(f_231797_), false);
      this.f_92085_ = new C_3387_("key.forward", 87, "key.categories.movement");
      this.f_92086_ = new C_3387_("key.left", 65, "key.categories.movement");
      this.f_92087_ = new C_3387_("key.back", 83, "key.categories.movement");
      this.f_92088_ = new C_3387_("key.right", 68, "key.categories.movement");
      this.f_92089_ = new C_3387_("key.jump", 32, "key.categories.movement");
      C_213334_ var10006 = this.f_92081_;
      Objects.requireNonNull(var10006);
      this.f_92090_ = new C_3419_("key.sneak", 340, "key.categories.movement", var10006::m_231551_);
      var10006 = this.f_92082_;
      Objects.requireNonNull(var10006);
      this.f_92091_ = new C_3419_("key.sprint", 341, "key.categories.movement", var10006::m_231551_);
      this.f_92092_ = new C_3387_("key.inventory", 69, "key.categories.inventory");
      this.f_92093_ = new C_3387_("key.swapOffhand", 70, "key.categories.inventory");
      this.f_92094_ = new C_3387_("key.drop", 81, "key.categories.inventory");
      this.f_92095_ = new C_3387_("key.use", C_3143_.MOUSE, 1, "key.categories.gameplay");
      this.f_92096_ = new C_3387_("key.attack", C_3143_.MOUSE, 0, "key.categories.gameplay");
      this.f_92097_ = new C_3387_("key.pickItem", C_3143_.MOUSE, 2, "key.categories.gameplay");
      this.f_92098_ = new C_3387_("key.chat", 84, "key.categories.multiplayer");
      this.f_92099_ = new C_3387_("key.playerlist", 258, "key.categories.multiplayer");
      this.f_92100_ = new C_3387_("key.command", 47, "key.categories.multiplayer");
      this.f_92101_ = new C_3387_("key.socialInteractions", 80, "key.categories.multiplayer");
      this.f_92102_ = new C_3387_("key.screenshot", 291, "key.categories.misc");
      this.f_92103_ = new C_3387_("key.togglePerspective", 294, "key.categories.misc");
      this.f_92104_ = new C_3387_("key.smoothCamera", C_3140_.f_84822_.m_84873_(), "key.categories.misc");
      this.f_92105_ = new C_3387_("key.fullscreen", 300, "key.categories.misc");
      this.f_92054_ = new C_3387_("key.spectatorOutlines", C_3140_.f_84822_.m_84873_(), "key.categories.misc");
      this.f_92055_ = new C_3387_("key.advancements", 76, "key.categories.misc");
      this.f_92056_ = new C_3387_[]{new C_3387_("key.hotbar.1", 49, "key.categories.inventory"), new C_3387_("key.hotbar.2", 50, "key.categories.inventory"), new C_3387_("key.hotbar.3", 51, "key.categories.inventory"), new C_3387_("key.hotbar.4", 52, "key.categories.inventory"), new C_3387_("key.hotbar.5", 53, "key.categories.inventory"), new C_3387_("key.hotbar.6", 54, "key.categories.inventory"), new C_3387_("key.hotbar.7", 55, "key.categories.inventory"), new C_3387_("key.hotbar.8", 56, "key.categories.inventory"), new C_3387_("key.hotbar.9", 57, "key.categories.inventory")};
      this.f_92057_ = new C_3387_("key.saveToolbarActivator", 67, "key.categories.creative");
      this.f_92058_ = new C_3387_("key.loadToolbarActivator", 88, "key.categories.creative");
      this.f_92059_ = (C_3387_[])ArrayUtils.addAll(new C_3387_[]{this.f_92096_, this.f_92095_, this.f_92085_, this.f_92086_, this.f_92087_, this.f_92088_, this.f_92089_, this.f_92090_, this.f_92091_, this.f_92094_, this.f_92092_, this.f_92098_, this.f_92099_, this.f_92097_, this.f_92100_, this.f_92101_, this.f_92102_, this.f_92103_, this.f_92104_, this.f_92105_, this.f_92054_, this.f_92093_, this.f_92057_, this.f_92058_, this.f_92055_}, this.f_92056_);
      this.f_92111_ = C_3374_.FIRST_PERSON;
      this.f_92066_ = "";
      this.f_92068_ = new C_213334_("options.fov", C_213334_.m_231498_(), (p_231998_0_, p_231998_1_) -> {
         C_4996_ var10000;
         switch (p_231998_1_) {
            case 70:
               var10000 = m_231921_(p_231998_0_, C_4996_.m_237115_("options.fov.min"));
               break;
            case 110:
               var10000 = m_231921_(p_231998_0_, C_4996_.m_237115_("options.fov.max"));
               break;
            default:
               var10000 = m_231900_(p_231998_0_, p_231998_1_);
         }

         return var10000;
      }, new C_213334_.C_213341_(30, 110), Codec.DOUBLE.xmap((p_232006_0_) -> {
         return (int)(p_232006_0_ * 40.0 + 70.0);
      }, (p_232008_0_) -> {
         return ((double)p_232008_0_ - 70.0) / 40.0;
      }), 70, (p_231991_0_) -> {
         C_3391_.m_91087_().f_91060_.m_109826_();
      });
      this.f_260461_ = C_213334_.m_260965_("options.telemetry.button", C_213334_.m_231535_(f_260656_), (p_260741_0_, p_260741_1_) -> {
         C_3391_ minecraft = C_3391_.m_91087_();
         if (!minecraft.m_261210_()) {
            return C_4996_.m_237115_("options.telemetry.state.none");
         } else {
            return p_260741_1_ && minecraft.m_261227_() ? C_4996_.m_237115_("options.telemetry.state.all") : C_4996_.m_237115_("options.telemetry.state.minimal");
         }
      }, false, (p_231948_0_) -> {
      });
      this.f_92069_ = new C_213334_("options.screenEffectScale", C_213334_.m_231535_(f_231799_), C_3401_::m_324758_, C_213334_.C_213350_.INSTANCE, 1.0, (p_231876_0_) -> {
      });
      this.f_92070_ = new C_213334_("options.fovEffectScale", C_213334_.m_231535_(f_231800_), C_3401_::m_324758_, C_213334_.C_213350_.INSTANCE.m_231750_(C_188_::m_144952_, Math::sqrt), Codec.doubleRange(0.0, 1.0), 1.0, (p_231973_0_) -> {
      });
      this.f_231802_ = new C_213334_("options.darknessEffectScale", C_213334_.m_231535_(f_231801_), C_3401_::m_324758_, C_213334_.C_213350_.INSTANCE.m_231750_(C_188_::m_144952_, Math::sqrt), 1.0, (p_231868_0_) -> {
      });
      this.f_267458_ = new C_213334_("options.glintSpeed", C_213334_.m_231535_(f_267409_), C_3401_::m_324758_, C_213334_.C_213350_.INSTANCE, 0.5, (p_241717_0_) -> {
      });
      this.f_267462_ = new C_213334_("options.glintStrength", C_213334_.m_231535_(f_267450_), C_3401_::m_324758_, C_213334_.C_213350_.INSTANCE, 0.75, RenderSystem::setShaderGlintAlpha);
      this.f_268427_ = new C_213334_("options.damageTiltStrength", C_213334_.m_231535_(f_268597_), C_3401_::m_324758_, C_213334_.C_213350_.INSTANCE, 1.0, (p_260742_0_) -> {
      });
      this.f_92071_ = new C_213334_("options.gamma", C_213334_.m_231498_(), (p_231912_0_, p_231912_1_) -> {
         int i = (int)(p_231912_1_ * 100.0);
         if (i == 0) {
            return m_231921_(p_231912_0_, C_4996_.m_237115_("options.gamma.min"));
         } else if (i == 50) {
            return m_231921_(p_231912_0_, C_4996_.m_237115_("options.gamma.default"));
         } else {
            return i == 100 ? m_231921_(p_231912_0_, C_4996_.m_237115_("options.gamma.max")) : m_231900_(p_231912_0_, i);
         }
      }, C_213334_.C_213350_.INSTANCE, 0.5, (p_263858_0_) -> {
      });
      this.f_92072_ = new C_213334_("options.guiScale", C_213334_.m_231498_(), (p_231981_0_, p_231981_1_) -> {
         return p_231981_1_ == 0 ? C_4996_.m_237115_("options.guiScale.auto") : C_4996_.m_237113_(Integer.toString(p_231981_1_));
      }, new C_213334_.C_213337_(0, () -> {
         C_3391_ minecraft = C_3391_.m_91087_();
         return !minecraft.m_91396_() ? 2147483646 : minecraft.m_91268_().m_85385_(0, minecraft.m_91390_());
      }, 2147483646), 0, (p_317301_1_) -> {
         this.f_92060_.m_5741_();
      });
      this.f_92073_ = new C_213334_("options.particles", C_213334_.m_231498_(), C_213334_.m_231546_(), new C_213334_.C_213340_(Arrays.asList(C_3404_.values()), Codec.INT.xmap(C_3404_::m_92196_, C_3404_::m_35965_)), C_3404_.ALL, (p_267500_0_) -> {
      });
      this.f_231803_ = new C_213334_("options.narrator", C_213334_.m_231498_(), (p_240390_1_, p_240390_2_) -> {
         return (C_4996_)(this.f_92060_.m_240477_().m_93316_() ? p_240390_2_.m_91621_() : C_4996_.m_237115_("options.narrator.notavailable"));
      }, new C_213334_.C_213340_(Arrays.asList(C_3398_.values()), Codec.INT.xmap(C_3398_::m_91619_, C_3398_::m_91618_)), C_3398_.OFF, (p_240389_1_) -> {
         this.f_92060_.m_240477_().m_93317_(p_240389_1_);
      });
      this.f_92075_ = "en_us";
      this.f_193764_ = new C_213334_("options.audioDevice", C_213334_.m_231498_(), (p_231918_0_, p_231918_1_) -> {
         if ("".equals(p_231918_1_)) {
            return C_4996_.m_237115_("options.audioDevice.default");
         } else {
            return p_231918_1_.startsWith("OpenAL Soft on ") ? C_4996_.m_237113_(p_231918_1_.substring(C_4600_.f_194470_)) : C_4996_.m_237113_(p_231918_1_);
         }
      }, new C_213334_.C_213344_(() -> {
         return Stream.concat(Stream.of(""), C_3391_.m_91087_().m_91106_().m_194525_().stream()).toList();
      }, (p_232010_0_) -> {
         return C_3391_.m_91087_().m_91396_() && (p_232010_0_ == null || p_232010_0_.isEmpty()) && !C_3391_.m_91087_().m_91106_().m_194525_().contains(p_232010_0_) ? Optional.empty() : Optional.of(p_232010_0_);
      }, Codec.STRING), "", (p_263138_0_) -> {
         C_4603_ soundmanager = C_3391_.m_91087_().m_91106_();
         soundmanager.m_194526_();
         soundmanager.m_120367_(C_4561_.m_263171_(C_124_.f_12490_, 1.0F));
      });
      this.f_263744_ = true;
      this.ofFogType = 1;
      this.ofFogStart = 0.8F;
      this.ofMipmapType = 0;
      this.ofOcclusionFancy = false;
      this.ofSmoothFps = false;
      this.ofSmoothWorld = Config.isSingleProcessor();
      this.ofLazyChunkLoading = Config.isSingleProcessor();
      this.ofRenderRegions = false;
      this.ofSmartAnimations = false;
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
      this.ofCustomFonts = true;
      this.ofCustomColors = true;
      this.ofCustomSky = true;
      this.ofShowCapes = true;
      this.ofConnectedTextures = 2;
      this.ofCustomItems = true;
      this.ofNaturalTextures = false;
      this.ofEmissiveTextures = true;
      this.ofFastMath = false;
      this.ofFastRender = false;
      this.ofDynamicFov = true;
      this.ofAlternateBlocks = true;
      this.ofDynamicLights = 3;
      this.ofCustomEntityModels = true;
      this.ofCustomGuis = true;
      this.ofShowGlErrors = true;
      this.ofScreenshotSize = 1;
      this.ofChatBackground = 0;
      this.ofChatShadow = true;
      this.ofTelemetry = 0;
      this.ofHeldItemTooltips = true;
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
      this.GRAPHICS = this.f_92115_;
      this.field_72 = this.f_92116_;
      this.FRAMERATE_LIMIT = this.f_92113_;
      this.GUI_SCALE = this.f_92072_;
      this.ENTITY_SHADOWS = this.f_92042_;
      this.GAMMA = this.f_92071_;
      this.ATTACK_INDICATOR = this.f_92029_;
      this.PARTICLES = this.f_92073_;
      this.VIEW_BOBBING = this.f_92080_;
      this.AUTOSAVE_INDICATOR = this.f_193763_;
      this.ENTITY_DISTANCE_SCALING = this.f_92112_;
      this.BIOME_BLEND_RADIUS = this.f_92032_;
      this.FULLSCREEN = this.f_92052_;
      this.PRIORITIZE_CHUNK_UPDATES = this.f_193769_;
      this.MIPMAP_LEVELS = this.f_92027_;
      this.SCREEN_EFFECT_SCALE = this.f_92069_;
      this.FOV_EFFECT_SCALE = this.f_92070_;
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
      this.f_92106_ = new C_213334_("options.renderDistance", C_213334_.m_231498_(), (p_231915_0_, p_231915_1_) -> {
         return m_231921_(p_231915_0_, C_4996_.m_237110_("options.chunks", new Object[]{p_231915_1_}));
      }, new C_213334_.C_213341_(2, flag ? maxRenderDist : 16, false), 12, (p_231950_0_) -> {
         C_3391_.m_91087_().f_91060_.m_109826_();
      });
      this.f_193768_ = new C_213334_("options.simulationDistance", C_213334_.m_231498_(), (p_263859_0_, p_263859_1_) -> {
         return m_231921_(p_263859_0_, C_4996_.m_237110_("options.chunks", new Object[]{p_263859_1_}));
      }, new C_213334_.C_213341_(5, flag ? 32 : 16, false), 12, (p_268764_0_) -> {
      });
      this.f_92076_ = C_5322_.m_137581_() == C_5322_.C_5330_.WINDOWS;
      this.RENDER_DISTANCE = this.f_92106_;
      this.SIMULATION_DISTANCE = this.f_193768_;
      this.optionsFileOF = new File(mcDataDir, "optionsof.txt");
      this.m_232035_().m_231514_((Integer)this.m_232035_().getMaxValue());
      this.ofKeyBindZoom = new C_3387_("of.key.zoom", 67, "key.categories.misc");
      this.f_92059_ = (C_3387_[])ArrayUtils.add(this.f_92059_, this.ofKeyBindZoom);
      KeyUtils.fixKeyConflicts(this.f_92059_, new C_3387_[]{this.ofKeyBindZoom});
      this.f_92106_.m_231514_(8);
      this.m_92140_();
      Config.initGameSettings(this);
   }

   public float m_92141_(float opacityIn) {
      return (Boolean)this.f_92050_.m_231551_() ? opacityIn : ((Double)this.m_232104_().m_231551_()).floatValue();
   }

   public int m_92170_(float opacityIn) {
      return (int)(this.m_92141_(opacityIn) * 255.0F) << 24 & -16777216;
   }

   public int m_92143_(int colorIn) {
      return (Boolean)this.f_92050_.m_231551_() ? colorIn : (int)((Double)this.f_92122_.m_231551_() * 255.0) << 24 & -16777216;
   }

   public void m_92159_(C_3387_ keyBindingIn, C_3140_.C_3142_ inputIn) {
      keyBindingIn.m_90848_(inputIn);
      this.m_92169_();
   }

   private void m_323232_(C_313807_ optionAccessIn) {
      optionAccessIn.m_232124_("ao", this.f_92116_);
      optionAccessIn.m_232124_("biomeBlendRadius", this.f_92032_);
      optionAccessIn.m_232124_("enableVsync", this.f_92041_);
      if (this.loadOptions) {
         if ((Boolean)this.m_231817_().m_231551_()) {
            this.f_92113_.m_231514_((Integer)this.f_92113_.getMinValue());
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
      if (this.loadOptions && (Boolean)this.m_231817_().m_231551_()) {
         this.m_232035_().m_231514_((Integer)this.m_232035_().getMinValue());
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

   private void m_168427_(C_141581_ fieldAccessIn) {
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
      List var10003 = this.f_92117_;
      Function var10004 = C_3401_::m_292893_;
      Gson var10005 = f_92078_;
      Objects.requireNonNull(var10005);
      this.f_92117_ = (List)fieldAccessIn.m_142634_("resourcePacks", var10003, var10004, var10005::toJson);
      var10003 = this.f_92118_;
      var10004 = C_3401_::m_292893_;
      var10005 = f_92078_;
      Objects.requireNonNull(var10005);
      this.f_92118_ = (List)fieldAccessIn.m_142634_("incompatibleResourcePacks", var10003, var10004, var10005::toJson);
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
      this.f_92030_ = (C_4621_)fieldAccessIn.m_142634_("tutorialStep", this.f_92030_, C_4621_::m_120642_, C_4621_::m_120639_);
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

   private void processOptionsForge(C_141581_ fieldAccessIn) {
      C_3387_[] var2 = this.f_92059_;
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         C_3387_ keymapping = var2[var4];
         String s = keymapping.m_90865_();
         Object keyModifierNone;
         if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
            Object keyModifier = Reflector.call(keymapping, Reflector.ForgeKeyBinding_getKeyModifier);
            keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
            String var10000 = keymapping.m_90865_();
            s = var10000 + (keyModifier != keyModifierNone ? ":" + String.valueOf(keyModifier) : "");
         }

         String s1 = fieldAccessIn.m_141943_("key_" + keymapping.m_90860_(), s);
         if (!s.equals(s1)) {
            if (Reflector.KeyModifier_valueFromString.exists()) {
               if (s1.indexOf(58) != -1) {
                  String[] pts = s1.split(":");
                  Object keyModifier = Reflector.call(Reflector.KeyModifier_valueFromString, pts[1]);
                  Reflector.call(keymapping, Reflector.ForgeKeyBinding_setKeyModifierAndCode, keyModifier, C_3140_.m_84851_(pts[0]));
               } else {
                  keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
                  Reflector.call(keymapping, Reflector.ForgeKeyBinding_setKeyModifierAndCode, keyModifierNone, C_3140_.m_84851_(s1));
               }
            } else {
               keymapping.m_90848_(C_3140_.m_84851_(s1));
            }
         }
      }

      C_125_[] var10 = C_125_.values();
      var3 = var10.length;

      for(var4 = 0; var4 < var3; ++var4) {
         C_125_ soundsource = var10[var4];
         fieldAccessIn.m_232124_("soundCategory_" + soundsource.m_12676_(), (C_213334_)this.f_244498_.get(soundsource));
      }

      C_1144_[] var11 = C_1144_.values();
      var3 = var11.length;

      for(var4 = 0; var4 < var3; ++var4) {
         C_1144_ playermodelpart = var11[var4];
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

         C_4917_ compoundtag = new C_4917_();
         BufferedReader bufferedreader = Files.newReader(this.f_92110_, Charsets.UTF_8);

         try {
            bufferedreader.lines().forEach((lineIn) -> {
               try {
                  Iterator iterator = f_92107_.split(lineIn).iterator();
                  compoundtag.m_128359_((String)iterator.next(), (String)iterator.next());
               } catch (Exception var3) {
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

         final C_4917_ compoundtag1 = this.m_92164_(compoundtag);
         if (!compoundtag1.m_128441_("graphicsMode") && compoundtag1.m_128441_("fancyGraphics")) {
            if (m_168435_(compoundtag1.m_128461_("fancyGraphics"))) {
               this.f_92115_.m_231514_(C_3383_.FANCY);
            } else {
               this.f_92115_.m_231514_(C_3383_.FAST);
            }
         }

         Consumer processor = limited ? this::processOptionsForge : this::m_168427_;
         processor.accept(new C_141581_(this) {
            @Nullable
            private String m_168458_(String nameIn) {
               return compoundtag1.m_128441_(nameIn) ? compoundtag1.m_128423_(nameIn).m_7916_() : null;
            }

            public void m_232124_(String keyIn, C_213334_ optionIn) {
               String s = this.m_168458_(keyIn);
               if (s != null) {
                  JsonReader jsonreader = new JsonReader(new StringReader(s.isEmpty() ? "\"\"" : s));
                  JsonElement jsonelement = JsonParser.parseReader(jsonreader);
                  DataResult dataresult = optionIn.m_231554_().parse(JsonOps.INSTANCE, jsonelement);
                  dataresult.error().ifPresent((errorIn) -> {
                     C_3401_.f_92077_.error("Error parsing option value " + s + " for option " + String.valueOf(optionIn) + ": " + errorIn.message());
                  });
                  Objects.requireNonNull(optionIn);
                  dataresult.ifSuccess(optionIn::m_231514_);
               }

            }

            public int m_142708_(String keyIn, int valueIn) {
               String s = this.m_168458_(keyIn);
               if (s != null) {
                  try {
                     return Integer.parseInt(s);
                  } catch (NumberFormatException var5) {
                     C_3401_.f_92077_.warn("Invalid integer value for option {} = {}", new Object[]{keyIn, s, var5});
                  }
               }

               return valueIn;
            }

            public boolean m_142682_(String keyIn, boolean valueIn) {
               String s = this.m_168458_(keyIn);
               return s != null ? C_3401_.m_168435_(s) : valueIn;
            }

            public String m_141943_(String keyIn, String valueIn) {
               return (String)MoreObjects.firstNonNull(this.m_168458_(keyIn), valueIn);
            }

            public float m_142432_(String keyIn, float valueIn) {
               String s = this.m_168458_(keyIn);
               if (s == null) {
                  return valueIn;
               } else if (C_3401_.m_168435_(s)) {
                  return 1.0F;
               } else if (C_3401_.m_168440_(s)) {
                  return 0.0F;
               } else {
                  try {
                     return Float.parseFloat(s);
                  } catch (NumberFormatException var5) {
                     C_3401_.f_92077_.warn("Invalid floating point value for option {} = {}", new Object[]{keyIn, s, var5});
                     return valueIn;
                  }
               }
            }

            public Object m_142634_(String keyIn, Object valueIn, Function readerIn, Function writerIn) {
               String s = this.m_168458_(keyIn);
               return s == null ? valueIn : readerIn.apply(s);
            }
         });
         if (compoundtag1.m_128441_("fullscreenResolution")) {
            this.f_92123_ = compoundtag1.m_128461_("fullscreenResolution");
         }

         if (this.f_92060_.m_91268_() != null) {
            this.f_92060_.m_91268_().m_85380_((Integer)this.f_92113_.m_231551_());
         }

         C_3387_.m_90854_();
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

   private C_4917_ m_92164_(C_4917_ nbt) {
      int i = 0;

      try {
         i = Integer.parseInt(nbt.m_128461_("version"));
      } catch (RuntimeException var4) {
      }

      return C_208_.OPTIONS.m_264218_(this.f_92060_.m_91295_(), nbt, i);
   }

   public void m_92169_() {
      this.saveOptions = true;
      if (!Reflector.ClientModLoader_isLoading.exists() || !Reflector.callBoolean(Reflector.ClientModLoader_isLoading)) {
         try {
            final PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.f_92110_), StandardCharsets.UTF_8));

            try {
               printwriter.println("version:" + C_5285_.m_183709_().m_183476_().m_193006_());
               this.m_168427_(new C_141581_(this) {
                  public void m_168490_(String prefixIn) {
                     printwriter.print(prefixIn);
                     printwriter.print(':');
                  }

                  public void m_232124_(String keyIn, C_213334_ optionIn) {
                     optionIn.m_231554_().encodeStart(JsonOps.INSTANCE, optionIn.m_231551_()).ifError((errorIn) -> {
                        Logger var10000 = C_3401_.f_92077_;
                        String var10001 = String.valueOf(optionIn);
                        var10000.error("Error saving option " + var10001 + ": " + String.valueOf(errorIn));
                     }).ifSuccess((jsonElemIn) -> {
                        this.m_168490_(keyIn);
                        printwriter.println(C_3401_.f_92078_.toJson(jsonElemIn));
                     });
                  }

                  public int m_142708_(String keyIn, int valueIn) {
                     this.m_168490_(keyIn);
                     printwriter.println(valueIn);
                     return valueIn;
                  }

                  public boolean m_142682_(String keyIn, boolean valueIn) {
                     this.m_168490_(keyIn);
                     printwriter.println(valueIn);
                     return valueIn;
                  }

                  public String m_141943_(String keyIn, String valueIn) {
                     this.m_168490_(keyIn);
                     printwriter.println(valueIn);
                     return valueIn;
                  }

                  public float m_142432_(String keyIn, float valueIn) {
                     this.m_168490_(keyIn);
                     printwriter.println(valueIn);
                     return valueIn;
                  }

                  public Object m_142634_(String keyIn, Object valueIn, Function readerIn, Function writerIn) {
                     this.m_168490_(keyIn);
                     printwriter.println((String)writerIn.apply(valueIn));
                     return valueIn;
                  }
               });
               if (this.f_92060_.m_91268_().m_85436_().isPresent()) {
                  printwriter.println("fullscreenResolution:" + ((C_3160_)this.f_92060_.m_91268_().m_85436_().get()).m_85342_());
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

   public C_290276_ m_294596_() {
      int i = 0;

      C_1144_ playermodelpart;
      for(Iterator var2 = this.f_92108_.iterator(); var2.hasNext(); i |= playermodelpart.m_36445_()) {
         playermodelpart = (C_1144_)var2.next();
      }

      return new C_290276_(this.f_92075_, (Integer)this.f_92106_.m_231551_(), (C_1139_)this.f_92119_.m_231551_(), (Boolean)this.f_92038_.m_231551_(), i, (C_520_)this.f_92127_.m_231551_(), this.f_92060_.m_167974_(), (Boolean)this.f_193762_.m_231551_());
   }

   public void m_92172_() {
      if (this.f_92060_.f_91074_ != null) {
         this.f_92060_.f_91074_.f_108617_.b(new C_290111_(this.m_294596_()));
      }

   }

   private void m_92154_(C_1144_ modelPart, boolean enable) {
      if (enable) {
         this.f_92108_.add(modelPart);
      } else {
         this.f_92108_.remove(modelPart);
      }

   }

   public boolean m_168416_(C_1144_ partIn) {
      return this.f_92108_.contains(partIn);
   }

   public void m_168418_(C_1144_ partIn, boolean enabledIn) {
      this.m_92154_(partIn, enabledIn);
      this.m_92172_();
   }

   public C_3376_ m_92174_() {
      return this.m_193772_() >= 4 ? (C_3376_)this.f_231792_.m_231551_() : C_3376_.OFF;
   }

   public boolean m_92175_() {
      return this.f_92028_;
   }

   public void setOptionFloatValueOF(C_213334_ option, double val) {
      if (option == Option.CLOUD_HEIGHT) {
         this.ofCloudsHeight = val;
      }

      if (option == Option.AO_LEVEL) {
         this.ofAoLevel = val;
         this.f_92060_.f_91060_.m_109818_();
      }

      int valInt;
      if (option == Option.AA_LEVEL) {
         valInt = (int)val;
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
         valInt = (int)val;
         this.ofAfLevel = valInt;
         this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
         this.f_92060_.m_91088_();
         Shaders.uninit();
      }

      if (option == Option.MIPMAP_TYPE) {
         valInt = (int)val;
         this.ofMipmapType = Config.limit(valInt, 0, 3);
         this.updateMipmaps();
      }

   }

   public double getOptionFloatValueOF(C_213334_ settingOption) {
      if (settingOption == Option.CLOUD_HEIGHT) {
         return this.ofCloudsHeight;
      } else if (settingOption == Option.AO_LEVEL) {
         return this.ofAoLevel;
      } else if (settingOption == Option.AA_LEVEL) {
         return (double)this.ofAaLevel;
      } else if (settingOption == Option.AF_LEVEL) {
         return (double)this.ofAfLevel;
      } else {
         return settingOption == Option.MIPMAP_TYPE ? (double)this.ofMipmapType : 3.4028234663852886E38;
      }
   }

   public void setOptionValueOF(C_213334_ par1EnumOptions, int par2) {
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
         ++this.ofClouds;
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
         ++this.ofRain;
         if (this.ofRain > 3) {
            this.ofRain = 0;
         }
      }

      if (par1EnumOptions == Option.ANIMATED_WATER) {
         ++this.ofAnimatedWater;
         if (this.ofAnimatedWater == 1) {
            ++this.ofAnimatedWater;
         }

         if (this.ofAnimatedWater > 2) {
            this.ofAnimatedWater = 0;
         }
      }

      if (par1EnumOptions == Option.ANIMATED_LAVA) {
         ++this.ofAnimatedLava;
         if (this.ofAnimatedLava == 1) {
            ++this.ofAnimatedLava;
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
         ++this.ofBetterGrass;
         if (this.ofBetterGrass > 3) {
            this.ofBetterGrass = 1;
         }

         this.f_92060_.f_91060_.m_109818_();
      }

      if (par1EnumOptions == Option.CONNECTED_TEXTURES) {
         ++this.ofConnectedTextures;
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
         ++this.ofVignette;
         if (this.ofVignette > 2) {
            this.ofVignette = 0;
         }
      }

      if (par1EnumOptions == Option.CHUNK_UPDATES) {
         ++this.ofChunkUpdates;
         if (this.ofChunkUpdates > 5) {
            this.ofChunkUpdates = 1;
         }
      }

      if (par1EnumOptions == Option.CHUNK_UPDATES_DYNAMIC) {
         this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
      }

      if (par1EnumOptions == Option.TIME) {
         ++this.ofTime;
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
         C_188_.fastMath = this.ofFastMath;
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
         ++this.ofScreenshotSize;
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

   public C_4996_ getKeyComponentOF(C_213334_ option) {
      String str = this.getKeyBindingOF(option);
      if (str == null) {
         return null;
      } else {
         C_4996_ comp = C_4996_.m_237113_(str);
         return comp;
      }
   }

   public String getKeyBindingOF(C_213334_ par1EnumOptions) {
      String var10000 = par1EnumOptions.getResourceKey();
      String var2 = C_4513_.m_118938_(var10000, new Object[0]) + ": ";
      if (var2 == null) {
         var2 = par1EnumOptions.getResourceKey();
      }

      int index;
      if (par1EnumOptions == this.RENDER_DISTANCE) {
         index = (Integer)this.m_231984_().m_231551_();
         String str = C_4513_.m_118938_("of.options.renderDistance.tiny", new Object[0]);
         int baseDist = 2;
         if (index >= 4) {
            str = C_4513_.m_118938_("of.options.renderDistance.short", new Object[0]);
            baseDist = 4;
         }

         if (index >= 8) {
            str = C_4513_.m_118938_("of.options.renderDistance.normal", new Object[0]);
            baseDist = 8;
         }

         if (index >= 16) {
            str = C_4513_.m_118938_("of.options.renderDistance.far", new Object[0]);
            baseDist = 16;
         }

         if (index >= 32) {
            str = Lang.get("of.options.renderDistance.extreme");
            baseDist = 32;
         }

         if (index >= 48) {
            str = Lang.get("of.options.renderDistance.insane");
            baseDist = 48;
         }

         if (index >= 64) {
            str = Lang.get("of.options.renderDistance.ludicrous");
            baseDist = 64;
         }

         int diff = (Integer)this.m_231984_().m_231551_() - baseDist;
         String descr = str;
         if (diff > 0) {
            descr = str + "+";
         }

         return var2 + index + " " + descr;
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
         index = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
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
         index = indexOf(this.ofTelemetry, OF_TELEMETRY);
         return var2 + getTranslation(KEYS_TELEMETRY, index);
      } else if (par1EnumOptions.isProgressOption()) {
         double d0 = (Double)par1EnumOptions.m_231551_();
         return d0 == 0.0 ? var2 + C_4513_.m_118938_("options.off", new Object[0]) : var2 + (int)(d0 * 100.0) + "%";
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

         List persistentOptions = this.getPersistentOptions();
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(ofReadFile), StandardCharsets.UTF_8));
         String s = "";

         while((s = bufferedreader.readLine()) != null) {
            try {
               String[] as = s.split(":");
               if (as[0].equals("ofRenderDistanceChunks") && as.length >= 2) {
                  this.f_92106_.m_231514_(Integer.valueOf(as[1]));
                  this.f_92106_.m_231514_(Config.limit((Integer)this.f_92106_.m_231551_(), 2, 1024));
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
                  this.ofAoLevel = (double)Float.valueOf(as[1]);
                  this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0, 1.0);
               }

               if (as[0].equals("ofClouds") && as.length >= 2) {
                  this.ofClouds = Integer.valueOf(as[1]);
                  this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                  this.updateRenderClouds();
               }

               if (as[0].equals("ofCloudsHeight") && as.length >= 2) {
                  this.ofCloudsHeight = (double)Float.valueOf(as[1]);
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
                  C_188_.fastMath = this.ofFastMath;
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
                  this.ofKeyBindZoom.m_90848_(C_3140_.m_84851_(as[1]));
               }

               String key = as[0];
               String val = as[1];
               Iterator var8 = persistentOptions.iterator();

               while(var8.hasNext()) {
                  IPersitentOption ipo = (IPersitentOption)var8.next();
                  if (Config.equals(key, ipo.getSaveKey())) {
                     ipo.loadValue(this, val);
                  }
               }
            } catch (Exception var10) {
               Config.dbg("Skipping bad option: " + s);
               var10.printStackTrace();
            }
         }

         KeyUtils.fixKeyConflicts(this.f_92059_, new C_3387_[]{this.ofKeyBindZoom});
         C_3387_.m_90854_();
         bufferedreader.close();
      } catch (Exception var11) {
         Config.warn("Failed to load options");
         var11.printStackTrace();
      }

   }

   private List getPersistentOptions() {
      List list = new ArrayList();
      Iterator var2 = C_213334_.OPTIONS_BY_KEY.values().iterator();

      while(var2.hasNext()) {
         C_213334_ opt = (C_213334_)var2.next();
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
         String var10001 = this.ofKeyBindZoom.m_90860_();
         printwriter.println("key_" + var10001 + ":" + this.ofKeyBindZoom.m_90865_());
         List persistentOptions = this.getPersistentOptions();
         Iterator var3 = persistentOptions.iterator();

         while(var3.hasNext()) {
            IPersitentOption ipo = (IPersitentOption)var3.next();
            var10001 = ipo.getSaveKey();
            printwriter.println(var10001 + ":" + ipo.getSaveText(this));
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
            this.f_231792_.m_231514_(C_3376_.FAST);
            break;
         case 2:
            this.f_231792_.m_231514_(C_3376_.FANCY);
            break;
         case 3:
            this.f_231792_.m_231514_(C_3376_.OFF);
            break;
         default:
            if (this.m_232060_().m_231551_() != C_3383_.FAST) {
               this.f_231792_.m_231514_(C_3376_.FANCY);
            } else {
               this.f_231792_.m_231514_(C_3376_.FAST);
            }
      }

      if (this.m_232060_().m_231551_() == C_3383_.FABULOUS) {
         C_4134_ wr = C_3391_.m_91087_().f_91060_;
         if (wr != null) {
            C_3106_ framebuffer = wr.m_109832_();
            if (framebuffer != null) {
               framebuffer.m_83954_(C_3391_.f_91002_);
            }
         }
      }

   }

   public void resetSettings() {
      this.f_92106_.m_231514_(8);
      this.f_193768_.m_231514_(8);
      this.f_92112_.m_231514_(1.0);
      this.f_92080_.m_231514_(true);
      this.f_92113_.m_231514_((Integer)this.f_92113_.getMaxValue());
      this.f_92041_.m_231514_(false);
      this.updateVSync();
      this.f_92027_.m_231514_(4);
      this.f_92115_.m_231514_(C_3383_.FANCY);
      this.f_92116_.m_231514_(true);
      this.f_231792_.m_231514_(C_3376_.FANCY);
      this.f_92068_.m_231514_(70);
      this.f_92071_.m_231514_(0.0);
      this.f_92072_.m_231514_(0);
      this.f_92073_.m_231514_(C_3404_.ALL);
      this.ofHeldItemTooltips = true;
      this.f_92043_.m_231514_(false);
      this.f_193769_.m_231514_(C_183059_.NONE);
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
         this.f_92060_.m_91268_().m_85409_((Boolean)this.f_92041_.m_231551_());
      }

   }

   public void updateMipmaps() {
      this.f_92060_.m_91312_((Integer)this.f_92027_.m_231551_());
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
      this.f_92073_.m_231514_(flag ? C_3404_.ALL : C_3404_.MINIMAL);
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
         ++index;
         if (index >= vals.length) {
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
      for(int i = 0; i < vals.length; ++i) {
         if (vals[i] == val) {
            return i;
         }
      }

      return -1;
   }

   public static int indexOf(double val, double[] vals) {
      for(int i = 0; i < vals.length; ++i) {
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

      return C_4513_.m_118938_(strArray[index], new Object[0]);
   }

   public static C_4996_ genericValueLabel(String keyIn, int valueIn) {
      return m_231921_(C_4996_.m_237115_(keyIn), C_4996_.m_237113_(Integer.toString(valueIn)));
   }

   public static C_4996_ genericValueLabel(String keyIn, String valueKeyIn) {
      return m_231921_(C_4996_.m_237115_(keyIn), C_4996_.m_237115_(valueKeyIn));
   }

   public static C_4996_ genericValueLabel(String keyIn, String valueKeyIn, int valueIn) {
      return m_231921_(C_4996_.m_237115_(keyIn), C_4996_.m_237110_(valueKeyIn, new Object[]{Integer.toString(valueIn)}));
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

   public void m_92145_(C_62_ resourcePackListIn) {
      Set set = Sets.newLinkedHashSet();
      Iterator iterator = this.f_92117_.iterator();

      while(true) {
         while(iterator.hasNext()) {
            String s = (String)iterator.next();
            C_58_ pack = resourcePackListIn.m_10507_(s);
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
         return;
      }
   }

   public C_3374_ m_92176_() {
      return this.f_92111_;
   }

   public void m_92157_(C_3374_ pointOfViewIn) {
      this.f_92111_ = pointOfViewIn;
   }

   private static List m_292893_(String stringIn) {
      List list = (List)C_181_.m_13785_(f_92078_, stringIn, f_290931_);
      return (List)(list != null ? list : Lists.newArrayList());
   }

   public File m_168450_() {
      return this.f_92110_;
   }

   public String m_168451_() {
      final List list = new ArrayList();
      this.m_323232_(new C_313807_(this) {
         public void m_232124_(String keyIn, C_213334_ optionIn) {
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
      return (String)list.stream().sorted(Comparator.comparing(Pair::getFirst)).map((pairIn) -> {
         String var10000 = (String)pairIn.getFirst();
         return var10000 + ": " + String.valueOf(pairIn.getSecond());
      }).collect(Collectors.joining(System.lineSeparator()));
   }

   public void m_193770_(int valueIn) {
      this.f_193765_ = valueIn;
   }

   public int m_193772_() {
      return this.f_193765_ > 0 ? Math.min((Integer)this.f_92106_.m_231551_(), this.f_193765_) : (Integer)this.f_92106_.m_231551_();
   }

   private static C_4996_ m_231952_(C_4996_ componentIn, int valueIn) {
      return C_4996_.m_237110_("options.pixel_value", new Object[]{componentIn, valueIn});
   }

   private static C_4996_ m_231897_(C_4996_ componentIn, double valueIn) {
      return C_4996_.m_237110_("options.percent_value", new Object[]{componentIn, (int)(valueIn * 100.0)});
   }

   public static C_4996_ m_231921_(C_4996_ componentIn, C_4996_ valueIn) {
      return C_4996_.m_237110_("options.generic_value", new Object[]{componentIn, valueIn});
   }

   public static C_4996_ m_231900_(C_4996_ componentIn, int valueIn) {
      return m_231921_(componentIn, C_4996_.m_237113_(Integer.toString(valueIn)));
   }

   public static C_4996_ m_338389_(C_4996_ componentIn, int valueIn) {
      return valueIn == 0 ? m_231921_(componentIn, C_4995_.f_130654_) : m_231900_(componentIn, valueIn);
   }

   private static C_4996_ m_324758_(C_4996_ componentIn, double valueIn) {
      return valueIn == 0.0 ? m_231921_(componentIn, C_4995_.f_130654_) : m_231897_(componentIn, valueIn);
   }

   static {
      f_231794_ = C_4996_.m_237110_("options.graphics.fabulous.tooltip", new Object[]{C_4996_.m_237115_("options.graphics.fabulous").m_130940_(C_4856_.ITALIC)});
      f_231785_ = C_4996_.m_237115_("options.graphics.fancy.tooltip");
      f_231786_ = C_4996_.m_237115_("options.prioritizeChunkUpdates.none.tooltip");
      f_231787_ = C_4996_.m_237115_("options.prioritizeChunkUpdates.byPlayer.tooltip");
      f_231788_ = C_4996_.m_237115_("options.prioritizeChunkUpdates.nearby.tooltip");
      f_315005_ = C_4996_.m_237115_("options.accessibility.menu_background_blurriness.tooltip");
      f_273812_ = C_4996_.m_237115_("options.accessibility.high_contrast.tooltip");
      f_263815_ = C_4996_.m_237115_("options.notifications.display_time.tooltip");
      f_337252_ = C_4996_.m_237115_("options.realmsNotifications.tooltip");
      f_231804_ = C_4996_.m_237115_("options.allowServerListing.tooltip");
      f_231805_ = C_4996_.m_237115_("options.directionalAudio.on.tooltip");
      f_231806_ = C_4996_.m_237115_("options.directionalAudio.off.tooltip");
      f_231808_ = C_4996_.m_237115_("options.key.toggle");
      f_231809_ = C_4996_.m_237115_("options.key.hold");
      f_231810_ = C_4996_.m_237115_("options.hideMatchedNames.tooltip");
      f_231797_ = C_4996_.m_237115_("options.onlyShowSecureChat.tooltip");
      f_260656_ = C_4996_.m_237110_("options.telemetry.button.tooltip", new Object[]{C_4996_.m_237115_("options.telemetry.state.minimal"), C_4996_.m_237115_("options.telemetry.state.all")});
      f_231799_ = C_4996_.m_237115_("options.screenEffectScale.tooltip");
      f_231800_ = C_4996_.m_237115_("options.fovEffectScale.tooltip");
      f_231801_ = C_4996_.m_237115_("options.darknessEffectScale.tooltip");
      f_267409_ = C_4996_.m_237115_("options.glintSpeed.tooltip");
      f_267450_ = C_4996_.m_237115_("options.glintStrength.tooltip");
      f_268597_ = C_4996_.m_237115_("options.damageTiltStrength.tooltip");
      VALS_FAST_FANCY_OFF = new int[]{1, 2, 3};
      OF_TREES_VALUES = new int[]{0, 1, 4, 2};
      OF_DYNAMIC_LIGHTS = new int[]{3, 1, 2};
      KEYS_DYNAMIC_LIGHTS = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
      OF_TELEMETRY = new int[]{0, 1, 2};
      KEYS_TELEMETRY = new String[]{"options.on", "of.general.anonymous", "options.off"};
   }

   interface C_313807_ {
      void m_232124_(String var1, C_213334_ var2);
   }

   interface C_141581_ extends C_313807_ {
      int m_142708_(String var1, int var2);

      boolean m_142682_(String var1, boolean var2);

      String m_141943_(String var1, String var2);

      float m_142432_(String var1, float var2);

      Object m_142634_(String var1, Object var2, Function var3, Function var4);
   }
}
