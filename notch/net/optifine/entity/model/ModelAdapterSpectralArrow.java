package net.optifine.entity.model;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4391_;
import net.minecraft.src.C_513_;

public class ModelAdapterSpectralArrow extends ModelAdapterArrow {
   public ModelAdapterSpectralArrow() {
      super(C_513_.f_20478_, "spectral_arrow", 0.0F);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4391_ render = new C_4391_(renderManager.getContext());
      render.model = (ArrowModel)modelBase;
      render.e = shadowSize;
      return render;
   }
}
