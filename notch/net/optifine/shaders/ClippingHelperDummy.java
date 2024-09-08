package net.optifine.shaders;

import net.minecraft.src.C_3040_;
import net.minecraft.src.C_4273_;
import org.joml.Matrix4f;

public class ClippingHelperDummy extends C_4273_ {
   public ClippingHelperDummy() {
      super(new Matrix4f(), new Matrix4f());
   }

   @Override
   public boolean m_113029_(C_3040_ aabbIn) {
      return true;
   }

   @Override
   public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return true;
   }
}
