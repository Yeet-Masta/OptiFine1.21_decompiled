package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_302024_;
import net.minecraft.src.C_302054_;
import net.minecraft.src.C_302185_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterBreezeEyes extends ModelAdapterBreeze {
   public ModelAdapterBreezeEyes() {
      super(C_513_.f_302782_, "breeze_eyes", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_302185_(bakeModelLayer(C_141656_.f_303100_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_302054_ customRenderer = new C_302054_(renderManager.getContext());
      customRenderer.g = new C_302185_(bakeModelLayer(C_141656_.f_303100_));
      customRenderer.e = 0.0F;
      C_4331_ render = rendererCache.get(C_513_.f_302782_, index, () -> customRenderer);
      if (!(render instanceof C_302054_ renderBreeze)) {
         Config.warn("Not a RenderBreeze: " + render);
         return null;
      } else {
         C_5265_ locTex = modelBase.locationTextureCustom != null ? modelBase.locationTextureCustom : new C_5265_("textures/entity/breeze/breeze.png");
         C_302024_ layer = new C_302024_(renderBreeze);
         layer.setModel((C_302185_)modelBase);
         layer.setTextureLocation(locTex);
         renderBreeze.removeLayers(C_302024_.class);
         renderBreeze.a(layer);
         return renderBreeze;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      return true;
   }
}
