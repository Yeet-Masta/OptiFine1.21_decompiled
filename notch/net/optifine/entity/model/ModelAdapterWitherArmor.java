package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3884_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4411_;
import net.minecraft.src.C_4460_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterWitherArmor extends ModelAdapterWither {
   public ModelAdapterWitherArmor() {
      super(C_513_.f_20496_, "wither_armor", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3884_(bakeModelLayer(C_141656_.f_171215_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4411_ customRenderer = new C_4411_(renderManager.getContext());
      customRenderer.g = new C_3884_(bakeModelLayer(C_141656_.f_171215_));
      customRenderer.e = 0.5F;
      C_4331_ render = rendererCache.get(C_513_.f_20496_, index, () -> customRenderer);
      if (!(render instanceof C_4411_ renderWither)) {
         Config.warn("Not a WitherRenderer: " + render);
         return null;
      } else {
         C_4460_ layer = new C_4460_(renderWither, renderManager.getContext().m_174027_());
         layer.f_117696_ = (C_3884_)modelBase;
         renderWither.removeLayers(C_4460_.class);
         renderWither.a(layer);
         return renderWither;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4411_ renderer = (C_4411_)er;

      for (C_4460_ layer : renderer.getLayers(C_4460_.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
