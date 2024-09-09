package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCow extends ModelAdapterQuadruped {
   public ModelAdapterCow() {
      super(EntityType.f_20557_, "cow", 0.7F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new CowModel(bakeModelLayer(ModelLayers.f_171284_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CowRenderer render = new CowRenderer(renderManager.getContext());
      render.f_115290_ = (CowModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
