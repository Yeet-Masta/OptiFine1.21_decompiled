package net.optifine.entity.model.anim;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class EntityVariableFloat implements IModelVariableFloat {
   private String name;
   private static net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();

   public EntityVariableFloat(String name) {
      this.name = name;
   }

   @Override
   public float eval() {
      return this.getValue();
   }

   @Override
   public float getValue() {
      return getEntityValue(this.name);
   }

   public static float getEntityValue(String key) {
      net.minecraft.network.syncher.SynchedEntityData entityData = getEntityData();
      if (entityData == null) {
         return 0.0F;
      } else if (entityData.modelVariables == null) {
         return 0.0F;
      } else {
         Float val = (Float)entityData.modelVariables.get(key);
         return val == null ? 0.0F : val;
      }
   }

   @Override
   public void setValue(float value) {
      setEntityValue(this.name, value);
   }

   public static void setEntityValue(String key, float value) {
      net.minecraft.network.syncher.SynchedEntityData entityData = getEntityData();
      if (entityData != null) {
         if (entityData.modelVariables == null) {
            entityData.modelVariables = new HashMap();
         }

         entityData.modelVariables.put(key, value);
      }
   }

   private static net.minecraft.network.syncher.SynchedEntityData getEntityData() {
      Entity entity = renderManager.getRenderedEntity();
      return entity == null ? null : entity.m_20088_();
   }
}
