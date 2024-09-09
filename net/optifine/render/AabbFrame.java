package net.optifine.render;

import net.minecraft.world.phys.AABB;

public class AabbFrame extends AABB {
   private int frameCount = -1;
   private boolean inFrustumFully = false;

   public AabbFrame(double x1, double y1, double z1, double x2, double y2, double z2) {
      super(x1, y1, z1, x2, y2, z2);
   }

   public boolean isBoundingBoxInFrustumFully(ICamera camera, int frameCount) {
      if (this.frameCount != frameCount) {
         this.inFrustumFully = camera.isBoxInFrustumFully(this.f_82288_, this.f_82289_, this.f_82290_, this.f_82291_, this.f_82292_, this.f_82293_);
         this.frameCount = frameCount;
      }

      return this.inFrustumFully;
   }
}
