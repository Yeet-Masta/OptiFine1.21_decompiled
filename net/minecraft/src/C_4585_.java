package net.minecraft.src;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import org.slf4j.Logger;

public class C_4585_ extends MinecraftServer {
   private static final Logger f_120014_ = LogUtils.getLogger();
   private static final int f_194466_ = 2;
   private final C_3391_ f_120015_;
   private boolean f_120016_ = true;
   private int f_120017_ = -1;
   @Nullable
   private C_1593_ f_174966_;
   @Nullable
   private C_4590_ f_120018_;
   @Nullable
   private UUID f_120019_;
   private int f_194467_ = 0;
   private long ticksSaveLast = 0L;
   public C_1596_ difficultyUpdateWorld = null;
   public C_4675_ difficultyUpdatePos = null;
   public C_469_ difficultyLast = null;

   public C_4585_(Thread threadIn, C_3391_ mcIn, C_2785_.C_2786_ levelSaveIn, C_62_ resPackListIn, C_203257_ worldStemIn, C_212929_ servicesIn, C_22_ listenerFactoryIn) {
      super(threadIn, levelSaveIn, resPackListIn, worldStemIn, mcIn.m_91096_(), mcIn.m_91295_(), servicesIn, listenerFactoryIn);
      this.m_236740_(mcIn.m_294346_());
      this.m_129975_(mcIn.m_91402_());
      this.m_129823_(new C_4584_(this, this.m_247573_(), this.f_129745_));
      this.f_120015_ = mcIn;
   }

   public boolean m_7038_() {
      f_120014_.info("Starting integrated minecraft server version {}", C_5285_.m_183709_().m_132493_());
      this.m_129985_(true);
      this.m_129997_(true);
      this.m_129999_(true);
      this.m_129793_();
      if (Reflector.ServerLifecycleHooks_handleServerAboutToStart.exists() && !Reflector.callBoolean(Reflector.ServerLifecycleHooks_handleServerAboutToStart, this)) {
         return false;
      } else {
         this.m_130006_();
         GameProfile gameprofile = this.m_236731_();
         String s = this.m_129910_().m_5462_();
         this.m_129989_(gameprofile != null ? gameprofile.getName() + " - " + s : s);
         return Reflector.ServerLifecycleHooks_handleServerStarting.exists() ? Reflector.callBoolean(Reflector.ServerLifecycleHooks_handleServerStarting, this) : true;
      }
   }

   public boolean m_305863_() {
      return this.f_120016_;
   }

   public void m_5705_(BooleanSupplier hasTimeLeft) {
      this.onTick();
      boolean flag = this.f_120016_;
      this.f_120016_ = C_3391_.m_91087_().m_91104_();
      C_442_ profilerfiller = this.m_129905_();
      if (!flag && this.f_120016_) {
         profilerfiller.m_6180_("autoSave");
         f_120014_.info("Saving and pausing game...");
         this.m_195514_(false, false, false);
         profilerfiller.m_7238_();
      }

      boolean flag1 = C_3391_.m_91087_().m_91403_() != null;
      if (flag1 && this.f_120016_) {
         this.m_174968_();
      } else {
         if (flag && !this.f_120016_) {
            this.m_276350_();
         }

         super.m_5705_(hasTimeLeft);
         int i = Math.max(2, (Integer)this.f_120015_.f_91066_.m_231984_().m_231551_());
         if (i != this.m_6846_().m_11312_()) {
            f_120014_.info("Changing view distance to {}, from {}", i, this.m_6846_().m_11312_());
            this.m_6846_().m_11217_(i);
         }

         int j = Math.max(2, (Integer)this.f_120015_.f_91066_.m_232001_().m_231551_());
         if (j != this.f_194467_) {
            f_120014_.info("Changing simulation distance to {}, from {}", j, this.f_194467_);
            this.m_6846_().m_184211_(j);
            this.f_194467_ = j;
         }
      }

   }

   protected C_313429_ m_318596_() {
      return this.f_120015_.m_293199_().m_324537_();
   }

   public boolean m_319241_() {
      return true;
   }

   private void m_174968_() {
      Iterator var1 = this.m_6846_().m_11314_().iterator();

      while(var1.hasNext()) {
         C_13_ serverplayer = (C_13_)var1.next();
         serverplayer.a(C_135_.f_144256_);
      }

   }

   public boolean m_6983_() {
      return true;
   }

   public boolean m_6102_() {
      return true;
   }

