package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class DecoratedPotModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart neck;
   public net.minecraft.client.model.geom.ModelPart frontSide;
   public net.minecraft.client.model.geom.ModelPart backSide;
   public net.minecraft.client.model.geom.ModelPart leftSide;
   public net.minecraft.client.model.geom.ModelPart rightSide;
   public net.minecraft.client.model.geom.ModelPart top;
   public net.minecraft.client.model.geom.ModelPart bottom;

   public DecoratedPotModel() {
      super(net.minecraft.client.renderer.RenderType::m_110458_);
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      DecoratedPotRenderer renderer = new DecoratedPotRenderer(dispatcher.getContext());
      this.neck = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 0);
      this.frontSide = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 1);
      this.backSide = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 2);
      this.leftSide = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 3);
      this.rightSide = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 4);
      this.top = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 5);
      this.bottom = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 6);
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer updateRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityDecoratedPotRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: DecoratedPotRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 0, this.neck);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 1, this.frontSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 2, this.backSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 3, this.leftSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 4, this.rightSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 5, this.top);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 6, this.bottom);
         return renderer;
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
