package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.optifine.Config;

public class ModelAdapterCreeperCharge extends ModelAdapterCreeper {
   public ModelAdapterCreeperCharge() {
      super(EntityType.f_20558_, "creeper_charge", 0.25F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new CreeperModel(bakeModelLayer(ModelLayers.f_171129_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CreeperRenderer customRenderer = new CreeperRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new CreeperModel(bakeModelLayer(ModelLayers.f_171129_));
      customRenderer.f_114477_ = 0.25F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20558_, index, () -> customRenderer);
      if (!(render instanceof CreeperRenderer renderCreeper)) {
         Config.warn("Not a CreeperRenderer: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.CreeperPowerLayer layer = new net.minecraft.client.renderer.entity.layers.CreeperPowerLayer(
            renderCreeper, renderManager.getContext().m_174027_()
         );
         layer.f_116677_ = (CreeperModel<Creeper>)modelBase;
         renderCreeper.removeLayers(net.minecraft.client.renderer.entity.layers.CreeperPowerLayer.class);
         renderCreeper.m_115326_(layer);
         return renderCreeper;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      CreeperRenderer renderer = (CreeperRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.CreeperPowerLayer layer : renderer.getLayers(
         net.minecraft.client.renderer.entity.layers.CreeperPowerLayer.class
      )) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
