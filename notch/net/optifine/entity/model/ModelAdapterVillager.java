package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3882_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4406_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_75_;

public class ModelAdapterVillager extends ModelAdapter {
   public ModelAdapterVillager() {
      super(C_513_.f_20492_, "villager", 0.5F);
   }

   protected ModelAdapterVillager(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3882_(bakeModelLayer(C_141656_.f_171210_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3882_ modelVillager)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelVillager.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("headwear")) {
         return modelVillager.m_142109_().getChildModelDeep("hat");
      } else if (modelPart.equals("headwear2")) {
         return modelVillager.m_142109_().getChildModelDeep("hat_rim");
      } else if (modelPart.equals("body")) {
         return modelVillager.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("bodywear")) {
         return modelVillager.m_142109_().getChildModelDeep("jacket");
      } else if (modelPart.equals("arms")) {
         return modelVillager.m_142109_().getChildModelDeep("arms");
      } else if (modelPart.equals("right_leg")) {
         return modelVillager.m_142109_().getChildModelDeep("right_leg");
      } else if (modelPart.equals("left_leg")) {
         return modelVillager.m_142109_().getChildModelDeep("left_leg");
      } else if (modelPart.equals("nose")) {
         return modelVillager.m_142109_().getChildModelDeep("nose");
      } else {
         return modelPart.equals("root") ? modelVillager.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "headwear", "headwear2", "body", "bodywear", "arms", "right_leg", "left_leg", "nose", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_75_ resourceManager = (C_75_)C_3391_.m_91087_().m_91098_();
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4406_ render = new C_4406_(renderManager.getContext());
      render.g = (C_3882_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
