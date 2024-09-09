package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GoatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GoatRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterGoat extends ModelAdapterQuadruped {
   public ModelAdapterGoat() {
      super(EntityType.f_147035_, "goat", 0.7F);
   }

   public Model makeModel() {
      return new GoatModel(bakeModelLayer(ModelLayers.f_171182_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof GoatModel modelGoat)) {
         return null;
      } else {
         ModelPart head = super.getModelRenderer(modelGoat, "head");
         if (head != null) {
            if (modelPart.equals("left_horn")) {
               return head.m_171324_(modelPart);
            }

            if (modelPart.equals("right_horn")) {
               return head.m_171324_(modelPart);
            }

            if (modelPart.equals("nose")) {
               return head.m_171324_(modelPart);
            }
         }

         return super.getModelRenderer(model, modelPart);
      }
   }

   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      names = (String[])Config.addObjectsToArray(names, new String[]{"left_horn", "right_horn", "nose"});
      return names;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      GoatRenderer render = new GoatRenderer(renderManager.getContext());
      render.f_115290_ = (GoatModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
