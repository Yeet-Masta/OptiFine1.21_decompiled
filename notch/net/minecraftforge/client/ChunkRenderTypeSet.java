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

public class ChunkRenderTypeSet implements Iterable<C_4168_> {
   private static final List<C_4168_> CHUNK_RENDER_TYPES_LIST = C_4168_.m_110506_();
   private static final C_4168_[] CHUNK_RENDER_TYPES = (C_4168_[])CHUNK_RENDER_TYPES_LIST.toArray(new C_4168_[0]);
   private static final ChunkRenderTypeSet NONE = new ChunkRenderTypeSet.None();
   private static final ChunkRenderTypeSet ALL = new ChunkRenderTypeSet.All();
   private final BitSet bits;

   public static ChunkRenderTypeSet none() {
      return NONE;
   }

   public static ChunkRenderTypeSet all() {
      return ALL;
   }

   public static ChunkRenderTypeSet of(C_4168_... renderTypes) {
      return of(Arrays.asList(renderTypes));
   }

   public static ChunkRenderTypeSet of(Collection<C_4168_> renderTypes) {
      return renderTypes.isEmpty() ? none() : of(renderTypes);
   }

   private static ChunkRenderTypeSet of(Iterable<C_4168_> renderTypes) {
      BitSet bits = new BitSet();

      for (C_4168_ renderType : renderTypes) {
         int index = renderType.getChunkLayerId();
         Preconditions.checkArgument(index >= 0, "Attempted to create chunk render type set with a non-chunk render type: " + renderType);
         bits.set(index);
      }

      return new ChunkRenderTypeSet(bits);
   }

   public static ChunkRenderTypeSet union(ChunkRenderTypeSet... sets) {
      return union(Arrays.asList(sets));
   }

   public static ChunkRenderTypeSet union(Collection<ChunkRenderTypeSet> sets) {
      return sets.isEmpty() ? none() : union(sets);
   }

   public static ChunkRenderTypeSet union(Iterable<ChunkRenderTypeSet> sets) {
      BitSet bits = new BitSet();

      for (ChunkRenderTypeSet set : sets) {
         bits.or(set.bits);
      }

      return new ChunkRenderTypeSet(bits);
   }

   public static ChunkRenderTypeSet intersection(ChunkRenderTypeSet... sets) {
      return intersection(Arrays.asList(sets));
   }

   public static ChunkRenderTypeSet intersection(Collection<ChunkRenderTypeSet> sets) {
      return sets.isEmpty() ? all() : intersection(sets);
   }

   public static ChunkRenderTypeSet intersection(Iterable<ChunkRenderTypeSet> sets) {
      BitSet bits = new BitSet();
      bits.set(0, CHUNK_RENDER_TYPES.length);

      for (ChunkRenderTypeSet set : sets) {
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

   public Iterator<C_4168_> iterator() {
      return new ChunkRenderTypeSet.IteratorImpl();
   }

   public List<C_4168_> asList() {
      return ImmutableList.copyOf(this);
   }

   private static final class All extends ChunkRenderTypeSet {
      private All() {
         super(C_5322_.m_137469_(new BitSet(), bits -> bits.set(0, ChunkRenderTypeSet.CHUNK_RENDER_TYPES.length)));
      }

      @Override
      public boolean isEmpty() {
         return false;
      }

      @Override
      public boolean contains(C_4168_ renderType) {
         return renderType.getChunkLayerId() >= 0;
      }

      @Override
      public Iterator<C_4168_> iterator() {
         return ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST.iterator();
      }

      @Override
      public List<C_4168_> asList() {
         return ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST;
      }
   }

   private final class IteratorImpl implements Iterator<C_4168_> {
      private int index = ChunkRenderTypeSet.this.bits.nextSetBit(0);

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

      @Override
      public boolean isEmpty() {
         return true;
      }

      @Override
      public boolean contains(C_4168_ renderType) {
         return false;
      }

      @Override
      public Iterator<C_4168_> iterator() {
         return Collections.emptyIterator();
      }

      @Override
      public List<C_4168_> asList() {
         return List.of();
      }
   }
}
