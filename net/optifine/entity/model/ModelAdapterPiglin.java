package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterPiglin extends ModelAdapterPlayer {
   public ModelAdapterPiglin() {
      super(EntityType.f_20511_, "piglin", 0.5F);
   }

   protected ModelAdapterPiglin(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public Model makeModel() {
      return new PiglinModel(bakeModelLayer(ModelLayers.f_171206_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (model instanceof PiglinModel piglinModel && Reflector.ModelPiglin_ModelRenderers.exists()) {
         if (modelPart.equals("left_ear")) {
            return piglinModel.f_102808_.m_171324_("left_ear");
         }

         if (modelPart.equals("right_ear")) {
            return piglinModel.f_102808_.m_171324_("right_ear");
         }
      }

      return super.getModelRenderer(model, modelPart);
   }

   @Override
   public String[] getModelRendererNames() {
      List<String> names = new ArrayList(Arrays.asList(super.getModelRendererNames()));
      names.add("left_ear");
      names.add("right_ear");
      return (String[])names.toArray(new String[names.size()]);
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PiglinRenderer render = new PiglinRenderer(renderManager.getContext(), ModelLayers.f_171206_, ModelLayers.f_171158_, ModelLayers.f_171159_, false);
      render.f_115290_ = (PiglinModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
