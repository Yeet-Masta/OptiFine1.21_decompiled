package net.optifine.render;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ChunkLayerSet implements Set<net.minecraft.client.renderer.RenderType> {
   private boolean[] layers = new boolean[net.minecraft.client.renderer.RenderType.CHUNK_RENDER_TYPES.length];
   private boolean empty = true;

   public boolean add(net.minecraft.client.renderer.RenderType renderType) {
      this.layers[renderType.ordinal()] = true;
      this.empty = false;
      return false;
   }

   public boolean contains(net.minecraft.client.renderer.RenderType renderType) {
      return this.layers[renderType.ordinal()];
   }

   public boolean contains(Object obj) {
      return obj instanceof net.minecraft.client.renderer.RenderType ? this.contains((net.minecraft.client.renderer.RenderType)obj) : false;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public int size() {
      throw new UnsupportedOperationException("Not supported");
   }

   public Iterator<net.minecraft.client.renderer.RenderType> iterator() {
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

   public boolean addAll(Collection<? extends net.minecraft.client.renderer.RenderType> c) {
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
