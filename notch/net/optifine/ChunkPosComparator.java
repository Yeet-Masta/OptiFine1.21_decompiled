package net.optifine;

import java.util.Comparator;
import net.minecraft.src.C_1560_;
import net.minecraft.src.C_188_;

public class ChunkPosComparator implements Comparator<C_1560_> {
   private int chunkPosX;
   private int chunkPosZ;
   private double yawRad;
   private double pitchNorm;

   public ChunkPosComparator(int chunkPosX, int chunkPosZ, double yawRad, double pitchRad) {
      this.chunkPosX = chunkPosX;
      this.chunkPosZ = chunkPosZ;
      this.yawRad = yawRad;
      this.pitchNorm = 1.0 - C_188_.m_14008_(Math.abs(pitchRad) / (Math.PI / 2), 0.0, 1.0);
   }

   public int compare(C_1560_ cp1, C_1560_ cp2) {
      int distSq1 = this.getDistSq(cp1);
      int distSq2 = this.getDistSq(cp2);
      return distSq1 - distSq2;
   }

   private int getDistSq(C_1560_ cp) {
      int dx = cp.f_45578_ - this.chunkPosX;
      int dz = cp.f_45579_ - this.chunkPosZ;
      int distSq = dx * dx + dz * dz;
      double yaw = C_188_.m_14136_((double)dz, (double)dx);
      double dYaw = Math.abs(yaw - this.yawRad);
      if (dYaw > Math.PI) {
         dYaw = (Math.PI * 2) - dYaw;
      }

      return (int)((double)distSq * 1000.0 * this.pitchNorm * dYaw * dYaw);
   }
}
