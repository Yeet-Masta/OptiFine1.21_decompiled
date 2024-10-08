package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ModelAdapterSquid extends ModelAdapter {
   public ModelAdapterSquid() {
      super(EntityType.f_20480_, "squid", 0.7F);
   }

   protected ModelAdapterSquid(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public Model makeModel() {
      return new SquidModel(bakeModelLayer(ModelLayers.f_171246_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof SquidModel modelSquid)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelSquid.m_142109_().getChildModelDeep("body");
      } else {
         String PREFIX_TENTACLE = "tentacle";
         if (modelPart.startsWith(PREFIX_TENTACLE)) {
            String numStr = StrUtils.removePrefix(modelPart, PREFIX_TENTACLE);
            int index = Config.parseInt(numStr, -1);
            int indexPart = index - 1;
            return modelSquid.m_142109_().getChildModelDeep("tentacle" + indexPart);
         } else {
            return modelPart.equals("root") ? modelSquid.m_142109_() : null;
         }
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SquidRenderer render = new SquidRenderer(renderManager.getContext(), (SquidModel)modelBase);
      render.f_115290_ = (SquidModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
