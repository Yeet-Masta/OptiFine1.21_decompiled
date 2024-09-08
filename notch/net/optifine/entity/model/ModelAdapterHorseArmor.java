package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3827_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4345_;
import net.minecraft.src.C_4435_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_928_;
import net.optifine.Config;

public class ModelAdapterHorseArmor extends ModelAdapterHorse {
   public ModelAdapterHorseArmor() {
      super(C_513_.f_20457_, "horse_armor", 0.75F);
   }

   public C_3840_ makeModel() {
      return new C_3827_(bakeModelLayer(C_141656_.f_171187_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4345_ customRenderer = new C_4345_(renderManager.getContext());
      customRenderer.g = new C_3827_(bakeModelLayer(C_141656_.f_171187_));
      customRenderer.e = 0.75F;
      C_4331_ render = rendererCache.get(C_513_.f_20457_, index, () -> customRenderer);
      if (!(render instanceof C_4345_ renderHorse)) {
         Config.warn("Not a HorseRenderer: " + render);
         return null;
      } else {
         C_4435_ layer = new C_4435_(renderHorse, renderManager.getContext().m_174027_());
         layer.f_117017_ = (C_3827_<C_928_>)modelBase;
         renderHorse.removeLayers(C_4435_.class);
         renderHorse.a(layer);
         return renderHorse;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4345_ renderer = (C_4345_)er;

      for (C_4435_ layer : renderer.getLayers(C_4435_.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
