package net.minecraftforge.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_5322_;

public class ChunkRenderTypeSet implements Iterable {
   private static final List CHUNK_RENDER_TYPES_LIST = C_4168_.m_110506_();
   private static final C_4168_[] CHUNK_RENDER_TYPES;
   private static final ChunkRenderTypeSet NONE;
   private static final ChunkRenderTypeSet ALL;
   private final BitSet bits;

   public static ChunkRenderTypeSet none() {
      return NONE;
   }

   public static ChunkRenderTypeSet all() {
      return ALL;
   }

   // $FF: renamed from: of (net.minecraft.src.C_4168_[]) net.minecraftforge.client.ChunkRenderTypeSet
   public static ChunkRenderTypeSet method_6(C_4168_... renderTypes) {
      return method_7(Arrays.asList(renderTypes));
   }

   // $FF: renamed from: of (java.util.Collection) net.minecraftforge.client.ChunkRenderTypeSet
   public static ChunkRenderTypeSet method_7(Collection renderTypes) {
      return renderTypes.isEmpty() ? none() : method_8(renderTypes);
   }

   // $FF: renamed from: of (java.lang.Iterable) net.minecraftforge.client.ChunkRenderTypeSet
   private static ChunkRenderTypeSet method_8(Iterable renderTypes) {
      BitSet bits = new BitSet();
      Iterator var2 = renderTypes.iterator();

      while(var2.hasNext()) {
         C_4168_ renderType = (C_4168_)var2.next();
         int index = renderType.getChunkLayerId();
         Preconditions.checkArgument(index >= 0, "Attempted to create chunk render type set with a non-chunk render type: " + String.valueOf(renderType));
         bits.set(index);
      }

      return new ChunkRenderTypeSet(bits);
   }

   public static ChunkRenderTypeSet union(ChunkRenderTypeSet... sets) {
      return union((Collection)Arrays.asList(sets));
   }

   public static ChunkRenderTypeSet union(Collection sets) {
      return sets.isEmpty() ? none() : union((Iterable)sets);
   }

   public static ChunkRenderTypeSet union(Iterable sets) {
      BitSet bits = new BitSet();
      Iterator var2 = sets.iterator();

      while(var2.hasNext()) {
         ChunkRenderTypeSet set = (ChunkRenderTypeSet)var2.next();
         bits.or(set.bits);
      }

      return new ChunkRenderTypeSet(bits);
   }

   public static ChunkRenderTypeSet intersection(ChunkRenderTypeSet... sets) {
      return intersection((Collection)Arrays.asList(sets));
   }

   public static ChunkRenderTypeSet intersection(Collection sets) {
      return sets.isEmpty() ? all() : intersection((Iterable)sets);
   }

   public static ChunkRenderTypeSet intersection(Iterable sets) {
      BitSet bits = new BitSet();
      bits.set(0, CHUNK_RENDER_TYPES.length);
      Iterator var2 = sets.iterator();

      while(var2.hasNext()) {
         ChunkRenderTypeSet set = (ChunkRenderTypeSet)var2.next();
         bits.and(set.bits);
      }

      return new ChunkRenderTypeSet(bits);
   }

   private ChunkRenderTypeSet(BitSet bits) {
      this.bits = bits;
   }

   public boolean isEmpty() {
      return this.bits.isEmpty();
   }

   public boolean contains(C_4168_ renderType) {
      int id = renderType.getChunkLayerId();
      return id >= 0 && this.bits.get(id);
   }

   public Iterator iterator() {
      return new IteratorImpl();
   }

   public List asList() {
      return ImmutableList.copyOf(this);
   }

   static {
      CHUNK_RENDER_TYPES = (C_4168_[])CHUNK_RENDER_TYPES_LIST.toArray(new C_4168_[0]);
      NONE = new None();
      ALL = new All();
   }

   private final class IteratorImpl implements Iterator {
      private int index;

      private IteratorImpl() {
         this.index = ChunkRenderTypeSet.this.bits.nextSetBit(0);
      }

      public boolean hasNext() {
         return this.index >= 0;
      }

      public C_4168_ next() {
         C_4168_ renderType = ChunkRenderTypeSet.CHUNK_RENDER_TYPES[this.index];
         this.index = ChunkRenderTypeSet.this.bits.nextSetBit(this.index + 1);
         return renderType;
      }
   }

   private static final class None extends ChunkRenderTypeSet {
      private None() {
         super(new BitSet());
      }

      public boolean isEmpty() {
         return true;
      }

      public boolean contains(C_4168_ renderType) {
         return false;
      }

      public Iterator iterator() {
         return Collections.emptyIterator();
      }

      public List asList() {
         return List.of();
      }
   }

   private static final class All extends ChunkRenderTypeSet {
      private All() {
         super((BitSet)C_5322_.m_137469_(new BitSet(), (bits) -> {
            bits.set(0, ChunkRenderTypeSet.CHUNK_RENDER_TYPES.length);
         }));
      }

      public boolean isEmpty() {
         return false;
      }

      public boolean contains(C_4168_ renderType) {
         return renderType.getChunkLayerId() >= 0;
      }

      public Iterator iterator() {
         return ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST.iterator();
      }

      public List asList() {
         return ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST;
      }
   }
}
