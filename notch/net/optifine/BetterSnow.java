package net.optifine;

import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_1715_;
import net.minecraft.src.C_1716_;
import net.minecraft.src.C_1752_;
import net.minecraft.src.C_1772_;
import net.minecraft.src.C_1787_;
import net.minecraft.src.C_1788_;
import net.minecraft.src.C_1792_;
import net.minecraft.src.C_1793_;
import net.minecraft.src.C_1812_;
import net.minecraft.src.C_1823_;
import net.minecraft.src.C_1831_;
import net.minecraft.src.C_1840_;
import net.minecraft.src.C_1876_;
import net.minecraft.src.C_1889_;
import net.minecraft.src.C_1900_;
import net.minecraft.src.C_1920_;
import net.minecraft.src.C_1930_;
import net.minecraft.src.C_1937_;
import net.minecraft.src.C_1950_;
import net.minecraft.src.C_1953_;
import net.minecraft.src.C_1956_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2079_;
import net.minecraft.src.C_2093_;
import net.minecraft.src.C_2102_;
import net.minecraft.src.C_4528_;
import net.minecraft.src.C_4675_;

public class BetterSnow {
   private static C_4528_ modelSnowLayer = null;

   public static void update() {
      modelSnowLayer = Config.getMinecraft().m_91289_().m_110907_().m_110893_(C_1710_.f_50125_.m_49966_());
   }

   public static C_4528_ getModelSnowLayer() {
      return modelSnowLayer;
   }

   public static C_2064_ getStateSnowLayer() {
      return C_1710_.f_50125_.m_49966_();
   }

   public static boolean shouldRender(C_1557_ lightReader, C_2064_ blockState, C_4675_ blockPos) {
      if (!(lightReader instanceof C_1559_)) {
         return false;
      } else {
         return !checkBlock(lightReader, blockState, blockPos) ? false : hasSnowNeighbours(lightReader, blockPos);
      }
   }

   private static boolean hasSnowNeighbours(C_1559_ blockAccess, C_4675_ pos) {
      C_1706_ blockSnow = C_1710_.f_50125_;
      if (blockAccess.m_8055_(pos.m_122012_()).m_60734_() == blockSnow
         || blockAccess.m_8055_(pos.m_122019_()).m_60734_() == blockSnow
         || blockAccess.m_8055_(pos.m_122024_()).m_60734_() == blockSnow
         || blockAccess.m_8055_(pos.m_122029_()).m_60734_() == blockSnow) {
         C_2064_ bsDown = blockAccess.m_8055_(pos.m_7495_());
         if (bsDown.m_60804_(blockAccess, pos)) {
            return true;
         }

         C_1706_ blockDown = bsDown.m_60734_();
         if (blockDown instanceof C_1920_) {
            return bsDown.c(C_1920_.f_56842_) == C_2093_.TOP;
         }

         if (blockDown instanceof C_1900_) {
            return bsDown.c(C_1900_.f_56353_) == C_2102_.TOP;
         }
      }

      return false;
   }

   private static boolean checkBlock(C_1559_ blockAccess, C_2064_ blockState, C_4675_ blockPos) {
      if (blockState.m_60804_(blockAccess, blockPos)) {
         return false;
      } else {
         C_1706_ block = blockState.m_60734_();
         if (block == C_1710_.f_50127_) {
            return false;
         } else if (!(block instanceof C_1715_)
            || !(block instanceof C_1772_)
               && !(block instanceof C_1792_)
               && !(block instanceof C_1840_)
               && !(block instanceof C_1889_)
               && !(block instanceof C_1937_)) {
            if (block instanceof C_1787_
               || block instanceof C_1788_
               || block instanceof C_1793_
               || block instanceof C_1752_
               || block instanceof C_1930_
               || block instanceof C_1956_) {
               return true;
            } else if (block instanceof C_1876_) {
               return true;
            } else if (block instanceof C_1920_) {
               return blockState.c(C_1920_.f_56842_) == C_2093_.TOP;
            } else if (block instanceof C_1900_) {
               return blockState.c(C_1900_.f_56353_) == C_2102_.TOP;
            } else if (block instanceof C_1716_) {
               return blockState.c(C_1716_.K) != C_2079_.FLOOR;
            } else if (block instanceof C_1812_) {
               return true;
            } else if (block instanceof C_1823_) {
               return true;
            } else if (block instanceof C_1831_) {
               return true;
            } else {
               return block instanceof C_1950_ ? true : block instanceof C_1953_;
            }
         } else {
            return true;
         }
      }
   }
}
