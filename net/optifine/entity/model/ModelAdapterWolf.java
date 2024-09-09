package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterWolf extends ModelAdapter {
   public ModelAdapterWolf() {
      super(EntityType.f_20499_, "wolf", 0.5F);
   }

   protected ModelAdapterWolf(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public Model makeModel() {
      return new WolfModel(bakeModelLayer(ModelLayers.f_171221_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof WolfModel modelWolf)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 0);
      } else if (modelPart.equals("body")) {
         return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 2);
      } else if (modelPart.equals("leg1")) {
         return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 3);
      } else if (modelPart.equals("leg2")) {
         return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 4);
      } else if (modelPart.equals("leg3")) {
         return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 5);
      } else if (modelPart.equals("leg4")) {
         return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 6);
      } else if (modelPart.equals("tail")) {
         return (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 7);
      } else {
         return modelPart.equals("mane") ? (ModelPart)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 9) : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WolfRenderer render = new WolfRenderer(renderManager.getContext());
      render.f_115290_ = (WolfModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
