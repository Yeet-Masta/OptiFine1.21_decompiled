package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3870_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4389_;
import net.minecraft.src.C_4451_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterSlimeOuter extends ModelAdapter {
   public ModelAdapterSlimeOuter() {
      super(C_513_.f_20526_, "slime_outer", 0.25F);
   }

   public C_3840_ makeModel() {
      return new C_3870_(bakeModelLayer(C_141656_.f_171242_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3870_ modelSlime)) {
         return null;
      } else {
         return modelPart.equals("body") ? modelSlime.m_142109_().getChildModelDeep("cube") : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4389_ customRenderer = new C_4389_(renderManager.getContext());
      customRenderer.g = new C_3870_(bakeModelLayer(C_141656_.f_171242_));
      customRenderer.e = 0.25F;
      C_4331_ render = rendererCache.get(C_513_.f_20526_, index, () -> customRenderer);
      if (!(render instanceof C_4389_ renderSlime)) {
         Config.warn("Not a SlimeRenderer: " + render);
         return null;
      } else {
         C_4451_ layer = new C_4451_(renderSlime, renderManager.getContext().m_174027_());
         layer.f_117455_ = (C_3870_)modelBase;
         renderSlime.removeLayers(C_4451_.class);
         renderSlime.a(layer);
         return renderSlime;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4389_ renderer = (C_4389_)er;

      for (C_4451_ layer : renderer.getLayers(C_4451_.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
