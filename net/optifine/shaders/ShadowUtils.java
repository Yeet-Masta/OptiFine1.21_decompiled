package net.optifine.shaders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

public class ShadowUtils {
   public static Iterator<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> makeShadowChunkIterator(
      net.minecraft.client.multiplayer.ClientLevel world,
      double partialTicks,
      Entity viewEntity,
      int renderDistanceChunks,
      net.minecraft.client.renderer.ViewArea viewFrustum
   ) {
      float shadowRenderDistance = Shaders.getShadowRenderDistance();
      if (!(shadowRenderDistance <= 0.0F) && !(shadowRenderDistance >= (float)((renderDistanceChunks - 1) * 16))) {
         int shadowDistanceChunks = net.minecraft.util.Mth.m_14167_(shadowRenderDistance / 16.0F) + 1;
         float car = world.m_46490_((float)partialTicks);
         float sunTiltRad = Shaders.sunPathRotation * net.minecraft.util.Mth.deg2Rad;
         float sar = car > net.minecraft.util.Mth.PId2 && car < 3.0F * net.minecraft.util.Mth.PId2 ? car + (float) Math.PI : car;
         float dx = -net.minecraft.util.Mth.m_14031_(sar);
         float dy = net.minecraft.util.Mth.m_14089_(sar) * net.minecraft.util.Mth.m_14089_(sunTiltRad);
         float dz = -net.minecraft.util.Mth.m_14089_(sar) * net.minecraft.util.Mth.m_14031_(sunTiltRad);
         BlockPos posEntity = new BlockPos(
            net.minecraft.util.Mth.m_14107_(viewEntity.m_20185_()) >> 4,
            net.minecraft.util.Mth.m_14107_(viewEntity.m_20186_()) >> 4,
            net.minecraft.util.Mth.m_14107_(viewEntity.m_20189_()) >> 4
         );
         BlockPos posStart = posEntity.m_7918_(
            (int)(-dx * (float)shadowDistanceChunks), (int)(-dy * (float)shadowDistanceChunks), (int)(-dz * (float)shadowDistanceChunks)
         );
         BlockPos posEnd = posEntity.m_7918_(
            (int)(dx * (float)renderDistanceChunks), (int)(dy * (float)renderDistanceChunks), (int)(dz * (float)renderDistanceChunks)
         );
         return new IteratorRenderChunks(viewFrustum, posStart, posEnd, shadowDistanceChunks, shadowDistanceChunks);
      } else {
         List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> listChunks = Arrays.asList(viewFrustum.f_291707_);
         return listChunks.iterator();
      }
   }
}
