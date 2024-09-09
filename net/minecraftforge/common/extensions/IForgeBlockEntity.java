package net.minecraftforge.common.extensions;

import net.minecraft.src.C_3040_;
import net.minecraft.src.C_4917_;

public interface IForgeBlockEntity {
   C_3040_ INFINITE_EXTENT_AABB = new C_3040_(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

   C_4917_ getPersistentData();

   default void requestModelDataUpdate() {
   }

   default void onChunkUnloaded() {
   }
}
