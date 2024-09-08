package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3818_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4329_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterEndermite extends ModelAdapter {
   public ModelAdapterEndermite() {
      super(C_513_.f_20567_, "endermite", 0.3F);
   }

   public C_3840_ makeModel() {
      return new C_3818_(bakeModelLayer(C_141656_.f_171143_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3818_ modelEnderMite)) {
         return null;
      } else {
         String PREFIX_BODY = "body";
         if (modelPart.startsWith(PREFIX_BODY)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelEnderMite.m_142109_().getChildModelDeep("segment" + indexPart);
         } else {
            return modelPart.equals("root") ? modelEnderMite.m_142109_() : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "body4", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4329_ render = new C_4329_(renderManager.getContext());
      render.g = (C_3818_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
