package net.minecraft.client.particle;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import org.slf4j.Logger;

public class ParticleEngine implements PreparableReloadListener {
   private static final Logger f_243727_ = LogUtils.getLogger();
   private static final FileToIdConverter f_243929_ = FileToIdConverter.m_246568_("particles");
   private static final ResourceLocation f_260634_ = ResourceLocation.m_340282_("particles");
   private static final int f_172264_ = 16384;
   private static final List f_107288_;
   protected ClientLevel f_107287_;
   private Map f_107289_ = Maps.newIdentityHashMap();
   private final Queue f_107290_ = Queues.newArrayDeque();
   private final TextureManager f_107291_;
   private final RandomSource f_107292_ = RandomSource.m_216327_();
   private final Map f_107293_ = new HashMap();
   private final Queue f_107294_ = Queues.newArrayDeque();
   private final Map f_107295_ = Maps.newHashMap();
   private final TextureAtlas f_107296_;
   private final Object2IntOpenHashMap f_172265_ = new Object2IntOpenHashMap();
   private RenderEnv renderEnv = new RenderEnv((BlockState)null, (BlockPos)null);

   public ParticleEngine(ClientLevel worldIn, TextureManager rendererIn) {
      if (Reflector.ForgeHooksClient_makeParticleRenderTypeComparator.exists()) {
         Comparator comp = (Comparator)Reflector.ForgeHooksClient_makeParticleRenderTypeComparator.call((Object)f_107288_);
         if (comp != null) {
            this.f_107289_ = Maps.newTreeMap(comp);
         }
      }

      this.f_107296_ = new TextureAtlas(TextureAtlas.f_118260_);
      rendererIn.m_118495_(this.f_107296_.m_118330_(), this.f_107296_);
      this.f_107287_ = worldIn;
      this.f_107291_ = rendererIn;
      this.m_107404_();
   }

