package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3801_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4311_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterBee extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterBee() {
      super(C_513_.f_20550_, "bee", 0.4F);
   }

   public C_3840_ makeModel() {
      return new C_3801_(bakeModelLayer(C_141656_.f_171268_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3801_ modelBee)) {
         return null;
      } else if (modelPart.equals("body")) {
         return (C_3889_)Reflector.getFieldValue(modelBee, Reflector.ModelBee_ModelRenderers, 0);
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         C_3889_ body = this.getModelRenderer(modelBee, "body");
         return body.getChildModelDeep(name);
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
      map.put("body", "bone");
      map.put("torso", "body");
      map.put("right_wing", "right_wing");
      map.put("left_wing", "left_wing");
      map.put("front_legs", "front_legs");
      map.put("middle_legs", "middle_legs");
      map.put("back_legs", "back_legs");
      map.put("stinger", "stinger");
      map.put("left_antenna", "left_antenna");
      map.put("right_antenna", "right_antenna");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4311_ render = new C_4311_(renderManager.getContext());
      render.g = (C_3801_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
