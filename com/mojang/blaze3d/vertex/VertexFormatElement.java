package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public record VertexFormatElement(int f_337730_, int f_86032_, Type f_86030_, Usage f_86031_, int f_86033_, String name, int attributeIndex) {
   public static final int f_336943_ = 32;
   private static final VertexFormatElement[] f_336955_ = new VertexFormatElement[32];
   private static final List f_337011_ = new ArrayList(32);
   public static final VertexFormatElement f_336661_;
   public static final VertexFormatElement f_336914_;
   public static final VertexFormatElement f_336642_;
   public static final VertexFormatElement f_337593_;
   public static final VertexFormatElement f_337543_;
   public static final VertexFormatElement f_337050_;
   public static final VertexFormatElement f_336839_;
   public static final VertexFormatElement PADDING;

   public VertexFormatElement(int id, int index, Type type, Usage usage, int elementCount) {
      this(id, index, type, usage, elementCount, (String)null, -1);
   }

   public VertexFormatElement(int id, int index, Type type, Usage usage, int elementCount, String name, int attributeIndex) {
      if (id >= 0 && id < f_336955_.length) {
         if (!this.m_86042_(index, usage)) {
            throw new IllegalStateException("Multiple vertex elements of the same type other than UVs are not supported");
         } else {
            this.f_337730_ = id;
            this.f_86032_ = index;
            this.f_86030_ = type;
            this.f_86031_ = usage;
            this.f_86033_ = elementCount;
            this.name = name;
            this.attributeIndex = attributeIndex;
         }
      } else {
         throw new IllegalArgumentException("Element ID must be in range [0; " + f_336955_.length + ")");
      }
   }

   public static VertexFormatElement m_340492_(int p_340492_0_, int p_340492_1_, Type p_340492_2_, Usage p_340492_3_, int p_340492_4_) {
      return register(p_340492_0_, p_340492_1_, p_340492_2_, p_340492_3_, p_340492_4_, (String)null, -1);
   }

   public static VertexFormatElement register(int p_340492_0_, int p_340492_1_, Type p_340492_2_, Usage p_340492_3_, int p_340492_4_, String name, int attributeIndex) {
      VertexFormatElement vertexformatelement = new VertexFormatElement(p_340492_0_, p_340492_1_, p_340492_2_, p_340492_3_, p_340492_4_, name, attributeIndex);
      if (f_336955_[p_340492_0_] != null) {
         throw new IllegalArgumentException("Duplicate element registration for: " + p_340492_0_);
      } else {
         f_336955_[p_340492_0_] = vertexformatelement;
         f_337011_.add(vertexformatelement);
         return vertexformatelement;
      }
   }

   private boolean m_86042_(int indexIn, Usage usageIn) {
      return indexIn == 0 || usageIn == VertexFormatElement.Usage.field_48;
   }

   public String toString() {
      if (this.name != null) {
         return this.name;
      } else {
         int var10000 = this.f_86033_;
         return "" + var10000 + "," + String.valueOf(this.f_86031_) + "," + String.valueOf(this.f_86030_) + " (" + this.f_337730_ + ")";
      }
   }

   public int m_339950_() {
      return 1 << this.f_337730_;
   }

   public int m_339527_() {
      return this.f_86030_.m_86074_() * this.f_86033_;
   }

   public void m_166965_(int elementIndexIn, long elementOffsetIn, int sizeIn) {
      this.f_86031_.f_86087_.m_167052_(this.f_86033_, this.f_86030_.m_86076_(), sizeIn, elementOffsetIn, elementIndexIn);
   }

   @Nullable
   public static VertexFormatElement m_340524_(int idIn) {
      return f_336955_[idIn];
   }

   public static Stream m_339640_(int maskIn) {
      return f_337011_.stream().filter((p_340316_1_) -> {
         return p_340316_1_ != null && (maskIn & p_340316_1_.m_339950_()) != 0;
      });
   }

   public final int getElementCount() {
      return this.f_86033_;
   }

   public String getName() {
      return this.name;
   }

   public int getAttributeIndex() {
      return this.attributeIndex;
   }

   public static int getElementsCount() {
      return f_337011_.size();
   }

   public int f_337730_() {
      return this.f_337730_;
   }

   public int f_86032_() {
      return this.f_86032_;
   }

   public Type f_86030_() {
      return this.f_86030_;
   }

   public Usage f_86031_() {
      return this.f_86031_;
   }

   public int f_86033_() {
      return this.f_86033_;
   }

   public String name() {
      return this.name;
   }

   public int attributeIndex() {
      return this.attributeIndex;
   }

   static {
      f_336661_ = register(0, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.POSITION, 3, "POSITION_3F", 0);
      f_336914_ = register(1, 0, VertexFormatElement.Type.UBYTE, VertexFormatElement.Usage.COLOR, 4, "COLOR_4UB", 1);
      f_336642_ = register(2, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.field_48, 2, "TEX_2F", 2);
      f_337593_ = f_336642_;
      f_337543_ = register(3, 1, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.field_48, 2, "TEX_2S", 3);
      f_337050_ = register(4, 2, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.field_48, 2, "TEX_2SB", 4);
      f_336839_ = register(5, 0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.NORMAL, 3, "NORMAL_3B", 5);
      PADDING = register(6, 0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.PADDING, 1, "PADDING_1B", -1);
   }

   public static enum Type {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int f_86063_;
      private final String f_86064_;
      private final int f_86065_;

      private Type(final int sizeIn, final String displayNameIn, final int glConstantIn) {
         this.f_86063_ = sizeIn;
         this.f_86064_ = displayNameIn;
         this.f_86065_ = glConstantIn;
      }

      public int m_86074_() {
         return this.f_86063_;
      }

      public int m_86076_() {
         return this.f_86065_;
      }

      public String toString() {
         return this.f_86064_;
      }

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{FLOAT, UBYTE, BYTE, USHORT, SHORT, UINT, INT};
      }
   }

   public static enum Usage {
      POSITION("Position", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
         GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, offsetIn);
      }),
      NORMAL("Normal", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
         GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, true, strideIn, offsetIn);
      }),
      COLOR("Vertex Color", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
         GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, true, strideIn, offsetIn);
      }),
      // $FF: renamed from: UV com.mojang.blaze3d.vertex.VertexFormatElement$Usage
      field_48("UV", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
         if (typeIn == 5126) {
            GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, offsetIn);
         } else {
            GlStateManager._vertexAttribIPointer(indexIn, sizeIn, typeIn, strideIn, offsetIn);
         }

      }),
      PADDING("Padding", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
      }),
      GENERIC("Generic", (sizeIn, typeIn, strideIn, offsetIn, indexIn) -> {
         GlStateManager._vertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, offsetIn);
      });

      private final String f_86086_;
      final SetupState f_86087_;

      private Usage(final String displayNameIn, final SetupState setupStateIn) {
         this.f_86086_ = displayNameIn;
         this.f_86087_ = setupStateIn;
      }

      public String toString() {
         return this.f_86086_;
      }

      // $FF: synthetic method
      private static Usage[] $values() {
         return new Usage[]{POSITION, NORMAL, COLOR, field_48, PADDING, GENERIC};
      }

      @FunctionalInterface
      interface SetupState {
         void m_167052_(int var1, int var2, int var3, long var4, int var6);
      }
   }
}
