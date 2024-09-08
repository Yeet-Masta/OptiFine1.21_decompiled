package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3813_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4321_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterDolphin extends ModelAdapter {
   public ModelAdapterDolphin() {
      super(C_513_.f_20559_, "dolphin", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3813_(bakeModelLayer(C_141656_.f_171131_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3813_ modelDolphin)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelDolphin.m_142109_();
      } else {
         C_3889_ modelBody = modelDolphin.m_142109_().m_171324_("body");
         if (modelBody == null) {
            return null;
         } else if (modelPart.equals("body")) {
            return modelBody;
         } else if (modelPart.equals("back_fin")) {
            return modelBody.m_171324_("back_fin");
         } else if (modelPart.equals("left_fin")) {
            return modelBody.m_171324_("left_fin");
         } else if (modelPart.equals("right_fin")) {
            return modelBody.m_171324_("right_fin");
         } else if (modelPart.equals("tail")) {
            return modelBody.m_171324_("tail");
         } else if (modelPart.equals("tail_fin")) {
            return modelBody.m_171324_("tail").m_171324_("tail_fin");
         } else {
            return modelPart.equals("head") ? modelBody.m_171324_("head") : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "back_fin", "left_fin", "right_fin", "tail", "tail_fin", "head", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4321_ render = new C_4321_(renderManager.getContext());
      render.g = (C_3813_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
