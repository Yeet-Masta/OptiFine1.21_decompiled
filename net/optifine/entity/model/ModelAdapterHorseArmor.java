package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
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
import net.optifine.Config;

public class ModelAdapterHorseArmor extends ModelAdapterHorse {
   public ModelAdapterHorseArmor() {
      super(EntityType.f_20457_, "horse_armor", 0.75F);
   }

   public Model makeModel() {
      return new HorseModel(bakeModelLayer(ModelLayers.f_171187_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      HorseRenderer customRenderer = new HorseRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new HorseModel(bakeModelLayer(ModelLayers.f_171187_));
      customRenderer.f_114477_ = 0.75F;
      EntityRenderer render = rendererCache.get(EntityType.f_20457_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof HorseRenderer renderHorse)) {
         Config.warn("Not a HorseRenderer: " + String.valueOf(render));
         return null;
      } else {
         HorseArmorLayer layer = new HorseArmorLayer(renderHorse, renderManager.getContext().m_174027_());
         layer.f_117017_ = (HorseModel)modelBase;
         renderHorse.removeLayers(HorseArmorLayer.class);
         renderHorse.m_115326_(layer);
         return renderHorse;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      HorseRenderer renderer = (HorseRenderer)er;
      List layers = renderer.getLayers(HorseArmorLayer.class);

      HorseArmorLayer layer;
      for(Iterator var5 = layers.iterator(); var5.hasNext(); layer.customTextureLocation = textureLocation) {
         layer = (HorseArmorLayer)var5.next();
      }

      return true;
   }
}
