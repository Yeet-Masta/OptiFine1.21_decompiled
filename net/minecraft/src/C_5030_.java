package net.minecraft.src;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.optifine.util.PacketRunnable;
import org.slf4j.Logger;

public class C_5030_ {
   private static final Logger f_131354_ = LogUtils.getLogger();
   public static C_5264_ lastDimensionType = null;

   public static void m_131359_(C_5028_ packetIn, C_4986_ processor, C_12_ worldIn) throws C_5278_ {
      m_131363_(packetIn, processor, worldIn.m_7654_());
   }

   public static void m_131363_(C_5028_ packetIn, C_4986_ processor, C_449_ executor) throws C_5278_ {
      if (!executor.m_18695_()) {
         executor.m_201446_(new PacketRunnable(packetIn, () -> {
            clientPreProcessPacket(packetIn);
            if (processor.m_294638_(packetIn)) {
               try {
                  packetIn.m_5797_(processor);
               } catch (Exception var4) {
                  if (var4 instanceof C_5204_) {
                     C_5204_ reportedexception = (C_5204_)var4;
                     if (reportedexception.getCause() instanceof OutOfMemoryError) {
                        throw m_322247_(var4, packetIn, processor);
                     }
                  }

                  processor.m_322364_(packetIn, var4);
               }
            } else {
               f_131354_.debug("Ignoring packet due to disconnection: {}", packetIn);
            }

         }));
         throw C_5278_.f_136017_;
      } else {
         clientPreProcessPacket(packetIn);
      }
   }

   protected static void clientPreProcessPacket(C_5028_ packetIn) {
      if (packetIn instanceof C_5096_) {
         C_3391_.m_91087_().f_91060_.onPlayerPositionSet();
      }

      if (packetIn instanceof C_5103_ respawn) {
         lastDimensionType = respawn.f_290899_().f_290731_();
      } else if (packetIn instanceof C_5074_ joinGame) {
         lastDimensionType = joinGame.f_291078_().f_290731_();
      } else {
         lastDimensionType = null;
      }

   }

   public static C_5204_ m_322247_(Exception exceptionIn, C_5028_ packetIn, C_4986_ listenerIn) {
      if (exceptionIn instanceof C_5204_ reportedexception) {
         m_323092_(reportedexception.m_134761_(), listenerIn, packetIn);
         return reportedexception;
      } else {
         C_4883_ crashreport = C_4883_.m_127521_(exceptionIn, "Main thread packet handler");
         m_323092_(crashreport, listenerIn, packetIn);
         return new C_5204_(crashreport);
      }
   }

   public static void m_323092_(C_4883_ reportIn, C_4986_ listenerIn, @Nullable C_5028_ packetIn) {
      if (packetIn != null) {
         C_4909_ crashreportcategory = reportIn.m_127514_("Incoming Packet");
         crashreportcategory.m_128165_("Type", () -> {
            return packetIn.m_5779_().toString();
         });
         crashreportcategory.m_128165_("Is Terminal", () -> {
            return Boolean.toString(packetIn.m_319635_());
         });
         crashreportcategory.m_128165_("Is Skippable", () -> {
            return Boolean.toString(packetIn.m_6588_());
         });
      }

      listenerIn.m_307358_(reportIn);
   }
}
