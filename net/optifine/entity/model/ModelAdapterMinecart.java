package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecart extends ModelAdapter {
   public ModelAdapterMinecart() {
      super(EntityType.f_20469_, "minecart", 0.5F);
   }

   protected ModelAdapterMinecart(EntityType type, String name, float shadow) {
      super(type, name, shadow);
   }

   public Model makeModel() {
      return new MinecartModel(bakeModelLayer(ModelLayers.f_171198_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof MinecartModel modelMinecart)) {
         return null;
      } else if (modelPart.equals("bottom")) {
         return modelMinecart.m_142109_().getChildModelDeep("bottom");
      } else if (modelPart.equals("back")) {
         return modelMinecart.m_142109_().getChildModelDeep("back");
      } else if (modelPart.equals("front")) {
         return modelMinecart.m_142109_().getChildModelDeep("front");
      } else if (modelPart.equals("right")) {
         return modelMinecart.m_142109_().getChildModelDeep("right");
      } else if (modelPart.equals("left")) {
         return modelMinecart.m_142109_().getChildModelDeep("left");
      } else {
         return modelPart.equals("root") ? modelMinecart.m_142109_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"bottom", "back", "front", "right", "left", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      MinecartRenderer render = new MinecartRenderer(renderManager.getContext(), ModelLayers.f_171198_);
      if (!Reflector.RenderMinecart_modelMinecart.exists()) {
         Config.warn("Field not found: RenderMinecart.modelMinecart");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
