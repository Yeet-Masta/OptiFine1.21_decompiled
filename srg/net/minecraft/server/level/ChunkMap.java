package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtException;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.server.level.ChunkHolder.PlayerProvider;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter.Message;
import net.minecraft.server.level.ChunkTrackingView.Positioned;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.StaticCache2D;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.chunk.status.ChunkType;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.minecraft.world.phys.Vec3;
import net.optifine.reflect.Reflector;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;

public class ChunkMap extends ChunkStorage implements PlayerProvider, GeneratingChunkMap {
   private static final ChunkResult<List<ChunkAccess>> f_336919_ = ChunkResult.m_322259_("Unloaded chunks found in range");
   private static final CompletableFuture<ChunkResult<List<ChunkAccess>>> f_337118_ = CompletableFuture.completedFuture(f_336919_);
   private static final byte f_143034_ = -1;
   private static final byte f_143035_ = 0;
   private static final byte f_143036_ = 1;
   private static final Logger f_140128_ = LogUtils.getLogger();
   private static final int f_143037_ = 200;
   private static final int f_198789_ = 20;
   private static final int f_202982_ = 10000;
   public static final int f_143038_ = 2;
   public static final int f_143032_ = 32;
   public static final int f_143033_ = ChunkLevel.m_287154_(FullChunkStatus.ENTITY_TICKING);
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140129_ = new Long2ObjectLinkedOpenHashMap();
   private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140130_ = this.f_140129_.clone();
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140131_ = new Long2ObjectLinkedOpenHashMap();
   private final List<ChunkGenerationTask> f_337610_ = new ArrayList();
   final ServerLevel f_140133_;
   private final ThreadedLevelLightEngine f_140134_;
   private final BlockableEventLoop<Runnable> f_140135_;
   private final RandomState f_214834_;
   private final ChunkGeneratorStructureState f_254626_;
   private final Supplier<DimensionDataStorage> f_140137_;
   private final PoiManager f_140138_;
   final LongSet f_140139_ = new LongOpenHashSet();
   private boolean f_140140_;
   private final ChunkTaskPriorityQueueSorter f_140141_;
   private final ProcessorHandle<Message<Runnable>> f_140142_;
   private final ProcessorHandle<Message<Runnable>> f_140143_;
   private final ChunkProgressListener f_140144_;
   private final ChunkStatusUpdateListener f_143031_;
   private final ChunkMap.DistanceManager f_140145_;
   private final AtomicInteger f_140146_ = new AtomicInteger();
   private final String f_182284_;
   private final PlayerMap f_140149_ = new PlayerMap();
   private final Int2ObjectMap<ChunkMap.TrackedEntity> f_140150_ = new Int2ObjectOpenHashMap();
   private final Long2ByteMap f_140151_ = new Long2ByteOpenHashMap();
   private final Long2LongMap f_202981_ = new Long2LongOpenHashMap();
   private final Queue<Runnable> f_140125_ = Queues.newConcurrentLinkedQueue();
   private int f_290679_;
   private final WorldGenContext f_314073_;

   public ChunkMap(
      ServerLevel worldIn,
      LevelStorageAccess levelSaveIn,
      DataFixer dataFixerIn,
      StructureTemplateManager templateManagerIn,
      Executor executorIn,
      BlockableEventLoop<Runnable> taskExecutorIn,
      LightChunkGetter lightProviderIn,
      ChunkGenerator chunkGeneratorIn,
      ChunkProgressListener progressListenerIn,
      ChunkStatusUpdateListener statusUpdateListenerIn,
      Supplier<DimensionDataStorage> dimensionDataIn,
      int viewDistanceIn,
      boolean syncWritesIn
   ) {
      super(
         new RegionStorageInfo(levelSaveIn.m_78277_(), worldIn.m_46472_(), "chunk"),
         levelSaveIn.m_197394_(worldIn.m_46472_()).resolve("region"),
         dataFixerIn,
         syncWritesIn
      );
      Path path = levelSaveIn.m_197394_(worldIn.m_46472_());
      this.f_182284_ = path.getFileName().toString();
      this.f_140133_ = worldIn;
      RegistryAccess registryaccess = worldIn.m_9598_();
      long i = worldIn.m_7328_();
      if (chunkGeneratorIn instanceof NoiseBasedChunkGenerator noisebasedchunkgenerator) {
         this.f_214834_ = RandomState.m_255302_(
            (NoiseGeneratorSettings)noisebasedchunkgenerator.m_224341_().m_203334_(), registryaccess.m_255025_(Registries.f_256865_), i
         );
      } else {
         this.f_214834_ = RandomState.m_255302_(NoiseGeneratorSettings.m_238396_(), registryaccess.m_255025_(Registries.f_256865_), i);
      }

      this.f_254626_ = chunkGeneratorIn.m_255169_(registryaccess.m_255025_(Registries.f_256998_), this.f_214834_, i);
      this.f_140135_ = taskExecutorIn;
      ProcessorMailbox<Runnable> processormailbox1 = ProcessorMailbox.m_18751_(executorIn, "worldgen");
      ProcessorHandle<Runnable> processorhandle = ProcessorHandle.m_18714_("main", taskExecutorIn::m_6937_);
      this.f_140144_ = progressListenerIn;
      this.f_143031_ = statusUpdateListenerIn;
      ProcessorMailbox<Runnable> processormailbox = ProcessorMailbox.m_18751_(executorIn, "light");
      this.f_140141_ = new ChunkTaskPriorityQueueSorter(ImmutableList.of(processormailbox1, processorhandle, processormailbox), executorIn, Integer.MAX_VALUE);
      this.f_140142_ = this.f_140141_.m_140604_(processormailbox1, false);
      this.f_140143_ = this.f_140141_.m_140604_(processorhandle, false);
      this.f_140134_ = new ThreadedLevelLightEngine(
         lightProviderIn, this, this.f_140133_.m_6042_().f_223549_(), processormailbox, this.f_140141_.m_140604_(processormailbox, false)
      );
      this.f_140145_ = new ChunkMap.DistanceManager(executorIn, taskExecutorIn);
      this.f_140137_ = dimensionDataIn;
      this.f_140138_ = new PoiManager(
         new RegionStorageInfo(levelSaveIn.m_78277_(), worldIn.m_46472_(), "poi"),
         path.resolve("poi"),
         dataFixerIn,
         syncWritesIn,
         registryaccess,
         worldIn.m_7654_(),
         worldIn
      );
      this.m_293413_(viewDistanceIn);
      this.f_314073_ = new WorldGenContext(worldIn, chunkGeneratorIn, templateManagerIn, this.f_140134_, this.f_140143_);
   }

