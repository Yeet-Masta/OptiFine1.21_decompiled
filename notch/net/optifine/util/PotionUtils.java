package net.optifine.util;

import java.util.Optional;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1440_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_313529_;
import net.minecraft.src.C_313616_;
import net.minecraft.src.C_496_;
import net.minecraft.src.C_5265_;
import net.optifine.reflect.Reflector;

public class PotionUtils {
   public static C_496_ getPotion(C_5265_ loc) {
      return !C_256712_.f_256974_.m_7804_(loc) ? null : (C_496_)C_256712_.f_256974_.m_7745_(loc);
   }

   public static C_496_ getPotion(int potionID) {
      return (C_496_)C_256712_.f_256974_.a(potionID);
   }

   public static int getId(C_496_ potionIn) {
      return C_256712_.f_256974_.m_7447_(potionIn);
   }

   public static String getPotionBaseName(C_1440_ p) {
      return p == null ? null : (String)Reflector.Potion_baseName.getValue(p);
   }

   public static C_1440_ getPotion(C_1391_ itemStack) {
      C_313529_ pc = (C_313529_)itemStack.a(C_313616_.f_314188_);
      if (pc == null) {
         return null;
      } else {
         Optional<C_203228_<C_1440_>> opt = pc.f_317059_();
         if (opt.isEmpty()) {
            return null;
         } else {
            C_203228_<C_1440_> holder = (C_203228_<C_1440_>)opt.get();
            return !holder.m_203633_() ? null : (C_1440_)holder.m_203334_();
         }
      }
   }
}
