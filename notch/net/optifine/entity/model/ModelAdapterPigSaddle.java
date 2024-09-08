package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3851_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4375_;
import net.minecraft.src.C_4448_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterPigSaddle extends ModelAdapterQuadruped {
   public ModelAdapterPigSaddle() {
      super(C_513_.f_20510_, "pig_saddle", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3851_(bakeModelLayer(C_141656_.f_171160_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4375_ customRenderer = new C_4375_(renderManager.getContext());
      customRenderer.g = new C_3851_(bakeModelLayer(C_141656_.f_171160_));
      customRenderer.e = 0.7F;
      C_4331_ render = rendererCache.get(C_513_.f_20510_, index, () -> customRenderer);
      if (!(render instanceof C_4375_ renderPig)) {
         Config.warn("Not a PigRenderer: " + render);
         return null;
      } else {
         C_4448_ layer = new C_4448_(renderPig, (C_3851_)modelBase, new C_5265_("textures/entity/pig/pig_saddle.png"));
         renderPig.removeLayers(C_4448_.class);
         renderPig.a(layer);
         return renderPig;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4375_ renderer = (C_4375_)er;

      for (C_4448_ layer : renderer.getLayers(C_4448_.class)) {
         layer.f_117387_ = textureLocation;
      }

      return true;
   }
}
