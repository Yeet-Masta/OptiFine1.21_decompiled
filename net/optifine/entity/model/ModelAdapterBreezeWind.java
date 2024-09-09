package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.optifine.Config;

public class ModelAdapterBreezeWind extends ModelAdapterBreeze {
   public ModelAdapterBreezeWind() {
      super(EntityType.f_302782_, "breeze_wind", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new BreezeModel(BreezeModel.m_304895_(128, 128).m_171564_());
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BreezeRenderer customRenderer = new BreezeRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new BreezeModel(bakeModelLayer(ModelLayers.f_303100_));
      customRenderer.f_114477_ = 0.0F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_302782_, index, () -> customRenderer);
      if (!(render instanceof BreezeRenderer renderBreeze)) {
         Config.warn("Not a RenderBreeze: " + render);
         return null;
      } else {
         net.minecraft.resources.ResourceLocation locTex = modelBase.locationTextureCustom != null
            ? modelBase.locationTextureCustom
            : new net.minecraft.resources.ResourceLocation("textures/entity/breeze/breeze_wind.png");
         net.minecraft.client.renderer.entity.layers.BreezeWindLayer layer = new net.minecraft.client.renderer.entity.layers.BreezeWindLayer(
            renderManager.getContext(), renderBreeze
         );
         layer.setModel((BreezeModel<Breeze>)modelBase);
         layer.setTextureLocation(locTex);
         renderBreeze.removeLayers(net.minecraft.client.renderer.entity.layers.BreezeWindLayer.class);
         renderBreeze.m_115326_(layer);
         return renderBreeze;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      return true;
   }
}
