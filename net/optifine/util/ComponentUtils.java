package net.optifine.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public class ComponentUtils {
   public static String getTranslationKey(Component comp) {
      if (comp == null) {
         return null;
      } else {
         ComponentContents cont = comp.m_214077_();
         if (!(cont instanceof TranslatableContents)) {
            return null;
         } else {
            TranslatableContents tran = (TranslatableContents)cont;
            return tran.m_237508_();
         }
      }
   }
}
