package net.minecraft.client.renderer.blockentity;

import java.util.IdentityHashMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public interface BlockEntityRenderer<T extends net.minecraft.world.level.block.entity.BlockEntity> extends IEntityRenderer {
   IdentityHashMap<net.minecraft.client.renderer.blockentity.BlockEntityRenderer, BlockEntityType> CACHED_TYPES = new IdentityHashMap();

   void m_6922_(T var1, float var2, com.mojang.blaze3d.vertex.PoseStack var3, net.minecraft.client.renderer.MultiBufferSource var4, int var5, int var6);

   default boolean m_5932_(T te) {
      return false;
   }

   default int m_142163_() {
      return 64;
   }

   default boolean m_142756_(T blockEntityIn, net.minecraft.world.phys.Vec3 posIn) {
      return net.minecraft.world.phys.Vec3.m_82512_(blockEntityIn.m_58899_()).m_82509_(posIn, (double)this.m_142163_());
   }

   @Override
   default Either<EntityType, BlockEntityType> getType() {
      BlockEntityType type = (BlockEntityType)CACHED_TYPES.get(this);
      return type == null ? null : Either.makeRight(type);
   }

   @Override
   default void setType(Either<EntityType, BlockEntityType> type) {
      CACHED_TYPES.put(this, (BlockEntityType)type.getRight().get());
   }

   @Override
   default net.minecraft.resources.ResourceLocation getLocationTextureCustom() {
      return null;
   }

   @Override
   default void setLocationTextureCustom(net.minecraft.resources.ResourceLocation locationTextureCustom) {
   }
}
