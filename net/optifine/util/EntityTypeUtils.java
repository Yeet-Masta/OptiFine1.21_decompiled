package net.optifine.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public class EntityTypeUtils {
   public static EntityType getEntityType(net.minecraft.resources.ResourceLocation loc) {
      return !BuiltInRegistries.f_256780_.m_7804_(loc) ? null : (EntityType)BuiltInRegistries.f_256780_.m_7745_(loc);
   }
}
