package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ConduitModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart eye;
   public net.minecraft.client.model.geom.ModelPart wind;
   public net.minecraft.client.model.geom.ModelPart base;
   public net.minecraft.client.model.geom.ModelPart cage;

   public ConduitModel() {
      super(net.minecraft.client.renderer.RenderType::m_110452_);
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      ConduitRenderer renderer = new ConduitRenderer(dispatcher.getContext());
      this.eye = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 0);
      this.wind = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 1);
      this.base = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 2);
      this.cage = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 3);
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer updateRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityConduitRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: TileEntityConduitRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 0, this.eye);
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 1, this.wind);
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 2, this.base);
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 3, this.cage);
         return renderer;
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
