package net.optifine.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BedModel extends Model {
   public ModelPart headPiece;
   public ModelPart footPiece;
   public ModelPart[] legs = new ModelPart[4];

   public BedModel() {
      super(RenderType::m_110458_);
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BedRenderer renderer = new BedRenderer(dispatcher.getContext());
      ModelPart headRoot = (ModelPart)Reflector.TileEntityBedRenderer_headModel.getValue(renderer);
      if (headRoot != null) {
         this.headPiece = headRoot.m_171324_("main");
         this.legs[0] = headRoot.m_171324_("left_leg");
         this.legs[1] = headRoot.m_171324_("right_leg");
      }

      ModelPart footRoot = (ModelPart)Reflector.TileEntityBedRenderer_footModel.getValue(renderer);
      if (footRoot != null) {
         this.footPiece = footRoot.m_171324_("main");
         this.legs[2] = footRoot.m_171324_("left_leg");
         this.legs[3] = footRoot.m_171324_("right_leg");
      }

   }

   public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }

   public BlockEntityRenderer updateRenderer(BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityBedRenderer_headModel.exists()) {
         Config.warn("Field not found: TileEntityBedRenderer.head");
         return null;
      } else if (!Reflector.TileEntityBedRenderer_footModel.exists()) {
         Config.warn("Field not found: TileEntityBedRenderer.footModel");
         return null;
      } else {
         ModelPart headRoot = (ModelPart)Reflector.TileEntityBedRenderer_headModel.getValue(renderer);
         if (headRoot != null) {
            headRoot.addChildModel("main", this.headPiece);
            headRoot.addChildModel("left_leg", this.legs[0]);
            headRoot.addChildModel("right_leg", this.legs[1]);
         }

         ModelPart footRoot = (ModelPart)Reflector.TileEntityBedRenderer_footModel.getValue(renderer);
         if (footRoot != null) {
            footRoot.addChildModel("main", this.footPiece);
            footRoot.addChildModel("left_leg", this.legs[2]);
            footRoot.addChildModel("right_leg", this.legs[3]);
         }

         return renderer;
      }
   }
}
