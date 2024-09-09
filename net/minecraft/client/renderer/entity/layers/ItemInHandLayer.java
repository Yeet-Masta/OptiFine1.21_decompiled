package net.minecraft.client.renderer.entity.layers;

import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.model.AttachmentType;

public class ItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel>
   extends net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> {
   private final net.minecraft.client.renderer.ItemInHandRenderer f_234844_;

   public ItemInHandLayer(RenderLayerParent<T, M> p_i234845_1_, net.minecraft.client.renderer.ItemInHandRenderer p_i234845_2_) {
      super(p_i234845_1_);
      this.f_234844_ = p_i234845_2_;
   }

   public void m_6494_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      boolean flag = entitylivingbaseIn.m_5737_() == HumanoidArm.RIGHT;
      ItemStack itemstack = flag ? entitylivingbaseIn.m_21206_() : entitylivingbaseIn.m_21205_();
      ItemStack itemstack1 = flag ? entitylivingbaseIn.m_21205_() : entitylivingbaseIn.m_21206_();
      if (!itemstack.m_41619_() || !itemstack1.m_41619_()) {
         matrixStackIn.m_85836_();
         if (this.m_117386_().f_102610_) {
            float f = 0.5F;
            matrixStackIn.m_252880_(0.0F, 0.75F, 0.0F);
            matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
         }

         this.m_117184_(entitylivingbaseIn, itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, matrixStackIn, bufferIn, packedLightIn);
         this.m_117184_(entitylivingbaseIn, itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.m_85849_();
      }
   }

   protected void m_117184_(
      LivingEntity entityIn,
      ItemStack itemStackIn,
      ItemDisplayContext contextIn,
      HumanoidArm armIn,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferSourceIn,
      int combinedLightIn
   ) {
      if (!itemStackIn.m_41619_()) {
         matrixStackIn.m_85836_();
         if (!this.applyAttachmentTransform(armIn, matrixStackIn)) {
            this.m_117386_().m_6002_(armIn, matrixStackIn);
         }

         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(-90.0F));
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F));
         boolean flag = armIn == HumanoidArm.LEFT;
         matrixStackIn.m_252880_((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
         this.f_234844_.m_269530_(entityIn, itemStackIn, contextIn, flag, matrixStackIn, bufferSourceIn, combinedLightIn);
         matrixStackIn.m_85849_();
      }
   }

   private boolean applyAttachmentTransform(HumanoidArm armIn, com.mojang.blaze3d.vertex.PoseStack matrixStackIn) {
      if (!CustomEntityModels.isActive()) {
         return false;
      } else {
         net.minecraft.client.model.geom.ModelPart root = this.getRoot();
         if (root == null) {
            return false;
         } else {
            AttachmentType type = armIn == HumanoidArm.LEFT ? AttachmentType.LEFT_HANDHELD_ITEM : AttachmentType.RIGHT_HANDHELD_ITEM;
            return root.applyAttachmentTransform(type, matrixStackIn);
         }
      }
   }

   private net.minecraft.client.model.geom.ModelPart getRoot() {
      ArmedModel model = this.m_117386_();
      if (model instanceof HumanoidModel humanoidModel) {
         return humanoidModel.f_102810_.getParent();
      } else {
         return model instanceof HierarchicalModel hierarchicalModel ? hierarchicalModel.m_142109_() : null;
      }
   }
}
