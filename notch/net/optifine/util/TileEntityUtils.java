package net.optifine.util;

import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1976_;
import net.minecraft.src.C_1980_;
import net.minecraft.src.C_1981_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_313415_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_475_;
import net.minecraft.src.C_4996_;
import net.optifine.reflect.Reflector;

public class TileEntityUtils {
   public static String getTileEntityName(C_1559_ blockAccess, C_4675_ blockPos) {
      C_1991_ te = blockAccess.m_7702_(blockPos);
      return getTileEntityName(te);
   }

   public static String getTileEntityName(C_1991_ te) {
      if (!(te instanceof C_475_ iwn)) {
         return null;
      } else {
         updateTileEntityName(te);
         return !iwn.m_8077_() ? null : iwn.m_7770_().getString();
      }
   }

   public static void updateTileEntityName(C_1991_ te) {
      C_4675_ pos = te.m_58899_();
      C_4996_ name = getTileEntityRawName(te);
      if (name == null) {
         C_4996_ nameServer = getServerTileEntityRawName(pos);
         if (nameServer == null) {
            nameServer = C_4996_.m_237113_("");
         }

         setTileEntityRawName(te, nameServer);
      }
   }

   public static C_4996_ getServerTileEntityRawName(C_4675_ blockPos) {
      C_1991_ tes = IntegratedServerUtils.getTileEntity(blockPos);
      return tes == null ? null : getTileEntityRawName(tes);
   }

   public static C_4996_ getTileEntityRawName(C_1991_ te) {
      if (te instanceof C_475_) {
         return ((C_475_)te).m_7770_();
      } else {
         return te instanceof C_1981_ ? (C_4996_)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName) : null;
      }
   }

   public static boolean setTileEntityRawName(C_1991_ te, C_4996_ name) {
      if (te instanceof C_1980_) {
         Reflector.BaseContainerBlockEntity_customName.setValue(te, name);
         return true;
      } else if (te instanceof C_1976_) {
         Reflector.BannerBlockEntity_customName.setValue(te, name);
         return true;
      } else if (te instanceof C_313415_) {
         ((C_313415_)te).m_321991_(name);
         return true;
      } else if (te instanceof C_1981_) {
         ((C_1981_)te).m_58681_(name);
         return true;
      } else {
         return false;
      }
   }
}
