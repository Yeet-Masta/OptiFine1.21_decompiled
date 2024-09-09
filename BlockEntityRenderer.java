import java.util.IdentityHashMap;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_4703_;
import net.minecraft.src.C_513_;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public interface BlockEntityRenderer<T extends BlockEntity> extends IEntityRenderer {
   IdentityHashMap<BlockEntityRenderer, C_1992_> CACHED_TYPES = new IdentityHashMap();

   void a(T var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6);

   default boolean a(T te) {
      return false;
   }

   default int aW_() {
      return 64;
   }

   default boolean a(T blockEntityIn, Vec3 posIn) {
      return Vec3.b(blockEntityIn.aD_()).a((C_4703_)posIn, (double)this.aW_());
   }

   @Override
   default Either<C_513_, C_1992_> getType() {
      C_1992_ type = (C_1992_)CACHED_TYPES.get(this);
      return type == null ? null : Either.makeRight(type);
   }

   @Override
   default void setType(Either<C_513_, C_1992_> type) {
      CACHED_TYPES.put(this, (C_1992_)type.getRight().get());
   }

   default ResourceLocation getLocationTextureCustom() {
      return null;
   }

   default void setLocationTextureCustom(ResourceLocation locationTextureCustom) {
   }
}
