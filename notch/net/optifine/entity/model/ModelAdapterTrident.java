package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3875_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4397_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTrident extends ModelAdapter {
   public ModelAdapterTrident() {
      super(C_513_.f_20487_, "trident", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3875_(bakeModelLayer(C_141656_.f_171255_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3875_ modelTrident)) {
         return null;
      } else {
         C_3889_ root = (C_3889_)Reflector.ModelTrident_root.getValue(modelTrident);
         if (root != null) {
            if (modelPart.equals("body")) {
               return root.getChildModelDeep("pole");
            }

            if (modelPart.equals("base")) {
               return root.getChildModelDeep("base");
            }

            if (modelPart.equals("left_spike")) {
               return root.getChildModelDeep("left_spike");
            }

            if (modelPart.equals("middle_spike")) {
               return root.getChildModelDeep("middle_spike");
            }

            if (modelPart.equals("right_spike")) {
               return root.getChildModelDeep("right_spike");
            }
         }

         return modelPart.equals("root") ? root : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "base", "left_spike", "middle_spike", "right_spike", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4397_ render = new C_4397_(renderManager.getContext());
      if (!Reflector.RenderTrident_modelTrident.exists()) {
         Config.warn("Field not found: RenderTrident.modelTrident");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderTrident_modelTrident, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
