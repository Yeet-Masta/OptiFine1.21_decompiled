package net.optifine.entity.model.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.optifine.expr.IExpressionBool;
import net.optifine.reflect.Reflector;
import net.optifine.util.BlockUtils;

public enum RenderEntityParameterBool implements IExpressionBool {
   IS_ALIVE("is_alive"),
   IS_AGGRESSIVE("is_aggressive"),
   IS_BURNING("is_burning"),
   IS_CHILD("is_child"),
   IS_GLOWING("is_glowing"),
   IS_HURT("is_hurt"),
   IS_IN_HAND("is_in_hand", true),
   IS_IN_ITEM_FRAME("is_in_item_frame", true),
   IS_IN_GROUND("is_in_ground"),
   IS_IN_GUI("is_in_gui", true),
   IS_IN_LAVA("is_in_lava"),
   IS_IN_WATER("is_in_water", true),
   IS_INVISIBLE("is_invisible"),
   IS_ON_GROUND("is_on_ground"),
   IS_ON_HEAD("is_on_head", true),
   IS_ON_SHOULDER("is_on_shoulder"),
   IS_RIDDEN("is_ridden"),
   IS_RIDING("is_riding"),
   IS_SITTING("is_sitting"),
   IS_SNEAKING("is_sneaking"),
   IS_SPRINTING("is_sprinting"),
   IS_TAMED("is_tamed"),
   IS_WET("is_wet");

   private String name;
   private boolean blockEntity;
   private EntityRenderDispatcher renderManager;
   // $FF: renamed from: mc net.minecraft.client.Minecraft
   private Minecraft field_70;
   private static final RenderEntityParameterBool[] VALUES = values();

   private RenderEntityParameterBool(String name) {
      this(name, false);
   }

   private RenderEntityParameterBool(String name, boolean blockEntity) {
      this.name = name;
      this.blockEntity = blockEntity;
      this.renderManager = Minecraft.m_91087_().m_91290_();
      this.field_70 = Minecraft.m_91087_();
   }

   public String getName() {
      return this.name;
   }

   public boolean isBlockEntity() {
      return this.blockEntity;
   }

   public boolean eval() {
      switch (this.ordinal()) {
         case 6:
            return ItemInHandRenderer.isRenderItemHand() && !LivingEntityRenderer.isRenderItemHead();
         case 7:
            return ItemFrameRenderer.isRenderItemFrame();
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         default:
            BlockEntity blockEntity = BlockEntityRenderDispatcher.tileEntityRendered;
            if (blockEntity != null) {
               switch (this.ordinal()) {
                  case 11:
                     return BlockUtils.isPropertyTrue(blockEntity.m_58900_(), BlockStateProperties.f_61362_);
               }
            }

            Entity entity = this.renderManager.getRenderedEntity();
            if (entity == null) {
               return false;
            } else {
               if (entity instanceof LivingEntity) {
                  LivingEntity livingEntity = (LivingEntity)entity;
                  switch (this.ordinal()) {
                     case 3:
                        return livingEntity.m_6162_();
                     case 5:
                        return livingEntity.f_20916_ > 0;
                     case 15:
                        return entity == this.field_70.f_91074_.entityShoulderLeft || entity == this.field_70.f_91074_.entityShoulderRight;
                  }
               }

               if (entity instanceof Mob) {
                  Mob mob = (Mob)entity;
                  switch (this.ordinal()) {
                     case 1:
                        return mob.m_5912_();
                  }
               }

               if (entity instanceof TamableAnimal) {
                  TamableAnimal tamable = (TamableAnimal)entity;
                  switch (this.ordinal()) {
                     case 18:
                        return tamable.m_21825_();
                     case 21:
                        return tamable.m_21824_();
                  }
               }

               if (entity instanceof Fox) {
                  Fox fox = (Fox)entity;
                  switch (this.ordinal()) {
                     case 18:
                        return fox.m_28555_();
                  }
               }

               if (entity instanceof AbstractArrow) {
                  AbstractArrow arrowEntity = (AbstractArrow)entity;
                  switch (this.ordinal()) {
                     case 8:
                        if (arrowEntity.f_19797_ == 0 && arrowEntity.f_19854_ == 0.0 && arrowEntity.f_19855_ == 0.0 && arrowEntity.f_19856_ == 0.0) {
                           return true;
                        }

                        return Reflector.getFieldValueBoolean(arrowEntity, Reflector.AbstractArrow_inGround, false);
                  }
               }

               switch (this.ordinal()) {
                  case 0:
                     return entity.m_6084_();
                  case 1:
                  case 3:
                  case 5:
                  case 6:
                  case 7:
                  case 8:
                  case 9:
                  case 14:
                  case 15:
                  case 18:
                  case 21:
                  default:
                     return false;
                  case 2:
                     return entity.m_6060_();
                  case 4:
                     return entity.m_142038_();
                  case 10:
                     return entity.m_20077_();
                  case 11:
                     return entity.m_20069_();
                  case 12:
                     return entity.m_20145_();
                  case 13:
                     return entity.m_20096_();
                  case 16:
                     return entity.m_20160_();
                  case 17:
                     return entity.m_20159_();
                  case 19:
                     return entity.m_6047_();
                  case 20:
                     return entity.m_20142_();
                  case 22:
                     return entity.m_20070_();
               }
            }
         case 9:
            return ItemRenderer.isRenderItemGui();
         case 14:
            return LivingEntityRenderer.isRenderItemHead();
      }
   }

   public static RenderEntityParameterBool parse(String str) {
      if (str == null) {
         return null;
      } else {
         for(int i = 0; i < VALUES.length; ++i) {
            RenderEntityParameterBool type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }

   // $FF: synthetic method
   private static RenderEntityParameterBool[] $values() {
      return new RenderEntityParameterBool[]{IS_ALIVE, IS_AGGRESSIVE, IS_BURNING, IS_CHILD, IS_GLOWING, IS_HURT, IS_IN_HAND, IS_IN_ITEM_FRAME, IS_IN_GROUND, IS_IN_GUI, IS_IN_LAVA, IS_IN_WATER, IS_INVISIBLE, IS_ON_GROUND, IS_ON_HEAD, IS_ON_SHOULDER, IS_RIDDEN, IS_RIDING, IS_SITTING, IS_SNEAKING, IS_SPRINTING, IS_TAMED, IS_WET};
   }
}
