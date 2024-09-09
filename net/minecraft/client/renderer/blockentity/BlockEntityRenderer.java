package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.IdentityHashMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public interface BlockEntityRenderer extends IEntityRenderer {
   IdentityHashMap CACHED_TYPES = new IdentityHashMap();

   void m_6922_(BlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6);

   default boolean m_5932_(BlockEntity te) {
      return false;
   }

   default int m_142163_() {
      return 64;
   }

   default boolean m_142756_(BlockEntity blockEntityIn, Vec3 posIn) {
      return Vec3.m_82512_(blockEntityIn.m_58899_()).m_82509_(posIn, (double)this.m_142163_());
   }

   default Either getType() {
      BlockEntityType type = (BlockEntityType)CACHED_TYPES.get(this);
      return type == null ? null : Either.makeRight(type);
   }

   default void setType(Either type) {
      CACHED_TYPES.put(this, (BlockEntityType)type.getRight().get());
   }

   default ResourceLocation getLocationTextureCustom() {
      return null;
   }

   default void setLocationTextureCustom(ResourceLocation locationTextureCustom) {
   }
}
