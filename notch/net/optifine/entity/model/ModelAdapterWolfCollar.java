package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3885_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4414_;
import net.minecraft.src.C_4461_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterWolfCollar extends ModelAdapterWolf {
   public ModelAdapterWolfCollar() {
      super(C_513_.f_20499_, "wolf_collar", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3885_(bakeModelLayer(C_141656_.f_171221_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4414_ customRenderer = new C_4414_(renderManager.getContext());
      customRenderer.g = new C_3885_(bakeModelLayer(C_141656_.f_171221_));
      customRenderer.e = 0.5F;
      C_4331_ render = rendererCache.get(C_513_.f_20499_, index, () -> customRenderer);
      if (!(render instanceof C_4414_ renderWolf)) {
         Config.warn("Not a RenderWolf: " + render);
         return null;
      } else {
         C_4461_ layer = new C_4461_(renderWolf);
         layer.model = (C_3885_)modelBase;
         renderWolf.removeLayers(C_4461_.class);
         renderWolf.a(layer);
         return renderWolf;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4414_ renderWolf = (C_4414_)er;

      for (C_4461_ layer : renderWolf.getLayers(C_4461_.class)) {
         layer.model.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
