package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class EnderCrystalModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart cube;
   public net.minecraft.client.model.geom.ModelPart glass;
   public net.minecraft.client.model.geom.ModelPart base;

   public EnderCrystalModel() {
      super(net.minecraft.client.renderer.RenderType::m_110458_);
      EndCrystalRenderer renderer = new EndCrystalRenderer(Minecraft.m_91087_().m_91290_().getContext());
      this.cube = (net.minecraft.client.model.geom.ModelPart)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 0);
      this.glass = (net.minecraft.client.model.geom.ModelPart)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 1);
      this.base = (net.minecraft.client.model.geom.ModelPart)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 2);
   }

   public EndCrystalRenderer updateRenderer(EndCrystalRenderer render) {
      if (!Reflector.RenderEnderCrystal_modelRenderers.exists()) {
         Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
         return null;
      } else {
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 0, this.cube);
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 1, this.glass);
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 2, this.base);
         return render;
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
