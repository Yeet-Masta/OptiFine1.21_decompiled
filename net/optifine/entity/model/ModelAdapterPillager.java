package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterPillager extends ModelAdapterIllager {
   public ModelAdapterPillager() {
      super(EntityType.f_20513_, "pillager", 0.5F);
   }

   public Model makeModel() {
      return new IllagerModel(bakeModelLayer(ModelLayers.f_171161_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PillagerRenderer render = new PillagerRenderer(renderManager.getContext());
      render.f_115290_ = (IllagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
