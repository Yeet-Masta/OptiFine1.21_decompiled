package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3863_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4384_;
import net.minecraft.src.C_513_;

public class ModelAdapterSheep extends ModelAdapterQuadruped {
   public ModelAdapterSheep() {
      super(C_513_.f_20520_, "sheep", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3863_(bakeModelLayer(C_141656_.f_171177_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4384_ render = new C_4384_(renderManager.getContext());
      render.g = (C_3863_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
