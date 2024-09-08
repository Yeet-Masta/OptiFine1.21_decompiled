package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4399_;
import net.minecraft.src.C_513_;

public class ModelAdapterArrow extends ModelAdapter {
   public ModelAdapterArrow() {
      super(C_513_.f_20548_, "arrow", 0.0F);
   }

   protected ModelAdapterArrow(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new ArrowModel(new C_3889_(new ArrayList(), new HashMap()));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof ArrowModel modelArrow)) {
         return null;
      } else {
         return modelPart.equals("body") ? modelArrow.body : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4399_ render = new C_4399_(renderManager.getContext());
      render.model = (ArrowModel)modelBase;
      render.e = shadowSize;
      return render;
   }
}
