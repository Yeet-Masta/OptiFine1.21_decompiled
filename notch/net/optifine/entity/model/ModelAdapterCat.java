package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3805_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4314_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterCat extends ModelAdapterOcelot {
   public ModelAdapterCat() {
      super(C_513_.f_20553_, "cat", 0.4F);
   }

   public C_3840_ makeModel() {
      return new C_3805_(bakeModelLayer(C_141656_.f_171272_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4314_ render = new C_4314_(renderManager.getContext());
      render.g = (C_3805_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
