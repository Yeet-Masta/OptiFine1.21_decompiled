package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3858_;
import net.minecraft.src.C_3879_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4403_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTurtle extends ModelAdapterQuadruped {
   public ModelAdapterTurtle() {
      super(C_513_.f_20490_, "turtle", 0.7F);
   }

   public C_3840_ makeModel() {
      return new C_3879_(bakeModelLayer(C_141656_.f_171260_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3858_)) {
         return null;
      } else {
         C_3879_ modelQuadruped = (C_3879_)model;
         return modelPart.equals("body2") ? (C_3889_)Reflector.ModelTurtle_body2.getValue(modelQuadruped) : super.getModelRenderer(model, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])Config.addObjectToArray(names, "body2");
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4403_ render = new C_4403_(renderManager.getContext());
      render.g = (C_3879_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
