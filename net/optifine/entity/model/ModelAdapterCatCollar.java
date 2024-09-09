package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.optifine.Config;

public class ModelAdapterCatCollar extends ModelAdapterOcelot {
   public ModelAdapterCatCollar() {
      super(EntityType.f_20553_, "cat_collar", 0.4F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new CatModel(bakeModelLayer(ModelLayers.f_171273_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CatRenderer customRenderer = new CatRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new CatModel(bakeModelLayer(ModelLayers.f_171273_));
      customRenderer.f_114477_ = 0.4F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20553_, index, () -> customRenderer);
      if (!(render instanceof CatRenderer renderCat)) {
         Config.warn("Not a RenderCat: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.CatCollarLayer layer = new net.minecraft.client.renderer.entity.layers.CatCollarLayer(
            renderCat, renderManager.getContext().m_174027_()
         );
         layer.f_116650_ = (CatModel<Cat>)modelBase;
         renderCat.removeLayers(net.minecraft.client.renderer.entity.layers.CatCollarLayer.class);
         renderCat.m_115326_(layer);
         return renderCat;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      CatRenderer renderCat = (CatRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.CatCollarLayer layer : renderCat.getLayers(
         net.minecraft.client.renderer.entity.layers.CatCollarLayer.class
      )) {
         layer.f_116650_.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
