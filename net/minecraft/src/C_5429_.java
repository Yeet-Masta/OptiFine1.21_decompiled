package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import net.optifine.reflect.Reflector;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;

public class C_5429_ extends C_2159_ implements C_5422_.C_5428_, C_336423_ {
   private static final C_313292_ f_336919_ = C_313292_.m_322259_("Unloaded chunks found in range");
   private static final CompletableFuture f_337118_;
   private static final byte f_143034_ = -1;
   private static final byte f_143035_ = 0;
   private static final byte f_143036_ = 1;
   private static final Logger f_140128_;
   private static final int f_143037_ = 200;
   private static final int f_198789_ = 20;
   private static final int f_202982_ = 10000;
   public static final int f_143038_ = 2;
   public static final int f_143032_ = 32;
   public static final int f_143033_;
   private final Long2ObjectLinkedOpenHashMap f_140129_ = new Long2ObjectLinkedOpenHashMap();
   private volatile Long2ObjectLinkedOpenHashMap f_140130_;
   private final Long2ObjectLinkedOpenHashMap f_140131_;
   private final List f_337610_;
   final C_12_ f_140133_;
   private final C_15_ f_140134_;
   private final C_449_ f_140135_;
   private final C_213138_ f_214834_;
   private final C_254614_ f_254626_;
   private final Supplier f_140137_;
   private final C_787_ f_140138_;
   final LongSet f_140139_;
   private boolean f_140140_;
   private final C_5435_ f_140141_;
   private final C_450_ f_140142_;
   private final C_450_ f_140143_;
   private final C_21_ f_140144_;
   private final C_141278_ f_143031_;
   private final C_5432_ f_140145_;
   private final AtomicInteger f_140146_;
   private final String f_182284_;
   private final C_4_ f_140149_;
   private final Int2ObjectMap f_140150_;
   private final Long2ByteMap f_140151_;
   private final Long2LongMap f_202981_;
   private final Queue f_140125_;
   private int f_290679_;
   private final C_313475_ f_314073_;

   public C_5429_(C_12_ worldIn, C_2785_.C_2786_ levelSaveIn, DataFixer dataFixerIn, C_213305_ templateManagerIn, Executor executorIn, C_449_ taskExecutorIn, C_2140_ lightProviderIn, C_2118_ chunkGeneratorIn, C_21_ progressListenerIn, C_141278_ statusUpdateListenerIn, Supplier dimensionDataIn, int viewDistanceIn, boolean syncWritesIn) {
      super(new C_313747_(levelSaveIn.m_78277_(), worldIn.af(), "chunk"), levelSaveIn.m_197394_(worldIn.af()).resolve("region"), dataFixerIn, syncWritesIn);
      this.f_140130_ = this.f_140129_.clone();
      this.f_140131_ = new Long2ObjectLinkedOpenHashMap();
      this.f_337610_ = new ArrayList();
      this.f_140139_ = new LongOpenHashSet();
      this.f_140146_ = new AtomicInteger();
      this.f_140149_ = new C_4_();
      this.f_140150_ = new Int2ObjectOpenHashMap();
      this.f_140151_ = new Long2ByteOpenHashMap();
      this.f_202981_ = new Long2LongOpenHashMap();
      this.f_140125_ = Queues.newConcurrentLinkedQueue();
      Path path = levelSaveIn.m_197394_(worldIn.af());
      this.f_182284_ = path.getFileName().toString();
      this.f_140133_ = worldIn;
      C_4706_ registryaccess = worldIn.H_();
      long i = worldIn.m_7328_();
      if (chunkGeneratorIn instanceof C_2193_ noisebasedchunkgenerator) {
         this.f_214834_ = C_213138_.m_255302_((C_2194_)noisebasedchunkgenerator.m_224341_().m_203334_(), registryaccess.b(C_256686_.f_256865_), i);
      } else {
         this.f_214834_ = C_213138_.m_255302_(C_2194_.m_238396_(), registryaccess.b(C_256686_.f_256865_), i);
      }

      this.f_254626_ = chunkGeneratorIn.m_255169_(registryaccess.b(C_256686_.f_256998_), this.f_214834_, i);
      this.f_140135_ = taskExecutorIn;
      C_452_ processormailbox1 = C_452_.m_18751_(executorIn, "worldgen");
      Objects.requireNonNull(taskExecutorIn);
      C_450_ processorhandle = C_450_.m_18714_("main", taskExecutorIn::m_6937_);
      this.f_140144_ = progressListenerIn;
      this.f_143031_ = statusUpdateListenerIn;
      C_452_ processormailbox = C_452_.m_18751_(executorIn, "light");
      this.f_140141_ = new C_5435_(ImmutableList.of(processormailbox1, processorhandle, processormailbox), executorIn, Integer.MAX_VALUE);
      this.f_140142_ = this.f_140141_.m_140604_(processormailbox1, false);
      this.f_140143_ = this.f_140141_.m_140604_(processorhandle, false);
      this.f_140134_ = new C_15_(lightProviderIn, this, this.f_140133_.D_().f_223549_(), processormailbox, this.f_140141_.m_140604_(processormailbox, false));
      this.f_140145_ = new C_5432_(executorIn, taskExecutorIn);
      this.f_140137_ = dimensionDataIn;
      this.f_140138_ = new C_787_(new C_313747_(levelSaveIn.m_78277_(), worldIn.af(), "poi"), path.resolve("poi"), dataFixerIn, syncWritesIn, registryaccess, worldIn.m_7654_(), worldIn);
      this.m_293413_(viewDistanceIn);
      this.f_314073_ = new C_313475_(worldIn, chunkGeneratorIn, templateManagerIn, this.f_140134_, this.f_140143_);
   }

   protected C_2118_ m_183719_() {
      return this.f_314073_.f_315907_();
   }

   protected C_254614_ m_255435_() {
      return this.f_254626_;
   }

   protected C_213138_ m_214914_() {
      return this.f_214834_;
   }

   private static double m_140226_(C_1560_ chunkPosIn, C_507_ entityIn) {
      double d0 = (double)C_4710_.m_175554_(chunkPosIn.f_45578_, 8);
      double d1 = (double)C_4710_.m_175554_(chunkPosIn.f_45579_, 8);
      double d2 = d0 - entityIn.m_20185_();
      double d3 = d1 - entityIn.m_20189_();
      return d2 * d2 + d3 * d3;
   }

