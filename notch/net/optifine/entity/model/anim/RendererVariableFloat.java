package net.optifine.entity.model.anim;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4363_;

public enum RendererVariableFloat implements IModelVariableFloat {
   SHADOW_SIZE("shadow_size"),
   SHADOW_OPACITY("shadow_opacity"),
   LEASH_OFFSET_X("leash_offset_x"),
   LEASH_OFFSET_Y("leash_offset_y"),
   LEASH_OFFSET_Z("leash_offset_z"),
   SHADOW_OFFSET_X("shadow_offset_x"),
   SHADOW_OFFSET_Z("shadow_offset_z");

   private String name;
   private C_4330_ renderManager;
   private static final RendererVariableFloat[] VALUES = values();

   private RendererVariableFloat(String name) {
      this.name = name;
      this.renderManager = C_3391_.m_91087_().m_91290_();
   }

   @Override
   public float eval() {
      return this.getValue();
   }

   @Override
   public float getValue() {
      C_4331_ renderer = this.renderManager.getEntityRenderer();
      if (renderer == null) {
         return 0.0F;
      } else {
         switch (this) {
            case SHADOW_SIZE:
               return renderer.f_114477_;
            case SHADOW_OPACITY:
               return renderer.f_114478_;
            case LEASH_OFFSET_X:
            case LEASH_OFFSET_Y:
            case LEASH_OFFSET_Z:
            default:
               if (renderer instanceof C_4363_ mobRenderer) {
                  switch (this) {
                     case LEASH_OFFSET_X:
                        return mobRenderer.leashOffsetX;
                     case LEASH_OFFSET_Y:
                        return mobRenderer.leashOffsetY;
                     case LEASH_OFFSET_Z:
                        return mobRenderer.leashOffsetZ;
                  }
               }

               return 0.0F;
            case SHADOW_OFFSET_X:
               return renderer.shadowOffsetX;
            case SHADOW_OFFSET_Z:
               return renderer.shadowOffsetZ;
         }
      }
   }

   @Override
   public void setValue(float value) {
      C_4331_ renderer = this.renderManager.getEntityRenderer();
      if (renderer != null) {
         switch (this) {
            case SHADOW_SIZE:
               renderer.f_114477_ = value;
               return;
            case SHADOW_OPACITY:
               renderer.f_114478_ = value;
               return;
            case LEASH_OFFSET_X:
            case LEASH_OFFSET_Y:
            case LEASH_OFFSET_Z:
            default:
               if (renderer instanceof C_4363_ mobRenderer) {
                  switch (this) {
                     case LEASH_OFFSET_X:
                        mobRenderer.leashOffsetX = value;
                        return;
                     case LEASH_OFFSET_Y:
                        mobRenderer.leashOffsetY = value;
                        return;
                     case LEASH_OFFSET_Z:
                        mobRenderer.leashOffsetZ = value;
                        return;
                  }
               }

               return;
            case SHADOW_OFFSET_X:
               renderer.shadowOffsetX = value;
               return;
            case SHADOW_OFFSET_Z:
               renderer.shadowOffsetZ = value;
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public static RendererVariableFloat parse(String str) {
      if (str == null) {
         return null;
      } else {
         for (int i = 0; i < VALUES.length; i++) {
            RendererVariableFloat type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }

   public String toString() {
      return this.name;
   }
}
