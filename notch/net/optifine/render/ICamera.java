package net.optifine.render;

import net.minecraft.src.C_3040_;

public interface ICamera {
   void setCameraPosition(double var1, double var3, double var5);

   boolean isBoundingBoxInFrustum(C_3040_ var1);

   boolean isBoxInFrustumFully(double var1, double var3, double var5, double var7, double var9, double var11);
}
