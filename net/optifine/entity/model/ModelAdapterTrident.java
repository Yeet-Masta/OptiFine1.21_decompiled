package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTrident extends ModelAdapter {
   public ModelAdapterTrident() {
      super(EntityType.f_20487_, "trident", 0.0F);
   }

   public Model makeModel() {
      return new TridentModel(bakeModelLayer(ModelLayers.f_171255_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof TridentModel modelTrident)) {
         return null;
      } else {
         ModelPart root = (ModelPart)Reflector.ModelTrident_root.getValue(modelTrident);
         if (root != null) {
            if (modelPart.equals("body")) {
               return root.getChildModelDeep("pole");
            }

            if (modelPart.equals("base")) {
               return root.getChildModelDeep("base");
            }

            if (modelPart.equals("left_spike")) {
               return root.getChildModelDeep("left_spike");
            }

            if (modelPart.equals("middle_spike")) {
               return root.getChildModelDeep("middle_spike");
            }

            if (modelPart.equals("right_spike")) {
               return root.getChildModelDeep("right_spike");
            }
         }

         return modelPart.equals("root") ? root : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "base", "left_spike", "middle_spike", "right_spike", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ThrownTridentRenderer render = new ThrownTridentRenderer(renderManager.getContext());
      if (!Reflector.RenderTrident_modelTrident.exists()) {
         Config.warn("Field not found: RenderTrident.modelTrident");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderTrident_modelTrident, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
