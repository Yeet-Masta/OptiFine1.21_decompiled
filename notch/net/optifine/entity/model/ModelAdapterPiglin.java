package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3852_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4376_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterPiglin extends ModelAdapterPlayer {
   public ModelAdapterPiglin() {
      super(C_513_.f_20511_, "piglin", 0.5F);
   }

   protected ModelAdapterPiglin(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3852_(bakeModelLayer(C_141656_.f_171206_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (model instanceof C_3852_ piglinModel && Reflector.ModelPiglin_ModelRenderers.exists()) {
         if (modelPart.equals("left_ear")) {
            return piglinModel.k.m_171324_("left_ear");
         }

         if (modelPart.equals("right_ear")) {
            return piglinModel.k.m_171324_("right_ear");
         }
      }

      return super.getModelRenderer(model, modelPart);
   }

   @Override
   public String[] getModelRendererNames() {
      List<String> names = new ArrayList(Arrays.asList(super.getModelRendererNames()));
      names.add("left_ear");
      names.add("right_ear");
      return (String[])names.toArray(new String[names.size()]);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4376_ render = new C_4376_(renderManager.getContext(), C_141656_.f_171206_, C_141656_.f_171158_, C_141656_.f_171159_, false);
      render.g = (C_3852_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
