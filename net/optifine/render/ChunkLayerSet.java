package net.optifine.render;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.renderer.RenderType;

public class ChunkLayerSet implements Set<RenderType> {
   private boolean[] layers = new boolean[RenderType.CHUNK_RENDER_TYPES.length];
   private boolean empty = true;

   public boolean add(RenderType renderType) {
      this.layers[renderType.ordinal()] = true;
      this.empty = false;
      return false;
   }

   public boolean m_274455_(RenderType renderType) {
      return this.layers[renderType.ordinal()];
   }

   public boolean m_274455_(Object obj) {
      return obj instanceof RenderType ? this.m_274455_((RenderType)obj) : false;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public int size() {
      throw new UnsupportedOperationException("Not supported");
   }

   public Iterator<RenderType> iterator() {
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

   public boolean addAll(Collection<? extends RenderType> c) {
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
