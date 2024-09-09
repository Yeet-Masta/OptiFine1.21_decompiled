package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterArmorStand extends ModelAdapterBiped {
   public ModelAdapterArmorStand() {
      super(EntityType.f_20529_, "armor_stand", 0.0F);
   }

   public Model makeModel() {
      return new ArmorStandModel(bakeModelLayer(ModelLayers.f_171155_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof ArmorStandModel modelArmorStand)) {
         return null;
      } else if (modelPart.equals("right")) {
         return (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 0);
      } else if (modelPart.equals("left")) {
         return (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 1);
      } else if (modelPart.equals("waist")) {
         return (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 2);
      } else {
         return modelPart.equals("base") ? (ModelPart)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 3) : super.getModelRenderer(modelArmorStand, modelPart);
      }
   }

   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      names = (String[])Config.addObjectsToArray(names, new String[]{"right", "left", "waist", "base"});
      return names;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ArmorStandRenderer render = new ArmorStandRenderer(renderManager.getContext());
      render.f_115290_ = (ArmorStandArmorModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
