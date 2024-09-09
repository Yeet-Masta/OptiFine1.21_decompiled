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
import net.minecraft.src.C_13_;
import net.minecraft.src.C_17_;
import net.minecraft.src.C_182767_;
import net.minecraft.src.C_18_;
import net.minecraft.src.C_193_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_286921_;
import net.minecraft.src.C_313292_;
import net.minecraft.src.C_450_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_5422_;
import net.minecraft.src.C_5435_;
import net.minecraft.src.C_5439_;
import net.minecraft.src.C_5435_.C_5437_;
import net.minecraft.src.C_5435_.C_5438_;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public abstract class DistanceManager {
   static final Logger a = LogUtils.getLogger();
   static final int b = ChunkLevel.a(C_286921_.ENTITY_TICKING);
   private static final int c = 4;
   final Long2ObjectMap<ObjectSet<C_13_>> d = new Long2ObjectOpenHashMap();
   final Long2ObjectOpenHashMap<C_193_<C_17_<?>>> e = new Long2ObjectOpenHashMap();
   private final DistanceManager.a f = new DistanceManager.a();
   private final DistanceManager.b g = new DistanceManager.b(8);
   private final C_182767_ h = new C_182767_();
   private final DistanceManager.c i = new DistanceManager.c(64);
   final Set<C_5422_> j = Sets.newHashSet();
   final C_5435_ k;
   final C_450_<C_5437_<Runnable>> l;
   final C_450_<C_5438_> m;
   final LongSet n = new LongOpenHashSet();
   final Executor o;
   private long p;
   private int q = 10;
   private final Long2ObjectOpenHashMap<C_193_<C_17_<?>>> forcedTickets = new Long2ObjectOpenHashMap();

   protected DistanceManager(Executor priorityExecutorIn, Executor executorIn) {
      C_450_<Runnable> processorhandle = C_450_.m_18714_("player ticket throttler", executorIn::execute);
      C_5435_ chunktaskpriorityqueuesorter = new C_5435_(ImmutableList.of(processorhandle), priorityExecutorIn, 4);
      this.k = chunktaskpriorityqueuesorter;
      this.l = chunktaskpriorityqueuesorter.m_140604_(processorhandle, true);
      this.m = chunktaskpriorityqueuesorter.m_140567_(processorhandle);
      this.o = executorIn;
   }

   protected void a() {
      this.p++;
      ObjectIterator<Entry<C_193_<C_17_<?>>>> objectiterator = this.e.long2ObjectEntrySet().fastIterator();

      while (objectiterator.hasNext()) {
         Entry<C_193_<C_17_<?>>> entry = (Entry<C_193_<C_17_<?>>>)objectiterator.next();
         Iterator<C_17_<?>> iterator = ((C_193_)entry.getValue()).iterator();
         boolean flag = false;

         while (iterator.hasNext()) {
            C_17_<?> ticket = (C_17_<?>)iterator.next();
            if (ticket.m_9434_(this.p)) {
               iterator.remove();
               flag = true;
               this.h.m_184165_(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.f.m_140715_(entry.getLongKey(), a((C_193_<C_17_<?>>)entry.getValue()), false);
         }

         if (((C_193_)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   private static int a(C_193_<C_17_<?>> ticketsIn) {
      return !ticketsIn.isEmpty() ? ((C_17_)ticketsIn.m_14262_()).m_9433_() : ChunkLevel.b + 1;
   }

   protected abstract boolean a(long var1);

   @Nullable
   protected abstract C_5422_ b(long var1);

   @Nullable
   protected abstract C_5422_ a(long var1, int var3, @Nullable C_5422_ var4, int var5);

   public boolean a(ChunkMap chunkManagerIn) {
      this.g.a();
      this.h.m_184145_();
      this.i.a();
      int i = Integer.MAX_VALUE - this.f.a(Integer.MAX_VALUE);
      boolean flag = i != 0;
      if (flag) {
      }

      if (!this.j.isEmpty()) {
         this.j.forEach(holderIn -> holderIn.a(chunkManagerIn));
         this.j.forEach(holderIn -> holderIn.a(chunkManagerIn, this.o));
         this.j.clear();
         return true;
      } else {
         if (!this.n.isEmpty()) {
            LongIterator longiterator = this.n.iterator();

            while (longiterator.hasNext()) {
               long j = longiterator.nextLong();
               if (this.g(j).stream().anyMatch(ticketIn -> ticketIn.m_9428_() == C_18_.f_9444_)) {
                  C_5422_ chunkholder = chunkManagerIn.a(j);
                  if (chunkholder == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<C_313292_<C_2137_>> completablefuture = chunkholder.m_140073_();
                  completablefuture.thenAccept(voidIn -> this.o.execute(() -> this.m.m_6937_(C_5435_.m_140628_(() -> {
                        }, j, false))));
               }
            }

            this.n.clear();
         }

         return flag;
      }
   }

   void a(long chunkPosIn, C_17_<?> ticketIn) {
      C_193_<C_17_<?>> sortedarrayset = this.g(chunkPosIn);
      int i = a(sortedarrayset);
      C_17_<?> ticket = (C_17_<?>)sortedarrayset.m_14253_(ticketIn);
      ticket.m_9429_(this.p);
      if (ticketIn.m_9433_() < i) {
         this.f.m_140715_(chunkPosIn, ticketIn.m_9433_(), true);
      }

      if (Reflector.callBoolean(ticketIn, Reflector.ForgeTicket_isForceTicks)) {
         C_193_<C_17_<?>> tickets = (C_193_<C_17_<?>>)this.forcedTickets.computeIfAbsent(chunkPosIn, e -> C_193_.m_14246_(4));
         tickets.m_14253_(ticket);
      }
   }

   void b(long chunkPosIn, C_17_<?> ticketIn) {
      C_193_<C_17_<?>> sortedarrayset = this.g(chunkPosIn);
      if (sortedarrayset.remove(ticketIn)) {
      }

      if (sortedarrayset.isEmpty()) {
         this.e.remove(chunkPosIn);
      }

      this.f.m_140715_(chunkPosIn, a(sortedarrayset), false);
      if (Reflector.callBoolean(ticketIn, Reflector.ForgeTicket_isForceTicks)) {
         C_193_<C_17_<?>> tickets = (C_193_<C_17_<?>>)this.forcedTickets.get(chunkPosIn);
         if (tickets != null) {
            tickets.remove(ticketIn);
         }
      }
   }

   public <T> void a(C_18_<T> type, ChunkPos pos, int level, T value) {
      this.a(pos.a(), new C_17_(type, level, value));
   }

   public <T> void b(C_18_<T> type, ChunkPos pos, int level, T value) {
      C_17_<T> ticket = new C_17_(type, level, value);
      this.b(pos.a(), ticket);
   }

   public <T> void c(C_18_<T> type, ChunkPos pos, int distance, T value) {
      this.addRegionTicket(type, pos, distance, value, false);
   }

   public <T> void addRegionTicket(C_18_<T> type, ChunkPos pos, int distance, T value, boolean forceTicks) {
      C_17_<T> ticket = new C_17_(type, ChunkLevel.a(C_286921_.FULL) - distance, value);
      Reflector.setFieldValue(ticket, Reflector.ForgeTicket_forceTicks, forceTicks);
      long i = pos.a();
      this.a(i, ticket);
      this.h.m_184151_(i, ticket);
   }

   public <T> void d(C_18_<T> type, ChunkPos pos, int distance, T value) {
      this.removeRegionTicket(type, pos, distance, value, false);
   }

   public <T> void removeRegionTicket(C_18_<T> type, ChunkPos pos, int distance, T value, boolean forceTicks) {
      C_17_<T> ticket = new C_17_(type, ChunkLevel.a(C_286921_.FULL) - distance, value);
      Reflector.setFieldValue(ticket, Reflector.ForgeTicket_forceTicks, forceTicks);
      long i = pos.a();
      this.b(i, ticket);
      this.h.m_184165_(i, ticket);
   }

   private C_193_<C_17_<?>> g(long chunkPosIn) {
      return (C_193_<C_17_<?>>)this.e.computeIfAbsent(chunkPosIn, posIn -> C_193_.m_14246_(4));
   }

   protected void a(ChunkPos pos, boolean add) {
      C_17_<ChunkPos> ticket = new C_17_(C_18_.f_9445_, ChunkMap.c, pos);
      long i = pos.a();
      if (add) {
         this.a(i, ticket);
         this.h.m_184151_(i, ticket);
      } else {
         this.b(i, ticket);
         this.h.m_184165_(i, ticket);
      }
   }

   public void a(C_4710_ sectionPosIn, C_13_ player) {
      ChunkPos chunkpos = sectionPosIn.r();
      long i = chunkpos.a();
      ((ObjectSet)this.d.computeIfAbsent(i, posIn -> new ObjectOpenHashSet())).add(player);
      this.g.m_140715_(i, 0, true);
      this.i.m_140715_(i, 0, true);
      this.h.a(C_18_.f_9444_, chunkpos, this.g(), chunkpos);
   }

   public void b(C_4710_ sectionPosIn, C_13_ player) {
      ChunkPos chunkpos = sectionPosIn.r();
      long i = chunkpos.a();
      ObjectSet<C_13_> objectset = (ObjectSet<C_13_>)this.d.get(i);
      objectset.remove(player);
      if (objectset.isEmpty()) {
         this.d.remove(i);
         this.g.m_140715_(i, Integer.MAX_VALUE, false);
         this.i.m_140715_(i, Integer.MAX_VALUE, false);
         this.h.b(C_18_.f_9444_, chunkpos, this.g(), chunkpos);
      }
   }

   private int g() {
      return Math.max(0, ChunkLevel.a(C_286921_.ENTITY_TICKING) - this.q);
   }

   public boolean c(long posLongIn) {
      return ChunkLevel.d(this.h.m_6172_(posLongIn));
   }

   public boolean d(long posLongIn) {
      return ChunkLevel.e(this.h.m_6172_(posLongIn));
   }

   protected String e(long posLongIn) {
      C_193_<C_17_<?>> sortedarrayset = (C_193_<C_17_<?>>)this.e.get(posLongIn);
      return sortedarrayset != null && !sortedarrayset.isEmpty() ? ((C_17_)sortedarrayset.m_14262_()).toString() : "no_ticket";
   }

   protected void a(int viewDistance) {
      this.i.a(viewDistance);
   }

   public void b(int distanceIn) {
      if (distanceIn != this.q) {
         this.q = distanceIn;
         this.h.m_184146_(this.g());
      }
   }

   public int b() {
      this.g.a();
      return this.g.a.size();
   }

   public boolean f(long chunkPosIn) {
      this.g.a();
      return this.g.a.containsKey(chunkPosIn);
   }

   public String c() {
      return this.k.m_140558_();
   }

   private void a(String fileNameIn) {
      try {
         FileOutputStream fileoutputstream = new FileOutputStream(new File(fileNameIn));

         try {
            ObjectIterator var3 = this.e.long2ObjectEntrySet().iterator();

            while (var3.hasNext()) {
               Entry<C_193_<C_17_<?>>> entry = (Entry<C_193_<C_17_<?>>>)var3.next();
               ChunkPos chunkpos = new ChunkPos(entry.getLongKey());

               for (C_17_<?> ticket : (C_193_)entry.getValue()) {
                  fileoutputstream.write(
                     (chunkpos.e + "\t" + chunkpos.f + "\t" + ticket.m_9428_() + "\t" + ticket.m_9433_() + "\t\n").getBytes(StandardCharsets.UTF_8)
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
         a.error("Failed to dump tickets to {}", fileNameIn, var10);
      }
   }

   @VisibleForTesting
   C_182767_ d() {
      return this.h;
   }

   public void e() {
      ImmutableSet<C_18_<?>> immutableset = ImmutableSet.of(C_18_.f_9449_, C_18_.f_9448_);
      ObjectIterator<Entry<C_193_<C_17_<?>>>> objectiterator = this.e.long2ObjectEntrySet().fastIterator();

      while (objectiterator.hasNext()) {
         Entry<C_193_<C_17_<?>>> entry = (Entry<C_193_<C_17_<?>>>)objectiterator.next();
         Iterator<C_17_<?>> iterator = ((C_193_)entry.getValue()).iterator();
         boolean flag = false;

         while (iterator.hasNext()) {
            C_17_<?> ticket = (C_17_<?>)iterator.next();
            if (!immutableset.contains(ticket.m_9428_())) {
               iterator.remove();
               flag = true;
               this.h.m_184165_(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.f.m_140715_(entry.getLongKey(), a((C_193_<C_17_<?>>)entry.getValue()), false);
         }

         if (((C_193_)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   public boolean f() {
      return !this.e.isEmpty();
   }

   public boolean shouldForceTicks(long chunkPos) {
      C_193_<C_17_<?>> tickets = (C_193_<C_17_<?>>)this.forcedTickets.get(chunkPos);
      return tickets != null && !tickets.isEmpty();
   }

   class a extends C_5439_ {
      private static final int b = ChunkLevel.b + 1;

      public a() {
         super(b + 1, 256, 256);
      }

      protected int m_7031_(long pos) {
         C_193_<C_17_<?>> sortedarrayset = (C_193_<C_17_<?>>)DistanceManager.this.e.get(pos);
         if (sortedarrayset == null) {
            return Integer.MAX_VALUE;
         } else {
            return sortedarrayset.isEmpty() ? Integer.MAX_VALUE : ((C_17_)sortedarrayset.m_14262_()).m_9433_();
         }
      }

      protected int m_6172_(long sectionPosIn) {
         if (!DistanceManager.this.a(sectionPosIn)) {
            C_5422_ chunkholder = DistanceManager.this.b(sectionPosIn);
            if (chunkholder != null) {
               return chunkholder.m_140093_();
            }
         }

         return b;
      }

      protected void m_7351_(long sectionPosIn, int level) {
         C_5422_ chunkholder = DistanceManager.this.b(sectionPosIn);
         int i = chunkholder == null ? b : chunkholder.m_140093_();
         if (i != level) {
            chunkholder = DistanceManager.this.a(sectionPosIn, level, chunkholder, i);
            if (chunkholder != null) {
               DistanceManager.this.j.add(chunkholder);
            }
         }
      }

      public int a(int toUpdateCount) {
         return this.m_75588_(toUpdateCount);
      }
   }

   class b extends C_5439_ {
      protected final Long2ByteMap a = new Long2ByteOpenHashMap();
      protected final int b;

      protected b(final int levelCountIn) {
         super(levelCountIn + 2, 2048, 2048);
         this.b = levelCountIn;
         this.a.defaultReturnValue((byte)(levelCountIn + 2));
      }

      protected int m_6172_(long sectionPosIn) {
         return this.a.get(sectionPosIn);
      }

      protected void m_7351_(long sectionPosIn, int level) {
         byte b0;
         if (level > this.b) {
            b0 = this.a.remove(sectionPosIn);
         } else {
            b0 = this.a.put(sectionPosIn, (byte)level);
         }

         this.a(sectionPosIn, b0, level);
      }

      protected void a(long chunkPosIn, int oldLevel, int newLevel) {
      }

      protected int m_7031_(long pos) {
         return this.d(pos) ? 0 : Integer.MAX_VALUE;
      }

      private boolean d(long chunkPosIn) {
         ObjectSet<C_13_> objectset = (ObjectSet<C_13_>)DistanceManager.this.d.get(chunkPosIn);
         return objectset != null && !objectset.isEmpty();
      }

      public void a() {
         this.m_75588_(Integer.MAX_VALUE);
      }

      private void a(String fileNameIn) {
         try {
            FileOutputStream fileoutputstream = new FileOutputStream(new File(fileNameIn));

            try {
               ObjectIterator var3 = this.a.long2ByteEntrySet().iterator();

               while (var3.hasNext()) {
                  it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)var3.next();
                  ChunkPos chunkpos = new ChunkPos(entry.getLongKey());
                  String s = Byte.toString(entry.getByteValue());
                  fileoutputstream.write((chunkpos.e + "\t" + chunkpos.f + "\t" + s + "\n").getBytes(StandardCharsets.UTF_8));
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
            DistanceManager.a.error("Failed to dump chunks to {}", fileNameIn, var9);
         }
      }
   }

   class c extends DistanceManager.b {
      private int g;
      private final Long2IntMap h = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet i = new LongOpenHashSet();

      protected c(final int rangeIn) {
         super(rangeIn);
         this.g = 0;
         this.h.defaultReturnValue(rangeIn + 2);
      }

      @Override
      protected void a(long chunkPosIn, int oldLevel, int newLevel) {
         this.i.add(chunkPosIn);
      }

      public void a(int viewDistanceIn) {
         ObjectIterator var2 = this.a.long2ByteEntrySet().iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)var2.next();
            byte b0 = entry.getByteValue();
            long i = entry.getLongKey();
            this.a(i, b0, this.c(b0), b0 <= viewDistanceIn);
         }

         this.g = viewDistanceIn;
      }

      private void a(long chunkPosIn, int levelIn, boolean hasPosTicketIn, boolean hasLevelTicketIn) {
         if (hasPosTicketIn != hasLevelTicketIn) {
            C_17_<?> ticket = new C_17_(C_18_.f_9444_, DistanceManager.b, new ChunkPos(chunkPosIn));
            if (hasLevelTicketIn) {
               DistanceManager.this.l.m_6937_(C_5435_.m_140624_(() -> DistanceManager.this.o.execute(() -> {
                     if (this.c(this.m_6172_(chunkPosIn))) {
                        DistanceManager.this.a(chunkPosIn, ticket);
                        DistanceManager.this.n.add(chunkPosIn);
                     } else {
                        DistanceManager.this.m.m_6937_(C_5435_.m_140628_(() -> {
                        }, chunkPosIn, false));
                     }
                  }), chunkPosIn, () -> levelIn));
            } else {
               DistanceManager.this.m
                  .m_6937_(C_5435_.m_140628_(() -> DistanceManager.this.o.execute(() -> DistanceManager.this.b(chunkPosIn, ticket)), chunkPosIn, true));
            }
         }
      }

      @Override
      public void a() {
         super.a();
         if (!this.i.isEmpty()) {
            LongIterator longiterator = this.i.iterator();

            while (longiterator.hasNext()) {
               long i = longiterator.nextLong();
               int j = this.h.get(i);
               int k = this.m_6172_(i);
               if (j != k) {
                  DistanceManager.this.k.onLevelChange(new ChunkPos(i), () -> this.h.get(i), k, levelIn -> {
                     if (levelIn >= this.h.defaultReturnValue()) {
                        this.h.remove(i);
                     } else {
                        this.h.put(i, levelIn);
                     }
                  });
                  this.a(i, k, this.c(j), this.c(k));
               }
            }

            this.i.clear();
         }
      }

      private boolean c(int distanceIn) {
         return distanceIn <= this.g;
      }
   }
}
