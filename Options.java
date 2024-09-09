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
import net.minecraft.src.C_1139_;
import net.minecraft.src.C_1144_;
import net.minecraft.src.C_124_;
import net.minecraft.src.C_125_;
import net.minecraft.src.C_181_;
import net.minecraft.src.C_183059_;
import net.minecraft.src.C_208_;
import net.minecraft.src.C_256714_;
import net.minecraft.src.C_290111_;
import net.minecraft.src.C_290276_;
import net.minecraft.src.C_3140_;
import net.minecraft.src.C_3160_;
import net.minecraft.src.C_3371_;
import net.minecraft.src.C_3374_;
import net.minecraft.src.C_3376_;
import net.minecraft.src.C_3383_;
import net.minecraft.src.C_3387_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3398_;
import net.minecraft.src.C_3404_;
import net.minecraft.src.C_3419_;
import net.minecraft.src.C_4127_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4561_;
import net.minecraft.src.C_4600_;
import net.minecraft.src.C_4603_;
import net.minecraft.src.C_4621_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4995_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5012_;
import net.minecraft.src.C_520_;
import net.minecraft.src.C_5285_;
import net.minecraft.src.C_58_;
import net.minecraft.src.C_62_;
import net.minecraft.src.C_3140_.C_3142_;
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

