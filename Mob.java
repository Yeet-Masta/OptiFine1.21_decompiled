import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.src.C_1004_;
import net.minecraft.src.C_1027_;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1189_;
import net.minecraft.src.C_1205_;
import net.minecraft.src.C_123_;
import net.minecraft.src.C_12_;
import net.minecraft.src.C_1313_;
import net.minecraft.src.C_1325_;
import net.minecraft.src.C_1330_;
import net.minecraft.src.C_1349_;
import net.minecraft.src.C_1351_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_1406_;
import net.minecraft.src.C_141307_;
import net.minecraft.src.C_1420_;
import net.minecraft.src.C_1425_;
import net.minecraft.src.C_1522_;
import net.minecraft.src.C_1583_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1598_;
import net.minecraft.src.C_1599_;
import net.minecraft.src.C_1618_;
import net.minecraft.src.C_203208_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_2690_;
import net.minecraft.src.C_271030_;
import net.minecraft.src.C_2822_;
import net.minecraft.src.C_286926_;
import net.minecraft.src.C_2973_;
import net.minecraft.src.C_2974_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_313447_;
import net.minecraft.src.C_313465_;
import net.minecraft.src.C_313470_;
import net.minecraft.src.C_313491_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_313716_;
import net.minecraft.src.C_336472_;
import net.minecraft.src.C_336480_;
import net.minecraft.src.C_336597_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_468_;
import net.minecraft.src.C_469_;
import net.minecraft.src.C_4702_;
import net.minecraft.src.C_470_;
import net.minecraft.src.C_4713_;
import net.minecraft.src.C_471_;
import net.minecraft.src.C_4759_;
import net.minecraft.src.C_489_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4923_;
import net.minecraft.src.C_4930_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5143_;
import net.minecraft.src.C_516_;
import net.minecraft.src.C_520_;
import net.minecraft.src.C_5225_;
import net.minecraft.src.C_5227_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_529_;
import net.minecraft.src.C_542_;
import net.minecraft.src.C_553_;
import net.minecraft.src.C_555_;
import net.minecraft.src.C_559_;
import net.minecraft.src.C_661_;
import net.minecraft.src.C_664_;
import net.minecraft.src.C_667_;
import net.minecraft.src.C_668_;
import net.minecraft.src.C_688_;
import net.minecraft.src.C_690_;
import net.minecraft.src.C_753_;
import net.minecraft.src.C_757_;
import net.minecraft.src.C_758_;
import net.minecraft.src.C_775_;
import net.minecraft.src.C_976_;
import net.minecraft.src.C_286926_.C_286924_;
import net.minecraft.src.C_336597_.C_336538_;
import net.minecraft.src.C_516_.C_517_;
import net.minecraft.src.C_555_.C_556_;
import net.minecraft.src.C_557_.C_558_;
import net.minecraft.src.C_688_.C_689_;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.fluids.FluidType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public abstract class Mob extends C_524_ implements C_313491_, C_336597_, C_271030_, IForgeLivingEntity {
   private static final C_5225_<Byte> b = SynchedEntityData.a(Mob.class, C_5227_.f_135027_);
   private static final int c = 1;
   private static final int d = 2;
   private static final int e = 4;
   protected static final int h = 1;
   private static final C_4713_ cb = new C_4713_(1, 0, 1);
   public static final float bH = 0.15F;
   public static final float bI = 0.55F;
   public static final float bJ = 0.5F;
   public static final float bK = 0.25F;
   public static final float bL = 0.085F;
   public static final float bM = 1.0F;
   public static final int bN = 2;
   public static final int bO = 2;
   private static final double cc = Math.sqrt(2.04F) - 0.6F;
   protected static final ResourceLocation bP = ResourceLocation.b("random_spawn_bonus");
   public int bQ;
   protected int bR;
   protected C_667_ bS;
   protected C_668_ bT;
   protected C_664_ bU;
   private final C_661_ cd;
   protected C_758_ bV;
   protected final C_690_ bW;
   protected final C_690_ bX;
   @Nullable
   private C_524_ f_20951_;
   private final C_775_ cf;
   private final C_4702_<C_1391_> cg = C_4702_.m_122780_(2, C_1391_.f_41583_);
   protected final float[] bY = new float[2];
   private final C_4702_<C_1391_> ch = C_4702_.m_122780_(4, C_1391_.f_41583_);
   protected final float[] bZ = new float[4];
   private C_1391_ ci = C_1391_.f_41583_;
   protected float ca;
   private boolean cj;
   private boolean ck;
   private final Map<C_313716_, Float> cl = Maps.newEnumMap(C_313716_.class);
   @Nullable
   private C_5264_<C_2822_> cm;
   private long cn;
   @Nullable
   private C_336538_ co;
   private C_4675_ cp = C_4675_.f_121853_;
   private float cq = -1.0F;
   private C_529_ spawnType;
   private boolean spawnCancelled = false;

   protected Mob(C_513_<? extends Mob> type, C_1596_ worldIn) {
      super(type, worldIn);
      this.bW = new C_690_(worldIn.m_46658_());
      this.bX = new C_690_(worldIn.m_46658_());
      this.bS = new C_667_(this);
      this.bT = new C_668_(this);
      this.bU = new C_664_(this);
      this.cd = this.H();
      this.bV = this.b(worldIn);
      this.cf = new C_775_(this);
      Arrays.fill(this.bZ, 0.085F);
      Arrays.fill(this.bY, 0.085F);
      this.ca = 0.085F;
      if (worldIn != null && !worldIn.f_46443_) {
         this.m_292733_();
      }
   }

   protected void m_292733_() {
   }

   public static C_558_ C() {
      return C_524_.m_21183_().m_22268_(C_559_.f_22277_, 16.0);
   }

   protected C_758_ b(C_1596_ worldIn) {
      return new C_757_(this, worldIn);
   }

   protected boolean D() {
      return false;
   }

   public float a(C_313716_ nodeType) {
      Mob mob;
      label17: {
         if (this.m_275832_() instanceof Mob mob1 && mob1.D()) {
            mob = mob1;
            break label17;
         }

         mob = this;
      }

      Float f = (Float)mob.cl.get(nodeType);
      return f == null ? nodeType.m_320214_() : f;
   }

   public void a(C_313716_ nodeType, float priority) {
      this.cl.put(nodeType, priority);
   }

   public void m_21312_() {
   }

   public void m_21315_() {
   }

   protected C_661_ H() {
      return new C_661_(this);
   }

   public C_667_ I() {
      return this.bS;
   }

   public C_668_ J() {
      return this.m_275832_() instanceof Mob mob ? mob.J() : this.bT;
   }

   public C_664_ L() {
      return this.bU;
   }

   public C_758_ N() {
      return this.m_275832_() instanceof Mob mob ? mob.N() : this.bV;
   }

   @Nullable
   public C_524_ m_6688_() {
      C_507_ entity = this.m_146895_();
      if (!this.fZ() && entity instanceof Mob mob && entity.m_293117_()) {
         return mob;
      }

      return null;
   }

   public C_775_ O() {
      return this.cf;
   }

   @Nullable
   public C_524_ m_5448_() {
      return this.f_20951_;
   }

   @Nullable
   protected final C_524_ P() {
      return (C_524_)this.m_6274_().m_21952_(C_753_.f_26372_).orElse(null);
   }

   public void h(@Nullable C_524_ entitylivingbaseIn) {
      if (Reflector.ForgeHooks_onLivingChangeTarget.exists()) {
         LivingChangeTargetEvent changeTargetEvent = (LivingChangeTargetEvent)Reflector.ForgeHooks_onLivingChangeTarget
            .call(this, entitylivingbaseIn, LivingChangeTargetEvent.LivingTargetType.MOB_TARGET);
         if (!changeTargetEvent.isCanceled()) {
            this.f_20951_ = changeTargetEvent.getNewTarget();
         }
      } else {
         this.f_20951_ = entitylivingbaseIn;
      }
   }

   public boolean m_6549_(C_513_<?> typeIn) {
      return typeIn != C_513_.f_20453_;
   }

   public boolean a(C_1406_ itemIn) {
      return false;
   }

   public void Q() {
      this.m_146850_(C_141307_.f_157806_);
   }

   protected void a(SynchedEntityData.a builderIn) {
      super.a(builderIn);
      builderIn.a(b, (byte)0);
   }

   public int R() {
      return 80;
   }

   public void S() {
      this.m_323137_(this.v());
   }

   public void m_6075_() {
      super.m_6075_();
      this.m_9236_().m_46473_().m_6180_("mobBaseTick");
      if (this.m_6084_() && this.f_19796_.m_188503_(1000) < this.bQ++) {
         this.m_323815_();
         this.S();
      }

      this.m_9236_().m_46473_().m_7238_();
   }

   protected void m_6677_(C_489_ source) {
      this.m_323815_();
      super.m_6677_(source);
   }

   private void m_323815_() {
      this.bQ = -this.R();
   }

   protected int m_213860_() {
      if (this.bR > 0) {
         int i = this.bR;

         for (int j = 0; j < this.ch.size(); j++) {
            if (!((C_1391_)this.ch.get(j)).m_41619_() && this.bZ[j] <= 1.0F) {
               i += 1 + this.f_19796_.m_188503_(3);
            }
         }

         for (int k = 0; k < this.cg.size(); k++) {
            if (!((C_1391_)this.cg.get(k)).m_41619_() && this.bY[k] <= 1.0F) {
               i += 1 + this.f_19796_.m_188503_(3);
            }
         }

         if (!this.ci.m_41619_() && this.ca <= 1.0F) {
            i += 1 + this.f_19796_.m_188503_(3);
         }

         return i;
      } else {
         return this.bR;
      }
   }

   public void T() {
      if (this.m_9236_().f_46443_) {
         for (int i = 0; i < 20; i++) {
            double d0 = this.f_19796_.m_188583_() * 0.02;
            double d1 = this.f_19796_.m_188583_() * 0.02;
            double d2 = this.f_19796_.m_188583_() * 0.02;
            double d3 = 10.0;
            this.m_9236_().m_7106_(C_4759_.f_123759_, this.m_20165_(1.0) - d0 * 10.0, this.m_20187_() - d1 * 10.0, this.m_20262_(1.0) - d2 * 10.0, d0, d1, d2);
         }
      } else {
         this.m_9236_().m_7605_(this, (byte)20);
      }
   }

   public void m_7822_(byte id) {
      if (id == 20) {
         this.T();
      } else {
         super.m_7822_(id);
      }
   }

   public void m_8119_() {
      if (Config.isSmoothWorld() && this.canSkipUpdate()) {
         this.onUpdateMinimal();
      } else {
         super.m_8119_();
         if (!this.m_9236_().f_46443_ && this.f_19797_ % 5 == 0) {
            this.U();
         }
      }
   }

   protected void U() {
      boolean flag = !(this.m_6688_() instanceof Mob);
      boolean flag1 = !(this.m_20202_() instanceof C_1205_);
      this.bW.m_25360_(C_689_.MOVE, flag);
      this.bW.m_25360_(C_689_.JUMP, flag && flag1);
      this.bW.m_25360_(C_689_.LOOK, flag);
   }

   protected float m_5632_(float yawOffsetIn, float distanceIn) {
      this.cd.m_8121_();
      return distanceIn;
   }

   @Nullable
   protected C_123_ v() {
      return null;
   }

   public void m_7380_(C_4917_ compound) {
      super.m_7380_(compound);
      compound.m_128379_("CanPickUpLoot", this.fS());
      compound.m_128379_("PersistenceRequired", this.ck);
      C_4930_ listtag = new C_4930_();

      for (C_1391_ itemstack : this.ch) {
         if (!itemstack.m_41619_()) {
            listtag.add(itemstack.m_41739_(this.m_321891_()));
         } else {
            listtag.add(new C_4917_());
         }
      }

      compound.m_128365_("ArmorItems", listtag);
      C_4930_ listtag1 = new C_4930_();

      for (float f : this.bZ) {
         listtag1.add(C_4923_.m_128566_(f));
      }

      compound.m_128365_("ArmorDropChances", listtag1);
      C_4930_ listtag2 = new C_4930_();

      for (C_1391_ itemstack1 : this.cg) {
         if (!itemstack1.m_41619_()) {
            listtag2.add(itemstack1.m_41739_(this.m_321891_()));
         } else {
            listtag2.add(new C_4917_());
         }
      }

      compound.m_128365_("HandItems", listtag2);
      C_4930_ listtag3 = new C_4930_();

      for (float f1 : this.bY) {
         listtag3.add(C_4923_.m_128566_(f1));
      }

      compound.m_128365_("HandDropChances", listtag3);
      if (!this.ci.m_41619_()) {
         compound.m_128365_("body_armor_item", this.ci.m_41739_(this.m_321891_()));
         compound.m_128350_("body_armor_drop_chance", this.ca);
      }

      this.m_339731_(compound, this.co);
      compound.m_128379_("LeftHanded", this.ga());
      if (this.cm != null) {
         compound.m_128359_("DeathLootTable", this.cm.a().toString());
         if (this.cn != 0L) {
            compound.m_128356_("DeathLootTableSeed", this.cn);
         }
      }

      if (this.fZ()) {
         compound.m_128379_("NoAI", this.fZ());
      }

      if (this.spawnType != null) {
         compound.m_128359_("forge:spawn_type", this.spawnType.name());
      }
   }

   public void m_7378_(C_4917_ compound) {
      super.m_7378_(compound);
      if (compound.m_128425_("CanPickUpLoot", 1)) {
         this.a_(compound.m_128471_("CanPickUpLoot"));
      }

      this.ck = compound.m_128471_("PersistenceRequired");
      if (compound.m_128425_("ArmorItems", 9)) {
         C_4930_ listtag = compound.m_128437_("ArmorItems", 10);

         for (int i = 0; i < this.ch.size(); i++) {
            C_4917_ compoundtag = listtag.m_128728_(i);
            this.ch.set(i, C_1391_.m_318937_(this.m_321891_(), compoundtag));
         }
      }

      if (compound.m_128425_("ArmorDropChances", 9)) {
         C_4930_ listtag1 = compound.m_128437_("ArmorDropChances", 5);

         for (int j = 0; j < listtag1.size(); j++) {
            this.bZ[j] = listtag1.m_128775_(j);
         }
      }

      if (compound.m_128425_("HandItems", 9)) {
         C_4930_ listtag2 = compound.m_128437_("HandItems", 10);

         for (int k = 0; k < this.cg.size(); k++) {
            C_4917_ compoundtag1 = listtag2.m_128728_(k);
            this.cg.set(k, C_1391_.m_318937_(this.m_321891_(), compoundtag1));
         }
      }

      if (compound.m_128425_("HandDropChances", 9)) {
         C_4930_ listtag3 = compound.m_128437_("HandDropChances", 5);

         for (int l = 0; l < listtag3.size(); l++) {
            this.bY[l] = listtag3.m_128775_(l);
         }
      }

      if (compound.m_128425_("body_armor_item", 10)) {
         this.ci = (C_1391_)C_1391_.m_323951_(this.m_321891_(), compound.m_128469_("body_armor_item")).orElse(C_1391_.f_41583_);
         this.ca = compound.m_128457_("body_armor_drop_chance");
      } else {
         this.ci = C_1391_.f_41583_;
      }

      this.co = this.m_340395_(compound);
      this.v(compound.m_128471_("LeftHanded"));
      if (compound.m_128425_("DeathLootTable", 8)) {
         this.cm = C_5264_.a(C_256686_.f_314309_, ResourceLocation.a(compound.m_128461_("DeathLootTable")));
         this.cn = compound.m_128454_("DeathLootTableSeed");
      }

      this.u(compound.m_128471_("NoAI"));
      if (compound.m_128441_("forge:spawn_type")) {
         try {
            this.spawnType = C_529_.valueOf(compound.m_128461_("forge:spawn_type"));
         } catch (Exception var5) {
            compound.m_128473_("forge:spawn_type");
         }
      }
   }

   protected void m_7625_(C_489_ damageSourceIn, boolean recentHitIn) {
      super.m_7625_(damageSourceIn, recentHitIn);
      this.cm = null;
   }

   public final C_5264_<C_2822_> m_5743_() {
      return this.cm == null ? this.V() : this.cm;
   }

   protected C_5264_<C_2822_> V() {
      return super.m_5743_();
   }

   public long m_287233_() {
      return this.cn;
   }

   public void E(float amount) {
      this.f_20902_ = amount;
   }

   public void F(float amount) {
      this.f_20901_ = amount;
   }

   public void G(float amount) {
      this.f_20900_ = amount;
   }

   public void m_7910_(float speedIn) {
      super.m_7910_(speedIn);
      this.E(speedIn);
   }

   public void W() {
      this.N().m_26573_();
      this.G(0.0F);
      this.F(0.0F);
      this.m_7910_(0.0F);
   }

   public void m_8107_() {
      super.m_8107_();
      this.m_9236_().m_46473_().m_6180_("looting");
      boolean mobGriefing = this.m_9236_().m_46469_().m_46207_(C_1583_.f_46132_);
      if (Reflector.ForgeEventFactory_getMobGriefingEvent.exists()) {
         mobGriefing = Reflector.callBoolean(Reflector.ForgeEventFactory_getMobGriefingEvent, this.m_9236_(), this);
      }

      if (!this.m_9236_().f_46443_ && this.fS() && this.m_6084_() && !this.f_20890_ && mobGriefing) {
         C_4713_ vec3i = this.X();

         for (C_976_ itementity : this.m_9236_()
            .m_45976_(C_976_.class, this.m_20191_().m_82377_((double)vec3i.m_123341_(), (double)vec3i.m_123342_(), (double)vec3i.m_123343_()))) {
            if (!itementity.m_213877_() && !itementity.m_32055_().m_41619_() && !itementity.m_32063_() && this.k(itementity.m_32055_())) {
               this.b(itementity);
            }
         }
      }

      this.m_9236_().m_46473_().m_7238_();
   }

   protected C_4713_ X() {
      return cb;
   }

   protected void b(C_976_ itemEntity) {
      C_1391_ itemstack = itemEntity.m_32055_();
      C_1391_ itemstack1 = this.i(itemstack.m_41777_());
      if (!itemstack1.m_41619_()) {
         this.m_21053_(itemEntity);
         this.m_7938_(itemEntity, itemstack1.m_41613_());
         itemstack.m_41774_(itemstack1.m_41613_());
         if (itemstack.m_41619_()) {
            itemEntity.m_146870_();
         }
      }
   }

   public C_1391_ i(C_1391_ itemStackIn) {
      C_516_ equipmentslot = this.m_147233_(itemStackIn);
      C_1391_ itemstack = this.m_6844_(equipmentslot);
      boolean flag = this.b(itemStackIn, itemstack);
      if (equipmentslot.m_254934_() && !flag) {
         equipmentslot = C_516_.MAINHAND;
         itemstack = this.m_6844_(equipmentslot);
         flag = itemstack.m_41619_();
      }

      if (flag && this.j(itemStackIn)) {
         double d0 = (double)this.f(equipmentslot);
         if (!itemstack.m_41619_() && (double)Math.max(this.f_19796_.m_188501_() - 0.1F, 0.0F) < d0) {
            this.m_19983_(itemstack);
         }

         C_1391_ itemstack1 = equipmentslot.m_338803_(itemStackIn);
         this.m_21128_(equipmentslot, itemstack1);
         return itemstack1;
      } else {
         return C_1391_.f_41583_;
      }
   }

   protected void m_21128_(C_516_ slotIn, C_1391_ itemStackIn) {
      this.m_21035_(slotIn, itemStackIn);
      this.e(slotIn);
      this.ck = true;
   }

   public void e(C_516_ slotIn) {
      switch (slotIn.m_20743_()) {
         case HAND:
            this.bY[slotIn.m_20749_()] = 2.0F;
            break;
         case HUMANOID_ARMOR:
            this.bZ[slotIn.m_20749_()] = 2.0F;
            break;
         case ANIMAL_ARMOR:
            this.ca = 2.0F;
      }
   }

   protected boolean b(C_1391_ candidate, C_1391_ existing) {
      if (existing.m_41619_()) {
         return true;
      } else if (candidate.m_41720_() instanceof C_1425_) {
         if (!(existing.m_41720_() instanceof C_1425_)) {
            return true;
         } else {
            double d2 = this.o(candidate);
            double d3 = this.o(existing);
            return d2 != d3 ? d2 > d3 : this.c(candidate, existing);
         }
      } else if (candidate.m_41720_() instanceof C_1330_ && existing.m_41720_() instanceof C_1330_) {
         return this.c(candidate, existing);
      } else if (candidate.m_41720_() instanceof C_1349_ && existing.m_41720_() instanceof C_1349_) {
         return this.c(candidate, existing);
      } else if (candidate.m_41720_() instanceof C_1313_ armoritem) {
         if (C_1522_.m_340193_(existing, C_336480_.f_337286_)) {
            return false;
         } else if (!(existing.m_41720_() instanceof C_1313_)) {
            return true;
         } else {
            C_1313_ armoritem1 = (C_1313_)existing.m_41720_();
            if (armoritem.m_40404_() != armoritem1.m_40404_()) {
               return armoritem.m_40404_() > armoritem1.m_40404_();
            } else {
               return armoritem.m_40405_() != armoritem1.m_40405_() ? armoritem.m_40405_() > armoritem1.m_40405_() : this.c(candidate, existing);
            }
         }
      } else {
         if (candidate.m_41720_() instanceof C_1351_) {
            if (existing.m_41720_() instanceof C_1325_) {
               return true;
            }

            if (existing.m_41720_() instanceof C_1351_) {
               double d1 = this.o(candidate);
               double d0 = this.o(existing);
               if (d1 != d0) {
                  return d1 > d0;
               }

               return this.c(candidate, existing);
            }
         }

         return false;
      }
   }

   private double o(C_1391_ itemStackIn) {
      C_313447_ itemattributemodifiers = (C_313447_)itemStackIn.m_322304_(C_313616_.f_316119_, C_313447_.f_314473_);
      return itemattributemodifiers.m_324178_(this.m_245892_(C_559_.f_22281_), C_516_.MAINHAND);
   }

   public boolean c(C_1391_ itemStackIn, C_1391_ itemStack2In) {
      return itemStackIn.m_41773_() < itemStack2In.m_41773_() ? true : p(itemStackIn) && !p(itemStack2In);
   }

   private static boolean p(C_1391_ itemStackIn) {
      C_313470_ datacomponentmap = itemStackIn.m_318732_();
      int i = datacomponentmap.m_319491_();
      return i > 1 || i == 1 && !datacomponentmap.m_321946_(C_313616_.f_313972_);
   }

   public boolean j(C_1391_ stack) {
      return true;
   }

   public boolean k(C_1391_ itemStackIn) {
      return this.j(itemStackIn);
   }

   public boolean h(double distanceToClosestPlayer) {
      return true;
   }

   public boolean Y() {
      return this.m_20159_();
   }

   protected boolean Z() {
      return false;
   }

   public void m_6043_() {
      if (this.m_9236_().m_46791_() == C_468_.PEACEFUL && this.Z()) {
         this.m_146870_();
      } else if (!this.fT() && !this.Y()) {
         C_507_ entity = this.m_9236_().m_45930_(this, -1.0);
         if (Reflector.ForgeEventFactory_canEntityDespawn.exists()) {
            Object result = Reflector.ForgeEventFactory_canEntityDespawn.call(this, this.m_9236_());
            if (result == ReflectorForge.EVENT_RESULT_DENY) {
               this.f_20891_ = 0;
               entity = null;
            } else if (result == ReflectorForge.EVENT_RESULT_ALLOW) {
               this.m_146870_();
               entity = null;
            }
         }

         if (entity != null) {
            double d0 = entity.m_20280_(this);
            int i = this.m_6095_().m_20674_().m_21611_();
            int j = i * i;
            if (d0 > (double)j && this.h(d0)) {
               this.m_146870_();
            }

            int k = this.m_6095_().m_20674_().m_21612_();
            int l = k * k;
            if (this.f_20891_ > 600 && this.f_19796_.m_188503_(800) == 0 && d0 > (double)l && this.h(d0)) {
               this.m_146870_();
            } else if (d0 < (double)l) {
               this.f_20891_ = 0;
            }
         }
      } else {
         this.f_20891_ = 0;
      }
   }

   protected final void m_6140_() {
      this.f_20891_++;
      C_442_ profilerfiller = this.m_9236_().m_46473_();
      profilerfiller.m_6180_("sensing");
      this.cf.m_26789_();
      profilerfiller.m_7238_();
      int i = this.f_19797_ + this.m_19879_();
      if (i % 2 != 0 && this.f_19797_ > 1) {
         profilerfiller.m_6180_("targetSelector");
         this.bX.m_186081_(false);
         profilerfiller.m_7238_();
         profilerfiller.m_6180_("goalSelector");
         this.bW.m_186081_(false);
         profilerfiller.m_7238_();
      } else {
         profilerfiller.m_6180_("targetSelector");
         this.bX.m_25373_();
         profilerfiller.m_7238_();
         profilerfiller.m_6180_("goalSelector");
         this.bW.m_25373_();
         profilerfiller.m_7238_();
      }

      profilerfiller.m_6180_("navigation");
      this.bV.m_7638_();
      profilerfiller.m_7238_();
      profilerfiller.m_6180_("mob tick");
      this.ab();
      profilerfiller.m_7238_();
      profilerfiller.m_6180_("controls");
      profilerfiller.m_6180_("move");
      this.bT.m_8126_();
      profilerfiller.m_6182_("look");
      this.bS.m_8128_();
      profilerfiller.m_6182_("jump");
      this.bU.m_8124_();
      profilerfiller.m_7238_();
      profilerfiller.m_7238_();
      this.aa();
   }

   protected void aa() {
      C_5143_.a(this.m_9236_(), this, this.bW);
   }

   protected void ab() {
   }

   public int ac() {
      return 40;
   }

   public int ae() {
      return 75;
   }

   protected void af() {
      float f = (float)this.ae();
      float f1 = this.m_6080_();
      float f2 = Mth.g(this.f_20883_ - f1);
      float f3 = Mth.a(Mth.g(this.f_20883_ - f1), -f, f);
      float f4 = f1 + f2 - f3;
      this.m_5616_(f4);
   }

   public int fM() {
      return 10;
   }

   public void a(C_507_ entityIn, float maxYawIncrease, float maxPitchIncrease) {
      double d0 = entityIn.m_20185_() - this.m_20185_();
      double d2 = entityIn.m_20189_() - this.m_20189_();
      double d1;
      if (entityIn instanceof C_524_ livingentity) {
         d1 = livingentity.m_20188_() - this.m_20188_();
      } else {
         d1 = (entityIn.m_20191_().f_82289_ + entityIn.m_20191_().f_82292_) / 2.0 - this.m_20188_();
      }

      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = (float)(Mth.d(d2, d0) * 180.0 / (float) Math.PI) - 90.0F;
      float f1 = (float)(-(Mth.d(d1, d3) * 180.0 / (float) Math.PI));
      this.m_146926_(this.a(this.m_146909_(), f1, maxPitchIncrease));
      this.m_146922_(this.a(this.m_146908_(), f, maxYawIncrease));
   }

   private float a(float angle, float targetAngle, float maxIncrease) {
      float f = Mth.g(targetAngle - angle);
      if (f > maxIncrease) {
         f = maxIncrease;
      }

      if (f < -maxIncrease) {
         f = -maxIncrease;
      }

      return angle + f;
   }

   public static boolean a(C_513_<? extends Mob> typeIn, C_1598_ worldIn, C_529_ reason, C_4675_ pos, C_212974_ randomIn) {
      C_4675_ blockpos = pos.m_7495_();
      return reason == C_529_.SPAWNER || worldIn.a_(blockpos).m_60643_(worldIn, blockpos, typeIn);
   }

   public boolean a(C_1598_ worldIn, C_529_ spawnReasonIn) {
      return true;
   }

   public boolean a(C_1599_ worldIn) {
      return !worldIn.m_46855_(this.m_20191_()) && worldIn.m_45784_(this);
   }

   public int fN() {
      return 4;
   }

   public boolean r(int sizeIn) {
      return false;
   }

   public int m_6056_() {
      if (this.m_5448_() == null) {
         return this.m_320747_(0.0F);
      } else {
         int i = (int)(this.m_21223_() - this.m_21233_() * 0.33F);
         i -= (3 - this.m_9236_().m_46791_().m_19028_()) * 4;
         if (i < 0) {
            i = 0;
         }

         return this.m_320747_((float)i);
      }
   }

   public Iterable<C_1391_> m_21487_() {
      return this.cg;
   }

   public Iterable<C_1391_> m_21151_() {
      return this.ch;
   }

   public C_1391_ fO() {
      return this.ci;
   }

   public boolean m_30729_(C_516_ slotIn) {
      return slotIn != C_516_.BODY;
   }

   public boolean fP() {
      return !this.m_6844_(C_516_.BODY).m_41619_();
   }

   public boolean l(C_1391_ itemStackIn) {
      return false;
   }

   public void m(C_1391_ itemStackIn) {
      this.m_21128_(C_516_.BODY, itemStackIn);
   }

   public Iterable<C_1391_> m_322068_() {
      return (Iterable<C_1391_>)(this.ci.m_41619_() ? this.ch : Iterables.concat(this.ch, List.of(this.ci)));
   }

   public C_1391_ m_6844_(C_516_ slotIn) {
      return switch (slotIn.m_20743_()) {
         case HAND -> (C_1391_)this.cg.get(slotIn.m_20749_());
         case HUMANOID_ARMOR -> (C_1391_)this.ch.get(slotIn.m_20749_());
         case ANIMAL_ARMOR -> this.ci;
         default -> throw new MatchException(null, null);
      };
   }

   public void m_21035_(C_516_ slotIn, C_1391_ itemStackIn) {
      this.m_181122_(itemStackIn);
      switch (slotIn.m_20743_()) {
         case HAND:
            this.m_238392_(slotIn, (C_1391_)this.cg.set(slotIn.m_20749_(), itemStackIn), itemStackIn);
            break;
         case HUMANOID_ARMOR:
            this.m_238392_(slotIn, (C_1391_)this.ch.set(slotIn.m_20749_(), itemStackIn), itemStackIn);
            break;
         case ANIMAL_ARMOR:
            C_1391_ itemstack = this.ci;
            this.ci = itemStackIn;
            this.m_238392_(slotIn, itemstack, itemStackIn);
      }
   }

   protected void m_7472_(C_12_ worldIn, C_489_ sourceIn, boolean recentlyHitIn) {
      super.m_7472_(worldIn, sourceIn, recentlyHitIn);

      for (C_516_ equipmentslot : C_516_.values()) {
         C_1391_ itemstack = this.m_6844_(equipmentslot);
         float f = this.f(equipmentslot);
         if (f != 0.0F) {
            boolean flag = f > 1.0F;
            C_507_ entity = sourceIn.m_7639_();
            if (entity instanceof C_524_) {
               C_524_ livingentity = (C_524_)entity;
               if (this.m_9236_() instanceof C_12_ serverlevel) {
                  f = C_1522_.m_339734_(serverlevel, livingentity, sourceIn, f);
               }
            }

            if (!itemstack.m_41619_() && !C_1522_.m_340193_(itemstack, C_336480_.f_337159_) && (recentlyHitIn || flag) && this.f_19796_.m_188501_() < f) {
               if (!flag && itemstack.m_41763_()) {
                  itemstack.m_41721_(itemstack.m_41776_() - this.f_19796_.m_188503_(1 + this.f_19796_.m_188503_(Math.max(itemstack.m_41776_() - 3, 1))));
               }

               this.m_19983_(itemstack);
               this.m_21035_(equipmentslot, C_1391_.f_41583_);
            }
         }
      }
   }

   protected float f(C_516_ slotIn) {
      return switch (slotIn.m_20743_()) {
         case HAND -> this.bY[slotIn.m_20749_()];
         case HUMANOID_ARMOR -> this.bZ[slotIn.m_20749_()];
         case ANIMAL_ARMOR -> this.ca;
         default -> throw new MatchException(null, null);
      };
   }

   public void fQ() {
      this.c(goalIn -> true);
   }

   public Set<C_516_> c(Predicate<C_1391_> checkIn) {
      Set<C_516_> set = new HashSet();

      for (C_516_ equipmentslot : C_516_.values()) {
         C_1391_ itemstack = this.m_6844_(equipmentslot);
         if (!itemstack.m_41619_()) {
            if (!checkIn.test(itemstack)) {
               set.add(equipmentslot);
            } else {
               double d0 = (double)this.f(equipmentslot);
               if (d0 > 1.0) {
                  this.m_21035_(equipmentslot, C_1391_.f_41583_);
                  this.m_19983_(itemstack);
               }
            }
         }
      }

      return set;
   }

   private C_286926_ a(C_12_ levelIn) {
      return new C_286924_(levelIn).m_287286_(C_2974_.f_81460_, this.dm()).m_287286_(C_2974_.f_81455_, this).m_287235_(C_2973_.f_313897_);
   }

   public void a(C_313465_ tableIn) {
      this.a(tableIn.f_316700_(), tableIn.f_315505_());
   }

   public void a(C_5264_<C_2822_> keyIn, Map<C_516_, Float> mapIn) {
      if (this.m_9236_() instanceof C_12_ serverlevel) {
         this.m_319719_(keyIn, this.a(serverlevel), mapIn);
      }
   }

   protected void a(C_212974_ randomIn, C_469_ difficulty) {
      if (randomIn.m_188501_() < 0.15F * difficulty.m_19057_()) {
         int i = randomIn.m_188503_(2);
         float f = this.m_9236_().m_46791_() == C_468_.HARD ? 0.1F : 0.25F;
         if (randomIn.m_188501_() < 0.095F) {
            i++;
         }

         if (randomIn.m_188501_() < 0.095F) {
            i++;
         }

         if (randomIn.m_188501_() < 0.095F) {
            i++;
         }

         boolean flag = true;

         for (C_516_ equipmentslot : C_516_.values()) {
            if (equipmentslot.m_20743_() == C_517_.HUMANOID_ARMOR) {
               C_1391_ itemstack = this.m_6844_(equipmentslot);
               if (!flag && randomIn.m_188501_() < f) {
                  break;
               }

               flag = false;
               if (itemstack.m_41619_()) {
                  C_1381_ item = a(equipmentslot, i);
                  if (item != null) {
                     this.m_21035_(equipmentslot, new C_1391_(item));
                  }
               }
            }
         }
      }
   }

   @Nullable
   public static C_1381_ a(C_516_ slotIn, int chance) {
      switch (slotIn) {
         case HEAD:
            if (chance == 0) {
               return C_1394_.f_42407_;
            } else if (chance == 1) {
               return C_1394_.f_42476_;
            } else if (chance == 2) {
               return C_1394_.f_42464_;
            } else if (chance == 3) {
               return C_1394_.f_42468_;
            } else if (chance == 4) {
               return C_1394_.f_42472_;
            }
         case CHEST:
            if (chance == 0) {
               return C_1394_.f_42408_;
            } else if (chance == 1) {
               return C_1394_.f_42477_;
            } else if (chance == 2) {
               return C_1394_.f_42465_;
            } else if (chance == 3) {
               return C_1394_.f_42469_;
            } else if (chance == 4) {
               return C_1394_.f_42473_;
            }
         case LEGS:
            if (chance == 0) {
               return C_1394_.f_42462_;
            } else if (chance == 1) {
               return C_1394_.f_42478_;
            } else if (chance == 2) {
               return C_1394_.f_42466_;
            } else if (chance == 3) {
               return C_1394_.f_42470_;
            } else if (chance == 4) {
               return C_1394_.f_42474_;
            }
         case FEET:
            if (chance == 0) {
               return C_1394_.f_42463_;
            } else if (chance == 1) {
               return C_1394_.f_42479_;
            } else if (chance == 2) {
               return C_1394_.f_42467_;
            } else if (chance == 3) {
               return C_1394_.f_42471_;
            } else if (chance == 4) {
               return C_1394_.f_42475_;
            }
         default:
            return null;
      }
   }

   protected void a(C_1618_ worldIn, C_212974_ randomIn, C_469_ difficultyIn) {
      this.b(worldIn, randomIn, difficultyIn);

      for (C_516_ equipmentslot : C_516_.values()) {
         if (equipmentslot.m_20743_() == C_517_.HUMANOID_ARMOR) {
            this.a(worldIn, randomIn, equipmentslot, difficultyIn);
         }
      }
   }

   protected void b(C_1618_ worldIn, C_212974_ randomIn, C_469_ difficultyIn) {
      this.a(worldIn, C_516_.MAINHAND, randomIn, 0.25F, difficultyIn);
   }

   protected void a(C_1618_ worldIn, C_212974_ randomIn, C_516_ slotIn, C_469_ difficultyIn) {
      this.a(worldIn, slotIn, randomIn, 0.5F, difficultyIn);
   }

   private void a(C_1618_ worldIn, C_516_ slotIn, C_212974_ randomIn, float difficultyMulIn, C_469_ difficultyIn) {
      C_1391_ itemstack = this.m_6844_(slotIn);
      if (!itemstack.m_41619_() && randomIn.m_188501_() < difficultyMulIn * difficultyIn.m_19057_()) {
         C_1522_.m_338695_(itemstack, worldIn.m_9598_(), C_336472_.f_337458_, difficultyIn, randomIn);
         this.m_21035_(slotIn, itemstack);
      }
   }

   @Nullable
   public C_542_ a(C_1618_ worldIn, C_469_ difficultyIn, C_529_ reason, @Nullable C_542_ spawnDataIn) {
      C_212974_ randomsource = worldIn.m_213780_();
      C_553_ attributeinstance = (C_553_)Objects.requireNonNull(this.m_21051_(C_559_.f_22277_));
      if (!attributeinstance.b(bP)) {
         attributeinstance.m_22125_(new C_555_(bP, randomsource.m_216328_(0.0, 0.11485000000000001), C_556_.ADD_MULTIPLIED_BASE));
      }

      this.v(randomsource.m_188501_() < 0.05F);
      this.spawnType = reason;
      return spawnDataIn;
   }

   public void fR() {
      this.ck = true;
   }

   public void m_21409_(C_516_ slotIn, float chance) {
      switch (slotIn.m_20743_()) {
         case HAND:
            this.bY[slotIn.m_20749_()] = chance;
            break;
         case HUMANOID_ARMOR:
            this.bZ[slotIn.m_20749_()] = chance;
            break;
         case ANIMAL_ARMOR:
            this.ca = chance;
      }
   }

   public boolean fS() {
      return this.cj;
   }

   public void a_(boolean canPickup) {
      this.cj = canPickup;
   }

   public boolean m_7066_(C_1391_ itemstackIn) {
      C_516_ equipmentslot = this.m_147233_(itemstackIn);
      return this.m_6844_(equipmentslot).m_41619_() && this.fS();
   }

   public boolean fT() {
      return this.ck;
   }

   public final C_471_ m_6096_(C_1141_ player, C_470_ hand) {
      if (!this.m_6084_()) {
         return C_471_.PASS;
      } else {
         C_471_ interactionresult = this.c(player, hand);
         if (interactionresult.m_19077_()) {
            this.m_146852_(C_141307_.f_223708_, player);
            return interactionresult;
         } else {
            C_471_ interactionresult1 = super.m_6096_(player, hand);
            if (interactionresult1 != C_471_.PASS) {
               return interactionresult1;
            } else {
               interactionresult = this.b(player, hand);
               if (interactionresult.m_19077_()) {
                  this.m_146852_(C_141307_.f_223708_, player);
                  return interactionresult;
               } else {
                  return C_471_.PASS;
               }
            }
         }
      }
   }

   private C_471_ c(C_1141_ playerIn, C_470_ handIn) {
      C_1391_ itemstack = playerIn.m_21120_(handIn);
      if (itemstack.m_150930_(C_1394_.f_42656_)) {
         C_471_ interactionresult = itemstack.m_41647_(playerIn, this, handIn);
         if (interactionresult.m_19077_()) {
            return interactionresult;
         }
      }

      if (itemstack.m_41720_() instanceof C_1420_) {
         if (this.m_9236_() instanceof C_12_) {
            C_1420_ spawneggitem = (C_1420_)itemstack.m_41720_();
            Optional<Mob> optional = spawneggitem.a(playerIn, this, this.m_6095_(), (C_12_)this.m_9236_(), this.dm(), itemstack);
            optional.ifPresent(mobIn -> this.a(playerIn, mobIn));
            return optional.isPresent() ? C_471_.SUCCESS : C_471_.PASS;
         } else {
            return C_471_.CONSUME;
         }
      } else {
         return C_471_.PASS;
      }
   }

   protected void a(C_1141_ playerIn, Mob child) {
   }

   protected C_471_ b(C_1141_ playerIn, C_470_ handIn) {
      return C_471_.PASS;
   }

   public boolean fU() {
      return this.a(this.m_20183_());
   }

   public boolean a(C_4675_ pos) {
      return this.cq == -1.0F ? true : this.cp.m_123331_(pos) < (double)(this.cq * this.cq);
   }

   public void a(C_4675_ pos, int distance) {
      this.cp = pos;
      this.cq = (float)distance;
   }

   public C_4675_ fV() {
      return this.cp;
   }

   public float fW() {
      return this.cq;
   }

   public void fX() {
      this.cq = -1.0F;
   }

   public boolean fY() {
      return this.cq != -1.0F;
   }

   @Nullable
   public <T extends Mob> T a(C_513_<T> typeIn, boolean equipmentIn) {
      if (this.m_213877_()) {
         return null;
      } else {
         T t = (T)typeIn.m_20615_(this.m_9236_());
         if (t == null) {
            return null;
         } else {
            t.m_20359_(this);
            t.a(this.m_6162_());
            t.u(this.fZ());
            if (this.m_8077_()) {
               t.m_6593_(this.m_7770_());
               t.m_20340_(this.m_20151_());
            }

            if (this.fT()) {
               t.fR();
            }

            t.m_20331_(this.m_20147_());
            if (equipmentIn) {
               t.a_(this.fS());

               for (C_516_ equipmentslot : C_516_.values()) {
                  C_1391_ itemstack = this.m_6844_(equipmentslot);
                  if (!itemstack.m_41619_()) {
                     t.m_21035_(equipmentslot, itemstack.m_278832_());
                     t.m_21409_(equipmentslot, this.f(equipmentslot));
                  }
               }
            }

            this.m_9236_().m_7967_(t);
            if (this.m_20159_()) {
               C_507_ entity = this.m_20202_();
               this.m_8127_();
               t.m_7998_(entity, true);
            }

            this.m_146870_();
            return t;
         }
      }
   }

   @Nullable
   public C_336538_ m_338492_() {
      return this.co;
   }

   public void m_338401_(@Nullable C_336538_ leashDataIn) {
      this.co = leashDataIn;
   }

   public void m_21455_(boolean sendPacket, boolean dropLead) {
      super.m_21455_(sendPacket, dropLead);
      if (this.m_338492_() == null) {
         this.fX();
      }
   }

   public void m_339671_() {
      super.m_339671_();
      this.bW.m_25355_(C_689_.MOVE);
   }

   public boolean m_6573_() {
      return !(this instanceof C_1004_);
   }

   public boolean m_7998_(C_507_ entityIn, boolean force) {
      boolean flag = super.m_7998_(entityIn, force);
      if (flag && this.m_339418_()) {
         this.m_21455_(true, true);
      }

      return flag;
   }

   public boolean m_21515_() {
      return super.m_21515_() && !this.fZ();
   }

   public void u(boolean disable) {
      byte b0 = this.ao.<Byte>a(b);
      this.ao.a(b, disable ? (byte)(b0 | 1) : (byte)(b0 & -2));
   }

   public void v(boolean leftHanded) {
      byte b0 = this.ao.<Byte>a(b);
      this.ao.a(b, leftHanded ? (byte)(b0 | 2) : (byte)(b0 & -3));
   }

   public void w(boolean hasAggro) {
      byte b0 = this.ao.<Byte>a(b);
      this.ao.a(b, hasAggro ? (byte)(b0 | 4) : (byte)(b0 & -5));
   }

   public boolean fZ() {
      return (this.ao.<Byte>a(b) & 1) != 0;
   }

   public boolean ga() {
      return (this.ao.<Byte>a(b) & 2) != 0;
   }

   public boolean gb() {
      return (this.ao.<Byte>a(b) & 4) != 0;
   }

   public void a(boolean childZombie) {
   }

   public C_520_ m_5737_() {
      return this.ga() ? C_520_.LEFT : C_520_.RIGHT;
   }

   public boolean i(C_524_ entityIn) {
      return this.gc().m_82381_(entityIn.m_293919_());
   }

   protected C_3040_ gc() {
      C_507_ entity = this.m_20202_();
      C_3040_ aabb;
      if (entity != null) {
         C_3040_ aabb1 = entity.m_20191_();
         C_3040_ aabb2 = this.m_20191_();
         aabb = new C_3040_(
            Math.min(aabb2.f_82288_, aabb1.f_82288_),
            aabb2.f_82289_,
            Math.min(aabb2.f_82290_, aabb1.f_82290_),
            Math.max(aabb2.f_82291_, aabb1.f_82291_),
            aabb2.f_82292_,
            Math.max(aabb2.f_82293_, aabb1.f_82293_)
         );
      } else {
         aabb = this.m_20191_();
      }

      return aabb.m_82377_(cc, 0.0, cc);
   }

   public boolean m_7327_(C_507_ entityIn) {
      float f = (float)this.m_246858_(C_559_.f_22281_);
      C_489_ damagesource = this.m_269291_().m_269333_(this);
      if (this.m_9236_() instanceof C_12_ serverlevel) {
         f = C_1522_.m_338960_(serverlevel, this.m_338776_(), entityIn, damagesource, f);
      }

      boolean flag = entityIn.m_6469_(damagesource, f);
      if (flag) {
         float f1 = this.m_338419_(entityIn, damagesource);
         if (f1 > 0.0F && entityIn instanceof C_524_ livingentity) {
            livingentity.m_147240_(
               (double)(f1 * 0.5F), (double)Mth.a(this.m_146908_() * (float) (Math.PI / 180.0)), (double)(-Mth.b(this.m_146908_() * (float) (Math.PI / 180.0)))
            );
            this.i(this.dr().d(0.6, 1.0, 0.6));
         }

         if (this.m_9236_() instanceof C_12_ serverlevel1) {
            C_1522_.m_338760_(serverlevel1, entityIn, damagesource);
         }

         this.m_21335_(entityIn);
         this.gd();
      }

      return flag;
   }

   protected void gd() {
   }

   protected boolean ge() {
      if (this.m_9236_().m_46461_() && !this.m_9236_().f_46443_) {
         float f = this.m_213856_();
         C_4675_ blockpos = C_4675_.m_274561_(this.m_20185_(), this.m_20188_(), this.m_20189_());
         boolean flag = this.m_20071_() || this.f_146808_ || this.f_146809_;
         if (f > 0.5F && this.f_19796_.m_188501_() * 30.0F < (f - 0.4F) * 2.0F && !flag && this.m_9236_().m_45527_(blockpos)) {
            return true;
         }
      }

      return false;
   }

   protected void m_203347_(C_203208_<C_2690_> fluidTag) {
      this.jumpInLiquidInternal(() -> super.m_203347_(fluidTag));
   }

   private void jumpInLiquidInternal(Runnable onSuper) {
      if (this.N().m_26576_()) {
         onSuper.run();
      } else {
         this.i(this.dr().b(0.0, 0.3, 0.0));
      }
   }

   @Override
   public void jumpInFluid(FluidType type) {
      this.jumpInLiquidInternal(() -> IForgeLivingEntity.super.jumpInFluid(type));
   }

   public final C_529_ getSpawnType() {
      return this.spawnType;
   }

   public final boolean isSpawnCancelled() {
      return this.spawnCancelled;
   }

   public final void setSpawnCancelled(boolean cancel) {
      if (this.isAddedToWorld()) {
         throw new UnsupportedOperationException("Late invocations of Mob#setSpawnCancelled are not permitted.");
      } else {
         this.spawnCancelled = cancel;
      }
   }

   @VisibleForTesting
   public void gf() {
      this.d(goalIn -> true);
      this.m_6274_().m_147343_();
   }

   public void d(Predicate<C_688_> predicateIn) {
      this.bW.m_262460_(predicateIn);
   }

   protected void m_6089_() {
      super.m_6089_();
      this.m_323629_().forEach(itemStackIn -> {
         if (!itemStackIn.m_41619_()) {
            itemStackIn.m_41764_(0);
         }
      });
   }

   @Nullable
   public C_1391_ m_142340_() {
      C_1420_ spawneggitem = C_1420_.m_43213_(this.m_6095_());
      return spawneggitem == null ? null : new C_1391_(spawneggitem);
   }

   private boolean canSkipUpdate() {
      if (this.m_6162_()) {
         return false;
      } else if (this.f_146799_ > 0) {
         return false;
      } else if (this.f_19797_ < 20) {
         return false;
      } else {
         List listPlayers = this.getListPlayers(this.m_20193_());
         if (listPlayers == null) {
            return false;
         } else if (listPlayers.size() != 1) {
            return false;
         } else {
            C_507_ player = (C_507_)listPlayers.get(0);
            double dx = Math.max(Math.abs(this.m_20185_() - player.m_20185_()) - 16.0, 0.0);
            double dz = Math.max(Math.abs(this.m_20189_() - player.m_20189_()) - 16.0, 0.0);
            double distSq = dx * dx + dz * dz;
            return !this.m_6783_(distSq);
         }
      }
   }

   private List getListPlayers(C_1596_ entityWorld) {
      C_1596_ world = this.m_20193_();
      if (world instanceof ClientLevel worldClient) {
         return worldClient.m_6907_();
      } else {
         return world instanceof C_12_ worldServer ? worldServer.m_6907_() : null;
      }
   }

   private void onUpdateMinimal() {
      this.f_20891_++;
      if (this instanceof C_1027_) {
         float brightness = this.m_213856_();
         boolean raider = this instanceof C_1189_;
         if (brightness > 0.5F || raider) {
            this.f_20891_ += 2;
         }
      }
   }
}
