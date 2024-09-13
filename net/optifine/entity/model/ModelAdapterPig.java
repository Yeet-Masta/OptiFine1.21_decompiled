package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterPig extends ModelAdapterQuadruped {
   public ModelAdapterPig() {
      super(EntityType.f_20510_, "pig", 0.7F);
   }

   @Override
   public Model makeModel() {
      return new PigModel(bakeModelLayer(ModelLayers.f_171205_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PigRenderer render = new PigRenderer(renderManager.getContext());
      render.f_115290_ = (PigModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
