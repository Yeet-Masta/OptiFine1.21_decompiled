package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;

public class ModelRendererUtils {
   public static net.minecraft.client.model.geom.ModelPart getModelRenderer(Iterator<net.minecraft.client.model.geom.ModelPart> iterator, int index) {
      if (iterator == null) {
         return null;
      } else if (index < 0) {
         return null;
      } else {
         for (int i = 0; i < index; i++) {
            if (!iterator.hasNext()) {
               return null;
            }

            net.minecraft.client.model.geom.ModelPart var3 = (net.minecraft.client.model.geom.ModelPart)iterator.next();
         }

         return !iterator.hasNext() ? null : (net.minecraft.client.model.geom.ModelPart)iterator.next();
      }
   }

   public static net.minecraft.client.model.geom.ModelPart getModelRenderer(ImmutableList<net.minecraft.client.model.geom.ModelPart> models, int index) {
      if (models == null) {
         return null;
      } else if (index < 0) {
         return null;
      } else {
         return index >= models.size() ? null : (net.minecraft.client.model.geom.ModelPart)models.get(index);
      }
   }
}
