package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.optifine.Config;

public class ModelAdapterHorseArmor extends ModelAdapterHorse {
   public ModelAdapterHorseArmor() {
      super(EntityType.f_20457_, "horse_armor", 0.75F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new HorseModel(bakeModelLayer(ModelLayers.f_171187_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      HorseRenderer customRenderer = new HorseRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new HorseModel(bakeModelLayer(ModelLayers.f_171187_));
      customRenderer.f_114477_ = 0.75F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20457_, index, () -> customRenderer);
      if (!(render instanceof HorseRenderer renderHorse)) {
         Config.warn("Not a HorseRenderer: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.HorseArmorLayer layer = new net.minecraft.client.renderer.entity.layers.HorseArmorLayer(
            renderHorse, renderManager.getContext().m_174027_()
         );
         layer.f_117017_ = (HorseModel<Horse>)modelBase;
         renderHorse.removeLayers(net.minecraft.client.renderer.entity.layers.HorseArmorLayer.class);
         renderHorse.m_115326_(layer);
         return renderHorse;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      HorseRenderer renderer = (HorseRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.HorseArmorLayer layer : renderer.getLayers(
         net.minecraft.client.renderer.entity.layers.HorseArmorLayer.class
      )) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
