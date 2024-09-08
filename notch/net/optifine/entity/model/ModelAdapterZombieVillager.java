package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3887_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4417_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_75_;

public class ModelAdapterZombieVillager extends ModelAdapterBiped {
   public ModelAdapterZombieVillager() {
      super(C_513_.f_20530_, "zombie_villager", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3887_(bakeModelLayer(C_141656_.f_171228_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_75_ resourceManager = (C_75_)C_3391_.m_91087_().m_91098_();
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4417_ render = new C_4417_(renderManager.getContext());
      render.g = (C_3887_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
