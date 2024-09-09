package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ChestModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart lid;
   public net.minecraft.client.model.geom.ModelPart base;
   public net.minecraft.client.model.geom.ModelPart knob;

   public ChestModel() {
      super(net.minecraft.client.renderer.RenderType::m_110452_);
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      ChestRenderer renderer = new ChestRenderer(dispatcher.getContext());
      this.lid = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 0);
      this.base = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 1);
      this.knob = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 2);
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer updateRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 0, this.lid);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 1, this.base);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 2, this.knob);
         return renderer;
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
