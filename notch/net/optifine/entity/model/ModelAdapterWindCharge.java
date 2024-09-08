package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_301905_;
import net.minecraft.src.C_301983_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterWindCharge extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterWindCharge() {
      super(C_513_.f_303421_, "wind_charge", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_301905_(bakeModelLayer(C_141656_.f_303259_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_301905_ modelWindCharge)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelWindCharge.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelWindCharge.m_142109_().getChildModelDeep(name);
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])mapParts.keySet().toArray(new String[0]);
   }

   private static Map<String, String> makeMapParts() {
      Map<String, String> map = new LinkedHashMap();
      map.put("core", "projectile");
      map.put("wind", "wind");
      map.put("cube1", "cube_r1");
      map.put("cube2", "cube_r2");
      map.put("charge", "wind_charge");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_301983_ render = new C_301983_(renderManager.getContext());
      if (!Reflector.RenderWindCharge_model.exists()) {
         Config.warn("Field not found: RenderWindCharge.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderWindCharge_model, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
