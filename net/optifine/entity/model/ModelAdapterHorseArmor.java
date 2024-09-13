package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.optifine.Config;

public class ModelAdapterHorseArmor extends ModelAdapterHorse {
   public ModelAdapterHorseArmor() {
      super(EntityType.f_20457_, "horse_armor", 0.75F);
   }

   @Override
   public Model makeModel() {
      return new HorseModel(bakeModelLayer(ModelLayers.f_171187_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      HorseRenderer customRenderer = new HorseRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new HorseModel(bakeModelLayer(ModelLayers.f_171187_));
      customRenderer.f_114477_ = 0.75F;
      EntityRenderer render = rendererCache.get(EntityType.f_20457_, index, () -> customRenderer);
      if (!(render instanceof HorseRenderer renderHorse)) {
         Config.warn("Not a HorseRenderer: " + render);
         return null;
      } else {
         HorseArmorLayer layer = new HorseArmorLayer(renderHorse, renderManager.getContext().m_174027_());
         layer.f_117017_ = (HorseModel<Horse>)modelBase;
         renderHorse.removeLayers(HorseArmorLayer.class);
         renderHorse.m_115326_(layer);
         return renderHorse;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      HorseRenderer renderer = (HorseRenderer)er;

      for (HorseArmorLayer layer : renderer.getLayers(HorseArmorLayer.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
