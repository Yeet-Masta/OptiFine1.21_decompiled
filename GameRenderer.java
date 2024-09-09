import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.src.C_1071_;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1168_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_140974_;
import net.minecraft.src.C_141436_;
import net.minecraft.src.C_1593_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_2070_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_276405_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_3041_;
import net.minecraft.src.C_3042_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_3144_;
import net.minecraft.src.C_336468_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3538_;
import net.minecraft.src.C_4145_;
import net.minecraft.src.C_4148_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4703_;
import net.minecraft.src.C_4705_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_498_;
import net.minecraft.src.C_4993_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_500_;
import net.minecraft.src.C_5012_;
import net.minecraft.src.C_5020_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_69_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_81_;
import net.minecraft.src.C_988_;
import net.minecraft.src.C_998_;
import net.minecraft.src.C_3043_.C_3044_;
import net.minecraft.src.C_4993_.C_4994_;
import net.optifine.Config;
import net.optifine.GlErrors;
import net.optifine.Lagometer;
import net.optifine.QuickInfo;
import net.optifine.RandomEntities;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.gui.GuiChatOF;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.reflect.ReflectorResolver;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.TimedEvent;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class GameRenderer implements AutoCloseable {
   private static final ResourceLocation f = ResourceLocation.b("textures/misc/nausea.png");
   private static final ResourceLocation g = ResourceLocation.b("shaders/post/blur.json");
   public static final int a = 10;
   static final Logger h = LogUtils.getLogger();
   private static final boolean i = false;
   public static final float b = 0.05F;
   private static final float j = 1000.0F;
   final C_3391_ k;
   private final C_77_ l;
   private final C_212974_ m = C_212974_.m_216327_();
   private float n;
   public final ItemInHandRenderer c;
   private final MapRenderer o;
   private final C_4148_ p;
   private int q;
   private float r;
   private float s;
   private float t;
   private float u;
   private boolean v = true;
   private boolean w = true;
   private long x;
   private boolean y;
   private long z = Util.c();
   private final LightTexture A;
   private final C_4474_ B = new C_4474_();
   private boolean C;
   private float D = 1.0F;
   private float E;
   private float F;
   public static final int d = 40;
   @Nullable
   private C_1391_ G;
   private int H;
   private float I;
   private float J;
   @Nullable
   C_4145_ K;
   @Nullable
   private C_4145_ L;
   private boolean M;
   private final Camera N = new Camera();
   @Nullable
   public ShaderInstance e;
   private final Map<String, ShaderInstance> O = Maps.newHashMap();
   @Nullable
   private static ShaderInstance P;
   @Nullable
   private static ShaderInstance Q;
   @Nullable
   private static ShaderInstance R;
   @Nullable
   private static ShaderInstance S;
   @Nullable
   private static ShaderInstance T;
   @Nullable
   private static ShaderInstance U;
   @Nullable
   private static ShaderInstance V;
   @Nullable
   private static ShaderInstance W;
   @Nullable
   private static ShaderInstance X;
   @Nullable
   private static ShaderInstance Y;
   @Nullable
   private static ShaderInstance Z;
   @Nullable
   private static ShaderInstance aa;
   @Nullable
   private static ShaderInstance ab;
   @Nullable
   private static ShaderInstance ac;
   @Nullable
   private static ShaderInstance ad;
   @Nullable
   private static ShaderInstance ae;
   @Nullable
   private static ShaderInstance af;
   @Nullable
   private static ShaderInstance ag;
   @Nullable
   private static ShaderInstance ah;
   @Nullable
   private static ShaderInstance ai;
   @Nullable
   private static ShaderInstance aj;
   @Nullable
   private static ShaderInstance ak;
   @Nullable
   private static ShaderInstance al;
   @Nullable
   private static ShaderInstance am;
   @Nullable
   private static ShaderInstance an;
   @Nullable
   private static ShaderInstance ao;
   @Nullable
   private static ShaderInstance ap;
   @Nullable
   private static ShaderInstance aq;
   @Nullable
   private static ShaderInstance ar;
   @Nullable
   private static ShaderInstance as;
   @Nullable
   private static ShaderInstance at;
   @Nullable
   private static ShaderInstance au;
   @Nullable
   private static ShaderInstance av;
   @Nullable
   private static ShaderInstance aw;
   @Nullable
   private static ShaderInstance ax;
   @Nullable
   private static ShaderInstance ay;
   @Nullable
   private static ShaderInstance az;
   @Nullable
   private static ShaderInstance aA;
   @Nullable
   private static ShaderInstance aB;
   @Nullable
   private static ShaderInstance aC;
   @Nullable
   private static ShaderInstance aD;
   @Nullable
   private static ShaderInstance aE;
   @Nullable
   private static ShaderInstance aF;
   @Nullable
   private static ShaderInstance aG;
   @Nullable
   private static ShaderInstance aH;
   @Nullable
   private static ShaderInstance aI;
   @Nullable
   private static ShaderInstance aJ;
   @Nullable
   private static ShaderInstance aK;
   @Nullable
   private static ShaderInstance aL;
   @Nullable
   private static ShaderInstance aM;
   @Nullable
   private static ShaderInstance aN;
   @Nullable
   private static ShaderInstance aO;
   @Nullable
   private static ShaderInstance aP;
   @Nullable
   private static ShaderInstance aQ;
   @Nullable
   private static ShaderInstance aR;
   @Nullable
   private static ShaderInstance aS;
   @Nullable
   private static ShaderInstance aT;
   private boolean initialized = false;
   private C_1596_ updatedWorld = null;
   private float clipDistance = 128.0F;
   private long lastServerTime = 0L;
   private int lastServerTicks = 0;
   private int serverWaitTime = 0;
   private int serverWaitTimeCurrent = 0;
   private float avgServerTimeDiff = 0.0F;
   private float avgServerTickDiff = 0.0F;
   private C_4145_[] fxaaShaders = new C_4145_[10];
   private boolean guiLoadingVisible = false;

   public GameRenderer(C_3391_ mcIn, ItemInHandRenderer itemRendererIn, C_77_ resourceManagerIn, C_4148_ renderTypeBuffersIn) {
      this.k = mcIn;
      this.l = resourceManagerIn;
      this.c = itemRendererIn;
      this.o = new MapRenderer(mcIn.aa(), mcIn.m_319582_());
      this.A = new LightTexture(this, mcIn);
      this.p = renderTypeBuffersIn;
      this.K = null;
   }

   public void close() {
      this.A.close();
      this.o.close();
      this.B.close();
      this.b();
      this.at();
      if (this.L != null) {
         this.L.close();
      }

      if (this.e != null) {
         this.e.close();
      }
   }

   public void a(boolean renderHandIn) {
      this.v = renderHandIn;
   }

   public void b(boolean renderOutlineIn) {
      this.w = renderOutlineIn;
   }

   public void c(boolean debugViewIn) {
      this.C = debugViewIn;
   }

   public boolean a() {
      return this.C;
   }

   public void b() {
      if (this.K != null) {
         this.K.close();
      }

      this.K = null;
   }

   public void c() {
      this.M = !this.M;
   }

   public void a(@Nullable C_507_ entityIn) {
      if (this.K != null) {
         this.K.close();
      }

      this.K = null;
      if (entityIn instanceof C_988_) {
         this.a(ResourceLocation.b("shaders/post/creeper.json"));
      } else if (entityIn instanceof C_1071_) {
         this.a(ResourceLocation.b("shaders/post/spider.json"));
      } else if (entityIn instanceof C_998_) {
         this.a(ResourceLocation.b("shaders/post/invert.json"));
      } else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
         Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, entityIn, this);
      }
   }

   private void a(ResourceLocation resourceLocationIn) {
      if (GLX.isUsingFBOs()) {
         if (this.K != null) {
            this.K.close();
         }

         try {
            this.K = new C_4145_(this.k.aa(), this.l, this.k.h(), resourceLocationIn);
            this.K.m_110025_(this.k.aM().l(), this.k.aM().m());
            this.M = true;
         } catch (IOException var3) {
            h.warn("Failed to load shader: {}", resourceLocationIn, var3);
            this.M = false;
         } catch (JsonSyntaxException var4) {
            h.warn("Failed to parse shader: {}", resourceLocationIn, var4);
            this.M = false;
         }
      }
   }

   private void b(C_140974_ resourceManagerIn) {
      if (this.L != null) {
         this.L.close();
      }

      try {
         this.L = new C_4145_(this.k.aa(), resourceManagerIn, this.k.h(), g);
         this.L.m_110025_(this.k.aM().l(), this.k.aM().m());
      } catch (IOException var3) {
         h.warn("Failed to load shader: {}", g, var3);
      } catch (JsonSyntaxException var4) {
         h.warn("Failed to parse shader: {}", g, var4);
      }
   }

   public void a(float partialTicks) {
      if (GLX.isUsingFBOs()) {
         float f = (float)this.k.m.q();
         if (this.L != null && f >= 1.0F) {
            this.L.m_321643_("Radius", f);
            this.L.m_110023_(partialTicks);
         }
      }
   }

   public C_69_ d() {
      return new C_81_<GameRenderer.a>() {
         protected GameRenderer.a a(C_77_ resourceManagerIn, C_442_ profilerIn) {
            Map<ResourceLocation, C_76_> map = resourceManagerIn.m_214159_("shaders", locIn -> {
               String s = locIn.a();
               return s.endsWith(".json") || s.endsWith(Program.a.b.b()) || s.endsWith(Program.a.a.b()) || s.endsWith(".glsl");
            });
            Map<ResourceLocation, C_76_> map1 = new HashMap();
            map.forEach((locIn, resIn) -> {
               try {
                  InputStream inputstream = resIn.m_215507_();

                  try {
                     byte[] abyte = inputstream.readAllBytes();
                     map1.put(locIn, new C_76_(resIn.m_247173_(), () -> new ByteArrayInputStream(abyte)));
                  } catch (Throwable var7) {
                     if (inputstream != null) {
                        try {
                           inputstream.close();
                        } catch (Throwable var6) {
                           var7.addSuppressed(var6);
                        }
                     }

                     throw var7;
                  }

                  if (inputstream != null) {
                     inputstream.close();
                  }
               } catch (Exception var8) {
                  GameRenderer.h.warn("Failed to read resource {}", locIn, var8);
               }
            });
            return new GameRenderer.a(resourceManagerIn, map1);
         }

         protected void a(GameRenderer.a objectIn, C_77_ resourceManagerIn, C_442_ profilerIn) {
            GameRenderer.this.c(objectIn);
            if (GameRenderer.this.K != null) {
               GameRenderer.this.K.close();
            }

            GameRenderer.this.K = null;
            GameRenderer.this.a(GameRenderer.this.k.m_91288_());
         }

         public String m_7812_() {
            return "Shader Loader";
         }
      };
   }

   public void a(C_140974_ providerIn) {
      if (this.e != null) {
         throw new RuntimeException("Blit shader already preloaded");
      } else {
         try {
            this.e = new ShaderInstance(providerIn, "blit_screen", DefaultVertexFormat.a);
         } catch (IOException var3) {
            throw new RuntimeException("could not preload blit shader", var3);
         }

         aQ = this.a(providerIn, "rendertype_gui", DefaultVertexFormat.f);
         aR = this.a(providerIn, "rendertype_gui_overlay", DefaultVertexFormat.f);
         P = this.a(providerIn, "position", DefaultVertexFormat.e);
         Q = this.a(providerIn, "position_color", DefaultVertexFormat.f);
         R = this.a(providerIn, "position_tex", DefaultVertexFormat.i);
         S = this.a(providerIn, "position_tex_color", DefaultVertexFormat.j);
         aD = this.a(providerIn, "rendertype_text", DefaultVertexFormat.k);
      }
   }

   private ShaderInstance a(C_140974_ providerIn, String nameIn, VertexFormat formatIn) {
      try {
         ShaderInstance shaderinstance = new ShaderInstance(providerIn, nameIn, formatIn);
         this.O.put(nameIn, shaderinstance);
         return shaderinstance;
      } catch (Exception var5) {
         throw new IllegalStateException("could not preload shader " + nameIn, var5);
      }
   }

   void c(C_140974_ providerIn) {
      RenderSystem.assertOnRenderThread();
      List<Program> list = Lists.newArrayList();
      list.addAll(Program.a.b.c().values());
      list.addAll(Program.a.a.c().values());
      list.forEach(Program::a);
      List<Pair<ShaderInstance, Consumer<ShaderInstance>>> list1 = Lists.newArrayListWithCapacity(this.O.size());

      try {
         list1.add(Pair.of(new ShaderInstance(providerIn, "particle", DefaultVertexFormat.d), (Consumer)p_172713_0_ -> T = p_172713_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "position", DefaultVertexFormat.e), (Consumer)p_172710_0_ -> P = p_172710_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "position_color", DefaultVertexFormat.f), (Consumer)p_172707_0_ -> Q = p_172707_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "position_color_lightmap", DefaultVertexFormat.h), (Consumer)p_172704_0_ -> U = p_172704_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "position_color_tex_lightmap", DefaultVertexFormat.k), (Consumer)p_172698_0_ -> V = p_172698_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "position_tex", DefaultVertexFormat.i), (Consumer)p_172695_0_ -> R = p_172695_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "position_tex_color", DefaultVertexFormat.j), (Consumer)p_172692_0_ -> S = p_172692_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_solid", DefaultVertexFormat.b), (Consumer)p_172683_0_ -> W = p_172683_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_cutout_mipped", DefaultVertexFormat.b), (Consumer)p_172680_0_ -> X = p_172680_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_cutout", DefaultVertexFormat.b), (Consumer)p_172677_0_ -> Y = p_172677_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_translucent", DefaultVertexFormat.b), (Consumer)p_172674_0_ -> Z = p_172674_0_));
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_translucent_moving_block", DefaultVertexFormat.b), (Consumer)p_172671_0_ -> aa = p_172671_0_)
         );
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_armor_cutout_no_cull", DefaultVertexFormat.c), (Consumer)p_172665_0_ -> ab = p_172665_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_solid", DefaultVertexFormat.c), (Consumer)p_172662_0_ -> ac = p_172662_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_cutout", DefaultVertexFormat.c), (Consumer)p_172659_0_ -> ad = p_172659_0_));
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_entity_cutout_no_cull", DefaultVertexFormat.c), (Consumer)p_172656_0_ -> ae = p_172656_0_)
         );
         list1.add(
            Pair.of(
               new ShaderInstance(providerIn, "rendertype_entity_cutout_no_cull_z_offset", DefaultVertexFormat.c), (Consumer)p_172653_0_ -> af = p_172653_0_
            )
         );
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_item_entity_translucent_cull", DefaultVertexFormat.c), (Consumer)p_172650_0_ -> ag = p_172650_0_)
         );
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_entity_translucent_cull", DefaultVertexFormat.c), (Consumer)p_172647_0_ -> ah = p_172647_0_)
         );
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_translucent", DefaultVertexFormat.c), (Consumer)p_172644_0_ -> ai = p_172644_0_));
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_entity_translucent_emissive", DefaultVertexFormat.c), (Consumer)p_172641_0_ -> aj = p_172641_0_)
         );
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_smooth_cutout", DefaultVertexFormat.c), (Consumer)p_172638_0_ -> ak = p_172638_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_beacon_beam", DefaultVertexFormat.b), (Consumer)p_172839_0_ -> al = p_172839_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_decal", DefaultVertexFormat.c), (Consumer)p_172836_0_ -> am = p_172836_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_no_outline", DefaultVertexFormat.c), (Consumer)p_172833_0_ -> an = p_172833_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_shadow", DefaultVertexFormat.c), (Consumer)p_172830_0_ -> ao = p_172830_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_alpha", DefaultVertexFormat.c), (Consumer)p_172827_0_ -> ap = p_172827_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_eyes", DefaultVertexFormat.c), (Consumer)p_172824_0_ -> aq = p_172824_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_energy_swirl", DefaultVertexFormat.c), (Consumer)p_172821_0_ -> ar = p_172821_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_leash", DefaultVertexFormat.h), (Consumer)p_172818_0_ -> at = p_172818_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_water_mask", DefaultVertexFormat.e), (Consumer)p_172815_0_ -> au = p_172815_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_outline", DefaultVertexFormat.j), (Consumer)p_172812_0_ -> av = p_172812_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_armor_entity_glint", DefaultVertexFormat.i), (Consumer)p_172806_0_ -> ax = p_172806_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_glint_translucent", DefaultVertexFormat.i), (Consumer)p_172804_0_ -> ay = p_172804_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_glint", DefaultVertexFormat.i), (Consumer)p_172802_0_ -> az = p_172802_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_glint", DefaultVertexFormat.i), (Consumer)p_172798_0_ -> aB = p_172798_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_entity_glint_direct", DefaultVertexFormat.i), (Consumer)p_172795_0_ -> aC = p_172795_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_text", DefaultVertexFormat.k), (Consumer)p_172793_0_ -> aD = p_172793_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_text_background", DefaultVertexFormat.h), (Consumer)p_268793_0_ -> aE = p_268793_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_text_intensity", DefaultVertexFormat.k), (Consumer)p_172791_0_ -> aF = p_172791_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_text_see_through", DefaultVertexFormat.k), (Consumer)p_172788_0_ -> aG = p_172788_0_));
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_text_background_see_through", DefaultVertexFormat.h), (Consumer)p_268792_0_ -> aH = p_268792_0_)
         );
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_text_intensity_see_through", DefaultVertexFormat.k), (Consumer)p_172786_0_ -> aI = p_172786_0_)
         );
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_lightning", DefaultVertexFormat.f), (Consumer)p_172784_0_ -> aJ = p_172784_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_tripwire", DefaultVertexFormat.b), (Consumer)p_172781_0_ -> aK = p_172781_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_end_portal", DefaultVertexFormat.e), (Consumer)p_172777_0_ -> aL = p_172777_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_end_gateway", DefaultVertexFormat.e), (Consumer)p_172773_0_ -> aM = p_172773_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_clouds", DefaultVertexFormat.m), (Consumer)p_317418_0_ -> aN = p_317418_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_lines", DefaultVertexFormat.g), (Consumer)p_172732_0_ -> aO = p_172732_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_crumbling", DefaultVertexFormat.b), (Consumer)p_234229_0_ -> aP = p_234229_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_gui", DefaultVertexFormat.f), (Consumer)p_285677_0_ -> aQ = p_285677_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_gui_overlay", DefaultVertexFormat.f), (Consumer)p_285675_0_ -> aR = p_285675_0_));
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_gui_text_highlight", DefaultVertexFormat.f), (Consumer)p_285674_0_ -> aS = p_285674_0_));
         list1.add(
            Pair.of(new ShaderInstance(providerIn, "rendertype_gui_ghost_recipe_overlay", DefaultVertexFormat.f), (Consumer)p_285676_0_ -> aT = p_285676_0_)
         );
         list1.add(Pair.of(new ShaderInstance(providerIn, "rendertype_breeze_wind", DefaultVertexFormat.c), (Consumer)p_304052_0_ -> as = p_304052_0_));
         this.b(providerIn);
         ReflectorForge.postModLoaderEvent(Reflector.RegisterShadersEvent_Constructor, providerIn, list1);
      } catch (IOException var5) {
         list1.forEach(pairIn -> ((ShaderInstance)pairIn.getFirst()).close());
         throw new RuntimeException("could not reload shaders", var5);
      }

      this.at();
      list1.forEach(pairIn -> {
         ShaderInstance shaderinstance = (ShaderInstance)pairIn.getFirst();
         this.O.put(shaderinstance.i(), shaderinstance);
         ((Consumer)pairIn.getSecond()).accept(shaderinstance);
      });
   }

   private void at() {
      RenderSystem.assertOnRenderThread();
      this.O.values().forEach(ShaderInstance::close);
      this.O.clear();
   }

   @Nullable
   public ShaderInstance a(@Nullable String nameIn) {
      return nameIn == null ? null : (ShaderInstance)this.O.get(nameIn);
   }

   public void e() {
      this.au();
      this.A.a();
      if (this.k.m_91288_() == null) {
         this.k.m_91118_(this.k.f_91074_);
      }

      this.N.a();
      this.c.a();
      this.q++;
      if (this.k.r.s().i()) {
         this.k.f.a(this.N);
         this.u = this.t;
         if (this.k.l.j().c()) {
            this.t += 0.05F;
            if (this.t > 1.0F) {
               this.t = 1.0F;
            }
         } else if (this.t > 0.0F) {
            this.t -= 0.0125F;
         }

         if (this.H > 0) {
            this.H--;
            if (this.H == 0) {
               this.G = null;
            }
         }
      }
   }

   @Nullable
   public C_4145_ f() {
      return this.K;
   }

   public void a(int width, int height) {
      if (this.K != null) {
         this.K.m_110025_(width, height);
      }

      if (this.L != null) {
         this.L.m_110025_(width, height);
      }

      this.k.f.a(width, height);
   }

   public void b(float partialTicks) {
      C_507_ entity = this.k.m_91288_();
      if (entity != null && this.k.r != null && this.k.f_91074_ != null) {
         this.k.m_91307_().m_6180_("pick");
         double d0 = this.k.f_91074_.gy();
         double d1 = this.k.f_91074_.gz();
         C_3043_ hitresult = this.a(entity, d0, d1, partialTicks);
         this.k.f_91077_ = hitresult;
         this.k.f_91076_ = hitresult instanceof C_3042_ entityhitresult ? entityhitresult.m_82443_() : null;
         this.k.m_91307_().m_7238_();
      }
   }

   private C_3043_ a(C_507_ entityHitIn, double blockRangeIn, double entityRangeIn, float partialTicks) {
      double d0 = Math.max(blockRangeIn, entityRangeIn);
      double d1 = Mth.k(d0);
      Vec3 vec3 = entityHitIn.k(partialTicks);
      C_3043_ hitresult = entityHitIn.m_19907_(d0, partialTicks, false);
      double d2 = hitresult.e().g(vec3);
      if (hitresult.m_6662_() != C_3044_.MISS) {
         d1 = d2;
         d0 = Math.sqrt(d2);
      }

      Vec3 vec31 = entityHitIn.g(partialTicks);
      Vec3 vec32 = vec3.b(vec31.c * d0, vec31.d * d0, vec31.e * d0);
      float f = 1.0F;
      C_3040_ aabb = entityHitIn.m_20191_().b(vec31.a(d0)).m_82377_(1.0, 1.0, 1.0);
      C_3042_ entityhitresult = C_1168_.a(entityHitIn, vec3, vec32, aabb, entityIn -> !entityIn.m_5833_() && entityIn.m_6087_(), d1);
      return entityhitresult != null && entityhitresult.e().g(vec3) < d2 ? a(entityhitresult, vec3, entityRangeIn) : a(hitresult, vec3, blockRangeIn);
   }

   private static C_3043_ a(C_3043_ resultIn, Vec3 vecIn, double entityRangeIn) {
      Vec3 vec3 = resultIn.e();
      if (!vec3.a((C_4703_)vecIn, entityRangeIn)) {
         Vec3 vec31 = resultIn.e();
         Direction direction = Direction.a(vec31.c - vecIn.c, vec31.d - vecIn.d, vec31.e - vecIn.e);
         return C_3041_.a(vec31, direction, C_4675_.m_274446_(vec31));
      } else {
         return resultIn;
      }
   }

   private void au() {
      float f = 1.0F;
      if (this.k.m_91288_() instanceof AbstractClientPlayer abstractclientplayer) {
         f = abstractclientplayer.c();
      }

      this.s = this.r;
      this.r = this.r + (f - this.r) * 0.5F;
      if (this.r > 1.5F) {
         this.r = 1.5F;
      }

      if (this.r < 0.1F) {
         this.r = 0.1F;
      }
   }

   public double a(Camera activeRenderInfoIn, float partialTicks, boolean useFOVSetting) {
      if (this.C) {
         return 90.0;
      } else {
         double d0 = 70.0;
         if (useFOVSetting) {
            d0 = (double)this.k.m.ah().c().intValue();
            boolean playerScoping = this.k.m_91288_() instanceof AbstractClientPlayer && ((AbstractClientPlayer)this.k.m_91288_()).m_150108_();
            if (Config.isDynamicFov() || playerScoping) {
               d0 *= (double)Mth.i(partialTicks, this.s, this.r);
            }
         }

         boolean zoomActive = false;
         if (this.k.f_91080_ == null) {
            zoomActive = this.k.m.ofKeyBindZoom.m_90857_();
         }

         if (zoomActive) {
            if (!Config.zoomMode) {
               Config.zoomMode = true;
               Config.zoomSmoothCamera = this.k.m.aa;
               this.k.m.aa = true;
               this.k.f.r();
            }

            if (Config.zoomMode) {
               d0 /= 4.0;
            }
         } else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.k.m.aa = Config.zoomSmoothCamera;
            this.k.f.r();
         }

         if (activeRenderInfoIn.g() instanceof C_524_ && ((C_524_)activeRenderInfoIn.g()).m_21224_()) {
            float f = Math.min((float)((C_524_)activeRenderInfoIn.g()).f_20919_ + partialTicks, 20.0F);
            d0 /= (double)((1.0F - 500.0F / (f + 500.0F)) * 2.0F + 1.0F);
         }

         C_141436_ fogtype = activeRenderInfoIn.k();
         if (fogtype == C_141436_.LAVA || fogtype == C_141436_.WATER) {
            d0 *= Mth.d(this.k.m.ak().c(), 1.0, 0.85714287F);
         }

         return Reflector.ForgeHooksClient_getFieldOfView.exists()
            ? Reflector.callDouble(Reflector.ForgeHooksClient_getFieldOfView, this, activeRenderInfoIn, partialTicks, d0, useFOVSetting)
            : d0;
      }
   }

   private void a(PoseStack matrixStackIn, float partialTicks) {
      if (this.k.m_91288_() instanceof C_524_ livingentity) {
         float f2 = (float)livingentity.f_20916_ - partialTicks;
         if (livingentity.m_21224_()) {
            float f = Math.min((float)livingentity.f_20919_ + partialTicks, 20.0F);
            matrixStackIn.a(C_252363_.f_252403_.m_252977_(40.0F - 8000.0F / (f + 200.0F)));
         }

         if (f2 < 0.0F) {
            return;
         }

         f2 /= (float)livingentity.f_20917_;
         f2 = Mth.a(f2 * f2 * f2 * f2 * (float) Math.PI);
         float f3 = livingentity.m_264297_();
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(-f3));
         float f1 = (float)((double)(-f2) * 14.0 * this.k.m.ao().c());
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(f1));
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(f3));
      }
   }

   private void b(PoseStack matrixStackIn, float partialTicks) {
      if (this.k.m_91288_() instanceof C_1141_) {
         C_1141_ player = (C_1141_)this.k.m_91288_();
         float f = player.f_19787_ - player.f_19867_;
         float f1 = -(player.f_19787_ + f * partialTicks);
         float f2 = Mth.i(partialTicks, player.f_36099_, player.f_36100_);
         matrixStackIn.a(Mth.a(f1 * (float) Math.PI) * f2 * 0.5F, -Math.abs(Mth.b(f1 * (float) Math.PI) * f2), 0.0F);
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(Mth.a(f1 * (float) Math.PI) * f2 * 3.0F));
         matrixStackIn.a(C_252363_.f_252529_.m_252977_(Math.abs(Mth.b(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F));
      }
   }

   public void a(float zoomIn, float yawIn, float pitchIn) {
      this.D = zoomIn;
      this.E = yawIn;
      this.F = pitchIn;
      this.b(false);
      this.a(false);
      this.a(C_336468_.f_337638_);
      this.D = 1.0F;
   }

   private void a(Camera activeRenderInfoIn, float partialTicks, Matrix4f matrixStackIn) {
      this.renderHand(activeRenderInfoIn, partialTicks, matrixStackIn, true, true, false);
   }

   public void renderHand(
      Camera activeRenderInfoIn, float partialTicks, Matrix4f matrixStackIn, boolean renderItem, boolean renderOverlay, boolean renderTranslucent
   ) {
      if (!this.C) {
         Shaders.beginRenderFirstPersonHand(renderTranslucent);
         this.a(this.a(this.a(activeRenderInfoIn, partialTicks, false)));
         PoseStack posestack = new PoseStack();
         boolean flag = false;
         if (renderItem) {
            posestack.a();
            posestack.a(matrixStackIn.invert(new Matrix4f()));
            Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
            matrix4fstack.pushMatrix().set(matrixStackIn);
            RenderSystem.applyModelViewMatrix();
            this.a(posestack, partialTicks);
            if (this.k.m.ab().c()) {
               this.b(posestack, partialTicks);
            }

            flag = this.k.m_91288_() instanceof C_524_ && ((C_524_)this.k.m_91288_()).m_5803_();
            if (this.k.m.aB().m_90612_() && !flag && !this.k.m.Y && this.k.f_91072_.m_105295_() != C_1593_.SPECTATOR) {
               this.A.c();
               if (Config.isShaders()) {
                  ShadersRender.renderItemFP(
                     this.c, partialTicks, posestack, this.p.c(), this.k.f_91074_, this.k.ap().a(this.k.f_91074_, partialTicks), renderTranslucent
                  );
               } else {
                  this.c.a(partialTicks, posestack, this.p.c(), this.k.f_91074_, this.k.ap().a(this.k.f_91074_, partialTicks));
               }

               this.A.b();
            }

            matrix4fstack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            posestack.b();
         }

         Shaders.endRenderFirstPersonHand();
         if (!renderOverlay) {
            return;
         }

         this.A.b();
         if (this.k.m.aB().m_90612_() && !flag) {
            ScreenEffectRenderer.a(this.k, posestack);
         }
      }
   }

   public void a(Matrix4f matrixIn) {
      RenderSystem.setProjectionMatrix(matrixIn, C_276405_.f_276450_);
   }

   public Matrix4f a(double fovModifierIn) {
      Matrix4f matrix4f = new Matrix4f();
      if (Config.isShaders() && Shaders.isRenderingFirstPersonHand()) {
         Shaders.applyHandDepth(matrix4f);
      }

      this.clipDistance = this.n + 1024.0F;
      if (this.D != 1.0F) {
         matrix4f.translate(this.E, -this.F, 0.0F);
         matrix4f.scale(this.D, this.D, 1.0F);
      }

      return matrix4f.perspective((float)(fovModifierIn * (float) (Math.PI / 180.0)), (float)this.k.aM().l() / (float)this.k.aM().m(), 0.05F, this.clipDistance);
   }

   public float g() {
      return this.n * 4.0F;
   }

   public static float a(C_524_ livingEntityIn, float entitylivingbaseIn) {
      C_498_ mobeffectinstance = livingEntityIn.m_21124_(C_500_.f_19611_);
      return !mobeffectinstance.m_267633_(200)
         ? 1.0F
         : 0.7F + Mth.a(((float)mobeffectinstance.m_19557_() - entitylivingbaseIn) * (float) Math.PI * 0.2F) * 0.3F;
   }

   public void a(C_336468_ partialTicks, boolean renderWorldIn) {
      this.frameInit();
      if (!this.k.m_91302_() && this.k.m.n && (!this.k.m.Z().c() || !this.k.f_91067_.m_91584_())) {
         if (Util.c() - this.z > 500L) {
            this.k.m_91358_(false);
         }
      } else {
         this.z = Util.c();
      }

      if (!this.k.f_91079_) {
         boolean flag = this.k.m_293453_();
         int i = (int)(this.k.f_91067_.m_91589_() * (double)this.k.aM().p() / (double)this.k.aM().n());
         int j = (int)(this.k.f_91067_.m_91594_() * (double)this.k.aM().q() / (double)this.k.aM().o());
         if (flag && renderWorldIn && this.k.r != null && !Config.isReloadingResources()) {
            this.k.m_91307_().m_6180_("level");
            this.a(partialTicks);
            this.av();
            this.k.f.b();
            if (this.K != null && this.M) {
               RenderSystem.disableBlend();
               RenderSystem.disableDepthTest();
               RenderSystem.resetTextureMatrix();
               this.K.m_110023_(partialTicks.m_339005_());
               RenderSystem.enableTexture();
            }

            this.k.h().a(true);
         } else {
            RenderSystem.viewport(0, 0, this.k.aM().l(), this.k.aM().m());
         }

         Window window = this.k.aM();
         RenderSystem.clear(256, C_3391_.f_91002_);
         float guiFarPlane = Reflector.ForgeHooksClient_getGuiFarPlane.exists() ? Reflector.ForgeHooksClient_getGuiFarPlane.callFloat() : 21000.0F;
         Matrix4f matrix4f = new Matrix4f()
            .setOrtho(0.0F, (float)((double)window.l() / window.t()), (float)((double)window.m() / window.t()), 0.0F, 1000.0F, guiFarPlane);
         RenderSystem.setProjectionMatrix(matrix4f, C_276405_.f_276633_);
         Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
         matrix4fstack.pushMatrix();
         float guiOffsetZ = Reflector.ForgeHooksClient_getGuiFarPlane.exists() ? 1000.0F - guiFarPlane : -11000.0F;
         matrix4fstack.translation(0.0F, 0.0F, guiOffsetZ);
         RenderSystem.applyModelViewMatrix();
         C_3144_.m_84931_();
         GuiGraphics guigraphics = new GuiGraphics(this.k, this.p.c());
         if (this.A.isCustom()) {
            this.A.setAllowed(false);
         }

         if (flag && renderWorldIn && this.k.r != null) {
            this.k.m_91307_().m_6182_("gui");
            if (this.k.f_91074_ != null) {
               float f = Mth.i(partialTicks.m_338527_(false), this.k.f_91074_.f_108590_, this.k.f_91074_.f_108589_);
               float f1 = this.k.m.aj().c().floatValue();
               if (f > 0.0F && this.k.f_91074_.b(C_500_.f_19604_) && f1 < 1.0F) {
                  this.b(guigraphics, f * (1.0F - f1));
               }
            }

            if (!this.k.m.Y) {
               this.a(guigraphics, partialTicks.m_338527_(false));
            }

            this.k.l.a(guigraphics, partialTicks);
            if (this.k.m.ofQuickInfo && !this.k.aN().n) {
               QuickInfo.render(guigraphics);
            }

            RenderSystem.clear(256, C_3391_.f_91002_);
            this.k.m_91307_().m_7238_();
         }

         if (this.guiLoadingVisible != (this.k.m_91265_() != null)) {
            if (this.k.m_91265_() != null) {
               LoadingOverlay.a(this.k);
               if (this.k.m_91265_() instanceof LoadingOverlay) {
                  LoadingOverlay rlpg = (LoadingOverlay)this.k.m_91265_();
                  rlpg.update();
               }
            }

            this.guiLoadingVisible = this.k.m_91265_() != null;
         }

         if (this.k.m_91265_() != null) {
            try {
               this.k.m_91265_().a(guigraphics, i, j, partialTicks.m_338557_());
            } catch (Throwable var16) {
               CrashReport crashreport = CrashReport.a(var16, "Rendering overlay");
               C_4909_ crashreportcategory = crashreport.a("Overlay render details");
               crashreportcategory.m_128165_("Overlay name", () -> this.k.m_91265_().getClass().getCanonicalName());
               throw new C_5204_(crashreport);
            }
         } else if (flag && this.k.f_91080_ != null) {
            try {
               if (Config.isCustomEntityModels()) {
                  CustomEntityModels.onRenderScreen(this.k.f_91080_);
               }

               if (Reflector.ForgeHooksClient_drawScreen.exists()) {
                  Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, this.k.f_91080_, guigraphics, i, j, partialTicks.m_338557_());
               } else {
                  this.k.f_91080_.c(guigraphics, i, j, partialTicks.m_338557_());
               }
            } catch (Throwable var17) {
               CrashReport crashreport1 = CrashReport.a(var17, "Rendering screen");
               C_4909_ crashreportcategory1 = crashreport1.a("Screen render details");
               crashreportcategory1.m_128165_("Screen name", () -> this.k.f_91080_.getClass().getCanonicalName());
               crashreportcategory1.m_128165_(
                  "Mouse location",
                  () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", i, j, this.k.f_91067_.m_91589_(), this.k.f_91067_.m_91594_())
               );
               crashreportcategory1.m_128165_(
                  "Screen size",
                  () -> String.format(
                        Locale.ROOT,
                        "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f",
                        this.k.aM().p(),
                        this.k.aM().q(),
                        this.k.aM().l(),
                        this.k.aM().m(),
                        this.k.aM().t()
                     )
               );
               throw new C_5204_(crashreport1);
            }

            try {
               if (this.k.f_91080_ != null) {
                  this.k.f_91080_.m_169417_();
               }
            } catch (Throwable var15) {
               CrashReport crashreport2 = CrashReport.a(var15, "Narrating screen");
               C_4909_ crashreportcategory2 = crashreport2.a("Screen details");
               crashreportcategory2.m_128165_("Screen name", () -> this.k.f_91080_.getClass().getCanonicalName());
               throw new C_5204_(crashreport2);
            }
         }

         if (flag && renderWorldIn && this.k.r != null) {
            this.k.l.b(guigraphics, partialTicks);
         }

         if (flag) {
            this.k.m_91307_().m_6180_("toasts");
            this.k.m_91300_().a(guigraphics);
            this.k.m_91307_().m_7238_();
         }

         guigraphics.e();
         matrix4fstack.popMatrix();
         RenderSystem.applyModelViewMatrix();
         this.A.setAllowed(true);
      }

      this.frameFinish();
      this.waitForServerThread();
      MemoryMonitor.update();
      Lagometer.updateLagometer();
   }

   private void av() {
      if (!this.y && this.k.m_91090_()) {
         long i = Util.c();
         if (i - this.x >= 1000L) {
            this.x = i;
            IntegratedServer integratedserver = this.k.V();
            if (integratedserver != null && !integratedserver.m_129918_()) {
               integratedserver.m_182649_().ifPresent(pathIn -> {
                  if (Files.isRegularFile(pathIn, new LinkOption[0])) {
                     this.y = true;
                  } else {
                     this.a(pathIn);
                  }
               });
            }
         }
      }
   }

   private void a(Path pathIn) {
      if (this.k.f.k() > 10 && this.k.f.q()) {
         NativeImage nativeimage = Screenshot.a(this.k.h());
         Util.h().execute(() -> {
            int i = nativeimage.a();
            int j = nativeimage.b();
            int k = 0;
            int l = 0;
            if (i > j) {
               k = (i - j) / 2;
               i = j;
            } else {
               l = (j - i) / 2;
               j = i;
            }

            try (NativeImage nativeimage1 = new NativeImage(64, 64, false)) {
               nativeimage.a(k, l, i, j, nativeimage1);
               nativeimage1.a(pathIn);
            } catch (IOException var16) {
               h.warn("Couldn't save auto screenshot", var16);
            } finally {
               nativeimage.close();
            }
         });
      }
   }

   private boolean aw() {
      if (!this.w) {
         return false;
      } else {
         C_507_ entity = this.k.m_91288_();
         boolean flag = entity instanceof C_1141_ && !this.k.m.Y;
         if (flag && !((C_1141_)entity).m_150110_().f_35938_) {
            C_1391_ itemstack = ((C_524_)entity).m_21205_();
            C_3043_ hitresult = this.k.f_91077_;
            if (hitresult != null && hitresult.m_6662_() == C_3044_.BLOCK) {
               C_4675_ blockpos = ((C_3041_)hitresult).m_82425_();
               BlockState blockstate = this.k.r.a_(blockpos);
               if (this.k.f_91072_.m_105295_() == C_1593_.SPECTATOR) {
                  flag = blockstate.m_60750_(this.k.r, blockpos) != null;
               } else {
                  C_2070_ blockinworld = new C_2070_(this.k.r, blockpos, false);
                  C_4705_<C_1706_> registry = this.k.r.m_9598_().m_175515_(C_256686_.f_256747_);
                  flag = !itemstack.m_41619_() && (itemstack.m_323082_(blockinworld) || itemstack.m_321400_(blockinworld));
               }
            }
         }

         return flag;
      }
   }

   public void a(C_336468_ partialTicks) {
      float f = partialTicks.m_338527_(true);
      this.A.a(f);
      if (this.k.m_91288_() == null) {
         this.k.m_91118_(this.k.f_91074_);
      }

      this.b(f);
      if (Config.isShaders()) {
         Shaders.beginRender(this.k, this.N, f);
      }

      this.k.m_91307_().m_6180_("center");
      boolean isShaders = Config.isShaders();
      if (isShaders) {
         Shaders.beginRenderPass(f);
      }

      boolean flag = this.aw();
      this.k.m_91307_().m_6182_("camera");
      Camera camera = this.N;
      C_507_ entity = (C_507_)(this.k.m_91288_() == null ? this.k.f_91074_ : this.k.m_91288_());
      float f1 = this.k.r.s().a(entity) ? 1.0F : f;
      camera.a(this.k.r, entity, !this.k.m.aB().m_90612_(), this.k.m.aB().m_90613_(), f1);
      this.n = (float)(this.k.m.aE() * 16);
      double d0 = this.a(camera, f, true);
      Matrix4f matrix4f = this.a(d0);
      Matrix4f matrixProjection = matrix4f;
      if (Shaders.isEffectsModelView()) {
         matrix4f = new Matrix4f();
      }

      PoseStack posestack = new PoseStack();
      this.a(posestack, camera.p());
      if (this.k.m.ab().c()) {
         this.b(posestack, camera.p());
      }

      matrix4f.mul(posestack.c().a());
      float f2 = this.k.m.aj().c().floatValue();
      float f3 = Mth.i(f, this.k.f_91074_.f_108590_, this.k.f_91074_.f_108589_) * f2 * f2;
      if (f3 > 0.0F) {
         int i = this.k.f_91074_.b(C_500_.f_19604_) ? 7 : 20;
         float f4 = 5.0F / (f3 * f3 + 5.0F) - f3 * 0.04F;
         f4 *= f4;
         Vector3f vector3f = new Vector3f(0.0F, Mth.g / 2.0F, Mth.g / 2.0F);
         float f5 = ((float)this.q + f) * (float)i * (float) (Math.PI / 180.0);
         matrix4f.rotate(f5, vector3f);
         matrix4f.scale(1.0F / f4, 1.0F, 1.0F);
         matrix4f.rotate(-f5, vector3f);
      }

      Matrix4f matrixEffects = matrix4f;
      if (Shaders.isEffectsModelView()) {
         matrix4f = matrixProjection;
      }

      this.a(matrix4f);
      Quaternionf quaternionf = camera.f().conjugate(new Quaternionf());
      Matrix4f matrix4f1 = new Matrix4f().rotation(quaternionf);
      if (Shaders.isEffectsModelView()) {
         matrix4f1 = matrixEffects.mul(matrix4f1);
      }

      this.k.f.a(camera.b(), matrix4f1, this.a(Math.max(d0, (double)this.k.m.ah().c().intValue())));
      this.k.f.a(partialTicks, flag, camera, this, this.A, matrix4f1, matrix4f);
      this.k.m_91307_().m_6182_("forge_render_last");
      ReflectorForge.dispatchRenderStageS(
         Reflector.RenderLevelStageEvent_Stage_AFTER_LEVEL, this.k.f, matrix4f1, matrix4f, this.k.f.getTicks(), camera, this.k.f.getFrustum()
      );
      this.k.m_91307_().m_6182_("hand");
      if (this.v && !Shaders.isShadowPass) {
         if (isShaders) {
            ShadersRender.renderHand1(this, matrix4f1, camera, f);
            Shaders.renderCompositeFinal();
         }

         RenderSystem.clear(256, C_3391_.f_91002_);
         if (isShaders) {
            ShadersRender.renderFPOverlay(this, matrix4f1, camera, f);
         } else {
            this.a(camera, f, matrix4f1);
         }
      }

      if (isShaders) {
         Shaders.endRender();
      }

      this.k.m_91307_().m_7238_();
   }

   public void h() {
      this.G = null;
      this.o.a();
      this.N.o();
      this.y = false;
   }

   public MapRenderer i() {
      return this.o;
   }

   private void waitForServerThread() {
      this.serverWaitTimeCurrent = 0;
      if (!Config.isSmoothWorld() || !Config.isSingleProcessor()) {
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
      } else if (this.k.m_91090_()) {
         IntegratedServer srv = this.k.V();
         if (srv != null) {
            boolean paused = this.k.m_91104_();
            if (!paused && !(this.k.f_91080_ instanceof ReceivingLevelScreen)) {
               if (this.serverWaitTime > 0) {
                  Lagometer.timerServer.start();
                  Config.sleep((long)this.serverWaitTime);
                  Lagometer.timerServer.end();
                  this.serverWaitTimeCurrent = this.serverWaitTime;
               }

               long timeNow = System.nanoTime() / 1000000L;
               if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                  long timeDiff = timeNow - this.lastServerTime;
                  if (timeDiff < 0L) {
                     this.lastServerTime = timeNow;
                     timeDiff = 0L;
                  }

                  if (timeDiff >= 50L) {
                     this.lastServerTime = timeNow;
                     int ticks = srv.m_129921_();
                     int tickDiff = ticks - this.lastServerTicks;
                     if (tickDiff < 0) {
                        this.lastServerTicks = ticks;
                        tickDiff = 0;
                     }

                     if (tickDiff < 1 && this.serverWaitTime < 100) {
                        this.serverWaitTime += 2;
                     }

                     if (tickDiff > 1 && this.serverWaitTime > 0) {
                        this.serverWaitTime--;
                     }

                     this.lastServerTicks = ticks;
                  }
               } else {
                  this.lastServerTime = timeNow;
                  this.lastServerTicks = srv.m_129921_();
                  this.avgServerTickDiff = 1.0F;
                  this.avgServerTimeDiff = 50.0F;
               }
            } else {
               if (this.k.f_91080_ instanceof ReceivingLevelScreen) {
                  Config.sleep(20L);
               }

               this.lastServerTime = 0L;
               this.lastServerTicks = 0;
            }
         }
      }
   }

   private void frameInit() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/gui/GuiChatOF.<init>(Lnet/minecraft/src/C_3538_;)V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:1153)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:902)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 000: invokestatic net/optifine/Config.frameStart ()V
      // 003: invokestatic net/optifine/GlErrors.frameStart ()V
      // 006: aload 0
      // 007: getfield GameRenderer.initialized Z
      // 00a: ifne 029
      // 00d: invokestatic net/optifine/reflect/ReflectorResolver.resolve ()V
      // 010: invokestatic net/optifine/Config.getBitsOs ()I
      // 013: bipush 64
      // 015: if_icmpne 024
      // 018: invokestatic net/optifine/Config.getBitsJre ()I
      // 01b: bipush 32
      // 01d: if_icmpne 024
      // 020: bipush 1
      // 021: invokestatic net/optifine/Config.setNotify64BitJava (Z)V
      // 024: aload 0
      // 025: bipush 1
      // 026: putfield GameRenderer.initialized Z
      // 029: aload 0
      // 02a: getfield GameRenderer.k Lnet/minecraft/src/C_3391_;
      // 02d: getfield net/minecraft/src/C_3391_.r LClientLevel;
      // 030: astore 1
      // 031: aload 1
      // 032: ifnull 0c5
      // 035: invokestatic net/optifine/Config.getNewRelease ()Ljava/lang/String;
      // 038: ifnull 09f
      // 03b: ldc_w "HD_U"
      // 03e: ldc_w "HD_U"
      // 041: ldc_w "HD Ultra"
      // 044: invokevirtual java/lang/String.replace (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      // 047: ldc_w "L"
      // 04a: ldc_w "Light"
      // 04d: invokevirtual java/lang/String.replace (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      // 050: astore 2
      // 051: aload 2
      // 052: invokestatic net/optifine/Config.getNewRelease ()Ljava/lang/String;
      // 055: invokedynamic makeConcatWithConstants (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String; bsm=java/lang/invoke/StringConcatFactory.makeConcatWithConstants (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; args=[ "\u0001 \u0001" ]
      // 05a: astore 3
      // 05b: ldc_w "of.message.newVersion"
      // 05e: bipush 1
      // 05f: anewarray 4
      // 062: dup
      // 063: bipush 0
      // 064: aload 3
      // 065: invokedynamic makeConcatWithConstants (Ljava/lang/String;)Ljava/lang/String; bsm=java/lang/invoke/StringConcatFactory.makeConcatWithConstants (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; args=[ "ï¿½n\u0001ï¿½r" ]
      // 06a: aastore
      // 06b: invokestatic net/minecraft/src/C_4513_.m_118938_ (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 06e: invokestatic net/minecraft/src/C_4996_.m_237113_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 071: astore 4
      // 073: aload 4
      // 075: getstatic net/minecraft/src/C_5020_.f_131099_ Lnet/minecraft/src/C_5020_;
      // 078: new net/minecraft/src/C_4993_
      // 07b: dup
      // 07c: getstatic net/minecraft/src/C_4993_$C_4994_.OPEN_URL Lnet/minecraft/src/C_4993_$C_4994_;
      // 07f: ldc_w "https://optifine.net/downloads"
      // 082: invokespecial net/minecraft/src/C_4993_.<init> (Lnet/minecraft/src/C_4993_$C_4994_;Ljava/lang/String;)V
      // 085: invokevirtual net/minecraft/src/C_5020_.m_131142_ (Lnet/minecraft/src/C_4993_;)Lnet/minecraft/src/C_5020_;
      // 088: invokevirtual net/minecraft/src/C_5012_.m_6270_ (Lnet/minecraft/src/C_5020_;)Lnet/minecraft/src/C_5012_;
      // 08b: pop
      // 08c: aload 0
      // 08d: getfield GameRenderer.k Lnet/minecraft/src/C_3391_;
      // 090: getfield net/minecraft/src/C_3391_.l LGui;
      // 093: invokevirtual Gui.d ()LChatComponent;
      // 096: aload 4
      // 098: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 09b: aconst_null
      // 09c: invokestatic net/optifine/Config.setNewRelease (Ljava/lang/String;)V
      // 09f: invokestatic net/optifine/Config.isNotify64BitJava ()Z
      // 0a2: ifeq 0c5
      // 0a5: bipush 0
      // 0a6: invokestatic net/optifine/Config.setNotify64BitJava (Z)V
      // 0a9: ldc_w "of.message.java64Bit"
      // 0ac: bipush 0
      // 0ad: anewarray 4
      // 0b0: invokestatic net/minecraft/src/C_4513_.m_118938_ (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 0b3: invokestatic net/minecraft/src/C_4996_.m_237113_ (Ljava/lang/String;)Lnet/minecraft/src/C_5012_;
      // 0b6: astore 2
      // 0b7: aload 0
      // 0b8: getfield GameRenderer.k Lnet/minecraft/src/C_3391_;
      // 0bb: getfield net/minecraft/src/C_3391_.l LGui;
      // 0be: invokevirtual Gui.d ()LChatComponent;
      // 0c1: aload 2
      // 0c2: invokevirtual ChatComponent.a (Lnet/minecraft/src/C_4996_;)V
      // 0c5: aload 0
      // 0c6: getfield GameRenderer.updatedWorld Lnet/minecraft/src/C_1596_;
      // 0c9: aload 1
      // 0ca: if_acmpeq 0e7
      // 0cd: aload 0
      // 0ce: getfield GameRenderer.updatedWorld Lnet/minecraft/src/C_1596_;
      // 0d1: aload 1
      // 0d2: invokestatic net/optifine/RandomEntities.worldChanged (Lnet/minecraft/src/C_1596_;Lnet/minecraft/src/C_1596_;)V
      // 0d5: invokestatic net/optifine/Config.updateThreadPriorities ()V
      // 0d8: aload 0
      // 0d9: lconst_0
      // 0da: putfield GameRenderer.lastServerTime J
      // 0dd: aload 0
      // 0de: bipush 0
      // 0df: putfield GameRenderer.lastServerTicks I
      // 0e2: aload 0
      // 0e3: aload 1
      // 0e4: putfield GameRenderer.updatedWorld Lnet/minecraft/src/C_1596_;
      // 0e7: aload 0
      // 0e8: getstatic net/optifine/shaders/Shaders.configAntialiasingLevel I
      // 0eb: invokevirtual GameRenderer.setFxaaShader (I)Z
      // 0ee: ifne 0f5
      // 0f1: bipush 0
      // 0f2: putstatic net/optifine/shaders/Shaders.configAntialiasingLevel I
      // 0f5: aload 0
      // 0f6: getfield GameRenderer.k Lnet/minecraft/src/C_3391_;
      // 0f9: getfield net/minecraft/src/C_3391_.f_91080_ Lnet/minecraft/src/C_3583_;
      // 0fc: ifnull 127
      // 0ff: aload 0
      // 100: getfield GameRenderer.k Lnet/minecraft/src/C_3391_;
      // 103: getfield net/minecraft/src/C_3391_.f_91080_ Lnet/minecraft/src/C_3583_;
      // 106: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 109: ldc_w net/minecraft/src/C_3538_
      // 10c: if_acmpne 127
      // 10f: aload 0
      // 110: getfield GameRenderer.k Lnet/minecraft/src/C_3391_;
      // 113: new net/optifine/gui/GuiChatOF
      // 116: dup
      // 117: aload 0
      // 118: getfield GameRenderer.k Lnet/minecraft/src/C_3391_;
      // 11b: getfield net/minecraft/src/C_3391_.f_91080_ Lnet/minecraft/src/C_3583_;
      // 11e: checkcast net/minecraft/src/C_3538_
      // 121: invokespecial net/optifine/gui/GuiChatOF.<init> (Lnet/minecraft/src/C_3538_;)V
      // 124: invokevirtual net/minecraft/src/C_3391_.m_91152_ (Lnet/minecraft/src/C_3583_;)V
      // 127: return
   }

   private void frameFinish() {
      if (this.k.r != null && Config.isShowGlErrors() && TimedEvent.isActive("CheckGlErrorFrameFinish", 10000L)) {
         int err = GlStateManager._getError();
         if (err != 0 && GlErrors.isEnabled(err)) {
            String text = Config.getGlErrorString(err);
            C_4996_ msg = C_4996_.m_237113_(C_4513_.m_118938_("of.message.openglError", new Object[]{err, text}));
            this.k.l.d().a(msg);
         }
      }
   }

   public boolean setFxaaShader(int fxaaLevel) {
      if (!GLX.isUsingFBOs()) {
         return false;
      } else if (this.K != null && this.K != this.fxaaShaders[2] && this.K != this.fxaaShaders[4]) {
         return true;
      } else if (fxaaLevel != 2 && fxaaLevel != 4) {
         if (this.K == null) {
            return true;
         } else {
            this.K.close();
            this.K = null;
            return true;
         }
      } else if (this.K != null && this.K == this.fxaaShaders[fxaaLevel]) {
         return true;
      } else if (this.k.r == null) {
         return true;
      } else {
         this.a(new ResourceLocation("shaders/post/fxaa_of_" + fxaaLevel + "x.json"));
         this.fxaaShaders[fxaaLevel] = this.K;
         return this.M;
      }
   }

   public static float getRenderPartialTicks() {
      return C_3391_.m_91087_().m_338668_().m_338527_(false);
   }

   public void a(C_1391_ stack) {
      this.G = stack;
      this.H = 40;
      this.I = this.m.m_188501_() * 2.0F - 1.0F;
      this.J = this.m.m_188501_() * 2.0F - 1.0F;
   }

   private void a(GuiGraphics graphicsIn, float partialTicks) {
      if (this.G != null && this.H > 0) {
         int i = 40 - this.H;
         float f = ((float)i + partialTicks) / 40.0F;
         float f1 = f * f;
         float f2 = f * f1;
         float f3 = 10.25F * f2 * f1 - 24.95F * f1 * f1 + 25.5F * f2 - 13.8F * f1 + 4.0F * f;
         float f4 = f3 * (float) Math.PI;
         float f5 = this.I * (float)(graphicsIn.a() / 4);
         float f6 = this.J * (float)(graphicsIn.b() / 4);
         PoseStack posestack = new PoseStack();
         posestack.a();
         posestack.a((float)(graphicsIn.a() / 2) + f5 * Mth.e(Mth.a(f4 * 2.0F)), (float)(graphicsIn.b() / 2) + f6 * Mth.e(Mth.a(f4 * 2.0F)), -50.0F);
         float f7 = 50.0F + 175.0F * Mth.a(f4);
         posestack.b(f7, -f7, f7);
         posestack.a(C_252363_.f_252436_.m_252977_(900.0F * Mth.e(Mth.a(f4))));
         posestack.a(C_252363_.f_252529_.m_252977_(6.0F * Mth.b(f * 8.0F)));
         posestack.a(C_252363_.f_252403_.m_252977_(6.0F * Mth.b(f * 8.0F)));
         graphicsIn.a(() -> this.k.ar().a(this.G, C_268388_.FIXED, 15728880, C_4474_.f_118083_, posestack, graphicsIn.d(), this.k.r, 0));
         posestack.b();
      }
   }

   private void b(GuiGraphics graphicsIn, float scaleIn) {
      int i = graphicsIn.a();
      int j = graphicsIn.b();
      graphicsIn.c().a();
      float f = Mth.i(scaleIn, 2.0F, 1.0F);
      graphicsIn.c().a((float)i / 2.0F, (float)j / 2.0F, 0.0F);
      graphicsIn.c().b(f, f, f);
      graphicsIn.c().a((float)(-i) / 2.0F, (float)(-j) / 2.0F, 0.0F);
      float f1 = 0.2F * scaleIn;
      float f2 = 0.4F * scaleIn;
      float f3 = 0.2F * scaleIn;
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(
         GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE
      );
      graphicsIn.a(f1, f2, f3, 1.0F);
      graphicsIn.a(GameRenderer.f, 0, 0, -90, 0.0F, 0.0F, i, j, i, j);
      graphicsIn.a(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      graphicsIn.c().b();
   }

   public C_3391_ j() {
      return this.k;
   }

   public float c(float partialTicks) {
      return Mth.i(partialTicks, this.u, this.t);
   }

   public float k() {
      return this.n;
   }

   public Camera l() {
      return this.N;
   }

   public LightTexture m() {
      return this.A;
   }

   public C_4474_ n() {
      return this.B;
   }

   @Nullable
   public static ShaderInstance o() {
      return P;
   }

   @Nullable
   public static ShaderInstance p() {
      return Q;
   }

   @Nullable
   public static ShaderInstance q() {
      return R;
   }

   @Nullable
   public static ShaderInstance r() {
      return S;
   }

   @Nullable
   public static ShaderInstance s() {
      return T;
   }

   @Nullable
   public static ShaderInstance t() {
      return U;
   }

   @Nullable
   public static ShaderInstance u() {
      return V;
   }

   @Nullable
   public static ShaderInstance v() {
      return W;
   }

   @Nullable
   public static ShaderInstance w() {
      return X;
   }

   @Nullable
   public static ShaderInstance x() {
      return Y;
   }

   @Nullable
   public static ShaderInstance y() {
      return Z;
   }

   @Nullable
   public static ShaderInstance z() {
      return aa;
   }

   @Nullable
   public static ShaderInstance A() {
      return ab;
   }

   @Nullable
   public static ShaderInstance B() {
      return ac;
   }

   @Nullable
   public static ShaderInstance C() {
      return ad;
   }

   @Nullable
   public static ShaderInstance D() {
      return ae;
   }

   @Nullable
   public static ShaderInstance E() {
      return af;
   }

   @Nullable
   public static ShaderInstance F() {
      return ag;
   }

   @Nullable
   public static ShaderInstance G() {
      return ah;
   }

   @Nullable
   public static ShaderInstance H() {
      return ai;
   }

   @Nullable
   public static ShaderInstance I() {
      return aj;
   }

   @Nullable
   public static ShaderInstance J() {
      return ak;
   }

   @Nullable
   public static ShaderInstance K() {
      return al;
   }

   @Nullable
   public static ShaderInstance L() {
      return am;
   }

   @Nullable
   public static ShaderInstance M() {
      return an;
   }

   @Nullable
   public static ShaderInstance N() {
      return ao;
   }

   @Nullable
   public static ShaderInstance O() {
      return ap;
   }

   @Nullable
   public static ShaderInstance P() {
      return aq;
   }

   @Nullable
   public static ShaderInstance Q() {
      return ar;
   }

   @Nullable
   public static ShaderInstance R() {
      return as;
   }

   @Nullable
   public static ShaderInstance S() {
      return at;
   }

   @Nullable
   public static ShaderInstance T() {
      return au;
   }

   @Nullable
   public static ShaderInstance U() {
      return av;
   }

   @Nullable
   public static ShaderInstance V() {
      return aw;
   }

   @Nullable
   public static ShaderInstance W() {
      return ax;
   }

   @Nullable
   public static ShaderInstance X() {
      return ay;
   }

   @Nullable
   public static ShaderInstance Y() {
      return az;
   }

   @Nullable
   public static ShaderInstance Z() {
      return aA;
   }

   @Nullable
   public static ShaderInstance aa() {
      return aB;
   }

   @Nullable
   public static ShaderInstance ab() {
      return aC;
   }

   @Nullable
   public static ShaderInstance ac() {
      return aD;
   }

   @Nullable
   public static ShaderInstance ad() {
      return aE;
   }

   @Nullable
   public static ShaderInstance ae() {
      return aF;
   }

   @Nullable
   public static ShaderInstance af() {
      return aG;
   }

   @Nullable
   public static ShaderInstance ag() {
      return aH;
   }

   @Nullable
   public static ShaderInstance ah() {
      return aI;
   }

   @Nullable
   public static ShaderInstance ai() {
      return aJ;
   }

   @Nullable
   public static ShaderInstance aj() {
      return aK;
   }

   @Nullable
   public static ShaderInstance ak() {
      return aL;
   }

   @Nullable
   public static ShaderInstance al() {
      return aM;
   }

   @Nullable
   public static ShaderInstance am() {
      return aN;
   }

   @Nullable
   public static ShaderInstance an() {
      return aO;
   }

   @Nullable
   public static ShaderInstance ao() {
      return aP;
   }

   @Nullable
   public static ShaderInstance ap() {
      return aQ;
   }

   @Nullable
   public static ShaderInstance aq() {
      return aR;
   }

   @Nullable
   public static ShaderInstance ar() {
      return aS;
   }

   @Nullable
   public static ShaderInstance as() {
      return aT;
   }

   public static record a(C_140974_ a, Map<ResourceLocation, C_76_> c) implements C_140974_ {
      public Optional<C_76_> getResource(ResourceLocation locIn) {
         C_76_ resource = (C_76_)this.c.get(locIn);
         return resource != null ? Optional.of(resource) : this.a.getResource(locIn);
      }

      public Map<ResourceLocation, C_76_> b() {
         return this.c;
      }
   }
}
