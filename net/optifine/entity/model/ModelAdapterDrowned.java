package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterDrowned extends ModelAdapterZombie {
   public ModelAdapterDrowned() {
      super(EntityType.f_20562_, "drowned", 0.5F);
   }

   public ModelAdapterDrowned(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public Model makeModel() {
      return new DrownedModel(bakeModelLayer(ModelLayers.f_171136_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      DrownedRenderer render = new DrownedRenderer(renderManager.getContext());
      render.f_115290_ = (DrownedModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
