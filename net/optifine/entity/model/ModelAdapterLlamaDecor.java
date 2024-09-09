package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaDecor extends ModelAdapterLlama {
   public ModelAdapterLlamaDecor() {
      super(EntityType.f_20466_, "llama_decor", 0.7F);
   }

   protected ModelAdapterLlamaDecor(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new LlamaModel(bakeModelLayer(ModelLayers.f_171195_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      LlamaRenderer customRenderer = new LlamaRenderer(renderManager.getContext(), ModelLayers.f_171195_);
      customRenderer.f_115290_ = new LlamaModel(bakeModelLayer(ModelLayers.f_171195_));
      customRenderer.f_114477_ = 0.7F;
      EntityType entityType = (EntityType)this.getType().getLeft().get();
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(entityType, index, () -> customRenderer);
      if (!(render instanceof LlamaRenderer renderLlama)) {
         Config.warn("Not a RenderLlama: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.LlamaDecorLayer layer = new net.minecraft.client.renderer.entity.layers.LlamaDecorLayer(
            renderLlama, renderManager.getContext().m_174027_()
         );
         if (!Reflector.LayerLlamaDecor_model.exists()) {
            Config.warn("Field not found: LayerLlamaDecor.model");
            return null;
         } else {
            Reflector.LayerLlamaDecor_model.setValue(layer, modelBase);
            renderLlama.removeLayers(net.minecraft.client.renderer.entity.layers.LlamaDecorLayer.class);
            renderLlama.m_115326_(layer);
            return renderLlama;
         }
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      LlamaRenderer llamaRenderer = (LlamaRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.RenderLayer layer : llamaRenderer.getLayers(
         net.minecraft.client.renderer.entity.layers.LlamaDecorLayer.class
      )) {
         net.minecraft.client.model.Model model = (net.minecraft.client.model.Model)Reflector.LayerLlamaDecor_model.getValue(layer);
         if (model != null) {
            model.locationTextureCustom = textureLocation;
         }
      }

      return true;
   }
}
