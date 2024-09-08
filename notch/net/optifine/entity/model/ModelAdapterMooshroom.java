package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3811_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4364_;
import net.minecraft.src.C_513_;

public class ModelAdapterMooshroom extends ModelAdapterQuadruped {
   public ModelAdapterMooshroom() {
      super(C_513_.f_20504_, "mooshroom", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3811_(bakeModelLayer(C_141656_.f_171199_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4364_ render = new C_4364_(renderManager.getContext());
      render.g = (C_3811_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
