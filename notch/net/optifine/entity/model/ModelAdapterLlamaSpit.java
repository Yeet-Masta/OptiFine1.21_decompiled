package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3838_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4360_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaSpit extends ModelAdapter {
   public ModelAdapterLlamaSpit() {
      super(C_513_.f_20467_, "llama_spit", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3838_(bakeModelLayer(C_141656_.f_171196_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3838_ modelLlamaSpit)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelLlamaSpit.m_142109_().getChildModelDeep("main");
      } else {
         return modelPart.equals("root") ? modelLlamaSpit.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4360_ render = new C_4360_(renderManager.getContext());
      if (!Reflector.RenderLlamaSpit_model.exists()) {
         Config.warn("Field not found: RenderLlamaSpit.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderLlamaSpit_model, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
