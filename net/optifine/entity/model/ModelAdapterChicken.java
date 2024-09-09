package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterChicken extends ModelAdapter {
   public ModelAdapterChicken() {
      super(EntityType.f_20555_, "chicken", 0.3F);
   }

   public Model makeModel() {
      return new ChickenModel(bakeModelLayer(ModelLayers.f_171277_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof ChickenModel modelChicken)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 0);
      } else if (modelPart.equals("body")) {
         return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 1);
      } else if (modelPart.equals("right_leg")) {
         return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 2);
      } else if (modelPart.equals("left_leg")) {
         return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 3);
      } else if (modelPart.equals("right_wing")) {
         return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 4);
      } else if (modelPart.equals("left_wing")) {
         return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 5);
      } else if (modelPart.equals("bill")) {
         return (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 6);
      } else {
         return modelPart.equals("chin") ? (ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 7) : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ChickenRenderer render = new ChickenRenderer(renderManager.getContext());
      render.f_115290_ = (ChickenModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
