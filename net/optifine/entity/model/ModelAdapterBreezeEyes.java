package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.BreezeEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.optifine.Config;

public class ModelAdapterBreezeEyes extends ModelAdapterBreeze {
   public ModelAdapterBreezeEyes() {
      super(EntityType.f_302782_, "breeze_eyes", 0.0F);
   }

   @Override
   public Model makeModel() {
      return new BreezeModel(bakeModelLayer(ModelLayers.f_303100_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BreezeRenderer customRenderer = new BreezeRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new BreezeModel(bakeModelLayer(ModelLayers.f_303100_));
      customRenderer.f_114477_ = 0.0F;
      EntityRenderer render = rendererCache.get(EntityType.f_302782_, index, () -> customRenderer);
      if (!(render instanceof BreezeRenderer renderBreeze)) {
         Config.warn("Not a RenderBreeze: " + render);
         return null;
      } else {
         ResourceLocation locTex = modelBase.locationTextureCustom != null
            ? modelBase.locationTextureCustom
            : new ResourceLocation("textures/entity/breeze/breeze.png");
         BreezeEyesLayer layer = new BreezeEyesLayer(renderBreeze);
         layer.setModel((BreezeModel<Breeze>)modelBase);
         layer.setTextureLocation(locTex);
         renderBreeze.removeLayers(BreezeEyesLayer.class);
         renderBreeze.m_115326_(layer);
         return renderBreeze;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      return true;
   }
}
