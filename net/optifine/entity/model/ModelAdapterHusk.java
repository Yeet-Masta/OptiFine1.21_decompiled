package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterHusk extends ModelAdapterBiped {
   public ModelAdapterHusk() {
      super(EntityType.f_20458_, "husk", 0.5F);
   }

   public Model makeModel() {
      return new ZombieModel(bakeModelLayer(ModelLayers.f_171188_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      HuskRenderer render = new HuskRenderer(renderManager.getContext());
      render.f_115290_ = (ZombieModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
