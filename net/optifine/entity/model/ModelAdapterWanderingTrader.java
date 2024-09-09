package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.WanderingTraderRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterWanderingTrader extends ModelAdapterVillager {
   public ModelAdapterWanderingTrader() {
      super(EntityType.f_20494_, "wandering_trader", 0.5F);
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WanderingTraderRenderer render = new WanderingTraderRenderer(renderManager.getContext());
      render.f_115290_ = (VillagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
