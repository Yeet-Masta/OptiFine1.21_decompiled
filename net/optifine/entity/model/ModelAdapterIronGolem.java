package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterIronGolem extends ModelAdapter {
   public ModelAdapterIronGolem() {
      super(EntityType.f_20460_, "iron_golem", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new IronGolemModel(bakeModelLayer(ModelLayers.f_171192_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof IronGolemModel modelIronGolem)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelIronGolem.m_142109_().m_171324_("head");
      } else if (modelPart.equals("body")) {
         return modelIronGolem.m_142109_().m_171324_("body");
      } else if (modelPart.equals("right_arm")) {
         return modelIronGolem.m_142109_().m_171324_("right_arm");
      } else if (modelPart.equals("left_arm")) {
         return modelIronGolem.m_142109_().m_171324_("left_arm");
      } else if (modelPart.equals("left_leg")) {
         return modelIronGolem.m_142109_().m_171324_("left_leg");
      } else if (modelPart.equals("right_leg")) {
         return modelIronGolem.m_142109_().m_171324_("right_leg");
      } else {
         return modelPart.equals("root") ? modelIronGolem.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_arm", "left_arm", "left_leg", "right_leg", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      IronGolemRenderer render = new IronGolemRenderer(renderManager.getContext());
      render.f_115290_ = (IronGolemModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
