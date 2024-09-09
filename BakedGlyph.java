import net.minecraft.src.C_283726_;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;

public class BakedGlyph {
   private final C_283726_ a;
   private final float b;
   private final float c;
   private final float d;
   private final float e;
   private final float f;
   private final float g;
   private final float h;
   private final float i;
   public static final Matrix4f MATRIX_IDENTITY = MathUtils.makeMatrixIdentity();

   public BakedGlyph(C_283726_ typesIn, float u0In, float u1In, float v0In, float v1In, float minXIn, float maxXIn, float minYIn, float maxYIn) {
      this.a = typesIn;
      this.b = u0In;
      this.c = u1In;
      this.d = v0In;
      this.e = v1In;
      this.f = minXIn;
      this.g = maxXIn;
      this.h = minYIn;
      this.i = maxYIn;
   }

   public void a(
      boolean italicIn,
      float xIn,
      float yIn,
      Matrix4f matrixIn,
      VertexConsumer bufferIn,
      float redIn,
      float greenIn,
      float blueIn,
      float alphaIn,
      int packedLight
   ) {
      float f = xIn + this.f;
      float f1 = xIn + this.g;
      float f2 = yIn + this.h;
      float f3 = yIn + this.i;
      float f4 = italicIn ? 1.0F - 0.25F * this.h : 0.0F;
      float f5 = italicIn ? 1.0F - 0.25F * this.i : 0.0F;
      if (bufferIn instanceof BufferBuilder && ((BufferBuilder)bufferIn).canAddVertexText()) {
         BufferBuilder bb = (BufferBuilder)bufferIn;
         int r = (int)(redIn * 255.0F);
         int g = (int)(greenIn * 255.0F);
         int b = (int)(blueIn * 255.0F);
         int a = (int)(alphaIn * 255.0F);
         int col = a << 24 | b << 16 | g << 8 | r;
         Matrix4f mat4 = matrixIn == MATRIX_IDENTITY ? null : matrixIn;
         bb.addVertexText(mat4, f + f4, f2, 0.0F, col, this.b, this.d, packedLight);
         bb.addVertexText(mat4, f + f5, f3, 0.0F, col, this.b, this.e, packedLight);
         bb.addVertexText(mat4, f1 + f5, f3, 0.0F, col, this.c, this.e, packedLight);
         bb.addVertexText(mat4, f1 + f4, f2, 0.0F, col, this.c, this.d, packedLight);
      } else {
         bufferIn.a(matrixIn, f + f4, f2, 0.0F).a(redIn, greenIn, blueIn, alphaIn).a(this.b, this.d).c(packedLight);
         bufferIn.a(matrixIn, f + f5, f3, 0.0F).a(redIn, greenIn, blueIn, alphaIn).a(this.b, this.e).c(packedLight);
         bufferIn.a(matrixIn, f1 + f5, f3, 0.0F).a(redIn, greenIn, blueIn, alphaIn).a(this.c, this.e).c(packedLight);
         bufferIn.a(matrixIn, f1 + f4, f2, 0.0F).a(redIn, greenIn, blueIn, alphaIn).a(this.c, this.d).c(packedLight);
      }
   }

   public void a(BakedGlyph.a effectIn, Matrix4f matrixIn, VertexConsumer bufferIn, int packedLightIn) {
      if (bufferIn instanceof BufferBuilder && ((BufferBuilder)bufferIn).canAddVertexText()) {
         BufferBuilder bb = (BufferBuilder)bufferIn;
         int r = (int)(effectIn.f * 255.0F);
         int g = (int)(effectIn.g * 255.0F);
         int b = (int)(effectIn.h * 255.0F);
         int a = (int)(effectIn.i * 255.0F);
         int col = a << 24 | b << 16 | g << 8 | r;
         Matrix4f mat4 = matrixIn == MATRIX_IDENTITY ? null : matrixIn;
         bb.addVertexText(mat4, effectIn.a, effectIn.b, effectIn.e, col, this.b, this.d, packedLightIn);
         bb.addVertexText(mat4, effectIn.c, effectIn.b, effectIn.e, col, this.b, this.e, packedLightIn);
         bb.addVertexText(mat4, effectIn.c, effectIn.d, effectIn.e, col, this.c, this.e, packedLightIn);
         bb.addVertexText(mat4, effectIn.a, effectIn.d, effectIn.e, col, this.c, this.d, packedLightIn);
      } else {
         bufferIn.a(matrixIn, effectIn.a, effectIn.b, effectIn.e).a(effectIn.f, effectIn.g, effectIn.h, effectIn.i).a(this.b, this.d).c(packedLightIn);
         bufferIn.a(matrixIn, effectIn.c, effectIn.b, effectIn.e).a(effectIn.f, effectIn.g, effectIn.h, effectIn.i).a(this.b, this.e).c(packedLightIn);
         bufferIn.a(matrixIn, effectIn.c, effectIn.d, effectIn.e).a(effectIn.f, effectIn.g, effectIn.h, effectIn.i).a(this.c, this.e).c(packedLightIn);
         bufferIn.a(matrixIn, effectIn.a, effectIn.d, effectIn.e).a(effectIn.f, effectIn.g, effectIn.h, effectIn.i).a(this.c, this.d).c(packedLightIn);
      }
   }

   public RenderType a(Font.a modeIn) {
      return this.a.a(modeIn);
   }

   public static class a {
      protected final float a;
      protected final float b;
      protected final float c;
      protected final float d;
      protected final float e;
      protected final float f;
      protected final float g;
      protected final float h;
      protected final float i;

      public a(float x0In, float y0In, float x1In, float y1In, float depthIn, float rIn, float gIn, float bIn, float aIn) {
         this.a = x0In;
         this.b = y0In;
         this.c = x1In;
         this.d = y1In;
         this.e = depthIn;
         this.f = rIn;
         this.g = gIn;
         this.h = bIn;
         this.i = aIn;
      }
   }
}
