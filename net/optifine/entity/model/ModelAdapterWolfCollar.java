package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.optifine.Config;

public class ModelAdapterWolfCollar extends ModelAdapterWolf {
   public ModelAdapterWolfCollar() {
      super(EntityType.f_20499_, "wolf_collar", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new WolfModel(bakeModelLayer(ModelLayers.f_171221_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WolfRenderer customRenderer = new WolfRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new WolfModel(bakeModelLayer(ModelLayers.f_171221_));
      customRenderer.f_114477_ = 0.5F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20499_, index, () -> customRenderer);
      if (!(render instanceof WolfRenderer renderWolf)) {
         Config.warn("Not a RenderWolf: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.WolfCollarLayer layer = new net.minecraft.client.renderer.entity.layers.WolfCollarLayer(renderWolf);
         layer.model = (WolfModel<Wolf>)modelBase;
         renderWolf.removeLayers(net.minecraft.client.renderer.entity.layers.WolfCollarLayer.class);
         renderWolf.m_115326_(layer);
         return renderWolf;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      WolfRenderer renderWolf = (WolfRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.WolfCollarLayer layer : renderWolf.getLayers(
         net.minecraft.client.renderer.entity.layers.WolfCollarLayer.class
      )) {
         layer.model.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
