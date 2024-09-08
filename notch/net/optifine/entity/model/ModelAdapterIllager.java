package net.optifine.entity.model;

import net.minecraft.src.C_3832_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_513_;

public abstract class ModelAdapterIllager extends ModelAdapter {
   public ModelAdapterIllager(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public ModelAdapterIllager(C_513_ type, String name, float shadowSize, String[] aliases) {
      super(type, name, shadowSize, aliases);
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3832_ modelVillager)) {
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
