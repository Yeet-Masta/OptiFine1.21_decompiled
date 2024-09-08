package net.optifine.entity.model;

import net.minecraft.src.C_3889_;
import net.optifine.entity.model.anim.ModelUpdater;

public class CustomModelRenderer {
   private String modelPart;
   private boolean attach;
   private C_3889_ modelRenderer;
   private ModelUpdater modelUpdater;

   public CustomModelRenderer(String modelPart, boolean attach, C_3889_ modelRenderer, ModelUpdater modelUpdater) {
      this.modelPart = modelPart;
      this.attach = attach;
      this.modelRenderer = modelRenderer;
      this.modelUpdater = modelUpdater;
   }

   public C_3889_ getModelRenderer() {
      return this.modelRenderer;
   }

   public String getModelPart() {
      return this.modelPart;
   }

   public boolean isAttach() {
      return this.attach;
   }

   public ModelUpdater getModelUpdater() {
      return this.modelUpdater;
   }
}
