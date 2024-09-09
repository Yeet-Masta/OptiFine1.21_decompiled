package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GlowSquidRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterGlowSquid extends ModelAdapterSquid {
   public ModelAdapterGlowSquid() {
      super(EntityType.f_147034_, "glow_squid", 0.7F);
   }

   public Model makeModel() {
      return new SquidModel(bakeModelLayer(ModelLayers.f_171154_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      GlowSquidRenderer render = new GlowSquidRenderer(renderManager.getContext(), (SquidModel)modelBase);
      render.f_115290_ = (SquidModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
