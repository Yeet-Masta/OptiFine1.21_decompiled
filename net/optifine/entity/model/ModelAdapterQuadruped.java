package net.optifine.entity.model;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public abstract class ModelAdapterQuadruped extends ModelAdapter {
   public ModelAdapterQuadruped(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof QuadrupedModel modelQuadruped)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 0);
      } else if (modelPart.equals("body")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 1);
      } else if (modelPart.equals("leg1")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 2);
      } else if (modelPart.equals("leg2")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 3);
      } else if (modelPart.equals("leg3")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 4);
      } else {
         return modelPart.equals("leg4")
            ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 5)
            : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4"};
   }
}
