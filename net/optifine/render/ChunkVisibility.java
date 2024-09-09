package net.optifine.render;

import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.optifine.Config;

public class ChunkVisibility {
   public static final int MASK_FACINGS = 63;
   public static final net.minecraft.core.Direction[][] enumFacingArrays = makeEnumFacingArrays(false);
   public static final net.minecraft.core.Direction[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
   private static int counter = 0;
   private static int iMaxStatic = -1;
   private static int iMaxStaticFinal = 16;
   private static net.minecraft.client.multiplayer.ClientLevel worldLast = null;
   private static int pcxLast = Integer.MIN_VALUE;
   private static int pczLast = Integer.MIN_VALUE;

   public static int getMaxChunkY(
      net.minecraft.client.multiplayer.ClientLevel world, Entity viewEntity, int renderDistanceChunks, net.minecraft.client.renderer.ViewArea viewFrustum
   ) {
      int minHeight = world.m_141937_();
      int maxHeight = world.m_141928_();
      int minChunkHeight = minHeight >> 4;
      int pcx = net.minecraft.util.Mth.m_14107_(viewEntity.m_20185_()) >> 4;
      int pcy = net.minecraft.util.Mth.m_14107_(viewEntity.m_20186_() - (double)minHeight) >> 4;
      int pcz = net.minecraft.util.Mth.m_14107_(viewEntity.m_20189_()) >> 4;
      int pcyMax = maxHeight - minHeight >> 4;
      pcy = Config.limit(pcy, 0, pcyMax - 1);
      long playerSectionKey = SectionPos.m_175568_(viewEntity.m_20183_());
      net.minecraft.world.level.entity.EntitySection playerSection = world.getSectionStorage().m_156895_(playerSectionKey);
      boolean multiplayer = !Config.isIntegratedServerRunning();
      int cxStart = pcx - renderDistanceChunks;
      int cxEnd = pcx + renderDistanceChunks;
      int czStart = pcz - renderDistanceChunks;
      int czEnd = pcz + renderDistanceChunks;
      if (world != worldLast || pcx != pcxLast || pcz != pczLast) {
         counter = 0;
         iMaxStaticFinal = 16;
         worldLast = world;
         pcxLast = pcx;
         pczLast = pcz;
      }

      if (counter == 0) {
         iMaxStatic = -1;
      }

      int iMax = iMaxStatic;
      switch (counter) {
         case 0:
            cxEnd = pcx;
            czEnd = pcz;
            break;
         case 1:
            cxStart = pcx;
            czEnd = pcz;
            break;
         case 2:
            cxEnd = pcx;
            czStart = pcz;
            break;
         case 3:
            cxStart = pcx;
            czStart = pcz;
      }

      for (int cx = cxStart; cx < cxEnd; cx++) {
         for (int cz = czStart; cz < czEnd; cz++) {
            LevelChunk chunk = world.m_6325_(cx, cz);
            if (chunk.m_6430_()) {
               if (multiplayer) {
                  int i = viewFrustum.getHighestUsedChunkIndex(cx, iMax, cz);
                  if (i > iMax) {
                     iMax = i;
                  }
               }
            } else {
               net.minecraft.world.level.chunk.LevelChunkSection[] ebss = chunk.m_7103_();

               for (int i = ebss.length - 1; i > iMax; i--) {
                  net.minecraft.world.level.chunk.LevelChunkSection ebs = ebss[i];
                  if (ebs != null && !ebs.m_188008_()) {
                     if (i > iMax) {
                        iMax = i;
                     }
                     break;
                  }
               }

               try {
                  Map<BlockPos, net.minecraft.world.level.block.entity.BlockEntity> mapTileEntities = chunk.m_62954_();
                  if (!mapTileEntities.isEmpty()) {
                     for (BlockPos pos : mapTileEntities.keySet()) {
                        int ix = pos.m_123342_() - minHeight >> 4;
                        if (ix > iMax) {
                           iMax = ix;
                        }
                     }
                  }
               } catch (ConcurrentModificationException var29) {
               }
            }
         }
      }

      if (counter == 0) {
         LongSet sectionKeys = world.getSectionStorage().getSectionKeys();
         LongIterator it = sectionKeys.iterator();

         while (it.hasNext()) {
            long sectionKey = it.nextLong();
            int sectionY = SectionPos.m_123225_(sectionKey);
            int ix = sectionY - minChunkHeight;
            if ((sectionKey != playerSectionKey || ix != pcy || playerSection == null || playerSection.getEntityList().size() != 1) && ix > iMax) {
               iMax = ix;
            }
         }
      }

      if (counter < 3) {
         iMaxStatic = iMax;
         iMax = iMaxStaticFinal;
      } else {
         iMaxStaticFinal = iMax;
         iMaxStatic = -1;
      }

      counter = (counter + 1) % 4;
      return (iMax << 4) + minHeight;
   }

   public static boolean isFinished() {
      return counter == 0;
   }

   private static net.minecraft.core.Direction[][] makeEnumFacingArrays(boolean opposite) {
      int count = 64;
      net.minecraft.core.Direction[][] arrs = new net.minecraft.core.Direction[count][];

      for (int i = 0; i < count; i++) {
         List<net.minecraft.core.Direction> list = new ArrayList();

         for (int ix = 0; ix < net.minecraft.core.Direction.f_122346_.length; ix++) {
            net.minecraft.core.Direction facing = net.minecraft.core.Direction.f_122346_[ix];
            net.minecraft.core.Direction facingMask = opposite ? facing.m_122424_() : facing;
            int mask = 1 << facingMask.ordinal();
            if ((i & mask) != 0) {
               list.add(facing);
            }
         }

         net.minecraft.core.Direction[] fs = (net.minecraft.core.Direction[])list.toArray(new net.minecraft.core.Direction[list.size()]);
         arrs[i] = fs;
      }

      return arrs;
   }

   public static net.minecraft.core.Direction[] getFacingsNotOpposite(int setDisabled) {
      int index = ~setDisabled & 63;
      return enumFacingOppositeArrays[index];
   }

   public static net.minecraft.core.Direction[] getFacings(int setDirections) {
      int index = setDirections & 63;
      return enumFacingArrays[index];
   }

   public static void reset() {
      worldLast = null;
   }
}
