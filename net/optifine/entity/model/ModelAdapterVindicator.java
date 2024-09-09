package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterVindicator extends ModelAdapterIllager {
   public ModelAdapterVindicator() {
      super(EntityType.f_20493_, "vindicator", 0.5F, new String[]{"vindication_illager"});
   }

   public Model makeModel() {
      return new IllagerModel(bakeModelLayer(ModelLayers.f_171211_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      VindicatorRenderer render = new VindicatorRenderer(renderManager.getContext());
      render.f_115290_ = (IllagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
