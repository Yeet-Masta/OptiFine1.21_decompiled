package net.optifine.entity.model;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3824_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4324_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterElderGuardian extends ModelAdapterGuardian {
   public ModelAdapterElderGuardian() {
      super(C_513_.f_20563_, "elder_guardian", 0.5F);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4324_ render = new C_4324_(renderManager.getContext());
      render.g = (C_3824_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
