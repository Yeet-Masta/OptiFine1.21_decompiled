import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class VertexFormat {
   public static final int a = -1;
   private List<VertexFormatElement> b;
   private List<String> c;
   private int d;
   private int e;
   private int[] f = new int[32];
   @Nullable
   private VertexBuffer g;
   private String name;
   private int positionElementOffset = -1;
   private int normalElementOffset = -1;
   private int colorElementOffset = -1;
   private Int2IntMap uvOffsetsById = new Int2IntArrayMap();
   private ImmutableMap<String, VertexFormatElement> elementMapping;
   private boolean extended;

   VertexFormat(List<VertexFormatElement> elementsIn, List<String> namesIn, IntList offsetsIn, int vertexSizeIn) {
      this.b = elementsIn;
      this.c = namesIn;
      this.d = vertexSizeIn;
      this.e = elementsIn.stream().mapToInt(VertexFormatElement::a).reduce(0, (val1, val2) -> val1 | val2);
      Map<String, VertexFormatElement> mapElements = new HashMap();

      for (int i = 0; i < this.c.size(); i++) {
         String name = (String)this.c.get(i);
         VertexFormatElement element = (VertexFormatElement)this.b.get(i);
         mapElements.put(name, element);
      }

      this.elementMapping = ImmutableMap.copyOf(mapElements);

      for (int i = 0; i < this.f.length; i++) {
         VertexFormatElement vertexformatelement = VertexFormatElement.a(i);
         int j = vertexformatelement != null ? elementsIn.indexOf(vertexformatelement) : -1;
         this.f[i] = j != -1 ? offsetsIn.getInt(j) : -1;
         if (vertexformatelement != null) {
            VertexFormatElement.b usage = vertexformatelement.f();
            int offset = this.f[i];
            if (usage == VertexFormatElement.b.a) {
               this.positionElementOffset = offset;
            } else if (usage == VertexFormatElement.b.b) {
               this.normalElementOffset = offset;
            } else if (usage == VertexFormatElement.b.c) {
               this.colorElementOffset = offset;
            } else if (usage == VertexFormatElement.b.d) {
               this.uvOffsetsById.put(vertexformatelement.d(), offset);
            }
         }
      }
   }

   public static VertexFormat.a a() {
      return new VertexFormat.a();
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("Vertex format: " + this.name + " (").append(this.d).append(" bytes):\n");

      for (int i = 0; i < this.b.size(); i++) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)this.b.get(i);
         stringbuilder.append(i)
            .append(". ")
            .append((String)this.c.get(i))
            .append(": ")
            .append(vertexformatelement)
            .append(" @ ")
            .append(this.a(vertexformatelement))
            .append('\n');
      }

      return stringbuilder.toString();
   }

   public int b() {
      return this.d;
   }

   public List<VertexFormatElement> c() {
      return this.b;
   }

   public List<String> d() {
      return this.c;
   }

   public int[] e() {
      return this.f;
   }

   public int a(VertexFormatElement elementIn) {
      return this.f[elementIn.c()];
   }

   public boolean b(VertexFormatElement elementIn) {
      return (this.e & elementIn.a()) != 0;
   }

   public int f() {
      return this.e;
   }

   public String c(VertexFormatElement elementIn) {
      int i = this.b.indexOf(elementIn);
      if (i == -1) {
         throw new IllegalArgumentException(elementIn + " is not contained in format");
      } else {
         return (String)this.c.get(i);
      }
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof VertexFormat vertexformat
            && this.e == vertexformat.e
            && this.d == vertexformat.d
            && this.c.equals(vertexformat.c)
            && Arrays.equals(this.f, vertexformat.f)) {
            return true;
         }

         return false;
      }
   }

   public int hashCode() {
      return this.e * 31 + Arrays.hashCode(this.f);
   }

   public void g() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::j);
      } else {
         this.j();
      }
   }

   private void j() {
      int i = this.b();

      for (int j = 0; j < this.b.size(); j++) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)this.b.get(j);
         int attributeIndex = vertexformatelement.getAttributeIndex();
         if (attributeIndex >= 0) {
            GlStateManager._enableVertexAttribArray(attributeIndex);
            vertexformatelement.a(attributeIndex, (long)this.a(vertexformatelement), i);
         }
      }
   }

   public void h() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::k);
      } else {
         this.k();
      }
   }

   private void k() {
      for (int i = 0; i < this.b.size(); i++) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)this.b.get(i);
         int attributeIndex = vertexformatelement.getAttributeIndex();
         if (attributeIndex >= 0) {
            GlStateManager._disableVertexAttribArray(attributeIndex);
         }
      }
   }

   public VertexBuffer i() {
      VertexBuffer vertexbuffer = this.g;
      if (vertexbuffer == null) {
         this.g = vertexbuffer = new VertexBuffer(VertexBuffer.a.b);
      }

      return vertexbuffer;
   }

   public int getOffset(int index) {
      return this.f[index];
   }

   public boolean hasPosition() {
      return this.positionElementOffset >= 0;
   }

   public int getPositionOffset() {
      return this.positionElementOffset;
   }

   public boolean hasNormal() {
      return this.normalElementOffset >= 0;
   }

   public int getNormalOffset() {
      return this.normalElementOffset;
   }

   public boolean hasColor() {
      return this.colorElementOffset >= 0;
   }

   public int getColorOffset() {
      return this.colorElementOffset;
   }

   public boolean hasUV(int id) {
      return this.uvOffsetsById.containsKey(id);
   }

   public int getUvOffsetById(int id) {
      return this.uvOffsetsById.get(id);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void copyFrom(VertexFormat vf) {
      this.b = vf.b;
      this.c = vf.c;
      this.d = vf.d;
      this.e = vf.e;
      this.f = vf.f;
      this.g = vf.g;
      this.name = vf.name;
      this.positionElementOffset = vf.positionElementOffset;
      this.normalElementOffset = vf.normalElementOffset;
      this.colorElementOffset = vf.colorElementOffset;
      this.uvOffsetsById = vf.uvOffsetsById;
      this.elementMapping = vf.elementMapping;
      this.extended = vf.extended;
   }

   public VertexFormat duplicate() {
      VertexFormat.a vfb = a();
      vfb.addAll(this);
      return vfb.a();
   }

   public ImmutableMap<String, VertexFormatElement> getElementMapping() {
      return this.elementMapping;
   }

   public int getIntegerSize() {
      return this.b() / 4;
   }

   public boolean isExtended() {
      return this.extended;
   }

   public void setExtended(boolean extended) {
      this.extended = extended;
   }

   public static class a {
      private final Builder<String, VertexFormatElement> a = ImmutableMap.builder();
      private final IntList b = new IntArrayList();
      private int c;

      public VertexFormat.a a(String nameIn, VertexFormatElement elementIn) {
         this.a.put(nameIn, elementIn);
         this.b.add(this.c);
         this.c = this.c + elementIn.b();
         return this;
      }

      public VertexFormat.a a(int sizeIn) {
         this.c += sizeIn;
         return this;
      }

      public VertexFormat a() {
         ImmutableMap<String, VertexFormatElement> immutablemap = this.a.buildOrThrow();
         ImmutableList<VertexFormatElement> immutablelist = immutablemap.values().asList();
         ImmutableList<String> immutablelist1 = immutablemap.keySet().asList();
         return new VertexFormat(immutablelist, immutablelist1, this.b, this.c);
      }

      public VertexFormat.a addAll(VertexFormat vf) {
         for (VertexFormatElement vfe : vf.c()) {
            String name = vf.c(vfe);
            this.a(name, vfe);
         }

         while (this.c < vf.b()) {
            this.a(1);
         }

         return this;
      }
   }

   public static enum b {
      a(5123, 2),
      b(5125, 4);

      public final int c;
      public final int d;

      private b(final int glTypeIn, final int sizeBytesIn) {
         this.c = glTypeIn;
         this.d = sizeBytesIn;
      }

      public static VertexFormat.b a(int indexCountIn) {
         return (indexCountIn & -65536) != 0 ? b : a;
      }
   }

   public static enum c {
      a(4, 2, 2, false),
      b(5, 2, 1, true),
      c(1, 2, 2, false),
      d(3, 2, 1, true),
      e(4, 3, 3, false),
      f(5, 3, 1, true),
      g(6, 3, 1, true),
      h(4, 4, 4, false);

      public final int i;
      public final int j;
      public final int k;
      public final boolean l;

      private c(final int glModeIn, final int lengthIn, final int strideIn, final boolean connectedIn) {
         this.i = glModeIn;
         this.j = lengthIn;
         this.k = strideIn;
         this.l = connectedIn;
      }

      public int a(int vertexCountIn) {
         return switch (this) {
            case a, h -> vertexCountIn / 4 * 6;
            case b, c, d, e, f, g -> vertexCountIn;
            default -> 0;
         };
      }
   }
}
