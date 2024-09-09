import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionException;
import javax.annotation.Nullable;
import net.minecraft.src.C_140956_;
import net.minecraft.src.C_181893_;
import net.minecraft.src.C_336586_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5144_;
import net.minecraft.src.C_5204_;
import net.optifine.CrashReporter;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class CrashReport {
   private static final Logger a = LogUtils.getLogger();
   private static final DateTimeFormatter b = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
   private final String c;
   private final Throwable d;
   private final List<C_4909_> e = Lists.newArrayList();
   @Nullable
   private Path f;
   private boolean g = true;
   private StackTraceElement[] h = new StackTraceElement[0];
   private final C_140956_ i = new C_140956_();
   private boolean reported = false;

   public CrashReport(String descriptionIn, Throwable causeThrowable) {
      this.c = descriptionIn;
      this.d = causeThrowable;
   }

   public String a() {
      return this.c;
   }

   public Throwable b() {
      return this.d;
   }

   public String c() {
      StringBuilder stringbuilder = new StringBuilder();
      this.a(stringbuilder);
      return stringbuilder.toString();
   }

   public void a(StringBuilder builder) {
      if ((this.h == null || this.h.length <= 0) && !this.e.isEmpty()) {
         this.h = (StackTraceElement[])ArrayUtils.subarray(((C_4909_)this.e.get(0)).m_128143_(), 0, 1);
      }

      if (this.h != null && this.h.length > 0) {
         builder.append("-- Head --\n");
         builder.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         if (Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.exists()) {
            builder.append(Reflector.CrashReportAnalyser_appendSuspectedMods.callString(this.d, this.h));
            builder.append("Stacktrace:");
            builder.append(Reflector.CrashReportExtender_generateEnhancedStackTraceSTE.callString1(this.h));
         } else {
            builder.append("Stacktrace:\n");

            for (StackTraceElement stacktraceelement : this.h) {
               builder.append("\t").append("at ").append(stacktraceelement);
               builder.append("\n");
            }

            builder.append("\n");
         }
      }

      for (C_4909_ crashreportcategory : this.e) {
         crashreportcategory.m_128168_(builder);
         builder.append("\n\n");
      }

      Reflector.CrashReportExtender_extendSystemReport.call(this.i);
      this.i.m_143525_(builder);
   }

   public String d() {
      StringWriter stringwriter = null;
      PrintWriter printwriter = null;
      Throwable throwable = this.d;
      if (throwable.getMessage() == null) {
         if (throwable instanceof NullPointerException) {
            throwable = new NullPointerException(this.c);
         } else if (throwable instanceof StackOverflowError) {
            throwable = new StackOverflowError(this.c);
         } else if (throwable instanceof OutOfMemoryError) {
            throwable = new OutOfMemoryError(this.c);
         }

         throwable.setStackTrace(this.d.getStackTrace());
      }

      try {
         if (Reflector.CrashReportExtender_generateEnhancedStackTraceT.exists()) {
            return Reflector.CrashReportExtender_generateEnhancedStackTraceT.callString(throwable);
         }
      } catch (Throwable var9) {
         var9.printStackTrace();
      }

      String s;
      try {
         stringwriter = new StringWriter();
         printwriter = new PrintWriter(stringwriter);
         throwable.printStackTrace(printwriter);
         s = stringwriter.toString();
      } finally {
         IOUtils.closeQuietly(stringwriter);
         IOUtils.closeQuietly(printwriter);
      }

      return s;
   }

   public String a(C_336586_ typeIn, List<String> headerIn) {
      if (!this.reported) {
         this.reported = true;
         CrashReporter.onCrashReport(this, this.i);
      }

      StringBuilder stringbuilder = new StringBuilder();
      typeIn.m_338999_(stringbuilder, headerIn);
      stringbuilder.append("Time: ");
      stringbuilder.append(b.format(ZonedDateTime.now()));
      stringbuilder.append("\n");
      stringbuilder.append("Description: ");
      stringbuilder.append(this.c);
      stringbuilder.append("\n\n");
      stringbuilder.append(this.d());
      stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for (int i = 0; i < 87; i++) {
         stringbuilder.append("-");
      }

      stringbuilder.append("\n\n");
      this.a(stringbuilder);
      return stringbuilder.toString();
   }

   public String a(C_336586_ typeIn) {
      return this.a(typeIn, List.of());
   }

   @Nullable
   public Path e() {
      return this.f;
   }

   public boolean a(Path pathIn, C_336586_ typeIn, List<String> headerIn) {
      if (this.f != null) {
         return false;
      } else {
         try {
            if (pathIn.getParent() != null) {
               C_5144_.m_257659_(pathIn.getParent());
            }

            Writer writer = Files.newBufferedWriter(pathIn, StandardCharsets.UTF_8);

            try {
               writer.write(this.a(typeIn, headerIn));
            } catch (Throwable var8) {
               if (writer != null) {
                  try {
                     writer.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (writer != null) {
               writer.close();
            }

            this.f = pathIn;
            return true;
         } catch (Throwable var9) {
            a.error("Could not save crash report to {}", pathIn, var9);
            return false;
         }
      }
   }

   public boolean a(Path pathIn, C_336586_ typeIn) {
      return this.a(pathIn, typeIn, List.of());
   }

   public C_140956_ f() {
      return this.i;
   }

   public C_4909_ a(String name) {
      return this.a(name, 1);
   }

   public C_4909_ a(String categoryName, int stacktraceLength) {
      C_4909_ crashreportcategory = new C_4909_(categoryName);

      try {
         if (this.g) {
            int i = crashreportcategory.m_128148_(stacktraceLength);
            StackTraceElement[] astacktraceelement = this.d.getStackTrace();
            StackTraceElement stacktraceelement = null;
            StackTraceElement stacktraceelement1 = null;
            int j = astacktraceelement.length - i;
            if (j < 0) {
               a.error("Negative index in crash report handler ({}/{})", astacktraceelement.length, i);
            }

            if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
               stacktraceelement = astacktraceelement[j];
               if (astacktraceelement.length + 1 - i < astacktraceelement.length) {
                  stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
               }
            }

            this.g = crashreportcategory.m_128156_(stacktraceelement, stacktraceelement1);
            if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length) {
               this.h = new StackTraceElement[j];
               System.arraycopy(astacktraceelement, 0, this.h, 0, this.h.length);
            } else {
               this.g = false;
            }
         }
      } catch (Throwable var9) {
         var9.printStackTrace();
      }

      this.e.add(crashreportcategory);
      return crashreportcategory;
   }

   public static CrashReport a(Throwable causeIn, String descriptionIn) {
      while (causeIn instanceof CompletionException && causeIn.getCause() != null) {
         causeIn = causeIn.getCause();
      }

      CrashReport crashreport;
      if (causeIn instanceof C_5204_ reportedexception) {
         crashreport = reportedexception.a();
      } else {
         crashreport = new CrashReport(descriptionIn, causeIn);
      }

      return crashreport;
   }

   public static void g() {
      C_181893_.m_182327_();
      new CrashReport("Don't panic!", new Throwable()).a(C_336586_.f_337619_);
   }
}
