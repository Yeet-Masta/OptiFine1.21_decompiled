package net.minecraft.src;

import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class C_3582_ extends C_3583_ {
   private static final C_4996_ f_96526_ = C_4996_.m_237115_("multiplayer.downloadingTerrain");
   private static final long f_202370_ = 30000L;
   private final long f_202373_;
   private final BooleanSupplier f_303520_;
   private final C_3582_.C_313294_ f_315092_;
   @Nullable
   private C_4486_ f_316402_;
   private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

   public C_3582_(BooleanSupplier levelReceivedIn, C_3582_.C_313294_ reasonIn) {
      super(C_3437_.f_93310_);
      this.f_303520_ = levelReceivedIn;
      this.f_315092_ = reasonIn;
      this.f_202373_ = System.currentTimeMillis();
   }

   public boolean m_6913_() {
      return false;
   }

   protected boolean m_264396_() {
      return false;
   }

   public void m_88315_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.m_280653_(this.f_96547_, f_96526_, this.f_96543_ / 2, this.f_96544_ / 2 - 50, 16777215);
   }

   public void m_280273_(C_279497_ graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.customLoadingScreen != null) {
         this.customLoadingScreen.drawBackground(this.f_96543_, this.f_96544_);
      } else {
         switch (this.f_315092_) {
            case NETHER_PORTAL:
               graphicsIn.m_280159_(0, 0, -90, graphicsIn.m_280182_(), graphicsIn.m_280206_(), this.m_323903_());
               break;
            case END_PORTAL:
               graphicsIn.m_319756_(C_4168_.m_173239_(), 0, 0, this.f_96543_, this.f_96544_, 0);
               break;
            case OTHER:
               this.m_318720_(graphicsIn, partialTicks);
               this.m_324436_(partialTicks);
               this.m_323963_(graphicsIn);
         }
      }
   }

   private C_4486_ m_323903_() {
      if (this.f_316402_ != null) {
         return this.f_316402_;
      } else {
         this.f_316402_ = this.f_96541_.m_91289_().m_110907_().m_110882_(C_1710_.f_50142_.m_49966_());
         return this.f_316402_;
      }
   }

   public void m_86600_() {
      if (this.f_303520_.getAsBoolean() || System.currentTimeMillis() > this.f_202373_ + 30000L) {
         this.m_7379_();
      }
   }

   public void m_7379_() {
      this.f_96541_.m_240477_().m_168785_(C_4996_.m_237115_("narrator.ready_to_play"));
      super.m_7379_();
   }

   public boolean m_7043_() {
      return false;
   }

   public static enum C_313294_ {
      NETHER_PORTAL,
      END_PORTAL,
      OTHER;
   }
}
