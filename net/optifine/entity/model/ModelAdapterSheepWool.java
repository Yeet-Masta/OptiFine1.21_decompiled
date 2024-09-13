package net.optifine.entity.model;

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
import net.minecraft.world.entity.animal.Sheep;
import net.optifine.Config;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
   public ModelAdapterSheepWool() {
      super(EntityType.f_20520_, "sheep_wool", 0.7F);
   }

   @Override
   public Model makeModel() {
      return new SheepFurModel(bakeModelLayer(ModelLayers.f_171178_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SheepRenderer customRenderer = new SheepRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new SheepModel(bakeModelLayer(ModelLayers.f_171178_));
      customRenderer.f_114477_ = 0.7F;
      EntityRenderer render = rendererCache.get(EntityType.f_20520_, index, () -> customRenderer);
      if (!(render instanceof SheepRenderer renderSheep)) {
         Config.warn("Not a RenderSheep: " + render);
         return null;
      } else {
         SheepFurLayer layer = new SheepFurLayer(renderSheep, renderManager.getContext().m_174027_());
         layer.f_117405_ = (SheepFurModel<Sheep>)modelBase;
         renderSheep.removeLayers(SheepFurLayer.class);
         renderSheep.m_115326_(layer);
         return renderSheep;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      SheepRenderer renderSheep = (SheepRenderer)er;

      for (SheepFurLayer layer : renderSheep.getLayers(SheepFurLayer.class)) {
         layer.f_117405_.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
