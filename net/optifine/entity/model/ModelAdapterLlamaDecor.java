package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
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
   public Model makeModel() {
      return new LlamaModel(bakeModelLayer(ModelLayers.f_171195_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      LlamaRenderer customRenderer = new LlamaRenderer(renderManager.getContext(), ModelLayers.f_171195_);
      customRenderer.f_115290_ = new LlamaModel(bakeModelLayer(ModelLayers.f_171195_));
      customRenderer.f_114477_ = 0.7F;
      EntityType entityType = (EntityType)this.getType().getLeft().get();
      EntityRenderer render = rendererCache.get(entityType, index, () -> customRenderer);
      if (!(render instanceof LlamaRenderer renderLlama)) {
         Config.warn("Not a RenderLlama: " + render);
         return null;
      } else {
         LlamaDecorLayer layer = new LlamaDecorLayer(renderLlama, renderManager.getContext().m_174027_());
         if (!Reflector.LayerLlamaDecor_model.exists()) {
            Config.warn("Field not found: LayerLlamaDecor.model");
            return null;
         } else {
            Reflector.LayerLlamaDecor_model.setValue(layer, modelBase);
            renderLlama.removeLayers(LlamaDecorLayer.class);
            renderLlama.m_115326_(layer);
            return renderLlama;
         }
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      LlamaRenderer llamaRenderer = (LlamaRenderer)er;

      for (RenderLayer layer : llamaRenderer.getLayers(LlamaDecorLayer.class)) {
         Model model = (Model)Reflector.LayerLlamaDecor_model.getValue(layer);
         if (model != null) {
            model.locationTextureCustom = textureLocation;
         }
      }

      return true;
   }
}
