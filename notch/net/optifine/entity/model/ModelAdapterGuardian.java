package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3824_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4343_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterGuardian extends ModelAdapter {
   public ModelAdapterGuardian() {
      super(C_513_.f_20455_, "guardian", 0.5F);
   }

   public ModelAdapterGuardian(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3824_(bakeModelLayer(C_141656_.f_171183_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3824_ modelGuardian)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelGuardian.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("eye")) {
         return modelGuardian.m_142109_().getChildModelDeep("eye");
      } else {
         String PREFIX_SPINE = "spine";
         if (modelPart.startsWith(PREFIX_SPINE)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_SPINE);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelGuardian.m_142109_().getChildModelDeep("spike" + indexPart);
         } else {
            String PREFIX_TAIL = "tail";
            if (modelPart.startsWith(PREFIX_TAIL)) {
               String numStr = StrUtils.removePrefix(modelPart, PREFIX_TAIL);
               int index = Config.parseInt(numStr, -1);
               int indexPart = index - 1;
               return modelGuardian.m_142109_().getChildModelDeep("tail" + indexPart);
            } else {
               return modelPart.equals("root") ? modelGuardian.m_142109_() : null;
            }
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{
         "body",
         "eye",
         "spine1",
         "spine2",
         "spine3",
         "spine4",
         "spine5",
         "spine6",
         "spine7",
         "spine8",
         "spine9",
         "spine10",
         "spine11",
         "spine12",
         "tail1",
         "tail2",
         "tail3",
         "root"
      };
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4343_ render = new C_4343_(renderManager.getContext());
      render.g = (C_3824_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
