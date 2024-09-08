package net.optifine.entity.model.anim;

import java.util.HashMap;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5247_;

public class EntityVariableFloat implements IModelVariableFloat {
   private String name;
   private static C_4330_ renderManager = C_3391_.m_91087_().m_91290_();

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
      C_5247_ entityData = getEntityData();
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
      C_5247_ entityData = getEntityData();
      if (entityData != null) {
         if (entityData.modelVariables == null) {
            entityData.modelVariables = new HashMap();
         }

         entityData.modelVariables.put(key, value);
      }
   }

   private static C_5247_ getEntityData() {
      C_507_ entity = renderManager.getRenderedEntity();
      return entity == null ? null : entity.m_20088_();
   }
}
