package net.minecraft.client.multiplayer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.particle.FireworkParticles.Starter;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.ticks.BlackholeTickAccess;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraftforge.client.model.data.ModelDataManager;
import net.minecraftforge.client.model.lighting.QuadLighter;
import net.minecraftforge.entity.PartEntity;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.DynamicLights;
import net.optifine.RandomEntities;
import net.optifine.Vec3M;
import net.optifine.override.PlayerControllerOF;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import org.slf4j.Logger;

public class ClientLevel extends Level {
   private static Logger f_233600_ = LogUtils.getLogger();
   private static double f_171629_;
   private static int f_194125_;
   private static int f_194126_;
   EntityTickList f_171630_ = new EntityTickList();
   private TransientEntitySectionManager<Entity> f_171631_ = new TransientEntitySectionManager(Entity.class, new ClientLevel.EntityCallbacks());
   private ClientPacketListener f_104561_;
   private LevelRenderer f_104562_;
   private ClientLevel.ClientLevelData f_104563_;
   private DimensionSpecialEffects f_104564_;
   private TickRateManager f_303463_;
   private Minecraft f_104565_ = Minecraft.m_91087_();
   List<AbstractClientPlayer> f_104566_ = Lists.newArrayList();
   private Map<MapId, MapItemSavedData> f_104556_ = Maps.newHashMap();
   private static long f_171628_;
   private int f_104557_;
   private Object2ObjectArrayMap<ColorResolver, BlockTintCache> f_104558_ = Util.m_137469_(new Object2ObjectArrayMap(3), mapIn -> {
      mapIn.put(BiomeColors.f_108789_, new BlockTintCache(posIn -> this.m_104762_(posIn, BiomeColors.f_108789_)));
      mapIn.put(BiomeColors.f_108790_, new BlockTintCache(posIn -> this.m_104762_(posIn, BiomeColors.f_108790_)));
      mapIn.put(BiomeColors.f_108791_, new BlockTintCache(posIn -> this.m_104762_(posIn, BiomeColors.f_108791_)));
      Reflector.ColorResolverManager_registerBlockTintCaches.m_46374_(this, mapIn);
   });
   private ClientChunkCache f_104559_;
   private Deque<Runnable> f_194122_ = Queues.newArrayDeque();
   private int f_194123_;
   private BlockStatePredictionHandler f_233599_ = new BlockStatePredictionHandler();
   private static Set<Item> f_194124_ = Set.m_253057_(Items.f_42127_, Items.f_151033_);
   private Int2ObjectMap<PartEntity<?>> partEntities = new Int2ObjectOpenHashMap();
   private ModelDataManager modelDataManager = new ModelDataManager(this);
   private boolean playerUpdate = false;

   public void m_233651_(int sequenceIn) {
      this.f_233599_.m_233856_(sequenceIn, this);
   }

   public void m_233653_(BlockPos posIn, BlockState stateIn, int flagsIn) {
      if (!this.f_233599_.m_233864_(posIn, stateIn)) {
         super.m_6933_(posIn, stateIn, flagsIn, 512);
      }
   }

   public void m_233647_(BlockPos posIn, BlockState stateIn, Vec3 vecIn) {
      BlockState blockstate = this.m_8055_(posIn);
      if (blockstate != stateIn) {
         this.m_7731_(posIn, stateIn, 19);
         Player player = this.f_104565_.f_91074_;
         if (this == player.m_9236_() && player.m_20039_(posIn, stateIn)) {
            player.m_20248_(vecIn.f_82479_, vecIn.f_82480_, vecIn.f_82481_);
         }
      }
   }

   BlockStatePredictionHandler m_233601_() {
      return this.f_233599_;
   }

   public boolean m_6933_(BlockPos posIn, BlockState stateIn, int flagsIn, int updateCountIn) {
      if (this.f_233599_.m_233872_()) {
         BlockState blockstate = this.m_8055_(posIn);
         boolean flag = super.m_6933_(posIn, stateIn, flagsIn, updateCountIn);
         if (flag) {
            this.f_233599_.m_233867_(posIn, blockstate, this.f_104565_.f_91074_);
         }

         return flag;
      } else {
         return super.m_6933_(posIn, stateIn, flagsIn, updateCountIn);
      }
   }

   public ClientLevel(
      ClientPacketListener connectionIn,
      ClientLevel.ClientLevelData worldInfoIn,
      ResourceKey<Level> dimIn,
      Holder<DimensionType> dimTypeIn,
      int viewDistIn,
      int simDistIn,
      Supplier<ProfilerFiller> profilerIn,
      LevelRenderer worldRendererIn,
      boolean debugIn,
      long seedIn
   ) {
      super(worldInfoIn, dimIn, connectionIn.m_105152_(), dimTypeIn, profilerIn, true, debugIn, seedIn, 1000000);
      this.f_104561_ = connectionIn;
      this.f_104559_ = new ClientChunkCache(this, viewDistIn);
      this.f_303463_ = new TickRateManager();
      this.f_104563_ = worldInfoIn;
      this.f_104562_ = worldRendererIn;
      this.f_104564_ = DimensionSpecialEffects.m_108876_((DimensionType)dimTypeIn.m_203334_());
      this.m_104752_(new BlockPos(8, 64, 8), 0.0F);
      this.f_194123_ = simDistIn;
      this.m_46465_();
      this.m_46466_();
      if (Reflector.CapabilityProvider_gatherCapabilities.exists() && Reflector.CapabilityProvider.getTargetClass().isAssignableFrom(this.getClass())) {
         Reflector.m_46374_(this, Reflector.CapabilityProvider_gatherCapabilities);
      }

      Reflector.postForgeBusEvent(Reflector.LevelEvent_Load_Constructor, this);
      if (this.f_104565_.f_91072_ != null && this.f_104565_.f_91072_.getClass() == MultiPlayerGameMode.class) {
         this.f_104565_.f_91072_ = new PlayerControllerOF(this.f_104565_, this.f_104561_);
         CustomGuis.setPlayerControllerOF((PlayerControllerOF)this.f_104565_.f_91072_);
      }
   }

