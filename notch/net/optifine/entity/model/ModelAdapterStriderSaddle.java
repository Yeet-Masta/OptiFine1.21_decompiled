package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3874_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4395_;
import net.minecraft.src.C_4448_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;

public class ModelAdapterStriderSaddle extends ModelAdapterStrider {
   public ModelAdapterStriderSaddle() {
      super(C_513_.f_20482_, "strider_saddle", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3874_(bakeModelLayer(C_141656_.f_171252_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4395_ customRenderer = new C_4395_(renderManager.getContext());
      customRenderer.g = new C_3874_(bakeModelLayer(C_141656_.f_171252_));
      customRenderer.e = 0.5F;
      C_4331_ render = rendererCache.get(C_513_.f_20482_, index, () -> customRenderer);
      if (!(render instanceof C_4395_ renderStrider)) {
         Config.warn("Not a StriderRenderer: " + render);
         return null;
      } else {
         C_4448_ layer = new C_4448_(renderStrider, (C_3874_)modelBase, new C_5265_("textures/entity/strider/strider_saddle.png"));
         renderStrider.removeLayers(C_4448_.class);
         renderStrider.a(layer);
         return renderStrider;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4395_ renderer = (C_4395_)er;

      for (C_4448_ layer : renderer.getLayers(C_4448_.class)) {
         layer.f_117387_ = textureLocation;
      }

      return true;
   }
}
