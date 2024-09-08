package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3860_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4381_;
import net.minecraft.src.C_513_;

public class ModelAdapterRavager extends ModelAdapter {
   private static Map<String, String> mapPartFields = null;

   public ModelAdapterRavager() {
      super(C_513_.f_20518_, "ravager", 1.1F);
   }

   public C_3840_ makeModel() {
      return new C_3860_(bakeModelLayer(C_141656_.f_171175_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3860_ modelRavager)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelRavager.m_142109_();
      } else {
         Map<String, String> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            String name = (String)mapParts.get(modelPart);
            return modelRavager.m_142109_().getChildModelDeep(name);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])getMapPartFields().keySet().toArray(new String[0]);
   }

   private static Map<String, String> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("head", "head");
         mapPartFields.put("jaw", "mouth");
         mapPartFields.put("body", "body");
         mapPartFields.put("leg1", "right_hind_leg");
         mapPartFields.put("leg2", "left_hind_leg");
         mapPartFields.put("leg3", "right_front_leg");
         mapPartFields.put("leg4", "left_front_leg");
         mapPartFields.put("neck", "neck");
         mapPartFields.put("root", "root");
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4381_ render = new C_4381_(renderManager.getContext());
      render.g = (C_3860_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
