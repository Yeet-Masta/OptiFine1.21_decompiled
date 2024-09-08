package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3839_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4362_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecart extends ModelAdapter {
   public ModelAdapterMinecart() {
      super(C_513_.f_20469_, "minecart", 0.5F);
   }

   protected ModelAdapterMinecart(C_513_ type, String name, float shadow) {
      super(type, name, shadow);
   }

   public C_3840_ makeModel() {
      return new C_3839_(bakeModelLayer(C_141656_.f_171198_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3839_ modelMinecart)) {
         return null;
      } else if (modelPart.equals("bottom")) {
         return modelMinecart.m_142109_().getChildModelDeep("bottom");
      } else if (modelPart.equals("back")) {
         return modelMinecart.m_142109_().getChildModelDeep("back");
      } else if (modelPart.equals("front")) {
         return modelMinecart.m_142109_().getChildModelDeep("front");
      } else if (modelPart.equals("right")) {
         return modelMinecart.m_142109_().getChildModelDeep("right");
      } else if (modelPart.equals("left")) {
         return modelMinecart.m_142109_().getChildModelDeep("left");
      } else {
         return modelPart.equals("root") ? modelMinecart.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"bottom", "back", "front", "right", "left", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4362_ render = new C_4362_(renderManager.getContext(), C_141656_.f_171198_);
      if (!Reflector.RenderMinecart_modelMinecart.exists()) {
         Config.warn("Field not found: RenderMinecart.modelMinecart");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
