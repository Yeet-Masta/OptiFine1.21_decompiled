package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSnowman extends ModelAdapter {
   public ModelAdapterSnowman() {
      super(EntityType.f_20528_, "snow_golem", 0.5F);
   }

   @Override
   public Model makeModel() {
      return new SnowGolemModel(bakeModelLayer(ModelLayers.f_171243_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof SnowGolemModel modelSnowman)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelSnowman.m_142109_().getChildModelDeep("upper_body");
      } else if (modelPart.equals("body_bottom")) {
         return modelSnowman.m_142109_().getChildModelDeep("lower_body");
      } else if (modelPart.equals("head")) {
         return modelSnowman.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("right_hand")) {
         return modelSnowman.m_142109_().getChildModelDeep("right_arm");
      } else if (modelPart.equals("left_hand")) {
         return modelSnowman.m_142109_().getChildModelDeep("left_arm");
      } else {
         return modelPart.equals("root") ? modelSnowman.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "body_bottom", "head", "right_hand", "left_hand", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SnowGolemRenderer render = new SnowGolemRenderer(renderManager.getContext());
      render.f_115290_ = (SnowGolemModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
