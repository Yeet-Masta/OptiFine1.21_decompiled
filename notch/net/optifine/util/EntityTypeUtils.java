package net.optifine.util;

import net.minecraft.src.C_256712_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;

public class EntityTypeUtils {
   public static C_513_ getEntityType(C_5265_ loc) {
      return !C_256712_.f_256780_.d(loc) ? null : (C_513_)C_256712_.f_256780_.m_7745_(loc);
   }
}
