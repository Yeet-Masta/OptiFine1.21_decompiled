import javax.annotation.Nullable;
import net.minecraft.src.C_189_;
import net.minecraft.src.C_3437_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_4996_;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class ProgressScreen extends C_3583_ implements C_189_ {
   @Nullable
   private C_4996_ a;
   @Nullable
   private C_4996_ f_169367_;
   private int c;
   private boolean r;
   private final boolean s;
   private CustomLoadingScreen customLoadingScreen;

   public ProgressScreen(boolean clearIn) {
      super(C_3437_.f_93310_);
      this.s = clearIn;
      this.customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();
   }

   public boolean m_6913_() {
      return false;
   }

   protected boolean m_264396_() {
      return false;
   }

   public void m_6309_(C_4996_ component) {
      this.m_6308_(component);
   }

   public void m_6308_(C_4996_ component) {
      this.a = component;
      this.m_6307_(C_4996_.m_237115_("menu.working"));
   }

   public void m_6307_(C_4996_ component) {
      this.f_169367_ = component;
      this.m_6952_(0);
   }

   public void m_6952_(int progress) {
      this.c = progress;
   }

   public void m_7730_() {
      this.r = true;
   }

   public void a(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.r) {
         if (this.s) {
            this.f_96541_.m_91152_(null);
         }
      } else {
         super.a(graphicsIn, mouseX, mouseY, partialTicks);
         if (this.c > 0) {
            if (this.a != null) {
               graphicsIn.a(this.o, this.a, this.f_96543_ / 2, 70, 16777215);
            }

            if (this.f_169367_ != null && this.c != 0) {
               graphicsIn.a(this.o, C_4996_.m_237119_().m_7220_(this.f_169367_).m_130946_(" " + this.c + "%"), this.f_96543_ / 2, 90, 16777215);
            }
         }
      }
   }

   public void b(GuiGraphics graphicsIn, int mouseX, int mouseY, float partialTicks) {
      if (this.customLoadingScreen != null && this.f_96541_.r == null) {
         this.customLoadingScreen.drawBackground(this.f_96543_, this.f_96544_);
      } else {
         super.b(graphicsIn, mouseX, mouseY, partialTicks);
      }
   }
}
