package net.optifine.util;

import java.util.Comparator;
import net.minecraft.src.C_3160_;

public class VideoModeComparator implements Comparator<C_3160_> {
   public int compare(C_3160_ vm1, C_3160_ vm2) {
      if (vm1.m_85332_() != vm2.m_85332_()) {
         return vm1.m_85332_() - vm2.m_85332_();
      } else if (vm1.m_85335_() != vm2.m_85335_()) {
         return vm1.m_85335_() - vm2.m_85335_();
      } else if (vm1.m_85341_() != vm2.m_85341_()) {
         return vm1.m_85341_() - vm2.m_85341_();
      } else {
         int cols1 = vm1.m_85336_() + vm1.m_85337_() + vm1.m_85338_();
         int cols2 = vm2.m_85336_() + vm2.m_85337_() + vm2.m_85338_();
         return cols1 != cols2 ? cols1 - cols2 : 0;
      }
   }
}
