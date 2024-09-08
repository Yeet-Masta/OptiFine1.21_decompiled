package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3842_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4365_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterOcelot extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterOcelot() {
      super(C_513_.f_20505_, "ocelot", 0.4F);
   }

   protected ModelAdapterOcelot(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3842_(bakeModelLayer(C_141656_.f_171201_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3842_ modelOcelot)) {
         return null;
      } else {
         Map<String, Integer> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (C_3889_)Reflector.getFieldValue(modelOcelot, Reflector.ModelOcelot_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body"};
   }

   private static Map<String, Integer> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("back_left_leg", 0);
         mapPartFields.put("back_right_leg", 1);
         mapPartFields.put("front_left_leg", 2);
         mapPartFields.put("front_right_leg", 3);
         mapPartFields.put("tail", 4);
         mapPartFields.put("tail2", 5);
         mapPartFields.put("head", 6);
         mapPartFields.put("body", 7);
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4365_ render = new C_4365_(renderManager.getContext());
      render.g = (C_3842_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