public class Options {
   static final Logger af = LogUtils.getLogger();
   static final Gson ag = new Gson();
   private static final TypeToken<List<String>> ah = new TypeToken<List<String>>() {
   };
   public static final int a = 2;
   public static final int b = 4;
   public static final int c = 8;
   public static final int d = 12;
   public static final int e = 16;
   public static final int f = 32;
   private static final Splitter ai = Splitter.on(':').limit(2);
   public static final String g = "";
   private static final C_4996_ aj = C_4996_.m_237115_("options.darkMojangStudiosBackgroundColor.tooltip");
   private final OptionInstance<Boolean> ak = OptionInstance.a("options.darkMojangStudiosBackgroundColor", OptionInstance.a(aj), false);
   private static final C_4996_ al = C_4996_.m_237115_("options.hideLightningFlashes.tooltip");
   private final OptionInstance<Boolean> am = OptionInstance.a("options.hideLightningFlashes", OptionInstance.a(al), false);
   private static final C_4996_ an = C_4996_.m_237115_("options.hideSplashTexts.tooltip");
   private final OptionInstance<Boolean> ao = OptionInstance.a("options.hideSplashTexts", OptionInstance.a(an), false);
   private final OptionInstance<Double> ap = new OptionInstance<>("options.sensitivity", OptionInstance.a(), (p_232095_0_, p_232095_1_) -> {
      if (p_232095_1_ == 0.0) {
         return a(p_232095_0_, C_4996_.m_237115_("options.sensitivity.min"));
      } else {
         return p_232095_1_ == 1.0 ? a(p_232095_0_, C_4996_.m_237115_("options.sensitivity.max")) : a(p_232095_0_, 2.0 * p_232095_1_);
      }
   }, OptionInstance.m.a, 0.5, p_232114_0_ -> {
   });
   private final OptionInstance<Integer> aq;
   private final OptionInstance<Integer> ar;
   private int as = 0;
   private final OptionInstance<Double> at = new OptionInstance<>(
      "options.entityDistanceScaling",
      OptionInstance.a(),
      Options::a,
      new OptionInstance.f(2, 20).a(p_232019_0_ -> (double)p_232019_0_ / 4.0, p_232111_0_ -> (int)(p_232111_0_ * 4.0)),
      Codec.doubleRange(0.5, 5.0),
      1.0,
      p_232040_0_ -> {
      }
   );
   public static final int h = 260;
   private final OptionInstance<Integer> au = new OptionInstance<>(
      "options.framerateLimit",
      OptionInstance.a(),
      (p_232047_0_, p_232047_1_) -> {
         if (this.N().c()) {
            return a(p_232047_0_, C_4996_.m_237115_("of.options.framerateLimit.vsync"));
         } else {
            return p_232047_1_ == 260
               ? a(p_232047_0_, C_4996_.m_237115_("options.framerateLimit.max"))
               : a(p_232047_0_, C_4996_.m_237110_("options.framerate", new Object[]{p_232047_1_}));
         }
      },
      new OptionInstance.f(0, 52).a(p_232002_0_ -> p_232002_0_ * 5, p_232093_0_ -> p_232093_0_ / 5),
      Codec.intRange(0, 260),
      120,
      p_232085_0_ -> {
         this.N().a(p_232085_0_ == 0);
         C_3391_.m_91087_().aM().a(p_232085_0_);
      }
   );
   private final OptionInstance<C_3376_> av = new OptionInstance<>(
      "options.renderClouds",
      OptionInstance.a(),
      OptionInstance.b(),
      new OptionInstance.e<>(
         Arrays.asList(C_3376_.values()), Codec.withAlternative(C_3376_.f_291249_, Codec.BOOL, p_232081_0_ -> p_232081_0_ ? C_3376_.FANCY : C_3376_.OFF)
      ),
      C_3376_.FANCY,
      p_231853_0_ -> {
         if (C_3391_.m_91085_()) {
            RenderTarget rendertarget = C_3391_.m_91087_().f.x();
            if (rendertarget != null) {
               rendertarget.b(C_3391_.f_91002_);
            }
         }
      }
   );
   private static final C_4996_ aw = C_4996_.m_237115_("options.graphics.fast.tooltip");
   private static final C_4996_ ax = C_4996_.m_237110_(
      "options.graphics.fabulous.tooltip", new Object[]{C_4996_.m_237115_("options.graphics.fabulous").m_130940_(C_4856_.ITALIC)}
   );
   private static final C_4996_ ay = C_4996_.m_237115_("options.graphics.fancy.tooltip");
   private final OptionInstance<C_3383_> az = new OptionInstance<>(
      "options.graphics",
      p_317296_0_ -> {
         if (Boolean.TRUE) {
            return null;
         } else {
            return switch (p_317296_0_) {
               case FANCY -> C_256714_.m_257550_(ay);
               case FAST -> C_256714_.m_257550_(aw);
               case FABULOUS -> C_256714_.m_257550_(ax);
               default -> throw new MatchException(null, null);
            };
         }
      },
      (p_231903_0_, p_231903_1_) -> {
         C_5012_ mutablecomponent = C_4996_.m_237115_(p_231903_1_.m_35968_());
         return p_231903_1_ == C_3383_.FABULOUS ? mutablecomponent.m_130940_(C_4856_.ITALIC) : mutablecomponent;
      },
      new OptionInstance.a<>(
         Arrays.asList(C_3383_.values()),
         (List<C_3383_>)Stream.of(C_3383_.values()).filter(p_231942_0_ -> p_231942_0_ != C_3383_.FABULOUS).collect(Collectors.toList()),
         () -> !Config.isShaders() && GLX.isUsingFBOs() ? C_3391_.m_91087_().m_91396_() && C_3391_.m_91087_().m_91105_().m_109251_() : true,
         (p_231861_0_, p_231861_1_) -> {
            C_3391_ minecraft = C_3391_.m_91087_();
            C_4127_ gpuwarnlistmanager = minecraft.m_91105_();
            if (p_231861_1_ == C_3383_.FABULOUS && gpuwarnlistmanager.m_109240_()) {
               gpuwarnlistmanager.m_109247_();
            } else {
               p_231861_0_.a(p_231861_1_);
               this.updateRenderClouds();
               minecraft.f.f();
            }
         },
         Codec.INT.xmap(C_3383_::m_90774_, C_3383_::m_35965_)
      ),
      C_3383_.FANCY,
      p_231855_0_ -> {
      }
   );
   private final OptionInstance<Boolean> aA = OptionInstance.a("options.ao", true, p_231849_0_ -> C_3391_.m_91087_().f.f());
   private static final C_4996_ aB = C_4996_.m_237115_("options.prioritizeChunkUpdates.none.tooltip");
   private static final C_4996_ aC = C_4996_.m_237115_("options.prioritizeChunkUpdates.byPlayer.tooltip");
   private static final C_4996_ aD = C_4996_.m_237115_("options.prioritizeChunkUpdates.nearby.tooltip");
   private final OptionInstance<C_183059_> aE = new OptionInstance<>(
      "options.prioritizeChunkUpdates",
      p_317297_0_ -> {
         if (Boolean.TRUE) {
            return null;
         } else {
            return switch (p_317297_0_) {
               case NONE -> C_256714_.m_257550_(aB);
               case PLAYER_AFFECTED -> C_256714_.m_257550_(aC);
               case NEARBY -> C_256714_.m_257550_(aD);
               default -> throw new MatchException(null, null);
            };
         }
      },
      OptionInstance.b(),
      new OptionInstance.e<>(Arrays.asList(C_183059_.values()), Codec.INT.xmap(C_183059_::m_193787_, C_183059_::m_35965_)),
      C_183059_.NONE,
      p_231870_0_ -> {
      }
   );
   public List<String> i = Lists.newArrayList();
   public List<String> j = Lists.newArrayList();
   private final OptionInstance<C_1139_> aF = new OptionInstance<>(
      "options.chat.visibility",
      OptionInstance.a(),
      OptionInstance.b(),
      new OptionInstance.e<>(Arrays.asList(C_1139_.values()), Codec.INT.xmap(C_1139_::m_35966_, C_1139_::m_35965_)),
      C_1139_.FULL,
      p_231843_0_ -> {
      }
   );
   private final OptionInstance<Double> aG = new OptionInstance<>(
      "options.chat.opacity",
      OptionInstance.a(),
      (p_232087_0_, p_232087_1_) -> a(p_232087_0_, p_232087_1_ * 0.9 + 0.1),
      OptionInstance.m.a,
      1.0,
      p_232105_0_ -> C_3391_.m_91087_().l.d().b()
   );
   private final OptionInstance<Double> aH = new OptionInstance<>(
      "options.chat.line_spacing", OptionInstance.a(), Options::a, OptionInstance.m.a, 0.0, p_232102_0_ -> {
      }
   );
   private static final C_4996_ aI = C_4996_.m_237115_("options.accessibility.menu_background_blurriness.tooltip");
   private static final int aJ = 5;
   private final OptionInstance<Integer> aK = new OptionInstance<>(
      "options.accessibility.menu_background_blurriness", OptionInstance.a(aI), Options::b, new OptionInstance.f(0, 10), 5, p_232108_0_ -> {
      }
   );
   private final OptionInstance<Double> aL = new OptionInstance<>(
      "options.accessibility.text_background_opacity", OptionInstance.a(), Options::a, OptionInstance.m.a, 0.5, p_232099_0_ -> C_3391_.m_91087_().l.d().b()
   );
   private final OptionInstance<Double> aM = new OptionInstance<>(
      "options.accessibility.panorama_speed", OptionInstance.a(), Options::a, OptionInstance.m.a, 1.0, p_232038_0_ -> {
      }
   );
   private static final C_4996_ aN = C_4996_.m_237115_("options.accessibility.high_contrast.tooltip");
   private final OptionInstance<Boolean> aO = OptionInstance.a("options.accessibility.high_contrast", OptionInstance.a(aN), false, p_275764_1_ -> {
      C_62_ packrepository = C_3391_.m_91087_().m_91099_();
      boolean flag1 = packrepository.m_10523_().contains("high_contrast");
      if (!flag1 && p_275764_1_) {
         if (packrepository.m_275855_("high_contrast")) {
            this.a(packrepository);
         }
      } else if (flag1 && !p_275764_1_ && packrepository.m_275853_("high_contrast")) {
         this.a(packrepository);
      }
   });
   private final OptionInstance<Boolean> aP = OptionInstance.a(
      "options.accessibility.narrator_hotkey",
      OptionInstance.a(
         C_3391_.f_91002_
            ? C_4996_.m_237115_("options.accessibility.narrator_hotkey.mac.tooltip")
            : C_4996_.m_237115_("options.accessibility.narrator_hotkey.tooltip")
      ),
      true
   );
   @Nullable
   public String k;
   public boolean l;
   public boolean m;
   public boolean n = true;
   private final Set<C_1144_> aQ = EnumSet.allOf(C_1144_.class);
   private final OptionInstance<C_520_> aR = new OptionInstance<>(
      "options.mainHand",
      OptionInstance.a(),
      OptionInstance.b(),
      new OptionInstance.e<>(Arrays.asList(C_520_.values()), C_520_.f_291347_),
      C_520_.RIGHT,
      p_231841_1_ -> this.ay()
   );
   public int o;
   public int p;
   private final OptionInstance<Double> aS = new OptionInstance<>(
      "options.chat.scale",
      OptionInstance.a(),
      (p_232077_0_, p_232077_1_) -> (C_4996_)(p_232077_1_ == 0.0 ? C_4995_.m_130663_(p_232077_0_, false) : a(p_232077_0_, p_232077_1_)),
      OptionInstance.m.a,
      1.0,
      p_232091_0_ -> C_3391_.m_91087_().l.d().b()
   );
   private final OptionInstance<Double> aT = new OptionInstance<>(
      "options.chat.width",
      OptionInstance.a(),
      (p_232067_0_, p_232067_1_) -> c(p_232067_0_, (int)((double)ChatComponent.a(p_232067_1_) / 4.0571431)),
      OptionInstance.m.a,
      1.0,
      p_232083_0_ -> C_3391_.m_91087_().l.d().b()
   );
   private final OptionInstance<Double> aU = new OptionInstance<>(
      "options.chat.height.unfocused",
      OptionInstance.a(),
      (p_232057_0_, p_232057_1_) -> c(p_232057_0_, ChatComponent.b(p_232057_1_)),
      OptionInstance.m.a,
      ChatComponent.i(),
      p_232073_0_ -> C_3391_.m_91087_().l.d().b()
   );
   private final OptionInstance<Double> aV = new OptionInstance<>(
      "options.chat.height.focused",
      OptionInstance.a(),
      (p_232044_0_, p_232044_1_) -> c(p_232044_0_, ChatComponent.b(p_232044_1_)),
      OptionInstance.m.a,
      1.0,
      p_232063_0_ -> C_3391_.m_91087_().l.d().b()
   );
   private final OptionInstance<Double> aW = new OptionInstance<>(
      "options.chat.delay_instant",
      OptionInstance.a(),
      (p_241715_0_, p_241715_1_) -> p_241715_1_ <= 0.0
            ? C_4996_.m_237115_("options.chat.delay_none")
            : C_4996_.m_237110_("options.chat.delay", new Object[]{String.format(Locale.ROOT, "%.1f", p_241715_1_)}),
      new OptionInstance.f(0, 60).a(p_231985_0_ -> (double)p_231985_0_ / 10.0, p_232053_0_ -> (int)(p_232053_0_ * 10.0)),
      Codec.doubleRange(0.0, 6.0),
      0.0,
      p_240679_0_ -> C_3391_.m_91087_().m_240442_().m_240692_(p_240679_0_)
   );
   private static final C_4996_ aX = C_4996_.m_237115_("options.notifications.display_time.tooltip");
   private final OptionInstance<Double> aY = new OptionInstance<>(
      "options.notifications.display_time",
      OptionInstance.a(aX),
      (p_231961_0_, p_231961_1_) -> a(p_231961_0_, C_4996_.m_237110_("options.multiplier", new Object[]{p_231961_1_})),
      new OptionInstance.f(5, 100).a(p_263860_0_ -> (double)p_263860_0_ / 10.0, p_263861_0_ -> (int)(p_263861_0_ * 10.0)),
      Codec.doubleRange(0.5, 10.0),
      1.0,
      p_231851_0_ -> {
      }
   );
   private final OptionInstance<Integer> aZ = new OptionInstance<>("options.mipmapLevels", OptionInstance.a(), (p_232032_0_, p_232032_1_) -> {
      if ((double)p_232032_1_.intValue() >= 4.0) {
         return a(p_232032_0_, C_4996_.m_237115_("of.general.max"));
      } else {
         return (C_4996_)(p_232032_1_ == 0 ? C_4995_.m_130663_(p_232032_0_, false) : a(p_232032_0_, p_232032_1_));
      }
   }, new OptionInstance.f(0, 4), 4, p_232023_0_ -> this.updateMipmaps());
   public boolean q = true;
   private final OptionInstance<C_3371_> ba = new OptionInstance<>(
      "options.attackIndicator",
      OptionInstance.a(),
      OptionInstance.b(),
      new OptionInstance.e<>(Arrays.asList(C_3371_.values()), Codec.INT.xmap(C_3371_::m_90509_, C_3371_::m_35965_)),
      C_3371_.CROSSHAIR,
      p_231987_0_ -> {
      }
   );
   public C_4621_ r = C_4621_.MOVEMENT;
   public boolean s = false;
   public boolean t = false;
   private final OptionInstance<Integer> bb = new OptionInstance<>("options.biomeBlendRadius", OptionInstance.a(), (p_232015_0_, p_232015_1_) -> {
      int i = p_232015_1_ * 2 + 1;
      return a(p_232015_0_, C_4996_.m_237115_("options.biomeBlendRadius." + i));
   }, new OptionInstance.f(0, 7, false), 2, p_232025_0_ -> C_3391_.m_91087_().f.f());
   private final OptionInstance<Double> bc = new OptionInstance<>(
      "options.mouseWheelSensitivity",
      OptionInstance.a(),
      (p_241716_0_, p_241716_1_) -> a(p_241716_0_, C_4996_.m_237113_(String.format(Locale.ROOT, "%.2f", p_241716_1_))),
      new OptionInstance.f(-200, 100).a(Options::c, Options::a),
      Codec.doubleRange(c(-200), c(100)),
      c(0),
      p_231946_0_ -> {
      }
   );
   private final OptionInstance<Boolean> bd = OptionInstance.a("options.rawMouseInput", true, p_232061_0_ -> {
      Window window = C_3391_.m_91087_().aM();
      if (window != null) {
         window.b(p_232061_0_);
      }
   });
   public int u = 1;
   private final OptionInstance<Boolean> be = OptionInstance.a("options.autoJump", false);
   private final OptionInstance<Boolean> bf = OptionInstance.a("options.operatorItemsTab", false);
   private final OptionInstance<Boolean> bg = OptionInstance.a("options.autoSuggestCommands", true);
   private final OptionInstance<Boolean> bh = OptionInstance.a("options.chat.color", true);
   private final OptionInstance<Boolean> bi = OptionInstance.a("options.chat.links", true);
   private final OptionInstance<Boolean> bj = OptionInstance.a("options.chat.links.prompt", true);
   private final OptionInstance<Boolean> bk = OptionInstance.a("options.vsync", true, p_232051_0_ -> {
      if (C_3391_.m_91087_().aM() != null) {
         C_3391_.m_91087_().aM().a(p_232051_0_);
      }
   });
   private final OptionInstance<Boolean> bl = OptionInstance.a("options.entityShadows", true);
   private final OptionInstance<Boolean> bm = OptionInstance.a("options.forceUnicodeFont", false, p_317299_0_ -> aF());
   private final OptionInstance<Boolean> bn = OptionInstance.a(
      "options.japaneseGlyphVariants", OptionInstance.a(C_4996_.m_237115_("options.japaneseGlyphVariants.tooltip")), aG(), p_317300_0_ -> aF()
   );
   private final OptionInstance<Boolean> bo = OptionInstance.a("options.invertMouse", false);
   private final OptionInstance<Boolean> bp = OptionInstance.a("options.discrete_mouse_scroll", false);
   private static final C_4996_ bq = C_4996_.m_237115_("options.realmsNotifications.tooltip");
   private final OptionInstance<Boolean> br = OptionInstance.a("options.realmsNotifications", OptionInstance.a(bq), true);
   private static final C_4996_ bs = C_4996_.m_237115_("options.allowServerListing.tooltip");
   private final OptionInstance<Boolean> bt = OptionInstance.a("options.allowServerListing", OptionInstance.a(bs), true, p_232021_1_ -> this.ay());
   private final OptionInstance<Boolean> bu = OptionInstance.a("options.reducedDebugInfo", false);
   private final Map<C_125_, OptionInstance<Double>> bv = Util.a(new EnumMap(C_125_.class), p_244656_1_ -> {
      for (C_125_ soundsource : C_125_.values()) {
         p_244656_1_.put(soundsource, this.a("soundCategory." + soundsource.m_12676_(), soundsource));
      }
   });
   private final OptionInstance<Boolean> bw = OptionInstance.a("options.showSubtitles", false);
   private static final C_4996_ bx = C_4996_.m_237115_("options.directionalAudio.on.tooltip");
   private static final C_4996_ by = C_4996_.m_237115_("options.directionalAudio.off.tooltip");
   private final OptionInstance<Boolean> bz = OptionInstance.a(
      "options.directionalAudio", p_257068_0_ -> p_257068_0_ ? C_256714_.m_257550_(bx) : C_256714_.m_257550_(by), false, p_263137_0_ -> {
         C_4603_ soundmanager = C_3391_.m_91087_().m_91106_();
         soundmanager.m_194526_();
         soundmanager.m_120367_(C_4561_.m_263171_(C_124_.f_12490_, 1.0F));
      }
   );
   private final OptionInstance<Boolean> bA = new OptionInstance<>(
      "options.accessibility.text_background",
      OptionInstance.a(),
      (p_231975_0_, p_231975_1_) -> p_231975_1_
            ? C_4996_.m_237115_("options.accessibility.text_background.chat")
            : C_4996_.m_237115_("options.accessibility.text_background.everywhere"),
      OptionInstance.a,
      true,
      p_231874_0_ -> {
      }
   );
   private final OptionInstance<Boolean> bB = OptionInstance.a("options.touchscreen", false);
   private final OptionInstance<Boolean> bC = OptionInstance.a("options.fullscreen", false, p_231969_1_ -> {
      C_3391_ minecraft = C_3391_.m_91087_();
      if (minecraft.aM() != null && minecraft.aM().k() != p_231969_1_) {
         minecraft.aM().i();
         this.aa().a(minecraft.aM().k());
      }
   });
   private final OptionInstance<Boolean> bD = OptionInstance.a("options.viewBobbing", true);
   private static final C_4996_ bE = C_4996_.m_237115_("options.key.toggle");
   private static final C_4996_ bF = C_4996_.m_237115_("options.key.hold");
   private final OptionInstance<Boolean> bG = new OptionInstance<>(
      "key.sneak", OptionInstance.a(), (p_231955_0_, p_231955_1_) -> p_231955_1_ ? bE : bF, OptionInstance.a, false, p_231989_0_ -> {
      }
   );
   private final OptionInstance<Boolean> bH = new OptionInstance<>(
      "key.sprint", OptionInstance.a(), (p_231909_0_, p_231909_1_) -> p_231909_1_ ? bE : bF, OptionInstance.a, false, p_231971_0_ -> {
      }
   );
   public boolean v;
   private static final C_4996_ bI = C_4996_.m_237115_("options.hideMatchedNames.tooltip");
   private final OptionInstance<Boolean> bJ = OptionInstance.a("options.hideMatchedNames", OptionInstance.a(bI), true);
   private final OptionInstance<Boolean> bK = OptionInstance.a("options.autosaveIndicator", true);
   private static final C_4996_ bL = C_4996_.m_237115_("options.onlyShowSecureChat.tooltip");
   private final OptionInstance<Boolean> bM = OptionInstance.a("options.onlyShowSecureChat", OptionInstance.a(bL), false);
   public final C_3387_ w = new C_3387_("key.forward", 87, "key.categories.movement");
   public final C_3387_ x = new C_3387_("key.left", 65, "key.categories.movement");
   public final C_3387_ y = new C_3387_("key.back", 83, "key.categories.movement");
   public final C_3387_ z = new C_3387_("key.right", 68, "key.categories.movement");
   public final C_3387_ A = new C_3387_("key.jump", 32, "key.categories.movement");
   public final C_3387_ B = new C_3419_("key.sneak", 340, "key.categories.movement", this.bG::c);
   public final C_3387_ C = new C_3419_("key.sprint", 341, "key.categories.movement", this.bH::c);
   public final C_3387_ D = new C_3387_("key.inventory", 69, "key.categories.inventory");
   public final C_3387_ E = new C_3387_("key.swapOffhand", 70, "key.categories.inventory");
   public final C_3387_ F = new C_3387_("key.drop", 81, "key.categories.inventory");
   public final C_3387_ G = new C_3387_("key.use", C_3143_.MOUSE, 1, "key.categories.gameplay");
   public final C_3387_ H = new C_3387_("key.attack", C_3143_.MOUSE, 0, "key.categories.gameplay");
   public final C_3387_ I = new C_3387_("key.pickItem", C_3143_.MOUSE, 2, "key.categories.gameplay");
   public final C_3387_ J = new C_3387_("key.chat", 84, "key.categories.multiplayer");
   public final C_3387_ K = new C_3387_("key.playerlist", 258, "key.categories.multiplayer");
   public final C_3387_ L = new C_3387_("key.command", 47, "key.categories.multiplayer");
   public final C_3387_ M = new C_3387_("key.socialInteractions", 80, "key.categories.multiplayer");
   public final C_3387_ N = new C_3387_("key.screenshot", 291, "key.categories.misc");
   public final C_3387_ O = new C_3387_("key.togglePerspective", 294, "key.categories.misc");
   public final C_3387_ P = new C_3387_("key.smoothCamera", C_3140_.f_84822_.m_84873_(), "key.categories.misc");
   public final C_3387_ Q = new C_3387_("key.fullscreen", 300, "key.categories.misc");
   public final C_3387_ R = new C_3387_("key.spectatorOutlines", C_3140_.f_84822_.m_84873_(), "key.categories.misc");
   public final C_3387_ S = new C_3387_("key.advancements", 76, "key.categories.misc");
   public final C_3387_[] T = new C_3387_[]{
      new C_3387_("key.hotbar.1", 49, "key.categories.inventory"),
      new C_3387_("key.hotbar.2", 50, "key.categories.inventory"),
      new C_3387_("key.hotbar.3", 51, "key.categories.inventory"),
      new C_3387_("key.hotbar.4", 52, "key.categories.inventory"),
      new C_3387_("key.hotbar.5", 53, "key.categories.inventory"),
      new C_3387_("key.hotbar.6", 54, "key.categories.inventory"),
      new C_3387_("key.hotbar.7", 55, "key.categories.inventory"),
      new C_3387_("key.hotbar.8", 56, "key.categories.inventory"),
      new C_3387_("key.hotbar.9", 57, "key.categories.inventory")
   };
   public final C_3387_ U = new C_3387_("key.saveToolbarActivator", 67, "key.categories.creative");
   public final C_3387_ V = new C_3387_("key.loadToolbarActivator", 88, "key.categories.creative");
   public C_3387_[] W = (C_3387_[])ArrayUtils.addAll(
      new C_3387_[]{
         this.H,
         this.G,
         this.w,
         this.x,
         this.y,
         this.z,
         this.A,
         this.B,
         this.C,
         this.F,
         this.D,
         this.J,
         this.K,
         this.I,
         this.L,
         this.M,
         this.N,
         this.O,
         this.P,
         this.Q,
         this.R,
         this.E,
         this.U,
         this.V,
         this.S
      },
      this.T
   );
   protected C_3391_ X;
   private final File bN;
   public boolean Y;
   private C_3374_ bO = C_3374_.FIRST_PERSON;
   public String Z = "";
   public boolean aa;
   private final OptionInstance<Integer> bP = new OptionInstance<>(
      "options.fov",
      OptionInstance.a(),
      (p_231998_0_, p_231998_1_) -> {
         return switch (p_231998_1_) {
            case 70 -> a(p_231998_0_, C_4996_.m_237115_("options.fov.min"));
            case 110 -> a(p_231998_0_, C_4996_.m_237115_("options.fov.max"));
            default -> a(p_231998_0_, p_231998_1_);
         };
      },
      new OptionInstance.f(30, 110),
      Codec.DOUBLE.xmap(p_232006_0_ -> (int)(p_232006_0_ * 40.0 + 70.0), p_232008_0_ -> ((double)p_232008_0_.intValue() - 70.0) / 40.0),
      70,
      p_231991_0_ -> C_3391_.m_91087_().f.r()
   );
   private static final C_4996_ bQ = C_4996_.m_237110_(
      "options.telemetry.button.tooltip", new Object[]{C_4996_.m_237115_("options.telemetry.state.minimal"), C_4996_.m_237115_("options.telemetry.state.all")}
   );
   private final OptionInstance<Boolean> bR = OptionInstance.a("options.telemetry.button", OptionInstance.a(bQ), (p_260741_0_, p_260741_1_) -> {
      C_3391_ minecraft = C_3391_.m_91087_();
      if (!minecraft.m_261210_()) {
         return C_4996_.m_237115_("options.telemetry.state.none");
      } else {
         return p_260741_1_ && minecraft.m_261227_() ? C_4996_.m_237115_("options.telemetry.state.all") : C_4996_.m_237115_("options.telemetry.state.minimal");
      }
   }, false, p_231948_0_ -> {
   });
   private static final C_4996_ bS = C_4996_.m_237115_("options.screenEffectScale.tooltip");
   private final OptionInstance<Double> bT = new OptionInstance<>(
      "options.screenEffectScale", OptionInstance.a(bS), Options::b, OptionInstance.m.a, 1.0, p_231876_0_ -> {
      }
   );
   private static final C_4996_ bU = C_4996_.m_237115_("options.fovEffectScale.tooltip");
   private final OptionInstance<Double> bV = new OptionInstance<>(
      "options.fovEffectScale", OptionInstance.a(bU), Options::b, OptionInstance.m.a.a(Mth::k, Math::sqrt), Codec.doubleRange(0.0, 1.0), 1.0, p_231973_0_ -> {
      }
   );
   private static final C_4996_ bW = C_4996_.m_237115_("options.darknessEffectScale.tooltip");
   private final OptionInstance<Double> bX = new OptionInstance<>(
      "options.darknessEffectScale", OptionInstance.a(bW), Options::b, OptionInstance.m.a.a(Mth::k, Math::sqrt), 1.0, p_231868_0_ -> {
      }
   );
   private static final C_4996_ bY = C_4996_.m_237115_("options.glintSpeed.tooltip");
   private final OptionInstance<Double> bZ = new OptionInstance<>(
      "options.glintSpeed", OptionInstance.a(bY), Options::b, OptionInstance.m.a, 0.5, p_241717_0_ -> {
      }
   );
   private static final C_4996_ ca = C_4996_.m_237115_("options.glintStrength.tooltip");
   private final OptionInstance<Double> cb = new OptionInstance<>(
      "options.glintStrength", OptionInstance.a(ca), Options::b, OptionInstance.m.a, 0.75, RenderSystem::setShaderGlintAlpha
   );
   private static final C_4996_ cc = C_4996_.m_237115_("options.damageTiltStrength.tooltip");
   private final OptionInstance<Double> cd = new OptionInstance<>(
      "options.damageTiltStrength", OptionInstance.a(cc), Options::b, OptionInstance.m.a, 1.0, p_260742_0_ -> {
      }
   );
   private final OptionInstance<Double> ce = new OptionInstance<>("options.gamma", OptionInstance.a(), (p_231912_0_, p_231912_1_) -> {
      int i = (int)(p_231912_1_ * 100.0);
      if (i == 0) {
         return a(p_231912_0_, C_4996_.m_237115_("options.gamma.min"));
      } else if (i == 50) {
         return a(p_231912_0_, C_4996_.m_237115_("options.gamma.default"));
      } else {
         return i == 100 ? a(p_231912_0_, C_4996_.m_237115_("options.gamma.max")) : a(p_231912_0_, i);
      }
   }, OptionInstance.m.a, 0.5, p_263858_0_ -> {
   });
   public static final int ab = 0;
   private static final int cf = 2147483646;
   private final OptionInstance<Integer> cg = new OptionInstance<>(
      "options.guiScale",
      OptionInstance.a(),
      (p_231981_0_, p_231981_1_) -> p_231981_1_ == 0 ? C_4996_.m_237115_("options.guiScale.auto") : C_4996_.m_237113_(Integer.toString(p_231981_1_)),
      new OptionInstance.c(0, () -> {
         C_3391_ minecraft = C_3391_.m_91087_();
         return !minecraft.m_91396_() ? 2147483646 : minecraft.aM().a(0, minecraft.m_91390_());
      }, 2147483646),
      0,
      p_317301_1_ -> this.X.m_5741_()
   );
   private final OptionInstance<C_3404_> ch = new OptionInstance<>(
      "options.particles",
      OptionInstance.a(),
      OptionInstance.b(),
      new OptionInstance.e<>(Arrays.asList(C_3404_.values()), Codec.INT.xmap(C_3404_::m_92196_, C_3404_::m_35965_)),
      C_3404_.ALL,
      p_267500_0_ -> {
      }
   );
   private final OptionInstance<C_3398_> ci = new OptionInstance<>(
      "options.narrator",
      OptionInstance.a(),
      (p_240390_1_, p_240390_2_) -> (C_4996_)(this.X.m_240477_().m_93316_() ? p_240390_2_.m_91621_() : C_4996_.m_237115_("options.narrator.notavailable")),
      new OptionInstance.e<>(Arrays.asList(C_3398_.values()), Codec.INT.xmap(C_3398_::m_91619_, C_3398_::m_91618_)),
      C_3398_.OFF,
      p_240389_1_ -> this.X.m_240477_().m_93317_(p_240389_1_)
   );
   public String ac = "en_us";
   private final OptionInstance<String> cj = new OptionInstance<>(
      "options.audioDevice",
      OptionInstance.a(),
      (p_231918_0_, p_231918_1_) -> {
         if ("".equals(p_231918_1_)) {
            return C_4996_.m_237115_("options.audioDevice.default");
         } else {
            return p_231918_1_.startsWith("OpenAL Soft on ") ? C_4996_.m_237113_(p_231918_1_.substring(C_4600_.f_194470_)) : C_4996_.m_237113_(p_231918_1_);
         }
      },
      new OptionInstance.h<>(
         () -> Stream.concat(Stream.of(""), C_3391_.m_91087_().m_91106_().m_194525_().stream()).toList(),
         p_232010_0_ -> C_3391_.m_91087_().m_91396_()
                  && (p_232010_0_ == null || p_232010_0_.isEmpty())
                  && !C_3391_.m_91087_().m_91106_().m_194525_().contains(p_232010_0_)
               ? Optional.empty()
               : Optional.of(p_232010_0_),
         Codec.STRING
      ),
      "",
      p_263138_0_ -> {
         C_4603_ soundmanager = C_3391_.m_91087_().m_91106_();
         soundmanager.m_194526_();
         soundmanager.m_120367_(C_4561_.m_263171_(C_124_.f_12490_, 1.0F));
      }
   );
   public boolean ad = true;
   public boolean ae;
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
   public C_3387_ ofKeyBindZoom;
   private File optionsFileOF;
   private boolean loadOptions;
   private boolean saveOptions;
   public final OptionInstance GRAPHICS = this.az;
   public final OptionInstance RENDER_DISTANCE;
   public final OptionInstance SIMULATION_DISTANCE;
   public final OptionInstance AO = this.aA;
   public final OptionInstance FRAMERATE_LIMIT = this.au;
   public final OptionInstance GUI_SCALE = this.cg;
   public final OptionInstance ENTITY_SHADOWS = this.bl;
   public final OptionInstance GAMMA = this.ce;
   public final OptionInstance ATTACK_INDICATOR = this.ba;
   public final OptionInstance PARTICLES = this.ch;
   public final OptionInstance VIEW_BOBBING = this.bD;
   public final OptionInstance AUTOSAVE_INDICATOR = this.bK;
   public final OptionInstance ENTITY_DISTANCE_SCALING = this.at;
   public final OptionInstance BIOME_BLEND_RADIUS = this.bb;
   public final OptionInstance FULLSCREEN = this.bC;
   public final OptionInstance PRIORITIZE_CHUNK_UPDATES = this.aE;
   public final OptionInstance MIPMAP_LEVELS = this.aZ;
   public final OptionInstance SCREEN_EFFECT_SCALE = this.bT;
   public final OptionInstance FOV_EFFECT_SCALE = this.bV;

