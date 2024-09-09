package net.optifine.util;

import com.mojang.blaze3d.platform.VideoMode;
import java.util.Comparator;

public class DisplayModeComparator implements Comparator {
   public int compare(Object o1, Object o2) {
      VideoMode dm1 = (VideoMode)o1;
      VideoMode dm2 = (VideoMode)o2;
      if (dm1.m_85332_() != dm2.m_85332_()) {
         return dm1.m_85332_() - dm2.m_85332_();
      } else if (dm1.m_85335_() != dm2.m_85335_()) {
         return dm1.m_85335_() - dm2.m_85335_();
      } else {
         int bits1 = dm1.m_85336_() + dm1.m_85337_() + dm1.m_85338_();
         int bits2 = dm2.m_85336_() + dm2.m_85337_() + dm2.m_85338_();
         if (bits1 != bits2) {
            return bits1 - bits2;
         } else {
            return dm1.m_85341_() != dm2.m_85341_() ? dm1.m_85341_() - dm2.m_85341_() : 0;
         }
      }
   }
}
