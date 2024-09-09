package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
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
import net.optifine.Config;

public class ModelAdapterWolfCollar extends ModelAdapterWolf {
   public ModelAdapterWolfCollar() {
      super(EntityType.f_20499_, "wolf_collar", 0.5F);
   }

   public Model makeModel() {
      return new WolfModel(bakeModelLayer(ModelLayers.f_171221_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WolfRenderer customRenderer = new WolfRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new WolfModel(bakeModelLayer(ModelLayers.f_171221_));
      customRenderer.f_114477_ = 0.5F;
      EntityRenderer render = rendererCache.get(EntityType.f_20499_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof WolfRenderer renderWolf)) {
         Config.warn("Not a RenderWolf: " + String.valueOf(render));
         return null;
      } else {
         WolfCollarLayer layer = new WolfCollarLayer(renderWolf);
         layer.model = (WolfModel)modelBase;
         renderWolf.removeLayers(WolfCollarLayer.class);
         renderWolf.m_115326_(layer);
         return renderWolf;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      WolfRenderer renderWolf = (WolfRenderer)er;
      List layers = renderWolf.getLayers(WolfCollarLayer.class);

      WolfCollarLayer layer;
      for(Iterator var5 = layers.iterator(); var5.hasNext(); layer.model.locationTextureCustom = textureLocation) {
         layer = (WolfCollarLayer)var5.next();
      }

      return true;
   }
}
