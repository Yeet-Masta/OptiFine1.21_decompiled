package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCow extends ModelAdapterQuadruped {
   public ModelAdapterCow() {
      super(EntityType.f_20557_, "cow", 0.7F);
   }

   public Model makeModel() {
      return new CowModel(bakeModelLayer(ModelLayers.f_171284_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CowRenderer render = new CowRenderer(renderManager.getContext());
      render.f_115290_ = (CowModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
