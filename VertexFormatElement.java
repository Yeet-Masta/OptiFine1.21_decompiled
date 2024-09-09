import com.mojang.blaze3d.platform.GlStateManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public record VertexFormatElement(int i, int j, VertexFormatElement.a k, VertexFormatElement.b l, int m, String name, int attributeIndex) {
   public static final int a = 32;
   private static final VertexFormatElement[] n = new VertexFormatElement[32];
   private static final List<VertexFormatElement> o = new ArrayList(32);
   public static final VertexFormatElement b = register(0, 0, VertexFormatElement.a.a, VertexFormatElement.b.a, 3, "POSITION_3F", 0);
   public static final VertexFormatElement c = register(1, 0, VertexFormatElement.a.b, VertexFormatElement.b.c, 4, "COLOR_4UB", 1);
   public static final VertexFormatElement d = register(2, 0, VertexFormatElement.a.a, VertexFormatElement.b.d, 2, "TEX_2F", 2);
   public static final VertexFormatElement e = d;
   public static final VertexFormatElement f = register(3, 1, VertexFormatElement.a.e, VertexFormatElement.b.d, 2, "TEX_2S", 3);
   public static final VertexFormatElement g = register(4, 2, VertexFormatElement.a.e, VertexFormatElement.b.d, 2, "TEX_2SB", 4);
   public static final VertexFormatElement h = register(5, 0, VertexFormatElement.a.c, VertexFormatElement.b.b, 3, "NORMAL_3B", 5);
   public static final VertexFormatElement PADDING = register(6, 0, VertexFormatElement.a.c, VertexFormatElement.b.PADDING, 1, "PADDING_1B", -1);

   public VertexFormatElement(int id, int index, VertexFormatElement.a type, VertexFormatElement.b usage, int elementCount) {
      this(id, index, type, usage, elementCount, null, -1);
   }

   public VertexFormatElement(int i, int j, VertexFormatElement.a k, VertexFormatElement.b l, int m, String name, int attributeIndex) {
      if (i < 0 || i >= n.length) {
         throw new IllegalArgumentException("Element ID must be in range [0; " + n.length + ")");
      } else if (!this.a(j, l)) {
         throw new IllegalStateException("Multiple vertex elements of the same type other than UVs are not supported");
      } else {
         this.i = i;
         this.j = j;
         this.k = k;
         this.l = l;
         this.m = m;
         this.name = name;
         this.attributeIndex = attributeIndex;
      }
   }

   public static VertexFormatElement a(int p_340492_0_, int p_340492_1_, VertexFormatElement.a p_340492_2_, VertexFormatElement.b p_340492_3_, int p_340492_4_) {
      return register(p_340492_0_, p_340492_1_, p_340492_2_, p_340492_3_, p_340492_4_, null, -1);
   }

   public static VertexFormatElement register(
      int p_340492_0_, int p_340492_1_, VertexFormatElement.a p_340492_2_, VertexFormatElement.b p_340492_3_, int p_340492_4_, String name, int attributeIndex
   ) {
      VertexFormatElement vertexformatelement = new VertexFormatElement(p_340492_0_, p_340492_1_, p_340492_2_, p_340492_3_, p_340492_4_, name, attributeIndex);
      if (n[p_340492_0_] != null) {
         throw new IllegalArgumentException("Duplicate element registration for: " + p_340492_0_);
      } else {
         n[p_340492_0_] = vertexformatelement;
         o.add(vertexformatelement);
         return vertexformatelement;
      }
   }

   private boolean a(int indexIn, VertexFormatElement.b usageIn) {
      return indexIn == 0 || usageIn == VertexFormatElement.b.d;
   }

   public String toString() {
      return this.name != null ? this.name : this.m + "," + this.l + "," + this.k + " (" + this.i + ")";
   }

   public int a() {
      return 1 << this.i;
   }

   public int b() {
      return this.k.a() * this.m;
   }

   public void a(int elementIndexIn, long elementOffsetIn, int sizeIn) {
      this.l.g.setupBufferState(this.m, this.k.b(), sizeIn, elementOffsetIn, elementIndexIn);
   }

   @Nullable
   public static VertexFormatElement a(int idIn) {
      return n[idIn];
   }

   public static Stream<VertexFormatElement> b(int maskIn) {
      return o.stream().filter(p_340316_1_ -> p_340316_1_ != null && (maskIn & p_340316_1_.a()) != 0);
   }

   public final int getElementCount() {
      return this.m;
   }

   public String getName() {
      return this.name;
   }

   public int getAttributeIndex() {
      return this.attributeIndex;
   }

   public static int getElementsCount() {
      return o.size();
   }

   public int c() {
      return this.i;
   }

   public int d() {
      return this.j;
   }

   public VertexFormatElement.a e() {
      return this.k;
   }

   public VertexFormatElement.b f() {
      return this.l;
   }

   public int g() {
      return this.m;
   }

   public static enum a {
      a(4, "Float", 5126),
      b(1, "Unsigned Byte", 5121),
      c(1, "Byte", 5120),
      d(2, "Unsigned Short", 5123),
      e(2, "Short", 5122),
      f(4, "Unsigned Int", 5125),
      g(4, "Int", 5124);

      private final int h;
      private final String i;
      private final int j;

      private a(final int sizeIn, final String displayNameIn, final int glConstantIn) {
         this.h = sizeIn;
         this.i = displayNameIn;
         this.j = glConstantIn;
      }

      public int a() {
         return this.h;
      }

      public int b() {
         return this.j;
      }

      public String toString() {
         return this.i;
      }
   }

   public static enum b {
      a("Position", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, offsetIn)),
      b("Normal", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, true, strideIn, offsetIn)),
      c("Vertex Color", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, true, strideIn, offsetIn)),
      d("UV", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
         if (typeIn == 5126) {
            GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, offsetIn);
         } else {
            GlStateManager._vertexAttribIPointer(indexIn, sizeIn, typeIn, strideIn, offsetIn);
         }
      }),
      PADDING("Padding", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
      }),
      e("Generic", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, offsetIn));

      private final String f;
      final VertexFormatElement.b.a g;

      private b(final String displayNameIn, final VertexFormatElement.b.a setupStateIn) {
         this.f = displayNameIn;
         this.g = setupStateIn;
      }

      public String toString() {
         return this.f;
      }

      @FunctionalInterface
      interface a {
         void setupBufferState(int var1, int var2, int var3, long var4, int var6);
      }
   }
}
