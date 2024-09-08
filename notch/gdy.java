package net.minecraft.src;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.optifine.Config;
import net.optifine.RandomEntities;
import net.optifine.player.CapeUtils;
import net.optifine.player.PlayerConfigurations;
import net.optifine.reflect.Reflector;

public abstract class C_4102_ extends C_1141_ {
   @Nullable
   private C_3906_ f_108546_;
   protected C_3046_ f_271420_ = C_3046_.f_82478_;
   public float f_108542_;
   public float f_108543_;
   public float f_108544_;
   public final C_3899_ f_108545_;
   private C_5265_ locationOfCape = null;
   private long reloadCapeTimeMs = 0L;
   private boolean elytraOfCape = false;
   private String nameClear = null;
   public C_899_ entityShoulderLeft;
   public C_899_ entityShoulderRight;
   public C_899_ lastAttachedEntity;
   public float capeRotateX;
   public float capeRotateY;
   public float capeRotateZ;
   private static final C_5265_ TEXTURE_ELYTRA = new C_5265_("textures/entity/elytra.png");

   public C_4102_(C_3899_ worldIn, GameProfile profileIn) {
      super(worldIn, worldIn.m_220360_(), worldIn.m_220361_(), profileIn);
      this.f_108545_ = worldIn;
      this.nameClear = profileIn.getName();
      if (this.nameClear != null && !this.nameClear.isEmpty()) {
         this.nameClear = C_200_.m_14406_(this.nameClear);
      }

      CapeUtils.downloadCape(this);
      PlayerConfigurations.getPlayerConfiguration(this);
   }

   public boolean m_5833_() {
      C_3906_ playerinfo = this.m_108558_();
      return playerinfo != null && playerinfo.m_105325_() == C_1593_.SPECTATOR;
   }

   public boolean m_7500_() {
      C_3906_ playerinfo = this.m_108558_();
      return playerinfo != null && playerinfo.m_105325_() == C_1593_.CREATIVE;
   }

   @Nullable
   protected C_3906_ m_108558_() {
      if (this.f_108546_ == null) {
         this.f_108546_ = C_3391_.m_91087_().m_91403_().m_104949_(this.cz());
      }

      return this.f_108546_;
   }

   public void m_8119_() {
      this.f_271420_ = this.dr();
      super.m_8119_();
      if (this.lastAttachedEntity != null) {
         RandomEntities.checkEntityShoulder(this.lastAttachedEntity, true);
         this.lastAttachedEntity = null;
      }
   }

   public C_3046_ m_272267_(float partialTicks) {
      return this.f_271420_.m_165921_(this.dr(), (double)partialTicks);
   }

   public C_290287_ m_294544_() {
      C_3906_ playerinfo = this.m_108558_();
      return playerinfo == null ? C_4497_.m_294143_(this.cz()) : playerinfo.m_293823_();
   }

   public float m_108565_() {
      float f = 1.0F;
      if (this.m_150110_().f_35935_) {
         f *= 1.1F;
      }

      f *= ((float)this.g(C_559_.f_22279_) / this.m_150110_().m_35947_() + 1.0F) / 2.0F;
      if (this.m_150110_().m_35947_() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
         f = 1.0F;
      }

      C_1391_ itemstack = this.ft();
      if (this.fr()) {
         if (itemstack.m_41720_() instanceof C_1330_) {
            int i = this.fv();
            float f1 = (float)i / 20.0F;
            if (f1 > 1.0F) {
               f1 = 1.0F;
            } else {
               f1 *= f1;
            }

            f *= 1.0F - f1 * 0.15F;
         } else if (C_3391_.m_91087_().f_91066_.m_92176_().m_90612_() && this.m_150108_()) {
            return 0.1F;
         }
      }

      return Reflector.ForgeHooksClient_getFieldOfViewModifier.exists()
         ? Reflector.callFloat(Reflector.ForgeHooksClient_getFieldOfViewModifier, this, f)
         : C_188_.m_14179_(C_3391_.m_91087_().f_91066_.m_231925_().m_231551_().floatValue(), 1.0F, f);
   }

   public String getNameClear() {
      return this.nameClear;
   }

   public C_5265_ getLocationOfCape() {
      return this.locationOfCape;
   }

   public void setLocationOfCape(C_5265_ locationOfCape) {
      this.locationOfCape = locationOfCape;
   }

   public boolean hasElytraCape() {
      C_5265_ loc = this.getLocationCape();
      if (loc == null) {
         return false;
      } else {
         return loc == this.locationOfCape ? this.elytraOfCape : true;
      }
   }

   public void setElytraOfCape(boolean elytraOfCape) {
      this.elytraOfCape = elytraOfCape;
   }

   public boolean isElytraOfCape() {
      return this.elytraOfCape;
   }

   public long getReloadCapeTimeMs() {
      return this.reloadCapeTimeMs;
   }

   public void setReloadCapeTimeMs(long reloadCapeTimeMs) {
      this.reloadCapeTimeMs = reloadCapeTimeMs;
   }

   @Nullable
   public C_5265_ getLocationCape() {
      if (!Config.isShowCapes()) {
         return null;
      } else {
         if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
            CapeUtils.reloadCape(this);
            this.reloadCapeTimeMs = 0L;
            PlayerConfigurations.setPlayerConfiguration(this.getNameClear(), null);
         }

         return this.locationOfCape != null ? this.locationOfCape : this.m_294544_().f_291348_();
      }
   }

   public C_5265_ getLocationElytra() {
      return this.hasElytraCape() ? this.locationOfCape : this.m_294544_().f_290452_();
   }

   public C_5265_ getSkinTextureLocation() {
      C_3906_ playerinfo = this.m_108558_();
      return playerinfo == null ? C_4497_.m_294143_(this.cz()).f_290339_() : playerinfo.m_293823_().f_290339_();
   }
}
