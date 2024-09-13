package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RendererCache {
   private Map<String, EntityRenderer> mapEntityRenderers = new HashMap();
   private Map<String, BlockEntityRenderer> mapBlockEntityRenderers = new HashMap();

   public EntityRenderer get(EntityType type, int index, Supplier<EntityRenderer> supplier) {
      String key = BuiltInRegistries.f_256780_.m_7981_(type) + ":" + index;
      EntityRenderer renderer = (EntityRenderer)this.mapEntityRenderers.get(key);
      if (renderer == null) {
         renderer = (EntityRenderer)supplier.get();
         this.mapEntityRenderers.put(key, renderer);
      }

      return renderer;
   }

   public BlockEntityRenderer get(BlockEntityType type, int index, Supplier<BlockEntityRenderer> supplier) {
      String key = BuiltInRegistries.f_257049_.m_7981_(type) + ":" + index;
      BlockEntityRenderer renderer = (BlockEntityRenderer)this.mapBlockEntityRenderers.get(key);
      if (renderer == null) {
         renderer = (BlockEntityRenderer)supplier.get();
         this.mapBlockEntityRenderers.put(key, renderer);
      }

      return renderer;
   }

   public void put(EntityType type, int index, EntityRenderer renderer) {
      String key = BuiltInRegistries.f_256780_.m_7981_(type) + ":" + index;
      this.mapEntityRenderers.put(key, renderer);
   }

   public void put(BlockEntityType type, int index, BlockEntityRenderer renderer) {
      String key = BuiltInRegistries.f_257049_.m_7981_(type) + ":" + index;
      this.mapBlockEntityRenderers.put(key, renderer);
   }

   public void clear() {
      this.mapEntityRenderers.clear();
      this.mapBlockEntityRenderers.clear();
   }
}
