package net.minecraft.src;

import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceImmutableList;
import it.unimi.dsi.fastutil.objects.ReferenceList;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.optifine.SmartExecutorService;
import org.slf4j.Logger;

public class C_5322_ {
   static final Logger f_137446_ = LogUtils.getLogger();
   private static final int f_183935_ = 255;
   private static final int f_303362_ = 10;
   private static final String f_183936_ = "max.bg.threads";
   private static final ExecutorService f_137444_ = m_137477_("Main");
   private static final ExecutorService f_137445_ = m_137586_("IO-Worker-", false);
   private static final ExecutorService f_302521_ = m_137586_("Download-", true);
   private static final DateTimeFormatter f_241646_;
   public static final int f_303450_ = 8;
   private static final Set f_337259_;
   public static final long f_291561_ = 1000000L;
   public static C_238509_.C_238510_ f_137440_;
   public static final Ticker f_211544_;
   public static final UUID f_137441_;
   public static final FileSystemProvider f_143778_;
   private static Consumer f_183937_;
   private static Exception exceptionOpenUrl;
   private static final ExecutorService CAPE_EXECUTOR;
   private static LongSupplier INNER_CLASS_SHIFT1;
   private static LongSupplier INNER_CLASS_SHIFT2;

   public static Collector m_137448_() {
      return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
   }

   public static Collector m_323807_() {
      return Collectors.toCollection(Lists::newArrayList);
   }

   public static String m_137453_(C_2097_ property, Object value) {
      return property.m_6940_((Comparable)value);
   }

   public static String m_137492_(String type, @Nullable C_5265_ id) {
      return id == null ? type + ".unregistered_sadface" : type + "." + id.m_135827_() + "." + id.m_135815_().replace('/', '.');
   }

   public static long m_137550_() {
      return m_137569_() / 1000000L;
   }

   public static long m_137569_() {
      return f_137440_.getAsLong();
   }

   public static long m_137574_() {
      return Instant.now().toEpochMilli();
   }

   public static String m_241986_() {
      return f_241646_.format(ZonedDateTime.now());
   }

   private static ExecutorService m_137477_(String nameIn) {
      int i = C_188_.m_14045_(Runtime.getRuntime().availableProcessors() - 1, 1, m_183993_());
      Object executorservice;
      if (i <= 0) {
         executorservice = MoreExecutors.newDirectExecutorService();
      } else {
         AtomicInteger atomicinteger = new AtomicInteger(1);
         executorservice = new ForkJoinPool(i, (poolIn) -> {
            ForkJoinWorkerThread forkjoinworkerthread = new ForkJoinWorkerThread(poolIn) {
               protected void onTermination(Throwable p_onTermination_1_) {
                  if (p_onTermination_1_ != null) {
                     C_5322_.f_137446_.warn("{} died", this.getName(), p_onTermination_1_);
                  } else {
                     C_5322_.f_137446_.debug("{} shutdown", this.getName());
                  }

                  super.onTermination(p_onTermination_1_);
               }
            };
            forkjoinworkerthread.setName("Worker-" + nameIn + "-" + atomicinteger.getAndIncrement());
            if (nameIn.equals("Bootstrap")) {
               forkjoinworkerthread.setPriority(1);
            }

            return forkjoinworkerthread;
         }, C_5322_::m_137495_, true);
      }

      if (nameIn.equals("Bootstrap")) {
         executorservice = createSmartExecutor((ExecutorService)executorservice);
      }

      return (ExecutorService)executorservice;
   }

   private static ExecutorService createSmartExecutor(ExecutorService executor) {
      int cpus = Runtime.getRuntime().availableProcessors();
      if (cpus <= 1) {
         return executor;
      } else {
         ExecutorService smartExecutor = new SmartExecutorService(executor);
         return smartExecutor;
      }
   }

