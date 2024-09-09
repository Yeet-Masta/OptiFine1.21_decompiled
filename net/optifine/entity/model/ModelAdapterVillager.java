package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterVillager extends ModelAdapter {
   public ModelAdapterVillager() {
      super(EntityType.f_20492_, "villager", 0.5F);
   }

   protected ModelAdapterVillager(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new VillagerModel(bakeModelLayer(ModelLayers.f_171210_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof VillagerModel modelVillager)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelVillager.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("headwear")) {
         return modelVillager.m_142109_().getChildModelDeep("hat");
      } else if (modelPart.equals("headwear2")) {
         return modelVillager.m_142109_().getChildModelDeep("hat_rim");
      } else if (modelPart.equals("body")) {
         return modelVillager.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("bodywear")) {
         return modelVillager.m_142109_().getChildModelDeep("jacket");
      } else if (modelPart.equals("arms")) {
         return modelVillager.m_142109_().getChildModelDeep("arms");
      } else if (modelPart.equals("right_leg")) {
         return modelVillager.m_142109_().getChildModelDeep("right_leg");
      } else if (modelPart.equals("left_leg")) {
         return modelVillager.m_142109_().getChildModelDeep("left_leg");
      } else if (modelPart.equals("nose")) {
         return modelVillager.m_142109_().getChildModelDeep("nose");
      } else {
         return modelPart.equals("root") ? modelVillager.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "headwear", "headwear2", "body", "bodywear", "arms", "right_leg", "left_leg", "nose", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.server.packs.resources.ReloadableResourceManager resourceManager = (net.minecraft.server.packs.resources.ReloadableResourceManager)Minecraft.m_91087_()
         .m_91098_();
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      VillagerRenderer render = new VillagerRenderer(renderManager.getContext());
      render.f_115290_ = (VillagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
