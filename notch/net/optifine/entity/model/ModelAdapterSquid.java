package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3873_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4393_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterSquid extends ModelAdapter {
   public ModelAdapterSquid() {
      super(C_513_.f_20480_, "squid", 0.7F);
   }

   protected ModelAdapterSquid(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3873_(bakeModelLayer(C_141656_.f_171246_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3873_ modelSquid)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelSquid.m_142109_().getChildModelDeep("body");
      } else {
         String PREFIX_TENTACLE = "tentacle";
         if (modelPart.startsWith(PREFIX_TENTACLE)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelSquid.m_142109_().getChildModelDeep("tentacle" + indexPart);
         } else {
            return modelPart.equals("root") ? modelSquid.m_142109_() : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4393_ render = new C_4393_(renderManager.getContext(), (C_3873_)modelBase);
      render.g = (C_3873_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
