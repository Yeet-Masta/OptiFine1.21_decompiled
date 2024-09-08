package net.optifine.entity.model;

import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3858_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public abstract class ModelAdapterQuadruped extends ModelAdapter {
   public ModelAdapterQuadruped(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3858_ modelQuadruped)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 0);
      } else if (modelPart.equals("body")) {
         return (C_3889_)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 1);
      } else if (modelPart.equals("leg1")) {
         return (C_3889_)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 2);
      } else if (modelPart.equals("leg2")) {
         return (C_3889_)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 3);
      } else if (modelPart.equals("leg3")) {
         return (C_3889_)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 4);
      } else {
         return modelPart.equals("leg4") ? (C_3889_)Reflector.ModelQuadruped_ModelRenderers.getValue(modelQuadruped, 5) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4"};
   }
}
