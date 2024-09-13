package net.optifine.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public class PlayerItemRenderer {
   private int attachTo = 0;
   private ModelPart modelRenderer = null;

   public PlayerItemRenderer(int attachTo, ModelPart modelRenderer) {
      this.attachTo = attachTo;
      this.modelRenderer = modelRenderer;
   }

   public ModelPart getModelRenderer() {
      return this.modelRenderer;
   }

   public void m_324219_(HumanoidModel modelBiped, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
      ModelPart attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
      if (attachModel != null) {
         attachModel.m_104299_(matrixStackIn);
      }

      this.modelRenderer.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
   }
}
