package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.renderer.entity.ZoglinRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterZoglin extends ModelAdapterHoglin {
   public ModelAdapterZoglin() {
      super(EntityType.f_20500_, "zoglin", 0.7F);
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ZoglinRenderer render = new ZoglinRenderer(renderManager.getContext());
      render.f_115290_ = (HoglinModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