   public void m_194171_(Runnable updateIn) {
      this.f_194122_.add(updateIn);
   }

   public void m_194141_() {
      int i = this.f_194122_.size();
      int j = i < 1000 ? Math.max(10, i / 10) : i;

      for (int k = 0; k < j; k++) {
         Runnable runnable = (Runnable)this.f_194122_.poll();
         if (runnable == null) {
            break;
         }

         runnable.run();
      }
   }

   public boolean m_194173_() {
      return this.f_194122_.isEmpty();
   }

   public DimensionSpecialEffects m_104583_() {
      return this.f_104564_;
   }

   public void m_104726_(BooleanSupplier p_104726_1_) {
      this.m_6857_().m_61969_();
      if (this.m_304826_().m_305915_()) {
         this.m_104826_();
      }

      if (this.f_104557_ > 0) {
         this.m_6580_(this.f_104557_ - 1);
      }

      this.m_46473_().m_6180_("blocks");
      this.f_104559_.m_201698_(p_104726_1_, true);
      this.m_46473_().m_7238_();
   }

   private void m_104826_() {
      this.m_104637_(this.f_46442_.m_6793_() + 1L);
      if (this.f_46442_.m_5470_().m_46207_(GameRules.f_46140_)) {
         this.m_104746_(this.f_46442_.m_6792_() + 1L);
      }
   }

   public void m_104637_(long timeIn) {
      this.f_104563_.m_104849_(timeIn);
   }

   public void m_104746_(long time) {
      if (time < 0L) {
         time = -time;
         ((BooleanValue)this.m_46469_().m_46170_(GameRules.f_46140_)).m_46246_(false, null);
      } else {
         ((BooleanValue)this.m_46469_().m_46170_(GameRules.f_46140_)).m_46246_(true, null);
      }

      this.f_104563_.m_104863_(time);
   }

   public Iterable<Entity> m_104735_() {
      return this.m_142646_().m_142273_();
   }

   public void m_104804_() {
      ProfilerFiller profilerfiller = this.m_46473_();
      profilerfiller.m_6180_("entities");
      this.f_171630_.m_156910_(entityIn -> {
         if (!entityIn.m_213877_() && !entityIn.m_20159_() && !this.f_303463_.m_305579_(entityIn)) {
            this.m_46653_(this::m_104639_, entityIn);
         }
      });
      profilerfiller.m_7238_();
      this.m_46463_();
   }

   public boolean m_183599_(Entity entityIn) {
      return entityIn.m_146902_().m_45594_(this.f_104565_.f_91074_.m_146902_()) <= this.f_194123_;
   }

   public void m_104639_(Entity entityIn) {
      entityIn.m_146867_();
      entityIn.f_19797_++;
      this.m_46473_().m_6521_(() -> BuiltInRegistries.f_256780_.m_7981_(entityIn.m_6095_()).toString());
      if (ReflectorForge.canUpdate(entityIn)) {
         entityIn.m_8119_();
      }

      if (entityIn.m_213877_()) {
         this.onEntityRemoved(entityIn);
      }

      this.m_46473_().m_7238_();

      for (Entity entity : entityIn.m_20197_()) {
         this.m_104641_(entityIn, entity);
      }
   }

   private void m_104641_(Entity entityIn, Entity entityPassangerIn) {
      if (!entityPassangerIn.m_213877_() && entityPassangerIn.m_20202_() == entityIn) {
         if (entityPassangerIn instanceof Player || this.f_171630_.m_156914_(entityPassangerIn)) {
            entityPassangerIn.m_146867_();
            entityPassangerIn.f_19797_++;
            entityPassangerIn.m_6083_();

            for (Entity entity : entityPassangerIn.m_20197_()) {
               this.m_104641_(entityPassangerIn, entity);
            }
         }
      } else {
         entityPassangerIn.m_8127_();
      }
   }

   public void m_104665_(LevelChunk chunkIn) {
      chunkIn.m_187957_();
      this.f_104559_.m_7827_().m_9335_(chunkIn.m_7697_(), false);
      this.f_171631_.m_157658_(chunkIn.m_7697_());
   }

