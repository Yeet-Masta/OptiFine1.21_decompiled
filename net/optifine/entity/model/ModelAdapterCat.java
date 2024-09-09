package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCat extends ModelAdapterOcelot {
   public ModelAdapterCat() {
      super(EntityType.f_20553_, "cat", 0.4F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new CatModel(bakeModelLayer(ModelLayers.f_171272_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CatRenderer render = new CatRenderer(renderManager.getContext());
      render.f_115290_ = (CatModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
