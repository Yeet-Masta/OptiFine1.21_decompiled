package net.optifine.render;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class ChunkLayerMap<T> {
   private T[] values = (T[])(new Object[net.minecraft.client.renderer.RenderType.CHUNK_RENDER_TYPES.length]);
   private Supplier<T> defaultValue;

   public ChunkLayerMap(Function<net.minecraft.client.renderer.RenderType, T> initialValue) {
      net.minecraft.client.renderer.RenderType[] renderTypes = net.minecraft.client.renderer.RenderType.CHUNK_RENDER_TYPES;
      this.values = (T[])(new Object[renderTypes.length]);

      for (int i = 0; i < renderTypes.length; i++) {
         net.minecraft.client.renderer.RenderType renderType = renderTypes[i];
         T t = (T)initialValue.apply(renderType);
         this.values[renderType.ordinal()] = t;
      }

      for (int i = 0; i < this.values.length; i++) {
         if (this.values[i] == null) {
            throw new RuntimeException("Missing value at index: " + i);
         }
      }
   }

   public T get(net.minecraft.client.renderer.RenderType layer) {
      return this.values[layer.ordinal()];
   }

   public Collection<T> values() {
      return Arrays.asList(this.values);
   }
}
