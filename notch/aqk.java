package net.minecraft.src;

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
import net.minecraft.src.C_5435_.C_5437_;
import net.minecraft.src.C_5435_.C_5438_;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public abstract class C_5442_ {
   static final Logger f_140758_ = LogUtils.getLogger();
   static final int f_140759_ = C_286930_.m_287154_(C_286921_.ENTITY_TICKING);
   private static final int f_143206_ = 4;
   final Long2ObjectMap<ObjectSet<C_13_>> f_140760_ = new Long2ObjectOpenHashMap();
   final Long2ObjectOpenHashMap<C_193_<C_17_<?>>> f_140761_ = new Long2ObjectOpenHashMap();
   private final C_5442_.C_5443_ f_140762_ = new C_5442_.C_5443_();
   private final C_5442_.C_5444_ f_140763_ = new C_5442_.C_5444_(8);
   private final C_182767_ f_183901_ = new C_182767_();
   private final C_5442_.C_5445_ f_140764_ = new C_5442_.C_5445_(64);
   final Set<C_5422_> f_140765_ = Sets.newHashSet();
   final C_5435_ f_140766_;
   final C_450_<C_5437_<Runnable>> f_140767_;
   final C_450_<C_5438_> f_140768_;
   final LongSet f_140769_ = new LongOpenHashSet();
   final Executor f_140770_;
   private long f_140771_;
   private int f_183902_ = 10;
   private final Long2ObjectOpenHashMap<C_193_<C_17_<?>>> forcedTickets = new Long2ObjectOpenHashMap();

   protected C_5442_(Executor priorityExecutorIn, Executor executorIn) {
      C_450_<Runnable> processorhandle = C_450_.m_18714_("player ticket throttler", executorIn::execute);
      C_5435_ chunktaskpriorityqueuesorter = new C_5435_(ImmutableList.of(processorhandle), priorityExecutorIn, 4);
      this.f_140766_ = chunktaskpriorityqueuesorter;
      this.f_140767_ = chunktaskpriorityqueuesorter.m_140604_(processorhandle, true);
      this.f_140768_ = chunktaskpriorityqueuesorter.m_140567_(processorhandle);
      this.f_140770_ = executorIn;
   }

   protected void m_140776_() {
      this.f_140771_++;
      ObjectIterator<Entry<C_193_<C_17_<?>>>> objectiterator = this.f_140761_.long2ObjectEntrySet().fastIterator();

      while (objectiterator.hasNext()) {
         Entry<C_193_<C_17_<?>>> entry = (Entry<C_193_<C_17_<?>>>)objectiterator.next();
         Iterator<C_17_<?>> iterator = ((C_193_)entry.getValue()).iterator();
         boolean flag = false;

         while (iterator.hasNext()) {
            C_17_<?> ticket = (C_17_<?>)iterator.next();
            if (ticket.m_9434_(this.f_140771_)) {
               iterator.remove();
               flag = true;
               this.f_183901_.m_184165_(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.f_140762_.m_140715_(entry.getLongKey(), m_140797_((C_193_<C_17_<?>>)entry.getValue()), false);
         }

         if (((C_193_)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   private static int m_140797_(C_193_<C_17_<?>> ticketsIn) {
      return !ticketsIn.isEmpty() ? ((C_17_)ticketsIn.m_14262_()).m_9433_() : C_286930_.f_286967_ + 1;
   }

   protected abstract boolean m_7009_(long var1);

   @Nullable
   protected abstract C_5422_ m_7316_(long var1);

   @Nullable
   protected abstract C_5422_ m_7288_(long var1, int var3, @Nullable C_5422_ var4, int var5);

   public boolean m_140805_(C_5429_ chunkManagerIn) {
      this.f_140763_.m_6410_();
      this.f_183901_.m_184145_();
      this.f_140764_.m_6410_();
      int i = Integer.MAX_VALUE - this.f_140762_.m_140877_(Integer.MAX_VALUE);
      boolean flag = i != 0;
      if (flag) {
      }

      if (!this.f_140765_.isEmpty()) {
         this.f_140765_.forEach(holderIn -> holderIn.a(chunkManagerIn));
         this.f_140765_.forEach(holderIn -> holderIn.m_143003_(chunkManagerIn, this.f_140770_));
         this.f_140765_.clear();
         return true;
      } else {
         if (!this.f_140769_.isEmpty()) {
            LongIterator longiterator = this.f_140769_.iterator();

            while (longiterator.hasNext()) {
               long j = longiterator.nextLong();
               if (this.m_140857_(j).stream().anyMatch(ticketIn -> ticketIn.m_9428_() == C_18_.f_9444_)) {
                  C_5422_ chunkholder = chunkManagerIn.m_140174_(j);
                  if (chunkholder == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<C_313292_<C_2137_>> completablefuture = chunkholder.m_140073_();
                  completablefuture.thenAccept(voidIn -> this.f_140770_.execute(() -> this.f_140768_.m_6937_(C_5435_.m_140628_(() -> {
                        }, j, false))));
               }
            }

            this.f_140769_.clear();
         }

         return flag;
      }
   }

   void m_140784_(long chunkPosIn, C_17_<?> ticketIn) {
      C_193_<C_17_<?>> sortedarrayset = this.m_140857_(chunkPosIn);
      int i = m_140797_(sortedarrayset);
      C_17_<?> ticket = (C_17_<?>)sortedarrayset.m_14253_(ticketIn);
      ticket.m_9429_(this.f_140771_);
      if (ticketIn.m_9433_() < i) {
         this.f_140762_.m_140715_(chunkPosIn, ticketIn.m_9433_(), true);
      }

      if (Reflector.callBoolean(ticketIn, Reflector.ForgeTicket_isForceTicks)) {
         C_193_<C_17_<?>> tickets = (C_193_<C_17_<?>>)this.forcedTickets.computeIfAbsent(chunkPosIn, e -> C_193_.m_14246_(4));
         tickets.m_14253_(ticket);
      }
   }

   void m_140818_(long chunkPosIn, C_17_<?> ticketIn) {
      C_193_<C_17_<?>> sortedarrayset = this.m_140857_(chunkPosIn);
      if (sortedarrayset.remove(ticketIn)) {
      }

      if (sortedarrayset.isEmpty()) {
         this.f_140761_.remove(chunkPosIn);
      }

      this.f_140762_.m_140715_(chunkPosIn, m_140797_(sortedarrayset), false);
      if (Reflector.callBoolean(ticketIn, Reflector.ForgeTicket_isForceTicks)) {
         C_193_<C_17_<?>> tickets = (C_193_<C_17_<?>>)this.forcedTickets.get(chunkPosIn);
         if (tickets != null) {
            tickets.remove(ticketIn);
         }
      }
   }

   public <T> void m_140792_(C_18_<T> type, C_1560_ pos, int level, T value) {
      this.m_140784_(pos.m_45588_(), new C_17_(type, level, value));
   }

   public <T> void m_140823_(C_18_<T> type, C_1560_ pos, int level, T value) {
      C_17_<T> ticket = new C_17_(type, level, value);
      this.m_140818_(pos.m_45588_(), ticket);
   }

   public <T> void m_140840_(C_18_<T> type, C_1560_ pos, int distance, T value) {
      this.addRegionTicket(type, pos, distance, value, false);
   }

   public <T> void addRegionTicket(C_18_<T> type, C_1560_ pos, int distance, T value, boolean forceTicks) {
      C_17_<T> ticket = new C_17_(type, C_286930_.m_287154_(C_286921_.FULL) - distance, value);
      Reflector.setFieldValue(ticket, Reflector.ForgeTicket_forceTicks, forceTicks);
      long i = pos.m_45588_();
      this.m_140784_(i, ticket);
      this.f_183901_.m_184151_(i, ticket);
   }

   public <T> void m_140849_(C_18_<T> type, C_1560_ pos, int distance, T value) {
      this.removeRegionTicket(type, pos, distance, value, false);
   }

   public <T> void removeRegionTicket(C_18_<T> type, C_1560_ pos, int distance, T value, boolean forceTicks) {
      C_17_<T> ticket = new C_17_(type, C_286930_.m_287154_(C_286921_.FULL) - distance, value);
      Reflector.setFieldValue(ticket, Reflector.ForgeTicket_forceTicks, forceTicks);
      long i = pos.m_45588_();
      this.m_140818_(i, ticket);
      this.f_183901_.m_184165_(i, ticket);
   }

   private C_193_<C_17_<?>> m_140857_(long chunkPosIn) {
      return (C_193_<C_17_<?>>)this.f_140761_.computeIfAbsent(chunkPosIn, posIn -> C_193_.m_14246_(4));
   }

   protected void m_140799_(C_1560_ pos, boolean add) {
      C_17_<C_1560_> ticket = new C_17_(C_18_.f_9445_, C_5429_.f_143033_, pos);
      long i = pos.m_45588_();
      if (add) {
         this.m_140784_(i, ticket);
         this.f_183901_.m_184151_(i, ticket);
      } else {
         this.m_140818_(i, ticket);
         this.f_183901_.m_184165_(i, ticket);
      }
   }

   public void m_140802_(C_4710_ sectionPosIn, C_13_ player) {
      C_1560_ chunkpos = sectionPosIn.m_123251_();
      long i = chunkpos.m_45588_();
      ((ObjectSet)this.f_140760_.computeIfAbsent(i, posIn -> new ObjectOpenHashSet())).add(player);
      this.f_140763_.m_140715_(i, 0, true);
      this.f_140764_.m_140715_(i, 0, true);
      this.f_183901_.m_184154_(C_18_.f_9444_, chunkpos, this.m_183918_(), chunkpos);
   }

   public void m_140828_(C_4710_ sectionPosIn, C_13_ player) {
      C_1560_ chunkpos = sectionPosIn.m_123251_();
      long i = chunkpos.m_45588_();
      ObjectSet<C_13_> objectset = (ObjectSet<C_13_>)this.f_140760_.get(i);
      objectset.remove(player);
      if (objectset.isEmpty()) {
         this.f_140760_.remove(i);
         this.f_140763_.m_140715_(i, Integer.MAX_VALUE, false);
         this.f_140764_.m_140715_(i, Integer.MAX_VALUE, false);
         this.f_183901_.m_184168_(C_18_.f_9444_, chunkpos, this.m_183918_(), chunkpos);
      }
   }

   private int m_183918_() {
      return Math.max(0, C_286930_.m_287154_(C_286921_.ENTITY_TICKING) - this.f_183902_);
   }

   public boolean m_183913_(long posLongIn) {
      return C_286930_.m_287155_(this.f_183901_.m_6172_(posLongIn));
   }

   public boolean m_183916_(long posLongIn) {
      return C_286930_.m_287283_(this.f_183901_.m_6172_(posLongIn));
   }

   protected String m_140838_(long posLongIn) {
      C_193_<C_17_<?>> sortedarrayset = (C_193_<C_17_<?>>)this.f_140761_.get(posLongIn);
      return sortedarrayset != null && !sortedarrayset.isEmpty() ? ((C_17_)sortedarrayset.m_14262_()).toString() : "no_ticket";
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
               Entry<C_193_<C_17_<?>>> entry = (Entry<C_193_<C_17_<?>>>)var3.next();
               C_1560_ chunkpos = new C_1560_(entry.getLongKey());

               for (C_17_<?> ticket : (C_193_)entry.getValue()) {
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
   C_182767_ m_183915_() {
      return this.f_183901_;
   }

   public void m_201910_() {
      ImmutableSet<C_18_<?>> immutableset = ImmutableSet.of(C_18_.f_9449_, C_18_.f_9448_);
      ObjectIterator<Entry<C_193_<C_17_<?>>>> objectiterator = this.f_140761_.long2ObjectEntrySet().fastIterator();

      while (objectiterator.hasNext()) {
         Entry<C_193_<C_17_<?>>> entry = (Entry<C_193_<C_17_<?>>>)objectiterator.next();
         Iterator<C_17_<?>> iterator = ((C_193_)entry.getValue()).iterator();
         boolean flag = false;

         while (iterator.hasNext()) {
            C_17_<?> ticket = (C_17_<?>)iterator.next();
            if (!immutableset.contains(ticket.m_9428_())) {
               iterator.remove();
               flag = true;
               this.f_183901_.m_184165_(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.f_140762_.m_140715_(entry.getLongKey(), m_140797_((C_193_<C_17_<?>>)entry.getValue()), false);
         }

         if (((C_193_)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   public boolean m_201911_() {
      return !this.f_140761_.isEmpty();
   }

   public boolean shouldForceTicks(long chunkPos) {
      C_193_<C_17_<?>> tickets = (C_193_<C_17_<?>>)this.forcedTickets.get(chunkPos);
      return tickets != null && !tickets.isEmpty();
   }

   class C_5443_ extends C_5439_ {
      private static final int f_286988_ = C_286930_.f_286967_ + 1;

      public C_5443_() {
         super(f_286988_ + 1, 256, 256);
      }

      protected int m_7031_(long pos) {
         C_193_<C_17_<?>> sortedarrayset = (C_193_<C_17_<?>>)C_5442_.this.f_140761_.get(pos);
         if (sortedarrayset == null) {
            return Integer.MAX_VALUE;
         } else {
            return sortedarrayset.isEmpty() ? Integer.MAX_VALUE : ((C_17_)sortedarrayset.m_14262_()).m_9433_();
         }
      }

      protected int m_6172_(long sectionPosIn) {
         if (!C_5442_.this.m_7009_(sectionPosIn)) {
            C_5422_ chunkholder = C_5442_.this.m_7316_(sectionPosIn);
            if (chunkholder != null) {
               return chunkholder.m_140093_();
            }
         }

         return f_286988_;
      }

      protected void m_7351_(long sectionPosIn, int level) {
         C_5422_ chunkholder = C_5442_.this.m_7316_(sectionPosIn);
         int i = chunkholder == null ? f_286988_ : chunkholder.m_140093_();
         if (i != level) {
            chunkholder = C_5442_.this.m_7288_(sectionPosIn, level, chunkholder, i);
            if (chunkholder != null) {
               C_5442_.this.f_140765_.add(chunkholder);
            }
         }
      }

      public int m_140877_(int toUpdateCount) {
         return this.b(toUpdateCount);
      }
   }

   class C_5444_ extends C_5439_ {
      protected final Long2ByteMap f_140886_ = new Long2ByteOpenHashMap();
      protected final int f_140887_;

      protected C_5444_(final int levelCountIn) {
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
         ObjectSet<C_13_> objectset = (ObjectSet<C_13_>)C_5442_.this.f_140760_.get(chunkPosIn);
         return objectset != null && !objectset.isEmpty();
      }

      public void m_6410_() {
         this.b(Integer.MAX_VALUE);
      }

      private void m_143212_(String fileNameIn) {
         try {
            FileOutputStream fileoutputstream = new FileOutputStream(new File(fileNameIn));

            try {
               ObjectIterator var3 = this.f_140886_.long2ByteEntrySet().iterator();

               while (var3.hasNext()) {
                  it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)var3.next();
                  C_1560_ chunkpos = new C_1560_(entry.getLongKey());
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
            C_5442_.f_140758_.error("Failed to dump chunks to {}", fileNameIn, var9);
         }
      }
   }

   class C_5445_ extends C_5442_.C_5444_ {
      private int f_140905_;
      private final Long2IntMap f_140906_ = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet f_140907_ = new LongOpenHashSet();

      protected C_5445_(final int rangeIn) {
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
            C_17_<?> ticket = new C_17_(C_18_.f_9444_, C_5442_.f_140759_, new C_1560_(chunkPosIn));
            if (hasLevelTicketIn) {
               C_5442_.this.f_140767_.m_6937_(C_5435_.m_140624_(() -> C_5442_.this.f_140770_.execute(() -> {
                     if (this.m_140932_(this.m_6172_(chunkPosIn))) {
                        C_5442_.this.m_140784_(chunkPosIn, ticket);
                        C_5442_.this.f_140769_.add(chunkPosIn);
                     } else {
                        C_5442_.this.f_140768_.m_6937_(C_5435_.m_140628_(() -> {
                        }, chunkPosIn, false));
                     }
                  }), chunkPosIn, () -> levelIn));
            } else {
               C_5442_.this.f_140768_
                  .m_6937_(C_5435_.m_140628_(() -> C_5442_.this.f_140770_.execute(() -> C_5442_.this.m_140818_(chunkPosIn, ticket)), chunkPosIn, true));
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
                  C_5442_.this.f_140766_.m_6250_(new C_1560_(i), () -> this.f_140906_.get(i), k, levelIn -> {
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
