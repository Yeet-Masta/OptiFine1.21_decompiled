package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LlamaSpitModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaSpit extends ModelAdapter {
   public ModelAdapterLlamaSpit() {
      super(EntityType.f_20467_, "llama_spit", 0.0F);
   }

   @Override
   public Model makeModel() {
      return new LlamaSpitModel(bakeModelLayer(ModelLayers.f_171196_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof LlamaSpitModel modelLlamaSpit)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelLlamaSpit.m_142109_().getChildModelDeep("main");
      } else {
         return modelPart.equals("root") ? modelLlamaSpit.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      LlamaSpitRenderer render = new LlamaSpitRenderer(renderManager.getContext());
      if (!Reflector.RenderLlamaSpit_model.exists()) {
         Config.warn("Field not found: RenderLlamaSpit.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderLlamaSpit_model, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
