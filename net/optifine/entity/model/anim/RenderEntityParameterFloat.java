package net.optifine.entity.model.anim;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.expr.IExpressionFloat;
import net.optifine.util.MathUtils;
import net.optifine.util.WorldUtils;

public enum RenderEntityParameterFloat implements IExpressionFloat {
   LIMB_SWING("limb_swing"),
   LIMB_SWING_SPEED("limb_speed"),
   AGE("age"),
   HEAD_YAW("head_yaw"),
   HEAD_PITCH("head_pitch"),
   HEALTH("health"),
   HURT_TIME("hurt_time"),
   DEATH_TIME("death_time"),
   IDLE_TIME("idle_time"),
   MAX_HEALTH("max_health"),
   MOVE_FORWARD("move_forward"),
   MOVE_STRAFING("move_strafing"),
   POS_X("pos_x", true),
   POS_Y("pos_y", true),
   POS_Z("pos_z", true),
   ROT_X("rot_x"),
   ROT_Y("rot_y"),
   // $FF: renamed from: ID net.optifine.entity.model.anim.RenderEntityParameterFloat
   field_34("id", true),
   PLAYER_POS_X("player_pos_x", true),
   PLAYER_POS_Y("player_pos_y", true),
   PLAYER_POS_Z("player_pos_z", true),
   PLAYER_ROT_X("player_rot_x", true),
   PLAYER_ROT_Y("player_rot_y", true),
   FRAME_TIME("frame_time", true),
   FRAME_COUNTER("frame_counter", true),
   ANGER_TIME("anger_time"),
   ANGER_TIME_START("anger_time_start"),
   SWING_PROGRESS("swing_progress"),
   DIMENSION("dimension", true),
   RULE_INDEX("rule_index", true);

   private String name;
   private boolean blockEntity;
   private EntityRenderDispatcher renderManager;
   private static final RenderEntityParameterFloat[] VALUES = values();
   // $FF: renamed from: mc net.minecraft.client.Minecraft
   private static Minecraft field_35 = Minecraft.m_91087_();
   private static String KEY_ANGER_TIME_MAX = "ANGER_TIME_MAX";

   private RenderEntityParameterFloat(String name) {
      this(name, false);
   }

   private RenderEntityParameterFloat(String name, boolean blockEntity) {
      this.name = name;
      this.blockEntity = blockEntity;
      this.renderManager = Minecraft.m_91087_().m_91290_();
   }

   public String getName() {
      return this.name;
   }

   public boolean isBlockEntity() {
      return this.blockEntity;
   }

