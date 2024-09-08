package net.optifine.entity.model;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_2106_;
import net.minecraft.src.C_243526_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_243526_.C_243448_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterHangingSign extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterHangingSign() {
      super(C_1992_.f_244529_, "hanging_sign", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_243448_(bakeModelLayer(C_141656_.m_247439_(C_2106_.f_61830_)));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_243448_ modelHangingSign)) {
         return null;
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelHangingSign.f_244554_.getChildModelDeep(name);
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
      map.put("board", "board");
      map.put("plank", "plank");
      map.put("chains", "normalChains");
      map.put("chain_left1", "chainL1");
      map.put("chain_left2", "chainL2");
      map.put("chain_right1", "chainR1");
      map.put("chain_right2", "chainR2");
      map.put("chains_v", "vChains");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_244529_, index, () -> new C_243526_(dispatcher.getContext()));
      if (!(renderer instanceof C_243526_)) {
         return null;
      } else if (!Reflector.TileEntityHangingSignRenderer_hangingSignModels.exists()) {
         Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
         return null;
      } else {
         Map<C_2106_, C_243448_> hangingSignModels = (Map<C_2106_, C_243448_>)Reflector.getFieldValue(
            renderer, Reflector.TileEntityHangingSignRenderer_hangingSignModels
         );
         if (hangingSignModels == null) {
            Config.warn("Field not found: TileEntityHangingSignRenderer.hangingSignModels");
            return null;
         } else {
            if (hangingSignModels instanceof ImmutableMap) {
               hangingSignModels = new HashMap(hangingSignModels);
               Reflector.TileEntityHangingSignRenderer_hangingSignModels.setValue(renderer, hangingSignModels);
            }

            for (C_2106_ type : new HashSet(hangingSignModels.keySet())) {
               hangingSignModels.put(type, (C_243448_)modelBase);
            }

            return renderer;
         }
      }
   }
}
