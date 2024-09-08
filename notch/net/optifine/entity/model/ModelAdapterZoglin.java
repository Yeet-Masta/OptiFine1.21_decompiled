package net.optifine.entity.model;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3826_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4415_;
import net.minecraft.src.C_513_;

public class ModelAdapterZoglin extends ModelAdapterHoglin {
   public ModelAdapterZoglin() {
      super(C_513_.f_20500_, "zoglin", 0.7F);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4415_ render = new C_4415_(renderManager.getContext());
      render.g = (C_3826_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
