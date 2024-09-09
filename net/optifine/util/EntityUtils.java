package net.optifine.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class EntityUtils {
   private static final Map<EntityType, Integer> mapIdByType = new HashMap();
   private static final Map<String, Integer> mapIdByLocation = new HashMap();
   private static final Map<String, Integer> mapIdByName = new HashMap();

   public static int getEntityIdByClass(Entity entity) {
      return entity == null ? -1 : getEntityIdByType(entity.m_6095_());
   }

   public static int getEntityIdByType(EntityType type) {
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
      for (EntityType type : BuiltInRegistries.f_256780_) {
         int id = BuiltInRegistries.f_256780_.m_7447_(type);
         net.minecraft.resources.ResourceLocation loc = BuiltInRegistries.f_256780_.m_7981_(type);
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
