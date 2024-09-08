package net.minecraft.src;

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

public class C_3188_ {
   public static final int f_337347_ = -1;
   private List<C_3189_> f_86012_;
   private List<String> f_337579_;
   private int f_86014_;
   private int f_337518_;
   private int[] f_337288_ = new int[32];
   @Nullable
   private C_3186_ f_231232_;
   private String name;
   private int positionElementOffset = -1;
   private int normalElementOffset = -1;
   private int colorElementOffset = -1;
   private Int2IntMap uvOffsetsById = new Int2IntArrayMap();
   private ImmutableMap<String, C_3189_> elementMapping;
   private boolean extended;

   C_3188_(List<C_3189_> elementsIn, List<String> namesIn, IntList offsetsIn, int vertexSizeIn) {
      this.f_86012_ = elementsIn;
      this.f_337579_ = namesIn;
      this.f_86014_ = vertexSizeIn;
      this.f_337518_ = elementsIn.stream().mapToInt(C_3189_::m_339950_).reduce(0, (val1, val2) -> val1 | val2);
      Map<String, C_3189_> mapElements = new HashMap();

      for (int i = 0; i < this.f_337579_.size(); i++) {
         String name = (String)this.f_337579_.get(i);
         C_3189_ element = (C_3189_)this.f_86012_.get(i);
         mapElements.put(name, element);
      }

      this.elementMapping = ImmutableMap.copyOf(mapElements);

      for (int i = 0; i < this.f_337288_.length; i++) {
         C_3189_ vertexformatelement = C_3189_.m_340524_(i);
         int j = vertexformatelement != null ? elementsIn.indexOf(vertexformatelement) : -1;
         this.f_337288_[i] = j != -1 ? offsetsIn.getInt(j) : -1;
         if (vertexformatelement != null) {
            C_3189_.C_3191_ usage = vertexformatelement.f_86031_();
            int offset = this.f_337288_[i];
            if (usage == C_3189_.C_3191_.POSITION) {
               this.positionElementOffset = offset;
            } else if (usage == C_3189_.C_3191_.NORMAL) {
               this.normalElementOffset = offset;
            } else if (usage == C_3189_.C_3191_.COLOR) {
               this.colorElementOffset = offset;
            } else if (usage == C_3189_.C_3191_.UV) {
               this.uvOffsetsById.put(vertexformatelement.f_86032_(), offset);
            }
         }
      }
   }

   public static C_3188_.C_336503_ m_339703_() {
      return new C_3188_.C_336503_();
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("Vertex format: " + this.name + " (").append(this.f_86014_).append(" bytes):\n");

      for (int i = 0; i < this.f_86012_.size(); i++) {
         C_3189_ vertexformatelement = (C_3189_)this.f_86012_.get(i);
         stringbuilder.append(i)
            .append(". ")
            .append((String)this.f_337579_.get(i))
            .append(": ")
            .append(vertexformatelement)
            .append(" @ ")
            .append(this.m_338798_(vertexformatelement))
            .append('\n');
      }

      return stringbuilder.toString();
   }

   public int m_86020_() {
      return this.f_86014_;
   }

   public List<C_3189_> m_86023_() {
      return this.f_86012_;
   }

   public List<String> m_166911_() {
      return this.f_337579_;
   }

   public int[] m_338562_() {
      return this.f_337288_;
   }

   public int m_338798_(C_3189_ elementIn) {
      return this.f_337288_[elementIn.f_337730_()];
   }

   public boolean m_339292_(C_3189_ elementIn) {
      return (this.f_337518_ & elementIn.m_339950_()) != 0;
   }

   public int m_340128_() {
      return this.f_337518_;
   }

