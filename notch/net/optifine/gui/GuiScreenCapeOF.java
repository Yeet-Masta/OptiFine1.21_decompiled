package net.optifine.gui;

import com.mojang.authlib.exceptions.InvalidCredentialsException;
import java.math.BigInteger;
import java.net.URI;
import java.util.Random;
import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3449_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4513_;
import net.minecraft.src.C_4996_;
import net.optifine.Config;
import net.optifine.Lang;

public class GuiScreenCapeOF extends GuiScreenOF {
   private final C_3583_ parentScreen;
   private String message;
   private long messageHideTimeMs;
   private String linkUrl;
   private GuiButtonOF buttonCopyLink;

   public GuiScreenCapeOF(C_3583_ parentScreenIn) {
      super(C_4996_.m_237113_(C_4513_.m_118938_("of.options.capeOF.title", new Object[0])));
      this.parentScreen = parentScreenIn;
   }

   protected void m_7856_() {
      int i = 0;
      i += 2;
      this.m_142416_(
         new GuiButtonOF(
            210, this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 24 * (i >> 1), 150, 20, C_4513_.m_118938_("of.options.capeOF.openEditor", new Object[0])
         )
      );
      this.m_142416_(
         new GuiButtonOF(
            220, this.f_96543_ / 2 - 155 + 160, this.f_96544_ / 6 + 24 * (i >> 1), 150, 20, C_4513_.m_118938_("of.options.capeOF.reloadCape", new Object[0])
         )
      );
      i += 6;
      this.buttonCopyLink = new GuiButtonOF(
         230, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 24 * (i >> 1), 200, 20, C_4513_.m_118938_("of.options.capeOF.copyEditorLink", new Object[0])
      );
      this.buttonCopyLink.k = this.linkUrl != null;
      this.m_142416_(this.buttonCopyLink);
      i += 4;
      this.m_142416_(new GuiButtonOF(200, this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 24 * (i >> 1), C_4513_.m_118938_("gui.done", new Object[0])));
   }

   protected void actionPerformed(C_3449_ guiElement) {
      if (guiElement instanceof GuiButtonOF button) {
         if (button.j) {
            if (button.id == 200) {
               this.f_96541_.m_91152_(this.parentScreen);
            }

            if (button.id == 210) {
               try {
                  String userName = this.f_96541_.m_294346_().getName();
                  String userId = this.f_96541_.m_294346_().getId().toString().replace("-", "");
                  String accessToken = this.f_96541_.m_91094_().m_92547_();
                  Random r1 = new Random();
                  Random r2 = new Random((long)System.identityHashCode(new Object()));
                  BigInteger random1Bi = new BigInteger(128, r1);
                  BigInteger random2Bi = new BigInteger(128, r2);
                  BigInteger serverBi = random1Bi.xor(random2Bi);
                  String serverId = serverBi.toString(16);
                  this.f_96541_.m_91108_().joinServer(this.f_96541_.m_294346_().getId(), accessToken, serverId);
                  String urlStr = "https://optifine.net/capeChange?u=" + userId + "&n=" + userName + "&s=" + serverId;
                  boolean opened = Config.openWebLink(new URI(urlStr));
                  if (opened) {
                     this.showMessage(Lang.get("of.message.capeOF.openEditor"), 10000L);
                  } else {
                     this.showMessage(Lang.get("of.message.capeOF.openEditorError"), 10000L);
                     this.setLinkUrl(urlStr);
                  }
               } catch (InvalidCredentialsException var14) {
                  Config.showGuiMessage(
                     C_4513_.m_118938_("of.message.capeOF.error1", new Object[0]),
                     C_4513_.m_118938_("of.message.capeOF.error2", new Object[]{var14.getMessage()})
                  );
                  Config.warn("Mojang authentication failed");
                  Config.warn(var14.getClass().getName() + ": " + var14.getMessage());
               } catch (Exception var15) {
                  Config.warn("Error opening OptiFine cape link");
                  Config.warn(var15.getClass().getName() + ": " + var15.getMessage());
               }
            }

            if (button.id == 220) {
               this.showMessage(Lang.get("of.message.capeOF.reloadCape"), 15000L);
               if (this.f_96541_.f_91074_ != null) {
                  long delayMs = 15000L;
                  long reloadTimeMs = System.currentTimeMillis() + delayMs;
                  this.f_96541_.f_91074_.setReloadCapeTimeMs(reloadTimeMs);
               }
            }

            if (button.id == 230 && this.linkUrl != null) {
               this.f_96541_.f_91068_.m_90911_(this.linkUrl);
            }
         }
      }
   }

   private void showMessage(String msg, long timeMs) {
      this.message = msg;
      this.messageHideTimeMs = System.currentTimeMillis() + timeMs;
      this.setLinkUrl(null);
   }

   public void m_88315_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      drawCenteredString(graphicsIn, this.fontRenderer, this.f_96539_, this.f_96543_ / 2, 20, 16777215);
      if (this.message != null) {
         drawCenteredString(graphicsIn, this.fontRenderer, this.message, this.f_96543_ / 2, this.f_96544_ / 6 + 60, 16777215);
         if (System.currentTimeMillis() > this.messageHideTimeMs) {
            this.message = null;
            this.setLinkUrl(null);
         }
      }
   }

   public void setLinkUrl(String linkUrl) {
      this.linkUrl = linkUrl;
      this.buttonCopyLink.k = linkUrl != null;
   }
}
