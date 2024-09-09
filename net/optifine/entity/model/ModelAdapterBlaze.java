package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterBlaze extends ModelAdapter {
   public ModelAdapterBlaze() {
      super(EntityType.f_20551_, "blaze", 0.5F);
   }

   public Model makeModel() {
      return new BlazeModel(bakeModelLayer(ModelLayers.f_171270_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof BlazeModel modelBlaze)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelBlaze.m_142109_().getChildModelDeep("head");
      } else {
         String PREFIX_STICK = "stick";
         if (modelPart.startsWith(PREFIX_STICK)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_STICK);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelBlaze.m_142109_().getChildModelDeep("part" + indexPart);
         } else {
            return modelPart.equals("root") ? modelBlaze.m_142109_() : null;
         }
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"head", "stick1", "stick2", "stick3", "stick4", "stick5", "stick6", "stick7", "stick8", "stick9", "stick10", "stick11", "stick12", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BlazeRenderer render = new BlazeRenderer(renderManager.getContext());
      render.f_115290_ = (BlazeModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
