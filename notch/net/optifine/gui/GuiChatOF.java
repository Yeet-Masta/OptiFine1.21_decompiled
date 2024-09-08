package net.optifine.gui;

import net.minecraft.src.C_3538_;
import net.minecraft.src.C_3565_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class GuiChatOF extends C_3538_ {
   private static final String CMD_RELOAD_SHADERS = "/reloadShaders";
   private static final String CMD_RELOAD_CHUNKS = "/reloadChunks";

   public GuiChatOF(C_3538_ guiChat) {
      super(C_3565_.getGuiChatText(guiChat));
   }

   public void m_241797_(String msg, boolean add) {
      if (this.checkCustomCommand(msg)) {
         this.l.f_91065_.m_93076_().m_93783_(msg);
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
            this.l.f_91060_.m_109818_();
            return true;
         } else {
            return false;
         }
      }
   }
}
