package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3861_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4383_;
import net.minecraft.src.C_513_;

public class ModelAdapterSalmon extends ModelAdapter {
   public ModelAdapterSalmon() {
      super(C_513_.f_20519_, "salmon", 0.3F);
   }

   public C_3840_ makeModel() {
      return new C_3861_(bakeModelLayer(C_141656_.f_171176_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3861_ modelSalmon)) {
         return null;
      } else if (modelPart.equals("body_front")) {
         return modelSalmon.m_142109_().getChildModelDeep("body_front");
      } else if (modelPart.equals("body_back")) {
         return modelSalmon.m_142109_().getChildModelDeep("body_back");
      } else if (modelPart.equals("head")) {
         return modelSalmon.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("fin_back_1")) {
         return modelSalmon.m_142109_().getChildModelDeep("top_front_fin");
      } else if (modelPart.equals("fin_back_2")) {
         return modelSalmon.m_142109_().getChildModelDeep("top_back_fin");
      } else if (modelPart.equals("tail")) {
         return modelSalmon.m_142109_().getChildModelDeep("back_fin");
      } else if (modelPart.equals("fin_right")) {
         return modelSalmon.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelSalmon.m_142109_().getChildModelDeep("left_fin");
      } else {
         return modelPart.equals("root") ? modelSalmon.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body_front", "body_back", "head", "fin_back_1", "fin_back_2", "tail", "fin_right", "fin_left", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4383_ render = new C_4383_(renderManager.getContext());
      render.g = (C_3861_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