   protected ChunkGenerator m_183719_() {
      return this.f_314073_.f_315907_();
   }

   protected ChunkGeneratorStructureState m_255435_() {
      return this.f_254626_;
   }

   protected RandomState m_214914_() {
      return this.f_214834_;
   }

   private static double m_140226_(ChunkPos chunkPosIn, Entity entityIn) {
      double d0 = (double)SectionPos.m_175554_(chunkPosIn.f_45578_, 8);
      double d1 = (double)SectionPos.m_175554_(chunkPosIn.f_45579_, 8);
      double d2 = d0 - entityIn.m_20185_();
      double d3 = d1 - entityIn.m_20189_();
      return d2 * d2 + d3 * d3;
   }

   boolean m_295978_(ServerPlayer playerIn, int xIn, int zIn) {
      return playerIn.m_292900_().m_294219_(xIn, zIn) && !playerIn.f_8906_.f_290664_.m_296008_(ChunkPos.m_45589_(xIn, zIn));
   }

   private boolean m_293654_(ServerPlayer playerIn, int xIn, int zIn) {
      if (!this.m_295978_(playerIn, xIn, zIn)) {
         return false;
      } else {
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if ((i != 0 || j != 0) && !this.m_295978_(playerIn, xIn + i, zIn + j)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected ThreadedLevelLightEngine m_140166_() {
      return this.f_140134_;
   }

   @Nullable
   protected ChunkHolder m_140174_(long chunkPosIn) {
      return (ChunkHolder)this.f_140129_.get(chunkPosIn);
   }

   @Nullable
   protected ChunkHolder m_140327_(long chunkPosIn) {
      return (ChunkHolder)this.f_140130_.get(chunkPosIn);
   }

   protected IntSupplier m_140371_(long chunkPosIn) {
      return () -> {
         ChunkHolder chunkholder = this.m_140327_(chunkPosIn);
         return chunkholder == null ? ChunkTaskPriorityQueue.f_140508_ - 1 : Math.min(chunkholder.m_140094_(), ChunkTaskPriorityQueue.f_140508_ - 1);
      };
   }

   public String m_140204_(ChunkPos pos) {
      ChunkHolder chunkholder = this.m_140327_(pos.m_45588_());
      if (chunkholder == null) {
         return "null";
      } else {
         String s = chunkholder.m_140093_() + "\n";
         ChunkStatus chunkstatus = chunkholder.m_338382_();
         ChunkAccess chunkaccess = chunkholder.m_340032_();
         if (chunkstatus != null) {
            s = s + "St: \u00a7" + chunkstatus.m_323297_() + chunkstatus + "\u00a7r\n";
         }

         if (chunkaccess != null) {
            s = s + "Ch: \u00a7" + chunkaccess.m_6415_().m_323297_() + chunkaccess.m_6415_() + "\u00a7r\n";
         }

         FullChunkStatus fullchunkstatus = chunkholder.m_339537_();
         s = s + "\u00a7" + fullchunkstatus.ordinal() + fullchunkstatus;
         return s + "\u00a7r";
      }
   }

   private CompletableFuture<ChunkResult<List<ChunkAccess>>> m_280541_(ChunkHolder chunkHolderIn, int rangeIn, IntFunction<ChunkStatus> funcStatusIn) {
      if (rangeIn == 0) {
         ChunkStatus chunkstatus1 = (ChunkStatus)funcStatusIn.apply(0);
         return chunkHolderIn.m_340221_(chunkstatus1, this).thenApply(resultIn -> resultIn.m_320014_(List::of));
      } else {
         List<CompletableFuture<ChunkResult<ChunkAccess>>> list = new ArrayList();
         ChunkPos chunkpos = chunkHolderIn.m_338581_();

         for (int i = -rangeIn; i <= rangeIn; i++) {
            for (int j = -rangeIn; j <= rangeIn; j++) {
               int k = Math.max(Math.abs(j), Math.abs(i));
               long l = ChunkPos.m_45589_(chunkpos.f_45578_ + j, chunkpos.f_45579_ + i);
               ChunkHolder chunkholder = this.m_140174_(l);
               if (chunkholder == null) {
                  return f_337118_;
               }

               ChunkStatus chunkstatus = (ChunkStatus)funcStatusIn.apply(k);
               list.add(chunkholder.m_340221_(chunkstatus, this));
            }
         }

         return Util.m_137567_(list).thenApply(chunkResultsIn -> {
            List<ChunkAccess> list1 = Lists.newArrayList();

            for (ChunkResult<ChunkAccess> chunkresult : chunkResultsIn) {
               if (chunkresult == null) {
                  throw this.m_203751_(new IllegalStateException("At least one of the chunk futures were null"), "n/a");
               }

               ChunkAccess chunkaccess = (ChunkAccess)chunkresult.m_318814_(null);
               if (chunkaccess == null) {
                  return f_336919_;
               }

               list1.add(chunkaccess);
            }

            return ChunkResult.m_323605_(list1);
         });
      }
   }

   public ReportedException m_203751_(IllegalStateException exceptionIn, String detailsIn) {
      StringBuilder stringbuilder = new StringBuilder();
      Consumer<ChunkHolder> consumer = holderIn -> holderIn.m_340094_()
            .forEach(
               pairIn -> {
                  ChunkStatus chunkstatus = (ChunkStatus)pairIn.getFirst();
                  CompletableFuture<ChunkResult<ChunkAccess>> completablefuture = (CompletableFuture<ChunkResult<ChunkAccess>>)pairIn.getSecond();
                  if (completablefuture != null && completablefuture.isDone() && completablefuture.join() == null) {
                     stringbuilder.append(holderIn.m_338581_())
                        .append(" - status: ")
                        .append(chunkstatus)
                        .append(" future: ")
                        .append(completablefuture)
                        .append(System.lineSeparator());
                  }
               }
            );
      stringbuilder.append("Updating:").append(System.lineSeparator());
      this.f_140129_.values().forEach(consumer);
      stringbuilder.append("Visible:").append(System.lineSeparator());
      this.f_140130_.values().forEach(consumer);
      CrashReport crashreport = CrashReport.m_127521_(exceptionIn, "Chunk loading");
      CrashReportCategory crashreportcategory = crashreport.m_127514_("Chunk loading");
      crashreportcategory.m_128159_("Details", detailsIn);
      crashreportcategory.m_128159_("Futures", stringbuilder);
      return new ReportedException(crashreport);
   }

   public CompletableFuture<ChunkResult<LevelChunk>> m_280208_(ChunkHolder chunkHolderIn) {
      return this.m_280541_(chunkHolderIn, 2, levelIn -> ChunkStatus.f_315432_)
         .thenApplyAsync(resultIn -> resultIn.m_320014_(chunksIn -> (LevelChunk)chunksIn.get(chunksIn.size() / 2)), this.f_140135_);
   }

   @Nullable
   ChunkHolder m_140176_(long chunkPosIn, int newLevel, @Nullable ChunkHolder holder, int oldLevel) {
      if (!ChunkLevel.m_287217_(oldLevel) && !ChunkLevel.m_287217_(newLevel)) {
         return holder;
      } else {
         if (holder != null) {
            holder.m_140027_(newLevel);
         }

         if (holder != null) {
            if (!ChunkLevel.m_287217_(newLevel)) {
               this.f_140139_.add(chunkPosIn);
            } else {
               this.f_140139_.remove(chunkPosIn);
            }
         }

         if (ChunkLevel.m_287217_(newLevel) && holder == null) {
            holder = (ChunkHolder)this.f_140131_.remove(chunkPosIn);
            if (holder != null) {
               holder.m_140027_(newLevel);
            } else {
               holder = new ChunkHolder(new ChunkPos(chunkPosIn), newLevel, this.f_140133_, this.f_140134_, this.f_140141_, this);
            }

            this.f_140129_.put(chunkPosIn, holder);
            this.f_140140_ = true;
         }

         if (Reflector.ForgeEventFactory_fireChunkTicketLevelUpdated.exists()) {
            Reflector.ForgeEventFactory_fireChunkTicketLevelUpdated.call(this.f_140133_, chunkPosIn, oldLevel, newLevel, holder);
         }

         return holder;
      }
   }

   public void close() throws IOException {
      try {
         this.f_140141_.close();
         this.f_140138_.close();
      } finally {
         super.close();
      }
   }

   protected void m_140318_(boolean flush) {
      if (flush) {
         List<ChunkHolder> list = this.f_140130_.values().stream().filter(ChunkHolder::m_140095_).peek(ChunkHolder::m_140096_).toList();
         MutableBoolean mutableboolean = new MutableBoolean();

         do {
            mutableboolean.setFalse();
            list.stream()
               .map(chunkHolderIn -> {
                  this.f_140135_.m_18701_(chunkHolderIn::m_339539_);
                  return chunkHolderIn.m_340032_();
               })
               .filter(chunkIn -> chunkIn instanceof ImposterProtoChunk || chunkIn instanceof LevelChunk)
               .filter(this::m_140258_)
               .forEach(voidIn -> mutableboolean.setTrue());
         } while (mutableboolean.isTrue());

         this.m_140353_(() -> true);
         this.m_63514_();
      } else {
         this.f_140130_.values().forEach(this::m_198874_);
      }
   }

   protected void m_140280_(BooleanSupplier hasMoreTime) {
      ProfilerFiller profilerfiller = this.f_140133_.m_46473_();
      profilerfiller.m_6180_("poi");
      this.f_140138_.m_6202_(hasMoreTime);
      profilerfiller.m_6182_("chunk_unload");
      if (!this.f_140133_.m_7441_()) {
         this.m_140353_(hasMoreTime);
      }

      profilerfiller.m_7238_();
   }

   public boolean m_201907_() {
      return this.f_140134_.m_75808_()
         || !this.f_140131_.isEmpty()
         || !this.f_140129_.isEmpty()
         || this.f_140138_.m_202164_()
         || !this.f_140139_.isEmpty()
         || !this.f_140125_.isEmpty()
         || this.f_140141_.m_201909_()
         || this.f_140145_.m_201911_();
   }

   private void m_140353_(BooleanSupplier hasMoreTime) {
      LongIterator longiterator = this.f_140139_.iterator();
      int i = 0;

      while (longiterator.hasNext() && (hasMoreTime.getAsBoolean() || i < 200 || this.f_140139_.size() > 2000)) {
         long j = longiterator.nextLong();
         ChunkHolder chunkholder = (ChunkHolder)this.f_140129_.get(j);
         if (chunkholder != null) {
            if (chunkholder.m_339108_() != 0) {
               continue;
            }

            this.f_140129_.remove(j);
            this.f_140131_.put(j, chunkholder);
            this.f_140140_ = true;
            i++;
            this.m_140181_(j, chunkholder);
         }

         longiterator.remove();
      }

      int k = Math.max(0, this.f_140125_.size() - 2000);

      Runnable runnable;
      while ((hasMoreTime.getAsBoolean() || k > 0) && (runnable = (Runnable)this.f_140125_.poll()) != null) {
         k--;
         runnable.run();
      }

      int l = 0;
      ObjectIterator<ChunkHolder> objectiterator = this.f_140130_.values().iterator();

      while (l < 20 && hasMoreTime.getAsBoolean() && objectiterator.hasNext()) {
         if (this.m_198874_((ChunkHolder)objectiterator.next())) {
            l++;
         }
      }
   }

   private void m_140181_(long chunkPosIn, ChunkHolder chunkHolderIn) {
      chunkHolderIn.m_339472_().thenRunAsync(() -> {
         if (!chunkHolderIn.m_339539_()) {
            this.m_140181_(chunkPosIn, chunkHolderIn);
         } else {
            ChunkAccess chunkaccess = chunkHolderIn.m_340032_();
            if (this.f_140131_.remove(chunkPosIn, chunkHolderIn) && chunkaccess != null) {
               if (chunkaccess instanceof LevelChunk levelchunk) {
                  levelchunk.m_62913_(false);
                  if (Reflector.ForgeEventFactory_onChunkUnload.exists()) {
                     Reflector.ForgeEventFactory_onChunkUnload.call(chunkaccess);
                  }
               }

               this.m_140258_(chunkaccess);
               if (chunkaccess instanceof LevelChunk levelchunk1) {
                  this.f_140133_.m_8712_(levelchunk1);
               }

               this.f_140134_.m_9330_(chunkaccess.m_7697_());
               this.f_140134_.m_9409_();
               this.f_140144_.m_5511_(chunkaccess.m_7697_(), null);
               this.f_202981_.remove(chunkaccess.m_7697_().m_45588_());
            }
         }
      }, this.f_140125_::add).whenComplete((worldIn, throwableIn) -> {
         if (throwableIn != null) {
            f_140128_.error("Failed to save chunk {}", chunkHolderIn.m_338581_(), throwableIn);
         }
      });
   }

   protected boolean m_140324_() {
      if (!this.f_140140_) {
         return false;
      } else {
         this.f_140130_ = this.f_140129_.clone();
         this.f_140140_ = false;
         return true;
      }
   }

   private CompletableFuture<ChunkAccess> m_140417_(ChunkPos chunkPosIn) {
      return this.m_214963_(chunkPosIn).thenApply(tagIn -> tagIn.filter(tag2In -> {
            boolean flag = m_214940_(tag2In);
            if (!flag) {
               f_140128_.error("Chunk file at {} is missing level data, skipping", chunkPosIn);
            }

            return flag;
         })).thenApplyAsync(tag3In -> {
         this.f_140133_.m_46473_().m_6174_("chunkLoad");
         if (tag3In.isPresent()) {
            ChunkAccess chunkaccess = ChunkSerializer.m_188230_(this.f_140133_, this.f_140138_, this.m_340375_(), chunkPosIn, (CompoundTag)tag3In.get());
            this.m_140229_(chunkPosIn, chunkaccess.m_6415_().m_321717_());
            return chunkaccess;
         } else {
            return this.m_214961_(chunkPosIn);
         }
      }, this.f_140135_).exceptionallyAsync(throwableIn -> this.m_214901_(throwableIn, chunkPosIn), this.f_140135_);
   }

   private static boolean m_214940_(CompoundTag tagIn) {
      return tagIn.m_128425_("Status", 8);
   }

   private ChunkAccess m_214901_(Throwable throwableIn, ChunkPos chunkPosIn) {
      Throwable throwable = throwableIn instanceof CompletionException completionexception ? completionexception.getCause() : throwableIn;
      Throwable throwable1 = throwable instanceof ReportedException reportedexception ? reportedexception.getCause() : throwable;
      boolean flag1 = throwable1 instanceof Error;
      boolean flag = throwable1 instanceof IOException || throwable1 instanceof NbtException;
      if (!flag1) {
         if (!flag) {
         }

         this.f_140133_.m_7654_().m_293783_(throwable1, this.m_340375_(), chunkPosIn);
         return this.m_214961_(chunkPosIn);
      } else {
         CrashReport crashreport = CrashReport.m_127521_(throwableIn, "Exception loading chunk");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Chunk being loaded");
         crashreportcategory.m_128159_("pos", chunkPosIn);
         this.m_140422_(chunkPosIn);
         throw new ReportedException(crashreport);
      }
   }

   private ChunkAccess m_214961_(ChunkPos chunkPosIn) {
      this.m_140422_(chunkPosIn);
      return new ProtoChunk(chunkPosIn, UpgradeData.f_63320_, this.f_140133_, this.f_140133_.m_9598_().m_175515_(Registries.f_256952_), null);
   }

   private void m_140422_(ChunkPos chunkPosIn) {
      this.f_140151_.put(chunkPosIn.m_45588_(), (byte)-1);
   }

   private byte m_140229_(ChunkPos chunkPosIn, ChunkType chunkTypeIn) {
      return this.f_140151_.put(chunkPosIn.m_45588_(), (byte)(chunkTypeIn == ChunkType.PROTOCHUNK ? -1 : 1));
   }

   public GenerationChunkHolder m_339158_(long posIn) {
      ChunkHolder chunkholder = (ChunkHolder)this.f_140129_.get(posIn);
      chunkholder.m_338841_();
      return chunkholder;
   }

   public void m_338685_(GenerationChunkHolder holderIn) {
      holderIn.m_340129_();
   }

   public CompletableFuture<ChunkAccess> m_338637_(GenerationChunkHolder holderIn, ChunkStep chunkStepIn, StaticCache2D<GenerationChunkHolder> cacheIn) {
      ChunkPos chunkpos = holderIn.m_338581_();
      if (chunkStepIn.f_337059_() == ChunkStatus.f_314297_) {
         return this.m_140417_(chunkpos);
      } else {
         try {
            GenerationChunkHolder generationchunkholder = (GenerationChunkHolder)cacheIn.m_338758_(chunkpos.f_45578_, chunkpos.f_45579_);
            ChunkAccess chunkaccess = generationchunkholder.m_338381_(chunkStepIn.f_337059_().m_322072_());
            if (chunkaccess == null) {
               throw new IllegalStateException("Parent chunk missing");
            } else {
               CompletableFuture<ChunkAccess> completablefuture = chunkStepIn.m_338624_(this.f_314073_, cacheIn, chunkaccess);
               this.f_140144_.m_5511_(chunkpos, chunkStepIn.f_337059_());
               return completablefuture;
            }
         } catch (Exception var8) {
            var8.getStackTrace();
            CrashReport crashreport = CrashReport.m_127521_(var8, "Exception generating new chunk");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Chunk to be generated");
            crashreportcategory.m_128165_("Status being generated", () -> chunkStepIn.f_337059_().m_339742_());
            crashreportcategory.m_128159_("Location", String.format(Locale.ROOT, "%d,%d", chunkpos.f_45578_, chunkpos.f_45579_));
            crashreportcategory.m_128159_("Position hash", ChunkPos.m_45589_(chunkpos.f_45578_, chunkpos.f_45579_));
            crashreportcategory.m_128159_("Generator", this.m_183719_());
            this.f_140135_.execute(() -> {
               throw new ReportedException(crashreport);
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   public ChunkGenerationTask m_338350_(ChunkStatus statusIn, ChunkPos posIn) {
      ChunkGenerationTask chunkgenerationtask = ChunkGenerationTask.m_339931_(this, statusIn, posIn);
      this.f_337610_.add(chunkgenerationtask);
      return chunkgenerationtask;
   }

   private void m_340434_(ChunkGenerationTask taskIn) {
      this.f_140142_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(taskIn.m_340452_(), () -> {
         CompletableFuture<?> completablefuture = taskIn.m_340381_();
         if (completablefuture != null) {
            completablefuture.thenRun(() -> this.m_340434_(taskIn));
         }
      }));
   }

   public void m_339290_() {
      this.f_337610_.forEach(this::m_340434_);
      this.f_337610_.clear();
   }

   public CompletableFuture<ChunkResult<LevelChunk>> m_143053_(ChunkHolder chunkHolderIn) {
      CompletableFuture<ChunkResult<List<ChunkAccess>>> completablefuture = this.m_280541_(chunkHolderIn, 1, levelIn -> ChunkStatus.f_315432_);
      CompletableFuture<ChunkResult<LevelChunk>> completablefuture1 = completablefuture.thenApplyAsync(
            resultIn -> resultIn.m_320014_(chunksIn -> (LevelChunk)chunksIn.get(chunksIn.size() / 2)),
            runnableIn -> this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(chunkHolderIn, runnableIn))
         )
         .thenApplyAsync(result2In -> result2In.m_320477_(levelChunk2In -> {
               levelChunk2In.m_62812_();
               this.f_140133_.m_184102_(levelChunk2In);
               CompletableFuture<?> completablefuture2 = chunkHolderIn.m_294134_();
               if (completablefuture2.isDone()) {
                  this.m_293802_(levelChunk2In);
               } else {
                  completablefuture2.thenAcceptAsync(voidIn -> this.m_293802_(levelChunk2In), this.f_140135_);
               }
            }), this.f_140135_);
      completablefuture1.handle((resultIn, throwableIn) -> {
         this.f_140146_.getAndIncrement();
         return null;
      });
      return completablefuture1;
   }

   private void m_293802_(LevelChunk chunkIn) {
      ChunkPos chunkpos = chunkIn.m_7697_();

      for (ServerPlayer serverplayer : this.f_140149_.m_183926_()) {
         if (serverplayer.m_292900_().m_293959_(chunkpos)) {
            m_296018_(serverplayer, chunkIn);
         }
      }
   }

   public CompletableFuture<ChunkResult<LevelChunk>> m_143109_(ChunkHolder chunkHolderIn) {
      return this.m_280541_(chunkHolderIn, 1, ChunkLevel::m_339977_)
         .thenApplyAsync(
            resultIn -> resultIn.m_320014_(worldsIn -> (LevelChunk)worldsIn.get(worldsIn.size() / 2)),
            runnableIn -> this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(chunkHolderIn, runnableIn))
         );
   }

   public int m_140368_() {
      return this.f_140146_.get();
   }

   private boolean m_198874_(ChunkHolder chunkHolderIn) {
      if (chunkHolderIn.m_140095_() && chunkHolderIn.m_339539_()) {
         ChunkAccess chunkaccess = chunkHolderIn.m_340032_();
         if (!(chunkaccess instanceof ImposterProtoChunk) && !(chunkaccess instanceof LevelChunk)) {
            return false;
         } else {
            long i = chunkaccess.m_7697_().m_45588_();
            long j = this.f_202981_.getOrDefault(i, -1L);
            long k = System.currentTimeMillis();
            if (k < j) {
               return false;
            } else {
               boolean flag = this.m_140258_(chunkaccess);
               chunkHolderIn.m_140096_();
               if (flag) {
                  this.f_202981_.put(i, k + 10000L);
               }

               return flag;
            }
         }
      } else {
         return false;
      }
   }

   private boolean m_140258_(ChunkAccess chunkIn) {
      this.f_140138_.m_63796_(chunkIn.m_7697_());
      if (!chunkIn.m_6344_()) {
         return false;
      } else {
         chunkIn.m_8092_(false);
         ChunkPos chunkpos = chunkIn.m_7697_();

         try {
            ChunkStatus chunkstatus = chunkIn.m_6415_();
            if (chunkstatus.m_321717_() != ChunkType.LEVELCHUNK) {
               if (this.m_140425_(chunkpos)) {
                  return false;
               }

               if (chunkstatus == ChunkStatus.f_314297_ && chunkIn.m_6633_().values().stream().noneMatch(StructureStart::m_73603_)) {
                  return false;
               }
            }

            this.f_140133_.m_46473_().m_6174_("chunkSave");
            CompoundTag compoundtag = ChunkSerializer.m_63454_(this.f_140133_, chunkIn);
            if (Reflector.ForgeEventFactory_onChunkDataSave.exists()) {
               Level worldForge = (Level)Reflector.call(chunkIn, Reflector.ForgeIChunk_getWorldForge);
               Reflector.ForgeEventFactory_onChunkDataSave.call(chunkIn, worldForge != null ? worldForge : this.f_140133_, compoundtag);
            }

            this.m_63502_(chunkpos, compoundtag).exceptionally(voidIn -> {
               this.f_140133_.m_7654_().m_322794_(voidIn, this.m_340375_(), chunkpos);
               return null;
            });
            this.m_140229_(chunkpos, chunkstatus.m_321717_());
            return true;
         } catch (Exception var6) {
            this.f_140133_.m_7654_().m_322794_(var6, this.m_340375_(), chunkpos);
            return false;
         }
      }
   }

   private boolean m_140425_(ChunkPos chunkPosIn) {
      byte b0 = this.f_140151_.get(chunkPosIn.m_45588_());
      if (b0 != 0) {
         return b0 == 1;
      } else {
         CompoundTag compoundtag;
         try {
            compoundtag = (CompoundTag)((Optional)this.m_214963_(chunkPosIn).join()).orElse(null);
            if (compoundtag == null) {
               this.m_140422_(chunkPosIn);
               return false;
            }
         } catch (Exception var5) {
            f_140128_.error("Failed to read chunk {}", chunkPosIn, var5);
            this.m_140422_(chunkPosIn);
            return false;
         }

         ChunkType chunktype = ChunkSerializer.m_63485_(compoundtag);
         return this.m_140229_(chunkPosIn, chunktype) == 1;
      }
   }

   protected void m_293413_(int distanceIn) {
      int i = Mth.m_14045_(distanceIn, 2, 64);
      if (i != this.f_290679_) {
         this.f_290679_ = i;
         this.f_140145_.m_140777_(this.f_290679_);

         for (ServerPlayer serverplayer : this.f_140149_.m_183926_()) {
            this.m_183754_(serverplayer);
         }
      }
   }

   int m_294650_(ServerPlayer playerIn) {
      return Mth.m_14045_(playerIn.m_295486_(), 2, this.f_290679_);
   }

   private void m_294841_(ServerPlayer playerIn, ChunkPos posIn) {
      LevelChunk levelchunk = this.m_295187_(posIn.m_45588_());
      if (levelchunk != null) {
         m_296018_(playerIn, levelchunk);
      }
   }

   private static void m_296018_(ServerPlayer playerIn, LevelChunk chunkIn) {
      playerIn.f_8906_.f_290664_.m_293202_(chunkIn);
   }

   private static void m_295122_(ServerPlayer playerIn, ChunkPos posIn) {
      playerIn.f_8906_.f_290664_.m_293883_(playerIn, posIn);
   }

   @Nullable
   public LevelChunk m_295187_(long posIn) {
      ChunkHolder chunkholder = this.m_140327_(posIn);
      return chunkholder == null ? null : chunkholder.m_295552_();
   }

   public int m_140394_() {
      return this.f_140130_.size();
   }

   public net.minecraft.server.level.DistanceManager m_143145_() {
      return this.f_140145_;
   }

   protected Iterable<ChunkHolder> m_140416_() {
      return Iterables.unmodifiableIterable(this.f_140130_.values());
   }

   void m_140274_(Writer writerIn) throws IOException {
      CsvOutput csvoutput = CsvOutput.m_13619_()
         .m_13630_("x")
         .m_13630_("z")
         .m_13630_("level")
         .m_13630_("in_memory")
         .m_13630_("status")
         .m_13630_("full_status")
         .m_13630_("accessible_ready")
         .m_13630_("ticking_ready")
         .m_13630_("entity_ticking_ready")
         .m_13630_("ticket")
         .m_13630_("spawning")
         .m_13630_("block_entity_count")
         .m_13630_("ticking_ticket")
         .m_13630_("ticking_level")
         .m_13630_("block_ticks")
         .m_13630_("fluid_ticks")
         .m_13628_(writerIn);
      TickingTracker tickingtracker = this.f_140145_.m_183915_();
      ObjectBidirectionalIterator var4 = this.f_140130_.long2ObjectEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<ChunkHolder> entry = (Entry<ChunkHolder>)var4.next();
         long i = entry.getLongKey();
         ChunkPos chunkpos = new ChunkPos(i);
         ChunkHolder chunkholder = (ChunkHolder)entry.getValue();
         Optional<ChunkAccess> optional = Optional.ofNullable(chunkholder.m_340032_());
         Optional<LevelChunk> optional1 = optional.flatMap(worldIn -> worldIn instanceof LevelChunk ? Optional.of((LevelChunk)worldIn) : Optional.empty());
         csvoutput.m_13624_(
            new Object[]{
               chunkpos.f_45578_,
               chunkpos.f_45579_,
               chunkholder.m_140093_(),
               optional.isPresent(),
               optional.map(ChunkAccess::m_6415_).orElse(null),
               optional1.map(LevelChunk::m_287138_).orElse(null),
               m_140278_(chunkholder.m_140082_()),
               m_140278_(chunkholder.m_140026_()),
               m_140278_(chunkholder.m_140073_()),
               this.f_140145_.m_140838_(i),
               this.m_183879_(chunkpos),
               optional1.map(chunkIn -> chunkIn.m_62954_().size()).orElse(0),
               tickingtracker.m_184175_(i),
               tickingtracker.m_6172_(i),
               optional1.map(chunk2In -> chunk2In.m_183531_().m_183574_()).orElse(0),
               optional1.map(chunk3In -> chunk3In.m_183526_().m_183574_()).orElse(0)
            }
         );
      }
   }

   private static String m_140278_(CompletableFuture<ChunkResult<LevelChunk>> futureIn) {
      try {
         ChunkResult<LevelChunk> chunkresult = (ChunkResult<LevelChunk>)futureIn.getNow(null);
         if (chunkresult != null) {
            return chunkresult.m_321137_() ? "done" : "unloaded";
         } else {
            return "not completed";
         }
      } catch (CompletionException var2) {
         return "failed " + var2.getCause().getMessage();
      } catch (CancellationException var3) {
         return "cancelled";
      }
   }

   private CompletableFuture<Optional<CompoundTag>> m_214963_(ChunkPos chunkPosIn) {
      return this.m_223454_(chunkPosIn).thenApplyAsync(tagIn -> tagIn.map(this::m_214947_), Util.m_183991_());
   }

   private CompoundTag m_214947_(CompoundTag tagIn) {
      return this.m_188288_(this.f_140133_.m_46472_(), this.f_140137_, tagIn, this.m_183719_().m_187743_());
   }

   boolean m_183879_(ChunkPos chunkPosIn) {
      if (!this.f_140145_.m_140847_(chunkPosIn.m_45588_())) {
         return false;
      } else {
         for (ServerPlayer serverplayer : this.f_140149_.m_183926_()) {
            if (this.m_183751_(serverplayer, chunkPosIn)) {
               return true;
            }
         }

         return false;
      }
   }

   public List<ServerPlayer> m_183888_(ChunkPos chunkPosIn) {
      long i = chunkPosIn.m_45588_();
      if (!this.f_140145_.m_140847_(i)) {
         return List.of();
      } else {
         Builder<ServerPlayer> builder = ImmutableList.builder();

         for (ServerPlayer serverplayer : this.f_140149_.m_183926_()) {
            if (this.m_183751_(serverplayer, chunkPosIn)) {
               builder.add(serverplayer);
            }
         }

         return builder.build();
      }
   }

   private boolean m_183751_(ServerPlayer playerIn, ChunkPos chunkPosIn) {
      if (playerIn.m_5833_()) {
         return false;
      } else {
         double d0 = m_140226_(chunkPosIn, playerIn);
         return d0 < 16384.0;
      }
   }

   private boolean m_140329_(ServerPlayer player) {
      return player.m_5833_() && !this.f_140133_.m_46469_().m_46207_(GameRules.f_46146_);
   }

   void m_140192_(ServerPlayer player, boolean track) {
      boolean flag = this.m_140329_(player);
      boolean flag1 = this.f_140149_.m_8260_(player);
      if (track) {
         this.f_140149_.m_8252_(player, flag);
         this.m_140373_(player);
         if (!flag) {
            this.f_140145_.m_140802_(SectionPos.m_235861_(player), player);
         }

         player.m_294756_(ChunkTrackingView.f_290823_);
         this.m_183754_(player);
      } else {
         SectionPos sectionpos = player.m_8965_();
         this.f_140149_.m_8249_(player);
         if (!flag1) {
            this.f_140145_.m_140828_(sectionpos, player);
         }

         this.m_294836_(player, ChunkTrackingView.f_290823_);
      }
   }

   private void m_140373_(ServerPlayer serverPlayerEntityIn) {
      SectionPos sectionpos = SectionPos.m_235861_(serverPlayerEntityIn);
      serverPlayerEntityIn.m_9119_(sectionpos);
   }

   public void m_140184_(ServerPlayer player) {
      ObjectIterator sectionpos = this.f_140150_.values().iterator();

      while (sectionpos.hasNext()) {
         ChunkMap.TrackedEntity chunkmap$trackedentity = (ChunkMap.TrackedEntity)sectionpos.next();
         if (chunkmap$trackedentity.f_140472_ == player) {
            chunkmap$trackedentity.m_140487_(this.f_140133_.m_6907_());
         } else {
            chunkmap$trackedentity.m_140497_(player);
         }
      }

      SectionPos sectionposx = player.m_8965_();
      SectionPos sectionpos1 = SectionPos.m_235861_(player);
      boolean flag = this.f_140149_.m_8262_(player);
      boolean flag1 = this.m_140329_(player);
      boolean flag2 = sectionposx.m_123252_() != sectionpos1.m_123252_();
      if (flag2 || flag != flag1) {
         this.m_140373_(player);
         if (!flag) {
            this.f_140145_.m_140828_(sectionposx, player);
         }

         if (!flag1) {
            this.f_140145_.m_140802_(sectionpos1, player);
         }

         if (!flag && flag1) {
            this.f_140149_.m_8256_(player);
         }

         if (flag && !flag1) {
            this.f_140149_.m_8258_(player);
         }

         this.m_183754_(player);
      }
   }

   private void m_183754_(ServerPlayer player) {
      ChunkPos chunkpos = player.m_146902_();
      int i = this.m_294650_(player);
      if (player.m_292900_() instanceof Positioned chunktrackingview$positioned
         && chunktrackingview$positioned.f_290448_().equals(chunkpos)
         && chunktrackingview$positioned.f_290668_() == i) {
         return;
      }

      this.m_294836_(player, ChunkTrackingView.m_294585_(chunkpos, i));
   }

   private void m_294836_(ServerPlayer playerIn, ChunkTrackingView viewIn) {
      if (playerIn.m_9236_() == this.f_140133_) {
         ChunkTrackingView chunktrackingview = playerIn.m_292900_();
         if (viewIn instanceof Positioned chunktrackingview$positioned
            && (
               !(chunktrackingview instanceof Positioned chunktrackingview$positioned1)
                  || !chunktrackingview$positioned1.f_290448_().equals(chunktrackingview$positioned.f_290448_())
            )) {
            playerIn.f_8906_
               .m_141995_(
                  new ClientboundSetChunkCacheCenterPacket(chunktrackingview$positioned.f_290448_().f_45578_, chunktrackingview$positioned.f_290448_().f_45579_)
               );
         }

         ChunkTrackingView.m_293383_(
            chunktrackingview, viewIn, chunkPos2In -> this.m_294841_(playerIn, chunkPos2In), chunkPos3In -> m_295122_(playerIn, chunkPos3In)
         );
         playerIn.m_294756_(viewIn);
      }
   }

   public List<ServerPlayer> m_183262_(ChunkPos pos, boolean boundaryOnly) {
      Set<ServerPlayer> set = this.f_140149_.m_183926_();
      Builder<ServerPlayer> builder = ImmutableList.builder();

      for (ServerPlayer serverplayer : set) {
         if (boundaryOnly && this.m_293654_(serverplayer, pos.f_45578_, pos.f_45579_)
            || !boundaryOnly && this.m_295978_(serverplayer, pos.f_45578_, pos.f_45579_)) {
            builder.add(serverplayer);
         }
      }

      return builder.build();
   }

   protected void m_140199_(Entity entityIn) {
      boolean multipart = entityIn instanceof EnderDragonPart;
      if (Reflector.PartEntity.exists()) {
         multipart = Reflector.PartEntity.isInstance(entityIn);
      }

      if (!multipart) {
         EntityType<?> entitytype = entityIn.m_6095_();
         int i = entitytype.m_20681_() * 16;
         if (i != 0) {
            int j = entitytype.m_20682_();
            if (this.f_140150_.containsKey(entityIn.m_19879_())) {
               throw (IllegalStateException)Util.m_137570_(new IllegalStateException("Entity is already tracked!"));
            }

            ChunkMap.TrackedEntity chunkmap$trackedentity = new ChunkMap.TrackedEntity(entityIn, i, j, entitytype.m_20683_());
            this.f_140150_.put(entityIn.m_19879_(), chunkmap$trackedentity);
            chunkmap$trackedentity.m_140487_(this.f_140133_.m_6907_());
            if (entityIn instanceof ServerPlayer serverplayer) {
               this.m_140192_(serverplayer, true);
               ObjectIterator var8 = this.f_140150_.values().iterator();

               while (var8.hasNext()) {
                  ChunkMap.TrackedEntity chunkmap$trackedentity1 = (ChunkMap.TrackedEntity)var8.next();
                  if (chunkmap$trackedentity1.f_140472_ != serverplayer) {
                     chunkmap$trackedentity1.m_140497_(serverplayer);
                  }
               }
            }
         }
      }
   }

   protected void m_140331_(Entity entityIn) {
      if (entityIn instanceof ServerPlayer serverplayer) {
         this.m_140192_(serverplayer, false);
         ObjectIterator var3 = this.f_140150_.values().iterator();

         while (var3.hasNext()) {
            ChunkMap.TrackedEntity chunkmap$trackedentity = (ChunkMap.TrackedEntity)var3.next();
            chunkmap$trackedentity.m_140485_(serverplayer);
         }
      }

      ChunkMap.TrackedEntity chunkmap$trackedentity1 = (ChunkMap.TrackedEntity)this.f_140150_.remove(entityIn.m_19879_());
      if (chunkmap$trackedentity1 != null) {
         chunkmap$trackedentity1.m_140482_();
      }
   }

   protected void m_140421_() {
      for (ServerPlayer serverplayer : this.f_140149_.m_183926_()) {
         this.m_183754_(serverplayer);
      }

      List<ServerPlayer> list = Lists.newArrayList();
      List<ServerPlayer> list1 = this.f_140133_.m_6907_();
      ObjectIterator var3 = this.f_140150_.values().iterator();

      while (var3.hasNext()) {
         ChunkMap.TrackedEntity chunkmap$trackedentity = (ChunkMap.TrackedEntity)var3.next();
         SectionPos sectionpos = chunkmap$trackedentity.f_140474_;
         SectionPos sectionpos1 = SectionPos.m_235861_(chunkmap$trackedentity.f_140472_);
         boolean flag = !Objects.equals(sectionpos, sectionpos1);
         if (flag) {
            chunkmap$trackedentity.m_140487_(list1);
            Entity entity = chunkmap$trackedentity.f_140472_;
            if (entity instanceof ServerPlayer) {
               list.add((ServerPlayer)entity);
            }

            chunkmap$trackedentity.f_140474_ = sectionpos1;
         }

         if (flag || this.f_140145_.m_183913_(sectionpos1.m_123251_().m_45588_())) {
            chunkmap$trackedentity.f_140471_.m_8533_();
         }
      }

      if (!list.isEmpty()) {
         var3 = this.f_140150_.values().iterator();

         while (var3.hasNext()) {
            ChunkMap.TrackedEntity chunkmap$trackedentity1 = (ChunkMap.TrackedEntity)var3.next();
            chunkmap$trackedentity1.m_140487_(list);
         }
      }
   }

   public void m_140201_(Entity entityIn, Packet<?> packetIn) {
      ChunkMap.TrackedEntity chunkmap$trackedentity = (ChunkMap.TrackedEntity)this.f_140150_.get(entityIn.m_19879_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.m_140489_(packetIn);
      }
   }

   protected void m_140333_(Entity entityIn, Packet<?> packetIn) {
      ChunkMap.TrackedEntity chunkmap$trackedentity = (ChunkMap.TrackedEntity)this.f_140150_.get(entityIn.m_19879_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.m_140499_(packetIn);
      }
   }

   public void m_274524_(List<ChunkAccess> chunksIn) {
      Map<ServerPlayer, List<LevelChunk>> map = new HashMap();

      for (ChunkAccess chunkaccess : chunksIn) {
         ChunkPos chunkpos = chunkaccess.m_7697_();
         LevelChunk levelchunk;
         if (chunkaccess instanceof LevelChunk levelchunk1) {
            levelchunk = levelchunk1;
         } else {
            levelchunk = this.f_140133_.m_6325_(chunkpos.f_45578_, chunkpos.f_45579_);
         }

         for (ServerPlayer serverplayer : this.m_183262_(chunkpos, false)) {
            ((List)map.computeIfAbsent(serverplayer, playerIn -> new ArrayList())).add(levelchunk);
         }
      }

      map.forEach((playerIn, chunks2In) -> playerIn.f_8906_.m_141995_(ClientboundChunksBiomesPacket.m_274415_(chunks2In)));
   }

   protected PoiManager m_140424_() {
      return this.f_140138_;
   }

   public String m_182285_() {
      return this.f_182284_;
   }

   void m_287285_(ChunkPos chunkPosIn, FullChunkStatus statusIn) {
      this.f_143031_.m_156794_(chunkPosIn, statusIn);
   }

   public void m_293872_(ChunkPos chunkPosIn, int distIn) {
      int i = distIn + 1;
      ChunkPos.m_45596_(chunkPosIn, i).forEach(chunkPos2In -> {
         ChunkHolder chunkholder = this.m_140327_(chunkPos2In.m_45588_());
         if (chunkholder != null) {
            chunkholder.m_295085_(this.f_140134_.m_293279_(chunkPos2In.f_45578_, chunkPos2In.f_45579_));
         }
      });
   }

   class DistanceManager extends net.minecraft.server.level.DistanceManager {
      protected DistanceManager(final Executor priorityExecutorIn, final Executor executorIn) {
         super(priorityExecutorIn, executorIn);
      }

      @Override
      protected boolean m_7009_(long chunkPosIn) {
         return ChunkMap.this.f_140139_.contains(chunkPosIn);
      }

      @Nullable
      @Override
      protected ChunkHolder m_7316_(long chunkPosIn) {
         return ChunkMap.this.m_140174_(chunkPosIn);
      }

      @Nullable
      @Override
      protected ChunkHolder m_7288_(long chunkPosIn, int newLevel, @Nullable ChunkHolder holder, int oldLevel) {
         return ChunkMap.this.m_140176_(chunkPosIn, newLevel, holder, oldLevel);
      }
   }

   class TrackedEntity {
      final ServerEntity f_140471_;
      final Entity f_140472_;
      private final int f_140473_;
      SectionPos f_140474_;
      private final Set<ServerPlayerConnection> f_140475_ = Sets.newIdentityHashSet();

      public TrackedEntity(final Entity entityIn, final int rangeIn, final int updateFrequency, final boolean sendVelocityUpdatesIn) {
         this.f_140471_ = new ServerEntity(ChunkMap.this.f_140133_, entityIn, updateFrequency, sendVelocityUpdatesIn, this::m_140489_);
         this.f_140472_ = entityIn;
         this.f_140473_ = rangeIn;
         this.f_140474_ = SectionPos.m_235861_(entityIn);
      }

      public boolean equals(Object p_equals_1_) {
         return p_equals_1_ instanceof ChunkMap.TrackedEntity ? ((ChunkMap.TrackedEntity)p_equals_1_).f_140472_.m_19879_() == this.f_140472_.m_19879_() : false;
      }

      public int hashCode() {
         return this.f_140472_.m_19879_();
      }

      public void m_140489_(Packet<?> packetIn) {
         for (ServerPlayerConnection serverplayerconnection : this.f_140475_) {
            serverplayerconnection.m_141995_(packetIn);
         }
      }

      public void m_140499_(Packet<?> packetIn) {
         this.m_140489_(packetIn);
         if (this.f_140472_ instanceof ServerPlayer) {
            ((ServerPlayer)this.f_140472_).f_8906_.m_141995_(packetIn);
         }
      }

      public void m_140482_() {
         for (ServerPlayerConnection serverplayerconnection : this.f_140475_) {
            this.f_140471_.m_8534_(serverplayerconnection.m_142253_());
         }
      }

      public void m_140485_(ServerPlayer player) {
         if (this.f_140475_.remove(player.f_8906_)) {
            this.f_140471_.m_8534_(player);
         }
      }

      public void m_140497_(ServerPlayer player) {
         if (player != this.f_140472_) {
            Vec3 vec3 = player.m_20182_().m_82546_(this.f_140472_.m_20182_());
            int i = ChunkMap.this.m_294650_(player);
            double d0 = (double)Math.min(this.m_140496_(), i * 16);
            double d1 = vec3.f_82479_ * vec3.f_82479_ + vec3.f_82481_ * vec3.f_82481_;
            double d2 = d0 * d0;
            boolean flag = d1 <= d2
               && this.f_140472_.m_6459_(player)
               && ChunkMap.this.m_295978_(player, this.f_140472_.m_146902_().f_45578_, this.f_140472_.m_146902_().f_45579_);
            if (flag) {
               if (this.f_140475_.add(player.f_8906_)) {
                  this.f_140471_.m_8541_(player);
               }
            } else if (this.f_140475_.remove(player.f_8906_)) {
               this.f_140471_.m_8534_(player);
            }
         }
      }

      private int m_140483_(int rangeIn) {
         return ChunkMap.this.f_140133_.m_7654_().m_7186_(rangeIn);
      }

      private int m_140496_() {
         int i = this.f_140473_;
         if (!this.f_140472_.m_20197_().isEmpty()) {
            for (Entity entity : this.f_140472_.m_146897_()) {
               int j = entity.m_6095_().m_20681_() * 16;
               if (j > i) {
                  i = j;
               }
            }
         }

         return this.m_140483_(i);
      }

      public void m_140487_(List<ServerPlayer> playersListIn) {
         for (ServerPlayer serverplayer : playersListIn) {
            this.m_140497_(serverplayer);
         }
      }
   }
}
