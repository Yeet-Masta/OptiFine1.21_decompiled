package net.optifine.gui;

import com.mojang.blaze3d.platform.VideoMode;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class OptionFullscreenResolution {
   public static net.minecraft.client.OptionInstance make() {
      com.mojang.blaze3d.platform.Window window = Minecraft.m_91087_().m_91268_();
      com.mojang.blaze3d.platform.Monitor monitor = window.m_85450_();
      int j;
      if (monitor == null) {
         j = -1;
      } else {
         Optional<VideoMode> optional = window.m_85436_();
         j = (Integer)optional.map(monitor::m_84946_).orElse(-1);
      }

      net.minecraft.client.OptionInstance<Integer> optioninstance = new net.minecraft.client.OptionInstance<>(
         "options.fullscreen.resolution",
         net.minecraft.client.OptionInstance.m_231498_(),
         (p_232804_1_, p_232804_2_) -> {
            if (monitor == null) {
               return Component.m_237115_("options.fullscreen.unavailable");
            } else {
               return p_232804_2_ == -1
                  ? net.minecraft.client.Options.m_231921_(p_232804_1_, Component.m_237115_("options.fullscreen.current"))
                  : net.minecraft.client.Options.m_231921_(p_232804_1_, Component.m_237113_(monitor.m_84944_(p_232804_2_).toString()));
            }
         },
         new net.minecraft.client.OptionInstance.IntRange(-1, monitor != null ? monitor.m_84953_() - 1 : -1),
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
