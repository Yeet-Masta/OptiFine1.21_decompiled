package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3823_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4342_;
import net.minecraft.src.C_513_;

public class ModelAdapterGiant extends ModelAdapterZombie {
   public ModelAdapterGiant() {
      super(C_513_.f_20454_, "giant", 3.0F);
   }

   public C_3840_ makeModel() {
      return new C_3823_(bakeModelLayer(C_141656_.f_171151_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4342_ render = new C_4342_(renderManager.getContext(), 6.0F);
      render.g = (C_3823_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
