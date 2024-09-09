package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RendererCache {
   private Map<String, net.minecraft.client.renderer.entity.EntityRenderer> mapEntityRenderers = new HashMap();
   private Map<String, net.minecraft.client.renderer.blockentity.BlockEntityRenderer> mapBlockEntityRenderers = new HashMap();

   public net.minecraft.client.renderer.entity.EntityRenderer get(
      EntityType type, int index, Supplier<net.minecraft.client.renderer.entity.EntityRenderer> supplier
   ) {
      String key = BuiltInRegistries.f_256780_.m_7981_(type) + ":" + index;
      net.minecraft.client.renderer.entity.EntityRenderer renderer = (net.minecraft.client.renderer.entity.EntityRenderer)this.mapEntityRenderers.get(key);
      if (renderer == null) {
         renderer = (net.minecraft.client.renderer.entity.EntityRenderer)supplier.get();
         this.mapEntityRenderers.put(key, renderer);
      }

      return renderer;
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer get(
      BlockEntityType type, int index, Supplier<net.minecraft.client.renderer.blockentity.BlockEntityRenderer> supplier
   ) {
      String key = BuiltInRegistries.f_257049_.m_7981_(type) + ":" + index;
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = (net.minecraft.client.renderer.blockentity.BlockEntityRenderer)this.mapBlockEntityRenderers
         .get(key);
      if (renderer == null) {
         renderer = (net.minecraft.client.renderer.blockentity.BlockEntityRenderer)supplier.get();
         this.mapBlockEntityRenderers.put(key, renderer);
      }

      return renderer;
   }

   public void put(EntityType type, int index, net.minecraft.client.renderer.entity.EntityRenderer renderer) {
      String key = BuiltInRegistries.f_256780_.m_7981_(type) + ":" + index;
      this.mapEntityRenderers.put(key, renderer);
   }

   public void put(BlockEntityType type, int index, net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      String key = BuiltInRegistries.f_257049_.m_7981_(type) + ":" + index;
      this.mapBlockEntityRenderers.put(key, renderer);
   }

   public void clear() {
      this.mapEntityRenderers.clear();
      this.mapBlockEntityRenderers.clear();
   }
}
