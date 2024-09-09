package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterDrownedOuter extends ModelAdapterDrowned {
   public ModelAdapterDrownedOuter() {
      super(EntityType.f_20562_, "drowned_outer", 0.5F);
   }

   public Model makeModel() {
      return new DrownedModel(bakeModelLayer(ModelLayers.f_171139_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      DrownedRenderer customRenderer = new DrownedRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new DrownedModel(bakeModelLayer(ModelLayers.f_171139_));
      customRenderer.f_114477_ = 0.75F;
      EntityRenderer render = rendererCache.get(EntityType.f_20562_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof DrownedRenderer renderDrowned)) {
         Config.warn("Not a DrownedRenderer: " + String.valueOf(render));
         return null;
      } else {
         DrownedOuterLayer layer = new DrownedOuterLayer(renderDrowned, renderManager.getContext().m_174027_());
         layer.f_116908_ = (DrownedModel)modelBase;
         renderDrowned.removeLayers(DrownedOuterLayer.class);
         renderDrowned.m_115326_(layer);
         return renderDrowned;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      DrownedRenderer renderer = (DrownedRenderer)er;
      List layers = renderer.getLayers(DrownedOuterLayer.class);

      DrownedOuterLayer layer;
      for(Iterator var5 = layers.iterator(); var5.hasNext(); layer.customTextureLocation = textureLocation) {
         layer = (DrownedOuterLayer)var5.next();
      }

      return true;
   }
}
