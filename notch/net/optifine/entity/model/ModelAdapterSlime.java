package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3870_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4389_;
import net.minecraft.src.C_513_;

public class ModelAdapterSlime extends ModelAdapter {
   public ModelAdapterSlime() {
      super(C_513_.f_20526_, "slime", 0.25F);
   }

   public ModelAdapterSlime(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3870_(bakeModelLayer(C_141656_.f_171241_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3870_ modelSlime)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelSlime.m_142109_().getChildModelDeep("cube");
      } else if (modelPart.equals("left_eye")) {
         return modelSlime.m_142109_().getChildModelDeep("left_eye");
      } else if (modelPart.equals("right_eye")) {
         return modelSlime.m_142109_().getChildModelDeep("right_eye");
      } else if (modelPart.equals("mouth")) {
         return modelSlime.m_142109_().getChildModelDeep("mouth");
      } else {
         return modelPart.equals("root") ? modelSlime.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "left_eye", "right_eye", "mouth", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4389_ render = new C_4389_(renderManager.getContext());
      render.g = (C_3870_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
