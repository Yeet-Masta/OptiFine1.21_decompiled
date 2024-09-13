package net.optifine.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ChestLargeModel extends Model {
   public ModelPart lid_left;
   public ModelPart base_left;
   public ModelPart knob_left;
   public ModelPart lid_right;
   public ModelPart base_right;
   public ModelPart knob_right;

   public ChestLargeModel() {
      super(RenderType::m_110452_);
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      ChestRenderer renderer = new ChestRenderer(dispatcher.getContext());
      this.lid_right = (ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 3);
      this.base_right = (ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 4);
      this.knob_right = (ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 5);
      this.lid_left = (ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 6);
      this.base_left = (ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 7);
      this.knob_left = (ModelPart)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 8);
   }

   public BlockEntityRenderer updateRenderer(BlockEntityRenderer renderer) {
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
   public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
