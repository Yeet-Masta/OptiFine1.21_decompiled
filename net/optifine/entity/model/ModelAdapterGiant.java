package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GiantZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.GiantMobRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterGiant extends ModelAdapterZombie {
   public ModelAdapterGiant() {
      super(EntityType.f_20454_, "giant", 3.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new GiantZombieModel(bakeModelLayer(ModelLayers.f_171151_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      GiantMobRenderer render = new GiantMobRenderer(renderManager.getContext(), 6.0F);
      render.f_115290_ = (GiantZombieModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
