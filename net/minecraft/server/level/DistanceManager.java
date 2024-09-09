package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter.Message;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter.Release;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.world.level.chunk.LevelChunk;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public abstract class DistanceManager {
   static final Logger f_140758_ = LogUtils.getLogger();
   static final int f_140759_ = net.minecraft.server.level.ChunkLevel.m_287154_(FullChunkStatus.ENTITY_TICKING);
   private static final int f_143206_ = 4;
   final Long2ObjectMap<ObjectSet<ServerPlayer>> f_140760_ = new Long2ObjectOpenHashMap();
   final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> f_140761_ = new Long2ObjectOpenHashMap();
   private final net.minecraft.server.level.DistanceManager.ChunkTicketTracker f_140762_ = new net.minecraft.server.level.DistanceManager.ChunkTicketTracker();
   private final net.minecraft.server.level.DistanceManager.FixedPlayerDistanceChunkTracker f_140763_ = new net.minecraft.server.level.DistanceManager.FixedPlayerDistanceChunkTracker(
      8
   );
   private final TickingTracker f_183901_ = new TickingTracker();
   private final net.minecraft.server.level.DistanceManager.PlayerTicketTracker f_140764_ = new net.minecraft.server.level.DistanceManager.PlayerTicketTracker(
      64
   );
   final Set<ChunkHolder> f_140765_ = Sets.newHashSet();
   final ChunkTaskPriorityQueueSorter f_140766_;
   final ProcessorHandle<Message<Runnable>> f_140767_;
   final ProcessorHandle<Release> f_140768_;
   final LongSet f_140769_ = new LongOpenHashSet();
   final Executor f_140770_;
   private long f_140771_;
   private int f_183902_ = 10;
   private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> forcedTickets = new Long2ObjectOpenHashMap();

   protected DistanceManager(Executor priorityExecutorIn, Executor executorIn) {
      ProcessorHandle<Runnable> processorhandle = ProcessorHandle.m_18714_("player ticket throttler", executorIn::execute);
      ChunkTaskPriorityQueueSorter chunktaskpriorityqueuesorter = new ChunkTaskPriorityQueueSorter(ImmutableList.of(processorhandle), priorityExecutorIn, 4);
      this.f_140766_ = chunktaskpriorityqueuesorter;
      this.f_140767_ = chunktaskpriorityqueuesorter.m_140604_(processorhandle, true);
      this.f_140768_ = chunktaskpriorityqueuesorter.m_140567_(processorhandle);
      this.f_140770_ = executorIn;
   }

   protected void m_140776_() {
      this.f_140771_++;
      ObjectIterator<Entry<SortedArraySet<Ticket<?>>>> objectiterator = this.f_140761_.long2ObjectEntrySet().fastIterator();

      while (objectiterator.hasNext()) {
         Entry<SortedArraySet<Ticket<?>>> entry = (Entry<SortedArraySet<Ticket<?>>>)objectiterator.next();
         Iterator<Ticket<?>> iterator = ((SortedArraySet)entry.getValue()).iterator();
         boolean flag = false;

         while (iterator.hasNext()) {
            Ticket<?> ticket = (Ticket<?>)iterator.next();
            if (ticket.m_9434_(this.f_140771_)) {
               iterator.remove();
               flag = true;
               this.f_183901_.m_184165_(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.f_140762_.m_140715_(entry.getLongKey(), m_140797_((SortedArraySet<Ticket<?>>)entry.getValue()), false);
         }

         if (((SortedArraySet)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   private static int m_140797_(SortedArraySet<Ticket<?>> ticketsIn) {
      return !ticketsIn.isEmpty() ? ((Ticket)ticketsIn.m_14262_()).m_9433_() : net.minecraft.server.level.ChunkLevel.f_286967_ + 1;
   }

   protected abstract boolean m_7009_(long var1);

   @Nullable
   protected abstract ChunkHolder m_7316_(long var1);

   @Nullable
   protected abstract ChunkHolder m_7288_(long var1, int var3, @Nullable ChunkHolder var4, int var5);

   public boolean m_140805_(net.minecraft.server.level.ChunkMap chunkManagerIn) {
      this.f_140763_.m_6410_();
      this.f_183901_.m_184145_();
      this.f_140764_.m_6410_();
      int i = Integer.MAX_VALUE - this.f_140762_.m_140877_(Integer.MAX_VALUE);
      boolean flag = i != 0;
      if (flag) {
      }

      if (!this.f_140765_.isEmpty()) {
         this.f_140765_.forEach(holderIn -> holderIn.m_338906_(chunkManagerIn));
         this.f_140765_.forEach(holderIn -> holderIn.m_143003_(chunkManagerIn, this.f_140770_));
         this.f_140765_.clear();
         return true;
      } else {
         if (!this.f_140769_.isEmpty()) {
            LongIterator longiterator = this.f_140769_.iterator();

            while (longiterator.hasNext()) {
               long j = longiterator.nextLong();
               if (this.m_140857_(j).stream().anyMatch(ticketIn -> ticketIn.m_9428_() == TicketType.f_9444_)) {
                  ChunkHolder chunkholder = chunkManagerIn.m_140174_(j);
                  if (chunkholder == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<ChunkResult<LevelChunk>> completablefuture = chunkholder.m_140073_();
                  completablefuture.thenAccept(voidIn -> this.f_140770_.execute(() -> this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> {
                        }, j, false))));
               }
            }

            this.f_140769_.clear();
         }

         return flag;
      }
   }

   void m_140784_(long chunkPosIn, Ticket<?> ticketIn) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.m_140857_(chunkPosIn);
      int i = m_140797_(sortedarrayset);
      Ticket<?> ticket = (Ticket<?>)sortedarrayset.m_14253_(ticketIn);
      ticket.m_9429_(this.f_140771_);
      if (ticketIn.m_9433_() < i) {
         this.f_140762_.m_140715_(chunkPosIn, ticketIn.m_9433_(), true);
      }

      if (Reflector.callBoolean(ticketIn, Reflector.ForgeTicket_isForceTicks)) {
         SortedArraySet<Ticket<?>> tickets = (SortedArraySet<Ticket<?>>)this.forcedTickets.computeIfAbsent(chunkPosIn, e -> SortedArraySet.m_14246_(4));
         tickets.m_14253_(ticket);
      }
   }

   void m_140818_(long chunkPosIn, Ticket<?> ticketIn) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.m_140857_(chunkPosIn);
      if (sortedarrayset.remove(ticketIn)) {
      }

      if (sortedarrayset.isEmpty()) {
         this.f_140761_.remove(chunkPosIn);
      }

      this.f_140762_.m_140715_(chunkPosIn, m_140797_(sortedarrayset), false);
      if (Reflector.callBoolean(ticketIn, Reflector.ForgeTicket_isForceTicks)) {
         SortedArraySet<Ticket<?>> tickets = (SortedArraySet<Ticket<?>>)this.forcedTickets.get(chunkPosIn);
         if (tickets != null) {
            tickets.remove(ticketIn);
         }
      }
   }

   public <T> void m_140792_(TicketType<T> type, net.minecraft.world.level.ChunkPos pos, int level, T value) {
      this.m_140784_(pos.m_45588_(), new Ticket(type, level, value));
   }

   public <T> void m_140823_(TicketType<T> type, net.minecraft.world.level.ChunkPos pos, int level, T value) {
      Ticket<T> ticket = new Ticket(type, level, value);
      this.m_140818_(pos.m_45588_(), ticket);
   }

   public <T> void m_140840_(TicketType<T> type, net.minecraft.world.level.ChunkPos pos, int distance, T value) {
      this.addRegionTicket(type, pos, distance, value, false);
   }

   public <T> void addRegionTicket(TicketType<T> type, net.minecraft.world.level.ChunkPos pos, int distance, T value, boolean forceTicks) {
      Ticket<T> ticket = new Ticket(type, net.minecraft.server.level.ChunkLevel.m_287154_(FullChunkStatus.FULL) - distance, value);
      Reflector.setFieldValue(ticket, Reflector.ForgeTicket_forceTicks, forceTicks);
      long i = pos.m_45588_();
      this.m_140784_(i, ticket);
      this.f_183901_.m_184151_(i, ticket);
   }

   public <T> void m_140849_(TicketType<T> type, net.minecraft.world.level.ChunkPos pos, int distance, T value) {
      this.removeRegionTicket(type, pos, distance, value, false);
   }

   public <T> void removeRegionTicket(TicketType<T> type, net.minecraft.world.level.ChunkPos pos, int distance, T value, boolean forceTicks) {
      Ticket<T> ticket = new Ticket(type, net.minecraft.server.level.ChunkLevel.m_287154_(FullChunkStatus.FULL) - distance, value);
      Reflector.setFieldValue(ticket, Reflector.ForgeTicket_forceTicks, forceTicks);
      long i = pos.m_45588_();
      this.m_140818_(i, ticket);
      this.f_183901_.m_184165_(i, ticket);
   }

   private SortedArraySet<Ticket<?>> m_140857_(long chunkPosIn) {
      return (SortedArraySet<Ticket<?>>)this.f_140761_.computeIfAbsent(chunkPosIn, posIn -> SortedArraySet.m_14246_(4));
   }

   protected void m_140799_(net.minecraft.world.level.ChunkPos pos, boolean add) {
      Ticket<net.minecraft.world.level.ChunkPos> ticket = new Ticket(TicketType.f_9445_, net.minecraft.server.level.ChunkMap.f_143033_, pos);
      long i = pos.m_45588_();
      if (add) {
         this.m_140784_(i, ticket);
         this.f_183901_.m_184151_(i, ticket);
      } else {
         this.m_140818_(i, ticket);
         this.f_183901_.m_184165_(i, ticket);
      }
   }

   public void m_140802_(SectionPos sectionPosIn, ServerPlayer player) {
      net.minecraft.world.level.ChunkPos chunkpos = sectionPosIn.m_123251_();
      long i = chunkpos.m_45588_();
      ((ObjectSet)this.f_140760_.computeIfAbsent(i, posIn -> new ObjectOpenHashSet())).add(player);
      this.f_140763_.m_140715_(i, 0, true);
      this.f_140764_.m_140715_(i, 0, true);
      this.f_183901_.m_184154_(TicketType.f_9444_, chunkpos, this.m_183918_(), chunkpos);
   }

   public void m_140828_(SectionPos sectionPosIn, ServerPlayer player) {
      net.minecraft.world.level.ChunkPos chunkpos = sectionPosIn.m_123251_();
      long i = chunkpos.m_45588_();
      ObjectSet<ServerPlayer> objectset = (ObjectSet<ServerPlayer>)this.f_140760_.get(i);
      objectset.remove(player);
      if (objectset.isEmpty()) {
         this.f_140760_.remove(i);
         this.f_140763_.m_140715_(i, Integer.MAX_VALUE, false);
         this.f_140764_.m_140715_(i, Integer.MAX_VALUE, false);
         this.f_183901_.m_184168_(TicketType.f_9444_, chunkpos, this.m_183918_(), chunkpos);
      }
   }

   private int m_183918_() {
      return Math.max(0, net.minecraft.server.level.ChunkLevel.m_287154_(FullChunkStatus.ENTITY_TICKING) - this.f_183902_);
   }

   public boolean m_183913_(long posLongIn) {
      return net.minecraft.server.level.ChunkLevel.m_287155_(this.f_183901_.m_6172_(posLongIn));
   }

   public boolean m_183916_(long posLongIn) {
      return net.minecraft.server.level.ChunkLevel.m_287283_(this.f_183901_.m_6172_(posLongIn));
   }

   protected String m_140838_(long posLongIn) {
      SortedArraySet<Ticket<?>> sortedarrayset = (SortedArraySet<Ticket<?>>)this.f_140761_.get(posLongIn);
      return sortedarrayset != null && !sortedarrayset.isEmpty() ? ((Ticket)sortedarrayset.m_14262_()).toString() : "no_ticket";
   }

   protected void m_140777_(int viewDistance) {
      this.f_140764_.m_140912_(viewDistance);
   }

   public void m_183911_(int distanceIn) {
      if (distanceIn != this.f_183902_) {
         this.f_183902_ = distanceIn;
         this.f_183901_.m_184146_(this.m_183918_());
      }
   }

   public int m_140816_() {
      this.f_140763_.m_6410_();
      return this.f_140763_.f_140886_.size();
   }

   public boolean m_140847_(long chunkPosIn) {
      this.f_140763_.m_6410_();
      return this.f_140763_.f_140886_.containsKey(chunkPosIn);
   }

   public String m_140837_() {
      return this.f_140766_.m_140558_();
   }

   private void m_143207_(String fileNameIn) {
      try {
         FileOutputStream fileoutputstream = new FileOutputStream(new File(fileNameIn));

         try {
            ObjectIterator var3 = this.f_140761_.long2ObjectEntrySet().iterator();

            while (var3.hasNext()) {
               Entry<SortedArraySet<Ticket<?>>> entry = (Entry<SortedArraySet<Ticket<?>>>)var3.next();
               net.minecraft.world.level.ChunkPos chunkpos = new net.minecraft.world.level.ChunkPos(entry.getLongKey());

               for (Ticket<?> ticket : (SortedArraySet)entry.getValue()) {
                  fileoutputstream.write(
                     (chunkpos.f_45578_ + "\t" + chunkpos.f_45579_ + "\t" + ticket.m_9428_() + "\t" + ticket.m_9433_() + "\t\n")
                        .getBytes(StandardCharsets.UTF_8)
                  );
               }
            }
         } catch (Throwable var9) {
            try {
               fileoutputstream.close();
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
            }

            throw var9;
         }

         fileoutputstream.close();
      } catch (IOException var10) {
         f_140758_.error("Failed to dump tickets to {}", fileNameIn, var10);
      }
   }

   @VisibleForTesting
   TickingTracker m_183915_() {
      return this.f_183901_;
   }

   public void m_201910_() {
      ImmutableSet<TicketType<?>> immutableset = ImmutableSet.of(TicketType.f_9449_, TicketType.f_9448_);
      ObjectIterator<Entry<SortedArraySet<Ticket<?>>>> objectiterator = this.f_140761_.long2ObjectEntrySet().fastIterator();

      while (objectiterator.hasNext()) {
         Entry<SortedArraySet<Ticket<?>>> entry = (Entry<SortedArraySet<Ticket<?>>>)objectiterator.next();
         Iterator<Ticket<?>> iterator = ((SortedArraySet)entry.getValue()).iterator();
         boolean flag = false;

         while (iterator.hasNext()) {
            Ticket<?> ticket = (Ticket<?>)iterator.next();
            if (!immutableset.contains(ticket.m_9428_())) {
               iterator.remove();
               flag = true;
               this.f_183901_.m_184165_(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.f_140762_.m_140715_(entry.getLongKey(), m_140797_((SortedArraySet<Ticket<?>>)entry.getValue()), false);
         }

         if (((SortedArraySet)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   public boolean m_201911_() {
      return !this.f_140761_.isEmpty();
   }

   public boolean shouldForceTicks(long chunkPos) {
      SortedArraySet<Ticket<?>> tickets = (SortedArraySet<Ticket<?>>)this.forcedTickets.get(chunkPos);
      return tickets != null && !tickets.isEmpty();
   }

   class ChunkTicketTracker extends ChunkTracker {
      private static final int f_286988_ = net.minecraft.server.level.ChunkLevel.f_286967_ + 1;

      public ChunkTicketTracker() {
         super(f_286988_ + 1, 256, 256);
      }

      protected int m_7031_(long pos) {
         SortedArraySet<Ticket<?>> sortedarrayset = (SortedArraySet<Ticket<?>>)DistanceManager.this.f_140761_.get(pos);
         if (sortedarrayset == null) {
            return Integer.MAX_VALUE;
         } else {
            return sortedarrayset.isEmpty() ? Integer.MAX_VALUE : ((Ticket)sortedarrayset.m_14262_()).m_9433_();
         }
      }

      protected int m_6172_(long sectionPosIn) {
         if (!DistanceManager.this.m_7009_(sectionPosIn)) {
            ChunkHolder chunkholder = DistanceManager.this.m_7316_(sectionPosIn);
            if (chunkholder != null) {
               return chunkholder.m_140093_();
            }
         }

         return f_286988_;
      }

      protected void m_7351_(long sectionPosIn, int level) {
         ChunkHolder chunkholder = DistanceManager.this.m_7316_(sectionPosIn);
         int i = chunkholder == null ? f_286988_ : chunkholder.m_140093_();
         if (i != level) {
            chunkholder = DistanceManager.this.m_7288_(sectionPosIn, level, chunkholder, i);
            if (chunkholder != null) {
               DistanceManager.this.f_140765_.add(chunkholder);
            }
         }
      }

      public int m_140877_(int toUpdateCount) {
         return this.m_75588_(toUpdateCount);
      }
   }

   class FixedPlayerDistanceChunkTracker extends ChunkTracker {
      protected final Long2ByteMap f_140886_ = new Long2ByteOpenHashMap();
      protected final int f_140887_;

      protected FixedPlayerDistanceChunkTracker(final int levelCountIn) {
         super(levelCountIn + 2, 2048, 2048);
         this.f_140887_ = levelCountIn;
         this.f_140886_.defaultReturnValue((byte)(levelCountIn + 2));
      }

      protected int m_6172_(long sectionPosIn) {
         return this.f_140886_.get(sectionPosIn);
      }

      protected void m_7351_(long sectionPosIn, int level) {
         byte b0;
         if (level > this.f_140887_) {
            b0 = this.f_140886_.remove(sectionPosIn);
         } else {
            b0 = this.f_140886_.put(sectionPosIn, (byte)level);
         }

         this.m_8002_(sectionPosIn, b0, level);
      }

      protected void m_8002_(long chunkPosIn, int oldLevel, int newLevel) {
      }

      protected int m_7031_(long pos) {
         return this.m_140902_(pos) ? 0 : Integer.MAX_VALUE;
      }

      private boolean m_140902_(long chunkPosIn) {
         ObjectSet<ServerPlayer> objectset = (ObjectSet<ServerPlayer>)DistanceManager.this.f_140760_.get(chunkPosIn);
         return objectset != null && !objectset.isEmpty();
      }

      public void m_6410_() {
         this.m_75588_(Integer.MAX_VALUE);
      }

      private void m_143212_(String fileNameIn) {
         try {
            FileOutputStream fileoutputstream = new FileOutputStream(new File(fileNameIn));

            try {
               ObjectIterator var3 = this.f_140886_.long2ByteEntrySet().iterator();

               while (var3.hasNext()) {
                  it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)var3.next();
                  net.minecraft.world.level.ChunkPos chunkpos = new net.minecraft.world.level.ChunkPos(entry.getLongKey());
                  String s = Byte.toString(entry.getByteValue());
                  fileoutputstream.write((chunkpos.f_45578_ + "\t" + chunkpos.f_45579_ + "\t" + s + "\n").getBytes(StandardCharsets.UTF_8));
               }
            } catch (Throwable var8) {
               try {
                  fileoutputstream.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }

               throw var8;
            }

            fileoutputstream.close();
         } catch (IOException var9) {
            net.minecraft.server.level.DistanceManager.f_140758_.error("Failed to dump chunks to {}", fileNameIn, var9);
         }
      }
   }

   class PlayerTicketTracker extends net.minecraft.server.level.DistanceManager.FixedPlayerDistanceChunkTracker {
      private int f_140905_;
      private final Long2IntMap f_140906_ = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet f_140907_ = new LongOpenHashSet();

      protected PlayerTicketTracker(final int rangeIn) {
         super(rangeIn);
         this.f_140905_ = 0;
         this.f_140906_.defaultReturnValue(rangeIn + 2);
      }

      @Override
      protected void m_8002_(long chunkPosIn, int oldLevel, int newLevel) {
         this.f_140907_.add(chunkPosIn);
      }

      public void m_140912_(int viewDistanceIn) {
         ObjectIterator var2 = this.f_140886_.long2ByteEntrySet().iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)var2.next();
            byte b0 = entry.getByteValue();
            long i = entry.getLongKey();
            this.m_140918_(i, b0, this.m_140932_(b0), b0 <= viewDistanceIn);
         }

         this.f_140905_ = viewDistanceIn;
      }

      private void m_140918_(long chunkPosIn, int levelIn, boolean hasPosTicketIn, boolean hasLevelTicketIn) {
         if (hasPosTicketIn != hasLevelTicketIn) {
            Ticket<?> ticket = new Ticket(
               TicketType.f_9444_, net.minecraft.server.level.DistanceManager.f_140759_, new net.minecraft.world.level.ChunkPos(chunkPosIn)
            );
            if (hasLevelTicketIn) {
               DistanceManager.this.f_140767_.m_6937_(ChunkTaskPriorityQueueSorter.m_140624_(() -> DistanceManager.this.f_140770_.execute(() -> {
                     if (this.m_140932_(this.m_6172_(chunkPosIn))) {
                        DistanceManager.this.m_140784_(chunkPosIn, ticket);
                        DistanceManager.this.f_140769_.add(chunkPosIn);
                     } else {
                        DistanceManager.this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> {
                        }, chunkPosIn, false));
                     }
                  }), chunkPosIn, () -> levelIn));
            } else {
               DistanceManager.this.f_140768_
                  .m_6937_(
                     ChunkTaskPriorityQueueSorter.m_140628_(
                        () -> DistanceManager.this.f_140770_.execute(() -> DistanceManager.this.m_140818_(chunkPosIn, ticket)), chunkPosIn, true
                     )
                  );
            }
         }
      }

      @Override
      public void m_6410_() {
         super.m_6410_();
         if (!this.f_140907_.isEmpty()) {
            LongIterator longiterator = this.f_140907_.iterator();

            while (longiterator.hasNext()) {
               long i = longiterator.nextLong();
               int j = this.f_140906_.get(i);
               int k = this.m_6172_(i);
               if (j != k) {
                  DistanceManager.this.f_140766_.m_6250_(new net.minecraft.world.level.ChunkPos(i), () -> this.f_140906_.get(i), k, levelIn -> {
                     if (levelIn >= this.f_140906_.defaultReturnValue()) {
                        this.f_140906_.remove(i);
                     } else {
                        this.f_140906_.put(i, levelIn);
                     }
                  });
                  this.m_140918_(i, k, this.m_140932_(j), this.m_140932_(k));
               }
            }

            this.f_140907_.clear();
         }
      }

      private boolean m_140932_(int distanceIn) {
         return distanceIn <= this.f_140905_;
      }
   }
}
