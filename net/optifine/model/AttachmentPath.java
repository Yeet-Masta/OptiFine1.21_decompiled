package net.optifine.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;

public class AttachmentPath {
   private Attachment attachment;
   private ModelPart[] modelParts;

   public AttachmentPath(Attachment attachment, ModelPart[] modelParts) {
      this.attachment = attachment;
      this.modelParts = modelParts;
   }

   public Attachment getAttachment() {
      return this.attachment;
   }

   public ModelPart[] getModelParts() {
      return this.modelParts;
   }

   public boolean isVisible() {
      for (int i = 0; i < this.modelParts.length; i++) {
         ModelPart modelPart = this.modelParts[i];
         if (!modelPart.f_104207_) {
            return false;
         }
      }

      return true;
   }

   public void applyTransform(PoseStack matrixStackIn) {
      for (int i = 0; i < this.modelParts.length; i++) {
         ModelPart modelPart = this.modelParts[i];
         modelPart.m_104299_(matrixStackIn);
      }

      this.attachment.applyTransform(matrixStackIn);
   }

   public String toString() {
      return "attachment: " + this.attachment.getType() + ", parents: " + this.modelParts.length;
   }
}