   public float eval() {
      switch (this.ordinal()) {
         case 18:
            return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)field_35.f_91074_.f_19854_, (float)field_35.f_91074_.m_20185_());
         case 19:
            return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)field_35.f_91074_.f_19855_, (float)field_35.f_91074_.m_20186_());
         case 20:
            return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)field_35.f_91074_.f_19856_, (float)field_35.f_91074_.m_20189_());
         case 21:
            return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), field_35.f_91074_.f_19860_, field_35.f_91074_.m_146909_()));
         case 22:
            return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), field_35.f_91074_.f_19859_, field_35.f_91074_.m_146908_()));
         case 23:
            return this.getLastFrameTime();
         case 24:
            return (float)(Config.getWorldRenderer().getFrameCount() % 720720);
         case 25:
         case 26:
         case 27:
         default:
            BlockEntity blockEntity = BlockEntityRenderDispatcher.tileEntityRendered;
            if (blockEntity != null) {
               switch (this.ordinal()) {
                  case 12:
                     return (float)blockEntity.m_58899_().m_123341_();
                  case 13:
                     return (float)blockEntity.m_58899_().m_123342_();
                  case 14:
                     return (float)blockEntity.m_58899_().m_123343_();
                  case 15:
                  case 16:
                  default:
                     break;
                  case 17:
                     return this.toFloat(blockEntity.m_58899_());
               }
            }

            EntityRenderer render = this.renderManager.getEntityRenderer();
            if (render == null) {
               return 0.0F;
            } else {
               Entity entity = this.renderManager.getRenderedEntity();
               if (entity == null) {
                  return 0.0F;
               } else {
                  if (render instanceof LivingEntityRenderer) {
                     LivingEntityRenderer rlb = (LivingEntityRenderer)render;
                     switch (this.ordinal()) {
                        case 0:
                           return rlb.renderLimbSwing;
                        case 1:
                           return rlb.renderLimbSwingAmount;
                        case 2:
                           return rlb.renderAgeInTicks;
                        case 3:
                           return rlb.renderHeadYaw;
                        case 4:
                           return rlb.renderHeadPitch;
                        default:
                           if (entity instanceof LivingEntity) {
                              LivingEntity livingEntity = (LivingEntity)entity;
                              switch (this.ordinal()) {
                                 case 5:
                                    return livingEntity.m_21223_();
                                 case 6:
                                    return livingEntity.f_20916_ > 0 ? (float)livingEntity.f_20916_ - GameRenderer.getRenderPartialTicks() : 0.0F;
                                 case 7:
                                    return livingEntity.f_20919_ > 0 ? (float)livingEntity.f_20919_ + GameRenderer.getRenderPartialTicks() : 0.0F;
                                 case 8:
                                    return livingEntity.m_21216_() > 0 ? (float)livingEntity.m_21216_() + GameRenderer.getRenderPartialTicks() : 0.0F;
                                 case 9:
                                    return livingEntity.m_21233_();
                                 case 10:
                                    return livingEntity.f_20902_;
                                 case 11:
                                    return livingEntity.f_20900_;
                                 case 12:
                                 case 13:
                                 case 14:
                                 case 15:
                                 case 16:
                                 case 17:
                                 case 18:
                                 case 19:
                                 case 20:
                                 case 21:
                                 case 22:
                                 case 23:
                                 case 24:
                                 default:
                                    break;
                                 case 25:
                                    if (livingEntity instanceof NeutralMob) {
                                       NeutralMob neutralMob = (NeutralMob)livingEntity;
                                       float val = (float)neutralMob.m_6784_();
                                       float valMax = EntityVariableFloat.getEntityValue(KEY_ANGER_TIME_MAX);
                                       if (val > 0.0F) {
                                          if (val > valMax) {
                                             EntityVariableFloat.setEntityValue(KEY_ANGER_TIME_MAX, val);
                                          }

                                          if (val < valMax) {
                                             val -= GameRenderer.getRenderPartialTicks();
                                          }
                                       } else if (valMax > 0.0F) {
                                          EntityVariableFloat.setEntityValue(KEY_ANGER_TIME_MAX, 0.0F);
                                       }

                                       return val;
                                    }
                                 case 26:
                                    return EntityVariableFloat.getEntityValue(KEY_ANGER_TIME_MAX);
                                 case 27:
                                    return livingEntity.m_21324_(GameRenderer.getRenderPartialTicks());
                              }
                           }
                     }
                  }

                  float posX;
                  float posZ;
                  if (entity instanceof Boat) {
                     Boat boat = (Boat)entity;
                     switch (this.ordinal()) {
                        case 0:
                           posX = boat.m_38315_(0, GameRenderer.getRenderPartialTicks());
                           posZ = boat.m_38315_(1, GameRenderer.getRenderPartialTicks());
                           return Math.max(posX, posZ);
                        case 1:
                           return 1.0F;
                     }
                  }

                  if (entity instanceof AbstractMinecart) {
                     AbstractMinecart minecart = (AbstractMinecart)entity;
                     switch (this.ordinal()) {
                        case 0:
                           posX = Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)minecart.f_19790_, (float)minecart.m_20185_());
                           posZ = Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)minecart.f_19792_, (float)minecart.m_20189_());
                           BlockState bs = Minecraft.m_91087_().f_91073_.m_8055_(minecart.m_20183_());
                           if (bs.m_204336_(BlockTags.f_13034_)) {
                              RailShape rs = (RailShape)bs.m_61143_(((BaseRailBlock)bs.m_60734_()).m_7978_());
                              switch (rs) {
                                 case SOUTH_WEST:
                                    return posX + posZ;
                                 case SOUTH_EAST:
                                    return -(posX - posZ);
                                 case NORTH_WEST:
                                    return posX - posZ;
                                 default:
                                    return -(posX + posZ);
                              }
                           }
                        case 1:
                           return 1.0F;
                     }
                  }

                  switch (this.ordinal()) {
                     case 12:
                        return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)entity.f_19854_, (float)entity.m_20185_());
                     case 13:
                        return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)entity.f_19855_, (float)entity.m_20186_());
                     case 14:
                        return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)entity.f_19856_, (float)entity.m_20189_());
                     case 15:
                        return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), entity.f_19860_, entity.m_146909_()));
                     case 16:
                        if (entity instanceof LivingEntity) {
                           return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), ((LivingEntity)entity).f_20884_, ((LivingEntity)entity).f_20883_));
                        }

                        return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), entity.f_19859_, entity.m_146908_()));
                     case 17:
                        return this.toFloat(entity.m_20148_());
                     default:
                        return 0.0F;
                  }
               }
            }
         case 28:
            return (float)WorldUtils.getDimensionId((Level)field_35.f_91073_);
         case 29:
            return (float)CustomEntityModels.getMatchingRuleIndex();
      }
   }

   float getLastFrameTime() {
      float timeMul = 1.0F;
      Minecraft mc = Minecraft.m_91087_();
      IntegratedServer is = mc.m_91092_();
      if (is != null && mc.m_91090_()) {
         if (is.m_305863_()) {
            return 0.0F;
         }

         TickRateManager trm = is.m_306290_();
         if (trm.m_306363_()) {
            return 0.0F;
         }

         float tickRate = trm.m_306179_();
         if (tickRate > 0.0F && tickRate < 20.0F) {
            timeMul = tickRate / 20.0F;
         }
      }

      return timeMul * (float)Config.getLastFrameTimeMs() / 1000.0F;
   }

   private float toFloat(UUID uuid) {
      int i0 = Long.hashCode(uuid.getLeastSignificantBits());
      int i1 = Long.hashCode(uuid.getMostSignificantBits());
      float id = Float.intBitsToFloat(i0 ^ i1);
      return id;
   }

   private float toFloat(BlockPos pos) {
      int hash = Config.getRandom(pos, 0);
      float id = Float.intBitsToFloat(hash);
      return id;
   }

   public static RenderEntityParameterFloat parse(String str) {
      if (str == null) {
         return null;
      } else {
         for(int i = 0; i < VALUES.length; ++i) {
            RenderEntityParameterFloat type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }

   // $FF: synthetic method
   private static RenderEntityParameterFloat[] $values() {
      return new RenderEntityParameterFloat[]{LIMB_SWING, LIMB_SWING_SPEED, AGE, HEAD_YAW, HEAD_PITCH, HEALTH, HURT_TIME, DEATH_TIME, IDLE_TIME, MAX_HEALTH, MOVE_FORWARD, MOVE_STRAFING, POS_X, POS_Y, POS_Z, ROT_X, ROT_Y, field_34, PLAYER_POS_X, PLAYER_POS_Y, PLAYER_POS_Z, PLAYER_ROT_X, PLAYER_ROT_Y, FRAME_TIME, FRAME_COUNTER, ANGER_TIME, ANGER_TIME_START, SWING_PROGRESS, DIMENSION, RULE_INDEX};
   }
}
