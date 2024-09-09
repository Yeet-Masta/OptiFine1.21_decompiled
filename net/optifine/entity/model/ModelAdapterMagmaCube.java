package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LavaSlimeModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterMagmaCube extends ModelAdapter {
   public ModelAdapterMagmaCube() {
      super(EntityType.f_20468_, "magma_cube", 0.5F);
   }

   public Model makeModel() {
      return new LavaSlimeModel(bakeModelLayer(ModelLayers.f_171197_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof LavaSlimeModel modelMagmaCube)) {
         return null;
      } else if (modelPart.equals("core")) {
         return modelMagmaCube.m_142109_().getChildModelDeep("inside_cube");
      } else {
         String PREFIX_SEGMENT = "segment";
         if (modelPart.startsWith(PREFIX_SEGMENT)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_SEGMENT);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelMagmaCube.m_142109_().getChildModelDeep("cube" + indexPart);
         } else {
            return modelPart.equals("root") ? modelMagmaCube.m_142109_() : null;
         }
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      MagmaCubeRenderer render = new MagmaCubeRenderer(renderManager.getContext());
      render.f_115290_ = (LavaSlimeModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
