package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCaveSpider extends ModelAdapterSpider {
   public ModelAdapterCaveSpider() {
      super(EntityType.f_20554_, "cave_spider", 0.7F);
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CaveSpiderRenderer render = new CaveSpiderRenderer(renderManager.getContext());
      render.f_115290_ = (SpiderModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
