package net.optifine.entity.model.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;

public enum RendererVariableFloat implements IModelVariableFloat {
   SHADOW_SIZE("shadow_size"),
   SHADOW_OPACITY("shadow_opacity"),
   LEASH_OFFSET_X("leash_offset_x"),
   LEASH_OFFSET_Y("leash_offset_y"),
   LEASH_OFFSET_Z("leash_offset_z"),
   SHADOW_OFFSET_X("shadow_offset_x"),
   SHADOW_OFFSET_Z("shadow_offset_z");

   private String name;
   private EntityRenderDispatcher renderManager;
   private static final RendererVariableFloat[] VALUES = values();

   private RendererVariableFloat(String name) {
      this.name = name;
      this.renderManager = Minecraft.m_91087_().m_91290_();
   }

   public float eval() {
      return this.getValue();
   }

   public float getValue() {
      EntityRenderer renderer = this.renderManager.getEntityRenderer();
      if (renderer == null) {
         return 0.0F;
      } else {
         switch (this.ordinal()) {
            case 0:
               return renderer.f_114477_;
            case 1:
               return renderer.f_114478_;
            case 2:
            case 3:
            case 4:
            default:
               if (renderer instanceof MobRenderer) {
                  MobRenderer mobRenderer = (MobRenderer)renderer;
                  switch (this.ordinal()) {
                     case 2:
                        return mobRenderer.leashOffsetX;
                     case 3:
                        return mobRenderer.leashOffsetY;
                     case 4:
                        return mobRenderer.leashOffsetZ;
                  }
               }

               return 0.0F;
            case 5:
               return renderer.shadowOffsetX;
            case 6:
               return renderer.shadowOffsetZ;
         }
      }
   }

   public void setValue(float value) {
      EntityRenderer renderer = this.renderManager.getEntityRenderer();
      if (renderer != null) {
         switch (this.ordinal()) {
            case 0:
               renderer.f_114477_ = value;
               return;
            case 1:
               renderer.f_114478_ = value;
               return;
            case 2:
            case 3:
            case 4:
            default:
               if (renderer instanceof MobRenderer) {
                  MobRenderer mobRenderer = (MobRenderer)renderer;
                  switch (this.ordinal()) {
                     case 2:
                        mobRenderer.leashOffsetX = value;
                        return;
                     case 3:
                        mobRenderer.leashOffsetY = value;
                        return;
                     case 4:
                        mobRenderer.leashOffsetZ = value;
                        return;
                  }
               }

               return;
            case 5:
               renderer.shadowOffsetX = value;
               return;
            case 6:
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
         for(int i = 0; i < VALUES.length; ++i) {
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

   // $FF: synthetic method
   private static RendererVariableFloat[] $values() {
      return new RendererVariableFloat[]{SHADOW_SIZE, SHADOW_OPACITY, LEASH_OFFSET_X, LEASH_OFFSET_Y, LEASH_OFFSET_Z, SHADOW_OFFSET_X, SHADOW_OFFSET_Z};
   }
}
