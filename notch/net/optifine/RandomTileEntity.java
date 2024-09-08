package net.optifine;

import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1699_;
import net.minecraft.src.C_1984_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2081_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_4706_;
import net.minecraft.src.C_4917_;
import net.optifine.util.TileEntityUtils;

public class RandomTileEntity implements IRandomEntity {
   private C_1991_ tileEntity;
   private static final C_4917_ EMPTY_TAG = new C_4917_();

   @Override
   public int getId() {
      return Config.getRandom(this.getSpawnPosition(), 0);
   }

   public C_4675_ getSpawnPosition() {
      if (this.tileEntity instanceof C_1984_ bbe) {
         C_2064_ bs = bbe.n();
         C_2081_ part = (C_2081_)bs.c(C_1699_.f_49440_);
         if (part == C_2081_.HEAD) {
            C_4687_ dir = (C_4687_)bs.c(C_1699_.aE);
            return this.tileEntity.m_58899_().m_121945_(dir.m_122424_());
         }
      }

      return this.tileEntity.m_58899_();
   }

   @Override
   public String getName() {
      return TileEntityUtils.getTileEntityName(this.tileEntity);
   }

   public C_1629_ getSpawnBiome() {
      return (C_1629_)this.tileEntity.m_58904_().t(this.tileEntity.m_58899_()).m_203334_();
   }

   @Override
   public int getHealth() {
      return -1;
   }

   @Override
   public int getMaxHealth() {
      return -1;
   }

   public C_1991_ getTileEntity() {
      return this.tileEntity;
   }

   public void setTileEntity(C_1991_ tileEntity) {
      this.tileEntity = tileEntity;
   }

   public C_4917_ getNbtTag() {
      C_4917_ nbt = this.tileEntity.nbtTag;
      long timeMs = System.currentTimeMillis();
      if (nbt == null || this.tileEntity.nbtTagUpdateMs < timeMs - 1000L) {
         this.tileEntity.nbtTag = makeNbtTag(this.tileEntity);
         this.tileEntity.nbtTagUpdateMs = timeMs;
      }

      return nbt;
   }

   private static C_4917_ makeNbtTag(C_1991_ te) {
      C_1596_ world = te.m_58904_();
      if (world == null) {
         return EMPTY_TAG;
      } else {
         C_4706_ ra = world.m_9598_();
         return ra == null ? EMPTY_TAG : te.m_187482_(ra);
      }
   }

   public C_1353_ getColor() {
      return RandomEntityRule.getBlockEntityColor(this.tileEntity);
   }

   public C_2064_ getBlockState() {
      return this.tileEntity.m_58900_();
   }

   public String toString() {
      return this.tileEntity.toString();
   }
}
