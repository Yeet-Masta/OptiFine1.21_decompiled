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
import net.minecraft.src.C_11_;
import net.minecraft.src.C_12_;
import net.minecraft.src.C_13_;
import net.minecraft.src.C_140962_;
import net.minecraft.src.C_141278_;
import net.minecraft.src.C_1583_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_15_;
import net.minecraft.src.C_168_;
import net.minecraft.src.C_182767_;
import net.minecraft.src.C_2116_;
import net.minecraft.src.C_2118_;
import net.minecraft.src.C_213138_;
import net.minecraft.src.C_213305_;
import net.minecraft.src.C_2136_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_2140_;
import net.minecraft.src.C_2147_;
import net.minecraft.src.C_2149_;
import net.minecraft.src.C_2158_;
import net.minecraft.src.C_2159_;
import net.minecraft.src.C_2193_;
import net.minecraft.src.C_2194_;
import net.minecraft.src.C_21_;
import net.minecraft.src.C_254614_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_2588_;
import net.minecraft.src.C_273796_;
import net.minecraft.src.C_2781_;
import net.minecraft.src.C_286921_;
import net.minecraft.src.C_290036_;
import net.minecraft.src.C_302104_;
import net.minecraft.src.C_313292_;
import net.minecraft.src.C_313475_;
import net.minecraft.src.C_313554_;
import net.minecraft.src.C_313671_;
import net.minecraft.src.C_313747_;
import net.minecraft.src.C_336423_;
import net.minecraft.src.C_336526_;
import net.minecraft.src.C_336542_;
import net.minecraft.src.C_336561_;
import net.minecraft.src.C_336565_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_450_;
import net.minecraft.src.C_452_;
import net.minecraft.src.C_4706_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4_;
import net.minecraft.src.C_5028_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5112_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_5422_;
import net.minecraft.src.C_5434_;
import net.minecraft.src.C_5435_;
import net.minecraft.src.C_787_;
import net.minecraft.src.C_943_;
import net.minecraft.src.C_2785_.C_2786_;
import net.minecraft.src.C_290036_.C_290047_;
import net.minecraft.src.C_5422_.C_5428_;
import net.minecraft.src.C_5435_.C_5437_;
import net.optifine.reflect.Reflector;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;

public class ChunkMap extends C_2159_ implements C_5428_, C_336423_ {
   private static final C_313292_<List<C_2116_>> f = C_313292_.m_322259_("Unloaded chunks found in range");
   private static final CompletableFuture<C_313292_<List<C_2116_>>> g = CompletableFuture.completedFuture(f);
   private static final byte h = -1;
   private static final byte i = 0;
   private static final byte j = 1;
   private static final Logger k = LogUtils.getLogger();
   private static final int l = 200;
   private static final int m = 20;
   private static final int n = 10000;
   public static final int a = 2;
   public static final int b = 32;
   public static final int c = ChunkLevel.a(C_286921_.ENTITY_TICKING);
   private final Long2ObjectLinkedOpenHashMap<C_5422_> o = new Long2ObjectLinkedOpenHashMap();
   private volatile Long2ObjectLinkedOpenHashMap<C_5422_> p = this.o.clone();
   private final Long2ObjectLinkedOpenHashMap<C_5422_> q = new Long2ObjectLinkedOpenHashMap();
   private final List<C_336542_> r = new ArrayList();
   final C_12_ s;
   private final C_15_ t;
   private final BlockableEventLoop<Runnable> u;
   private final C_213138_ v;
   private final C_254614_ w;
   private final Supplier<C_2781_> x;
   private final C_787_ y;
   final LongSet z = new LongOpenHashSet();
   private boolean A;
   private final C_5435_ B;
   private final C_450_<C_5437_<Runnable>> C;
   private final C_450_<C_5437_<Runnable>> D;
   private final C_21_ E;
   private final C_141278_ F;
   private final ChunkMap.a G;
   private final AtomicInteger H = new AtomicInteger();
   private final String I;
   private final C_4_ J = new C_4_();
   private final Int2ObjectMap<ChunkMap.b> K = new Int2ObjectOpenHashMap();
   private final Long2ByteMap L = new Long2ByteOpenHashMap();
   private final Long2LongMap M = new Long2LongOpenHashMap();
   private final Queue<Runnable> N = Queues.newConcurrentLinkedQueue();
   private int O;
   private final C_313475_ P;

   public ChunkMap(
      C_12_ worldIn,
      C_2786_ levelSaveIn,
      DataFixer dataFixerIn,
      C_213305_ templateManagerIn,
      Executor executorIn,
      BlockableEventLoop<Runnable> taskExecutorIn,
      C_2140_ lightProviderIn,
      C_2118_ chunkGeneratorIn,
      C_21_ progressListenerIn,
      C_141278_ statusUpdateListenerIn,
      Supplier<C_2781_> dimensionDataIn,
      int viewDistanceIn,
      boolean syncWritesIn
   ) {
      super(
         new C_313747_(levelSaveIn.m_78277_(), worldIn.m_46472_(), "chunk"),
         levelSaveIn.m_197394_(worldIn.m_46472_()).resolve("region"),
         dataFixerIn,
         syncWritesIn
      );
      Path path = levelSaveIn.m_197394_(worldIn.m_46472_());
      this.I = path.getFileName().toString();
      this.s = worldIn;
      C_4706_ registryaccess = worldIn.m_9598_();
      long i = worldIn.m_7328_();
      if (chunkGeneratorIn instanceof C_2193_ noisebasedchunkgenerator) {
         this.v = C_213138_.m_255302_((C_2194_)noisebasedchunkgenerator.m_224341_().m_203334_(), registryaccess.m_255025_(C_256686_.f_256865_), i);
      } else {
         this.v = C_213138_.m_255302_(C_2194_.m_238396_(), registryaccess.m_255025_(C_256686_.f_256865_), i);
      }

      this.w = chunkGeneratorIn.m_255169_(registryaccess.m_255025_(C_256686_.f_256998_), this.v, i);
      this.u = taskExecutorIn;
      C_452_<Runnable> processormailbox1 = C_452_.m_18751_(executorIn, "worldgen");
      C_450_<Runnable> processorhandle = C_450_.m_18714_("main", taskExecutorIn::i);
      this.E = progressListenerIn;
      this.F = statusUpdateListenerIn;
      C_452_<Runnable> processormailbox = C_452_.m_18751_(executorIn, "light");
      this.B = new C_5435_(ImmutableList.of(processormailbox1, processorhandle, processormailbox), executorIn, Integer.MAX_VALUE);
      this.C = this.B.m_140604_(processormailbox1, false);
      this.D = this.B.m_140604_(processorhandle, false);
      this.t = new C_15_(lightProviderIn, this, this.s.m_6042_().f_223549_(), processormailbox, this.B.m_140604_(processormailbox, false));
      this.G = new ChunkMap.a(executorIn, taskExecutorIn);
      this.x = dimensionDataIn;
      this.y = new C_787_(
         new C_313747_(levelSaveIn.m_78277_(), worldIn.m_46472_(), "poi"),
         path.resolve("poi"),
         dataFixerIn,
         syncWritesIn,
         registryaccess,
         worldIn.m_7654_(),
         worldIn
      );
      this.a(viewDistanceIn);
      this.P = new C_313475_(worldIn, chunkGeneratorIn, templateManagerIn, this.t, this.D);
   }

