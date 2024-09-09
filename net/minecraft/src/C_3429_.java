package net.minecraft.src;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraftforge.client.extensions.IForgeFont;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class C_3429_ implements IForgeFont {
   private static final float f_168643_ = 0.01F;
   private static final Vector3f f_92712_ = new Vector3f(0.0F, 0.0F, 0.03F);
   public static final int f_193827_ = 8;
   public final int f_92710_ = 9;
   public final C_212974_ f_92711_ = C_212974_.m_216327_();
   private final Function f_92713_;
   final boolean f_242994_;
   private final C_3410_ f_92714_;
   private Matrix4f matrixShadow = new Matrix4f();

   public C_3429_(Function fontsIn, boolean filterIn) {
      this.f_92713_ = fontsIn;
      this.f_242994_ = filterIn;
      this.f_92714_ = new C_3410_((charIn, styleIn) -> {
         return this.m_92863_(styleIn.m_131192_()).m_243128_(charIn, this.f_242994_).m_83827_(styleIn.m_131154_());
      });
   }

   C_3511_ m_92863_(C_5265_ locationIn) {
      return (C_3511_)this.f_92713_.apply(locationIn);
   }

   public String m_92801_(String text) {
      try {
         Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
         bidi.setReorderingMode(0);
         return bidi.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return text;
      }
   }

   public int m_271703_(String text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn) {
      return this.m_272078_(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn, this.m_92718_());
   }

   public int m_272078_(String text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn, boolean bidiIn) {
      return this.m_271880_(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn, bidiIn);
   }

   public int m_272077_(C_4996_ text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn) {
      return this.m_272191_(text.m_7532_(), x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
   }

   public int m_272191_(C_178_ text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn) {
      return this.m_272085_(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
   }

   public void m_168645_(C_178_ text, float x, float y, int colorText, int colorOutline, Matrix4f matrixIn, C_4139_ bufferIn, int packedLight) {
      int i = m_92719_(colorOutline);
      C_3430_ font$stringrenderoutput = new C_3430_(bufferIn, 0.0F, 0.0F, i, false, matrixIn, C_3429_.C_180532_.NORMAL, packedLight);

      for(int j = -1; j <= 1; ++j) {
         for(int k = -1; k <= 1; ++k) {
            if (j != 0 || k != 0) {
               float[] afloat = new float[]{x};
               text.m_13731_((indexIn, styleIn, charIn) -> {
                  boolean flag = styleIn.m_131154_();
                  C_3511_ fontset = this.m_92863_(styleIn.m_131192_());
                  C_3098_ glyphinfo = fontset.m_243128_(charIn, this.f_242994_);
                  font$stringrenderoutput.f_92948_ = afloat[0] + (float)j * glyphinfo.m_5645_();
                  font$stringrenderoutput.f_92949_ = y + (float)k * glyphinfo.m_5645_();
                  afloat[0] += glyphinfo.m_83827_(flag);
                  return font$stringrenderoutput.m_6411_(indexIn, styleIn.m_178520_(i), charIn);
               });
            }
         }
      }

      C_3430_ font$stringrenderoutput1 = new C_3430_(bufferIn, x, y, m_92719_(colorText), false, matrixIn, C_3429_.C_180532_.POLYGON_OFFSET, packedLight);
      text.m_13731_(font$stringrenderoutput1);
      font$stringrenderoutput1.m_92961_(0, x);
   }

   private static int m_92719_(int colorIn) {
      return (colorIn & -67108864) == 0 ? colorIn | -16777216 : colorIn;
   }

   private int m_271880_(String text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn, boolean bidiIn) {
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

   private int m_272085_(C_178_ text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn) {
      color = m_92719_(color);
      Matrix4f matrix4f = this.matrixShadow.set(matrixIn);
      if (shadow) {
         this.m_271992_(text, x, y, color, true, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
         matrix4f.translate(f_92712_);
      }

      x = this.m_271992_(text, x, y, color, false, matrix4f, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
      return (int)x + (shadow ? 1 : 0);
   }

   private float m_271978_(String text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn) {
      C_3430_ font$stringrenderoutput = new C_3430_(bufferIn, x, y, color, shadow, matrixIn, modeIn, packedLightIn);
      C_196_.m_14346_(text, C_5020_.f_131099_, font$stringrenderoutput);
      return font$stringrenderoutput.m_92961_(colorBackgroundIn, x);
   }

   private float m_271992_(C_178_ text, float x, float y, int color, boolean shadow, Matrix4f matrixIn, C_4139_ bufferIn, C_180532_ modeIn, int colorBackgroundIn, int packedLightIn) {
      C_3430_ font$stringrenderoutput = new C_3430_(bufferIn, x, y, color, shadow, matrixIn, modeIn, packedLightIn);
      text.m_13731_(font$stringrenderoutput);
      return font$stringrenderoutput.m_92961_(colorBackgroundIn, x);
   }

   void m_253238_(C_3516_ glyphIn, boolean boldIn, boolean italicIn, float boldOffsetIn, float xIn, float yIn, Matrix4f matrix, C_3187_ bufferIn, float redIn, float greenIn, float blueIn, float alphaIn, int packedLight) {
      glyphIn.m_5626_(italicIn, xIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      if (boldIn) {
         glyphIn.m_5626_(italicIn, xIn + boldOffsetIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      }

   }

   public int m_92895_(String text) {
      return C_188_.m_14167_(this.f_92714_.m_92353_(text));
   }

   public int m_92852_(C_5000_ text) {
      return C_188_.m_14167_(this.f_92714_.m_92384_(text));
   }

   public int m_92724_(C_178_ textIn) {
      return C_188_.m_14167_(this.f_92714_.m_92336_(textIn));
   }

   public String m_92837_(String text, int width, boolean fromRight) {
      return fromRight ? this.f_92714_.m_92423_(text, width, C_5020_.f_131099_) : this.f_92714_.m_92410_(text, width, C_5020_.f_131099_);
   }

   public String m_92834_(String text, int width) {
      return this.f_92714_.m_92410_(text, width, C_5020_.f_131099_);
   }

   public C_5000_ m_92854_(C_5000_ text, int width) {
      return this.f_92714_.m_92389_(text, width, C_5020_.f_131099_);
   }

   public int m_92920_(String str, int maxLength) {
      return 9 * this.f_92714_.m_92432_(str, maxLength, C_5020_.f_131099_).size();
   }

   public int m_239133_(C_5000_ text, int width) {
      return 9 * this.f_92714_.m_92414_(text, width, C_5020_.f_131099_).size();
   }

   public List m_92923_(C_5000_ text, int width) {
      return C_4907_.m_128107_().m_128112_(this.f_92714_.m_92414_(text, width, C_5020_.f_131099_));
   }

   public boolean m_92718_() {
      return C_4907_.m_128107_().m_6627_();
   }

   public C_3410_ m_92865_() {
      return this.f_92714_;
   }

   public C_3429_ self() {
      return this;
   }

   public static enum C_180532_ {
      NORMAL,
      SEE_THROUGH,
      POLYGON_OFFSET;

      // $FF: synthetic method
      private static C_180532_[] $values() {
         return new C_180532_[]{NORMAL, SEE_THROUGH, POLYGON_OFFSET};
      }
   }

   class C_3430_ implements C_179_ {
      final C_4139_ f_92937_;
      private final boolean f_92939_;
      private final float f_92940_;
      private final float f_92941_;
      private final float f_92942_;
      private final float f_92943_;
      private final float f_92944_;
      private final Matrix4f f_92945_;
      private final C_180532_ f_181362_;
      private final int f_92947_;
      float f_92948_;
      float f_92949_;
      @Nullable
      private List f_92950_;
      private C_5020_ lastStyle;
      private C_3511_ lastStyleFont;

      private void m_92964_(C_3516_.C_3517_ effectIn) {
         if (this.f_92950_ == null) {
            this.f_92950_ = Lists.newArrayList();
         }

         this.f_92950_.add(effectIn);
      }

      public C_3430_(final C_4139_ bufferIn, final float xIn, final float yIn, final int colorIn, final boolean shadowIn, final Matrix4f matrixIn, final C_180532_ modeIn, final int packedLightIn) {
         this.f_92937_ = bufferIn;
         this.f_92948_ = xIn;
         this.f_92949_ = yIn;
         this.f_92939_ = shadowIn;
         this.f_92940_ = shadowIn ? 0.25F : 1.0F;
         this.f_92941_ = (float)(colorIn >> 16 & 255) / 255.0F * this.f_92940_;
         this.f_92942_ = (float)(colorIn >> 8 & 255) / 255.0F * this.f_92940_;
         this.f_92943_ = (float)(colorIn & 255) / 255.0F * this.f_92940_;
         this.f_92944_ = (float)(colorIn >> 24 & 255) / 255.0F;
         this.f_92945_ = MathUtils.isIdentity(matrixIn) ? C_3516_.MATRIX_IDENTITY : matrixIn;
         this.f_181362_ = modeIn;
         this.f_92947_ = packedLightIn;
      }

      public boolean m_6411_(int indexIn, C_5020_ styleIn, int charIn) {
         C_3511_ fontset = this.getFont(styleIn);
         C_3098_ glyphinfo = fontset.m_243128_(charIn, C_3429_.this.f_242994_);
         C_3516_ bakedglyph = styleIn.m_131176_() && charIn != 32 ? fontset.m_95067_(glyphinfo) : fontset.m_95078_(charIn);
         boolean flag = styleIn.m_131154_();
         float f3 = this.f_92944_;
         C_5024_ textcolor = styleIn.m_131135_();
         float f;
         float f1;
         float f2;
         if (textcolor != null) {
            int i = textcolor.m_131265_();
            f = (float)(i >> 16 & 255) / 255.0F * this.f_92940_;
            f1 = (float)(i >> 8 & 255) / 255.0F * this.f_92940_;
            f2 = (float)(i & 255) / 255.0F * this.f_92940_;
         } else {
            f = this.f_92941_;
            f1 = this.f_92942_;
            f2 = this.f_92943_;
         }

         float f7;
         float f6;
         if (!(bakedglyph instanceof C_3519_)) {
            f6 = flag ? glyphinfo.m_5619_() : 0.0F;
            f7 = this.f_92939_ ? glyphinfo.m_5645_() : 0.0F;
            C_3187_ vertexconsumer = this.f_92937_.m_6299_(bakedglyph.m_181387_(this.f_181362_));
            C_3429_.this.m_253238_(bakedglyph, flag, styleIn.m_131161_(), f6, this.f_92948_ + f7, this.f_92949_ + f7, this.f_92945_, vertexconsumer, f, f1, f2, f3, this.f_92947_);
         }

         f6 = glyphinfo.m_83827_(flag);
         f7 = this.f_92939_ ? 1.0F : 0.0F;
         if (styleIn.m_131168_()) {
            this.m_92964_(new C_3516_.C_3517_(this.f_92948_ + f7 - 1.0F, this.f_92949_ + f7 + 4.5F, this.f_92948_ + f7 + f6, this.f_92949_ + f7 + 4.5F - 1.0F, 0.01F, f, f1, f2, f3));
         }

         if (styleIn.m_131171_()) {
            this.m_92964_(new C_3516_.C_3517_(this.f_92948_ + f7 - 1.0F, this.f_92949_ + f7 + 9.0F, this.f_92948_ + f7 + f6, this.f_92949_ + f7 + 9.0F - 1.0F, 0.01F, f, f1, f2, f3));
         }

         this.f_92948_ += f6;
         return true;
      }

      public float m_92961_(int colorBackgroundIn, float xIn) {
         if (colorBackgroundIn != 0) {
            float f = (float)(colorBackgroundIn >> 24 & 255) / 255.0F;
            float f1 = (float)(colorBackgroundIn >> 16 & 255) / 255.0F;
            float f2 = (float)(colorBackgroundIn >> 8 & 255) / 255.0F;
            float f3 = (float)(colorBackgroundIn & 255) / 255.0F;
            this.m_92964_(new C_3516_.C_3517_(xIn - 1.0F, this.f_92949_ + 9.0F, this.f_92948_ + 1.0F, this.f_92949_ - 1.0F, 0.01F, f1, f2, f3, f));
         }

         if (this.f_92950_ != null) {
            C_3516_ bakedglyph = C_3429_.this.m_92863_(C_5020_.f_131100_).m_95064_();
            C_3187_ vertexconsumer = this.f_92937_.m_6299_(bakedglyph.m_181387_(this.f_181362_));
            Iterator var9 = this.f_92950_.iterator();

            while(var9.hasNext()) {
               C_3516_.C_3517_ bakedglyph$effect = (C_3516_.C_3517_)var9.next();
               bakedglyph.m_95220_(bakedglyph$effect, this.f_92945_, vertexconsumer, this.f_92947_);
            }
         }

         return this.f_92948_;
      }

      private C_3511_ getFont(C_5020_ styleIn) {
         if (styleIn == this.lastStyle) {
            return this.lastStyleFont;
         } else {
            this.lastStyle = styleIn;
            this.lastStyleFont = C_3429_.this.m_92863_(styleIn.m_131192_());
            return this.lastStyleFont;
         }
      }
   }
}
