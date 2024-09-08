package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3827_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4404_;
import net.minecraft.src.C_513_;

public class ModelAdapterSkeletonHorse extends ModelAdapterHorse {
   public ModelAdapterSkeletonHorse() {
      super(C_513_.f_20525_, "skeleton_horse", 0.75F);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4404_ render = new C_4404_(renderManager.getContext(), C_141656_.f_171237_);
      render.g = (C_3827_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
