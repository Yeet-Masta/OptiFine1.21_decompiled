package net.optifine;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class RandomEntity implements IRandomEntity {
   private Entity entity;

   @Override
   public int getId() {
      UUID uuid = this.entity.m_20148_();
      long uuidLow = uuid.getLeastSignificantBits();
      return (int)(uuidLow & 2147483647L);
   }

   @Override
   public BlockPos getSpawnPosition() {
      return this.entity.m_20088_().spawnPosition;
   }

   @Override
   public Biome getSpawnBiome() {
      return this.entity.m_20088_().spawnBiome;
   }

   @Override
   public String getName() {
      return this.entity.m_8077_() ? this.entity.m_7770_().getString() : null;
   }

   @Override
   public int getHealth() {
      return !(this.entity instanceof LivingEntity el) ? 0 : (int)el.m_21223_();
   }

   @Override
   public int getMaxHealth() {
      return !(this.entity instanceof LivingEntity el) ? 0 : (int)el.m_21233_();
   }

   public Entity getEntity() {
      return this.entity;
   }

   public void setEntity(Entity entity) {
      this.entity = entity;
   }

   @Override
   public CompoundTag getNbtTag() {
      SynchedEntityData edm = this.entity.m_20088_();
      CompoundTag nbt = edm.nbtTag;
      long timeMs = System.currentTimeMillis();
      if (nbt == null || edm.nbtTagUpdateMs < timeMs - 1000L) {
         nbt = new CompoundTag();
         this.entity.m_20240_(nbt);
         if (this.entity instanceof TamableAnimal et) {
            nbt.m_128379_("Sitting", et.m_21825_());
         }

         edm.nbtTag = nbt;
         edm.nbtTagUpdateMs = timeMs;
      }

      return nbt;
   }

   @Override
   public DyeColor m_130045_() {
      return RandomEntityRule.getEntityColor(this.entity);
   }

   @Override
   public BlockState getBlockState() {
      if (this.entity instanceof ItemEntity ie && ie.m_32055_().m_41720_() instanceof BlockItem bi) {
         return bi.m_40614_().m_49966_();
      }

      SynchedEntityData edm = this.entity.m_20088_();
      BlockState bs = edm.blockStateOn;
      long timeMs = System.currentTimeMillis();
      if (bs == null || edm.blockStateOnUpdateMs < timeMs - 50L) {
         BlockPos pos = this.entity.m_20183_();
         bs = this.entity.m_20193_().m_8055_(pos);
         if (bs.m_60795_()) {
            bs = this.entity.m_20193_().m_8055_(pos.m_7495_());
         }

         edm.blockStateOn = bs;
         edm.blockStateOnUpdateMs = timeMs;
      }

      return bs;
   }

   public String toString() {
      return this.entity.toString();
   }
}
