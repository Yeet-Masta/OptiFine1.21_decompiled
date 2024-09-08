package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import net.minecraft.src.C_3889_;

public class ModelRendererUtils {
   public static C_3889_ getModelRenderer(Iterator<C_3889_> iterator, int index) {
      if (iterator == null) {
         return null;
      } else if (index < 0) {
         return null;
      } else {
         for (int i = 0; i < index; i++) {
            if (!iterator.hasNext()) {
               return null;
            }

            C_3889_ var3 = (C_3889_)iterator.next();
         }

         return !iterator.hasNext() ? null : (C_3889_)iterator.next();
      }
   }

   public static C_3889_ getModelRenderer(ImmutableList<C_3889_> models, int index) {
      if (models == null) {
         return null;
      } else if (index < 0) {
         return null;
      } else {
         return index >= models.size() ? null : (C_3889_)models.get(index);
      }
   }
}
