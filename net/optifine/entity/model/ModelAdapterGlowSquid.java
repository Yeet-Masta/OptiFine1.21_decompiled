package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.GlowSquidRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterGlowSquid extends ModelAdapterSquid {
   public ModelAdapterGlowSquid() {
      super(EntityType.f_147034_, "glow_squid", 0.7F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SquidModel(bakeModelLayer(ModelLayers.f_171154_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      GlowSquidRenderer render = new GlowSquidRenderer(renderManager.getContext(), (SquidModel)modelBase);
      render.f_115290_ = (SquidModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
