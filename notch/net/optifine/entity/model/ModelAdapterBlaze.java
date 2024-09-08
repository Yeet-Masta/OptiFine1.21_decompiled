package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3802_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4312_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterBlaze extends ModelAdapter {
   public ModelAdapterBlaze() {
      super(C_513_.f_20551_, "blaze", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3802_(bakeModelLayer(C_141656_.f_171270_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3802_ modelBlaze)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelBlaze.m_142109_().getChildModelDeep("head");
      } else {
         String PREFIX_STICK = "stick";
         if (modelPart.startsWith(PREFIX_STICK)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_STICK);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelBlaze.m_142109_().getChildModelDeep("part" + indexPart);
         } else {
            return modelPart.equals("root") ? modelBlaze.m_142109_() : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{
         "head", "stick1", "stick2", "stick3", "stick4", "stick5", "stick6", "stick7", "stick8", "stick9", "stick10", "stick11", "stick12", "root"
      };
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4312_ render = new C_4312_(renderManager.getContext());
      render.g = (C_3802_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
