package net.optifine.entity.model;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_2106_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4253_;
import net.minecraft.src.C_4253_.C_4254_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterSign extends ModelAdapter {
   public ModelAdapterSign() {
      super(C_1992_.f_58924_, "sign", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_4254_(bakeModelLayer(C_141656_.m_171291_(C_2106_.f_61830_)));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_4254_ modelSign)) {
         return null;
      } else if (modelPart.equals("board")) {
         return modelSign.f_173655_.getChildModelDeep("sign");
      } else {
         return modelPart.equals("stick") ? modelSign.f_173655_.getChildModelDeep("stick") : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"board", "stick"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58924_, index, () -> new C_4253_(dispatcher.getContext()));
      if (!(renderer instanceof C_4253_)) {
         return null;
      } else if (!Reflector.TileEntitySignRenderer_signModels.exists()) {
         Config.warn("Field not found: TileEntitySignRenderer.signModels");
         return null;
      } else {
         Map<C_2106_, C_4254_> signModels = (Map<C_2106_, C_4254_>)Reflector.getFieldValue(renderer, Reflector.TileEntitySignRenderer_signModels);
         if (signModels == null) {
            Config.warn("Field not found: TileEntitySignRenderer.signModels");
            return null;
         } else {
            if (signModels instanceof ImmutableMap) {
               signModels = new HashMap(signModels);
               Reflector.TileEntitySignRenderer_signModels.setValue(renderer, signModels);
            }

            for (C_2106_ type : new HashSet(signModels.keySet())) {
               signModels.put(type, (C_4254_)modelBase);
            }

            return renderer;
         }
      }
   }
}
