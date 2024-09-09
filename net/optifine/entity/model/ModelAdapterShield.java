package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;

public class ModelAdapterShield extends ModelAdapter {
   public ModelAdapterShield() {
      super((Either)((Either)null), "shield", 0.0F, (String[])null);
   }

   public Model makeModel() {
      return new ShieldModel(bakeModelLayer(ModelLayers.f_171179_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof ShieldModel modelShield)) {
         return null;
      } else if (modelPart.equals("plate")) {
         return (ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 1);
      } else if (modelPart.equals("handle")) {
         return (ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 2);
      } else {
         return modelPart.equals("root") ? (ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 0) : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"plate", "handle", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      IEntityRenderer rc = new VirtualEntityRenderer(modelBase);
      return rc;
   }
}
