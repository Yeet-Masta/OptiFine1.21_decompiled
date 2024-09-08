package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3812_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4320_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterCreeper extends ModelAdapter {
   public ModelAdapterCreeper() {
      super(C_513_.f_20558_, "creeper", 0.5F);
   }

   public ModelAdapterCreeper(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3812_(bakeModelLayer(C_141656_.f_171285_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3812_ modelCreeper)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelCreeper.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("body")) {
         return modelCreeper.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("leg1")) {
         return modelCreeper.m_142109_().getChildModelDeep("right_hind_leg");
      } else if (modelPart.equals("leg2")) {
         return modelCreeper.m_142109_().getChildModelDeep("left_hind_leg");
      } else if (modelPart.equals("leg3")) {
         return modelCreeper.m_142109_().getChildModelDeep("right_front_leg");
      } else if (modelPart.equals("leg4")) {
         return modelCreeper.m_142109_().getChildModelDeep("left_front_leg");
      } else {
         return modelPart.equals("root") ? modelCreeper.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4320_ render = new C_4320_(renderManager.getContext());
      render.g = (C_3812_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