   public OptionInstance<Boolean> a() {
      return this.ak;
   }

   public OptionInstance<Boolean> b() {
      return this.am;
   }

   public OptionInstance<Boolean> c() {
      return this.ao;
   }

   public OptionInstance<Double> d() {
      return this.ap;
   }

   public OptionInstance<Integer> e() {
      return this.aq;
   }

   public OptionInstance<Integer> f() {
      return this.ar;
   }

   public OptionInstance<Double> g() {
      return this.at;
   }

   public OptionInstance<Integer> h() {
      return this.au;
   }

   public OptionInstance<C_3376_> i() {
      return this.av;
   }

   public OptionInstance<C_3383_> j() {
      return this.az;
   }

   public OptionInstance<Boolean> k() {
      return this.aA;
   }

   public OptionInstance<C_183059_> l() {
      return this.aE;
   }

   public void a(C_62_ repoIn) {
      List<String> list = ImmutableList.copyOf(this.i);
      this.i.clear();
      this.j.clear();

      for (C_58_ pack : repoIn.m_10524_()) {
         if (!pack.m_10450_()) {
            this.i.add(pack.m_10446_());
            if (!pack.m_10443_().m_10489_()) {
               this.j.add(pack.m_10446_());
            }
         }
      }

      this.aw();
      List<String> list1 = ImmutableList.copyOf(this.i);
      if (!list1.equals(list)) {
         this.X.m_91391_();
      }
   }

