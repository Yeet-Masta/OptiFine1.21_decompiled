package net.optifine.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.C_3889_;

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

   public void addPaths(List<C_3889_> parents, Attachment[] attachments) {
      C_3889_[] parentArr = (C_3889_[])parents.toArray(new C_3889_[parents.size()]);

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
