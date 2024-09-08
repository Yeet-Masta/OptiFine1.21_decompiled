package net.optifine.entity.model.anim;

import java.util.UUID;
import net.minecraft.src.C_1201_;
import net.minecraft.src.C_1205_;
import net.minecraft.src.C_137_;
import net.minecraft.src.C_1695_;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2100_;
import net.minecraft.src.C_302051_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4124_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4357_;
import net.minecraft.src.C_4585_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_532_;
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
   private C_4330_ renderManager;
   private static final RenderEntityParameterFloat[] VALUES = values();
   private static C_3391_ mc = C_3391_.m_91087_();
   private static String KEY_ANGER_TIME_MAX = "ANGER_TIME_MAX";

   private RenderEntityParameterFloat(String name) {
      this(name, false);
   }

   private RenderEntityParameterFloat(String name, boolean blockEntity) {
      this.name = name;
      this.blockEntity = blockEntity;
      this.renderManager = C_3391_.m_91087_().m_91290_();
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
            return C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)mc.f_91074_.L, (float)mc.f_91074_.dt());
         case PLAYER_POS_Y:
            return C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)mc.f_91074_.M, (float)mc.f_91074_.dv());
         case PLAYER_POS_Z:
            return C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)mc.f_91074_.N, (float)mc.f_91074_.dz());
         case PLAYER_ROT_X:
            return MathUtils.toRad(C_188_.m_14179_(C_4124_.getRenderPartialTicks(), mc.f_91074_.P, mc.f_91074_.dG()));
         case PLAYER_ROT_Y:
            return MathUtils.toRad(C_188_.m_14179_(C_4124_.getRenderPartialTicks(), mc.f_91074_.O, mc.f_91074_.dE()));
         case FRAME_TIME:
            return this.getLastFrameTime();
         case FRAME_COUNTER:
            return (float)(Config.getWorldRenderer().getFrameCount() % 720720);
         case ANGER_TIME:
         case ANGER_TIME_START:
         case SWING_PROGRESS:
         default:
            C_1991_ blockEntity = C_4243_.tileEntityRendered;
            if (blockEntity != null) {
               switch (this) {
                  case POS_X:
                     return (float)blockEntity.m_58899_().u();
                  case POS_Y:
                     return (float)blockEntity.m_58899_().v();
                  case POS_Z:
                     return (float)blockEntity.m_58899_().w();
                  case ROT_X:
                  case ROT_Y:
                  default:
                     break;
                  case ID:
                     return this.toFloat(blockEntity.m_58899_());
               }
            }

            C_4331_ render = this.renderManager.getEntityRenderer();
            if (render == null) {
               return 0.0F;
            } else {
               C_507_ entity = this.renderManager.getRenderedEntity();
               if (entity == null) {
                  return 0.0F;
               } else {
                  if (render instanceof C_4357_ rlb) {
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
                           if (entity instanceof C_524_ livingEntity) {
                              switch (this) {
                                 case HEALTH:
                                    return livingEntity.m_21223_();
                                 case HURT_TIME:
                                    return livingEntity.f_20916_ > 0 ? (float)livingEntity.f_20916_ - C_4124_.getRenderPartialTicks() : 0.0F;
                                 case DEATH_TIME:
                                    return livingEntity.f_20919_ > 0 ? (float)livingEntity.f_20919_ + C_4124_.getRenderPartialTicks() : 0.0F;
                                 case IDLE_TIME:
                                    return livingEntity.m_21216_() > 0 ? (float)livingEntity.m_21216_() + C_4124_.getRenderPartialTicks() : 0.0F;
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
                                    if (livingEntity instanceof C_532_ neutralMob) {
                                       float val = (float)neutralMob.m_6784_();
                                       float valMax = EntityVariableFloat.getEntityValue(KEY_ANGER_TIME_MAX);
                                       if (val > 0.0F) {
                                          if (val > valMax) {
                                             EntityVariableFloat.setEntityValue(KEY_ANGER_TIME_MAX, val);
                                          }

                                          if (val < valMax) {
                                             val -= C_4124_.getRenderPartialTicks();
                                          }
                                       } else if (valMax > 0.0F) {
                                          EntityVariableFloat.setEntityValue(KEY_ANGER_TIME_MAX, 0.0F);
                                       }

                                       return val;
                                    }
                                 case ANGER_TIME_START:
                                    return EntityVariableFloat.getEntityValue(KEY_ANGER_TIME_MAX);
                                 case SWING_PROGRESS:
                                    return livingEntity.m_21324_(C_4124_.getRenderPartialTicks());
                              }
                           }
                     }
                  }

                  if (entity instanceof C_1205_ boat) {
                     switch (this) {
                        case LIMB_SWING:
                           float left = boat.m_38315_(0, C_4124_.getRenderPartialTicks());
                           float right = boat.m_38315_(1, C_4124_.getRenderPartialTicks());
                           return Math.max(left, right);
                        case LIMB_SWING_SPEED:
                           return 1.0F;
                     }
                  }

                  if (entity instanceof C_1201_ minecart) {
                     switch (this) {
                        case LIMB_SWING:
                           float posX = C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)minecart.ad, (float)minecart.dt());
                           float posZ = C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)minecart.af, (float)minecart.dz());
                           C_2064_ bs = C_3391_.m_91087_().f_91073_.a_(minecart.do());
                           if (bs.a(C_137_.f_13034_)) {
                              C_2100_ rs = (C_2100_)bs.c(((C_1695_)bs.b()).m_7978_());
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
                        case LIMB_SWING_SPEED:
                           return 1.0F;
                     }
                  }

                  switch (this) {
                     case POS_X:
                        return C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)entity.f_19854_, (float)entity.m_20185_());
                     case POS_Y:
                        return C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)entity.f_19855_, (float)entity.m_20186_());
                     case POS_Z:
                        return C_188_.m_14179_(C_4124_.getRenderPartialTicks(), (float)entity.f_19856_, (float)entity.m_20189_());
                     case ROT_X:
                        return MathUtils.toRad(C_188_.m_14179_(C_4124_.getRenderPartialTicks(), entity.f_19860_, entity.m_146909_()));
                     case ROT_Y:
                        if (entity instanceof C_524_) {
                           return MathUtils.toRad(C_188_.m_14179_(C_4124_.getRenderPartialTicks(), ((C_524_)entity).f_20884_, ((C_524_)entity).f_20883_));
                        }

                        return MathUtils.toRad(C_188_.m_14179_(C_4124_.getRenderPartialTicks(), entity.f_19859_, entity.m_146908_()));
                     case ID:
                        return this.toFloat(entity.m_20148_());
                     default:
                        return 0.0F;
                  }
               }
            }
         case DIMENSION:
            return (float)WorldUtils.getDimensionId(mc.f_91073_);
         case RULE_INDEX:
            return (float)CustomEntityModels.getMatchingRuleIndex();
      }
   }

   float getLastFrameTime() {
      float timeMul = 1.0F;
      C_3391_ mc = C_3391_.m_91087_();
      C_4585_ is = mc.m_91092_();
      if (is != null && mc.m_91090_()) {
         if (is.m_305863_()) {
            return 0.0F;
         }

         C_302051_ trm = is.aQ();
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

   private float toFloat(C_4675_ pos) {
      int hash = Config.getRandom(pos, 0);
      return Float.intBitsToFloat(hash);
   }

   public static RenderEntityParameterFloat parse(String str) {
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
