package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MushroomCowRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterMooshroom extends ModelAdapterQuadruped {
   public ModelAdapterMooshroom() {
      super(EntityType.f_20504_, "mooshroom", 0.7F);
   }

   @Override
   public Model makeModel() {
      return new CowModel(bakeModelLayer(ModelLayers.f_171199_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      MushroomCowRenderer render = new MushroomCowRenderer(renderManager.getContext());
      render.f_115290_ = (CowModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
