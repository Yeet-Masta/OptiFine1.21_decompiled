package net.optifine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.optifine.reflect.Reflector;

public class TileEntityUtils {
   public static String getTileEntityName(BlockGetter blockAccess, BlockPos blockPos) {
      net.minecraft.world.level.block.entity.BlockEntity te = blockAccess.m_7702_(blockPos);
      return getTileEntityName(te);
   }

   public static String getTileEntityName(net.minecraft.world.level.block.entity.BlockEntity te) {
      if (!(te instanceof Nameable iwn)) {
         return null;
      } else {
         updateTileEntityName(te);
         return !iwn.m_8077_() ? null : iwn.m_7770_().getString();
      }
   }

   public static void updateTileEntityName(net.minecraft.world.level.block.entity.BlockEntity te) {
      BlockPos pos = te.m_58899_();
      Component name = getTileEntityRawName(te);
      if (name == null) {
         Component nameServer = getServerTileEntityRawName(pos);
         if (nameServer == null) {
            nameServer = Component.m_237113_("");
         }

         setTileEntityRawName(te, nameServer);
      }
   }

   public static Component getServerTileEntityRawName(BlockPos blockPos) {
      net.minecraft.world.level.block.entity.BlockEntity tes = IntegratedServerUtils.getTileEntity(blockPos);
      return tes == null ? null : getTileEntityRawName(tes);
   }

   public static Component getTileEntityRawName(net.minecraft.world.level.block.entity.BlockEntity te) {
      if (te instanceof Nameable) {
         return ((Nameable)te).m_7770_();
      } else {
         return te instanceof BeaconBlockEntity ? (Component)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName) : null;
      }
   }

   public static boolean setTileEntityRawName(net.minecraft.world.level.block.entity.BlockEntity te, Component name) {
      if (te instanceof BaseContainerBlockEntity) {
         Reflector.BaseContainerBlockEntity_customName.setValue(te, name);
         return true;
      } else if (te instanceof BannerBlockEntity) {
         Reflector.BannerBlockEntity_customName.setValue(te, name);
         return true;
      } else if (te instanceof EnchantingTableBlockEntity) {
         ((EnchantingTableBlockEntity)te).m_321991_(name);
         return true;
      } else if (te instanceof BeaconBlockEntity) {
         ((BeaconBlockEntity)te).m_58681_(name);
         return true;
      } else {
         return false;
      }
   }
}
