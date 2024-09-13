package net.optifine;

import net.minecraft.world.phys.Vec3;

public class CustomColorFader {
   private Vec3 color = null;
   private long timeUpdate = System.currentTimeMillis();

   public Vec3 m_130045_(double x, double y, double z) {
      if (this.color == null) {
         this.color = new Vec3(x, y, z);
         return this.color;
      } else {
         long timeNow = System.currentTimeMillis();
         long timeDiff = timeNow - this.timeUpdate;
         if (timeDiff == 0L) {
            return this.color;
         } else {
            this.timeUpdate = timeNow;
            if (Math.abs(x - this.color.f_82479_) < 0.004 && Math.abs(y - this.color.f_82480_) < 0.004 && Math.abs(z - this.color.f_82481_) < 0.004) {
               return this.color;
            } else {
               double k = (double)timeDiff * 0.001;
               k = Config.limit(k, 0.0, 1.0);
               double dx = x - this.color.f_82479_;
               double dy = y - this.color.f_82480_;
               double dz = z - this.color.f_82481_;
               double xn = this.color.f_82479_ + dx * k;
               double yn = this.color.f_82480_ + dy * k;
               double zn = this.color.f_82481_ + dz * k;
               this.color = new Vec3(xn, yn, zn);
               return this.color;
            }
         }
      }
   }
}
