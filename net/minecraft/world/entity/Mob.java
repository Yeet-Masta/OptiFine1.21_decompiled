package net.minecraft.world.entity;

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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.fluids.FluidType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public abstract class Mob extends LivingEntity implements EquipmentUser, Leashable, Targeting, IForgeLivingEntity {
   private static final EntityDataAccessor f_21340_;
   private static final int f_147266_ = 1;
   private static final int f_147267_ = 2;
   private static final int f_147268_ = 4;
   protected static final int f_147265_ = 1;
   private static final Vec3i f_217048_;
   public static final float f_147269_ = 0.15F;
   public static final float f_147261_ = 0.55F;
   public static final float f_147262_ = 0.5F;
   public static final float f_147263_ = 0.25F;
   public static final float f_182333_ = 0.085F;
   public static final float f_337144_ = 1.0F;
   public static final int f_217047_ = 2;
   public static final int f_186008_ = 2;
   private static final double f_290867_;
   protected static final ResourceLocation f_337189_;
   public int f_21363_;
   protected int f_21364_;
   protected LookControl f_21365_;
   protected MoveControl f_21342_;
   protected JumpControl f_21343_;
   private final BodyRotationControl f_21361_;
   protected PathNavigation f_21344_;
   protected final GoalSelector f_21345_;
   protected final GoalSelector f_21346_;
   @Nullable
   private LivingEntity f_21362_;
   private final Sensing f_21349_;
   private final NonNullList f_21350_;
   protected final float[] f_21347_;
   private final NonNullList f_21351_;
   protected final float[] f_21348_;
   private ItemStack f_314973_;
   protected float f_315062_;
   private boolean f_21352_;
   private boolean f_21353_;
   private final Map f_21354_;
   @Nullable
   private ResourceKey f_21355_;
   private long f_21356_;
   @Nullable
   private Leashable.LeashData f_337544_;
   private BlockPos f_21360_;
   private float f_21341_;
   private MobSpawnType spawnType;
   private boolean spawnCancelled;

   protected Mob(EntityType type, Level worldIn) {
      super(type, worldIn);
      this.f_21350_ = NonNullList.m_122780_(2, ItemStack.f_41583_);
      this.f_21347_ = new float[2];
      this.f_21351_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
      this.f_21348_ = new float[4];
      this.f_314973_ = ItemStack.f_41583_;
      this.f_21354_ = Maps.newEnumMap(PathType.class);
      this.f_21360_ = BlockPos.f_121853_;
      this.f_21341_ = -1.0F;
      this.spawnCancelled = false;
      this.f_21345_ = new GoalSelector(worldIn.m_46658_());
      this.f_21346_ = new GoalSelector(worldIn.m_46658_());
      this.f_21365_ = new LookControl(this);
      this.f_21342_ = new MoveControl(this);
      this.f_21343_ = new JumpControl(this);
      this.f_21361_ = this.m_7560_();
      this.f_21344_ = this.m_6037_(worldIn);
      this.f_21349_ = new Sensing(this);
      Arrays.fill(this.f_21348_, 0.085F);
      Arrays.fill(this.f_21347_, 0.085F);
      this.f_315062_ = 0.085F;
      if (worldIn != null && !worldIn.f_46443_) {
         this.m_8099_();
      }

   }

   protected void m_8099_() {
   }

   public static AttributeSupplier.Builder m_21552_() {
      return LivingEntity.m_21183_().m_22268_(Attributes.f_22277_, 16.0);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new GroundPathNavigation(this, worldIn);
   }

   protected boolean m_8091_() {
      return false;
   }

   public float m_21439_(PathType nodeType) {
      Mob mob;
      label17: {
         Entity var4 = this.m_275832_();
         if (var4 instanceof Mob mob1) {
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

   public void m_21441_(PathType nodeType, float priority) {
      this.f_21354_.put(nodeType, priority);
   }

   public void m_284177_() {
   }

   public void m_284461_() {
   }

   protected BodyRotationControl m_7560_() {
      return new BodyRotationControl(this);
   }

   public LookControl m_21563_() {
      return this.f_21365_;
   }

   public MoveControl m_21566_() {
      Entity var2 = this.m_275832_();
      MoveControl var10000;
      if (var2 instanceof Mob mob) {
         var10000 = mob.m_21566_();
      } else {
         var10000 = this.f_21342_;
      }

      return var10000;
   }

   public JumpControl m_21569_() {
      return this.f_21343_;
   }

   public PathNavigation m_21573_() {
      Entity var2 = this.m_275832_();
      PathNavigation var10000;
      if (var2 instanceof Mob mob) {
         var10000 = mob.m_21573_();
      } else {
         var10000 = this.f_21344_;
      }

      return var10000;
   }

   @Nullable
   public LivingEntity m_6688_() {
      Entity entity = this.m_146895_();
      if (!this.m_21525_() && entity instanceof Mob mob) {
         if (entity.m_293117_()) {
            return mob;
         }
      }

      return null;
   }

   public Sensing m_21574_() {
      return this.f_21349_;
   }

   @Nullable
   public LivingEntity m_5448_() {
      return this.f_21362_;
   }

   @Nullable
   protected final LivingEntity m_319699_() {
      return (LivingEntity)this.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElse((Object)null);
   }

   public void m_6710_(@Nullable LivingEntity entitylivingbaseIn) {
      if (Reflector.ForgeHooks_onLivingChangeTarget.exists()) {
         LivingChangeTargetEvent changeTargetEvent = (LivingChangeTargetEvent)Reflector.ForgeHooks_onLivingChangeTarget.call(this, entitylivingbaseIn, LivingChangeTargetEvent.LivingTargetType.MOB_TARGET);
         if (!changeTargetEvent.isCanceled()) {
            this.f_21362_ = changeTargetEvent.getNewTarget();
         }

      } else {
         this.f_21362_ = entitylivingbaseIn;
      }
   }

   public boolean m_6549_(EntityType typeIn) {
      return typeIn != EntityType.f_20453_;
   }

   public boolean m_5886_(ProjectileWeaponItem itemIn) {
      return false;
   }

   public void m_8035_() {
      this.m_146850_(GameEvent.f_157806_);
   }

   protected void m_8097_(SynchedEntityData.Builder builderIn) {
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
      this.m_9236_().m_46473_().m_6180_("mobBaseTick");
      if (this.m_6084_() && this.f_19796_.m_188503_(1000) < this.f_21363_++) {
         this.m_21551_();
         this.m_8032_();
      }

      this.m_9236_().m_46473_().m_7238_();
   }

   protected void m_6677_(DamageSource source) {
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
            if (!((ItemStack)this.f_21351_.get(k)).m_41619_() && this.f_21348_[k] <= 1.0F) {
               i += 1 + this.f_19796_.m_188503_(3);
            }
         }

         for(k = 0; k < this.f_21350_.size(); ++k) {
            if (!((ItemStack)this.f_21350_.get(k)).m_41619_() && this.f_21347_[k] <= 1.0F) {
               i += 1 + this.f_19796_.m_188503_(3);
            }
         }

         if (!this.f_314973_.m_41619_() && this.f_315062_ <= 1.0F) {
            i += 1 + this.f_19796_.m_188503_(3);
         }

         return i;
      } else {
         return this.f_21364_;
      }
   }

   public void m_21373_() {
      if (this.m_9236_().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double d0 = this.f_19796_.m_188583_() * 0.02;
            double d1 = this.f_19796_.m_188583_() * 0.02;
            double d2 = this.f_19796_.m_188583_() * 0.02;
            double d3 = 10.0;
            this.m_9236_().m_7106_(ParticleTypes.f_123759_, this.m_20165_(1.0) - d0 * 10.0, this.m_20187_() - d1 * 10.0, this.m_20262_(1.0) - d2 * 10.0, d0, d1, d2);
         }
      } else {
         this.m_9236_().m_7605_(this, (byte)20);
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
         if (!this.m_9236_().f_46443_ && this.f_19797_ % 5 == 0) {
            this.m_8022_();
         }

      }
   }

   protected void m_8022_() {
      boolean flag = !(this.m_6688_() instanceof Mob);
      boolean flag1 = !(this.m_20202_() instanceof Boat);
      this.f_21345_.m_25360_(Flag.MOVE, flag);
      this.f_21345_.m_25360_(Flag.JUMP, flag && flag1);
      this.f_21345_.m_25360_(Flag.LOOK, flag);
   }

   protected float m_5632_(float yawOffsetIn, float distanceIn) {
      this.f_21361_.m_8121_();
      return distanceIn;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return null;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("CanPickUpLoot", this.m_21531_());
      compound.m_128379_("PersistenceRequired", this.f_21353_);
      ListTag listtag = new ListTag();
      Iterator var3 = this.f_21351_.iterator();

      while(var3.hasNext()) {
         ItemStack itemstack = (ItemStack)var3.next();
         if (!itemstack.m_41619_()) {
            listtag.add(itemstack.m_41739_(this.m_321891_()));
         } else {
            listtag.add(new CompoundTag());
         }
      }

      compound.m_128365_("ArmorItems", listtag);
      ListTag listtag1 = new ListTag();
      float[] var11 = this.f_21348_;
      int var5 = var11.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         float f = var11[var6];
         listtag1.add(FloatTag.m_128566_(f));
      }

      compound.m_128365_("ArmorDropChances", listtag1);
      ListTag listtag2 = new ListTag();
      Iterator var13 = this.f_21350_.iterator();

      while(var13.hasNext()) {
         ItemStack itemstack1 = (ItemStack)var13.next();
         if (!itemstack1.m_41619_()) {
            listtag2.add(itemstack1.m_41739_(this.m_321891_()));
         } else {
            listtag2.add(new CompoundTag());
         }
      }

      compound.m_128365_("HandItems", listtag2);
      ListTag listtag3 = new ListTag();
      float[] var16 = this.f_21347_;
      int var17 = var16.length;

      for(int var8 = 0; var8 < var17; ++var8) {
         float f1 = var16[var8];
         listtag3.add(FloatTag.m_128566_(f1));
      }

      compound.m_128365_("HandDropChances", listtag3);
      if (!this.f_314973_.m_41619_()) {
         compound.m_128365_("body_armor_item", this.f_314973_.m_41739_(this.m_321891_()));
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

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128425_("CanPickUpLoot", 1)) {
         this.m_21553_(compound.m_128471_("CanPickUpLoot"));
      }

      this.f_21353_ = compound.m_128471_("PersistenceRequired");
      ListTag listtag3;
      int l;
      CompoundTag compoundtag1;
      if (compound.m_128425_("ArmorItems", 9)) {
         listtag3 = compound.m_128437_("ArmorItems", 10);

         for(l = 0; l < this.f_21351_.size(); ++l) {
            compoundtag1 = listtag3.m_128728_(l);
            this.f_21351_.set(l, ItemStack.m_318937_(this.m_321891_(), compoundtag1));
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
            this.f_21350_.set(l, ItemStack.m_318937_(this.m_321891_(), compoundtag1));
         }
      }

      if (compound.m_128425_("HandDropChances", 9)) {
         listtag3 = compound.m_128437_("HandDropChances", 5);

         for(l = 0; l < listtag3.size(); ++l) {
            this.f_21347_[l] = listtag3.m_128775_(l);
         }
      }

      if (compound.m_128425_("body_armor_item", 10)) {
         this.f_314973_ = (ItemStack)ItemStack.m_323951_(this.m_321891_(), compound.m_128469_("body_armor_item")).orElse(ItemStack.f_41583_);
         this.f_315062_ = compound.m_128457_("body_armor_drop_chance");
      } else {
         this.f_314973_ = ItemStack.f_41583_;
      }

      this.f_337544_ = this.m_340395_(compound);
      this.m_21559_(compound.m_128471_("LeftHanded"));
      if (compound.m_128425_("DeathLootTable", 8)) {
         this.f_21355_ = ResourceKey.m_135785_(Registries.f_314309_, ResourceLocation.m_338530_(compound.m_128461_("DeathLootTable")));
         this.f_21356_ = compound.m_128454_("DeathLootTableSeed");
      }

      this.m_21557_(compound.m_128471_("NoAI"));
      if (compound.m_128441_("forge:spawn_type")) {
         try {
            this.spawnType = MobSpawnType.valueOf(compound.m_128461_("forge:spawn_type"));
         } catch (Exception var5) {
            compound.m_128473_("forge:spawn_type");
         }
      }

   }

   protected void m_7625_(DamageSource damageSourceIn, boolean recentHitIn) {
      super.m_7625_(damageSourceIn, recentHitIn);
      this.f_21355_ = null;
   }

   public final ResourceKey m_5743_() {
      return this.f_21355_ == null ? this.m_7582_() : this.f_21355_;
   }

   protected ResourceKey m_7582_() {
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
      this.m_9236_().m_46473_().m_6180_("looting");
      boolean mobGriefing = this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_);
      if (Reflector.ForgeEventFactory_getMobGriefingEvent.exists()) {
         mobGriefing = Reflector.callBoolean(Reflector.ForgeEventFactory_getMobGriefingEvent, this.m_9236_(), this);
      }

      if (!this.m_9236_().f_46443_ && this.m_21531_() && this.m_6084_() && !this.f_20890_ && mobGriefing) {
         Vec3i vec3i = this.m_213552_();
         Iterator var3 = this.m_9236_().m_45976_(ItemEntity.class, this.m_20191_().m_82377_((double)vec3i.m_123341_(), (double)vec3i.m_123342_(), (double)vec3i.m_123343_())).iterator();

         while(var3.hasNext()) {
            ItemEntity itementity = (ItemEntity)var3.next();
            if (!itementity.m_213877_() && !itementity.m_32055_().m_41619_() && !itementity.m_32063_() && this.m_7243_(itementity.m_32055_())) {
               this.m_7581_(itementity);
            }
         }
      }

      this.m_9236_().m_46473_().m_7238_();
   }

   protected Vec3i m_213552_() {
      return f_217048_;
   }

   protected void m_7581_(ItemEntity itemEntity) {
      ItemStack itemstack = itemEntity.m_32055_();
      ItemStack itemstack1 = this.m_255207_(itemstack.m_41777_());
      if (!itemstack1.m_41619_()) {
         this.m_21053_(itemEntity);
         this.m_7938_(itemEntity, itemstack1.m_41613_());
         itemstack.m_41774_(itemstack1.m_41613_());
         if (itemstack.m_41619_()) {
            itemEntity.m_146870_();
         }
      }

   }

   public ItemStack m_255207_(ItemStack itemStackIn) {
      EquipmentSlot equipmentslot = this.m_147233_(itemStackIn);
      ItemStack itemstack = this.m_6844_(equipmentslot);
      boolean flag = this.m_7808_(itemStackIn, itemstack);
      if (equipmentslot.m_254934_() && !flag) {
         equipmentslot = EquipmentSlot.MAINHAND;
         itemstack = this.m_6844_(equipmentslot);
         flag = itemstack.m_41619_();
      }

      if (flag && this.m_7252_(itemStackIn)) {
         double d0 = (double)this.m_21519_(equipmentslot);
         if (!itemstack.m_41619_() && (double)Math.max(this.f_19796_.m_188501_() - 0.1F, 0.0F) < d0) {
            this.m_19983_(itemstack);
         }

         ItemStack itemstack1 = equipmentslot.m_338803_(itemStackIn);
         this.m_21468_(equipmentslot, itemstack1);
         return itemstack1;
      } else {
         return ItemStack.f_41583_;
      }
   }

   protected void m_21468_(EquipmentSlot slotIn, ItemStack itemStackIn) {
      this.m_21035_(slotIn, itemStackIn);
      this.m_21508_(slotIn);
      this.f_21353_ = true;
   }

   public void m_21508_(EquipmentSlot slotIn) {
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

   protected boolean m_7808_(ItemStack candidate, ItemStack existing) {
      if (existing.m_41619_()) {
         return true;
      } else {
         double d0;
         double d1;
         if (candidate.m_41720_() instanceof SwordItem) {
            if (!(existing.m_41720_() instanceof SwordItem)) {
               return true;
            } else {
               d1 = this.m_319522_(candidate);
               d0 = this.m_319522_(existing);
               return d1 != d0 ? d1 > d0 : this.m_21477_(candidate, existing);
            }
         } else if (candidate.m_41720_() instanceof BowItem && existing.m_41720_() instanceof BowItem) {
            return this.m_21477_(candidate, existing);
         } else if (candidate.m_41720_() instanceof CrossbowItem && existing.m_41720_() instanceof CrossbowItem) {
            return this.m_21477_(candidate, existing);
         } else {
            Item var4 = candidate.m_41720_();
            if (var4 instanceof ArmorItem) {
               ArmorItem armoritem = (ArmorItem)var4;
               if (EnchantmentHelper.m_340193_(existing, EnchantmentEffectComponents.f_337286_)) {
                  return false;
               } else if (!(existing.m_41720_() instanceof ArmorItem)) {
                  return true;
               } else {
                  ArmorItem armoritem1 = (ArmorItem)existing.m_41720_();
                  if (armoritem.m_40404_() != armoritem1.m_40404_()) {
                     return armoritem.m_40404_() > armoritem1.m_40404_();
                  } else {
                     return armoritem.m_40405_() != armoritem1.m_40405_() ? armoritem.m_40405_() > armoritem1.m_40405_() : this.m_21477_(candidate, existing);
                  }
               }
            } else {
               if (candidate.m_41720_() instanceof DiggerItem) {
                  if (existing.m_41720_() instanceof BlockItem) {
                     return true;
                  }

                  if (existing.m_41720_() instanceof DiggerItem) {
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

   private double m_319522_(ItemStack itemStackIn) {
      ItemAttributeModifiers itemattributemodifiers = (ItemAttributeModifiers)itemStackIn.m_322304_(DataComponents.f_316119_, ItemAttributeModifiers.f_314473_);
      return itemattributemodifiers.m_324178_(this.m_245892_(Attributes.f_22281_), EquipmentSlot.MAINHAND);
   }

   public boolean m_21477_(ItemStack itemStackIn, ItemStack itemStack2In) {
      return itemStackIn.m_41773_() < itemStack2In.m_41773_() ? true : m_321483_(itemStackIn) && !m_321483_(itemStack2In);
   }

   private static boolean m_321483_(ItemStack itemStackIn) {
      DataComponentMap datacomponentmap = itemStackIn.m_318732_();
      int i = datacomponentmap.m_319491_();
      return i > 1 || i == 1 && !datacomponentmap.m_321946_(DataComponents.f_313972_);
   }

   public boolean m_7252_(ItemStack stack) {
      return true;
   }

   public boolean m_7243_(ItemStack itemStackIn) {
      return this.m_7252_(itemStackIn);
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return true;
   }

   public boolean m_8023_() {
      return this.m_20159_();
   }

   protected boolean m_8028_() {
      return false;
   }

   public void m_6043_() {
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
         this.m_146870_();
      } else if (!this.m_21532_() && !this.m_8023_()) {
         Entity entity = this.m_9236_().m_45930_(this, -1.0);
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
            if (d0 > (double)j && this.m_6785_(d0)) {
               this.m_146870_();
            }

            int k = this.m_6095_().m_20674_().m_21612_();
            int l = k * k;
            if (this.f_20891_ > 600 && this.f_19796_.m_188503_(800) == 0 && d0 > (double)l && this.m_6785_(d0)) {
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
      ++this.f_20891_;
      ProfilerFiller profilerfiller = this.m_9236_().m_46473_();
      profilerfiller.m_6180_("sensing");
      this.f_21349_.m_26789_();
      profilerfiller.m_7238_();
      int i = this.f_19797_ + this.m_19879_();
      if (i % 2 != 0 && this.f_19797_ > 1) {
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
      DebugPackets.m_133699_(this.m_9236_(), this, this.f_21345_);
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
      float f2 = Mth.m_14177_(this.f_20883_ - f1);
      float f3 = Mth.m_14036_(Mth.m_14177_(this.f_20883_ - f1), -f, f);
      float f4 = f1 + f2 - f3;
      this.m_5616_(f4);
   }

   public int m_21529_() {
      return 10;
   }

   public void m_21391_(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
      double d0 = entityIn.m_20185_() - this.m_20185_();
      double d2 = entityIn.m_20189_() - this.m_20189_();
      double d1;
      if (entityIn instanceof LivingEntity livingentity) {
         d1 = livingentity.m_20188_() - this.m_20188_();
      } else {
         d1 = (entityIn.m_20191_().f_82289_ + entityIn.m_20191_().f_82292_) / 2.0 - this.m_20188_();
      }

      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = (float)(Mth.m_14136_(d2, d0) * 180.0 / 3.1415927410125732) - 90.0F;
      float f1 = (float)(-(Mth.m_14136_(d1, d3) * 180.0 / 3.1415927410125732));
      this.m_146926_(this.m_21376_(this.m_146909_(), f1, maxPitchIncrease));
      this.m_146922_(this.m_21376_(this.m_146908_(), f, maxYawIncrease));
   }

   private float m_21376_(float angle, float targetAngle, float maxIncrease) {
      float f = Mth.m_14177_(targetAngle - angle);
      if (f > maxIncrease) {
         f = maxIncrease;
      }

      if (f < -maxIncrease) {
         f = -maxIncrease;
      }

      return angle + f;
   }

   public static boolean m_217057_(EntityType typeIn, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
      BlockPos blockpos = pos.m_7495_();
      return reason == MobSpawnType.SPAWNER || worldIn.m_8055_(blockpos).m_60643_(worldIn, blockpos, typeIn);
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return true;
   }

   public boolean m_6914_(LevelReader worldIn) {
      return !worldIn.m_46855_(this.m_20191_()) && worldIn.m_45784_(this);
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
         i -= (3 - this.m_9236_().m_46791_().m_19028_()) * 4;
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

   public ItemStack m_319275_() {
      return this.f_314973_;
   }

   public boolean m_30729_(EquipmentSlot slotIn) {
      return slotIn != EquipmentSlot.BODY;
   }

   public boolean m_324340_() {
      return !this.m_6844_(EquipmentSlot.BODY).m_41619_();
   }

   public boolean m_30833_(ItemStack itemStackIn) {
      return false;
   }

   public void m_323866_(ItemStack itemStackIn) {
      this.m_21468_(EquipmentSlot.BODY, itemStackIn);
   }

   public Iterable m_322068_() {
      return (Iterable)(this.f_314973_.m_41619_() ? this.f_21351_ : Iterables.concat(this.f_21351_, List.of(this.f_314973_)));
   }

   public ItemStack m_6844_(EquipmentSlot slotIn) {
      ItemStack var10000;
      switch (slotIn.m_20743_()) {
         case HAND:
            var10000 = (ItemStack)this.f_21350_.get(slotIn.m_20749_());
            break;
         case HUMANOID_ARMOR:
            var10000 = (ItemStack)this.f_21351_.get(slotIn.m_20749_());
            break;
         case ANIMAL_ARMOR:
            var10000 = this.f_314973_;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public void m_21035_(EquipmentSlot slotIn, ItemStack itemStackIn) {
      this.m_181122_(itemStackIn);
      switch (slotIn.m_20743_()) {
         case HAND:
            this.m_238392_(slotIn, (ItemStack)this.f_21350_.set(slotIn.m_20749_(), itemStackIn), itemStackIn);
            break;
         case HUMANOID_ARMOR:
            this.m_238392_(slotIn, (ItemStack)this.f_21351_.set(slotIn.m_20749_(), itemStackIn), itemStackIn);
            break;
         case ANIMAL_ARMOR:
            ItemStack itemstack = this.f_314973_;
            this.f_314973_ = itemStackIn;
            this.m_238392_(slotIn, itemstack, itemStackIn);
      }

   }

   protected void m_7472_(ServerLevel worldIn, DamageSource sourceIn, boolean recentlyHitIn) {
      super.m_7472_(worldIn, sourceIn, recentlyHitIn);
      EquipmentSlot[] var4 = EquipmentSlot.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EquipmentSlot equipmentslot = var4[var6];
         ItemStack itemstack = this.m_6844_(equipmentslot);
         float f = this.m_21519_(equipmentslot);
         if (f != 0.0F) {
            boolean flag = f > 1.0F;
            Entity entity = sourceIn.m_7639_();
            if (entity instanceof LivingEntity) {
               LivingEntity livingentity = (LivingEntity)entity;
               Level var14 = this.m_9236_();
               if (var14 instanceof ServerLevel) {
                  ServerLevel serverlevel = (ServerLevel)var14;
                  f = EnchantmentHelper.m_339734_(serverlevel, livingentity, sourceIn, f);
               }
            }

            if (!itemstack.m_41619_() && !EnchantmentHelper.m_340193_(itemstack, EnchantmentEffectComponents.f_337159_) && (recentlyHitIn || flag) && this.f_19796_.m_188501_() < f) {
               if (!flag && itemstack.m_41763_()) {
                  itemstack.m_41721_(itemstack.m_41776_() - this.f_19796_.m_188503_(1 + this.f_19796_.m_188503_(Math.max(itemstack.m_41776_() - 3, 1))));
               }

               this.m_19983_(itemstack);
               this.m_21035_(equipmentslot, ItemStack.f_41583_);
            }
         }
      }

   }

   protected float m_21519_(EquipmentSlot slotIn) {
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
      EquipmentSlot[] var3 = EquipmentSlot.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EquipmentSlot equipmentslot = var3[var5];
         ItemStack itemstack = this.m_6844_(equipmentslot);
         if (!itemstack.m_41619_()) {
            if (!checkIn.test(itemstack)) {
               set.add(equipmentslot);
            } else {
               double d0 = (double)this.m_21519_(equipmentslot);
               if (d0 > 1.0) {
                  this.m_21035_(equipmentslot, ItemStack.f_41583_);
                  this.m_19983_(itemstack);
               }
            }
         }
      }

      return set;
   }

   private LootParams m_320276_(ServerLevel levelIn) {
      return (new LootParams.Builder(levelIn)).m_287286_(LootContextParams.f_81460_, this.m_20182_()).m_287286_(LootContextParams.f_81455_, this).m_287235_(LootContextParamSets.f_313897_);
   }

   public void m_319416_(EquipmentTable tableIn) {
      this.m_322414_(tableIn.f_316700_(), tableIn.f_315505_());
   }

   public void m_322414_(ResourceKey keyIn, Map mapIn) {
      Level var4 = this.m_9236_();
      if (var4 instanceof ServerLevel serverlevel) {
         this.m_319719_(keyIn, this.m_320276_(serverlevel), mapIn);
      }

   }

   protected void m_213945_(RandomSource randomIn, DifficultyInstance difficulty) {
      if (randomIn.m_188501_() < 0.15F * difficulty.m_19057_()) {
         int i = randomIn.m_188503_(2);
         float f = this.m_9236_().m_46791_() == Difficulty.HARD ? 0.1F : 0.25F;
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
         EquipmentSlot[] var6 = EquipmentSlot.values();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            EquipmentSlot equipmentslot = var6[var8];
            if (equipmentslot.m_20743_() == Type.HUMANOID_ARMOR) {
               ItemStack itemstack = this.m_6844_(equipmentslot);
               if (!flag && randomIn.m_188501_() < f) {
                  break;
               }

               flag = false;
               if (itemstack.m_41619_()) {
                  Item item = m_21412_(equipmentslot, i);
                  if (item != null) {
                     this.m_21035_(equipmentslot, new ItemStack(item));
                  }
               }
            }
         }
      }

   }

   @Nullable
   public static Item m_21412_(EquipmentSlot slotIn, int chance) {
      switch (slotIn) {
         case HEAD:
            if (chance == 0) {
               return Items.f_42407_;
            } else if (chance == 1) {
               return Items.f_42476_;
            } else if (chance == 2) {
               return Items.f_42464_;
            } else if (chance == 3) {
               return Items.f_42468_;
            } else if (chance == 4) {
               return Items.f_42472_;
            }
         case CHEST:
            if (chance == 0) {
               return Items.f_42408_;
            } else if (chance == 1) {
               return Items.f_42477_;
            } else if (chance == 2) {
               return Items.f_42465_;
            } else if (chance == 3) {
               return Items.f_42469_;
            } else if (chance == 4) {
               return Items.f_42473_;
            }
         case LEGS:
            if (chance == 0) {
               return Items.f_42462_;
            } else if (chance == 1) {
               return Items.f_42478_;
            } else if (chance == 2) {
               return Items.f_42466_;
            } else if (chance == 3) {
               return Items.f_42470_;
            } else if (chance == 4) {
               return Items.f_42474_;
            }
         case FEET:
            if (chance == 0) {
               return Items.f_42463_;
            } else if (chance == 1) {
               return Items.f_42479_;
            } else if (chance == 2) {
               return Items.f_42467_;
            } else if (chance == 3) {
               return Items.f_42471_;
            } else if (chance == 4) {
               return Items.f_42475_;
            }
         default:
            return null;
      }
   }

   protected void m_213946_(ServerLevelAccessor worldIn, RandomSource randomIn, DifficultyInstance difficultyIn) {
      this.m_214095_(worldIn, randomIn, difficultyIn);
      EquipmentSlot[] var4 = EquipmentSlot.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EquipmentSlot equipmentslot = var4[var6];
         if (equipmentslot.m_20743_() == Type.HUMANOID_ARMOR) {
            this.m_217051_(worldIn, randomIn, equipmentslot, difficultyIn);
         }
      }

   }

   protected void m_214095_(ServerLevelAccessor worldIn, RandomSource randomIn, DifficultyInstance difficultyIn) {
      this.m_340386_(worldIn, EquipmentSlot.MAINHAND, randomIn, 0.25F, difficultyIn);
   }

   protected void m_217051_(ServerLevelAccessor worldIn, RandomSource randomIn, EquipmentSlot slotIn, DifficultyInstance difficultyIn) {
      this.m_340386_(worldIn, slotIn, randomIn, 0.5F, difficultyIn);
   }

   private void m_340386_(ServerLevelAccessor worldIn, EquipmentSlot slotIn, RandomSource randomIn, float difficultyMulIn, DifficultyInstance difficultyIn) {
      ItemStack itemstack = this.m_6844_(slotIn);
      if (!itemstack.m_41619_() && randomIn.m_188501_() < difficultyMulIn * difficultyIn.m_19057_()) {
         EnchantmentHelper.m_338695_(itemstack, worldIn.m_9598_(), VanillaEnchantmentProviders.f_337458_, difficultyIn, randomIn);
         this.m_21035_(slotIn, itemstack);
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn) {
      RandomSource randomsource = worldIn.m_213780_();
      AttributeInstance attributeinstance = (AttributeInstance)Objects.requireNonNull(this.m_21051_(Attributes.f_22277_));
      if (!attributeinstance.m_22109_(f_337189_)) {
         attributeinstance.m_22125_(new AttributeModifier(f_337189_, randomsource.m_216328_(0.0, 0.11485000000000001), Operation.ADD_MULTIPLIED_BASE));
      }

      this.m_21559_(randomsource.m_188501_() < 0.05F);
      this.spawnType = reason;
      return spawnDataIn;
   }

   public void m_21530_() {
      this.f_21353_ = true;
   }

   public void m_21409_(EquipmentSlot slotIn, float chance) {
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

   public boolean m_7066_(ItemStack itemstackIn) {
      EquipmentSlot equipmentslot = this.m_147233_(itemstackIn);
      return this.m_6844_(equipmentslot).m_41619_() && this.m_21531_();
   }

   public boolean m_21532_() {
      return this.f_21353_;
   }

   public final InteractionResult m_6096_(Player player, InteractionHand hand) {
      if (!this.m_6084_()) {
         return InteractionResult.PASS;
      } else {
         InteractionResult interactionresult = this.m_21499_(player, hand);
         if (interactionresult.m_19077_()) {
            this.m_146852_(GameEvent.f_223708_, player);
            return interactionresult;
         } else {
            InteractionResult interactionresult1 = super.m_6096_(player, hand);
            if (interactionresult1 != InteractionResult.PASS) {
               return interactionresult1;
            } else {
               interactionresult = this.m_6071_(player, hand);
               if (interactionresult.m_19077_()) {
                  this.m_146852_(GameEvent.f_223708_, player);
                  return interactionresult;
               } else {
                  return InteractionResult.PASS;
               }
            }
         }
      }
   }

   private InteractionResult m_21499_(Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      if (itemstack.m_150930_(Items.f_42656_)) {
         InteractionResult interactionresult = itemstack.m_41647_(playerIn, this, handIn);
         if (interactionresult.m_19077_()) {
            return interactionresult;
         }
      }

      if (itemstack.m_41720_() instanceof SpawnEggItem) {
         if (this.m_9236_() instanceof ServerLevel) {
            SpawnEggItem spawneggitem = (SpawnEggItem)itemstack.m_41720_();
            Optional optional = spawneggitem.m_43215_(playerIn, this, this.m_6095_(), (ServerLevel)this.m_9236_(), this.m_20182_(), itemstack);
            optional.ifPresent((mobIn) -> {
               this.m_5502_(playerIn, mobIn);
            });
            return optional.isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
         } else {
            return InteractionResult.CONSUME;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   protected void m_5502_(Player playerIn, Mob child) {
   }

   protected InteractionResult m_6071_(Player playerIn, InteractionHand handIn) {
      return InteractionResult.PASS;
   }

   public boolean m_21533_() {
      return this.m_21444_(this.m_20183_());
   }

   public boolean m_21444_(BlockPos pos) {
      return this.f_21341_ == -1.0F ? true : this.f_21360_.m_123331_(pos) < (double)(this.f_21341_ * this.f_21341_);
   }

   public void m_21446_(BlockPos pos, int distance) {
      this.f_21360_ = pos;
      this.f_21341_ = (float)distance;
   }

   public BlockPos m_21534_() {
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
   public Mob m_21406_(EntityType typeIn, boolean equipmentIn) {
      if (this.m_213877_()) {
         return null;
      } else {
         Mob t = (Mob)typeIn.m_20615_(this.m_9236_());
         if (t == null) {
            return null;
         } else {
            t.m_20359_(this);
            t.m_6863_(this.m_6162_());
            t.m_21557_(this.m_21525_());
            if (this.m_8077_()) {
               t.m_6593_(this.m_7770_());
               t.m_20340_(this.m_20151_());
            }

            if (this.m_21532_()) {
               t.m_21530_();
            }

            t.m_20331_(this.m_20147_());
            if (equipmentIn) {
               t.m_21553_(this.m_21531_());
               EquipmentSlot[] var4 = EquipmentSlot.values();
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  EquipmentSlot equipmentslot = var4[var6];
                  ItemStack itemstack = this.m_6844_(equipmentslot);
                  if (!itemstack.m_41619_()) {
                     t.m_21035_(equipmentslot, itemstack.m_278832_());
                     t.m_21409_(equipmentslot, this.m_21519_(equipmentslot));
                  }
               }
            }

            this.m_9236_().m_7967_(t);
            if (this.m_20159_()) {
               Entity entity = this.m_20202_();
               this.m_8127_();
               t.m_7998_(entity, true);
            }

            this.m_146870_();
            return t;
         }
      }
   }

   @Nullable
   public Leashable.LeashData m_338492_() {
      return this.f_337544_;
   }

   public void m_338401_(@Nullable Leashable.LeashData leashDataIn) {
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
      this.f_21345_.m_25355_(Flag.MOVE);
   }

   public boolean m_6573_() {
      return !(this instanceof Enemy);
   }

   public boolean m_7998_(Entity entityIn, boolean force) {
      boolean flag = super.m_7998_(entityIn, force);
      if (flag && this.m_339418_()) {
         this.m_21455_(true, true);
      }

      return flag;
   }

   public boolean m_21515_() {
      return super.m_21515_() && !this.m_21525_();
   }

   public void m_21557_(boolean disable) {
      byte b0 = (Byte)this.f_19804_.m_135370_(f_21340_);
      this.f_19804_.m_135381_(f_21340_, disable ? (byte)(b0 | 1) : (byte)(b0 & -2));
   }

   public void m_21559_(boolean leftHanded) {
      byte b0 = (Byte)this.f_19804_.m_135370_(f_21340_);
      this.f_19804_.m_135381_(f_21340_, leftHanded ? (byte)(b0 | 2) : (byte)(b0 & -3));
   }

   public void m_21561_(boolean hasAggro) {
      byte b0 = (Byte)this.f_19804_.m_135370_(f_21340_);
      this.f_19804_.m_135381_(f_21340_, hasAggro ? (byte)(b0 | 4) : (byte)(b0 & -5));
   }

   public boolean m_21525_() {
      return ((Byte)this.f_19804_.m_135370_(f_21340_) & 1) != 0;
   }

   public boolean m_21526_() {
      return ((Byte)this.f_19804_.m_135370_(f_21340_) & 2) != 0;
   }

   public boolean m_5912_() {
      return ((Byte)this.f_19804_.m_135370_(f_21340_) & 4) != 0;
   }

   public void m_6863_(boolean childZombie) {
   }

   public HumanoidArm m_5737_() {
      return this.m_21526_() ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   public boolean m_217066_(LivingEntity entityIn) {
      return this.m_292684_().m_82381_(entityIn.m_293919_());
   }

   protected AABB m_292684_() {
      Entity entity = this.m_20202_();
      AABB aabb;
      if (entity != null) {
         AABB aabb1 = entity.m_20191_();
         AABB aabb2 = this.m_20191_();
         aabb = new AABB(Math.min(aabb2.f_82288_, aabb1.f_82288_), aabb2.f_82289_, Math.min(aabb2.f_82290_, aabb1.f_82290_), Math.max(aabb2.f_82291_, aabb1.f_82291_), aabb2.f_82292_, Math.max(aabb2.f_82293_, aabb1.f_82293_));
      } else {
         aabb = this.m_20191_();
      }

      return aabb.m_82377_(f_290867_, 0.0, f_290867_);
   }

   public boolean m_7327_(Entity entityIn) {
      float f = (float)this.m_246858_(Attributes.f_22281_);
      DamageSource damagesource = this.m_269291_().m_269333_(this);
      Level var5 = this.m_9236_();
      if (var5 instanceof ServerLevel serverlevel) {
         f = EnchantmentHelper.m_338960_(serverlevel, this.m_338776_(), entityIn, damagesource, f);
      }

      boolean flag = entityIn.m_6469_(damagesource, f);
      if (flag) {
         float f1 = this.m_338419_(entityIn, damagesource);
         if (f1 > 0.0F && entityIn instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entityIn;
            livingentity.m_147240_((double)(f1 * 0.5F), (double)Mth.m_14031_(this.m_146908_() * 0.017453292F), (double)(-Mth.m_14089_(this.m_146908_() * 0.017453292F)));
            this.m_20256_(this.m_20184_().m_82542_(0.6, 1.0, 0.6));
         }

         Level var7 = this.m_9236_();
         if (var7 instanceof ServerLevel) {
            ServerLevel serverlevel1 = (ServerLevel)var7;
            EnchantmentHelper.m_338760_(serverlevel1, entityIn, damagesource);
         }

         this.m_21335_(entityIn);
         this.m_339982_();
      }

      return flag;
   }

   protected void m_339982_() {
   }

   protected boolean m_21527_() {
      if (this.m_9236_().m_46461_() && !this.m_9236_().f_46443_) {
         float f = this.m_213856_();
         BlockPos blockpos = BlockPos.m_274561_(this.m_20185_(), this.m_20188_(), this.m_20189_());
         boolean flag = this.m_20071_() || this.f_146808_ || this.f_146809_;
         if (f > 0.5F && this.f_19796_.m_188501_() * 30.0F < (f - 0.4F) * 2.0F && !flag && this.m_9236_().m_45527_(blockpos)) {
            return true;
         }
      }

      return false;
   }

   protected void m_203347_(TagKey fluidTag) {
      this.jumpInLiquidInternal(() -> {
         super.m_203347_(fluidTag);
      });
   }

   private void jumpInLiquidInternal(Runnable onSuper) {
      if (this.m_21573_().m_26576_()) {
         onSuper.run();
      } else {
         this.m_20256_(this.m_20184_().m_82520_(0.0, 0.3, 0.0));
      }

   }

   public void jumpInFluid(FluidType type) {
      this.jumpInLiquidInternal(() -> {
         IForgeLivingEntity.super.jumpInFluid(type);
      });
   }

   public final MobSpawnType getSpawnType() {
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
      super.m_6089_();
      this.m_323629_().forEach((itemStackIn) -> {
         if (!itemStackIn.m_41619_()) {
            itemStackIn.m_41764_(0);
         }

      });
   }

   @Nullable
   public ItemStack m_142340_() {
      SpawnEggItem spawneggitem = SpawnEggItem.m_43213_(this.m_6095_());
      return spawneggitem == null ? null : new ItemStack(spawneggitem);
   }

   private boolean canSkipUpdate() {
      if (this.m_6162_()) {
         return false;
      } else if (this.f_20916_ > 0) {
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
            Entity player = (Entity)listPlayers.get(0);
            double dx = Math.max(Math.abs(this.m_20185_() - player.m_20185_()) - 16.0, 0.0);
            double dz = Math.max(Math.abs(this.m_20189_() - player.m_20189_()) - 16.0, 0.0);
            double distSq = dx * dx + dz * dz;
            return !this.m_6783_(distSq);
         }
      }
   }

   private List getListPlayers(Level entityWorld) {
      Level world = this.m_20193_();
      if (world instanceof ClientLevel worldClient) {
         return worldClient.m_6907_();
      } else if (world instanceof ServerLevel worldServer) {
         return worldServer.m_6907_();
      } else {
         return null;
      }
   }

   private void onUpdateMinimal() {
      ++this.f_20891_;
      if (this instanceof Monster) {
         float brightness = this.m_213856_();
         boolean raider = this instanceof Raider;
         if (brightness > 0.5F || raider) {
            this.f_20891_ += 2;
         }
      }

   }

   static {
      f_21340_ = SynchedEntityData.m_135353_(Mob.class, EntityDataSerializers.f_135027_);
      f_217048_ = new Vec3i(1, 0, 1);
      f_290867_ = Math.sqrt(2.0399999618530273) - 0.6000000238418579;
      f_337189_ = ResourceLocation.m_340282_("random_spawn_bonus");
   }
}
