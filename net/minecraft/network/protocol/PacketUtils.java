package net.minecraft.network.protocol;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.optifine.util.PacketRunnable;
import org.slf4j.Logger;

public class PacketUtils {
   private static final Logger f_131354_ = LogUtils.getLogger();
   public static ResourceKey<Level> lastDimensionType = null;

   public static <T extends PacketListener> void m_131359_(Packet<T> packetIn, T processor, ServerLevel worldIn) throws RunningOnDifferentThreadException {
      m_131363_(packetIn, processor, worldIn.m_7654_());
   }

   public static <T extends PacketListener> void m_131363_(Packet<T> packetIn, T processor, net.minecraft.util.thread.BlockableEventLoop<?> executor) throws RunningOnDifferentThreadException {
      if (!executor.m_18695_()) {
         executor.m_201446_(new PacketRunnable(packetIn, () -> {
            clientPreProcessPacket(packetIn);
            if (processor.m_294638_(packetIn)) {
               try {
                  packetIn.m_5797_(processor);
               } catch (Exception var4) {
                  if (var4 instanceof ReportedException reportedexception && reportedexception.getCause() instanceof OutOfMemoryError) {
                     throw m_322247_(var4, packetIn, processor);
                  }

                  processor.m_322364_(packetIn, var4);
               }
            } else {
               f_131354_.debug("Ignoring packet due to disconnection: {}", packetIn);
            }
         }));
         throw RunningOnDifferentThreadException.f_136017_;
      } else {
         clientPreProcessPacket(packetIn);
      }
   }

   protected static void clientPreProcessPacket(Packet packetIn) {
      if (packetIn instanceof ClientboundPlayerPositionPacket) {
         Minecraft.m_91087_().f_91060_.onPlayerPositionSet();
      }

      if (packetIn instanceof ClientboundRespawnPacket respawn) {
         lastDimensionType = respawn.f_290899_().f_290731_();
      } else if (packetIn instanceof ClientboundLoginPacket joinGame) {
         lastDimensionType = joinGame.f_291078_().f_290731_();
      } else {
         lastDimensionType = null;
      }
   }

   public static <T extends PacketListener> ReportedException m_322247_(Exception exceptionIn, Packet<T> packetIn, T listenerIn) {
      if (exceptionIn instanceof ReportedException reportedexception) {
         m_323092_(reportedexception.m_134761_(), listenerIn, packetIn);
         return reportedexception;
      } else {
         net.minecraft.CrashReport crashreport = net.minecraft.CrashReport.m_127521_(exceptionIn, "Main thread packet handler");
         m_323092_(crashreport, listenerIn, packetIn);
         return new ReportedException(crashreport);
      }
   }

   public static <T extends PacketListener> void m_323092_(net.minecraft.CrashReport reportIn, T listenerIn, @Nullable Packet<T> packetIn) {
      if (packetIn != null) {
         CrashReportCategory crashreportcategory = reportIn.m_127514_("Incoming Packet");
         crashreportcategory.m_128165_("Type", () -> packetIn.m_5779_().toString());
         crashreportcategory.m_128165_("Is Terminal", () -> Boolean.toString(packetIn.m_319635_()));
         crashreportcategory.m_128165_("Is Skippable", () -> Boolean.toString(packetIn.m_6588_()));
      }

      listenerIn.m_307358_(reportIn);
   }
}
