package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EvokerRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterEvoker extends ModelAdapterIllager {
   public ModelAdapterEvoker() {
      super(EntityType.f_20568_, "evoker", 0.5F, new String[]{"evocation_illager"});
   }

   @Override
   public Model makeModel() {
      return new IllagerModel(bakeModelLayer(ModelLayers.f_171146_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      EvokerRenderer render = new EvokerRenderer(renderManager.getContext());
      render.f_115290_ = (EntityModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
