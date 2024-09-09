package net.optifine.render;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.renderer.RenderType;

public class ChunkLayerSet implements Set {
   private boolean[] layers;
   private boolean empty;

   public ChunkLayerSet() {
      this.layers = new boolean[RenderType.CHUNK_RENDER_TYPES.length];
      this.empty = true;
   }

   public boolean add(RenderType renderType) {
      this.layers[renderType.ordinal()] = true;
      this.empty = false;
      return false;
   }

   public boolean contains(RenderType renderType) {
      return this.layers[renderType.ordinal()];
   }

   public boolean contains(Object obj) {
      return obj instanceof RenderType ? this.contains((RenderType)obj) : false;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public int size() {
      throw new UnsupportedOperationException("Not supported");
   }

   public Iterator iterator() {
      throw new UnsupportedOperationException("Not supported");
   }

   public Object[] toArray() {
      throw new UnsupportedOperationException("Not supported");
   }

   public Object[] toArray(Object[] a) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean containsAll(Collection c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException("Not supported");
   }

   public void clear() {
      throw new UnsupportedOperationException("Not supported");
   }
}
