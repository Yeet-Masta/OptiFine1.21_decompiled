package net.optifine;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.CrashReport;
import net.minecraft.ReportType;
import net.minecraft.SystemReport;
import net.optifine.http.FileUploadThread;
import net.optifine.http.IFileUploadListener;
import net.optifine.shaders.Shaders;

public class CrashReporter {
   public static void onCrashReport(CrashReport crashReport, SystemReport category) {
      try {
         Throwable cause = crashReport.m_127524_();
         if (cause == null) {
            return;
         }

         if (cause.getClass().getName().contains(".fml.client.SplashProgress")) {
            return;
         }

         if (cause.getClass() == Throwable.class) {
            return;
         }

         if (cause.getClass() == Exception.class && Config.equals(cause.getMessage(), "dummy")) {
            return;
         }

         extendCrashReport(category);
         if (!Config.isTelemetryOn()) {
            return;
         }

         String url = "http://optifine.net/crashReport";
         String reportStr = makeReport(crashReport);
         byte[] content = reportStr.getBytes("ASCII");
         IFileUploadListener listener = new IFileUploadListener() {
            public void fileUploadFinished(String url, byte[] content, Throwable exception) {
            }
         };
         Map headers = new HashMap();
         headers.put("OF-Version", Config.getVersion());
         headers.put("OF-Summary", makeSummary(crashReport));
         FileUploadThread fut = new FileUploadThread(url, headers, content, listener);
         fut.setPriority(10);
         fut.start();
         Thread.sleep(1000L);
      } catch (Exception var9) {
         String var10000 = var9.getClass().getName();
         Config.dbg(var10000 + ": " + var9.getMessage());
      }

   }

   private static String makeReport(CrashReport crashReport) {
      StringBuffer sb = new StringBuffer();
      sb.append("OptiFineVersion: " + Config.getVersion() + "\n");
      sb.append("Summary: " + makeSummary(crashReport) + "\n");
      sb.append("\n");
      sb.append(crashReport.m_127526_(ReportType.f_337619_));
      sb.append("\n");
      return sb.toString();
   }

   private static String makeSummary(CrashReport crashReport) {
      Throwable t = crashReport.m_127524_();
      if (t == null) {
         return "Unknown";
      } else {
         StackTraceElement[] traces = t.getStackTrace();
         String firstTrace = "unknown";
         if (traces.length > 0) {
            firstTrace = traces[0].toString().trim();
         }

         String var10000 = t.getClass().getName();
         String sum = var10000 + ": " + t.getMessage() + " (" + crashReport.m_127511_() + ") [" + firstTrace + "]";
         return sum;
      }
   }

   public static void extendCrashReport(SystemReport cat) {
      cat.m_143519_("OptiFine Version", Config.getVersion());
      cat.m_143519_("OptiFine Build", Config.getBuild());
      if (Config.getGameSettings() != null) {
         cat.m_143519_("Render Distance Chunks", "" + Config.getChunkViewDistance());
         cat.m_143519_("Mipmaps", "" + Config.getMipmapLevels());
         cat.m_143519_("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
         cat.m_143519_("Antialiasing", "" + Config.getAntialiasingLevel());
         cat.m_143519_("Multitexture", "" + Config.isMultiTexture());
      }

      cat.m_143519_("Shaders", "" + Shaders.getShaderPackName());
      cat.m_143519_("OpenGlVersion", "" + Config.openGlVersion);
      cat.m_143519_("OpenGlRenderer", "" + Config.openGlRenderer);
      cat.m_143519_("OpenGlVendor", "" + Config.openGlVendor);
      cat.m_143519_("CpuCount", "" + Config.getAvailableProcessors());
   }
}
