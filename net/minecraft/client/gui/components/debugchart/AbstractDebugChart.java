package net.minecraft.client.gui.components.debugchart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.util.debugchart.SampleStorage;

public abstract class AbstractDebugChart {
   protected static final int f_290832_ = 14737632;
   protected static final int f_290328_ = 60;
   protected static final int f_291047_ = 1;
   protected final Font f_290686_;
   protected final SampleStorage f_316855_;

   protected AbstractDebugChart(Font fontIn, SampleStorage loggerIn) {
      this.f_290686_ = fontIn;
      this.f_316855_ = loggerIn;
   }

   public int m_295014_(int widthIn) {
      return Math.min(this.f_316855_.m_323740_() + 2, widthIn);
   }

   public void m_293623_(GuiGraphics graphicsIn, int xIn, int widthIn) {
      if (this instanceof TpsDebugChart) {
         Minecraft mc = Minecraft.m_91087_();
         int widthScaledOF = (int)(512.0 / mc.m_91268_().m_85449_());
         xIn = Math.max(xIn, widthScaledOF);
         widthIn = mc.m_91268_().m_85445_() - xIn;
      }

      int i = graphicsIn.m_280206_();
      graphicsIn.m_285944_(RenderType.m_286086_(), xIn, i - 60, xIn + widthIn, i, -1873784752);
      long j = 0L;
      long k = 2147483647L;
      long l = -2147483648L;
      int i1 = Math.max(0, this.f_316855_.m_323740_() - (widthIn - 2));
      int j1 = this.f_316855_.m_322219_() - i1;

      for(int k1 = 0; k1 < j1; ++k1) {
         int l1 = xIn + k1 + 1;
         int i2 = i1 + k1;
         long j2 = this.m_320595_(i2);
         k = Math.min(k, j2);
         l = Math.max(l, j2);
         j += j2;
         this.m_319338_(graphicsIn, i, l1, i2);
      }

      graphicsIn.m_285844_(RenderType.m_286086_(), xIn, xIn + widthIn - 1, i - 60, -1);
      graphicsIn.m_285844_(RenderType.m_286086_(), xIn, xIn + widthIn - 1, i - 1, -1);
      graphicsIn.m_285886_(RenderType.m_286086_(), xIn, i - 60, i, -1);
      graphicsIn.m_285886_(RenderType.m_286086_(), xIn + widthIn - 1, i - 60, i, -1);
      if (j1 > 0) {
         String var10000 = this.m_293688_((double)k);
         String s = var10000 + " min";
         var10000 = this.m_293688_((double)j / (double)j1);
         String s1 = var10000 + " avg";
         var10000 = this.m_293688_((double)l);
         String s2 = var10000 + " max";
         graphicsIn.m_280488_(this.f_290686_, s, xIn + 2, i - 60 - 9, 14737632);
         graphicsIn.m_280137_(this.f_290686_, s1, xIn + widthIn / 2, i - 60 - 9, 14737632);
         graphicsIn.m_280488_(this.f_290686_, s2, xIn + widthIn - this.f_290686_.m_92895_(s2) - 2, i - 60 - 9, 14737632);
      }

      this.m_293086_(graphicsIn, xIn, widthIn, i);
   }

   protected void m_319338_(GuiGraphics graphicsIn, int yIn, int xIn, int indexIn) {
      this.m_323640_(graphicsIn, yIn, xIn, indexIn);
      this.m_321123_(graphicsIn, yIn, xIn, indexIn);
   }

   protected void m_323640_(GuiGraphics graphicsIn, int yIn, int xIn, int indexIn) {
      long i = this.f_316855_.m_318870_(indexIn);
      int j = this.m_292631_((double)i);
      int k = this.m_292871_(i);
      graphicsIn.m_285944_(RenderType.m_286086_(), xIn, yIn - j, xIn + 1, yIn, k);
   }

   protected void m_321123_(GuiGraphics graphicsIn, int yIn, int xIn, int indexIn) {
   }

   protected long m_320595_(int indexIn) {
      return this.f_316855_.m_318870_(indexIn);
   }

   protected void m_293086_(GuiGraphics graphicsIn, int xIn, int widthIn, int heightIn) {
   }

   protected void m_293663_(GuiGraphics graphicsIn, String textIn, int xIn, int yIn) {
      graphicsIn.m_285944_(RenderType.m_286086_(), xIn, yIn, xIn + this.f_290686_.m_92895_(textIn) + 1, yIn + 9, -1873784752);
      graphicsIn.m_280056_(this.f_290686_, textIn, xIn + 1, yIn + 1, 14737632, false);
   }

   protected abstract String m_293688_(double var1);

   protected abstract int m_292631_(double var1);

   protected abstract int m_292871_(long var1);

   protected int m_295426_(double valueIn, double valueMinIn, int colMinIn, double valueTresholdIn, int colThreasholdIn, double valueMaxIn, int colMaxIn) {
      valueIn = Mth.m_14008_(valueIn, valueMinIn, valueMaxIn);
      return valueIn < valueTresholdIn ? ARGB32.m_269105_((float)((valueIn - valueMinIn) / (valueTresholdIn - valueMinIn)), colMinIn, colThreasholdIn) : ARGB32.m_269105_((float)((valueIn - valueTresholdIn) / (valueMaxIn - valueTresholdIn)), colThreasholdIn, colMaxIn);
   }
}
