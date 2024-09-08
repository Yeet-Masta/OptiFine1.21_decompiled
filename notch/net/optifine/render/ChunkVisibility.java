package net.optifine.render;

import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import net.minecraft.src.C_141284_;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_2139_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4180_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_507_;
import net.optifine.Config;

public class ChunkVisibility {
   public static final int MASK_FACINGS = 63;
   public static final C_4687_[][] enumFacingArrays = makeEnumFacingArrays(false);
   public static final C_4687_[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
   private static int counter = 0;
   private static int iMaxStatic = -1;
   private static int iMaxStaticFinal = 16;
   private static C_3899_ worldLast = null;
   private static int pcxLast = Integer.MIN_VALUE;
   private static int pczLast = Integer.MIN_VALUE;

   public static int getMaxChunkY(C_3899_ world, C_507_ viewEntity, int renderDistanceChunks, C_4180_ viewFrustum) {
      int minHeight = world.I_();
      int maxHeight = world.J_();
      int minChunkHeight = minHeight >> 4;
      int pcx = C_188_.m_14107_(viewEntity.m_20185_()) >> 4;
      int pcy = C_188_.m_14107_(viewEntity.m_20186_() - (double)minHeight) >> 4;
      int pcz = C_188_.m_14107_(viewEntity.m_20189_()) >> 4;
      int pcyMax = maxHeight - minHeight >> 4;
      pcy = Config.limit(pcy, 0, pcyMax - 1);
      long playerSectionKey = C_4710_.m_175568_(viewEntity.m_20183_());
      C_141284_ playerSection = world.getSectionStorage().m_156895_(playerSectionKey);
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
            C_2137_ chunk = world.m_6325_(cx, cz);
            if (chunk.m_6430_()) {
               if (multiplayer) {
                  int i = viewFrustum.getHighestUsedChunkIndex(cx, iMax, cz);
                  if (i > iMax) {
                     iMax = i;
                  }
               }
            } else {
               C_2139_[] ebss = chunk.d();

               for (int i = ebss.length - 1; i > iMax; i--) {
                  C_2139_ ebs = ebss[i];
                  if (ebs != null && !ebs.m_188008_()) {
                     if (i > iMax) {
                        iMax = i;
                     }
                     break;
                  }
               }

               try {
                  Map<C_4675_, C_1991_> mapTileEntities = chunk.m_62954_();
                  if (!mapTileEntities.isEmpty()) {
                     for (C_4675_ pos : mapTileEntities.keySet()) {
                        int ix = pos.v() - minHeight >> 4;
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
            int sectionY = C_4710_.m_123225_(sectionKey);
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

   private static C_4687_[][] makeEnumFacingArrays(boolean opposite) {
      int count = 64;
      C_4687_[][] arrs = new C_4687_[count][];

      for (int i = 0; i < count; i++) {
         List<C_4687_> list = new ArrayList();

         for (int ix = 0; ix < C_4687_.f_122346_.length; ix++) {
            C_4687_ facing = C_4687_.f_122346_[ix];
            C_4687_ facingMask = opposite ? facing.m_122424_() : facing;
            int mask = 1 << facingMask.ordinal();
            if ((i & mask) != 0) {
               list.add(facing);
            }
         }

         C_4687_[] fs = (C_4687_[])list.toArray(new C_4687_[list.size()]);
         arrs[i] = fs;
      }

      return arrs;
   }

   public static C_4687_[] getFacingsNotOpposite(int setDisabled) {
      int index = ~setDisabled & 63;
      return enumFacingOppositeArrays[index];
   }

   public static C_4687_[] getFacings(int setDirections) {
      int index = setDirections & 63;
      return enumFacingArrays[index];
   }

   public static void reset() {
      worldLast = null;
   }
}
