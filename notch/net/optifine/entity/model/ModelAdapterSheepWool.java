package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3862_;
import net.minecraft.src.C_3863_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4384_;
import net.minecraft.src.C_4449_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
   public ModelAdapterSheepWool() {
      super(C_513_.f_20520_, "sheep_wool", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3862_(bakeModelLayer(C_141656_.f_171178_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4384_ customRenderer = new C_4384_(renderManager.getContext());
      customRenderer.g = new C_3863_(bakeModelLayer(C_141656_.f_171178_));
      customRenderer.e = 0.7F;
      C_4331_ render = rendererCache.get(C_513_.f_20520_, index, () -> customRenderer);
      if (!(render instanceof C_4384_ renderSheep)) {
         Config.warn("Not a RenderSheep: " + render);
         return null;
      } else {
         C_4449_ layer = new C_4449_(renderSheep, renderManager.getContext().m_174027_());
         layer.f_117405_ = (C_3862_)modelBase;
         renderSheep.removeLayers(C_4449_.class);
         renderSheep.a(layer);
         return renderSheep;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4384_ renderSheep = (C_4384_)er;

      for (C_4449_ layer : renderSheep.getLayers(C_4449_.class)) {
         layer.f_117405_.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
