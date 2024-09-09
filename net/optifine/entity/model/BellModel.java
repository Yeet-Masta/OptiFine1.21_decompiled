package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BellModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart bellBody;

   public BellModel() {
      super(net.minecraft.client.renderer.RenderType::m_110458_);
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BellRenderer renderer = new BellRenderer(dispatcher.getContext());
      this.bellBody = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBellRenderer_modelRenderer.getValue(renderer);
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer updateRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityBellRenderer_modelRenderer.exists()) {
         Config.warn("Field not found: TileEntityBellRenderer.modelRenderer");
         return null;
      } else {
         Reflector.TileEntityBellRenderer_modelRenderer.setValue(renderer, this.bellBody);
         return renderer;
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
