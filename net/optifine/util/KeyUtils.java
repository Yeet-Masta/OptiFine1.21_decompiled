package net.optifine.util;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.KeyMapping;
import net.optifine.reflect.Reflector;

public class KeyUtils {
   public static void fixKeyConflicts(KeyMapping[] keys, KeyMapping[] keysPrio) {
      Set<String> keyPrioNames = new HashSet();

      for (int i = 0; i < keysPrio.length; i++) {
         KeyMapping keyPrio = keysPrio[i];
         keyPrioNames.add(getId(keyPrio));
      }

      Set<KeyMapping> setKeys = new HashSet(Arrays.asList(keys));
      setKeys.removeAll(Arrays.asList(keysPrio));

      for (KeyMapping key : setKeys) {
         String name = getId(key);
         if (keyPrioNames.contains(name)) {
            key.m_90848_(InputConstants.f_84822_);
         }
      }
   }

   public static String getId(KeyMapping keyMapping) {
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
