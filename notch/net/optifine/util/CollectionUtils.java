package net.optifine.util;

import java.util.Set;

public class CollectionUtils {
   public static boolean noneMatch(Set s1, Set s2) {
      return !anyMatch(s1, s2);
   }

   public static boolean anyMatch(Set s1, Set s2) {
      if (!s1.isEmpty() && !s2.isEmpty()) {
         if (s2.size() < s1.size()) {
            Set temp = s1;
            s1 = s2;
            s2 = temp;
         }

         for (Object elem : s1) {
            if (s2.contains(elem)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
