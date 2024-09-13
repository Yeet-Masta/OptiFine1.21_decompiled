package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterZombie extends ModelAdapterBiped {
   public ModelAdapterZombie() {
      super(EntityType.f_20501_, "zombie", 0.5F);
   }

   protected ModelAdapterZombie(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public Model makeModel() {
      return new ZombieModel(bakeModelLayer(ModelLayers.f_171223_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ZombieRenderer render = new ZombieRenderer(renderManager.getContext());
      render.f_115290_ = (ZombieModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
