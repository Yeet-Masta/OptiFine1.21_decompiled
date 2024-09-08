package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3852_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4376_;
import net.minecraft.src.C_513_;

public class ModelAdapterZombifiedPiglin extends ModelAdapterPiglin {
   public ModelAdapterZombifiedPiglin() {
      super(C_513_.f_20531_, "zombified_piglin", 0.5F);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4376_ render = new C_4376_(renderManager.getContext(), C_141656_.f_171231_, C_141656_.f_171232_, C_141656_.f_171233_, true);
      render.g = (C_3852_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
