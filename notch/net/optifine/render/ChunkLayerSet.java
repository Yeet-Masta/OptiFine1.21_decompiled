package net.optifine.render;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.src.C_4168_;

public class ChunkLayerSet implements Set<C_4168_> {
   private boolean[] layers = new boolean[C_4168_.CHUNK_RENDER_TYPES.length];
   private boolean empty = true;

   public boolean add(C_4168_ renderType) {
      this.layers[renderType.ordinal()] = true;
      this.empty = false;
      return false;
   }

   public boolean contains(C_4168_ renderType) {
      return this.layers[renderType.ordinal()];
   }

   public boolean contains(Object obj) {
      return obj instanceof C_4168_ ? this.contains((C_4168_)obj) : false;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public int size() {
      throw new UnsupportedOperationException("Not supported");
   }

   public Iterator<C_4168_> iterator() {
      throw new UnsupportedOperationException("Not supported");
   }

   public Object[] toArray() {
      throw new UnsupportedOperationException("Not supported");
   }

   public <T> T[] toArray(T[] a) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean containsAll(Collection<?> c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean addAll(Collection<? extends C_4168_> c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean retainAll(Collection<?> c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean removeAll(Collection<?> c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public void clear() {
      throw new UnsupportedOperationException("Not supported");
   }
}
