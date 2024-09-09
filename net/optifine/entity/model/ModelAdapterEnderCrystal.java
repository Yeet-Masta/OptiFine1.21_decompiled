package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterEnderCrystal extends ModelAdapter {
   public ModelAdapterEnderCrystal() {
      this("end_crystal");
   }

   protected ModelAdapterEnderCrystal(String name) {
      super(EntityType.f_20564_, name, 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new EnderCrystalModel();
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
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

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      net.minecraft.client.renderer.entity.EntityRenderer renderObj = rendererCache.get(
         EntityType.f_20564_, index, () -> new EndCrystalRenderer(renderManager.getContext())
      );
      if (!(renderObj instanceof EndCrystalRenderer render)) {
         Config.warn("Not an instance of RenderEnderCrystal: " + renderObj);
         return null;
      } else if (!(modelBase instanceof EnderCrystalModel enderCrystalModel)) {
         Config.warn("Not a EnderCrystalModel model: " + modelBase);
         return null;
      } else {
         EndCrystalRenderer var9 = enderCrystalModel.updateRenderer(render);
         var9.f_114477_ = shadowSize;
         return var9;
      }
   }
}
