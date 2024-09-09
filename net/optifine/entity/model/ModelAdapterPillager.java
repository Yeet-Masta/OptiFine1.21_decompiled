package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterPillager extends ModelAdapterIllager {
   public ModelAdapterPillager() {
      super(EntityType.f_20513_, "pillager", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new IllagerModel(bakeModelLayer(ModelLayers.f_171161_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PillagerRenderer render = new PillagerRenderer(renderManager.getContext());
      render.f_115290_ = (IllagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