   private static int m_183993_() {
      String s = System.getProperty("max.bg.threads");
      if (s != null) {
         try {
            int i = Integer.parseInt(s);
            if (i >= 1 && i <= 255) {
               return i;
            }

            f_137446_.error("Wrong {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{"max.bg.threads", s, 255});
         } catch (NumberFormatException var2) {
            f_137446_.error("Could not parse {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{"max.bg.threads", s, 255});
         }
      }

      return 255;
   }

   public static ExecutorService m_183991_() {
      return f_137444_;
   }

   public static ExecutorService m_183992_() {
      return f_137445_;
   }

   public static ExecutorService m_306705_() {
      return f_302521_;
   }

   public static void m_137580_() {
      m_137531_(f_137444_);
      m_137531_(f_137445_);
      m_137531_(CAPE_EXECUTOR);
   }

   private static void m_137531_(ExecutorService serviceIn) {
      serviceIn.shutdown();

      boolean flag;
      try {
         flag = serviceIn.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException var3) {
         flag = false;
      }

      if (!flag) {
         serviceIn.shutdownNow();
      }

   }

   private static ExecutorService m_137586_(String nameIn, boolean daemonIn) {
      AtomicInteger atomicinteger = new AtomicInteger(1);
      return Executors.newCachedThreadPool((runnableIn) -> {
         Thread thread = new Thread(runnableIn);
         thread.setName(nameIn + atomicinteger.getAndIncrement());
         thread.setDaemon(daemonIn);
         thread.setUncaughtExceptionHandler(C_5322_::m_137495_);
         return thread;
      });
   }

   public static void m_137559_(Throwable throwableIn) {
      throw throwableIn instanceof RuntimeException ? (RuntimeException)throwableIn : new RuntimeException(throwableIn);
   }

   private static void m_137495_(Thread threadIn, Throwable throwableIn) {
      m_137570_(throwableIn);
      if (throwableIn instanceof CompletionException) {
         throwableIn = throwableIn.getCause();
      }

      if (throwableIn instanceof C_5204_ reportedexception) {
         C_5267_.m_135875_(reportedexception.m_134761_().m_127526_(C_336586_.f_337619_));
         System.exit(-1);
      }

      f_137446_.error(String.format(Locale.ROOT, "Caught exception in thread %s", threadIn), throwableIn);
   }

   @Nullable
   public static Type m_137456_(DSL.TypeReference typeIn, String nameIn) {
      return !C_5285_.f_136182_ ? null : m_137551_(typeIn, nameIn);
   }

   @Nullable
   private static Type m_137551_(DSL.TypeReference typeIn, String nameIn) {
      Type type = null;

      try {
         type = C_209_.m_14512_().getSchema(DataFixUtils.makeKey(C_5285_.m_183709_().m_183476_().m_193006_())).getChoiceType(typeIn, nameIn);
      } catch (IllegalArgumentException var4) {
         f_137446_.debug("No data fixer registered for {}", nameIn);
         if (C_5285_.f_136183_) {
            throw var4;
         }
      }

      return type;
   }

   public static Runnable m_143787_(String nameIn, Runnable taskIn) {
      return C_5285_.f_136183_ ? () -> {
         Thread thread = Thread.currentThread();
         String s = thread.getName();
         thread.setName(nameIn);

         try {
            taskIn.run();
         } finally {
            thread.setName(s);
         }

      } : taskIn;
   }

   public static Supplier m_183946_(String nameIn, Supplier taskIn) {
      return C_5285_.f_136183_ ? () -> {
         Thread thread = Thread.currentThread();
         String s = thread.getName();
         thread.setName(nameIn);

         Object object;
         try {
            object = taskIn.get();
         } finally {
            thread.setName(s);
         }

         return object;
      } : taskIn;
   }

   public static String m_322642_(C_4705_ registryIn, Object valueIn) {
      C_5265_ resourcelocation = registryIn.m_7981_(valueIn);
      return resourcelocation == null ? "[unregistered]" : resourcelocation.toString();
   }

   public static Predicate m_322468_(List predicatesIn) {
      Predicate var10000;
      switch (predicatesIn.size()) {
         case 0:
            var10000 = (objIn) -> {
               return true;
            };
            break;
         case 1:
            var10000 = (Predicate)predicatesIn.get(0);
            break;
         case 2:
            var10000 = ((Predicate)predicatesIn.get(0)).and((Predicate)predicatesIn.get(1));
            break;
         default:
            Predicate[] predicate = (Predicate[])predicatesIn.toArray((x$0) -> {
               return new Predicate[x$0];
            });
            var10000 = (valIn) -> {
               Predicate[] var2 = predicate;
               int var3 = predicate.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  Predicate predicate1 = var2[var4];
                  if (!predicate1.test(valIn)) {
                     return false;
                  }
               }

               return true;
            };
      }

      return var10000;
   }

   public static Predicate m_321702_(List predicatesIn) {
      Predicate var10000;
      switch (predicatesIn.size()) {
         case 0:
            var10000 = (objIn) -> {
               return false;
            };
            break;
         case 1:
            var10000 = (Predicate)predicatesIn.get(0);
            break;
         case 2:
            var10000 = ((Predicate)predicatesIn.get(0)).or((Predicate)predicatesIn.get(1));
            break;
         default:
            Predicate[] predicate = (Predicate[])predicatesIn.toArray((x$0) -> {
               return new Predicate[x$0];
            });
            var10000 = (valIn) -> {
               Predicate[] var2 = predicate;
               int var3 = predicate.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  Predicate predicate1 = var2[var4];
                  if (predicate1.test(valIn)) {
                     return true;
                  }
               }

               return false;
            };
      }

      return var10000;
   }

