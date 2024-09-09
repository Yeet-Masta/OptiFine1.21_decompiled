package net.minecraft.world;

import java.util.List;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class TickRateManager {
   public static final float f_302470_ = 1.0F;
   protected float f_302740_ = 20.0F;
   protected long f_303856_ = TimeUtil.f_145016_ / 20L;
   protected int f_303482_ = 0;
   protected boolean f_302370_ = true;
   protected boolean f_303812_ = false;

   public void m_307254_(float rateIn) {
      this.f_302740_ = Math.max(rateIn, 1.0F);
      this.f_303856_ = (long)((double)TimeUtil.f_145016_ / (double)this.f_302740_);
   }

   public float m_306179_() {
      return this.f_302740_;
   }

   public float m_305111_() {
      return (float)this.f_303856_ / (float)TimeUtil.f_145017_;
   }

   public long m_307289_() {
      return this.f_303856_;
   }

   public boolean m_305915_() {
      return this.f_302370_;
   }

   public boolean m_307006_() {
      return this.f_303482_ > 0;
   }

   public void m_307652_(int ticksIn) {
      this.f_303482_ = ticksIn;
   }

   public int m_306668_() {
      return this.f_303482_;
   }

   public void m_306419_(boolean frozenIn) {
      this.f_303812_ = frozenIn;
   }

   public boolean m_306363_() {
      return this.f_303812_;
   }

   public void m_306707_() {
      this.f_302370_ = !this.f_303812_ || this.f_303482_ > 0;
      if (this.f_303482_ > 0) {
         this.f_303482_--;
      }
   }

   public boolean m_305579_(Entity entityIn) {
      return !this.m_305915_() && !(entityIn instanceof Player) && !this.hasPlayerPassengers(entityIn);
   }

   private boolean hasPlayerPassengers(Entity entity) {
      List<Entity> passengers = entity.m_20197_();

      for (int i = 0; i < passengers.size(); i++) {
         Entity passenger = (Entity)passengers.get(i);
         if (passenger instanceof Player) {
            return true;
         }

         if (this.hasPlayerPassengers(passenger)) {
            return true;
         }
      }

      return false;
   }
}
