package net.optifine.entity.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EntityType;

public abstract class ModelAdapterBiped extends ModelAdapter {
   public ModelAdapterBiped(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof HumanoidModel modelBiped)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelBiped.f_102808_;
      } else if (modelPart.equals("headwear")) {
         return modelBiped.f_102809_;
      } else if (modelPart.equals("body")) {
         return modelBiped.f_102810_;
      } else if (modelPart.equals("left_arm")) {
         return modelBiped.f_102812_;
      } else if (modelPart.equals("right_arm")) {
         return modelBiped.f_102811_;
      } else if (modelPart.equals("left_leg")) {
         return modelBiped.f_102814_;
      } else {
         return modelPart.equals("right_leg") ? modelBiped.f_102813_ : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "headwear", "body", "left_arm", "right_arm", "left_leg", "right_leg"};
   }
}
