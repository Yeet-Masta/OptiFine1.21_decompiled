package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3874_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4395_;
import net.minecraft.src.C_513_;

public class ModelAdapterStrider extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterStrider() {
      super(C_513_.f_20482_, "strider", 0.5F);
   }

   protected ModelAdapterStrider(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3874_(bakeModelLayer(C_141656_.f_171251_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3874_ modelStrider)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelStrider.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelStrider.m_142109_().getChildModelDeep(name);
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
      map.put("right_leg", "right_leg");
      map.put("left_leg", "left_leg");
      map.put("body", "body");
      map.put("hair_right_bottom", "right_bottom_bristle");
      map.put("hair_right_middle", "right_middle_bristle");
      map.put("hair_right_top", "right_top_bristle");
      map.put("hair_left_top", "left_top_bristle");
      map.put("hair_left_middle", "left_middle_bristle");
      map.put("hair_left_bottom", "left_bottom_bristle");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4395_ render = new C_4395_(renderManager.getContext());
      render.g = (C_3874_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