   public static boolean m_340468_(int widthIn, int heightIn, List valuesIn) {
      if (widthIn == 1) {
         return true;
      } else {
         int i = widthIn / 2;

         for(int j = 0; j < heightIn; ++j) {
            for(int k = 0; k < i; ++k) {
               int l = widthIn - 1 - k;
               Object t = valuesIn.get(k + j * widthIn);
               Object t1 = valuesIn.get(l + j * widthIn);
               if (!t.equals(t1)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static C_5330_ m_137581_() {
      String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (s.contains("win")) {
         return C_5322_.C_5330_.WINDOWS;
      } else if (s.contains("mac")) {
         return C_5322_.C_5330_.OSX;
      } else if (s.contains("solaris")) {
         return C_5322_.C_5330_.SOLARIS;
      } else if (s.contains("sunos")) {
         return C_5322_.C_5330_.SOLARIS;
      } else if (s.contains("linux")) {
         return C_5322_.C_5330_.LINUX;
      } else {
         return s.contains("unix") ? C_5322_.C_5330_.LINUX : C_5322_.C_5330_.UNKNOWN;
      }
   }

   public static URI m_340630_(String strIn) throws URISyntaxException {
      URI uri = new URI(strIn);
      String s = uri.getScheme();
      if (s == null) {
         throw new URISyntaxException(strIn, "Missing protocol in URI: " + strIn);
      } else {
         String s1 = s.toLowerCase(Locale.ROOT);
         if (!f_337259_.contains(s1)) {
            throw new URISyntaxException(strIn, "Unsupported protocol in URI: " + strIn);
         } else {
            return uri;
         }
      }
   }

   public static Stream m_137582_() {
      RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
      return runtimemxbean.getInputArguments().stream().filter((argIn) -> {
         return argIn.startsWith("-X");
      });
   }

   public static Object m_137509_(List listIn) {
      return listIn.get(listIn.size() - 1);
   }

   public static Object m_137466_(Iterable iterable, @Nullable Object element) {
      Iterator iterator = iterable.iterator();
      Object t = iterator.next();
      if (element != null) {
         Object t1 = t;

         while(t1 != element) {
            if (iterator.hasNext()) {
               t1 = iterator.next();
            }
         }

         if (iterator.hasNext()) {
            return iterator.next();
         }
      }

      return t;
   }

   public static Object m_137554_(Iterable iterable, @Nullable Object current) {
      Iterator iterator = iterable.iterator();

      Object t;
      Object t1;
      for(t = null; iterator.hasNext(); t = t1) {
         t1 = iterator.next();
         if (t1 == current) {
            if (t == null) {
               t = iterator.hasNext() ? Iterators.getLast(iterator) : current;
            }
            break;
         }
      }

      return t;
   }

   public static Object m_137537_(Supplier supplier) {
      return supplier.get();
   }

   public static Object m_137469_(Object object, Consumer consumer) {
      consumer.accept(object);
      return object;
   }

   public static CompletableFuture m_137567_(List futuresIn) {
      if (futuresIn.isEmpty()) {
         return CompletableFuture.completedFuture(List.of());
      } else if (futuresIn.size() == 1) {
         return ((CompletableFuture)futuresIn.get(0)).thenApply(List::of);
      } else {
         CompletableFuture completablefuture = CompletableFuture.allOf((CompletableFuture[])futuresIn.toArray(new CompletableFuture[0]));
         return completablefuture.thenApply((objIn) -> {
            return futuresIn.stream().map(CompletableFuture::join).toList();
         });
      }
   }

   public static CompletableFuture m_143840_(List listIn) {
      CompletableFuture completablefuture = new CompletableFuture();
      Objects.requireNonNull(completablefuture);
      return m_214631_(listIn, completablefuture::completeExceptionally).applyToEither(completablefuture, Function.identity());
   }

   public static CompletableFuture m_214684_(List listIn) {
      CompletableFuture completablefuture = new CompletableFuture();
      return m_214631_(listIn, (throwableIn) -> {
         if (completablefuture.completeExceptionally(throwableIn)) {
            Iterator var3 = listIn.iterator();

            while(var3.hasNext()) {
               CompletableFuture completablefuture1 = (CompletableFuture)var3.next();
               completablefuture1.cancel(true);
            }
         }

      }).applyToEither(completablefuture, Function.identity());
   }

   private static CompletableFuture m_214631_(List listIn, Consumer handlerIn) {
      List list = Lists.newArrayListWithCapacity(listIn.size());
      CompletableFuture[] completablefuture = new CompletableFuture[listIn.size()];
      listIn.forEach((futureIn) -> {
         int i = list.size();
         list.add((Object)null);
         completablefuture[i] = futureIn.whenComplete((objIn, throwableIn) -> {
            if (throwableIn != null) {
               handlerIn.accept(throwableIn);
            } else {
               list.set(i, objIn);
            }

         });
      });
      return CompletableFuture.allOf(completablefuture).thenApply((voidIn) -> {
         return list;
      });
   }

   public static Exception getExceptionOpenUrl() {
      return exceptionOpenUrl;
   }

   public static void setExceptionOpenUrl(Exception exceptionOpenUrl) {
      C_5322_.exceptionOpenUrl = exceptionOpenUrl;
   }

   public static ExecutorService getCapeExecutor() {
      return CAPE_EXECUTOR;
   }

   public static Optional m_137521_(Optional opt, Consumer consumer, Runnable orElse) {
      if (opt.isPresent()) {
         consumer.accept(opt.get());
      } else {
         orElse.run();
      }

      return opt;
   }

   public static Supplier m_214655_(Supplier objIn, Supplier nameIn) {
      return objIn;
   }

   public static Runnable m_137474_(Runnable runnableIn, Supplier supplierIn) {
      return runnableIn;
   }

   public static void m_143785_(String logTextIn) {
      f_137446_.error(logTextIn);
      if (C_5285_.f_136183_) {
         m_183984_(logTextIn);
      }

   }

   public static void m_200890_(String textIn, Throwable throwableIn) {
      f_137446_.error(textIn, throwableIn);
      if (C_5285_.f_136183_) {
         m_183984_(textIn);
      }

   }

   public static Throwable m_137570_(Throwable throwableIn) {
      if (C_5285_.f_136183_) {
         f_137446_.error("Trying to throw a fatal exception, pausing in IDE", throwableIn);
         m_183984_(throwableIn.getMessage());
      }

      return throwableIn;
   }

   public static void m_183969_(Consumer pauserIn) {
      f_183937_ = pauserIn;
   }

   private static void m_183984_(String nameIn) {
      Instant instant = Instant.now();
      f_137446_.warn("Did you remember to set a breakpoint here?");
      boolean flag = Duration.between(instant, Instant.now()).toMillis() > 500L;
      if (!flag) {
         f_183937_.accept(nameIn);
      }

   }

   public static String m_137575_(Throwable throwableIn) {
      if (throwableIn.getCause() != null) {
         return m_137575_(throwableIn.getCause());
      } else {
         return throwableIn.getMessage() != null ? throwableIn.getMessage() : throwableIn.toString();
      }
   }

   public static Object m_214670_(Object[] valuesIn, C_212974_ randomIn) {
      return valuesIn[randomIn.m_188503_(valuesIn.length)];
   }

   public static int m_214667_(int[] valuesIn, C_212974_ randomIn) {
      return valuesIn[randomIn.m_188503_(valuesIn.length)];
   }

   public static Object m_214621_(List listIn, C_212974_ randomIn) {
      return listIn.get(randomIn.m_188503_(listIn.size()));
   }

   public static Optional m_214676_(List listIn, C_212974_ randomIn) {
      return listIn.isEmpty() ? Optional.empty() : Optional.of(m_214621_(listIn, randomIn));
   }

   private static BooleanSupplier m_137502_(final Path pathFrom, final Path pathTo) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.move(pathFrom, pathTo);
               return true;
            } catch (IOException var2) {
               C_5322_.f_137446_.error("Failed to rename", var2);
               return false;
            }
         }

         public String toString() {
            String var10000 = String.valueOf(pathFrom);
            return "rename " + var10000 + " to " + String.valueOf(pathTo);
         }
      };
   }

   private static BooleanSupplier m_137500_(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.deleteIfExists(pathIn);
               return true;
            } catch (IOException var2) {
               C_5322_.f_137446_.warn("Failed to delete", var2);
               return false;
            }
         }