   boolean m_295978_(C_13_ playerIn, int xIn, int zIn) {
      return playerIn.m_292900_().m_294219_(xIn, zIn) && !playerIn.f_8906_.f_290664_.m_296008_(C_1560_.m_45589_(xIn, zIn));
   }

   private boolean m_293654_(C_13_ playerIn, int xIn, int zIn) {
      if (!this.m_295978_(playerIn, xIn, zIn)) {
         return false;
      } else {
         for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
               if ((i != 0 || j != 0) && !this.m_295978_(playerIn, xIn + i, zIn + j)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected C_15_ m_140166_() {
      return this.f_140134_;
   }

   @Nullable
   protected C_5422_ m_140174_(long chunkPosIn) {
      return (C_5422_)this.f_140129_.get(chunkPosIn);
   }

   @Nullable
   protected C_5422_ m_140327_(long chunkPosIn) {
      return (C_5422_)this.f_140130_.get(chunkPosIn);
   }

   protected IntSupplier m_140371_(long chunkPosIn) {
      return () -> {
         C_5422_ chunkholder = this.m_140327_(chunkPosIn);
         return chunkholder == null ? C_5434_.f_140508_ - 1 : Math.min(chunkholder.m_140094_(), C_5434_.f_140508_ - 1);
      };
   }

   public String m_140204_(C_1560_ pos) {
      C_5422_ chunkholder = this.m_140327_(pos.m_45588_());
      if (chunkholder == null) {
         return "null";
      } else {
         String s = chunkholder.m_140093_() + "\n";
         C_313554_ chunkstatus = chunkholder.u();
         C_2116_ chunkaccess = chunkholder.p();
         if (chunkstatus != null) {
            s = s + "St: §" + chunkstatus.m_323297_() + String.valueOf(chunkstatus) + "§r\n";
         }

         if (chunkaccess != null) {
            s = s + "Ch: §" + chunkaccess.m_6415_().m_323297_() + String.valueOf(chunkaccess.m_6415_()) + "§r\n";
         }

         C_286921_ fullchunkstatus = chunkholder.s();
         s = s + "§" + fullchunkstatus.ordinal() + String.valueOf(fullchunkstatus);
         return s + "§r";
      }
   }

   private CompletableFuture m_280541_(C_5422_ chunkHolderIn, int rangeIn, IntFunction funcStatusIn) {
      if (rangeIn == 0) {
         C_313554_ chunkstatus1 = (C_313554_)funcStatusIn.apply(0);
         return chunkHolderIn.a(chunkstatus1, this).thenApply((resultIn) -> {
            return resultIn.m_320014_(List::of);
         });
      } else {
         List list = new ArrayList();
         C_1560_ chunkpos = chunkHolderIn.r();

         for(int i = -rangeIn; i <= rangeIn; ++i) {
            for(int j = -rangeIn; j <= rangeIn; ++j) {
               int k = Math.max(Math.abs(j), Math.abs(i));
               long l = C_1560_.m_45589_(chunkpos.f_45578_ + j, chunkpos.f_45579_ + i);
               C_5422_ chunkholder = this.m_140174_(l);
               if (chunkholder == null) {
                  return f_337118_;
               }

               C_313554_ chunkstatus = (C_313554_)funcStatusIn.apply(k);
               list.add(chunkholder.a(chunkstatus, this));
            }
         }

         return C_5322_.m_137567_(list).thenApply((chunkResultsIn) -> {
            List list1 = Lists.newArrayList();
            Iterator var3 = chunkResultsIn.iterator();

            while(var3.hasNext()) {
               C_313292_ chunkresult = (C_313292_)var3.next();
               if (chunkresult == null) {
                  throw this.m_203751_(new IllegalStateException("At least one of the chunk futures were null"), "n/a");
               }

               C_2116_ chunkaccess = (C_2116_)chunkresult.m_318814_((Object)null);
               if (chunkaccess == null) {
                  return f_336919_;
               }

               list1.add(chunkaccess);
            }

            return C_313292_.m_323605_(list1);
         });
      }
   }

   public C_5204_ m_203751_(IllegalStateException exceptionIn, String detailsIn) {
      StringBuilder stringbuilder = new StringBuilder();
      Consumer consumer = (holderIn) -> {
         holderIn.t().forEach((pairIn) -> {
            C_313554_ chunkstatus = (C_313554_)pairIn.getFirst();
            CompletableFuture completablefuture = (CompletableFuture)pairIn.getSecond();
            if (completablefuture != null && completablefuture.isDone() && completablefuture.join() == null) {
               stringbuilder.append(holderIn.r()).append(" - status: ").append(chunkstatus).append(" future: ").append(completablefuture).append(System.lineSeparator());
            }

         });
      };
      stringbuilder.append("Updating:").append(System.lineSeparator());
      this.f_140129_.values().forEach(consumer);
      stringbuilder.append("Visible:").append(System.lineSeparator());
      this.f_140130_.values().forEach(consumer);
      C_4883_ crashreport = C_4883_.m_127521_(exceptionIn, "Chunk loading");
      C_4909_ crashreportcategory = crashreport.m_127514_("Chunk loading");
      crashreportcategory.m_128159_("Details", detailsIn);
      crashreportcategory.m_128159_("Futures", stringbuilder);
      return new C_5204_(crashreport);
   }

   public CompletableFuture m_280208_(C_5422_ chunkHolderIn) {
      return this.m_280541_(chunkHolderIn, 2, (levelIn) -> {
         return C_313554_.f_315432_;
      }).thenApplyAsync((resultIn) -> {
         return resultIn.m_320014_((chunksIn) -> {
            return (C_2137_)chunksIn.get(chunksIn.size() / 2);
         });
      }, this.f_140135_);
   }

   @Nullable
   C_5422_ m_140176_(long chunkPosIn, int newLevel, @Nullable C_5422_ holder, int oldLevel) {
      if (!C_286930_.m_287217_(oldLevel) && !C_286930_.m_287217_(newLevel)) {
         return holder;
      } else {
         if (holder != null) {
            holder.m_140027_(newLevel);
         }

         if (holder != null) {
            if (!C_286930_.m_287217_(newLevel)) {
               this.f_140139_.add(chunkPosIn);
            } else {
               this.f_140139_.remove(chunkPosIn);
            }
         }

         if (C_286930_.m_287217_(newLevel) && holder == null) {
            holder = (C_5422_)this.f_140131_.remove(chunkPosIn);
            if (holder != null) {
               holder.m_140027_(newLevel);
            } else {
               holder = new C_5422_(new C_1560_(chunkPosIn), newLevel, this.f_140133_, this.f_140134_, this.f_140141_, this);
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
         List list = this.f_140130_.values().stream().filter(C_5422_::m_140095_).peek(C_5422_::m_140096_).toList();
         MutableBoolean mutableboolean = new MutableBoolean();

         do {
            mutableboolean.setFalse();
            list.stream().map((chunkHolderIn) -> {
               C_449_ var10000 = this.f_140135_;
               Objects.requireNonNull(chunkHolderIn);
               var10000.m_18701_(chunkHolderIn::m_339539_);
               return chunkHolderIn.p();
            }).filter((chunkIn) -> {
               return chunkIn instanceof C_2136_ || chunkIn instanceof C_2137_;
            }).filter(this::m_140258_).forEach((voidIn) -> {
               mutableboolean.setTrue();
            });
         } while(mutableboolean.isTrue());

         this.m_140353_(() -> {
            return true;
         });
         this.m_63514_();
      } else {
         this.f_140130_.values().forEach(this::m_198874_);
      }

   }

   protected void m_140280_(BooleanSupplier hasMoreTime) {
      C_442_ profilerfiller = this.f_140133_.ag();
      profilerfiller.m_6180_("poi");
      this.f_140138_.m_6202_(hasMoreTime);
      profilerfiller.m_6182_("chunk_unload");
      if (!this.f_140133_.m_7441_()) {
         this.m_140353_(hasMoreTime);
      }

      profilerfiller.m_7238_();
   }

   public boolean m_201907_() {
      return this.f_140134_.K_() || !this.f_140131_.isEmpty() || !this.f_140129_.isEmpty() || this.f_140138_.a() || !this.f_140139_.isEmpty() || !this.f_140125_.isEmpty() || this.f_140141_.m_201909_() || this.f_140145_.m_201911_();
   }

   private void m_140353_(BooleanSupplier hasMoreTime) {
      LongIterator longiterator = this.f_140139_.iterator();
      int i = 0;

      while(longiterator.hasNext() && (hasMoreTime.getAsBoolean() || i < 200 || this.f_140139_.size() > 2000)) {
         long j = longiterator.nextLong();
         C_5422_ chunkholder = (C_5422_)this.f_140129_.get(j);
         if (chunkholder != null) {
            if (chunkholder.o() != 0) {
               continue;
            }

            this.f_140129_.remove(j);
            this.f_140131_.put(j, chunkholder);
            this.f_140140_ = true;
            ++i;
            this.m_140181_(j, chunkholder);
         }

         longiterator.remove();
      }

      int k = Math.max(0, this.f_140125_.size() - 2000);

      Runnable runnable;
      while((hasMoreTime.getAsBoolean() || k > 0) && (runnable = (Runnable)this.f_140125_.poll()) != null) {
         --k;
         runnable.run();
      }

      int l = 0;
      ObjectIterator objectiterator = this.f_140130_.values().iterator();

      while(l < 20 && hasMoreTime.getAsBoolean() && objectiterator.hasNext()) {
         if (this.m_198874_((C_5422_)objectiterator.next())) {
            ++l;
         }
      }

   }

   private void m_140181_(long chunkPosIn, C_5422_ chunkHolderIn) {
      CompletableFuture var10000 = chunkHolderIn.m_339472_();
      Runnable var10001 = () -> {
         if (!chunkHolderIn.m_339539_()) {
            this.m_140181_(chunkPosIn, chunkHolderIn);
         } else {
            C_2116_ chunkaccess = chunkHolderIn.p();
            if (this.f_140131_.remove(chunkPosIn, chunkHolderIn) && chunkaccess != null) {
               C_2137_ levelchunk1;
               if (chunkaccess instanceof C_2137_) {
                  levelchunk1 = (C_2137_)chunkaccess;
                  levelchunk1.m_62913_(false);
                  if (Reflector.ForgeEventFactory_onChunkUnload.exists()) {
                     Reflector.ForgeEventFactory_onChunkUnload.call((Object)chunkaccess);
                  }
               }

               this.m_140258_(chunkaccess);
               if (chunkaccess instanceof C_2137_) {
                  levelchunk1 = (C_2137_)chunkaccess;
                  this.f_140133_.m_8712_(levelchunk1);
               }

               this.f_140134_.m_9330_(chunkaccess.m_7697_());
               this.f_140134_.m_9409_();
               this.f_140144_.m_5511_(chunkaccess.m_7697_(), (C_313554_)null);
               this.f_202981_.remove(chunkaccess.m_7697_().m_45588_());
            }
         }

      };
      Queue var10002 = this.f_140125_;
      Objects.requireNonNull(var10002);
      var10000.thenRunAsync(var10001, var10002::add).whenComplete((worldIn, throwableIn) -> {
         if (throwableIn != null) {
            f_140128_.error("Failed to save chunk {}", chunkHolderIn.r(), throwableIn);
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

   private CompletableFuture m_140417_(C_1560_ chunkPosIn) {
      return this.m_214963_(chunkPosIn).thenApply((tagIn) -> {
         return tagIn.filter((tag2In) -> {
            boolean flag = m_214940_(tag2In);
            if (!flag) {
               f_140128_.error("Chunk file at {} is missing level data, skipping", chunkPosIn);
            }

            return flag;
         });
      }).thenApplyAsync((tag3In) -> {
         this.f_140133_.ag().m_6174_("chunkLoad");
         if (tag3In.isPresent()) {
            C_2116_ chunkaccess = C_2158_.m_188230_(this.f_140133_, this.f_140138_, this.m_340375_(), chunkPosIn, (C_4917_)tag3In.get());
            this.m_140229_(chunkPosIn, chunkaccess.m_6415_().m_321717_());
            return chunkaccess;
         } else {
            return this.m_214961_(chunkPosIn);
         }
      }, this.f_140135_).exceptionallyAsync((throwableIn) -> {
         return this.m_214901_(throwableIn, chunkPosIn);
      }, this.f_140135_);
   }

   private static boolean m_214940_(C_4917_ tagIn) {
      return tagIn.m_128425_("Status", 8);
   }

   private C_2116_ m_214901_(Throwable throwableIn, C_1560_ chunkPosIn) {
      Throwable var10000;
      if (throwableIn instanceof CompletionException completionexception) {
         var10000 = completionexception.getCause();
      } else {
         var10000 = throwableIn;
      }

      Throwable throwable = var10000;
      if (throwable instanceof C_5204_ reportedexception) {
         var10000 = reportedexception.getCause();
      } else {
         var10000 = throwable;
      }

      Throwable throwable1 = var10000;
      boolean flag1 = throwable1 instanceof Error;
      boolean flag = throwable1 instanceof IOException || throwable1 instanceof C_302104_;
      if (!flag1) {
         if (!flag) {
         }

         this.f_140133_.m_7654_().m_293783_(throwable1, this.m_340375_(), chunkPosIn);
         return this.m_214961_(chunkPosIn);
      } else {
         C_4883_ crashreport = C_4883_.m_127521_(throwableIn, "Exception loading chunk");
         C_4909_ crashreportcategory = crashreport.m_127514_("Chunk being loaded");
         crashreportcategory.m_128159_("pos", chunkPosIn);
         this.m_140422_(chunkPosIn);
         throw new C_5204_(crashreport);
      }
   }

   private C_2116_ m_214961_(C_1560_ chunkPosIn) {
      this.m_140422_(chunkPosIn);
      return new C_2147_(chunkPosIn, C_2149_.f_63320_, this.f_140133_, this.f_140133_.H_().m_175515_(C_256686_.f_256952_), (C_182959_)null);
   }

   private void m_140422_(C_1560_ chunkPosIn) {
      this.f_140151_.put(chunkPosIn.m_45588_(), (byte)-1);
   }

   private byte m_140229_(C_1560_ chunkPosIn, C_313671_ chunkTypeIn) {
      return this.f_140151_.put(chunkPosIn.m_45588_(), (byte)(chunkTypeIn == C_313671_.PROTOCHUNK ? -1 : 1));
   }

   public C_336526_ m_339158_(long posIn) {
      C_5422_ chunkholder = (C_5422_)this.f_140129_.get(posIn);
      chunkholder.m();
      return chunkholder;
   }

   public void m_338685_(C_336526_ holderIn) {
      holderIn.m_340129_();
   }

   public CompletableFuture m_338637_(C_336526_ holderIn, C_336565_ chunkStepIn, C_336561_ cacheIn) {
      C_1560_ chunkpos = holderIn.m_338581_();
      if (chunkStepIn.f_337059_() == C_313554_.f_314297_) {
         return this.m_140417_(chunkpos);
      } else {
         try {
            C_336526_ generationchunkholder = (C_336526_)cacheIn.m_338758_(chunkpos.f_45578_, chunkpos.f_45579_);
            C_2116_ chunkaccess = generationchunkholder.m_338381_(chunkStepIn.f_337059_().m_322072_());
            if (chunkaccess == null) {
               throw new IllegalStateException("Parent chunk missing");
            } else {
               CompletableFuture completablefuture = chunkStepIn.m_338624_(this.f_314073_, cacheIn, chunkaccess);
               this.f_140144_.m_5511_(chunkpos, chunkStepIn.f_337059_());
               return completablefuture;
            }
         } catch (Exception var8) {
            var8.getStackTrace();
            C_4883_ crashreport = C_4883_.m_127521_(var8, "Exception generating new chunk");
            C_4909_ crashreportcategory = crashreport.m_127514_("Chunk to be generated");
            crashreportcategory.m_128165_("Status being generated", () -> {
               return chunkStepIn.f_337059_().m_339742_();
            });
            crashreportcategory.m_128159_("Location", String.format(Locale.ROOT, "%d,%d", chunkpos.f_45578_, chunkpos.f_45579_));
            crashreportcategory.m_128159_("Position hash", C_1560_.m_45589_(chunkpos.f_45578_, chunkpos.f_45579_));
            crashreportcategory.m_128159_("Generator", this.m_183719_());
            this.f_140135_.execute(() -> {
               throw new C_5204_(crashreport);
            });
            throw new C_5204_(crashreport);
         }
      }
   }

   public C_336542_ m_338350_(C_313554_ statusIn, C_1560_ posIn) {
      C_336542_ chunkgenerationtask = C_336542_.m_339931_(this, statusIn, posIn);
      this.f_337610_.add(chunkgenerationtask);
      return chunkgenerationtask;
   }

   private void m_340434_(C_336542_ taskIn) {
      this.f_140142_.m_6937_(C_5435_.m_140642_(taskIn.m_340452_(), () -> {
         CompletableFuture completablefuture = taskIn.m_340381_();
         if (completablefuture != null) {
            completablefuture.thenRun(() -> {
               this.m_340434_(taskIn);
            });
         }

      }));
   }

   public void m_339290_() {
      this.f_337610_.forEach(this::m_340434_);
      this.f_337610_.clear();
   }

   public CompletableFuture m_143053_(C_5422_ chunkHolderIn) {
      CompletableFuture completablefuture = this.m_280541_(chunkHolderIn, 1, (levelIn) -> {
         return C_313554_.f_315432_;
      });
      CompletableFuture completablefuture1 = completablefuture.thenApplyAsync((resultIn) -> {
         return resultIn.m_320014_((chunksIn) -> {
            return (C_2137_)chunksIn.get(chunksIn.size() / 2);
         });
      }, (runnableIn) -> {
         this.f_140143_.m_6937_(C_5435_.m_140642_(chunkHolderIn, runnableIn));
      }).thenApplyAsync((result2In) -> {
         return result2In.m_320477_((levelChunk2In) -> {
            levelChunk2In.m_62812_();
            this.f_140133_.m_184102_(levelChunk2In);
            CompletableFuture completablefuture2 = chunkHolderIn.m_294134_();
            if (completablefuture2.isDone()) {
               this.m_293802_(levelChunk2In);
            } else {
               completablefuture2.thenAcceptAsync((voidIn) -> {
                  this.m_293802_(levelChunk2In);
               }, this.f_140135_);
            }

         });
      }, this.f_140135_);
      completablefuture1.handle((resultIn, throwableIn) -> {
         this.f_140146_.getAndIncrement();
         return null;
      });
      return completablefuture1;
   }

   private void m_293802_(C_2137_ chunkIn) {
      C_1560_ chunkpos = chunkIn.f();
      Iterator var3 = this.f_140149_.m_183926_().iterator();

      while(var3.hasNext()) {
         C_13_ serverplayer = (C_13_)var3.next();
         if (serverplayer.m_292900_().m_293959_(chunkpos)) {
            m_296018_(serverplayer, chunkIn);
         }
      }

   }

   public CompletableFuture m_143109_(C_5422_ chunkHolderIn) {
      return this.m_280541_(chunkHolderIn, 1, C_286930_::m_339977_).thenApplyAsync((resultIn) -> {
         return resultIn.m_320014_((worldsIn) -> {
            return (C_2137_)worldsIn.get(worldsIn.size() / 2);
         });
      }, (runnableIn) -> {
         this.f_140143_.m_6937_(C_5435_.m_140642_(chunkHolderIn, runnableIn));
      });
   }

   public int m_140368_() {
      return this.f_140146_.get();
   }

   private boolean m_198874_(C_5422_ chunkHolderIn) {
      if (chunkHolderIn.m_140095_() && chunkHolderIn.m_339539_()) {
         C_2116_ chunkaccess = chunkHolderIn.p();
         if (!(chunkaccess instanceof C_2136_) && !(chunkaccess instanceof C_2137_)) {
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

   private boolean m_140258_(C_2116_ chunkIn) {
      this.f_140138_.a(chunkIn.m_7697_());
      if (!chunkIn.m_6344_()) {
         return false;
      } else {
         chunkIn.m_8092_(false);
         C_1560_ chunkpos = chunkIn.m_7697_();

         try {
            C_313554_ chunkstatus = chunkIn.m_6415_();
            if (chunkstatus.m_321717_() != C_313671_.LEVELCHUNK) {
               if (this.m_140425_(chunkpos)) {
                  return false;
               }

               if (chunkstatus == C_313554_.f_314297_ && chunkIn.m_6633_().values().stream().noneMatch(C_2588_::m_73603_)) {
                  return false;
               }
            }

            this.f_140133_.ag().m_6174_("chunkSave");
            C_4917_ compoundtag = C_2158_.m_63454_(this.f_140133_, chunkIn);
            if (Reflector.ForgeEventFactory_onChunkDataSave.exists()) {
               C_1596_ worldForge = (C_1596_)Reflector.call(chunkIn, Reflector.ForgeIChunk_getWorldForge);
               Reflector.ForgeEventFactory_onChunkDataSave.call(chunkIn, worldForge != null ? worldForge : this.f_140133_, compoundtag);
            }

            this.m_63502_(chunkpos, compoundtag).exceptionally((voidIn) -> {
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

   private boolean m_140425_(C_1560_ chunkPosIn) {
      byte b0 = this.f_140151_.get(chunkPosIn.m_45588_());
      if (b0 != 0) {
         return b0 == 1;
      } else {
         C_4917_ compoundtag;
         try {
            compoundtag = (C_4917_)((Optional)this.m_214963_(chunkPosIn).join()).orElse((Object)null);
            if (compoundtag == null) {
               this.m_140422_(chunkPosIn);
               return false;
            }
         } catch (Exception var5) {
            f_140128_.error("Failed to read chunk {}", chunkPosIn, var5);
            this.m_140422_(chunkPosIn);
            return false;
         }

         C_313671_ chunktype = C_2158_.m_63485_(compoundtag);
         return this.m_140229_(chunkPosIn, chunktype) == 1;
      }
   }

   protected void m_293413_(int distanceIn) {
      int i = C_188_.m_14045_(distanceIn, 2, 64);
      if (i != this.f_290679_) {
         this.f_290679_ = i;
         this.f_140145_.m_140777_(this.f_290679_);
         Iterator var3 = this.f_140149_.m_183926_().iterator();

         while(var3.hasNext()) {
            C_13_ serverplayer = (C_13_)var3.next();
            this.m_183754_(serverplayer);
         }
      }

   }

   int m_294650_(C_13_ playerIn) {
      return C_188_.m_14045_(playerIn.m_295486_(), 2, this.f_290679_);
   }

   private void m_294841_(C_13_ playerIn, C_1560_ posIn) {
      C_2137_ levelchunk = this.m_295187_(posIn.m_45588_());
      if (levelchunk != null) {
         m_296018_(playerIn, levelchunk);
      }

   }

   private static void m_296018_(C_13_ playerIn, C_2137_ chunkIn) {
      playerIn.f_8906_.f_290664_.m_293202_(chunkIn);
   }

   private static void m_295122_(C_13_ playerIn, C_1560_ posIn) {
      playerIn.f_8906_.f_290664_.m_293883_(playerIn, posIn);
   }

   @Nullable
   public C_2137_ m_295187_(long posIn) {
      C_5422_ chunkholder = this.m_140327_(posIn);
      return chunkholder == null ? null : chunkholder.m_295552_();
   }

   public int m_140394_() {
      return this.f_140130_.size();
   }

   public C_5442_ m_143145_() {
      return this.f_140145_;
   }

   protected Iterable m_140416_() {
      return Iterables.unmodifiableIterable(this.f_140130_.values());
   }

   void m_140274_(Writer writerIn) throws IOException {
      C_168_ csvoutput = C_168_.m_13619_().m_13630_("x").m_13630_("z").m_13630_("level").m_13630_("in_memory").m_13630_("status").m_13630_("full_status").m_13630_("accessible_ready").m_13630_("ticking_ready").m_13630_("entity_ticking_ready").m_13630_("ticket").m_13630_("spawning").m_13630_("block_entity_count").m_13630_("ticking_ticket").m_13630_("ticking_level").m_13630_("block_ticks").m_13630_("fluid_ticks").m_13628_(writerIn);
      C_182767_ tickingtracker = this.f_140145_.m_183915_();
      ObjectBidirectionalIterator var4 = this.f_140130_.long2ObjectEntrySet().iterator();

      while(var4.hasNext()) {
         Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry)var4.next();
         long i = entry.getLongKey();
         C_1560_ chunkpos = new C_1560_(i);
         C_5422_ chunkholder = (C_5422_)entry.getValue();
         Optional optional = Optional.ofNullable(chunkholder.p());
         Optional optional1 = optional.flatMap((worldIn) -> {
            return worldIn instanceof C_2137_ ? Optional.of((C_2137_)worldIn) : Optional.empty();
         });
         csvoutput.m_13624_(new Object[]{chunkpos.f_45578_, chunkpos.f_45579_, chunkholder.m_140093_(), optional.isPresent(), optional.map(C_2116_::m_6415_).orElse((Object)null), optional1.map(C_2137_::m_287138_).orElse((Object)null), m_140278_(chunkholder.m_140082_()), m_140278_(chunkholder.m_140026_()), m_140278_(chunkholder.m_140073_()), this.f_140145_.m_140838_(i), this.m_183879_(chunkpos), optional1.map((chunkIn) -> {
            return chunkIn.m_62954_().size();
         }).orElse(0), tickingtracker.m_184175_(i), tickingtracker.m_6172_(i), optional1.map((chunk2In) -> {
            return chunk2In.m_183531_().a();
         }).orElse(0), optional1.map((chunk3In) -> {
            return chunk3In.m_183526_().a();
         }).orElse(0)});
      }

   }

   private static String m_140278_(CompletableFuture futureIn) {
      try {
         C_313292_ chunkresult = (C_313292_)futureIn.getNow((Object)null);
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

   private CompletableFuture m_214963_(C_1560_ chunkPosIn) {
      return this.m_223454_(chunkPosIn).thenApplyAsync((tagIn) -> {
         return tagIn.map(this::m_214947_);
      }, C_5322_.m_183991_());
   }

   private C_4917_ m_214947_(C_4917_ tagIn) {
      return this.m_188288_(this.f_140133_.af(), this.f_140137_, tagIn, this.m_183719_().m_187743_());
   }

   boolean m_183879_(C_1560_ chunkPosIn) {
      if (!this.f_140145_.m_140847_(chunkPosIn.m_45588_())) {
         return false;
      } else {
         Iterator var2 = this.f_140149_.m_183926_().iterator();

         C_13_ serverplayer;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            serverplayer = (C_13_)var2.next();
         } while(!this.m_183751_(serverplayer, chunkPosIn));

         return true;
      }
   }

   public List m_183888_(C_1560_ chunkPosIn) {
      long i = chunkPosIn.m_45588_();
      if (!this.f_140145_.m_140847_(i)) {
         return List.of();
      } else {
         ImmutableList.Builder builder = ImmutableList.builder();
         Iterator var5 = this.f_140149_.m_183926_().iterator();

         while(var5.hasNext()) {
            C_13_ serverplayer = (C_13_)var5.next();
            if (this.m_183751_(serverplayer, chunkPosIn)) {
               builder.add(serverplayer);
            }
         }

         return builder.build();
      }
   }

   private boolean m_183751_(C_13_ playerIn, C_1560_ chunkPosIn) {
      if (playerIn.m_5833_()) {
         return false;
      } else {
         double d0 = m_140226_(chunkPosIn, playerIn);
         return d0 < 16384.0;
      }
   }

   private boolean m_140329_(C_13_ player) {
      return player.m_5833_() && !this.f_140133_.ab().m_46207_(C_1583_.f_46146_);
   }

   void m_140192_(C_13_ player, boolean track) {
      boolean flag = this.m_140329_(player);
      boolean flag1 = this.f_140149_.m_8260_(player);
      if (track) {
         this.f_140149_.m_8252_(player, flag);
         this.m_140373_(player);
         if (!flag) {
            this.f_140145_.m_140802_(C_4710_.m_235861_(player), player);
         }

         player.m_294756_(C_290036_.f_290823_);
         this.m_183754_(player);
      } else {
         C_4710_ sectionpos = player.m_8965_();
         this.f_140149_.m_8249_(player);
         if (!flag1) {
            this.f_140145_.m_140828_(sectionpos, player);
         }

         this.m_294836_(player, C_290036_.f_290823_);
      }

   }

   private void m_140373_(C_13_ serverPlayerEntityIn) {
      C_4710_ sectionpos = C_4710_.m_235861_(serverPlayerEntityIn);
      serverPlayerEntityIn.m_9119_(sectionpos);
   }

   public void m_140184_(C_13_ player) {
      ObjectIterator var2 = this.f_140150_.values().iterator();

      while(var2.hasNext()) {
         C_5433_ chunkmap$trackedentity = (C_5433_)var2.next();
         if (chunkmap$trackedentity.f_140472_ == player) {
            chunkmap$trackedentity.m_140487_(this.f_140133_.m_6907_());
         } else {
            chunkmap$trackedentity.m_140497_(player);
         }
      }

      C_4710_ sectionpos = player.m_8965_();
      C_4710_ sectionpos1 = C_4710_.m_235861_(player);
      boolean flag = this.f_140149_.m_8262_(player);
      boolean flag1 = this.m_140329_(player);
      boolean flag2 = sectionpos.m_123252_() != sectionpos1.m_123252_();
      if (flag2 || flag != flag1) {
         this.m_140373_(player);
         if (!flag) {
            this.f_140145_.m_140828_(sectionpos, player);
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

   private void m_183754_(C_13_ player) {
      C_1560_ chunkpos = player.dq();
      int i = this.m_294650_(player);
      C_290036_ var5 = player.m_292900_();
      if (var5 instanceof C_290036_.C_290047_ chunktrackingview$positioned) {
         if (chunktrackingview$positioned.f_290448_().equals(chunkpos) && chunktrackingview$positioned.f_290668_() == i) {
            return;
         }
      }

      this.m_294836_(player, C_290036_.m_294585_(chunkpos, i));
   }

   private void m_294836_(C_13_ playerIn, C_290036_ viewIn) {
      if (playerIn.dO() == this.f_140133_) {
         C_290036_ chunktrackingview = playerIn.m_292900_();
         if (viewIn instanceof C_290036_.C_290047_) {
            label21: {
               C_290036_.C_290047_ chunktrackingview$positioned = (C_290036_.C_290047_)viewIn;
               if (chunktrackingview instanceof C_290036_.C_290047_) {
                  C_290036_.C_290047_ chunktrackingview$positioned1 = (C_290036_.C_290047_)chunktrackingview;
                  if (chunktrackingview$positioned1.f_290448_().equals(chunktrackingview$positioned.f_290448_())) {
                     break label21;
                  }
               }

               playerIn.f_8906_.b(new C_5112_(chunktrackingview$positioned.f_290448_().f_45578_, chunktrackingview$positioned.f_290448_().f_45579_));
            }
         }

         C_290036_.m_293383_(chunktrackingview, viewIn, (chunkPos2In) -> {
            this.m_294841_(playerIn, chunkPos2In);
         }, (chunkPos3In) -> {
            m_295122_(playerIn, chunkPos3In);
         });
         playerIn.m_294756_(viewIn);
      }

   }

   public List m_183262_(C_1560_ pos, boolean boundaryOnly) {
      Set set = this.f_140149_.m_183926_();
      ImmutableList.Builder builder = ImmutableList.builder();
      Iterator var5 = set.iterator();

      while(true) {
         C_13_ serverplayer;
         do {
            if (!var5.hasNext()) {
               return builder.build();
            }

            serverplayer = (C_13_)var5.next();
         } while((!boundaryOnly || !this.m_293654_(serverplayer, pos.f_45578_, pos.f_45579_)) && (boundaryOnly || !this.m_295978_(serverplayer, pos.f_45578_, pos.f_45579_)));

         builder.add(serverplayer);
      }
   }

   protected void m_140199_(C_507_ entityIn) {
      boolean multipart = entityIn instanceof C_943_;
      if (Reflector.PartEntity.exists()) {
         multipart = Reflector.PartEntity.isInstance(entityIn);
      }

      if (!multipart) {
         C_513_ entitytype = entityIn.m_6095_();
         int i = entitytype.m_20681_() * 16;
         if (i != 0) {
            int j = entitytype.m_20682_();
            if (this.f_140150_.containsKey(entityIn.m_19879_())) {
               throw (IllegalStateException)C_5322_.m_137570_(new IllegalStateException("Entity is already tracked!"));
            }

            C_5433_ chunkmap$trackedentity = new C_5433_(entityIn, i, j, entitytype.m_20683_());
            this.f_140150_.put(entityIn.m_19879_(), chunkmap$trackedentity);
            chunkmap$trackedentity.m_140487_(this.f_140133_.m_6907_());
            if (entityIn instanceof C_13_) {
               C_13_ serverplayer = (C_13_)entityIn;
               this.m_140192_(serverplayer, true);
               ObjectIterator var8 = this.f_140150_.values().iterator();

               while(var8.hasNext()) {
                  C_5433_ chunkmap$trackedentity1 = (C_5433_)var8.next();
                  if (chunkmap$trackedentity1.f_140472_ != serverplayer) {
                     chunkmap$trackedentity1.m_140497_(serverplayer);
                  }
               }
            }
         }
      }

   }

   protected void m_140331_(C_507_ entityIn) {
      if (entityIn instanceof C_13_ serverplayer) {
         this.m_140192_(serverplayer, false);
         ObjectIterator var3 = this.f_140150_.values().iterator();

         while(var3.hasNext()) {
            C_5433_ chunkmap$trackedentity = (C_5433_)var3.next();
            chunkmap$trackedentity.m_140485_(serverplayer);
         }
      }

      C_5433_ chunkmap$trackedentity1 = (C_5433_)this.f_140150_.remove(entityIn.m_19879_());
      if (chunkmap$trackedentity1 != null) {
         chunkmap$trackedentity1.m_140482_();
      }

   }

   protected void m_140421_() {
      Iterator var1 = this.f_140149_.m_183926_().iterator();

      while(var1.hasNext()) {
         C_13_ serverplayer = (C_13_)var1.next();
         this.m_183754_(serverplayer);
      }

      List list = Lists.newArrayList();
      List list1 = this.f_140133_.m_6907_();
      ObjectIterator var3 = this.f_140150_.values().iterator();

      C_5433_ chunkmap$trackedentity1;
      while(var3.hasNext()) {
         chunkmap$trackedentity1 = (C_5433_)var3.next();
         C_4710_ sectionpos = chunkmap$trackedentity1.f_140474_;
         C_4710_ sectionpos1 = C_4710_.m_235861_(chunkmap$trackedentity1.f_140472_);
         boolean flag = !Objects.equals(sectionpos, sectionpos1);
         if (flag) {
            chunkmap$trackedentity1.m_140487_(list1);
            C_507_ entity = chunkmap$trackedentity1.f_140472_;
            if (entity instanceof C_13_) {
               list.add((C_13_)entity);
            }

            chunkmap$trackedentity1.f_140474_ = sectionpos1;
         }

         if (flag || this.f_140145_.m_183913_(sectionpos1.m_123251_().m_45588_())) {
            chunkmap$trackedentity1.f_140471_.m_8533_();
         }
      }

      if (!list.isEmpty()) {
         var3 = this.f_140150_.values().iterator();

         while(var3.hasNext()) {
            chunkmap$trackedentity1 = (C_5433_)var3.next();
            chunkmap$trackedentity1.m_140487_(list);
         }
      }

   }

   public void m_140201_(C_507_ entityIn, C_5028_ packetIn) {
      C_5433_ chunkmap$trackedentity = (C_5433_)this.f_140150_.get(entityIn.m_19879_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.m_140489_(packetIn);
      }

   }

   protected void m_140333_(C_507_ entityIn, C_5028_ packetIn) {
      C_5433_ chunkmap$trackedentity = (C_5433_)this.f_140150_.get(entityIn.m_19879_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.m_140499_(packetIn);
      }

   }

   public void m_274524_(List chunksIn) {
      Map map = new HashMap();
      Iterator var3 = chunksIn.iterator();

      while(var3.hasNext()) {
         C_2116_ chunkaccess = (C_2116_)var3.next();
         C_1560_ chunkpos = chunkaccess.m_7697_();
         C_2137_ levelchunk;
         if (chunkaccess instanceof C_2137_ levelchunk1) {
            levelchunk = levelchunk1;
         } else {
            levelchunk = this.f_140133_.d(chunkpos.f_45578_, chunkpos.f_45579_);
         }

         Iterator var9 = this.m_183262_(chunkpos, false).iterator();

         while(var9.hasNext()) {
            C_13_ serverplayer = (C_13_)var9.next();
            ((List)map.computeIfAbsent(serverplayer, (playerIn) -> {
               return new ArrayList();
            })).add(levelchunk);
         }
      }

      map.forEach((playerIn, chunks2In) -> {
         playerIn.f_8906_.b(C_273796_.m_274415_(chunks2In));
      });
   }

   protected C_787_ m_140424_() {
      return this.f_140138_;
   }

   public String m_182285_() {
      return this.f_182284_;
   }

   void m_287285_(C_1560_ chunkPosIn, C_286921_ statusIn) {
      this.f_143031_.m_156794_(chunkPosIn, statusIn);
   }

   public void m_293872_(C_1560_ chunkPosIn, int distIn) {
      int i = distIn + 1;
      C_1560_.m_45596_(chunkPosIn, i).forEach((chunkPos2In) -> {
         C_5422_ chunkholder = this.m_140327_(chunkPos2In.m_45588_());
         if (chunkholder != null) {
            chunkholder.m_295085_(this.f_140134_.m_293279_(chunkPos2In.f_45578_, chunkPos2In.f_45579_));
         }

      });
   }

   static {
      f_337118_ = CompletableFuture.completedFuture(f_336919_);
      f_140128_ = LogUtils.getLogger();
      f_143033_ = C_286930_.m_287154_(C_286921_.ENTITY_TICKING);
   }

   class C_5432_ extends C_5442_ {
      protected C_5432_(final Executor priorityExecutorIn, final Executor executorIn) {
         super(priorityExecutorIn, executorIn);
      }

      protected boolean m_7009_(long chunkPosIn) {
         return C_5429_.this.f_140139_.contains(chunkPosIn);
      }

      @Nullable
      protected C_5422_ m_7316_(long chunkPosIn) {
         return C_5429_.this.m_140174_(chunkPosIn);
      }

      @Nullable
      protected C_5422_ m_7288_(long chunkPosIn, int newLevel, @Nullable C_5422_ holder, int oldLevel) {
         return C_5429_.this.m_140176_(chunkPosIn, newLevel, holder, oldLevel);
      }
   }

   class C_5433_ {
      final C_11_ f_140471_;
      final C_507_ f_140472_;
      private final int f_140473_;
      C_4710_ f_140474_;
      private final Set f_140475_ = Sets.newIdentityHashSet();

      public C_5433_(final C_507_ entityIn, final int rangeIn, final int updateFrequency, final boolean sendVelocityUpdatesIn) {
         this.f_140471_ = new C_11_(C_5429_.this.f_140133_, entityIn, updateFrequency, sendVelocityUpdatesIn, this::m_140489_);
         this.f_140472_ = entityIn;
         this.f_140473_ = rangeIn;
         this.f_140474_ = C_4710_.m_235861_(entityIn);
      }

      public boolean equals(Object p_equals_1_) {
         return p_equals_1_ instanceof C_5433_ ? ((C_5433_)p_equals_1_).f_140472_.m_19879_() == this.f_140472_.m_19879_() : false;
      }

      public int hashCode() {
         return this.f_140472_.m_19879_();
      }

      public void m_140489_(C_5028_ packetIn) {
         Iterator var2 = this.f_140475_.iterator();

         while(var2.hasNext()) {
            C_140962_ serverplayerconnection = (C_140962_)var2.next();
            serverplayerconnection.m_141995_(packetIn);
         }

      }

      public void m_140499_(C_5028_ packetIn) {
         this.m_140489_(packetIn);
         if (this.f_140472_ instanceof C_13_) {
            ((C_13_)this.f_140472_).f_8906_.b(packetIn);
         }

      }

      public void m_140482_() {
         Iterator var1 = this.f_140475_.iterator();

         while(var1.hasNext()) {
            C_140962_ serverplayerconnection = (C_140962_)var1.next();
            this.f_140471_.m_8534_(serverplayerconnection.m_142253_());
         }

      }

      public void m_140485_(C_13_ player) {
         if (this.f_140475_.remove(player.f_8906_)) {
            this.f_140471_.m_8534_(player);
         }

      }

      public void m_140497_(C_13_ player) {
         if (player != this.f_140472_) {
            C_3046_ vec3 = player.dm().m_82546_(this.f_140472_.m_20182_());
            int i = C_5429_.this.m_294650_(player);
            double d0 = (double)Math.min(this.m_140496_(), i * 16);
            double d1 = vec3.f_82479_ * vec3.f_82479_ + vec3.f_82481_ * vec3.f_82481_;
            double d2 = d0 * d0;
            boolean flag = d1 <= d2 && this.f_140472_.m_6459_(player) && C_5429_.this.m_295978_(player, this.f_140472_.m_146902_().f_45578_, this.f_140472_.m_146902_().f_45579_);
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
         return C_5429_.this.f_140133_.m_7654_().m_7186_(rangeIn);
      }

      private int m_140496_() {
         int i = this.f_140473_;
         if (!this.f_140472_.m_20197_().isEmpty()) {
            Iterator var2 = this.f_140472_.m_146897_().iterator();

            while(var2.hasNext()) {
               C_507_ entity = (C_507_)var2.next();
               int j = entity.m_6095_().m_20681_() * 16;
               if (j > i) {
                  i = j;
               }
            }
         }

         return this.m_140483_(i);
      }

      public void m_140487_(List playersListIn) {
         Iterator var2 = playersListIn.iterator();

         while(var2.hasNext()) {
            C_13_ serverplayer = (C_13_)var2.next();
            this.m_140497_(serverplayer);
         }

      }
   }
}
