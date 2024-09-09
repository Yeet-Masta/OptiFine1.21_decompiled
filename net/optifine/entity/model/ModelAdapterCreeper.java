package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCreeper extends ModelAdapter {
   public ModelAdapterCreeper() {
      super(EntityType.f_20558_, "creeper", 0.5F);
   }

   public ModelAdapterCreeper(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new CreeperModel(bakeModelLayer(ModelLayers.f_171285_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof CreeperModel modelCreeper)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelCreeper.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("body")) {
         return modelCreeper.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("leg1")) {
         return modelCreeper.m_142109_().getChildModelDeep("right_hind_leg");
      } else if (modelPart.equals("leg2")) {
         return modelCreeper.m_142109_().getChildModelDeep("left_hind_leg");
      } else if (modelPart.equals("leg3")) {
         return modelCreeper.m_142109_().getChildModelDeep("right_front_leg");
      } else if (modelPart.equals("leg4")) {
         return modelCreeper.m_142109_().getChildModelDeep("left_front_leg");
      } else {
         return modelPart.equals("root") ? modelCreeper.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CreeperRenderer render = new CreeperRenderer(renderManager.getContext());
      render.f_115290_ = (CreeperModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
