package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.BreezeWindLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterBreezeWind extends ModelAdapterBreeze {
   public ModelAdapterBreezeWind() {
      super(EntityType.f_302782_, "breeze_wind", 0.0F);
   }

   public Model makeModel() {
      return new BreezeModel(BreezeModel.m_304895_(128, 128).m_171564_());
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BreezeRenderer customRenderer = new BreezeRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new BreezeModel(bakeModelLayer(ModelLayers.f_303100_));
      customRenderer.f_114477_ = 0.0F;
      EntityRenderer render = rendererCache.get(EntityType.f_302782_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof BreezeRenderer renderBreeze)) {
         Config.warn("Not a RenderBreeze: " + String.valueOf(render));
         return null;
      } else {
         ResourceLocation locTex = modelBase.locationTextureCustom != null ? modelBase.locationTextureCustom : new ResourceLocation("textures/entity/breeze/breeze_wind.png");
         BreezeWindLayer layer = new BreezeWindLayer(renderManager.getContext(), renderBreeze);
         layer.setModel((BreezeModel)modelBase);
         layer.setTextureLocation(locTex);
         renderBreeze.removeLayers(BreezeWindLayer.class);
         renderBreeze.m_115326_(layer);
         return renderBreeze;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      return true;
   }
}
