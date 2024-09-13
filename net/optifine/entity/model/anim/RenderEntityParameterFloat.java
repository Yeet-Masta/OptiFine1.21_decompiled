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
   ID("id", true),
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
   private static RenderEntityParameterFloat[] VALUES = values();
   private static Minecraft f_303183_ = Minecraft.m_91087_();
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

   @Override
   public float eval() {
      switch (this) {
         case PLAYER_POS_X:
            return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)f_303183_.f_91074_.f_19854_, (float)f_303183_.f_91074_.m_20185_());
         case PLAYER_POS_Y:
            return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)f_303183_.f_91074_.f_19855_, (float)f_303183_.f_91074_.m_20186_());
         case PLAYER_POS_Z:
            return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)f_303183_.f_91074_.f_19856_, (float)f_303183_.f_91074_.m_20189_());
         case PLAYER_ROT_X:
            return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), f_303183_.f_91074_.f_19860_, f_303183_.f_91074_.m_146909_()));
         case PLAYER_ROT_Y:
            return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), f_303183_.f_91074_.f_19859_, f_303183_.f_91074_.m_146908_()));
         case FRAME_TIME:
            return this.getLastFrameTime();
         case FRAME_COUNTER:
            return (float)(Config.getWorldRenderer().getFrameCount() % 720720);
         case ANGER_TIME:
         case ANGER_TIME_START:
         case SWING_PROGRESS:
         default:
            BlockEntity blockEntity = BlockEntityRenderDispatcher.tileEntityRendered;
            if (blockEntity != null) {
               switch (this) {
                  case POS_X:
                     return (float)blockEntity.m_58899_().m_123341_();
                  case POS_Y:
                     return (float)blockEntity.m_58899_().m_123342_();
                  case POS_Z:
                     return (float)blockEntity.m_58899_().m_123343_();
                  case ROT_X:
                  case ROT_Y:
                  default:
                     break;
                  case ID:
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
                  if (render instanceof LivingEntityRenderer rlb) {
                     switch (this) {
                        case LIMB_SWING:
                           return rlb.renderLimbSwing;
                        case LIMB_SWING_SPEED:
                           return rlb.renderLimbSwingAmount;
                        case AGE:
                           return rlb.renderAgeInTicks;
                        case HEAD_YAW:
                           return rlb.renderHeadYaw;
                        case HEAD_PITCH:
                           return rlb.renderHeadPitch;
                        default:
                           if (entity instanceof LivingEntity livingEntity) {
                              switch (this) {
                                 case HEALTH:
                                    return livingEntity.m_21223_();
                                 case HURT_TIME:
                                    return livingEntity.f_20916_ > 0 ? (float)livingEntity.f_20916_ - GameRenderer.getRenderPartialTicks() : 0.0F;
                                 case DEATH_TIME:
                                    return livingEntity.f_20919_ > 0 ? (float)livingEntity.f_20919_ + GameRenderer.getRenderPartialTicks() : 0.0F;
                                 case IDLE_TIME:
                                    return livingEntity.m_21216_() > 0 ? (float)livingEntity.m_21216_() + GameRenderer.getRenderPartialTicks() : 0.0F;
                                 case MAX_HEALTH:
                                    return livingEntity.m_21233_();
                                 case MOVE_FORWARD:
                                    return livingEntity.f_20902_;
                                 case MOVE_STRAFING:
                                    return livingEntity.f_20900_;
                                 case POS_X:
                                 case POS_Y:
                                 case POS_Z:
                                 case ROT_X:
                                 case ROT_Y:
                                 case ID:
                                 case PLAYER_POS_X:
                                 case PLAYER_POS_Y:
                                 case PLAYER_POS_Z:
                                 case PLAYER_ROT_X:
                                 case PLAYER_ROT_Y:
                                 case FRAME_TIME:
                                 case FRAME_COUNTER:
                                 default:
                                    break;
                                 case ANGER_TIME:
                                    if (livingEntity instanceof NeutralMob neutralMob) {
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
                                 case ANGER_TIME_START:
                                    return EntityVariableFloat.getEntityValue(KEY_ANGER_TIME_MAX);
                                 case SWING_PROGRESS:
                                    return livingEntity.m_21324_(GameRenderer.getRenderPartialTicks());
                              }
                           }
                     }
                  }

                  if (entity instanceof Boat boat) {
                     switch (this) {
                        case LIMB_SWING:
                           float left = boat.m_38315_(0, GameRenderer.getRenderPartialTicks());
                           float right = boat.m_38315_(1, GameRenderer.getRenderPartialTicks());
                           return Math.max(left, right);
                        case LIMB_SWING_SPEED:
                           return 1.0F;
                     }
                  }

                  if (entity instanceof AbstractMinecart minecart) {
                     switch (this) {
                        case LIMB_SWING:
                           float posX = Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)minecart.f_19790_, (float)minecart.m_20185_());
                           float posZ = Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)minecart.f_19792_, (float)minecart.m_20189_());
                           BlockState bs = Minecraft.m_91087_().f_91073_.m_8055_(minecart.m_20183_());
                           if (bs.m_204336_(BlockTags.f_13034_)) {
                              RailShape rs = (RailShape)bs.m_61143_(((BaseRailBlock)bs.m_60734_()).m_7978_());
                              switch (<unrepresentable>.$SwitchMap$net$minecraft$world$level$block$state$properties$RailShape[rs.ordinal()]) {
                                 case 1:
                                    return posX + posZ;
                                 case 2:
                                    return -(posX - posZ);
                                 case 3:
                                    return posX - posZ;
                                 default:
                                    return -(posX + posZ);
                              }
                           }
                        case LIMB_SWING_SPEED:
                           return 1.0F;
                     }
                  }

                  switch (this) {
                     case POS_X:
                        return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)entity.f_19854_, (float)entity.m_20185_());
                     case POS_Y:
                        return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)entity.f_19855_, (float)entity.m_20186_());
                     case POS_Z:
                        return Mth.m_14179_(GameRenderer.getRenderPartialTicks(), (float)entity.f_19856_, (float)entity.m_20189_());
                     case ROT_X:
                        return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), entity.f_19860_, entity.m_146909_()));
                     case ROT_Y:
                        if (entity instanceof LivingEntity) {
                           return MathUtils.toRad(
                              Mth.m_14179_(GameRenderer.getRenderPartialTicks(), ((LivingEntity)entity).f_20884_, ((LivingEntity)entity).f_20883_)
                           );
                        }

                        return MathUtils.toRad(Mth.m_14179_(GameRenderer.getRenderPartialTicks(), entity.f_19859_, entity.m_146908_()));
                     case ID:
                        return this.toFloat(entity.m_20148_());
                     default:
                        return 0.0F;
                  }
               }
            }
         case DIMENSION:
            return (float)WorldUtils.getDimensionId(f_303183_.f_91073_);
         case RULE_INDEX:
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
      return Float.intBitsToFloat(i0 ^ i1);
   }

   private float toFloat(BlockPos pos) {
      int hash = Config.getRandom(pos, 0);
      return Float.intBitsToFloat(hash);
   }

   public static RenderEntityParameterFloat m_82160_(String str) {
      if (str == null) {
         return null;
      } else {
         for (int i = 0; i < VALUES.length; i++) {
            RenderEntityParameterFloat type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }
}
