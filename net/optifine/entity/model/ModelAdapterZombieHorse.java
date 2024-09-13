package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterZombieHorse extends ModelAdapterHorse {
   public ModelAdapterZombieHorse() {
      super(EntityType.f_20502_, "zombie_horse", 0.75F);
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      UndeadHorseRenderer render = new UndeadHorseRenderer(renderManager.getContext(), ModelLayers.f_171225_);
      render.f_115290_ = (HorseModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
