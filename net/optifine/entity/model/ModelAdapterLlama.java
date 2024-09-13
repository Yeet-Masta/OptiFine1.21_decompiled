package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlama extends ModelAdapter {
   public ModelAdapterLlama() {
      super(EntityType.f_20466_, "llama", 0.7F);
   }

   public ModelAdapterLlama(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public Model makeModel() {
      return new LlamaModel(bakeModelLayer(ModelLayers.f_171194_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof LlamaModel modelLlama)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 0);
      } else if (modelPart.equals("body")) {
         return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 1);
      } else if (modelPart.equals("leg1")) {
         return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 2);
      } else if (modelPart.equals("leg2")) {
         return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 3);
      } else if (modelPart.equals("leg3")) {
         return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 4);
      } else if (modelPart.equals("leg4")) {
         return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 5);
      } else if (modelPart.equals("chest_right")) {
         return (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 6);
      } else {
         return modelPart.equals("chest_left") ? (ModelPart)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 7) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "chest_right", "chest_left"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      LlamaRenderer render = new LlamaRenderer(renderManager.getContext(), ModelLayers.f_171194_);
      render.f_115290_ = (LlamaModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
