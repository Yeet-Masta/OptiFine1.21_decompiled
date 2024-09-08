package net.optifine.entity.model;

import net.minecraft.src.C_141649_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_141746_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.Config;

public class ModelAdapterGoat extends ModelAdapterQuadruped {
   public ModelAdapterGoat() {
      super(C_513_.f_147035_, "goat", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_141649_(bakeModelLayer(C_141656_.f_171182_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_141649_ modelGoat)) {
         return null;
      } else {
         C_3889_ head = super.getModelRenderer(modelGoat, "head");
         if (head != null) {
            if (modelPart.equals("left_horn")) {
               return head.m_171324_(modelPart);
            }

            if (modelPart.equals("right_horn")) {
               return head.m_171324_(modelPart);
            }

            if (modelPart.equals("nose")) {
               return head.m_171324_(modelPart);
            }
         }

         return super.getModelRenderer(model, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])Config.addObjectsToArray(names, new String[]{"left_horn", "right_horn", "nose"});
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_141746_ render = new C_141746_(renderManager.getContext());
      render.g = (C_141649_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
