package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_302054_;
import net.minecraft.src.C_302185_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterBreeze extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterBreeze() {
      super(C_513_.f_302782_, "breeze", 0.8F);
   }

   protected ModelAdapterBreeze(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_302185_(bakeModelLayer(C_141656_.f_303100_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_302185_ modelBreeze)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelBreeze.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelBreeze.m_142109_().getChildModelDeep(name);
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
      map.put("body", "body");
      map.put("rods", "rods");
      map.put("head", "head");
      map.put("wind_body", "wind_body");
      map.put("wind_middle", "wind_mid");
      map.put("wind_bottom", "wind_bottom");
      map.put("wind_top", "wind_top");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_302054_ render = new C_302054_(renderManager.getContext());
      render.g = (C_302185_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
