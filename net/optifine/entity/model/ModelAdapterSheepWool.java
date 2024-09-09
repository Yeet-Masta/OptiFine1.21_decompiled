package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
   public ModelAdapterSheepWool() {
      super(EntityType.f_20520_, "sheep_wool", 0.7F);
   }

   public Model makeModel() {
      return new SheepFurModel(bakeModelLayer(ModelLayers.f_171178_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SheepRenderer customRenderer = new SheepRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new SheepModel(bakeModelLayer(ModelLayers.f_171178_));
      customRenderer.f_114477_ = 0.7F;
      EntityRenderer render = rendererCache.get(EntityType.f_20520_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof SheepRenderer renderSheep)) {
         Config.warn("Not a RenderSheep: " + String.valueOf(render));
         return null;
      } else {
         SheepFurLayer layer = new SheepFurLayer(renderSheep, renderManager.getContext().m_174027_());
         layer.f_117405_ = (SheepFurModel)modelBase;
         renderSheep.removeLayers(SheepFurLayer.class);
         renderSheep.m_115326_(layer);
         return renderSheep;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      SheepRenderer renderSheep = (SheepRenderer)er;
      List layers = renderSheep.getLayers(SheepFurLayer.class);

      SheepFurLayer layer;
      for(Iterator var5 = layers.iterator(); var5.hasNext(); layer.f_117405_.locationTextureCustom = textureLocation) {
         layer = (SheepFurLayer)var5.next();
      }

      return true;
   }
}
