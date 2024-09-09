package net.minecraft.src;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_1629_.C_1635_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_2190_.C_2191_;
import net.minecraft.src.C_301982_.C_313594_;
import net.minecraft.src.C_3043_.C_3044_;
import net.minecraft.src.C_313346_.C_313550_;
import net.minecraft.src.C_4112_.C_4116_;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.EmissiveTextures;
import net.optifine.Lagometer;
import net.optifine.SmartAnimations;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.ChunkVisibility;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderStateManager;
import net.optifine.render.RenderUtils;
import net.optifine.render.VboRegion;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.BiomeUtils;
import net.optifine.util.GpuMemory;
import net.optifine.util.MathUtils;
import net.optifine.util.PairInt;
import net.optifine.util.RandomUtils;
import net.optifine.util.RenderChunkUtils;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;

public class C_4134_ implements C_79_, AutoCloseable {
   private static final Logger f_109453_ = LogUtils.getLogger();
   public static final int f_291639_ = 16;
   public static final int f_291129_ = 8;
   private static final float f_172941_ = 512.0F;
   private static final int f_172942_ = 32;
   private static final int f_172943_ = 10;
   private static final int f_172944_ = 21;
   private static final int f_172945_ = 15;
   private static final C_5265_ f_109454_ = C_5265_.m_340282_("textures/environment/moon_phases.png");
   private static final C_5265_ f_109455_ = C_5265_.m_340282_("textures/environment/sun.png");
   protected static final C_5265_ f_109456_ = C_5265_.m_340282_("textures/environment/clouds.png");
   private static final C_5265_ f_109457_ = C_5265_.m_340282_("textures/environment/end_sky.png");
   private static final C_5265_ f_109458_ = C_5265_.m_340282_("textures/misc/forcefield.png");
   private static final C_5265_ f_109459_ = C_5265_.m_340282_("textures/environment/rain.png");
   private static final C_5265_ f_109460_ = C_5265_.m_340282_("textures/environment/snow.png");
   public static final C_4687_[] f_109434_ = C_4687_.values();
   private final C_3391_ f_109461_;
   private final C_4330_ f_109463_;
   private final C_4243_ f_172946_;
   private final C_4148_ f_109464_;
   @Nullable
   protected C_3899_ f_109465_;
   private final C_290263_ f_291822_ = new C_290263_();
   private ObjectArrayList f_290776_ = new ObjectArrayList(10000);
   private final Set f_109468_ = Sets.newHashSet();
   @Nullable
   private C_4180_ f_109469_;
   @Nullable
   private C_3186_ f_109471_;
   @Nullable
   private C_3186_ f_109472_;
   @Nullable
   private C_3186_ f_109473_;
   private boolean f_109474_ = true;
   @Nullable
   private C_3186_ f_109475_;
   private final C_4175_ f_109476_ = new C_4175_(100);
   private int f_109477_;
   private final Int2ObjectMap f_109408_ = new Int2ObjectOpenHashMap();
   private final Long2ObjectMap f_109409_ = new Long2ObjectOpenHashMap();
   private final Map f_336897_ = Maps.newHashMap();
   @Nullable
   private C_3106_ f_109411_;
   @Nullable
   private C_4145_ f_109412_;
   @Nullable
   private C_3106_ f_109413_;
   @Nullable
   private C_3106_ f_109414_;
   @Nullable
   private C_3106_ f_109415_;
   @Nullable
   private C_3106_ f_109416_;
   @Nullable
   private C_3106_ f_109417_;
   @Nullable
   private C_4145_ f_109418_;
   private int f_291437_ = Integer.MIN_VALUE;
   private int f_291126_ = Integer.MIN_VALUE;
   private int f_290828_ = Integer.MIN_VALUE;
   private double f_109425_ = Double.MIN_VALUE;
   private double f_109426_ = Double.MIN_VALUE;
   private double f_109427_ = Double.MIN_VALUE;
   private double f_109428_ = Double.MIN_VALUE;
   private double f_109429_ = Double.MIN_VALUE;
   private int f_109430_ = Integer.MIN_VALUE;
   private int f_109431_ = Integer.MIN_VALUE;
   private int f_109432_ = Integer.MIN_VALUE;
   private C_3046_ f_109433_;
   @Nullable
   private C_3376_ f_109435_;
   @Nullable
   private C_290152_ f_290446_;
   private int f_109438_;
   private int f_109439_;
   private int f_109440_;
   private C_4273_ f_172938_;
   private boolean f_109441_;
   @Nullable
   private C_4273_ f_109442_;
   private final Vector4f[] f_109443_;
   private final Vector3d f_109444_;
   private double f_109445_;
   private double f_109446_;
   private double f_109447_;
   private int f_109450_;
   private final float[] f_109451_;
   private final float[] f_109452_;
   private Set chunksToResortTransparency;
   private int countChunksToUpdate;
   private ObjectArrayList renderInfosTerrain;
   private LongOpenHashSet renderInfosEntities;
   private List renderInfosTileEntities;
   private ObjectArrayList renderInfosTerrainNormal;
   private LongOpenHashSet renderInfosEntitiesNormal;
   private List renderInfosTileEntitiesNormal;
   private ObjectArrayList renderInfosTerrainShadow;
   private LongOpenHashSet renderInfosEntitiesShadow;
   private List renderInfosTileEntitiesShadow;
   protected int renderDistance;
   protected int renderDistanceSq;
   protected int renderDistanceXZSq;
   private int countTileEntitiesRendered;
   private RenderEnv renderEnv;
   public boolean renderOverlayDamaged;
   public boolean renderOverlayEyes;
   private boolean firstWorldLoad;
   private static int renderEntitiesCounter = 0;
   public int loadVisibleChunksCounter;
   public static C_213508_ loadVisibleChunksMessageId = new C_213508_(RandomUtils.getRandomBytes(256));
   private static boolean ambientOcclusion = false;
   private Map mapEntityLists;
   private Map mapRegionLayers;
   private int frameId;
   private boolean debugFixTerrainFrustumShadow;

   public C_4134_(C_3391_ mcIn, C_4330_ renderManagerIn, C_4243_ blockEntityDispatcherIn, C_4148_ renderTypeTexturesIn) {
      this.f_109433_ = C_3046_.f_82478_;
      this.f_109438_ = -1;
      this.f_109443_ = new Vector4f[8];
      this.f_109444_ = new Vector3d(0.0, 0.0, 0.0);
      this.f_109451_ = new float[1024];
      this.f_109452_ = new float[1024];
      this.chunksToResortTransparency = new LinkedHashSet();
      this.countChunksToUpdate = 0;
      this.renderInfosTerrain = new ObjectArrayList(1024);
      this.renderInfosEntities = new LongOpenHashSet(1024);
      this.renderInfosTileEntities = new ArrayList(1024);
      this.renderInfosTerrainNormal = new ObjectArrayList(1024);
      this.renderInfosEntitiesNormal = new LongOpenHashSet(1024);
      this.renderInfosTileEntitiesNormal = new ArrayList(1024);
      this.renderInfosTerrainShadow = new ObjectArrayList(1024);
      this.renderInfosEntitiesShadow = new LongOpenHashSet(1024);
      this.renderInfosTileEntitiesShadow = new ArrayList(1024);
      this.renderDistance = 0;
      this.renderDistanceSq = 0;
      this.renderDistanceXZSq = 0;
      this.renderEnv = new RenderEnv(C_1710_.f_50016_.m_49966_(), new C_4675_(0, 0, 0));
      this.renderOverlayDamaged = false;
      this.renderOverlayEyes = false;
      this.firstWorldLoad = false;
      this.loadVisibleChunksCounter = -1;
      this.mapEntityLists = new HashMap();
      this.mapRegionLayers = new LinkedHashMap();
      this.f_109461_ = mcIn;
      this.f_109463_ = renderManagerIn;
      this.f_172946_ = blockEntityDispatcherIn;
      this.f_109464_ = renderTypeTexturesIn;

      for(int i = 0; i < 32; ++i) {
         for(int j = 0; j < 32; ++j) {
            float f = (float)(j - 16);
            float f1 = (float)(i - 16);
            float f2 = C_188_.m_14116_(f * f + f1 * f1);
            this.f_109451_[i << 5 | j] = -f1 / f2;
            this.f_109452_[i << 5 | j] = f / f2;
         }
      }

      this.m_109837_();
      this.m_109836_();
      this.m_109835_();
   }

