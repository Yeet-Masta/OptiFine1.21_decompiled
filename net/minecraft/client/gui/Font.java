package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringDecomposer;
import net.minecraftforge.client.extensions.IForgeFont;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Font implements IForgeFont {
   private static float f_168643_;
   private static Vector3f f_92712_ = new Vector3f(0.0F, 0.0F, 0.03F);
   public static int f_193827_;
   public int f_92710_ = 9;
   public RandomSource f_92711_ = RandomSource.m_216327_();
   private Function<ResourceLocation, FontSet> f_92713_;
   boolean f_242994_;
   private StringSplitter f_92714_;
   private Matrix4f matrixShadow = new Matrix4f();

   public Font(Function<ResourceLocation, FontSet> fontsIn, boolean filterIn) {
      this.f_92713_ = fontsIn;
      this.f_242994_ = filterIn;
      this.f_92714_ = new StringSplitter(
         (charIn, styleIn) -> this.m_92863_(styleIn.m_131192_()).m_243128_(charIn, this.f_242994_).m_83827_(styleIn.m_131154_())
      );
   }

   FontSet m_92863_(ResourceLocation locationIn) {
      return (FontSet)this.f_92713_.apply(locationIn);
   }

   public String m_92801_(String text) {
      try {
         Bidi bidi = new Bidi(new ArabicShaping(8).shape(text), 127);
         bidi.setReorderingMode(0);
         return bidi.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return text;
      }
   }

   public int m_271703_(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      return this.m_272078_(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn, this.m_92718_());
   }

   public int m_272078_(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn,
      boolean bidiIn
   ) {
      return this.m_271880_(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn, bidiIn);
   }

   public int m_272077_(
      Component text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      return this.m_272191_(text.m_7532_(), x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
   }

   public int m_272191_(
      FormattedCharSequence text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      return this.m_272085_(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
   }

   public void m_168645_(
      FormattedCharSequence text, float x, float y, int colorText, int colorOutline, Matrix4f matrixIn, MultiBufferSource bufferIn, int packedLight
   ) {
      int i = m_92719_(colorOutline);
      Font.StringRenderOutput font$stringrenderoutput = new Font.StringRenderOutput(
         bufferIn, 0.0F, 0.0F, i, false, matrixIn, Font.DisplayMode.NORMAL, packedLight
      );

      for (int j = -1; j <= 1; j++) {
         for (int k = -1; k <= 1; k++) {
            if (j != 0 || k != 0) {
               float[] afloat = new float[]{x};
               int l = j;
               int i1 = k;
               text.m_13731_((indexIn, styleIn, charIn) -> {
                  boolean flag = styleIn.m_131154_();
                  FontSet fontset = this.m_92863_(styleIn.m_131192_());
                  GlyphInfo glyphinfo = fontset.m_243128_(charIn, this.f_242994_);
                  font$stringrenderoutput.f_92948_ = afloat[0] + (float)l * glyphinfo.m_5645_();
                  font$stringrenderoutput.f_92949_ = y + (float)i1 * glyphinfo.m_5645_();
                  afloat[0] += glyphinfo.m_83827_(flag);
                  return font$stringrenderoutput.m_6411_(indexIn, styleIn.m_178520_(i), charIn);
               });
            }
         }
      }

      Font.StringRenderOutput font$stringrenderoutput1 = new Font.StringRenderOutput(
         bufferIn, x, y, m_92719_(colorText), false, matrixIn, Font.DisplayMode.POLYGON_OFFSET, packedLight
      );
      text.m_13731_(font$stringrenderoutput1);
      font$stringrenderoutput1.m_92961_(0, x);
   }

   private static int m_92719_(int colorIn) {
      return (colorIn & -67108864) == 0 ? colorIn | 0xFF000000 : colorIn;
   }

   private int m_271880_(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn,
      boolean bidiIn
   ) {
      if (bidiIn) {
         text = this.m_92801_(text);
      }

      color = m_92719_(color);
      Matrix4f matrix4f = this.matrixShadow.set(matrixIn);
      if (shadow) {
         this.m_271978_(text, x, y, color, true, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
         matrix4f.translate(f_92712_);
      }

      x = this.m_271978_(text, x, y, color, false, matrix4f, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
      return (int)x + (shadow ? 1 : 0);
   }

   private int m_272085_(
      FormattedCharSequence text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      color = m_92719_(color);
      Matrix4f matrix4f = this.matrixShadow.set(matrixIn);
      if (shadow) {
         this.m_271992_(text, x, y, color, true, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
         matrix4f.translate(f_92712_);
      }

      x = this.m_271992_(text, x, y, color, false, matrix4f, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
      return (int)x + (shadow ? 1 : 0);
   }

   private float m_271978_(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      Font.StringRenderOutput font$stringrenderoutput = new Font.StringRenderOutput(bufferIn, x, y, color, shadow, matrixIn, modeIn, packedLightIn);
      StringDecomposer.m_14346_(text, Style.f_131099_, font$stringrenderoutput);
      return font$stringrenderoutput.m_92961_(colorBackgroundIn, x);
   }

   private float m_271992_(
      FormattedCharSequence text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.DisplayMode modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      Font.StringRenderOutput font$stringrenderoutput = new Font.StringRenderOutput(bufferIn, x, y, color, shadow, matrixIn, modeIn, packedLightIn);
      text.m_13731_(font$stringrenderoutput);
      return font$stringrenderoutput.m_92961_(colorBackgroundIn, x);
   }

   void m_253238_(
      BakedGlyph glyphIn,
      boolean boldIn,
      boolean italicIn,
      float boldOffsetIn,
      float xIn,
      float yIn,
      Matrix4f matrix,
      VertexConsumer bufferIn,
      float redIn,
      float greenIn,
      float blueIn,
      float alphaIn,
      int packedLight
   ) {
      glyphIn.m_5626_(italicIn, xIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      if (boldIn) {
         glyphIn.m_5626_(italicIn, xIn + boldOffsetIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      }
   }

   public int m_92895_(String text) {
      return Mth.m_14167_(this.f_92714_.m_92353_(text));
   }

   public int m_92852_(FormattedText text) {
      return Mth.m_14167_(this.f_92714_.m_92384_(text));
   }

   public int m_92724_(FormattedCharSequence textIn) {
      return Mth.m_14167_(this.f_92714_.m_92336_(textIn));
   }

   public String m_92837_(String text, int width, boolean fromRight) {
      return fromRight ? this.f_92714_.m_92423_(text, width, Style.f_131099_) : this.f_92714_.m_92410_(text, width, Style.f_131099_);
   }

   public String m_92834_(String text, int width) {
      return this.f_92714_.m_92410_(text, width, Style.f_131099_);
   }

   public FormattedText m_92854_(FormattedText text, int width) {
      return this.f_92714_.m_92389_(text, width, Style.f_131099_);
   }

   public int m_92920_(String str, int maxLength) {
      return 9 * this.f_92714_.m_92432_(str, maxLength, Style.f_131099_).size();
   }

   public int m_239133_(FormattedText text, int width) {
      return 9 * this.f_92714_.m_92414_(text, width, Style.f_131099_).size();
   }

   public List<FormattedCharSequence> m_92923_(FormattedText text, int width) {
      return Language.m_128107_().m_128112_(this.f_92714_.m_92414_(text, width, Style.f_131099_));
   }

   public boolean m_92718_() {
      return Language.m_128107_().m_6627_();
   }

   public StringSplitter m_92865_() {
      return this.f_92714_;
   }

   public Font self() {
      return this;
   }

   public static enum DisplayMode {
      NORMAL,
      SEE_THROUGH,
      POLYGON_OFFSET;
   }

   class StringRenderOutput implements FormattedCharSink {
      MultiBufferSource f_92937_;
      private boolean f_92939_;
      private float f_92940_;
      private float f_92941_;
      private float f_92942_;
      private float f_92943_;
      private float f_92944_;
      private Matrix4f f_92945_;
      private Font.DisplayMode f_181362_;
      private int f_92947_;
      float f_92948_;
      float f_92949_;
      @Nullable
      private List<BakedGlyph.Effect> f_92950_;
      private Style lastStyle;
      private FontSet lastStyleFont;

      private void m_92964_(BakedGlyph.Effect effectIn) {
         if (this.f_92950_ == null) {
            this.f_92950_ = Lists.newArrayList();
         }

         this.f_92950_.add(effectIn);
      }

      public StringRenderOutput(
         final MultiBufferSource bufferIn,
         final float xIn,
         final float yIn,
         final int colorIn,
         final boolean shadowIn,
         final Matrix4f matrixIn,
         final Font.DisplayMode modeIn,
         final int packedLightIn
      ) {
         this.f_92937_ = bufferIn;
         this.f_92948_ = xIn;
         this.f_92949_ = yIn;
         this.f_92939_ = shadowIn;
         this.f_92940_ = shadowIn ? 0.25F : 1.0F;
         this.f_92941_ = (float)(colorIn >> 16 & 0xFF) / 255.0F * this.f_92940_;
         this.f_92942_ = (float)(colorIn >> 8 & 0xFF) / 255.0F * this.f_92940_;
         this.f_92943_ = (float)(colorIn & 0xFF) / 255.0F * this.f_92940_;
         this.f_92944_ = (float)(colorIn >> 24 & 0xFF) / 255.0F;
         this.f_92945_ = MathUtils.isIdentity(matrixIn) ? BakedGlyph.MATRIX_IDENTITY : matrixIn;
         this.f_181362_ = modeIn;
         this.f_92947_ = packedLightIn;
      }

      public boolean m_6411_(int indexIn, Style styleIn, int charIn) {
         FontSet fontset = this.getFont(styleIn);
         GlyphInfo glyphinfo = fontset.m_243128_(charIn, Font.this.f_242994_);
         BakedGlyph bakedglyph = styleIn.m_131176_() && charIn != 32 ? fontset.m_95067_(glyphinfo) : fontset.m_95078_(charIn);
         boolean flag = styleIn.m_131154_();
         float f3 = this.f_92944_;
         TextColor textcolor = styleIn.m_131135_();
         float f;
         float f1;
         float f2;
         if (textcolor != null) {
            int i = textcolor.m_131265_();
            f = (float)(i >> 16 & 0xFF) / 255.0F * this.f_92940_;
            f1 = (float)(i >> 8 & 0xFF) / 255.0F * this.f_92940_;
            f2 = (float)(i & 0xFF) / 255.0F * this.f_92940_;
         } else {
            f = this.f_92941_;
            f1 = this.f_92942_;
            f2 = this.f_92943_;
         }

         if (!(bakedglyph instanceof EmptyGlyph)) {
            float f5 = flag ? glyphinfo.m_5619_() : 0.0F;
            float f4 = this.f_92939_ ? glyphinfo.m_5645_() : 0.0F;
            VertexConsumer vertexconsumer = this.f_92937_.m_6299_(bakedglyph.m_181387_(this.f_181362_));
            Font.this.m_253238_(
               bakedglyph, flag, styleIn.m_131161_(), f5, this.f_92948_ + f4, this.f_92949_ + f4, this.f_92945_, vertexconsumer, f, f1, f2, f3, this.f_92947_
            );
         }

         float f6 = glyphinfo.m_83827_(flag);
         float f7 = this.f_92939_ ? 1.0F : 0.0F;
         if (styleIn.m_131168_()) {
            this.m_92964_(
               new BakedGlyph.Effect(
                  this.f_92948_ + f7 - 1.0F, this.f_92949_ + f7 + 4.5F, this.f_92948_ + f7 + f6, this.f_92949_ + f7 + 4.5F - 1.0F, 0.01F, f, f1, f2, f3
               )
            );
         }

         if (styleIn.m_131171_()) {
            this.m_92964_(
               new BakedGlyph.Effect(
                  this.f_92948_ + f7 - 1.0F, this.f_92949_ + f7 + 9.0F, this.f_92948_ + f7 + f6, this.f_92949_ + f7 + 9.0F - 1.0F, 0.01F, f, f1, f2, f3
               )
            );
         }

         this.f_92948_ += f6;
         return true;
      }

      public float m_92961_(int colorBackgroundIn, float xIn) {
         if (colorBackgroundIn != 0) {
            float f = (float)(colorBackgroundIn >> 24 & 0xFF) / 255.0F;
            float f1 = (float)(colorBackgroundIn >> 16 & 0xFF) / 255.0F;
            float f2 = (float)(colorBackgroundIn >> 8 & 0xFF) / 255.0F;
            float f3 = (float)(colorBackgroundIn & 0xFF) / 255.0F;
            this.m_92964_(new BakedGlyph.Effect(xIn - 1.0F, this.f_92949_ + 9.0F, this.f_92948_ + 1.0F, this.f_92949_ - 1.0F, 0.01F, f1, f2, f3, f));
         }

         if (this.f_92950_ != null) {
            BakedGlyph bakedglyph = Font.this.m_92863_(Style.f_131100_).m_95064_();
            VertexConsumer vertexconsumer = this.f_92937_.m_6299_(bakedglyph.m_181387_(this.f_181362_));

            for (BakedGlyph.Effect bakedglyph$effect : this.f_92950_) {
               bakedglyph.m_95220_(bakedglyph$effect, this.f_92945_, vertexconsumer, this.f_92947_);
            }
         }

         return this.f_92948_;
      }

      private FontSet getFont(Style styleIn) {
         if (styleIn == this.lastStyle) {
            return this.lastStyleFont;
         } else {
            this.lastStyle = styleIn;
            this.lastStyleFont = Font.this.m_92863_(styleIn.m_131192_());
            return this.lastStyleFont;
         }
      }
   }
}
