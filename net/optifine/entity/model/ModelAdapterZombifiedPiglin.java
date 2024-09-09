package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterZombifiedPiglin extends ModelAdapterPiglin {
   public ModelAdapterZombifiedPiglin() {
      super(EntityType.f_20531_, "zombified_piglin", 0.5F);
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PiglinRenderer render = new PiglinRenderer(renderManager.getContext(), ModelLayers.f_171231_, ModelLayers.f_171232_, ModelLayers.f_171233_, true);
      render.f_115290_ = (PiglinModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