   private void m_107404_() {
      this.m_107378_(ParticleTypes.f_123792_, HeartParticle.AngryVillagerProvider::new);
      this.m_107381_(ParticleTypes.f_194652_, new BlockMarker.Provider());
      this.m_107381_(ParticleTypes.f_123794_, new TerrainParticle.Provider());
      this.m_107378_(ParticleTypes.f_123795_, BubbleParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123774_, BubbleColumnUpParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123772_, BubblePopParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123777_, CampfireSmokeParticle.CosyProvider::new);
      this.m_107378_(ParticleTypes.f_123778_, CampfireSmokeParticle.SignalProvider::new);
      this.m_107378_(ParticleTypes.f_123796_, PlayerCloudParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123749_, SuspendedTownParticle.ComposterFillProvider::new);
      this.m_107378_(ParticleTypes.f_123797_, CritParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123773_, WaterCurrentDownParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123798_, CritParticle.DamageIndicatorProvider::new);
      this.m_107378_(ParticleTypes.f_123799_, DragonBreathParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123776_, SuspendedTownParticle.DolphinSpeedProvider::new);
      this.m_272137_(ParticleTypes.f_123800_, DripParticle::m_272109_);
      this.m_272137_(ParticleTypes.f_123801_, DripParticle::m_272026_);
      this.m_272137_(ParticleTypes.f_123802_, DripParticle::m_271885_);
      this.m_272137_(ParticleTypes.f_123803_, DripParticle::m_272020_);
      this.m_272137_(ParticleTypes.f_123804_, DripParticle::m_271915_);
      this.m_107378_(ParticleTypes.f_123805_, DustParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_175836_, DustColorTransitionParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123806_, SpellParticle.Provider::new);
      this.m_107381_(ParticleTypes.f_123807_, new MobAppearanceParticle.Provider());
      this.m_107378_(ParticleTypes.f_123808_, CritParticle.MagicProvider::new);
      this.m_107378_(ParticleTypes.f_123809_, FlyTowardsPositionParticle.EnchantProvider::new);
      this.m_107378_(ParticleTypes.f_123810_, EndRodParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123811_, SpellParticle.MobEffectProvider::new);
      this.m_107381_(ParticleTypes.f_123812_, new HugeExplosionSeedParticle.Provider());
      this.m_107378_(ParticleTypes.f_123813_, HugeExplosionParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_235902_, SonicBoomParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123814_, FallingDustParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_302334_, GustParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_314220_, GustParticle.SmallProvider::new);
      this.m_107381_(ParticleTypes.f_316181_, new GustSeedParticle.Provider(3.0, 7, 0));
      this.m_107381_(ParticleTypes.f_315099_, new GustSeedParticle.Provider(1.0, 3, 2));
      this.m_107378_(ParticleTypes.f_123815_, FireworkParticles.SparkProvider::new);
      this.m_107378_(ParticleTypes.f_123816_, WakeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123744_, FlameParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_314307_, SpellParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_235898_, SoulParticle.EmissiveProvider::new);
      this.m_107378_(ParticleTypes.f_235899_, SculkChargeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_235900_, SculkChargePopParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123746_, SoulParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123745_, FlameParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123747_, FireworkParticles.FlashProvider::new);
      this.m_107378_(ParticleTypes.f_123748_, SuspendedTownParticle.HappyVillagerProvider::new);
      this.m_107378_(ParticleTypes.f_123750_, HeartParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123751_, SpellParticle.InstantProvider::new);
      this.m_107381_(ParticleTypes.f_123752_, new BreakingItemParticle.Provider());
      this.m_107381_(ParticleTypes.f_123753_, new BreakingItemParticle.SlimeProvider());
      this.m_107381_(ParticleTypes.f_315496_, new BreakingItemParticle.CobwebProvider());
      this.m_107381_(ParticleTypes.f_123754_, new BreakingItemParticle.SnowballProvider());
      this.m_107378_(ParticleTypes.f_123755_, LargeSmokeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123756_, LavaParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123757_, SuspendedTownParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123775_, FlyTowardsPositionParticle.NautilusProvider::new);
      this.m_107378_(ParticleTypes.f_123758_, NoteParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123759_, ExplodeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123760_, PortalParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123761_, WaterDropParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123762_, SmokeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_302345_, WhiteSmokeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123763_, PlayerCloudParticle.SneezeProvider::new);
      this.m_107378_(ParticleTypes.f_175821_, SnowflakeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123764_, SpitParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123766_, AttackSweepParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123767_, TotemParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123765_, SquidInkParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123768_, SuspendedParticle.UnderwaterProvider::new);
      this.m_107378_(ParticleTypes.f_123769_, SplashParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123771_, SpellParticle.WitchProvider::new);
      this.m_272137_(ParticleTypes.f_123779_, DripParticle::m_272107_);
      this.m_272137_(ParticleTypes.f_123780_, DripParticle::m_272030_);
      this.m_272137_(ParticleTypes.f_123781_, DripParticle::m_271744_);
      this.m_272137_(ParticleTypes.f_123782_, DripParticle::m_272129_);
      this.m_272137_(ParticleTypes.f_175832_, DripParticle::m_272261_);
      this.m_107378_(ParticleTypes.f_175833_, SuspendedParticle.SporeBlossomAirProvider::new);
      this.m_107378_(ParticleTypes.f_123783_, AshParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123784_, SuspendedParticle.CrimsonSporeProvider::new);
      this.m_107378_(ParticleTypes.f_123785_, SuspendedParticle.WarpedSporeProvider::new);
      this.m_272137_(ParticleTypes.f_123786_, DripParticle::m_271935_);
      this.m_272137_(ParticleTypes.f_123787_, DripParticle::m_271941_);
      this.m_272137_(ParticleTypes.f_123788_, DripParticle::m_272251_);
      this.m_107378_(ParticleTypes.f_123789_, ReversePortalParticle.ReversePortalProvider::new);
      this.m_107378_(ParticleTypes.f_123790_, WhiteAshParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_175834_, FlameParticle.SmallFlameProvider::new);
      this.m_272137_(ParticleTypes.f_175824_, DripParticle::m_272002_);
      this.m_272137_(ParticleTypes.f_175825_, DripParticle::m_271993_);
      this.m_107378_(ParticleTypes.f_276452_, (p_276702_0_) -> {
         return (p_276703_1_, p_276703_2_, p_276703_3_, p_276703_5_, p_276703_7_, p_276703_9_, p_276703_11_, p_276703_13_) -> {
            return new CherryParticle(p_276703_2_, p_276703_3_, p_276703_5_, p_276703_7_, p_276702_0_);
         };
      });
      this.m_272137_(ParticleTypes.f_175822_, DripParticle::m_271789_);
      this.m_272137_(ParticleTypes.f_175823_, DripParticle::m_271760_);
      this.m_107378_(ParticleTypes.f_175820_, VibrationSignalParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_175826_, SquidInkParticle.GlowInkProvider::new);
      this.m_107378_(ParticleTypes.f_175827_, GlowParticle.GlowSquidProvider::new);
      this.m_107378_(ParticleTypes.f_175828_, GlowParticle.WaxOnProvider::new);
      this.m_107378_(ParticleTypes.f_175829_, GlowParticle.WaxOffProvider::new);
      this.m_107378_(ParticleTypes.f_175830_, GlowParticle.ElectricSparkProvider::new);
      this.m_107378_(ParticleTypes.f_175831_, GlowParticle.ScrapeProvider::new);
      this.m_107378_(ParticleTypes.f_235901_, ShriekParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_276512_, SuspendedTownParticle.EggCrackProvider::new);
      this.m_107378_(ParticleTypes.f_303068_, DustPlumeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_314928_, TrialSpawnerDetectionParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_314692_, TrialSpawnerDetectionParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_314380_, FlyTowardsPositionParticle.VaultConnectionProvider::new);
      this.m_107381_(ParticleTypes.f_314186_, new TerrainParticle.DustPillarProvider());
      this.m_107378_(ParticleTypes.f_314966_, SpellParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_317125_, SpellParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_314395_, FlyStraightTowardsParticle.OminousSpawnProvider::new);
   }

