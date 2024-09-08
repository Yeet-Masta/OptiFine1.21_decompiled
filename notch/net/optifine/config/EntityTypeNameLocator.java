package net.optifine.config;

import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.util.EntityTypeUtils;

public class EntityTypeNameLocator implements IObjectLocator<String> {
   public String getObject(C_5265_ loc) {
      C_513_ type = EntityTypeUtils.getEntityType(loc);
      return type == null ? null : type.m_20675_();
   }

   public static String getEntityTypeName(C_507_ entity) {
      return entity.m_6095_().m_20675_();
   }
}
