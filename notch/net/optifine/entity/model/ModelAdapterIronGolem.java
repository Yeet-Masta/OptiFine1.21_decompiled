package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3833_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4351_;
import net.minecraft.src.C_513_;

public class ModelAdapterIronGolem extends ModelAdapter {
   public ModelAdapterIronGolem() {
      super(C_513_.f_20460_, "iron_golem", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3833_(bakeModelLayer(C_141656_.f_171192_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3833_ modelIronGolem)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelIronGolem.m_142109_().m_171324_("head");
      } else if (modelPart.equals("body")) {
         return modelIronGolem.m_142109_().m_171324_("body");
      } else if (modelPart.equals("right_arm")) {
         return modelIronGolem.m_142109_().m_171324_("right_arm");
      } else if (modelPart.equals("left_arm")) {
         return modelIronGolem.m_142109_().m_171324_("left_arm");
      } else if (modelPart.equals("left_leg")) {
         return modelIronGolem.m_142109_().m_171324_("left_leg");
      } else if (modelPart.equals("right_leg")) {
         return modelIronGolem.m_142109_().m_171324_("right_leg");
      } else {
         return modelPart.equals("root") ? modelIronGolem.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_arm", "left_arm", "left_leg", "right_leg", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4351_ render = new C_4351_(renderManager.getContext());
      render.g = (C_3833_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
