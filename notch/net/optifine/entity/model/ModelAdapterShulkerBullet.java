package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3865_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4385_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBullet extends ModelAdapter {
   public ModelAdapterShulkerBullet() {
      super(C_513_.f_20522_, "shulker_bullet", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3865_(bakeModelLayer(C_141656_.f_171181_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3865_ modelShulkerBullet)) {
         return null;
      } else if (modelPart.equals("bullet")) {
         return modelShulkerBullet.m_142109_().getChildModelDeep("main");
      } else {
         return modelPart.equals("root") ? modelShulkerBullet.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"bullet", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4385_ render = new C_4385_(renderManager.getContext());
      if (!Reflector.RenderShulkerBullet_model.exists()) {
         Config.warn("Field not found: RenderShulkerBullet.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderShulkerBullet_model, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
