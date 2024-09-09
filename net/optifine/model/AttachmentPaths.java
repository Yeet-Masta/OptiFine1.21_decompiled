package net.optifine.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.model.geom.ModelPart;

public class AttachmentPaths {
   private List pathsByType = new ArrayList();

   public void addPath(AttachmentPath ap) {
      AttachmentType type = ap.getAttachment().getType();

      while(this.pathsByType.size() <= type.ordinal()) {
         this.pathsByType.add((Object)null);
      }

      List paths = (List)this.pathsByType.get(type.ordinal());
      if (paths == null) {
         paths = new ArrayList();
         this.pathsByType.set(type.ordinal(), paths);
      }

      ((List)paths).add(ap);
   }

   public void addPaths(List parents, Attachment[] attachments) {
      ModelPart[] parentArr = (ModelPart[])parents.toArray(new ModelPart[parents.size()]);

      for(int i = 0; i < attachments.length; ++i) {
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
         List paths = (List)this.pathsByType.get(typeIn.ordinal());
         Iterator var3 = paths.iterator();

         AttachmentPath path;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            path = (AttachmentPath)var3.next();
         } while(!path.isVisible());

         return path;
      }
   }

   public String toString() {
      int count = 0;
      Iterator var2 = this.pathsByType.iterator();

      while(var2.hasNext()) {
         List paths = (List)var2.next();
         if (paths != null) {
            ++count;
         }
      }

      return "types: " + count;
   }
}
