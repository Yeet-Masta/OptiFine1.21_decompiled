package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3844_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4373_;
import net.minecraft.src.C_513_;

public class ModelAdapterParrot extends ModelAdapter {
   public ModelAdapterParrot() {
      super(C_513_.f_20508_, "parrot", 0.3F);
   }

   public C_3840_ makeModel() {
      return new C_3844_(bakeModelLayer(C_141656_.f_171203_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3844_ modelParrot)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelParrot.m_142109_().m_171324_("body");
      } else if (modelPart.equals("tail")) {
         return modelParrot.m_142109_().m_171324_("tail");
      } else if (modelPart.equals("left_wing")) {
         return modelParrot.m_142109_().m_171324_("left_wing");
      } else if (modelPart.equals("right_wing")) {
         return modelParrot.m_142109_().m_171324_("right_wing");
      } else if (modelPart.equals("head")) {
         return modelParrot.m_142109_().m_171324_("head");
      } else if (modelPart.equals("left_leg")) {
         return modelParrot.m_142109_().m_171324_("left_leg");
      } else if (modelPart.equals("right_leg")) {
         return modelParrot.m_142109_().m_171324_("right_leg");
      } else {
         return modelPart.equals("root") ? modelParrot.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "tail", "left_wing", "right_wing", "head", "left_leg", "right_leg", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4373_ render = new C_4373_(renderManager.getContext());
      render.g = (C_3844_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
