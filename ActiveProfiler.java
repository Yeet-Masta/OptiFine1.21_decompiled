import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_141011_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_434_;
import net.minecraft.src.C_436_;
import net.minecraft.src.C_440_;
import net.minecraft.src.C_441_;
import net.minecraft.src.C_444_;
import net.optifine.Config;
import net.optifine.Lagometer;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

public class ActiveProfiler implements C_440_ {
   private static final long a = Duration.ofMillis(100L).toNanos();
   private static final Logger c = LogUtils.getLogger();
   private final List<String> d = Lists.newArrayList();
   private final LongList e = new LongArrayList();
   private final Map<String, ActiveProfiler.a> f = Maps.newHashMap();
   private final IntSupplier g;
   private final LongSupplier h;
   private final long i;
   private final int j;
   private String k = "";
   private boolean l;
   @Nullable
   private ActiveProfiler.a m;
   private final boolean n;
   private final Set<Pair<String, C_141011_>> o = new ObjectArraySet();
   private boolean clientProfiler = false;
   private boolean lagometerActive = false;
   private static final String SCHEDULED_EXECUTABLES = "scheduledExecutables";
   private static final String TICK = "tick";
   private static final String SOUND = "sound";
   private static final int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
   private static final int HASH_TICK = "tick".hashCode();
   private static final int HASH_SOUND = "sound".hashCode();
   private static final ReflectorClass MINECRAFT = new ReflectorClass(C_3391_.class);
   private static final ReflectorField Minecraft_timeTracker = new ReflectorField(MINECRAFT, C_434_.class);

   public ActiveProfiler(LongSupplier p_i18382_1_, IntSupplier p_i18382_2_, boolean p_i18382_3_) {
      this.i = p_i18382_1_.getAsLong();
      this.h = p_i18382_1_;
      this.j = p_i18382_2_.getAsInt();
      this.g = p_i18382_2_;
      this.n = p_i18382_3_;
   }

   public void m_7242_() {
      C_434_ timeTracker = (C_434_)Reflector.getFieldValue(C_3391_.m_91087_(), Minecraft_timeTracker);
      this.clientProfiler = timeTracker != null && timeTracker.m_18439_() == this;
      this.lagometerActive = this.clientProfiler && Lagometer.isActive();
      if (this.l) {
         c.error("Profiler tick already started - missing endTick()?");
      } else {
         this.l = true;
         this.k = "";
         this.d.clear();
         this.m_6180_("root");
      }
   }

   public void m_7241_() {
      if (!this.l) {
         c.error("Profiler tick already ended - missing startTick()?");
      } else {
         this.m_7238_();
         this.l = false;
         if (!this.k.isEmpty()) {
            c.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", LogUtils.defer(() -> C_441_.m_18575_(this.k)));
         }
      }
   }

   public void m_6180_(String name) {
      if (this.lagometerActive) {
         int hashName = name.hashCode();
         if (hashName == HASH_SCHEDULED_EXECUTABLES && name.equals("scheduledExecutables")) {
            Lagometer.timerScheduledExecutables.start();
         } else if (hashName == HASH_TICK && name.equals("tick") && Config.isMinecraftThread()) {
            Lagometer.timerScheduledExecutables.end();
            Lagometer.timerTick.start();
         }
      }

      if (!this.l) {
         c.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", name);
      } else {
         if (!this.k.isEmpty()) {
            this.k = this.k + "\u001e";
         }

         this.k = this.k + name;
         this.d.add(this.k);
         this.e.add(Util.d());
         this.m = null;
      }
   }

   public void m_6521_(Supplier<String> nameSupplier) {
      this.m_6180_((String)nameSupplier.get());
   }

   public void m_142259_(C_141011_ categoryIn) {
      this.o.add(Pair.of(this.k, categoryIn));
   }

   public void m_7238_() {
      if (!this.l) {
         c.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
      } else if (this.e.isEmpty()) {
         c.error("Tried to pop one too many times! Mismatched push() and pop()?");
      } else {
         long i = Util.d();
         long j = this.e.removeLong(this.e.size() - 1);
         this.d.remove(this.d.size() - 1);
         long k = i - j;
         ActiveProfiler.a activeprofiler$pathentry = this.f();
         activeprofiler$pathentry.c = (activeprofiler$pathentry.c * 49L + k) / 50L;
         activeprofiler$pathentry.d = 1L;
         activeprofiler$pathentry.c += k;
         activeprofiler$pathentry.d++;
         activeprofiler$pathentry.a = Math.max(activeprofiler$pathentry.a, k);
         activeprofiler$pathentry.b = Math.min(activeprofiler$pathentry.b, k);
         if (this.n && k > a) {
            c.warn(
               "Something's taking too long! '{}' took aprox {} ms", LogUtils.defer(() -> C_441_.m_18575_(this.k)), LogUtils.defer(() -> (double)k / 1000000.0)
            );
         }

         this.k = this.d.isEmpty() ? "" : (String)this.d.get(this.d.size() - 1);
         this.m = null;
      }
   }

   public void m_6182_(String name) {
      if (this.lagometerActive) {
         int hashName = name.hashCode();
         if (hashName == HASH_SOUND && name.equals("sound")) {
            Lagometer.timerTick.end();
         }
      }

      this.m_7238_();
      this.m_6180_(name);
   }

   public void m_6523_(Supplier<String> nameSupplier) {
      this.m_7238_();
      this.m_6521_(nameSupplier);
   }

   private ActiveProfiler.a f() {
      if (this.m == null) {
         this.m = (ActiveProfiler.a)this.f.computeIfAbsent(this.k, keyIn -> new ActiveProfiler.a());
      }

      return this.m;
   }

   public void m_183275_(String nameIn, int countIn) {
      this.f().e.addTo(nameIn, (long)countIn);
   }

   public void m_183536_(Supplier<String> nameSupplierIn, int countIn) {
      this.f().e.addTo((String)nameSupplierIn.get(), (long)countIn);
   }

   public C_441_ m_5948_() {
      return new C_436_(this.f, this.i, this.j, this.h.getAsLong(), this.g.getAsInt());
   }

   @Nullable
   public ActiveProfiler.a c(String nameIn) {
      return (ActiveProfiler.a)this.f.get(nameIn);
   }

   public Set<Pair<String, C_141011_>> m_142579_() {
      return this.o;
   }

   public static class a implements C_444_ {
      long a = Long.MIN_VALUE;
      long b = Long.MAX_VALUE;
      long c;
      long d;
      final Object2LongOpenHashMap<String> e = new Object2LongOpenHashMap();

      public long m_7235_() {
         return this.c;
      }

      public long m_142752_() {
         return this.a;
      }

      public long m_7234_() {
         return this.d;
      }

      public Object2LongMap<String> m_7446_() {
         return Object2LongMaps.unmodifiable(this.e);
      }
   }
}
