package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSpectralArrow extends ModelAdapterArrow {
   public ModelAdapterSpectralArrow() {
      super(EntityType.f_20478_, "spectral_arrow", 0.0F);
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SpectralArrowRenderer render = new SpectralArrowRenderer(renderManager.getContext());
      render.model = (ArrowModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
