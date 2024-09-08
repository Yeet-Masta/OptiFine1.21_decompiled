package net.optifine;

import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4917_;

public interface IRandomEntity {
   int getId();

   C_4675_ getSpawnPosition();

   C_1629_ getSpawnBiome();

   String getName();

   int getHealth();

   int getMaxHealth();

   C_4917_ getNbtTag();

   C_1353_ getColor();

   C_2064_ getBlockState();
}
