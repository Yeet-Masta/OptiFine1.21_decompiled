package net.minecraft.src;

import net.optifine.entity.model.CustomEntityModels;
import net.optifine.model.AttachmentType;

public class C_4441_<T extends C_524_, M extends C_3819_<T> & C_3797_> extends C_4447_<T, M> {
   private final C_4131_ f_234844_;

   public C_4441_(C_4382_<T, M> p_i234845_1_, C_4131_ p_i234845_2_) {
      super(p_i234845_1_);
      this.f_234844_ = p_i234845_2_;
   }

   public void m_6494_(
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      boolean flag = entitylivingbaseIn.m_5737_() == C_520_.RIGHT;
      C_1391_ itemstack = flag ? entitylivingbaseIn.m_21206_() : entitylivingbaseIn.m_21205_();
      C_1391_ itemstack1 = flag ? entitylivingbaseIn.m_21205_() : entitylivingbaseIn.m_21206_();
      if (!itemstack.m_41619_() || !itemstack1.m_41619_()) {
         matrixStackIn.m_85836_();
         if (this.m_117386_().f_102610_) {
            float f = 0.5F;
            matrixStackIn.m_252880_(0.0F, 0.75F, 0.0F);
            matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
         }

         this.m_117184_(entitylivingbaseIn, itemstack1, C_268388_.THIRD_PERSON_RIGHT_HAND, C_520_.RIGHT, matrixStackIn, bufferIn, packedLightIn);
         this.m_117184_(entitylivingbaseIn, itemstack, C_268388_.THIRD_PERSON_LEFT_HAND, C_520_.LEFT, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.m_85849_();
      }
   }

   protected void m_117184_(
      C_524_ entityIn, C_1391_ itemStackIn, C_268388_ contextIn, C_520_ armIn, C_3181_ matrixStackIn, C_4139_ bufferSourceIn, int combinedLightIn
   ) {
      if (!itemStackIn.m_41619_()) {
         matrixStackIn.m_85836_();
         if (!this.applyAttachmentTransform(armIn, matrixStackIn)) {
            this.m_117386_().m_6002_(armIn, matrixStackIn);
         }

         matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-90.0F));
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(180.0F));
         boolean flag = armIn == C_520_.LEFT;
         matrixStackIn.m_252880_((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
         this.f_234844_.m_269530_(entityIn, itemStackIn, contextIn, flag, matrixStackIn, bufferSourceIn, combinedLightIn);
         matrixStackIn.m_85849_();
      }
   }

   private boolean applyAttachmentTransform(C_520_ armIn, C_3181_ matrixStackIn) {
      if (!CustomEntityModels.isActive()) {
         return false;
      } else {
         C_3889_ root = this.getRoot();
         if (root == null) {
            return false;
         } else {
            AttachmentType type = armIn == C_520_.LEFT ? AttachmentType.LEFT_HANDHELD_ITEM : AttachmentType.RIGHT_HANDHELD_ITEM;
            return root.applyAttachmentTransform(type, matrixStackIn);
         }
      }
   }

   private C_3889_ getRoot() {
      C_3797_ model = this.m_117386_();
      if (model instanceof C_3829_ humanoidModel) {
         return humanoidModel.f_102810_.getParent();
      } else {
         return model instanceof C_141650_ hierarchicalModel ? hierarchicalModel.m_142109_() : null;
      }
   }
}
