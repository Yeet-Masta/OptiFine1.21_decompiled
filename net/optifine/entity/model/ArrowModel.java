package net.optifine.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;

public class ArrowModel extends Model {
   public ModelPart body;

   public ArrowModel(ModelPart body) {
      super(RenderType::m_110458_);
      this.body = body;
   }

   @Override
   public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
      this.body.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
   }
}
