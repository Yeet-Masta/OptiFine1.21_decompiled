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
import net.minecraft.src.C_209_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_268387_;
import net.minecraft.src.C_336586_;
import net.minecraft.src.C_4705_;
import net.minecraft.src.C_4824_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5018_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_5267_;
import net.minecraft.src.C_5285_;
import net.minecraft.src.C_238509_.C_238510_;
import net.optifine.SmartExecutorService;
import org.slf4j.Logger;

public class Util {
   static final Logger g = LogUtils.getLogger();
   private static final int h = 255;
   private static final int i = 10;
   private static final String j = "max.bg.threads";
   private static final ExecutorService k = c("Main");
   private static final ExecutorService l = a("IO-Worker-", false);
   private static final ExecutorService m = a("Download-", true);
   private static final DateTimeFormatter n = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss", Locale.ROOT);
   public static final int a = 8;
   private static final Set<String> o = Set.of("http", "https");
   public static final long b = 1000000L;
   public static C_238510_ c = System::nanoTime;
   public static final Ticker d = new Ticker() {
      public long read() {
         return Util.c.getAsLong();
      }
   };
   public static final UUID e = new UUID(0L, 0L);
   public static final FileSystemProvider f = (FileSystemProvider)FileSystemProvider.installedProviders()
      .stream()
      .filter(providerIn -> providerIn.getScheme().equalsIgnoreCase("jar"))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("No jar file system provider found"));
   private static Consumer<String> p = nameIn -> {
   };
   private static Exception exceptionOpenUrl;
   private static final ExecutorService CAPE_EXECUTOR = c("Cape");
   private static LongSupplier INNER_CLASS_SHIFT1 = () -> 0L;
   private static LongSupplier INNER_CLASS_SHIFT2 = () -> 0L;

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> a() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T> Collector<T, ?, List<T>> b() {
      return Collectors.toCollection(Lists::newArrayList);
   }

   public static <T extends Comparable<T>> String a(Property<T> property, Object value) {
      return property.a((T)value);
   }

   public static String a(String type, @Nullable ResourceLocation id) {
      return id == null ? type + ".unregistered_sadface" : type + "." + id.b() + "." + id.a().replace('/', '.');
   }

   public static long c() {
      return d() / 1000000L;
   }

   public static long d() {
      return c.getAsLong();
   }

   public static long e() {
      return Instant.now().toEpochMilli();
   }

   public static String f() {
      return n.format(ZonedDateTime.now());
   }

   private static ExecutorService c(String nameIn) {
      int i = Mth.a(Runtime.getRuntime().availableProcessors() - 1, 1, n());
      ExecutorService executorservice;
      if (i <= 0) {
         executorservice = MoreExecutors.newDirectExecutorService();
      } else {
         AtomicInteger atomicinteger = new AtomicInteger(1);
         executorservice = new ForkJoinPool(i, poolIn -> {
            ForkJoinWorkerThread forkjoinworkerthread = new ForkJoinWorkerThread(poolIn) {
               protected void onTermination(Throwable p_onTermination_1_) {
                  if (p_onTermination_1_ != null) {
                     Util.g.warn("{} died", this.getName(), p_onTermination_1_);
                  } else {
                     Util.g.debug("{} shutdown", this.getName());
                  }

                  super.onTermination(p_onTermination_1_);
               }
            };
            forkjoinworkerthread.setName("Worker-" + nameIn + "-" + atomicinteger.getAndIncrement());
            if (nameIn.equals("Bootstrap")) {
               forkjoinworkerthread.setPriority(1);
            }

            return forkjoinworkerthread;
         }, Util::a, true);
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

   private static int n() {
      String s = System.getProperty("max.bg.threads");
      if (s != null) {
         try {
            int i = Integer.parseInt(s);
            if (i >= 1 && i <= 255) {
               return i;
            }

            g.error("Wrong {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{"max.bg.threads", s, 255});
         } catch (NumberFormatException var2) {
            g.error("Could not parse {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{"max.bg.threads", s, 255});
         }
      }

      return 255;
   }

   public static ExecutorService g() {
      return k;
   }

   public static ExecutorService h() {
      return l;
   }

   public static ExecutorService i() {
      return m;
   }

   public static void j() {
      a(k);
      a(l);
      a(CAPE_EXECUTOR);
   }

   private static void a(ExecutorService serviceIn) {
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

   private static ExecutorService a(String nameIn, boolean daemonIn) {
      AtomicInteger atomicinteger = new AtomicInteger(1);
      return Executors.newCachedThreadPool(runnableIn -> {
         Thread thread = new Thread(runnableIn);
         thread.setName(nameIn + atomicinteger.getAndIncrement());
         thread.setDaemon(daemonIn);
         thread.setUncaughtExceptionHandler(Util::a);
         return thread;
      });
   }

   public static void a(Throwable throwableIn) {
      throw throwableIn instanceof RuntimeException ? (RuntimeException)throwableIn : new RuntimeException(throwableIn);
   }

   private static void a(Thread threadIn, Throwable throwableIn) {
      b(throwableIn);
      if (throwableIn instanceof CompletionException) {
         throwableIn = throwableIn.getCause();
      }

      if (throwableIn instanceof C_5204_ reportedexception) {
         C_5267_.m_135875_(reportedexception.a().a(C_336586_.f_337619_));
         System.exit(-1);
      }

      g.error(String.format(Locale.ROOT, "Caught exception in thread %s", threadIn), throwableIn);
   }

   @Nullable
   public static Type<?> a(TypeReference typeIn, String nameIn) {
      return !C_5285_.f_136182_ ? null : b(typeIn, nameIn);
   }

   @Nullable
   private static Type<?> b(TypeReference typeIn, String nameIn) {
      Type<?> type = null;

      try {
         type = C_209_.m_14512_().getSchema(DataFixUtils.makeKey(C_5285_.m_183709_().m_183476_().m_193006_())).getChoiceType(typeIn, nameIn);
      } catch (IllegalArgumentException var4) {
         g.debug("No data fixer registered for {}", nameIn);
         if (C_5285_.f_136183_) {
            throw var4;
         }
      }

      return type;
   }

   public static Runnable a(String nameIn, Runnable taskIn) {
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

   public static <V> Supplier<V> a(String nameIn, Supplier<V> taskIn) {
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

   public static <T> String a(C_4705_<T> registryIn, T valueIn) {
      ResourceLocation resourcelocation = registryIn.b(valueIn);
      return resourcelocation == null ? "[unregistered]" : resourcelocation.toString();
   }

   public static <T> Predicate<T> a(List<? extends Predicate<T>> predicatesIn) {
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

   public static <T> Predicate<T> b(List<? extends Predicate<T>> predicatesIn) {
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

   public static <T> boolean a(int widthIn, int heightIn, List<T> valuesIn) {
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

   public static Util.a k() {
      String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (s.contains("win")) {
         return Util.a.c;
      } else if (s.contains("mac")) {
         return Util.a.d;
      } else if (s.contains("solaris")) {
         return Util.a.b;
      } else if (s.contains("sunos")) {
         return Util.a.b;
      } else if (s.contains("linux")) {
         return Util.a.a;
      } else {
         return s.contains("unix") ? Util.a.a : Util.a.e;
      }
   }

   public static URI a(String strIn) throws URISyntaxException {
      URI uri = new URI(strIn);
      String s = uri.getScheme();
      if (s == null) {
         throw new URISyntaxException(strIn, "Missing protocol in URI: " + strIn);
      } else {
         String s1 = s.toLowerCase(Locale.ROOT);
         if (!o.contains(s1)) {
            throw new URISyntaxException(strIn, "Unsupported protocol in URI: " + strIn);
         } else {
            return uri;
         }
      }
   }

   public static Stream<String> l() {
      RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
      return runtimemxbean.getInputArguments().stream().filter(argIn -> argIn.startsWith("-X"));
   }

   public static <T> T c(List<T> listIn) {
      return (T)listIn.get(listIn.size() - 1);
   }

   public static <T> T a(Iterable<T> iterable, @Nullable T element) {
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

   public static <T> T b(Iterable<T> iterable, @Nullable T current) {
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

   public static <T> T a(Supplier<T> supplier) {
      return (T)supplier.get();
   }

   public static <T> T a(T object, Consumer<? super T> consumer) {
      consumer.accept(object);
      return object;
   }

   public static <V> CompletableFuture<List<V>> d(List<? extends CompletableFuture<V>> futuresIn) {
      if (futuresIn.isEmpty()) {
         return CompletableFuture.completedFuture(List.of());
      } else if (futuresIn.size() == 1) {
         return ((CompletableFuture)futuresIn.get(0)).thenApply(List::of);
      } else {
         CompletableFuture<Void> completablefuture = CompletableFuture.allOf((CompletableFuture[])futuresIn.toArray(new CompletableFuture[0]));
         return completablefuture.thenApply(objIn -> futuresIn.stream().map(CompletableFuture::join).toList());
      }
   }

   public static <V> CompletableFuture<List<V>> e(List<? extends CompletableFuture<? extends V>> listIn) {
      CompletableFuture<List<V>> completablefuture = new CompletableFuture();
      return a(listIn, completablefuture::completeExceptionally).applyToEither(completablefuture, Function.identity());
   }

   public static <V> CompletableFuture<List<V>> f(List<? extends CompletableFuture<? extends V>> listIn) {
      CompletableFuture<List<V>> completablefuture = new CompletableFuture();
      return a(listIn, (Consumer<Throwable>)(throwableIn -> {
         if (completablefuture.completeExceptionally(throwableIn)) {
            for (CompletableFuture<? extends V> completablefuture1 : listIn) {
               completablefuture1.cancel(true);
            }
         }
      })).applyToEither(completablefuture, Function.identity());
   }

   private static <V> CompletableFuture<List<V>> a(List<? extends CompletableFuture<? extends V>> listIn, Consumer<Throwable> handlerIn) {
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
      Util.exceptionOpenUrl = exceptionOpenUrl;
   }

   public static ExecutorService getCapeExecutor() {
      return CAPE_EXECUTOR;
   }

   public static <T> Optional<T> a(Optional<T> opt, Consumer<T> consumer, Runnable orElse) {
      if (opt.isPresent()) {
         consumer.accept(opt.get());
      } else {
         orElse.run();
      }

      return opt;
   }

   public static <T> Supplier<T> a(Supplier<T> objIn, Supplier<String> nameIn) {
      return objIn;
   }

   public static Runnable a(Runnable runnableIn, Supplier<String> supplierIn) {
      return runnableIn;
   }

   public static void b(String logTextIn) {
      g.error(logTextIn);
      if (C_5285_.f_136183_) {
         d(logTextIn);
      }
   }

   public static void a(String textIn, Throwable throwableIn) {
      g.error(textIn, throwableIn);
      if (C_5285_.f_136183_) {
         d(textIn);
      }
   }

   public static <T extends Throwable> T b(T throwableIn) {
      if (C_5285_.f_136183_) {
         g.error("Trying to throw a fatal exception, pausing in IDE", throwableIn);
         d(throwableIn.getMessage());
      }

      return throwableIn;
   }

   public static void a(Consumer<String> pauserIn) {
      p = pauserIn;
   }

   private static void d(String nameIn) {
      Instant instant = Instant.now();
      g.warn("Did you remember to set a breakpoint here?");
      boolean flag = Duration.between(instant, Instant.now()).toMillis() > 500L;
      if (!flag) {
         p.accept(nameIn);
      }
   }

   public static String c(Throwable throwableIn) {
      if (throwableIn.getCause() != null) {
         return c(throwableIn.getCause());
      } else {
         return throwableIn.getMessage() != null ? throwableIn.getMessage() : throwableIn.toString();
      }
   }

   public static <T> T a(T[] valuesIn, C_212974_ randomIn) {
      return valuesIn[randomIn.m_188503_(valuesIn.length)];
   }

   public static int a(int[] valuesIn, C_212974_ randomIn) {
      return valuesIn[randomIn.m_188503_(valuesIn.length)];
   }

   public static <T> T a(List<T> listIn, C_212974_ randomIn) {
      return (T)listIn.get(randomIn.m_188503_(listIn.size()));
   }

   public static <T> Optional<T> b(List<T> listIn, C_212974_ randomIn) {
      return listIn.isEmpty() ? Optional.empty() : Optional.of(a(listIn, randomIn));
   }

   private static BooleanSupplier a(final Path pathFrom, final Path pathTo) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.move(pathFrom, pathTo);
               return true;
            } catch (IOException var2) {
               Util.g.error("Failed to rename", var2);
               return false;
            }
         }

         public String toString() {
            return "rename " + pathFrom + " to " + pathTo;
         }
      };
   }

   private static BooleanSupplier a(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.deleteIfExists(pathIn);
               return true;
            } catch (IOException var2) {
               Util.g.warn("Failed to delete", var2);
               return false;
            }
         }

         public String toString() {
            return "delete old " + pathIn;
         }
      };
   }

   private static BooleanSupplier b(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return !Files.exists(pathIn, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + pathIn + " is deleted";
         }
      };
   }

   private static BooleanSupplier c(final Path pathIn) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return Files.isRegularFile(pathIn, new LinkOption[0]);
         }

         public String toString() {
            return "verify that " + pathIn + " is present";
         }
      };
   }

   private static boolean a(BooleanSupplier... listIn) {
      for (BooleanSupplier booleansupplier : listIn) {
         if (!booleansupplier.getAsBoolean()) {
            g.warn("Failed to execute {}", booleansupplier);
            return false;
         }
      }

      return true;
   }

   private static boolean a(int countIn, String nameIn, BooleanSupplier... listIn) {
      for (int i = 0; i < countIn; i++) {
         if (a(listIn)) {
            return true;
         }

         g.error("Failed to {}, retrying {}/{}", new Object[]{nameIn, i, countIn});
      }

      g.error("Failed to {}, aborting, progress might be lost", nameIn);
      return false;
   }

   public static void a(Path pathFrom, Path pathTo, Path pathBackup) {
      a(pathFrom, pathTo, pathBackup, false);
   }

   public static boolean a(Path fileFrom, Path fileTo, Path fileBackup, boolean restoreIn) {
      if (Files.exists(fileFrom, new LinkOption[0]) && !a(10, "create backup " + fileBackup, a(fileBackup), a(fileFrom, fileBackup), c(fileBackup))) {
         return false;
      } else if (!a(10, "remove old " + fileFrom, a(fileFrom), b(fileFrom))) {
         return false;
      } else if (!a(10, "replace " + fileFrom + " with " + fileTo, a(fileTo, fileFrom), c(fileFrom)) && !restoreIn) {
         a(10, "restore " + fileFrom + " from " + fileBackup, a(fileBackup, fileFrom), c(fileFrom));
         return false;
      } else {
         return true;
      }
   }

   public static int a(String textIn, int posIn, int offsetIn) {
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

   public static Consumer<String> a(String textIn, Consumer<String> consumerIn) {
      return strIn -> consumerIn.accept(textIn + strIn);
   }

   public static DataResult<int[]> a(IntStream streamIn, int sizeIn) {
      int[] aint = streamIn.limit((long)(sizeIn + 1)).toArray();
      if (aint.length != sizeIn) {
         Supplier<String> supplier = () -> "Input is not a list of " + sizeIn + " ints";
         return aint.length >= sizeIn ? DataResult.error(supplier, Arrays.copyOf(aint, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(aint);
      }
   }

   public static DataResult<long[]> a(LongStream streamIn, int sizeIn) {
      long[] along = streamIn.limit((long)(sizeIn + 1)).toArray();
      if (along.length != sizeIn) {
         Supplier<String> supplier = () -> "Input is not a list of " + sizeIn + " longs";
         return along.length >= sizeIn ? DataResult.error(supplier, Arrays.copyOf(along, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(along);
      }
   }

   public static <T> DataResult<List<T>> a(List<T> listIn, int sizeIn) {
      if (listIn.size() != sizeIn) {
         Supplier<String> supplier = () -> "Input is not a list of " + sizeIn + " elements";
         return listIn.size() >= sizeIn ? DataResult.error(supplier, listIn.subList(0, sizeIn)) : DataResult.error(supplier);
      } else {
         return DataResult.success(listIn);
      }
   }

   public static void m() {
      Thread thread = new Thread("Timer hack thread") {
         public void run() {
            while (true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  Util.g.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(new C_5018_(g));
      thread.start();
   }

   public static void b(Path srcPathIn, Path dstPathIn, Path pathIn) throws IOException {
      Path path = srcPathIn.relativize(pathIn);
      Path path1 = dstPathIn.resolve(path);
      Files.copy(pathIn, path1);
   }

   public static String a(String nameIn, C_4824_ checkIn) {
      return (String)nameIn.toLowerCase(Locale.ROOT)
         .chars()
         .mapToObj(charIn -> checkIn.m_125854_((char)charIn) ? Character.toString((char)charIn) : "_")
         .collect(Collectors.joining());
   }

   public static <K, V> C_268387_<K, V> a(Function<K, V> mappingIn) {
      return new C_268387_(mappingIn);
   }

   public static <T, R> Function<T, R> b(final Function<T, R> functionIn) {
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

   public static <T, U, R> BiFunction<T, U, R> a(final BiFunction<T, U, R> functionIn) {
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

   public static <T> List<T> a(Stream<T> streamIn, C_212974_ randomIn) {
      ObjectArrayList<T> objectarraylist = (ObjectArrayList<T>)streamIn.collect(ObjectArrayList.toList());
      c(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static IntArrayList a(IntStream streamIn, C_212974_ randomIn) {
      IntArrayList intarraylist = IntArrayList.wrap(streamIn.toArray());
      int i = intarraylist.size();

      for (int j = i; j > 1; j--) {
         int k = randomIn.m_188503_(j);
         intarraylist.set(j - 1, intarraylist.set(k, intarraylist.getInt(j - 1)));
      }

      return intarraylist;
   }

   public static <T> List<T> b(T[] valuesIn, C_212974_ randomIn) {
      ObjectArrayList<T> objectarraylist = new ObjectArrayList(valuesIn);
      c(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static <T> List<T> a(ObjectArrayList<T> listIn, C_212974_ randomIn) {
      ObjectArrayList<T> objectarraylist = new ObjectArrayList(listIn);
      c(objectarraylist, randomIn);
      return objectarraylist;
   }

   public static <T> void c(List<T> listIn, C_212974_ randomIn) {
      int i = listIn.size();

      for (int j = i; j > 1; j--) {
         int k = randomIn.m_188503_(j);
         listIn.set(j - 1, listIn.set(k, listIn.get(j - 1)));
      }
   }

   public static <T> CompletableFuture<T> c(Function<Executor, CompletableFuture<T>> funcIn) {
      return a(funcIn, CompletableFuture::isDone);
   }

   public static <T> T a(Function<Executor, T> funcIn, Predicate<T> checkIn) {
      BlockingQueue<Runnable> blockingqueue = new LinkedBlockingQueue();
      T t = (T)funcIn.apply(blockingqueue::add);

      while (!checkIn.test(t)) {
         try {
            Runnable runnable = (Runnable)blockingqueue.poll(100L, TimeUnit.MILLISECONDS);
            if (runnable != null) {
               runnable.run();
            }
         } catch (InterruptedException var5) {
            g.warn("Interrupted wait");
            break;
         }
      }

      int i = blockingqueue.size();
      if (i > 0) {
         g.warn("Tasks left in queue: {}", i);
      }

      return t;
   }

   public static <T> ToIntFunction<T> g(List<T> listIn) {
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

   public static <T> ToIntFunction<T> h(List<T> listIn) {
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

   public static <A, B> Typed<B> a(Typed<A> typedAIn, Type<B> typedBIn, UnaryOperator<Dynamic<?>> operIn) {
      Dynamic<?> dynamic = (Dynamic<?>)typedAIn.write().getOrThrow();
      return a(typedBIn, (Dynamic<?>)operIn.apply(dynamic), true);
   }

   public static <T> Typed<T> a(Type<T> typeIn, Dynamic<?> dynamicIn) {
      return a(typeIn, dynamicIn, false);
   }

   public static <T> Typed<T> a(Type<T> typeIn, Dynamic<?> dynamicIn, boolean partialIn) {
      DataResult<Typed<T>> dataresult = typeIn.readTyped(dynamicIn).map(Pair::getFirst);

      try {
         return partialIn ? (Typed)dataresult.getPartialOrThrow(IllegalStateException::new) : (Typed)dataresult.getOrThrow(IllegalStateException::new);
      } catch (IllegalStateException var7) {
         CrashReport crashreport = CrashReport.a(var7, "Reading type");
         C_4909_ crashreportcategory = crashreport.a("Info");
         crashreportcategory.m_128159_("Data", dynamicIn);
         crashreportcategory.m_128159_("Type", typeIn);
         throw new C_5204_(crashreport);
      }
   }

   public static <T> List<T> a(List<T> valuesIn, T valueIn) {
      return ImmutableList.builderWithExpectedSize(valuesIn.size() + 1).addAll(valuesIn).add(valueIn).build();
   }

   public static <T> List<T> a(T valueIn, List<T> valuesIn) {
      return ImmutableList.builderWithExpectedSize(valuesIn.size() + 1).add(valueIn).addAll(valuesIn).build();
   }

   public static <K, V> Map<K, V> a(Map<K, V> mapIn, K keyIn, V valueIn) {
      return ImmutableMap.builderWithExpectedSize(mapIn.size() + 1).putAll(mapIn).put(keyIn, valueIn).buildKeepingLast();
   }

   public static enum a {
      a("linux"),
      b("solaris"),
      c("windows") {
         @Override
         protected String[] b(URI uriIn) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", uriIn.toString()};
         }
      },
      d("mac") {
         @Override
         protected String[] b(URI uriIn) {
            return new String[]{"open", uriIn.toString()};
         }
      },
      e("unknown");

      private final String f;

      private a(final String nameIn) {
         this.f = nameIn;
      }

      public void a(URI uri) {
         try {
            Process process = (Process)AccessController.doPrivileged(() -> Runtime.getRuntime().exec(this.b(uri)));
            process.getInputStream().close();
            process.getErrorStream().close();
            process.getOutputStream().close();
         } catch (PrivilegedActionException | IOException var3) {
            Util.g.error("Couldn't open location '{}'", uri, var3);
            Util.exceptionOpenUrl = var3;
         }
      }

      public void a(File fileIn) {
         this.a(fileIn.toURI());
      }

      public void a(Path pathIn) {
         this.a(pathIn.toUri());
      }

      protected String[] b(URI uriIn) {
         String s = uriIn.toString();
         if ("file".equals(uriIn.getScheme())) {
            s = s.replace("file:", "file://");
         }

         return new String[]{"xdg-open", s};
      }

      public void a(String uri) {
         try {
            this.a(new URI(uri));
         } catch (URISyntaxException | IllegalArgumentException var3) {
            Util.g.error("Couldn't open uri '{}'", uri, var3);
         }
      }

      public String a() {
         return this.f;
      }
   }
}
