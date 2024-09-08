package net.optifine;

import java.util.UUID;
import net.minecraft.src.C_1325_;
import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5247_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_547_;
import net.minecraft.src.C_976_;

public class RandomEntity implements IRandomEntity {
   private C_507_ entity;

   @Override
   public int getId() {
      UUID uuid = this.entity.m_20148_();
      long uuidLow = uuid.getLeastSignificantBits();
      return (int)(uuidLow & 2147483647L);
   }

   public C_4675_ getSpawnPosition() {
      return this.entity.m_20088_().spawnPosition;
   }

   public C_1629_ getSpawnBiome() {
      return this.entity.m_20088_().spawnBiome;
   }

   @Override
   public String getName() {
      return this.entity.m_8077_() ? this.entity.m_7770_().getString() : null;
   }

   @Override
   public int getHealth() {
      return !(this.entity instanceof C_524_ el) ? 0 : (int)el.m_21223_();
   }

   @Override
   public int getMaxHealth() {
      return !(this.entity instanceof C_524_ el) ? 0 : (int)el.m_21233_();
   }

   public C_507_ getEntity() {
      return this.entity;
   }

   public void setEntity(C_507_ entity) {
      this.entity = entity;
   }

   public C_4917_ getNbtTag() {
      C_5247_ edm = this.entity.m_20088_();
      C_4917_ nbt = edm.nbtTag;
      long timeMs = System.currentTimeMillis();
      if (nbt == null || edm.nbtTagUpdateMs < timeMs - 1000L) {
         nbt = new C_4917_();
         this.entity.m_20240_(nbt);
         if (this.entity instanceof C_547_ et) {
            nbt.m_128379_("Sitting", et.m_21825_());
         }

         edm.nbtTag = nbt;
         edm.nbtTagUpdateMs = timeMs;
      }

      return nbt;
   }

   public C_1353_ getColor() {
      return RandomEntityRule.getEntityColor(this.entity);
   }

   public C_2064_ getBlockState() {
      if (this.entity instanceof C_976_ ie && ie.m_32055_().m_41720_() instanceof C_1325_ bi) {
         return bi.m_40614_().m_49966_();
      }

      C_5247_ edm = this.entity.m_20088_();
      C_2064_ bs = edm.blockStateOn;
      long timeMs = System.currentTimeMillis();
      if (bs == null || edm.blockStateOnUpdateMs < timeMs - 50L) {
         C_4675_ pos = this.entity.m_20183_();
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
