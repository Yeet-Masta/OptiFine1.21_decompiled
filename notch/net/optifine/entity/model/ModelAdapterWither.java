package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3884_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4411_;
import net.minecraft.src.C_513_;

public class ModelAdapterWither extends ModelAdapter {
   public ModelAdapterWither() {
      super(C_513_.f_20496_, "wither", 0.5F);
   }

   public ModelAdapterWither(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3884_(bakeModelLayer(C_141656_.f_171214_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3884_ modelWither)) {
         return null;
      } else if (modelPart.equals("body1")) {
         return modelWither.m_142109_().getChildModelDeep("shoulders");
      } else if (modelPart.equals("body2")) {
         return modelWither.m_142109_().getChildModelDeep("ribcage");
      } else if (modelPart.equals("body3")) {
         return modelWither.m_142109_().getChildModelDeep("tail");
      } else if (modelPart.equals("head1")) {
         return modelWither.m_142109_().getChildModelDeep("center_head");
      } else if (modelPart.equals("head2")) {
         return modelWither.m_142109_().getChildModelDeep("right_head");
      } else if (modelPart.equals("head3")) {
         return modelWither.m_142109_().getChildModelDeep("left_head");
      } else {
         return modelPart.equals("root") ? modelWither.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "head1", "head2", "head3", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4411_ render = new C_4411_(renderManager.getContext());
      render.g = (C_3884_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
