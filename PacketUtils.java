import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.src.C_12_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_4986_;
import net.minecraft.src.C_5028_;
import net.minecraft.src.C_5074_;
import net.minecraft.src.C_5096_;
import net.minecraft.src.C_5103_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_5278_;
import net.optifine.util.PacketRunnable;
import org.slf4j.Logger;

public class PacketUtils {
   private static final Logger a = LogUtils.getLogger();
   public static C_5264_<C_1596_> lastDimensionType = null;

   public static <T extends C_4986_> void a(C_5028_<T> packetIn, T processor, C_12_ worldIn) throws C_5278_ {
      a(packetIn, processor, worldIn.m_7654_());
   }

   public static <T extends C_4986_> void a(C_5028_<T> packetIn, T processor, BlockableEventLoop<?> executor) throws C_5278_ {
      if (!executor.bx()) {
         executor.c(new PacketRunnable(packetIn, () -> {
            clientPreProcessPacket(packetIn);
            if (processor.m_294638_(packetIn)) {
               try {
                  packetIn.m_5797_(processor);
               } catch (Exception var4) {
                  if (var4 instanceof C_5204_ reportedexception && reportedexception.getCause() instanceof OutOfMemoryError) {
                     throw a(var4, packetIn, processor);
                  }

                  processor.m_322364_(packetIn, var4);
               }
            } else {
               a.debug("Ignoring packet due to disconnection: {}", packetIn);
            }
         }));
         throw C_5278_.f_136017_;
      } else {
         clientPreProcessPacket(packetIn);
      }
   }

   protected static void clientPreProcessPacket(C_5028_ packetIn) {
      if (packetIn instanceof C_5096_) {
         C_3391_.m_91087_().f.onPlayerPositionSet();
      }

      if (packetIn instanceof C_5103_ respawn) {
         lastDimensionType = respawn.f_290899_().f_290731_();
      } else if (packetIn instanceof C_5074_ joinGame) {
         lastDimensionType = joinGame.f_291078_().f_290731_();
      } else {
         lastDimensionType = null;
      }
   }

   public static <T extends C_4986_> C_5204_ a(Exception exceptionIn, C_5028_<T> packetIn, T listenerIn) {
      if (exceptionIn instanceof C_5204_ reportedexception) {
         a(reportedexception.a(), listenerIn, packetIn);
         return reportedexception;
      } else {
         CrashReport crashreport = CrashReport.a(exceptionIn, "Main thread packet handler");
         a(crashreport, listenerIn, packetIn);
         return new C_5204_(crashreport);
      }
   }

   public static <T extends C_4986_> void a(CrashReport reportIn, T listenerIn, @Nullable C_5028_<T> packetIn) {
      if (packetIn != null) {
         C_4909_ crashreportcategory = reportIn.a("Incoming Packet");
         crashreportcategory.m_128165_("Type", () -> packetIn.m_5779_().toString());
         crashreportcategory.m_128165_("Is Terminal", () -> Boolean.toString(packetIn.m_319635_()));
         crashreportcategory.m_128165_("Is Skippable", () -> Boolean.toString(packetIn.m_6588_()));
      }

      listenerIn.a(reportIn);
   }
}
