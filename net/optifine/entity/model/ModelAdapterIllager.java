package net.optifine.entity.model;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.world.entity.EntityType;

public abstract class ModelAdapterIllager extends ModelAdapter {
   public ModelAdapterIllager(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public ModelAdapterIllager(EntityType type, String name, float shadowSize, String[] aliases) {
      super(type, name, shadowSize, aliases);
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof IllagerModel modelVillager)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelVillager.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("hat")) {
         return modelVillager.m_142109_().getChildModelDeep("hat");
      } else if (modelPart.equals("body")) {
         return modelVillager.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("arms")) {
         return modelVillager.m_142109_().getChildModelDeep("arms");
      } else if (modelPart.equals("right_leg")) {
         return modelVillager.m_142109_().getChildModelDeep("right_leg");
      } else if (modelPart.equals("left_leg")) {
         return modelVillager.m_142109_().getChildModelDeep("left_leg");
      } else if (modelPart.equals("nose")) {
         return modelVillager.m_142109_().getChildModelDeep("nose");
      } else if (modelPart.equals("right_arm")) {
         return modelVillager.m_142109_().getChildModelDeep("right_arm");
      } else if (modelPart.equals("left_arm")) {
         return modelVillager.m_142109_().getChildModelDeep("left_arm");
      } else {
         return modelPart.equals("root") ? modelVillager.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "hat", "body", "arms", "right_leg", "left_leg", "nose", "right_arm", "left_arm", "root"};
   }
}
