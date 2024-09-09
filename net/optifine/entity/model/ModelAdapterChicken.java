package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterChicken extends ModelAdapter {
   public ModelAdapterChicken() {
      super(EntityType.f_20555_, "chicken", 0.3F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ChickenModel(bakeModelLayer(ModelLayers.f_171277_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ChickenModel modelChicken)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 0);
      } else if (modelPart.equals("body")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 1);
      } else if (modelPart.equals("right_leg")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 2);
      } else if (modelPart.equals("left_leg")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 3);
      } else if (modelPart.equals("right_wing")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 4);
      } else if (modelPart.equals("left_wing")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 5);
      } else if (modelPart.equals("bill")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 6);
      } else {
         return modelPart.equals("chin") ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 7) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ChickenRenderer render = new ChickenRenderer(renderManager.getContext());
      render.f_115290_ = (ChickenModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
