package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3822_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4341_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterGhast extends ModelAdapter {
   public ModelAdapterGhast() {
      super(C_513_.f_20453_, "ghast", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3822_(bakeModelLayer(C_141656_.f_171150_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3822_ modelGhast)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelGhast.m_142109_().getChildModelDeep("body");
      } else {
         String PREFIX_TENTACLE = "tentacle";
         if (modelPart.startsWith(PREFIX_TENTACLE)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelGhast.m_142109_().getChildModelDeep("tentacle" + indexPart);
         } else {
            return modelPart.equals("root") ? modelGhast.m_142109_() : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "tentacle9", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4341_ render = new C_4341_(renderManager.getContext());
      render.g = (C_3822_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
