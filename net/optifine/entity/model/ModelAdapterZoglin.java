package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ZoglinRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterZoglin extends ModelAdapterHoglin {
   public ModelAdapterZoglin() {
      super(EntityType.f_20500_, "zoglin", 0.7F);
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ZoglinRenderer render = new ZoglinRenderer(renderManager.getContext());
      render.f_115290_ = (HoglinModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
