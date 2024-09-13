package net.optifine.gui;

import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class GuiChatOF extends ChatScreen {
   private static String CMD_RELOAD_SHADERS;
   private static String CMD_RELOAD_CHUNKS;

   public GuiChatOF(ChatScreen guiChat) {
      super(LoadingOverlay.getGuiChatText(guiChat));
   }

   public void m_241797_(String msg, boolean add) {
      if (this.checkCustomCommand(msg)) {
         this.f_96541_.f_91065_.m_93076_().m_93783_(msg);
      } else {
         super.m_241797_(msg, add);
      }
   }

   private boolean checkCustomCommand(String msg) {
      if (msg == null) {
         return false;
      } else {
         msg = msg.trim();
         if (msg.equals("/reloadShaders")) {
            if (Config.isShaders()) {
               Shaders.uninit();
               Shaders.loadShaderPack();
            }

            return true;
         } else if (msg.equals("/reloadChunks")) {
            this.f_96541_.f_91060_.m_109818_();
            return true;
         } else {
            return false;
         }
      }
   }
}
