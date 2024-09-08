package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3843_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4372_;
import net.minecraft.src.C_513_;

public class ModelAdapterPanda extends ModelAdapterQuadruped {
   public ModelAdapterPanda() {
      super(C_513_.f_20507_, "panda", 0.9F);
   }

   public C_3840_ makeModel() {
      return new C_3843_(bakeModelLayer(C_141656_.f_171202_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4372_ render = new C_4372_(renderManager.getContext());
      render.g = (C_3843_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
