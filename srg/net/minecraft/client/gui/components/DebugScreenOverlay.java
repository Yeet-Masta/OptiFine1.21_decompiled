package net.minecraft.client.gui.components;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.datafixers.DataFixUtils;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.debugchart.BandwidthDebugChart;
import net.minecraft.client.gui.components.debugchart.FpsDebugChart;
import net.minecraft.client.gui.components.debugchart.PingDebugChart;
import net.minecraft.client.gui.components.debugchart.TpsDebugChart;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.Connection;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.debugchart.LocalSampleLogger;
import net.minecraft.util.debugchart.RemoteDebugSampleType;
import net.minecraft.util.debugchart.TpsDebugDimensions;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner.SpawnState;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.optifine.Config;
import net.optifine.Lagometer;
import net.optifine.SmartAnimations;
import net.optifine.TextureAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderCache;
import net.optifine.util.GpuMemory;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.NativeMemory;

public class DebugScreenOverlay {
   private static final int f_168988_ = 14737632;
   private static final int f_168989_ = 2;
   private static final int f_168990_ = 2;
   private static final int f_168991_ = 2;
   private static final Map<Types, String> f_94029_ = Util.m_137469_(new EnumMap(Types.class), mapIn -> {
      mapIn.put(Types.WORLD_SURFACE_WG, "SW");
      mapIn.put(Types.WORLD_SURFACE, "S");
      mapIn.put(Types.OCEAN_FLOOR_WG, "OW");
      mapIn.put(Types.OCEAN_FLOOR, "O");
      mapIn.put(Types.MOTION_BLOCKING, "M");
      mapIn.put(Types.MOTION_BLOCKING_NO_LEAVES, "ML");
   });
   private final Minecraft f_94030_;
   private final DebugScreenOverlay.AllocationRateCalculator f_232506_;
   private final Font f_94031_;
   private HitResult f_94032_;
   private HitResult f_94033_;
   @Nullable
   private ChunkPos f_94034_;
   @Nullable
   private LevelChunk f_94035_;
   @Nullable
   private CompletableFuture<LevelChunk> f_94036_;
   public boolean f_291101_;
   public boolean f_291871_;
   public boolean f_290551_;
   public boolean f_291005_;
   private final LocalSampleLogger f_291039_ = new LocalSampleLogger(1);
   private final LocalSampleLogger f_291524_ = new LocalSampleLogger(TpsDebugDimensions.values().length);
   private final LocalSampleLogger f_290653_ = new LocalSampleLogger(1);
   private final LocalSampleLogger f_290862_ = new LocalSampleLogger(1);
   private final Map<RemoteDebugSampleType, LocalSampleLogger> f_314774_ = Map.of(RemoteDebugSampleType.TICK_TIME, this.f_291524_);
   private final FpsDebugChart f_291857_;
   private final TpsDebugChart f_291179_;
   private final PingDebugChart f_291349_;
   private final BandwidthDebugChart f_291508_;
   private String debugOF = null;
   private static final Pattern PATTERN_DEBUG_SPACING = Pattern.compile("(\\d|f|c)(fa)");
   private RenderCache renderCache = new RenderCache(100L);

   public DebugScreenOverlay(Minecraft mc) {
      this.f_94030_ = mc;
      this.f_232506_ = new DebugScreenOverlay.AllocationRateCalculator();
      this.f_94031_ = mc.f_91062_;
      this.f_291857_ = new FpsDebugChart(this.f_94031_, this.f_291039_);
      this.f_291179_ = new TpsDebugChart(this.f_94031_, this.f_291524_, () -> mc.f_91073_.m_304826_().m_305111_());
      this.f_291349_ = new PingDebugChart(this.f_94031_, this.f_290653_);
      this.f_291508_ = new BandwidthDebugChart(this.f_94031_, this.f_290862_);
   }

   public void m_94040_() {
      this.f_94036_ = null;
      this.f_94035_ = null;
   }