   private void m_107381_(ParticleType particleTypeIn, ParticleProvider particleFactoryIn) {
      this.f_107293_.put(BuiltInRegistries.f_257034_.m_7981_(particleTypeIn), particleFactoryIn);
   }

   private void m_272137_(ParticleType p_272137_1_, ParticleProvider.Sprite p_272137_2_) {
      this.m_107378_(p_272137_1_, (p_271560_1_) -> {
         return (p_271561_2_, p_271561_3_, p_271561_4_, p_271561_6_, p_271561_8_, p_271561_10_, p_271561_12_, p_271561_14_) -> {
            TextureSheetParticle texturesheetparticle = p_272137_2_.m_272232_(p_271561_2_, p_271561_3_, p_271561_4_, p_271561_6_, p_271561_8_, p_271561_10_, p_271561_12_, p_271561_14_);
            if (texturesheetparticle != null) {
               texturesheetparticle.m_108335_(p_271560_1_);
            }

            return texturesheetparticle;
         };
      });
   }

   private void m_107378_(ParticleType particleTypeIn, SpriteParticleRegistration particleMetaFactoryIn) {
      MutableSpriteSet particleengine$mutablespriteset = new MutableSpriteSet();
      this.f_107295_.put(BuiltInRegistries.f_257034_.m_7981_(particleTypeIn), particleengine$mutablespriteset);
      this.f_107293_.put(BuiltInRegistries.f_257034_.m_7981_(particleTypeIn), particleMetaFactoryIn.m_107419_(particleengine$mutablespriteset));
   }

