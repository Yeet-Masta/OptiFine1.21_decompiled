package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3883_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4410_;
import net.minecraft.src.C_513_;
import net.optifine.Config;

public class ModelAdapterWitch extends ModelAdapterVillager {
   public ModelAdapterWitch() {
      super(C_513_.f_20495_, "witch", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3883_(bakeModelLayer(C_141656_.f_171213_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3883_ modelWitch)) {
         return null;
      } else {
         return modelPart.equals("mole") ? modelWitch.a().getChildModelDeep("mole") : super.getModelRenderer(modelWitch, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])Config.addObjectToArray(names, "mole");
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4410_ render = new C_4410_(renderManager.getContext());
      render.g = (C_3883_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
