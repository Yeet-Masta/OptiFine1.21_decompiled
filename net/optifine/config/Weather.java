package net.optifine.config;

import net.minecraft.world.level.Level;

public enum Weather {
   CLEAR,
   RAIN,
   THUNDER;

   public static Weather getWeather(Level world, float partialTicks) {
      float thunderStrength = world.m_46661_(partialTicks);
      if (thunderStrength > 0.5F) {
         return THUNDER;
      } else {
         float rainStrength = world.m_46722_(partialTicks);
         return rainStrength > 0.5F ? RAIN : CLEAR;
      }
   }

   // $FF: synthetic method
   private static Weather[] $values() {
      return new Weather[]{CLEAR, RAIN, THUNDER};
   }
}
