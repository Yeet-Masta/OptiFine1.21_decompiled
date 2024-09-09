import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1330_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1593_;
import net.minecraft.src.C_200_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3906_;
import net.minecraft.src.C_4497_;
import net.minecraft.src.C_559_;
import net.minecraft.src.C_899_;
import net.optifine.Config;
import net.optifine.RandomEntities;
import net.optifine.player.CapeUtils;
import net.optifine.player.PlayerConfigurations;
import net.optifine.reflect.Reflector;

public abstract class AbstractClientPlayer extends C_1141_ {
   @Nullable
   private C_3906_ g;
   protected Vec3 b;
   public float c;
   public float d;
   public float e;
   public final ClientLevel f;
   private ResourceLocation locationOfCape;
   private long reloadCapeTimeMs;
   private boolean elytraOfCape;
   private String nameClear;
   public C_899_ entityShoulderLeft;
   public C_899_ entityShoulderRight;
   public C_899_ lastAttachedEntity;
   public float capeRotateX;
   public float capeRotateY;
   public float capeRotateZ;
   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");

   public AbstractClientPlayer(ClientLevel worldIn, GameProfile profileIn) {
      super(worldIn, worldIn.m_220360_(), worldIn.m_220361_(), profileIn);
      this.b = Vec3.b;
      this.locationOfCape = null;
      this.reloadCapeTimeMs = 0L;
      this.elytraOfCape = false;
      this.nameClear = null;
      this.f = worldIn;
      this.nameClear = profileIn.getName();
      if (this.nameClear != null && !this.nameClear.isEmpty()) {
         this.nameClear = C_200_.m_14406_(this.nameClear);
      }

      CapeUtils.downloadCape(this);
      PlayerConfigurations.getPlayerConfiguration(this);
   }

   public boolean m_5833_() {
      C_3906_ playerinfo = this.a();
      return playerinfo != null && playerinfo.m_105325_() == C_1593_.SPECTATOR;
   }

   public boolean m_7500_() {
      C_3906_ playerinfo = this.a();
      return playerinfo != null && playerinfo.m_105325_() == C_1593_.CREATIVE;
   }

   @Nullable
   protected C_3906_ a() {
      if (this.g == null) {
         this.g = C_3391_.m_91087_().m_91403_().m_104949_(this.m_20148_());
      }

      return this.g;
   }

   public void m_8119_() {
      this.b = this.dr();
      super.m_8119_();
      if (this.lastAttachedEntity != null) {
         RandomEntities.checkEntityShoulder(this.lastAttachedEntity, true);
         this.lastAttachedEntity = null;
      }
   }

   public Vec3 G(float partialTicks) {
      return this.b.a(this.dr(), (double)partialTicks);
   }

   public PlayerSkin b() {
      C_3906_ playerinfo = this.a();
      return playerinfo == null ? C_4497_.a(this.m_20148_()) : playerinfo.g();
   }

   public float c() {
      float f = 1.0F;
      if (this.m_150110_().f_35935_) {
         f *= 1.1F;
      }

      f *= ((float)this.m_246858_(C_559_.f_22279_) / this.m_150110_().m_35947_() + 1.0F) / 2.0F;
      if (this.m_150110_().m_35947_() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
         f = 1.0F;
      }

      C_1391_ itemstack = this.m_21211_();
      if (this.m_6117_()) {
         if (itemstack.m_41720_() instanceof C_1330_) {
            int i = this.m_21252_();
            float f1 = (float)i / 20.0F;
            if (f1 > 1.0F) {
               f1 = 1.0F;
            } else {
               f1 *= f1;
            }

            f *= 1.0F - f1 * 0.15F;
         } else if (C_3391_.m_91087_().m.aB().m_90612_() && this.m_150108_()) {
            return 0.1F;
         }
      }

      return Reflector.ForgeHooksClient_getFieldOfViewModifier.exists()
         ? Reflector.callFloat(Reflector.ForgeHooksClient_getFieldOfViewModifier, this, f)
         : Mth.i(C_3391_.m_91087_().m.ak().c().floatValue(), 1.0F, f);
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

         return this.locationOfCape != null ? this.locationOfCape : this.b().c();
      }
   }

   public ResourceLocation getLocationElytra() {
      return this.hasElytraCape() ? this.locationOfCape : this.b().d();
   }

   public ResourceLocation getSkinTextureLocation() {
      C_3906_ playerinfo = this.a();
      return playerinfo == null ? C_4497_.a(this.m_20148_()).a() : playerinfo.g().a();
   }
}
