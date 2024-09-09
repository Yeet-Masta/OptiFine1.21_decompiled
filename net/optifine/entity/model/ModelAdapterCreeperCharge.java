package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterCreeperCharge extends ModelAdapterCreeper {
   public ModelAdapterCreeperCharge() {
      super(EntityType.f_20558_, "creeper_charge", 0.25F);
   }

   public Model makeModel() {
      return new CreeperModel(bakeModelLayer(ModelLayers.f_171129_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CreeperRenderer customRenderer = new CreeperRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new CreeperModel(bakeModelLayer(ModelLayers.f_171129_));
      customRenderer.f_114477_ = 0.25F;
      EntityRenderer render = rendererCache.get(EntityType.f_20558_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof CreeperRenderer renderCreeper)) {
         Config.warn("Not a CreeperRenderer: " + String.valueOf(render));
         return null;
      } else {
         CreeperPowerLayer layer = new CreeperPowerLayer(renderCreeper, renderManager.getContext().m_174027_());
         layer.f_116677_ = (CreeperModel)modelBase;
         renderCreeper.removeLayers(CreeperPowerLayer.class);
         renderCreeper.m_115326_(layer);
         return renderCreeper;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      CreeperRenderer renderer = (CreeperRenderer)er;
      List layers = renderer.getLayers(CreeperPowerLayer.class);

      CreeperPowerLayer layer;
      for(Iterator var5 = layers.iterator(); var5.hasNext(); layer.customTextureLocation = textureLocation) {
         layer = (CreeperPowerLayer)var5.next();
      }

      return true;
   }
}