   public OptionInstance<C_1139_> m() {
      return this.aF;
   }

   public OptionInstance<Double> n() {
      return this.aG;
   }

   public OptionInstance<Double> o() {
      return this.aH;
   }

   public OptionInstance<Integer> p() {
      return this.aK;
   }

   public int q() {
      return this.p().c();
   }

   public OptionInstance<Double> r() {
      return this.aL;
   }

   public OptionInstance<Double> s() {
      return this.aM;
   }

   public OptionInstance<Boolean> t() {
      return this.aO;
   }

   public OptionInstance<Boolean> u() {
      return this.aP;
   }

   public OptionInstance<C_520_> v() {
      return this.aR;
   }

   public OptionInstance<Double> w() {
      return this.aS;
   }

   public OptionInstance<Double> x() {
      return this.aT;
   }

   public OptionInstance<Double> y() {
      return this.aU;
   }

   public OptionInstance<Double> z() {
      return this.aV;
   }

   public OptionInstance<Double> A() {
      return this.aW;
   }

   public OptionInstance<Double> B() {
      return this.aY;
   }

   public OptionInstance<Integer> C() {
      return this.aZ;
   }

   public OptionInstance<C_3371_> D() {
      return this.ba;
   }

   public OptionInstance<Integer> E() {
      return this.bb;
   }

