package net.optifine.util;

import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_313555_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_5265_;
import net.optifine.reflect.Reflector;

public class ItemUtils {
   private static C_4917_ EMPTY_TAG = new C_4917_();

   public static C_1381_ getItem(C_5265_ loc) {
      return !C_256712_.f_257033_.d(loc) ? null : (C_1381_)C_256712_.f_257033_.m_7745_(loc);
   }

   public static int getId(C_1381_ item) {
      return C_256712_.f_257033_.a(item);
   }

   public static C_4917_ getTag(C_1391_ itemStack) {
      if (itemStack == null) {
         return EMPTY_TAG;
      } else {
         C_313555_ components = (C_313555_)Reflector.ItemStack_components.getValue(itemStack);
         return components == null ? EMPTY_TAG : components.getTag();
      }
   }
}
