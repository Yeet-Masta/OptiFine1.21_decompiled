package net.optifine.util;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.optifine.Config;

public class IntegratedServerUtils {
   public static ServerLevel getWorldServer() {
      Minecraft mc = Config.getMinecraft();
      Level world = mc.f_91073_;
      if (world == null) {
         return null;
      } else if (!mc.m_91090_()) {
         return null;
      } else {
         IntegratedServer is = mc.m_91092_();
         if (is == null) {
            return null;
         } else {
            ResourceKey wd = world.m_46472_();
            if (wd == null) {
               return null;
            } else {
               try {
                  ServerLevel ws = is.m_129880_(wd);
                  return ws;
               } catch (NullPointerException var5) {
                  return null;
               }
            }
         }
      }
   }

   public static Entity getEntity(UUID uuid) {
      ServerLevel ws = getWorldServer();
      if (ws == null) {
         return null;
      } else {
         Entity e = ws.m_8791_(uuid);
         return e;
      }
   }

   public static BlockEntity getTileEntity(BlockPos pos) {
      ServerLevel ws = getWorldServer();
      if (ws == null) {
         return null;
      } else {
         ChunkAccess chunk = ws.m_7726_().m_7587_(pos.m_123341_() >> 4, pos.m_123343_() >> 4, ChunkStatus.f_315432_, false);
         if (chunk == null) {
            return null;
         } else {
            BlockEntity te = chunk.m_7702_(pos);
            return te;
         }
      }
   }
}
