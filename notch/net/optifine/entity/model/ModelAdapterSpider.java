package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3872_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4392_;
import net.minecraft.src.C_513_;

public class ModelAdapterSpider extends ModelAdapter {
   public ModelAdapterSpider() {
      super(C_513_.f_20479_, "spider", 1.0F);
   }

   protected ModelAdapterSpider(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3872_(bakeModelLayer(C_141656_.f_171245_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3872_ modelSpider)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelSpider.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("neck")) {
         return modelSpider.m_142109_().getChildModelDeep("body0");
      } else if (modelPart.equals("body")) {
         return modelSpider.m_142109_().getChildModelDeep("body1");
      } else if (modelPart.equals("leg1")) {
         return modelSpider.m_142109_().getChildModelDeep("right_hind_leg");
      } else if (modelPart.equals("leg2")) {
         return modelSpider.m_142109_().getChildModelDeep("left_hind_leg");
      } else if (modelPart.equals("leg3")) {
         return modelSpider.m_142109_().getChildModelDeep("right_middle_hind_leg");
      } else if (modelPart.equals("leg4")) {
         return modelSpider.m_142109_().getChildModelDeep("left_middle_hind_leg");
      } else if (modelPart.equals("leg5")) {
         return modelSpider.m_142109_().getChildModelDeep("right_middle_front_leg");
      } else if (modelPart.equals("leg6")) {
         return modelSpider.m_142109_().getChildModelDeep("left_middle_front_leg");
      } else if (modelPart.equals("leg7")) {
         return modelSpider.m_142109_().getChildModelDeep("right_front_leg");
      } else if (modelPart.equals("leg8")) {
         return modelSpider.m_142109_().getChildModelDeep("left_front_leg");
      } else {
         return modelPart.equals("root") ? modelSpider.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", "leg8", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4392_ render = new C_4392_(renderManager.getContext());
      render.g = (C_3819_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