   private void m_109703_(C_4138_ lightmapIn, float partialTicks, double xIn, double yIn, double zIn) {
      if (!Reflector.IForgeDimensionSpecialEffects_renderSnowAndRain.exists() || !Reflector.callBoolean(this.f_109465_.m_104583_(), Reflector.IForgeDimensionSpecialEffects_renderSnowAndRain, this.f_109465_, this.f_109477_, partialTicks, lightmapIn, xIn, yIn, zIn)) {
         float f = this.f_109461_.f_91073_.m_46722_(partialTicks);
         if (!(f <= 0.0F)) {
            if (Config.isRainOff()) {
               return;
            }

            lightmapIn.m_109896_();
            C_1596_ level = this.f_109461_.f_91073_;
            int i = C_188_.m_14107_(xIn);
            int j = C_188_.m_14107_(yIn);
            int k = C_188_.m_14107_(zIn);
            C_3185_ tesselator = C_3185_.m_85913_();
            C_3173_ bufferbuilder = null;
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (Config.isRainFancy()) {
               l = 10;
            }

            RenderSystem.depthMask(C_3391_.m_91085_());
            if (Config.isShaders()) {
               GlStateManager._depthMask(Shaders.isRainDepth());
            }

            int i1 = -1;
            float f1 = (float)this.f_109477_ + partialTicks;
            RenderSystem.setShader(C_4124_::m_172829_);
            C_4675_.C_4681_ blockpos$mutableblockpos = new C_4675_.C_4681_();

            for(int j1 = k - l; j1 <= k + l; ++j1) {
               for(int k1 = i - l; k1 <= i + l; ++k1) {
                  int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
                  double d0 = (double)this.f_109451_[l1] * 0.5;
                  double d1 = (double)this.f_109452_[l1] * 0.5;
                  blockpos$mutableblockpos.m_122169_((double)k1, yIn, (double)j1);
                  C_1629_ biome = (C_1629_)level.t(blockpos$mutableblockpos).m_203334_();
                  if (biome.m_264473_()) {
                     int i2 = level.m_6924_(C_2191_.MOTION_BLOCKING, k1, j1);
                     int j2 = j - l;
                     int k2 = j + l;
                     if (j2 < i2) {
                        j2 = i2;
                     }

                     if (k2 < i2) {
                        k2 = i2;
                     }

                     int l2 = i2;
                     if (i2 < j) {
                        l2 = j;
                     }

                     if (j2 != k2) {
                        C_212974_ randomsource = C_212974_.m_216335_((long)(k1 * k1 * 3121 + k1 * 45238971 ^ j1 * j1 * 418711 + j1 * 13761));
                        blockpos$mutableblockpos.m_122178_(k1, j2, j1);
                        C_1629_.C_1635_ biome$precipitation = biome.m_264600_(blockpos$mutableblockpos);
                        float f10;
                        double d5;
                        int l4;
                        if (biome$precipitation == C_1635_.RAIN) {
                           if (i1 != 0) {
                              if (i1 >= 0) {
                                 C_3177_.m_231202_(bufferbuilder.m_339905_());
                              }

                              i1 = 0;
                              RenderSystem.setShaderTexture(0, f_109459_);
                              bufferbuilder = tesselator.m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85813_);
                           }

                           int i3 = this.f_109477_ & 131071;
                           int j3 = k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 255;
                           f10 = 3.0F + randomsource.m_188501_();
                           float f3 = -((float)(i3 + j3) + partialTicks) / 32.0F * f10;
                           float f4 = f3 % 32.0F;
                           d5 = (double)k1 + 0.5 - xIn;
                           double d3 = (double)j1 + 0.5 - zIn;
                           float f6 = (float)Math.sqrt(d5 * d5 + d3 * d3) / (float)l;
                           float f7 = ((1.0F - f6 * f6) * 0.5F + 0.5F) * f;
                           blockpos$mutableblockpos.m_122178_(k1, l2, j1);
                           l4 = m_109541_(level, blockpos$mutableblockpos);
                           bufferbuilder.m_167146_((float)((double)k1 - xIn - d0 + 0.5), (float)((double)k2 - yIn), (float)((double)j1 - zIn - d1 + 0.5)).m_167083_(0.0F, (float)j2 * 0.25F + f4).m_340057_(1.0F, 1.0F, 1.0F, f7).m_338973_(l4);
                           bufferbuilder.m_167146_((float)((double)k1 - xIn + d0 + 0.5), (float)((double)k2 - yIn), (float)((double)j1 - zIn + d1 + 0.5)).m_167083_(1.0F, (float)j2 * 0.25F + f4).m_340057_(1.0F, 1.0F, 1.0F, f7).m_338973_(l4);
                           bufferbuilder.m_167146_((float)((double)k1 - xIn + d0 + 0.5), (float)((double)j2 - yIn), (float)((double)j1 - zIn + d1 + 0.5)).m_167083_(1.0F, (float)k2 * 0.25F + f4).m_340057_(1.0F, 1.0F, 1.0F, f7).m_338973_(l4);
                           bufferbuilder.m_167146_((float)((double)k1 - xIn - d0 + 0.5), (float)((double)j2 - yIn), (float)((double)j1 - zIn - d1 + 0.5)).m_167083_(0.0F, (float)k2 * 0.25F + f4).m_340057_(1.0F, 1.0F, 1.0F, f7).m_338973_(l4);
                        } else if (biome$precipitation == C_1635_.SNOW) {
                           if (i1 != 1) {
                              if (i1 >= 0) {
                                 C_3177_.m_231202_(bufferbuilder.m_339905_());
                              }

                              i1 = 1;
                              RenderSystem.setShaderTexture(0, f_109460_);
                              bufferbuilder = tesselator.m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85813_);
                           }

                           float f8 = -((float)(this.f_109477_ & 511) + partialTicks) / 512.0F;
                           float f9 = (float)(randomsource.m_188500_() + (double)f1 * 0.01 * (double)((float)randomsource.m_188583_()));
                           f10 = (float)(randomsource.m_188500_() + (double)(f1 * (float)randomsource.m_188583_()) * 0.001);
                           double d4 = (double)k1 + 0.5 - xIn;
                           d5 = (double)j1 + 0.5 - zIn;
                           float f11 = (float)Math.sqrt(d4 * d4 + d5 * d5) / (float)l;
                           float f5 = ((1.0F - f11 * f11) * 0.3F + 0.5F) * f;
                           blockpos$mutableblockpos.m_122178_(k1, l2, j1);
                           int j4 = m_109541_(level, blockpos$mutableblockpos);
                           int k4 = j4 >> 16 & '\uffff';
                           l4 = j4 & '\uffff';
                           int l3 = (k4 * 3 + 240) / 4;
                           int i4 = (l4 * 3 + 240) / 4;
                           bufferbuilder.m_167146_((float)((double)k1 - xIn - d0 + 0.5), (float)((double)k2 - yIn), (float)((double)j1 - zIn - d1 + 0.5)).m_167083_(0.0F + f9, (float)j2 * 0.25F + f8 + f10).m_340057_(1.0F, 1.0F, 1.0F, f5).m_338813_(i4, l3);
                           bufferbuilder.m_167146_((float)((double)k1 - xIn + d0 + 0.5), (float)((double)k2 - yIn), (float)((double)j1 - zIn + d1 + 0.5)).m_167083_(1.0F + f9, (float)j2 * 0.25F + f8 + f10).m_340057_(1.0F, 1.0F, 1.0F, f5).m_338813_(i4, l3);
                           bufferbuilder.m_167146_((float)((double)k1 - xIn + d0 + 0.5), (float)((double)j2 - yIn), (float)((double)j1 - zIn + d1 + 0.5)).m_167083_(1.0F + f9, (float)k2 * 0.25F + f8 + f10).m_340057_(1.0F, 1.0F, 1.0F, f5).m_338813_(i4, l3);
                           bufferbuilder.m_167146_((float)((double)k1 - xIn - d0 + 0.5), (float)((double)j2 - yIn), (float)((double)j1 - zIn - d1 + 0.5)).m_167083_(0.0F + f9, (float)k2 * 0.25F + f8 + f10).m_340057_(1.0F, 1.0F, 1.0F, f5).m_338813_(i4, l3);
                        }
                     }
                  }
               }
            }

            if (i1 >= 0) {
               C_3177_.m_231202_(bufferbuilder.m_339905_());
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            lightmapIn.m_109891_();
         }

      }
   }

   public void m_109693_(C_3373_ activeRenderInfoIn) {
      if (!Reflector.IForgeDimensionSpecialEffects_tickRain.exists() || !Reflector.callBoolean(this.f_109465_.m_104583_(), Reflector.IForgeDimensionSpecialEffects_tickRain, this.f_109465_, this.f_109477_, activeRenderInfoIn)) {
         float f = this.f_109461_.f_91073_.m_46722_(1.0F) / (C_3391_.m_91405_() ? 1.0F : 2.0F);
         if (!Config.isRainFancy()) {
            f /= 2.0F;
         }

         if (!(f <= 0.0F) && Config.isRainSplash()) {
            C_212974_ randomsource = C_212974_.m_216335_((long)this.f_109477_ * 312987231L);
            C_1599_ levelreader = this.f_109461_.f_91073_;
            C_4675_ blockpos = C_4675_.m_274446_(activeRenderInfoIn.m_90583_());
            C_4675_ blockpos1 = null;
            int i = (int)(100.0F * f * f) / (this.f_109461_.f_91066_.m_231929_().m_231551_() == C_3404_.DECREASED ? 2 : 1);

            for(int j = 0; j < i; ++j) {
               int k = randomsource.m_188503_(21) - 10;
               int l = randomsource.m_188503_(21) - 10;
               C_4675_ blockpos2 = levelreader.m_5452_(C_2191_.MOTION_BLOCKING, blockpos.m_7918_(k, 0, l));
               if (blockpos2.v() > levelreader.m_141937_() && blockpos2.v() <= blockpos.v() + 10 && blockpos2.v() >= blockpos.v() - 10) {
                  C_1629_ biome = (C_1629_)levelreader.m_204166_(blockpos2).m_203334_();
                  if (biome.m_264600_(blockpos2) == C_1635_.RAIN) {
                     blockpos1 = blockpos2.m_7495_();
                     if (this.f_109461_.f_91066_.m_231929_().m_231551_() == C_3404_.MINIMAL) {
                        break;
                     }

                     double d0 = randomsource.m_188500_();
                     double d1 = randomsource.m_188500_();
                     C_2064_ blockstate = levelreader.a_(blockpos1);
                     C_2691_ fluidstate = levelreader.b_(blockpos1);
                     C_3072_ voxelshape = blockstate.m_60812_(levelreader, blockpos1);
                     double d2 = voxelshape.m_83290_(C_4687_.C_4689_.field_7, d0, d1);
                     double d3 = (double)fluidstate.m_76155_(levelreader, blockpos1);
                     double d4 = Math.max(d2, d3);
                     C_4756_ particleoptions = !fluidstate.m_205070_(C_139_.f_13132_) && !blockstate.m_60713_(C_1710_.f_50450_) && !C_1720_.m_51319_(blockstate) ? C_4759_.f_123761_ : C_4759_.f_123762_;
                     this.f_109461_.f_91073_.m_7106_(particleoptions, (double)blockpos1.u() + d0, (double)blockpos1.v() + d4, (double)blockpos1.w() + d1, 0.0, 0.0, 0.0);
                  }
               }
            }

            if (blockpos1 != null && randomsource.m_188503_(3) < this.f_109450_++) {
               this.f_109450_ = 0;
               if (blockpos1.v() > blockpos.v() + 1 && levelreader.m_5452_(C_2191_.MOTION_BLOCKING, blockpos).v() > C_188_.m_14143_((float)blockpos.v())) {
                  this.f_109461_.f_91073_.m_245747_(blockpos1, C_124_.f_12542_, C_125_.WEATHER, 0.1F, 0.5F, false);
               } else {
                  this.f_109461_.f_91073_.m_245747_(blockpos1, C_124_.f_12541_, C_125_.WEATHER, 0.2F, 1.0F, false);
               }
            }
         }

      }
   }

   public void close() {
      if (this.f_109412_ != null) {
         this.f_109412_.close();
      }

      if (this.f_109418_ != null) {
         this.f_109418_.close();
      }

   }

   public void m_6213_(C_77_ resourceManager) {
      this.m_109482_();
      if (C_3391_.m_91085_()) {
         this.m_109833_();
      }

   }

   public void m_109482_() {
      if (this.f_109412_ != null) {
         this.f_109412_.close();
      }

      C_5265_ resourcelocation = C_5265_.m_340282_("shaders/post/entity_outline.json");

      try {
         this.f_109412_ = new C_4145_(this.f_109461_.m_91097_(), this.f_109461_.m_91098_(), this.f_109461_.m_91385_(), resourcelocation);
         this.f_109412_.m_110025_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
         this.f_109411_ = this.f_109412_.m_110036_("final");
      } catch (IOException var3) {
         f_109453_.warn("Failed to load shader: {}", resourcelocation, var3);
         this.f_109412_ = null;
         this.f_109411_ = null;
      } catch (JsonSyntaxException var4) {
         f_109453_.warn("Failed to parse shader: {}", resourcelocation, var4);
         this.f_109412_ = null;
         this.f_109411_ = null;
      }

   }

   private void m_109833_() {
      this.m_109834_();
      C_5265_ resourcelocation = C_5265_.m_340282_("shaders/post/transparency.json");

      try {
         C_4145_ postchain = new C_4145_(this.f_109461_.m_91097_(), this.f_109461_.m_91098_(), this.f_109461_.m_91385_(), resourcelocation);
         postchain.m_110025_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
         C_3106_ rendertarget1 = postchain.m_110036_("translucent");
         C_3106_ rendertarget2 = postchain.m_110036_("itemEntity");
         C_3106_ rendertarget3 = postchain.m_110036_("particles");
         C_3106_ rendertarget4 = postchain.m_110036_("weather");
         C_3106_ rendertarget = postchain.m_110036_("clouds");
         this.f_109418_ = postchain;
         this.f_109413_ = rendertarget1;
         this.f_109414_ = rendertarget2;
         this.f_109415_ = rendertarget3;
         this.f_109416_ = rendertarget4;
         this.f_109417_ = rendertarget;
      } catch (Exception var8) {
         String s = var8 instanceof JsonSyntaxException ? "parse" : "load";
         String s1 = "Failed to " + s + " shader: " + String.valueOf(resourcelocation);
         C_4137_ levelrenderer$transparencyshaderexception = new C_4137_(s1, var8);
         if (this.f_109461_.m_91099_().m_10523_().size() > 1) {
            C_4996_ component = (C_4996_)this.f_109461_.m_91098_().m_7536_().findFirst().map((packResourcesIn) -> {
               return C_4996_.m_237113_(packResourcesIn.m_5542_());
            }).orElse((Object)null);
            this.f_109461_.f_91066_.m_232060_().m_231514_(C_3383_.FANCY);
            this.f_109461_.m_91241_(levelrenderer$transparencyshaderexception, component, (C_3391_.C_290041_)null);
         } else {
            this.f_109461_.f_91066_.m_232060_().m_231514_(C_3383_.FANCY);
            this.f_109461_.f_91066_.m_92169_();
            f_109453_.error(LogUtils.FATAL_MARKER, s1, levelrenderer$transparencyshaderexception);
            this.f_109461_.m_306708_(new C_4883_(s1, levelrenderer$transparencyshaderexception));
         }
      }

   }

   private void m_109834_() {
      if (this.f_109418_ != null) {
         this.f_109418_.close();
         this.f_109413_.m_83930_();
         this.f_109414_.m_83930_();
         this.f_109415_.m_83930_();
         this.f_109416_.m_83930_();
         this.f_109417_.m_83930_();
         this.f_109418_ = null;
         this.f_109413_ = null;
         this.f_109414_ = null;
         this.f_109415_ = null;
         this.f_109416_ = null;
         this.f_109417_ = null;
      }

   }

   public void m_109769_() {
      if (this.m_109817_()) {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
         this.f_109411_.m_83957_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_(), false);
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      }

   }

   public boolean m_109817_() {
      if (!Config.isShaders() && !Config.isAntialiasing()) {
         return !this.f_109461_.f_91063_.m_172715_() && this.f_109411_ != null && this.f_109412_ != null && this.f_109461_.f_91074_ != null;
      } else {
         return false;
      }
   }

   private void m_109835_() {
      if (this.f_109473_ != null) {
         this.f_109473_.close();
      }

      this.f_109473_ = new C_3186_(C_3186_.C_285533_.STATIC);
      this.f_109473_.m_85921_();
      this.f_109473_.m_231221_(m_234267_(C_3185_.m_85913_(), -16.0F));
      C_3186_.m_85931_();
   }

   private void m_109836_() {
      if (this.f_109472_ != null) {
         this.f_109472_.close();
      }

      this.f_109472_ = new C_3186_(C_3186_.C_285533_.STATIC);
      this.f_109472_.m_85921_();
      this.f_109472_.m_231221_(m_234267_(C_3185_.m_85913_(), 16.0F));
      C_3186_.m_85931_();
   }

   private static C_336471_ m_234267_(C_3185_ bufferBuilderIn, float posY) {
      float f = Math.signum(posY) * 512.0F;
      float f1 = 512.0F;
      C_3173_ bufferbuilder = bufferBuilderIn.m_339075_(C_3188_.C_141549_.TRIANGLE_FAN, C_3179_.f_85814_);
      bufferbuilder.m_167146_(0.0F, posY, 0.0F);

      for(int i = -180; i <= 180; i += 45) {
         bufferbuilder.m_167146_(f * C_188_.m_14089_((float)i * 0.017453292F), posY, 512.0F * C_188_.m_14031_((float)i * 0.017453292F));
      }

      return bufferbuilder.m_339905_();
   }

   private void m_109837_() {
      if (this.f_109471_ != null) {
         this.f_109471_.close();
      }

      this.f_109471_ = new C_3186_(C_3186_.C_285533_.STATIC);
      this.f_109471_.m_85921_();
      this.f_109471_.m_231221_(this.m_234259_(C_3185_.m_85913_()));
      C_3186_.m_85931_();
   }

   private C_336471_ m_234259_(C_3185_ bufferBuilderIn) {
      C_212974_ randomsource = C_212974_.m_216335_(10842L);
      int i = true;
      float f = 100.0F;
      C_3173_ bufferbuilder = bufferBuilderIn.m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85814_);

      for(int j = 0; j < 1500; ++j) {
         float f1 = randomsource.m_188501_() * 2.0F - 1.0F;
         float f2 = randomsource.m_188501_() * 2.0F - 1.0F;
         float f3 = randomsource.m_188501_() * 2.0F - 1.0F;
         float f4 = 0.15F + randomsource.m_188501_() * 0.1F;
         float f5 = C_188_.m_338503_(f1, f2, f3);
         if (!(f5 <= 0.010000001F) && !(f5 >= 1.0F)) {
            Vector3f vector3f = (new Vector3f(f1, f2, f3)).normalize(100.0F);
            float f6 = (float)(randomsource.m_188500_() * 3.1415927410125732 * 2.0);
            Quaternionf quaternionf = (new Quaternionf()).rotateTo(new Vector3f(0.0F, 0.0F, -1.0F), vector3f).rotateZ(f6);
            bufferbuilder.m_340435_(vector3f.add((new Vector3f(f4, -f4, 0.0F)).rotate(quaternionf)));
            bufferbuilder.m_340435_(vector3f.add((new Vector3f(f4, f4, 0.0F)).rotate(quaternionf)));
            bufferbuilder.m_340435_(vector3f.add((new Vector3f(-f4, f4, 0.0F)).rotate(quaternionf)));
            bufferbuilder.m_340435_(vector3f.add((new Vector3f(-f4, -f4, 0.0F)).rotate(quaternionf)));
         }
      }

      return bufferbuilder.m_339905_();
   }

   public void m_109701_(@Nullable C_3899_ worldClientIn) {
      this.f_291437_ = Integer.MIN_VALUE;
      this.f_291126_ = Integer.MIN_VALUE;
      this.f_290828_ = Integer.MIN_VALUE;
      this.f_109463_.m_114406_(worldClientIn);
      this.f_109465_ = worldClientIn;
      if (Config.isDynamicLights()) {
         DynamicLights.clear();
      }

      ChunkVisibility.reset();
      this.renderEnv.reset((C_2064_)null, (C_4675_)null);
      BiomeUtils.onWorldChanged(this.f_109465_);
      Shaders.checkWorldChanged(this.f_109465_);
      if (worldClientIn != null) {
         this.m_109818_();
      } else {
         if (this.f_109469_ != null) {
            this.f_109469_.m_110849_();
            this.f_109469_ = null;
         }

         if (this.f_290446_ != null) {
            this.f_290446_.m_294449_();
         }

         this.f_290446_ = null;
         this.f_109468_.clear();
         this.f_291822_.m_295341_((C_4180_)null);
         this.f_290776_.clear();
         this.clearRenderInfos();
      }

   }

   public void m_173014_() {
      if (C_3391_.m_91085_()) {
         this.m_109833_();
      } else {
         this.m_109834_();
      }

   }

   public void m_109818_() {
      if (this.f_109465_ != null) {
         this.m_173014_();
         this.f_109465_.m_104810_();
         if (this.f_290446_ == null) {
            this.f_290446_ = new C_290152_(this.f_109465_, this, C_5322_.m_183991_(), this.f_109464_, this.f_109461_.m_91289_(), this.f_109461_.m_167982_());
         } else {
            this.f_290446_.m_293166_(this.f_109465_);
         }

         this.f_109474_ = true;
         C_4130_.m_109291_(Config.isTreesFancy());
         C_4186_.updateAoLightValue();
         if (Config.isDynamicLights()) {
            DynamicLights.clear();
         }

         SmartAnimations.update();
         ambientOcclusion = C_3391_.m_91086_();
         this.f_109438_ = this.f_109461_.f_91066_.m_193772_();
         this.renderDistance = this.f_109438_ * 16;
         this.renderDistanceSq = this.renderDistance * this.renderDistance;
         double renderDistanceXZ = (double)((this.f_109438_ + 1) * 16);
         this.renderDistanceXZSq = (int)(renderDistanceXZ * renderDistanceXZ);
         if (this.f_109469_ != null) {
            this.f_109469_.m_110849_();
         }

         GpuMemory.bufferFreed(GpuMemory.getBufferAllocated());
         this.f_290446_.m_295714_();
         synchronized(this.f_109468_) {
            this.f_109468_.clear();
         }

         this.f_109469_ = new C_4180_(this.f_290446_, this.f_109465_, this.f_109461_.f_91066_.m_193772_(), this);
         this.f_291822_.m_295341_(this.f_109469_);
         this.f_290776_.clear();
         this.clearRenderInfos();
         this.m_173019_();
         C_507_ entity = this.f_109461_.m_91288_();
         if (entity != null) {
            this.f_109469_.m_110850_(entity.m_20185_(), entity.m_20189_());
         }
      }

   }

   public void m_109487_(int width, int height) {
      this.m_109826_();
      if (this.f_109412_ != null) {
         this.f_109412_.m_110025_(width, height);
      }

      if (this.f_109418_ != null) {
         this.f_109418_.m_110025_(width, height);
      }

   }

   public String m_109820_() {
      int i = this.f_109469_.f_291707_.length;
      int j = this.m_294574_();
      return String.format(Locale.ROOT, "C: %d/%d %sD: %d, %s", j, i, this.f_109461_.f_90980_ ? "(s) " : "", this.f_109438_, this.f_290446_ == null ? "null" : this.f_290446_.m_292950_());
   }

   public C_290152_ m_295427_() {
      return this.f_290446_;
   }

   public double m_294933_() {
      return (double)this.f_109469_.f_291707_.length;
   }

   public double m_173017_() {
      return (double)this.f_109438_;
   }

   public int m_294574_() {
      return this.renderInfosTerrain.size();
   }

   public String m_109822_() {
      int var10000 = this.f_109439_;
      return "E: " + var10000 + "/" + this.f_109465_.m_104813_() + ", B: " + this.f_109440_ + ", SD: " + this.f_109465_.m_194186_() + ", " + Config.getVersionDebug();
   }

   private void m_194338_(C_3373_ activeRenderInfoIn, C_4273_ camera, boolean debugCamera, boolean spectator) {
      C_3046_ vec3 = activeRenderInfoIn.m_90583_();
      if (this.f_109461_.f_91066_.m_193772_() != this.f_109438_) {
         this.m_109818_();
      }

      this.f_109465_.m_46473_().m_6180_("camera");
      double d0 = this.f_109461_.f_91074_.dt();
      double d1 = this.f_109461_.f_91074_.dv();
      double d2 = this.f_109461_.f_91074_.dz();
      int i = C_4710_.m_175552_(d0);
      int j = C_4710_.m_175552_(d1);
      int k = C_4710_.m_175552_(d2);
      if (this.f_291437_ != i || this.f_291126_ != j || this.f_290828_ != k) {
         this.f_291437_ = i;
         this.f_291126_ = j;
         this.f_290828_ = k;
         this.f_109469_.m_110850_(d0, d2);
      }

      if (Config.isDynamicLights()) {
         DynamicLights.update(this);
      }

      this.f_290446_.m_294870_(vec3);
      this.f_109465_.m_46473_().m_6182_("cull");
      this.f_109461_.m_91307_().m_6182_("culling");
      C_4675_ blockpos = activeRenderInfoIn.m_90588_();
      double d3 = Math.floor(vec3.f_82479_ / 8.0);
      double d4 = Math.floor(vec3.f_82480_ / 8.0);
      double d5 = Math.floor(vec3.f_82481_ / 8.0);
      if (d3 != this.f_109425_ || d4 != this.f_109426_ || d5 != this.f_109427_) {
         this.f_291822_.m_295966_();
      }

      this.f_109425_ = d3;
      this.f_109426_ = d4;
      this.f_109427_ = d5;
      this.f_109461_.m_91307_().m_6182_("update");
      Lagometer.timerVisibility.start();
      if (!debugCamera) {
         boolean flag = this.f_109461_.f_90980_;
         if (spectator && this.f_109465_.m_8055_(blockpos).m_60804_(this.f_109465_, blockpos)) {
            flag = false;
         }

         C_507_.m_20103_(C_188_.m_14008_((double)this.f_109461_.f_91066_.m_193772_() / 8.0, 1.0, 2.5) * (Double)this.f_109461_.f_91066_.m_232018_().m_231551_());
         this.f_109461_.m_91307_().m_6180_("section_occlusion_graph");
         this.f_291822_.m_292654_(flag, activeRenderInfoIn, camera, this.f_290776_);
         this.f_109461_.m_91307_().m_7238_();
         double d6 = Math.floor((double)(activeRenderInfoIn.m_90589_() / 2.0F));
         double d7 = Math.floor((double)(activeRenderInfoIn.m_90590_() / 2.0F));
         boolean frustumChanged = false;
         if (this.f_291822_.m_293178_() || d6 != this.f_109428_ || d7 != this.f_109429_) {
            this.m_194354_(m_295345_(camera));
            this.f_109428_ = d6;
            this.f_109429_ = d7;
            frustumChanged = true;
            ShadersRender.frustumTerrainShadowChanged = true;
         }

         if (this.f_109465_.getSectionStorage().resetUpdated() || frustumChanged) {
            this.applyFrustumEntities(camera, -1);
            ShadersRender.frustumEntitiesShadowChanged = true;
         }
      }

      Lagometer.timerVisibility.end();
      this.f_109461_.m_91307_().m_7238_();
   }

   public static C_4273_ m_295345_(C_4273_ frustumIn) {
      return (new C_4273_(frustumIn)).m_194441_(8);
   }

   private void m_194354_(C_4273_ frustumIn) {
      this.applyFrustum(frustumIn, true, -1);
   }

   public void applyFrustum(C_4273_ frustumIn, boolean updateRenderInfos, int maxChunkDistance) {
      if (!C_3391_.m_91087_().bx()) {
         throw new IllegalStateException("applyFrustum called from wrong thread: " + Thread.currentThread().getName());
      } else {
         this.f_109461_.m_91307_().m_6180_("apply_frustum");
         if (updateRenderInfos) {
            this.f_290776_.clear();
         }

         this.clearRenderInfosTerrain();
         this.f_291822_.addSectionsInFrustum(frustumIn, this.f_290776_, updateRenderInfos, maxChunkDistance);
         this.f_109461_.m_91307_().m_7238_();
      }
   }

   public void m_294499_(C_290152_.C_290138_ renderSectionIn) {
      this.f_291822_.m_293743_(renderSectionIn);
   }

   private void m_252964_(Matrix4f matrixIn, Matrix4f projectionIn, double camX, double camY, double camZ, C_4273_ frustumIn) {
      this.f_109442_ = frustumIn;
      Matrix4f matrix4f = new Matrix4f(projectionIn);
      matrix4f.mul(matrixIn);
      matrix4f.invert();
      this.f_109444_.x = camX;
      this.f_109444_.y = camY;
      this.f_109444_.z = camZ;
      this.f_109443_[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.f_109443_[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.f_109443_[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.f_109443_[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.f_109443_[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.f_109443_[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.f_109443_[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.f_109443_[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for(int i = 0; i < 8; ++i) {
         matrix4f.transform(this.f_109443_[i]);
         this.f_109443_[i].div(this.f_109443_[i].w());
      }

   }

   public void m_253210_(C_3046_ posIn, Matrix4f viewMatrixIn, Matrix4f projectionMatrixIn) {
      this.f_172938_ = new C_4273_(viewMatrixIn, projectionMatrixIn);
      this.f_172938_.m_113002_(posIn.m_7096_(), posIn.m_7098_(), posIn.m_7094_());
      if (Config.isShaders() && !Shaders.isFrustumCulling()) {
         this.f_172938_.disabled = true;
      }

   }

   public void m_109599_(C_336468_ partialTicks, boolean drawBlockOutline, C_3373_ activeRenderInfoIn, C_4124_ gameRendererIn, C_4138_ lightmapIn, Matrix4f viewIn, Matrix4f projectionIn) {
      C_302051_ tickratemanager = this.f_109461_.f_91073_.m_304826_();
      float f = partialTicks.m_338527_(false);
      RenderSystem.setShaderGameTime(this.f_109465_.m_46467_(), f);
      this.f_172946_.m_173564_(this.f_109465_, activeRenderInfoIn, this.f_109461_.f_91077_);
      this.f_109463_.m_114408_(this.f_109465_, activeRenderInfoIn, this.f_109461_.f_91076_);
      C_442_ profilerfiller = this.f_109465_.m_46473_();
      profilerfiller.m_6182_("light_update_queue");
      this.f_109465_.m_194141_();
      profilerfiller.m_6182_("light_updates");
      this.f_109465_.m_7726_().m_7827_().m_9323_();
      C_3046_ vec3 = activeRenderInfoIn.m_90583_();
      double d0 = vec3.m_7096_();
      double d1 = vec3.m_7098_();
      double d2 = vec3.m_7094_();
      profilerfiller.m_6182_("culling");
      boolean flag = this.f_109442_ != null;
      C_4273_ frustum;
      if (flag) {
         frustum = this.f_109442_;
         frustum.m_113002_(this.f_109444_.x, this.f_109444_.y, this.f_109444_.z);
      } else {
         frustum = this.f_172938_;
      }

      this.f_109461_.m_91307_().m_6182_("captureFrustum");
      if (this.f_109441_) {
         this.m_252964_(viewIn, projectionIn, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, flag ? new C_4273_(viewIn, projectionIn) : frustum);
         this.f_109441_ = false;
         frustum = this.f_109442_;
         frustum.disabled = Config.isShaders() && !Shaders.isFrustumCulling();
         frustum.m_113002_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
         this.applyFrustum(frustum, false, -1);
         this.applyFrustumEntities(frustum, -1);
      }

      if (this.debugFixTerrainFrustumShadow) {
         this.m_252964_(viewIn, projectionIn, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, ShadersRender.makeShadowFrustum(activeRenderInfoIn, f));
         this.debugFixTerrainFrustumShadow = false;
         frustum = this.f_109442_;
         frustum.m_113002_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
         ShadersRender.frustumTerrainShadowChanged = true;
         ShadersRender.frustumEntitiesShadowChanged = true;
         ShadersRender.applyFrustumShadow(this, frustum);
      }

      profilerfiller.m_6182_("clear");
      if (Config.isShaders()) {
         Shaders.setViewport(0, 0, this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
      } else {
         RenderSystem.viewport(0, 0, this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
      }

      C_4122_.m_109018_(activeRenderInfoIn, f, this.f_109461_.f_91073_, this.f_109461_.f_91066_.m_193772_(), gameRendererIn.m_109131_(f));
      C_4122_.m_109036_();
      RenderSystem.clear(16640, C_3391_.f_91002_);
      boolean isShaders = Config.isShaders();
      if (isShaders) {
         Shaders.clearRenderBuffer();
         Shaders.setCamera(viewIn, activeRenderInfoIn, f);
         Shaders.renderPrepare();
      }

      float f1 = gameRendererIn.m_109152_();
      boolean flag1 = this.f_109461_.f_91073_.m_104583_().m_5781_(C_188_.m_14107_(d0), C_188_.m_14107_(d1)) || this.f_109461_.f_91065_.m_93090_().m_93715_();
      boolean renderSky = this.f_109461_.f_91073_.m_104583_().m_5781_(C_188_.m_14107_(d0), C_188_.m_14107_(d1));
      if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
         profilerfiller.m_6182_("sky");
         if (isShaders) {
            Shaders.beginSky();
         }

         RenderSystem.setShader(C_4124_::m_172808_);
         this.m_202423_(viewIn, projectionIn, f, activeRenderInfoIn, renderSky, () -> {
            C_4122_.m_234172_(activeRenderInfoIn, C_4122_.C_4123_.FOG_SKY, f1, flag1, f);
         });
         if (isShaders) {
            Shaders.endSky();
         }
      } else {
         GlStateManager._disableBlend();
      }

      ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_SKY, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
      profilerfiller.m_6182_("fog");
      C_4122_.m_234172_(activeRenderInfoIn, C_4122_.C_4123_.FOG_TERRAIN, Math.max(f1, 32.0F), flag1, f);
      profilerfiller.m_6182_("terrain_setup");
      this.checkLoadVisibleChunks(activeRenderInfoIn, frustum, this.f_109461_.f_91074_.R_());
      ++this.frameId;
      this.m_194338_(activeRenderInfoIn, frustum, flag, this.f_109461_.f_91074_.R_());
      profilerfiller.m_6182_("compile_sections");
      this.m_194370_(activeRenderInfoIn);
      profilerfiller.m_6182_("terrain");
      Lagometer.timerTerrain.start();
      if (this.f_109461_.f_91066_.ofSmoothFps) {
         this.f_109461_.m_91307_().m_6182_("finish");
         GL11.glFinish();
         this.f_109461_.m_91307_().m_6182_("terrain");
      }

      if (Config.isFogOff() && C_4122_.fogStandard) {
         RenderSystem.setFogAllowed(false);
      }

      this.m_293111_(C_4168_.m_110451_(), d0, d1, d2, viewIn, projectionIn);
      this.f_109461_.m_91304_().m_119428_(C_4484_.f_118259_).setBlurMipmap(false, (Integer)this.f_109461_.f_91066_.m_232119_().m_231551_() > 0);
      this.m_293111_(C_4168_.m_110457_(), d0, d1, d2, viewIn, projectionIn);
      this.f_109461_.m_91304_().m_119428_(C_4484_.f_118259_).restoreLastBlurMipmap();
      this.m_293111_(C_4168_.m_110463_(), d0, d1, d2, viewIn, projectionIn);
      if (isShaders) {
         ShadersRender.endTerrain();
      }

      if (this.f_109465_.m_104583_().m_108885_()) {
         C_3144_.m_252995_();
      } else {
         C_3144_.m_252756_();
      }

      if (isShaders) {
         Shaders.beginEntities();
      }

      C_4353_.updateItemRenderDistance();
      profilerfiller.m_6182_("entities");
      ++renderEntitiesCounter;
      this.f_109439_ = 0;
      this.f_109440_ = 0;
      this.countTileEntitiesRendered = 0;
      if (this.f_109414_ != null) {
         this.f_109414_.m_83954_(C_3391_.f_91002_);
         this.f_109414_.m_83945_(this.f_109461_.m_91385_());
         this.f_109461_.m_91385_().m_83947_(false);
      }

      if (this.f_109416_ != null) {
         this.f_109416_.m_83954_(C_3391_.f_91002_);
      }

      if (this.m_109817_()) {
         this.f_109411_.m_83954_(C_3391_.f_91002_);
         this.f_109461_.m_91385_().m_83947_(false);
      }

      Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
      matrix4fstack.pushMatrix();
      matrix4fstack.mul(viewIn);
      RenderSystem.applyModelViewMatrix();
      boolean flag2 = false;
      C_3181_ posestack = new C_3181_();
      C_4139_.C_4140_ multibuffersource$buffersource = this.f_109464_.m_110104_();
      if (Config.isFastRender()) {
         RenderStateManager.enableCache();
         multibuffersource$buffersource.enableCache();
      }

      int minWorldY = this.f_109465_.I_();
      int maxWorldY = this.f_109465_.am();
      if (Config.isRenderRegions() || Config.isMultiTexture()) {
         C_4124_.m_172808_().m_173363_();
      }

      Iterator var31 = this.f_109465_.m_104735_().iterator();

      while(true) {
         C_507_ entity;
         boolean entityPlayerNotSpectator;
         Object multibuffersource;
         do {
            C_4675_ blockpos;
            do {
               do {
                  do {
                     do {
                        if (!var31.hasNext()) {
                           Collection entityLists = this.mapEntityLists.values();
                           Iterator var50 = entityLists.iterator();

                           Iterator var54;
                           while(var50.hasNext()) {
                              List entityList = (List)var50.next();
                              var54 = entityList.iterator();

                              while(var54.hasNext()) {
                                 C_507_ entity = (C_507_)var54.next();
                                 ++this.f_109439_;
                                 if (entity.f_19797_ == 0) {
                                    entity.f_19790_ = entity.m_20185_();
                                    entity.f_19791_ = entity.m_20186_();
                                    entity.f_19792_ = entity.m_20189_();
                                 }

                                 if (this.m_109817_() && this.f_109461_.m_91314_(entity)) {
                                    flag2 = true;
                                    C_4141_ outlinebuffersource = this.f_109464_.m_110109_();
                                    multibuffersource = outlinebuffersource;
                                    int i = entity.m_19876_();
                                    outlinebuffersource.m_109929_(C_175_.m_13665_(i), C_175_.m_13667_(i), C_175_.m_13669_(i), 255);
                                 } else {
                                    if (Reflector.IForgeEntity_hasCustomOutlineRendering.exists() && this.m_109817_() && Reflector.callBoolean(entity, Reflector.IForgeEntity_hasCustomOutlineRendering, this.f_109461_.f_91074_)) {
                                       flag2 = true;
                                    }

                                    multibuffersource = multibuffersource$buffersource;
                                 }

                                 if (isShaders) {
                                    Shaders.nextEntity(entity);
                                 }

                                 float f2 = partialTicks.m_338527_(!tickratemanager.m_305579_(entity));
                                 this.m_109517_(entity, d0, d1, d2, f2, posestack, (C_4139_)multibuffersource);
                              }

                              entityList.clear();
                           }

                           multibuffersource$buffersource.m_173043_();
                           this.m_109588_(posestack);
                           multibuffersource$buffersource.m_109912_(C_4168_.m_110446_(C_4484_.f_118259_));
                           multibuffersource$buffersource.m_109912_(C_4168_.m_110452_(C_4484_.f_118259_));
                           multibuffersource$buffersource.m_109912_(C_4168_.m_110458_(C_4484_.f_118259_));
                           multibuffersource$buffersource.m_109912_(C_4168_.m_110476_(C_4484_.f_118259_));
                           if (Config.isFastRender()) {
                              multibuffersource$buffersource.flushCache();
                              RenderStateManager.flushCache();
                           }

                           if (isShaders) {
                              Shaders.endEntities();
                           }

                           ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_ENTITIES, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
                           if (isShaders) {
                              Shaders.beginBlockEntities();
                           }

                           profilerfiller.m_6182_("blockentities");
                           C_4253_.updateTextRenderDistance();
                           boolean forgeRenderBoundingBox = Reflector.IForgeBlockEntity_getRenderBoundingBox.exists();
                           C_4273_ camera = frustum;
                           var54 = this.renderInfosTileEntities.iterator();

                           label366:
                           while(true) {
                              List list;
                              do {
                                 if (!var54.hasNext()) {
                                    synchronized(this.f_109468_) {
                                       Iterator var59 = this.f_109468_.iterator();

                                       label341:
                                       while(true) {
                                          C_3040_ aabb;
                                          C_1991_ blockentity;
                                          do {
                                             if (!var59.hasNext()) {
                                                break label341;
                                             }

                                             blockentity = (C_1991_)var59.next();
                                             if (!forgeRenderBoundingBox) {
                                                break;
                                             }

                                             aabb = (C_3040_)Reflector.call(blockentity, Reflector.IForgeBlockEntity_getRenderBoundingBox);
                                          } while(aabb != null && !camera.m_113029_(aabb));

                                          if (isShaders) {
                                             Shaders.nextBlockEntity(blockentity);
                                          }

                                          C_4675_ blockpos3 = blockentity.m_58899_();
                                          posestack.m_85836_();
                                          posestack.m_85837_((double)blockpos3.u() - d0, (double)blockpos3.v() - d1, (double)blockpos3.w() - d2);
                                          if (Reflector.IForgeBlockEntity_hasCustomOutlineRendering.exists() && this.m_109817_() && Reflector.callBoolean(blockentity, Reflector.IForgeBlockEntity_hasCustomOutlineRendering, this.f_109461_.f_91074_)) {
                                             flag2 = true;
                                          }

                                          this.f_172946_.m_112267_(blockentity, f, posestack, multibuffersource$buffersource);
                                          posestack.m_85849_();
                                          ++this.countTileEntitiesRendered;
                                       }
                                    }

                                    this.m_109588_(posestack);
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_110451_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_173239_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_173242_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110789_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110790_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110785_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110786_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110787_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_246640_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110788_());
                                    this.f_109464_.m_110109_().m_109928_();
                                    if (Config.isFastRender()) {
                                       multibuffersource$buffersource.disableCache();
                                       RenderStateManager.disableCache();
                                    }

                                    Lagometer.timerTerrain.end();
                                    if (flag2) {
                                       this.f_109412_.m_110023_(partialTicks.m_339005_());
                                       this.f_109461_.m_91385_().m_83947_(false);
                                    }

                                    if (isShaders) {
                                       Shaders.endBlockEntities();
                                    }

                                    ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_BLOCK_ENTITIES, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
                                    this.renderOverlayDamaged = true;
                                    profilerfiller.m_6182_("destroyProgress");
                                    ObjectIterator var55 = this.f_109409_.long2ObjectEntrySet().iterator();

                                    while(var55.hasNext()) {
                                       Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry)var55.next();
                                       C_4675_ blockpos2 = C_4675_.m_122022_(entry.getLongKey());
                                       double d3 = (double)blockpos2.u() - d0;
                                       double d4 = (double)blockpos2.v() - d1;
                                       double d5 = (double)blockpos2.w() - d2;
                                       if (!(d3 * d3 + d4 * d4 + d5 * d5 > 1024.0)) {
                                          SortedSet sortedset1 = (SortedSet)entry.getValue();
                                          if (sortedset1 != null && !sortedset1.isEmpty()) {
                                             int k = ((C_5421_)sortedset1.last()).m_139988_();
                                             posestack.m_85836_();
                                             posestack.m_85837_((double)blockpos2.u() - d0, (double)blockpos2.v() - d1, (double)blockpos2.w() - d2);
                                             C_3181_.C_3183_ posestack$pose1 = posestack.m_85850_();
                                             C_3187_ vertexconsumer1 = new C_3184_(this.f_109464_.m_110108_().m_6299_((C_4168_)C_4532_.f_119229_.get(k)), posestack$pose1, 1.0F);
                                             ModelData modelData = this.f_109465_.getModelDataManager().getAt(blockpos2);
                                             if (modelData == null) {
                                                modelData = ModelData.EMPTY;
                                             }

                                             this.f_109461_.m_91289_().renderBreakingTexture(this.f_109465_.m_8055_(blockpos2), blockpos2, this.f_109465_, posestack, vertexconsumer1, modelData);
                                             posestack.m_85849_();
                                          }
                                       }
                                    }

                                    this.renderOverlayDamaged = false;
                                    RenderUtils.flushRenderBuffers();
                                    --renderEntitiesCounter;
                                    this.m_109588_(posestack);
                                    C_3043_ hitresult = this.f_109461_.f_91077_;
                                    if (drawBlockOutline && hitresult != null && hitresult.m_6662_() == C_3044_.BLOCK) {
                                       profilerfiller.m_6182_("outline");
                                       C_4675_ blockpos1 = ((C_3041_)hitresult).m_82425_();
                                       C_2064_ blockstate = this.f_109465_.m_8055_(blockpos1);
                                       if (isShaders) {
                                          ShadersRender.beginOutline();
                                       }

                                       if (!Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawHighlight, this, activeRenderInfoIn, hitresult, partialTicks, viewIn, multibuffersource$buffersource) && !blockstate.m_60795_() && this.f_109465_.m_6857_().m_61937_(blockpos1)) {
                                          C_3187_ vertexconsumer2 = multibuffersource$buffersource.m_6299_(C_4168_.m_110504_());
                                          this.m_109637_(posestack, vertexconsumer2, activeRenderInfoIn.m_90592_(), d0, d1, d2, blockpos1, blockstate);
                                       }

                                       if (isShaders) {
                                          multibuffersource$buffersource.m_109912_(C_4168_.m_110504_());
                                          ShadersRender.endOutline();
                                       }
                                    } else if (hitresult != null && hitresult.m_6662_() == C_3044_.ENTITY) {
                                       Reflector.ForgeHooksClient_onDrawHighlight.call(this, activeRenderInfoIn, hitresult, partialTicks, viewIn, multibuffersource$buffersource);
                                    }

                                    this.f_109461_.f_91064_.m_113457_(posestack, multibuffersource$buffersource, d0, d1, d2);
                                    multibuffersource$buffersource.m_173043_();
                                    if (isShaders) {
                                       RenderUtils.finishRenderBuffers();
                                       ShadersRender.beginDebug();
                                    }

                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110792_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110762_());
                                    multibuffersource$buffersource.m_109912_(C_4177_.m_110782_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_110484_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_110490_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_110487_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_110496_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_110499_());
                                    multibuffersource$buffersource.m_109912_(C_4168_.m_110478_());
                                    this.f_109464_.m_110108_().m_109911_();
                                    if (isShaders) {
                                       multibuffersource$buffersource.m_109911_();
                                       ShadersRender.endDebug();
                                       Shaders.preRenderHand();
                                       Matrix4f projectionPrev = MathUtils.copy(RenderSystem.getProjectionMatrix());
                                       ShadersRender.renderHand0(gameRendererIn, viewIn, activeRenderInfoIn, f);
                                       RenderSystem.setProjectionMatrix(projectionPrev, RenderSystem.getVertexSorting());
                                       Shaders.preWater();
                                    }

                                    if (this.f_109418_ != null) {
                                       multibuffersource$buffersource.m_109912_(C_4168_.m_110504_());
                                       multibuffersource$buffersource.m_109911_();
                                       this.f_109413_.m_83954_(C_3391_.f_91002_);
                                       this.f_109413_.m_83945_(this.f_109461_.m_91385_());
                                       profilerfiller.m_6182_("translucent");
                                       this.m_293111_(C_4168_.m_110466_(), d0, d1, d2, viewIn, projectionIn);
                                       profilerfiller.m_6182_("string");
                                       this.m_293111_(C_4168_.m_110503_(), d0, d1, d2, viewIn, projectionIn);
                                       this.f_109415_.m_83954_(C_3391_.f_91002_);
                                       this.f_109415_.m_83945_(this.f_109461_.m_91385_());
                                       C_4149_.f_110126_.m_110185_();
                                       profilerfiller.m_6182_("particles");
                                       this.f_109461_.f_91061_.renderParticles(lightmapIn, activeRenderInfoIn, f, frustum);
                                       ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_PARTICLES, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
                                       C_4149_.f_110126_.m_110188_();
                                    } else {
                                       profilerfiller.m_6182_("translucent");
                                       Lagometer.timerTerrain.start();
                                       if (Shaders.isParticlesBeforeDeferred()) {
                                          Shaders.beginParticles();
                                          this.f_109461_.f_91061_.renderParticles(lightmapIn, activeRenderInfoIn, f, frustum);
                                          Shaders.endParticles();
                                          ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_PARTICLES, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
                                       }

                                       if (isShaders) {
                                          Shaders.beginWater();
                                       }

                                       if (this.f_109413_ != null) {
                                          this.f_109413_.m_83954_(C_3391_.f_91002_);
                                       }

                                       this.m_293111_(C_4168_.m_110466_(), d0, d1, d2, viewIn, projectionIn);
                                       if (isShaders) {
                                          Shaders.endWater();
                                       }

                                       Lagometer.timerTerrain.end();
                                       multibuffersource$buffersource.m_109912_(C_4168_.m_110504_());
                                       multibuffersource$buffersource.m_109911_();
                                       profilerfiller.m_6182_("string");
                                       this.m_293111_(C_4168_.m_110503_(), d0, d1, d2, viewIn, projectionIn);
                                       profilerfiller.m_6182_("particles");
                                       if (!Shaders.isParticlesBeforeDeferred()) {
                                          if (isShaders) {
                                             Shaders.beginParticles();
                                          }

                                          this.f_109461_.f_91061_.renderParticles(lightmapIn, activeRenderInfoIn, f, frustum);
                                          if (isShaders) {
                                             Shaders.endParticles();
                                          }

                                          ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_PARTICLES, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
                                       }
                                    }

                                    RenderSystem.setFogAllowed(true);
                                    if (this.f_109461_.f_91066_.m_92174_() != C_3376_.OFF) {
                                       if (this.f_109418_ != null) {
                                          this.f_109417_.m_83954_(C_3391_.f_91002_);
                                       }

                                       profilerfiller.m_6182_("clouds");
                                       this.m_253054_(posestack, viewIn, projectionIn, f, d0, d1, d2);
                                    }

                                    if (this.f_109418_ != null) {
                                       C_4149_.f_110127_.m_110185_();
                                       profilerfiller.m_6182_("weather");
                                       this.m_109703_(lightmapIn, f, d0, d1, d2);
                                       ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_WEATHER, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
                                       this.m_173012_(activeRenderInfoIn);
                                       C_4149_.f_110127_.m_110188_();
                                       this.f_109418_.m_110023_(partialTicks.m_339005_());
                                       this.f_109461_.m_91385_().m_83947_(false);
                                    } else {
                                       RenderSystem.depthMask(false);
                                       profilerfiller.m_6182_("weather");
                                       if (isShaders) {
                                          Shaders.beginWeather();
                                       }

                                       this.m_109703_(lightmapIn, f, d0, d1, d2);
                                       if (isShaders) {
                                          Shaders.endWeather();
                                       }

                                       ReflectorForge.dispatchRenderStageS(Reflector.RenderLevelStageEvent_Stage_AFTER_WEATHER, this, viewIn, projectionIn, this.f_109477_, activeRenderInfoIn, frustum);
                                       this.m_173012_(activeRenderInfoIn);
                                       RenderSystem.depthMask(true);
                                    }

                                    this.m_269240_(posestack, multibuffersource$buffersource, activeRenderInfoIn);
                                    multibuffersource$buffersource.m_173043_();
                                    matrix4fstack.popMatrix();
                                    RenderSystem.applyModelViewMatrix();
                                    RenderSystem.depthMask(true);
                                    RenderSystem.disableBlend();
                                    C_4122_.m_109017_();
                                    return;
                                 }

                                 C_290152_.C_290138_ sectionrenderdispatcher$rendersection = (C_290152_.C_290138_)var54.next();
                                 list = sectionrenderdispatcher$rendersection.m_293175_().m_293674_();
                              } while(list.isEmpty());

                              Iterator var64 = list.iterator();

                              while(true) {
                                 C_3040_ aabb;
                                 C_1991_ blockentity1;
                                 do {
                                    if (!var64.hasNext()) {
                                       continue label366;
                                    }

                                    blockentity1 = (C_1991_)var64.next();
                                    if (!forgeRenderBoundingBox) {
                                       break;
                                    }

                                    aabb = (C_3040_)Reflector.call(blockentity1, Reflector.IForgeBlockEntity_getRenderBoundingBox);
                                 } while(aabb != null && !camera.m_113029_(aabb));

                                 if (isShaders) {
                                    Shaders.nextBlockEntity(blockentity1);
                                 }

                                 C_4675_ blockpos4 = blockentity1.m_58899_();
                                 C_4139_ multibuffersource1 = multibuffersource$buffersource;
                                 posestack.m_85836_();
                                 posestack.m_85837_((double)blockpos4.u() - d0, (double)blockpos4.v() - d1, (double)blockpos4.w() - d2);
                                 SortedSet sortedset = (SortedSet)this.f_109409_.get(blockpos4.m_121878_());
                                 if (sortedset != null && !sortedset.isEmpty()) {
                                    int j = ((C_5421_)sortedset.last()).m_139988_();
                                    if (j >= 0) {
                                       C_3181_.C_3183_ posestack$pose = posestack.m_85850_();
                                       C_3187_ vertexconsumer = new C_3184_(this.f_109464_.m_110108_().m_6299_((C_4168_)C_4532_.f_119229_.get(j)), posestack$pose, 1.0F);
                                       multibuffersource1 = (renderTypeIn) -> {
                                          C_3187_ vertexconsumer3 = multibuffersource$buffersource.m_6299_(renderTypeIn);
                                          return renderTypeIn.m_110405_() ? C_3193_.m_86168_(vertexconsumer, vertexconsumer3) : vertexconsumer3;
                                       };
                                    }
                                 }

                                 if (Reflector.IForgeBlockEntity_hasCustomOutlineRendering.exists() && this.m_109817_() && Reflector.callBoolean(blockentity1, Reflector.IForgeBlockEntity_hasCustomOutlineRendering, this.f_109461_.f_91074_)) {
                                    flag2 = true;
                                 }

                                 this.f_172946_.m_112267_(blockentity1, f, posestack, (C_4139_)multibuffersource1);
                                 posestack.m_85849_();
                                 ++this.countTileEntitiesRendered;
                              }
                           }
                        }

                        entity = (C_507_)var31.next();
                     } while(!this.shouldRenderEntity(entity, minWorldY, maxWorldY));

                     entityPlayerNotSpectator = entity == this.f_109461_.f_91074_ && !this.f_109461_.f_91074_.R_();
                  } while(!this.f_109463_.m_114397_(entity, frustum, d0, d1, d2) && !entity.m_20367_(this.f_109461_.f_91074_));

                  blockpos = entity.m_20183_();
               } while(!this.f_109465_.d(blockpos.v()) && !this.m_292727_(blockpos));
            } while(entity == activeRenderInfoIn.m_90592_() && !activeRenderInfoIn.m_90594_() && (!(activeRenderInfoIn.m_90592_() instanceof C_524_) || !((C_524_)activeRenderInfoIn.m_90592_()).m_5803_()));
         } while(entity instanceof C_4105_ && activeRenderInfoIn.m_90592_() != entity && !entityPlayerNotSpectator);

         String key = entity.getClass().getName();
         multibuffersource = (List)this.mapEntityLists.get(key);
         if (multibuffersource == null) {
            multibuffersource = new ArrayList();
            this.mapEntityLists.put(key, multibuffersource);
         }

         ((List)multibuffersource).add(entity);
      }
   }

   public void m_109588_(C_3181_ matrixStackIn) {
      if (!matrixStackIn.m_85851_()) {
         throw new IllegalStateException("Pose stack not empty");
      }
   }

   public void m_109517_(C_507_ entityIn, double camX, double camY, double camZ, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn) {
      double d0 = C_188_.m_14139_((double)partialTicks, entityIn.f_19790_, entityIn.m_20185_());
      double d1 = C_188_.m_14139_((double)partialTicks, entityIn.f_19791_, entityIn.m_20186_());
      double d2 = C_188_.m_14139_((double)partialTicks, entityIn.f_19792_, entityIn.m_20189_());
      float f = C_188_.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_());
      this.f_109463_.m_114384_(entityIn, d0 - camX, d1 - camY, d2 - camZ, f, partialTicks, matrixStackIn, bufferIn, this.f_109463_.m_114394_(entityIn, partialTicks));
   }

   public void m_293111_(C_4168_ blockLayerIn, double xIn, double yIn, double zIn, Matrix4f viewIn, Matrix4f projectionIn) {
      RenderSystem.assertOnRenderThread();
      blockLayerIn.m_110185_();
      boolean isShaders = Config.isShaders();
      int lastRegionZ;
      if (blockLayerIn == C_4168_.m_110466_() && !Shaders.isShadowPass) {
         this.f_109461_.m_91307_().m_6180_("translucent_sort");
         double d0 = xIn - this.f_109445_;
         double d1 = yIn - this.f_109446_;
         double d2 = zIn - this.f_109447_;
         if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0) {
            lastRegionZ = C_4710_.m_175552_(xIn);
            int j = C_4710_.m_175552_(yIn);
            int k = C_4710_.m_175552_(zIn);
            boolean flag = lastRegionZ != C_4710_.m_175552_(this.f_109445_) || k != C_4710_.m_175552_(this.f_109447_) || j != C_4710_.m_175552_(this.f_109446_);
            this.f_109445_ = xIn;
            this.f_109446_ = yIn;
            this.f_109447_ = zIn;
            int l = 0;
            this.chunksToResortTransparency.clear();
            ObjectListIterator var22 = this.renderInfosTerrain.iterator();

            label190:
            while(true) {
               C_290152_.C_290138_ sectionrenderdispatcher$rendersection;
               do {
                  do {
                     if (!var22.hasNext()) {
                        break label190;
                     }

                     sectionrenderdispatcher$rendersection = (C_290152_.C_290138_)var22.next();
                  } while(l >= 15);
               } while(!flag && !sectionrenderdispatcher$rendersection.m_292850_(lastRegionZ, j, k));

               if (sectionrenderdispatcher$rendersection.m_293175_().isLayerUsed(blockLayerIn)) {
                  this.chunksToResortTransparency.add(sectionrenderdispatcher$rendersection);
                  ++l;
               }
            }
         }

         this.f_109461_.m_91307_().m_7238_();
      }

      this.f_109461_.m_91307_().m_6180_("filterempty");
      this.f_109461_.m_91307_().m_6523_(() -> {
         return "render_" + String.valueOf(blockLayerIn);
      });
      boolean flag1 = blockLayerIn != C_4168_.m_110466_();
      ObjectListIterator objectlistiterator = this.renderInfosTerrain.listIterator(flag1 ? 0 : this.renderInfosTerrain.size());
      C_141721_ shaderinstance = RenderSystem.getShader();
      shaderinstance.m_340471_(C_3188_.C_141549_.QUADS, viewIn, projectionIn, this.f_109461_.m_91268_());
      shaderinstance.m_173363_();
      C_3172_ uniform = shaderinstance.f_173320_;
      if (isShaders) {
         ShadersRender.preRenderChunkLayer(blockLayerIn);
         Shaders.setModelViewMatrix(viewIn);
         Shaders.setProjectionMatrix(projectionIn);
         Shaders.setTextureMatrix(RenderSystem.getTextureMatrix());
         Shaders.setColorModulator(RenderSystem.getShaderColor());
      }

      boolean smartAnimations = SmartAnimations.isActive();
      if (isShaders && Shaders.activeProgramID > 0) {
         uniform = null;
      }

      if (Config.isRenderRegions() && !blockLayerIn.isNeedsSorting()) {
         int lastRegionX = Integer.MIN_VALUE;
         lastRegionZ = Integer.MIN_VALUE;
         VboRegion lastVboRegion = null;
         Map mapRegionPositions = (Map)this.mapRegionLayers.computeIfAbsent(blockLayerIn, (kx) -> {
            return new LinkedHashMap(16);
         });
         Map lastMapVboRegions = null;
         List lastBuffers = null;

         label159:
         while(true) {
            C_290152_.C_290138_ chunkrenderdispatcher$chunkrender;
            do {
               if (flag1) {
                  if (!objectlistiterator.hasNext()) {
                     break label159;
                  }
               } else if (!objectlistiterator.hasPrevious()) {
                  break label159;
               }

               chunkrenderdispatcher$chunkrender = flag1 ? (C_290152_.C_290138_)objectlistiterator.next() : (C_290152_.C_290138_)objectlistiterator.previous();
            } while(chunkrenderdispatcher$chunkrender.m_293175_().m_294492_(blockLayerIn));

            C_3186_ vertexbuffer = chunkrenderdispatcher$chunkrender.m_294581_(blockLayerIn);
            VboRegion vboRegion = vertexbuffer.getVboRegion();
            if (chunkrenderdispatcher$chunkrender.regionX != lastRegionX || chunkrenderdispatcher$chunkrender.regionZ != lastRegionZ) {
               PairInt pos = PairInt.method_12(chunkrenderdispatcher$chunkrender.regionX, chunkrenderdispatcher$chunkrender.regionZ);
               lastMapVboRegions = (Map)mapRegionPositions.computeIfAbsent(pos, (kx) -> {
                  return new LinkedHashMap(8);
               });
               lastRegionX = chunkrenderdispatcher$chunkrender.regionX;
               lastRegionZ = chunkrenderdispatcher$chunkrender.regionZ;
               lastVboRegion = null;
            }

            if (vboRegion != lastVboRegion) {
               lastBuffers = (List)lastMapVboRegions.computeIfAbsent(vboRegion, (kx) -> {
                  return new ArrayList();
               });
               lastVboRegion = vboRegion;
            }

            lastBuffers.add(vertexbuffer);
            if (smartAnimations) {
               BitSet animatedSprites = chunkrenderdispatcher$chunkrender.m_293175_().getAnimatedSprites(blockLayerIn);
               if (animatedSprites != null) {
                  SmartAnimations.spritesRendered(animatedSprites);
               }
            }
         }

         Iterator var44 = mapRegionPositions.entrySet().iterator();

         label142:
         while(var44.hasNext()) {
            Map.Entry entryPos = (Map.Entry)var44.next();
            PairInt pos = (PairInt)entryPos.getKey();
            Map mapRegions = (Map)entryPos.getValue();
            Iterator var49 = mapRegions.entrySet().iterator();

            while(true) {
               VboRegion reg;
               List listBuffers;
               do {
                  if (!var49.hasNext()) {
                     continue label142;
                  }

                  Map.Entry entryReg = (Map.Entry)var49.next();
                  reg = (VboRegion)entryReg.getKey();
                  listBuffers = (List)entryReg.getValue();
               } while(listBuffers.isEmpty());

               Iterator var30 = listBuffers.iterator();

               while(var30.hasNext()) {
                  C_3186_ vertexBuffer = (C_3186_)var30.next();
                  vertexBuffer.m_166882_();
               }

               this.drawRegion(pos.getLeft(), 0, pos.getRight(), xIn, yIn, zIn, reg, uniform, isShaders);
               listBuffers.clear();
            }
         }
      } else {
         while(true) {
            if (flag1) {
               if (!objectlistiterator.hasNext()) {
                  break;
               }
            } else if (!objectlistiterator.hasPrevious()) {
               break;
            }

            C_290152_.C_290138_ sectionrenderdispatcher$rendersection1 = flag1 ? (C_290152_.C_290138_)objectlistiterator.next() : (C_290152_.C_290138_)objectlistiterator.previous();
            if (!sectionrenderdispatcher$rendersection1.m_293175_().m_294492_(blockLayerIn)) {
               C_3186_ vertexbuffer = sectionrenderdispatcher$rendersection1.m_294581_(blockLayerIn);
               C_4675_ blockpos = sectionrenderdispatcher$rendersection1.m_295500_();
               if (uniform != null) {
                  uniform.m_5889_((float)((double)blockpos.u() - xIn - (double)sectionrenderdispatcher$rendersection1.regionDX), (float)((double)blockpos.v() - yIn - (double)sectionrenderdispatcher$rendersection1.regionDY), (float)((double)blockpos.w() - zIn - (double)sectionrenderdispatcher$rendersection1.regionDZ));
                  uniform.m_85633_();
               }

               if (isShaders) {
                  Shaders.uniform_chunkOffset.setValue((float)((double)blockpos.u() - xIn - (double)sectionrenderdispatcher$rendersection1.regionDX), (float)((double)blockpos.v() - yIn - (double)sectionrenderdispatcher$rendersection1.regionDY), (float)((double)blockpos.w() - zIn - (double)sectionrenderdispatcher$rendersection1.regionDZ));
               }

               if (smartAnimations) {
                  BitSet animatedSprites = sectionrenderdispatcher$rendersection1.m_293175_().getAnimatedSprites(blockLayerIn);
                  if (animatedSprites != null) {
                     SmartAnimations.spritesRendered(animatedSprites);
                  }
               }

               vertexbuffer.m_85921_();
               vertexbuffer.m_166882_();
            }
         }
      }

      if (Config.isMultiTexture()) {
         this.f_109461_.m_91097_().m_174784_(C_4484_.f_118259_);
      }

      if (uniform != null) {
         uniform.m_5889_(0.0F, 0.0F, 0.0F);
      }

      if (isShaders) {
         Shaders.uniform_chunkOffset.setValue(0.0F, 0.0F, 0.0F);
      }

      shaderinstance.m_173362_();
      C_3186_.m_85931_();
      this.f_109461_.m_91307_().m_7238_();
      if (isShaders) {
         ShadersRender.postRenderChunkLayer(blockLayerIn);
      }

      if (Reflector.ForgeHooksClient_dispatchRenderStageRT.exists()) {
         Reflector.ForgeHooksClient_dispatchRenderStageRT.call(blockLayerIn, this, viewIn, projectionIn, this.f_109477_, this.f_109461_.f_91063_.m_109153_(), this.getFrustum());
      }

      blockLayerIn.m_110188_();
   }

   private void drawRegion(int regionX, int regionY, int regionZ, double xIn, double yIn, double zIn, VboRegion vboRegion, C_3172_ uniform, boolean isShaders) {
      if (uniform != null) {
         uniform.m_5889_((float)((double)regionX - xIn), (float)((double)regionY - yIn), (float)((double)regionZ - zIn));
         uniform.m_85633_();
      }

      if (isShaders) {
         Shaders.uniform_chunkOffset.setValue((float)((double)regionX - xIn), (float)((double)regionY - yIn), (float)((double)regionZ - zIn));
      }

      vboRegion.finishDraw();
   }

   private void m_269240_(C_3181_ matrixStackIn, C_4139_ bufferIn, C_3373_ activeRenderInfoIn) {
      if (this.f_109461_.f_291316_ || this.f_109461_.f_291317_) {
         if (Config.isShaders()) {
            Shaders.pushUseProgram(Shaders.ProgramBasic);
         }

         double d0 = activeRenderInfoIn.m_90583_().m_7096_();
         double d1 = activeRenderInfoIn.m_90583_().m_7098_();
         double d2 = activeRenderInfoIn.m_90583_().m_7094_();
         ObjectListIterator var10 = this.f_290776_.iterator();

         while(var10.hasNext()) {
            C_290152_.C_290138_ sectionrenderdispatcher$rendersection = (C_290152_.C_290138_)var10.next();
            C_290263_.C_290165_ sectionocclusiongraph$node = this.f_291822_.m_292796_(sectionrenderdispatcher$rendersection);
            if (sectionocclusiongraph$node != null) {
               C_4675_ blockpos = sectionrenderdispatcher$rendersection.m_295500_();
               matrixStackIn.m_85836_();
               matrixStackIn.m_85837_((double)blockpos.u() - d0, (double)blockpos.v() - d1, (double)blockpos.w() - d2);
               Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
               C_3187_ vertexconsumer3;
               int j1;
               int k;
               int l;
               if (this.f_109461_.f_291316_) {
                  if (Config.isShaders()) {
                     Shaders.beginLines();
                  }

                  vertexconsumer3 = bufferIn.m_6299_(C_4168_.m_110504_());
                  j1 = sectionocclusiongraph$node.f_291195_ == 0 ? 0 : C_188_.m_14169_((float)sectionocclusiongraph$node.f_291195_ / 50.0F, 0.9F, 0.9F);
                  int j = j1 >> 16 & 255;
                  k = j1 >> 8 & 255;
                  l = j1 & 255;

                  for(int i1 = 0; i1 < f_109434_.length; ++i1) {
                     if (sectionocclusiongraph$node.m_295060_(i1)) {
                        C_4687_ direction = f_109434_[i1];
                        vertexconsumer3.m_339083_(matrix4f, 8.0F, 8.0F, 8.0F).m_167129_(j, k, l, 255).m_338525_((float)direction.m_122429_(), (float)direction.m_122430_(), (float)direction.m_122431_());
                        vertexconsumer3.m_339083_(matrix4f, (float)(8 - 16 * direction.m_122429_()), (float)(8 - 16 * direction.m_122430_()), (float)(8 - 16 * direction.m_122431_())).m_167129_(j, k, l, 255).m_338525_((float)direction.m_122429_(), (float)direction.m_122430_(), (float)direction.m_122431_());
                     }
                  }

                  if (Config.isShaders()) {
                     Shaders.endLines();
                  }
               }

               if (this.f_109461_.f_291317_ && !sectionrenderdispatcher$rendersection.m_293175_().m_295467_()) {
                  if (Config.isShaders()) {
                     Shaders.beginLines();
                  }

                  vertexconsumer3 = bufferIn.m_6299_(C_4168_.m_110504_());
                  j1 = 0;
                  C_4687_[] var28 = f_109434_;
                  k = var28.length;

                  for(l = 0; l < k; ++l) {
                     C_4687_ direction2 = var28[l];
                     C_4687_[] var33 = f_109434_;
                     int var22 = var33.length;

                     for(int var23 = 0; var23 < var22; ++var23) {
                        C_4687_ direction1 = var33[var23];
                        boolean flag = sectionrenderdispatcher$rendersection.m_293175_().m_293115_(direction2, direction1);
                        if (!flag) {
                           ++j1;
                           vertexconsumer3.m_339083_(matrix4f, (float)(8 + 8 * direction2.m_122429_()), (float)(8 + 8 * direction2.m_122430_()), (float)(8 + 8 * direction2.m_122431_())).m_167129_(255, 0, 0, 255).m_338525_((float)direction2.m_122429_(), (float)direction2.m_122430_(), (float)direction2.m_122431_());
                           vertexconsumer3.m_339083_(matrix4f, (float)(8 + 8 * direction1.m_122429_()), (float)(8 + 8 * direction1.m_122430_()), (float)(8 + 8 * direction1.m_122431_())).m_167129_(255, 0, 0, 255).m_338525_((float)direction1.m_122429_(), (float)direction1.m_122430_(), (float)direction1.m_122431_());
                        }
                     }
                  }

                  if (Config.isShaders()) {
                     Shaders.endLines();
                  }

                  if (j1 > 0) {
                     C_3187_ vertexconsumer4 = bufferIn.m_6299_(C_4168_.m_269166_());
                     float f = 0.5F;
                     float f1 = 0.2F;
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 15.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 15.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 15.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 15.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 0.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 0.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 0.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 0.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 15.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 15.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 0.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 0.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 0.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 0.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 15.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 15.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 0.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 0.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 15.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 15.5F, 0.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 15.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 15.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 15.5F, 0.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                     vertexconsumer4.m_339083_(matrix4f, 0.5F, 0.5F, 15.5F).m_340057_(0.9F, 0.9F, 0.0F, 0.2F);
                  }
               }

               matrixStackIn.m_85849_();
            }

            if (Config.isShaders()) {
               Shaders.popProgram();
            }
         }
      }

      if (this.f_109442_ != null) {
         if (Config.isShaders()) {
            Shaders.pushUseProgram(Shaders.ProgramBasic);
         }

         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_((float)(this.f_109444_.x - activeRenderInfoIn.m_90583_().f_82479_), (float)(this.f_109444_.y - activeRenderInfoIn.m_90583_().f_82480_), (float)(this.f_109444_.z - activeRenderInfoIn.m_90583_().f_82481_));
         Matrix4f matrix4f1 = matrixStackIn.m_85850_().m_252922_();
         C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_269166_());
         this.m_269092_(vertexconsumer, matrix4f1, 0, 1, 2, 3, 0, 1, 1);
         this.m_269092_(vertexconsumer, matrix4f1, 4, 5, 6, 7, 1, 0, 0);
         this.m_269092_(vertexconsumer, matrix4f1, 0, 1, 5, 4, 1, 1, 0);
         this.m_269092_(vertexconsumer, matrix4f1, 2, 3, 7, 6, 0, 0, 1);
         this.m_269092_(vertexconsumer, matrix4f1, 0, 4, 7, 3, 0, 1, 0);
         this.m_269092_(vertexconsumer, matrix4f1, 1, 5, 6, 2, 1, 0, 1);
         if (Config.isShaders()) {
            Shaders.beginLines();
         }

         C_3187_ vertexconsumer2 = bufferIn.m_6299_(C_4168_.m_110504_());
         this.m_269236_(vertexconsumer2, matrix4f1, 0);
         this.m_269236_(vertexconsumer2, matrix4f1, 1);
         this.m_269236_(vertexconsumer2, matrix4f1, 1);
         this.m_269236_(vertexconsumer2, matrix4f1, 2);
         this.m_269236_(vertexconsumer2, matrix4f1, 2);
         this.m_269236_(vertexconsumer2, matrix4f1, 3);
         this.m_269236_(vertexconsumer2, matrix4f1, 3);
         this.m_269236_(vertexconsumer2, matrix4f1, 0);
         this.m_269236_(vertexconsumer2, matrix4f1, 4);
         this.m_269236_(vertexconsumer2, matrix4f1, 5);
         this.m_269236_(vertexconsumer2, matrix4f1, 5);
         this.m_269236_(vertexconsumer2, matrix4f1, 6);
         this.m_269236_(vertexconsumer2, matrix4f1, 6);
         this.m_269236_(vertexconsumer2, matrix4f1, 7);
         this.m_269236_(vertexconsumer2, matrix4f1, 7);
         this.m_269236_(vertexconsumer2, matrix4f1, 4);
         this.m_269236_(vertexconsumer2, matrix4f1, 0);
         this.m_269236_(vertexconsumer2, matrix4f1, 4);
         this.m_269236_(vertexconsumer2, matrix4f1, 1);
         this.m_269236_(vertexconsumer2, matrix4f1, 5);
         this.m_269236_(vertexconsumer2, matrix4f1, 2);
         this.m_269236_(vertexconsumer2, matrix4f1, 6);
         this.m_269236_(vertexconsumer2, matrix4f1, 3);
         this.m_269236_(vertexconsumer2, matrix4f1, 7);
         if (Config.isShaders()) {
            Shaders.endLines();
         }

         matrixStackIn.m_85849_();
         if (Config.isShaders()) {
            Shaders.popProgram();
         }
      }

   }

   private void m_269236_(C_3187_ bufferIn, Matrix4f matrixIn, int vertex) {
      bufferIn.m_339083_(matrixIn, this.f_109443_[vertex].x(), this.f_109443_[vertex].y(), this.f_109443_[vertex].z()).m_338399_(-16777216).m_338525_(0.0F, 0.0F, -1.0F);
   }

   private void m_269092_(C_3187_ bufferIn, Matrix4f matrixIn, int vertex1, int vertex2, int vertex3, int vertex4, int red, int green, int blue) {
      float f = 0.25F;
      bufferIn.m_339083_(matrixIn, this.f_109443_[vertex1].x(), this.f_109443_[vertex1].y(), this.f_109443_[vertex1].z()).m_340057_((float)red, (float)green, (float)blue, 0.25F);
      bufferIn.m_339083_(matrixIn, this.f_109443_[vertex2].x(), this.f_109443_[vertex2].y(), this.f_109443_[vertex2].z()).m_340057_((float)red, (float)green, (float)blue, 0.25F);
      bufferIn.m_339083_(matrixIn, this.f_109443_[vertex3].x(), this.f_109443_[vertex3].y(), this.f_109443_[vertex3].z()).m_340057_((float)red, (float)green, (float)blue, 0.25F);
      bufferIn.m_339083_(matrixIn, this.f_109443_[vertex4].x(), this.f_109443_[vertex4].y(), this.f_109443_[vertex4].z()).m_340057_((float)red, (float)green, (float)blue, 0.25F);
   }

   public void m_173018_() {
      this.f_109441_ = true;
   }

   public void m_173019_() {
      this.f_109442_ = null;
   }

   public void m_109823_() {
      if (this.f_109465_.m_304826_().m_305915_()) {
         ++this.f_109477_;
      }

      if (this.f_109477_ % 20 == 0) {
         Iterator iterator = this.f_109408_.values().iterator();

         while(iterator.hasNext()) {
            C_5421_ blockdestructionprogress = (C_5421_)iterator.next();
            int i = blockdestructionprogress.m_139991_();
            if (this.f_109477_ - i > 400) {
               iterator.remove();
               this.m_109765_(blockdestructionprogress);
            }
         }
      }

      if (Config.isRenderRegions() && this.f_109477_ % 20 == 0) {
         this.mapRegionLayers.clear();
      }

   }

   private void m_109765_(C_5421_ progressIn) {
      long i = progressIn.m_139985_().m_121878_();
      Set set = (Set)this.f_109409_.get(i);
      set.remove(progressIn);
      if (set.isEmpty()) {
         this.f_109409_.remove(i);
      }

   }

   private void m_109780_(C_3181_ matrixStackIn) {
      if (Config.isSkyEnabled()) {
         RenderSystem.enableBlend();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(C_4124_::m_172820_);
         RenderSystem.setShaderTexture(0, f_109457_);
         C_3185_ tesselator = C_3185_.m_85913_();

         for(int i = 0; i < 6; ++i) {
            matrixStackIn.m_85836_();
            if (i == 1) {
               matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(90.0F));
            }

            if (i == 2) {
               matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-90.0F));
            }

            if (i == 3) {
               matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(180.0F));
            }

            if (i == 4) {
               matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(90.0F));
            }

            if (i == 5) {
               matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(-90.0F));
            }

            Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
            C_3173_ bufferbuilder = tesselator.m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85819_);
            int argb = -14145496;
            if (Config.isCustomColors()) {
               int r = C_175_.m_13665_(argb);
               int g = C_175_.m_13667_(argb);
               int b = C_175_.m_13669_(argb);
               C_3046_ vector3d = new C_3046_((double)r / 255.0, (double)g / 255.0, (double)b / 255.0);
               vector3d = CustomColors.getWorldSkyColor(vector3d, this.f_109465_, this.f_109461_.m_91288_(), 0.0F);
               r = (int)(vector3d.f_82479_ * 255.0);
               g = (int)(vector3d.f_82480_ * 255.0);
               b = (int)(vector3d.f_82481_ * 255.0);
               argb = C_175_.m_13660_(255, r, g, b);
            }

            bufferbuilder.m_339083_(matrix4f, -100.0F, -100.0F, -100.0F).m_167083_(0.0F, 0.0F).m_338399_(argb);
            bufferbuilder.m_339083_(matrix4f, -100.0F, -100.0F, 100.0F).m_167083_(0.0F, 16.0F).m_338399_(argb);
            bufferbuilder.m_339083_(matrix4f, 100.0F, -100.0F, 100.0F).m_167083_(16.0F, 16.0F).m_338399_(argb);
            bufferbuilder.m_339083_(matrix4f, 100.0F, -100.0F, -100.0F).m_167083_(16.0F, 0.0F).m_338399_(argb);
            C_3177_.m_231202_(bufferbuilder.m_339905_());
            matrixStackIn.m_85849_();
         }

         CustomSky.renderSky(this.f_109465_, matrixStackIn, 0.0F);
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
      }
   }

   public void m_202423_(Matrix4f matrixStackIn, Matrix4f projectionIn, float partialTicks, C_3373_ cameraIn, boolean skipSkyIn, Runnable setupFog) {
      setupFog.run();
      if (!Reflector.IForgeDimensionSpecialEffects_renderSky.exists() || !Reflector.callBoolean(this.f_109465_.m_104583_(), Reflector.IForgeDimensionSpecialEffects_renderSky, this.f_109465_, this.f_109477_, partialTicks, matrixStackIn, cameraIn, projectionIn, skipSkyIn, setupFog)) {
         if (!skipSkyIn) {
            C_141436_ fogtype = cameraIn.m_167685_();
            if (fogtype != C_141436_.POWDER_SNOW && fogtype != C_141436_.LAVA && !this.m_234310_(cameraIn)) {
               C_3181_ posestack = new C_3181_();
               posestack.m_318714_(matrixStackIn);
               if (this.f_109461_.f_91073_.m_104583_().m_108883_() == C_4116_.END) {
                  this.m_109780_(posestack);
               } else if (this.f_109461_.f_91073_.m_104583_().m_108883_() == C_4116_.NORMAL) {
                  boolean isShaders = Config.isShaders();
                  if (isShaders) {
                     Shaders.disableTexture2D();
                  }

                  C_3046_ vec3 = this.f_109465_.m_171660_(this.f_109461_.f_91063_.m_109153_().m_90583_(), partialTicks);
                  vec3 = CustomColors.getSkyColor(vec3, this.f_109461_.f_91073_, this.f_109461_.m_91288_().m_20185_(), this.f_109461_.m_91288_().m_20186_() + 1.0, this.f_109461_.m_91288_().m_20189_());
                  if (isShaders) {
                     Shaders.setSkyColor(vec3);
                     RenderSystem.setColorToAttribute(true);
                  }

                  float f = (float)vec3.f_82479_;
                  float f1 = (float)vec3.f_82480_;
                  float f2 = (float)vec3.f_82481_;
                  C_4122_.m_109036_();
                  C_3185_ tesselator = C_3185_.m_85913_();
                  RenderSystem.depthMask(false);
                  if (isShaders) {
                     Shaders.enableFog();
                  }

                  RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                  if (isShaders) {
                     Shaders.preSkyList(posestack);
                  }

                  C_141721_ shaderinstance = RenderSystem.getShader();
                  if (Config.isSkyEnabled()) {
                     this.f_109472_.m_85921_();
                     this.f_109472_.m_253207_(posestack.m_85850_().m_252922_(), projectionIn, shaderinstance);
                     C_3186_.m_85931_();
                  }

                  if (isShaders) {
                     Shaders.disableFog();
                  }

                  RenderSystem.enableBlend();
                  float[] afloat = this.f_109465_.m_104583_().m_7518_(this.f_109465_.f(partialTicks), partialTicks);
                  float f11;
                  float f12;
                  float f10;
                  boolean voidRendered;
                  float f15;
                  float f16;
                  if (afloat != null && Config.isSunMoonEnabled()) {
                     RenderSystem.setShader(C_4124_::m_172811_);
                     if (isShaders) {
                        Shaders.disableTexture2D();
                     }

                     if (isShaders) {
                        Shaders.setRenderStage(RenderStage.SUNSET);
                     }

                     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                     posestack.m_85836_();
                     posestack.m_252781_(C_252363_.f_252529_.m_252977_(90.0F));
                     f11 = C_188_.m_14031_(this.f_109465_.m_46490_(partialTicks)) < 0.0F ? 180.0F : 0.0F;
                     posestack.m_252781_(C_252363_.f_252403_.m_252977_(f11));
                     posestack.m_252781_(C_252363_.f_252403_.m_252977_(90.0F));
                     float f4 = afloat[0];
                     f12 = afloat[1];
                     f10 = afloat[2];
                     Matrix4f matrix4f = posestack.m_85850_().m_252922_();
                     C_3173_ bufferbuilder = tesselator.m_339075_(C_3188_.C_141549_.TRIANGLE_FAN, C_3179_.f_85815_);
                     bufferbuilder.m_339083_(matrix4f, 0.0F, 100.0F, 0.0F).m_340057_(f4, f12, f10, afloat[3]);
                     voidRendered = true;

                     for(int j = 0; j <= 16; ++j) {
                        f15 = (float)j * 6.2831855F / 16.0F;
                        f16 = C_188_.m_14031_(f15);
                        float f9 = C_188_.m_14089_(f15);
                        bufferbuilder.m_339083_(matrix4f, f16 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).m_340057_(afloat[0], afloat[1], afloat[2], 0.0F);
                     }

                     C_3177_.m_231202_(bufferbuilder.m_339905_());
                     posestack.m_85849_();
                  }

                  if (isShaders) {
                     Shaders.enableTexture2D();
                  }

                  RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                  posestack.m_85836_();
                  f11 = 1.0F - this.f_109465_.m_46722_(partialTicks);
                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
                  posestack.m_252781_(C_252363_.f_252436_.m_252977_(-90.0F));
                  CustomSky.renderSky(this.f_109465_, posestack, partialTicks);
                  if (isShaders) {
                     Shaders.preCelestialRotate(posestack);
                  }

                  posestack.m_252781_(C_252363_.f_252529_.m_252977_(this.f_109465_.f(partialTicks) * 360.0F));
                  if (isShaders) {
                     Shaders.postCelestialRotate(posestack);
                  }

                  Matrix4f matrix4f1 = posestack.m_85850_().m_252922_();
                  f12 = 30.0F;
                  RenderSystem.setShader(C_4124_::m_172817_);
                  if (Config.isSunTexture()) {
                     if (isShaders) {
                        Shaders.setRenderStage(RenderStage.SUN);
                     }

                     RenderSystem.setShaderTexture(0, f_109455_);
                     C_3173_ bufferbuilder1 = tesselator.m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85817_);
                     bufferbuilder1.m_339083_(matrix4f1, -f12, 100.0F, -f12).m_167083_(0.0F, 0.0F);
                     bufferbuilder1.m_339083_(matrix4f1, f12, 100.0F, -f12).m_167083_(1.0F, 0.0F);
                     bufferbuilder1.m_339083_(matrix4f1, f12, 100.0F, f12).m_167083_(1.0F, 1.0F);
                     bufferbuilder1.m_339083_(matrix4f1, -f12, 100.0F, f12).m_167083_(0.0F, 1.0F);
                     C_3177_.m_231202_(bufferbuilder1.m_339905_());
                  }

                  f12 = 20.0F;
                  if (Config.isMoonTexture()) {
                     if (isShaders) {
                        Shaders.setRenderStage(RenderStage.MOON);
                     }

                     RenderSystem.setShaderTexture(0, f_109454_);
                     int k = this.f_109465_.ar();
                     int l = k % 4;
                     int i1 = k / 4 % 2;
                     float f13 = (float)(l + 0) / 4.0F;
                     float f14 = (float)(i1 + 0) / 2.0F;
                     f15 = (float)(l + 1) / 4.0F;
                     f16 = (float)(i1 + 1) / 2.0F;
                     C_3173_ bufferbuilder1 = tesselator.m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85817_);
                     bufferbuilder1.m_339083_(matrix4f1, -f12, -100.0F, f12).m_167083_(f15, f16);
                     bufferbuilder1.m_339083_(matrix4f1, f12, -100.0F, f12).m_167083_(f13, f16);
                     bufferbuilder1.m_339083_(matrix4f1, f12, -100.0F, -f12).m_167083_(f13, f14);
                     bufferbuilder1.m_339083_(matrix4f1, -f12, -100.0F, -f12).m_167083_(f15, f14);
                     C_3177_.m_231202_(bufferbuilder1.m_339905_());
                  }

                  if (isShaders) {
                     Shaders.disableTexture2D();
                  }

                  f10 = this.f_109465_.m_104811_(partialTicks) * f11;
                  if (f10 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.f_109465_)) {
                     if (isShaders) {
                        Shaders.setRenderStage(RenderStage.STARS);
                     }

                     RenderSystem.setShaderColor(f10, f10, f10, f10);
                     C_4122_.m_109017_();
                     this.f_109471_.m_85921_();
                     this.f_109471_.m_253207_(posestack.m_85850_().m_252922_(), projectionIn, C_4124_.m_172808_());
                     C_3186_.m_85931_();
                     setupFog.run();
                  }

                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                  RenderSystem.disableBlend();
                  RenderSystem.defaultBlendFunc();
                  if (isShaders) {
                     Shaders.enableFog();
                  }

                  posestack.m_85849_();
                  if (isShaders) {
                     Shaders.disableTexture2D();
                  }

                  RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                  double d0 = this.f_109461_.f_91074_.k(partialTicks).f_82480_ - this.f_109465_.m_6106_().m_171687_(this.f_109465_);
                  voidRendered = false;
                  if (d0 < 0.0) {
                     if (isShaders) {
                        Shaders.setRenderStage(RenderStage.VOID);
                     }

                     posestack.m_85836_();
                     posestack.m_252880_(0.0F, 12.0F, 0.0F);
                     this.f_109473_.m_85921_();
                     this.f_109473_.m_253207_(posestack.m_85850_().m_252922_(), projectionIn, shaderinstance);
                     C_3186_.m_85931_();
                     posestack.m_85849_();
                     voidRendered = true;
                  }

                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                  if (isShaders) {
                     RenderSystem.setColorToAttribute(false);
                  }

                  RenderSystem.depthMask(true);
               }
            }
         }

      }
   }

   private boolean m_234310_(C_3373_ cameraIn) {
      C_507_ var3 = cameraIn.m_90592_();
      boolean var10000;
      if (var3 instanceof C_524_ livingentity) {
         var10000 = livingentity.m_21023_(C_500_.f_19610_) || livingentity.m_21023_(C_500_.f_216964_);
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public void m_253054_(C_3181_ matrixStackIn, Matrix4f viewIn, Matrix4f projectionIn, float partialTicks, double viewEntityX, double viewEntityY, double viewEntityZ) {
      if (!Config.isCloudsOff()) {
         if (!Reflector.IForgeDimensionSpecialEffects_renderClouds.exists() || !Reflector.callBoolean(this.f_109465_.m_104583_(), Reflector.IForgeDimensionSpecialEffects_renderClouds, this.f_109465_, this.f_109477_, partialTicks, matrixStackIn, viewEntityX, viewEntityY, viewEntityZ, projectionIn)) {
            float f = this.f_109465_.m_104583_().m_108871_();
            if (!Float.isNaN(f)) {
               if (Config.isShaders()) {
                  Shaders.beginClouds();
               }

               float f1 = 12.0F;
               float f2 = 4.0F;
               double d0 = 2.0E-4;
               double d1 = (double)(((float)this.f_109477_ + partialTicks) * 0.03F);
               double d2 = (viewEntityX + d1) / 12.0;
               double d3 = (double)(f - (float)viewEntityY + 0.33F);
               d3 += this.f_109461_.f_91066_.ofCloudsHeight * 128.0;
               double d4 = viewEntityZ / 12.0 + 0.33000001311302185;
               d2 -= (double)(C_188_.m_14107_(d2 / 2048.0) * 2048);
               d4 -= (double)(C_188_.m_14107_(d4 / 2048.0) * 2048);
               float f3 = (float)(d2 - (double)C_188_.m_14107_(d2));
               float f4 = (float)(d3 / 4.0 - (double)C_188_.m_14107_(d3 / 4.0)) * 4.0F;
               float f5 = (float)(d4 - (double)C_188_.m_14107_(d4));
               C_3046_ vec3 = this.f_109465_.m_104808_(partialTicks);
               int i = (int)Math.floor(d2);
               int j = (int)Math.floor(d3 / 4.0);
               int k = (int)Math.floor(d4);
               if (i != this.f_109430_ || j != this.f_109431_ || k != this.f_109432_ || this.f_109461_.f_91066_.m_92174_() != this.f_109435_ || this.f_109433_.m_82557_(vec3) > 2.0E-4) {
                  this.f_109430_ = i;
                  this.f_109431_ = j;
                  this.f_109432_ = k;
                  this.f_109433_ = vec3;
                  this.f_109435_ = this.f_109461_.f_91066_.m_92174_();
                  this.f_109474_ = true;
               }

               if (this.f_109474_) {
                  this.f_109474_ = false;
                  if (this.f_109475_ != null) {
                     this.f_109475_.close();
                  }

                  this.f_109475_ = new C_3186_(C_3186_.C_285533_.STATIC);
                  this.f_109475_.m_85921_();
                  this.f_109475_.m_231221_(this.m_234261_(C_3185_.m_85913_(), d2, d3, d4, vec3));
                  C_3186_.m_85931_();
               }

               C_4122_.m_109036_();
               matrixStackIn.m_85836_();
               matrixStackIn.m_318714_(viewIn);
               matrixStackIn.m_85841_(12.0F, 1.0F, 12.0F);
               matrixStackIn.m_252880_(-f3, f4, -f5);
               if (this.f_109475_ != null) {
                  this.f_109475_.m_85921_();
                  int l = this.f_109435_ == C_3376_.FANCY ? 0 : 1;

                  for(int i1 = l; i1 < 2; ++i1) {
                     C_4168_ rendertype = i1 == 0 ? C_4168_.m_319097_() : C_4168_.m_325090_();
                     rendertype.m_110185_();
                     C_141721_ shaderinstance = RenderSystem.getShader();
                     this.f_109475_.m_253207_(matrixStackIn.m_85850_().m_252922_(), projectionIn, shaderinstance);
                     rendertype.m_110188_();
                  }

                  C_3186_.m_85931_();
               }

               matrixStackIn.m_85849_();
               if (Config.isShaders()) {
                  Shaders.endClouds();
               }
            }

         }
      }
   }

   private C_336471_ m_234261_(C_3185_ bufferIn, double cloudsX, double cloudsY, double cloudsZ, C_3046_ cloudsColor) {
      float f = 4.0F;
      float f1 = 0.00390625F;
      int i = true;
      int j = true;
      float f2 = 9.765625E-4F;
      float f3 = (float)C_188_.m_14107_(cloudsX) * 0.00390625F;
      float f4 = (float)C_188_.m_14107_(cloudsZ) * 0.00390625F;
      float f5 = (float)cloudsColor.f_82479_;
      float f6 = (float)cloudsColor.f_82480_;
      float f7 = (float)cloudsColor.f_82481_;
      float f8 = f5 * 0.9F;
      float f9 = f6 * 0.9F;
      float f10 = f7 * 0.9F;
      float f11 = f5 * 0.7F;
      float f12 = f6 * 0.7F;
      float f13 = f7 * 0.7F;
      float f14 = f5 * 0.8F;
      float f15 = f6 * 0.8F;
      float f16 = f7 * 0.8F;
      C_3173_ bufferbuilder = bufferIn.m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85822_);
      float f17 = (float)Math.floor(cloudsY / 4.0) * 4.0F;
      if (Config.isCloudsFancy()) {
         for(int k = -3; k <= 4; ++k) {
            for(int l = -3; l <= 4; ++l) {
               float f18 = (float)(k * 8);
               float f19 = (float)(l * 8);
               if (f17 > -5.0F) {
                  bufferbuilder.m_167146_(f18 + 0.0F, f17 + 0.0F, f19 + 8.0F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f11, f12, f13, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
                  bufferbuilder.m_167146_(f18 + 8.0F, f17 + 0.0F, f19 + 8.0F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f11, f12, f13, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
                  bufferbuilder.m_167146_(f18 + 8.0F, f17 + 0.0F, f19 + 0.0F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f11, f12, f13, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
                  bufferbuilder.m_167146_(f18 + 0.0F, f17 + 0.0F, f19 + 0.0F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f11, f12, f13, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
               }

               if (f17 <= 5.0F) {
                  bufferbuilder.m_167146_(f18 + 0.0F, f17 + 4.0F - 9.765625E-4F, f19 + 8.0F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, 1.0F, 0.0F);
                  bufferbuilder.m_167146_(f18 + 8.0F, f17 + 4.0F - 9.765625E-4F, f19 + 8.0F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, 1.0F, 0.0F);
                  bufferbuilder.m_167146_(f18 + 8.0F, f17 + 4.0F - 9.765625E-4F, f19 + 0.0F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, 1.0F, 0.0F);
                  bufferbuilder.m_167146_(f18 + 0.0F, f17 + 4.0F - 9.765625E-4F, f19 + 0.0F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, 1.0F, 0.0F);
               }

               int l2;
               if (k > -1) {
                  for(l2 = 0; l2 < 8; ++l2) {
                     bufferbuilder.m_167146_(f18 + (float)l2 + 0.0F, f17 + 0.0F, f19 + 8.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(-1.0F, 0.0F, 0.0F);
                     bufferbuilder.m_167146_(f18 + (float)l2 + 0.0F, f17 + 4.0F, f19 + 8.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(-1.0F, 0.0F, 0.0F);
                     bufferbuilder.m_167146_(f18 + (float)l2 + 0.0F, f17 + 4.0F, f19 + 0.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(-1.0F, 0.0F, 0.0F);
                     bufferbuilder.m_167146_(f18 + (float)l2 + 0.0F, f17 + 0.0F, f19 + 0.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(-1.0F, 0.0F, 0.0F);
                  }
               }

               if (k <= 1) {
                  for(l2 = 0; l2 < 8; ++l2) {
                     bufferbuilder.m_167146_(f18 + (float)l2 + 1.0F - 9.765625E-4F, f17 + 0.0F, f19 + 8.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(1.0F, 0.0F, 0.0F);
                     bufferbuilder.m_167146_(f18 + (float)l2 + 1.0F - 9.765625E-4F, f17 + 4.0F, f19 + 8.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(1.0F, 0.0F, 0.0F);
                     bufferbuilder.m_167146_(f18 + (float)l2 + 1.0F - 9.765625E-4F, f17 + 4.0F, f19 + 0.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(1.0F, 0.0F, 0.0F);
                     bufferbuilder.m_167146_(f18 + (float)l2 + 1.0F - 9.765625E-4F, f17 + 0.0F, f19 + 0.0F).m_167083_((f18 + (float)l2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_340057_(f8, f9, f10, 0.8F).m_338525_(1.0F, 0.0F, 0.0F);
                  }
               }

               if (l > -1) {
                  for(l2 = 0; l2 < 8; ++l2) {
                     bufferbuilder.m_167146_(f18 + 0.0F, f17 + 4.0F, f19 + (float)l2 + 0.0F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, -1.0F);
                     bufferbuilder.m_167146_(f18 + 8.0F, f17 + 4.0F, f19 + (float)l2 + 0.0F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, -1.0F);
                     bufferbuilder.m_167146_(f18 + 8.0F, f17 + 0.0F, f19 + (float)l2 + 0.0F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, -1.0F);
                     bufferbuilder.m_167146_(f18 + 0.0F, f17 + 0.0F, f19 + (float)l2 + 0.0F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, -1.0F);
                  }
               }

               if (l <= 1) {
                  for(l2 = 0; l2 < 8; ++l2) {
                     bufferbuilder.m_167146_(f18 + 0.0F, f17 + 4.0F, f19 + (float)l2 + 1.0F - 9.765625E-4F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, 1.0F);
                     bufferbuilder.m_167146_(f18 + 8.0F, f17 + 4.0F, f19 + (float)l2 + 1.0F - 9.765625E-4F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, 1.0F);
                     bufferbuilder.m_167146_(f18 + 8.0F, f17 + 0.0F, f19 + (float)l2 + 1.0F - 9.765625E-4F).m_167083_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, 1.0F);
                     bufferbuilder.m_167146_(f18 + 0.0F, f17 + 0.0F, f19 + (float)l2 + 1.0F - 9.765625E-4F).m_167083_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_340057_(f14, f15, f16, 0.8F).m_338525_(0.0F, 0.0F, 1.0F);
                  }
               }
            }
         }
      } else {
         int j1 = true;
         int k1 = true;

         for(int l1 = -32; l1 < 32; l1 += 32) {
            for(int i2 = -32; i2 < 32; i2 += 32) {
               bufferbuilder.m_167146_((float)(l1 + 0), f17, (float)(i2 + 32)).m_167083_((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
               bufferbuilder.m_167146_((float)(l1 + 32), f17, (float)(i2 + 32)).m_167083_((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
               bufferbuilder.m_167146_((float)(l1 + 32), f17, (float)(i2 + 0)).m_167083_((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
               bufferbuilder.m_167146_((float)(l1 + 0), f17, (float)(i2 + 0)).m_167083_((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).m_340057_(f5, f6, f7, 0.8F).m_338525_(0.0F, -1.0F, 0.0F);
            }
         }
      }

      return bufferbuilder.m_339905_();
   }

   private void m_194370_(C_3373_ camera) {
      this.f_109461_.m_91307_().m_6180_("populate_sections_to_compile");
      C_2681_ levellightengine = this.f_109465_.m_5518_();
      C_200009_ renderregioncache = new C_200009_();
      C_4675_ blockpos = camera.m_90588_();
      List list = Lists.newArrayList();
      Lagometer.timerChunkUpdate.start();
      ObjectListIterator var6 = this.f_290776_.iterator();

      while(true) {
         while(true) {
            C_290152_.C_290138_ sectionrenderdispatcher$rendersection;
            C_4710_ sectionpos;
            do {
               do {
                  if (!var6.hasNext()) {
                     Lagometer.timerChunkUpdate.end();
                     Lagometer.timerChunkUpload.start();
                     this.f_109461_.m_91307_().m_6182_("upload");
                     this.f_290446_.m_295287_();
                     this.f_109469_.clearUnusedVbos();
                     this.f_109461_.m_91307_().m_6182_("schedule_async_compile");
                     if (this.chunksToResortTransparency.size() > 0) {
                        Iterator itTransparency = this.chunksToResortTransparency.iterator();
                        if (itTransparency.hasNext()) {
                           sectionrenderdispatcher$rendersection = (C_290152_.C_290138_)itTransparency.next();
                           if (this.f_290446_.updateTransparencyLater(sectionrenderdispatcher$rendersection)) {
                              itTransparency.remove();
                           }
                        }
                     }

                     double weightTotal = 0.0;
                     int updatesPerFrame = Config.getUpdatesPerFrame();
                     this.countChunksToUpdate = list.size();
                     Iterator var18 = list.iterator();

                     while(var18.hasNext()) {
                        C_290152_.C_290138_ sectionrenderdispatcher$rendersection1 = (C_290152_.C_290138_)var18.next();
                        boolean empty = sectionrenderdispatcher$rendersection1.isChunkRegionEmpty();
                        boolean backgroundPriority = sectionrenderdispatcher$rendersection1.needsBackgroundPriorityUpdate();
                        if (sectionrenderdispatcher$rendersection1.m_295586_()) {
                           sectionrenderdispatcher$rendersection1.m_294845_(this.f_290446_, renderregioncache);
                           sectionrenderdispatcher$rendersection1.m_294599_();
                           if (!empty && !backgroundPriority) {
                              double weight = 2.0 * RenderChunkUtils.getRelativeBufferSize(sectionrenderdispatcher$rendersection1);
                              weightTotal += weight;
                              if (weightTotal > (double)updatesPerFrame) {
                                 break;
                              }
                           }
                        }
                     }

                     Lagometer.timerChunkUpload.end();
                     this.f_109461_.m_91307_().m_7238_();
                     return;
                  }

                  sectionrenderdispatcher$rendersection = (C_290152_.C_290138_)var6.next();
                  sectionpos = sectionrenderdispatcher$rendersection.getSectionPosition();
               } while(!sectionrenderdispatcher$rendersection.m_295586_());
            } while(!levellightengine.m_284439_(sectionpos));

            if (sectionrenderdispatcher$rendersection.needsBackgroundPriorityUpdate()) {
               list.add(sectionrenderdispatcher$rendersection);
            } else {
               boolean flag = false;
               if (this.f_109461_.f_91066_.m_232080_().m_231551_() == C_183059_.NEARBY) {
                  C_4675_ blockpos1 = sectionrenderdispatcher$rendersection.m_295500_().m_7918_(8, 8, 8);
                  flag = blockpos1.j(blockpos) < 768.0 || sectionrenderdispatcher$rendersection.m_295878_();
               } else if (this.f_109461_.f_91066_.m_232080_().m_231551_() == C_183059_.PLAYER_AFFECTED) {
                  flag = sectionrenderdispatcher$rendersection.m_295878_();
               }

               if (flag) {
                  this.f_109461_.m_91307_().m_6180_("build_near_sync");
                  this.f_290446_.m_295202_(sectionrenderdispatcher$rendersection, renderregioncache);
                  sectionrenderdispatcher$rendersection.m_294599_();
                  this.f_109461_.m_91307_().m_7238_();
               } else {
                  list.add(sectionrenderdispatcher$rendersection);
               }
            }
         }
      }
   }

   private void m_173012_(C_3373_ activeRenderInfoIn) {
      C_2110_ worldborder = this.f_109465_.m_6857_();
      double d0 = (double)(this.f_109461_.f_91066_.m_193772_() * 16);
      if (!(activeRenderInfoIn.m_90583_().f_82479_ < worldborder.m_61957_() - d0) || !(activeRenderInfoIn.m_90583_().f_82479_ > worldborder.m_61955_() + d0) || !(activeRenderInfoIn.m_90583_().f_82481_ < worldborder.m_61958_() - d0) || !(activeRenderInfoIn.m_90583_().f_82481_ > worldborder.m_61956_() + d0)) {
         if (Config.isShaders()) {
            Shaders.pushProgram();
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.WORLD_BORDER);
         }

         double d1 = 1.0 - worldborder.m_61941_(activeRenderInfoIn.m_90583_().f_82479_, activeRenderInfoIn.m_90583_().f_82481_) / d0;
         d1 = Math.pow(d1, 4.0);
         d1 = C_188_.m_14008_(d1, 0.0, 1.0);
         double d2 = activeRenderInfoIn.m_90583_().f_82479_;
         double d3 = activeRenderInfoIn.m_90583_().f_82481_;
         double d4 = (double)this.f_109461_.f_91063_.m_172790_();
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         RenderSystem.setShaderTexture(0, f_109458_);
         RenderSystem.depthMask(C_3391_.m_91085_());
         int i = worldborder.m_61954_().m_61901_();
         float f = (float)(i >> 16 & 255) / 255.0F;
         float f1 = (float)(i >> 8 & 255) / 255.0F;
         float f2 = (float)(i & 255) / 255.0F;
         RenderSystem.setShaderColor(f, f1, f2, (float)d1);
         RenderSystem.setShader(C_4124_::m_172817_);
         RenderSystem.polygonOffset(-3.0F, -3.0F);
         RenderSystem.enablePolygonOffset();
         RenderSystem.disableCull();
         float f3 = (float)(C_5322_.m_137550_() % 3000L) / 3000.0F;
         float f4 = (float)(-C_188_.m_14185_(activeRenderInfoIn.m_90583_().f_82480_ * 0.5));
         float f5 = f4 + (float)d4;
         C_3173_ bufferbuilder = C_3185_.m_85913_().m_339075_(C_3188_.C_141549_.QUADS, C_3179_.f_85817_);
         double d5 = Math.max((double)C_188_.m_14107_(d3 - d0), worldborder.m_61956_());
         double d6 = Math.min((double)C_188_.m_14165_(d3 + d0), worldborder.m_61958_());
         float f6 = (float)(C_188_.m_14107_(d5) & 1) * 0.5F;
         float f11;
         double d11;
         double d14;
         float f14;
         if (d2 > worldborder.m_61957_() - d0) {
            f11 = f6;

            for(d11 = d5; d11 < d6; f11 += 0.5F) {
               d14 = Math.min(1.0, d6 - d11);
               f14 = (float)d14 * 0.5F;
               bufferbuilder.m_167146_((float)(worldborder.m_61957_() - d2), (float)(-d4), (float)(d11 - d3)).m_167083_(f3 - f11, f3 + f5);
               bufferbuilder.m_167146_((float)(worldborder.m_61957_() - d2), (float)(-d4), (float)(d11 + d14 - d3)).m_167083_(f3 - (f14 + f11), f3 + f5);
               bufferbuilder.m_167146_((float)(worldborder.m_61957_() - d2), (float)d4, (float)(d11 + d14 - d3)).m_167083_(f3 - (f14 + f11), f3 + f4);
               bufferbuilder.m_167146_((float)(worldborder.m_61957_() - d2), (float)d4, (float)(d11 - d3)).m_167083_(f3 - f11, f3 + f4);
               ++d11;
            }
         }

         if (d2 < worldborder.m_61955_() + d0) {
            f11 = f6;

            for(d11 = d5; d11 < d6; f11 += 0.5F) {
               d14 = Math.min(1.0, d6 - d11);
               f14 = (float)d14 * 0.5F;
               bufferbuilder.m_167146_((float)(worldborder.m_61955_() - d2), (float)(-d4), (float)(d11 - d3)).m_167083_(f3 + f11, f3 + f5);
               bufferbuilder.m_167146_((float)(worldborder.m_61955_() - d2), (float)(-d4), (float)(d11 + d14 - d3)).m_167083_(f3 + f14 + f11, f3 + f5);
               bufferbuilder.m_167146_((float)(worldborder.m_61955_() - d2), (float)d4, (float)(d11 + d14 - d3)).m_167083_(f3 + f14 + f11, f3 + f4);
               bufferbuilder.m_167146_((float)(worldborder.m_61955_() - d2), (float)d4, (float)(d11 - d3)).m_167083_(f3 + f11, f3 + f4);
               ++d11;
            }
         }

         d5 = Math.max((double)C_188_.m_14107_(d2 - d0), worldborder.m_61955_());
         d6 = Math.min((double)C_188_.m_14165_(d2 + d0), worldborder.m_61957_());
         f6 = (float)(C_188_.m_14107_(d5) & 1) * 0.5F;
         if (d3 > worldborder.m_61958_() - d0) {
            f11 = f6;

            for(d11 = d5; d11 < d6; f11 += 0.5F) {
               d14 = Math.min(1.0, d6 - d11);
               f14 = (float)d14 * 0.5F;
               bufferbuilder.m_167146_((float)(d11 - d2), (float)(-d4), (float)(worldborder.m_61958_() - d3)).m_167083_(f3 + f11, f3 + f5);
               bufferbuilder.m_167146_((float)(d11 + d14 - d2), (float)(-d4), (float)(worldborder.m_61958_() - d3)).m_167083_(f3 + f14 + f11, f3 + f5);
               bufferbuilder.m_167146_((float)(d11 + d14 - d2), (float)d4, (float)(worldborder.m_61958_() - d3)).m_167083_(f3 + f14 + f11, f3 + f4);
               bufferbuilder.m_167146_((float)(d11 - d2), (float)d4, (float)(worldborder.m_61958_() - d3)).m_167083_(f3 + f11, f3 + f4);
               ++d11;
            }
         }

         if (d3 < worldborder.m_61956_() + d0) {
            f11 = f6;

            for(d11 = d5; d11 < d6; f11 += 0.5F) {
               d14 = Math.min(1.0, d6 - d11);
               f14 = (float)d14 * 0.5F;
               bufferbuilder.m_167146_((float)(d11 - d2), (float)(-d4), (float)(worldborder.m_61956_() - d3)).m_167083_(f3 - f11, f3 + f5);
               bufferbuilder.m_167146_((float)(d11 + d14 - d2), (float)(-d4), (float)(worldborder.m_61956_() - d3)).m_167083_(f3 - (f14 + f11), f3 + f5);
               bufferbuilder.m_167146_((float)(d11 + d14 - d2), (float)d4, (float)(worldborder.m_61956_() - d3)).m_167083_(f3 - (f14 + f11), f3 + f4);
               bufferbuilder.m_167146_((float)(d11 - d2), (float)d4, (float)(worldborder.m_61956_() - d3)).m_167083_(f3 - f11, f3 + f4);
               ++d11;
            }
         }

         C_336471_ meshdata = bufferbuilder.m_339970_();
         if (meshdata != null) {
            C_3177_.m_231202_(meshdata);
         }

         RenderSystem.enableCull();
         RenderSystem.polygonOffset(0.0F, 0.0F);
         RenderSystem.disablePolygonOffset();
         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.depthMask(true);
         if (Config.isShaders()) {
            Shaders.popProgram();
            Shaders.setRenderStage(RenderStage.NONE);
         }
      }

   }

   private void m_109637_(C_3181_ matrixStackIn, C_3187_ bufferIn, C_507_ entityIn, double xIn, double yIn, double zIn, C_4675_ blockPosIn, C_2064_ blockStateIn) {
      if (!Config.isCustomEntityModels() || !CustomEntityModels.isCustomModel(blockStateIn)) {
         m_109782_(matrixStackIn, bufferIn, blockStateIn.m_60651_(this.f_109465_, blockPosIn, C_3051_.m_82750_(entityIn)), (double)blockPosIn.u() - xIn, (double)blockPosIn.v() - yIn, (double)blockPosIn.w() - zIn, 0.0F, 0.0F, 0.0F, 0.4F);
      }
   }

   private static C_3046_ m_285956_(float factorIn) {
      float f = 5.99999F;
      int i = (int)(C_188_.m_14036_(factorIn, 0.0F, 1.0F) * 5.99999F);
      float f1 = factorIn * 5.99999F - (float)i;
      C_3046_ var10000;
      switch (i) {
         case 0:
            var10000 = new C_3046_(1.0, (double)f1, 0.0);
            break;
         case 1:
            var10000 = new C_3046_((double)(1.0F - f1), 1.0, 0.0);
            break;
         case 2:
            var10000 = new C_3046_(0.0, 1.0, (double)f1);
            break;
         case 3:
            var10000 = new C_3046_(0.0, 1.0 - (double)f1, 1.0);
            break;
         case 4:
            var10000 = new C_3046_((double)f1, 0.0, 1.0);
            break;
         case 5:
            var10000 = new C_3046_(1.0, 0.0, 1.0 - (double)f1);
            break;
         default:
            throw new IllegalStateException("Unexpected value: " + i);
      }

      return var10000;
   }

   private static C_3046_ m_285739_(float red, float green, float blue, float factorIn) {
      C_3046_ vec3 = m_285956_(factorIn).m_82490_((double)red);
      C_3046_ vec31 = m_285956_((factorIn + 0.33333334F) % 1.0F).m_82490_((double)green);
      C_3046_ vec32 = m_285956_((factorIn + 0.6666667F) % 1.0F).m_82490_((double)blue);
      C_3046_ vec33 = vec3.m_82549_(vec31).m_82549_(vec32);
      double d0 = Math.max(Math.max(1.0, vec33.f_82479_), Math.max(vec33.f_82480_, vec33.f_82481_));
      return new C_3046_(vec33.f_82479_ / d0, vec33.f_82480_ / d0, vec33.f_82481_ / d0);
   }

   public static void m_285900_(C_3181_ matrixStackIn, C_3187_ bufferIn, C_3072_ shapeIn, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha, boolean shiftHueIn) {
      List list = shapeIn.m_83299_();
      if (!list.isEmpty()) {
         int i = shiftHueIn ? list.size() : list.size() * 8;
         m_109782_(matrixStackIn, bufferIn, C_3068_.m_83064_((C_3040_)list.get(0)), xIn, yIn, zIn, red, green, blue, alpha);

         for(int j = 1; j < list.size(); ++j) {
            C_3040_ aabb = (C_3040_)list.get(j);
            float f = (float)j / (float)i;
            C_3046_ vec3 = m_285739_(red, green, blue, f);
            m_109782_(matrixStackIn, bufferIn, C_3068_.m_83064_(aabb), xIn, yIn, zIn, (float)vec3.f_82479_, (float)vec3.f_82480_, (float)vec3.f_82481_, alpha);
         }
      }

   }

   private static void m_109782_(C_3181_ matrixStackIn, C_3187_ bufferIn, C_3072_ shapeIn, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha) {
      C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_();
      shapeIn.m_83224_((x0, y0, z0, x1, y1, z1) -> {
         float f = (float)(x1 - x0);
         float f1 = (float)(y1 - y0);
         float f2 = (float)(z1 - z0);
         float f3 = C_188_.m_14116_(f * f + f1 * f1 + f2 * f2);
         f /= f3;
         f1 /= f3;
         f2 /= f3;
         bufferIn.m_338370_(posestack$pose, (float)(x0 + xIn), (float)(y0 + yIn), (float)(z0 + zIn)).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, f, f1, f2);
         bufferIn.m_338370_(posestack$pose, (float)(x1 + xIn), (float)(y1 + yIn), (float)(z1 + zIn)).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, f, f1, f2);
      });
   }

   public static void m_172965_(C_3187_ bufferIn, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
      m_109621_(new C_3181_(), bufferIn, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha, red, green, blue);
   }

   public static void m_109646_(C_3181_ matrixStackIn, C_3187_ bufferIn, C_3040_ aabbIn, float red, float green, float blue, float alpha) {
      m_109621_(matrixStackIn, bufferIn, aabbIn.f_82288_, aabbIn.f_82289_, aabbIn.f_82290_, aabbIn.f_82291_, aabbIn.f_82292_, aabbIn.f_82293_, red, green, blue, alpha, red, green, blue);
   }

   public static void m_109608_(C_3181_ matrixStackIn, C_3187_ bufferIn, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
      m_109621_(matrixStackIn, bufferIn, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha, red, green, blue);
   }

   public static void m_109621_(C_3181_ matrixStackIn, C_3187_ bufferIn, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha, float red2, float green2, float blue2) {
      C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_();
      float f = (float)minX;
      float f1 = (float)minY;
      float f2 = (float)minZ;
      float f3 = (float)maxX;
      float f4 = (float)maxY;
      float f5 = (float)maxZ;
      bufferIn.m_338370_(posestack$pose, f, f1, f2).m_340057_(red, green2, blue2, alpha).m_339200_(posestack$pose, 1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f1, f2).m_340057_(red, green2, blue2, alpha).m_339200_(posestack$pose, 1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f, f1, f2).m_340057_(red2, green, blue2, alpha).m_339200_(posestack$pose, 0.0F, 1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f, f4, f2).m_340057_(red2, green, blue2, alpha).m_339200_(posestack$pose, 0.0F, 1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f, f1, f2).m_340057_(red2, green2, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, 1.0F);
      bufferIn.m_338370_(posestack$pose, f, f1, f5).m_340057_(red2, green2, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, 1.0F);
      bufferIn.m_338370_(posestack$pose, f3, f1, f2).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f4, f2).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f4, f2).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, -1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f, f4, f2).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, -1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f, f4, f2).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, 1.0F);
      bufferIn.m_338370_(posestack$pose, f, f4, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, 1.0F);
      bufferIn.m_338370_(posestack$pose, f, f4, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, -1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f, f1, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, -1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f, f1, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f1, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f1, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, -1.0F);
      bufferIn.m_338370_(posestack$pose, f3, f1, f2).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, -1.0F);
      bufferIn.m_338370_(posestack$pose, f, f4, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f4, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 1.0F, 0.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f1, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f4, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 1.0F, 0.0F);
      bufferIn.m_338370_(posestack$pose, f3, f4, f2).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, 1.0F);
      bufferIn.m_338370_(posestack$pose, f3, f4, f5).m_340057_(red, green, blue, alpha).m_339200_(posestack$pose, 0.0F, 0.0F, 1.0F);
   }

   public static void m_269208_(C_3181_ matrixStackIn, C_3187_ bufferIn, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
      m_269282_(matrixStackIn, bufferIn, (float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2, red, green, blue, alpha);
   }

   public static void m_269282_(C_3181_ matrixStackIn, C_3187_ bufferIn, float x1, float y1, float z1, float x2, float y2, float z2, float red, float green, float blue, float alpha) {
      Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
      bufferIn.m_339083_(matrix4f, x1, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y1, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y2, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y2, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y2, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y1, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y1, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y1, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y2, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y1, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y1, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y1, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y1, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y2, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y2, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x1, y2, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z1).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z2).m_340057_(red, green, blue, alpha);
      bufferIn.m_339083_(matrix4f, x2, y2, z2).m_340057_(red, green, blue, alpha);
   }

   public static void m_340636_(C_3181_ matrixStackIn, C_3187_ bufferIn, C_4687_ dirIn, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax, float red, float green, float blue, float alpha) {
      Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
      switch (dirIn) {
         case DOWN:
            bufferIn.m_339083_(matrix4f, xMin, yMin, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMin, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMin, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMin, yMin, zMax).m_340057_(red, green, blue, alpha);
            break;
         case field_50:
            bufferIn.m_339083_(matrix4f, xMin, yMax, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMin, yMax, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMax, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMax, zMin).m_340057_(red, green, blue, alpha);
            break;
         case NORTH:
            bufferIn.m_339083_(matrix4f, xMin, yMin, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMin, yMax, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMax, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMin, zMin).m_340057_(red, green, blue, alpha);
            break;
         case SOUTH:
            bufferIn.m_339083_(matrix4f, xMin, yMin, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMin, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMax, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMin, yMax, zMax).m_340057_(red, green, blue, alpha);
            break;
         case WEST:
            bufferIn.m_339083_(matrix4f, xMin, yMin, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMin, yMin, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMin, yMax, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMin, yMax, zMin).m_340057_(red, green, blue, alpha);
            break;
         case EAST:
            bufferIn.m_339083_(matrix4f, xMax, yMin, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMax, zMin).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMax, zMax).m_340057_(red, green, blue, alpha);
            bufferIn.m_339083_(matrix4f, xMax, yMin, zMax).m_340057_(red, green, blue, alpha);
      }

   }

   public void m_109544_(C_1559_ worldIn, C_4675_ pos, C_2064_ oldState, C_2064_ newState, int flags) {
      this.m_109732_(pos, (flags & 8) != 0);
   }

   private void m_109732_(C_4675_ posIn, boolean rerenderOnMainThread) {
      for(int i = posIn.w() - 1; i <= posIn.w() + 1; ++i) {
         for(int j = posIn.u() - 1; j <= posIn.u() + 1; ++j) {
            for(int k = posIn.v() - 1; k <= posIn.v() + 1; ++k) {
               this.m_109501_(C_4710_.m_123171_(j), C_4710_.m_123171_(k), C_4710_.m_123171_(i), rerenderOnMainThread);
            }
         }
      }

   }

   public void m_109494_(int x1, int y1, int z1, int x2, int y2, int z2) {
      for(int i = z1 - 1; i <= z2 + 1; ++i) {
         for(int j = x1 - 1; j <= x2 + 1; ++j) {
            for(int k = y1 - 1; k <= y2 + 1; ++k) {
               this.m_109770_(C_4710_.m_123171_(j), C_4710_.m_123171_(k), C_4710_.m_123171_(i));
            }
         }
      }

   }

   public void m_109721_(C_4675_ blockPosIn, C_2064_ oldState, C_2064_ newState) {
      if (this.f_109461_.m_91304_().m_119415_(oldState, newState)) {
         this.m_109494_(blockPosIn.u(), blockPosIn.v(), blockPosIn.w(), blockPosIn.u(), blockPosIn.v(), blockPosIn.w());
      }

   }

   public void m_109490_(int sectionX, int sectionY, int sectionZ) {
      for(int i = sectionZ - 1; i <= sectionZ + 1; ++i) {
         for(int j = sectionX - 1; j <= sectionX + 1; ++j) {
            for(int k = sectionY - 1; k <= sectionY + 1; ++k) {
               this.m_109770_(j, k, i);
            }
         }
      }

   }

   public void m_109770_(int sectionX, int sectionY, int sectionZ) {
      this.m_109501_(sectionX, sectionY, sectionZ, false);
   }

   private void m_109501_(int sectionX, int sectionY, int sectionZ, boolean rerenderOnMainThread) {
      this.f_109469_.m_110859_(sectionX, sectionY, sectionZ, rerenderOnMainThread);
   }

   public void m_338545_(C_203228_ songIn, C_4675_ posIn) {
      if (this.f_109465_ != null) {
         this.m_340029_(posIn);
         C_336495_ jukeboxsong = (C_336495_)songIn.m_203334_();
         C_123_ soundevent = (C_123_)jukeboxsong.f_337080_().m_203334_();
         C_4566_ soundinstance = C_4561_.m_246411_(soundevent, C_3046_.m_82512_(posIn));
         this.f_336897_.put(posIn, soundinstance);
         this.f_109461_.m_91106_().m_120367_(soundinstance);
         this.f_109461_.f_91065_.m_93055_(jukeboxsong.f_337519_());
         this.m_109550_(this.f_109465_, posIn, true);
      }

   }

   private void m_340029_(C_4675_ posIn) {
      C_4566_ soundinstance = (C_4566_)this.f_336897_.remove(posIn);
      if (soundinstance != null) {
         this.f_109461_.m_91106_().m_120399_(soundinstance);
      }

   }

   public void m_340440_(C_4675_ posIn) {
      this.m_340029_(posIn);
      if (this.f_109465_ != null) {
         this.m_109550_(this.f_109465_, posIn, false);
      }

   }

   private void m_109550_(C_1596_ worldIn, C_4675_ pos, boolean isPartying) {
      Iterator var4 = worldIn.a(C_524_.class, (new C_3040_(pos)).m_82400_(3.0)).iterator();

      while(var4.hasNext()) {
         C_524_ livingentity = (C_524_)var4.next();
         livingentity.m_6818_(pos, isPartying);
      }

   }

   public void m_109743_(C_4756_ particleData, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.m_109752_(particleData, alwaysRender, false, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_109752_(C_4756_ particleData, boolean ignoreRange, boolean minimizeLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      try {
         this.m_109804_(particleData, ignoreRange, minimizeLevel, x, y, z, xSpeed, ySpeed, zSpeed);
      } catch (Throwable var19) {
         C_4883_ crashreport = C_4883_.m_127521_(var19, "Exception while adding particle");
         C_4909_ crashreportcategory = crashreport.m_127514_("Particle being added");
         crashreportcategory.m_128159_("ID", C_256712_.f_257034_.m_7981_(particleData.m_6012_()));
         crashreportcategory.m_128165_("Parameters", () -> {
            return C_4759_.f_123791_.encodeStart(this.f_109465_.m_9598_().a(C_4940_.f_128958_), particleData).toString();
         });
         crashreportcategory.m_128165_("Position", () -> {
            return C_4909_.m_178937_(this.f_109465_, x, y, z);
         });
         throw new C_5204_(crashreport);
      }
   }

   private void m_109735_(C_4756_ particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.m_109743_(particleData, particleData.m_6012_().m_123742_(), x, y, z, xSpeed, ySpeed, zSpeed);
   }

   @Nullable
   private C_4022_ m_109795_(C_4756_ particleData, boolean alwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return this.m_109804_(particleData, alwaysRender, false, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   @Nullable
   private C_4022_ m_109804_(C_4756_ particleData, boolean alwaysRender, boolean minimizeLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      C_3373_ camera = this.f_109461_.f_91063_.m_109153_();
      C_3404_ particlestatus = this.m_109767_(minimizeLevel);
      if (particleData == C_4759_.f_123812_ && !Config.isAnimatedExplosion()) {
         return null;
      } else if (particleData == C_4759_.f_123813_ && !Config.isAnimatedExplosion()) {
         return null;
      } else if (particleData == C_4759_.f_123759_ && !Config.isAnimatedExplosion()) {
         return null;
      } else if (particleData == C_4759_.f_123768_ && !Config.isWaterParticles()) {
         return null;
      } else if (particleData == C_4759_.f_123762_ && !Config.isAnimatedSmoke()) {
         return null;
      } else if (particleData == C_4759_.f_123755_ && !Config.isAnimatedSmoke()) {
         return null;
      } else if (particleData == C_4759_.f_123811_ && !Config.isPotionParticles()) {
         return null;
      } else if (particleData == C_4759_.f_123806_ && !Config.isPotionParticles()) {
         return null;
      } else if (particleData == C_4759_.f_123751_ && !Config.isPotionParticles()) {
         return null;
      } else if (particleData == C_4759_.f_123771_ && !Config.isPotionParticles()) {
         return null;
      } else if (particleData == C_4759_.f_123760_ && !Config.isPortalParticles()) {
         return null;
      } else if (particleData == C_4759_.f_123744_ && !Config.isAnimatedFlame()) {
         return null;
      } else if (particleData == C_4759_.f_123745_ && !Config.isAnimatedFlame()) {
         return null;
      } else if (particleData == C_4759_.f_123805_ && !Config.isAnimatedRedstone()) {
         return null;
      } else if (particleData == C_4759_.f_123803_ && !Config.isDrippingWaterLava()) {
         return null;
      } else if (particleData == C_4759_.f_123800_ && !Config.isDrippingWaterLava()) {
         return null;
      } else if (particleData == C_4759_.f_123815_ && !Config.isFireworkParticles()) {
         return null;
      } else {
         if (!alwaysRender) {
            double maxDistSq = 1024.0;
            if (particleData == C_4759_.f_123797_) {
               maxDistSq = 38416.0;
            }

            if (camera.m_90583_().m_82531_(x, y, z) > maxDistSq) {
               return null;
            }

            if (particlestatus == C_3404_.MINIMAL) {
               return null;
            }
         }

         C_4022_ entityFx = this.f_109461_.f_91061_.m_107370_(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
         if (particleData == C_4759_.f_123795_) {
            CustomColors.updateWaterFX(entityFx, this.f_109465_, x, y, z, this.renderEnv);
         }

         if (particleData == C_4759_.f_123769_) {
            CustomColors.updateWaterFX(entityFx, this.f_109465_, x, y, z, this.renderEnv);
         }

         if (particleData == C_4759_.f_123761_) {
            CustomColors.updateWaterFX(entityFx, this.f_109465_, x, y, z, this.renderEnv);
         }

         if (particleData == C_4759_.f_123757_) {
            CustomColors.updateMyceliumFX(entityFx);
         }

         if (particleData == C_4759_.f_123760_) {
            CustomColors.updatePortalFX(entityFx);
         }

         if (particleData == C_4759_.f_123805_) {
            CustomColors.updateReddustFX(entityFx, this.f_109465_, x, y, z);
         }

         if (particleData == C_4759_.f_123756_) {
            CustomColors.updateLavaFX(entityFx);
         }

         return entityFx;
      }
   }

   private C_3404_ m_109767_(boolean minimiseLevel) {
      C_3404_ particlestatus = (C_3404_)this.f_109461_.f_91066_.m_231929_().m_231551_();
      if (minimiseLevel && particlestatus == C_3404_.MINIMAL && this.f_109465_.f_46441_.m_188503_(10) == 0) {
         particlestatus = C_3404_.DECREASED;
      }

      if (particlestatus == C_3404_.DECREASED && this.f_109465_.f_46441_.m_188503_(3) == 0) {
         particlestatus = C_3404_.MINIMAL;
      }

      return particlestatus;
   }

   public void m_109824_() {
   }

   public void m_109506_(int soundID, C_4675_ pos, int data) {
      switch (soundID) {
         case 1023:
         case 1028:
         case 1038:
            C_3373_ camera = this.f_109461_.f_91063_.m_109153_();
            if (camera.m_90593_()) {
               double d0 = (double)pos.u() - camera.m_90583_().f_82479_;
               double d1 = (double)pos.v() - camera.m_90583_().f_82480_;
               double d2 = (double)pos.w() - camera.m_90583_().f_82481_;
               double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
               double d4 = camera.m_90583_().f_82479_;
               double d5 = camera.m_90583_().f_82480_;
               double d6 = camera.m_90583_().f_82481_;
               if (d3 > 0.0) {
                  d4 += d0 / d3 * 2.0;
                  d5 += d1 / d3 * 2.0;
                  d6 += d2 / d3 * 2.0;
               }

               if (soundID == 1023) {
                  this.f_109465_.m_7785_(d4, d5, d6, C_124_.f_12563_, C_125_.HOSTILE, 1.0F, 1.0F, false);
               } else if (soundID == 1038) {
                  this.f_109465_.m_7785_(d4, d5, d6, C_124_.f_11860_, C_125_.HOSTILE, 1.0F, 1.0F, false);
               } else {
                  this.f_109465_.m_7785_(d4, d5, d6, C_124_.f_11891_, C_125_.HOSTILE, 5.0F, 1.0F, false);
               }
            }
         default:
      }
   }

   public void m_234304_(int type, C_4675_ blockPosIn, int data) {
      C_212974_ randomsource = this.f_109465_.f_46441_;
      int k;
      double d1;
      double d2;
      int j2;
      double d11;
      double d16;
      double d21;
      float f4;
      float f6;
      double d24;
      double d25;
      float f1;
      switch (type) {
         case 1000:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11796_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1001:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11797_, C_125_.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1002:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11798_, C_125_.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1004:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11933_, C_125_.NEUTRAL, 1.0F, 1.2F, false);
            break;
         case 1009:
            if (data == 0) {
               this.f_109465_.m_245747_(blockPosIn, C_124_.f_11937_, C_125_.BLOCKS, 0.5F, 2.6F + (randomsource.m_188501_() - randomsource.m_188501_()) * 0.8F, false);
            } else if (data == 1) {
               this.f_109465_.m_245747_(blockPosIn, C_124_.f_11914_, C_125_.BLOCKS, 0.7F, 1.6F + (randomsource.m_188501_() - randomsource.m_188501_()) * 0.4F, false);
            }
            break;
         case 1010:
            this.f_109465_.m_9598_().m_175515_(C_256686_.f_337466_).m_203300_(data).ifPresent((songIn) -> {
               this.m_338545_(songIn, blockPosIn);
            });
            break;
         case 1011:
            this.m_340440_(blockPosIn);
            break;
         case 1015:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11924_, C_125_.HOSTILE, 10.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1016:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11923_, C_125_.HOSTILE, 10.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1017:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11896_, C_125_.HOSTILE, 10.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1018:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11705_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1019:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12599_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1020:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12600_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1021:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12601_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1022:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12555_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1024:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12558_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1025:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11735_, C_125_.NEUTRAL, 0.05F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1026:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12609_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1027:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12616_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1029:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11665_, C_125_.BLOCKS, 1.0F, randomsource.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1030:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11671_, C_125_.BLOCKS, 1.0F, randomsource.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1031:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11668_, C_125_.BLOCKS, 0.3F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1032:
            this.f_109461_.m_91106_().m_120367_(C_4561_.m_119766_(C_124_.f_12287_, randomsource.m_188501_() * 0.4F + 0.8F, 0.25F));
            break;
         case 1033:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11756_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1034:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11755_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1035:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11772_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1039:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12228_, C_125_.HOSTILE, 0.3F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1040:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12602_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1041:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12044_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1042:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11998_, C_125_.BLOCKS, 1.0F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1043:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11713_, C_125_.BLOCKS, 1.0F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1044:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12471_, C_125_.BLOCKS, 1.0F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1045:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_144126_, C_125_.BLOCKS, 2.0F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1046:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_144129_, C_125_.BLOCKS, 2.0F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1047:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_144130_, C_125_.BLOCKS, 2.0F, this.f_109465_.f_46441_.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 1048:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_144211_, C_125_.HOSTILE, 2.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, false);
            break;
         case 1049:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_303486_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1050:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_303417_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1051:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_316553_, C_125_.BLOCKS, 0.5F, 0.4F / (this.f_109465_.m_213780_().m_188501_() * 0.4F + 0.8F), false);
         case 2010:
            this.m_304955_(data, blockPosIn, randomsource, C_4759_.f_302345_);
            break;
         case 1500:
            C_1740_.m_51923_(this.f_109465_, blockPosIn, data > 0);
            break;
         case 1501:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12031_, C_125_.BLOCKS, 0.5F, 2.6F + (randomsource.m_188501_() - randomsource.m_188501_()) * 0.8F, false);

            for(j2 = 0; j2 < 8; ++j2) {
               this.f_109465_.m_7106_(C_4759_.f_123755_, (double)blockPosIn.u() + randomsource.m_188500_(), (double)blockPosIn.v() + 1.2, (double)blockPosIn.w() + randomsource.m_188500_(), 0.0, 0.0, 0.0);
            }

            return;
         case 1502:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12374_, C_125_.BLOCKS, 0.5F, 2.6F + (randomsource.m_188501_() - randomsource.m_188501_()) * 0.8F, false);

            for(j2 = 0; j2 < 5; ++j2) {
               d11 = (double)blockPosIn.u() + randomsource.m_188500_() * 0.6 + 0.2;
               d16 = (double)blockPosIn.v() + randomsource.m_188500_() * 0.6 + 0.2;
               d21 = (double)blockPosIn.w() + randomsource.m_188500_() * 0.6 + 0.2;
               this.f_109465_.m_7106_(C_4759_.f_123762_, d11, d16, d21, 0.0, 0.0, 0.0);
            }

            return;
         case 1503:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11859_, C_125_.BLOCKS, 1.0F, 1.0F, false);

            for(j2 = 0; j2 < 16; ++j2) {
               d11 = (double)blockPosIn.u() + (5.0 + randomsource.m_188500_() * 6.0) / 16.0;
               d16 = (double)blockPosIn.v() + 0.8125;
               d21 = (double)blockPosIn.w() + (5.0 + randomsource.m_188500_() * 6.0) / 16.0;
               this.f_109465_.m_7106_(C_4759_.f_123762_, d11, d16, d21, 0.0, 0.0, 0.0);
            }

            return;
         case 1504:
            C_141220_.m_154062_(this.f_109465_, blockPosIn, this.f_109465_.m_8055_(blockPosIn));
            break;
         case 1505:
            C_1327_.m_40638_(this.f_109465_, blockPosIn, data);
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_144074_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 2000:
            this.m_304955_(data, blockPosIn, randomsource, C_4759_.f_123762_);
            break;
         case 2001:
            C_2064_ blockstate1 = C_1706_.m_49803_(data);
            if (!blockstate1.m_60795_()) {
               C_1914_ soundtype = blockstate1.m_60827_();
               if (Reflector.IForgeBlockState_getSoundType3.exists()) {
                  soundtype = (C_1914_)Reflector.call(blockstate1, Reflector.IForgeBlockState_getSoundType3, this.f_109465_, blockPosIn, null);
               }

               this.f_109465_.m_245747_(blockPosIn, soundtype.m_56775_(), C_125_.BLOCKS, (soundtype.m_56773_() + 1.0F) / 2.0F, soundtype.m_56774_() * 0.8F, false);
            }

            this.f_109465_.m_142052_(blockPosIn, blockstate1);
            break;
         case 2002:
         case 2007:
            C_3046_ vec3 = C_3046_.m_82539_(blockPosIn);

            for(int j = 0; j < 8; ++j) {
               this.m_109735_(new C_4754_(C_4759_.f_123752_, new C_1391_(C_1394_.f_42736_)), vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, randomsource.m_188583_() * 0.15, randomsource.m_188500_() * 0.2, randomsource.m_188583_() * 0.15);
            }

            float f2 = (float)(data >> 16 & 255) / 255.0F;
            float f3 = (float)(data >> 8 & 255) / 255.0F;
            float f5 = (float)(data >> 0 & 255) / 255.0F;
            C_4756_ particleoptions = type == 2007 ? C_4759_.f_123751_ : C_4759_.f_123806_;

            for(int i2 = 0; i2 < 100; ++i2) {
               double d10 = randomsource.m_188500_() * 4.0;
               double d15 = randomsource.m_188500_() * Math.PI * 2.0;
               double d20 = Math.cos(d15) * d10;
               d24 = 0.01 + randomsource.m_188500_() * 0.5;
               d25 = Math.sin(d15) * d10;
               C_4022_ particle1 = this.m_109795_(particleoptions, particleoptions.m_6012_().m_123742_(), vec3.f_82479_ + d20 * 0.1, vec3.f_82480_ + 0.3, vec3.f_82481_ + d25 * 0.1, d20, d24, d25);
               if (particle1 != null) {
                  f1 = 0.75F + randomsource.m_188501_() * 0.25F;
                  particle1.m_107253_(f2 * f1, f3 * f1, f5 * f1);
                  particle1.m_107268_((float)d10);
               }
            }

            this.f_109465_.m_245747_(blockPosIn, C_124_.f_12436_, C_125_.NEUTRAL, 1.0F, randomsource.m_188501_() * 0.1F + 0.9F, false);
            break;
         case 2003:
            double d0 = (double)blockPosIn.u() + 0.5;
            double d5 = (double)blockPosIn.v();
            double d7 = (double)blockPosIn.w() + 0.5;

            for(k = 0; k < 8; ++k) {
               this.m_109735_(new C_4754_(C_4759_.f_123752_, new C_1391_(C_1394_.f_42545_)), d0, d5, d7, randomsource.m_188583_() * 0.15, randomsource.m_188500_() * 0.2, randomsource.m_188583_() * 0.15);
            }

            for(double d9 = 0.0; d9 < 6.283185307179586; d9 += 0.15707963267948966) {
               this.m_109735_(C_4759_.f_123760_, d0 + Math.cos(d9) * 5.0, d5 - 0.4, d7 + Math.sin(d9) * 5.0, Math.cos(d9) * -5.0, 0.0, Math.sin(d9) * -5.0);
               this.m_109735_(C_4759_.f_123760_, d0 + Math.cos(d9) * 5.0, d5 - 0.4, d7 + Math.sin(d9) * 5.0, Math.cos(d9) * -7.0, 0.0, Math.sin(d9) * -7.0);
            }

            return;
         case 2004:
            for(k = 0; k < 20; ++k) {
               d24 = (double)blockPosIn.u() + 0.5 + (randomsource.m_188500_() - 0.5) * 2.0;
               d25 = (double)blockPosIn.v() + 0.5 + (randomsource.m_188500_() - 0.5) * 2.0;
               d1 = (double)blockPosIn.w() + 0.5 + (randomsource.m_188500_() - 0.5) * 2.0;
               this.f_109465_.m_7106_(C_4759_.f_123762_, d24, d25, d1, 0.0, 0.0, 0.0);
               this.f_109465_.m_7106_(C_4759_.f_123744_, d24, d25, d1, 0.0, 0.0, 0.0);
            }

            return;
         case 2006:
            for(k = 0; k < 200; ++k) {
               f4 = randomsource.m_188501_() * 4.0F;
               f6 = randomsource.m_188501_() * 6.2831855F;
               d25 = (double)(C_188_.m_14089_(f6) * f4);
               d1 = 0.01 + randomsource.m_188500_() * 0.5;
               d2 = (double)(C_188_.m_14031_(f6) * f4);
               C_4022_ particle = this.m_109795_(C_4759_.f_123799_, false, (double)blockPosIn.u() + d25 * 0.1, (double)blockPosIn.v() + 0.3, (double)blockPosIn.w() + d2 * 0.1, d25, d1, d2);
               if (particle != null) {
                  particle.m_107268_(f4);
               }
            }

            if (data == 1) {
               this.f_109465_.m_245747_(blockPosIn, C_124_.f_11892_, C_125_.HOSTILE, 1.0F, randomsource.m_188501_() * 0.1F + 0.9F, false);
            }
            break;
         case 2008:
            this.f_109465_.m_7106_(C_4759_.f_123813_, (double)blockPosIn.u() + 0.5, (double)blockPosIn.v() + 0.5, (double)blockPosIn.w() + 0.5, 0.0, 0.0, 0.0);
            break;
         case 2009:
            for(k = 0; k < 8; ++k) {
               this.f_109465_.m_7106_(C_4759_.f_123796_, (double)blockPosIn.u() + randomsource.m_188500_(), (double)blockPosIn.v() + 1.2, (double)blockPosIn.w() + randomsource.m_188500_(), 0.0, 0.0, 0.0);
            }

            return;
         case 2011:
            C_140992_.m_320303_(this.f_109465_, blockPosIn, data, C_4759_.f_123748_);
            break;
         case 2012:
            C_140992_.m_320303_(this.f_109465_, blockPosIn, data, C_4759_.f_123748_);
            break;
         case 2013:
            C_140992_.m_324552_(this.f_109465_, blockPosIn, data);
            break;
         case 3000:
            this.f_109465_.m_6493_(C_4759_.f_123812_, true, (double)blockPosIn.u() + 0.5, (double)blockPosIn.v() + 0.5, (double)blockPosIn.w() + 0.5, 0.0, 0.0, 0.0);
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11858_, C_125_.BLOCKS, 10.0F, (1.0F + (this.f_109465_.f_46441_.m_188501_() - this.f_109465_.f_46441_.m_188501_()) * 0.2F) * 0.7F, false);
            break;
         case 3001:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_11894_, C_125_.HOSTILE, 64.0F, 0.8F + this.f_109465_.f_46441_.m_188501_() * 0.3F, false);
            break;
         case 3002:
            if (data >= 0 && data < C_4687_.C_4689_.f_122448_.length) {
               C_140992_.m_144967_(C_4687_.C_4689_.f_122448_[data], this.f_109465_, blockPosIn, 0.125, C_4759_.f_175830_, C_141056_.m_146622_(10, 19));
            } else {
               C_140992_.m_216313_(this.f_109465_, blockPosIn, C_4759_.f_175830_, C_141056_.m_146622_(3, 5));
            }
            break;
         case 3003:
            C_140992_.m_216313_(this.f_109465_, blockPosIn, C_4759_.f_175828_, C_141056_.m_146622_(3, 5));
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_144178_, C_125_.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 3004:
            C_140992_.m_216313_(this.f_109465_, blockPosIn, C_4759_.f_175829_, C_141056_.m_146622_(3, 5));
            break;
         case 3005:
            C_140992_.m_216313_(this.f_109465_, blockPosIn, C_4759_.f_175831_, C_141056_.m_146622_(3, 5));
            break;
         case 3006:
            k = data >> 6;
            float f8;
            float f13;
            if (k > 0) {
               if (randomsource.m_188501_() < 0.3F + (float)k * 0.1F) {
                  f4 = 0.15F + 0.02F * (float)k * (float)k * randomsource.m_188501_();
                  f6 = 0.4F + 0.3F * (float)k * randomsource.m_188501_();
                  this.f_109465_.m_245747_(blockPosIn, C_124_.f_215734_, C_125_.BLOCKS, f4, f6, false);
               }

               byte b0 = (byte)(data & 63);
               C_141052_ intprovider = C_141056_.m_146622_(0, k);
               f8 = 0.005F;
               Supplier supplier = () -> {
                  return new C_3046_(C_188_.m_216263_(randomsource, -0.004999999888241291, 0.004999999888241291), C_188_.m_216263_(randomsource, -0.004999999888241291, 0.004999999888241291), C_188_.m_216263_(randomsource, -0.004999999888241291, 0.004999999888241291));
               };
               if (b0 == 0) {
                  C_4687_[] var53 = C_4687_.values();
                  int var23 = var53.length;

                  for(int var58 = 0; var58 < var23; ++var58) {
                     C_4687_ direction = var53[var58];
                     float f = direction == C_4687_.DOWN ? 3.1415927F : 0.0F;
                     double d4 = direction.m_122434_() == C_4687_.C_4689_.field_7 ? 0.65 : 0.57;
                     C_140992_.m_216318_(this.f_109465_, blockPosIn, new C_213470_(f), intprovider, direction, supplier, d4);
                  }

                  return;
               } else {
                  Iterator var54 = C_141219_.m_221569_(b0).iterator();

                  while(var54.hasNext()) {
                     C_4687_ direction1 = (C_4687_)var54.next();
                     f13 = direction1 == C_4687_.field_50 ? 3.1415927F : 0.0F;
                     double d18 = 0.35;
                     C_140992_.m_216318_(this.f_109465_, blockPosIn, new C_213470_(f13), intprovider, direction1, supplier, 0.35);
                  }

                  return;
               }
            } else {
               this.f_109465_.m_245747_(blockPosIn, C_124_.f_215734_, C_125_.BLOCKS, 1.0F, 1.0F, false);
               boolean flag1 = this.f_109465_.m_8055_(blockPosIn).m_60838_(this.f_109465_, blockPosIn);
               int j1 = flag1 ? 40 : 20;
               f8 = flag1 ? 0.45F : 0.25F;
               float f9 = 0.07F;

               for(int j3 = 0; j3 < j1; ++j3) {
                  f1 = 2.0F * randomsource.m_188501_() - 1.0F;
                  f13 = 2.0F * randomsource.m_188501_() - 1.0F;
                  float f15 = 2.0F * randomsource.m_188501_() - 1.0F;
                  this.f_109465_.m_7106_(C_4759_.f_235900_, (double)blockPosIn.u() + 0.5 + (double)(f1 * f8), (double)blockPosIn.v() + 0.5 + (double)(f13 * f8), (double)blockPosIn.w() + 0.5 + (double)(f15 * f8), (double)(f1 * 0.07F), (double)(f13 * 0.07F), (double)(f15 * 0.07F));
               }

               return;
            }
         case 3007:
            for(int i1 = 0; i1 < 10; ++i1) {
               this.f_109465_.m_6493_(new C_213472_(i1 * 5), false, (double)blockPosIn.u() + 0.5, (double)blockPosIn.v() + C_213113_.f_222156_, (double)blockPosIn.w() + 0.5, 0.0, 0.0, 0.0);
            }

            C_2064_ blockstate2 = this.f_109465_.m_8055_(blockPosIn);
            boolean flag = blockstate2.b(C_2083_.f_61362_) && (Boolean)blockstate2.c(C_2083_.f_61362_);
            if (!flag) {
               this.f_109465_.m_7785_((double)blockPosIn.u() + 0.5, (double)blockPosIn.v() + C_213113_.f_222156_, (double)blockPosIn.w() + 0.5, C_124_.f_215750_, C_125_.BLOCKS, 2.0F, 0.6F + this.f_109465_.f_46441_.m_188501_() * 0.4F, false);
            }
            break;
         case 3008:
            C_2064_ blockstate = C_1706_.m_49803_(data);
            C_1706_ var51 = blockstate.m_60734_();
            if (var51 instanceof C_276390_ brushableblock) {
               this.f_109465_.m_245747_(blockPosIn, brushableblock.m_277154_(), C_125_.PLAYERS, 1.0F, 1.0F, false);
            }

            this.f_109465_.m_142052_(blockPosIn, blockstate);
            break;
         case 3009:
            C_140992_.m_216313_(this.f_109465_, blockPosIn, C_4759_.f_276512_, C_141056_.m_146622_(3, 6));
            break;
         case 3011:
            C_301982_.m_320714_(this.f_109465_, blockPosIn, randomsource, C_313594_.m_319943_(data).f_316337_);
            break;
         case 3012:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_303183_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            C_301982_.m_320714_(this.f_109465_, blockPosIn, randomsource, C_313594_.m_319943_(data).f_316337_);
            break;
         case 3013:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_302635_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            C_301982_.m_306813_(this.f_109465_, blockPosIn, randomsource, data, C_4759_.f_314928_);
            break;
         case 3014:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_302685_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            C_301982_.m_306726_(this.f_109465_, blockPosIn, randomsource);
            break;
         case 3015:
            C_1991_ var49 = this.f_109465_.m_7702_(blockPosIn);
            if (var49 instanceof C_313346_ vaultblockentity) {
               C_313550_.m_322037_(this.f_109465_, vaultblockentity.aD_(), vaultblockentity.n(), vaultblockentity.m_318941_(), data == 0 ? C_4759_.f_175834_ : C_4759_.f_123745_);
               this.f_109465_.m_245747_(blockPosIn, C_124_.f_314077_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            }
            break;
         case 3016:
            C_313550_.m_319825_(this.f_109465_, blockPosIn, data == 0 ? C_4759_.f_175834_ : C_4759_.f_123745_);
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_313961_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            break;
         case 3017:
            C_301982_.m_306726_(this.f_109465_, blockPosIn, randomsource);
            break;
         case 3018:
            for(int i = 0; i < 10; ++i) {
               d1 = randomsource.m_188583_() * 0.02;
               d2 = randomsource.m_188583_() * 0.02;
               double d3 = randomsource.m_188583_() * 0.02;
               this.f_109465_.m_7106_(C_4759_.f_123759_, (double)blockPosIn.u() + randomsource.m_188500_(), (double)blockPosIn.v() + randomsource.m_188500_(), (double)blockPosIn.w() + randomsource.m_188500_(), d1, d2, d3);
            }

            this.f_109465_.m_245747_(blockPosIn, C_124_.f_315251_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            break;
         case 3019:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_302635_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            C_301982_.m_306813_(this.f_109465_, blockPosIn, randomsource, data, C_4759_.f_314692_);
            break;
         case 3020:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_315182_, C_125_.BLOCKS, data == 0 ? 0.3F : 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            C_301982_.m_306813_(this.f_109465_, blockPosIn, randomsource, 0, C_4759_.f_314692_);
            C_301982_.m_307155_(this.f_109465_, blockPosIn, randomsource);
            break;
         case 3021:
            this.f_109465_.m_245747_(blockPosIn, C_124_.f_315614_, C_125_.BLOCKS, 1.0F, (randomsource.m_188501_() - randomsource.m_188501_()) * 0.2F + 1.0F, true);
            C_301982_.m_320714_(this.f_109465_, blockPosIn, randomsource, C_313594_.m_319943_(data).f_316337_);
      }

   }

   public void m_109774_(int breakerId, C_4675_ pos, int progress) {
      C_5421_ blockdestructionprogress1;
      if (progress >= 0 && progress < 10) {
         blockdestructionprogress1 = (C_5421_)this.f_109408_.get(breakerId);
         if (blockdestructionprogress1 != null) {
            this.m_109765_(blockdestructionprogress1);
         }

         if (blockdestructionprogress1 == null || blockdestructionprogress1.m_139985_().u() != pos.u() || blockdestructionprogress1.m_139985_().v() != pos.v() || blockdestructionprogress1.m_139985_().w() != pos.w()) {
            blockdestructionprogress1 = new C_5421_(breakerId, pos);
            this.f_109408_.put(breakerId, blockdestructionprogress1);
         }

         blockdestructionprogress1.m_139981_(progress);
         blockdestructionprogress1.m_139986_(this.f_109477_);
         ((SortedSet)this.f_109409_.computeIfAbsent(blockdestructionprogress1.m_139985_().m_121878_(), (keyIn) -> {
            return Sets.newTreeSet();
         })).add(blockdestructionprogress1);
      } else {
         blockdestructionprogress1 = (C_5421_)this.f_109408_.remove(breakerId);
         if (blockdestructionprogress1 != null) {
            this.m_109765_(blockdestructionprogress1);
         }
      }

   }

   public boolean m_294493_() {
      return this.f_290446_.m_293214_();
   }

   public void m_292785_(C_1560_ chunkPosIn) {
      this.f_291822_.m_294751_(chunkPosIn);
   }

   public void m_109826_() {
      this.f_291822_.m_295966_();
      this.f_109474_ = true;
   }

   public int getCountRenderers() {
      return this.f_109469_.f_291707_.length;
   }

   public int getCountEntitiesRendered() {
      return this.f_109439_;
   }

   public int getCountTileEntitiesRendered() {
      return this.countTileEntitiesRendered;
   }

   public int getCountLoadedChunks() {
      if (this.f_109465_ == null) {
         return 0;
      } else {
         C_3895_ chunkProvider = this.f_109465_.m_7726_();
         return chunkProvider == null ? 0 : chunkProvider.m_8482_();
      }
   }

   public int getCountChunksToUpdate() {
      return this.countChunksToUpdate;
   }

   public C_290152_.C_290138_ getRenderChunk(C_4675_ pos) {
      return this.f_109469_.m_292642_(pos);
   }

   public C_3899_ getWorld() {
      return this.f_109465_;
   }

   private void clearRenderInfos() {
      this.clearRenderInfosTerrain();
      this.clearRenderInfosEntities();
   }

   private void clearRenderInfosTerrain() {
      if (renderEntitiesCounter > 0) {
         this.renderInfosTerrain = new ObjectArrayList(this.renderInfosTerrain.size() + 16);
         this.renderInfosTileEntities = new ArrayList(this.renderInfosTileEntities.size() + 16);
      } else {
         this.renderInfosTerrain.clear();
         this.renderInfosTileEntities.clear();
      }

   }

   private void clearRenderInfosEntities() {
      if (renderEntitiesCounter > 0) {
         this.renderInfosEntities = new LongOpenHashSet(this.renderInfosEntities.size() + 16);
      } else {
         this.renderInfosEntities.clear();
      }

   }

   public void onPlayerPositionSet() {
      if (this.firstWorldLoad) {
         this.m_109818_();
         this.firstWorldLoad = false;
      }

   }

   public void pauseChunkUpdates() {
      if (this.f_290446_ != null) {
         this.f_290446_.pauseChunkUpdates();
      }

   }

   public void resumeChunkUpdates() {
      if (this.f_290446_ != null) {
         this.f_290446_.resumeChunkUpdates();
      }

   }

   public int getFrameCount() {
      return this.frameId;
   }

   public C_4148_ getRenderTypeTextures() {
      return this.f_109464_;
   }

   public LongOpenHashSet getRenderChunksEntities() {
      return this.renderInfosEntities;
   }

   private void addEntitySection(LongOpenHashSet set, C_141285_ storage, C_4675_ pos) {
      long sectionPos = C_4710_.m_175568_(pos);
      C_141284_ es = storage.m_156895_(sectionPos);
      if (es != null) {
         set.add(sectionPos);
      }
   }

   private boolean hasEntitySection(C_141285_ storage, C_4675_ pos) {
      long sectionPos = C_4710_.m_175568_(pos);
      C_141284_ es = storage.m_156895_(sectionPos);
      return es != null;
   }

   public List getRenderInfos() {
      return this.f_290776_;
   }

   public List getRenderInfosTerrain() {
      return this.renderInfosTerrain;
   }

   public List getRenderInfosTileEntities() {
      return this.renderInfosTileEntities;
   }

   private void checkLoadVisibleChunks(C_3373_ activeRenderInfo, C_4273_ icamera, boolean spectator) {
      if (this.loadVisibleChunksCounter == 0) {
         this.loadAllVisibleChunks(activeRenderInfo, icamera, spectator);
         this.f_109461_.f_91065_.m_93076_().m_240953_(loadVisibleChunksMessageId);
      }

      if (this.loadVisibleChunksCounter >= 0) {
         --this.loadVisibleChunksCounter;
      }

   }

   private void loadAllVisibleChunks(C_3373_ activeRenderInfo, C_4273_ icamera, boolean spectator) {
      int chunkUpdatesConfig = this.f_109461_.f_91066_.ofChunkUpdates;
      boolean lazyChunkLoadingConfig = this.f_109461_.f_91066_.ofLazyChunkLoading;

      try {
         this.f_109461_.f_91066_.ofChunkUpdates = 1000;
         this.f_109461_.f_91066_.ofLazyChunkLoading = false;
         C_4134_ renderGlobal = Config.getRenderGlobal();
         int countLoadedChunks = renderGlobal.getCountLoadedChunks();
         long timeStart = System.currentTimeMillis();
         Config.dbg("Loading visible chunks");
         long timeLog = System.currentTimeMillis() + 5000L;
         int chunksUpdated = 0;
         boolean hasUpdates = false;

         do {
            hasUpdates = false;

            for(int i = 0; i < 100; ++i) {
               renderGlobal.m_109826_();
               renderGlobal.m_194338_(activeRenderInfo, icamera, false, spectator);
               Config.sleep(1L);
               this.m_194370_(activeRenderInfo);
               if (renderGlobal.getCountChunksToUpdate() > 0) {
                  hasUpdates = true;
               }

               if (!renderGlobal.m_294493_()) {
                  hasUpdates = true;
               }

               chunksUpdated += renderGlobal.getCountChunksToUpdate();

               while(!renderGlobal.m_294493_()) {
                  int countUpdates = renderGlobal.getCountChunksToUpdate();
                  this.m_194370_(activeRenderInfo);
                  if (countUpdates == renderGlobal.getCountChunksToUpdate()) {
                     break;
                  }
               }

               chunksUpdated -= renderGlobal.getCountChunksToUpdate();
               if (!hasUpdates) {
                  break;
               }
            }

            if (renderGlobal.getCountLoadedChunks() != countLoadedChunks) {
               hasUpdates = true;
               countLoadedChunks = renderGlobal.getCountLoadedChunks();
            }

            if (System.currentTimeMillis() > timeLog) {
               Config.log("Chunks loaded: " + chunksUpdated);
               timeLog = System.currentTimeMillis() + 5000L;
            }
         } while(hasUpdates);

         Config.log("Chunks loaded: " + chunksUpdated);
         Config.log("Finished loading visible chunks");
         C_290152_.renderChunksUpdated = 0;
      } finally {
         this.f_109461_.f_91066_.ofChunkUpdates = chunkUpdatesConfig;
         this.f_109461_.f_91066_.ofLazyChunkLoading = lazyChunkLoadingConfig;
      }

   }

   public void applyFrustumEntities(C_4273_ camera, int maxChunkDistance) {
      this.renderInfosEntities.clear();
      int cameraChunkX = (int)camera.getCameraX() >> 4 << 4;
      int cameraChunkY = (int)camera.getCameraY() >> 4 << 4;
      int cameraChunkZ = (int)camera.getCameraZ() >> 4 << 4;
      int maxChunkDistSq = maxChunkDistance * maxChunkDistance;
      C_141285_ entitySectionStorage = this.f_109465_.getSectionStorage();
      BlockPosM posM = new BlockPosM();
      LongSet posLongSet = entitySectionStorage.getSectionKeys();
      LongIterator it = posLongSet.iterator();

      while(true) {
         long posLong;
         int chunkDistSq;
         do {
            C_290152_.C_290138_ renderChunk;
            do {
               do {
                  if (!it.hasNext()) {
                     return;
                  }

                  posLong = it.nextLong();
                  posM.setXyz(C_4710_.m_123223_(C_4710_.m_123213_(posLong)), C_4710_.m_123223_(C_4710_.m_123225_(posLong)), C_4710_.m_123223_(C_4710_.m_123230_(posLong)));
                  renderChunk = this.f_109469_.m_292642_(posM);
               } while(renderChunk == null);
            } while(!camera.m_113029_(renderChunk.m_293301_()));

            if (maxChunkDistance <= 0) {
               break;
            }

            C_4675_ posChunk = renderChunk.m_295500_();
            int dx = cameraChunkX - posChunk.u();
            int dy = cameraChunkY - posChunk.v();
            int dz = cameraChunkZ - posChunk.w();
            chunkDistSq = dx * dx + dy * dy + dz * dz;
         } while(chunkDistSq > maxChunkDistSq);

         this.renderInfosEntities.add(posLong);
      }
   }

   public void setShadowRenderInfos(boolean shadowInfos) {
      if (shadowInfos) {
         this.renderInfosTerrain = this.renderInfosTerrainShadow;
         this.renderInfosEntities = this.renderInfosEntitiesShadow;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
      } else {
         this.renderInfosTerrain = this.renderInfosTerrainNormal;
         this.renderInfosEntities = this.renderInfosEntitiesNormal;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
      }

   }

   public int getRenderedChunksShadow() {
      return !Config.isShadersShadows() ? -1 : this.renderInfosTerrainShadow.size();
   }

   public int getCountEntitiesRenderedShadow() {
      return !Config.isShadersShadows() ? -1 : ShadersRender.countEntitiesRenderedShadow;
   }

   public int getCountTileEntitiesRenderedShadow() {
      if (!Config.isShaders()) {
         return -1;
      } else {
         return !Shaders.hasShadowMap ? -1 : ShadersRender.countTileEntitiesRenderedShadow;
      }
   }

   public void captureFrustumShadow() {
      this.debugFixTerrainFrustumShadow = true;
   }

   public boolean isDebugFrustum() {
      return this.f_109442_ != null;
   }

   public void onChunkRenderNeedsUpdate(C_290152_.C_290138_ renderChunk) {
      if (!renderChunk.m_293175_().hasTerrainBlockEntities()) {
         ;
      }
   }

   public boolean needsFrustumUpdate() {
      return this.f_291822_.needsFrustumUpdate();
   }

   public boolean shouldRenderEntity(C_507_ entity, int minWorldY, int maxWorldY) {
      if (entity instanceof C_268383_) {
         return true;
      } else {
         C_4675_ posEntity = entity.m_20183_();
         return this.renderInfosEntities.contains(C_4710_.m_175568_(posEntity)) || posEntity.v() <= minWorldY || posEntity.v() >= maxWorldY;
      }
   }

   public C_4273_ getFrustum() {
      return this.f_109442_ != null ? this.f_109442_ : this.f_172938_;
   }

   public int getTicks() {
      return this.f_109477_;
   }

   public void m_109762_(Collection tileEntitiesToRemove, Collection tileEntitiesToAdd) {
      synchronized(this.f_109468_) {
         this.f_109468_.removeAll(tileEntitiesToRemove);
         this.f_109468_.addAll(tileEntitiesToAdd);
      }
   }

   public static int m_109541_(C_1557_ lightReaderIn, C_4675_ blockPosIn) {
      return m_109537_(lightReaderIn, lightReaderIn.a_(blockPosIn), blockPosIn);
   }

   public static int m_109537_(C_1557_ lightReaderIn, C_2064_ blockStateIn, C_4675_ blockPosIn) {
      if (EmissiveTextures.isRenderEmissive() && Config.isMinecraftThread()) {
         return C_4138_.MAX_BRIGHTNESS;
      } else if (blockStateIn.m_60788_(lightReaderIn, blockPosIn)) {
         return 15794417;
      } else {
         int i = lightReaderIn.m_45517_(C_1607_.SKY, blockPosIn);
         int j = lightReaderIn.m_45517_(C_1607_.BLOCK, blockPosIn);
         int k = blockStateIn.getLightValue(lightReaderIn, blockPosIn);
         if (j < k) {
            j = k;
         }

         int light = i << 20 | j << 4;
         if (Config.isDynamicLights() && lightReaderIn instanceof C_1559_ && (!ambientOcclusion || !blockStateIn.m_60804_(lightReaderIn, blockPosIn))) {
            light = DynamicLights.getCombinedLight((C_4675_)blockPosIn, light);
         }

         return light;
      }
   }

   public boolean m_292727_(C_4675_ blockPosIn) {
      C_290152_.C_290138_ sectionrenderdispatcher$rendersection = this.f_109469_.m_292642_(blockPosIn);
      return sectionrenderdispatcher$rendersection != null && sectionrenderdispatcher$rendersection.f_290312_.get() != C_290152_.C_290185_.f_290410_;
   }

   @Nullable
   public C_3106_ m_109827_() {
      return this.f_109411_;
   }

   @Nullable
   public C_3106_ m_109828_() {
      return this.f_109413_;
   }

   @Nullable
   public C_3106_ m_109829_() {
      return this.f_109414_;
   }

   @Nullable
   public C_3106_ m_109830_() {
      return this.f_109415_;
   }

   @Nullable
   public C_3106_ m_109831_() {
      return this.f_109416_;
   }

   @Nullable
   public C_3106_ m_109832_() {
      return this.f_109417_;
   }

   private void m_304955_(int dataIn, C_4675_ posIn, C_212974_ sourceIn, C_4761_ typeIn) {
      C_4687_ direction = C_4687_.m_122376_(dataIn);
      int i = direction.m_122429_();
      int j = direction.m_122430_();
      int k = direction.m_122431_();
      double d0 = (double)posIn.u() + (double)i * 0.6 + 0.5;
      double d1 = (double)posIn.v() + (double)j * 0.6 + 0.5;
      double d2 = (double)posIn.w() + (double)k * 0.6 + 0.5;

      for(int l = 0; l < 10; ++l) {
         double d3 = sourceIn.m_188500_() * 0.2 + 0.01;
         double d4 = d0 + (double)i * 0.01 + (sourceIn.m_188500_() - 0.5) * (double)k * 0.5;
         double d5 = d1 + (double)j * 0.01 + (sourceIn.m_188500_() - 0.5) * (double)j * 0.5;
         double d6 = d2 + (double)k * 0.01 + (sourceIn.m_188500_() - 0.5) * (double)i * 0.5;
         double d7 = (double)i * d3 + sourceIn.m_188583_() * 0.01;
         double d8 = (double)j * d3 + sourceIn.m_188583_() * 0.01;
         double d9 = (double)k * d3 + sourceIn.m_188583_() * 0.01;
         this.m_109735_(typeIn, d4, d5, d6, d7, d8, d9);
      }

   }

   public static class C_4137_ extends RuntimeException {
      public C_4137_(String nameIn, Throwable causeIn) {
         super(nameIn, causeIn);
      }
   }
}
