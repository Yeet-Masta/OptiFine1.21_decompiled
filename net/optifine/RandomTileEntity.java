package net.optifine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.optifine.util.TileEntityUtils;

public class RandomTileEntity implements IRandomEntity {
   private BlockEntity tileEntity;
   private static final CompoundTag EMPTY_TAG = new CompoundTag();

   public int getId() {
      return Config.getRandom(this.getSpawnPosition(), 0);
   }

   public BlockPos getSpawnPosition() {
      if (this.tileEntity instanceof BedBlockEntity) {
         BedBlockEntity bbe = (BedBlockEntity)this.tileEntity;
         BlockState bs = bbe.m_58900_();
         BedPart part = (BedPart)bs.m_61143_(BedBlock.f_49440_);
         if (part == BedPart.HEAD) {
            Direction dir = (Direction)bs.m_61143_(BedBlock.f_54117_);
            return this.tileEntity.m_58899_().m_121945_(dir.m_122424_());
         }
      }

      return this.tileEntity.m_58899_();
   }

   public String getName() {
      String name = TileEntityUtils.getTileEntityName(this.tileEntity);
      return name;
   }

   public Biome getSpawnBiome() {
      return (Biome)this.tileEntity.m_58904_().m_204166_(this.tileEntity.m_58899_()).m_203334_();
   }

   public int getHealth() {
      return -1;
   }

   public int getMaxHealth() {
      return -1;
   }

   public BlockEntity getTileEntity() {
      return this.tileEntity;
   }

   public void setTileEntity(BlockEntity tileEntity) {
      this.tileEntity = tileEntity;
   }

   public CompoundTag getNbtTag() {
      CompoundTag nbt = this.tileEntity.nbtTag;
      long timeMs = System.currentTimeMillis();
      if (nbt == null || this.tileEntity.nbtTagUpdateMs < timeMs - 1000L) {
         this.tileEntity.nbtTag = makeNbtTag(this.tileEntity);
         this.tileEntity.nbtTagUpdateMs = timeMs;
      }

      return nbt;
   }

   private static CompoundTag makeNbtTag(BlockEntity te) {
      Level world = te.m_58904_();
      if (world == null) {
         return EMPTY_TAG;
      } else {
         RegistryAccess ra = world.m_9598_();
         return ra == null ? EMPTY_TAG : te.m_187482_(ra);
      }
   }

   public DyeColor getColor() {
      return RandomEntityRule.getBlockEntityColor(this.tileEntity);
   }

   public BlockState getBlockState() {
      return this.tileEntity.m_58900_();
   }

   public String toString() {
      return this.tileEntity.toString();
   }
}
