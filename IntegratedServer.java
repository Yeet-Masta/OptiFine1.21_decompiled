import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.C_12_;
import net.minecraft.src.C_135_;
import net.minecraft.src.C_13_;
import net.minecraft.src.C_140956_;
import net.minecraft.src.C_1593_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_182781_;
import net.minecraft.src.C_203257_;
import net.minecraft.src.C_212929_;
import net.minecraft.src.C_22_;
import net.minecraft.src.C_313429_;
import net.minecraft.src.C_313747_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3499_;
import net.minecraft.src.C_3902_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_4584_;
import net.minecraft.src.C_4590_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_469_;
import net.minecraft.src.C_5285_;
import net.minecraft.src.C_62_;
import net.minecraft.src.C_2785_.C_2786_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class IntegratedServer extends MinecraftServer {
   private static final Logger f_129750_ = LogUtils.getLogger();
   private static final int f_177884_ = 2;
   private final C_3391_ f_177885_;
   private boolean f_302524_ = true;
   private int f_303374_ = -1;
   @Nullable
   private C_1593_ f_302842_;
   @Nullable
   private C_4590_ f_302898_;
   @Nullable
   private UUID f_303358_;
   private int f_303596_ = 0;
   private long ticksSaveLast = 0L;
   public C_1596_ difficultyUpdateWorld = null;
   public C_4675_ difficultyUpdatePos = null;
   public C_469_ difficultyLast = null;

   public IntegratedServer(
      Thread threadIn, C_3391_ mcIn, C_2786_ levelSaveIn, C_62_ resPackListIn, C_203257_ worldStemIn, C_212929_ servicesIn, C_22_ listenerFactoryIn
   ) {
      super(threadIn, levelSaveIn, resPackListIn, worldStemIn, mcIn.m_91096_(), mcIn.m_91295_(), servicesIn, listenerFactoryIn);
      this.m_236740_(mcIn.m_294346_());
      this.m_129975_(mcIn.m_91402_());
      this.m_129823_(new C_4584_(this, this.m_247573_(), this.f_129745_));
      this.f_177885_ = mcIn;
   }

   public boolean m_7038_() {
      f_129750_.info("Starting integrated minecraft server version {}", C_5285_.m_183709_().m_132493_());
      this.m_129985_(true);
      this.m_129997_(true);
      this.m_129999_(true);
      this.m_129793_();
      if (Reflector.ServerLifecycleHooks_handleServerAboutToStart.exists()
         && !Reflector.callBoolean(Reflector.ServerLifecycleHooks_handleServerAboutToStart, this)) {
         return false;
      } else {
         this.m_130006_();
         GameProfile gameprofile = this.m_236731_();
         String s = this.m_129910_().m_5462_();
         this.m_129989_(gameprofile != null ? gameprofile.getName() + " - " + s : s);
         return Reflector.ServerLifecycleHooks_handleServerStarting.exists()
            ? Reflector.callBoolean(Reflector.ServerLifecycleHooks_handleServerStarting, this)
            : true;
      }
   }

   public boolean m_305863_() {
      return this.f_302524_;
   }

   public void m_5705_(BooleanSupplier hasTimeLeft) {
      this.onTick();
      boolean flag = this.f_302524_;
      this.f_302524_ = C_3391_.m_91087_().m_91104_();
      C_442_ profilerfiller = this.m_129905_();
      if (!flag && this.f_302524_) {
         profilerfiller.m_6180_("autoSave");
         f_129750_.info("Saving and pausing game...");
         this.m_195514_(false, false, false);
         profilerfiller.m_7238_();
      }

      boolean flag1 = C_3391_.m_91087_().m_91403_() != null;
      if (flag1 && this.f_302524_) {
         this.b();
      } else {
         if (flag && !this.f_302524_) {
            this.m_276350_();
         }

         super.m_5705_(hasTimeLeft);
         int i = Math.max(2, this.f_177885_.m.e().c());
         if (i != this.m_6846_().m_11312_()) {
            f_129750_.info("Changing view distance to {}, from {}", i, this.m_6846_().m_11312_());
            this.m_6846_().m_11217_(i);
         }

         int j = Math.max(2, this.f_177885_.m.f().c());
         if (j != this.f_303596_) {
            f_129750_.info("Changing simulation distance to {}, from {}", j, this.f_303596_);
            this.m_6846_().m_184211_(j);
            this.f_303596_ = j;
         }
      }
   }

   protected C_313429_ a() {
      return this.f_177885_.aN().l();
   }

   public boolean m_319241_() {
      return true;
   }

   private void b() {
      for (C_13_ serverplayer : this.m_6846_().m_11314_()) {
         serverplayer.a(C_135_.l);
      }
   }

   public boolean m_6983_() {
      return true;
   }

   public boolean m_6102_() {
      return true;
   }

   public Path m_6237_() {
      return this.f_177885_.f_91069_.toPath();
   }

   public boolean m_6982_() {
      return false;
   }

   public int m_7032_() {
      return 0;
   }

   public boolean m_6994_() {
      return false;
   }

   public void a(CrashReport report) {
      this.f_177885_.b(report);
   }

   public C_140956_ m_142424_(C_140956_ report) {
      report.m_143519_("Type", "Integrated Server (map_client.txt)");
      report.m_143522_("Is Modded", () -> this.m_183471_().m_184605_());
      report.m_143522_("Launched Version", this.f_177885_::m_91388_);
      return report;
   }

   public C_182781_ m_183471_() {
      return C_3391_.m_193589_().m_184598_(super.m_183471_());
   }

   public boolean m_7386_(@Nullable C_1593_ gameMode, boolean cheats, int port) {
      try {
         this.f_177885_.m_193588_();
         this.f_177885_.m_231465_().m_252904_().thenAcceptAsync(optionalIn -> optionalIn.ifPresent(keyPairIn -> {
               C_3902_ clientpacketlistener = this.f_177885_.m_91403_();
               if (clientpacketlistener != null) {
                  clientpacketlistener.m_260951_(keyPairIn);
               }
            }), this.f_177885_);
         this.m_129919_().m_9711_(null, port);
         f_129750_.info("Started serving on {}", port);
         this.f_303374_ = port;
         this.f_302898_ = new C_4590_(this.m_129916_(), port + "");
         this.f_302898_.start();
         this.f_302842_ = gameMode;
         this.m_6846_().m_318715_(cheats);
         int i = this.m_129944_(this.f_177885_.f_91074_.fX());
         this.f_177885_.f_91074_.m_108648_(i);

         for (C_13_ serverplayer : this.m_6846_().m_11314_()) {
            this.m_129892_().m_82095_(serverplayer);
         }

         return true;
      } catch (IOException var7) {
         return false;
      }
   }

   public void m_7041_() {
      super.m_7041_();
      if (this.f_302898_ != null) {
         this.f_302898_.interrupt();
         this.f_302898_ = null;
      }
   }

   public void m_7570_(boolean waitForServer) {
      if (!Reflector.MinecraftForge.exists() || this.m_130010_()) {
         this.h(() -> {
            for (C_13_ serverplayer : Lists.newArrayList(this.m_6846_().m_11314_())) {
               if (!serverplayer.m_20148_().equals(this.f_303358_)) {
                  this.m_6846_().m_11286_(serverplayer);
               }
            }
         });
      }

      super.m_7570_(waitForServer);
      if (this.f_302898_ != null) {
         this.f_302898_.interrupt();
         this.f_302898_ = null;
      }
   }

   public boolean m_6992_() {
      return this.f_303374_ > -1;
   }

   public int m_7010_() {
      return this.f_303374_;
   }

   public void m_7835_(C_1593_ gameMode) {
      super.m_7835_(gameMode);
      this.f_302842_ = null;
   }

   public boolean m_6993_() {
      return true;
   }

   public int m_7022_() {
      return 2;
   }

   public int m_7034_() {
      return 2;
   }

   public void a(UUID uuid) {
      this.f_303358_ = uuid;
   }

   public boolean m_7779_(GameProfile profileIn) {
      return this.m_236731_() != null && profileIn.getName().equalsIgnoreCase(this.m_236731_().getName());
   }

   public int m_7186_(int distanceIn) {
      return (int)(this.f_177885_.m.g().c() * (double)distanceIn);
   }

   public boolean m_6365_() {
      return this.f_177885_.m.ae;
   }

   @Nullable
   public C_1593_ m_142359_() {
      return this.m_6992_() ? (C_1593_)MoreObjects.firstNonNull(this.f_302842_, this.f_129749_.m_5464_()) : null;
   }

   public boolean m_195514_(boolean suppresLog, boolean flush, boolean forced) {
      boolean flag = super.m_195514_(suppresLog, flush, forced);
      this.c();
      return flag;
   }

   private void c() {
      if (this.f_129744_.m_323802_()) {
         this.f_177885_.execute(() -> C_3499_.m_321093_(this.f_177885_));
      }
   }

   public void a(Throwable throwableIn, C_313747_ storageInfoIn, ChunkPos chunkPosIn) {
      super.a(throwableIn, storageInfoIn, chunkPosIn);
      this.c();
      this.f_177885_.execute(() -> C_3499_.a(this.f_177885_, chunkPosIn));
   }

   public void b(Throwable throwableIn, C_313747_ storageInfoIn, ChunkPos chunkPosIn) {
      super.b(throwableIn, storageInfoIn, chunkPosIn);
      this.c();
      this.f_177885_.execute(() -> C_3499_.b(this.f_177885_, chunkPosIn));
   }

   private void onTick() {
      for (C_12_ ws : this.m_129785_()) {
         this.onTick(ws);
      }
   }

   private void onTick(C_12_ ws) {
      if (!Config.isTimeDefault()) {
         this.fixWorldTime(ws);
      }

      if (!Config.isWeatherEnabled()) {
         this.fixWorldWeather(ws);
      }

      if (this.difficultyUpdateWorld == ws && this.difficultyUpdatePos != null) {
         this.difficultyLast = ws.m_6436_(this.difficultyUpdatePos);
         this.difficultyUpdateWorld = null;
         this.difficultyUpdatePos = null;
      }
   }

   public C_469_ getDifficultyAsync(C_1596_ world, C_4675_ blockPos) {
      this.difficultyUpdateWorld = world;
      this.difficultyUpdatePos = blockPos;
      return this.difficultyLast;
   }

   private void fixWorldWeather(C_12_ ws) {
      if (ws.m_46722_(1.0F) > 0.0F || ws.m_46470_()) {
         ws.m_8606_(6000, 0, false, false);
      }
   }

   private void fixWorldTime(C_12_ ws) {
      if (this.m_130008_() == C_1593_.CREATIVE) {
         long time = ws.m_46468_();
         long timeOfDay = time % 24000L;
         if (Config.isTimeDayOnly()) {
            if (timeOfDay <= 1000L) {
               ws.m_8615_(time - timeOfDay + 1001L);
            }

            if (timeOfDay >= 11000L) {
               ws.m_8615_(time - timeOfDay + 24001L);
            }
         }

         if (Config.isTimeNightOnly()) {
            if (timeOfDay <= 14000L) {
               ws.m_8615_(time - timeOfDay + 14001L);
            }

            if (timeOfDay >= 22000L) {
               ws.m_8615_(time - timeOfDay + 24000L + 14001L);
            }
         }
      }
   }

   public boolean m_129885_(boolean silentIn, boolean flushIn, boolean commandIn) {
      if (silentIn) {
         int ticks = this.m_129921_();
         int ticksSaveInterval = this.f_177885_.m.ofAutoSaveTicks;
         if ((long)ticks < this.ticksSaveLast + (long)ticksSaveInterval) {
            return false;
         }

         this.ticksSaveLast = (long)ticks;
      }

      return super.m_129885_(silentIn, flushIn, commandIn);
   }
}
