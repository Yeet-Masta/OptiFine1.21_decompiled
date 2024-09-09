package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterWitherSkull extends ModelAdapter {
   public ModelAdapterWitherSkull() {
      super(EntityType.f_20498_, "wither_skull", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SkullModel(bakeModelLayer(ModelLayers.f_171220_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof SkullModel modelSkeletonHead)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 1);
      } else {
         return modelPart.equals("root") ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 0) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WitherSkullRenderer render = new WitherSkullRenderer(renderManager.getContext());
      if (!Reflector.RenderWitherSkull_model.exists()) {
         Config.warn("Field not found: RenderWitherSkull_model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderWitherSkull_model, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
