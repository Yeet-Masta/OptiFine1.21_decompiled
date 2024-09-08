package net.minecraft.client.player;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.optifine.Config;
import net.optifine.RandomEntities;
import net.optifine.player.CapeUtils;
import net.optifine.player.PlayerConfigurations;
import net.optifine.reflect.Reflector;

public abstract class AbstractClientPlayer extends Player {
   @Nullable
   private PlayerInfo f_108546_;
   protected Vec3 f_271420_ = Vec3.f_82478_;
   public float f_108542_;
   public float f_108543_;
   public float f_108544_;
   public final ClientLevel f_108545_;
   private ResourceLocation locationOfCape = null;
   private long reloadCapeTimeMs = 0L;
   private boolean elytraOfCape = false;
   private String nameClear = null;
   public ShoulderRidingEntity entityShoulderLeft;
   public ShoulderRidingEntity entityShoulderRight;
   public ShoulderRidingEntity lastAttachedEntity;
   public float capeRotateX;
   public float capeRotateY;
   public float capeRotateZ;
   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");

   public AbstractClientPlayer(ClientLevel worldIn, GameProfile profileIn) {
      super(worldIn, worldIn.m_220360_(), worldIn.m_220361_(), profileIn);
      this.f_108545_ = worldIn;
      this.nameClear = profileIn.getName();
      if (this.nameClear != null && !this.nameClear.isEmpty()) {
         this.nameClear = StringUtil.m_14406_(this.nameClear);
      }

      CapeUtils.downloadCape(this);
      PlayerConfigurations.getPlayerConfiguration(this);
   }

   public boolean m_5833_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo != null && playerinfo.m_105325_() == GameType.SPECTATOR;
   }

   public boolean m_7500_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo != null && playerinfo.m_105325_() == GameType.CREATIVE;
   }

   @Nullable
   protected PlayerInfo m_108558_() {
      if (this.f_108546_ == null) {
         this.f_108546_ = Minecraft.m_91087_().m_91403_().m_104949_(this.m_20148_());
      }

      return this.f_108546_;
   }

   public void m_8119_() {
      this.f_271420_ = this.m_20184_();
      super.m_8119_();
      if (this.lastAttachedEntity != null) {
         RandomEntities.checkEntityShoulder(this.lastAttachedEntity, true);
         this.lastAttachedEntity = null;
      }
   }

   public Vec3 m_272267_(float partialTicks) {
      return this.f_271420_.m_165921_(this.m_20184_(), (double)partialTicks);
   }

   public PlayerSkin m_294544_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo == null ? DefaultPlayerSkin.m_294143_(this.m_20148_()) : playerinfo.m_293823_();
   }

   public float m_108565_() {
      float f = 1.0F;
      if (this.m_150110_().f_35935_) {
         f *= 1.1F;
      }

      f *= ((float)this.m_246858_(Attributes.f_22279_) / this.m_150110_().m_35947_() + 1.0F) / 2.0F;
      if (this.m_150110_().m_35947_() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
         f = 1.0F;
      }

      ItemStack itemstack = this.m_21211_();
      if (this.m_6117_()) {
         if (itemstack.m_41720_() instanceof BowItem) {
            int i = this.m_21252_();
            float f1 = (float)i / 20.0F;
            if (f1 > 1.0F) {
               f1 = 1.0F;
            } else {
               f1 *= f1;
            }

            f *= 1.0F - f1 * 0.15F;
         } else if (Minecraft.m_91087_().f_91066_.m_92176_().m_90612_() && this.m_150108_()) {
            return 0.1F;
         }
      }

      return Reflector.ForgeHooksClient_getFieldOfViewModifier.exists()
         ? Reflector.callFloat(Reflector.ForgeHooksClient_getFieldOfViewModifier, this, f)
         : Mth.m_14179_(Minecraft.m_91087_().f_91066_.m_231925_().m_231551_().floatValue(), 1.0F, f);
   }

   public String getNameClear() {
      return this.nameClear;
   }

   public ResourceLocation getLocationOfCape() {
      return this.locationOfCape;
   }

   public void setLocationOfCape(ResourceLocation locationOfCape) {
      this.locationOfCape = locationOfCape;
   }

   public boolean hasElytraCape() {
      ResourceLocation loc = this.getLocationCape();
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
   public ResourceLocation getLocationCape() {
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

   public ResourceLocation getLocationElytra() {
      return this.hasElytraCape() ? this.locationOfCape : this.m_294544_().f_290452_();
   }

   public ResourceLocation getSkinTextureLocation() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo == null ? DefaultPlayerSkin.m_294143_(this.m_20148_()).f_290339_() : playerinfo.m_293823_().f_290339_();
   }
}
