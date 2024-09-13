package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSheep extends ModelAdapterQuadruped {
   public ModelAdapterSheep() {
      super(EntityType.f_20520_, "sheep", 0.7F);
   }

   @Override
   public Model makeModel() {
      return new SheepModel(bakeModelLayer(ModelLayers.f_171177_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SheepRenderer render = new SheepRenderer(renderManager.getContext());
      render.f_115290_ = (SheepModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
