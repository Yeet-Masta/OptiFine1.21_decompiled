package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3806_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4316_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterDonkey extends ModelAdapterHorse {
   public ModelAdapterDonkey() {
      super(C_513_.f_20560_, "donkey", 0.75F);
   }

   public C_3840_ makeModel() {
      return new C_3806_(bakeModelLayer(C_141656_.f_171132_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3806_ modelHorseChests)) {
         return null;
      } else if (modelPart.equals("left_chest")) {
         return (C_3889_)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 0);
      } else {
         return modelPart.equals("right_chest")
            ? (C_3889_)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 1)
            : super.getModelRenderer(model, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      List<String> list = new ArrayList(Arrays.asList(super.getModelRendererNames()));
      list.add("left_chest");
      list.add("right_chest");
      return (String[])list.toArray(new String[list.size()]);
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4316_ render = new C_4316_(renderManager.getContext(), 0.87F, C_141656_.f_171132_);
      render.g = (C_3819_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
