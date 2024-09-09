package net.optifine.gui;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.Window;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

public class OptionFullscreenResolution {
   public static OptionInstance make() {
      Window window = Minecraft.m_91087_().m_91268_();
      Monitor monitor = window.m_85450_();
      int j;
      if (monitor == null) {
         j = -1;
      } else {
         Optional optional = window.m_85436_();
         Objects.requireNonNull(monitor);
         j = (Integer)optional.map(monitor::m_84946_).orElse(-1);
      }

      OptionInstance optioninstance = new OptionInstance("options.fullscreen.resolution", OptionInstance.m_231498_(), (p_232804_1_, p_232804_2_) -> {
         if (monitor == null) {
            return Component.m_237115_("options.fullscreen.unavailable");
         } else {
            return p_232804_2_ == -1 ? Options.m_231921_(p_232804_1_, Component.m_237115_("options.fullscreen.current")) : Options.m_231921_(p_232804_1_, Component.m_237113_(monitor.m_84944_(p_232804_2_).toString()));
         }
      }, new OptionInstance.IntRange(-1, monitor != null ? monitor.m_84953_() - 1 : -1), j, (p_232800_2_) -> {
         if (monitor != null) {
            window.m_85405_(p_232800_2_ == -1 ? Optional.empty() : Optional.of(monitor.m_84944_(p_232800_2_)));
         }

      });
      return optioninstance;
   }
}
