package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterGuardian extends ModelAdapter {
   public ModelAdapterGuardian() {
      super(EntityType.f_20455_, "guardian", 0.5F);
   }

   public ModelAdapterGuardian(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public Model makeModel() {
      return new GuardianModel(bakeModelLayer(ModelLayers.f_171183_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof GuardianModel modelGuardian)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelGuardian.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("eye")) {
         return modelGuardian.m_142109_().getChildModelDeep("eye");
      } else {
         String PREFIX_SPINE = "spine";
         String PREFIX_TAIL;
         int index;
         if (modelPart.startsWith(PREFIX_SPINE)) {
            PREFIX_TAIL = StrUtils.removePrefix(modelPart, PREFIX_SPINE);
            int index = Config.parseInt(PREFIX_TAIL, -1);
            index = index - 1;
            return modelGuardian.m_142109_().getChildModelDeep("spike" + index);
         } else {
            PREFIX_TAIL = "tail";
            if (modelPart.startsWith(PREFIX_TAIL)) {
               String numStr = StrUtils.removePrefix(modelPart, PREFIX_TAIL);
               index = Config.parseInt(numStr, -1);
               int indexPart = index - 1;
               return modelGuardian.m_142109_().getChildModelDeep("tail" + indexPart);
            } else {
               return modelPart.equals("root") ? modelGuardian.m_142109_() : null;
            }
         }
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "eye", "spine1", "spine2", "spine3", "spine4", "spine5", "spine6", "spine7", "spine8", "spine9", "spine10", "spine11", "spine12", "tail1", "tail2", "tail3", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      GuardianRenderer render = new GuardianRenderer(renderManager.getContext());
      render.f_115290_ = (GuardianModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
