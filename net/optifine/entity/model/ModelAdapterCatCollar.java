package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.optifine.Config;

public class ModelAdapterCatCollar extends ModelAdapterOcelot {
   public ModelAdapterCatCollar() {
      super(EntityType.f_20553_, "cat_collar", 0.4F);
   }

   @Override
   public Model makeModel() {
      return new CatModel(bakeModelLayer(ModelLayers.f_171273_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CatRenderer customRenderer = new CatRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new CatModel(bakeModelLayer(ModelLayers.f_171273_));
      customRenderer.f_114477_ = 0.4F;
      EntityRenderer render = rendererCache.get(EntityType.f_20553_, index, () -> customRenderer);
      if (!(render instanceof CatRenderer renderCat)) {
         Config.warn("Not a RenderCat: " + render);
         return null;
      } else {
         CatCollarLayer layer = new CatCollarLayer(renderCat, renderManager.getContext().m_174027_());
         layer.f_116650_ = (CatModel<Cat>)modelBase;
         renderCat.removeLayers(CatCollarLayer.class);
         renderCat.m_115326_(layer);
         return renderCat;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      CatRenderer renderCat = (CatRenderer)er;

      for (CatCollarLayer layer : renderCat.getLayers(CatCollarLayer.class)) {
         layer.f_116650_.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
