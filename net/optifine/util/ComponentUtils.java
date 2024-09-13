package net.optifine.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ComponentUtils {
   public static String getTranslationKey(Component comp) {
      if (comp == null) {
         return null;
      } else {
         return !(comp.m_214077_() instanceof TranslatableContents tran) ? null : tran.m_237508_();
      }
   }
}
