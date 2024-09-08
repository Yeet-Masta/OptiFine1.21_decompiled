package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_313407_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3868_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4394_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterStrayOuter extends ModelAdapterStray {
   public ModelAdapterStrayOuter() {
      super(C_513_.f_20481_, "stray_outer", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3868_(bakeModelLayer(C_141656_.f_171250_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4394_ customRenderer = new C_4394_(renderManager.getContext());
      customRenderer.g = new C_3868_(bakeModelLayer(C_141656_.f_171250_));
      customRenderer.e = 0.7F;
      C_4331_ render = rendererCache.get(C_513_.f_20481_, index, () -> customRenderer);
      if (!(render instanceof C_4394_ renderStray)) {
         Config.warn("Not a SkeletonModelRenderer: " + render);
         return null;
      } else {
         C_5265_ STRAY_CLOTHES_LOCATION = new C_5265_("textures/entity/skeleton/stray_overlay.png");
         C_313407_ layer = new C_313407_(renderStray, renderManager.getContext().m_174027_(), C_141656_.f_171250_, STRAY_CLOTHES_LOCATION);
         layer.f_314940_ = (C_3868_<T>)modelBase;
         renderStray.removeLayers(C_313407_.class);
         renderStray.a(layer);
         return renderStray;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4394_ renderer = (C_4394_)er;

      for (C_313407_ layer : renderer.getLayers(C_313407_.class)) {
         layer.f_316006_ = textureLocation;
      }

      return true;
   }
}
