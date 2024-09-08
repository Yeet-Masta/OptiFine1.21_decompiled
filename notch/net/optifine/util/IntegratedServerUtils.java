package net.optifine.util;

import java.util.UUID;
import net.minecraft.src.C_12_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_2116_;
import net.minecraft.src.C_313554_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4585_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5264_;
import net.optifine.Config;

public class IntegratedServerUtils {
   public static C_12_ getWorldServer() {
      C_3391_ mc = Config.getMinecraft();
      C_1596_ world = mc.f_91073_;
      if (world == null) {
         return null;
      } else if (!mc.m_91090_()) {
         return null;
      } else {
         C_4585_ is = mc.m_91092_();
         if (is == null) {
            return null;
         } else {
            C_5264_<C_1596_> wd = world.m_46472_();
            if (wd == null) {
               return null;
            } else {
               try {
                  return is.a(wd);
               } catch (NullPointerException var5) {
                  return null;
               }
            }
         }
      }
   }

   public static C_507_ getEntity(UUID uuid) {
      C_12_ ws = getWorldServer();
      return ws == null ? null : ws.m_8791_(uuid);
   }

   public static C_1991_ getTileEntity(C_4675_ pos) {
      C_12_ ws = getWorldServer();
      if (ws == null) {
         return null;
      } else {
         C_2116_ chunk = ws.m_7726_().m_7587_(pos.u() >> 4, pos.w() >> 4, C_313554_.f_315432_, false);
         return chunk == null ? null : chunk.c_(pos);
      }
   }
}
