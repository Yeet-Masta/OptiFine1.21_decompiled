package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3832_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4377_;
import net.minecraft.src.C_513_;

public class ModelAdapterPillager extends ModelAdapterIllager {
   public ModelAdapterPillager() {
      super(C_513_.f_20513_, "pillager", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3832_(bakeModelLayer(C_141656_.f_171161_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4377_ render = new C_4377_(renderManager.getContext());
      render.g = (C_3832_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
