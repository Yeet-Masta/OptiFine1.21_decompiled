package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
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
import net.optifine.Config;

public class ModelAdapterCatCollar extends ModelAdapterOcelot {
   public ModelAdapterCatCollar() {
      super(EntityType.f_20553_, "cat_collar", 0.4F);
   }

   public Model makeModel() {
      return new CatModel(bakeModelLayer(ModelLayers.f_171273_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CatRenderer customRenderer = new CatRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new CatModel(bakeModelLayer(ModelLayers.f_171273_));
      customRenderer.f_114477_ = 0.4F;
      EntityRenderer render = rendererCache.get(EntityType.f_20553_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof CatRenderer renderCat)) {
         Config.warn("Not a RenderCat: " + String.valueOf(render));
         return null;
      } else {
         CatCollarLayer layer = new CatCollarLayer(renderCat, renderManager.getContext().m_174027_());
         layer.f_116650_ = (CatModel)modelBase;
         renderCat.removeLayers(CatCollarLayer.class);
         renderCat.m_115326_(layer);
         return renderCat;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      CatRenderer renderCat = (CatRenderer)er;
      List layers = renderCat.getLayers(CatCollarLayer.class);

      CatCollarLayer layer;
      for(Iterator var5 = layers.iterator(); var5.hasNext(); layer.f_116650_.locationTextureCustom = textureLocation) {
         layer = (CatCollarLayer)var5.next();
      }

      return true;
   }
}
