package net.optifine.shaders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4180_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_290152_.C_290138_;

public class ShadowUtils {
   public static Iterator<C_290138_> makeShadowChunkIterator(
      C_3899_ world, double partialTicks, C_507_ viewEntity, int renderDistanceChunks, C_4180_ viewFrustum
   ) {
      float shadowRenderDistance = Shaders.getShadowRenderDistance();
      if (!(shadowRenderDistance <= 0.0F) && !(shadowRenderDistance >= (float)((renderDistanceChunks - 1) * 16))) {
         int shadowDistanceChunks = C_188_.m_14167_(shadowRenderDistance / 16.0F) + 1;
         float car = world.a((float)partialTicks);
         float sunTiltRad = Shaders.sunPathRotation * C_188_.deg2Rad;
         float sar = car > C_188_.PId2 && car < 3.0F * C_188_.PId2 ? car + (float) Math.PI : car;
         float dx = -C_188_.m_14031_(sar);
         float dy = C_188_.m_14089_(sar) * C_188_.m_14089_(sunTiltRad);
         float dz = -C_188_.m_14089_(sar) * C_188_.m_14031_(sunTiltRad);
         C_4675_ posEntity = new C_4675_(
            C_188_.m_14107_(viewEntity.m_20185_()) >> 4, C_188_.m_14107_(viewEntity.m_20186_()) >> 4, C_188_.m_14107_(viewEntity.m_20189_()) >> 4
         );
         C_4675_ posStart = posEntity.m_7918_(
            (int)(-dx * (float)shadowDistanceChunks), (int)(-dy * (float)shadowDistanceChunks), (int)(-dz * (float)shadowDistanceChunks)
         );
         C_4675_ posEnd = posEntity.m_7918_(
            (int)(dx * (float)renderDistanceChunks), (int)(dy * (float)renderDistanceChunks), (int)(dz * (float)renderDistanceChunks)
         );
         return new IteratorRenderChunks(viewFrustum, posStart, posEnd, shadowDistanceChunks, shadowDistanceChunks);
      } else {
         List<C_290138_> listChunks = Arrays.asList(viewFrustum.f_291707_);
         return listChunks.iterator();
      }
   }
}