   public CompletableFuture m_5540_(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
      CompletableFuture completablefuture = CompletableFuture.supplyAsync(() -> {
         return f_243929_.m_247457_(resourceManager);
      }, backgroundExecutor).thenCompose((mapIn) -> {
         List list = new ArrayList(mapIn.size());
         mapIn.forEach((locIn, resIn) -> {
            ResourceLocation resourcelocation = f_243929_.m_245273_(locIn);
            list.add(CompletableFuture.supplyAsync(() -> {
               return new ParticleDefinition(resourcelocation, this.m_245118_(resourcelocation, resIn));
            }, backgroundExecutor));
         });
         return Util.m_137567_(list);
      });
      CompletableFuture completablefuture1 = SpriteLoader.m_245483_(this.f_107296_).m_260881_(resourceManager, f_260634_, 0, backgroundExecutor).thenCompose(SpriteLoader.Preparations::m_246429_);
      CompletableFuture var10000 = CompletableFuture.allOf(completablefuture1, completablefuture);
      Objects.requireNonNull(stage);
      return var10000.thenCompose(stage::m_6769_).thenAcceptAsync((voidIn) -> {
         this.m_263560_();
         reloadProfiler.m_7242_();
         reloadProfiler.m_6180_("upload");
         SpriteLoader.Preparations spriteloader$preparations = (SpriteLoader.Preparations)completablefuture1.join();
         this.f_107296_.m_247065_(spriteloader$preparations);
         reloadProfiler.m_6182_("bindSpriteSets");
         Set set = new HashSet();
         TextureAtlasSprite textureatlassprite = spriteloader$preparations.f_243912_();
         ((List)completablefuture.join()).forEach((defIn) -> {
            Optional optional = defIn.f_243741_();
            if (!optional.isEmpty()) {
               List list = new ArrayList();
               Iterator var7 = ((List)optional.get()).iterator();

               while(var7.hasNext()) {
                  ResourceLocation resourcelocation = (ResourceLocation)var7.next();
                  TextureAtlasSprite textureatlassprite1 = (TextureAtlasSprite)spriteloader$preparations.f_243807_().get(resourcelocation);
                  if (textureatlassprite1 == null) {
                     set.add(resourcelocation);
                     list.add(textureatlassprite);
                  } else {
                     list.add(textureatlassprite1);
                  }
               }

               if (list.isEmpty()) {
                  list.add(textureatlassprite);
               }

               ((MutableSpriteSet)this.f_107295_.get(defIn.f_244103_())).m_107415_(list);
            }

         });
         if (!set.isEmpty()) {
            f_243727_.warn("Missing particle sprites: {}", set.stream().sorted().map(ResourceLocation::toString).collect(Collectors.joining(",")));
         }

         reloadProfiler.m_7238_();
         reloadProfiler.m_7241_();
      }, gameExecutor);
   }

   public void m_107301_() {
      this.f_107296_.m_118329_();
   }

