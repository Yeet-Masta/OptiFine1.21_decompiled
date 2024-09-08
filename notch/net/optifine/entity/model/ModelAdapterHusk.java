package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3886_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4347_;
import net.minecraft.src.C_513_;

public class ModelAdapterHusk extends ModelAdapterBiped {
   public ModelAdapterHusk() {
      super(C_513_.f_20458_, "husk", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3886_(bakeModelLayer(C_141656_.f_171188_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4347_ render = new C_4347_(renderManager.getContext());
      render.g = (C_3886_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
