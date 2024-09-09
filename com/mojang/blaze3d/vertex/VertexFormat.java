package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class VertexFormat {
   public static final int f_337347_ = -1;
   private List f_86012_;
   private List f_337579_;
   private int f_86014_;
   private int f_337518_;
   private int[] f_337288_ = new int[32];
   @Nullable
   private VertexBuffer f_231232_;
   private String name;
   private int positionElementOffset = -1;
   private int normalElementOffset = -1;
   private int colorElementOffset = -1;
   private Int2IntMap uvOffsetsById = new Int2IntArrayMap();
   private ImmutableMap elementMapping;
   private boolean extended;

   VertexFormat(List elementsIn, List namesIn, IntList offsetsIn, int vertexSizeIn) {
      this.f_86012_ = elementsIn;
      this.f_337579_ = namesIn;
      this.f_86014_ = vertexSizeIn;
      this.f_337518_ = elementsIn.stream().mapToInt(VertexFormatElement::m_339950_).reduce(0, (val1, val2) -> {
         return val1 | val2;
      });
      Map mapElements = new HashMap();

      int i;
      for(i = 0; i < this.f_337579_.size(); ++i) {
         String name = (String)this.f_337579_.get(i);
         VertexFormatElement element = (VertexFormatElement)this.f_86012_.get(i);
         mapElements.put(name, element);
      }

      this.elementMapping = ImmutableMap.copyOf(mapElements);

      for(i = 0; i < this.f_337288_.length; ++i) {
         VertexFormatElement vertexformatelement = VertexFormatElement.m_340524_(i);
         int j = vertexformatelement != null ? elementsIn.indexOf(vertexformatelement) : -1;
         this.f_337288_[i] = j != -1 ? offsetsIn.getInt(j) : -1;
         if (vertexformatelement != null) {
            VertexFormatElement.Usage usage = vertexformatelement.f_86031_();
            int offset = this.f_337288_[i];
            if (usage == VertexFormatElement.Usage.POSITION) {
               this.positionElementOffset = offset;
            } else if (usage == VertexFormatElement.Usage.NORMAL) {
               this.normalElementOffset = offset;
            } else if (usage == VertexFormatElement.Usage.COLOR) {
               this.colorElementOffset = offset;
            } else if (usage == VertexFormatElement.Usage.field_48) {
               this.uvOffsetsById.put(vertexformatelement.f_86032_(), offset);
            }
         }
      }

   }

   public static Builder m_339703_() {
      return new Builder();
   }

   public String toString() {
      StringBuilder stringbuilder = (new StringBuilder("Vertex format: " + this.name + " (")).append(this.f_86014_).append(" bytes):\n");

      for(int i = 0; i < this.f_86012_.size(); ++i) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)this.f_86012_.get(i);
         stringbuilder.append(i).append(". ").append((String)this.f_337579_.get(i)).append(": ").append(vertexformatelement).append(" @ ").append(this.m_338798_(vertexformatelement)).append('\n');
      }

      return stringbuilder.toString();
   }

   public int m_86020_() {
      return this.f_86014_;
   }

   public List m_86023_() {
      return this.f_86012_;
   }

   public List m_166911_() {
      return this.f_337579_;
   }

   public int[] m_338562_() {
      return this.f_337288_;
   }

   public int m_338798_(VertexFormatElement elementIn) {
      return this.f_337288_[elementIn.f_337730_()];
   }

   public boolean m_339292_(VertexFormatElement elementIn) {
      return (this.f_337518_ & elementIn.m_339950_()) != 0;
   }

   public int m_340128_() {
      return this.f_337518_;
   }

   public String m_340604_(VertexFormatElement elementIn) {
      int i = this.f_86012_.indexOf(elementIn);
      if (i == -1) {
         throw new IllegalArgumentException(String.valueOf(elementIn) + " is not contained in format");
      } else {
         return (String)this.f_337579_.get(i);
      }
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof VertexFormat) {
            VertexFormat vertexformat = (VertexFormat)p_equals_1_;
            if (this.f_337518_ == vertexformat.f_337518_ && this.f_86014_ == vertexformat.f_86014_ && this.f_337579_.equals(vertexformat.f_337579_) && Arrays.equals(this.f_337288_, vertexformat.f_337288_)) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return this.f_337518_ * 31 + Arrays.hashCode(this.f_337288_);
   }

   public void m_166912_() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::m_166916_);
      } else {
         this.m_166916_();
      }

   }

   private void m_166916_() {
      int i = this.m_86020_();

      for(int j = 0; j < this.f_86012_.size(); ++j) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)this.f_86012_.get(j);
         int attributeIndex = vertexformatelement.getAttributeIndex();
         if (attributeIndex >= 0) {
            GlStateManager._enableVertexAttribArray(attributeIndex);
            vertexformatelement.m_166965_(attributeIndex, (long)this.m_338798_(vertexformatelement), i);
         }
      }

   }

   public void m_86024_() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::m_166917_);
      } else {
         this.m_166917_();
      }

   }

   private void m_166917_() {
      for(int i = 0; i < this.f_86012_.size(); ++i) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)this.f_86012_.get(i);
         int attributeIndex = vertexformatelement.getAttributeIndex();
         if (attributeIndex >= 0) {
            GlStateManager._disableVertexAttribArray(attributeIndex);
         }
      }

   }

   public VertexBuffer m_231233_() {
      VertexBuffer vertexbuffer = this.f_231232_;
      if (vertexbuffer == null) {
         this.f_231232_ = vertexbuffer = new VertexBuffer(VertexBuffer.Usage.DYNAMIC);
      }

      return vertexbuffer;
   }

   public int getOffset(int index) {
      return this.f_337288_[index];
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
      this.f_86012_ = vf.f_86012_;
      this.f_337579_ = vf.f_337579_;
      this.f_86014_ = vf.f_86014_;
      this.f_337518_ = vf.f_337518_;
      this.f_337288_ = vf.f_337288_;
      this.f_231232_ = vf.f_231232_;
      this.name = vf.name;
      this.positionElementOffset = vf.positionElementOffset;
      this.normalElementOffset = vf.normalElementOffset;
      this.colorElementOffset = vf.colorElementOffset;
      this.uvOffsetsById = vf.uvOffsetsById;
      this.elementMapping = vf.elementMapping;
      this.extended = vf.extended;
   }

   public VertexFormat duplicate() {
      Builder vfb = m_339703_();
      vfb.addAll(this);
      VertexFormat vf = vfb.m_339368_();
      return vf;
   }

   public ImmutableMap getElementMapping() {
      return this.elementMapping;
   }

   public int getIntegerSize() {
      return this.m_86020_() / 4;
   }

   public boolean isExtended() {
      return this.extended;
   }

   public void setExtended(boolean extended) {
      this.extended = extended;
   }

   public static class Builder {
      private final ImmutableMap.Builder f_337231_ = ImmutableMap.builder();
      private final IntList f_337307_ = new IntArrayList();
      private int f_336835_;

      public Builder m_339091_(String nameIn, VertexFormatElement elementIn) {
         this.f_337231_.put(nameIn, elementIn);
         this.f_337307_.add(this.f_336835_);
         this.f_336835_ += elementIn.m_339527_();
         return this;
      }

      public Builder m_339010_(int sizeIn) {
         this.f_336835_ += sizeIn;
         return this;
      }

      public VertexFormat m_339368_() {
         ImmutableMap immutablemap = this.f_337231_.buildOrThrow();
         ImmutableList immutablelist = immutablemap.values().asList();
         ImmutableList immutablelist1 = immutablemap.keySet().asList();
         return new VertexFormat(immutablelist, immutablelist1, this.f_337307_, this.f_336835_);
      }

      public Builder addAll(VertexFormat vf) {
         List elements = vf.m_86023_();
         Iterator var3 = elements.iterator();

         while(var3.hasNext()) {
            VertexFormatElement vfe = (VertexFormatElement)var3.next();
            String name = vf.m_340604_(vfe);
            this.m_339091_(name, vfe);
         }

         while(this.f_336835_ < vf.m_86020_()) {
            this.m_339010_(1);
         }

         return this;
      }
   }

   public static enum Mode {
      LINES(4, 2, 2, false),
      LINE_STRIP(5, 2, 1, true),
      DEBUG_LINES(1, 2, 2, false),
      DEBUG_LINE_STRIP(3, 2, 1, true),
      TRIANGLES(4, 3, 3, false),
      TRIANGLE_STRIP(5, 3, 1, true),
      TRIANGLE_FAN(6, 3, 1, true),
      QUADS(4, 4, 4, false);

      public final int f_166946_;
      public final int f_166947_;
      public final int f_166948_;
      public final boolean f_231234_;

      private Mode(final int glModeIn, final int lengthIn, final int strideIn, final boolean connectedIn) {
         this.f_166946_ = glModeIn;
         this.f_166947_ = lengthIn;
         this.f_166948_ = strideIn;
         this.f_231234_ = connectedIn;
      }

      public int m_166958_(int vertexCountIn) {
         int var10000;
         switch (this.ordinal()) {
            case 0:
            case 7:
               var10000 = vertexCountIn / 4 * 6;
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
               var10000 = vertexCountIn;
               break;
            default:
               var10000 = 0;
         }

         return var10000;
      }

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{LINES, LINE_STRIP, DEBUG_LINES, DEBUG_LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, TRIANGLE_FAN, QUADS};
      }
   }

   public static enum IndexType {
      SHORT(5123, 2),
      INT(5125, 4);

      public final int f_166923_;
      public final int f_166924_;

      private IndexType(final int glTypeIn, final int sizeBytesIn) {
         this.f_166923_ = glTypeIn;
         this.f_166924_ = sizeBytesIn;
      }

      public static IndexType m_166933_(int indexCountIn) {
         return (indexCountIn & -65536) != 0 ? INT : SHORT;
      }

      // $FF: synthetic method
      private static IndexType[] $values() {
         return new IndexType[]{SHORT, INT};
      }
   }
}
