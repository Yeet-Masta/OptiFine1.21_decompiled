package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.optifine.Config;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
   public ModelAdapterSheepWool() {
      super(EntityType.f_20520_, "sheep_wool", 0.7F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SheepFurModel(bakeModelLayer(ModelLayers.f_171178_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SheepRenderer customRenderer = new SheepRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new SheepModel(bakeModelLayer(ModelLayers.f_171178_));
      customRenderer.f_114477_ = 0.7F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20520_, index, () -> customRenderer);
      if (!(render instanceof SheepRenderer renderSheep)) {
         Config.warn("Not a RenderSheep: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.SheepFurLayer layer = new net.minecraft.client.renderer.entity.layers.SheepFurLayer(
            renderSheep, renderManager.getContext().m_174027_()
         );
         layer.f_117405_ = (SheepFurModel<Sheep>)modelBase;
         renderSheep.removeLayers(net.minecraft.client.renderer.entity.layers.SheepFurLayer.class);
         renderSheep.m_115326_(layer);
         return renderSheep;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      SheepRenderer renderSheep = (SheepRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.SheepFurLayer layer : renderSheep.getLayers(
         net.minecraft.client.renderer.entity.layers.SheepFurLayer.class
      )) {
         layer.f_117405_.locationTextureCustom = textureLocation;
      }

      return true;
   }
}
