package net.optifine.model;

public class AttachmentPath {
   private Attachment attachment;
   private net.minecraft.client.model.geom.ModelPart[] modelParts;

   public AttachmentPath(Attachment attachment, net.minecraft.client.model.geom.ModelPart[] modelParts) {
      this.attachment = attachment;
      this.modelParts = modelParts;
   }

   public Attachment getAttachment() {
      return this.attachment;
   }

   public net.minecraft.client.model.geom.ModelPart[] getModelParts() {
      return this.modelParts;
   }

   public boolean isVisible() {
      for (int i = 0; i < this.modelParts.length; i++) {
         net.minecraft.client.model.geom.ModelPart modelPart = this.modelParts[i];
         if (!modelPart.f_104207_) {
            return false;
         }
      }

      return true;
   }

   public void applyTransform(com.mojang.blaze3d.vertex.PoseStack matrixStackIn) {
      for (int i = 0; i < this.modelParts.length; i++) {
         net.minecraft.client.model.geom.ModelPart modelPart = this.modelParts[i];
         modelPart.m_104299_(matrixStackIn);
      }

      this.attachment.applyTransform(matrixStackIn);
   }

   public String toString() {
      return "attachment: " + this.attachment.getType() + ", parents: " + this.modelParts.length;
   }
}
