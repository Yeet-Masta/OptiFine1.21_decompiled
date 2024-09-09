import net.minecraft.src.C_1391_;
import net.minecraft.src.C_141650_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_268388_;
import net.minecraft.src.C_3797_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_3829_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_520_;
import net.minecraft.src.C_524_;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.model.AttachmentType;

public class ItemInHandLayer<T extends C_524_, M extends C_3819_<T> & C_3797_> extends RenderLayer<T, M> {
   private final ItemInHandRenderer a;

   public ItemInHandLayer(C_4382_<T, M> p_i234845_1_, ItemInHandRenderer p_i234845_2_) {
      super(p_i234845_1_);
      this.a = p_i234845_2_;
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
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
         matrixStackIn.a();
         if (this.c().f_102610_) {
            float f = 0.5F;
            matrixStackIn.a(0.0F, 0.75F, 0.0F);
            matrixStackIn.b(0.5F, 0.5F, 0.5F);
         }

         this.a(entitylivingbaseIn, itemstack1, C_268388_.THIRD_PERSON_RIGHT_HAND, C_520_.RIGHT, matrixStackIn, bufferIn, packedLightIn);
         this.a(entitylivingbaseIn, itemstack, C_268388_.THIRD_PERSON_LEFT_HAND, C_520_.LEFT, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.b();
      }
   }

   protected void a(
      C_524_ entityIn, C_1391_ itemStackIn, C_268388_ contextIn, C_520_ armIn, PoseStack matrixStackIn, MultiBufferSource bufferSourceIn, int combinedLightIn
   ) {
      if (!itemStackIn.m_41619_()) {
         matrixStackIn.a();
         if (!this.applyAttachmentTransform(armIn, matrixStackIn)) {
            this.c().a(armIn, matrixStackIn);
         }

         matrixStackIn.a(C_252363_.f_252529_.m_252977_(-90.0F));
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(180.0F));
         boolean flag = armIn == C_520_.LEFT;
         matrixStackIn.a((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
         this.a.a(entityIn, itemStackIn, contextIn, flag, matrixStackIn, bufferSourceIn, combinedLightIn);
         matrixStackIn.b();
      }
   }

   private boolean applyAttachmentTransform(C_520_ armIn, PoseStack matrixStackIn) {
      if (!CustomEntityModels.isActive()) {
         return false;
      } else {
         ModelPart root = this.getRoot();
         if (root == null) {
            return false;
         } else {
            AttachmentType type = armIn == C_520_.LEFT ? AttachmentType.LEFT_HANDHELD_ITEM : AttachmentType.RIGHT_HANDHELD_ITEM;
            return root.applyAttachmentTransform(type, matrixStackIn);
         }
      }
   }

   private ModelPart getRoot() {
      C_3797_ model = this.c();
      if (model instanceof C_3829_ humanoidModel) {
         return humanoidModel.m.getParent();
      } else {
         return model instanceof C_141650_ hierarchicalModel ? hierarchicalModel.a() : null;
      }
   }
}
