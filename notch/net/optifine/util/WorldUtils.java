package net.optifine.util;

import net.minecraft.src.C_1596_;
import net.minecraft.src.C_5264_;

public class WorldUtils {
   public static int getDimensionId(C_1596_ world) {
      return world == null ? 0 : getDimensionId(world.m_46472_());
   }

   public static int getDimensionId(C_5264_<C_1596_> dimension) {
      if (dimension == C_1596_.f_46429_) {
         return -1;
      } else if (dimension == C_1596_.f_46428_) {
         return 0;
      } else {
         return dimension == C_1596_.f_46430_ ? 1 : 0;
      }
   }

   public static boolean isNether(C_1596_ world) {
      return world.m_46472_() == C_1596_.f_46429_;
   }

   public static boolean isOverworld(C_1596_ world) {
      C_5264_<C_1596_> dimension = world.m_46472_();
      return getDimensionId(dimension) == 0;
   }

   public static boolean isEnd(C_1596_ world) {
      return world.m_46472_() == C_1596_.f_46430_;
   }
}
