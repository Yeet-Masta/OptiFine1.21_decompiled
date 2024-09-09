package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterElderGuardian extends ModelAdapterGuardian {
   public ModelAdapterElderGuardian() {
      super(EntityType.f_20563_, "elder_guardian", 0.5F);
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ElderGuardianRenderer render = new ElderGuardianRenderer(renderManager.getContext());
      render.f_115290_ = (GuardianModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
