package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3808_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4318_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterCod extends ModelAdapter {
   public ModelAdapterCod() {
      super(C_513_.f_20556_, "cod", 0.3F);
   }

   public C_3840_ makeModel() {
      return new C_3808_(bakeModelLayer(C_141656_.f_171278_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3808_ modelCod)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelCod.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("fin_back")) {
         return modelCod.m_142109_().getChildModelDeep("top_fin");
      } else if (modelPart.equals("head")) {
         return modelCod.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("nose")) {
         return modelCod.m_142109_().getChildModelDeep("nose");
      } else if (modelPart.equals("fin_right")) {
         return modelCod.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelCod.m_142109_().getChildModelDeep("left_fin");
      } else if (modelPart.equals("tail")) {
         return modelCod.m_142109_().getChildModelDeep("tail_fin");
      } else {
         return modelPart.equals("root") ? modelCod.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "fin_back", "head", "nose", "fin_right", "fin_left", "tail", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4318_ render = new C_4318_(renderManager.getContext());
      render.g = (C_3808_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