   public Path m_6237_() {
      return this.f_120015_.f_91069_.toPath();
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

   public void m_7268_(C_4883_ report) {
      this.f_120015_.m_231439_(report);
   }

   public C_140956_ m_142424_(C_140956_ report) {
      report.m_143519_("Type", "Integrated Server (map_client.txt)");
      report.m_143522_("Is Modded", () -> {
         return this.m_183471_().m_184605_();
      });
      C_3391_ var10002 = this.f_120015_;
      Objects.requireNonNull(var10002);
      report.m_143522_("Launched Version", var10002::m_91388_);
      return report;
   }

   public C_182781_ m_183471_() {
      return C_3391_.m_193589_().m_184598_(super.m_183471_());
   }

   public boolean m_7386_(@Nullable C_1593_ gameMode, boolean cheats, int port) {
      try {
         this.f_120015_.m_193588_();
         this.f_120015_.m_231465_().m_252904_().thenAcceptAsync((optionalIn) -> {
            optionalIn.ifPresent((keyPairIn) -> {
               C_3902_ clientpacketlistener = this.f_120015_.m_91403_();
               if (clientpacketlistener != null) {
                  clientpacketlistener.m_260951_(keyPairIn);
               }

            });
         }, this.f_120015_);
         this.m_129919_().m_9711_((InetAddress)null, port);
         f_120014_.info("Started serving on {}", port);
         this.f_120017_ = port;
         this.f_120018_ = new C_4590_(this.m_129916_(), "" + port);
         this.f_120018_.start();
         this.f_174966_ = gameMode;
         this.m_6846_().m_318715_(cheats);
         int i = this.m_129944_(this.f_120015_.f_91074_.fX());
         this.f_120015_.f_91074_.m_108648_(i);
         Iterator var5 = this.m_6846_().m_11314_().iterator();

         while(var5.hasNext()) {
            C_13_ serverplayer = (C_13_)var5.next();
            this.m_129892_().m_82095_(serverplayer);
         }

         return true;
      } catch (IOException var7) {
         return false;
      }
   }

   public void m_7041_() {
      super.m_7041_();
      if (this.f_120018_ != null) {
         this.f_120018_.interrupt();
         this.f_120018_ = null;
      }

   }

   public void m_7570_(boolean waitForServer) {
      if (!Reflector.MinecraftForge.exists() || this.m_130010_()) {
         this.h(() -> {
            Iterator var1 = Lists.newArrayList(this.m_6846_().m_11314_()).iterator();

            while(var1.hasNext()) {
               C_13_ serverplayer = (C_13_)var1.next();
               if (!serverplayer.cz().equals(this.f_120019_)) {
                  this.m_6846_().m_11286_(serverplayer);
               }
            }

         });
      }

      super.m_7570_(waitForServer);
      if (this.f_120018_ != null) {
         this.f_120018_.interrupt();
         this.f_120018_ = null;
      }

   }

   public boolean m_6992_() {
      return this.f_120017_ > -1;
   }

   public int m_7010_() {
      return this.f_120017_;
   }

   public void m_7835_(C_1593_ gameMode) {
      super.m_7835_(gameMode);
      this.f_174966_ = null;
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

   public void m_120046_(UUID uuid) {
      this.f_120019_ = uuid;
   }

   public boolean m_7779_(GameProfile profileIn) {
      return this.m_236731_() != null && profileIn.getName().equalsIgnoreCase(this.m_236731_().getName());
   }

   public int m_7186_(int distanceIn) {
      return (int)((Double)this.f_120015_.f_91066_.m_232018_().m_231551_() * (double)distanceIn);
   }

   public boolean m_6365_() {
      return this.f_120015_.f_91066_.f_92076_;
   }

   @Nullable
   public C_1593_ m_142359_() {
      return this.m_6992_() ? (C_1593_)MoreObjects.firstNonNull(this.f_174966_, this.f_129749_.m_5464_()) : null;
   }

   public boolean m_195514_(boolean suppresLog, boolean flush, boolean forced) {
      boolean flag = super.m_195514_(suppresLog, flush, forced);
      this.m_321341_();
      return flag;
   }

   private void m_321341_() {
      if (this.f_129744_.m_323802_()) {
         this.f_120015_.execute(() -> {
            C_3499_.m_321093_(this.f_120015_);
         });
      }

   }

   public void m_293783_(Throwable throwableIn, C_313747_ storageInfoIn, C_1560_ chunkPosIn) {
      super.m_293783_(throwableIn, storageInfoIn, chunkPosIn);
      this.m_321341_();
      this.f_120015_.execute(() -> {
         C_3499_.m_321637_(this.f_120015_, chunkPosIn);
      });
   }

   public void m_322794_(Throwable throwableIn, C_313747_ storageInfoIn, C_1560_ chunkPosIn) {
      super.m_322794_(throwableIn, storageInfoIn, chunkPosIn);
      this.m_321341_();
      this.f_120015_.execute(() -> {
         C_3499_.m_323567_(this.f_120015_, chunkPosIn);
      });
   }

   private void onTick() {
      Iterable iws = this.m_129785_();
      Iterator it = iws.iterator();

      while(it.hasNext()) {
         C_12_ ws = (C_12_)it.next();
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
         this.difficultyLast = ws.d_(this.difficultyUpdatePos);
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
      if (ws.d(1.0F) > 0.0F || ws.ac()) {
         ws.m_8606_(6000, 0, false, false);
      }

   }

   private void fixWorldTime(C_12_ ws) {
      if (this.m_130008_() == C_1593_.CREATIVE) {
         long time = ws.aa();
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
         int ticksSaveInterval = this.f_120015_.f_91066_.ofAutoSaveTicks;
         if ((long)ticks < this.ticksSaveLast + (long)ticksSaveInterval) {
            return false;
         }

         this.ticksSaveLast = (long)ticks;
      }

      return super.m_129885_(silentIn, flushIn, commandIn);
   }
}
