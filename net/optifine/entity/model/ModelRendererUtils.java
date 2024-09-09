package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import net.minecraft.client.model.geom.ModelPart;

public class ModelRendererUtils {
   public static ModelPart getModelRenderer(Iterator iterator, int index) {
      if (iterator == null) {
         return null;
      } else if (index < 0) {
         return null;
      } else {
         for(int i = 0; i < index; ++i) {
            if (!iterator.hasNext()) {
               return null;
            }

            ModelPart var3 = (ModelPart)iterator.next();
         }

         if (!iterator.hasNext()) {
            return null;
         } else {
            ModelPart model = (ModelPart)iterator.next();
            return model;
         }
      }
   }

   public static ModelPart getModelRenderer(ImmutableList models, int index) {
      if (models == null) {
         return null;
      } else if (index < 0) {
         return null;
      } else {
         return index >= models.size() ? null : (ModelPart)models.get(index);
      }
   }
}
