package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3835_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4355_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterLeadKnot extends ModelAdapter {
   public ModelAdapterLeadKnot() {
      super(C_513_.f_20464_, "lead_knot", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3835_(bakeModelLayer(C_141656_.f_171193_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3835_ modelLeashKnot)) {
         return null;
      } else if (modelPart.equals("knot")) {
         return modelLeashKnot.m_142109_().getChildModelDeep("knot");
      } else {
         return modelPart.equals("root") ? modelLeashKnot.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"knot", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4355_ render = new C_4355_(renderManager.getContext());
      if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
         Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
