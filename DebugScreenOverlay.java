import com.google.common.base.Strings;
import com.google.common.collect.Lists;
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
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.src.C_12_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1607_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1644_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_2118_;
import net.minecraft.src.C_213138_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_290070_;
import net.minecraft.src.C_290133_;
import net.minecraft.src.C_290197_;
import net.minecraft.src.C_290213_;
import net.minecraft.src.C_302003_;
import net.minecraft.src.C_3041_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_313429_;
import net.minecraft.src.C_313522_;
import net.minecraft.src.C_313536_;
import net.minecraft.src.C_313554_;
import net.minecraft.src.C_3139_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3902_;
import net.minecraft.src.C_4145_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_469_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_4961_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5285_;
import net.minecraft.src.C_528_;
import net.minecraft.src.C_8_;
import net.minecraft.src.C_1608_.C_1613_;
import net.minecraft.src.C_182857_.C_182866_;
import net.minecraft.src.C_2190_.C_2191_;
import net.minecraft.src.C_3043_.C_3044_;
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
   private static final int a = 14737632;
   private static final int b = 2;
   private static final int c = 2;
   private static final int d = 2;
   private static final Map<C_2191_, String> e = Util.a(new EnumMap(C_2191_.class), mapIn -> {
      mapIn.put(C_2191_.WORLD_SURFACE_WG, "SW");
      mapIn.put(C_2191_.WORLD_SURFACE, "S");
      mapIn.put(C_2191_.OCEAN_FLOOR_WG, "OW");
      mapIn.put(C_2191_.OCEAN_FLOOR, "O");
      mapIn.put(C_2191_.MOTION_BLOCKING, "M");
      mapIn.put(C_2191_.MOTION_BLOCKING_NO_LEAVES, "ML");
   });
   private final C_3391_ f;
   private final DebugScreenOverlay.a g;
   private final Font h;
   private C_3043_ i;
   private C_3043_ j;
   @Nullable
   private ChunkPos k;
   @Nullable
   private C_2137_ l;
   @Nullable
   private CompletableFuture<C_2137_> m;
   public boolean n;
   public boolean o;
   public boolean p;
   public boolean q;
   private final C_313429_ r = new C_313429_(1);
   private final C_313429_ s = new C_313429_(C_313522_.values().length);
   private final C_313429_ t = new C_313429_(1);
   private final C_313429_ u = new C_313429_(1);
   private final Map<C_313536_, C_313429_> v = Map.of(C_313536_.TICK_TIME, this.s);
   private final C_290213_ w;
   private final C_290133_ x;
   private final C_290197_ y;
   private final C_290070_ z;
   private String debugOF = null;
   private static final Pattern PATTERN_DEBUG_SPACING = Pattern.compile("(\\d|f|c)(fa)");
   private RenderCache renderCache = new RenderCache(100L);

   public DebugScreenOverlay(C_3391_ mc) {
      this.f = mc;
      this.g = new DebugScreenOverlay.a();
      this.h = mc.h;
      this.w = new C_290213_(this.h, this.r);
      this.x = new C_290133_(this.h, this.s, () -> mc.r.s().g());
      this.y = new C_290197_(this.h, this.t);
      this.z = new C_290070_(this.h, this.u);
   }

   public void a() {
      this.m = null;
      this.l = null;
   }

   public void a(GuiGraphics graphicsIn) {
      this.f.m_91307_().m_6180_("debug");
      this.update();
      graphicsIn.a(() -> {
         this.drawText(graphicsIn);
         this.drawFPSCharts(graphicsIn);
         this.drawNetworkCharts(graphicsIn);
      });
      this.f.m_91307_().m_7238_();
   }

   protected void update() {
      C_507_ entity = this.f.m_91288_();
      this.i = entity.m_19907_(20.0, 0.0F, false);
      this.j = entity.m_19907_(20.0, 0.0F, true);
   }

   protected void drawText(GuiGraphics graphicsIn) {
      if (!this.renderCache.drawCached(graphicsIn)) {
         this.renderCache.startRender(graphicsIn);
         this.b(graphicsIn);
         this.c(graphicsIn);
         this.renderCache.stopRender(graphicsIn);
      }
   }

   protected void drawFPSCharts(GuiGraphics graphicsIn) {
      if (this.p) {
         graphicsIn.c().a();
         graphicsIn.c().a(0.0F, 0.0F, 400.0F);
         int i = graphicsIn.a();
         int j = i / 2;
         Lagometer.renderLagometer(graphicsIn, (int)this.f.aM().t());
         if (this.s.m_322219_() > 0) {
            int k = this.x.a(j);
            this.x.a(graphicsIn, i - k, k);
         }

         graphicsIn.c().b();
      }
   }

   protected void drawNetworkCharts(GuiGraphics graphicsIn) {
      if (this.q) {
         graphicsIn.c().a();
         graphicsIn.c().a(0.0F, 0.0F, 400.0F);
         int l = graphicsIn.a();
         int i1 = l / 2;
         if (!this.f.m_91090_()) {
            this.z.a(graphicsIn, 0, this.z.a(i1));
         }

         int j1 = this.y.a(i1);
         this.y.a(graphicsIn, l - j1, j1);
         graphicsIn.c().b();
      }
   }

   protected void b(GuiGraphics graphicsIn) {
      List<String> list = this.b();
      list.addAll(this.getOverlayHelp());
      this.a(graphicsIn, list, true);
   }

   protected List<String> getOverlayHelp() {
      List<String> list = Lists.newArrayList();
      list.add("");
      boolean flag = this.f.V() != null;
      list.add(
         "Debug charts: [F3+1] Profiler "
            + (this.o ? "visible" : "hidden")
            + "; [F3+2] "
            + (flag ? "FPS + TPS " : "FPS ")
            + (this.p ? "visible" : "hidden")
            + "; [F3+3] "
            + (!this.f.m_91090_() ? "Bandwidth + Ping" : "Ping")
            + (this.q ? " visible" : " hidden")
      );
      list.add("For help: press F3 + Q");
      return list;
   }

   protected void c(GuiGraphics graphicsIn) {
      graphicsIn.c().a();
      graphicsIn.c().a(0.0F, 0.0F, -10.0F);
      List<String> list = this.c();
      this.a(graphicsIn, list, false);
      graphicsIn.c().b();
   }

   private void a(GuiGraphics graphicsIn, List<String> linesIn, boolean leftIn) {
      int i = 9;

      for (int j = 0; j < linesIn.size(); j++) {
         String s = (String)linesIn.get(j);
         if (!Strings.isNullOrEmpty(s)) {
            int k = this.h.b(s);
            int l = leftIn ? 2 : graphicsIn.a() - 2 - k;
            int i1 = 2 + i * j;
            graphicsIn.a(l - 1, i1 - 1, l + k + 1, i1 + i - 1, -1873784752);
         }
      }

      for (int j1 = 0; j1 < linesIn.size(); j1++) {
         String s1 = (String)linesIn.get(j1);
         if (!Strings.isNullOrEmpty(s1)) {
            int k1 = this.h.b(s1);
            int l1 = leftIn ? 2 : graphicsIn.a() - 2 - k1;
            int i2 = 2 + i * j1;
            graphicsIn.a(this.h, s1, l1, i2, 14737632, false);
         }
      }
   }

   protected List<String> b() {
      if (this.f.f_90977_ != this.debugOF) {
         StringBuffer sb = new StringBuffer(this.f.f_90977_);
         Matcher m = PATTERN_DEBUG_SPACING.matcher(this.f.f_90977_);
         if (m.find()) {
            sb.insert(m.start(2), ' ');
         }

         int chunkUpdates = Config.getChunkUpdates();
         int posT = this.f.f_90977_.indexOf("T: ");
         if (posT >= 0) {
            sb.insert(posT, "(" + chunkUpdates + " updates) ");
         }

         int fpsMin = Config.getFpsMin();
         int posFps = this.f.f_90977_.indexOf(" fps ");
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

         this.f.f_90977_ = sb.toString();
         this.debugOF = this.f.f_90977_;
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
         int renderersShadow = this.f.f.getRenderedChunksShadow();
         int entitiesShadow = this.f.f.getCountEntitiesRenderedShadow();
         int tileEntitiesShadow = this.f.f.getCountTileEntitiesRenderedShadow();
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
      IntegratedServer integratedserver = this.f.V();
      C_3902_ clientpacketlistener = this.f.m_91403_();
      C_4961_ connection = clientpacketlistener.m_104910_();
      float f = connection.m_129543_();
      float f1 = connection.m_129542_();
      TickRateManager tickratemanager = this.r().s();
      String s1;
      if (tickratemanager.j()) {
         s1 = " (frozen - stepping)";
      } else if (tickratemanager.l()) {
         s1 = " (frozen)";
      } else {
         s1 = "";
      }

      String s;
      if (integratedserver != null) {
         C_302003_ servertickratemanager = integratedserver.m_306290_();
         boolean flag = servertickratemanager.m_306078_();
         if (flag) {
            s1 = " (sprinting)";
         }

         String s2 = flag ? "-" : String.format(Locale.ROOT, "%.1f", tickratemanager.g());
         s = String.format(Locale.ROOT, "Integrated server @ %.1f/%s ms%s, %.0f tx, %.0f rx", integratedserver.m_304767_(), s2, s1, f, f1);
      } else {
         s = String.format(Locale.ROOT, "\"%s\" server%s, %.0f tx, %.0f rx", clientpacketlistener.m_295034_(), s1, f, f1);
      }

      C_4675_ blockpos = this.f.m_91288_().m_20183_();
      if (this.f.m_91299_()) {
         return Lists.newArrayList(
            new String[]{
               "Minecraft " + C_5285_.m_183709_().m_132493_() + " (" + this.f.m_91388_() + "/" + ClientBrandRetriever.getClientModName() + ")",
               this.f.f_90977_,
               s,
               this.f.f.g(),
               this.f.f.l(),
               "P: " + this.f.g.d() + ". T: " + this.f.r.h(),
               this.f.r.m_46464_(),
               "",
               String.format(Locale.ROOT, "Chunk-relative: %d %d %d", blockpos.m_123341_() & 15, blockpos.m_123342_() & 15, blockpos.m_123343_() & 15)
            }
         );
      } else {
         C_507_ entity = this.f.m_91288_();
         Direction direction = entity.cH();

         String $$21 = switch (direction) {
            case c -> "Towards negative Z";
            case d -> "Towards positive Z";
            case e -> "Towards negative X";
            case f -> "Towards positive X";
            default -> "Invalid";
         };
         ChunkPos chunkpos = new ChunkPos(blockpos);
         if (!Objects.equals(this.k, chunkpos)) {
            this.k = chunkpos;
            this.a();
         }

         C_1596_ level = this.r();
         LongSet longset = (LongSet)(level instanceof C_12_ ? ((C_12_)level).m_8902_() : LongSets.EMPTY_SET);
         List<String> list = Lists.newArrayList(
            new String[]{
               "Minecraft "
                  + C_5285_.m_183709_().m_132493_()
                  + " ("
                  + this.f.m_91388_()
                  + "/"
                  + ClientBrandRetriever.getClientModName()
                  + ("release".equalsIgnoreCase(this.f.m_91389_()) ? "" : "/" + this.f.m_91389_())
                  + ")",
               this.f.f_90977_,
               s,
               this.f.f.g(),
               this.f.f.l(),
               "P: " + this.f.g.d() + ". T: " + this.f.r.h(),
               this.f.r.m_46464_()
            }
         );
         String s4 = this.q();
         if (s4 != null) {
            list.add(s4);
         }

         list.add(this.f.r.m_46472_().a() + " FC: " + longset.size());
         list.add("");
         list.add(
            String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.f.m_91288_().m_20185_(), this.f.m_91288_().m_20186_(), this.f.m_91288_().m_20189_())
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
               chunkpos.e,
               C_4710_.m_123171_(blockpos.m_123342_()),
               chunkpos.f,
               chunkpos.j(),
               chunkpos.k(),
               chunkpos.h(),
               chunkpos.i()
            )
         );
         list.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, $$21, Mth.g(entity.m_146908_()), Mth.g(entity.m_146909_())));
         C_2137_ levelchunk = this.t();
         if (levelchunk.m_6430_()) {
            list.add("Waiting for chunk...");
         } else {
            int i = this.f.r.i().m_7827_().m_75831_(blockpos, 0);
            int j = this.f.r.m_45517_(C_1607_.SKY, blockpos);
            int k = this.f.r.m_45517_(C_1607_.BLOCK, blockpos);
            list.add("Client Light: " + i + " (" + j + " sky, " + k + " block)");
            C_2137_ levelchunk1 = this.s();
            StringBuilder stringbuilder = new StringBuilder("CH");

            for (C_2191_ heightmap$types : C_2191_.values()) {
               if (heightmap$types.m_64297_()) {
                  stringbuilder.append(" ")
                     .append((String)e.get(heightmap$types))
                     .append(": ")
                     .append(levelchunk.m_5885_(heightmap$types, blockpos.m_123341_(), blockpos.m_123343_()));
               }
            }

            list.add(stringbuilder.toString());
            stringbuilder.setLength(0);
            stringbuilder.append("SH");

            for (C_2191_ heightmap$types1 : C_2191_.values()) {
               if (heightmap$types1.m_64298_()) {
                  stringbuilder.append(" ").append((String)e.get(heightmap$types1)).append(": ");
                  if (levelchunk1 != null) {
                     stringbuilder.append(levelchunk1.m_5885_(heightmap$types1, blockpos.m_123341_(), blockpos.m_123343_()));
                  } else {
                     stringbuilder.append("??");
                  }
               }
            }

            list.add(stringbuilder.toString());
            if (blockpos.m_123342_() >= this.f.r.m_141937_() && blockpos.m_123342_() < this.f.r.m_151558_()) {
               list.add("Biome: " + a(this.f.r.m_204166_(blockpos)));
               if (levelchunk1 != null) {
                  float f2 = level.m_46940_();
                  long l = levelchunk1.m_6319_();
                  C_469_ difficultyinstance = new C_469_(level.m_46791_(), level.m_46468_(), l, f2);
                  list.add(
                     String.format(
                        Locale.ROOT,
                        "Local Difficulty: %.2f // %.2f (Day %d)",
                        difficultyinstance.m_19056_(),
                        difficultyinstance.m_19057_(),
                        this.f.r.m_46468_() / 24000L
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

         C_12_ serverlevel = this.p();
         if (serverlevel != null) {
            C_8_ serverchunkcache = serverlevel.m_7726_();
            C_2118_ chunkgenerator = serverchunkcache.m_8481_();
            C_213138_ randomstate = serverchunkcache.m_214994_();
            chunkgenerator.m_213600_(list, randomstate, blockpos);
            C_182866_ climate$sampler = randomstate.m_224579_();
            C_1644_ biomesource = chunkgenerator.m_62218_();
            biomesource.m_207301_(list, blockpos, climate$sampler);
            C_1613_ naturalspawner$spawnstate = serverchunkcache.m_8485_();
            if (naturalspawner$spawnstate != null) {
               Object2IntMap<C_528_> object2intmap = naturalspawner$spawnstate.m_47148_();
               int i1 = naturalspawner$spawnstate.m_47126_();
               list.add(
                  "SC: "
                     + i1
                     + ", "
                     + (String)Stream.of(C_528_.values())
                        .map(categoryIn -> Character.toUpperCase(categoryIn.m_21607_().charAt(0)) + ": " + object2intmap.getInt(categoryIn))
                        .collect(Collectors.joining(", "))
               );
            } else {
               list.add("SC: N/A");
            }
         }

         C_4145_ postchain = this.f.j.f();
         if (postchain != null) {
            list.add("Shader: " + postchain.m_110022_());
         }

         list.add(this.f.m_91106_().m_120408_() + String.format(Locale.ROOT, " (Mood %d%%)", Math.round(this.f.f_91074_.m_108762_() * 100.0F)));
         return list;
      }
   }

   private static String a(C_203228_<C_1629_> biomeHolderIn) {
      return (String)biomeHolderIn.m_203439_().map(keyIn -> keyIn.a().toString(), p_317322_0_ -> "[unregistered " + p_317322_0_ + "]");
   }

   @Nullable
   private C_12_ p() {
      IntegratedServer integratedserver = this.f.V();
      return integratedserver != null ? integratedserver.m_129880_(this.f.r.m_46472_()) : null;
   }

   @Nullable
   private String q() {
      C_12_ serverlevel = this.p();
      return serverlevel != null ? serverlevel.m_46464_() : null;
   }

   private C_1596_ r() {
      return (C_1596_)DataFixUtils.orElse(
         Optional.ofNullable(this.f.V()).flatMap(serverIn -> Optional.ofNullable(serverIn.m_129880_(this.f.r.m_46472_()))), this.f.r
      );
   }

   @Nullable
   private C_2137_ s() {
      if (this.m == null) {
         C_12_ serverlevel = this.p();
         if (serverlevel == null) {
            return null;
         }

         this.m = serverlevel.m_7726_().m_8431_(this.k.e, this.k.f, C_313554_.f_315432_, false).thenApply(chunkIn -> (C_2137_)chunkIn.m_318814_(null));
      }

      return (C_2137_)this.m.getNow(null);
   }

   private C_2137_ t() {
      if (this.l == null) {
         this.l = this.f.r.m_6325_(this.k.e, this.k.f);
      }

      return this.l;
   }

   protected List<String> c() {
      long i = Runtime.getRuntime().maxMemory();
      long j = Runtime.getRuntime().totalMemory();
      long k = Runtime.getRuntime().freeMemory();
      long l = j - k;
      List<String> list = Lists.newArrayList(
         new String[]{
            String.format(Locale.ROOT, "Java: %s", System.getProperty("java.version")),
            String.format(Locale.ROOT, "Mem: %2d%% %03d/%03dMB", l * 100L / i, b(l), b(i)),
            String.format(Locale.ROOT, "Allocation rate: %dMB/s", MemoryMonitor.getAllocationRateAvgMb()),
            String.format(Locale.ROOT, "Allocated: %2d%% %03dMB", j * 100L / i, b(j)),
            "",
            String.format(Locale.ROOT, "CPU: %s", C_3139_.m_84819_()),
            "",
            String.format(Locale.ROOT, "Display: %dx%d (%s)", C_3391_.m_91087_().aM().l(), C_3391_.m_91087_().aM().m(), C_3139_.m_84818_()),
            C_3139_.m_84820_(),
            C_3139_.m_84821_()
         }
      );
      long bufferAllocated = NativeMemory.getBufferAllocated();
      long bufferMaximum = NativeMemory.getBufferMaximum();
      long imageAllocated = NativeMemory.getImageAllocated();
      String strNative = "Native: " + b(bufferAllocated) + "/" + b(bufferMaximum) + "+" + b(imageAllocated) + "MB";
      list.add(3, strNative);
      long gpuBufferAllocated = GpuMemory.getBufferAllocated();
      long gpuTextureAllocated = GpuMemory.getTextureAllocated();
      list.set(4, "GPU: " + b(gpuBufferAllocated) + "+" + b(gpuTextureAllocated) + "MB");
      if (Reflector.BrandingControl_getBrandings.exists()) {
         list.add("");

         for (String line : (Collection)Reflector.call(Reflector.BrandingControl_getBrandings, true, false)) {
            if (!line.startsWith("Minecraft ")) {
               list.add(line);
            }
         }
      }

      if (this.f.m_91299_()) {
         return list;
      } else {
         if (this.i.m_6662_() == C_3044_.BLOCK) {
            C_4675_ blockpos = ((C_3041_)this.i).m_82425_();
            BlockState blockstate = this.f.r.a_(blockpos);
            list.add("");
            list.add(C_4856_.UNDERLINE + "Targeted Block: " + blockpos.m_123341_() + ", " + blockpos.m_123342_() + ", " + blockpos.m_123343_());
            list.add(String.valueOf(C_256712_.f_256975_.b(blockstate.m_60734_())));

            for (Entry<Property<?>, Comparable<?>> entry : blockstate.m_61148_().entrySet()) {
               list.add(this.a(entry));
            }

            blockstate.m_204343_().map(keyIn -> "#" + keyIn.b()).forEach(list::add);
         }

         if (this.j.m_6662_() == C_3044_.BLOCK) {
            C_4675_ blockpos1 = ((C_3041_)this.j).m_82425_();
            C_2691_ fluidstate = this.f.r.m_6425_(blockpos1);
            list.add("");
            list.add(C_4856_.UNDERLINE + "Targeted Fluid: " + blockpos1.m_123341_() + ", " + blockpos1.m_123342_() + ", " + blockpos1.m_123343_());
            list.add(String.valueOf(C_256712_.f_257020_.b(fluidstate.m_76152_())));

            for (Entry<Property<?>, Comparable<?>> entry1 : fluidstate.m_61148_().entrySet()) {
               list.add(this.a(entry1));
            }

            fluidstate.m_205075_().map(keyIn -> "#" + keyIn.b()).forEach(list::add);
         }

         C_507_ entity = this.f.f_91076_;
         if (entity != null) {
            list.add("");
            list.add(C_4856_.UNDERLINE + "Targeted Entity");
            list.add(String.valueOf(C_256712_.f_256780_.b(entity.m_6095_())));
            entity.m_6095_().m_204041_().m_203616_().forEach(t -> list.add("#" + t.b()));
         }

         return list;
      }
   }

   private String a(Entry<Property<?>, Comparable<?>> entryIn) {
      Property<?> property = (Property<?>)entryIn.getKey();
      Comparable<?> comparable = (Comparable<?>)entryIn.getValue();
      String s = Util.a(property, comparable);
      if (Boolean.TRUE.equals(comparable)) {
         s = C_4856_.GREEN + s;
      } else if (Boolean.FALSE.equals(comparable)) {
         s = C_4856_.RED + s;
      }

      return property.f() + ": " + s;
   }

   private static long b(long bytes) {
      return bytes / 1024L / 1024L;
   }

   public boolean d() {
      return this.n && !this.f.m.Y;
   }

   public boolean e() {
      return this.d() && this.o;
   }

   public boolean f() {
      return this.d() && this.q;
   }

   public boolean g() {
      return this.d() && this.p;
   }

   public void h() {
      this.n = !this.n;
      if (this.n && !this.p && this.f.m.ofLagometer) {
         this.j();
      }

      if (this.n && !this.o && this.f.m.ofProfiler) {
         this.k();
      }
   }

   public void i() {
      this.q = !this.n || !this.q;
      if (this.q) {
         this.n = true;
         this.p = false;
      }
   }

   public void j() {
      this.p = !this.n || !this.p;
      if (this.p) {
         this.n = true;
         this.q = false;
      }
   }

   public void k() {
      this.o = !this.n || !this.o;
      if (this.o) {
         this.n = true;
      }
   }

   public void a(long timeIn) {
      this.r.m_322732_(timeIn);
   }

   public C_313429_ l() {
      return this.s;
   }

   public C_313429_ m() {
      return this.t;
   }

   public C_313429_ n() {
      return this.u;
   }

   public void a(long[] sampleIn, C_313536_ typeIn) {
      C_313429_ localsamplelogger = (C_313429_)this.v.get(typeIn);
      if (localsamplelogger != null) {
         localsamplelogger.m_320889_(sampleIn);
      }
   }

   public void o() {
      this.n = false;
      this.s.m_320406_();
      this.t.m_320406_();
      this.u.m_320406_();
   }

   public C_313429_ getFrameTimeLogger() {
      return this.r;
   }

   static class a {
      private static final int a = 500;
      private static final List<GarbageCollectorMXBean> b = ManagementFactory.getGarbageCollectorMXBeans();
      private long c = 0L;
      private long d = -1L;
      private long e = -1L;
      private long f = 0L;

      long a(long heapUsageIn) {
         long i = System.currentTimeMillis();
         if (i - this.c < 500L) {
            return this.f;
         } else {
            long j = a();
            if (this.c != 0L && j == this.e) {
               double d0 = (double)TimeUnit.SECONDS.toMillis(1L) / (double)(i - this.c);
               long k = heapUsageIn - this.d;
               this.f = Math.round((double)k * d0);
            }

            this.c = i;
            this.d = heapUsageIn;
            this.e = j;
            return this.f;
         }
      }

      private static long a() {
         long i = 0L;

         for (GarbageCollectorMXBean garbagecollectormxbean : b) {
            i += garbagecollectormxbean.getCollectionCount();
         }

         return i;
      }
   }
}
