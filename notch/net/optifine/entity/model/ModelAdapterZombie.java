package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3886_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4416_;
import net.minecraft.src.C_513_;

public class ModelAdapterZombie extends ModelAdapterBiped {
   public ModelAdapterZombie() {
      super(C_513_.f_20501_, "zombie", 0.5F);
   }

   protected ModelAdapterZombie(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3886_(bakeModelLayer(C_141656_.f_171223_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4416_ render = new C_4416_(renderManager.getContext());
      render.g = (C_3886_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
