import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_3437_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4996_;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class ReceivingLevelScreen extends C_3583_ {
   private static final C_4996_ a = C_4996_.m_237115_("multiplayer.downloadingTerrain");
   private static final long b = 30000L;
   private final long c;
   private final BooleanSupplier r;
   private final ReceivingLevelScreen.a s;
   @Nullable
   private TextureAtlasSprite u;
   private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

   public ReceivingLevelScreen(BooleanSupplier levelReceivedIn, ReceivingLevelScreen.a reasonIn) {
      super(C_3437_.f_93310_);
      this.r = levelReceivedIn;
      this.s = reasonIn;
      this.c = System.currentTimeMillis();
   }

   public boolean m_6913_() {
      return false;
   }

   protected boolean m_264396_() {
      return false;
   }

   public void a(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      super.a(graphicsIn, mouseX, mouseY, partialTicks);
      graphicsIn.a(this.o, a, this.f_96543_ / 2, this.f_96544_ / 2 - 50, 16777215);
   }

   public void b(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.customLoadingScreen != null) {
         this.customLoadingScreen.drawBackground(this.f_96543_, this.f_96544_);
      } else {
         switch (this.s) {
            case a:
               graphicsIn.a(0, 0, -90, graphicsIn.a(), graphicsIn.b(), this.m());
               break;
            case b:
               graphicsIn.b(RenderType.u(), 0, 0, this.f_96543_, this.f_96544_, 0);
               break;
            case c:
               this.a(graphicsIn, partialTicks);
               this.m_324436_(partialTicks);
               this.a(graphicsIn);
         }
      }
   }

   private TextureAtlasSprite m() {
      if (this.u != null) {
         return this.u;
      } else {
         this.u = this.f_96541_.ao().a().a(C_1710_.f_50142_.o());
         return this.u;
      }
   }

   public void m_86600_() {
      if (this.r.getAsBoolean() || System.currentTimeMillis() > this.c + 30000L) {
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

   public static enum a {
      a,
      b,
      c;
   }
}
