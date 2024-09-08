package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3867_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4387_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterSilverfish extends ModelAdapter {
   public ModelAdapterSilverfish() {
      super(C_513_.f_20523_, "silverfish", 0.3F);
   }

   public C_3840_ makeModel() {
      return new C_3867_(bakeModelLayer(C_141656_.f_171235_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3867_ modelSilverfish)) {
         return null;
      } else {
         String PREFIX_BODY = "body";
         if (modelPart.startsWith(PREFIX_BODY)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelSilverfish.m_142109_().getChildModelDeep("segment" + indexPart);
         } else {
            String PREFIX_WINGS = "wing";
            if (modelPart.startsWith(PREFIX_WINGS)) {
               String numStr = StrUtils.removePrefix(modelPart, PREFIX_WINGS);
               int index = Config.parseInt(numStr, -1);
               int indexPart = index - 1;
               return modelSilverfish.m_142109_().getChildModelDeep("layer" + indexPart);
            } else {
               return modelPart.equals("root") ? modelSilverfish.m_142109_() : null;
            }
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4387_ render = new C_4387_(renderManager.getContext());
      render.g = (C_3867_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
