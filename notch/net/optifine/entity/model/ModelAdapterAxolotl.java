package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141647_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_141741_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterAxolotl extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterAxolotl() {
      super(C_513_.f_147039_, "axolotl", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_141647_(bakeModelLayer(C_141656_.f_171263_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_141647_ modelAxolotl)) {
         return null;
      } else {
         Map<String, Integer> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (C_3889_)Reflector.getFieldValue(modelAxolotl, Reflector.ModelAxolotl_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])getMapPartFields().keySet().toArray(new String[0]);
   }

   private static Map<String, Integer> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("tail", 0);
         mapPartFields.put("leg2", 1);
         mapPartFields.put("leg1", 2);
         mapPartFields.put("leg4", 3);
         mapPartFields.put("leg3", 4);
         mapPartFields.put("body", 5);
         mapPartFields.put("head", 6);
         mapPartFields.put("top_gills", 7);
         mapPartFields.put("left_gills", 8);
         mapPartFields.put("right_gills", 9);
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_141741_ render = new C_141741_(renderManager.getContext());
      render.g = (C_141647_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
