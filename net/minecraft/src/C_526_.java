package net.minecraft.src;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.src.C_516_.C_517_;
import net.minecraft.src.C_555_.C_556_;
import net.minecraft.src.C_688_.C_689_;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.fluids.FluidType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public abstract class C_526_ extends C_524_ implements C_313491_, C_336597_, C_271030_, IForgeLivingEntity {
   private static final C_5225_ f_21340_;
   private static final int f_147266_ = 1;
   private static final int f_147267_ = 2;
   private static final int f_147268_ = 4;
   protected static final int f_147265_ = 1;
   private static final C_4713_ f_217048_;
   public static final float f_147269_ = 0.15F;
   public static final float f_147261_ = 0.55F;
   public static final float f_147262_ = 0.5F;
   public static final float f_147263_ = 0.25F;
   public static final float f_182333_ = 0.085F;
   public static final float f_337144_ = 1.0F;
   public static final int f_217047_ = 2;
   public static final int f_186008_ = 2;
   private static final double f_290867_;
   protected static final C_5265_ f_337189_;
   public int f_21363_;
   protected int f_21364_;
   protected C_667_ f_21365_;
   protected C_668_ f_21342_;
   protected C_664_ f_21343_;
   private final C_661_ f_21361_;
   protected C_758_ f_21344_;
   protected final C_690_ f_21345_;
   protected final C_690_ f_21346_;
   @Nullable
   private C_524_ f_21362_;
   private final C_775_ f_21349_;
   private final C_4702_ f_21350_;
   protected final float[] f_21347_;
   private final C_4702_ f_21351_;
   protected final float[] f_21348_;
   private C_1391_ f_314973_;
   protected float f_315062_;
   private boolean f_21352_;
   private boolean f_21353_;
   private final Map f_21354_;
   @Nullable
   private C_5264_ f_21355_;
   private long f_21356_;
   @Nullable
   private C_336597_.C_336538_ f_337544_;
   private C_4675_ f_21360_;
   private float f_21341_;
   private C_529_ spawnType;
   private boolean spawnCancelled;

   protected C_526_(C_513_ type, C_1596_ worldIn) {
      super(type, worldIn);
      this.f_21350_ = C_4702_.m_122780_(2, C_1391_.f_41583_);
      this.f_21347_ = new float[2];
      this.f_21351_ = C_4702_.m_122780_(4, C_1391_.f_41583_);
      this.f_21348_ = new float[4];
      this.f_314973_ = C_1391_.f_41583_;
      this.f_21354_ = Maps.newEnumMap(C_313716_.class);
      this.f_21360_ = C_4675_.f_121853_;
      this.f_21341_ = -1.0F;
      this.spawnCancelled = false;
      this.f_21345_ = new C_690_(worldIn.m_46658_());
      this.f_21346_ = new C_690_(worldIn.m_46658_());
      this.f_21365_ = new C_667_(this);
      this.f_21342_ = new C_668_(this);
      this.f_21343_ = new C_664_(this);
      this.f_21361_ = this.m_7560_();
      this.f_21344_ = this.m_6037_(worldIn);
      this.f_21349_ = new C_775_(this);
      Arrays.fill(this.f_21348_, 0.085F);
      Arrays.fill(this.f_21347_, 0.085F);
      this.f_315062_ = 0.085F;
      if (worldIn != null && !worldIn.f_46443_) {
         this.m_8099_();
      }

   }

   protected void m_8099_() {
   }

   public static C_557_.C_558_ m_21552_() {
      return C_524_.m_21183_().m_22268_(C_559_.f_22277_, 16.0);
   }

   protected C_758_ m_6037_(C_1596_ worldIn) {
      return new C_757_(this, worldIn);
   }

   protected boolean m_8091_() {
      return false;
   }

   public float m_21439_(C_313716_ nodeType) {
      C_526_ mob;
      label17: {
         C_507_ var4 = this.dd();
         if (var4 instanceof C_526_ mob1) {
            if (mob1.m_8091_()) {
               mob = mob1;
               break label17;
            }
         }

         mob = this;
      }

      Float f = (Float)mob.f_21354_.get(nodeType);
      return f == null ? nodeType.m_320214_() : f;
   }

   public void m_21441_(C_313716_ nodeType, float priority) {
      this.f_21354_.put(nodeType, priority);
   }

   public void m_284177_() {
   }

   public void m_284461_() {
   }

   protected C_661_ m_7560_() {
      return new C_661_(this);
   }

   public C_667_ m_21563_() {
      return this.f_21365_;
   }

   public C_668_ m_21566_() {
      C_507_ var2 = this.dd();
      C_668_ var10000;
      if (var2 instanceof C_526_ mob) {
         var10000 = mob.m_21566_();
      } else {
         var10000 = this.f_21342_;
      }

      return var10000;
   }

   public C_664_ m_21569_() {
      return this.f_21343_;
   }

   public C_758_ m_21573_() {
      C_507_ var2 = this.dd();
      C_758_ var10000;
      if (var2 instanceof C_526_ mob) {
         var10000 = mob.m_21573_();
      } else {
         var10000 = this.f_21344_;
      }

      return var10000;
   }

   @Nullable
   public C_524_ m_6688_() {
      C_507_ entity = this.cT();
      if (!this.m_21525_() && entity instanceof C_526_ mob) {
         if (entity.m_293117_()) {
            return mob;
         }
      }

      return null;
   }

   public C_775_ m_21574_() {
      return this.f_21349_;
   }

   @Nullable
   public C_524_ m_5448_() {
      return this.f_21362_;
   }

   @Nullable
   protected final C_524_ m_319699_() {
      return (C_524_)this.m_6274_().m_21952_(C_753_.f_26372_).orElse((Object)null);
   }

   public void m_6710_(@Nullable C_524_ entitylivingbaseIn) {
      if (Reflector.ForgeHooks_onLivingChangeTarget.exists()) {
         LivingChangeTargetEvent changeTargetEvent = (LivingChangeTargetEvent)Reflector.ForgeHooks_onLivingChangeTarget.call(this, entitylivingbaseIn, LivingChangeTargetEvent.LivingTargetType.MOB_TARGET);
         if (!changeTargetEvent.isCanceled()) {
            this.f_21362_ = changeTargetEvent.getNewTarget();
         }

      } else {
         this.f_21362_ = entitylivingbaseIn;
      }
   }

   public boolean m_6549_(C_513_ typeIn) {
      return typeIn != C_513_.f_20453_;
   }

   public boolean m_5886_(C_1406_ itemIn) {
      return false;
   }

   public void m_8035_() {
      this.a(C_141307_.f_157806_);
   }

   protected void m_8097_(C_5247_.C_313487_ builderIn) {
      super.m_8097_(builderIn);
      builderIn.m_318949_(f_21340_, (byte)0);
   }

   public int m_8100_() {
      return 80;
   }

   public void m_8032_() {
      this.m_323137_(this.m_7515_());
   }

   public void m_6075_() {
      super.m_6075_();
      this.dO().m_46473_().m_6180_("mobBaseTick");
      if (this.m_6084_() && this.ah.m_188503_(1000) < this.f_21363_++) {
         this.m_21551_();
         this.m_8032_();
      }

      this.dO().m_46473_().m_7238_();
   }

   protected void m_6677_(C_489_ source) {
      this.m_21551_();
      super.m_6677_(source);
   }

   private void m_21551_() {
      this.f_21363_ = -this.m_8100_();
   }

   protected int m_213860_() {
      if (this.f_21364_ > 0) {
         int i = this.f_21364_;

         int k;
         for(k = 0; k < this.f_21351_.size(); ++k) {
            if (!((C_1391_)this.f_21351_.get(k)).m_41619_() && this.f_21348_[k] <= 1.0F) {
               i += 1 + this.ah.m_188503_(3);
            }
         }

         for(k = 0; k < this.f_21350_.size(); ++k) {
            if (!((C_1391_)this.f_21350_.get(k)).m_41619_() && this.f_21347_[k] <= 1.0F) {
               i += 1 + this.ah.m_188503_(3);
            }
         }

         if (!this.f_314973_.m_41619_() && this.f_315062_ <= 1.0F) {
            i += 1 + this.ah.m_188503_(3);
         }

         return i;
      } else {
         return this.f_21364_;
      }
   }

   public void m_21373_() {
      if (this.dO().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double d0 = this.ah.m_188583_() * 0.02;
            double d1 = this.ah.m_188583_() * 0.02;
            double d2 = this.ah.m_188583_() * 0.02;
            double d3 = 10.0;
            this.dO().m_7106_(C_4759_.f_123759_, this.c(1.0) - d0 * 10.0, this.dw() - d1 * 10.0, this.g(1.0) - d2 * 10.0, d0, d1, d2);
         }
      } else {
         this.dO().m_7605_(this, (byte)20);
      }

   }

   public void m_7822_(byte id) {
      if (id == 20) {
         this.m_21373_();
      } else {
         super.m_7822_(id);
      }

   }

   public void m_8119_() {
      if (Config.isSmoothWorld() && this.canSkipUpdate()) {
         this.onUpdateMinimal();
      } else {
         super.m_8119_();
         if (!this.dO().f_46443_ && this.ai % 5 == 0) {
            this.m_8022_();
         }

      }
   }

   protected void m_8022_() {
      boolean flag = !(this.m_6688_() instanceof C_526_);
      boolean flag1 = !(this.dc() instanceof C_1205_);
      this.f_21345_.m_25360_(C_689_.MOVE, flag);
      this.f_21345_.m_25360_(C_689_.JUMP, flag && flag1);
      this.f_21345_.m_25360_(C_689_.LOOK, flag);
   }

   protected float m_5632_(float yawOffsetIn, float distanceIn) {
      this.f_21361_.m_8121_();
      return distanceIn;
   }

   @Nullable
   protected C_123_ m_7515_() {
      return null;
   }

   public void m_7380_(C_4917_ compound) {
      super.m_7380_(compound);
      compound.m_128379_("CanPickUpLoot", this.m_21531_());
      compound.m_128379_("PersistenceRequired", this.f_21353_);
      C_4930_ listtag = new C_4930_();
      Iterator var3 = this.f_21351_.iterator();

      while(var3.hasNext()) {
         C_1391_ itemstack = (C_1391_)var3.next();
         if (!itemstack.m_41619_()) {
            listtag.add(itemstack.m_41739_(this.dQ()));
         } else {
            listtag.add(new C_4917_());
         }
      }

      compound.m_128365_("ArmorItems", listtag);
      C_4930_ listtag1 = new C_4930_();
      float[] var11 = this.f_21348_;
      int var5 = var11.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         float f = var11[var6];
         listtag1.add(C_4923_.m_128566_(f));
      }

      compound.m_128365_("ArmorDropChances", listtag1);
      C_4930_ listtag2 = new C_4930_();
      Iterator var13 = this.f_21350_.iterator();

      while(var13.hasNext()) {
         C_1391_ itemstack1 = (C_1391_)var13.next();
         if (!itemstack1.m_41619_()) {
            listtag2.add(itemstack1.m_41739_(this.dQ()));
         } else {
            listtag2.add(new C_4917_());
         }
      }

      compound.m_128365_("HandItems", listtag2);
      C_4930_ listtag3 = new C_4930_();
      float[] var16 = this.f_21347_;
      int var17 = var16.length;

      for(int var8 = 0; var8 < var17; ++var8) {
         float f1 = var16[var8];
         listtag3.add(C_4923_.m_128566_(f1));
      }

      compound.m_128365_("HandDropChances", listtag3);
      if (!this.f_314973_.m_41619_()) {
         compound.m_128365_("body_armor_item", this.f_314973_.m_41739_(this.dQ()));
         compound.m_128350_("body_armor_drop_chance", this.f_315062_);
      }

      this.m_339731_(compound, this.f_337544_);
      compound.m_128379_("LeftHanded", this.m_21526_());
      if (this.f_21355_ != null) {
         compound.m_128359_("DeathLootTable", this.f_21355_.m_135782_().toString());
         if (this.f_21356_ != 0L) {
            compound.m_128356_("DeathLootTableSeed", this.f_21356_);
         }
      }

      if (this.m_21525_()) {
         compound.m_128379_("NoAI", this.m_21525_());
      }

      if (this.spawnType != null) {
         compound.m_128359_("forge:spawn_type", this.spawnType.name());
      }

   }

   public void m_7378_(C_4917_ compound) {
      super.m_7378_(compound);
      if (compound.m_128425_("CanPickUpLoot", 1)) {
         this.m_21553_(compound.m_128471_("CanPickUpLoot"));
      }

      this.f_21353_ = compound.m_128471_("PersistenceRequired");
      C_4930_ listtag3;
      int l;
      C_4917_ compoundtag1;
      if (compound.m_128425_("ArmorItems", 9)) {
         listtag3 = compound.m_128437_("ArmorItems", 10);

         for(l = 0; l < this.f_21351_.size(); ++l) {
            compoundtag1 = listtag3.m_128728_(l);
            this.f_21351_.set(l, C_1391_.m_318937_(this.dQ(), compoundtag1));
         }
      }

      if (compound.m_128425_("ArmorDropChances", 9)) {
         listtag3 = compound.m_128437_("ArmorDropChances", 5);

         for(l = 0; l < listtag3.size(); ++l) {
            this.f_21348_[l] = listtag3.m_128775_(l);
         }
      }

      if (compound.m_128425_("HandItems", 9)) {
         listtag3 = compound.m_128437_("HandItems", 10);

         for(l = 0; l < this.f_21350_.size(); ++l) {
            compoundtag1 = listtag3.m_128728_(l);
            this.f_21350_.set(l, C_1391_.m_318937_(this.dQ(), compoundtag1));
         }
      }

      if (compound.m_128425_("HandDropChances", 9)) {
         listtag3 = compound.m_128437_("HandDropChances", 5);

         for(l = 0; l < listtag3.size(); ++l) {
            this.f_21347_[l] = listtag3.m_128775_(l);
         }
      }

      if (compound.m_128425_("body_armor_item", 10)) {
         this.f_314973_ = (C_1391_)C_1391_.m_323951_(this.dQ(), compound.m_128469_("body_armor_item")).orElse(C_1391_.f_41583_);
         this.f_315062_ = compound.m_128457_("body_armor_drop_chance");
      } else {
         this.f_314973_ = C_1391_.f_41583_;
      }

      this.f_337544_ = this.m_340395_(compound);
      this.m_21559_(compound.m_128471_("LeftHanded"));
      if (compound.m_128425_("DeathLootTable", 8)) {
         this.f_21355_ = C_5264_.m_135785_(C_256686_.f_314309_, C_5265_.m_338530_(compound.m_128461_("DeathLootTable")));
         this.f_21356_ = compound.m_128454_("DeathLootTableSeed");
      }

      this.m_21557_(compound.m_128471_("NoAI"));
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
      this.f_21355_ = null;
   }

   public final C_5264_ m_5743_() {
      return this.f_21355_ == null ? this.m_7582_() : this.f_21355_;
   }

   protected C_5264_ m_7582_() {
      return super.m_5743_();
   }

   public long m_287233_() {
      return this.f_21356_;
   }

   public void m_21564_(float amount) {
      this.f_20902_ = amount;
   }

   public void m_21567_(float amount) {
      this.f_20901_ = amount;
   }

   public void m_21570_(float amount) {
      this.f_20900_ = amount;
   }

   public void m_7910_(float speedIn) {
      super.m_7910_(speedIn);
      this.m_21564_(speedIn);
   }

   public void m_324154_() {
      this.m_21573_().m_26573_();
      this.m_21570_(0.0F);
      this.m_21567_(0.0F);
      this.m_7910_(0.0F);
   }

   public void m_8107_() {
      super.m_8107_();
      this.dO().m_46473_().m_6180_("looting");
      boolean mobGriefing = this.dO().m_46469_().m_46207_(C_1583_.f_46132_);
      if (Reflector.ForgeEventFactory_getMobGriefingEvent.exists()) {
         mobGriefing = Reflector.callBoolean(Reflector.ForgeEventFactory_getMobGriefingEvent, this.dO(), this);
      }

      if (!this.dO().f_46443_ && this.m_21531_() && this.m_6084_() && !this.f_20890_ && mobGriefing) {
         C_4713_ vec3i = this.m_213552_();
         Iterator var3 = this.dO().a(C_976_.class, this.cK().m_82377_((double)vec3i.m_123341_(), (double)vec3i.m_123342_(), (double)vec3i.m_123343_())).iterator();

         while(var3.hasNext()) {
            C_976_ itementity = (C_976_)var3.next();
            if (!itementity.dJ() && !itementity.m_32055_().m_41619_() && !itementity.m_32063_() && this.m_7243_(itementity.m_32055_())) {
               this.m_7581_(itementity);
            }
         }
      }

      this.dO().m_46473_().m_7238_();
   }

   protected C_4713_ m_213552_() {
      return f_217048_;
   }

   protected void m_7581_(C_976_ itemEntity) {
      C_1391_ itemstack = itemEntity.m_32055_();
      C_1391_ itemstack1 = this.m_255207_(itemstack.m_41777_());
      if (!itemstack1.m_41619_()) {
         this.m_21053_(itemEntity);
         this.m_7938_(itemEntity, itemstack1.m_41613_());
         itemstack.m_41774_(itemstack1.m_41613_());
         if (itemstack.m_41619_()) {
            itemEntity.aq();
         }
      }

   }

   public C_1391_ m_255207_(C_1391_ itemStackIn) {
      C_516_ equipmentslot = this.m_147233_(itemStackIn);
      C_1391_ itemstack = this.m_6844_(equipmentslot);
      boolean flag = this.m_7808_(itemStackIn, itemstack);
      if (equipmentslot.m_254934_() && !flag) {
         equipmentslot = C_516_.MAINHAND;
         itemstack = this.m_6844_(equipmentslot);
         flag = itemstack.m_41619_();
      }

      if (flag && this.m_7252_(itemStackIn)) {
         double d0 = (double)this.m_21519_(equipmentslot);
         if (!itemstack.m_41619_() && (double)Math.max(this.ah.m_188501_() - 0.1F, 0.0F) < d0) {
            this.b(itemstack);
         }

         C_1391_ itemstack1 = equipmentslot.m_338803_(itemStackIn);
         this.m_21468_(equipmentslot, itemstack1);
         return itemstack1;
      } else {
         return C_1391_.f_41583_;
      }
   }

   protected void m_21468_(C_516_ slotIn, C_1391_ itemStackIn) {
      this.m_21035_(slotIn, itemStackIn);
      this.m_21508_(slotIn);
      this.f_21353_ = true;
   }

   public void m_21508_(C_516_ slotIn) {
      switch (slotIn.m_20743_()) {
         case HAND:
            this.f_21347_[slotIn.m_20749_()] = 2.0F;
            break;
         case HUMANOID_ARMOR:
            this.f_21348_[slotIn.m_20749_()] = 2.0F;
            break;
         case ANIMAL_ARMOR:
            this.f_315062_ = 2.0F;
      }

   }

   protected boolean m_7808_(C_1391_ candidate, C_1391_ existing) {
      if (existing.m_41619_()) {
         return true;
      } else {
         double d0;
         double d1;
         if (candidate.m_41720_() instanceof C_1425_) {
            if (!(existing.m_41720_() instanceof C_1425_)) {
               return true;
            } else {
               d1 = this.m_319522_(candidate);
               d0 = this.m_319522_(existing);
               return d1 != d0 ? d1 > d0 : this.m_21477_(candidate, existing);
            }
         } else if (candidate.m_41720_() instanceof C_1330_ && existing.m_41720_() instanceof C_1330_) {
            return this.m_21477_(candidate, existing);
         } else if (candidate.m_41720_() instanceof C_1349_ && existing.m_41720_() instanceof C_1349_) {
            return this.m_21477_(candidate, existing);
         } else {
            C_1381_ var4 = candidate.m_41720_();
            if (var4 instanceof C_1313_) {
               C_1313_ armoritem = (C_1313_)var4;
               if (C_1522_.m_340193_(existing, C_336480_.f_337286_)) {
                  return false;
               } else if (!(existing.m_41720_() instanceof C_1313_)) {
                  return true;
               } else {
                  C_1313_ armoritem1 = (C_1313_)existing.m_41720_();
                  if (armoritem.m_40404_() != armoritem1.m_40404_()) {
                     return armoritem.m_40404_() > armoritem1.m_40404_();
                  } else {
                     return armoritem.m_40405_() != armoritem1.m_40405_() ? armoritem.m_40405_() > armoritem1.m_40405_() : this.m_21477_(candidate, existing);
                  }
               }
            } else {
               if (candidate.m_41720_() instanceof C_1351_) {
                  if (existing.m_41720_() instanceof C_1325_) {
                     return true;
                  }

                  if (existing.m_41720_() instanceof C_1351_) {
                     d1 = this.m_319522_(candidate);
                     d0 = this.m_319522_(existing);
                     if (d1 != d0) {
                        return d1 > d0;
                     }

                     return this.m_21477_(candidate, existing);
                  }
               }

               return false;
            }
         }
      }
   }

   private double m_319522_(C_1391_ itemStackIn) {
      C_313447_ itemattributemodifiers = (C_313447_)itemStackIn.a(C_313616_.f_316119_, C_313447_.f_314473_);
      return itemattributemodifiers.m_324178_(this.m_245892_(C_559_.f_22281_), C_516_.MAINHAND);
   }

   public boolean m_21477_(C_1391_ itemStackIn, C_1391_ itemStack2In) {
      return itemStackIn.m_41773_() < itemStack2In.m_41773_() ? true : m_321483_(itemStackIn) && !m_321483_(itemStack2In);
   }

   private static boolean m_321483_(C_1391_ itemStackIn) {
      C_313470_ datacomponentmap = itemStackIn.m_318732_();
      int i = datacomponentmap.m_319491_();
      return i > 1 || i == 1 && !datacomponentmap.m_321946_(C_313616_.f_313972_);
   }

   public boolean m_7252_(C_1391_ stack) {
      return true;
   }

   public boolean m_7243_(C_1391_ itemStackIn) {
      return this.m_7252_(itemStackIn);
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return true;
   }

   public boolean m_8023_() {
      return this.bS();
   }

   protected boolean m_8028_() {
      return false;
   }

   public void m_6043_() {
      if (this.dO().al() == C_468_.PEACEFUL && this.m_8028_()) {
         this.aq();
      } else if (!this.m_21532_() && !this.m_8023_()) {
         C_507_ entity = this.dO().a(this, -1.0);
         if (Reflector.ForgeEventFactory_canEntityDespawn.exists()) {
            Object result = Reflector.ForgeEventFactory_canEntityDespawn.call(this, this.dO());
            if (result == ReflectorForge.EVENT_RESULT_DENY) {
               this.f_20891_ = 0;
               entity = null;
            } else if (result == ReflectorForge.EVENT_RESULT_ALLOW) {
               this.aq();
               entity = null;
            }
         }

         if (entity != null) {
            double d0 = entity.m_20280_(this);
            int i = this.am().m_20674_().m_21611_();
            int j = i * i;
            if (d0 > (double)j && this.m_6785_(d0)) {
               this.aq();
            }

            int k = this.am().m_20674_().m_21612_();
            int l = k * k;
            if (this.f_20891_ > 600 && this.ah.m_188503_(800) == 0 && d0 > (double)l && this.m_6785_(d0)) {
               this.aq();
            } else if (d0 < (double)l) {
               this.f_20891_ = 0;
            }
         }
      } else {
         this.f_20891_ = 0;
      }

   }

   protected final void m_6140_() {
      ++this.f_20891_;
      C_442_ profilerfiller = this.dO().m_46473_();
      profilerfiller.m_6180_("sensing");
      this.f_21349_.m_26789_();
      profilerfiller.m_7238_();
      int i = this.ai + this.an();
      if (i % 2 != 0 && this.ai > 1) {
         profilerfiller.m_6180_("targetSelector");
         this.f_21346_.m_186081_(false);
         profilerfiller.m_7238_();
         profilerfiller.m_6180_("goalSelector");
         this.f_21345_.m_186081_(false);
         profilerfiller.m_7238_();
      } else {
         profilerfiller.m_6180_("targetSelector");
         this.f_21346_.m_25373_();
         profilerfiller.m_7238_();
         profilerfiller.m_6180_("goalSelector");
         this.f_21345_.m_25373_();
         profilerfiller.m_7238_();
      }

      profilerfiller.m_6180_("navigation");
      this.f_21344_.m_7638_();
      profilerfiller.m_7238_();
      profilerfiller.m_6180_("mob tick");
      this.m_8024_();
      profilerfiller.m_7238_();
      profilerfiller.m_6180_("controls");
      profilerfiller.m_6180_("move");
      this.f_21342_.m_8126_();
      profilerfiller.m_6182_("look");
      this.f_21365_.m_8128_();
      profilerfiller.m_6182_("jump");
      this.f_21343_.m_8124_();
      profilerfiller.m_7238_();
      profilerfiller.m_7238_();
      this.m_8025_();
   }

   protected void m_8025_() {
      C_5143_.m_133699_(this.dO(), this, this.f_21345_);
   }

   protected void m_8024_() {
   }

   public int m_8132_() {
      return 40;
   }

   public int m_8085_() {
      return 75;
   }

   protected void m_322776_() {
      float f = (float)this.m_8085_();
      float f1 = this.m_6080_();
      float f2 = C_188_.m_14177_(this.f_20883_ - f1);
      float f3 = C_188_.m_14036_(C_188_.m_14177_(this.f_20883_ - f1), -f, f);
      float f4 = f1 + f2 - f3;
      this.m_5616_(f4);
   }

   public int m_21529_() {
      return 10;
   }

   public void m_21391_(C_507_ entityIn, float maxYawIncrease, float maxPitchIncrease) {
      double d0 = entityIn.m_20185_() - this.dt();
      double d2 = entityIn.m_20189_() - this.dz();
      double d1;
      if (entityIn instanceof C_524_ livingentity) {
         d1 = livingentity.dx() - this.dx();
      } else {
         d1 = (entityIn.m_20191_().f_82289_ + entityIn.m_20191_().f_82292_) / 2.0 - this.dx();
      }

      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = (float)(C_188_.m_14136_(d2, d0) * 180.0 / 3.1415927410125732) - 90.0F;
      float f1 = (float)(-(C_188_.m_14136_(d1, d3) * 180.0 / 3.1415927410125732));
      this.u(this.m_21376_(this.dG(), f1, maxPitchIncrease));
      this.t(this.m_21376_(this.dE(), f, maxYawIncrease));
   }

   private float m_21376_(float angle, float targetAngle, float maxIncrease) {
      float f = C_188_.m_14177_(targetAngle - angle);
      if (f > maxIncrease) {
         f = maxIncrease;
      }

      if (f < -maxIncrease) {
         f = -maxIncrease;
      }

      return angle + f;
   }

   public static boolean m_217057_(C_513_ typeIn, C_1598_ worldIn, C_529_ reason, C_4675_ pos, C_212974_ randomIn) {
      C_4675_ blockpos = pos.m_7495_();
      return reason == C_529_.SPAWNER || worldIn.a_(blockpos).m_60643_(worldIn, blockpos, typeIn);
   }

   public boolean m_5545_(C_1598_ worldIn, C_529_ spawnReasonIn) {
      return true;
   }

   public boolean m_6914_(C_1599_ worldIn) {
      return !worldIn.m_46855_(this.cK()) && worldIn.f(this);
   }

   public int m_5792_() {
      return 4;
   }

   public boolean m_7296_(int sizeIn) {
      return false;
   }

   public int m_6056_() {
      if (this.m_5448_() == null) {
         return this.m_320747_(0.0F);
      } else {
         int i = (int)(this.m_21223_() - this.m_21233_() * 0.33F);
         i -= (3 - this.dO().al().m_19028_()) * 4;
         if (i < 0) {
            i = 0;
         }

         return this.m_320747_((float)i);
      }
   }

   public Iterable m_21487_() {
      return this.f_21350_;
   }

   public Iterable m_21151_() {
      return this.f_21351_;
   }

   public C_1391_ m_319275_() {
      return this.f_314973_;
   }

   public boolean m_30729_(C_516_ slotIn) {
      return slotIn != C_516_.BODY;
   }

   public boolean m_324340_() {
      return !this.m_6844_(C_516_.BODY).m_41619_();
   }

   public boolean m_30833_(C_1391_ itemStackIn) {
      return false;
   }

   public void m_323866_(C_1391_ itemStackIn) {
      this.m_21468_(C_516_.BODY, itemStackIn);
   }

   public Iterable m_322068_() {
      return (Iterable)(this.f_314973_.m_41619_() ? this.f_21351_ : Iterables.concat(this.f_21351_, List.of(this.f_314973_)));
   }

   public C_1391_ m_6844_(C_516_ slotIn) {
      C_1391_ var10000;
      switch (slotIn.m_20743_()) {
         case HAND:
            var10000 = (C_1391_)this.f_21350_.get(slotIn.m_20749_());
            break;
         case HUMANOID_ARMOR:
            var10000 = (C_1391_)this.f_21351_.get(slotIn.m_20749_());
            break;
         case ANIMAL_ARMOR:
            var10000 = this.f_314973_;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public void m_21035_(C_516_ slotIn, C_1391_ itemStackIn) {
      this.m_181122_(itemStackIn);
      switch (slotIn.m_20743_()) {
         case HAND:
            this.m_238392_(slotIn, (C_1391_)this.f_21350_.set(slotIn.m_20749_(), itemStackIn), itemStackIn);
            break;
         case HUMANOID_ARMOR:
            this.m_238392_(slotIn, (C_1391_)this.f_21351_.set(slotIn.m_20749_(), itemStackIn), itemStackIn);
            break;
         case ANIMAL_ARMOR:
            C_1391_ itemstack = this.f_314973_;
            this.f_314973_ = itemStackIn;
            this.m_238392_(slotIn, itemstack, itemStackIn);
      }

   }

   protected void m_7472_(C_12_ worldIn, C_489_ sourceIn, boolean recentlyHitIn) {
      super.m_7472_(worldIn, sourceIn, recentlyHitIn);
      C_516_[] var4 = C_516_.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         C_516_ equipmentslot = var4[var6];
         C_1391_ itemstack = this.m_6844_(equipmentslot);
         float f = this.m_21519_(equipmentslot);
         if (f != 0.0F) {
            boolean flag = f > 1.0F;
            C_507_ entity = sourceIn.m_7639_();
            if (entity instanceof C_524_) {
               C_524_ livingentity = (C_524_)entity;
               C_1596_ var14 = this.dO();
               if (var14 instanceof C_12_) {
                  C_12_ serverlevel = (C_12_)var14;
                  f = C_1522_.m_339734_(serverlevel, livingentity, sourceIn, f);
               }
            }

            if (!itemstack.m_41619_() && !C_1522_.m_340193_(itemstack, C_336480_.f_337159_) && (recentlyHitIn || flag) && this.ah.m_188501_() < f) {
               if (!flag && itemstack.m_41763_()) {
                  itemstack.m_41721_(itemstack.m_41776_() - this.ah.m_188503_(1 + this.ah.m_188503_(Math.max(itemstack.m_41776_() - 3, 1))));
               }

               this.b(itemstack);
               this.m_21035_(equipmentslot, C_1391_.f_41583_);
            }
         }
      }

   }

   protected float m_21519_(C_516_ slotIn) {
      float var10000;
      switch (slotIn.m_20743_()) {
         case HAND:
            var10000 = this.f_21347_[slotIn.m_20749_()];
            break;
         case HUMANOID_ARMOR:
            var10000 = this.f_21348_[slotIn.m_20749_()];
            break;
         case ANIMAL_ARMOR:
            var10000 = this.f_315062_;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public void m_339194_() {
      this.m_339901_((goalIn) -> {
         return true;
      });
   }

   public Set m_339901_(Predicate checkIn) {
      Set set = new HashSet();
      C_516_[] var3 = C_516_.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         C_516_ equipmentslot = var3[var5];
         C_1391_ itemstack = this.m_6844_(equipmentslot);
         if (!itemstack.m_41619_()) {
            if (!checkIn.test(itemstack)) {
               set.add(equipmentslot);
            } else {
               double d0 = (double)this.m_21519_(equipmentslot);
               if (d0 > 1.0) {
                  this.m_21035_(equipmentslot, C_1391_.f_41583_);
                  this.b(itemstack);
               }
            }
         }
      }

      return set;
   }

   private C_286926_ m_320276_(C_12_ levelIn) {
      return (new C_286926_.C_286924_(levelIn)).m_287286_(C_2974_.f_81460_, this.dm()).m_287286_(C_2974_.f_81455_, this).m_287235_(C_2973_.f_313897_);
   }

   public void m_319416_(C_313465_ tableIn) {
      this.m_322414_(tableIn.f_316700_(), tableIn.f_315505_());
   }

   public void m_322414_(C_5264_ keyIn, Map mapIn) {
      C_1596_ var4 = this.dO();
      if (var4 instanceof C_12_ serverlevel) {
         this.m_319719_(keyIn, this.m_320276_(serverlevel), mapIn);
      }

   }

   protected void m_213945_(C_212974_ randomIn, C_469_ difficulty) {
      if (randomIn.m_188501_() < 0.15F * difficulty.m_19057_()) {
         int i = randomIn.m_188503_(2);
         float f = this.dO().al() == C_468_.HARD ? 0.1F : 0.25F;
         if (randomIn.m_188501_() < 0.095F) {
            ++i;
         }

         if (randomIn.m_188501_() < 0.095F) {
            ++i;
         }

         if (randomIn.m_188501_() < 0.095F) {
            ++i;
         }

         boolean flag = true;
         C_516_[] var6 = C_516_.values();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            C_516_ equipmentslot = var6[var8];
            if (equipmentslot.m_20743_() == C_517_.HUMANOID_ARMOR) {
               C_1391_ itemstack = this.m_6844_(equipmentslot);
               if (!flag && randomIn.m_188501_() < f) {
                  break;
               }

               flag = false;
               if (itemstack.m_41619_()) {
                  C_1381_ item = m_21412_(equipmentslot, i);
                  if (item != null) {
                     this.m_21035_(equipmentslot, new C_1391_(item));
                  }
               }
            }
         }
      }

   }

   @Nullable
   public static C_1381_ m_21412_(C_516_ slotIn, int chance) {
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

   protected void m_213946_(C_1618_ worldIn, C_212974_ randomIn, C_469_ difficultyIn) {
      this.m_214095_(worldIn, randomIn, difficultyIn);
      C_516_[] var4 = C_516_.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         C_516_ equipmentslot = var4[var6];
         if (equipmentslot.m_20743_() == C_517_.HUMANOID_ARMOR) {
            this.m_217051_(worldIn, randomIn, equipmentslot, difficultyIn);
         }
      }

   }

   protected void m_214095_(C_1618_ worldIn, C_212974_ randomIn, C_469_ difficultyIn) {
      this.m_340386_(worldIn, C_516_.MAINHAND, randomIn, 0.25F, difficultyIn);
   }

   protected void m_217051_(C_1618_ worldIn, C_212974_ randomIn, C_516_ slotIn, C_469_ difficultyIn) {
      this.m_340386_(worldIn, slotIn, randomIn, 0.5F, difficultyIn);
   }

   private void m_340386_(C_1618_ worldIn, C_516_ slotIn, C_212974_ randomIn, float difficultyMulIn, C_469_ difficultyIn) {
      C_1391_ itemstack = this.m_6844_(slotIn);
      if (!itemstack.m_41619_() && randomIn.m_188501_() < difficultyMulIn * difficultyIn.m_19057_()) {
         C_1522_.m_338695_(itemstack, worldIn.H_(), C_336472_.f_337458_, difficultyIn, randomIn);
         this.m_21035_(slotIn, itemstack);
      }

   }

   @Nullable
   public C_542_ m_6518_(C_1618_ worldIn, C_469_ difficultyIn, C_529_ reason, @Nullable C_542_ spawnDataIn) {
      C_212974_ randomsource = worldIn.E_();
      C_553_ attributeinstance = (C_553_)Objects.requireNonNull(this.m_21051_(C_559_.f_22277_));
      if (!attributeinstance.m_22109_(f_337189_)) {
         attributeinstance.m_22125_(new C_555_(f_337189_, randomsource.m_216328_(0.0, 0.11485000000000001), C_556_.ADD_MULTIPLIED_BASE));
      }

      this.m_21559_(randomsource.m_188501_() < 0.05F);
      this.spawnType = reason;
      return spawnDataIn;
   }

   public void m_21530_() {
      this.f_21353_ = true;
   }

   public void m_21409_(C_516_ slotIn, float chance) {
      switch (slotIn.m_20743_()) {
         case HAND:
            this.f_21347_[slotIn.m_20749_()] = chance;
            break;
         case HUMANOID_ARMOR:
            this.f_21348_[slotIn.m_20749_()] = chance;
            break;
         case ANIMAL_ARMOR:
            this.f_315062_ = chance;
      }

   }

   public boolean m_21531_() {
      return this.f_21352_;
   }

   public void m_21553_(boolean canPickup) {
      this.f_21352_ = canPickup;
   }

   public boolean m_7066_(C_1391_ itemstackIn) {
      C_516_ equipmentslot = this.m_147233_(itemstackIn);
      return this.m_6844_(equipmentslot).m_41619_() && this.m_21531_();
   }

   public boolean m_21532_() {
      return this.f_21353_;
   }

   public final C_471_ m_6096_(C_1141_ player, C_470_ hand) {
      if (!this.m_6084_()) {
         return C_471_.PASS;
      } else {
         C_471_ interactionresult = this.m_21499_(player, hand);
         if (interactionresult.m_19077_()) {
            this.a(C_141307_.f_223708_, player);
            return interactionresult;
         } else {
            C_471_ interactionresult1 = super.a(player, hand);
            if (interactionresult1 != C_471_.PASS) {
               return interactionresult1;
            } else {
               interactionresult = this.m_6071_(player, hand);
               if (interactionresult.m_19077_()) {
                  this.a(C_141307_.f_223708_, player);
                  return interactionresult;
               } else {
                  return C_471_.PASS;
               }
            }
         }
      }
   }

   private C_471_ m_21499_(C_1141_ playerIn, C_470_ handIn) {
      C_1391_ itemstack = playerIn.b(handIn);
      if (itemstack.m_150930_(C_1394_.f_42656_)) {
         C_471_ interactionresult = itemstack.m_41647_(playerIn, this, handIn);
         if (interactionresult.m_19077_()) {
            return interactionresult;
         }
      }

      if (itemstack.m_41720_() instanceof C_1420_) {
         if (this.dO() instanceof C_12_) {
            C_1420_ spawneggitem = (C_1420_)itemstack.m_41720_();
            Optional optional = spawneggitem.m_43215_(playerIn, this, this.am(), (C_12_)this.dO(), this.dm(), itemstack);
            optional.ifPresent((mobIn) -> {
               this.m_5502_(playerIn, mobIn);
            });
            return optional.isPresent() ? C_471_.SUCCESS : C_471_.PASS;
         } else {
            return C_471_.CONSUME;
         }
      } else {
         return C_471_.PASS;
      }
   }

   protected void m_5502_(C_1141_ playerIn, C_526_ child) {
   }

   protected C_471_ m_6071_(C_1141_ playerIn, C_470_ handIn) {
      return C_471_.PASS;
   }

   public boolean m_21533_() {
      return this.m_21444_(this.do());
   }

   public boolean m_21444_(C_4675_ pos) {
      return this.f_21341_ == -1.0F ? true : this.f_21360_.j(pos) < (double)(this.f_21341_ * this.f_21341_);
   }

   public void m_21446_(C_4675_ pos, int distance) {
      this.f_21360_ = pos;
      this.f_21341_ = (float)distance;
   }

   public C_4675_ m_21534_() {
      return this.f_21360_;
   }

   public float m_21535_() {
      return this.f_21341_;
   }

   public void m_147271_() {
      this.f_21341_ = -1.0F;
   }

   public boolean m_21536_() {
      return this.f_21341_ != -1.0F;
   }

   @Nullable
   public C_526_ m_21406_(C_513_ typeIn, boolean equipmentIn) {
      if (this.dJ()) {
         return null;
      } else {
         C_526_ t = (C_526_)typeIn.m_20615_(this.dO());
         if (t == null) {
            return null;
         } else {
            t.v(this);
            t.m_6863_(this.m_6162_());
            t.m_21557_(this.m_21525_());
            if (this.ai()) {
               t.b(this.aj());
               t.p(this.cE());
            }

            if (this.m_21532_()) {
               t.m_21530_();
            }

            t.n(this.cv());
            if (equipmentIn) {
               t.m_21553_(this.m_21531_());
               C_516_[] var4 = C_516_.values();
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  C_516_ equipmentslot = var4[var6];
                  C_1391_ itemstack = this.m_6844_(equipmentslot);
                  if (!itemstack.m_41619_()) {
                     t.m_21035_(equipmentslot, itemstack.m_278832_());
                     t.m_21409_(equipmentslot, this.m_21519_(equipmentslot));
                  }
               }
            }

            this.dO().b(t);
            if (this.bS()) {
               C_507_ entity = this.dc();
               this.m_8127_();
               t.m_7998_(entity, true);
            }

            this.aq();
            return t;
         }
      }
   }

   @Nullable
   public C_336597_.C_336538_ m_338492_() {
      return this.f_337544_;
   }

   public void m_338401_(@Nullable C_336597_.C_336538_ leashDataIn) {
      this.f_337544_ = leashDataIn;
   }

   public void m_21455_(boolean sendPacket, boolean dropLead) {
      super.m_21455_(sendPacket, dropLead);
      if (this.m_338492_() == null) {
         this.m_147271_();
      }

   }

   public void m_339671_() {
      super.m_339671_();
      this.f_21345_.m_25355_(C_689_.MOVE);
   }

   public boolean m_6573_() {
      return !(this instanceof C_1004_);
   }

   public boolean m_7998_(C_507_ entityIn, boolean force) {
      boolean flag = super.a(entityIn, force);
      if (flag && this.m_339418_()) {
         this.m_21455_(true, true);
      }

      return flag;
   }

   public boolean m_21515_() {
      return super.db() && !this.m_21525_();
   }

   public void m_21557_(boolean disable) {
      byte b0 = (Byte)this.ao.m_135370_(f_21340_);
      this.ao.m_135381_(f_21340_, disable ? (byte)(b0 | 1) : (byte)(b0 & -2));
   }

   public void m_21559_(boolean leftHanded) {
      byte b0 = (Byte)this.ao.m_135370_(f_21340_);
      this.ao.m_135381_(f_21340_, leftHanded ? (byte)(b0 | 2) : (byte)(b0 & -3));
   }

   public void m_21561_(boolean hasAggro) {
      byte b0 = (Byte)this.ao.m_135370_(f_21340_);
      this.ao.m_135381_(f_21340_, hasAggro ? (byte)(b0 | 4) : (byte)(b0 & -5));
   }

   public boolean m_21525_() {
      return ((Byte)this.ao.m_135370_(f_21340_) & 1) != 0;
   }

   public boolean m_21526_() {
      return ((Byte)this.ao.m_135370_(f_21340_) & 2) != 0;
   }

   public boolean m_5912_() {
      return ((Byte)this.ao.m_135370_(f_21340_) & 4) != 0;
   }

   public void m_6863_(boolean childZombie) {
   }

   public C_520_ m_5737_() {
      return this.m_21526_() ? C_520_.LEFT : C_520_.RIGHT;
   }

   public boolean m_217066_(C_524_ entityIn) {
      return this.m_292684_().m_82381_(entityIn.m_293919_());
   }

   protected C_3040_ m_292684_() {
      C_507_ entity = this.dc();
      C_3040_ aabb;
      if (entity != null) {
         C_3040_ aabb1 = entity.m_20191_();
         C_3040_ aabb2 = this.cK();
         aabb = new C_3040_(Math.min(aabb2.f_82288_, aabb1.f_82288_), aabb2.f_82289_, Math.min(aabb2.f_82290_, aabb1.f_82290_), Math.max(aabb2.f_82291_, aabb1.f_82291_), aabb2.f_82292_, Math.max(aabb2.f_82293_, aabb1.f_82293_));
      } else {
         aabb = this.cK();
      }

      return aabb.m_82377_(f_290867_, 0.0, f_290867_);
   }

   public boolean m_7327_(C_507_ entityIn) {
      float f = (float)this.m_246858_(C_559_.f_22281_);
      C_489_ damagesource = this.dP().m_269333_(this);
      C_1596_ var5 = this.dO();
      if (var5 instanceof C_12_ serverlevel) {
         f = C_1522_.m_338960_(serverlevel, this.m_338776_(), entityIn, damagesource, f);
      }

      boolean flag = entityIn.m_6469_(damagesource, f);
      if (flag) {
         float f1 = this.m_338419_(entityIn, damagesource);
         if (f1 > 0.0F && entityIn instanceof C_524_) {
            C_524_ livingentity = (C_524_)entityIn;
            livingentity.m_147240_((double)(f1 * 0.5F), (double)C_188_.m_14031_(this.dE() * 0.017453292F), (double)(-C_188_.m_14089_(this.dE() * 0.017453292F)));
            this.i(this.dr().m_82542_(0.6, 1.0, 0.6));
         }

         C_1596_ var7 = this.dO();
         if (var7 instanceof C_12_) {
            C_12_ serverlevel1 = (C_12_)var7;
            C_1522_.m_338760_(serverlevel1, entityIn, damagesource);
         }

         this.m_21335_(entityIn);
         this.m_339982_();
      }

      return flag;
   }

   protected void m_339982_() {
   }

   protected boolean m_21527_() {
      if (this.dO().m_46461_() && !this.dO().f_46443_) {
         float f = this.bu();
         C_4675_ blockpos = C_4675_.m_274561_(this.dt(), this.dx(), this.dz());
         boolean flag = this.bh() || this.az || this.aA;
         if (f > 0.5F && this.ah.m_188501_() * 30.0F < (f - 0.4F) * 2.0F && !flag && this.dO().h(blockpos)) {
            return true;
         }
      }

      return false;
   }

   protected void m_203347_(C_203208_ fluidTag) {
      this.jumpInLiquidInternal(() -> {
         super.m_203347_(fluidTag);
      });
   }

   private void jumpInLiquidInternal(Runnable onSuper) {
      if (this.m_21573_().m_26576_()) {
         onSuper.run();
      } else {
         this.i(this.dr().m_82520_(0.0, 0.3, 0.0));
      }

   }

   public void jumpInFluid(FluidType type) {
      this.jumpInLiquidInternal(() -> {
         IForgeLivingEntity.super.jumpInFluid(type);
      });
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
   public void m_147272_() {
      this.m_262441_((goalIn) -> {
         return true;
      });
      this.m_6274_().m_147343_();
   }

   public void m_262441_(Predicate predicateIn) {
      this.f_21345_.m_262460_(predicateIn);
   }

   protected void m_6089_() {
      super.cw();
      this.m_323629_().forEach((itemStackIn) -> {
         if (!itemStackIn.m_41619_()) {
            itemStackIn.m_41764_(0);
         }

      });
   }

   @Nullable
   public C_1391_ m_142340_() {
      C_1420_ spawneggitem = C_1420_.m_43213_(this.am());
      return spawneggitem == null ? null : new C_1391_(spawneggitem);
   }

   private boolean canSkipUpdate() {
      if (this.m_6162_()) {
         return false;
      } else if (this.f_20916_ > 0) {
         return false;
      } else if (this.ai < 20) {
         return false;
      } else {
         List listPlayers = this.getListPlayers(this.cN());
         if (listPlayers == null) {
            return false;
         } else if (listPlayers.size() != 1) {
            return false;
         } else {
            C_507_ player = (C_507_)listPlayers.get(0);
            double dx = Math.max(Math.abs(this.dt() - player.m_20185_()) - 16.0, 0.0);
            double dz = Math.max(Math.abs(this.dz() - player.m_20189_()) - 16.0, 0.0);
            double distSq = dx * dx + dz * dz;
            return !this.a(distSq);
         }
      }
   }

   private List getListPlayers(C_1596_ entityWorld) {
      C_1596_ world = this.cN();
      if (world instanceof C_3899_ worldClient) {
         return worldClient.m_6907_();
      } else if (world instanceof C_12_ worldServer) {
         return worldServer.m_6907_();
      } else {
         return null;
      }
   }

   private void onUpdateMinimal() {
      ++this.f_20891_;
      if (this instanceof C_1027_) {
         float brightness = this.bu();
         boolean raider = this instanceof C_1189_;
         if (brightness > 0.5F || raider) {
            this.f_20891_ += 2;
         }
      }

   }

   static {
      f_21340_ = C_5247_.m_135353_(C_526_.class, C_5227_.f_135027_);
      f_217048_ = new C_4713_(1, 0, 1);
      f_290867_ = Math.sqrt(2.0399999618530273) - 0.6000000238418579;
      f_337189_ = C_5265_.m_340282_("random_spawn_bonus");
   }
}
