package net.minecraft.src;

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

public class C_4124_ implements AutoCloseable {
   private static final C_5265_ f_109057_ = C_5265_.m_340282_("textures/misc/nausea.png");
   private static final C_5265_ f_314877_ = C_5265_.m_340282_("shaders/post/blur.json");
   public static final int f_313914_ = 10;
   static final Logger f_109058_ = LogUtils.getLogger();
   private static final boolean f_172636_ = false;
   public static final float f_172592_ = 0.05F;
   private static final float f_289032_ = 1000.0F;
   final C_3391_ f_109059_;
   private final C_77_ f_109060_;
   private final C_212974_ f_109061_ = C_212974_.m_216327_();
   private float f_109062_;
   public final C_4131_ f_109055_;
   private final C_3433_ f_109063_;
   private final C_4148_ f_109064_;
   private int f_303613_;
   private float f_109066_;
   private float f_109067_;
   private float f_109068_;
   private float f_109069_;
   private boolean f_109070_ = true;
   private boolean f_109071_ = true;
   private long f_109072_;
   private boolean f_182638_;
   private long f_109073_ = C_5322_.m_137550_();
   private final C_4138_ f_109074_;
   private final C_4474_ f_109075_ = new C_4474_();
   private boolean f_109076_;
   private float f_109077_ = 1.0F;
   private float f_109078_;
   private float f_109079_;
   public static final int f_172634_ = 40;
   @Nullable
   private C_1391_ f_109080_;
   private int f_109047_;
   private float f_109048_;
   private float f_109049_;
   @Nullable
   C_4145_ f_109050_;
   @Nullable
   private C_4145_ f_316883_;
   private boolean f_109053_;
   private final C_3373_ f_109054_ = new C_3373_();
   @Nullable
   public C_141721_ f_172635_;
   private final Map<String, C_141721_> f_172578_ = Maps.newHashMap();
   @Nullable
   private static C_141721_ f_172579_;
   @Nullable
   private static C_141721_ f_172580_;
   @Nullable
   private static C_141721_ f_172582_;
   @Nullable
   private static C_141721_ f_172583_;
   @Nullable
   private static C_141721_ f_172586_;
   @Nullable
   private static C_141721_ f_172587_;
   @Nullable
   private static C_141721_ f_172588_;
   @Nullable
   private static C_141721_ f_172591_;
   @Nullable
   private static C_141721_ f_172608_;
   @Nullable
   private static C_141721_ f_172609_;
   @Nullable
   private static C_141721_ f_172610_;
   @Nullable
   private static C_141721_ f_172611_;
   @Nullable
   private static C_141721_ f_172613_;
   @Nullable
   private static C_141721_ f_172614_;
   @Nullable
   private static C_141721_ f_172615_;
   @Nullable
   private static C_141721_ f_172616_;
   @Nullable
   private static C_141721_ f_172617_;
   @Nullable
   private static C_141721_ f_172618_;
   @Nullable
   private static C_141721_ f_172619_;
   @Nullable
   private static C_141721_ f_172620_;
   @Nullable
   private static C_141721_ f_234217_;
   @Nullable
   private static C_141721_ f_172621_;
   @Nullable
   private static C_141721_ f_172622_;
   @Nullable
   private static C_141721_ f_172623_;
   @Nullable
   private static C_141721_ f_172624_;
   @Nullable
   private static C_141721_ f_172625_;
   @Nullable
   private static C_141721_ f_172626_;
   @Nullable
   private static C_141721_ f_172627_;
   @Nullable
   private static C_141721_ f_172628_;
   @Nullable
   private static C_141721_ f_303765_;
   @Nullable
   private static C_141721_ f_172629_;
   @Nullable
   private static C_141721_ f_172630_;
   @Nullable
   private static C_141721_ f_172631_;
   @Nullable
   private static C_141721_ f_172632_;
   @Nullable
   private static C_141721_ f_172633_;
   @Nullable
   private static C_141721_ f_172593_;
   @Nullable
   private static C_141721_ f_172594_;
   @Nullable
   private static C_141721_ f_172595_;
   @Nullable
   private static C_141721_ f_172596_;
   @Nullable
   private static C_141721_ f_172597_;
   @Nullable
   private static C_141721_ f_172598_;
   @Nullable
   private static C_141721_ f_268423_;
   @Nullable
   private static C_141721_ f_172599_;
   @Nullable
   private static C_141721_ f_172600_;
   @Nullable
   private static C_141721_ f_268525_;
   @Nullable
   private static C_141721_ f_172601_;
   @Nullable
   private static C_141721_ f_172602_;
   @Nullable
   private static C_141721_ f_172603_;
   @Nullable
   private static C_141721_ f_172604_;
   @Nullable
   private static C_141721_ f_172605_;
   @Nullable
   private static C_141721_ f_314342_;
   @Nullable
   private static C_141721_ f_172606_;
   @Nullable
   private static C_141721_ f_172607_;
   @Nullable
   private static C_141721_ f_285653_;
   @Nullable
   private static C_141721_ f_285598_;
   @Nullable
   private static C_141721_ f_285623_;
   @Nullable
   private static C_141721_ f_285569_;
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

   public C_4124_(C_3391_ mcIn, C_4131_ itemRendererIn, C_77_ resourceManagerIn, C_4148_ renderTypeBuffersIn) {
      this.f_109059_ = mcIn;
      this.f_109060_ = resourceManagerIn;
      this.f_109055_ = itemRendererIn;
      this.f_109063_ = new C_3433_(mcIn.m_91097_(), mcIn.m_319582_());
      this.f_109074_ = new C_4138_(this, mcIn);
      this.f_109064_ = renderTypeBuffersIn;
      this.f_109050_ = null;
   }

   public void close() {
      this.f_109074_.close();
      this.f_109063_.close();
      this.f_109075_.close();
      this.m_109086_();
      this.m_172759_();
      if (this.f_316883_ != null) {
         this.f_316883_.close();
      }

      if (this.f_172635_ != null) {
         this.f_172635_.close();
      }
   }

   public void m_172736_(boolean renderHandIn) {
      this.f_109070_ = renderHandIn;
   }

   public void m_172775_(boolean renderOutlineIn) {
      this.f_109071_ = renderOutlineIn;
   }

   public void m_172779_(boolean debugViewIn) {
      this.f_109076_ = debugViewIn;
   }

   public boolean m_172715_() {
      return this.f_109076_;
   }

   public void m_109086_() {
      if (this.f_109050_ != null) {
         this.f_109050_.close();
      }

      this.f_109050_ = null;
   }

   public void m_109130_() {
      this.f_109053_ = !this.f_109053_;
   }

