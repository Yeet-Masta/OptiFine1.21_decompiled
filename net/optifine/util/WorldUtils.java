package net.optifine.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class WorldUtils {
   public static int getDimensionId(Level world) {
      return world == null ? 0 : getDimensionId(world.m_46472_());
   }

   public static int getDimensionId(ResourceKey dimension) {
      if (dimension == Level.f_46429_) {
         return -1;
      } else if (dimension == Level.f_46428_) {
         return 0;
      } else {
         return dimension == Level.f_46430_ ? 1 : 0;
      }
   }

   public static boolean isNether(Level world) {
      return world.m_46472_() == Level.f_46429_;
   }

   public static boolean isOverworld(Level world) {
      ResourceKey dimension = world.m_46472_();
      return getDimensionId(dimension) == 0;
   }

   public static boolean isEnd(Level world) {
      return world.m_46472_() == Level.f_46430_;
   }
}
