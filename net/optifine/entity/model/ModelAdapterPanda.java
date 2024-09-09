package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PandaModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.PandaRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterPanda extends ModelAdapterQuadruped {
   public ModelAdapterPanda() {
      super(EntityType.f_20507_, "panda", 0.9F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new PandaModel(bakeModelLayer(ModelLayers.f_171202_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PandaRenderer render = new PandaRenderer(renderManager.getContext());
      render.f_115290_ = (PandaModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
