package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3852_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4376_;
import net.minecraft.src.C_513_;

public class ModelAdapterPiglinBrute extends ModelAdapterPiglin {
   public ModelAdapterPiglinBrute() {
      super(C_513_.f_20512_, "piglin_brute", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3852_(bakeModelLayer(C_141656_.f_171207_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4376_ render = new C_4376_(renderManager.getContext(), C_141656_.f_171207_, C_141656_.f_171156_, C_141656_.f_171157_, false);
      render.g = (C_3852_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
