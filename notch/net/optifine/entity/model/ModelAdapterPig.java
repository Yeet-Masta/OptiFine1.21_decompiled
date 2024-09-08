package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3851_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4375_;
import net.minecraft.src.C_513_;

public class ModelAdapterPig extends ModelAdapterQuadruped {
   public ModelAdapterPig() {
      super(C_513_.f_20510_, "pig", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3851_(bakeModelLayer(C_141656_.f_171205_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4375_ render = new C_4375_(renderManager.getContext());
      render.g = (C_3851_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
