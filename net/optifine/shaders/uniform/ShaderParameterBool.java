package net.optifine.shaders.uniform;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
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
   private static final ShaderParameterBool[] VALUES = values();

   private ShaderParameterBool(String name) {
      this.name = name;
      this.renderManager = Minecraft.m_91087_().m_91290_();
   }

   public String getName() {
      return this.name;
   }

   public boolean eval() {
      Entity entityGeneral = Minecraft.m_91087_().m_91288_();
      if (entityGeneral instanceof LivingEntity) {
         LivingEntity entity = (LivingEntity)entityGeneral;
         switch (this.ordinal()) {
            case 0:
               return entity.m_6084_();
            case 1:
               return entity.m_6060_();
            case 2:
               return entity.m_6162_();
            case 3:
               return entity.m_142038_();
            case 4:
               return entity.f_20916_ > 0;
            case 5:
               return entity.m_20077_();
            case 6:
               return entity.m_20069_();
            case 7:
               return entity.m_20145_();
            case 8:
               return entity.m_20096_();
            case 9:
               return entity.m_20160_();
            case 10:
               return entity.m_20159_();
            case 11:
               return entity.m_6047_();
            case 12:
               return entity.m_20142_();
            case 13:
               return entity.m_20070_();
         }
      }

      return false;
   }

   public static ShaderParameterBool parse(String str) {
      if (str == null) {
         return null;
      } else {
         for(int i = 0; i < VALUES.length; ++i) {
            ShaderParameterBool type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }

   // $FF: synthetic method
   private static ShaderParameterBool[] $values() {
      return new ShaderParameterBool[]{IS_ALIVE, IS_BURNING, IS_CHILD, IS_GLOWING, IS_HURT, IS_IN_LAVA, IS_IN_WATER, IS_INVISIBLE, IS_ON_GROUND, IS_RIDDEN, IS_RIDING, IS_SNEAKING, IS_SPRINTING, IS_WET};
   }
}
