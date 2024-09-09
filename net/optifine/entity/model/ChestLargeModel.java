package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ChestLargeModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart lid_left;
   public net.minecraft.client.model.geom.ModelPart base_left;
   public net.minecraft.client.model.geom.ModelPart knob_left;
   public net.minecraft.client.model.geom.ModelPart lid_right;
   public net.minecraft.client.model.geom.ModelPart base_right;
   public net.minecraft.client.model.geom.ModelPart knob_right;

   public ChestLargeModel() {
      super(net.minecraft.client.renderer.RenderType::m_110452_);
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      ChestRenderer renderer = new ChestRenderer(dispatcher.getContext());
      this.lid_right = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 3);
      this.base_right = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 4);
      this.knob_right = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 5);
      this.lid_left = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 6);
      this.base_left = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 7);
      this.knob_left = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 8);
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer updateRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 3, this.lid_right);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 4, this.base_right);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 5, this.knob_right);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 6, this.lid_left);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 7, this.base_left);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 8, this.knob_left);
         return renderer;
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
