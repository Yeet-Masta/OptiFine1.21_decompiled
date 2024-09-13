package net.minecraft.util.profiling;

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
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.optifine.Config;
import net.optifine.Lagometer;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

public class ActiveProfiler implements ProfileCollector {
   private static long f_18368_ = Duration.ofMillis(100L).toNanos();
   private static Logger f_18369_ = LogUtils.getLogger();
   private List<String> f_18370_ = Lists.newArrayList();
   private LongList f_18371_ = new LongArrayList();
   private Map<String, ActiveProfiler.PathEntry> f_18372_ = Maps.newHashMap();
   private IntSupplier f_18373_;
   private LongSupplier f_18374_;
   private long f_18375_;
   private int f_18376_;
   private String f_18377_ = "";
   private boolean f_18378_;
   @Nullable
   private ActiveProfiler.PathEntry f_18379_;
   private boolean f_18380_;
   private Set<Pair<String, MetricCategory>> f_145926_ = new ObjectArraySet();
   private boolean clientProfiler = false;
   private boolean lagometerActive = false;
   private static String SCHEDULED_EXECUTABLES;
   private static String TICK;
   private static String SOUND;
   private static int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
   private static int HASH_TICK = "tick".hashCode();
   private static int HASH_SOUND = "sound".hashCode();
   private static ReflectorClass MINECRAFT = new ReflectorClass(Minecraft.class);
   private static ReflectorField Minecraft_timeTracker = new ReflectorField(MINECRAFT, ContinuousProfiler.class);

   public ActiveProfiler(LongSupplier p_i18382_1_, IntSupplier p_i18382_2_, boolean p_i18382_3_) {
      this.f_18375_ = p_i18382_1_.getAsLong();
      this.f_18374_ = p_i18382_1_;
      this.f_18376_ = p_i18382_2_.getAsInt();
      this.f_18373_ = p_i18382_2_;
      this.f_18380_ = p_i18382_3_;
   }

   public void m_7242_() {
      ContinuousProfiler timeTracker = (ContinuousProfiler)Reflector.getFieldValue(Minecraft.m_91087_(), Minecraft_timeTracker);
      this.clientProfiler = timeTracker != null && timeTracker.m_18439_() == this;
      this.lagometerActive = this.clientProfiler && Lagometer.isActive();
      if (this.f_18378_) {
         f_18369_.error("Profiler tick already started - missing endTick()?");
      } else {
         this.f_18378_ = true;
         this.f_18377_ = "";
         this.f_18370_.clear();
         this.m_6180_("root");
      }
   }

   public void m_7241_() {
      if (!this.f_18378_) {
         f_18369_.error("Profiler tick already ended - missing startTick()?");
      } else {
         this.m_7238_();
         this.f_18378_ = false;
         if (!this.f_18377_.isEmpty()) {
            f_18369_.error(
               "Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?",
               LogUtils.defer(() -> ProfileResults.m_18575_(this.f_18377_))
            );
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

      if (!this.f_18378_) {
         f_18369_.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", name);
      } else {
         if (!this.f_18377_.isEmpty()) {
            this.f_18377_ = this.f_18377_ + "\u001e";
         }

         this.f_18377_ = this.f_18377_ + name;
         this.f_18370_.add(this.f_18377_);
         this.f_18371_.add(Util.m_137569_());
         this.f_18379_ = null;
      }
   }

   public void m_6521_(Supplier<String> nameSupplier) {
      this.m_6180_((String)nameSupplier.get());
   }

   public void m_142259_(MetricCategory categoryIn) {
      this.f_145926_.add(Pair.m_253057_(this.f_18377_, categoryIn));
   }

   public void m_7238_() {
      if (!this.f_18378_) {
         f_18369_.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
      } else if (this.f_18371_.isEmpty()) {
         f_18369_.error("Tried to pop one too many times! Mismatched push() and pop()?");
      } else {
         long i = Util.m_137569_();
         long j = this.f_18371_.removeLong(this.f_18371_.size() - 1);
         this.f_18370_.remove(this.f_18370_.size() - 1);
         long k = i - j;
         ActiveProfiler.PathEntry activeprofiler$pathentry = this.m_18406_();
         activeprofiler$pathentry.f_145934_ = (activeprofiler$pathentry.f_145934_ * 49L + k) / 50L;
         activeprofiler$pathentry.f_18410_ = 1L;
         activeprofiler$pathentry.f_145934_ += k;
         activeprofiler$pathentry.f_18410_++;
         activeprofiler$pathentry.f_145932_ = Math.max(activeprofiler$pathentry.f_145932_, k);
         activeprofiler$pathentry.f_145933_ = Math.min(activeprofiler$pathentry.f_145933_, k);
         if (this.f_18380_ && k > f_18368_) {
            f_18369_.warn(
               "Something's taking too long! '{}' took aprox {} ms",
               LogUtils.defer(() -> ProfileResults.m_18575_(this.f_18377_)),
               LogUtils.defer(() -> (double)k / 1000000.0)
            );
         }

         this.f_18377_ = this.f_18370_.isEmpty() ? "" : (String)this.f_18370_.get(this.f_18370_.size() - 1);
         this.f_18379_ = null;
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

   private ActiveProfiler.PathEntry m_18406_() {
      if (this.f_18379_ == null) {
         this.f_18379_ = (ActiveProfiler.PathEntry)this.f_18372_.computeIfAbsent(this.f_18377_, keyIn -> new ActiveProfiler.PathEntry());
      }

      return this.f_18379_;
   }

   public void m_183275_(String nameIn, int countIn) {
      this.m_18406_().f_18411_.addTo(nameIn, (long)countIn);
   }

   public void m_183536_(Supplier<String> nameSupplierIn, int countIn) {
      this.m_18406_().f_18411_.addTo((String)nameSupplierIn.get(), (long)countIn);
   }

   public ProfileResults m_5948_() {
      return new FilledProfileResults(this.f_18372_, this.f_18375_, this.f_18376_, this.f_18374_.getAsLong(), this.f_18373_.getAsInt());
   }

   @Nullable
   public ActiveProfiler.PathEntry m_142431_(String nameIn) {
      return (ActiveProfiler.PathEntry)this.f_18372_.get(nameIn);
   }

   public Set<Pair<String, MetricCategory>> m_142579_() {
      return this.f_145926_;
   }

   public static class PathEntry implements ProfilerPathEntry {
      long f_145932_ = Long.MIN_VALUE;
      long f_145933_ = Long.MAX_VALUE;
      long f_145934_;
      long f_18410_;
      Object2LongOpenHashMap<String> f_18411_ = new Object2LongOpenHashMap();

      public long m_7235_() {
         return this.f_145934_;
      }

      public long m_142752_() {
         return this.f_145932_;
      }

      public long m_7234_() {
         return this.f_18410_;
      }

      public Object2LongMap<String> m_7446_() {
         return Object2LongMaps.unmodifiable(this.f_18411_);
      }
   }
}
