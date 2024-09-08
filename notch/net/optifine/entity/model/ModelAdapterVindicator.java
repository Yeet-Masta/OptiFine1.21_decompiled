package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3832_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4407_;
import net.minecraft.src.C_513_;

public class ModelAdapterVindicator extends ModelAdapterIllager {
   public ModelAdapterVindicator() {
      super(C_513_.f_20493_, "vindicator", 0.5F, new String[]{"vindication_illager"});
   }

   public C_3840_ makeModel() {
      return new C_3832_(bakeModelLayer(C_141656_.f_171211_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4407_ render = new C_4407_(renderManager.getContext());
      render.g = (C_3832_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