   public void m_171649_(ChunkPos chunkPosIn) {
      this.f_104558_.forEach((resolverIn, cacheIn) -> cacheIn.m_92655_(chunkPosIn.f_45578_, chunkPosIn.f_45579_));
      this.f_171631_.m_157651_(chunkPosIn);
      this.f_104562_.m_292785_(chunkPosIn);
   }

   public void m_104810_() {
      this.f_104558_.forEach((resolverIn, cacheIn) -> cacheIn.m_92654_());
   }

   public boolean m_7232_(int chunkX, int chunkZ) {
      return true;
   }

   public int m_104813_() {
      return this.f_171631_.m_157657_();
   }

   public void m_104739_(Entity entityIdIn) {
      if (!Reflector.EntityJoinLevelEvent_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.EntityJoinLevelEvent_Constructor, entityIdIn, this)) {
         this.m_171642_(entityIdIn.m_19879_(), RemovalReason.DISCARDED);
         this.f_171631_.m_157653_(entityIdIn);
         if (Reflector.IForgeEntity_onAddedToWorld.exists()) {
            Reflector.m_46374_(entityIdIn, Reflector.IForgeEntity_onAddedToWorld);
         }

         this.onEntityAdded(entityIdIn);
      }
   }

   public void m_171642_(int entityIdIn, RemovalReason reasonIn) {
      Entity entity = (Entity)this.m_142646_().m_142597_(entityIdIn);
      if (entity != null) {
         entity.m_142467_(reasonIn);
         entity.m_142036_();
      }
   }

   @Nullable
   public Entity m_6815_(int id) {
      return (Entity)this.m_142646_().m_142597_(id);
   }

   public void m_7462_() {
      this.f_104561_.m_104910_().m_129507_(Component.m_237115_("multiplayer.status.quitting"));
   }

   public void m_104784_(int posX, int posY, int posZ) {
      int i = 32;
      RandomSource randomsource = RandomSource.m_216327_();
      Block block = this.m_194187_();
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

      for (int j = 0; j < 667; j++) {
         this.m_233612_(posX, posY, posZ, 16, randomsource, block, blockpos$mutableblockpos);
         this.m_233612_(posX, posY, posZ, 32, randomsource, block, blockpos$mutableblockpos);
      }
   }

   @Nullable
   private Block m_194187_() {
      if (this.f_104565_.f_91072_.m_105295_() == GameType.CREATIVE) {
         ItemStack itemstack = this.f_104565_.f_91074_.m_21205_();
         Item item = itemstack.m_41720_();
         if (f_194124_.m_274455_(item) && item instanceof BlockItem blockitem) {
            return blockitem.m_40614_();
         }
      }

      return null;
   }

   public void m_233612_(int posX, int posY, int posZ, int offsetIn, RandomSource randomIn, @Nullable Block blockIn, MutableBlockPos posIn) {
      int i = posX + this.f_46441_.m_188503_(offsetIn) - this.f_46441_.m_188503_(offsetIn);
      int j = posY + this.f_46441_.m_188503_(offsetIn) - this.f_46441_.m_188503_(offsetIn);
      int k = posZ + this.f_46441_.m_188503_(offsetIn) - this.f_46441_.m_188503_(offsetIn);
      posIn.m_122178_(i, j, k);
      BlockState blockstate = this.m_8055_(posIn);
      blockstate.m_60734_().m_214162_(blockstate, this, posIn, randomIn);
      FluidState fluidstate = this.m_6425_(posIn);
      if (!fluidstate.m_76178_()) {
         fluidstate.m_230558_(this, posIn, randomIn);
         ParticleOptions particleoptions = fluidstate.m_76189_();
         if (particleoptions != null && this.f_46441_.m_188503_(10) == 0) {
            boolean flag = blockstate.m_60783_(this, posIn, Direction.DOWN);
            BlockPos blockpos = posIn.m_7495_();
            this.m_104689_(blockpos, this.m_8055_(blockpos), particleoptions, flag);
         }
      }

      if (blockIn == blockstate.m_60734_()) {
         this.m_7106_(new BlockParticleOption(ParticleTypes.f_194652_, blockstate), (double)i + 0.5, (double)j + 0.5, (double)k + 0.5, 0.0, 0.0, 0.0);
      }

      if (!blockstate.m_60838_(this, posIn)) {
         ((Biome)this.m_204166_(posIn).m_203334_())
            .m_47562_()
            .ifPresent(
               settingsIn -> {
                  if (settingsIn.m_220527_(this.f_46441_)) {
                     this.m_7106_(
                        settingsIn.m_47419_(),
                        (double)posIn.m_123341_() + this.f_46441_.m_188500_(),
                        (double)posIn.m_123342_() + this.f_46441_.m_188500_(),
                        (double)posIn.m_123343_() + this.f_46441_.m_188500_(),
                        0.0,
                        0.0,
                        0.0
                     );
                  }
               }
            );
      }
   }

   private void m_104689_(BlockPos blockPosIn, BlockState blockStateIn, ParticleOptions particleDataIn, boolean shapeDownSolid) {
      if (blockStateIn.m_60819_().m_76178_()) {
         VoxelShape voxelshape = blockStateIn.m_60812_(this, blockPosIn);
         double d0 = voxelshape.m_83297_(Direction.Axis.f_56474_);
         if (d0 < 1.0) {
            if (shapeDownSolid) {
               this.m_104592_(
                  (double)blockPosIn.m_123341_(),
                  (double)(blockPosIn.m_123341_() + 1),
                  (double)blockPosIn.m_123343_(),
                  (double)(blockPosIn.m_123343_() + 1),
                  (double)(blockPosIn.m_123342_() + 1) - 0.05,
                  particleDataIn
               );
            }
         } else if (!blockStateIn.m_204336_(BlockTags.f_13049_)) {
            double d1 = voxelshape.m_83288_(Direction.Axis.f_56474_);
            if (d1 > 0.0) {
               this.m_104694_(blockPosIn, particleDataIn, voxelshape, (double)blockPosIn.m_123342_() + d1 - 0.05);
            } else {
               BlockPos blockpos = blockPosIn.m_7495_();
               BlockState blockstate = this.m_8055_(blockpos);
               VoxelShape voxelshape1 = blockstate.m_60812_(this, blockpos);
               double d2 = voxelshape1.m_83297_(Direction.Axis.f_56474_);
               if (d2 < 1.0 && blockstate.m_60819_().m_76178_()) {
                  this.m_104694_(blockPosIn, particleDataIn, voxelshape, (double)blockPosIn.m_123342_() - 0.05);
               }
            }
         }
      }
   }

   private void m_104694_(BlockPos posIn, ParticleOptions particleDataIn, VoxelShape voxelShapeIn, double y) {
      this.m_104592_(
         (double)posIn.m_123341_() + voxelShapeIn.m_83288_(Direction.Axis.f_56473_),
         (double)posIn.m_123341_() + voxelShapeIn.m_83297_(Direction.Axis.f_56473_),
         (double)posIn.m_123343_() + voxelShapeIn.m_83288_(Direction.Axis.f_56475_),
         (double)posIn.m_123343_() + voxelShapeIn.m_83297_(Direction.Axis.f_56475_),
         y,
         particleDataIn
      );
   }

   private void m_104592_(double xStart, double xEnd, double zStart, double zEnd, double y, ParticleOptions particleDataIn) {
      this.m_7106_(
         particleDataIn, Mth.m_14139_(this.f_46441_.m_188500_(), xStart, xEnd), y, Mth.m_14139_(this.f_46441_.m_188500_(), zStart, zEnd), 0.0, 0.0, 0.0
      );
   }

   public CrashReportCategory m_6026_(CrashReport report) {
      CrashReportCategory crashreportcategory = super.m_6026_(report);
      crashreportcategory.m_128165_("Server brand", () -> this.f_104565_.f_91074_.f_108617_.m_295034_());
      crashreportcategory.m_128165_(
         "Server type", () -> this.f_104565_.m_91092_() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server"
      );
      crashreportcategory.m_128165_("Tracked entity count", () -> String.valueOf(this.m_104813_()));
      return crashreportcategory;
   }

   public void m_262808_(
      @Nullable Player player, double x, double y, double z, Holder<SoundEvent> soundIn, SoundSource category, float volume, float pitch, long randomSeedIn
   ) {
      if (Reflector.ForgeEventFactory_onPlaySoundAtPosition.exists()) {
         Object event = Reflector.ForgeEventFactory_onPlaySoundAtPosition.m_46374_(this, x, y, z, soundIn, category, volume, pitch);
         if (Reflector.callBoolean(event, Reflector.Event_isCanceled) || Reflector.m_46374_(event, Reflector.PlayLevelSoundEvent_getSound) == null) {
            return;
         }

         soundIn = (Holder<SoundEvent>)Reflector.m_46374_(event, Reflector.PlayLevelSoundEvent_getSound);
         category = (SoundSource)Reflector.m_46374_(event, Reflector.PlayLevelSoundEvent_getSource);
         volume = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewVolume);
         pitch = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewPitch);
      }

      if (player == this.f_104565_.f_91074_) {
         this.m_233602_(x, y, z, (SoundEvent)soundIn.m_203334_(), category, volume, pitch, false, randomSeedIn);
      }
   }

   public void m_213890_(
      @Nullable Player playerIn, Entity entityIn, Holder<SoundEvent> eventIn, SoundSource categoryIn, float volume, float pitch, long randomSeedIn
   ) {
      if (Reflector.ForgeEventFactory_onPlaySoundAtEntity.exists()) {
         Object event = Reflector.ForgeEventFactory_onPlaySoundAtEntity.m_46374_(entityIn, eventIn, categoryIn, volume, pitch);
         if (Reflector.callBoolean(event, Reflector.Event_isCanceled) || Reflector.m_46374_(event, Reflector.PlayLevelSoundEvent_getSound) == null) {
            return;
         }

         eventIn = (Holder<SoundEvent>)Reflector.m_46374_(event, Reflector.PlayLevelSoundEvent_getSound);
         categoryIn = (SoundSource)Reflector.m_46374_(event, Reflector.PlayLevelSoundEvent_getSource);
         volume = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewVolume);
         pitch = Reflector.callFloat(event, Reflector.PlayLevelSoundEvent_getNewPitch);
      }

      if (playerIn == this.f_104565_.f_91074_) {
         this.f_104565_.m_91106_().m_120367_(new EntityBoundSoundInstance((SoundEvent)eventIn.m_203334_(), categoryIn, volume, pitch, entityIn, randomSeedIn));
      }
   }

   public void m_307553_(Entity playerIn, SoundEvent soundIn, SoundSource sourceIn, float volumeIn, float pitchIn) {
      this.f_104565_.m_91106_().m_120367_(new EntityBoundSoundInstance(soundIn, sourceIn, volumeIn, pitchIn, playerIn, this.f_46441_.m_188505_()));
   }

   public void m_7785_(double x, double y, double z, SoundEvent soundIn, SoundSource category, float volume, float pitch, boolean distanceDelay) {
      this.m_233602_(x, y, z, soundIn, category, volume, pitch, distanceDelay, this.f_46441_.m_188505_());
   }

   private void m_233602_(
      double x, double y, double z, SoundEvent soundIn, SoundSource category, float volume, float pitch, boolean delayedIn, long randomSeedIn
   ) {
      double d0 = this.f_104565_.f_91063_.m_109153_().m_90583_().m_82531_(x, y, z);
      SimpleSoundInstance simplesoundinstance = new SimpleSoundInstance(soundIn, category, volume, pitch, RandomSource.m_216335_(randomSeedIn), x, y, z);
      if (delayedIn && d0 > 100.0) {
         double d1 = Math.sqrt(d0) / 40.0;
         this.f_104565_.m_91106_().m_120369_(simplesoundinstance, (int)(d1 * 20.0));
      } else {
         this.f_104565_.m_91106_().m_120367_(simplesoundinstance);
      }
   }

   public void m_7228_(double x, double y, double z, double motionX, double motionY, double motionZ, List<FireworkExplosion> compound) {
      if (compound.isEmpty()) {
         for (int i = 0; i < this.f_46441_.m_188503_(3) + 2; i++) {
            this.m_7106_(ParticleTypes.f_123759_, x, y, z, this.f_46441_.m_188583_() * 0.05, 0.005, this.f_46441_.m_188583_() * 0.05);
         }
      } else {
         this.f_104565_.f_91061_.m_107344_(new Starter(this, x, y, z, motionX, motionY, motionZ, this.f_104565_.f_91061_, compound));
      }
   }

   public void m_5503_(Packet<?> packetIn) {
      this.f_104561_.m_295327_(packetIn);
   }

   public RecipeManager m_7465_() {
      return this.f_104561_.m_105141_();
   }

   public TickRateManager m_304826_() {
      return this.f_303463_;
   }

   public LevelTickAccess<Block> m_183326_() {
      return BlackholeTickAccess.m_193145_();
   }

   public LevelTickAccess<Fluid> m_183324_() {
      return BlackholeTickAccess.m_193145_();
   }

   public ClientChunkCache m_7726_() {
      return this.f_104559_;
   }

   public boolean m_7731_(BlockPos pos, BlockState newState, int flags) {
      this.playerUpdate = this.isPlayerActing();
      boolean res = super.m_7731_(pos, newState, flags);
      this.playerUpdate = false;
      return res;
   }

   private boolean isPlayerActing() {
      return this.f_104565_.f_91072_ instanceof PlayerControllerOF pcof ? pcof.isActing() : false;
   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }

   public void onEntityAdded(Entity entityIn) {
      RandomEntities.entityLoaded(entityIn, this);
      if (Config.isDynamicLights()) {
         DynamicLights.entityAdded(entityIn, Config.getRenderGlobal());
      }
   }

   public void onEntityRemoved(Entity entityIn) {
      RandomEntities.entityUnloaded(entityIn, this);
      if (Config.isDynamicLights()) {
         DynamicLights.entityRemoved(entityIn, Config.getRenderGlobal());
      }
   }

   @Nullable
   public MapItemSavedData m_7489_(MapId mapName) {
      return (MapItemSavedData)this.f_104556_.get(mapName);
   }

   public void m_257583_(MapId keyIn, MapItemSavedData dataIn) {
      this.f_104556_.put(keyIn, dataIn);
   }

   public void m_142325_(MapId nameIn, MapItemSavedData mapDataIn) {
   }

   public MapId m_7354_() {
      return new MapId(0);
   }

   public Scoreboard m_6188_() {
      return this.f_104561_.m_323009_();
   }

   public void m_7260_(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
      this.f_104562_.m_109544_(this, pos, oldState, newState, flags);
   }

   public void m_6550_(BlockPos blockPosIn, BlockState oldState, BlockState newState) {
      this.f_104562_.m_109721_(blockPosIn, oldState, newState);
   }

   public void m_104793_(int sectionX, int sectionY, int sectionZ) {
      this.f_104562_.m_109490_(sectionX, sectionY, sectionZ);
   }

   public void m_6801_(int breakerId, BlockPos pos, int progress) {
      this.f_104562_.m_109774_(breakerId, pos, progress);
   }

   public void m_6798_(int id, BlockPos pos, int data) {
      this.f_104562_.m_109506_(id, pos, data);
   }

   public void m_5898_(@Nullable Player player, int type, BlockPos pos, int data) {
      try {
         this.f_104562_.m_234304_(type, pos, data);
      } catch (Throwable var8) {
         CrashReport crashreport = CrashReport.m_127521_(var8, "Playing level event");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Level event being played");
         crashreportcategory.m_128159_("Block coordinates", CrashReportCategory.m_178947_(this, pos));
         crashreportcategory.m_128159_("Event source", player);
         crashreportcategory.m_128159_("Event type", type);
         crashreportcategory.m_128159_("Event data", data);
         throw new ReportedException(crashreport);
      }
   }

   public void m_7106_(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.f_104562_.m_109743_(particleData, particleData.m_6012_().m_123742_(), x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_6493_(ParticleOptions particleData, boolean forceAlwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.f_104562_.m_109743_(particleData, particleData.m_6012_().m_123742_() || forceAlwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_7107_(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.f_104562_.m_109752_(particleData, false, true, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void m_6485_(ParticleOptions particleData, boolean ignoreRange, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      this.f_104562_.m_109752_(particleData, particleData.m_6012_().m_123742_() || ignoreRange, true, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public List<AbstractClientPlayer> m_6907_() {
      return this.f_104566_;
   }

   public Holder<Biome> m_203675_(int x, int y, int z) {
      return this.m_9598_().m_175515_(Registries.f_256952_).m_246971_(Biomes.f_48202_);
   }

   public float m_104805_(float partialTicks) {
      float f = this.m_46942_(partialTicks);
      float f1 = 1.0F - (Mth.m_14089_(f * (float) (Math.PI * 2)) * 2.0F + 0.2F);
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      f1 = 1.0F - f1;
      f1 *= 1.0F - this.m_46722_(partialTicks) * 5.0F / 16.0F;
      f1 *= 1.0F - this.m_46661_(partialTicks) * 5.0F / 16.0F;
      return f1 * 0.8F + 0.2F;
   }

   public Vec3 m_171660_(Vec3 posIn, float partialTicksIn) {
      float f = this.m_46942_(partialTicksIn);
      Vec3 vec3 = posIn.m_82492_(2.0, 2.0, 2.0).m_82490_(0.25);
      BiomeManager biomemanager = this.m_7062_();
      Vec3M vecCol = new Vec3M(0.0, 0.0, 0.0);
      Vec3 vec31 = CubicSampler.sampleM(vec3, (xIn, yIn, zIn) -> vecCol.fromRgbM(((Biome)biomemanager.m_204210_(xIn, yIn, zIn).m_203334_()).m_47463_()));
      float f1 = Mth.m_14089_(f * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      float f2 = (float)vec31.f_82479_ * f1;
      float f3 = (float)vec31.f_82480_ * f1;
      float f4 = (float)vec31.f_82481_ * f1;
      float f5 = this.m_46722_(partialTicksIn);
      if (f5 > 0.0F) {
         float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
         float f7 = 1.0F - f5 * 0.75F;
         f2 = f2 * f7 + f6 * (1.0F - f7);
         f3 = f3 * f7 + f6 * (1.0F - f7);
         f4 = f4 * f7 + f6 * (1.0F - f7);
      }

      float f9 = this.m_46661_(partialTicksIn);
      if (f9 > 0.0F) {
         float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
         float f8 = 1.0F - f9 * 0.75F;
         f2 = f2 * f8 + f10 * (1.0F - f8);
         f3 = f3 * f8 + f10 * (1.0F - f8);
         f4 = f4 * f8 + f10 * (1.0F - f8);
      }

      int i = this.m_104819_();
      if (i > 0) {
         float f11 = (float)i - partialTicksIn;
         if (f11 > 1.0F) {
            f11 = 1.0F;
         }

         f11 *= 0.45F;
         f2 = f2 * (1.0F - f11) + 0.8F * f11;
         f3 = f3 * (1.0F - f11) + 0.8F * f11;
         f4 = f4 * (1.0F - f11) + 1.0F * f11;
      }

      return new Vec3((double)f2, (double)f3, (double)f4);
   }

   public Vec3 m_104808_(float partialTicks) {
      float f = this.m_46942_(partialTicks);
      float f1 = Mth.m_14089_(f * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      float f2 = 1.0F;
      float f3 = 1.0F;
      float f4 = 1.0F;
      float f5 = this.m_46722_(partialTicks);
      if (f5 > 0.0F) {
         float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
         float f7 = 1.0F - f5 * 0.95F;
         f2 = f2 * f7 + f6 * (1.0F - f7);
         f3 = f3 * f7 + f6 * (1.0F - f7);
         f4 = f4 * f7 + f6 * (1.0F - f7);
      }

      f2 *= f1 * 0.9F + 0.1F;
      f3 *= f1 * 0.9F + 0.1F;
      f4 *= f1 * 0.85F + 0.15F;
      float f9 = this.m_46661_(partialTicks);
      if (f9 > 0.0F) {
         float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
         float f8 = 1.0F - f9 * 0.95F;
         f2 = f2 * f8 + f10 * (1.0F - f8);
         f3 = f3 * f8 + f10 * (1.0F - f8);
         f4 = f4 * f8 + f10 * (1.0F - f8);
      }

      return new Vec3((double)f2, (double)f3, (double)f4);
   }

   public float m_104811_(float partialTicks) {
      float f = this.m_46942_(partialTicks);
      float f1 = 1.0F - (Mth.m_14089_(f * (float) (Math.PI * 2)) * 2.0F + 0.25F);
      f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
      return f1 * f1 * 0.5F;
   }

   public int m_104819_() {
      return this.f_104565_.f_91066_.m_231935_().m_231551_() ? 0 : this.f_104557_;
   }

   public void m_6580_(int timeFlashIn) {
      this.f_104557_ = timeFlashIn;
   }

   public float m_7717_(Direction directionIn, boolean shadeIn) {
      boolean flag = this.m_104583_().m_108885_();
      boolean shaders = Config.isShaders();
      if (!shadeIn) {
         return flag ? 0.9F : 1.0F;
      } else {
         switch (<unrepresentable>.$SwitchMap$net$minecraft$core$Direction[directionIn.ordinal()]) {
            case 1:
               return flag ? 0.9F : (shaders ? Shaders.blockLightLevel05 : 0.5F);
            case 2:
               return flag ? 0.9F : 1.0F;
            case 3:
            case 4:
               if (Config.isShaders()) {
                  return Shaders.blockLightLevel08;
               }

               return 0.8F;
            case 5:
            case 6:
               if (Config.isShaders()) {
                  return Shaders.blockLightLevel06;
               }

               return 0.6F;
            default:
               return 1.0F;
         }
      }
   }

   public int m_6171_(BlockPos blockPosIn, ColorResolver colorResolverIn) {
      BlockTintCache blocktintcache = (BlockTintCache)this.f_104558_.get(colorResolverIn);
      return blocktintcache.m_193812_(blockPosIn);
   }

   public int m_104762_(BlockPos blockPosIn, ColorResolver colorResolverIn) {
      int i = Minecraft.m_91087_().f_91066_.m_232121_().m_231551_();
      if (i == 0) {
         return colorResolverIn.m_130045_(
            CustomColors.fixBiome((Biome)this.m_204166_(blockPosIn).m_203334_()), (double)blockPosIn.m_123341_(), (double)blockPosIn.m_123343_()
         );
      } else {
         int j = (i * 2 + 1) * (i * 2 + 1);
         int k = 0;
         int l = 0;
         int i1 = 0;
         Cursor3D cursor3d = new Cursor3D(
            blockPosIn.m_123341_() - i,
            blockPosIn.m_123342_(),
            blockPosIn.m_123343_() - i,
            blockPosIn.m_123341_() + i,
            blockPosIn.m_123342_(),
            blockPosIn.m_123343_() + i
         );
         MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

         while (cursor3d.m_122304_()) {
            blockpos$mutableblockpos.m_122178_(cursor3d.m_122305_(), cursor3d.m_122306_(), cursor3d.m_122307_());
            int j1 = colorResolverIn.m_130045_(
               CustomColors.fixBiome((Biome)this.m_204166_(blockpos$mutableblockpos).m_203334_()),
               (double)blockpos$mutableblockpos.m_123341_(),
               (double)blockpos$mutableblockpos.m_123343_()
            );
            k += (j1 & 0xFF0000) >> 16;
            l += (j1 & 0xFF00) >> 8;
            i1 += j1 & 0xFF;
         }

         return (k / j & 0xFF) << 16 | (l / j & 0xFF) << 8 | i1 / j & 0xFF;
      }
   }

   public void m_104752_(BlockPos blockPosIn, float angleIn) {
      this.f_46442_.m_7250_(blockPosIn, angleIn);
   }

   public String toString() {
      return "ClientLevel";
   }

   public ClientLevel.ClientLevelData m_6106_() {
      return this.f_104563_;
   }

   public void m_214171_(Holder<GameEvent> eventIn, Vec3 posIn, Context contextIn) {
   }

   protected Map<MapId, MapItemSavedData> m_171684_() {
      return ImmutableMap.copyOf(this.f_104556_);
   }

   protected void m_171672_(Map<MapId, MapItemSavedData> mapDataIn) {
      this.f_104556_.putAll(mapDataIn);
   }

   protected LevelEntityGetter<Entity> m_142646_() {
      return this.f_171631_.m_157645_();
   }

   public String m_46464_() {
      return "Chunks[C] W: " + this.f_104559_.m_6754_() + " E: " + this.f_171631_.m_157664_();
   }

   public void m_142052_(BlockPos blockPosIn, BlockState stateIn) {
      this.f_104565_.f_91061_.m_107355_(blockPosIn, stateIn);
   }

   public void m_194174_(int distanceIn) {
      this.f_194123_ = distanceIn;
   }

   public int m_194186_() {
      return this.f_194123_;
   }

   public FeatureFlagSet m_246046_() {
      return this.f_104561_.m_247016_();
   }

   public PotionBrewing m_319308_() {
      return this.f_104561_.m_320887_();
   }

   public TransientEntitySectionManager getEntityStorage() {
      return this.f_171631_;
   }

   public EntitySectionStorage getSectionStorage() {
      return EntitySection.getSectionStorage(this.f_171631_);
   }

   public Collection<PartEntity<?>> getPartEntities() {
      return this.partEntities.values();
   }

   public ModelDataManager getModelDataManager() {
      return this.modelDataManager;
   }

   public float getShade(float normalX, float normalY, float normalZ, boolean shade) {
      boolean constantAmbientLight = this.m_104583_().m_108885_();
      if (!shade) {
         return constantAmbientLight ? 0.9F : 1.0F;
      } else {
         return QuadLighter.calculateShade(normalX, normalY, normalZ, constantAmbientLight);
      }
   }

   public static class ClientLevelData implements WritableLevelData {
      private boolean f_104830_;
      private GameRules f_104831_;
      private boolean f_104832_;
      private BlockPos f_316685_;
      private float f_104836_;
      private long f_104837_;
      private long f_104838_;
      private boolean f_104839_;
      private Difficulty f_104840_;
      private boolean f_104841_;

      public ClientLevelData(Difficulty difficultyIn, boolean hardcoreIn, boolean flatIn) {
         this.f_104840_ = difficultyIn;
         this.f_104830_ = hardcoreIn;
         this.f_104832_ = flatIn;
         this.f_104831_ = new GameRules();
      }

      public BlockPos m_318766_() {
         return this.f_316685_;
      }

      public float m_6790_() {
         return this.f_104836_;
      }

      public long m_6793_() {
         return this.f_104837_;
      }

      public long m_6792_() {
         return this.f_104838_;
      }

      public void m_104849_(long timeIn) {
         this.f_104837_ = timeIn;
      }

      public void m_104863_(long timeIn) {
         this.f_104838_ = timeIn;
      }

      public void m_7250_(BlockPos spawnPoint, float angleIn) {
         this.f_316685_ = spawnPoint.m_7949_();
         this.f_104836_ = angleIn;
      }

      public boolean m_6534_() {
         return false;
      }

      public boolean m_6533_() {
         return this.f_104839_;
      }

      public void m_5565_(boolean isRaining) {
         this.f_104839_ = isRaining;
      }

      public boolean m_5466_() {
         return this.f_104830_;
      }

      public GameRules m_5470_() {
         return this.f_104831_;
      }

      public Difficulty m_5472_() {
         return this.f_104840_;
      }

      public boolean m_5474_() {
         return this.f_104841_;
      }

      public void m_142471_(CrashReportCategory categoryIn, LevelHeightAccessor heightIn) {
         super.m_142471_(categoryIn, heightIn);
      }

      public void m_104851_(Difficulty difficultyIn) {
         Reflector.ForgeHooks_onDifficultyChange.callVoid(difficultyIn, this.f_104840_);
         this.f_104840_ = difficultyIn;
      }

      public void m_104858_(boolean lockedIn) {
         this.f_104841_ = lockedIn;
      }

      public double m_171687_(LevelHeightAccessor accessorIn) {
         return this.f_104832_ ? (double)accessorIn.m_141937_() : 63.0;
      }

      public float m_205519_() {
         return this.f_104832_ ? 1.0F : 0.03125F;
      }
   }

   class EntityCallbacks implements LevelCallback<Entity> {
      public void m_141989_(Entity entityIn) {
      }

      public void m_141986_(Entity entityIn) {
      }

      public void m_141987_(Entity entityIn) {
         ClientLevel.this.f_171630_.m_156908_(entityIn);
      }

      public void m_141983_(Entity entityIn) {
         ClientLevel.this.f_171630_.m_156912_(entityIn);
      }

      public void m_141985_(Entity entityIn) {
         if (entityIn instanceof AbstractClientPlayer) {
            ClientLevel.this.f_104566_.add((AbstractClientPlayer)entityIn);
         }

         if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
            boolean multipartEntity = Reflector.callBoolean(entityIn, Reflector.IForgeEntity_isMultipartEntity);
            if (multipartEntity) {
               PartEntity[] parts = (PartEntity[])Reflector.m_46374_(entityIn, Reflector.IForgeEntity_getParts);

               for (PartEntity part : parts) {
                  ClientLevel.this.partEntities.put(part.m_19879_(), part);
               }
            }
         }
      }

      public void m_141981_(Entity entityIn) {
         entityIn.m_19877_();
         ClientLevel.this.f_104566_.remove(entityIn);
         if (Reflector.IForgeEntity_onRemovedFromWorld.exists()) {
            Reflector.m_46374_(entityIn, Reflector.IForgeEntity_onRemovedFromWorld);
         }

         if (Reflector.EntityLeaveLevelEvent_Constructor.exists()) {
            Reflector.postForgeBusEvent(Reflector.EntityLeaveLevelEvent_Constructor, entityIn, ClientLevel.this);
         }

         if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
            boolean multipartEntity = Reflector.callBoolean(entityIn, Reflector.IForgeEntity_isMultipartEntity);
            if (multipartEntity) {
               PartEntity[] parts = (PartEntity[])Reflector.m_46374_(entityIn, Reflector.IForgeEntity_getParts);

               for (PartEntity part : parts) {
                  ClientLevel.this.partEntities.remove(part.m_19879_(), part);
               }
            }
         }

         ClientLevel.this.onEntityRemoved(entityIn);
      }

      public void m_214006_(Entity entityIn) {
      }
   }
}
