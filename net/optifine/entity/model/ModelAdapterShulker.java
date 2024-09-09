package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulker extends ModelAdapter {
   public ModelAdapterShulker() {
      super(EntityType.f_20521_, "shulker", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ShulkerModel(bakeModelLayer(ModelLayers.f_171180_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ShulkerModel modelShulker)) {
         return null;
      } else if (modelPart.equals("base")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 0);
      } else if (modelPart.equals("lid")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 1);
      } else {
         return modelPart.equals("head") ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 2) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"base", "lid", "head"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ShulkerRenderer render = new ShulkerRenderer(renderManager.getContext());
      render.f_115290_ = (ShulkerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
