package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSlime extends ModelAdapter {
   public ModelAdapterSlime() {
      super(EntityType.f_20526_, "slime", 0.25F);
   }

   public ModelAdapterSlime(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SlimeModel(bakeModelLayer(ModelLayers.f_171241_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof SlimeModel modelSlime)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelSlime.m_142109_().getChildModelDeep("cube");
      } else if (modelPart.equals("left_eye")) {
         return modelSlime.m_142109_().getChildModelDeep("left_eye");
      } else if (modelPart.equals("right_eye")) {
         return modelSlime.m_142109_().getChildModelDeep("right_eye");
      } else if (modelPart.equals("mouth")) {
         return modelSlime.m_142109_().getChildModelDeep("mouth");
      } else {
         return modelPart.equals("root") ? modelSlime.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "left_eye", "right_eye", "mouth", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SlimeRenderer render = new SlimeRenderer(renderManager.getContext());
      render.f_115290_ = (SlimeModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