   private static double c(int valueIn) {
      return Math.pow(10.0, (double)valueIn / 100.0);
   }

   private static int a(double valueIn) {
      return Mth.a(Math.log10(valueIn) * 100.0);
   }

   public OptionInstance<Double> F() {
      return this.bc;
   }

   public OptionInstance<Boolean> G() {
      return this.bd;
   }

   public OptionInstance<Boolean> H() {
      return this.be;
   }

   public OptionInstance<Boolean> I() {
      return this.bf;
   }

   public OptionInstance<Boolean> J() {
      return this.bg;
   }

   public OptionInstance<Boolean> K() {
      return this.bh;
   }

   public OptionInstance<Boolean> L() {
      return this.bi;
   }

   public OptionInstance<Boolean> M() {
      return this.bj;
   }

   public OptionInstance<Boolean> N() {
      return this.bk;
   }

   public OptionInstance<Boolean> O() {
      return this.bl;
   }

   private static void aF() {
      C_3391_ minecraft = C_3391_.m_91087_();
      if (minecraft.aM() != null) {
         minecraft.m_323618_();
         minecraft.m_5741_();
      }
   }

   public OptionInstance<Boolean> P() {
      return this.bm;
   }

   private static boolean aG() {
      return Locale.getDefault().getLanguage().equalsIgnoreCase("ja");
   }

   public OptionInstance<Boolean> Q() {
      return this.bn;
   }

   public OptionInstance<Boolean> R() {
      return this.bo;
   }

   public OptionInstance<Boolean> S() {
      return this.bp;
   }

   public OptionInstance<Boolean> T() {
      return this.br;
   }

   public OptionInstance<Boolean> U() {
      return this.bt;
   }

   public OptionInstance<Boolean> V() {
      return this.bu;
   }

   public final float a(C_125_ category) {
      return this.b(category).c().floatValue();
   }

   public final OptionInstance<Double> b(C_125_ p_246669_1_) {
      return (OptionInstance<Double>)Objects.requireNonNull((OptionInstance)this.bv.get(p_246669_1_));
   }

   private OptionInstance<Double> a(String p_247249_1_, C_125_ p_247249_2_) {
      return new OptionInstance<>(
         p_247249_1_,
         OptionInstance.a(),
         Options::b,
         OptionInstance.m.a,
         1.0,
         p_244657_1_ -> C_3391_.m_91087_().m_91106_().m_120358_(p_247249_2_, p_244657_1_.floatValue())
      );
   }

   public OptionInstance<Boolean> W() {
      return this.bw;
   }

   public OptionInstance<Boolean> X() {
      return this.bz;
   }

   public OptionInstance<Boolean> Y() {
      return this.bA;
   }

   public OptionInstance<Boolean> Z() {
      return this.bB;
   }

   public OptionInstance<Boolean> aa() {
      return this.bC;
   }

   public OptionInstance<Boolean> ab() {
      return this.bD;
   }

   public OptionInstance<Boolean> ac() {
      return this.bG;
   }

   public OptionInstance<Boolean> ad() {
      return this.bH;
   }

   public OptionInstance<Boolean> ae() {
      return this.bJ;
   }

   public OptionInstance<Boolean> af() {
      return this.bK;
   }

   public OptionInstance<Boolean> ag() {
      return this.bM;
   }

   public OptionInstance<Integer> ah() {
      return this.bP;
   }

   public OptionInstance<Boolean> ai() {
      return this.bR;
   }

   public OptionInstance<Double> aj() {
      return this.bT;
   }

   public OptionInstance<Double> ak() {
      return this.bV;
   }

   public OptionInstance<Double> al() {
      return this.bX;
   }

   public OptionInstance<Double> am() {
      return this.bZ;
   }

   public OptionInstance<Double> an() {
      return this.cb;
   }

   public OptionInstance<Double> ao() {
      return this.cd;
   }

   public OptionInstance<Double> ap() {
      return this.ce;
   }

   public OptionInstance<Integer> aq() {
      return this.cg;
   }

   public OptionInstance<C_3404_> ar() {
      return this.ch;
   }

   public OptionInstance<C_3398_> as() {
      return this.ci;
   }

   public OptionInstance<String> at() {
      return this.cj;
   }

   public void au() {
      this.ad = false;
      this.aw();
   }

   public Options(C_3391_ mcIn, File mcDataDir) {
      this.setForgeKeybindProperties();
      long MB = 1000000L;
      int maxRenderDist = 32;
      if (Runtime.getRuntime().maxMemory() >= 1500L * MB) {
         maxRenderDist = 48;
      }

      if (Runtime.getRuntime().maxMemory() >= 2500L * MB) {
         maxRenderDist = 64;
      }

      this.X = mcIn;
      this.bN = new File(mcDataDir, "options.txt");
      boolean flag = Runtime.getRuntime().maxMemory() >= 1000000000L;
      this.aq = new OptionInstance<>(
         "options.renderDistance",
         OptionInstance.a(),
         (p_231915_0_, p_231915_1_) -> a(p_231915_0_, C_4996_.m_237110_("options.chunks", new Object[]{p_231915_1_})),
         new OptionInstance.f(2, flag ? maxRenderDist : 16, false),
         12,
         p_231950_0_ -> C_3391_.m_91087_().f.r()
      );
      this.ar = new OptionInstance<>(
         "options.simulationDistance",
         OptionInstance.a(),
         (p_263859_0_, p_263859_1_) -> a(p_263859_0_, C_4996_.m_237110_("options.chunks", new Object[]{p_263859_1_})),
         new OptionInstance.f(5, flag ? 32 : 16, false),
         12,
         p_268764_0_ -> {
         }
      );
      this.ae = Util.k() == Util.a.c;
      this.RENDER_DISTANCE = this.aq;
      this.SIMULATION_DISTANCE = this.ar;
      this.optionsFileOF = new File(mcDataDir, "optionsof.txt");
      this.h().a(this.h().getMaxValue());
      this.ofKeyBindZoom = new C_3387_("of.key.zoom", 67, "key.categories.misc");
      this.W = (C_3387_[])ArrayUtils.add(this.W, this.ofKeyBindZoom);
      KeyUtils.fixKeyConflicts(this.W, new C_3387_[]{this.ofKeyBindZoom});
      this.aq.a(8);
      this.av();
      Config.initGameSettings(this);
   }

   public float a(float opacityIn) {
      return this.bA.c() ? opacityIn : this.r().c().floatValue();
   }

   public int b(float opacityIn) {
      return (int)(this.a(opacityIn) * 255.0F) << 24 & 0xFF000000;
   }

   public int a(int colorIn) {
      return this.bA.c() ? colorIn : (int)(this.aL.c() * 255.0) << 24 & 0xFF000000;
   }

   public void a(C_3387_ keyBindingIn, C_3142_ inputIn) {
      keyBindingIn.m_90848_(inputIn);
      this.aw();
   }

