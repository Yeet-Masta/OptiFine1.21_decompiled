package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SilverfishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterSilverfish extends ModelAdapter {
   public ModelAdapterSilverfish() {
      super(EntityType.f_20523_, "silverfish", 0.3F);
   }

   @Override
   public Model makeModel() {
      return new SilverfishModel(bakeModelLayer(ModelLayers.f_171235_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof SilverfishModel modelSilverfish)) {
         return null;
      } else {
         String PREFIX_BODY = "body";
         if (modelPart.startsWith(PREFIX_BODY)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelSilverfish.m_142109_().getChildModelDeep("segment" + indexPart);
         } else {
            String PREFIX_WINGS = "wing";
            if (modelPart.startsWith(PREFIX_WINGS)) {
               String numStr = StrUtils.removePrefix(modelPart, PREFIX_WINGS);
               int index = Config.parseInt(numStr, -1);
               int indexPart = index - 1;
               return modelSilverfish.m_142109_().getChildModelDeep("layer" + indexPart);
            } else {
               return modelPart.equals("root") ? modelSilverfish.m_142109_() : null;
            }
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SilverfishRenderer render = new SilverfishRenderer(renderManager.getContext());
      render.f_115290_ = (SilverfishModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
