package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3868_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4388_;
import net.minecraft.src.C_513_;

public class ModelAdapterSkeleton extends ModelAdapterBiped {
   public ModelAdapterSkeleton() {
      super(C_513_.f_20524_, "skeleton", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3868_(bakeModelLayer(C_141656_.f_171236_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4388_ render = new C_4388_(renderManager.getContext());
      render.g = (C_3868_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
