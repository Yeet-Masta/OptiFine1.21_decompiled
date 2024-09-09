package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterArmorStand extends ModelAdapterBiped {
   public ModelAdapterArmorStand() {
      super(EntityType.f_20529_, "armor_stand", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ArmorStandModel(bakeModelLayer(ModelLayers.f_171155_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ArmorStandModel modelArmorStand)) {
         return null;
      } else if (modelPart.equals("right")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 0);
      } else if (modelPart.equals("left")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 1);
      } else if (modelPart.equals("waist")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 2);
      } else {
         return modelPart.equals("base")
            ? (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 3)
            : super.getModelRenderer(modelArmorStand, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])Config.addObjectsToArray(names, new String[]{"right", "left", "waist", "base"});
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ArmorStandRenderer render = new ArmorStandRenderer(renderManager.getContext());
      render.f_115290_ = (ArmorStandArmorModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
