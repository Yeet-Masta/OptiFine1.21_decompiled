package net.optifine.entity.model;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3872_;
import net.minecraft.src.C_4315_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterCaveSpider extends ModelAdapterSpider {
   public ModelAdapterCaveSpider() {
      super(C_513_.f_20554_, "cave_spider", 0.7F);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4315_ render = new C_4315_(renderManager.getContext());
      render.g = (C_3872_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
