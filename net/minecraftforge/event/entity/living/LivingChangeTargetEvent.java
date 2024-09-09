package net.minecraftforge.event.entity.living;

import net.minecraft.src.C_524_;

public class LivingChangeTargetEvent extends LivingEvent {
   public C_524_ getNewTarget() {
      return null;
   }

   public static enum LivingTargetType implements ILivingTargetType {
      MOB_TARGET,
      BEHAVIOR_TARGET;

      // $FF: synthetic method
      private static LivingTargetType[] $values() {
         return new LivingTargetType[]{MOB_TARGET, BEHAVIOR_TARGET};
      }
   }
}
