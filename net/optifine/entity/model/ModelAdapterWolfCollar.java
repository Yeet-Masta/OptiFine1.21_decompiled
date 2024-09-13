package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.optifine.Config;

public class ModelAdapterWolfCollar extends ModelAdapterWolf {
   public ModelAdapterWolfCollar() {
      super(EntityType.f_20499_, "wolf_collar", 0.5F);
   }

   @Override
   public Model makeModel() {
      return new WolfModel(bakeModelLayer(ModelLayers.f_171221_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WolfRenderer customRenderer = new WolfRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new WolfModel(bakeModelLayer(ModelLayers.f_171221_));
      customRenderer.f_114477_ = 0.5F;
      EntityRenderer render = rendererCache.get(EntityType.f_20499_, index, () -> customRenderer);
      if (!(render instanceof WolfRenderer renderWolf)) {
         Config.warn("Not a RenderWolf: " + render);
         return null;
      } else {
         WolfCollarLayer layer = new WolfCollarLayer(renderWolf);
         layer.model = (WolfModel<Wolf>)modelBase;
         renderWolf.removeLayers(WolfCollarLayer.class);
         renderWolf.m_115326_(layer);
         return renderWolf;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      WolfRenderer renderWolf = (WolfRenderer)er;

      for (WolfCollarLayer layer : renderWolf.getLayers(WolfCollarLayer.class)) {
         layer.model.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
