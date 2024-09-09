package net.optifine.util;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.KeyMapping;
import net.optifine.reflect.Reflector;

public class KeyUtils {
   public static void fixKeyConflicts(KeyMapping[] keys, KeyMapping[] keysPrio) {
      Set keyPrioNames = new HashSet();

      for(int i = 0; i < keysPrio.length; ++i) {
         KeyMapping keyPrio = keysPrio[i];
         keyPrioNames.add(getId(keyPrio));
      }

      Set setKeys = new HashSet(Arrays.asList(keys));
      setKeys.removeAll(Arrays.asList(keysPrio));
      Iterator iterator = setKeys.iterator();

      while(iterator.hasNext()) {
         KeyMapping key = (KeyMapping)iterator.next();
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
            String var10000 = String.valueOf(keyModifier);
            return var10000 + "+" + keyMapping.m_90865_();
         }
      }

      return keyMapping.m_90865_();
   }
}
