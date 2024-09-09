package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBullet extends ModelAdapter {
   public ModelAdapterShulkerBullet() {
      super(EntityType.f_20522_, "shulker_bullet", 0.0F);
   }

   public Model makeModel() {
      return new ShulkerBulletModel(bakeModelLayer(ModelLayers.f_171181_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof ShulkerBulletModel modelShulkerBullet)) {
         return null;
      } else if (modelPart.equals("bullet")) {
         return modelShulkerBullet.m_142109_().getChildModelDeep("main");
      } else {
         return modelPart.equals("root") ? modelShulkerBullet.m_142109_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"bullet", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ShulkerBulletRenderer render = new ShulkerBulletRenderer(renderManager.getContext());
      if (!Reflector.RenderShulkerBullet_model.exists()) {
         Config.warn("Field not found: RenderShulkerBullet.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderShulkerBullet_model, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
