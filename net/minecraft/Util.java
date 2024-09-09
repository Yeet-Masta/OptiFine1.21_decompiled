package net.minecraft;

import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
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
import net.minecraft.core.Registry;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SingleKeyCache;
import net.minecraft.util.TimeSource.NanoTimeSource;
import net.minecraft.util.datafix.DataFixers;
import net.optifine.SmartExecutorService;
import org.slf4j.Logger;

public class Util {
   static final Logger f_137446_ = LogUtils.getLogger();
   private static final int f_183935_ = 255;
   private static final int f_303362_ = 10;
   private static final String f_183936_ = "max.bg.threads";
   private static final ExecutorService f_137444_ = m_137477_("Main");
   private static final ExecutorService f_137445_ = m_137586_("IO-Worker-", false);
   private static final ExecutorService f_302521_ = m_137586_("Download-", true);
   private static final DateTimeFormatter f_241646_ = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss", Locale.ROOT);
   public static final int f_303450_ = 8;
   private static final Set<String> f_337259_ = Set.of("http", "https");
   public static final long f_291561_ = 1000000L;
   public static NanoTimeSource f_137440_ = System::nanoTime;
   public static final Ticker f_211544_ = new Ticker() {
      public long read() {
         return net.minecraft.Util.f_137440_.getAsLong();
      }
   };
   public static final UUID f_137441_ = new UUID(0L, 0L);
   public static final FileSystemProvider f_143778_ = (FileSystemProvider)FileSystemProvider.installedProviders()
      .stream()
      .filter(providerIn -> providerIn.getScheme().equalsIgnoreCase("jar"))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("No jar file system provider found"));
   private static Consumer<String> f_183937_ = nameIn -> {
   };
   private static Exception exceptionOpenUrl;
   private static final ExecutorService CAPE_EXECUTOR = m_137477_("Cape");
   private static LongSupplier INNER_CLASS_SHIFT1 = () -> 0L;
   private static LongSupplier INNER_CLASS_SHIFT2 = () -> 0L;

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> m_137448_() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T> Collector<T, ?, List<T>> m_323807_() {
      return Collectors.toCollection(Lists::newArrayList);
   }

   public static <T extends Comparable<T>> String m_137453_(net.minecraft.world.level.block.state.properties.Property<T> property, Object value) {
      return property.m_6940_((T)value);
   }

   public static String m_137492_(String type, @Nullable net.minecraft.resources.ResourceLocation id) {
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
      int i = net.minecraft.util.Mth.m_14045_(Runtime.getRuntime().availableProcessors() - 1, 1, m_183993_());
      ExecutorService executorservice;
      if (i <= 0) {
         executorservice = MoreExecutors.newDirectExecutorService();
      } else {
         AtomicInteger atomicinteger = new AtomicInteger(1);
         executorservice = new ForkJoinPool(i, poolIn -> {
            ForkJoinWorkerThread forkjoinworkerthread = new ForkJoinWorkerThread(poolIn) {
               protected void onTermination(Throwable p_onTermination_1_) {
                  if (p_onTermination_1_ != null) {
                     net.minecraft.Util.f_137446_.warn("{} died", this.getName(), p_onTermination_1_);
                  } else {
                     net.minecraft.Util.f_137446_.debug("{} shutdown", this.getName());
                  }

                  super.onTermination(p_onTermination_1_);
               }
            };
            forkjoinworkerthread.setName("Worker-" + nameIn + "-" + atomicinteger.getAndIncrement());
            if (nameIn.equals("Bootstrap")) {
               forkjoinworkerthread.setPriority(1);
            }

            return forkjoinworkerthread;
         }, net.minecraft.Util::m_137495_, true);
      }

      if (nameIn.equals("Bootstrap")) {
         executorservice = createSmartExecutor(executorservice);
      }

      return executorservice;
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
      return Executors.newCachedThreadPool(runnableIn -> {
         Thread thread = new Thread(runnableIn);
         thread.setName(nameIn + atomicinteger.getAndIncrement());
         thread.setDaemon(daemonIn);
         thread.setUncaughtExceptionHandler(net.minecraft.Util::m_137495_);
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

      if (throwableIn instanceof ReportedException reportedexception) {
         Bootstrap.m_135875_(reportedexception.m_134761_().m_127526_(ReportType.f_337619_));
         System.exit(-1);
      }

      f_137446_.error(String.format(Locale.ROOT, "Caught exception in thread %s", threadIn), throwableIn);
   }

   @Nullable
   public static Type<?> m_137456_(TypeReference typeIn, String nameIn) {
      return !SharedConstants.f_136182_ ? null : m_137551_(typeIn, nameIn);
   }

   @Nullable
   private static Type<?> m_137551_(TypeReference typeIn, String nameIn) {
      Type<?> type = null;

      try {
         type = DataFixers.m_14512_().getSchema(DataFixUtils.makeKey(SharedConstants.m_183709_().m_183476_().m_193006_())).getChoiceType(typeIn, nameIn);
      } catch (IllegalArgumentException var4) {
         f_137446_.debug("No data fixer registered for {}", nameIn);
         if (SharedConstants.f_136183_) {
            throw var4;
         }
      }

      return type;
   }

   public static Runnable m_143787_(String nameIn, Runnable taskIn) {
      return SharedConstants.f_136183_ ? () -> {
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

   public static <V> Supplier<V> m_183946_(String nameIn, Supplier<V> taskIn) {
      return SharedConstants.f_136183_ ? () -> {
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

   public static <T> String m_322642_(Registry<T> registryIn, T valueIn) {
      net.minecraft.resources.ResourceLocation resourcelocation = registryIn.m_7981_(valueIn);
      return resourcelocation == null ? "[unregistered]" : resourcelocation.toString();
   }

   public static <T> Predicate<T> m_322468_(List<? extends Predicate<T>> predicatesIn) {
      return switch (predicatesIn.size()) {
         case 0 -> objIn -> true;
         case 1 -> (Predicate)predicatesIn.get(0);
         case 2 -> ((Predicate)predicatesIn.get(0)).and((Predicate)predicatesIn.get(1));
         default -> {
            Predicate<T>[] predicate = (Predicate<T>[])predicatesIn.toArray(Predicate[]::new);
            yield valIn -> {
               for (Predicate<T> predicate1 : predicate) {
                  if (!predicate1.test(valIn)) {
                     return false;
                  }
               }

               return true;
            };
         }
      };
   }

   public static <T> Predicate<T> m_321702_(List<? extends Predicate<T>> predicatesIn) {
      return switch (predicatesIn.size()) {
         case 0 -> objIn -> false;
         case 1 -> (Predicate)predicatesIn.get(0);
         case 2 -> ((Predicate)predicatesIn.get(0)).or((Predicate)predicatesIn.get(1));
         default -> {
            Predicate<T>[] predicate = (Predicate<T>[])predicatesIn.toArray(Predicate[]::new);
            yield valIn -> {
               for (Predicate<T> predicate1 : predicate) {
                  if (predicate1.test(valIn)) {
                     return true;
                  }
               }

               return false;
            };
         }
      };
   }

   public static <T> boolean m_340468_(int widthIn, int heightIn, List<T> valuesIn) {
      if (widthIn == 1) {
         return true;
      } else {
         int i = widthIn / 2;

         for (int j = 0; j < heightIn; j++) {
            for (int k = 0; k < i; k++) {
               int l = widthIn - 1 - k;
               T t = (T)valuesIn.get(k + j * widthIn);
               T t1 = (T)valuesIn.get(l + j * widthIn);
               if (!t.equals(t1)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static net.minecraft.Util.OS m_137581_() {
      String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (s.contains("win")) {
         return net.minecraft.Util.OS.WINDOWS;
      } else if (s.contains("mac")) {
         return net.minecraft.Util.OS.OSX;
      } else if (s.contains("solaris")) {
         return net.minecraft.Util.OS.SOLARIS;
      } else if (s.contains("sunos")) {
         return net.minecraft.Util.OS.SOLARIS;
      } else if (s.contains("linux")) {
         return net.minecraft.Util.OS.LINUX;
      } else {
         return s.contains("unix") ? net.minecraft.Util.OS.LINUX : net.minecraft.Util.OS.UNKNOWN;
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

   public static Stream<String> m_137582_() {
      RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
      return runtimemxbean.getInputArguments().stream().filter(argIn -> argIn.startsWith("-X"));
   }

   public static <T> T m_137509_(List<T> listIn) {
      return (T)listIn.get(listIn.size() - 1);
   }

   public static <T> T m_137466_(Iterable<T> iterable, @Nullable T element) {
      Iterator<T> iterator = iterable.iterator();
      T t = (T)iterator.next();
      if (element != null) {
         T t1 = t;

         while (t1 != element) {
            if (iterator.hasNext()) {
               t1 = (T)iterator.next();
            }
         }

         if (iterator.hasNext()) {
            return (T)iterator.next();
         }
      }

      return t;
   }

   public static <T> T m_137554_(Iterable<T> iterable, @Nullable T current) {
      Iterator<T> iterator = iterable.iterator();
      T t = null;

      while (iterator.hasNext()) {
         T t1 = (T)iterator.next();
         if (t1 == current) {
            if (t == null) {
               t = (T)(iterator.hasNext() ? Iterators.getLast(iterator) : current);
            }
            break;
         }

         t = t1;
      }

      return t;
   }

   public static <T> T m_137537_(Supplier<T> supplier) {
      return (T)supplier.get();
   }

   public static <T> T m_137469_(T object, Consumer<? super T> consumer) {
      consumer.accept(object);
      return object;
   }

   public static <V> CompletableFuture<List<V>> m_137567_(List<? extends CompletableFuture<V>> futuresIn) {
      if (futuresIn.isEmpty()) {
         return CompletableFuture.completedFuture(List.of());
      } else if (futuresIn.size() == 1) {
         return ((CompletableFuture)futuresIn.get(0)).thenApply(List::of);
      } else {
         CompletableFuture<Void> completablefuture = CompletableFuture.allOf((CompletableFuture[])futuresIn.toArray(new CompletableFuture[0]));
         return completablefuture.thenApply(objIn -> futuresIn.stream().map(CompletableFuture::join).toList());
      }
   }

   public static <V> CompletableFuture<List<V>> m_143840_(List<? extends CompletableFuture<? extends V>> listIn) {
      CompletableFuture<List<V>> completablefuture = new CompletableFuture();
      return m_214631_(listIn, completablefuture::completeExceptionally).applyToEither(completablefuture, Function.identity());
   }

   public static <V> CompletableFuture<List<V>> m_214684_(List<? extends CompletableFuture<? extends V>> listIn) {
      CompletableFuture<List<V>> completablefuture = new CompletableFuture();
      return m_214631_(listIn, throwableIn -> {
         if (completablefuture.completeExceptionally(throwableIn)) {
            for (CompletableFuture<? extends V> completablefuture1 : listIn) {
               completablefuture1.cancel(true);
            }
         }
      }).applyToEither(completablefuture, Function.identity());
   }

   private static <V> CompletableFuture<List<V>> m_214631_(List<? extends CompletableFuture<? extends V>> listIn, Consumer<Throwable> handlerIn) {
      List<V> list = Lists.newArrayListWithCapacity(listIn.size());
      CompletableFuture<?>[] completablefuture = new CompletableFuture[listIn.size()];
      listIn.forEach(futureIn -> {
         int i = list.size();
         list.add(null);
         completablefuture[i] = futureIn.whenComplete((objIn, throwableIn) -> {
            if (throwableIn != null) {
               handlerIn.accept(throwableIn);
            } else {
               list.set(i, objIn);
            }
         });
      });
      return CompletableFuture.allOf(completablefuture).thenApply(voidIn -> list);
   }

   public static Exception getExceptionOpenUrl() {
      return exceptionOpenUrl;
   }

   public static void setExceptionOpenUrl(Exception exceptionOpenUrl) {
      net.minecraft.Util.exceptionOpenUrl = exceptionOpenUrl;
   }

   public static ExecutorService getCapeExecutor() {
      return CAPE_EXECUTOR;
   }

   public static <T> Optional<T> m_137521_(Optional<T> opt, Consumer<T> consumer, Runnable orElse) {
      if (opt.isPresent()) {
         consumer.accept(opt.get());
      } else {
         orElse.run();
      }

      return opt;
   }

   public static <T> Supplier<T> m_214655_(Supplier<T> objIn, Supplier<String> nameIn) {
      return objIn;
   }

   public static Runnable m_137474_(Runnable runnableIn, Supplier<String> supplierIn) {
      return runnableIn;
   }

   public static void m_143785_(String logTextIn) {
      f_137446_.error(logTextIn);
      if (SharedConstants.f_136183_) {
         m_183984_(logTextIn);
      }
   }

   public static void m_200890_(String textIn, Throwable throwableIn) {
      f_137446_.error(textIn, throwableIn);
      if (SharedConstants.f_136183_) {
         m_183984_(textIn);
      }
   }

   public static <T extends Throwable> T m_137570_(T throwableIn) {
      if (SharedConstants.f_136183_) {
         f_137446_.error("Trying to throw a fatal exception, pausing in IDE", throwableIn);
         m_183984_(throwableIn.getMessage());
      }

      return throwableIn;
   }

   public static void m_183969_(Consumer<String> pauserIn) {
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

   public static <T> T m_214670_(T[] valuesIn, RandomSource randomIn) {
      return valuesIn[randomIn.m_188503_(valuesIn.length)];
   }

   public static int m_214667_(int[] valuesIn, RandomSource randomIn) {
      return valuesIn[randomIn.m_188503_(valuesIn.length)];
   }

   public static <T> T m_214621_(List<T> listIn, RandomSource randomIn) {
      return (T)listIn.get(randomIn.m_188503_(listIn.size()));
   }

   public static <T> Optional<T> m_214676_(List<T> listIn, RandomSource randomIn) {
      return listIn.isEmpty() ? Optional.empty() : Optional.of(m_214621_(listIn, randomIn));
   }

   private static BooleanSupplier m_137502_(final Path pathFrom, final Path pathTo) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.move(pathFrom, pathTo);
               return true;
            } catch (IOException var2) {
               net.minecraft.Util.f_137446_.error("Failed to rename", var2);
               return false;
            }
         }

         public String toString() {
            return "rename " + pathFrom + " to " + pathTo;
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
               net.minecraft.Util.f_137446_.warn("Failed to delete", var2);
               return false;
            }
         }

         public String toString() {
            return "delete old " + pathIn;
         }
      };
   }

   private static BooleanSupplier m_137561_(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return !Files.exists(pathIn, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + pathIn + " is deleted";
         }
      };
   }

   private static BooleanSupplier m_137572_(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return Files.isRegularFile(pathIn, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + pathIn + " is present";
         }
      };
   }

   private static boolean m_137548_(BooleanSupplier... listIn) {
      for (BooleanSupplier booleansupplier : listIn) {
         if (!booleansupplier.getAsBoolean()) {
            f_137446_.warn("Failed to execute {}", booleansupplier);
            return false;
         }
      }

      return true;
   }

   private static boolean m_137449_(int countIn, String nameIn, BooleanSupplier... listIn) {
      for (int i = 0; i < countIn; i++) {
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
      if (Files.exists(fileFrom, new LinkOption[0])
         && !m_137449_(10, "create backup " + fileBackup, m_137500_(fileBackup), m_137502_(fileFrom, fileBackup), m_137572_(fileBackup))) {
         return false;
      } else if (!m_137449_(10, "remove old " + fileFrom, m_137500_(fileFrom), m_137561_(fileFrom))) {
         return false;
      } else if (!m_137449_(10, "replace " + fileFrom + " with " + fileTo, m_137502_(fileTo, fileFrom), m_137572_(fileFrom)) && !restoreIn) {
         m_137449_(10, "restore " + fileFrom + " from " + fileBackup, m_137502_(fileBackup, fileFrom), m_137572_(fileFrom));
         return false;
      } else {
         return true;
      }
   }

   public static int m_137479_(String textIn, int posIn, int offsetIn) {
      int i = textIn.length();
      if (offsetIn >= 0) {
         for (int j = 0; posIn < i && j < offsetIn; j++) {
            if (Character.isHighSurrogate(textIn.charAt(posIn++)) && posIn < i && Character.isLowSurrogate(textIn.charAt(posIn))) {
               posIn++;
            }
         }
      } else {
         for (int k = offsetIn; posIn > 0 && k < 0; k++) {
            posIn--;
            if (Character.isLowSurrogate(textIn.charAt(posIn)) && posIn > 0 && Character.isHighSurrogate(textIn.charAt(posIn - 1))) {
               posIn--;
            }
         }
      }

      return posIn;
   }

   public static Consumer<String> m_137489_(String textIn, Consumer<String> consumerIn) {
      return strIn -> consumerIn.accept(textIn + strIn);
   }

   public static DataResult<int[]> m_137539_(IntStream streamIn, int sizeIn) {
      int[] aint = streamIn.limit((long)(sizeIn + 1)).toArray();
      if (aint.length != sizeIn) {
         Supplier<String> supplier = () -> "Input is not a list of " + sizeIn + " ints";
         return aint.length >= sizeIn ? DataResult.error(supplier, Arrays.copyOf(aint, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(aint);
      }
   }

   public static DataResult<long[]> m_287262_(LongStream streamIn, int sizeIn) {
      long[] along = streamIn.limit((long)(sizeIn + 1)).toArray();
      if (along.length != sizeIn) {
         Supplier<String> supplier = () -> "Input is not a list of " + sizeIn + " longs";
         return along.length >= sizeIn ? DataResult.error(supplier, Arrays.copyOf(along, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(along);
      }
   }

   public static <T> DataResult<List<T>> m_143795_(List<T> listIn, int sizeIn) {
      if (listIn.size() != sizeIn) {
         Supplier<String> supplier = () -> "Input is not a list of " + sizeIn + " elements";
         return listIn.size() >= sizeIn ? DataResult.error(supplier, listIn.subList(0, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(listIn);
      }
   }

   public static void m_137584_() {
      Thread thread = new Thread("Timer hack thread") {
         public void run() {
            while (true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  net.minecraft.Util.f_137446_.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_137446_));
      thread.start();
   }

   public static void m_137563_(Path srcPathIn, Path dstPathIn, Path pathIn) throws IOException {
      Path path = srcPathIn.relativize(pathIn);
      Path path1 = dstPathIn.resolve(path);
      Files.copy(pathIn, path1);
   }

   public static String m_137483_(String nameIn, CharPredicate checkIn) {
      return (String)nameIn.toLowerCase(Locale.ROOT)
         .chars()
         .mapToObj(charIn -> checkIn.m_125854_((char)charIn) ? Character.toString((char)charIn) : "_")
         .collect(Collectors.joining());
   }

   public static <K, V> SingleKeyCache<K, V> m_269175_(Function<K, V> mappingIn) {
      return new SingleKeyCache(mappingIn);
   }

   public static <T, R> Function<T, R> m_143827_(final Function<T, R> functionIn) {
      return new Function<T, R>() {
         private final Map<T, R> cache = new ConcurrentHashMap();

         public R apply(T p_apply_1_) {
            return (R)this.cache.computeIfAbsent(p_apply_1_, functionIn);
         }

         public String toString() {
            return "memoize/1[function=" + functionIn + ", size=" + this.cache.size() + "]";
         }
      };
   }

   public static <T, U, R> BiFunction<T, U, R> m_143821_(final BiFunction<T, U, R> functionIn) {
      return new BiFunction<T, U, R>() {
         private final Map<Pair<T, U>, R> cache = new ConcurrentHashMap();

         public R apply(T p_apply_1_, U p_apply_2_) {
            return (R)this.cache.computeIfAbsent(Pair.of(p_apply_1_, p_apply_2_), pairIn -> functionIn.apply(pairIn.getFirst(), pairIn.getSecond()));
         }

         public String toString() {
            return "memoize/2[function=" + functionIn + ", size=" + this.cache.size() + "]";
         }
      };
   }

   public static <T> List<T> m_214661_(Stream<T> streamIn, RandomSource randomIn) {
      ObjectArrayList<T> objectarraylist = (ObjectArrayList<T>)streamIn.collect(ObjectArrayList.toList());
      m_214673_(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static IntArrayList m_214658_(IntStream streamIn, RandomSource randomIn) {
      IntArrayList intarraylist = IntArrayList.wrap(streamIn.toArray());
      int i = intarraylist.size();

      for (int j = i; j > 1; j--) {
         int k = randomIn.m_188503_(j);
         intarraylist.set(j - 1, intarraylist.set(k, intarraylist.getInt(j - 1)));
      }

      return intarraylist;
   }

   public static <T> List<T> m_214681_(T[] valuesIn, RandomSource randomIn) {
      ObjectArrayList<T> objectarraylist = new ObjectArrayList(valuesIn);
      m_214673_(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static <T> List<T> m_214611_(ObjectArrayList<T> listIn, RandomSource randomIn) {
      ObjectArrayList<T> objectarraylist = new ObjectArrayList(listIn);
      m_214673_(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static <T> void m_214673_(List<T> listIn, RandomSource randomIn) {
      int i = listIn.size();

      for (int j = i; j > 1; j--) {
         int k = randomIn.m_188503_(j);
         listIn.set(j - 1, listIn.set(k, listIn.get(j - 1)));
      }
   }

   public static <T> CompletableFuture<T> m_214679_(Function<Executor, CompletableFuture<T>> funcIn) {
      return m_214652_(funcIn, CompletableFuture::isDone);
   }

   public static <T> T m_214652_(Function<Executor, T> funcIn, Predicate<T> checkIn) {
      BlockingQueue<Runnable> blockingqueue = new LinkedBlockingQueue();
      T t = (T)funcIn.apply(blockingqueue::add);

      while (!checkIn.test(t)) {
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

   public static <T> ToIntFunction<T> m_214686_(List<T> listIn) {
      int i = listIn.size();
      if (i < 8) {
         return listIn::indexOf;
      } else {
         Object2IntMap<T> object2intmap = new Object2IntOpenHashMap(i);
         object2intmap.defaultReturnValue(-1);

         for (int j = 0; j < i; j++) {
            object2intmap.put(listIn.get(j), j);
         }

         return object2intmap;
      }
   }

   public static <T> ToIntFunction<T> m_307438_(List<T> listIn) {
      int i = listIn.size();
      if (i < 8) {
         ReferenceList<T> referencelist = new ReferenceImmutableList(listIn);
         return referencelist::indexOf;
      } else {
         Reference2IntMap<T> reference2intmap = new Reference2IntOpenHashMap(i);
         reference2intmap.defaultReturnValue(-1);

         for (int j = 0; j < i; j++) {
            reference2intmap.put(listIn.get(j), j);
         }

         return reference2intmap;
      }
   }

   public static <A, B> Typed<B> m_306942_(Typed<A> typedAIn, Type<B> typedBIn, UnaryOperator<Dynamic<?>> operIn) {
      Dynamic<?> dynamic = (Dynamic<?>)typedAIn.write().getOrThrow();
      return m_306397_(typedBIn, (Dynamic<?>)operIn.apply(dynamic), true);
   }

   public static <T> Typed<T> m_305473_(Type<T> typeIn, Dynamic<?> dynamicIn) {
      return m_306397_(typeIn, dynamicIn, false);
   }

   public static <T> Typed<T> m_306397_(Type<T> typeIn, Dynamic<?> dynamicIn, boolean partialIn) {
      DataResult<Typed<T>> dataresult = typeIn.readTyped(dynamicIn).map(Pair::getFirst);

      try {
         return partialIn ? (Typed)dataresult.getPartialOrThrow(IllegalStateException::new) : (Typed)dataresult.getOrThrow(IllegalStateException::new);
      } catch (IllegalStateException var7) {
         net.minecraft.CrashReport crashreport = net.minecraft.CrashReport.m_127521_(var7, "Reading type");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Info");
         crashreportcategory.m_128159_("Data", dynamicIn);
         crashreportcategory.m_128159_("Type", typeIn);
         throw new ReportedException(crashreport);
      }
   }

   public static <T> List<T> m_324319_(List<T> valuesIn, T valueIn) {
      return ImmutableList.builderWithExpectedSize(valuesIn.size() + 1).addAll(valuesIn).add(valueIn).build();
   }

   public static <T> List<T> m_321242_(T valueIn, List<T> valuesIn) {
      return ImmutableList.builderWithExpectedSize(valuesIn.size() + 1).add(valueIn).addAll(valuesIn).build();
   }

   public static <K, V> Map<K, V> m_321632_(Map<K, V> mapIn, K keyIn, V valueIn) {
      return ImmutableMap.builderWithExpectedSize(mapIn.size() + 1).putAll(mapIn).put(keyIn, valueIn).buildKeepingLast();
   }

   public static enum OS {
      LINUX("linux"),
      SOLARIS("solaris"),
      WINDOWS("windows") {
         @Override
         protected String[] m_338887_(URI uriIn) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", uriIn.toString()};
         }
      },
      OSX("mac") {
         @Override
         protected String[] m_338887_(URI uriIn) {
            return new String[]{"open", uriIn.toString()};
         }
      },
      UNKNOWN("unknown");

      private final String f_183994_;

      private OS(final String nameIn) {
         this.f_183994_ = nameIn;
      }

      public void m_137648_(URI uri) {
         try {
            Process process = (Process)AccessController.doPrivileged(() -> Runtime.getRuntime().exec(this.m_338887_(uri)));
            process.getInputStream().close();
            process.getErrorStream().close();
            process.getOutputStream().close();
         } catch (PrivilegedActionException | IOException var3) {
            net.minecraft.Util.f_137446_.error("Couldn't open location '{}'", uri, var3);
            net.minecraft.Util.exceptionOpenUrl = var3;
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
            net.minecraft.Util.f_137446_.error("Couldn't open uri '{}'", uri, var3);
         }
      }

      public String m_183999_() {
         return this.f_183994_;
      }
   }
}
