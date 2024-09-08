package net.optifine.gui;

import java.util.Optional;
import net.minecraft.src.C_213334_;
import net.minecraft.src.C_3146_;
import net.minecraft.src.C_3160_;
import net.minecraft.src.C_3161_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_4996_;

public class OptionFullscreenResolution {
   public static C_213334_ make() {
      C_3161_ window = C_3391_.m_91087_().m_91268_();
      C_3146_ monitor = window.m_85450_();
      int j;
      if (monitor == null) {
         j = -1;
      } else {
         Optional<C_3160_> optional = window.m_85436_();
         j = (Integer)optional.map(monitor::m_84946_).orElse(-1);
      }

      C_213334_<Integer> optioninstance = new C_213334_<>(
         "options.fullscreen.resolution",
         C_213334_.m_231498_(),
         (p_232804_1_, p_232804_2_) -> {
            if (monitor == null) {
               return C_4996_.m_237115_("options.fullscreen.unavailable");
            } else {
               return p_232804_2_ == -1
                  ? C_3401_.m_231921_(p_232804_1_, C_4996_.m_237115_("options.fullscreen.current"))
                  : C_3401_.m_231921_(p_232804_1_, C_4996_.m_237113_(monitor.m_84944_(p_232804_2_).toString()));
            }
         },
         new C_213334_.C_213341_(-1, monitor != null ? monitor.m_84953_() - 1 : -1),
         j,
         p_232800_2_ -> {
            if (monitor != null) {
               window.m_85405_(p_232800_2_ == -1 ? Optional.empty() : Optional.of(monitor.m_84944_(p_232800_2_)));
            }
         }
      );
      return optioninstance;
   }
}
