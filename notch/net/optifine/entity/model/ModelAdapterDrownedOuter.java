package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3814_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4323_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4429_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterDrownedOuter extends ModelAdapterDrowned {
   public ModelAdapterDrownedOuter() {
      super(C_513_.f_20562_, "drowned_outer", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3814_(bakeModelLayer(C_141656_.f_171139_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4323_ customRenderer = new C_4323_(renderManager.getContext());
      customRenderer.g = new C_3814_(bakeModelLayer(C_141656_.f_171139_));
      customRenderer.e = 0.75F;
      C_4331_ render = rendererCache.get(C_513_.f_20562_, index, () -> customRenderer);
      if (!(render instanceof C_4323_ renderDrowned)) {
         Config.warn("Not a DrownedRenderer: " + render);
         return null;
      } else {
         C_4429_ layer = new C_4429_(renderDrowned, renderManager.getContext().m_174027_());
         layer.f_116908_ = (C_3814_)modelBase;
         renderDrowned.removeLayers(C_4429_.class);
         renderDrowned.a(layer);
         return renderDrowned;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4323_ renderer = (C_4323_)er;

      for (C_4429_ layer : renderer.getLayers(C_4429_.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
