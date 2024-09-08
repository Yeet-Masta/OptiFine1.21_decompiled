package net.optifine.entity.model;

import java.util.Map;
import net.minecraft.src.C_141651_;
import net.minecraft.src.C_141655_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3869_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4255_;
import net.minecraft.src.C_1897_.C_1898_;
import net.minecraft.src.C_1897_.C_1899_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterHead extends ModelAdapter {
   private C_141655_ modelLayer;
   private C_1899_ skullBlockType;

   public ModelAdapterHead(String name, C_141655_ modelLayer, C_1899_ skullBlockType) {
      super(C_1992_.f_58931_, name, 0.0F);
      this.modelLayer = modelLayer;
      this.skullBlockType = skullBlockType;
   }

   public C_3840_ makeModel() {
      return new C_3869_(bakeModelLayer(this.modelLayer));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3869_ modelSkul)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.ModelSkull_renderers.getValue(modelSkul, 1);
      } else {
         return modelPart.equals("root") ? (C_3889_)Reflector.ModelSkull_renderers.getValue(modelSkul, 0) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58931_, index, () -> new C_4255_(dispatcher.getContext()));
      if (!(renderer instanceof C_4255_)) {
         return null;
      } else {
         Map<C_1898_, C_141651_> models = C_4255_.models;
         if (models == null) {
            Config.warn("Field not found: SkullBlockRenderer.models");
            return null;
         } else {
            models.put(this.skullBlockType, (C_141651_)modelBase);
            return renderer;
         }
      }
   }
}
