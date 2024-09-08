package net.optifine.entity.model.anim;

import java.util.HashMap;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5247_;

public class EntityVariableBool implements IModelVariableBool {
   private String name;
   private C_4330_ renderManager;

   public EntityVariableBool(String name) {
      this.name = name;
      this.renderManager = C_3391_.m_91087_().m_91290_();
   }

   @Override
   public boolean eval() {
      return this.getValue();
   }

   @Override
   public boolean getValue() {
      C_5247_ entityData = this.getEntityData();
      if (entityData == null) {
         return false;
      } else if (entityData.modelVariables == null) {
         return false;
      } else {
         Boolean val = (Boolean)entityData.modelVariables.get(this.name);
         return val == null ? false : val;
      }
   }

   @Override
   public void setValue(boolean value) {
      C_5247_ entityData = this.getEntityData();
      if (entityData != null) {
         if (entityData.modelVariables == null) {
            entityData.modelVariables = new HashMap();
         }

         entityData.modelVariables.put(this.name, value);
      }
   }

   private C_5247_ getEntityData() {
      C_507_ entity = this.renderManager.getRenderedEntity();
      return entity == null ? null : entity.m_20088_();
   }
}