   private void a(Options.b optionAccessIn) {
      optionAccessIn.a("ao", this.aA);
      optionAccessIn.a("biomeBlendRadius", this.bb);
      optionAccessIn.a("enableVsync", this.bk);
      if (this.loadOptions) {
         if (this.N().c()) {
            this.au.a(this.au.getMinValue());
         }

         this.updateVSync();
      }

      optionAccessIn.a("entityDistanceScaling", this.at);
      optionAccessIn.a("entityShadows", this.bl);
      optionAccessIn.a("forceUnicodeFont", this.bm);
      optionAccessIn.a("japaneseGlyphVariants", this.bn);
      optionAccessIn.a("fov", this.bP);
      optionAccessIn.a("fovEffectScale", this.bV);
      optionAccessIn.a("darknessEffectScale", this.bX);
      optionAccessIn.a("glintSpeed", this.bZ);
      optionAccessIn.a("glintStrength", this.cb);
      optionAccessIn.a("prioritizeChunkUpdates", this.aE);
      optionAccessIn.a("fullscreen", this.bC);
      optionAccessIn.a("gamma", this.ce);
      optionAccessIn.a("graphicsMode", this.az);
      if (this.loadOptions) {
         this.updateRenderClouds();
      }

      optionAccessIn.a("guiScale", this.cg);
      optionAccessIn.a("maxFps", this.au);
      if (this.loadOptions && this.N().c()) {
         this.h().a(this.h().getMinValue());
      }

      optionAccessIn.a("mipmapLevels", this.aZ);
      optionAccessIn.a("narrator", this.ci);
      optionAccessIn.a("particles", this.ch);
      optionAccessIn.a("reducedDebugInfo", this.bu);
      optionAccessIn.a("renderClouds", this.av);
      optionAccessIn.a("renderDistance", this.aq);
      optionAccessIn.a("simulationDistance", this.ar);
      optionAccessIn.a("screenEffectScale", this.bT);
      optionAccessIn.a("soundDevice", this.cj);
   }

   private void a(Options.a fieldAccessIn) {
      this.a((Options.b)fieldAccessIn);
      fieldAccessIn.a("autoJump", this.be);
      fieldAccessIn.a("operatorItemsTab", this.bf);
      fieldAccessIn.a("autoSuggestions", this.bg);
      fieldAccessIn.a("chatColors", this.bh);
      fieldAccessIn.a("chatLinks", this.bi);
      fieldAccessIn.a("chatLinksPrompt", this.bj);
      fieldAccessIn.a("discrete_mouse_scroll", this.bp);
      fieldAccessIn.a("invertYMouse", this.bo);
      fieldAccessIn.a("realmsNotifications", this.br);
      fieldAccessIn.a("showSubtitles", this.bw);
      fieldAccessIn.a("directionalAudio", this.bz);
      fieldAccessIn.a("touchscreen", this.bB);
      fieldAccessIn.a("bobView", this.bD);
      fieldAccessIn.a("toggleCrouch", this.bG);
      fieldAccessIn.a("toggleSprint", this.bH);
      fieldAccessIn.a("darkMojangStudiosBackground", this.ak);
      fieldAccessIn.a("hideLightningFlashes", this.am);
      fieldAccessIn.a("hideSplashTexts", this.ao);
      fieldAccessIn.a("mouseSensitivity", this.ap);
      fieldAccessIn.a("damageTiltStrength", this.cd);
      fieldAccessIn.a("highContrast", this.aO);
      fieldAccessIn.a("narratorHotkey", this.aP);
      this.i = fieldAccessIn.a("resourcePacks", this.i, Options::c, ag::toJson);
      this.j = fieldAccessIn.a("incompatibleResourcePacks", this.j, Options::c, ag::toJson);
      this.Z = fieldAccessIn.a("lastServer", this.Z);
      this.ac = fieldAccessIn.a("lang", this.ac);
      fieldAccessIn.a("chatVisibility", this.aF);
      fieldAccessIn.a("chatOpacity", this.aG);
      fieldAccessIn.a("chatLineSpacing", this.aH);
      fieldAccessIn.a("textBackgroundOpacity", this.aL);
      fieldAccessIn.a("backgroundForChatOnly", this.bA);
      this.l = fieldAccessIn.a("hideServerAddress", this.l);
      this.m = fieldAccessIn.a("advancedItemTooltips", this.m);
      this.n = fieldAccessIn.a("pauseOnLostFocus", this.n);
      this.o = fieldAccessIn.a("overrideWidth", this.o);
      this.p = fieldAccessIn.a("overrideHeight", this.p);
      fieldAccessIn.a("chatHeightFocused", this.aV);
      fieldAccessIn.a("chatDelay", this.aW);
      fieldAccessIn.a("chatHeightUnfocused", this.aU);
      fieldAccessIn.a("chatScale", this.aS);
      fieldAccessIn.a("chatWidth", this.aT);
      fieldAccessIn.a("notificationDisplayTime", this.aY);
      this.q = fieldAccessIn.a("useNativeTransport", this.q);
      fieldAccessIn.a("mainHand", this.aR);
      fieldAccessIn.a("attackIndicator", this.ba);
      this.r = fieldAccessIn.a("tutorialStep", this.r, C_4621_::m_120642_, C_4621_::m_120639_);
      fieldAccessIn.a("mouseWheelSensitivity", this.bc);
      fieldAccessIn.a("rawMouseInput", this.bd);
      this.u = fieldAccessIn.a("glDebugVerbosity", this.u);
      this.v = fieldAccessIn.a("skipMultiplayerWarning", this.v);
      fieldAccessIn.a("hideMatchedNames", this.bJ);
      this.s = fieldAccessIn.a("joinedFirstServer", this.s);
      this.t = fieldAccessIn.a("hideBundleTutorial", this.t);
      this.ae = fieldAccessIn.a("syncChunkWrites", this.ae);
      fieldAccessIn.a("showAutosaveIndicator", this.bK);
      fieldAccessIn.a("allowServerListing", this.bt);
      fieldAccessIn.a("onlyShowSecureChat", this.bM);
      fieldAccessIn.a("panoramaScrollSpeed", this.aM);
      fieldAccessIn.a("telemetryOptInExtra", this.bR);
      this.ad = fieldAccessIn.a("onboardAccessibility", this.ad);
      fieldAccessIn.a("menuBackgroundBlurriness", this.aK);
      this.processOptionsForge(fieldAccessIn);
   }

   private void processOptionsForge(Options.a fieldAccessIn) {
      for (C_3387_ keymapping : this.W) {
         String s = keymapping.m_90865_();
         if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
            Object keyModifier = Reflector.call(keymapping, Reflector.ForgeKeyBinding_getKeyModifier);
            Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
            s = keymapping.m_90865_() + (keyModifier != keyModifierNone ? ":" + keyModifier : "");
         }

         String s1 = fieldAccessIn.a("key_" + keymapping.m_90860_(), s);
         if (!s.equals(s1)) {
            if (Reflector.KeyModifier_valueFromString.exists()) {
               if (s1.indexOf(58) != -1) {
                  String[] pts = s1.split(":");
                  Object keyModifier = Reflector.call(Reflector.KeyModifier_valueFromString, pts[1]);
                  Reflector.call(keymapping, Reflector.ForgeKeyBinding_setKeyModifierAndCode, keyModifier, C_3140_.m_84851_(pts[0]));
               } else {
                  Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
                  Reflector.call(keymapping, Reflector.ForgeKeyBinding_setKeyModifierAndCode, keyModifierNone, C_3140_.m_84851_(s1));
               }
            } else {
               keymapping.m_90848_(C_3140_.m_84851_(s1));
            }
         }
      }

      for (C_125_ soundsource : C_125_.values()) {
         fieldAccessIn.a("soundCategory_" + soundsource.m_12676_(), (OptionInstance)this.bv.get(soundsource));
      }

