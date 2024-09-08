package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3832_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4349_;
import net.minecraft.src.C_513_;

public class ModelAdapterIllusioner extends ModelAdapterIllager {
   public ModelAdapterIllusioner() {
      super(C_513_.f_20459_, "illusioner", 0.5F, new String[]{"illusion_illager"});
   }

   public C_3840_ makeModel() {
      C_3832_ model = new C_3832_(bakeModelLayer(C_141656_.f_171191_));
      model.m_102934_().f_104207_ = true;
      return model;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4349_ render = new C_4349_(renderManager.getContext());
      render.g = (C_3832_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
