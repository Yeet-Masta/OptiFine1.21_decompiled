package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.optifine.Config;

public class ModelAdapterWitherArmor extends ModelAdapterWither {
   public ModelAdapterWitherArmor() {
      super(EntityType.f_20496_, "wither_armor", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new WitherBossModel(bakeModelLayer(ModelLayers.f_171215_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WitherBossRenderer customRenderer = new WitherBossRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new WitherBossModel(bakeModelLayer(ModelLayers.f_171215_));
      customRenderer.f_114477_ = 0.5F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20496_, index, () -> customRenderer);
      if (!(render instanceof WitherBossRenderer renderWither)) {
         Config.warn("Not a WitherRenderer: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.WitherArmorLayer layer = new net.minecraft.client.renderer.entity.layers.WitherArmorLayer(
            renderWither, renderManager.getContext().m_174027_()
         );
         layer.f_117696_ = (WitherBossModel<WitherBoss>)modelBase;
         renderWither.removeLayers(net.minecraft.client.renderer.entity.layers.WitherArmorLayer.class);
         renderWither.m_115326_(layer);
         return renderWither;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      WitherBossRenderer renderer = (WitherBossRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.WitherArmorLayer layer : renderer.getLayers(
         net.minecraft.client.renderer.entity.layers.WitherArmorLayer.class
      )) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
