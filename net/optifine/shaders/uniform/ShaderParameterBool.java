package net.optifine.shaders.uniform;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.LivingEntity;
import net.optifine.expr.IExpressionBool;

public enum ShaderParameterBool implements IExpressionBool {
   IS_ALIVE("is_alive"),
   IS_BURNING("is_burning"),
   IS_CHILD("is_child"),
   IS_GLOWING("is_glowing"),
   IS_HURT("is_hurt"),
   IS_IN_LAVA("is_in_lava"),
   IS_IN_WATER("is_in_water"),
   IS_INVISIBLE("is_invisible"),
   IS_ON_GROUND("is_on_ground"),
   IS_RIDDEN("is_ridden"),
   IS_RIDING("is_riding"),
   IS_SNEAKING("is_sneaking"),
   IS_SPRINTING("is_sprinting"),
   IS_WET("is_wet");

   private String name;
   private EntityRenderDispatcher renderManager;
   private static ShaderParameterBool[] VALUES = values();

   private ShaderParameterBool(String name) {
      this.name = name;
      this.renderManager = Minecraft.m_91087_().m_91290_();
   }

   public String getName() {
      return this.name;
   }

   @Override
   public boolean eval() {
      if (Minecraft.m_91087_().m_91288_() instanceof LivingEntity entity) {
         switch (this) {
            case IS_ALIVE:
               return entity.m_6084_();
            case IS_BURNING:
               return entity.m_6060_();
            case IS_CHILD:
               return entity.m_6162_();
            case IS_GLOWING:
               return entity.m_142038_();
            case IS_HURT:
               return entity.f_20916_ > 0;
            case IS_IN_LAVA:
               return entity.m_20077_();
            case IS_IN_WATER:
               return entity.m_20069_();
            case IS_INVISIBLE:
               return entity.m_20145_();
            case IS_ON_GROUND:
               return entity.m_20096_();
            case IS_RIDDEN:
               return entity.m_20160_();
            case IS_RIDING:
               return entity.m_20159_();
            case IS_SNEAKING:
               return entity.m_6047_();
            case IS_SPRINTING:
               return entity.m_20142_();
            case IS_WET:
               return entity.m_20070_();
         }
      }

      return false;
   }

   public static ShaderParameterBool m_82160_(String str) {
      if (str == null) {
         return null;
      } else {
         for (int i = 0; i < VALUES.length; i++) {
            ShaderParameterBool type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }
}
