package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3834_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4361_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterMagmaCube extends ModelAdapter {
   public ModelAdapterMagmaCube() {
      super(C_513_.f_20468_, "magma_cube", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3834_(bakeModelLayer(C_141656_.f_171197_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3834_ modelMagmaCube)) {
         return null;
      } else if (modelPart.equals("core")) {
         return modelMagmaCube.m_142109_().getChildModelDeep("inside_cube");
      } else {
         String PREFIX_SEGMENT = "segment";
         if (modelPart.startsWith(PREFIX_SEGMENT)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_SEGMENT);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelMagmaCube.m_142109_().getChildModelDeep("cube" + indexPart);
         } else {
            return modelPart.equals("root") ? modelMagmaCube.m_142109_() : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4361_ render = new C_4361_(renderManager.getContext());
      render.g = (C_3834_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
