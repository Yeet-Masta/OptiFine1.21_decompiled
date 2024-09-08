package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_141745_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3873_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterGlowSquid extends ModelAdapterSquid {
   public ModelAdapterGlowSquid() {
      super(C_513_.f_147034_, "glow_squid", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3873_(bakeModelLayer(C_141656_.f_171154_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_141745_ render = new C_141745_(renderManager.getContext(), (C_3873_)modelBase);
      render.g = (C_3873_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
