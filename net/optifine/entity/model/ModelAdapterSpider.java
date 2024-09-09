package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSpider extends ModelAdapter {
   public ModelAdapterSpider() {
      super(EntityType.f_20479_, "spider", 1.0F);
   }

   protected ModelAdapterSpider(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SpiderModel(bakeModelLayer(ModelLayers.f_171245_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof SpiderModel modelSpider)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelSpider.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("neck")) {
         return modelSpider.m_142109_().getChildModelDeep("body0");
      } else if (modelPart.equals("body")) {
         return modelSpider.m_142109_().getChildModelDeep("body1");
      } else if (modelPart.equals("leg1")) {
         return modelSpider.m_142109_().getChildModelDeep("right_hind_leg");
      } else if (modelPart.equals("leg2")) {
         return modelSpider.m_142109_().getChildModelDeep("left_hind_leg");
      } else if (modelPart.equals("leg3")) {
         return modelSpider.m_142109_().getChildModelDeep("right_middle_hind_leg");
      } else if (modelPart.equals("leg4")) {
         return modelSpider.m_142109_().getChildModelDeep("left_middle_hind_leg");
      } else if (modelPart.equals("leg5")) {
         return modelSpider.m_142109_().getChildModelDeep("right_middle_front_leg");
      } else if (modelPart.equals("leg6")) {
         return modelSpider.m_142109_().getChildModelDeep("left_middle_front_leg");
      } else if (modelPart.equals("leg7")) {
         return modelSpider.m_142109_().getChildModelDeep("right_front_leg");
      } else if (modelPart.equals("leg8")) {
         return modelSpider.m_142109_().getChildModelDeep("left_front_leg");
      } else {
         return modelPart.equals("root") ? modelSpider.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", "leg8", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SpiderRenderer render = new SpiderRenderer(renderManager.getContext());
      render.f_115290_ = (EntityModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
