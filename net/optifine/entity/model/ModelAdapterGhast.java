package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterGhast extends ModelAdapter {
   public ModelAdapterGhast() {
      super(EntityType.f_20453_, "ghast", 0.5F);
   }

   public Model makeModel() {
      return new GhastModel(bakeModelLayer(ModelLayers.f_171150_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof GhastModel modelGhast)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelGhast.m_142109_().getChildModelDeep("body");
      } else {
         String PREFIX_TENTACLE = "tentacle";
         if (modelPart.startsWith(PREFIX_TENTACLE)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelGhast.m_142109_().getChildModelDeep("tentacle" + indexPart);
         } else {
            return modelPart.equals("root") ? modelGhast.m_142109_() : null;
         }
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "tentacle9", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      GhastRenderer render = new GhastRenderer(renderManager.getContext());
      render.f_115290_ = (GhastModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
