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
   private Minecraft f_303183_;
   private static RenderEntityParameterBool[] VALUES = values();

   private RenderEntityParameterBool(String name) {
      this(name, false);
   }

   private RenderEntityParameterBool(String name, boolean blockEntity) {
      this.name = name;
      this.blockEntity = blockEntity;
      this.renderManager = Minecraft.m_91087_().m_91290_();
      this.f_303183_ = Minecraft.m_91087_();
   }

   public String getName() {
      return this.name;
   }

   public boolean isBlockEntity() {
      return this.blockEntity;
   }

   @Override
   public boolean eval() {
      switch (this) {
         case IS_IN_HAND:
            return ItemInHandRenderer.isRenderItemHand() && !LivingEntityRenderer.isRenderItemHead();
         case IS_IN_ITEM_FRAME:
            return ItemFrameRenderer.isRenderItemFrame();
         case IS_IN_GROUND:
         case IS_IN_LAVA:
         case IS_IN_WATER:
         case IS_INVISIBLE:
         case IS_ON_GROUND:
         default:
            BlockEntity blockEntity = BlockEntityRenderDispatcher.tileEntityRendered;
            if (blockEntity != null) {
               switch (this) {
                  case IS_IN_WATER:
                     return BlockUtils.isPropertyTrue(blockEntity.m_58900_(), BlockStateProperties.f_61362_);
               }
            }

            Entity entity = this.renderManager.getRenderedEntity();
            if (entity == null) {
               return false;
            } else {
               if (entity instanceof LivingEntity livingEntity) {
                  switch (this) {
                     case IS_CHILD:
                        return livingEntity.m_6162_();
                     case IS_HURT:
                        return livingEntity.f_20916_ > 0;
                     case IS_ON_SHOULDER:
                        return entity == this.f_303183_.f_91074_.entityShoulderLeft || entity == this.f_303183_.f_91074_.entityShoulderRight;
                  }
               }

               if (entity instanceof Mob mob) {
                  switch (this) {
                     case IS_AGGRESSIVE:
                        return mob.m_5912_();
                  }
               }

               if (entity instanceof TamableAnimal tamable) {
                  switch (this) {
                     case IS_SITTING:
                        return tamable.m_21825_();
                     case IS_TAMED:
                        return tamable.m_21824_();
                  }
               }

               if (entity instanceof Fox fox) {
                  switch (this) {
                     case IS_SITTING:
                        return fox.m_28555_();
                  }
               }

               if (entity instanceof AbstractArrow arrowEntity) {
                  switch (this) {
                     case IS_IN_GROUND:
                        if (arrowEntity.f_19797_ == 0 && arrowEntity.f_19854_ == 0.0 && arrowEntity.f_19855_ == 0.0 && arrowEntity.f_19856_ == 0.0) {
                           return true;
                        }

                        return Reflector.getFieldValueBoolean(arrowEntity, Reflector.AbstractArrow_inGround, false);
                  }
               }

               switch (this) {
                  case IS_ALIVE:
                     return entity.m_6084_();
                  case IS_AGGRESSIVE:
                  case IS_CHILD:
                  case IS_HURT:
                  case IS_IN_HAND:
                  case IS_IN_ITEM_FRAME:
                  case IS_IN_GROUND:
                  case IS_IN_GUI:
                  case IS_ON_HEAD:
                  case IS_ON_SHOULDER:
                  case IS_SITTING:
                  case IS_TAMED:
                  default:
                     return false;
                  case IS_BURNING:
                     return entity.m_6060_();
                  case IS_GLOWING:
                     return entity.m_142038_();
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
         case IS_IN_GUI:
            return ItemRenderer.isRenderItemGui();
         case IS_ON_HEAD:
            return LivingEntityRenderer.isRenderItemHead();
      }
   }

   public static RenderEntityParameterBool m_82160_(String str) {
      if (str == null) {
         return null;
      } else {
         for (int i = 0; i < VALUES.length; i++) {
            RenderEntityParameterBool type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }
}
