package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterSlimeOuter extends ModelAdapter {
   public ModelAdapterSlimeOuter() {
      super(EntityType.f_20526_, "slime_outer", 0.25F);
   }

   @Override
   public Model makeModel() {
      return new SlimeModel(bakeModelLayer(ModelLayers.f_171242_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof SlimeModel modelSlime)) {
         return null;
      } else {
         return modelPart.equals("body") ? modelSlime.m_142109_().getChildModelDeep("cube") : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SlimeRenderer customRenderer = new SlimeRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new SlimeModel(bakeModelLayer(ModelLayers.f_171242_));
      customRenderer.f_114477_ = 0.25F;
      EntityRenderer render = rendererCache.get(EntityType.f_20526_, index, () -> customRenderer);
      if (!(render instanceof SlimeRenderer renderSlime)) {
         Config.warn("Not a SlimeRenderer: " + render);
         return null;
      } else {
         SlimeOuterLayer layer = new SlimeOuterLayer(renderSlime, renderManager.getContext().m_174027_());
         layer.f_117455_ = (SlimeModel)modelBase;
         renderSlime.removeLayers(SlimeOuterLayer.class);
         renderSlime.m_115326_(layer);
         return renderSlime;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      SlimeRenderer renderer = (SlimeRenderer)er;

      for (SlimeOuterLayer layer : renderer.getLayers(SlimeOuterLayer.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
