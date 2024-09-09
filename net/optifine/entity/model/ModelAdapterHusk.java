package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterHusk extends ModelAdapterBiped {
   public ModelAdapterHusk() {
      super(EntityType.f_20458_, "husk", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ZombieModel(bakeModelLayer(ModelLayers.f_171188_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      HuskRenderer render = new HuskRenderer(renderManager.getContext());
      render.f_115290_ = (ZombieModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
