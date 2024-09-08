package net.optifine.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.src.C_3140_;
import net.minecraft.src.C_3387_;
import net.optifine.reflect.Reflector;

public class KeyUtils {
   public static void fixKeyConflicts(C_3387_[] keys, C_3387_[] keysPrio) {
      Set<String> keyPrioNames = new HashSet();

      for (int i = 0; i < keysPrio.length; i++) {
         C_3387_ keyPrio = keysPrio[i];
         keyPrioNames.add(getId(keyPrio));
      }

      Set<C_3387_> setKeys = new HashSet(Arrays.asList(keys));
      setKeys.removeAll(Arrays.asList(keysPrio));

      for (C_3387_ key : setKeys) {
         String name = getId(key);
         if (keyPrioNames.contains(name)) {
            key.m_90848_(C_3140_.f_84822_);
         }
      }
   }

   public static String getId(C_3387_ keyMapping) {
      if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
         Object keyModifier = Reflector.call(keyMapping, Reflector.ForgeKeyBinding_getKeyModifier);
         Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
         if (keyModifier != keyModifierNone) {
            return keyModifier + "+" + keyMapping.m_90865_();
         }
      }

      return keyMapping.m_90865_();
   }
}
