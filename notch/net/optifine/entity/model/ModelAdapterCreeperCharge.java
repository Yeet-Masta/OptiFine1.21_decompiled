package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3812_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4320_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4423_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_988_;
import net.optifine.Config;

public class ModelAdapterCreeperCharge extends ModelAdapterCreeper {
   public ModelAdapterCreeperCharge() {
      super(C_513_.f_20558_, "creeper_charge", 0.25F);
   }

   public C_3840_ makeModel() {
      return new C_3812_(bakeModelLayer(C_141656_.f_171129_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4320_ customRenderer = new C_4320_(renderManager.getContext());
      customRenderer.g = new C_3812_(bakeModelLayer(C_141656_.f_171129_));
      customRenderer.e = 0.25F;
      C_4331_ render = rendererCache.get(C_513_.f_20558_, index, () -> customRenderer);
      if (!(render instanceof C_4320_ renderCreeper)) {
         Config.warn("Not a CreeperRenderer: " + render);
         return null;
      } else {
         C_4423_ layer = new C_4423_(renderCreeper, renderManager.getContext().m_174027_());
         layer.f_116677_ = (C_3812_<C_988_>)modelBase;
         renderCreeper.removeLayers(C_4423_.class);
         renderCreeper.a(layer);
         return renderCreeper;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4320_ renderer = (C_4320_)er;

      for (C_4423_ layer : renderer.getLayers(C_4423_.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
