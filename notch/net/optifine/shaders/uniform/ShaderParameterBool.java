package net.optifine.shaders.uniform;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_524_;
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
   private C_4330_ renderManager;
   private static final ShaderParameterBool[] VALUES = values();

   private ShaderParameterBool(String name) {
      this.name = name;
      this.renderManager = C_3391_.m_91087_().m_91290_();
   }

   public String getName() {
      return this.name;
   }

   @Override
   public boolean eval() {
      if (C_3391_.m_91087_().m_91288_() instanceof C_524_ entity) {
         switch (this) {
            case IS_ALIVE:
               return entity.m_6084_();
            case IS_BURNING:
               return entity.bR();
            case IS_CHILD:
               return entity.m_6162_();
            case IS_GLOWING:
               return entity.m_142038_();
            case IS_HURT:
               return entity.f_20916_ > 0;
            case IS_IN_LAVA:
               return entity.bt();
            case IS_IN_WATER:
               return entity.bf();
            case IS_INVISIBLE:
               return entity.ci();
            case IS_ON_GROUND:
               return entity.aF();
            case IS_RIDDEN:
               return entity.bT();
            case IS_RIDING:
               return entity.bS();
            case IS_SNEAKING:
               return entity.cb();
            case IS_SPRINTING:
               return entity.cc();
            case IS_WET:
               return entity.bg();
         }
      }

      return false;
   }

   public static ShaderParameterBool parse(String str) {
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
