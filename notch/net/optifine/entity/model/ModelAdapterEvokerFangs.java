package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3820_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4332_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterEvokerFangs extends ModelAdapter {
   public ModelAdapterEvokerFangs() {
      super(C_513_.f_20569_, "evoker_fangs", 0.0F, new String[]{"evocation_fangs"});
   }

   public C_3840_ makeModel() {
      return new C_3820_(bakeModelLayer(C_141656_.f_171147_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3820_ modelEvokerFangs)) {
         return null;
      } else if (modelPart.equals("base")) {
         return modelEvokerFangs.m_142109_().getChildModelDeep("base");
      } else if (modelPart.equals("upper_jaw")) {
         return modelEvokerFangs.m_142109_().getChildModelDeep("upper_jaw");
      } else if (modelPart.equals("lower_jaw")) {
         return modelEvokerFangs.m_142109_().getChildModelDeep("lower_jaw");
      } else {
         return modelPart.equals("root") ? modelEvokerFangs.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"base", "upper_jaw", "lower_jaw", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4332_ render = new C_4332_(renderManager.getContext());
      if (!Reflector.RenderEvokerFangs_model.exists()) {
         Config.warn("Field not found: RenderEvokerFangs.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderEvokerFangs_model, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
