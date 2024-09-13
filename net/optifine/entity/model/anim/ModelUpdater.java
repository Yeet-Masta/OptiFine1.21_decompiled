package net.optifine.entity.model.anim;

public class ModelUpdater {
   private ModelVariableUpdater[] modelVariableUpdaters;

   public ModelUpdater(ModelVariableUpdater[] modelVariableUpdaters) {
      this.modelVariableUpdaters = modelVariableUpdaters;
   }

   public ModelVariableUpdater[] getModelVariableUpdaters() {
      return this.modelVariableUpdaters;
   }

   public void m_252999_() {
      for (int i = 0; i < this.modelVariableUpdaters.length; i++) {
         ModelVariableUpdater mvu = this.modelVariableUpdaters[i];
         mvu.m_252999_();
      }
   }

   public boolean initialize(IModelResolver mr) {
      for (int i = 0; i < this.modelVariableUpdaters.length; i++) {
         ModelVariableUpdater mvu = this.modelVariableUpdaters[i];
         if (!mvu.initialize(mr)) {
            return false;
         }
      }

      return true;
   }
}
