package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterPiglinBrute extends ModelAdapterPiglin {
   public ModelAdapterPiglinBrute() {
      super(EntityType.f_20512_, "piglin_brute", 0.5F);
   }

   public Model makeModel() {
      return new PiglinModel(bakeModelLayer(ModelLayers.f_171207_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PiglinRenderer render = new PiglinRenderer(renderManager.getContext(), ModelLayers.f_171207_, ModelLayers.f_171156_, ModelLayers.f_171157_, false);
      render.f_115290_ = (PiglinModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
