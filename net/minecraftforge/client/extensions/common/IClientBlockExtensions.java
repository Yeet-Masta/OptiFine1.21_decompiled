package net.minecraftforge.client.extensions.common;

import net.minecraft.src.C_1596_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_4675_;

public interface IClientBlockExtensions {
   IClientBlockExtensions DUMMY = new IClientBlockExtensions() {
   };

   static IClientBlockExtensions of(BlockState blockState) {
      return DUMMY;
   }

   default boolean addDestroyEffects(BlockState state, C_1596_ Level, C_4675_ pos, ParticleEngine manager) {
      return false;
   }

   default boolean addHitEffects(BlockState state, C_1596_ Level, C_3043_ target, ParticleEngine manager) {
      return false;
   }
}
