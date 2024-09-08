package net.minecraft.src;

import java.util.IdentityHashMap;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public interface C_4244_<T extends C_1991_> extends IEntityRenderer {
   IdentityHashMap<C_4244_, C_1992_> CACHED_TYPES = new IdentityHashMap();

   void m_6922_(T var1, float var2, C_3181_ var3, C_4139_ var4, int var5, int var6);

   default boolean m_5932_(T te) {
      return false;
   }

   default int m_142163_() {
      return 64;
   }

   default boolean m_142756_(T blockEntityIn, C_3046_ posIn) {
      return C_3046_.m_82512_(blockEntityIn.m_58899_()).m_82509_(posIn, (double)this.m_142163_());
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

   default C_5265_ getLocationTextureCustom() {
      return null;
   }

   default void setLocationTextureCustom(C_5265_ locationTextureCustom) {
   }
}
