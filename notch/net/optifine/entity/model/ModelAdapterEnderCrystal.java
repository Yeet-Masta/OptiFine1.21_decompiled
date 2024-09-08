package net.optifine.entity.model;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4325_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_513_;
import net.optifine.Config;

public class ModelAdapterEnderCrystal extends ModelAdapter {
   public ModelAdapterEnderCrystal() {
      this("end_crystal");
   }

   protected ModelAdapterEnderCrystal(String name) {
      super(C_513_.f_20564_, name, 0.5F);
   }

   public C_3840_ makeModel() {
      return new EnderCrystalModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof EnderCrystalModel modelEnderCrystal)) {
         return null;
      } else if (modelPart.equals("cube")) {
         return modelEnderCrystal.cube;
      } else if (modelPart.equals("glass")) {
         return modelEnderCrystal.glass;
      } else {
         return modelPart.equals("base") ? modelEnderCrystal.base : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"cube", "glass", "base"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4331_ renderObj = rendererCache.get(C_513_.f_20564_, index, () -> new C_4325_(renderManager.getContext()));
      if (!(renderObj instanceof C_4325_ render)) {
         Config.warn("Not an instance of RenderEnderCrystal: " + renderObj);
         return null;
      } else if (!(modelBase instanceof EnderCrystalModel enderCrystalModel)) {
         Config.warn("Not a EnderCrystalModel model: " + modelBase);
         return null;
      } else {
         C_4325_ var9 = enderCrystalModel.updateRenderer(render);
         var9.e = shadowSize;
         return var9;
      }
   }
}