   protected C_2118_ a() {
      return this.P.f_315907_();
   }

   protected C_254614_ b() {
      return this.w;
   }

   protected C_213138_ c() {
      return this.v;
   }

   private static double a(ChunkPos chunkPosIn, C_507_ entityIn) {
      double d0 = (double)C_4710_.m_175554_(chunkPosIn.e, 8);
      double d1 = (double)C_4710_.m_175554_(chunkPosIn.f, 8);
      double d2 = d0 - entityIn.m_20185_();
      double d3 = d1 - entityIn.m_20189_();
      return d2 * d2 + d3 * d3;
   }

   boolean a(C_13_ playerIn, int xIn, int zIn) {
      return playerIn.m_292900_().m_294219_(xIn, zIn) && !playerIn.f_8906_.f_290664_.m_296008_(ChunkPos.c(xIn, zIn));
   }

   private boolean b(C_13_ playerIn, int xIn, int zIn) {
      if (!this.a(playerIn, xIn, zIn)) {
         return false;
      } else {
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if ((i != 0 || j != 0) && !this.a(playerIn, xIn + i, zIn + j)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected C_15_ d() {
      return this.t;
   }

   @Nullable
   protected C_5422_ a(long chunkPosIn) {
      return (C_5422_)this.o.get(chunkPosIn);
   }

   @Nullable
   protected C_5422_ b(long chunkPosIn) {
      return (C_5422_)this.p.get(chunkPosIn);
   }

   protected IntSupplier c(long chunkPosIn) {
      return () -> {
         C_5422_ chunkholder = this.b(chunkPosIn);
         return chunkholder == null ? C_5434_.f_140508_ - 1 : Math.min(chunkholder.m_140094_(), C_5434_.f_140508_ - 1);
      };
   }

   public String a(ChunkPos pos) {
      C_5422_ chunkholder = this.b(pos.a());
      if (chunkholder == null) {
         return "null";
      } else {
         String s = chunkholder.m_140093_() + "\n";
         C_313554_ chunkstatus = chunkholder.m_338382_();
         C_2116_ chunkaccess = chunkholder.m_340032_();
         if (chunkstatus != null) {
            s = s + "St: \u00a7" + chunkstatus.m_323297_() + chunkstatus + "\u00a7r\n";
         }

         if (chunkaccess != null) {
            s = s + "Ch: \u00a7" + chunkaccess.m_6415_().m_323297_() + chunkaccess.m_6415_() + "\u00a7r\n";
         }

         C_286921_ fullchunkstatus = chunkholder.m_339537_();
         s = s + "\u00a7" + fullchunkstatus.ordinal() + fullchunkstatus;
         return s + "\u00a7r";
      }
   }

   private CompletableFuture<C_313292_<List<C_2116_>>> a(C_5422_ chunkHolderIn, int rangeIn, IntFunction<C_313554_> funcStatusIn) {
      if (rangeIn == 0) {
         C_313554_ chunkstatus1 = (C_313554_)funcStatusIn.apply(0);
         return chunkHolderIn.a(chunkstatus1, this).thenApply(resultIn -> resultIn.m_320014_(List::of));
      } else {
         List<CompletableFuture<C_313292_<C_2116_>>> list = new ArrayList();
         ChunkPos chunkpos = chunkHolderIn.r();

         for (int i = -rangeIn; i <= rangeIn; i++) {
            for (int j = -rangeIn; j <= rangeIn; j++) {
               int k = Math.max(Math.abs(j), Math.abs(i));
               long l = ChunkPos.c(chunkpos.e + j, chunkpos.f + i);
               C_5422_ chunkholder = this.a(l);
               if (chunkholder == null) {
                  return g;
               }

               C_313554_ chunkstatus = (C_313554_)funcStatusIn.apply(k);
               list.add(chunkholder.a(chunkstatus, this));
            }
         }

         return Util.d(list).thenApply(chunkResultsIn -> {
            List<C_2116_> list1 = Lists.newArrayList();

            for (C_313292_<C_2116_> chunkresult : chunkResultsIn) {
               if (chunkresult == null) {
                  throw this.a(new IllegalStateException("At least one of the chunk futures were null"), "n/a");
               }

               C_2116_ chunkaccess = (C_2116_)chunkresult.m_318814_(null);
               if (chunkaccess == null) {
                  return f;
               }

               list1.add(chunkaccess);
            }

            return C_313292_.m_323605_(list1);
         });
      }
   }

   public C_5204_ a(IllegalStateException exceptionIn, String detailsIn) {
      StringBuilder stringbuilder = new StringBuilder();
      Consumer<C_5422_> consumer = holderIn -> holderIn.m_340094_()
            .forEach(
               pairIn -> {
                  C_313554_ chunkstatus = (C_313554_)pairIn.getFirst();
                  CompletableFuture<C_313292_<C_2116_>> completablefuture = (CompletableFuture<C_313292_<C_2116_>>)pairIn.getSecond();
                  if (completablefuture != null && completablefuture.isDone() && completablefuture.join() == null) {
                     stringbuilder.append(holderIn.r())
                        .append(" - status: ")
                        .append(chunkstatus)
                        .append(" future: ")
                        .append(completablefuture)
                        .append(System.lineSeparator());
                  }
               }
            );
      stringbuilder.append("Updating:").append(System.lineSeparator());
      this.o.values().forEach(consumer);
      stringbuilder.append("Visible:").append(System.lineSeparator());
      this.p.values().forEach(consumer);
      CrashReport crashreport = CrashReport.a(exceptionIn, "Chunk loading");
      C_4909_ crashreportcategory = crashreport.a("Chunk loading");
      crashreportcategory.m_128159_("Details", detailsIn);
      crashreportcategory.m_128159_("Futures", stringbuilder);
      return new C_5204_(crashreport);
   }

   public CompletableFuture<C_313292_<C_2137_>> a(C_5422_ chunkHolderIn) {
      return this.a(chunkHolderIn, 2, levelIn -> C_313554_.f_315432_)
         .thenApplyAsync(resultIn -> resultIn.m_320014_(chunksIn -> (C_2137_)chunksIn.get(chunksIn.size() / 2)), this.u);
   }

   @Nullable
   C_5422_ a(long chunkPosIn, int newLevel, @Nullable C_5422_ holder, int oldLevel) {
      if (!ChunkLevel.f(oldLevel) && !ChunkLevel.f(newLevel)) {
         return holder;
      } else {
         if (holder != null) {
            holder.m_140027_(newLevel);
         }

         if (holder != null) {
            if (!ChunkLevel.f(newLevel)) {
               this.z.add(chunkPosIn);
            } else {
               this.z.remove(chunkPosIn);
            }
         }

         if (ChunkLevel.f(newLevel) && holder == null) {
            holder = (C_5422_)this.q.remove(chunkPosIn);
            if (holder != null) {
               holder.m_140027_(newLevel);
            } else {
               holder = new C_5422_(new ChunkPos(chunkPosIn), newLevel, this.s, this.t, this.B, this);
            }

            this.o.put(chunkPosIn, holder);
            this.A = true;
         }

         if (Reflector.ForgeEventFactory_fireChunkTicketLevelUpdated.exists()) {
            Reflector.ForgeEventFactory_fireChunkTicketLevelUpdated.call(this.s, chunkPosIn, oldLevel, newLevel, holder);
         }

         return holder;
      }
   }

   public void close() throws IOException {
      try {
         this.B.close();
         this.y.close();
      } finally {
         super.close();
      }
   }

   protected void a(boolean flush) {
      if (flush) {
         List<C_5422_> list = this.p.values().stream().filter(C_5422_::m_140095_).peek(C_5422_::m_140096_).toList();
         MutableBoolean mutableboolean = new MutableBoolean();

         do {
            mutableboolean.setFalse();
            list.stream().map(chunkHolderIn -> {
               this.u.b(chunkHolderIn::m_339539_);
               return chunkHolderIn.m_340032_();
            }).filter(chunkIn -> chunkIn instanceof C_2136_ || chunkIn instanceof C_2137_).filter(this::a).forEach(voidIn -> mutableboolean.setTrue());
         } while (mutableboolean.isTrue());

         this.b((BooleanSupplier)(() -> true));
         this.m_63514_();
      } else {
         this.p.values().forEach(this::d);
      }
   }

   protected void a(BooleanSupplier hasMoreTime) {
      C_442_ profilerfiller = this.s.m_46473_();
      profilerfiller.m_6180_("poi");
      this.y.m_6202_(hasMoreTime);
      profilerfiller.m_6182_("chunk_unload");
      if (!this.s.m_7441_()) {
         this.b(hasMoreTime);
      }

      profilerfiller.m_7238_();
   }

   public boolean e() {
      return this.t.m_75808_()
         || !this.q.isEmpty()
         || !this.o.isEmpty()
         || this.y.m_202164_()
         || !this.z.isEmpty()
         || !this.N.isEmpty()
         || this.B.m_201909_()
         || this.G.f();
   }

   private void b(BooleanSupplier hasMoreTime) {
      LongIterator longiterator = this.z.iterator();
      int i = 0;

      while (longiterator.hasNext() && (hasMoreTime.getAsBoolean() || i < 200 || this.z.size() > 2000)) {
         long j = longiterator.nextLong();
         C_5422_ chunkholder = (C_5422_)this.o.get(j);
         if (chunkholder != null) {
            if (chunkholder.m_339108_() != 0) {
               continue;
            }

            this.o.remove(j);
            this.q.put(j, chunkholder);
            this.A = true;
            i++;
            this.a(j, chunkholder);
         }

         longiterator.remove();
      }

      int k = Math.max(0, this.N.size() - 2000);

      Runnable runnable;
      while ((hasMoreTime.getAsBoolean() || k > 0) && (runnable = (Runnable)this.N.poll()) != null) {
         k--;
         runnable.run();
      }

      int l = 0;
      ObjectIterator<C_5422_> objectiterator = this.p.values().iterator();

      while (l < 20 && hasMoreTime.getAsBoolean() && objectiterator.hasNext()) {
         if (this.d((C_5422_)objectiterator.next())) {
            l++;
         }
      }
   }

   private void a(long chunkPosIn, C_5422_ chunkHolderIn) {
      chunkHolderIn.m_339472_().thenRunAsync(() -> {
         if (!chunkHolderIn.m_339539_()) {
            this.a(chunkPosIn, chunkHolderIn);
         } else {
            C_2116_ chunkaccess = chunkHolderIn.m_340032_();
            if (this.q.remove(chunkPosIn, chunkHolderIn) && chunkaccess != null) {
               if (chunkaccess instanceof C_2137_ levelchunk) {
                  levelchunk.m_62913_(false);
                  if (Reflector.ForgeEventFactory_onChunkUnload.exists()) {
                     Reflector.ForgeEventFactory_onChunkUnload.call(chunkaccess);
                  }
               }

               this.a(chunkaccess);
               if (chunkaccess instanceof C_2137_ levelchunk1) {
                  this.s.m_8712_(levelchunk1);
               }

               this.t.a(chunkaccess.f());
               this.t.m_9409_();
               this.E.a(chunkaccess.f(), null);
               this.M.remove(chunkaccess.f().a());
            }
         }
      }, this.N::add).whenComplete((worldIn, throwableIn) -> {
         if (throwableIn != null) {
            k.error("Failed to save chunk {}", chunkHolderIn.r(), throwableIn);
         }
      });
   }

   protected boolean f() {
      if (!this.A) {
         return false;
      } else {
         this.p = this.o.clone();
         this.A = false;
         return true;
      }
   }

   private CompletableFuture<C_2116_> f(ChunkPos chunkPosIn) {
      return this.j(chunkPosIn).thenApply(tagIn -> tagIn.filter(tag2In -> {
            boolean flag = b(tag2In);
            if (!flag) {
               k.error("Chunk file at {} is missing level data, skipping", chunkPosIn);
            }

            return flag;
         })).thenApplyAsync(tag3In -> {
         this.s.m_46473_().m_6174_("chunkLoad");
         if (tag3In.isPresent()) {
            C_2116_ chunkaccess = C_2158_.a(this.s, this.y, this.m_340375_(), chunkPosIn, (C_4917_)tag3In.get());
            this.a(chunkPosIn, chunkaccess.m_6415_().m_321717_());
            return chunkaccess;
         } else {
            return this.g(chunkPosIn);
         }
      }, this.u).exceptionallyAsync(throwableIn -> this.a(throwableIn, chunkPosIn), this.u);
   }

   private static boolean b(C_4917_ tagIn) {
      return tagIn.m_128425_("Status", 8);
   }

   private C_2116_ a(Throwable throwableIn, ChunkPos chunkPosIn) {
      Throwable throwable = throwableIn instanceof CompletionException completionexception ? completionexception.getCause() : throwableIn;
      Throwable throwable1 = throwable instanceof C_5204_ reportedexception ? reportedexception.getCause() : throwable;
      boolean flag1 = throwable1 instanceof Error;
      boolean flag = throwable1 instanceof IOException || throwable1 instanceof C_302104_;
      if (!flag1) {
         if (!flag) {
         }

         this.s.m_7654_().a(throwable1, this.m_340375_(), chunkPosIn);
         return this.g(chunkPosIn);
      } else {
         CrashReport crashreport = CrashReport.a(throwableIn, "Exception loading chunk");
         C_4909_ crashreportcategory = crashreport.a("Chunk being loaded");
         crashreportcategory.m_128159_("pos", chunkPosIn);
         this.h(chunkPosIn);
         throw new C_5204_(crashreport);
      }
   }

   private C_2116_ g(ChunkPos chunkPosIn) {
      this.h(chunkPosIn);
      return new C_2147_(chunkPosIn, C_2149_.f_63320_, this.s, this.s.m_9598_().m_175515_(C_256686_.f_256952_), null);
   }

   private void h(ChunkPos chunkPosIn) {
      this.L.put(chunkPosIn.a(), (byte)-1);
   }

   private byte a(ChunkPos chunkPosIn, C_313671_ chunkTypeIn) {
      return this.L.put(chunkPosIn.a(), (byte)(chunkTypeIn == C_313671_.PROTOCHUNK ? -1 : 1));
   }

   public C_336526_ m_339158_(long posIn) {
      C_5422_ chunkholder = (C_5422_)this.o.get(posIn);
      chunkholder.m_338841_();
      return chunkholder;
   }

   public void m_338685_(C_336526_ holderIn) {
      holderIn.m_340129_();
   }

   public CompletableFuture<C_2116_> m_338637_(C_336526_ holderIn, C_336565_ chunkStepIn, C_336561_<C_336526_> cacheIn) {
      ChunkPos chunkpos = holderIn.r();
      if (chunkStepIn.f_337059_() == C_313554_.f_314297_) {
         return this.f(chunkpos);
      } else {
         try {
            C_336526_ generationchunkholder = (C_336526_)cacheIn.m_338758_(chunkpos.e, chunkpos.f);
            C_2116_ chunkaccess = generationchunkholder.m_338381_(chunkStepIn.f_337059_().m_322072_());
            if (chunkaccess == null) {
               throw new IllegalStateException("Parent chunk missing");
            } else {
               CompletableFuture<C_2116_> completablefuture = chunkStepIn.m_338624_(this.P, cacheIn, chunkaccess);
               this.E.a(chunkpos, chunkStepIn.f_337059_());
               return completablefuture;
            }
         } catch (Exception var8) {
            var8.getStackTrace();
            CrashReport crashreport = CrashReport.a(var8, "Exception generating new chunk");
            C_4909_ crashreportcategory = crashreport.a("Chunk to be generated");
            crashreportcategory.m_128165_("Status being generated", () -> chunkStepIn.f_337059_().m_339742_());
            crashreportcategory.m_128159_("Location", String.format(Locale.ROOT, "%d,%d", chunkpos.e, chunkpos.f));
            crashreportcategory.m_128159_("Position hash", ChunkPos.c(chunkpos.e, chunkpos.f));
            crashreportcategory.m_128159_("Generator", this.a());
            this.u.execute(() -> {
               throw new C_5204_(crashreport);
            });
            throw new C_5204_(crashreport);
         }
      }
   }

   public C_336542_ a(C_313554_ statusIn, ChunkPos posIn) {
      C_336542_ chunkgenerationtask = C_336542_.a(this, statusIn, posIn);
      this.r.add(chunkgenerationtask);
      return chunkgenerationtask;
   }

   private void a(C_336542_ taskIn) {
      this.C.m_6937_(C_5435_.m_140642_(taskIn.m_340452_(), () -> {
         CompletableFuture<?> completablefuture = taskIn.m_340381_();
         if (completablefuture != null) {
            completablefuture.thenRun(() -> this.a(taskIn));
         }
      }));
   }

   public void m_339290_() {
      this.r.forEach(this::a);
      this.r.clear();
   }

   public CompletableFuture<C_313292_<C_2137_>> b(C_5422_ chunkHolderIn) {
      CompletableFuture<C_313292_<List<C_2116_>>> completablefuture = this.a(chunkHolderIn, 1, levelIn -> C_313554_.f_315432_);
      CompletableFuture<C_313292_<C_2137_>> completablefuture1 = completablefuture.thenApplyAsync(
            resultIn -> resultIn.m_320014_(chunksIn -> (C_2137_)chunksIn.get(chunksIn.size() / 2)),
            runnableIn -> this.D.m_6937_(C_5435_.m_140642_(chunkHolderIn, runnableIn))
         )
         .thenApplyAsync(result2In -> result2In.m_320477_(levelChunk2In -> {
               levelChunk2In.m_62812_();
               this.s.m_184102_(levelChunk2In);
               CompletableFuture<?> completablefuture2 = chunkHolderIn.m_294134_();
               if (completablefuture2.isDone()) {
                  this.a(levelChunk2In);
               } else {
                  completablefuture2.thenAcceptAsync(voidIn -> this.a(levelChunk2In), this.u);
               }
            }), this.u);
      completablefuture1.handle((resultIn, throwableIn) -> {
         this.H.getAndIncrement();
         return null;
      });
      return completablefuture1;
   }

   private void a(C_2137_ chunkIn) {
      ChunkPos chunkpos = chunkIn.f();

      for (C_13_ serverplayer : this.J.m_183926_()) {
         if (serverplayer.m_292900_().a(chunkpos)) {
            a(serverplayer, chunkIn);
         }
      }
   }

   public CompletableFuture<C_313292_<C_2137_>> c(C_5422_ chunkHolderIn) {
      return this.a(chunkHolderIn, 1, ChunkLevel::b)
         .thenApplyAsync(
            resultIn -> resultIn.m_320014_(worldsIn -> (C_2137_)worldsIn.get(worldsIn.size() / 2)),
            runnableIn -> this.D.m_6937_(C_5435_.m_140642_(chunkHolderIn, runnableIn))
         );
   }

   public int h() {
      return this.H.get();
   }

   private boolean d(C_5422_ chunkHolderIn) {
      if (chunkHolderIn.m_140095_() && chunkHolderIn.m_339539_()) {
         C_2116_ chunkaccess = chunkHolderIn.m_340032_();
         if (!(chunkaccess instanceof C_2136_) && !(chunkaccess instanceof C_2137_)) {
            return false;
         } else {
            long i = chunkaccess.f().a();
            long j = this.M.getOrDefault(i, -1L);
            long k = System.currentTimeMillis();
            if (k < j) {
               return false;
            } else {
               boolean flag = this.a(chunkaccess);
               chunkHolderIn.m_140096_();
               if (flag) {
                  this.M.put(i, k + 10000L);
               }

               return flag;
            }
         }
      } else {
         return false;
      }
   }

   private boolean a(C_2116_ chunkIn) {
      this.y.a(chunkIn.f());
      if (!chunkIn.m_6344_()) {
         return false;
      } else {
         chunkIn.m_8092_(false);
         ChunkPos chunkpos = chunkIn.f();

         try {
            C_313554_ chunkstatus = chunkIn.m_6415_();
            if (chunkstatus.m_321717_() != C_313671_.LEVELCHUNK) {
               if (this.i(chunkpos)) {
                  return false;
               }

               if (chunkstatus == C_313554_.f_314297_ && chunkIn.m_6633_().values().stream().noneMatch(C_2588_::m_73603_)) {
                  return false;
               }
            }

            this.s.m_46473_().m_6174_("chunkSave");
            C_4917_ compoundtag = C_2158_.m_63454_(this.s, chunkIn);
            if (Reflector.ForgeEventFactory_onChunkDataSave.exists()) {
               C_1596_ worldForge = (C_1596_)Reflector.call(chunkIn, Reflector.ForgeIChunk_getWorldForge);
               Reflector.ForgeEventFactory_onChunkDataSave.call(chunkIn, worldForge != null ? worldForge : this.s, compoundtag);
            }

            this.a(chunkpos, compoundtag).exceptionally(voidIn -> {
               this.s.m_7654_().b(voidIn, this.m_340375_(), chunkpos);
               return null;
            });
            this.a(chunkpos, chunkstatus.m_321717_());
            return true;
         } catch (Exception var6) {
            this.s.m_7654_().b(var6, this.m_340375_(), chunkpos);
            return false;
         }
      }
   }

   private boolean i(ChunkPos chunkPosIn) {
      byte b0 = this.L.get(chunkPosIn.a());
      if (b0 != 0) {
         return b0 == 1;
      } else {
         C_4917_ compoundtag;
         try {
            compoundtag = (C_4917_)((Optional)this.j(chunkPosIn).join()).orElse(null);
            if (compoundtag == null) {
               this.h(chunkPosIn);
               return false;
            }
         } catch (Exception var5) {
            k.error("Failed to read chunk {}", chunkPosIn, var5);
            this.h(chunkPosIn);
            return false;
         }

         C_313671_ chunktype = C_2158_.m_63485_(compoundtag);
         return this.a(chunkPosIn, chunktype) == 1;
      }
   }

   protected void a(int distanceIn) {
      int i = Mth.a(distanceIn, 2, 64);
      if (i != this.O) {
         this.O = i;
         this.G.a(this.O);

         for (C_13_ serverplayer : this.J.m_183926_()) {
            this.e(serverplayer);
         }
      }
   }

   int b(C_13_ playerIn) {
      return Mth.a(playerIn.m_295486_(), 2, this.O);
   }

   private void a(C_13_ playerIn, ChunkPos posIn) {
      C_2137_ levelchunk = this.e(posIn.a());
      if (levelchunk != null) {
         a(playerIn, levelchunk);
      }
   }

   private static void a(C_13_ playerIn, C_2137_ chunkIn) {
      playerIn.f_8906_.f_290664_.m_293202_(chunkIn);
   }

   private static void b(C_13_ playerIn, ChunkPos posIn) {
      playerIn.f_8906_.f_290664_.a(playerIn, posIn);
   }

   @Nullable
   public C_2137_ e(long posIn) {
      C_5422_ chunkholder = this.b(posIn);
      return chunkholder == null ? null : chunkholder.m_295552_();
   }

   public int i() {
      return this.p.size();
   }

   public DistanceManager j() {
      return this.G;
   }

   protected Iterable<C_5422_> k() {
      return Iterables.unmodifiableIterable(this.p.values());
   }

   void a(Writer writerIn) throws IOException {
      C_168_ csvoutput = C_168_.m_13619_()
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
      C_182767_ tickingtracker = this.G.d();
      ObjectBidirectionalIterator var4 = this.p.long2ObjectEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<C_5422_> entry = (Entry<C_5422_>)var4.next();
         long i = entry.getLongKey();
         ChunkPos chunkpos = new ChunkPos(i);
         C_5422_ chunkholder = (C_5422_)entry.getValue();
         Optional<C_2116_> optional = Optional.ofNullable(chunkholder.m_340032_());
         Optional<C_2137_> optional1 = optional.flatMap(worldIn -> worldIn instanceof C_2137_ ? Optional.of((C_2137_)worldIn) : Optional.empty());
         csvoutput.m_13624_(
            new Object[]{
               chunkpos.e,
               chunkpos.f,
               chunkholder.m_140093_(),
               optional.isPresent(),
               optional.map(C_2116_::m_6415_).orElse(null),
               optional1.map(C_2137_::m_287138_).orElse(null),
               a(chunkholder.m_140082_()),
               a(chunkholder.m_140026_()),
               a(chunkholder.m_140073_()),
               this.G.e(i),
               this.b(chunkpos),
               optional1.map(chunkIn -> chunkIn.m_62954_().size()).orElse(0),
               tickingtracker.m_184175_(i),
               tickingtracker.m_6172_(i),
               optional1.map(chunk2In -> chunk2In.m_183531_().m_183574_()).orElse(0),
               optional1.map(chunk3In -> chunk3In.m_183526_().m_183574_()).orElse(0)
            }
         );
      }
   }

   private static String a(CompletableFuture<C_313292_<C_2137_>> futureIn) {
      try {
         C_313292_<C_2137_> chunkresult = (C_313292_<C_2137_>)futureIn.getNow(null);
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

   private CompletableFuture<Optional<C_4917_>> j(ChunkPos chunkPosIn) {
      return this.d(chunkPosIn).thenApplyAsync(tagIn -> tagIn.map(this::c), Util.g());
   }

   private C_4917_ c(C_4917_ tagIn) {
      return this.m_188288_(this.s.m_46472_(), this.x, tagIn, this.a().m_187743_());
   }

   boolean b(ChunkPos chunkPosIn) {
      if (!this.G.f(chunkPosIn.a())) {
         return false;
      } else {
         for (C_13_ serverplayer : this.J.m_183926_()) {
            if (this.c(serverplayer, chunkPosIn)) {
               return true;
            }
         }

         return false;
      }
   }

   public List<C_13_> c(ChunkPos chunkPosIn) {
      long i = chunkPosIn.a();
      if (!this.G.f(i)) {
         return List.of();
      } else {
         Builder<C_13_> builder = ImmutableList.builder();

         for (C_13_ serverplayer : this.J.m_183926_()) {
            if (this.c(serverplayer, chunkPosIn)) {
               builder.add(serverplayer);
            }
         }

         return builder.build();
      }
   }

   private boolean c(C_13_ playerIn, ChunkPos chunkPosIn) {
      if (playerIn.m_5833_()) {
         return false;
      } else {
         double d0 = a(chunkPosIn, playerIn);
         return d0 < 16384.0;
      }
   }

   private boolean c(C_13_ player) {
      return player.m_5833_() && !this.s.m_46469_().m_46207_(C_1583_.f_46146_);
   }

   void a(C_13_ player, boolean track) {
      boolean flag = this.c(player);
      boolean flag1 = this.J.m_8260_(player);
      if (track) {
         this.J.m_8252_(player, flag);
         this.d(player);
         if (!flag) {
            this.G.a(C_4710_.m_235861_(player), player);
         }

         player.m_294756_(C_290036_.f_290823_);
         this.e(player);
      } else {
         C_4710_ sectionpos = player.m_8965_();
         this.J.m_8249_(player);
         if (!flag1) {
            this.G.b(sectionpos, player);
         }

         this.a(player, C_290036_.f_290823_);
      }
   }

   private void d(C_13_ serverPlayerEntityIn) {
      C_4710_ sectionpos = C_4710_.m_235861_(serverPlayerEntityIn);
      serverPlayerEntityIn.m_9119_(sectionpos);
   }

   public void a(C_13_ player) {
      ObjectIterator sectionpos = this.K.values().iterator();

      while (sectionpos.hasNext()) {
         ChunkMap.b chunkmap$trackedentity = (ChunkMap.b)sectionpos.next();
         if (chunkmap$trackedentity.c == player) {
            chunkmap$trackedentity.a(this.s.m_6907_());
         } else {
            chunkmap$trackedentity.b(player);
         }
      }

      C_4710_ sectionposx = player.m_8965_();
      C_4710_ sectionpos1 = C_4710_.m_235861_(player);
      boolean flag = this.J.m_8262_(player);
      boolean flag1 = this.c(player);
      boolean flag2 = sectionposx.m_123252_() != sectionpos1.m_123252_();
      if (flag2 || flag != flag1) {
         this.d(player);
         if (!flag) {
            this.G.b(sectionposx, player);
         }

         if (!flag1) {
            this.G.a(sectionpos1, player);
         }

         if (!flag && flag1) {
            this.J.m_8256_(player);
         }

         if (flag && !flag1) {
            this.J.m_8258_(player);
         }

         this.e(player);
      }
   }

   private void e(C_13_ player) {
      ChunkPos chunkpos = player.dq();
      int i = this.b(player);
      if (player.m_292900_() instanceof C_290047_ chunktrackingview$positioned
         && chunktrackingview$positioned.a().equals(chunkpos)
         && chunktrackingview$positioned.f_290668_() == i) {
         return;
      }

      this.a(player, C_290036_.a(chunkpos, i));
   }

   private void a(C_13_ playerIn, C_290036_ viewIn) {
      if (playerIn.m_9236_() == this.s) {
         C_290036_ chunktrackingview = playerIn.m_292900_();
         if (viewIn instanceof C_290047_ chunktrackingview$positioned
            && (
               !(chunktrackingview instanceof C_290047_ chunktrackingview$positioned1)
                  || !chunktrackingview$positioned1.a().equals(chunktrackingview$positioned.a())
            )) {
            playerIn.f_8906_.m_141995_(new C_5112_(chunktrackingview$positioned.a().e, chunktrackingview$positioned.a().f));
         }

         C_290036_.m_293383_(chunktrackingview, viewIn, chunkPos2In -> this.a(playerIn, chunkPos2In), chunkPos3In -> b(playerIn, chunkPos3In));
         playerIn.m_294756_(viewIn);
      }
   }

   public List<C_13_> a(ChunkPos pos, boolean boundaryOnly) {
      Set<C_13_> set = this.J.m_183926_();
      Builder<C_13_> builder = ImmutableList.builder();

      for (C_13_ serverplayer : set) {
         if (boundaryOnly && this.b(serverplayer, pos.e, pos.f) || !boundaryOnly && this.a(serverplayer, pos.e, pos.f)) {
            builder.add(serverplayer);
         }
      }

      return builder.build();
   }

   protected void a(C_507_ entityIn) {
      boolean multipart = entityIn instanceof C_943_;
      if (Reflector.PartEntity.exists()) {
         multipart = Reflector.PartEntity.isInstance(entityIn);
      }

      if (!multipart) {
         C_513_<?> entitytype = entityIn.m_6095_();
         int i = entitytype.m_20681_() * 16;
         if (i != 0) {
            int j = entitytype.m_20682_();
            if (this.K.containsKey(entityIn.m_19879_())) {
               throw (IllegalStateException)Util.b(new IllegalStateException("Entity is already tracked!"));
            }

            ChunkMap.b chunkmap$trackedentity = new ChunkMap.b(entityIn, i, j, entitytype.m_20683_());
            this.K.put(entityIn.m_19879_(), chunkmap$trackedentity);
            chunkmap$trackedentity.a(this.s.m_6907_());
            if (entityIn instanceof C_13_ serverplayer) {
               this.a(serverplayer, true);
               ObjectIterator var8 = this.K.values().iterator();

               while (var8.hasNext()) {
                  ChunkMap.b chunkmap$trackedentity1 = (ChunkMap.b)var8.next();
                  if (chunkmap$trackedentity1.c != serverplayer) {
                     chunkmap$trackedentity1.b(serverplayer);
                  }
               }
            }
         }
      }
   }

   protected void b(C_507_ entityIn) {
      if (entityIn instanceof C_13_ serverplayer) {
         this.a(serverplayer, false);
         ObjectIterator var3 = this.K.values().iterator();

         while (var3.hasNext()) {
            ChunkMap.b chunkmap$trackedentity = (ChunkMap.b)var3.next();
            chunkmap$trackedentity.a(serverplayer);
         }
      }

      ChunkMap.b chunkmap$trackedentity1 = (ChunkMap.b)this.K.remove(entityIn.m_19879_());
      if (chunkmap$trackedentity1 != null) {
         chunkmap$trackedentity1.a();
      }
   }

   protected void l() {
      for (C_13_ serverplayer : this.J.m_183926_()) {
         this.e(serverplayer);
      }

      List<C_13_> list = Lists.newArrayList();
      List<C_13_> list1 = this.s.m_6907_();
      ObjectIterator var3 = this.K.values().iterator();

      while (var3.hasNext()) {
         ChunkMap.b chunkmap$trackedentity = (ChunkMap.b)var3.next();
         C_4710_ sectionpos = chunkmap$trackedentity.e;
         C_4710_ sectionpos1 = C_4710_.m_235861_(chunkmap$trackedentity.c);
         boolean flag = !Objects.equals(sectionpos, sectionpos1);
         if (flag) {
            chunkmap$trackedentity.a(list1);
            C_507_ entity = chunkmap$trackedentity.c;
            if (entity instanceof C_13_) {
               list.add((C_13_)entity);
            }

            chunkmap$trackedentity.e = sectionpos1;
         }

         if (flag || this.G.c(sectionpos1.r().a())) {
            chunkmap$trackedentity.b.m_8533_();
         }
      }

      if (!list.isEmpty()) {
         var3 = this.K.values().iterator();

         while (var3.hasNext()) {
            ChunkMap.b chunkmap$trackedentity1 = (ChunkMap.b)var3.next();
            chunkmap$trackedentity1.a(list);
         }
      }
   }

   public void a(C_507_ entityIn, C_5028_<?> packetIn) {
      ChunkMap.b chunkmap$trackedentity = (ChunkMap.b)this.K.get(entityIn.m_19879_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.a(packetIn);
      }
   }

   protected void b(C_507_ entityIn, C_5028_<?> packetIn) {
      ChunkMap.b chunkmap$trackedentity = (ChunkMap.b)this.K.get(entityIn.m_19879_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.b(packetIn);
      }
   }

   public void a(List<C_2116_> chunksIn) {
      Map<C_13_, List<C_2137_>> map = new HashMap();

      for (C_2116_ chunkaccess : chunksIn) {
         ChunkPos chunkpos = chunkaccess.f();
         C_2137_ levelchunk;
         if (chunkaccess instanceof C_2137_ levelchunk1) {
            levelchunk = levelchunk1;
         } else {
            levelchunk = this.s.m_6325_(chunkpos.e, chunkpos.f);
         }

         for (C_13_ serverplayer : this.a(chunkpos, false)) {
            ((List)map.computeIfAbsent(serverplayer, playerIn -> new ArrayList())).add(levelchunk);
         }
      }

      map.forEach((playerIn, chunks2In) -> playerIn.f_8906_.m_141995_(C_273796_.m_274415_(chunks2In)));
   }

   protected C_787_ m() {
      return this.y;
   }

   public String n() {
      return this.I;
   }

   void a(ChunkPos chunkPosIn, C_286921_ statusIn) {
      this.F.onChunkStatusChange(chunkPosIn, statusIn);
   }

   public void a(ChunkPos chunkPosIn, int distIn) {
      int i = distIn + 1;
      ChunkPos.a(chunkPosIn, i).forEach(chunkPos2In -> {
         C_5422_ chunkholder = this.b(chunkPos2In.a());
         if (chunkholder != null) {
            chunkholder.m_295085_(this.t.m_293279_(chunkPos2In.e, chunkPos2In.f));
         }
      });
   }

   class a extends DistanceManager {
      protected a(final Executor priorityExecutorIn, final Executor executorIn) {
         super(priorityExecutorIn, executorIn);
      }

      @Override
      protected boolean a(long chunkPosIn) {
         return ChunkMap.this.z.contains(chunkPosIn);
      }

      @Nullable
      @Override
      protected C_5422_ b(long chunkPosIn) {
         return ChunkMap.this.a(chunkPosIn);
      }

      @Nullable
      @Override
      protected C_5422_ a(long chunkPosIn, int newLevel, @Nullable C_5422_ holder, int oldLevel) {
         return ChunkMap.this.a(chunkPosIn, newLevel, holder, oldLevel);
      }
   }

   class b {
      final C_11_ b;
      final C_507_ c;
      private final int d;
      C_4710_ e;
      private final Set<C_140962_> f = Sets.newIdentityHashSet();

      public b(final C_507_ entityIn, final int rangeIn, final int updateFrequency, final boolean sendVelocityUpdatesIn) {
         this.b = new C_11_(ChunkMap.this.s, entityIn, updateFrequency, sendVelocityUpdatesIn, this::a);
         this.c = entityIn;
         this.d = rangeIn;
         this.e = C_4710_.m_235861_(entityIn);
      }

      public boolean equals(Object p_equals_1_) {
         return p_equals_1_ instanceof ChunkMap.b ? ((ChunkMap.b)p_equals_1_).c.m_19879_() == this.c.m_19879_() : false;
      }

      public int hashCode() {
         return this.c.m_19879_();
      }

      public void a(C_5028_<?> packetIn) {
         for (C_140962_ serverplayerconnection : this.f) {
            serverplayerconnection.m_141995_(packetIn);
         }
      }

      public void b(C_5028_<?> packetIn) {
         this.a(packetIn);
         if (this.c instanceof C_13_) {
            ((C_13_)this.c).f_8906_.m_141995_(packetIn);
         }
      }

      public void a() {
         for (C_140962_ serverplayerconnection : this.f) {
            this.b.m_8534_(serverplayerconnection.m_142253_());
         }
      }

      public void a(C_13_ player) {
         if (this.f.remove(player.f_8906_)) {
            this.b.m_8534_(player);
         }
      }

      public void b(C_13_ player) {
         if (player != this.c) {
            Vec3 vec3 = player.dm().d(this.c.dm());
            int i = ChunkMap.this.b(player);
            double d0 = (double)Math.min(this.b(), i * 16);
            double d1 = vec3.c * vec3.c + vec3.e * vec3.e;
            double d2 = d0 * d0;
            boolean flag = d1 <= d2 && this.c.m_6459_(player) && ChunkMap.this.a(player, this.c.dq().e, this.c.dq().f);
            if (flag) {
               if (this.f.add(player.f_8906_)) {
                  this.b.m_8541_(player);
               }
            } else if (this.f.remove(player.f_8906_)) {
               this.b.m_8534_(player);
            }
         }
      }

      private int a(int rangeIn) {
         return ChunkMap.this.s.m_7654_().m_7186_(rangeIn);
      }

      private int b() {
         int i = this.d;
         if (!this.c.m_20197_().isEmpty()) {
            for (C_507_ entity : this.c.m_146897_()) {
               int j = entity.m_6095_().m_20681_() * 16;
               if (j > i) {
                  i = j;
               }
            }
         }

         return this.a(i);
      }

      public void a(List<C_13_> playersListIn) {
         for (C_13_ serverplayer : playersListIn) {
            this.b(serverplayer);
         }
      }
   }
}
