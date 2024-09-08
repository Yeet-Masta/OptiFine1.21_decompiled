package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3864_;
import net.minecraft.src.C_3889_;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;

public class ModelAdapterShield extends ModelAdapter {
   public ModelAdapterShield() {
      super((Either<EntityType, BlockEntityType>)null, "shield", 0.0F, null);
   }

   public C_3840_ makeModel() {
      return new C_3864_(bakeModelLayer(C_141656_.f_171179_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3864_ modelShield)) {
         return null;
      } else if (modelPart.equals("plate")) {
         return (C_3889_)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 1);
      } else if (modelPart.equals("handle")) {
         return (C_3889_)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 2);
      } else {
         return modelPart.equals("root") ? (C_3889_)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 0) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"plate", "handle", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      IEntityRenderer rc = new VirtualEntityRenderer(modelBase);
      return rc;
   }
}
