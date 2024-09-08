package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3805_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4314_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4422_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_819_;
import net.optifine.Config;

public class ModelAdapterCatCollar extends ModelAdapterOcelot {
   public ModelAdapterCatCollar() {
      super(C_513_.f_20553_, "cat_collar", 0.4F);
   }

   public C_3840_ makeModel() {
      return new C_3805_(bakeModelLayer(C_141656_.f_171273_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4314_ customRenderer = new C_4314_(renderManager.getContext());
      customRenderer.g = new C_3805_(bakeModelLayer(C_141656_.f_171273_));
      customRenderer.e = 0.4F;
      C_4331_ render = rendererCache.get(C_513_.f_20553_, index, () -> customRenderer);
      if (!(render instanceof C_4314_ renderCat)) {
         Config.warn("Not a RenderCat: " + render);
         return null;
      } else {
         C_4422_ layer = new C_4422_(renderCat, renderManager.getContext().m_174027_());
         layer.f_116650_ = (C_3805_<C_819_>)modelBase;
         renderCat.removeLayers(C_4422_.class);
         renderCat.a(layer);
         return renderCat;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4314_ renderCat = (C_4314_)er;

      for (C_4422_ layer : renderCat.getLayers(C_4422_.class)) {
         layer.f_116650_.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
