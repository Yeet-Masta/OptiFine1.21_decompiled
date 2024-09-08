package net.optifine.render;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.src.C_4168_;

public class ChunkLayerMap<T> {
   private T[] values = (T[])(new Object[C_4168_.CHUNK_RENDER_TYPES.length]);
   private Supplier<T> defaultValue;

   public ChunkLayerMap(Function<C_4168_, T> initialValue) {
      C_4168_[] renderTypes = C_4168_.CHUNK_RENDER_TYPES;
      this.values = (T[])(new Object[renderTypes.length]);

      for (int i = 0; i < renderTypes.length; i++) {
         C_4168_ renderType = renderTypes[i];
         T t = (T)initialValue.apply(renderType);
         this.values[renderType.ordinal()] = t;
      }

      for (int i = 0; i < this.values.length; i++) {
         if (this.values[i] == null) {
            throw new RuntimeException("Missing value at index: " + i);
         }
      }
   }

   public T get(C_4168_ layer) {
      return this.values[layer.ordinal()];
   }

   public Collection<T> values() {
      return Arrays.asList(this.values);
   }
}
