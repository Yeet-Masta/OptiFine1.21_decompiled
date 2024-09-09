package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCat extends ModelAdapterOcelot {
   public ModelAdapterCat() {
      super(EntityType.f_20553_, "cat", 0.4F);
   }

   public Model makeModel() {
      return new CatModel(bakeModelLayer(ModelLayers.f_171272_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CatRenderer render = new CatRenderer(renderManager.getContext());
      render.f_115290_ = (CatModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
