package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EndermiteRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterEndermite extends ModelAdapter {
   public ModelAdapterEndermite() {
      super(EntityType.f_20567_, "endermite", 0.3F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new EndermiteModel(bakeModelLayer(ModelLayers.f_171143_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof EndermiteModel modelEnderMite)) {
         return null;
      } else {
         String PREFIX_BODY = "body";
         if (modelPart.startsWith(PREFIX_BODY)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_BODY);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelEnderMite.m_142109_().getChildModelDeep("segment" + indexPart);
         } else {
            return modelPart.equals("root") ? modelEnderMite.m_142109_() : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "body4", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      EndermiteRenderer render = new EndermiteRenderer(renderManager.getContext());
      render.f_115290_ = (EndermiteModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
