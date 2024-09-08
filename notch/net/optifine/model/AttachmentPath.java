package net.optifine.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3889_;

public class AttachmentPath {
   private Attachment attachment;
   private C_3889_[] modelParts;

   public AttachmentPath(Attachment attachment, C_3889_[] modelParts) {
      this.attachment = attachment;
      this.modelParts = modelParts;
   }

   public Attachment getAttachment() {
      return this.attachment;
   }

   public C_3889_[] getModelParts() {
      return this.modelParts;
   }

   public boolean isVisible() {
      for (int i = 0; i < this.modelParts.length; i++) {
         C_3889_ modelPart = this.modelParts[i];
         if (!modelPart.f_104207_) {
            return false;
         }
      }

      return true;
   }

   public void applyTransform(C_3181_ matrixStackIn) {
      for (int i = 0; i < this.modelParts.length; i++) {
         C_3889_ modelPart = this.modelParts[i];
         modelPart.m_104299_(matrixStackIn);
      }

      this.attachment.applyTransform(matrixStackIn);
   }

   public String toString() {
      return "attachment: " + this.attachment.getType() + ", parents: " + this.modelParts.length;
   }
}