   private Optional m_245118_(ResourceLocation locationIn, Resource resourceIn) {
      if (!this.f_107295_.containsKey(locationIn)) {
         f_243727_.debug("Redundant texture list for particle: {}", locationIn);
         return Optional.empty();
      } else {
         try {
            Reader reader = resourceIn.m_215508_();

            Optional optional;
            try {
               ParticleDescription particledescription = ParticleDescription.m_107285_(GsonHelper.m_13859_(reader));
               optional = Optional.of(particledescription.m_107282_());
            } catch (Throwable var8) {
               if (reader != null) {
                  try {
                     reader.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (reader != null) {
               reader.close();
            }

            return optional;
         } catch (IOException var9) {
            throw new IllegalStateException("Failed to load description for particle " + String.valueOf(locationIn), var9);
         }
      }
   }

   public void m_107329_(Entity entityIn, ParticleOptions particleData) {
      this.f_107290_.add(new TrackingEmitter(this.f_107287_, entityIn, particleData));
   }

   public void m_107332_(Entity entityIn, ParticleOptions dataIn, int lifetimeIn) {
      this.f_107290_.add(new TrackingEmitter(this.f_107287_, entityIn, dataIn, lifetimeIn));
   }

   @Nullable
   public Particle m_107370_(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      Particle particle = this.m_107395_(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
      if (particle != null) {
         this.m_107344_(particle);
         return particle;
      } else {
         return null;
      }
   }

   @Nullable
   private Particle m_107395_(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      ParticleProvider particleprovider = (ParticleProvider)this.f_107293_.get(BuiltInRegistries.f_257034_.m_7981_(particleData.m_6012_()));
      return particleprovider == null ? null : particleprovider.m_6966_(particleData, this.f_107287_, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_107344_(Particle effect) {
      if (effect != null) {
         if (!(effect instanceof FireworkParticles.SparkParticle) || Config.isFireworkParticles()) {
            Optional optional = effect.m_142654_();
            if (optional.isPresent()) {
               if (this.m_172279_((ParticleGroup)optional.get())) {
                  this.f_107294_.add(effect);
                  this.m_172281_((ParticleGroup)optional.get(), 1);
               }
            } else {
               this.f_107294_.add(effect);
            }

         }
      }
   }

   public void m_107388_() {
      this.f_107289_.forEach((typeIn, listIn) -> {
         this.f_107287_.m_46473_().m_6180_(typeIn.toString());
         this.m_107384_(listIn);
         this.f_107287_.m_46473_().m_7238_();
      });
      if (!this.f_107290_.isEmpty()) {
         List list = Lists.newArrayList();
         Iterator var2 = this.f_107290_.iterator();

         while(var2.hasNext()) {
            TrackingEmitter trackingemitter = (TrackingEmitter)var2.next();
            trackingemitter.m_5989_();
            if (!trackingemitter.m_107276_()) {
               list.add(trackingemitter);
            }
         }

         this.f_107290_.removeAll(list);
      }

      Particle particle;
      if (!this.f_107294_.isEmpty()) {
         while((particle = (Particle)this.f_107294_.poll()) != null) {
            Queue queue = (Queue)this.f_107289_.computeIfAbsent(particle.m_7556_(), (renderTypeIn) -> {
               return EvictingQueue.create(16384);
            });
            queue.add(particle);
         }
      }

   }

   private void m_107384_(Collection particlesIn) {
      if (!particlesIn.isEmpty()) {
         long timeStartMs = System.currentTimeMillis();
         int countLeft = particlesIn.size();
         Iterator iterator = particlesIn.iterator();

         while(iterator.hasNext()) {
            Particle particle = (Particle)iterator.next();
            this.m_107393_(particle);
            if (!particle.m_107276_()) {
               particle.m_142654_().ifPresent((groupIn) -> {
                  this.m_172281_(groupIn, -1);
               });
               iterator.remove();
            }

            --countLeft;
            if (System.currentTimeMillis() > timeStartMs + 20L) {
               break;
            }
         }

         if (countLeft > 0) {
            int countToRemove = countLeft;

            for(Iterator it = particlesIn.iterator(); it.hasNext() && countToRemove > 0; --countToRemove) {
               Particle particle = (Particle)it.next();
               particle.m_107274_();
               it.remove();
            }
         }
      }

   }

   private void m_172281_(ParticleGroup groupIn, int countIn) {
      this.f_172265_.addTo(groupIn, countIn);
   }

   private void m_107393_(Particle particle) {
      try {
         particle.m_5989_();
      } catch (Throwable var5) {
         CrashReport crashreport = CrashReport.m_127521_(var5, "Ticking Particle");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Particle being ticked");
         Objects.requireNonNull(particle);
         crashreportcategory.m_128165_("Particle", particle::toString);
         ParticleRenderType var10002 = particle.m_7556_();
         Objects.requireNonNull(var10002);
         crashreportcategory.m_128165_("Particle Type", var10002::toString);
         throw new ReportedException(crashreport);
      }
   }

   public void m_107336_(LightTexture lightTextureIn, Camera cameraIn, float partialTicks) {
      this.renderParticles(lightTextureIn, cameraIn, partialTicks, (Frustum)null);
   }

   public void renderParticles(LightTexture lightTextureIn, Camera cameraIn, float partialTicks, Frustum clippingHelper) {
      lightTextureIn.m_109896_();
      RenderSystem.enableDepthTest();
      RenderSystem.activeTexture(33986);
      RenderSystem.activeTexture(33984);
      FogType cameraFogType = cameraIn.m_167685_();
      boolean isEyeInWater = cameraFogType == FogType.WATER;
      Collection renderTypes = f_107288_;
      if (Reflector.ForgeHooksClient.exists()) {
         renderTypes = this.f_107289_.keySet();
      }

      Iterator var8 = ((Collection)renderTypes).iterator();

      label88:
      while(true) {
         ParticleRenderType particlerendertype;
         Queue queue;
         BufferBuilder bufferbuilder;
         do {
            do {
               do {
                  do {
                     if (!var8.hasNext()) {
                        RenderSystem.depthMask(true);
                        RenderSystem.disableBlend();
                        lightTextureIn.m_109891_();
                        RenderSystem.enableDepthTest();
                        GlStateManager._glUseProgram(0);
                        return;
                     }

                     particlerendertype = (ParticleRenderType)var8.next();
                  } while(particlerendertype == ParticleRenderType.f_107434_);

                  queue = (Queue)this.f_107289_.get(particlerendertype);
               } while(queue == null);
            } while(queue.isEmpty());

            RenderSystem.setShader(GameRenderer::m_172829_);
            Tesselator tesselator = Tesselator.m_85913_();
            bufferbuilder = particlerendertype.m_6505_(tesselator, this.f_107291_);
         } while(bufferbuilder == null);

         Iterator var13 = queue.iterator();

         while(true) {
            Particle particle;
            do {
               do {
                  if (!var13.hasNext()) {
                     MeshData meshdata = bufferbuilder.m_339970_();
                     if (meshdata != null) {
                        BufferUploader.m_231202_(meshdata);
                     }
                     continue label88;
                  }

                  particle = (Particle)var13.next();
               } while(clippingHelper != null && particle.shouldCull() && !clippingHelper.m_113029_(particle.m_107277_()));
            } while(!isEyeInWater && particle instanceof SuspendedParticle && particle.f_107215_ == 0.0 && particle.f_107216_ == 0.0 && particle.f_107217_ == 0.0);

            try {
               particle.m_5744_(bufferbuilder, cameraIn, partialTicks);
            } catch (Throwable var18) {
               CrashReport crashreport = CrashReport.m_127521_(var18, "Rendering Particle");
               CrashReportCategory crashreportcategory = crashreport.m_127514_("Particle being rendered");
               Objects.requireNonNull(particle);
               crashreportcategory.m_128165_("Particle", particle::toString);
               Objects.requireNonNull(particlerendertype);
               crashreportcategory.m_128165_("Particle Type", particlerendertype::toString);
               throw new ReportedException(crashreport);
            }
         }
      }
   }

   public void m_107342_(@Nullable ClientLevel worldIn) {
      this.f_107287_ = worldIn;
      this.m_263560_();
      this.f_107290_.clear();
   }

   public void m_107355_(BlockPos pos, BlockState state) {
      boolean forgeAddDestroyEffects = false;
      IClientBlockExtensions cbe = IClientBlockExtensions.of(state);
      if (cbe != null) {
         forgeAddDestroyEffects = cbe.addDestroyEffects(state, this.f_107287_, pos, this);
      }

      if (!state.m_60795_() && state.m_295777_() && !forgeAddDestroyEffects) {
         VoxelShape voxelshape = state.m_60808_(this.f_107287_, pos);
         double d0 = 0.25;
         voxelshape.m_83286_((p_172270_3_, p_172270_5_, p_172270_7_, p_172270_9_, p_172270_11_, p_172270_13_) -> {
            double d1 = Math.min(1.0, p_172270_9_ - p_172270_3_);
            double d2 = Math.min(1.0, p_172270_11_ - p_172270_5_);
            double d3 = Math.min(1.0, p_172270_13_ - p_172270_7_);
            int i = Math.max(2, Mth.m_14165_(d1 / 0.25));
            int j = Math.max(2, Mth.m_14165_(d2 / 0.25));
            int k = Math.max(2, Mth.m_14165_(d3 / 0.25));

            for(int l = 0; l < i; ++l) {
               for(int i1 = 0; i1 < j; ++i1) {
                  for(int j1 = 0; j1 < k; ++j1) {
                     double d4 = ((double)l + 0.5) / (double)i;
                     double d5 = ((double)i1 + 0.5) / (double)j;
                     double d6 = ((double)j1 + 0.5) / (double)k;
                     double d7 = d4 * d1 + p_172270_3_;
                     double d8 = d5 * d2 + p_172270_5_;
                     double d9 = d6 * d3 + p_172270_7_;
                     Particle tp = new TerrainParticle(this.f_107287_, (double)pos.m_123341_() + d7, (double)pos.m_123342_() + d8, (double)pos.m_123343_() + d9, d4 - 0.5, d5 - 0.5, d6 - 0.5, state, pos);
                     if (Reflector.TerrainParticle_updateSprite.exists()) {
                        Reflector.call(tp, Reflector.TerrainParticle_updateSprite, state, pos);
                     }

                     if (Config.isCustomColors()) {
                        updateTerrainParticleColor(tp, state, this.f_107287_, pos, this.renderEnv);
                     }

                     this.m_107344_(tp);
                  }
               }
            }

         });
      }

   }

   public void m_107367_(BlockPos pos, Direction side) {
      BlockState blockstate = this.f_107287_.m_8055_(pos);
      if (blockstate.m_60799_() != RenderShape.INVISIBLE && blockstate.m_295777_()) {
         int i = pos.m_123341_();
         int j = pos.m_123342_();
         int k = pos.m_123343_();
         float f = 0.1F;
         AABB aabb = blockstate.m_60808_(this.f_107287_, pos).m_83215_();
         double d0 = (double)i + this.f_107292_.m_188500_() * (aabb.f_82291_ - aabb.f_82288_ - 0.20000000298023224) + 0.10000000149011612 + aabb.f_82288_;
         double d1 = (double)j + this.f_107292_.m_188500_() * (aabb.f_82292_ - aabb.f_82289_ - 0.20000000298023224) + 0.10000000149011612 + aabb.f_82289_;
         double d2 = (double)k + this.f_107292_.m_188500_() * (aabb.f_82293_ - aabb.f_82290_ - 0.20000000298023224) + 0.10000000149011612 + aabb.f_82290_;
         if (side == Direction.DOWN) {
            d1 = (double)j + aabb.f_82289_ - 0.10000000149011612;
         }

         if (side == Direction.field_61) {
            d1 = (double)j + aabb.f_82292_ + 0.10000000149011612;
         }

         if (side == Direction.NORTH) {
            d2 = (double)k + aabb.f_82290_ - 0.10000000149011612;
         }

         if (side == Direction.SOUTH) {
            d2 = (double)k + aabb.f_82293_ + 0.10000000149011612;
         }

         if (side == Direction.WEST) {
            d0 = (double)i + aabb.f_82288_ - 0.10000000149011612;
         }

         if (side == Direction.EAST) {
            d0 = (double)i + aabb.f_82291_ + 0.10000000149011612;
         }

         Particle tp = (new TerrainParticle(this.f_107287_, d0, d1, d2, 0.0, 0.0, 0.0, blockstate, pos)).m_107268_(0.2F).m_6569_(0.6F);
         if (Reflector.TerrainParticle_updateSprite.exists()) {
            Reflector.call(tp, Reflector.TerrainParticle_updateSprite, blockstate, pos);
         }

         if (Config.isCustomColors()) {
            updateTerrainParticleColor(tp, blockstate, this.f_107287_, pos, this.renderEnv);
         }

         this.m_107344_(tp);
      }

   }

   public String m_107403_() {
      return String.valueOf(this.f_107289_.values().stream().mapToInt(Collection::size).sum());
   }

   private boolean m_172279_(ParticleGroup groupIn) {
      return this.f_172265_.getInt(groupIn) < groupIn.m_175819_();
   }

   private void m_263560_() {
      this.f_107289_.clear();
      this.f_107294_.clear();
      this.f_107290_.clear();
      this.f_172265_.clear();
   }

   private boolean reuseBarrierParticle(Particle entityfx, Queue deque) {
      Particle var4;
      for(Iterator it = deque.iterator(); it.hasNext(); var4 = (Particle)it.next()) {
      }

      return false;
   }

   public static void updateTerrainParticleColor(Particle particle, BlockState state, BlockAndTintGetter world, BlockPos pos, RenderEnv renderEnv) {
      renderEnv.reset(state, pos);
      int col = CustomColors.getColorMultiplier(true, state, world, pos, renderEnv);
      if (col != -1) {
         particle.f_107227_ = 0.6F * (float)(col >> 16 & 255) / 255.0F;
         particle.f_107228_ = 0.6F * (float)(col >> 8 & 255) / 255.0F;
         particle.f_107229_ = 0.6F * (float)(col & 255) / 255.0F;
      }

   }

   public int getCountParticles() {
      int sum = 0;

      Queue queue;
      for(Iterator var2 = this.f_107289_.values().iterator(); var2.hasNext(); sum += queue.size()) {
         queue = (Queue)var2.next();
      }

      return sum;
   }

   public void addBlockHitEffects(BlockPos pos, BlockHitResult target) {
      BlockState state = this.f_107287_.m_8055_(pos);
      if (!IClientBlockExtensions.of(state).addHitEffects(state, this.f_107287_, target, this)) {
         this.m_107367_(pos, target.m_82434_());
      }

   }

   static {
      f_107288_ = ImmutableList.of(ParticleRenderType.f_107429_, ParticleRenderType.f_107430_, ParticleRenderType.f_107432_, ParticleRenderType.f_107431_, ParticleRenderType.f_107433_);
   }

   @FunctionalInterface
   interface SpriteParticleRegistration {
      ParticleProvider m_107419_(SpriteSet var1);
   }

   static class MutableSpriteSet implements SpriteSet {
      private List f_107406_;

      public TextureAtlasSprite m_5819_(int particleAge, int particleMaxAge) {
         return (TextureAtlasSprite)this.f_107406_.get(particleAge * (this.f_107406_.size() - 1) / particleMaxAge);
      }

      public TextureAtlasSprite m_213979_(RandomSource randomIn) {
         return (TextureAtlasSprite)this.f_107406_.get(randomIn.m_188503_(this.f_107406_.size()));
      }

      public void m_107415_(List spritesIn) {
         this.f_107406_ = ImmutableList.copyOf(spritesIn);
      }
   }

   static record ParticleDefinition(ResourceLocation f_244103_, Optional f_243741_) {
      ParticleDefinition(ResourceLocation id, Optional sprites) {
         this.f_244103_ = id;
         this.f_243741_ = sprites;
      }

      public ResourceLocation f_244103_() {
         return this.f_244103_;
      }

      public Optional f_243741_() {
         return this.f_243741_;
      }
   }
}
