package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BedModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart headPiece;
   public net.minecraft.client.model.geom.ModelPart footPiece;
   public net.minecraft.client.model.geom.ModelPart[] legs = new net.minecraft.client.model.geom.ModelPart[4];

   public BedModel() {
      super(net.minecraft.client.renderer.RenderType::m_110458_);
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BedRenderer renderer = new BedRenderer(dispatcher.getContext());
      net.minecraft.client.model.geom.ModelPart headRoot = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBedRenderer_headModel
         .getValue(renderer);
      if (headRoot != null) {
         this.headPiece = headRoot.m_171324_("main");
         this.legs[0] = headRoot.m_171324_("left_leg");
         this.legs[1] = headRoot.m_171324_("right_leg");
      }

      net.minecraft.client.model.geom.ModelPart footRoot = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBedRenderer_footModel
         .getValue(renderer);
      if (footRoot != null) {
         this.footPiece = footRoot.m_171324_("main");
         this.legs[2] = footRoot.m_171324_("left_leg");
         this.legs[3] = footRoot.m_171324_("right_leg");
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer updateRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityBedRenderer_headModel.exists()) {
         Config.warn("Field not found: TileEntityBedRenderer.head");
         return null;
      } else if (!Reflector.TileEntityBedRenderer_footModel.exists()) {
         Config.warn("Field not found: TileEntityBedRenderer.footModel");
         return null;
      } else {
         net.minecraft.client.model.geom.ModelPart headRoot = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBedRenderer_headModel
            .getValue(renderer);
         if (headRoot != null) {
            headRoot.addChildModel("main", this.headPiece);
            headRoot.addChildModel("left_leg", this.legs[0]);
            headRoot.addChildModel("right_leg", this.legs[1]);
         }

         net.minecraft.client.model.geom.ModelPart footRoot = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBedRenderer_footModel
            .getValue(renderer);
         if (footRoot != null) {
            footRoot.addChildModel("main", this.footPiece);
            footRoot.addChildModel("left_leg", this.legs[2]);
            footRoot.addChildModel("right_leg", this.legs[3]);
         }

         return renderer;
      }
   }
}
