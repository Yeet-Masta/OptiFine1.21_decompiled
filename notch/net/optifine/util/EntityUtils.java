package net.optifine.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class EntityUtils {
   private static final Map<C_513_, Integer> mapIdByType = new HashMap();
   private static final Map<String, Integer> mapIdByLocation = new HashMap();
   private static final Map<String, Integer> mapIdByName = new HashMap();

   public static int getEntityIdByClass(C_507_ entity) {
      return entity == null ? -1 : getEntityIdByType(entity.m_6095_());
   }

   public static int getEntityIdByType(C_513_ type) {
      Integer id = (Integer)mapIdByType.get(type);
      return id == null ? -1 : id;
   }

   public static int getEntityIdByLocation(String locStr) {
      Integer id = (Integer)mapIdByLocation.get(locStr);
      return id == null ? -1 : id;
   }

   public static int getEntityIdByName(String name) {
      name = name.toLowerCase(Locale.ROOT);
      Integer id = (Integer)mapIdByName.get(name);
      return id == null ? -1 : id;
   }

   static {
      for (C_513_ type : C_256712_.f_256780_) {
         int id = C_256712_.f_256780_.a(type);
         C_5265_ loc = C_256712_.f_256780_.m_7981_(type);
         String locStr = loc.toString();
         String name = loc.m_135815_();
         if (mapIdByType.containsKey(type)) {
            Config.warn("Duplicate entity type: " + type + ", id1: " + mapIdByType.get(type) + ", id2: " + id);
         }

         if (mapIdByLocation.containsKey(locStr)) {
            Config.warn("Duplicate entity location: " + locStr + ", id1: " + mapIdByLocation.get(locStr) + ", id2: " + id);
         }

         if (mapIdByName.containsKey(locStr)) {
            Config.warn("Duplicate entity name: " + name + ", id1: " + mapIdByName.get(name) + ", id2: " + id);
         }

         mapIdByType.put(type, id);
         mapIdByLocation.put(locStr, id);
         mapIdByName.put(name, id);
      }
   }
}