      for (C_1144_ playermodelpart : C_1144_.values()) {
         boolean flag = this.aQ.contains(playermodelpart);
         boolean flag1 = fieldAccessIn.a("modelPart_" + playermodelpart.m_36446_(), flag);
         if (flag1 != flag) {
            this.b(playermodelpart, flag1);
         }
      }
   }

   public void av() {
      this.load(false);
   }

   public void load(boolean limited) {
      this.loadOptions = true;

      try {
         if (!this.bN.exists()) {
            return;
         }

         C_4917_ compoundtag = new C_4917_();
         BufferedReader bufferedreader = Files.newReader(this.bN, Charsets.UTF_8);

         try {
            bufferedreader.lines().forEach(lineIn -> {
               try {
                  Iterator<String> iterator = ai.split(lineIn).iterator();
                  compoundtag.m_128359_((String)iterator.next(), (String)iterator.next());
               } catch (Exception var3x) {
                  af.warn("Skipping bad option: {}", lineIn);
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

         final C_4917_ compoundtag1 = this.a(compoundtag);
         if (!compoundtag1.m_128441_("graphicsMode") && compoundtag1.m_128441_("fancyGraphics")) {
            if (a(compoundtag1.m_128461_("fancyGraphics"))) {
               this.az.a(C_3383_.FANCY);
            } else {
               this.az.a(C_3383_.FAST);
            }
         }

         Consumer<Options.a> processor = limited ? this::processOptionsForge : this::a;
         processor.accept(
            new Options.a() {
               @Nullable
               private String a(String nameIn) {
                  return compoundtag1.m_128441_(nameIn) ? compoundtag1.m_128423_(nameIn).m_7916_() : null;
               }

               @Override
               public <T> void a(String keyIn, OptionInstance<T> optionIn) {
                  String s = this.a(keyIn);
                  if (s != null) {
                     JsonReader jsonreader = new JsonReader(new StringReader(s.isEmpty() ? "\"\"" : s));
                     JsonElement jsonelement = JsonParser.parseReader(jsonreader);
                     DataResult<T> dataresult = optionIn.d().parse(JsonOps.INSTANCE, jsonelement);
                     dataresult.error()
                        .ifPresent(errorIn -> Options.af.error("Error parsing option value " + s + " for option " + optionIn + ": " + errorIn.message()));
                     dataresult.ifSuccess(optionIn::a);
                  }
               }

               @Override
               public int a(String keyIn, int valueIn) {
                  String s = this.a(keyIn);
                  if (s != null) {
                     try {
                        return Integer.parseInt(s);
                     } catch (NumberFormatException var5) {
                        Options.af.warn("Invalid integer value for option {} = {}", new Object[]{keyIn, s, var5});
                     }
                  }

                  return valueIn;
               }

               @Override
               public boolean a(String keyIn, boolean valueIn) {
                  String s = this.a(keyIn);
                  return s != null ? Options.a(s) : valueIn;
               }

               @Override
               public String a(String keyIn, String valueIn) {
                  return (String)MoreObjects.firstNonNull(this.a(keyIn), valueIn);
               }

               @Override
               public float a(String keyIn, float valueIn) {
                  String s = this.a(keyIn);
                  if (s == null) {
                     return valueIn;
                  } else if (Options.a(s)) {
                     return 1.0F;
                  } else if (Options.b(s)) {
                     return 0.0F;
                  } else {
                     try {
                        return Float.parseFloat(s);
                     } catch (NumberFormatException var5) {
                        Options.af.warn("Invalid floating point value for option {} = {}", new Object[]{keyIn, s, var5});
                        return valueIn;
                     }
                  }
               }

               @Override
               public <T> T a(String keyIn, T valueIn, Function<String, T> readerIn, Function<T, String> writerIn) {
                  String s = this.a(keyIn);
                  return (T)(s == null ? valueIn : readerIn.apply(s));
               }
            }
         );
         if (compoundtag1.m_128441_("fullscreenResolution")) {
            this.k = compoundtag1.m_128461_("fullscreenResolution");
         }

         if (this.X.aM() != null) {
            this.X.aM().a(this.au.c());
         }

         C_3387_.m_90854_();
      } catch (Exception var8) {
         af.error("Failed to load options", var8);
      }

      this.loadOptions = false;
      this.loadOfOptions();
   }

   static boolean a(String textIn) {
      return "true".equals(textIn);
   }

   static boolean b(String textIn) {
      return "false".equals(textIn);
   }

   private C_4917_ a(C_4917_ nbt) {
      int i = 0;

      try {
         i = Integer.parseInt(nbt.m_128461_("version"));
      } catch (RuntimeException var4) {
      }

      return C_208_.OPTIONS.m_264218_(this.X.m_91295_(), nbt, i);
   }

   public void aw() {
      this.saveOptions = true;
      if (!Reflector.ClientModLoader_isLoading.exists() || !Reflector.callBoolean(Reflector.ClientModLoader_isLoading)) {
         try {
            final PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.bN), StandardCharsets.UTF_8));

            try {
               printwriter.println("version:" + C_5285_.m_183709_().m_183476_().m_193006_());
               this.a(
                  new Options.a() {
                     public void a(String prefixIn) {
                        printwriter.print(prefixIn);
                        printwriter.print(':');
                     }

                     @Override
                     public <T> void a(String keyIn, OptionInstance<T> optionIn) {
                        optionIn.d()
                           .encodeStart(JsonOps.INSTANCE, optionIn.c())
                           .ifError(errorIn -> Options.af.error("Error saving option " + optionIn + ": " + errorIn))
                           .ifSuccess(jsonElemIn -> {
                              this.a(keyIn);
                              printwriter.println(Options.ag.toJson(jsonElemIn));
                           });
                     }

                     @Override
                     public int a(String keyIn, int valueIn) {
                        this.a(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public boolean a(String keyIn, boolean valueIn) {
                        this.a(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public String a(String keyIn, String valueIn) {
                        this.a(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public float a(String keyIn, float valueIn) {
                        this.a(keyIn);
                        printwriter.println(valueIn);
                        return valueIn;
                     }

                     @Override
                     public <T> T a(String keyIn, T valueIn, Function<String, T> readerIn, Function<T, String> writerIn) {
                        this.a(keyIn);
                        printwriter.println((String)writerIn.apply(valueIn));
                        return valueIn;
                     }
                  }
               );
               if (this.X.aM().g().isPresent()) {
                  printwriter.println("fullscreenResolution:" + ((C_3160_)this.X.aM().g().get()).m_85342_());
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
            af.error("Failed to save options", var6);
         }

         this.saveOptions = false;
         this.saveOfOptions();
         this.ay();
      }
   }

   public C_290276_ ax() {
      int i = 0;

      for (C_1144_ playermodelpart : this.aQ) {
         i |= playermodelpart.m_36445_();
      }

      return new C_290276_(this.ac, this.aq.c(), this.aF.c(), this.bh.c(), i, this.aR.c(), this.X.m_167974_(), this.bt.c());
   }

   public void ay() {
      if (this.X.f_91074_ != null) {
         this.X.f_91074_.f_108617_.m_295327_(new C_290111_(this.ax()));
      }
   }

   private void b(C_1144_ modelPart, boolean enable) {
      if (enable) {
         this.aQ.add(modelPart);
      } else {
         this.aQ.remove(modelPart);
      }
   }

   public boolean a(C_1144_ partIn) {
      return this.aQ.contains(partIn);
   }

   public void a(C_1144_ partIn, boolean enabledIn) {
      this.b(partIn, enabledIn);
      this.ay();
   }

   public C_3376_ az() {
      return this.aE() >= 4 ? this.av.c() : C_3376_.OFF;
   }

   public boolean aA() {
      return this.q;
   }

   public void setOptionFloatValueOF(OptionInstance option, double val) {
      if (option == Option.CLOUD_HEIGHT) {
         this.ofCloudsHeight = val;
      }

      if (option == Option.AO_LEVEL) {
         this.ofAoLevel = val;
         this.X.f.f();
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
         this.X.m_91088_();
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
         this.X.f.f();
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
         if (this.X.aN().n && this.X.aN().p != this.ofLagometer) {
            this.X.aN().j();
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

         this.X.f.f();
      }

      if (par1EnumOptions == Option.CONNECTED_TEXTURES) {
         this.ofConnectedTextures++;
         if (this.ofConnectedTextures > 3) {
            this.ofConnectedTextures = 1;
         }

         if (this.ofConnectedTextures == 2) {
            this.X.f.f();
         } else {
            this.X.m_91088_();
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
         if (this.X.aN().n && this.X.aN().o != this.ofProfiler) {
            this.X.aN().k();
         }
      }

      if (par1EnumOptions == Option.BETTER_SNOW) {
         this.ofBetterSnow = !this.ofBetterSnow;
         this.X.f.f();
      }

      if (par1EnumOptions == Option.SWAMP_COLORS) {
         this.ofSwampColors = !this.ofSwampColors;
         this.X.f.f();
      }

      if (par1EnumOptions == Option.RANDOM_ENTITIES) {
         this.ofRandomEntities = !this.ofRandomEntities;
         this.X.m_91088_();
      }

      if (par1EnumOptions == Option.CUSTOM_FONTS) {
         this.ofCustomFonts = !this.ofCustomFonts;
         FontUtils.reloadFonts();
      }

      if (par1EnumOptions == Option.CUSTOM_COLORS) {
         this.ofCustomColors = !this.ofCustomColors;
         CustomColors.update();
         this.X.f.f();
      }

      if (par1EnumOptions == Option.CUSTOM_ITEMS) {
         this.ofCustomItems = !this.ofCustomItems;
         this.X.m_91088_();
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
         this.X.f.f();
      }

      if (par1EnumOptions == Option.EMISSIVE_TEXTURES) {
         this.ofEmissiveTextures = !this.ofEmissiveTextures;
         this.X.m_91088_();
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
         this.X.f.f();
      }

      if (par1EnumOptions == Option.SMART_ANIMATIONS) {
         this.ofSmartAnimations = !this.ofSmartAnimations;
         this.X.f.f();
      }

      if (par1EnumOptions == Option.DYNAMIC_FOV) {
         this.ofDynamicFov = !this.ofDynamicFov;
      }

      if (par1EnumOptions == Option.ALTERNATE_BLOCKS) {
         this.ofAlternateBlocks = !this.ofAlternateBlocks;
         this.X.f.f();
      }

      if (par1EnumOptions == Option.DYNAMIC_LIGHTS) {
         this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         DynamicLights.removeLights(this.X.f);
      }

      if (par1EnumOptions == Option.SCREENSHOT_SIZE) {
         this.ofScreenshotSize++;
         if (this.ofScreenshotSize > 4) {
            this.ofScreenshotSize = 1;
         }
      }

      if (par1EnumOptions == Option.CUSTOM_ENTITY_MODELS) {
         this.ofCustomEntityModels = !this.ofCustomEntityModels;
         this.X.m_91088_();
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
         this.m = !this.m;
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

   public C_4996_ getKeyComponentOF(OptionInstance option) {
      String str = this.getKeyBindingOF(option);
      if (str == null) {
         return null;
      } else {
         C_4996_ comp = C_4996_.m_237113_(str);
         return comp;
      }
   }

   public String getKeyBindingOF(OptionInstance par1EnumOptions) {
      String var2 = C_4513_.m_118938_(par1EnumOptions.getResourceKey(), new Object[0]) + ": ";
      if (var2 == null) {
         var2 = par1EnumOptions.getResourceKey();
      }

      if (par1EnumOptions == this.RENDER_DISTANCE) {
         int distChunks = this.e().c();
         String str = C_4513_.m_118938_("of.options.renderDistance.tiny", new Object[0]);
         int baseDist = 2;
         if (distChunks >= 4) {
            str = C_4513_.m_118938_("of.options.renderDistance.short", new Object[0]);
            baseDist = 4;
         }

         if (distChunks >= 8) {
            str = C_4513_.m_118938_("of.options.renderDistance.normal", new Object[0]);
            baseDist = 8;
         }

         if (distChunks >= 16) {
            str = C_4513_.m_118938_("of.options.renderDistance.far", new Object[0]);
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

         int diff = this.e().c() - baseDist;
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
         return this.m ? var2 + Lang.getOn() : var2 + Lang.getOff();
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
         double d0 = (Double)par1EnumOptions.c();
         return d0 == 0.0 ? var2 + C_4513_.m_118938_("options.off", new Object[0]) : var2 + (int)(d0 * 100.0) + "%";
      } else {
         return null;
      }
   }

   public void loadOfOptions() {
      try {
         File ofReadFile = this.optionsFileOF;
         if (!ofReadFile.exists()) {
            ofReadFile = this.bN;
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
                  this.aq.a(Integer.valueOf(as[1]));
                  this.aq.a(Config.limit(this.aq.c(), 2, 1024));
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
                  this.ofKeyBindZoom.m_90848_(C_3140_.m_84851_(as[1]));
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

         KeyUtils.fixKeyConflicts(this.W, new C_3387_[]{this.ofKeyBindZoom});
         C_3387_.m_90854_();
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
            this.av.a(C_3376_.FAST);
            break;
         case 2:
            this.av.a(C_3376_.FANCY);
            break;
         case 3:
            this.av.a(C_3376_.OFF);
            break;
         default:
            if (this.j().c() != C_3383_.FAST) {
               this.av.a(C_3376_.FANCY);
            } else {
               this.av.a(C_3376_.FAST);
            }
      }

      if (this.j().c() == C_3383_.FABULOUS) {
         LevelRenderer wr = C_3391_.m_91087_().f;
         if (wr != null) {
            RenderTarget framebuffer = wr.x();
            if (framebuffer != null) {
               framebuffer.b(C_3391_.f_91002_);
            }
         }
      }
   }

   public void resetSettings() {
      this.aq.a(8);
      this.ar.a(8);
      this.at.a(1.0);
      this.bD.a(true);
      this.au.a(this.au.getMaxValue());
      this.bk.a(false);
      this.updateVSync();
      this.aZ.a(4);
      this.az.a(C_3383_.FANCY);
      this.aA.a(true);
      this.av.a(C_3376_.FANCY);
      this.bP.a(70);
      this.ce.a(0.0);
      this.cg.a(0);
      this.ch.a(C_3404_.ALL);
      this.ofHeldItemTooltips = true;
      this.bm.a(false);
      this.aE.a(C_183059_.NONE);
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
      this.bb.a(2);
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
      this.X.m_91088_();
      this.aw();
   }

   public void updateVSync() {
      if (this.X.aM() != null) {
         this.X.aM().a(this.bk.c());
      }
   }

   public void updateMipmaps() {
      this.X.m_91312_(this.aZ.c());
      this.X.m_91088_();
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
      this.ch.a(flag ? C_3404_.ALL : C_3404_.MINIMAL);
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

      return C_4513_.m_118938_(strArray[index], new Object[0]);
   }

   public static C_4996_ genericValueLabel(String keyIn, int valueIn) {
      return a(C_4996_.m_237115_(keyIn), C_4996_.m_237113_(Integer.toString(valueIn)));
   }

   public static C_4996_ genericValueLabel(String keyIn, String valueKeyIn) {
      return a(C_4996_.m_237115_(keyIn), C_4996_.m_237115_(valueKeyIn));
   }

   public static C_4996_ genericValueLabel(String keyIn, String valueKeyIn, int valueIn) {
      return a(C_4996_.m_237115_(keyIn), C_4996_.m_237110_(valueKeyIn, new Object[]{Integer.toString(valueIn)}));
   }

   private void setForgeKeybindProperties() {
      if (Reflector.KeyConflictContext_IN_GAME.exists()) {
         if (Reflector.ForgeKeyBinding_setKeyConflictContext.exists()) {
            Object inGame = Reflector.getFieldValue(Reflector.KeyConflictContext_IN_GAME);
            Reflector.call(this.w, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.x, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.y, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.z, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.A, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.B, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.C, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.H, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.J, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.K, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.L, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.O, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
            Reflector.call(this.P, Reflector.ForgeKeyBinding_setKeyConflictContext, inGame);
         }
      }
   }

   public void b(C_62_ resourcePackListIn) {
      Set<String> set = Sets.newLinkedHashSet();
      Iterator<String> iterator = this.i.iterator();

      while (iterator.hasNext()) {
         String s = (String)iterator.next();
         C_58_ pack = resourcePackListIn.m_10507_(s);
         if (pack == null && !s.startsWith("file/")) {
            pack = resourcePackListIn.m_10507_("file/" + s);
         }

         if (pack == null) {
            af.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", s);
            iterator.remove();
         } else if (!pack.m_10443_().m_10489_() && !this.j.contains(s)) {
            af.warn("Removed resource pack {} from options because it is no longer compatible", s);
            iterator.remove();
         } else if (pack.m_10443_().m_10489_() && this.j.contains(s)) {
            af.info("Removed resource pack {} from incompatibility list because it's now compatible", s);
            this.j.remove(s);
         } else {
            set.add(pack.m_10446_());
         }
      }

      resourcePackListIn.m_10509_(set);
   }

   public C_3374_ aB() {
      return this.bO;
   }

   public void a(C_3374_ pointOfViewIn) {
      this.bO = pointOfViewIn;
   }

   private static List<String> c(String stringIn) {
      List<String> list = (List<String>)C_181_.m_13785_(ag, stringIn, ah);
      return (List<String>)(list != null ? list : Lists.newArrayList());
   }

   public File aC() {
      return this.bN;
   }

   public String aD() {
      final List<Pair<String, Object>> list = new ArrayList();
      this.a(new Options.b() {
         @Override
         public <T> void a(String keyIn, OptionInstance<T> optionIn) {
            list.add(Pair.of(keyIn, optionIn.c()));
         }
      });
      list.add(Pair.of("fullscreenResolution", String.valueOf(this.k)));
      list.add(Pair.of("glDebugVerbosity", this.u));
      list.add(Pair.of("overrideHeight", this.p));
      list.add(Pair.of("overrideWidth", this.o));
      list.add(Pair.of("syncChunkWrites", this.ae));
      list.add(Pair.of("useNativeTransport", this.q));
      list.add(Pair.of("resourcePacks", this.i));
      return (String)list.stream()
         .sorted(Comparator.comparing(Pair::getFirst))
         .map(pairIn -> (String)pairIn.getFirst() + ": " + pairIn.getSecond())
         .collect(Collectors.joining(System.lineSeparator()));
   }

   public void b(int valueIn) {
      this.as = valueIn;
   }

   public int aE() {
      return this.as > 0 ? Math.min(this.aq.c(), this.as) : this.aq.c();
   }

   private static C_4996_ c(C_4996_ componentIn, int valueIn) {
      return C_4996_.m_237110_("options.pixel_value", new Object[]{componentIn, valueIn});
   }

   private static C_4996_ a(C_4996_ componentIn, double valueIn) {
      return C_4996_.m_237110_("options.percent_value", new Object[]{componentIn, (int)(valueIn * 100.0)});
   }

   public static C_4996_ a(C_4996_ componentIn, C_4996_ valueIn) {
      return C_4996_.m_237110_("options.generic_value", new Object[]{componentIn, valueIn});
   }

   public static C_4996_ a(C_4996_ componentIn, int valueIn) {
      return a(componentIn, C_4996_.m_237113_(Integer.toString(valueIn)));
   }

   public static C_4996_ b(C_4996_ componentIn, int valueIn) {
      return valueIn == 0 ? a(componentIn, C_4995_.f_130654_) : a(componentIn, valueIn);
   }

   private static C_4996_ b(C_4996_ componentIn, double valueIn) {
      return valueIn == 0.0 ? a(componentIn, C_4995_.f_130654_) : a(componentIn, valueIn);
   }

   interface a extends Options.b {
      int a(String var1, int var2);

      boolean a(String var1, boolean var2);

      String a(String var1, String var2);

      float a(String var1, float var2);

      <T> T a(String var1, T var2, Function<String, T> var3, Function<T, String> var4);
   }

   interface b {
      <T> void a(String var1, OptionInstance<T> var2);
   }
}
