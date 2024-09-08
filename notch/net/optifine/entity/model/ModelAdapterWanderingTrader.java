package net.optifine.entity.model;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3882_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4409_;
import net.minecraft.src.C_513_;

public class ModelAdapterWanderingTrader extends ModelAdapterVillager {
   public ModelAdapterWanderingTrader() {
      super(C_513_.f_20494_, "wandering_trader", 0.5F);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4409_ render = new C_4409_(renderManager.getContext());
      render.g = (C_3882_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