   public void m_94056_(GuiGraphics graphicsIn) {
      this.f_94030_.m_91307_().m_6180_("debug");
      this.update();
      graphicsIn.m_286007_(() -> {
         this.drawText(graphicsIn);
         this.drawFPSCharts(graphicsIn);
         this.drawNetworkCharts(graphicsIn);
      });
      this.f_94030_.m_91307_().m_7238_();
   }

   protected void update() {
      Entity entity = this.f_94030_.m_91288_();
      this.f_94032_ = entity.m_19907_(20.0, 0.0F, false);
      this.f_94033_ = entity.m_19907_(20.0, 0.0F, true);
   }

   protected void drawText(GuiGraphics graphicsIn) {
      if (!this.renderCache.drawCached(graphicsIn)) {
         this.renderCache.startRender(graphicsIn);
         this.m_280186_(graphicsIn);
         this.m_280532_(graphicsIn);
         this.renderCache.stopRender(graphicsIn);
      }
   }

   protected void drawFPSCharts(GuiGraphics graphicsIn) {
      if (this.f_290551_) {
         graphicsIn.m_280168_().m_85836_();
         graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, 400.0F);
         int i = graphicsIn.m_280182_();
         int j = i / 2;
         Lagometer.renderLagometer(graphicsIn, (int)this.f_94030_.m_91268_().m_85449_());
         if (this.f_291524_.m_322219_() > 0) {
            int k = this.f_291179_.m_295014_(j);
            this.f_291179_.m_293623_(graphicsIn, i - k, k);
         }

         graphicsIn.m_280168_().m_85849_();
      }
   }

   protected void drawNetworkCharts(GuiGraphics graphicsIn) {
      if (this.f_291005_) {
         graphicsIn.m_280168_().m_85836_();
         graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, 400.0F);
         int l = graphicsIn.m_280182_();
         int i1 = l / 2;
         if (!this.f_94030_.m_91090_()) {
            this.f_291508_.m_293623_(graphicsIn, 0, this.f_291508_.m_295014_(i1));
         }

         int j1 = this.f_291349_.m_295014_(i1);
         this.f_291349_.m_293623_(graphicsIn, l - j1, j1);
         graphicsIn.m_280168_().m_85849_();
      }
   }

   protected void m_280186_(GuiGraphics graphicsIn) {
      List<String> list = this.m_94075_();
      list.addAll(this.getOverlayHelp());
      this.m_286013_(graphicsIn, list, true);
   }

   protected List<String> getOverlayHelp() {
      List<String> list = Lists.newArrayList();
      list.add("");
      boolean flag = this.f_94030_.m_91092_() != null;
      list.add(
         "Debug charts: [F3+1] Profiler "
            + (this.f_291871_ ? "visible" : "hidden")
            + "; [F3+2] "
            + (flag ? "FPS + TPS " : "FPS ")
            + (this.f_290551_ ? "visible" : "hidden")
            + "; [F3+3] "
            + (!this.f_94030_.m_91090_() ? "Bandwidth + Ping" : "Ping")
            + (this.f_291005_ ? " visible" : " hidden")
      );
      list.add("For help: press F3 + Q");
      return list;
   }

   protected void m_280532_(GuiGraphics graphicsIn) {
      graphicsIn.m_280168_().m_85836_();
      graphicsIn.m_280168_().m_252880_(0.0F, 0.0F, -10.0F);
      List<String> list = this.m_94078_();
      this.m_286013_(graphicsIn, list, false);
      graphicsIn.m_280168_().m_85849_();
   }

   private void m_286013_(GuiGraphics graphicsIn, List<String> linesIn, boolean leftIn) {
      int i = 9;

      for (int j = 0; j < linesIn.size(); j++) {
         String s = (String)linesIn.get(j);
         if (!Strings.isNullOrEmpty(s)) {
            int k = this.f_94031_.m_92895_(s);
            int l = leftIn ? 2 : graphicsIn.m_280182_() - 2 - k;
            int i1 = 2 + i * j;
            graphicsIn.m_280509_(l - 1, i1 - 1, l + k + 1, i1 + i - 1, -1873784752);
         }
      }

      for (int j1 = 0; j1 < linesIn.size(); j1++) {
         String s1 = (String)linesIn.get(j1);
         if (!Strings.isNullOrEmpty(s1)) {
            int k1 = this.f_94031_.m_92895_(s1);
            int l1 = leftIn ? 2 : graphicsIn.m_280182_() - 2 - k1;
            int i2 = 2 + i * j1;
            graphicsIn.m_280056_(this.f_94031_, s1, l1, i2, 14737632, false);
         }
      }
   }

   protected List<String> m_94075_() {
      if (this.f_94030_.f_90977_ != this.debugOF) {
         StringBuffer sb = new StringBuffer(this.f_94030_.f_90977_);
         Matcher m = PATTERN_DEBUG_SPACING.matcher(this.f_94030_.f_90977_);
         if (m.find()) {
            sb.insert(m.start(2), ' ');
         }

         int chunkUpdates = Config.getChunkUpdates();
         int posT = this.f_94030_.f_90977_.indexOf("T: ");
         if (posT >= 0) {
            sb.insert(posT, "(" + chunkUpdates + " updates) ");
         }

         int fpsMin = Config.getFpsMin();
         int posFps = this.f_94030_.f_90977_.indexOf(" fps ");
         if (posFps >= 0) {
            sb.replace(0, posFps + 4, Config.getFpsString());
         }

         sb.append("\u00a7r");
         if (Config.isSmoothFps()) {
            sb.append(" sf");
         }

         if (Config.isFastRender()) {
            sb.append(" fr");
         }

         if (Config.isAnisotropicFiltering()) {
            sb.append(" af");
         }

         if (Config.isAntialiasing()) {
            sb.append(" aa");
         }

         if (Config.isRenderRegions()) {
            sb.append(" rr");
         }

         if (Config.isShaders()) {
            sb.append(" sh");
         }

         this.f_94030_.f_90977_ = sb.toString();
         this.debugOF = this.f_94030_.f_90977_;
      }

      List<String> list = this.getInfoLeft();
      StringBuilder sbx = new StringBuilder();
      TextureAtlas tm = Config.getTextureMap();
      sbx.append(", A: ");
      if (SmartAnimations.isActive()) {
         sbx.append(tm.getCountAnimationsActive() + TextureAnimations.getCountAnimationsActive());
         sbx.append("/");
      }

      sbx.append(tm.getCountAnimations() + TextureAnimations.getCountAnimations());
      String ofInfo = sbx.toString();
      String ofInfoShadow = null;
      if (Config.isShadersShadows()) {
         int renderersShadow = this.f_94030_.f_91060_.getRenderedChunksShadow();
         int entitiesShadow = this.f_94030_.f_91060_.getCountEntitiesRenderedShadow();
         int tileEntitiesShadow = this.f_94030_.f_91060_.getCountTileEntitiesRenderedShadow();
         ofInfoShadow = "Shadow C: " + renderersShadow + ", E: " + entitiesShadow + "+" + tileEntitiesShadow;
      }

      for (int i = 0; i < list.size(); i++) {
         String line = (String)list.get(i);
         if (line != null && line.startsWith("P: ")) {
            line = line + ofInfo;
            list.set(i, line);
            if (ofInfoShadow != null) {
               list.add(i + 1, ofInfoShadow);
            }
            break;
         }
      }

      return list;
   }

   protected List<String> getInfoLeft() {
      IntegratedServer integratedserver = this.f_94030_.m_91092_();
      ClientPacketListener clientpacketlistener = this.f_94030_.m_91403_();
      Connection connection = clientpacketlistener.m_104910_();
      float f = connection.m_129543_();
      float f1 = connection.m_129542_();
      TickRateManager tickratemanager = this.m_94083_().m_304826_();
      String s1;
      if (tickratemanager.m_307006_()) {
         s1 = " (frozen - stepping)";
      } else if (tickratemanager.m_306363_()) {
         s1 = " (frozen)";
      } else {
         s1 = "";
      }

      String s;
      if (integratedserver != null) {
         ServerTickRateManager servertickratemanager = integratedserver.m_306290_();
         boolean flag = servertickratemanager.m_306078_();
         if (flag) {
            s1 = " (sprinting)";
         }

         String s2 = flag ? "-" : String.format(Locale.ROOT, "%.1f", tickratemanager.m_305111_());
         s = String.format(Locale.ROOT, "Integrated server @ %.1f/%s ms%s, %.0f tx, %.0f rx", integratedserver.m_304767_(), s2, s1, f, f1);
      } else {
         s = String.format(Locale.ROOT, "\"%s\" server%s, %.0f tx, %.0f rx", clientpacketlistener.m_295034_(), s1, f, f1);
      }

      BlockPos blockpos = this.f_94030_.m_91288_().m_20183_();
      if (this.f_94030_.m_91299_()) {
         return Lists.newArrayList(
            new String[]{
               "Minecraft " + SharedConstants.m_183709_().m_132493_() + " (" + this.f_94030_.m_91388_() + "/" + ClientBrandRetriever.getClientModName() + ")",
               this.f_94030_.f_90977_,
               s,
               this.f_94030_.f_91060_.m_109820_(),
               this.f_94030_.f_91060_.m_109822_(),
               "P: " + this.f_94030_.f_91061_.m_107403_() + ". T: " + this.f_94030_.f_91073_.m_104813_(),
               this.f_94030_.f_91073_.m_46464_(),
               "",
               String.format(Locale.ROOT, "Chunk-relative: %d %d %d", blockpos.m_123341_() & 15, blockpos.m_123342_() & 15, blockpos.m_123343_() & 15)
            }
         );
      } else {
         Entity entity = this.f_94030_.m_91288_();
         Direction direction = entity.m_6350_();

         String $$21 = switch (direction) {
            case NORTH -> "Towards negative Z";
            case SOUTH -> "Towards positive Z";
            case WEST -> "Towards negative X";
            case EAST -> "Towards positive X";
            default -> "Invalid";
         };
         ChunkPos chunkpos = new ChunkPos(blockpos);
         if (!Objects.equals(this.f_94034_, chunkpos)) {
            this.f_94034_ = chunkpos;
            this.m_94040_();
         }

         Level level = this.m_94083_();
         LongSet longset = (LongSet)(level instanceof ServerLevel ? ((ServerLevel)level).m_8902_() : LongSets.EMPTY_SET);
         List<String> list = Lists.newArrayList(
            new String[]{
               "Minecraft "
                  + SharedConstants.m_183709_().m_132493_()
                  + " ("
                  + this.f_94030_.m_91388_()
                  + "/"
                  + ClientBrandRetriever.getClientModName()
                  + ("release".equalsIgnoreCase(this.f_94030_.m_91389_()) ? "" : "/" + this.f_94030_.m_91389_())
                  + ")",
               this.f_94030_.f_90977_,
               s,
               this.f_94030_.f_91060_.m_109820_(),
               this.f_94030_.f_91060_.m_109822_(),
               "P: " + this.f_94030_.f_91061_.m_107403_() + ". T: " + this.f_94030_.f_91073_.m_104813_(),
               this.f_94030_.f_91073_.m_46464_()
            }
         );
         String s4 = this.m_94082_();
         if (s4 != null) {
            list.add(s4);
         }

         list.add(this.f_94030_.f_91073_.m_46472_().m_135782_() + " FC: " + longset.size());
         list.add("");
         list.add(
            String.format(
               Locale.ROOT,
               "XYZ: %.3f / %.5f / %.3f",
               this.f_94030_.m_91288_().m_20185_(),
               this.f_94030_.m_91288_().m_20186_(),
               this.f_94030_.m_91288_().m_20189_()
            )
         );
         list.add(
            String.format(
               Locale.ROOT,
               "Block: %d %d %d [%d %d %d]",
               blockpos.m_123341_(),
               blockpos.m_123342_(),
               blockpos.m_123343_(),
               blockpos.m_123341_() & 15,
               blockpos.m_123342_() & 15,
               blockpos.m_123343_() & 15
            )
         );
         list.add(
            String.format(
               Locale.ROOT,
               "Chunk: %d %d %d [%d %d in r.%d.%d.mca]",
               chunkpos.f_45578_,
               SectionPos.m_123171_(blockpos.m_123342_()),
               chunkpos.f_45579_,
               chunkpos.m_45613_(),
               chunkpos.m_45614_(),
               chunkpos.m_45610_(),
               chunkpos.m_45612_()
            )
         );
         list.add(
            String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, $$21, Mth.m_14177_(entity.m_146908_()), Mth.m_14177_(entity.m_146909_()))
         );
         LevelChunk levelchunk = this.m_94085_();
         if (levelchunk.m_6430_()) {
            list.add("Waiting for chunk...");
         } else {
            int i = this.f_94030_.f_91073_.m_7726_().m_7827_().m_75831_(blockpos, 0);
            int j = this.f_94030_.f_91073_.m_45517_(LightLayer.SKY, blockpos);
            int k = this.f_94030_.f_91073_.m_45517_(LightLayer.BLOCK, blockpos);
            list.add("Client Light: " + i + " (" + j + " sky, " + k + " block)");
            LevelChunk levelchunk1 = this.m_94084_();
            StringBuilder stringbuilder = new StringBuilder("CH");

            for (Types heightmap$types : Types.values()) {
               if (heightmap$types.m_64297_()) {
                  stringbuilder.append(" ")
                     .append((String)f_94029_.get(heightmap$types))
                     .append(": ")
                     .append(levelchunk.m_5885_(heightmap$types, blockpos.m_123341_(), blockpos.m_123343_()));
               }
            }

            list.add(stringbuilder.toString());
            stringbuilder.setLength(0);
            stringbuilder.append("SH");

            for (Types heightmap$types1 : Types.values()) {
               if (heightmap$types1.m_64298_()) {
                  stringbuilder.append(" ").append((String)f_94029_.get(heightmap$types1)).append(": ");
                  if (levelchunk1 != null) {
                     stringbuilder.append(levelchunk1.m_5885_(heightmap$types1, blockpos.m_123341_(), blockpos.m_123343_()));
                  } else {
                     stringbuilder.append("??");
                  }
               }
            }

            list.add(stringbuilder.toString());
            if (blockpos.m_123342_() >= this.f_94030_.f_91073_.m_141937_() && blockpos.m_123342_() < this.f_94030_.f_91073_.m_151558_()) {
               list.add("Biome: " + m_205374_(this.f_94030_.f_91073_.m_204166_(blockpos)));
               if (levelchunk1 != null) {
                  float f2 = level.m_46940_();
                  long l = levelchunk1.m_6319_();
                  DifficultyInstance difficultyinstance = new DifficultyInstance(level.m_46791_(), level.m_46468_(), l, f2);
                  list.add(
                     String.format(
                        Locale.ROOT,
                        "Local Difficulty: %.2f // %.2f (Day %d)",
                        difficultyinstance.m_19056_(),
                        difficultyinstance.m_19057_(),
                        this.f_94030_.f_91073_.m_46468_() / 24000L
                     )
                  );
               } else {
                  list.add("Local Difficulty: ??");
               }
            }

            if (levelchunk1 != null && levelchunk1.m_187675_()) {
               list.add("Blending: Old");
            }
         }

         ServerLevel serverlevel = this.m_94081_();
         if (serverlevel != null) {
            ServerChunkCache serverchunkcache = serverlevel.m_7726_();
            ChunkGenerator chunkgenerator = serverchunkcache.m_8481_();
            RandomState randomstate = serverchunkcache.m_214994_();
            chunkgenerator.m_213600_(list, randomstate, blockpos);
            Sampler climate$sampler = randomstate.m_224579_();
            BiomeSource biomesource = chunkgenerator.m_62218_();
            biomesource.m_207301_(list, blockpos, climate$sampler);
            SpawnState naturalspawner$spawnstate = serverchunkcache.m_8485_();
            if (naturalspawner$spawnstate != null) {
               Object2IntMap<MobCategory> object2intmap = naturalspawner$spawnstate.m_47148_();
               int i1 = naturalspawner$spawnstate.m_47126_();
               list.add(
                  "SC: "
                     + i1
                     + ", "
                     + (String)Stream.of(MobCategory.values())
                        .map(categoryIn -> Character.toUpperCase(categoryIn.m_21607_().charAt(0)) + ": " + object2intmap.getInt(categoryIn))
                        .collect(Collectors.joining(", "))
               );
            } else {
               list.add("SC: N/A");
            }
         }

         PostChain postchain = this.f_94030_.f_91063_.m_109149_();
         if (postchain != null) {
            list.add("Shader: " + postchain.m_110022_());
         }

         list.add(this.f_94030_.m_91106_().m_120408_() + String.format(Locale.ROOT, " (Mood %d%%)", Math.round(this.f_94030_.f_91074_.m_108762_() * 100.0F)));
         return list;
      }
   }

   private static String m_205374_(Holder<Biome> biomeHolderIn) {
      return (String)biomeHolderIn.m_203439_().map(keyIn -> keyIn.m_135782_().toString(), p_317322_0_ -> "[unregistered " + p_317322_0_ + "]");
   }

   @Nullable
   private ServerLevel m_94081_() {
      IntegratedServer integratedserver = this.f_94030_.m_91092_();
      return integratedserver != null ? integratedserver.m_129880_(this.f_94030_.f_91073_.m_46472_()) : null;
   }

   @Nullable
   private String m_94082_() {
      ServerLevel serverlevel = this.m_94081_();
      return serverlevel != null ? serverlevel.m_46464_() : null;
   }

   private Level m_94083_() {
      return (Level)DataFixUtils.orElse(
         Optional.ofNullable(this.f_94030_.m_91092_()).flatMap(serverIn -> Optional.ofNullable(serverIn.m_129880_(this.f_94030_.f_91073_.m_46472_()))),
         this.f_94030_.f_91073_
      );
   }

   @Nullable
   private LevelChunk m_94084_() {
      if (this.f_94036_ == null) {
         ServerLevel serverlevel = this.m_94081_();
         if (serverlevel == null) {
            return null;
         }

         this.f_94036_ = serverlevel.m_7726_()
            .m_8431_(this.f_94034_.f_45578_, this.f_94034_.f_45579_, ChunkStatus.f_315432_, false)
            .thenApply(chunkIn -> (LevelChunk)chunkIn.m_318814_(null));
      }

      return (LevelChunk)this.f_94036_.getNow(null);
   }

   private LevelChunk m_94085_() {
      if (this.f_94035_ == null) {
         this.f_94035_ = this.f_94030_.f_91073_.m_6325_(this.f_94034_.f_45578_, this.f_94034_.f_45579_);
      }

      return this.f_94035_;
   }

   protected List<String> m_94078_() {
      long i = Runtime.getRuntime().maxMemory();
      long j = Runtime.getRuntime().totalMemory();
      long k = Runtime.getRuntime().freeMemory();
      long l = j - k;
      List<String> list = Lists.newArrayList(
         new String[]{
            String.format(Locale.ROOT, "Java: %s", System.getProperty("java.version")),
            String.format(Locale.ROOT, "Mem: %2d%% %03d/%03dMB", l * 100L / i, m_94050_(l), m_94050_(i)),
            String.format(Locale.ROOT, "Allocation rate: %dMB/s", MemoryMonitor.getAllocationRateAvgMb()),
            String.format(Locale.ROOT, "Allocated: %2d%% %03dMB", j * 100L / i, m_94050_(j)),
            "",
            String.format(Locale.ROOT, "CPU: %s", GlUtil.m_84819_()),
            "",
            String.format(
               Locale.ROOT, "Display: %dx%d (%s)", Minecraft.m_91087_().m_91268_().m_85441_(), Minecraft.m_91087_().m_91268_().m_85442_(), GlUtil.m_84818_()
            ),
            GlUtil.m_84820_(),
            GlUtil.m_84821_()
         }
      );
      long bufferAllocated = NativeMemory.getBufferAllocated();
      long bufferMaximum = NativeMemory.getBufferMaximum();
      long imageAllocated = NativeMemory.getImageAllocated();
      String strNative = "Native: " + m_94050_(bufferAllocated) + "/" + m_94050_(bufferMaximum) + "+" + m_94050_(imageAllocated) + "MB";
      list.add(3, strNative);
      long gpuBufferAllocated = GpuMemory.getBufferAllocated();
      long gpuTextureAllocated = GpuMemory.getTextureAllocated();
      list.set(4, "GPU: " + m_94050_(gpuBufferAllocated) + "+" + m_94050_(gpuTextureAllocated) + "MB");
      if (Reflector.BrandingControl_getBrandings.exists()) {
         list.add("");

         for (String line : (Collection)Reflector.call(Reflector.BrandingControl_getBrandings, true, false)) {
            if (!line.startsWith("Minecraft ")) {
               list.add(line);
            }
         }
      }

      if (this.f_94030_.m_91299_()) {
         return list;
      } else {
         if (this.f_94032_.m_6662_() == Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)this.f_94032_).m_82425_();
            BlockState blockstate = this.f_94030_.f_91073_.m_8055_(blockpos);
            list.add("");
            list.add(ChatFormatting.UNDERLINE + "Targeted Block: " + blockpos.m_123341_() + ", " + blockpos.m_123342_() + ", " + blockpos.m_123343_());
            list.add(String.valueOf(BuiltInRegistries.f_256975_.m_7981_(blockstate.m_60734_())));

            for (Entry<Property<?>, Comparable<?>> entry : blockstate.m_61148_().entrySet()) {
               list.add(this.m_94071_(entry));
            }

            blockstate.m_204343_().map(keyIn -> "#" + keyIn.f_203868_()).forEach(list::add);
         }

         if (this.f_94033_.m_6662_() == Type.BLOCK) {
            BlockPos blockpos1 = ((BlockHitResult)this.f_94033_).m_82425_();
            FluidState fluidstate = this.f_94030_.f_91073_.m_6425_(blockpos1);
            list.add("");
            list.add(ChatFormatting.UNDERLINE + "Targeted Fluid: " + blockpos1.m_123341_() + ", " + blockpos1.m_123342_() + ", " + blockpos1.m_123343_());
            list.add(String.valueOf(BuiltInRegistries.f_257020_.m_7981_(fluidstate.m_76152_())));

            for (Entry<Property<?>, Comparable<?>> entry1 : fluidstate.m_61148_().entrySet()) {
               list.add(this.m_94071_(entry1));
            }

            fluidstate.m_205075_().map(keyIn -> "#" + keyIn.f_203868_()).forEach(list::add);
         }

         Entity entity = this.f_94030_.f_91076_;
         if (entity != null) {
            list.add("");
            list.add(ChatFormatting.UNDERLINE + "Targeted Entity");
            list.add(String.valueOf(BuiltInRegistries.f_256780_.m_7981_(entity.m_6095_())));
            entity.m_6095_().m_204041_().m_203616_().forEach(t -> list.add("#" + t.f_203868_()));
         }

         return list;
      }
   }

   private String m_94071_(Entry<Property<?>, Comparable<?>> entryIn) {
      Property<?> property = (Property<?>)entryIn.getKey();
      Comparable<?> comparable = (Comparable<?>)entryIn.getValue();
      String s = Util.m_137453_(property, comparable);
      if (Boolean.TRUE.equals(comparable)) {
         s = ChatFormatting.GREEN + s;
      } else if (Boolean.FALSE.equals(comparable)) {
         s = ChatFormatting.RED + s;
      }

      return property.m_61708_() + ": " + s;
   }

   private static long m_94050_(long bytes) {
      return bytes / 1024L / 1024L;
   }

   public boolean m_294516_() {
      return this.f_291101_ && !this.f_94030_.f_91066_.f_92062_;
   }

   public boolean m_295669_() {
      return this.m_294516_() && this.f_291871_;
   }

   public boolean m_295643_() {
      return this.m_294516_() && this.f_291005_;
   }

   public boolean m_321865_() {
      return this.m_294516_() && this.f_290551_;
   }

   public void m_293034_() {
      this.f_291101_ = !this.f_291101_;
      if (this.f_291101_ && !this.f_290551_ && this.f_94030_.f_91066_.ofLagometer) {
         this.m_294611_();
      }

      if (this.f_291101_ && !this.f_291871_ && this.f_94030_.f_91066_.ofProfiler) {
         this.m_293481_();
      }
   }

   public void m_295292_() {
      this.f_291005_ = !this.f_291101_ || !this.f_291005_;
      if (this.f_291005_) {
         this.f_291101_ = true;
         this.f_290551_ = false;
      }
   }

   public void m_294611_() {
      this.f_290551_ = !this.f_291101_ || !this.f_290551_;
      if (this.f_290551_) {
         this.f_291101_ = true;
         this.f_291005_ = false;
      }
   }

   public void m_293481_() {
      this.f_291871_ = !this.f_291101_ || !this.f_291871_;
      if (this.f_291871_) {
         this.f_291101_ = true;
      }
   }

   public void m_294107_(long timeIn) {
      this.f_291039_.m_322732_(timeIn);
   }

   public LocalSampleLogger m_324537_() {
      return this.f_291524_;
   }

   public LocalSampleLogger m_294664_() {
      return this.f_290653_;
   }

   public LocalSampleLogger m_293273_() {
      return this.f_290862_;
   }

   public void m_323707_(long[] sampleIn, RemoteDebugSampleType typeIn) {
      LocalSampleLogger localsamplelogger = (LocalSampleLogger)this.f_314774_.get(typeIn);
      if (localsamplelogger != null) {
         localsamplelogger.m_320889_(sampleIn);
      }
   }

   public void m_294940_() {
      this.f_291101_ = false;
      this.f_291524_.m_320406_();
      this.f_290653_.m_320406_();
      this.f_290862_.m_320406_();
   }

   public LocalSampleLogger getFrameTimeLogger() {
      return this.f_291039_;
   }

   static class AllocationRateCalculator {
      private static final int f_232507_ = 500;
      private static final List<GarbageCollectorMXBean> f_232508_ = ManagementFactory.getGarbageCollectorMXBeans();
      private long f_232509_ = 0L;
      private long f_232510_ = -1L;
      private long f_232511_ = -1L;
      private long f_232512_ = 0L;

      long m_232516_(long heapUsageIn) {
         long i = System.currentTimeMillis();
         if (i - this.f_232509_ < 500L) {
            return this.f_232512_;
         } else {
            long j = m_232515_();
            if (this.f_232509_ != 0L && j == this.f_232511_) {
               double d0 = (double)TimeUnit.SECONDS.toMillis(1L) / (double)(i - this.f_232509_);
               long k = heapUsageIn - this.f_232510_;
               this.f_232512_ = Math.round((double)k * d0);
            }

            this.f_232509_ = i;
            this.f_232510_ = heapUsageIn;
            this.f_232511_ = j;
            return this.f_232512_;
         }
      }

      private static long m_232515_() {
         long i = 0L;

         for (GarbageCollectorMXBean garbagecollectormxbean : f_232508_) {
            i += garbagecollectormxbean.getCollectionCount();
         }

         return i;
      }
   }
}
