package net.optifine.entity.model.anim;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;

public class EntityVariableBool implements IModelVariableBool {
   private String name;
   private EntityRenderDispatcher renderManager;

   public EntityVariableBool(String name) {
      this.name = name;
      this.renderManager = Minecraft.m_91087_().m_91290_();
   }

   public boolean eval() {
      return this.getValue();
   }

   public boolean getValue() {
      SynchedEntityData entityData = this.getEntityData();
      if (entityData == null) {
         return false;
      } else if (entityData.modelVariables == null) {
         return false;
      } else {
         Boolean val = (Boolean)entityData.modelVariables.get(this.name);
         return val == null ? false : val;
      }
   }

   public void setValue(boolean value) {
      SynchedEntityData entityData = this.getEntityData();
      if (entityData != null) {
         if (entityData.modelVariables == null) {
            entityData.modelVariables = new HashMap();
         }

         entityData.modelVariables.put(this.name, value);
      }
   }

   private SynchedEntityData getEntityData() {
      Entity entity = this.renderManager.getRenderedEntity();
      return entity == null ? null : entity.m_20088_();
   }
}