         public String toString() {
            return "delete old " + String.valueOf(pathIn);
         }
      };
   }

   private static BooleanSupplier m_137561_(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return !Files.exists(pathIn, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + String.valueOf(pathIn) + " is deleted";
         }
      };
   }

   private static BooleanSupplier m_137572_(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return Files.isRegularFile(pathIn, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + String.valueOf(pathIn) + " is present";
         }
      };
   }

   private static boolean m_137548_(BooleanSupplier... listIn) {
      BooleanSupplier[] var1 = listIn;
      int var2 = listIn.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         BooleanSupplier booleansupplier = var1[var3];
         if (!booleansupplier.getAsBoolean()) {
            f_137446_.warn("Failed to execute {}", booleansupplier);
            return false;
         }
      }

      return true;
   }

   private static boolean m_137449_(int countIn, String nameIn, BooleanSupplier... listIn) {
      for(int i = 0; i < countIn; ++i) {
         if (m_137548_(listIn)) {
            return true;
         }

         f_137446_.error("Failed to {}, retrying {}/{}", new Object[]{nameIn, i, countIn});
      }

      f_137446_.error("Failed to {}, aborting, progress might be lost", nameIn);
      return false;
   }

   public static void m_137505_(Path pathFrom, Path pathTo, Path pathBackup) {
      m_212224_(pathFrom, pathTo, pathBackup, false);
   }

   public static boolean m_212224_(Path fileFrom, Path fileTo, Path fileBackup, boolean restoreIn) {
      if (Files.exists(fileFrom, new LinkOption[0]) && !m_137449_(10, "create backup " + String.valueOf(fileBackup), m_137500_(fileBackup), m_137502_(fileFrom, fileBackup), m_137572_(fileBackup))) {
         return false;
      } else if (!m_137449_(10, "remove old " + String.valueOf(fileFrom), m_137500_(fileFrom), m_137561_(fileFrom))) {
         return false;
      } else if (!m_137449_(10, "replace " + String.valueOf(fileFrom) + " with " + String.valueOf(fileTo), m_137502_(fileTo, fileFrom), m_137572_(fileFrom)) && !restoreIn) {
         m_137449_(10, "restore " + String.valueOf(fileFrom) + " from " + String.valueOf(fileBackup), m_137502_(fileBackup, fileFrom), m_137572_(fileFrom));
         return false;
      } else {
         return true;
      }
   }

   public static int m_137479_(String textIn, int posIn, int offsetIn) {
      int i = textIn.length();
      int j;
      if (offsetIn >= 0) {
         for(j = 0; posIn < i && j < offsetIn; ++j) {
            if (Character.isHighSurrogate(textIn.charAt(posIn++)) && posIn < i && Character.isLowSurrogate(textIn.charAt(posIn))) {
               ++posIn;
            }
         }
      } else {
         for(j = offsetIn; posIn > 0 && j < 0; ++j) {
            --posIn;
            if (Character.isLowSurrogate(textIn.charAt(posIn)) && posIn > 0 && Character.isHighSurrogate(textIn.charAt(posIn - 1))) {
               --posIn;
            }
         }
      }

      return posIn;
   }

   public static Consumer m_137489_(String textIn, Consumer consumerIn) {
      return (strIn) -> {
         consumerIn.accept(textIn + strIn);
      };
   }

   public static DataResult m_137539_(IntStream streamIn, int sizeIn) {
      int[] aint = streamIn.limit((long)(sizeIn + 1)).toArray();
      if (aint.length != sizeIn) {
         Supplier supplier = () -> {
            return "Input is not a list of " + sizeIn + " ints";
         };
         return aint.length >= sizeIn ? DataResult.error(supplier, Arrays.copyOf(aint, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(aint);
      }
   }

   public static DataResult m_287262_(LongStream streamIn, int sizeIn) {
      long[] along = streamIn.limit((long)(sizeIn + 1)).toArray();
      if (along.length != sizeIn) {
         Supplier supplier = () -> {
            return "Input is not a list of " + sizeIn + " longs";
         };
         return along.length >= sizeIn ? DataResult.error(supplier, Arrays.copyOf(along, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(along);
      }
   }

   public static DataResult m_143795_(List listIn, int sizeIn) {
      if (listIn.size() != sizeIn) {
         Supplier supplier = () -> {
            return "Input is not a list of " + sizeIn + " elements";
         };
         return listIn.size() >= sizeIn ? DataResult.error(supplier, listIn.subList(0, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(listIn);
      }
   }

   public static void m_137584_() {
      Thread thread = new Thread("Timer hack thread") {
         public void run() {
            while(true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  C_5322_.f_137446_.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(new C_5018_(f_137446_));
      thread.start();
   }

   public static void m_137563_(Path srcPathIn, Path dstPathIn, Path pathIn) throws IOException {
      Path path = srcPathIn.relativize(pathIn);
      Path path1 = dstPathIn.resolve(path);
      Files.copy(pathIn, path1);
   }

   public static String m_137483_(String nameIn, C_4824_ checkIn) {
      return (String)nameIn.toLowerCase(Locale.ROOT).chars().mapToObj((charIn) -> {
         return checkIn.m_125854_((char)charIn) ? Character.toString((char)charIn) : "_";
      }).collect(Collectors.joining());
   }

   public static C_268387_ m_269175_(Function mappingIn) {
      return new C_268387_(mappingIn);
   }

   public static Function m_143827_(final Function functionIn) {
      return new Function() {
         private final Map cache = new ConcurrentHashMap();

         public Object apply(Object p_apply_1_) {
            return this.cache.computeIfAbsent(p_apply_1_, functionIn);
         }

         public String toString() {
            String var10000 = String.valueOf(functionIn);
            return "memoize/1[function=" + var10000 + ", size=" + this.cache.size() + "]";
         }
      };
   }

   public static BiFunction m_143821_(final BiFunction functionIn) {
      return new BiFunction() {
         private final Map cache = new ConcurrentHashMap();

         public Object apply(Object p_apply_1_, Object p_apply_2_) {
            return this.cache.computeIfAbsent(Pair.of(p_apply_1_, p_apply_2_), (pairIn) -> {
               return functionIn.apply(pairIn.getFirst(), pairIn.getSecond());
            });
         }

         public String toString() {
            String var10000 = String.valueOf(functionIn);
            return "memoize/2[function=" + var10000 + ", size=" + this.cache.size() + "]";
         }
      };
   }

   public static List m_214661_(Stream streamIn, C_212974_ randomIn) {
      ObjectArrayList objectarraylist = (ObjectArrayList)streamIn.collect(ObjectArrayList.toList());
      m_214673_(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static IntArrayList m_214658_(IntStream streamIn, C_212974_ randomIn) {
      IntArrayList intarraylist = IntArrayList.wrap(streamIn.toArray());
      int i = intarraylist.size();

      for(int j = i; j > 1; --j) {
         int k = randomIn.m_188503_(j);
         intarraylist.set(j - 1, intarraylist.set(k, intarraylist.getInt(j - 1)));
      }

      return intarraylist;
   }

   public static List m_214681_(Object[] valuesIn, C_212974_ randomIn) {
      ObjectArrayList objectarraylist = new ObjectArrayList(valuesIn);
      m_214673_(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static List m_214611_(ObjectArrayList listIn, C_212974_ randomIn) {
      ObjectArrayList objectarraylist = new ObjectArrayList(listIn);
      m_214673_(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static void m_214673_(List listIn, C_212974_ randomIn) {
      int i = listIn.size();

      for(int j = i; j > 1; --j) {
         int k = randomIn.m_188503_(j);
         listIn.set(j - 1, listIn.set(k, listIn.get(j - 1)));
      }

   }

   public static CompletableFuture m_214679_(Function funcIn) {
      return (CompletableFuture)m_214652_(funcIn, CompletableFuture::isDone);
   }

   public static Object m_214652_(Function funcIn, Predicate checkIn) {
      BlockingQueue blockingqueue = new LinkedBlockingQueue();
      Objects.requireNonNull(blockingqueue);
      Object t = funcIn.apply(blockingqueue::add);

      while(!checkIn.test(t)) {
         try {
            Runnable runnable = (Runnable)blockingqueue.poll(100L, TimeUnit.MILLISECONDS);
            if (runnable != null) {
               runnable.run();
            }
         } catch (InterruptedException var5) {
            f_137446_.warn("Interrupted wait");
            break;
         }
      }

      int i = blockingqueue.size();
      if (i > 0) {
         f_137446_.warn("Tasks left in queue: {}", i);
      }

      return t;
   }

   public static ToIntFunction m_214686_(List listIn) {
      int i = listIn.size();
      if (i < 8) {
         Objects.requireNonNull(listIn);
         return listIn::indexOf;
      } else {
         Object2IntMap object2intmap = new Object2IntOpenHashMap(i);
         object2intmap.defaultReturnValue(-1);

         for(int j = 0; j < i; ++j) {
            object2intmap.put(listIn.get(j), j);
         }

         return object2intmap;
      }
   }

   public static ToIntFunction m_307438_(List listIn) {
      int i = listIn.size();
      if (i < 8) {
         ReferenceList referencelist = new ReferenceImmutableList(listIn);
         Objects.requireNonNull(referencelist);
         return referencelist::indexOf;
      } else {
         Reference2IntMap reference2intmap = new Reference2IntOpenHashMap(i);
         reference2intmap.defaultReturnValue(-1);

         for(int j = 0; j < i; ++j) {
            reference2intmap.put(listIn.get(j), j);
         }

         return reference2intmap;
      }
   }

   public static Typed m_306942_(Typed typedAIn, Type typedBIn, UnaryOperator operIn) {
      Dynamic dynamic = (Dynamic)typedAIn.write().getOrThrow();
      return m_306397_(typedBIn, (Dynamic)operIn.apply(dynamic), true);
   }

   public static Typed m_305473_(Type typeIn, Dynamic dynamicIn) {
      return m_306397_(typeIn, dynamicIn, false);
   }

   public static Typed m_306397_(Type typeIn, Dynamic dynamicIn, boolean partialIn) {
      DataResult dataresult = typeIn.readTyped(dynamicIn).map(Pair::getFirst);

      try {
         return partialIn ? (Typed)dataresult.getPartialOrThrow(IllegalStateException::new) : (Typed)dataresult.getOrThrow(IllegalStateException::new);
      } catch (IllegalStateException var7) {
         C_4883_ crashreport = C_4883_.m_127521_(var7, "Reading type");
         C_4909_ crashreportcategory = crashreport.m_127514_("Info");
         crashreportcategory.m_128159_("Data", dynamicIn);
         crashreportcategory.m_128159_("Type", typeIn);
         throw new C_5204_(crashreport);
      }
   }

   public static List m_324319_(List valuesIn, Object valueIn) {
      return ImmutableList.builderWithExpectedSize(valuesIn.size() + 1).addAll(valuesIn).add(valueIn).build();
   }

   public static List m_321242_(Object valueIn, List valuesIn) {
      return ImmutableList.builderWithExpectedSize(valuesIn.size() + 1).add(valueIn).addAll(valuesIn).build();
   }

   public static Map m_321632_(Map mapIn, Object keyIn, Object valueIn) {
      return ImmutableMap.builderWithExpectedSize(mapIn.size() + 1).putAll(mapIn).put(keyIn, valueIn).buildKeepingLast();
   }

   static {
      f_241646_ = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss", Locale.ROOT);
      f_337259_ = Set.of("http", "https");
      f_137440_ = System::nanoTime;
      f_211544_ = new Ticker() {
         public long read() {
            return C_5322_.f_137440_.getAsLong();
         }
      };
      f_137441_ = new UUID(0L, 0L);
      f_143778_ = (FileSystemProvider)FileSystemProvider.installedProviders().stream().filter((providerIn) -> {
         return providerIn.getScheme().equalsIgnoreCase("jar");
      }).findFirst().orElseThrow(() -> {
         return new IllegalStateException("No jar file system provider found");
      });
      f_183937_ = (nameIn) -> {
      };
      CAPE_EXECUTOR = m_137477_("Cape");
      INNER_CLASS_SHIFT1 = () -> {
         return 0L;
      };
      INNER_CLASS_SHIFT2 = () -> {
         return 0L;
      };
   }

   public static enum C_5330_ {
      LINUX("linux"),
      SOLARIS("solaris"),
      WINDOWS("windows") {
         protected String[] m_338887_(URI uriIn) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", uriIn.toString()};
         }
      },
      OSX("mac") {
         protected String[] m_338887_(URI uriIn) {
            return new String[]{"open", uriIn.toString()};
         }
      },
      UNKNOWN("unknown");

      private final String f_183994_;

      private C_5330_(final String nameIn) {
         this.f_183994_ = nameIn;
      }

      public void m_137648_(URI uri) {
         try {
            Process process = (Process)AccessController.doPrivileged(() -> {
               return Runtime.getRuntime().exec(this.m_338887_(uri));
            });
            process.getInputStream().close();
            process.getErrorStream().close();
            process.getOutputStream().close();
         } catch (PrivilegedActionException | IOException var3) {
            C_5322_.f_137446_.error("Couldn't open location '{}'", uri, var3);
            C_5322_.exceptionOpenUrl = var3;
         }

      }

      public void m_137644_(File fileIn) {
         this.m_137648_(fileIn.toURI());
      }

      public void m_338824_(Path pathIn) {
         this.m_137648_(pathIn.toUri());
      }

      protected String[] m_338887_(URI uriIn) {
         String s = uriIn.toString();
         if ("file".equals(uriIn.getScheme())) {
            s = s.replace("file:", "file://");
         }

         return new String[]{"xdg-open", s};
      }

      public void m_137646_(String uri) {
         try {
            this.m_137648_(new URI(uri));
         } catch (URISyntaxException | IllegalArgumentException var3) {
            C_5322_.f_137446_.error("Couldn't open uri '{}'", uri, var3);
         }

      }

      public String m_183999_() {
         return this.f_183994_;
      }

      // $FF: synthetic method
      private static C_5330_[] $values() {
         return new C_5330_[]{LINUX, SOLARIS, WINDOWS, OSX, UNKNOWN};
      }
   }
}
