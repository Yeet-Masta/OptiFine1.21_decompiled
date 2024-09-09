import net.minecraft.src.C_290133_;
import net.minecraft.src.C_313658_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_174_.C_175_;

public abstract class AbstractDebugChart {
   protected static final int a = 14737632;
   protected static final int b = 60;
   protected static final int c = 1;
   protected final Font d;
   protected final C_313658_ e;

   protected AbstractDebugChart(Font fontIn, C_313658_ loggerIn) {
      this.d = fontIn;
      this.e = loggerIn;
   }

   public int a(int widthIn) {
      return Math.min(this.e.m_323740_() + 2, widthIn);
   }

   public void a(GuiGraphics graphicsIn, int xIn, int widthIn) {
      if (this instanceof C_290133_) {
         C_3391_ mc = C_3391_.m_91087_();
         int widthScaledOF = (int)(512.0 / mc.aM().t());
         xIn = Math.max(xIn, widthScaledOF);
         widthIn = mc.aM().p() - xIn;
      }

      int i = graphicsIn.b();
      graphicsIn.a(RenderType.F(), xIn, i - 60, xIn + widthIn, i, -1873784752);
      long j = 0L;
      long k = 2147483647L;
      long l = -2147483648L;
      int i1 = Math.max(0, this.e.m_323740_() - (widthIn - 2));
      int j1 = this.e.m_322219_() - i1;

      for (int k1 = 0; k1 < j1; k1++) {
         int l1 = xIn + k1 + 1;
         int i2 = i1 + k1;
         long j2 = this.b(i2);
         k = Math.min(k, j2);
         l = Math.max(l, j2);
         j += j2;
         this.a(graphicsIn, i, l1, i2);
      }

      graphicsIn.a(RenderType.F(), xIn, xIn + widthIn - 1, i - 60, -1);
      graphicsIn.a(RenderType.F(), xIn, xIn + widthIn - 1, i - 1, -1);
      graphicsIn.b(RenderType.F(), xIn, i - 60, i, -1);
      graphicsIn.b(RenderType.F(), xIn + widthIn - 1, i - 60, i, -1);
      if (j1 > 0) {
         String s = this.a((double)k) + " min";
         String s1 = this.a((double)j / (double)j1) + " avg";
         String s2 = this.a((double)l) + " max";
         graphicsIn.b(this.d, s, xIn + 2, i - 60 - 9, 14737632);
         graphicsIn.a(this.d, s1, xIn + widthIn / 2, i - 60 - 9, 14737632);
         graphicsIn.b(this.d, s2, xIn + widthIn - this.d.b(s2) - 2, i - 60 - 9, 14737632);
      }

      this.d(graphicsIn, xIn, widthIn, i);
   }

   protected void a(GuiGraphics graphicsIn, int yIn, int xIn, int indexIn) {
      this.b(graphicsIn, yIn, xIn, indexIn);
      this.c(graphicsIn, yIn, xIn, indexIn);
   }

   protected void b(GuiGraphics graphicsIn, int yIn, int xIn, int indexIn) {
      long i = this.e.m_318870_(indexIn);
      int j = this.b((double)i);
      int k = this.a(i);
      graphicsIn.a(RenderType.F(), xIn, yIn - j, xIn + 1, yIn, k);
   }

   protected void c(GuiGraphics graphicsIn, int yIn, int xIn, int indexIn) {
   }

   protected long b(int indexIn) {
      return this.e.m_318870_(indexIn);
   }

   protected void d(GuiGraphics graphicsIn, int xIn, int widthIn, int heightIn) {
   }

   protected void a(GuiGraphics graphicsIn, String textIn, int xIn, int yIn) {
      graphicsIn.a(RenderType.F(), xIn, yIn, xIn + this.d.b(textIn) + 1, yIn + 9, -1873784752);
      graphicsIn.a(this.d, textIn, xIn + 1, yIn + 1, 14737632, false);
   }

   protected abstract String a(double var1);

   protected abstract int b(double var1);

   protected abstract int a(long var1);

   protected int a(double valueIn, double valueMinIn, int colMinIn, double valueTresholdIn, int colThreasholdIn, double valueMaxIn, int colMaxIn) {
      valueIn = Mth.a(valueIn, valueMinIn, valueMaxIn);
      return valueIn < valueTresholdIn
         ? C_175_.m_269105_((float)((valueIn - valueMinIn) / (valueTresholdIn - valueMinIn)), colMinIn, colThreasholdIn)
         : C_175_.m_269105_((float)((valueIn - valueTresholdIn) / (valueMaxIn - valueTresholdIn)), colThreasholdIn, colMaxIn);
   }
}
