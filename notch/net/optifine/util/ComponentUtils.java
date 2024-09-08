package net.optifine.util;

import net.minecraft.src.C_213523_;
import net.minecraft.src.C_4996_;

public class ComponentUtils {
   public static String getTranslationKey(C_4996_ comp) {
      if (comp == null) {
         return null;
      } else {
         return !(comp.m_214077_() instanceof C_213523_ tran) ? null : tran.m_237508_();
      }
   }
}
