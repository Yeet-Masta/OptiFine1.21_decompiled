package net.optifine.model;

import java.util.ArrayList;
import java.util.List;

public class AttachmentPaths {
   private List<List<AttachmentPath>> pathsByType = new ArrayList();

   public void addPath(AttachmentPath ap) {
      AttachmentType type = ap.getAttachment().getType();

      while (this.pathsByType.size() <= type.ordinal()) {
         this.pathsByType.add(null);
      }

      List<AttachmentPath> paths = (List<AttachmentPath>)this.pathsByType.get(type.ordinal());
      if (paths == null) {
         paths = new ArrayList();
         this.pathsByType.set(type.ordinal(), paths);
      }

      paths.add(ap);
   }

   public void addPaths(List<net.minecraft.client.model.geom.ModelPart> parents, Attachment[] attachments) {
      net.minecraft.client.model.geom.ModelPart[] parentArr = (net.minecraft.client.model.geom.ModelPart[])parents.toArray(
         new net.minecraft.client.model.geom.ModelPart[parents.size()]
      );

      for (int i = 0; i < attachments.length; i++) {
         Attachment at = attachments[i];
         AttachmentPath path = new AttachmentPath(at, parentArr);
         this.addPath(path);
      }
   }

   public boolean isEmpty() {
      return this.pathsByType.isEmpty();
   }

   public AttachmentPath getVisiblePath(AttachmentType typeIn) {
      if (this.pathsByType.size() <= typeIn.ordinal()) {
         return null;
      } else {
         for (AttachmentPath path : (List)this.pathsByType.get(typeIn.ordinal())) {
            if (path.isVisible()) {
               return path;
            }
         }

         return null;
      }
   }

   public String toString() {
      int count = 0;

      for (List paths : this.pathsByType) {
         if (paths != null) {
            count++;
         }
      }

      return "types: " + count;
   }
}
