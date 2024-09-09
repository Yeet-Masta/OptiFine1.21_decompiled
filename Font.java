import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_179_;
import net.minecraft.src.C_196_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_3098_;
import net.minecraft.src.C_3410_;
import net.minecraft.src.C_3519_;
import net.minecraft.src.C_4907_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_5000_;
import net.minecraft.src.C_5020_;
import net.minecraft.src.C_5024_;
import net.minecraftforge.client.extensions.IForgeFont;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Font implements IForgeFont {
   private static final float d = 0.01F;
   private static final Vector3f e = new Vector3f(0.0F, 0.0F, 0.03F);
   public static final int a = 8;
   public final int b = 9;
   public final C_212974_ c = C_212974_.m_216327_();
   private final Function<ResourceLocation, FontSet> f;
   final boolean g;
   private final C_3410_ h;
   private Matrix4f matrixShadow = new Matrix4f();

   public Font(Function<ResourceLocation, FontSet> fontsIn, boolean filterIn) {
      this.f = fontsIn;
      this.g = filterIn;
      this.h = new C_3410_((charIn, styleIn) -> this.a(styleIn.k()).a(charIn, this.g).m_83827_(styleIn.m_131154_()));
   }

   FontSet a(ResourceLocation locationIn) {
      return (FontSet)this.f.apply(locationIn);
   }

   public String a(String text) {
      try {
         Bidi bidi = new Bidi(new ArabicShaping(8).shape(text), 127);
         bidi.setReorderingMode(0);
         return bidi.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return text;
      }
   }

   public int a(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      return this.a(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn, this.a());
   }

   public int a(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn,
      boolean bidiIn
   ) {
      return this.b(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn, bidiIn);
   }

   public int a(
      C_4996_ text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      return this.a(text.m_7532_(), x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
   }

   public int a(
      C_178_ text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      return this.b(text, x, y, color, shadow, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
   }

   public void a(C_178_ text, float x, float y, int colorText, int colorOutline, Matrix4f matrixIn, MultiBufferSource bufferIn, int packedLight) {
      int i = a(colorOutline);
      Font.b font$stringrenderoutput = new Font.b(bufferIn, 0.0F, 0.0F, i, false, matrixIn, Font.a.a, packedLight);

      for (int j = -1; j <= 1; j++) {
         for (int k = -1; k <= 1; k++) {
            if (j != 0 || k != 0) {
               float[] afloat = new float[]{x};
               int l = j;
               int i1 = k;
               text.m_13731_((indexIn, styleIn, charIn) -> {
                  boolean flag = styleIn.m_131154_();
                  FontSet fontset = this.a(styleIn.k());
                  C_3098_ glyphinfo = fontset.a(charIn, this.g);
                  font$stringrenderoutput.l = afloat[0] + (float)l * glyphinfo.m_5645_();
                  font$stringrenderoutput.m = y + (float)i1 * glyphinfo.m_5645_();
                  afloat[0] += glyphinfo.m_83827_(flag);
                  return font$stringrenderoutput.m_6411_(indexIn, styleIn.m_178520_(i), charIn);
               });
            }
         }
      }

      Font.b font$stringrenderoutput1 = new Font.b(bufferIn, x, y, a(colorText), false, matrixIn, Font.a.c, packedLight);
      text.m_13731_(font$stringrenderoutput1);
      font$stringrenderoutput1.a(0, x);
   }

   private static int a(int colorIn) {
      return (colorIn & -67108864) == 0 ? colorIn | 0xFF000000 : colorIn;
   }

   private int b(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn,
      boolean bidiIn
   ) {
      if (bidiIn) {
         text = this.a(text);
      }

      color = a(color);
      Matrix4f matrix4f = this.matrixShadow.set(matrixIn);
      if (shadow) {
         this.b(text, x, y, color, true, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
         matrix4f.translate(e);
      }

      x = this.b(text, x, y, color, false, matrix4f, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
      return (int)x + (shadow ? 1 : 0);
   }

   private int b(
      C_178_ text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      color = a(color);
      Matrix4f matrix4f = this.matrixShadow.set(matrixIn);
      if (shadow) {
         this.c(text, x, y, color, true, matrixIn, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
         matrix4f.translate(e);
      }

      x = this.c(text, x, y, color, false, matrix4f, bufferIn, modeIn, colorBackgroundIn, packedLightIn);
      return (int)x + (shadow ? 1 : 0);
   }

   private float b(
      String text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      Font.b font$stringrenderoutput = new Font.b(bufferIn, x, y, color, shadow, matrixIn, modeIn, packedLightIn);
      C_196_.m_14346_(text, C_5020_.f_131099_, font$stringrenderoutput);
      return font$stringrenderoutput.a(colorBackgroundIn, x);
   }

   private float c(
      C_178_ text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrixIn,
      MultiBufferSource bufferIn,
      Font.a modeIn,
      int colorBackgroundIn,
      int packedLightIn
   ) {
      Font.b font$stringrenderoutput = new Font.b(bufferIn, x, y, color, shadow, matrixIn, modeIn, packedLightIn);
      text.m_13731_(font$stringrenderoutput);
      return font$stringrenderoutput.a(colorBackgroundIn, x);
   }

   void a(
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
      glyphIn.a(italicIn, xIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      if (boldIn) {
         glyphIn.a(italicIn, xIn + boldOffsetIn, yIn, matrix, bufferIn, redIn, greenIn, blueIn, alphaIn, packedLight);
      }
   }

   public int b(String text) {
      return Mth.f(this.h.m_92353_(text));
   }

   public int a(C_5000_ text) {
      return Mth.f(this.h.m_92384_(text));
   }

   public int a(C_178_ textIn) {
      return Mth.f(this.h.m_92336_(textIn));
   }

   public String a(String text, int width, boolean fromRight) {
      return fromRight ? this.h.m_92423_(text, width, C_5020_.f_131099_) : this.h.m_92410_(text, width, C_5020_.f_131099_);
   }

   public String a(String text, int width) {
      return this.h.m_92410_(text, width, C_5020_.f_131099_);
   }

   public C_5000_ a(C_5000_ text, int width) {
      return this.h.m_92389_(text, width, C_5020_.f_131099_);
   }

   public int b(String str, int maxLength) {
      return 9 * this.h.m_92432_(str, maxLength, C_5020_.f_131099_).size();
   }

   public int b(C_5000_ text, int width) {
      return 9 * this.h.m_92414_(text, width, C_5020_.f_131099_).size();
   }

   public List<C_178_> c(C_5000_ text, int width) {
      return C_4907_.m_128107_().m_128112_(this.h.m_92414_(text, width, C_5020_.f_131099_));
   }

   public boolean a() {
      return C_4907_.m_128107_().m_6627_();
   }

   public C_3410_ b() {
      return this.h;
   }

   public Font self() {
      return this;
   }

   public static enum a {
      a,
      b,
      c;
   }

   class b implements C_179_ {
      final MultiBufferSource a;
      private final boolean c;
      private final float d;
      private final float e;
      private final float f;
      private final float g;
      private final float h;
      private final Matrix4f i;
      private final Font.a j;
      private final int k;
      float l;
      float m;
      @Nullable
      private List<BakedGlyph.a> n;
      private C_5020_ lastStyle;
      private FontSet lastStyleFont;

      private void a(BakedGlyph.a effectIn) {
         if (this.n == null) {
            this.n = Lists.newArrayList();
         }

         this.n.add(effectIn);
      }

      public b(
         final MultiBufferSource bufferIn,
         final float xIn,
         final float yIn,
         final int colorIn,
         final boolean shadowIn,
         final Matrix4f matrixIn,
         final Font.a modeIn,
         final int packedLightIn
      ) {
         this.a = bufferIn;
         this.l = xIn;
         this.m = yIn;
         this.c = shadowIn;
         this.d = shadowIn ? 0.25F : 1.0F;
         this.e = (float)(colorIn >> 16 & 0xFF) / 255.0F * this.d;
         this.f = (float)(colorIn >> 8 & 0xFF) / 255.0F * this.d;
         this.g = (float)(colorIn & 0xFF) / 255.0F * this.d;
         this.h = (float)(colorIn >> 24 & 0xFF) / 255.0F;
         this.i = MathUtils.isIdentity(matrixIn) ? BakedGlyph.MATRIX_IDENTITY : matrixIn;
         this.j = modeIn;
         this.k = packedLightIn;
      }

      public boolean m_6411_(int indexIn, C_5020_ styleIn, int charIn) {
         FontSet fontset = this.getFont(styleIn);
         C_3098_ glyphinfo = fontset.a(charIn, Font.this.g);
         BakedGlyph bakedglyph = styleIn.m_131176_() && charIn != 32 ? fontset.a(glyphinfo) : fontset.a(charIn);
         boolean flag = styleIn.m_131154_();
         float f3 = this.h;
         C_5024_ textcolor = styleIn.m_131135_();
         float f;
         float f1;
         float f2;
         if (textcolor != null) {
            int i = textcolor.m_131265_();
            f = (float)(i >> 16 & 0xFF) / 255.0F * this.d;
            f1 = (float)(i >> 8 & 0xFF) / 255.0F * this.d;
            f2 = (float)(i & 0xFF) / 255.0F * this.d;
         } else {
            f = this.e;
            f1 = this.f;
            f2 = this.g;
         }

         if (!(bakedglyph instanceof C_3519_)) {
            float f5 = flag ? glyphinfo.m_5619_() : 0.0F;
            float f4 = this.c ? glyphinfo.m_5645_() : 0.0F;
            VertexConsumer vertexconsumer = this.a.getBuffer(bakedglyph.a(this.j));
            Font.this.a(bakedglyph, flag, styleIn.m_131161_(), f5, this.l + f4, this.m + f4, this.i, vertexconsumer, f, f1, f2, f3, this.k);
         }

         float f6 = glyphinfo.m_83827_(flag);
         float f7 = this.c ? 1.0F : 0.0F;
         if (styleIn.m_131168_()) {
            this.a(new BakedGlyph.a(this.l + f7 - 1.0F, this.m + f7 + 4.5F, this.l + f7 + f6, this.m + f7 + 4.5F - 1.0F, 0.01F, f, f1, f2, f3));
         }

         if (styleIn.m_131171_()) {
            this.a(new BakedGlyph.a(this.l + f7 - 1.0F, this.m + f7 + 9.0F, this.l + f7 + f6, this.m + f7 + 9.0F - 1.0F, 0.01F, f, f1, f2, f3));
         }

         this.l += f6;
         return true;
      }

      public float a(int colorBackgroundIn, float xIn) {
         if (colorBackgroundIn != 0) {
            float f = (float)(colorBackgroundIn >> 24 & 0xFF) / 255.0F;
            float f1 = (float)(colorBackgroundIn >> 16 & 0xFF) / 255.0F;
            float f2 = (float)(colorBackgroundIn >> 8 & 0xFF) / 255.0F;
            float f3 = (float)(colorBackgroundIn & 0xFF) / 255.0F;
            this.a(new BakedGlyph.a(xIn - 1.0F, this.m + 9.0F, this.l + 1.0F, this.m - 1.0F, 0.01F, f1, f2, f3, f));
         }

         if (this.n != null) {
            BakedGlyph bakedglyph = Font.this.a(C_5020_.b).b();
            VertexConsumer vertexconsumer = this.a.getBuffer(bakedglyph.a(this.j));

            for (BakedGlyph.a bakedglyph$effect : this.n) {
               bakedglyph.a(bakedglyph$effect, this.i, vertexconsumer, this.k);
            }
         }

         return this.l;
      }

      private FontSet getFont(C_5020_ styleIn) {
         if (styleIn == this.lastStyle) {
            return this.lastStyleFont;
         } else {
            this.lastStyle = styleIn;
            this.lastStyleFont = Font.this.a(styleIn.k());
            return this.lastStyleFont;
         }
      }
   }
}