   public String m_340604_(C_3189_ elementIn) {
      int i = this.f_86012_.indexOf(elementIn);
      if (i == -1) {
         throw new IllegalArgumentException(elementIn + " is not contained in format");
      } else {
         return (String)this.f_337579_.get(i);
      }
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof C_3188_ vertexformat
            && this.f_337518_ == vertexformat.f_337518_
            && this.f_86014_ == vertexformat.f_86014_
            && this.f_337579_.equals(vertexformat.f_337579_)
            && Arrays.equals(this.f_337288_, vertexformat.f_337288_)) {
            return true;
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

      for (int j = 0; j < this.f_86012_.size(); j++) {
         C_3189_ vertexformatelement = (C_3189_)this.f_86012_.get(j);
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
      for (int i = 0; i < this.f_86012_.size(); i++) {
         C_3189_ vertexformatelement = (C_3189_)this.f_86012_.get(i);
         int attributeIndex = vertexformatelement.getAttributeIndex();
         if (attributeIndex >= 0) {
            GlStateManager._disableVertexAttribArray(attributeIndex);
         }
      }
   }

   public C_3186_ m_231233_() {
      C_3186_ vertexbuffer = this.f_231232_;
      if (vertexbuffer == null) {
         this.f_231232_ = vertexbuffer = new C_3186_(C_3186_.C_285533_.DYNAMIC);
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

   public void copyFrom(C_3188_ vf) {
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

   public C_3188_ duplicate() {
      C_3188_.C_336503_ vfb = m_339703_();
      vfb.addAll(this);
      return vfb.m_339368_();
   }

   public ImmutableMap<String, C_3189_> getElementMapping() {
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

   public static enum C_141548_ {
      SHORT(5123, 2),
      INT(5125, 4);

      public final int f_166923_;
      public final int f_166924_;

      private C_141548_(final int glTypeIn, final int sizeBytesIn) {
         this.f_166923_ = glTypeIn;
         this.f_166924_ = sizeBytesIn;
      }

      public static C_3188_.C_141548_ m_166933_(int indexCountIn) {
         return (indexCountIn & -65536) != 0 ? INT : SHORT;
      }
   }

   public static enum C_141549_ {
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

      private C_141549_(final int glModeIn, final int lengthIn, final int strideIn, final boolean connectedIn) {
         this.f_166946_ = glModeIn;
         this.f_166947_ = lengthIn;
         this.f_166948_ = strideIn;
         this.f_231234_ = connectedIn;
      }

      public int m_166958_(int vertexCountIn) {
         return switch (this) {
            case LINES, QUADS -> vertexCountIn / 4 * 6;
            case LINE_STRIP, DEBUG_LINES, DEBUG_LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, TRIANGLE_FAN -> vertexCountIn;
            default -> 0;
         };
      }
   }

   public static class C_336503_ {
      private final Builder<String, C_3189_> f_337231_ = ImmutableMap.builder();
      private final IntList f_337307_ = new IntArrayList();
      private int f_336835_;

      public C_3188_.C_336503_ m_339091_(String nameIn, C_3189_ elementIn) {
         this.f_337231_.put(nameIn, elementIn);
         this.f_337307_.add(this.f_336835_);
         this.f_336835_ = this.f_336835_ + elementIn.m_339527_();
         return this;
      }

      public C_3188_.C_336503_ m_339010_(int sizeIn) {
         this.f_336835_ += sizeIn;
         return this;
      }

      public C_3188_ m_339368_() {
         ImmutableMap<String, C_3189_> immutablemap = this.f_337231_.buildOrThrow();
         ImmutableList<C_3189_> immutablelist = immutablemap.values().asList();
         ImmutableList<String> immutablelist1 = immutablemap.keySet().asList();
         return new C_3188_(immutablelist, immutablelist1, this.f_337307_, this.f_336835_);
      }

      public C_3188_.C_336503_ addAll(C_3188_ vf) {
         for (C_3189_ vfe : vf.m_86023_()) {
            String name = vf.m_340604_(vfe);
            this.m_339091_(name, vfe);
         }

         while (this.f_336835_ < vf.m_86020_()) {
            this.m_339010_(1);
         }

         return this;
      }
   }
}
