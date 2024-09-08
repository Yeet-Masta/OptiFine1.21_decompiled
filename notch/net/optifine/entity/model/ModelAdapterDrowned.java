package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3814_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4323_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterDrowned extends ModelAdapterZombie {
   public ModelAdapterDrowned() {
      super(C_513_.f_20562_, "drowned", 0.5F);
   }

   public ModelAdapterDrowned(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3814_(bakeModelLayer(C_141656_.f_171136_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4323_ render = new C_4323_(renderManager.getContext());
      render.g = (C_3814_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
