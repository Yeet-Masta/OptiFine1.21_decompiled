package net.optifine.override;

import net.minecraft.src.C_1141_;
import net.minecraft.src.C_3041_;
import net.minecraft.src.C_3042_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3902_;
import net.minecraft.src.C_3905_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_470_;
import net.minecraft.src.C_471_;
import net.minecraft.src.C_507_;

public class PlayerControllerOF extends C_3905_ {
   private boolean acting = false;
   private C_4675_ lastClickBlockPos = null;
   private C_507_ lastClickEntity = null;

   public PlayerControllerOF(C_3391_ mcIn, C_3902_ netHandler) {
      super(mcIn, netHandler);
   }

   public boolean a(C_4675_ loc, C_4687_ face) {
      this.acting = true;
      this.lastClickBlockPos = loc;
      boolean res = super.m_105269_(loc, face);
      this.acting = false;
      return res;
   }

   public boolean b(C_4675_ posBlock, C_4687_ directionFacing) {
      this.acting = true;
      this.lastClickBlockPos = posBlock;
      boolean res = super.m_105283_(posBlock, directionFacing);
      this.acting = false;
      return res;
   }

   public C_471_ a(C_1141_ player, C_470_ hand) {
      this.acting = true;
      C_471_ res = super.m_233721_(player, hand);
      this.acting = false;
      return res;
   }

   public C_471_ a(C_4105_ player, C_470_ hand, C_3041_ rayTrace) {
      this.acting = true;
      this.lastClickBlockPos = rayTrace.m_82425_();
      C_471_ res = super.m_233732_(player, hand, rayTrace);
      this.acting = false;
      return res;
   }

   public C_471_ a(C_1141_ player, C_507_ target, C_470_ hand) {
      this.lastClickEntity = target;
      return super.m_105226_(player, target, hand);
   }

   public C_471_ a(C_1141_ player, C_507_ target, C_3042_ ray, C_470_ hand) {
      this.lastClickEntity = target;
      return super.m_105230_(player, target, ray, hand);
   }

   public boolean isActing() {
      return this.acting;
   }

   public C_4675_ getLastClickBlockPos() {
      return this.lastClickBlockPos;
   }

   public C_507_ getLastClickEntity() {
      return this.lastClickEntity;
   }
}
