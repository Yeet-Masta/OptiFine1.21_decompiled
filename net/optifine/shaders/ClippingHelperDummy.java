package net.optifine.shaders;

import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;

public class ClippingHelperDummy extends net.minecraft.client.renderer.culling.Frustum {
   public ClippingHelperDummy() {
      super(new Matrix4f(), new Matrix4f());
   }

   @Override
   public boolean m_113029_(AABB aabbIn) {
      return true;
   }

   @Override
   public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return true;
   }
}
