package net.minecraftforge.client.extensions.common;

import net.minecraft.src.C_1596_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_4024_;
import net.minecraft.src.C_4675_;

public interface IClientBlockExtensions {
   IClientBlockExtensions DUMMY = new IClientBlockExtensions() {
   };

   // $FF: renamed from: of (net.minecraft.src.C_2064_) net.minecraftforge.client.extensions.common.IClientBlockExtensions
   static IClientBlockExtensions method_2(C_2064_ blockState) {
      return DUMMY;
   }

   default boolean addDestroyEffects(C_2064_ state, C_1596_ Level, C_4675_ pos, C_4024_ manager) {
      return false;
   }

   default boolean addHitEffects(C_2064_ state, C_1596_ Level, C_3043_ target, C_4024_ manager) {
      return false;
   }
}
