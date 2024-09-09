package net.optifine.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.optifine.util.EntityTypeUtils;

public class EntityTypeNameLocator implements IObjectLocator {
   public String getObject(ResourceLocation loc) {
      EntityType type = EntityTypeUtils.getEntityType(loc);
      return type == null ? null : type.m_20675_();
   }

   public static String getEntityTypeName(Entity entity) {
      return entity.m_6095_().m_20675_();
   }
}
