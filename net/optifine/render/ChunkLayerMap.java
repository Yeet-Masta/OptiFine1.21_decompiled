package net.optifine.render;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;

public class ChunkLayerMap {
   private Object[] values;
   private Supplier defaultValue;

   public ChunkLayerMap(Function initialValue) {
      this.values = new Object[RenderType.CHUNK_RENDER_TYPES.length];
      RenderType[] renderTypes = RenderType.CHUNK_RENDER_TYPES;
      this.values = new Object[renderTypes.length];

      int i;
      for(i = 0; i < renderTypes.length; ++i) {
         RenderType renderType = renderTypes[i];
         Object t = initialValue.apply(renderType);
         this.values[renderType.ordinal()] = t;
      }

      for(i = 0; i < this.values.length; ++i) {
         if (this.values[i] == null) {
            throw new RuntimeException("Missing value at index: " + i);
         }
      }

   }

   public Object get(RenderType layer) {
      return this.values[layer.ordinal()];
   }

   public Collection values() {
      return Arrays.asList(this.values);
   }
}
