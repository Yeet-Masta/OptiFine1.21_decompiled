package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishA extends ModelAdapter {
   public ModelAdapterTropicalFishA() {
      super(EntityType.f_20489_, "tropical_fish_a", 0.2F);
   }

   public ModelAdapterTropicalFishA(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new TropicalFishModelA(bakeModelLayer(ModelLayers.f_171258_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof TropicalFishModelA modelTropicalFish)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("tail")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("tail");
      } else if (modelPart.equals("fin_right")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("left_fin");
      } else if (modelPart.equals("fin_top")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("top_fin");
      } else {
         return modelPart.equals("root") ? modelTropicalFish.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "tail", "fin_right", "fin_left", "fin_top", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TropicalFishRenderer customRenderer = new TropicalFishRenderer(renderManager.getContext());
      customRenderer.f_114477_ = shadowSize;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20489_, index, () -> customRenderer);
      if (!(render instanceof TropicalFishRenderer renderFish)) {
         Config.warn("Not a TropicalFishRenderer: " + render);
         return null;
      } else if (!Reflector.RenderTropicalFish_modelA.exists()) {
         Config.warn("Model field not found: RenderTropicalFish.modelA");
         return null;
      } else {
         Reflector.RenderTropicalFish_modelA.setValue(renderFish, modelBase);
         return renderFish;
      }
   }
}