   public void m_109106_(@Nullable C_507_ entityIn) {
      if (this.f_109050_ != null) {
         this.f_109050_.close();
      }

      this.f_109050_ = null;
      if (entityIn instanceof C_988_) {
         this.m_109128_(C_5265_.m_340282_("shaders/post/creeper.json"));
      } else if (entityIn instanceof C_1071_) {
         this.m_109128_(C_5265_.m_340282_("shaders/post/spider.json"));
      } else if (entityIn instanceof C_998_) {
         this.m_109128_(C_5265_.m_340282_("shaders/post/invert.json"));
      } else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
         Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, entityIn, this);
      }
   }

   private void m_109128_(C_5265_ resourceLocationIn) {
      if (GLX.isUsingFBOs()) {
         if (this.f_109050_ != null) {
            this.f_109050_.close();
         }

         try {
            this.f_109050_ = new C_4145_(this.f_109059_.m_91097_(), this.f_109060_, this.f_109059_.m_91385_(), resourceLocationIn);
            this.f_109050_.m_110025_(this.f_109059_.m_91268_().m_85441_(), this.f_109059_.m_91268_().m_85442_());
            this.f_109053_ = true;
         } catch (IOException var3) {
            f_109058_.warn("Failed to load shader: {}", resourceLocationIn, var3);
            this.f_109053_ = false;
         } catch (JsonSyntaxException var4) {
            f_109058_.warn("Failed to parse shader: {}", resourceLocationIn, var4);
            this.f_109053_ = false;
         }
      }
   }

   private void m_323672_(C_140974_ resourceManagerIn) {
      if (this.f_316883_ != null) {
         this.f_316883_.close();
      }

      try {
         this.f_316883_ = new C_4145_(this.f_109059_.m_91097_(), resourceManagerIn, this.f_109059_.m_91385_(), f_314877_);
         this.f_316883_.m_110025_(this.f_109059_.m_91268_().m_85441_(), this.f_109059_.m_91268_().m_85442_());
      } catch (IOException var3) {
         f_109058_.warn("Failed to load shader: {}", f_314877_, var3);
      } catch (JsonSyntaxException var4) {
         f_109058_.warn("Failed to parse shader: {}", f_314877_, var4);
      }
   }

   public void m_323091_(float partialTicks) {
      if (GLX.isUsingFBOs()) {
         float f = (float)this.f_109059_.f_91066_.m_321110_();
         if (this.f_316883_ != null && f >= 1.0F) {
            this.f_316883_.m_321643_("Radius", f);
            this.f_316883_.m_110023_(partialTicks);
         }
      }
   }

   public C_69_ m_247116_() {
      return new C_81_<C_4124_.C_243496_>() {
         protected C_4124_.C_243496_ m_5944_(C_77_ resourceManagerIn, C_442_ profilerIn) {
            Map<C_5265_, C_76_> map = resourceManagerIn.m_214159_(
               "shaders",
               locIn -> {
                  String s = locIn.m_135815_();
                  return s.endsWith(".json")
                     || s.endsWith(C_3169_.C_3170_.FRAGMENT.m_85569_())
                     || s.endsWith(C_3169_.C_3170_.VERTEX.m_85569_())
                     || s.endsWith(".glsl");
               }
            );
            Map<C_5265_, C_76_> map1 = new HashMap();
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
                  C_4124_.f_109058_.warn("Failed to read resource {}", locIn, var8);
               }
            });
            return new C_4124_.C_243496_(resourceManagerIn, map1);
         }

         protected void m_5787_(C_4124_.C_243496_ objectIn, C_77_ resourceManagerIn, C_442_ profilerIn) {
            C_4124_.this.m_172767_(objectIn);
            if (C_4124_.this.f_109050_ != null) {
               C_4124_.this.f_109050_.close();
            }

            C_4124_.this.f_109050_ = null;
            C_4124_.this.m_109106_(C_4124_.this.f_109059_.m_91288_());
         }

         public String m_7812_() {
            return "Shader Loader";
         }
      };
   }

   public void m_172722_(C_140974_ providerIn) {
      if (this.f_172635_ != null) {
         throw new RuntimeException("Blit shader already preloaded");
      } else {
         try {
            this.f_172635_ = new C_141721_(providerIn, "blit_screen", C_3179_.f_166850_);
         } catch (IOException var3) {
            throw new RuntimeException("could not preload blit shader", var3);
         }

         f_285653_ = this.m_172724_(providerIn, "rendertype_gui", C_3179_.f_85815_);
         f_285598_ = this.m_172724_(providerIn, "rendertype_gui_overlay", C_3179_.f_85815_);
         f_172579_ = this.m_172724_(providerIn, "position", C_3179_.f_85814_);
         f_172580_ = this.m_172724_(providerIn, "position_color", C_3179_.f_85815_);
         f_172582_ = this.m_172724_(providerIn, "position_tex", C_3179_.f_85817_);
         f_172583_ = this.m_172724_(providerIn, "position_tex_color", C_3179_.f_85819_);
         f_172598_ = this.m_172724_(providerIn, "rendertype_text", C_3179_.f_85820_);
      }
   }

   private C_141721_ m_172724_(C_140974_ providerIn, String nameIn, C_3188_ formatIn) {
      try {
         C_141721_ shaderinstance = new C_141721_(providerIn, nameIn, formatIn);
         this.f_172578_.put(nameIn, shaderinstance);
         return shaderinstance;
      } catch (Exception var5) {
         throw new IllegalStateException("could not preload shader " + nameIn, var5);
      }
   }

   void m_172767_(C_140974_ providerIn) {
      RenderSystem.assertOnRenderThread();
      List<C_3169_> list = Lists.newArrayList();
      list.addAll(C_3169_.C_3170_.FRAGMENT.m_85570_().values());
      list.addAll(C_3169_.C_3170_.VERTEX.m_85570_().values());
      list.forEach(C_3169_::m_85543_);
      List<Pair<C_141721_, Consumer<C_141721_>>> list1 = Lists.newArrayListWithCapacity(this.f_172578_.size());

      try {
         list1.add(Pair.of(new C_141721_(providerIn, "particle", C_3179_.f_85813_), (Consumer)p_172713_0_ -> f_172586_ = p_172713_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "position", C_3179_.f_85814_), (Consumer)p_172710_0_ -> f_172579_ = p_172710_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "position_color", C_3179_.f_85815_), (Consumer)p_172707_0_ -> f_172580_ = p_172707_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "position_color_lightmap", C_3179_.f_85816_), (Consumer)p_172704_0_ -> f_172587_ = p_172704_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "position_color_tex_lightmap", C_3179_.f_85820_), (Consumer)p_172698_0_ -> f_172588_ = p_172698_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "position_tex", C_3179_.f_85817_), (Consumer)p_172695_0_ -> f_172582_ = p_172695_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "position_tex_color", C_3179_.f_85819_), (Consumer)p_172692_0_ -> f_172583_ = p_172692_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_solid", C_3179_.f_85811_), (Consumer)p_172683_0_ -> f_172591_ = p_172683_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_cutout_mipped", C_3179_.f_85811_), (Consumer)p_172680_0_ -> f_172608_ = p_172680_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_cutout", C_3179_.f_85811_), (Consumer)p_172677_0_ -> f_172609_ = p_172677_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_translucent", C_3179_.f_85811_), (Consumer)p_172674_0_ -> f_172610_ = p_172674_0_));
         list1.add(
            Pair.of(new C_141721_(providerIn, "rendertype_translucent_moving_block", C_3179_.f_85811_), (Consumer)p_172671_0_ -> f_172611_ = p_172671_0_)
         );
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_armor_cutout_no_cull", C_3179_.f_85812_), (Consumer)p_172665_0_ -> f_172613_ = p_172665_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_solid", C_3179_.f_85812_), (Consumer)p_172662_0_ -> f_172614_ = p_172662_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_cutout", C_3179_.f_85812_), (Consumer)p_172659_0_ -> f_172615_ = p_172659_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_cutout_no_cull", C_3179_.f_85812_), (Consumer)p_172656_0_ -> f_172616_ = p_172656_0_));
         list1.add(
            Pair.of(new C_141721_(providerIn, "rendertype_entity_cutout_no_cull_z_offset", C_3179_.f_85812_), (Consumer)p_172653_0_ -> f_172617_ = p_172653_0_)
         );
         list1.add(
            Pair.of(new C_141721_(providerIn, "rendertype_item_entity_translucent_cull", C_3179_.f_85812_), (Consumer)p_172650_0_ -> f_172618_ = p_172650_0_)
         );
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_translucent_cull", C_3179_.f_85812_), (Consumer)p_172647_0_ -> f_172619_ = p_172647_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_translucent", C_3179_.f_85812_), (Consumer)p_172644_0_ -> f_172620_ = p_172644_0_));
         list1.add(
            Pair.of(new C_141721_(providerIn, "rendertype_entity_translucent_emissive", C_3179_.f_85812_), (Consumer)p_172641_0_ -> f_234217_ = p_172641_0_)
         );
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_smooth_cutout", C_3179_.f_85812_), (Consumer)p_172638_0_ -> f_172621_ = p_172638_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_beacon_beam", C_3179_.f_85811_), (Consumer)p_172839_0_ -> f_172622_ = p_172839_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_decal", C_3179_.f_85812_), (Consumer)p_172836_0_ -> f_172623_ = p_172836_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_no_outline", C_3179_.f_85812_), (Consumer)p_172833_0_ -> f_172624_ = p_172833_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_shadow", C_3179_.f_85812_), (Consumer)p_172830_0_ -> f_172625_ = p_172830_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_alpha", C_3179_.f_85812_), (Consumer)p_172827_0_ -> f_172626_ = p_172827_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_eyes", C_3179_.f_85812_), (Consumer)p_172824_0_ -> f_172627_ = p_172824_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_energy_swirl", C_3179_.f_85812_), (Consumer)p_172821_0_ -> f_172628_ = p_172821_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_leash", C_3179_.f_85816_), (Consumer)p_172818_0_ -> f_172629_ = p_172818_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_water_mask", C_3179_.f_85814_), (Consumer)p_172815_0_ -> f_172630_ = p_172815_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_outline", C_3179_.f_85819_), (Consumer)p_172812_0_ -> f_172631_ = p_172812_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_armor_entity_glint", C_3179_.f_85817_), (Consumer)p_172806_0_ -> f_172633_ = p_172806_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_glint_translucent", C_3179_.f_85817_), (Consumer)p_172804_0_ -> f_172593_ = p_172804_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_glint", C_3179_.f_85817_), (Consumer)p_172802_0_ -> f_172594_ = p_172802_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_glint", C_3179_.f_85817_), (Consumer)p_172798_0_ -> f_172596_ = p_172798_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_entity_glint_direct", C_3179_.f_85817_), (Consumer)p_172795_0_ -> f_172597_ = p_172795_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_text", C_3179_.f_85820_), (Consumer)p_172793_0_ -> f_172598_ = p_172793_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_text_background", C_3179_.f_85816_), (Consumer)p_268793_0_ -> f_268423_ = p_268793_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_text_intensity", C_3179_.f_85820_), (Consumer)p_172791_0_ -> f_172599_ = p_172791_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_text_see_through", C_3179_.f_85820_), (Consumer)p_172788_0_ -> f_172600_ = p_172788_0_));
         list1.add(
            Pair.of(new C_141721_(providerIn, "rendertype_text_background_see_through", C_3179_.f_85816_), (Consumer)p_268792_0_ -> f_268525_ = p_268792_0_)
         );
         list1.add(
            Pair.of(new C_141721_(providerIn, "rendertype_text_intensity_see_through", C_3179_.f_85820_), (Consumer)p_172786_0_ -> f_172601_ = p_172786_0_)
         );
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_lightning", C_3179_.f_85815_), (Consumer)p_172784_0_ -> f_172602_ = p_172784_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_tripwire", C_3179_.f_85811_), (Consumer)p_172781_0_ -> f_172603_ = p_172781_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_end_portal", C_3179_.f_85814_), (Consumer)p_172777_0_ -> f_172604_ = p_172777_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_end_gateway", C_3179_.f_85814_), (Consumer)p_172773_0_ -> f_172605_ = p_172773_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_clouds", C_3179_.f_85822_), (Consumer)p_317418_0_ -> f_314342_ = p_317418_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_lines", C_3179_.f_166851_), (Consumer)p_172732_0_ -> f_172606_ = p_172732_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_crumbling", C_3179_.f_85811_), (Consumer)p_234229_0_ -> f_172607_ = p_234229_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_gui", C_3179_.f_85815_), (Consumer)p_285677_0_ -> f_285653_ = p_285677_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_gui_overlay", C_3179_.f_85815_), (Consumer)p_285675_0_ -> f_285598_ = p_285675_0_));
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_gui_text_highlight", C_3179_.f_85815_), (Consumer)p_285674_0_ -> f_285623_ = p_285674_0_));
         list1.add(
            Pair.of(new C_141721_(providerIn, "rendertype_gui_ghost_recipe_overlay", C_3179_.f_85815_), (Consumer)p_285676_0_ -> f_285569_ = p_285676_0_)
         );
         list1.add(Pair.of(new C_141721_(providerIn, "rendertype_breeze_wind", C_3179_.f_85812_), (Consumer)p_304052_0_ -> f_303765_ = p_304052_0_));
         this.m_323672_(providerIn);
         ReflectorForge.postModLoaderEvent(Reflector.RegisterShadersEvent_Constructor, providerIn, list1);
      } catch (IOException var5) {
         list1.forEach(pairIn -> ((C_141721_)pairIn.getFirst()).close());
         throw new RuntimeException("could not reload shaders", var5);
      }

      this.m_172759_();
      list1.forEach(pairIn -> {
         C_141721_ shaderinstance = (C_141721_)pairIn.getFirst();
         this.f_172578_.put(shaderinstance.m_173365_(), shaderinstance);
         ((Consumer)pairIn.getSecond()).accept(shaderinstance);
      });
   }

   private void m_172759_() {
      RenderSystem.assertOnRenderThread();
      this.f_172578_.values().forEach(C_141721_::close);
      this.f_172578_.clear();
   }

   @Nullable
   public C_141721_ m_172734_(@Nullable String nameIn) {
      return nameIn == null ? null : (C_141721_)this.f_172578_.get(nameIn);
   }

   public void m_109148_() {
      this.m_109156_();
      this.f_109074_.m_109880_();
      if (this.f_109059_.m_91288_() == null) {
         this.f_109059_.m_91118_(this.f_109059_.f_91074_);
      }

      this.f_109054_.m_90565_();
      this.f_109055_.m_109311_();
      this.f_303613_++;
      if (this.f_109059_.f_91073_.m_304826_().m_305915_()) {
         this.f_109059_.f_91060_.m_109693_(this.f_109054_);
         this.f_109069_ = this.f_109068_;
         if (this.f_109059_.f_91065_.m_93090_().m_93714_()) {
            this.f_109068_ += 0.05F;
            if (this.f_109068_ > 1.0F) {
               this.f_109068_ = 1.0F;
            }
         } else if (this.f_109068_ > 0.0F) {
            this.f_109068_ -= 0.0125F;
         }

         if (this.f_109047_ > 0) {
            this.f_109047_--;
            if (this.f_109047_ == 0) {
               this.f_109080_ = null;
            }
         }
      }
   }

   @Nullable
   public C_4145_ m_109149_() {
      return this.f_109050_;
   }

   public void m_109097_(int width, int height) {
      if (this.f_109050_ != null) {
         this.f_109050_.m_110025_(width, height);
      }

      if (this.f_316883_ != null) {
         this.f_316883_.m_110025_(width, height);
      }

      this.f_109059_.f_91060_.m_109487_(width, height);
   }

   public void m_109087_(float partialTicks) {
      C_507_ entity = this.f_109059_.m_91288_();
      if (entity != null && this.f_109059_.f_91073_ != null && this.f_109059_.f_91074_ != null) {
         this.f_109059_.m_91307_().m_6180_("pick");
         double d0 = this.f_109059_.f_91074_.gy();
         double d1 = this.f_109059_.f_91074_.gz();
         C_3043_ hitresult = this.m_321147_(entity, d0, d1, partialTicks);
         this.f_109059_.f_91077_ = hitresult;
         this.f_109059_.f_91076_ = hitresult instanceof C_3042_ entityhitresult ? entityhitresult.m_82443_() : null;
         this.f_109059_.m_91307_().m_7238_();
      }
   }

   private C_3043_ m_321147_(C_507_ entityHitIn, double blockRangeIn, double entityRangeIn, float partialTicks) {
      double d0 = Math.max(blockRangeIn, entityRangeIn);
      double d1 = C_188_.m_144952_(d0);
      C_3046_ vec3 = entityHitIn.m_20299_(partialTicks);
      C_3043_ hitresult = entityHitIn.m_19907_(d0, partialTicks, false);
      double d2 = hitresult.m_82450_().m_82557_(vec3);
      if (hitresult.m_6662_() != C_3044_.MISS) {
         d1 = d2;
         d0 = Math.sqrt(d2);
      }

      C_3046_ vec31 = entityHitIn.m_20252_(partialTicks);
      C_3046_ vec32 = vec3.m_82520_(vec31.f_82479_ * d0, vec31.f_82480_ * d0, vec31.f_82481_ * d0);
      float f = 1.0F;
      C_3040_ aabb = entityHitIn.m_20191_().m_82369_(vec31.m_82490_(d0)).m_82377_(1.0, 1.0, 1.0);
      C_3042_ entityhitresult = C_1168_.m_37287_(entityHitIn, vec3, vec32, aabb, entityIn -> !entityIn.m_5833_() && entityIn.m_6087_(), d1);
      return entityhitresult != null && entityhitresult.e().m_82557_(vec3) < d2
         ? m_324916_(entityhitresult, vec3, entityRangeIn)
         : m_324916_(hitresult, vec3, blockRangeIn);
   }

   private static C_3043_ m_324916_(C_3043_ resultIn, C_3046_ vecIn, double entityRangeIn) {
      C_3046_ vec3 = resultIn.m_82450_();
      if (!vec3.m_82509_(vecIn, entityRangeIn)) {
         C_3046_ vec31 = resultIn.m_82450_();
         C_4687_ direction = C_4687_.m_122366_(vec31.f_82479_ - vecIn.f_82479_, vec31.f_82480_ - vecIn.f_82480_, vec31.f_82481_ - vecIn.f_82481_);
         return C_3041_.m_82426_(vec31, direction, C_4675_.m_274446_(vec31));
      } else {
         return resultIn;
      }
   }

   private void m_109156_() {
      float f = 1.0F;
      if (this.f_109059_.m_91288_() instanceof C_4102_ abstractclientplayer) {
         f = abstractclientplayer.m_108565_();
      }

      this.f_109067_ = this.f_109066_;
      this.f_109066_ = this.f_109066_ + (f - this.f_109066_) * 0.5F;
      if (this.f_109066_ > 1.5F) {
         this.f_109066_ = 1.5F;
      }

      if (this.f_109066_ < 0.1F) {
         this.f_109066_ = 0.1F;
      }
   }

   public double m_109141_(C_3373_ activeRenderInfoIn, float partialTicks, boolean useFOVSetting) {
      if (this.f_109076_) {
         return 90.0;
      } else {
         double d0 = 70.0;
         if (useFOVSetting) {
            d0 = (double)this.f_109059_.f_91066_.m_231837_().m_231551_().intValue();
            boolean playerScoping = this.f_109059_.m_91288_() instanceof C_4102_ && ((C_4102_)this.f_109059_.m_91288_()).m_150108_();
            if (Config.isDynamicFov() || playerScoping) {
               d0 *= (double)C_188_.m_14179_(partialTicks, this.f_109067_, this.f_109066_);
            }
         }

         boolean zoomActive = false;
         if (this.f_109059_.f_91080_ == null) {
            zoomActive = this.f_109059_.f_91066_.ofKeyBindZoom.m_90857_();
         }

         if (zoomActive) {
            if (!Config.zoomMode) {
               Config.zoomMode = true;
               Config.zoomSmoothCamera = this.f_109059_.f_91066_.f_92067_;
               this.f_109059_.f_91066_.f_92067_ = true;
               this.f_109059_.f_91060_.m_109826_();
            }

            if (Config.zoomMode) {
               d0 /= 4.0;
            }
         } else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.f_109059_.f_91066_.f_92067_ = Config.zoomSmoothCamera;
            this.f_109059_.f_91060_.m_109826_();
         }

         if (activeRenderInfoIn.m_90592_() instanceof C_524_ && ((C_524_)activeRenderInfoIn.m_90592_()).m_21224_()) {
            float f = Math.min((float)((C_524_)activeRenderInfoIn.m_90592_()).f_20919_ + partialTicks, 20.0F);
            d0 /= (double)((1.0F - 500.0F / (f + 500.0F)) * 2.0F + 1.0F);
         }

         C_141436_ fogtype = activeRenderInfoIn.m_167685_();
         if (fogtype == C_141436_.LAVA || fogtype == C_141436_.WATER) {
            d0 *= C_188_.m_14139_(this.f_109059_.f_91066_.m_231925_().m_231551_(), 1.0, 0.85714287F);
         }

         return Reflector.ForgeHooksClient_getFieldOfView.exists()
            ? Reflector.callDouble(Reflector.ForgeHooksClient_getFieldOfView, this, activeRenderInfoIn, partialTicks, d0, useFOVSetting)
            : d0;
      }
   }

   private void m_109117_(C_3181_ matrixStackIn, float partialTicks) {
      if (this.f_109059_.m_91288_() instanceof C_524_ livingentity) {
         float f2 = (float)livingentity.f_20916_ - partialTicks;
         if (livingentity.m_21224_()) {
            float f = Math.min((float)livingentity.f_20919_ + partialTicks, 20.0F);
            matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(40.0F - 8000.0F / (f + 200.0F)));
         }

         if (f2 < 0.0F) {
            return;
         }

         f2 /= (float)livingentity.f_20917_;
         f2 = C_188_.m_14031_(f2 * f2 * f2 * f2 * (float) Math.PI);
         float f3 = livingentity.m_264297_();
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(-f3));
         float f1 = (float)((double)(-f2) * 14.0 * this.f_109059_.f_91066_.m_269326_().m_231551_());
         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(f1));
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(f3));
      }
   }

   private void m_109138_(C_3181_ matrixStackIn, float partialTicks) {
      if (this.f_109059_.m_91288_() instanceof C_1141_) {
         C_1141_ player = (C_1141_)this.f_109059_.m_91288_();
         float f = player.Z - player.Y;
         float f1 = -(player.Z + f * partialTicks);
         float f2 = C_188_.m_14179_(partialTicks, player.f_36099_, player.f_36100_);
         matrixStackIn.m_252880_(C_188_.m_14031_(f1 * (float) Math.PI) * f2 * 0.5F, -Math.abs(C_188_.m_14089_(f1 * (float) Math.PI) * f2), 0.0F);
         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(C_188_.m_14031_(f1 * (float) Math.PI) * f2 * 3.0F));
         matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(Math.abs(C_188_.m_14089_(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F));
      }
   }

   public void m_172718_(float zoomIn, float yawIn, float pitchIn) {
      this.f_109077_ = zoomIn;
      this.f_109078_ = yawIn;
      this.f_109079_ = pitchIn;
      this.m_172775_(false);
      this.m_172736_(false);
      this.m_109089_(C_336468_.f_337638_);
      this.f_109077_ = 1.0F;
   }

   private void m_109120_(C_3373_ activeRenderInfoIn, float partialTicks, Matrix4f matrixStackIn) {
      this.renderHand(activeRenderInfoIn, partialTicks, matrixStackIn, true, true, false);
   }

   public void renderHand(
      C_3373_ activeRenderInfoIn, float partialTicks, Matrix4f matrixStackIn, boolean renderItem, boolean renderOverlay, boolean renderTranslucent
   ) {
      if (!this.f_109076_) {
         Shaders.beginRenderFirstPersonHand(renderTranslucent);
         this.m_252879_(this.m_253088_(this.m_109141_(activeRenderInfoIn, partialTicks, false)));
         C_3181_ posestack = new C_3181_();
         boolean flag = false;
         if (renderItem) {
            posestack.m_85836_();
            posestack.m_318714_(matrixStackIn.invert(new Matrix4f()));
            Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
            matrix4fstack.pushMatrix().set(matrixStackIn);
            RenderSystem.applyModelViewMatrix();
            this.m_109117_(posestack, partialTicks);
            if (this.f_109059_.f_91066_.m_231830_().m_231551_()) {
               this.m_109138_(posestack, partialTicks);
            }

            flag = this.f_109059_.m_91288_() instanceof C_524_ && ((C_524_)this.f_109059_.m_91288_()).m_5803_();
            if (this.f_109059_.f_91066_.m_92176_().m_90612_()
               && !flag
               && !this.f_109059_.f_91066_.f_92062_
               && this.f_109059_.f_91072_.m_105295_() != C_1593_.SPECTATOR) {
               this.f_109074_.m_109896_();
               if (Config.isShaders()) {
                  ShadersRender.renderItemFP(
                     this.f_109055_,
                     partialTicks,
                     posestack,
                     this.f_109064_.m_110104_(),
                     this.f_109059_.f_91074_,
                     this.f_109059_.m_91290_().m_114394_(this.f_109059_.f_91074_, partialTicks),
                     renderTranslucent
                  );
               } else {
                  this.f_109055_
                     .m_109314_(
                        partialTicks,
                        posestack,
                        this.f_109064_.m_110104_(),
                        this.f_109059_.f_91074_,
                        this.f_109059_.m_91290_().m_114394_(this.f_109059_.f_91074_, partialTicks)
                     );
               }

               this.f_109074_.m_109891_();
            }

            matrix4fstack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            posestack.m_85849_();
         }

         Shaders.endRenderFirstPersonHand();
         if (!renderOverlay) {
            return;
         }

         this.f_109074_.m_109891_();
         if (this.f_109059_.f_91066_.m_92176_().m_90612_() && !flag) {
            C_4176_.m_110718_(this.f_109059_, posestack);
         }
      }
   }

   public void m_252879_(Matrix4f matrixIn) {
      RenderSystem.setProjectionMatrix(matrixIn, C_276405_.f_276450_);
   }

   public Matrix4f m_253088_(double fovModifierIn) {
      Matrix4f matrix4f = new Matrix4f();
      if (Config.isShaders() && Shaders.isRenderingFirstPersonHand()) {
         Shaders.applyHandDepth(matrix4f);
      }

      this.clipDistance = this.f_109062_ + 1024.0F;
      if (this.f_109077_ != 1.0F) {
         matrix4f.translate(this.f_109078_, -this.f_109079_, 0.0F);
         matrix4f.scale(this.f_109077_, this.f_109077_, 1.0F);
      }

      return matrix4f.perspective(
         (float)(fovModifierIn * (float) (Math.PI / 180.0)),
         (float)this.f_109059_.m_91268_().m_85441_() / (float)this.f_109059_.m_91268_().m_85442_(),
         0.05F,
         this.clipDistance
      );
   }

   public float m_172790_() {
      return this.f_109062_ * 4.0F;
   }

   public static float m_109108_(C_524_ livingEntityIn, float entitylivingbaseIn) {
      C_498_ mobeffectinstance = livingEntityIn.m_21124_(C_500_.f_19611_);
      return !mobeffectinstance.m_267633_(200)
         ? 1.0F
         : 0.7F + C_188_.m_14031_(((float)mobeffectinstance.m_19557_() - entitylivingbaseIn) * (float) Math.PI * 0.2F) * 0.3F;
   }

   public void m_109093_(C_336468_ partialTicks, boolean renderWorldIn) {
      this.frameInit();
      if (!this.f_109059_.m_91302_()
         && this.f_109059_.f_91066_.f_92126_
         && (!this.f_109059_.f_91066_.m_231828_().m_231551_() || !this.f_109059_.f_91067_.m_91584_())) {
         if (C_5322_.m_137550_() - this.f_109073_ > 500L) {
            this.f_109059_.m_91358_(false);
         }
      } else {
         this.f_109073_ = C_5322_.m_137550_();
      }

      if (!this.f_109059_.f_91079_) {
         boolean flag = this.f_109059_.m_293453_();
         int i = (int)(this.f_109059_.f_91067_.m_91589_() * (double)this.f_109059_.m_91268_().m_85445_() / (double)this.f_109059_.m_91268_().m_85443_());
         int j = (int)(this.f_109059_.f_91067_.m_91594_() * (double)this.f_109059_.m_91268_().m_85446_() / (double)this.f_109059_.m_91268_().m_85444_());
         if (flag && renderWorldIn && this.f_109059_.f_91073_ != null && !Config.isReloadingResources()) {
            this.f_109059_.m_91307_().m_6180_("level");
            this.m_109089_(partialTicks);
            this.m_182644_();
            this.f_109059_.f_91060_.m_109769_();
            if (this.f_109050_ != null && this.f_109053_) {
               RenderSystem.disableBlend();
               RenderSystem.disableDepthTest();
               RenderSystem.resetTextureMatrix();
               this.f_109050_.m_110023_(partialTicks.m_339005_());
               RenderSystem.enableTexture();
            }

            this.f_109059_.m_91385_().m_83947_(true);
         } else {
            RenderSystem.viewport(0, 0, this.f_109059_.m_91268_().m_85441_(), this.f_109059_.m_91268_().m_85442_());
         }

         C_3161_ window = this.f_109059_.m_91268_();
         RenderSystem.clear(256, C_3391_.f_91002_);
         float guiFarPlane = Reflector.ForgeHooksClient_getGuiFarPlane.exists() ? Reflector.ForgeHooksClient_getGuiFarPlane.callFloat() : 21000.0F;
         Matrix4f matrix4f = new Matrix4f()
            .setOrtho(
               0.0F, (float)((double)window.m_85441_() / window.m_85449_()), (float)((double)window.m_85442_() / window.m_85449_()), 0.0F, 1000.0F, guiFarPlane
            );
         RenderSystem.setProjectionMatrix(matrix4f, C_276405_.f_276633_);
         Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
         matrix4fstack.pushMatrix();
         float guiOffsetZ = Reflector.ForgeHooksClient_getGuiFarPlane.exists() ? 1000.0F - guiFarPlane : -11000.0F;
         matrix4fstack.translation(0.0F, 0.0F, guiOffsetZ);
         RenderSystem.applyModelViewMatrix();
         C_3144_.m_84931_();
         C_279497_ guigraphics = new C_279497_(this.f_109059_, this.f_109064_.m_110104_());
         if (this.f_109074_.isCustom()) {
            this.f_109074_.setAllowed(false);
         }

         if (flag && renderWorldIn && this.f_109059_.f_91073_ != null) {
            this.f_109059_.m_91307_().m_6182_("gui");
            if (this.f_109059_.f_91074_ != null) {
               float f = C_188_.m_14179_(partialTicks.m_338527_(false), this.f_109059_.f_91074_.f_108590_, this.f_109059_.f_91074_.f_108589_);
               float f1 = this.f_109059_.f_91066_.m_231924_().m_231551_().floatValue();
               if (f > 0.0F && this.f_109059_.f_91074_.b(C_500_.f_19604_) && f1 < 1.0F) {
                  this.m_280083_(guigraphics, f * (1.0F - f1));
               }
            }

            if (!this.f_109059_.f_91066_.f_92062_) {
               this.m_109100_(guigraphics, partialTicks.m_338527_(false));
            }

            this.f_109059_.f_91065_.m_280421_(guigraphics, partialTicks);
            if (this.f_109059_.f_91066_.ofQuickInfo && !this.f_109059_.m_293199_().f_291101_) {
               QuickInfo.render(guigraphics);
            }

            RenderSystem.clear(256, C_3391_.f_91002_);
            this.f_109059_.m_91307_().m_7238_();
         }

         if (this.guiLoadingVisible != (this.f_109059_.m_91265_() != null)) {
            if (this.f_109059_.m_91265_() != null) {
               C_3565_.m_96189_(this.f_109059_);
               if (this.f_109059_.m_91265_() instanceof C_3565_) {
                  C_3565_ rlpg = (C_3565_)this.f_109059_.m_91265_();
                  rlpg.update();
               }
            }

            this.guiLoadingVisible = this.f_109059_.m_91265_() != null;
         }

         if (this.f_109059_.m_91265_() != null) {
            try {
               this.f_109059_.m_91265_().a(guigraphics, i, j, partialTicks.m_338557_());
            } catch (Throwable var16) {
               C_4883_ crashreport = C_4883_.m_127521_(var16, "Rendering overlay");
               C_4909_ crashreportcategory = crashreport.m_127514_("Overlay render details");
               crashreportcategory.m_128165_("Overlay name", () -> this.f_109059_.m_91265_().getClass().getCanonicalName());
               throw new C_5204_(crashreport);
            }
         } else if (flag && this.f_109059_.f_91080_ != null) {
            try {
               if (Config.isCustomEntityModels()) {
                  CustomEntityModels.onRenderScreen(this.f_109059_.f_91080_);
               }

               if (Reflector.ForgeHooksClient_drawScreen.exists()) {
                  Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, this.f_109059_.f_91080_, guigraphics, i, j, partialTicks.m_338557_());
               } else {
                  this.f_109059_.f_91080_.m_280264_(guigraphics, i, j, partialTicks.m_338557_());
               }
            } catch (Throwable var17) {
               C_4883_ crashreport1 = C_4883_.m_127521_(var17, "Rendering screen");
               C_4909_ crashreportcategory1 = crashreport1.m_127514_("Screen render details");
               crashreportcategory1.m_128165_("Screen name", () -> this.f_109059_.f_91080_.getClass().getCanonicalName());
               crashreportcategory1.m_128165_(
                  "Mouse location",
                  () -> String.format(
                        Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", i, j, this.f_109059_.f_91067_.m_91589_(), this.f_109059_.f_91067_.m_91594_()
                     )
               );
               crashreportcategory1.m_128165_(
                  "Screen size",
                  () -> String.format(
                        Locale.ROOT,
                        "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f",
                        this.f_109059_.m_91268_().m_85445_(),
                        this.f_109059_.m_91268_().m_85446_(),
                        this.f_109059_.m_91268_().m_85441_(),
                        this.f_109059_.m_91268_().m_85442_(),
                        this.f_109059_.m_91268_().m_85449_()
                     )
               );
               throw new C_5204_(crashreport1);
            }

            try {
               if (this.f_109059_.f_91080_ != null) {
                  this.f_109059_.f_91080_.m_169417_();
               }
            } catch (Throwable var15) {
               C_4883_ crashreport2 = C_4883_.m_127521_(var15, "Narrating screen");
               C_4909_ crashreportcategory2 = crashreport2.m_127514_("Screen details");
               crashreportcategory2.m_128165_("Screen name", () -> this.f_109059_.f_91080_.getClass().getCanonicalName());
               throw new C_5204_(crashreport2);
            }
         }

         if (flag && renderWorldIn && this.f_109059_.f_91073_ != null) {
            this.f_109059_.f_91065_.m_280266_(guigraphics, partialTicks);
         }

         if (flag) {
            this.f_109059_.m_91307_().m_6180_("toasts");
            this.f_109059_.m_91300_().m_94920_(guigraphics);
            this.f_109059_.m_91307_().m_7238_();
         }

         guigraphics.m_280262_();
         matrix4fstack.popMatrix();
         RenderSystem.applyModelViewMatrix();
         this.f_109074_.setAllowed(true);
      }

      this.frameFinish();
      this.waitForServerThread();
      MemoryMonitor.update();
      Lagometer.updateLagometer();
   }

   private void m_182644_() {
      if (!this.f_182638_ && this.f_109059_.m_91090_()) {
         long i = C_5322_.m_137550_();
         if (i - this.f_109072_ >= 1000L) {
            this.f_109072_ = i;
            C_4585_ integratedserver = this.f_109059_.m_91092_();
            if (integratedserver != null && !integratedserver.m_129918_()) {
               integratedserver.m_182649_().ifPresent(pathIn -> {
                  if (Files.isRegularFile(pathIn, new LinkOption[0])) {
                     this.f_182638_ = true;
                  } else {
                     this.m_182642_(pathIn);
                  }
               });
            }
         }
      }
   }

   private void m_182642_(Path pathIn) {
      if (this.f_109059_.f_91060_.m_294574_() > 10 && this.f_109059_.f_91060_.m_294493_()) {
         C_3148_ nativeimage = C_3408_.m_92279_(this.f_109059_.m_91385_());
         C_5322_.m_183992_().execute(() -> {
            int i = nativeimage.m_84982_();
            int j = nativeimage.m_85084_();
            int k = 0;
            int l = 0;
            if (i > j) {
               k = (i - j) / 2;
               i = j;
            } else {
               l = (j - i) / 2;
               j = i;
            }

            try (C_3148_ nativeimage1 = new C_3148_(64, 64, false)) {
               nativeimage.m_85034_(k, l, i, j, nativeimage1);
               nativeimage1.m_85066_(pathIn);
            } catch (IOException var16) {
               f_109058_.warn("Couldn't save auto screenshot", var16);
            } finally {
               nativeimage.close();
            }
         });
      }
   }

   private boolean m_109158_() {
      if (!this.f_109071_) {
         return false;
      } else {
         C_507_ entity = this.f_109059_.m_91288_();
         boolean flag = entity instanceof C_1141_ && !this.f_109059_.f_91066_.f_92062_;
         if (flag && !((C_1141_)entity).m_150110_().f_35938_) {
            C_1391_ itemstack = ((C_524_)entity).m_21205_();
            C_3043_ hitresult = this.f_109059_.f_91077_;
            if (hitresult != null && hitresult.m_6662_() == C_3044_.BLOCK) {
               C_4675_ blockpos = ((C_3041_)hitresult).m_82425_();
               C_2064_ blockstate = this.f_109059_.f_91073_.m_8055_(blockpos);
               if (this.f_109059_.f_91072_.m_105295_() == C_1593_.SPECTATOR) {
                  flag = blockstate.m_60750_(this.f_109059_.f_91073_, blockpos) != null;
               } else {
                  C_2070_ blockinworld = new C_2070_(this.f_109059_.f_91073_, blockpos, false);
                  C_4705_<C_1706_> registry = this.f_109059_.f_91073_.m_9598_().m_175515_(C_256686_.f_256747_);
                  flag = !itemstack.m_41619_() && (itemstack.m_323082_(blockinworld) || itemstack.m_321400_(blockinworld));
               }
            }
         }

         return flag;
      }
   }

   public void m_109089_(C_336468_ partialTicks) {
      float f = partialTicks.m_338527_(true);
      this.f_109074_.m_109881_(f);
      if (this.f_109059_.m_91288_() == null) {
         this.f_109059_.m_91118_(this.f_109059_.f_91074_);
      }

      this.m_109087_(f);
      if (Config.isShaders()) {
         Shaders.beginRender(this.f_109059_, this.f_109054_, f);
      }

      this.f_109059_.m_91307_().m_6180_("center");
      boolean isShaders = Config.isShaders();
      if (isShaders) {
         Shaders.beginRenderPass(f);
      }

      boolean flag = this.m_109158_();
      this.f_109059_.m_91307_().m_6182_("camera");
      C_3373_ camera = this.f_109054_;
      C_507_ entity = (C_507_)(this.f_109059_.m_91288_() == null ? this.f_109059_.f_91074_ : this.f_109059_.m_91288_());
      float f1 = this.f_109059_.f_91073_.m_304826_().m_305579_(entity) ? 1.0F : f;
      camera.m_90575_(this.f_109059_.f_91073_, entity, !this.f_109059_.f_91066_.m_92176_().m_90612_(), this.f_109059_.f_91066_.m_92176_().m_90613_(), f1);
      this.f_109062_ = (float)(this.f_109059_.f_91066_.m_193772_() * 16);
      double d0 = this.m_109141_(camera, f, true);
      Matrix4f matrix4f = this.m_253088_(d0);
      Matrix4f matrixProjection = matrix4f;
      if (Shaders.isEffectsModelView()) {
         matrix4f = new Matrix4f();
      }

      C_3181_ posestack = new C_3181_();
      this.m_109117_(posestack, camera.m_306445_());
      if (this.f_109059_.f_91066_.m_231830_().m_231551_()) {
         this.m_109138_(posestack, camera.m_306445_());
      }

      matrix4f.mul(posestack.m_85850_().m_252922_());
      float f2 = this.f_109059_.f_91066_.m_231924_().m_231551_().floatValue();
      float f3 = C_188_.m_14179_(f, this.f_109059_.f_91074_.f_108590_, this.f_109059_.f_91074_.f_108589_) * f2 * f2;
      if (f3 > 0.0F) {
         int i = this.f_109059_.f_91074_.b(C_500_.f_19604_) ? 7 : 20;
         float f4 = 5.0F / (f3 * f3 + 5.0F) - f3 * 0.04F;
         f4 *= f4;
         Vector3f vector3f = new Vector3f(0.0F, C_188_.f_13994_ / 2.0F, C_188_.f_13994_ / 2.0F);
         float f5 = ((float)this.f_303613_ + f) * (float)i * (float) (Math.PI / 180.0);
         matrix4f.rotate(f5, vector3f);
         matrix4f.scale(1.0F / f4, 1.0F, 1.0F);
         matrix4f.rotate(-f5, vector3f);
      }

      Matrix4f matrixEffects = matrix4f;
      if (Shaders.isEffectsModelView()) {
         matrix4f = matrixProjection;
      }

      this.m_252879_(matrix4f);
      Quaternionf quaternionf = camera.m_253121_().conjugate(new Quaternionf());
      Matrix4f matrix4f1 = new Matrix4f().rotation(quaternionf);
      if (Shaders.isEffectsModelView()) {
         matrix4f1 = matrixEffects.mul(matrix4f1);
      }

      this.f_109059_
         .f_91060_
         .m_253210_(camera.m_90583_(), matrix4f1, this.m_253088_(Math.max(d0, (double)this.f_109059_.f_91066_.m_231837_().m_231551_().intValue())));
      this.f_109059_.f_91060_.m_109599_(partialTicks, flag, camera, this, this.f_109074_, matrix4f1, matrix4f);
      this.f_109059_.m_91307_().m_6182_("forge_render_last");
      ReflectorForge.dispatchRenderStageS(
         Reflector.RenderLevelStageEvent_Stage_AFTER_LEVEL,
         this.f_109059_.f_91060_,
         matrix4f1,
         matrix4f,
         this.f_109059_.f_91060_.getTicks(),
         camera,
         this.f_109059_.f_91060_.getFrustum()
      );
      this.f_109059_.m_91307_().m_6182_("hand");
      if (this.f_109070_ && !Shaders.isShadowPass) {
         if (isShaders) {
            ShadersRender.renderHand1(this, matrix4f1, camera, f);
            Shaders.renderCompositeFinal();
         }

         RenderSystem.clear(256, C_3391_.f_91002_);
         if (isShaders) {
            ShadersRender.renderFPOverlay(this, matrix4f1, camera, f);
         } else {
            this.m_109120_(camera, f, matrix4f1);
         }
      }

      if (isShaders) {
         Shaders.endRender();
      }

      this.f_109059_.m_91307_().m_7238_();
   }

   public void m_109150_() {
      this.f_109080_ = null;
      this.f_109063_.m_93260_();
      this.f_109054_.m_90598_();
      this.f_182638_ = false;
   }

   public C_3433_ m_109151_() {
      return this.f_109063_;
   }

   private void waitForServerThread() {
      this.serverWaitTimeCurrent = 0;
      if (!Config.isSmoothWorld() || !Config.isSingleProcessor()) {
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
      } else if (this.f_109059_.m_91090_()) {
         C_4585_ srv = this.f_109059_.m_91092_();
         if (srv != null) {
            boolean paused = this.f_109059_.m_91104_();
            if (!paused && !(this.f_109059_.f_91080_ instanceof C_3582_)) {
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
               if (this.f_109059_.f_91080_ instanceof C_3582_) {
                  Config.sleep(20L);
               }

               this.lastServerTime = 0L;
               this.lastServerTicks = 0;
            }
         }
      }
   }

   private void frameInit() {
      Config.frameStart();
      GlErrors.frameStart();
      if (!this.initialized) {
         ReflectorResolver.resolve();
         if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
            Config.setNotify64BitJava(true);
         }

         this.initialized = true;
      }

      C_1596_ world = this.f_109059_.f_91073_;
      if (world != null) {
         if (Config.getNewRelease() != null) {
            String userEdition = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
            String fullNewVer = userEdition + " " + Config.getNewRelease();
            C_5012_ msg = C_4996_.m_237113_(
               C_4513_.m_118938_("of.message.newVersion", new Object[]{"\u00ef\u00bf\u00bdn" + fullNewVer + "\u00ef\u00bf\u00bdr"})
            );
            msg.m_6270_(C_5020_.f_131099_.m_131142_(new C_4993_(C_4994_.OPEN_URL, "https://optifine.net/downloads")));
            this.f_109059_.f_91065_.m_93076_().m_93785_(msg);
            Config.setNewRelease(null);
         }

         if (Config.isNotify64BitJava()) {
            Config.setNotify64BitJava(false);
            C_4996_ msg = C_4996_.m_237113_(C_4513_.m_118938_("of.message.java64Bit", new Object[0]));
            this.f_109059_.f_91065_.m_93076_().m_93785_(msg);
         }
      }

      if (this.updatedWorld != world) {
         RandomEntities.worldChanged(this.updatedWorld, world);
         Config.updateThreadPriorities();
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
         this.updatedWorld = world;
      }

      if (!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
         Shaders.configAntialiasingLevel = 0;
      }

      if (this.f_109059_.f_91080_ != null && this.f_109059_.f_91080_.getClass() == C_3538_.class) {
         this.f_109059_.m_91152_(new GuiChatOF((C_3538_)this.f_109059_.f_91080_));
      }
   }

   private void frameFinish() {
      if (this.f_109059_.f_91073_ != null && Config.isShowGlErrors() && TimedEvent.isActive("CheckGlErrorFrameFinish", 10000L)) {
         int err = GlStateManager._getError();
         if (err != 0 && GlErrors.isEnabled(err)) {
            String text = Config.getGlErrorString(err);
            C_4996_ msg = C_4996_.m_237113_(C_4513_.m_118938_("of.message.openglError", new Object[]{err, text}));
            this.f_109059_.f_91065_.m_93076_().m_93785_(msg);
         }
      }
   }

   public boolean setFxaaShader(int fxaaLevel) {
      if (!GLX.isUsingFBOs()) {
         return false;
      } else if (this.f_109050_ != null && this.f_109050_ != this.fxaaShaders[2] && this.f_109050_ != this.fxaaShaders[4]) {
         return true;
      } else if (fxaaLevel != 2 && fxaaLevel != 4) {
         if (this.f_109050_ == null) {
            return true;
         } else {
            this.f_109050_.close();
            this.f_109050_ = null;
            return true;
         }
      } else if (this.f_109050_ != null && this.f_109050_ == this.fxaaShaders[fxaaLevel]) {
         return true;
      } else if (this.f_109059_.f_91073_ == null) {
         return true;
      } else {
         this.m_109128_(new C_5265_("shaders/post/fxaa_of_" + fxaaLevel + "x.json"));
         this.fxaaShaders[fxaaLevel] = this.f_109050_;
         return this.f_109053_;
      }
   }

   public static float getRenderPartialTicks() {
      return C_3391_.m_91087_().m_338668_().m_338527_(false);
   }

   public void m_109113_(C_1391_ stack) {
      this.f_109080_ = stack;
      this.f_109047_ = 40;
      this.f_109048_ = this.f_109061_.m_188501_() * 2.0F - 1.0F;
      this.f_109049_ = this.f_109061_.m_188501_() * 2.0F - 1.0F;
   }

   private void m_109100_(C_279497_ graphicsIn, float partialTicks) {
      if (this.f_109080_ != null && this.f_109047_ > 0) {
         int i = 40 - this.f_109047_;
         float f = ((float)i + partialTicks) / 40.0F;
         float f1 = f * f;
         float f2 = f * f1;
         float f3 = 10.25F * f2 * f1 - 24.95F * f1 * f1 + 25.5F * f2 - 13.8F * f1 + 4.0F * f;
         float f4 = f3 * (float) Math.PI;
         float f5 = this.f_109048_ * (float)(graphicsIn.m_280182_() / 4);
         float f6 = this.f_109049_ * (float)(graphicsIn.m_280206_() / 4);
         C_3181_ posestack = new C_3181_();
         posestack.m_85836_();
         posestack.m_252880_(
            (float)(graphicsIn.m_280182_() / 2) + f5 * C_188_.m_14154_(C_188_.m_14031_(f4 * 2.0F)),
            (float)(graphicsIn.m_280206_() / 2) + f6 * C_188_.m_14154_(C_188_.m_14031_(f4 * 2.0F)),
            -50.0F
         );
         float f7 = 50.0F + 175.0F * C_188_.m_14031_(f4);
         posestack.m_85841_(f7, -f7, f7);
         posestack.m_252781_(C_252363_.f_252436_.m_252977_(900.0F * C_188_.m_14154_(C_188_.m_14031_(f4))));
         posestack.m_252781_(C_252363_.f_252529_.m_252977_(6.0F * C_188_.m_14089_(f * 8.0F)));
         posestack.m_252781_(C_252363_.f_252403_.m_252977_(6.0F * C_188_.m_14089_(f * 8.0F)));
         graphicsIn.m_286007_(
            () -> this.f_109059_
                  .m_91291_()
                  .m_269128_(this.f_109080_, C_268388_.FIXED, 15728880, C_4474_.f_118083_, posestack, graphicsIn.m_280091_(), this.f_109059_.f_91073_, 0)
         );
         posestack.m_85849_();
      }
   }

   private void m_280083_(C_279497_ graphicsIn, float scaleIn) {
      int i = graphicsIn.m_280182_();
      int j = graphicsIn.m_280206_();
      graphicsIn.m_280168_().m_85836_();
      float f = C_188_.m_14179_(scaleIn, 2.0F, 1.0F);
      graphicsIn.m_280168_().m_252880_((float)i / 2.0F, (float)j / 2.0F, 0.0F);
      graphicsIn.m_280168_().m_85841_(f, f, f);
      graphicsIn.m_280168_().m_252880_((float)(-i) / 2.0F, (float)(-j) / 2.0F, 0.0F);
      float f1 = 0.2F * scaleIn;
      float f2 = 0.4F * scaleIn;
      float f3 = 0.2F * scaleIn;
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(
         GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE
      );
      graphicsIn.m_280246_(f1, f2, f3, 1.0F);
      graphicsIn.m_280398_(f_109057_, 0, 0, -90, 0.0F, 0.0F, i, j, i, j);
      graphicsIn.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      graphicsIn.m_280168_().m_85849_();
   }

   public C_3391_ m_172797_() {
      return this.f_109059_;
   }

   public float m_109131_(float partialTicks) {
      return C_188_.m_14179_(partialTicks, this.f_109069_, this.f_109068_);
   }

   public float m_109152_() {
      return this.f_109062_;
   }

   public C_3373_ m_109153_() {
      return this.f_109054_;
   }

   public C_4138_ m_109154_() {
      return this.f_109074_;
   }

   public C_4474_ m_109155_() {
      return this.f_109075_;
   }

   @Nullable
   public static C_141721_ m_172808_() {
      return f_172579_;
   }

   @Nullable
   public static C_141721_ m_172811_() {
      return f_172580_;
   }

   @Nullable
   public static C_141721_ m_172817_() {
      return f_172582_;
   }

   @Nullable
   public static C_141721_ m_172820_() {
      return f_172583_;
   }

   @Nullable
   public static C_141721_ m_172829_() {
      return f_172586_;
   }

   @Nullable
   public static C_141721_ m_172832_() {
      return f_172587_;
   }

   @Nullable
   public static C_141721_ m_172835_() {
      return f_172588_;
   }

   @Nullable
   public static C_141721_ m_172640_() {
      return f_172591_;
   }

   @Nullable
   public static C_141721_ m_172643_() {
      return f_172608_;
   }

   @Nullable
   public static C_141721_ m_172646_() {
      return f_172609_;
   }

   @Nullable
   public static C_141721_ m_172649_() {
      return f_172610_;
   }

   @Nullable
   public static C_141721_ m_172652_() {
      return f_172611_;
   }

   @Nullable
   public static C_141721_ m_172658_() {
      return f_172613_;
   }

   @Nullable
   public static C_141721_ m_172661_() {
      return f_172614_;
   }

   @Nullable
   public static C_141721_ m_172664_() {
      return f_172615_;
   }

   @Nullable
   public static C_141721_ m_172667_() {
      return f_172616_;
   }

   @Nullable
   public static C_141721_ m_172670_() {
      return f_172617_;
   }

   @Nullable
   public static C_141721_ m_172673_() {
      return f_172618_;
   }

   @Nullable
   public static C_141721_ m_172676_() {
      return f_172619_;
   }

   @Nullable
   public static C_141721_ m_172679_() {
      return f_172620_;
   }

   @Nullable
   public static C_141721_ m_234223_() {
      return f_234217_;
   }

   @Nullable
   public static C_141721_ m_172682_() {
      return f_172621_;
   }

   @Nullable
   public static C_141721_ m_172685_() {
      return f_172622_;
   }

   @Nullable
   public static C_141721_ m_172688_() {
      return f_172623_;
   }

   @Nullable
   public static C_141721_ m_172691_() {
      return f_172624_;
   }

   @Nullable
   public static C_141721_ m_172694_() {
      return f_172625_;
   }

   @Nullable
   public static C_141721_ m_172697_() {
      return f_172626_;
   }

   @Nullable
   public static C_141721_ m_172700_() {
      return f_172627_;
   }

   @Nullable
   public static C_141721_ m_172703_() {
      return f_172628_;
   }

   @Nullable
   public static C_141721_ m_307576_() {
      return f_303765_;
   }

   @Nullable
   public static C_141721_ m_172706_() {
      return f_172629_;
   }

   @Nullable
   public static C_141721_ m_172709_() {
      return f_172630_;
   }

   @Nullable
   public static C_141721_ m_172712_() {
      return f_172631_;
   }

   @Nullable
   public static C_141721_ m_172738_() {
      return f_172632_;
   }

   @Nullable
   public static C_141721_ m_172741_() {
      return f_172633_;
   }

   @Nullable
   public static C_141721_ m_172744_() {
      return f_172593_;
   }

   @Nullable
   public static C_141721_ m_172745_() {
      return f_172594_;
   }

   @Nullable
   public static C_141721_ m_172746_() {
      return f_172595_;
   }

   @Nullable
   public static C_141721_ m_172747_() {
      return f_172596_;
   }

   @Nullable
   public static C_141721_ m_172748_() {
      return f_172597_;
   }

   @Nullable
   public static C_141721_ m_172749_() {
      return f_172598_;
   }

   @Nullable
   public static C_141721_ m_269563_() {
      return f_268423_;
   }

   @Nullable
   public static C_141721_ m_172750_() {
      return f_172599_;
   }

   @Nullable
   public static C_141721_ m_172751_() {
      return f_172600_;
   }

   @Nullable
   public static C_141721_ m_269511_() {
      return f_268525_;
   }

   @Nullable
   public static C_141721_ m_172752_() {
      return f_172601_;
   }

   @Nullable
   public static C_141721_ m_172753_() {
      return f_172602_;
   }

   @Nullable
   public static C_141721_ m_172754_() {
      return f_172603_;
   }

   @Nullable
   public static C_141721_ m_172755_() {
      return f_172604_;
   }

   @Nullable
   public static C_141721_ m_172756_() {
      return f_172605_;
   }

   @Nullable
   public static C_141721_ m_320329_() {
      return f_314342_;
   }

   @Nullable
   public static C_141721_ m_172757_() {
      return f_172606_;
   }

   @Nullable
   public static C_141721_ m_172758_() {
      return f_172607_;
   }

   @Nullable
   public static C_141721_ m_285858_() {
      return f_285653_;
   }

   @Nullable
   public static C_141721_ m_285975_() {
      return f_285598_;
   }

   @Nullable
   public static C_141721_ m_285738_() {
      return f_285623_;
   }

   @Nullable
   public static C_141721_ m_285862_() {
      return f_285569_;
   }

   public static record C_243496_(C_140974_ f_244315_, Map<C_5265_, C_76_> f_243825_) implements C_140974_ {
      public Optional<C_76_> m_213713_(C_5265_ locIn) {
         C_76_ resource = (C_76_)this.f_243825_.get(locIn);
         return resource != null ? Optional.of(resource) : this.f_244315_.m_213713_(locIn);
      }
   }
}
